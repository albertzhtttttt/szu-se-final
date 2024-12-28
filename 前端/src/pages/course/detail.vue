<template>
  <div class="course-detail-container" v-if="course">
    <div class="image-container">
      <img :src="getCourseImageUrl(course.image)" alt="Course Image" />
    </div>
    <h1 class="course-title">{{ course.name }}</h1>
    <p class="teacher-info">讲师：{{ course.teacher }}</p>
    <p class="course-description">简介：{{ course.info }}</p>
    <div class="action-buttons">
      <el-button type="primary" @click="registerCourse">注册课程</el-button>
      <el-button @click="goBack">返回</el-button>
    </div>
  </div>
  <div v-else>
    <el-empty class="empty-container" description="未找到课程" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessage } from 'element-plus'; // 确保已安装并引入 Element Plus
import { useCourseStore } from '@/store/store.js';
import { useUserStore } from '@/store/user.js';

const route = useRoute();
const courseStore = useCourseStore();
const userStore = useUserStore();
const courseId = ref(route.params.id); // 获取路由参数中的课程 ID
let course = ref(null);

// 判断学生是否已注册该课程
const isRegistered = computed(() =>
  userStore.registeredCourses.includes(parseInt(courseId.value))
);

// 注册课程逻辑
const registerCourse = () => {
  if (!isRegistered.value) {
    courseStore.registerStudentToCourse(userStore.id, parseInt(courseId.value));
    userStore.addRegisteredCourse(parseInt(courseId.value));
    ElMessage.success('课程注册成功！');
  }
};

// 返回上一页
const goBack = () => {
  window.history.back();
};

// 处理图片路径
const getCourseImageUrl = (imageName) => {
  return new URL(`/src/assets/${imageName}`, import.meta.url).href;
};

// 监听路由变化，当路由参数改变时重新加载课程
watch(
  () => route.params.id,
  async (newId) => {
    courseId.value = newId;
    await loadCourseDetails();
  }
);

onMounted(async () => {
  await loadCourseDetails();
});

async function loadCourseDetails() {
  try {
    // 如果课程ID存在，则尝试从store或其他来源加载课程详情
    if (courseId.value) {
      const selectedCourse = courseStore.courses.find(
        (c) => c.id === parseInt(courseId.value)
      );
      console.log('Selected Course:', selectedCourse); // 调试输出

      if (selectedCourse) {
        course.value = selectedCourse;
      } else {
        console.warn('No matching course found for ID:', courseId.value); // 调试警告
        course.value = null;
      }
    } else {
      console.error('Invalid courseId parameter');
      course.value = null;
    }
  } catch (error) {
    console.error('Error loading course details:', error);
    course.value = null;
  }
}
</script>

<style scoped>
.course-detail-container {
  padding: 20px;
  background-color: #f5f5f5;
  min-height: 100vh;
}

.image-container img {
  width: 100%;
  max-width: 400px;
  height: auto;
  display: block;
  margin: 0 auto;
}

.course-title {
  font-size: 2em;
  text-align: center;
  margin-top: 20px;
}

.teacher-info,
.course-description {
  text-align: center;
  margin-top: 10px;
}

.action-buttons {
  margin-top: 20px;
  text-align: center;
}

.empty-container {
  padding: 50px;
  text-align: center;
}
</style>