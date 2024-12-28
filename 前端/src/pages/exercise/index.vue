<template>
  <div :class="['wrap', mode]">
    <div class="content">
      <!-- 左侧进度概览 -->
      <div class="left">
        <ProgressBar
            :current="answeredCount"
            :currentQuestionSet="currentQuestionSet"
            :total="currentQuestionSet.questions.length"
            :answeredQuestionIds="answeredQuestionIds"
            :questionSets="questionSets"
        />
      </div>

      <!-- 中间答题区域 -->
      <div v-if="this.userStore.isAuthenticated" class="center">
        <!-- 模式切换 -->
        <div class="mode-switch" @click="toggleMode">
          <svg v-if="mode === 'light'" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="32" height="32">
            <circle cx="12" cy="12" r="5" fill="currentColor" />
            <path d="M12 1v3M12 20v3M4.22 4.22l2.12 2.12M17.66 17.66l2.12 2.12M1 12h3M20 12h3M4.22 19.78l2.12-2.12M17.66 6.34l2.12-2.12" stroke="currentColor" stroke-width="2" stroke-linecap="round" />
          </svg>
          <svg v-else xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="32" height="32">
            <path d="M21.64 13.37a8.14 8.14 0 1 1-10-10 8.13 8.13 0 0 0 10 10Z" fill="currentColor" />
          </svg>
        </div>
        <QuestionDisplay
            :mode="mode"
            :currentQuestionSet="currentQuestionSet"
            @updateAnsweredCount="updateAnsweredCount"
            @updateAnsweredQuestionIds="updateAnsweredQuestionIds"
        />
      </div>

      <!-- 右侧题目列表 -->
      <div class="right">
        <QuestionList :mode="mode" :questionSets="questionSets" @selectQuestionSet="goToQuestion" />
      </div>

      <!-- LLM聊天窗口悬浮 -->
      <div class="chatbot">
        <Chatbot :mode="mode"/>
      </div>
      <!-- 创建新题目集 -->
      <div  v-if="userStore.isAuthenticated" class="floating-button" @click="showModal = !showModal">
        +
      </div>

      <!-- 创建新题目集设置框 -->
      <div v-if="showModal" class="modal-overlay show">
        <div class="modal">
          <h3>生成题目集</h3>
          <label>单选题数量：</label>
          <input type="number" v-model="form.singleChoice" min="0">
          <label>多选题数量：</label>
          <input type="number" v-model="form.multiChoice" min="0">
          <label>判断题数量：</label>
          <input type="number" v-model="form.trueFalse" min="0">
          <label>简答题数量：</label>
          <input type="number" v-model="form.shortAnswer" min="0">
          <label>选项随机排列：</label>
          <select v-model="form.shuffleOptions">
            <option value="no">否</option>
            <option value="yes">是</option>
          </select>
          <label>提交后是否允许查看答案：</label>
          <select v-model="form.allowViewAnswers">
            <option value="no">否</option>
            <option value="yes">是</option>
          </select>
          <label>是否可以重新做题：</label>
          <select v-model="form.allowRetake">
            <option value="no">否</option>
            <option value="yes">是</option>
          </select>
          <button @click="generateQuestions">确认生成</button>
          <button @click="toggleModal">关闭</button>
        </div>
      </div>


    </div>
  </div>
</template>

<script>
import ProgressBar from './components/ProgressBar.vue'
import QuestionDisplay from './components/QuestionDisplay.vue'
import QuestionList from './components/QuestionList.vue'
import Chatbot from './components/Chatbot.vue'
import {ElMessage} from "element-plus";
import {useUserStore} from "@/store/user.js";
import {useExerciseStore } from '@/store/exercise.js';


