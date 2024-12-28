<template>
  <div class="learning-notes">
    <ul>
      <li v-for="(note, index) in notes" :key="index" class="note-item">
        <h3>{{ note.title }}</h3>
        <p>{{ note.content }}</p>
        <p class="timestamp">创建时间：{{ note.timestamp }}</p>
        <button @click="deleteNote(index)">删除</button>
      </li>
    </ul>
    <el-input v-model="newNoteTitle" placeholder="笔记标题" class="input-field" />
    <el-input type="textarea" v-model="newNoteContent" placeholder="写下你的笔记..." class="input-field" />
    <el-button type="primary" @click="addNote">添加笔记</el-button>
  </div>
</template>

<script setup>
import {ref} from 'vue';

const notes = ref([
  {
    title: 'JavaScript 基础',
    content: '今天学习了 JavaScript 中的变量声明、数据类型和控制流语句。',
    timestamp: '2023年12月12日 10:30'
  },
  {
    title: 'Vue 组件化',
    content: '学习了 Vue 中的组件、props、data、methods 等核心概念。',
    timestamp: '2023年12月10日 14:00'
  },
  {
    title: '数据结构与算法',
    content: '复习了线性表和二叉树的相关知识。',
    timestamp: '2023年12月8日 09:30'
  }
]);

const newNoteTitle = ref('');
const newNoteContent = ref('');

const addNote = () => {
  if (newNoteTitle.value.trim() && newNoteContent.value.trim()) {
    const timestamp = new Date().toLocaleString();
    notes.value.push({
      title: newNoteTitle.value.trim(),
      content: newNoteContent.value.trim(),
      timestamp
    });
    newNoteTitle.value = '';
    newNoteContent.value = '';
  }
};

const deleteNote = (index) => {
  notes.value.splice(index, 1);
};
</script>

<style scoped>
.learning-notes {
  padding: 20px;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

.note-item {
  background-color: #f9f9f9;
  margin-bottom: 15px;
  padding: 20px;
  border-radius: 8px;
  color: #424242;
}

.note-item h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.note-item p {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 5px;
}

.note-item .timestamp {
  font-size: 0.8rem;
  color: #999;
}

.input-field {
  margin-bottom: 10px;
  width: 100%;
}

button {
  background-color: #ff4d4f;
  border: none;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

button:hover {
  background-color: #d9363e;
}
</style>
