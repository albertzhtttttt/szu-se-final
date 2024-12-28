import { defineStore } from 'pinia';
import { toRaw } from 'vue';
import {useUserStore} from '/src/store/user.js'
import {getUserInfo} from "@/pages/user/utils/api.js";

const userStore = useUserStore();

// IndexedDB 操作封装
const dbName = 'ExerciseStore';
const dbVersion = 1; // 数据库版本

// 打开 IndexedDB 数据库
function openDb() {
    return new Promise((resolve, reject) => {
        const request = indexedDB.open(dbName, dbVersion);

        request.onupgradeneeded = (event) => {
            const db = event.target.result;
            // 如果数据库没有 "questionBank" 和 "userQuestionSets" 的对象存储空间，就创建
            if (!db.objectStoreNames.contains('questionBank')) {
                db.createObjectStore('questionBank', { keyPath: 'id' });
            }
            if (!db.objectStoreNames.contains('userQuestionSets')) {
                db.createObjectStore('userQuestionSets', { keyPath: 'username' });
            }
        };

        request.onsuccess = (event) => {
            resolve(event.target.result); // 返回数据库对象
        };

        request.onerror = (event) => {
            reject(event.target.error);
        };
    });
}

// 存储数据到 IndexedDB
function saveToDb(storeName, data) {
    return openDb().then((db) => {
        return new Promise((resolve, reject) => {
            const transaction = db.transaction(storeName, 'readwrite');
            const store = transaction.objectStore(storeName);
            // console.log(data);

            // 将 Proxy 转换为原始对象
            data = toRaw(data);  // 将 Vue 响应式对象转换为普通对象
            // console.log(data);

            // 如果是 userQuestionSets，确保数据格式正确
            if (storeName === 'userQuestionSets') {
                // 遍历 data 数组把每一项的quesitons的每一项toraw
                data.forEach(item => {
                    // 对每个题集的 questions 数组遍历
                    item.questions = item.questions.map(question => {
                        // 使用 toRaw 转换 Proxy 对象为普通对象
                        return toRaw(question);
                    });
                });
                console.log(data);
                const userName =  userStore.currentUser.username;
                // 以{username，【sets】}存储
                data = {
                    username: userName,
                    questionSets: data
                };
                console.log(data);
                // 直接put data（字典）
                store.put(data);
            }
            else {
                // 确保数据是可序列化的
                data = data.map(item => ({
                    ...item
                }));
                // console.log(data);
                data.forEach((item) => {
                    store.put(item); // 插入或更新数据
                });
            }


            transaction.oncomplete = () => resolve();
            transaction.onerror = (event) => reject(event.target.error);
        });
    });
}

// 从 IndexedDB 获取数据
function getFromDb(storeName, key = null) {
    return openDb().then((db) => {
        return new Promise((resolve, reject) => {
            const transaction = db.transaction(storeName, 'readonly');
            const store = transaction.objectStore(storeName);
            let request;

            if (key) {
                if(key === 'all'){
                    request = store.getAll(); // 获取所有数据
                }
                else{
                    request = store.get(key); // 通过 key 获取单条数据
                }
            }

            request.onsuccess = (event) => {
                resolve(event.target.result);
            };

            request.onerror = (event) => {
                reject(event.target.error);
            };
        });
    });
}

// 删除 IndexedDB 数据
function deleteFromDb(storeName, key) {
    return openDb().then((db) => {
        return new Promise((resolve, reject) => {
            const transaction = db.transaction(storeName, 'readwrite');
            const store = transaction.objectStore(storeName);
            store.delete(key); // 删除数据

            transaction.oncomplete = () => resolve();
            transaction.onerror = (event) => reject(event.target.error);
        });
    });
}

