// store/courseStore.js
import { defineStore } from 'pinia'

export const useCourseStore = defineStore('course', {
    state: () => ({
        selectedCourse: null,  // 当前选择的课程
        courses: [],        // 课程列表
        searchInfo: ''        // 搜索关键字
    }),
    actions: {
        // 设置选中的课程
        setCourse(course) {
            this.selectedCourse = course
        },
        setInfo(info) {
            this.searchInfo = info
        },

        // 加载课程列表，如果本地存储中没有数据则使用默认数据
        loadCourses() {
            const storedCourses = localStorage.getItem('courses')
            if (storedCourses) {
                this.courses = JSON.parse(storedCourses)  // 从 localStorage 加载课程数据
            } else {
                this.courses = this.getDefaultCourses()  // 如果没有数据，使用默认课程
                this.saveCourses()  // 保存默认课程到 localStorage
            }
        },

        // 保存课程数据到 localStorage
        saveCourses() {
            localStorage.setItem('courses', JSON.stringify(this.courses))
        },

        // 获取默认课程列表
        getDefaultCourses() {
            return [
                {
                    image: 'src/assets/rec6.png',
                    name: '英语畅谈中国',
                    teacher: '湖北大学 / 王志茹',
                    status: '开课中',
                    info:'这是课程简介',
                    keyWord: '教育·语言',
                },
                {
                    image: 'src/assets/rec7.png',
                    name: '现代科技与人类未来',
                    teacher: '大连大学 / 刘金涛',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '理学·工学',
                },
                {
                    image: 'src/assets/rec8.png',
                    name: '宇宙探索与发现',
                    teacher: '大连大学 / 刘金涛',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '理学·工学',
                },
                {
                    image: 'src/assets/rec9.png',
                    name: '女生穿搭技巧',
                    teacher: '南昌大学 / 吴小吟',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '',
                },
                {
                    image: 'src/assets/rec10.png',
                    name: '大学生职业生涯',
                    teacher: '昆明理工大学 / 洪云',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '',
                },
                {
                    image: 'src/assets/rec11.png',
                    name: '无处不在-传染病',
                    teacher: '南昌大学 / 孙水林',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '理学·工学',
                },
                {
                    image: 'src/assets/rec12.png',
                    name: '中国近现代史纲要',
                    teacher: '广州大学 / 张雪娇',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '教育·语言',
                },
                {
                    image: 'src/assets/rec13.png',
                    name: '生死学',
                    teacher: '广州大学 / 胡宣安',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '教育·语言',
                },
                {
                    image: 'src/assets/rec14.png',
                    name: '大学计算机',
                    teacher: '深圳大学 / 李延红',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '计算机',
                },
                {
                    image: 'src/assets/rec15.jpg',
                    name: '办公软件',
                    teacher: '西南石油大学 / 王杨',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '计算机',
                },
                {
                    image: 'src/assets/rec16.png',
                    name: 'c君带你玩编程',
                    teacher: '昆明理工大学 / 方娇莉',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '计算机',
                },
                {
                    image: 'src/assets/rec17.png',
                    name: '嵌入式系统及应用',
                    teacher: '苏州大学 / 王宜怀',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '计算机',
                },
                {
                    image: 'src/assets/rec2.png',
                    name: '云计算基础及应用',
                    teacher: '深圳大学 / 明仲',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '计算机',
                },
                {
                    image: 'src/assets/rec18.png',
                    name: '云计算技术',
                    teacher: '南昌大学 / 王炜立',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '计算机',
                },
                {
                    image: 'src/assets/rec19.png',
                    name: '管理信息系统',
                    teacher: '武汉科技大学 / 张志清',
                    status: '已结束',
                    info:'这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
                    keyWord: '计算机',
                },
            ]
        },

        // 添加课程
        addCourse(course) {
            this.courses.push(course)
            this.saveCourses()  // 保存更新后的课程列表到 localStorage
        }
    }
})
