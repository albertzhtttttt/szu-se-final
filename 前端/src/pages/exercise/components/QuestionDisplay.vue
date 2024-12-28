<template>
  <div class="question-display">
    <!-- 题目集选项状态 -->
    <div class="status-boxes">
      <div :class="['status-box', currentQuestionSet.allowRetake === 'yes' ? 'allow' : 'deny']">
        {{ currentQuestionSet.allowRetake === 'yes' ? '允许重新做题' : '禁止重新做题' }}
      </div>
      <div :class="['status-box', currentQuestionSet.allowViewAnswers === 'yes' ? 'allow' : 'deny']">
        {{ currentQuestionSet.allowViewAnswers === 'yes' ? '允许查看答案' : '禁止查看答案' }}
      </div>
    </div>

    <div class="header-container">
      <!-- 题目集名称 -->
      <h2 :class="['title', mode]">{{ currentQuestionSet.name }}</h2>
      <!-- 分数：只有当分数非 null 时才显示 -->
      <h2 v-if="currentQuestionSet.score" class="score">
        得分:{{ currentQuestionSet.score }} / {{totalScore}}
      </h2>
    </div>
    <!-- 遍历题目集中的所有题目 -->
    <div v-for="question in currentQuestionSet.questions" :key="question.id" :class="['question-item', mode]">
      <h3>{{ question.question }}</h3>

      <!-- 根据题目类型渲染不同的输入方式 -->

      <!-- 单选题 -->
      <div v-if="question.type === 'single'">
        <div v-for="(option, index) in question.options" :key="index">
          <label>
            <input type="radio" :name="'question-' + question.id" :value="option" v-model="question.userAnswer" :disabled="currentQuestionSet.score !== null && currentQuestionSet.allowRetake === 'no'"/>
            {{ option }}
          </label>
        </div>
      </div>

      <!-- 多选题 -->
      <div v-if="question.type === 'multi'">
        <div v-for="(option, index) in question.options" :key="index">
          <label>
            <input type="checkbox" :value="option" v-model="question.userAnswer" :disabled="currentQuestionSet.score !== null && currentQuestionSet.allowRetake === 'no'"/>
            {{ option }}
          </label>
        </div>
      </div>

      <!-- 判断题 -->
      <div v-if="question.type === 'trueFalse'">
        <label>
          <input type="radio" :name="'question-' + question.id" value="正确" v-model="question.userAnswer" :disabled="currentQuestionSet.score !== null && currentQuestionSet.allowRetake === 'no'"/>
          正确
        </label>
        <label>
          <input type="radio" :name="'question-' + question.id" value="错误" v-model="question.userAnswer" :disabled="currentQuestionSet.score !== null && currentQuestionSet.allowRetake === 'no'"/>
          错误
        </label>
      </div>

      <!-- 简答题 -->
      <div v-if="question.type === 'shortAnswer'">
        <textarea v-model="question.userAnswer" placeholder="请输入你的答案..." :class="['shortAnswerInput', mode]" :disabled="currentQuestionSet.score !== null && currentQuestionSet.allowRetake === 'no'"></textarea>
      </div>

      <!-- 下拉式答案框 -->
      <div v-if="currentQuestionSet.score!==null && currentQuestionSet.allowViewAnswers === 'yes'" :class="['answer-box',mode]"><!--没有提交且允许查看答案-->
        <div :class="['answer-header',mode]" @click="toggleAnswer(question.id)">
          <span>正确答案：</span>
          <span class="dropdown-icon">{{ question.isAnswerVisible ? '▲' : '▼' }}</span>
        </div>
        <div v-show="question.isAnswerVisible" class="answer-detail">
          <p>{{ question.answer }}</p>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
