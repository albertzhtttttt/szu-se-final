<template>
  <div class="topic">
    <el-image src="src/assets/recTopic1.png" style="height: 110px; width: 615px;" fit="cover" draggable="false"></el-image>
  </div>
    <el-carousel class="carousel" height="240px" trigger="click" arrow="always">
      <el-carousel-item>
        <el-space :size="40" style="margin-top: 20px; padding: 0 80px;">
          <div v-for="(course, index) in courses" :key="index" class="course-item">
            <div class="card" @click="goToCourseDetail(course)">
              <el-image :src="course.image" alt="Course Image" style="height: 100%; width: 100%;" fit="cover"/>
            </div>
            <h3 @click="goToCourseDetail(course)">{{ course.name }}</h3>
            <p>讲师：{{ course.teacher }}</p>
          </div>
        </el-space>
      </el-carousel-item>
      <el-carousel-item>
        <el-space :size="40" style="margin-top: 20px; padding: 0 80px;">
          <div v-for="(course, index) in courses" :key="index" class="course-item">
            <div class="card" @click="goToCourseDetail(course)">
              <el-image :src="course.image" alt="Course Image" style="height: 100%; width: 100%;" fit="cover"/>
            </div>
            <h3 @click="goToCourseDetail(course)">{{ course.name }}</h3>
            <p>讲师：{{ course.teacher }}</p>
          </div>
        </el-space>
      </el-carousel-item>
    </el-carousel>
</template>

<script setup>
import {ref} from 'vue'
import { useCourseStore } from '/src/store/store.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const courseStore = useCourseStore()
const courses = ref([
  {
    image: 'src/assets/rec1.png',
    name: '软件工程',
    teacher: '深圳大学 / 杜文峰',
    status: '开课中',
    info:'这是课程简介',
  },
  {
    image: 'src/assets/rec2.png',
    name: '云计算基础及应用',
    teacher: '深圳大学 / 明仲',
    status: '开课中',
    info:'这是课程简介',
  },
  {
    image: 'src/assets/rec3.jpg',
    name: '舞蹈与健康',
    teacher: '武汉科技大学 / 袁芳',
    status: '开课中',
    info:'这是课程简介',
  },
  {
    image: 'src/assets/rec4.png',
    name: '药物的奥秘',
    teacher: '三峡大学 / 罗华军',
    status: '开课中',
    info:'这是课程简介',
  },
  {
    image: 'src/assets/rec5.png',
    name: '词曲小说中的人生大爱',
    teacher: '湖北大学 / 薛海',
    status: '开课中',
    info:'这是课程简介',
  }
])
const goToCourseDetail = (course) => {
  courseStore.setCourse(course)
  router.push({ path: '/course/detail' })
}
</script>

<style scoped>
.carousel {
  width: 100%;
}

.el-carousel-item {
  width: 100%;
}

.card {
  width: 200px;
  height: 150px;
  text-align: center;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  background-color: #fff;
  transition: transform 0.3s ease;
  overflow: hidden;
}

.card:hover {
  transform: translateY(-5px);
  cursor: pointer;
}

.topic {
  display: flex;
  justify-content: center;
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