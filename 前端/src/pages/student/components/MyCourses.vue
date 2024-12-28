<template>
  <div class="my-courses">
    <h3>已注册课程</h3>
    <div class="course-list">
      <div class="course-card" @click="goToCourseDetail(course)" v-for="(course, index) in courses" :key="index">
        <div class="course-image-container">
          <el-image :src="course.image" alt="课程图片" class="course-image" fit="cover"></el-image>
        </div>
        <div class="course-info">
          <h4 class="course-name">{{ course.name }}</h4>
          <p class="course-teacher">{{ course.teacher }}</p>
          <p class="course-description">课程简介: <span
              class="description">详细内容可以在后续页面查看</span></p>
          <!-- 查看详情按钮 -->
          <router-link :to="`/courseDetail/index/${index}`">
            <el-button class="view-button">查看详情</el-button>
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue';

const name = ref('MyCourses')
const courses = ref([
  {
    image: 'src/assets/rec1.png',
    name: '软件工程',
    teacher: '深圳大学 / 杜文峰',
    status: '已结束',
    info: '本课程介绍了软件工程的基本概念、方法和技术，涵盖软件开发生命周期的各个阶段，包括需求分析、设计、实现、测试和维护。',
  },
  {
    image: 'src/assets/rec2.png',
    name: '云计算基础及应用',
    teacher: '深圳大学 / 明仲',
    status: '已结束',
    info: '本课程讲解云计算的基本概念、架构和应用，深入探讨虚拟化技术、云平台的搭建与管理，以及云计算在各行业中的实际应用。',
  },
  {
    image: 'src/assets/rec3.jpg',
    name: '舞蹈与健康',
    teacher: '武汉科技大学 / 袁芳',
    status: '已结束',
    info: '本课程结合舞蹈艺术与身体健康，讲解如何通过舞蹈增强身体素质、提高心理健康，探讨舞蹈对健康生活的积极影响。',
  },
  {
    image: 'src/assets/rec4.png',
    name: '药物的奥秘',
    teacher: '三峡大学 / 罗华军',
    status: '已结束',
    info: '本课程深入讲解药物的基本原理，探索药物的种类、作用机制及其在治疗疾病中的应用，帮助学生了解药物对人体的影响。',
  },
  {
    image: 'src/assets/rec5.png',
    name: '词曲小说中的人生大爱',
    teacher: '湖北大学 / 薛海',
    status: '已结束',
    info: '本课程分析词曲小说中表达的大爱主题，探讨如何通过文学艺术作品表现人类情感与社会责任。',
  },
  {
    image: 'src/assets/rec6.png',
    name: '现代科技与人类未来',
    teacher: '大连大学 / 刘金涛',
    status: '已结束',
    info: '本课程讨论现代科技发展对社会、经济和文化的深远影响，探索未来科技将如何塑造人类生活的各个方面。',
  }

]);


import {useCourseStore} from '/src/store/store.js'
import {useRouter} from 'vue-router'

const router = useRouter()
const courseStore = useCourseStore()
const goToCourseDetail = (course) => {
  courseStore.setCourse(course)
  router.push({path: '/course/detail'})
}
</script>

<style scoped>
.my-courses {
  padding: 20px;
  background-color: #f9f9f9;
}

h3 {
  font-size: 1.8rem;
  color: #333;
  margin-bottom: 20px;
}

.course-list {
  display: flex;
  flex-wrap: wrap; /* 允许换行 */
  gap: 20px; /* 卡片之间的间隙 */
  justify-content: space-between; /* 调整卡片对齐方式 */
}

.course-card {
  display: flex; /* 横向排列图片和内容 */
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: transform 0.3s, box-shadow 0.3s;
  padding: 15px;
  position: relative;
  width: calc(50% - 10px); /* 每行显示两个卡片 */
  height: 180px; /* 高度自适应 */
}

.course-card:hover {
  transform: translateY(-10px);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
}

.course-image-container {
  flex-shrink: 0;
  width: 33%; /* 图片宽度占卡片的1/3 */
  height: 100%; /* 高度自适应 */
  margin-right: 20px;
  display: flex;
  justify-content: flex-end; /* 图片向右对齐 */
}

.course-image {
  width: 100%;
  height: 100%;
  object-fit: contain; /* 按比例缩放图片 */
  border-radius: 8px;
}

.course-info {
  flex-grow: 1; /* 让信息部分占据剩余空间 */
  display: flex;
  flex-direction: column;
  justify-content: space-between; /* 内容与按钮对齐 */
  padding: 10px;
}

.course-name {
  font-size: 1.4rem;
  font-weight: bold;
  margin: 10px 0;
  text-align: left; /* 文字左对齐 */
}

.course-teacher {
  font-size: 1rem;
  color: #555;
  text-align: left; /* 文字左对齐 */
}

.course-description {
  font-size: 0.9rem;
  color: #777;
  text-align: left; /* 文字左对齐 */
}

.description {
  color: #333;
}

.view-button {
  position: absolute;
  bottom: 15px; /* 调整按钮的位置 */
  right: 15px;
  padding: 8px 15px;
  background-color: #409eff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
}

.view-button:hover {
  background-color: #66b1ff;
}
</style>
