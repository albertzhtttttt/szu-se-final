<template>
  <div class="discussions">
    <ul>
      <li v-for="(discussion, index) in discussions" :key="index" class="discussion-item">
        <div class="discussion-details">
          <h3>{{ discussion.title }}</h3>
          <p><strong>讨论内容：</strong>{{ discussion.content }}</p>
          <p><strong>参与者：</strong>{{ discussion.participants.join(', ') }}</p>
          <p><strong>讨论时间：</strong>{{ discussion.timestamp }}</p>
        </div>
        <button @click="removeDiscussion(index)">删除</button>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const discussions = ref([
  {
    title: '关于前端框架的讨论',
    content: 'React 与 Vue 哪个更适合大型项目？',
    participants: ['张三', '李四', '王五'],
    timestamp: '2023年12月12日 10:30',
  },
  {
    title: 'JavaScript 深入讨论',
    content: '闭包的使用场景和原理分析。',
    participants: ['赵六', '孙七'],
    timestamp: '2023年12月11日 14:00',
  },
  {
    title: 'Vue 3 新特性',
    content: 'Vue 3 中 Composition API 的优势。',
    participants: ['周八', '郑九'],
    timestamp: '2023年12月10日 18:15',
  },
]);

const removeDiscussion = (index) => {
  discussions.value.splice(index, 1);
};
</script>

<style scoped>
.discussions {
  padding: 20px;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

.discussion-item {
  background-color: #f9f9f9;
  margin-bottom: 15px;
  padding: 30px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  display: flex;
  flex-direction: column;
  color: #424242;
}

.discussion-item h3 {
  font-size: 1.2rem;
  font-weight: bold;
}

.discussion-item p {
  font-size: 0.9rem;
  color: #666;
  margin: 5px 0;
}

.discussion-item button {
  align-self: flex-end;
  background-color: #ff4d4f;
  border: none;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.discussion-item button:hover {
  background-color: #d9363e;
}
</style>
