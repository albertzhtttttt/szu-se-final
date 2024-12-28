<template>
  <div class="todo-list">
    <ul>
      <li v-for="(todo, index) in todos" :key="index" class="todo-item">
        <span>{{ todo.name }}</span>
        <button @click="removeTodo(index)">删除</button>
      </li>
    </ul>
    <el-input v-model="newTodo" placeholder="添加新待办" @keyup.enter="addTodo" />
  </div>
</template>

<script setup>
import { ref } from 'vue';

const todos = ref([
  { name: '学习Vue' },
  { name: '完成项目' },
  { name: '写博客' },
]);

const newTodo = ref('');

const addTodo = () => {
  if (newTodo.value.trim()) {
    todos.value.push({ name: newTodo.value.trim() });
    newTodo.value = '';
  }
};

const removeTodo = (index) => {
  todos.value.splice(index, 1);
};
</script>

<style scoped>
.todo-list {
  padding: 20px;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

.todo-item {
  display: flex;
  justify-content: space-between;
  padding: 8px;
  background-color: #f9f9f9;
  margin-bottom: 5px;
  border-radius: 5px;
  color: #181818;
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
