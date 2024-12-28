<template>
  <div class="topic">
    <el-image src="src/assets/recTopic4.png" style="height: 125px; width: 572px;" fit="cover"
              draggable="false"></el-image>
  </div>
  <div class="wrap">
    <el-image src="src/assets/recTopic4-1.png" class="image" fit="cover"
              draggable="false" @click="go('/course')"></el-image>
    <div class="space">
      <div v-for="(course, index) in courses" :key="index" class="course-item">
        <div class="card" @click="goToCourseDetail(course)">
          <el-tag size="small" type="warning" class="tag">高校推荐课程</el-tag>
          <el-image :src="course.image" alt="Course Image" style="height: 100%; width: 100%;" fit="cover"/>
        </div>
        <h3 @click="goToCourseDetail(course)">{{ course.name }}</h3>
        <p>讲师：{{ course.teacher }}</p>
      </div>
    </div>
  </div>

</template>

<script setup>
import {ref} from 'vue'
import { useCourseStore } from '/src/store/store.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const courseStore = useCourseStore()
const courses = ref([
  {
    image: 'src/assets/rec6.png',
    name: '英语畅谈中国',
    teacher: '湖北大学 / 王志茹'
  },
  {
    image: 'src/assets/rec7.png',
    name: '现代科技与人类未来',
    teacher: '大连大学 / 刘金涛'
  },
  {
    image: 'src/assets/rec8.png',
    name: '宇宙探索与发现',
    teacher: '大连大学 / 刘金涛'
  },
  {
    image: 'src/assets/rec9.png',
    name: '女生穿搭技巧',
    teacher: '南昌大学 / 吴小吟'
  },
  {
    image: 'src/assets/rec10.png',
    name: '大学生职业生涯',
    teacher: '昆明理工大学 / 洪云'
  },
  {
    image: 'src/assets/rec11.png',
    name: '无处不在-传染病',
    teacher: '南昌大学 / 孙水林'
  },
  {
    image: 'src/assets/rec12.png',
    name: '中国近现代史纲要',
    teacher: '广州大学 / 张雪娇'
  },
  {
    image: 'src/assets/rec13.png',
    name: '生死学',
    teacher: '广州大学 / 胡宣安'
  }
])
const goToCourseDetail = (course) => {
  courseStore.setCourse(course)
  router.push({ path: '/course/detail' })
}
const go = (path) => router.push(path)
</script>

<style scoped>
.wrap {
  display: flex;
  flex-direction: row;
  background-color: #ffffff;
  padding: 30px 50px 10px 50px;
  width: 100%;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
}

.space {
  display: flex;
  flex-wrap: wrap;
  justify-content: flex-start;
  width: 100%;
}

.course-item {
  display: flex;
  flex-direction: column;
  justify-content: center;
  flex: 0 1 calc(25% - 50px);
  box-sizing: border-box;
  margin-bottom: 20px;
  margin-left: 50px;
}

.card {
  width: 100%;
  height: 150px;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  background-color: #fff;
  transition: transform 0.3s ease;
  overflow: hidden;
  position: relative;
}

.card:hover {
  transform: translateY(-5px);
  cursor: pointer;
}

.topic {
  display: flex;
  justify-content: center;
}

.image {
  height: 456px;
  width: 211px;
  cursor: pointer;
}

.tag {
  position: absolute;
  z-index: 10;
  background-color: rgba(255, 255, 255, 0.7);
}

h3 {
  cursor: pointer;
  font-weight: bold;
  transition: color 0.3s ease;
}

h3:hover {
  color: #409eff;
}
</style>