function saveToStudent(storeName, data) {
    return openDb().then((db) => {
        return new Promise((resolve, reject) => {
            const transaction = db.transaction(storeName, 'readwrite');
            const store = transaction.objectStore(storeName);
            // 将 Proxy 转换为原始对象
            data.questionSets = toRaw(data.questionSets);  // 将 Vue 响应式对象转换为普通对象
            for (let i = 0; i < data.questionSets.length; i++) {
                let rawItem = toRaw(data.questionSets[i]);
                console.log(rawItem); // 这是原始对象
                data.questionSets[i] = rawItem; // 更新数组中的元素
            }
                // // 遍历 data 数组把每一项的quesitons的每一项toraw
                // data.questionSets.forEach(item => {
                //     // 对每个题集的 questions 数组遍历
                //     item = item.map(questionSet =>{
                //         return toRaw(questionSet);
                //     });
                //     // 对每个题集的 questions 数组遍历
                //     item.questions = item.questions.map(question => {
                //         // 使用 toRaw 转换 Proxy 对象为普通对象
                //         return toRaw(question);
                //     });
                // });
            console.log(data);
            // 直接put data（字典）
            store.put(data);


            transaction.oncomplete = () => resolve();
            transaction.onerror = (event) => reject(event.target.error);
        });
    });
}