export default {
  name: 'QuestionDisplay',
  props: {
    currentQuestionSet: {
      type: Object,
      required: true
    },
    mode: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      // 可以在这里定义任何额外的本地状态或数据
      answeredQuestionIds:[],
    };
  },
  computed: {
    // 计算满分
    totalScore() {
      return this.currentQuestionSet.questions.reduce((total, question) => {
        if (question.type === 'shortAnswer') {
          return total + 20; // 简答题为 20 分
        }
        return total + 4; // 其他题型为 4 分
      }, 0);
    }
  },
  watch: {
    // 监听 questions 数组的变化，计算已答题的数量
    'currentQuestionSet.questions': {
      handler() {
        // console.log("已答题ID:",this.answeredQuestionIds);
        const answeredCount = this.currentQuestionSet.questions.filter(question => {
         // console.log("用户答题:",question.userAnswer);
          const isAnswered = this.checkAnswered(question);
          if (isAnswered && !this.answeredQuestionIds.includes(question.id)) {
            this.answeredQuestionIds.push(question.id);  // 如果题目已经回答，记录其 ID
          }
          else if (!isAnswered && this.answeredQuestionIds.includes(question.id)) {
            const index = this.answeredQuestionIds.indexOf(question.id);
            if (index !== -1) {
              this.answeredQuestionIds.splice(index, 1);  // 移除没有回答的题目 ID
            }
          }
          return isAnswered;
        }).length;
        // console.log(answeredCount);
        // console.log(this.answeredQuestionIds);
        // 发送已答题数量和已答题目 ID 到父组件
        this.$emit('updateAnsweredCount', answeredCount);
        this.$emit('updateAnsweredQuestionIds', this.answeredQuestionIds);
        //答题完应该存储在浏览器本地的currentQuestionSet.questions，而不是立马更新到index.vue（数据库）

      },
      deep: true,
      immediate: true
    },
    // 监听 currentQuestionSet 的变化，清空 answeredQuestionIds，不然切换题目集后会把之前答题情况拿过来
    'currentQuestionSet': {
      handler() {
        this.answeredQuestionIds = []; // 每次更新题目集时清空已回答问题的 ID
      },
      immediate: true
    },
  },
  methods: {
    checkAnswered(question) {
      if (question.type === 'shortAnswer') {
        return question.userAnswer && question.userAnswer.trim().length > 0; // 简答题有输入
      }
      // 对于其他题目，判断是否有选择
      return Array.isArray(question.userAnswer) ? question.userAnswer.length > 0 : question.userAnswer !== null;
    },
    // 切换答案的显示状态
    toggleAnswer(questionId) {
      const question = this.currentQuestionSet.questions.find(q => q.id === questionId);
      if (question) {
        question.isAnswerVisible = !question.isAnswerVisible;
      }
    },
  }
};
</script>

<style scoped>
.status-boxes {
  display: flex;
  gap: 20px;
  margin-bottom: 20px;
}

.status-box {
  padding: 10px 15px;
  border-radius: 5px;
  font-size: 14px;
  font-weight: bold;
  text-align: center;
  flex: 1;
}

.status-box.allow {
  background-color: #4CAF50; /* 绿色 */
  color: white;
}

.status-box.deny {
  background-color: #FF5722; /* 红色 */
  color: white;
}
.question-display {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.question-item {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #f9f9f9;
}
.question-item.light {
  background-color: #fff;
  color: #333;
}

.question-item.dark {
  background-color: #333;
  color: #fff;
}

.header-container {
  display: flex;
  align-items: center; /* 垂直居中对齐 */
  justify-content: space-between; /* 让它们左右对齐 */
  margin-bottom: 20px;
}
.title {
  font-size: 28px; /* 设置字体大小 */
  font-weight: bold;
  text-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
}
.title.light{
  color: #3b3b3b;
}
.title.dark{
  color: #fff;
}

.score {
  color: #ff5722; /* 橘红色 */
  margin-left: 20px; /* 分数与题目之间有间距 */
  font-size: 22px; /* 设置字体大小 */
  font-weight: bold;
  border: 2px solid #ff5722; /* 边框 */
  padding: 5px 15px;
  border-radius: 5px;
  background-color: rgba(255, 87, 34, 0.1);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.question-display h3 {
  font-size: 20px;
  margin-bottom: 10px;
}

.question-display label {
  display: block;
  margin: 8px 0;
}

.shortAnswerInput {
  width: 100%;
  height: 100px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}
.shortAnswerInput.light {
}
.shortAnswerInput.dark {
  background-color: #333;
  width: 100%;
  height: 100px;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}
input[type="radio"], input[type="checkbox"] {
  margin-right: 10px;
}
/* 下拉框样式 */
.answer-box {
  margin-top: 10px;  /* 给答案框添加间距 */
  padding: 10px;
  border-radius: 5px;
}
.answer-box.light{
  background-color: #f4f4f4;
}
.answer-box.dark{
  background-color: #222;
}
.answer-header {
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  font-weight: bold;
}

.dropdown-icon {
  font-size: 18px;
}

.answer-detail {
  margin-top: 10px;
  font-size: 16px;
  background-color: #888;
  color: white;
  padding: 10px;
  border-radius: 5px;
}
</style>
