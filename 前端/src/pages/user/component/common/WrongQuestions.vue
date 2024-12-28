<template>
  <div class="wrong-questions">
    <ul>
      <li v-for="(question, index) in wrongQuestions" :key="index" class="question-item">
        <div class="question-details">
          <h3>{{ question.questionText }}</h3>
          <p><strong>错误答案：</strong>{{ question.userAnswer }}</p>
          <p><strong>正确答案：</strong>{{ question.correctAnswer }}</p>
          <p><strong>错误时间：</strong>{{ question.timestamp }}</p>
        </div>
        <button @click="removeQuestion(index)">删除</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const wrongQuestions = ref([
  {
    questionText: 'JavaScript 中，哪个方法可以用来创建一个新的对象？',
    userAnswer: 'Object.create()',
    correctAnswer: 'Object.assign()',
    timestamp: '2023年12月10日 14:30',
  },
  {
    questionText: '在 HTML 中，哪个标签用来插入图像？',
    userAnswer: '<image>',
    correctAnswer: '<img>',
    timestamp: '2023年12月9日 16:00',
  },
  {
    questionText: 'Vue 的响应式数据是什么时候更新的？',
    userAnswer: '立即更新',
    correctAnswer: '异步更新',
    timestamp: '2023年12月8日 10:00',
  },
]);

const removeQuestion = (index) => {
  wrongQuestions.value.splice(index, 1);
};
</script>

<style scoped>
.wrong-questions {
  padding: 20px;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

.question-item {
  background-color: #f9f9f9;
  margin-bottom: 15px;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  color: #424242;
}

.question-item h3 {
  font-size: 1.2rem;
  font-weight: bold;
}

.question-item p {
  font-size: 0.9rem;
  color: #666;
  margin: 5px 0;
}

.question-item button {
  align-self: flex-end;
  background-color: #ff4d4f;
  border: none;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.question-item button:hover {
  background-color: #d9363e;
}
</style>