export default {
  name: 'exercise',
  components: {
    ProgressBar,
    QuestionDisplay,
    QuestionList,
    Chatbot
  },
  data() {
    return {
      mode: 'light',// 模式状态
      questionBank: [],
      userStore: useUserStore(),  //pinia：用户登录数据
      exerciseStore: useExerciseStore(),  //pinia：题目集数据
      questionSets: [],// 当前用户的题目数据
      selectedSetIndex: 0,  //当前选中题目集编号
      answeredCount:0,  //当前题目集已答题数量
      answeredQuestionIds:[],
      showModal: false, // 控制悬浮窗显示
      form: { //生成新题目集的form
        singleChoice: 10, // 默认单选题数量
        multiChoice: 10, // 默认多选题数量
        trueFalse: 5, // 默认判断题数量
        shortAnswer: 1, // 默认简答题数量
        shuffleOptions: 'no', // 默认选项是否随机排列
        allowViewAnswers: 'no', // 默认提交后是否允许查看答案
        allowRetake: 'no' // 默认是否允许重新做题
      },
    };
  },
  computed: {
    currentQuestionSet() {
      // questionSets数组为空，则传一个空的questionSet
      if (this.questionSets.length === 0) {
        return { name: null, questions: [] };
      }
      // 如果不为空，则返回当前选中的question set
      return this.questionSets[this.selectedSetIndex];
    }
  },
  methods: {
    goToQuestion(index) {
      this.selectedSetIndex = index;
      // console.log(this.selectedSetIndex);
    },
    updateAnsweredCount(count) {
      this.answeredCount = count;
      // console.log(`已答题数量：${this.answeredCount}`);
      // console.log(`总数量：${this.currentQuestionSet.questions.length}`);
    },
    updateAnsweredQuestionIds(ids) {
      this.answeredQuestionIds = ids;
      // console.log('已答题ID:', ids);
    },
    toggleMode() {
      this.mode = this.mode === 'light' ? 'dark' : 'light';
    },
    // Toggle modal visibility
    toggleModal() {
      this.showModal = !this.showModal;
    },
    shuffleArray(array) {
      for (let i = array.length - 1; i > 0; i--) {
        const j = Math.floor(Math.random() * (i + 1));
        [array[i], array[j]] = [array[j], array[i]]; // 交换元素
      }
      return array;
    },
    async loadQuestionBank() {//从数据库获取当前用户所有题目集
      try {
        await this.exerciseStore.initQuestionBank();
      } catch (error) {
        console.error("Failed to load init bank", error);
      }
    },
    async loadQuestionSets() {//从数据库获取当前用户所有题目集
      try {
        const questionSets = await this.exerciseStore.loadData();
        if (questionSets) {
          this.questionSets = questionSets;  // 加载完成后赋值
          // console.log("get question set from db", this.questionSets);
        }
      } catch (error) {
        console.error("Failed to load question sets:", error);
      }
    },
    async generateQuestions() {
      if (!this.userStore.isAuthenticated) {
        this.allowAlert = true; // 开启警告框
        return;
      }
      // 从题库中生成新的题集pinia
      await this.exerciseStore.generateQuestions(this.form);
      // 获取当前用户的题集
      // this.questionSets = this.exerciseStore.getUserQuestionSets();
      await this.loadQuestionSets();
      this.showModal = false;// 关闭弹窗
    },
  },
  watch:{
    'userStore.isAuthenticated'(newVal) {
      if (newVal) {
        // //这样写得到一个promise，长度非0，但实际长度为0导致progressbar报错
        // this.questionSets = this.exerciseStore.loadData();
        // console.log("get questionset from db",this.questionSets);
        this.loadQuestionBank();  // 将题库初始化抽取出来，每次用户登录重新加载题库，防止在新机器上indexDB无法初始化
        this.loadQuestionSets();
      }
      else {
        // 如果未认证，清空题目集和生成的题目集
        this.questionSets = [];
        this.exerciseStore.userQuestionSets = [];//清空题目集和生成的题目集防止其他用户也能看到前一个用户的
        // console.log("questionSets has been cleared");
      }
    }
  },
  mounted() {
    // 页面加载时检查认证状态
    if (!this.userStore.isAuthenticated) {
      ElMessage.error('登录后才能使用题库，请先登录！');  // 如果未认证，弹出错误提示
    }
    else{
      this.loadQuestionBank();  // 将题库初始化抽取出来，每次刷新页面重新加载题库，防止在新机器上indexDB无法初始化
      this.loadQuestionSets();
    }
  },
}

</script>

<style scoped>
/* 页面全局样式 */
.wrap {
  display: flex;
  min-height: 100vh;
  flex-direction: column;
  font-family: Arial, sans-serif;
  transition: background-color 0.3s, color 0.3s;
}

/* 根据模式切换背景色 */
.wrap.light {
  background-color: #fff;
  color: #333;
}

.wrap.dark {
  background-color: #333;
  color: #fff;
}

/* 内容区域 */
.content {
  display: flex;
  flex: 1;
  justify-content: space-between;
  gap: 30px;
  padding: 20px;
}

/* 左侧进度条 */
.left {
  width: 20%;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  margin-top: 40px;
}

/* 中间答题区域 */
.center {
  width: 55%;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  margin-top: 20px;
}

/* 右侧题目列表 */
.right {
  margin-top: 40px;
  width: 20%;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

/* 悬浮聊天窗口 */
.chatbot {
  position: fixed;
  bottom: 20px;
  left: 20px;
  background-color: #333;
  color: #fff;
  border-radius: 5px;
  cursor: pointer;
}

/* 模式切换按钮 */
.mode-switch {
  align-self: flex-end;
  cursor: pointer;
  color: currentColor;
  transition: transform 0.3s;
}
.mode-switch:hover {
  transform: scale(1.2);
}

/* 悬浮按钮 */
.floating-button {
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #409eff;
  color: white;
  font-size: 30px;
  width: 50px;
  height: 50px;
  border-radius: 50%;
  display: flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
}
/* 弹出框遮罩层 */
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: opacity 0.3s ease;
  opacity: 0; /* Hidden by default */
  visibility: hidden;
}

.modal-overlay.show {
  opacity: 1;
  visibility: visible;
}

/* 弹出框样式 */
.modal {
  background-color: #fff;
  padding: 30px;
  border-radius: 12px;
  width: 400px;
  max-width: 90%;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  text-align: left;
  animation: fadeIn 0.3s ease-out;
}

.modal h3 {
  font-size: 1.5rem;
  margin-bottom: 20px;
  color: #333;
}

.modal label {
  font-size: 1rem;
  margin-bottom: 8px;
  color: #555;
  display: block;
}

.modal input,
.modal select {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 1rem;
  transition: border-color 0.2s ease;
}

.modal input:focus,
.modal select:focus {
  border-color: #409eff;
  outline: none;
}

.modal button {
  width: 100%;
  padding: 12px;
  margin-bottom: 10px;
  background-color: #409eff;
  border: none;
  border-radius: 8px;
  color: #fff;
  font-size: 1.1rem;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.modal button:hover {
  background-color: #2976c7;
}

.modal button:active {
  transform: scale(0.98);
}

/* Fade in animation */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
<script setup lang="ts">
</script>