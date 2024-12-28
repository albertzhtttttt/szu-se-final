<template>
  <div class="learning-history">
    <ul>
      <li v-for="(history, index) in learningHistory" :key="index" class="history-item">
        <h3>{{ history.courseName }}</h3>
        <p><strong>学习进度：</strong>{{ history.progress }}%</p>
        <p><strong>学习时长：</strong>{{ history.duration }}小时</p>
        <p><strong>最后学习时间：</strong>{{ history.lastStudyTime }}</p>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const learningHistory = ref([
  {
    courseName: 'Java基础',
    progress: 80,
    duration: 10,
    lastStudyTime: '2023年12月10日 14:00',
  },
  {
    courseName: '前端开发',
    progress: 45,
    duration: 15,
    lastStudyTime: '2023年12月8日 09:30',
  },
  {
    courseName: '数据结构与算法',
    progress: 60,
    duration: 20,
    lastStudyTime: '2023年12月5日 11:00',
  },
  {
    courseName: '计算机网络',
    progress: 30,
    duration: 8,
    lastStudyTime: '2023年12月2日 16:45',
  },
  {
    courseName: '操作系统原理',
    progress: 50,
    duration: 12,
    lastStudyTime: '2023年12月1日 10:00',
  }
]);
</script>

<style scoped>
.learning-history {
  padding: 20px;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

.history-item {
  background-color: #f9f9f9;
  margin-bottom: 15px;
  padding: 10px;
  border-radius: 8px;
  color: #424242;
}

.history-item h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.history-item p {
  font-size: 0.9rem;
  color: #666;
}

.history-item p strong {
  color: #409eff;
}
</style>
