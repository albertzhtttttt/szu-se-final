<template>
  <div class="container">
    <!-- 显示小方块的容器 -->
    <div class="question-ids-container">
      <!-- 遍历问题的 ID，生成小方块 -->
      <div
          v-for="(question, index) in currentQuestionSet.questions"
          :key="question.id"
          class="question-id-box"
          :class="{
            answered: answeredQuestionIds.includes(question.id),
            'not-answered': !answeredQuestionIds.includes(question.id)
          }"
      ></div>
    </div>
    <!-- 进度条 -->
    <div v-if="userStore.isAuthenticated" class="progress-bar">
      <div class="progress" :style="{ width: progressWidth + '%' }"></div>
    </div>

    <!-- 提交按钮 登录后 未提交 已提交但是可以重复提交情况下显示-->
    <button v-if="userStore.isAuthenticated && (currentQuestionSet.score===null || currentQuestionSet.allowRetake === 'yes')" @click="submit" class="submit-button">提交</button>

    <!-- 布置作业按钮 教师用户才显示-->
    <button v-if="userStore.isAuthenticated && userStore.isTeacher" @click="arrangeHomework" class="submit-button">布置作业</button>

  </div>
</template>

<script>
import { useExerciseStore } from '/src/store/exercise.js';
import {ElMessage} from "element-plus";
import {useUserStore} from "@/store/user.js";

export default {
  name: 'ProgressBar',
  props: {
    currentQuestionSet: {
      type: Object,
      required: true
    },
    current: {
      type: Number,
      required: true
    },
    total: {
      type: Number,
      required: true
    },
    answeredQuestionIds:{
      type: Array,
      required: true
    },
    questionSets: {
      type: Array,
      required: true
    },
  },
  data(){
    return{
      userStore: useUserStore(),  //pinia：用户登录数据
      exerciseStore: useExerciseStore(),  //pinia：题目集数据
    }
  },
  computed: {
    progressWidth() {
      if (this.total === 0 ){
        return 0;
      }
      return (this.current / this.total) * 100;
    }
  },
  methods: {
    submit() {// 处理提交逻辑
      // console.log(this.answeredQuestionIds);
      // console.log(this.total);
      // 检查是否所有问题都已经回答，将结果发送到服务器等。
      if (this.answeredQuestionIds.length === this.total) {
        this.exerciseStore.updateQuestions(this.questionSets, this.currentQuestionSet.name);
        ElMessage.success('提交成功！');
      }
      else {
        ElMessage.error('还有未回答的问题，请完成所有问题后再提交！')
      }
    },
    arrangeHomework(){ //分发作业给学生
      this.exerciseStore.arrangeQuestions(this.currentQuestionSet);
      ElMessage.success('布置作业成功！');
    }
  },
  mounted() {
    //console.log(this.currentQuestionSet); // 在组件挂载后打印 currentQuestionSet
  }
}
</script>

<style scoped>
/* 容器样式 */
.container {
  position: fixed;
  display: flex;
  flex-direction: column;
  align-items: center; /* 使子元素在主轴上居中对齐 */
  justify-content: center; /* 使子元素在交叉轴上居中对齐 */
}

.progress-bar {
  width: 100%;
  background-color: #eee;
  height: 10px;
  border-radius: 5px;
  overflow: hidden;
}

.progress {
  height: 100%;
  background-color: #4caf50;
  transition: width 0.3s ease;
}

/* 小方块的样式 */
.question-ids-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  width: calc(5 * 18px + 4 * 20px); /* 5个小方块的宽度加上4个间隔 */
  margin-bottom: 10px;
}

.question-id-box {
  width: 18px;
  height: 18px;
  background-color: #ddd; /* 默认灰色 */
  margin: 5px;
  border-radius: 50%;
  transition: background-color 0.3s ease;
}

/* 答案已完成的样式 */
.question-id-box.answered {
  background-color: #4caf50; /* 绿色 */
}

.question-id-box.not-answered {
  background-color: #ddd; /* 默认灰色 */
}

/* 提交按钮样式 */
.submit-button {
  margin-top: 20px;
  padding: 8px 20px;
  background-color: #4caf50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
  transition: background-color 0.3s;
}

.submit-button:hover {
  background-color: #45a049;
}


</style>
