<template>
  <div class="college-page">
    <header class="page-header">
      <h1># 合作高校 #</h1>
      <p>截止到2024年12月共有143所高校加盟（顺序按首字英文字母排序）</p>
    </header>
    <main class="college-grid">
      <div v-for="(university, index) in universities" :key="index" class="university-card">
        <img :src="university.logo" :alt="university.name" class="university-logo" />
        <p>{{ university.name }}</p>
      </div>
    </main>
  </div>
</template>

<script>
export default {
  name: 'CollegePage',
  data() {
    return {
      universities: [
        { name: '安徽建筑大学', logo: 'http://assets.uooconline.com/upload/2017/03/03/0e70ee7aa1a5692e.jpg' },
        { name: '长春大学', logo: 'http://assets.uooconline.com/upload/2017/03/03/8b3253a708dffc0f.jpg' },
        { name: '长春光华学院', logo: 'http://assets.uooconline.com/upload/2017/03/03/4da35c80d1e8a4aa.jpg' },
        { name: '长江大学', logo: 'http://assets.uooconline.com/upload/2017/03/03/c0a2f3cb9328a4c1.jpg' },
        { name: '成都大学', logo: 'http://assets.uooconline.com/upload/2017/03/03/eeb3356b7c5f2d1f.jpg' },
        { name: '成都信息工程大学继续教育学院', logo: 'http://assets.uooconline.com/upload/2017/03/03/417baf9541fa6fdc.jpg' },
        { name: '大连大学', logo: 'http://assets.uooconline.com/upload/uooc-www/org/logo/2018/06/11/180611072040_e77faf_14ce36d3d539b600deea4ae3ee50352ac65cb7ba.jpg' },
        { name: '东莞理工学院', logo: 'http://assets.uooconline.com/upload/2017/03/03/34b4b0ce4b802d36.jpg' },
        { name: '福建工程学院', logo: 'http://assets.uooconline.com/upload/2017/03/03/f1d38db9d5ed1782.jpg' },
        { name: '广东工业大学', logo: 'http://assets.uooconline.com/upload/2017/03/03/8264dc210890b427.jpg' },
      ]
    };
  }
};
</script>

<style scoped>
.college-page {
  font-family: Arial, sans-serif;
  margin: 0 auto;
  max-width: 1200px;
  padding: 20px;
  min-height: 100vh;

}

.page-header {
  text-align: center;
  color: #67696a;
}

.college-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 20px;
  margin-top: 20px;
}

.university-card {
  background-color: white;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  padding: 20px;
  text-align: center;
}

.university-logo {
  max-width: 100%;
  height: auto;
  margin-bottom: 10px;
}
</style>