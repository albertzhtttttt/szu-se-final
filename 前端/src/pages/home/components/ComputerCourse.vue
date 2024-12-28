<template>
  <div class="topic">
    <el-image src="src/assets/recTopic3.png" style="height: 125px; width: 548px;" fit="cover"
              draggable="false"></el-image>
  </div>
  <div class="wrap">
    <el-image src="src/assets/recTopic3-1.png" class="image" fit="cover"
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
    image: 'src/assets/rec14.png',
    name: '大学计算机',
    teacher: '深圳大学 / 李延红',
    status: '已结束',
    info: '这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
    keyWord: '计算机',
  },
  {
    image: 'src/assets/rec15.jpg',
    name: '办公软件',
    teacher: '西南石油大学 / 王杨',
    status: '已结束',
    info: '这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
    keyWord: '计算机',
  },
  {
    image: 'src/assets/rec16.png',
    name: 'c君带你玩编程',
    teacher: '昆明理工大学 / 方娇莉',
    status: '已结束',
    info: '这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
    keyWord: '计算机',
  },
  {
    image: 'src/assets/rec17.png',
    name: '嵌入式系统及应用',
    teacher: '苏州大学 / 王宜怀',
    status: '已结束',
    info: '这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
    keyWord: '计算机',
  },
  {
    image: 'src/assets/rec2.png',
    name: '云计算基础及应用',
    teacher: '深圳大学 / 明仲',
    status: '已结束',
    info: '这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
    keyWord: '计算机',
  },
  {
    image: 'src/assets/rec18.png',
    name: '云计算技术',
    teacher: '南昌大学 / 王炜立',
    status: '已结束',
    info: '这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
    keyWord: '计算机',
  },
  {
    image: 'src/assets/rec19.png',
    name: '管理信息系统',
    teacher: '武汉科技大学 / 张志清',
    status: '已结束',
    info: '这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介这是课程简介',
    keyWord: '计算机',
  },
])
const goToCourseDetail = (course) => {
  courseStore.setCourse(course)
  router.push({path: '/course/detail'})
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