export const useExerciseStore = defineStore('exercise', {
    state: () => ({
        questionBank: [],
        userQuestionSets: [],
    }),
    actions: {
        async initQuestionBank(){
            // 如果没有数据，使用默认数据，并将默认数据存入indexDB
            this.questionBank = [
                // 定义一个题库，所有的题目类型都在这里面
                { "id": 1, "type": "single", "question": "地球是太阳系的第几颗行星？", "options": ["第一", "第二", "第三"], "answer": "第三", "userAnswer": null },
                { "id": 2, "type": "multi", "question": "以下哪些是联合国安全理事会常任理事国？", "options": ["中国", "法国", "德国", "俄罗斯", "美国"], "answer": ["中国", "法国", "俄罗斯", "美国"], "userAnswer": [] },
                { "id": 3, "type": "trueFalse", "question": "水的化学式是H2O。", "options": ["正确", "错误"], "answer": "正确", "userAnswer": null },
                { "id": 4, "type": "shortAnswer", "question": "请解释牛顿第三定律。", "answer": "作用力和反作用力大小相等，方向相反。", "userAnswer": null },
                { "id": 5, "type": "single", "question": "世界上最高的山峰是什么？", "options": ["珠穆朗玛峰", "乞力马扎罗山", "麦金利山"], "answer": "珠穆朗玛峰", "userAnswer": null },
                { "id": 6, "type": "multi", "question": "以下哪些是哺乳动物？", "options": ["海豚", "鲨鱼", "蝙蝠", "鳄鱼"], "answer": ["海豚", "蝙蝠"], "userAnswer": [] },
                { "id": 7, "type": "trueFalse", "question": "光年是时间单位。", "options": ["正确", "错误"], "answer": "错误", "userAnswer": null },
                { "id": 8, "type": "shortAnswer", "question": "描述一下什么是温室效应。", "answer": "温室效应是指地球大气层中的温室气体对太阳辐射的吸收和反射作用，导致地球表面温度升高的现象。", "userAnswer": null },
                { "id": 9, "type": "single", "question": "人类的DNA由多少对染色体组成？", "options": ["22", "23", "24"], "answer": "23", "userAnswer": null },
                { "id": 10, "type": "multi", "question": "以下哪些是人体必需的微量元素？", "options": ["铁", "钙", "锌", "钠"], "answer": ["铁", "锌"], "userAnswer": [] },
                { "id": 11, "type": "trueFalse", "question": "维生素C是人体必需的维生素之一。", "options": ["正确", "错误"], "answer": "正确", "userAnswer": null },
                { "id": 12, "type": "shortAnswer", "question": "请解释什么是光合作用。", "answer": "光合作用是植物、藻类和某些细菌利用光能将二氧化碳和水转化为有机物和氧气的过程。", "userAnswer": null },
                { "id": 13, "type": "single", "question": "世界上最大的海洋是什么？", "options": ["太平洋", "大西洋", "印度洋", "北冰洋"], "answer": "太平洋", "userAnswer": null },
                { "id": 14, "type": "multi", "question": "以下哪些是计算机编程语言？", "options": ["Java", "C++", "Python", "SQL"], "answer": ["Java", "C++", "Python"], "userAnswer": [] },
                { "id": 15, "type": "trueFalse", "question": "二进制数1011等于十进制数11。", "options": ["正确", "错误"], "answer": "正确", "userAnswer": null },
                { "id": 16, "type": "shortAnswer", "question": "描述一下什么是相对论。", "answer": "相对论是爱因斯坦提出的描述物体高速运动和强引力场中物理现象的理论，包括狭义相对论和广义相对论。", "userAnswer": null },
                { "id": 17, "type": "single", "question": "人体最大的器官是什么？", "options": ["心脏", "肝脏", "皮肤", "肺"], "answer": "皮肤", "userAnswer": null },
                { "id": 18, "type": "multi", "question": "以下哪些是太阳系的行星？", "options": ["水星", "火星", "地球", "冥王星"], "answer": ["水星", "火星", "地球"], "userAnswer": [] },
                { "id": 19, "type": "trueFalse", "question": "DNA和RNA都是核酸。", "options": ["正确", "错误"], "answer": "正确", "userAnswer": null },
                { "id": 20, "type": "shortAnswer", "question": "请解释什么是生态系统。", "answer": "生态系统是由生物群落和其生存环境相互作用形成的一个统一整体。", "userAnswer": null },
                { "id": 21, "type": "single", "question": "世界上最长的河流是什么？", "options": ["尼罗河", "亚马逊河", "长江", "密西西比河"], "answer": "尼罗河", "userAnswer": null },
                { "id": 22, "type": "multi", "question": "以下哪些是哺乳动物的特征？", "options": ["哺乳", "卵生", "有毛发", "冷血"], "answer": ["哺乳", "有毛发"], "userAnswer": [] },
                { "id": 23, "type": "trueFalse", "question": "所有鸟类都能飞行。", "options": ["正确", "错误"], "answer": "错误", "userAnswer": null },
                { "id": 24, "type": "shortAnswer", "question": "描述一下什么是遗传学。", "answer": "遗传学是研究生物体遗传和变异规律的科学。", "userAnswer": null },
                { "id": 25, "type": "single", "question": "光速在真空中的速度是多少？", "options": ["299,792,458米/秒", "3.0 x 10^8米/秒", "7.9 x 10^6米/秒"], "answer": "299,792,458米/秒", "userAnswer": null },
                { "id": 26, "type": "multi", "question": "以下哪些是计算机硬件的组成部分？", "options": ["CPU", "GPU", "内存", "硬盘"], "answer": ["CPU", "GPU", "内存", "硬盘"], "userAnswer": [] },
                { "id": 27, "type": "trueFalse", "question": "人类的血液是红色的因为含有铁。", "options": ["正确", "错误"], "answer": "正确", "userAnswer": null },
                { "id": 28, "type": "shortAnswer", "question": "描述一下什么是细胞分裂。", "answer": "细胞分裂是细胞生命周期中的一个重要过程，通过这个过程，一个细胞分裂成两个或多个细胞。", "userAnswer": null },
                { "id": 29, "type": "single", "question": "人体最大的淋巴器官是什么？", "options": ["扁桃体", "脾脏", "胸腺", "阑尾"], "answer": "脾脏", "userAnswer": null }
            ];
            await saveToDb('questionBank', this.questionBank);
            console.log("default question bank saved to IndexDB")
        },
        // 初始化时加载数据, 获取用户的所有题目集
        async loadData() {
            try {
                // const userName =  userStore.currentUser.username;
                // 因为user.js更改，需要从服务器获取用户数据，而不是直接从本地获取，需要加上await，否则第一次登录会不显示，需要刷新才显示题目
                const userName = (await getUserInfo()).account.username;
                // 从 IndexedDB 获取数据
                const questionBank = await getFromDb('questionBank', 'all');
                const userQuestionSets = await getFromDb('userQuestionSets', userName);

                if (questionBank) {
                    this.questionBank = questionBank;
                    console.log("bank exits");
                } else {
                    await this.initQuestionBank();
                }

                if (userQuestionSets) {
                    console.log("get from db and extract questions :",userQuestionSets.questionSets);
                    this.userQuestionSets = userQuestionSets.questionSets;
                    return this.userQuestionSets;
                }
            } catch (error) {
                console.error('加载数据失败', error);
            }
        },
        // 根据用户生成新的题目集
        async generateQuestions(form) {
            const userName =  userStore.currentUser.username;
            // 根据用户角色来定制题目集
            const selectedQuestions = [];
            const { singleChoice, multiChoice, trueFalse, shortAnswer, shuffleOptions, allowViewAnswers, allowRetake } = form;

            selectedQuestions.push(...this.getRandomQuestions('single', singleChoice, shuffleOptions === 'yes'));
            selectedQuestions.push(...this.getRandomQuestions('multi', multiChoice, shuffleOptions === 'yes'));
            selectedQuestions.push(...this.getRandomQuestions('trueFalse', trueFalse, shuffleOptions === 'yes'));
            selectedQuestions.push(...this.getRandomQuestions('shortAnswer', shortAnswer));

            // 创建题集
            const newQuestionSet = {
                name: `${userName}的题集_${Date.now()}`,
                score: null,
                allowViewAnswers: allowViewAnswers,
                allowRetake: allowRetake,
                questions: selectedQuestions
            };

            // 保存用户的题目集
            this.userQuestionSets.push(newQuestionSet);
            console.log("userQuestionSets",this.userQuestionSets);

            // 保存数据到 IndexedDB
            await saveToDb('userQuestionSets', this.userQuestionSets);
        },

        getRandomQuestions(type, quantity, shuffle = false) {
            const filteredQuestions = this.questionBank.filter(q => q.type === type);
            const selected = [];
            for (let i = 0; i < quantity && filteredQuestions.length > 0; i++) {
                const randomIndex = Math.floor(Math.random() * filteredQuestions.length);
                selected.push(filteredQuestions.splice(randomIndex, 1)[0]);
            }
            if (shuffle) {
                selected.forEach(q => {
                    if (q.options && Array.isArray(q.options)) {
                        q.options = this.shuffleArray(q.options);
                    }
                });
            }
            return selected;
        },

        shuffleArray(array) {
            for (let i = array.length - 1; i > 0; i--) {
                const j = Math.floor(Math.random() * (i + 1));
                [array[i], array[j]] = [array[j], array[i]];
            }
            return array;
        },

        // 答题后提交，更新题目，计算得分
        async updateQuestions(newQuestionSets, QuestionSetName) {
            // 1. 查找当前题目集
            const currentSetIndex = this.userQuestionSets.findIndex(set => set.name === QuestionSetName);

            if (currentSetIndex === -1) {
                console.error('找不到对应的题目集');
                return;
            }
            // 获取当前题目集
            const currentSet = this.userQuestionSets[currentSetIndex];
            // 2. 初始化分数
            currentSet.score = 0;
            // 3. 遍历该题目集中的每一个问题，进行评分
            currentSet.questions.forEach((question) => {
                // 比较答案和用户答案是否相同
                if (Array.isArray(question.answer) && Array.isArray(question.userAnswer)) {
                    // 如果是多选题，比较数组
                    if (question.type === 'multi') {
                        if (question.answer.sort().join() === question.userAnswer.sort().join()) {
                            currentSet.score += 4;  // 其他类型加4分
                        }
                    }
                    else if (question.type === 'shortAnswer') {
                        if (question.answer === question.userAnswer) {
                            currentSet.score += 20; // 如果是简答题，加20分
                        }
                    }
                } else if (question.answer === question.userAnswer) {
                    // 如果是单选题或判断题，直接比较字符串
                    if (question.type === 'shortAnswer') {
                        currentSet.score += 20; // 如果是简答题，加20分
                    } else {
                        currentSet.score += 4;  // 其他类型加4分
                    }
                }
            });
            // 4. 更新题目集到 userQuestionSets
            this.userQuestionSets[currentSetIndex] = currentSet;
            // 保存数据到 IndexedDB
            await saveToDb('userQuestionSets', this.userQuestionSets);
            console.log("xkf",currentSet);
            // 返回更新后的题目集
            return currentSet;
        },

        // 老师布置作业
        async arrangeQuestions(homeworkSet) {
            console.log("家庭作业",homeworkSet);
            const studentQuestionSets = await getFromDb('userQuestionSets', 'student1');
            console.log("布置作业前的学生set",studentQuestionSets);
            studentQuestionSets.questionSets.push(homeworkSet);
            console.log("布置作业后的学生set",studentQuestionSets);
            // 保存数据到 IndexedDB
            await saveToStudent('userQuestionSets', studentQuestionSets);
        },
    },
});
