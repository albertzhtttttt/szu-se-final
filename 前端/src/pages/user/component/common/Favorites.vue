<template>
  <div class="favorites">
    <ul>
      <li v-for="(favorite, index) in favorites" :key="index" class="favorite-item">
        <el-image :src="favorite.image" alt="Favorite Course" class="favorite-image" fit="cover" />
        <div class="favorite-details">
          <h3>{{ favorite.courseName }}</h3>
          <p><strong>收藏时间：</strong>{{ favorite.timestamp }}</p>
          <button @click="removeFavorite(index)">删除</button>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const favorites = ref([
  {
    courseName: '前端开发基础',
    image: 'src/assets/rec6.png',
    timestamp: '2023年12月10日 12:30',
  },
  {
    courseName: 'JavaScript 深入',
    image: 'src/assets/rec7.png',
    timestamp: '2023年12月9日 15:00',
  },
  {
    courseName: 'Vue.js 高级应用',
    image: 'src/assets/rec8.png',
    timestamp: '2023年12月8日 11:30',
  },
]);

const removeFavorite = (index) => {
  favorites.value.splice(index, 1);
};
</script>

<style scoped>
.favorites {
  padding: 20px;
}

ul {
  list-style-type: none;
  padding-left: 0;
}

.favorite-item {
  display: flex;
  background-color: #f9f9f9;
  margin-bottom: 15px;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  color: #424242;
}

.favorite-item .favorite-image {
  width: 120px;
  height: 120px;
  margin-right: 20px;
  border-radius: 8px;
}

.favorite-details {
  flex: 1;
}

.favorite-item h3 {
  font-size: 1.2rem;
  font-weight: bold;
  margin-bottom: 10px;
}

.favorite-item p {
  font-size: 0.9rem;
  color: #666;
  margin-bottom: 10px;
}

.favorite-item button {
  background-color: #ff4d4f;
  border: none;
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  cursor: pointer;
}

.favorite-item button:hover {
  background-color: #d9363e;
}
</style>
