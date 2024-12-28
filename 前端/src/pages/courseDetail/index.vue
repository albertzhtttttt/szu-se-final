<template>
  <div class="wrap">
    <div style="width: 100%; background-color: #ffffff;display: flex;flex-direction: row;">
      <div style="height: 270px;width: 480px;">
        <el-image :src="imagePath(course.image)" style="height: 270px;width: 480px;" fit="cover"></el-image>
      </div>
      <div style="display: flex; flex-direction: column; justify-content: space-between;padding: 10px 10px">
        <h1>{{ course.name }}</h1>
        <el-space>
          <p>讲师：{{ course.teacher }}</p>
          <p>课程状态：{{ course.status }}</p>
        </el-space>
        <div>
          <el-button v-if="course.status === '开课中'" type="warning">参与课程</el-button>
          <el-button v-if="course.status === '已结束'" type="warning" disabled>课程结束</el-button>
        </div>
      </div>
    </div>
    <div style="width: 100%; background-color: #ffffff;display: flex;flex-direction: row; margin-top: 20px; padding: 10px;">
      <el-tabs v-model="activeName" style="width: 100%;">
        <el-tab-pane label="基本介绍" name="info">
          <h3>课程简介</h3>
          <p>{{ course.info }}</p>
        </el-tab-pane>
        <el-tab-pane label="课程评论" name="com">
          无
        </el-tab-pane>
      </el-tabs>

    </div>
  </div>
</template>

<script setup>
import {useCourseStore} from '/src/store/store.js'
import {computed, onMounted, ref} from "vue";

const courseStore = useCourseStore()

const course = computed(() => courseStore.selectedCourse)
const activeName = ref('info')

function imagePath(img) {
  if (img) {
    return new URL('http://localhost:5173/' + img, import.meta.url).href;
  }
  return '/assets/default-image.png';  // 如果没有图片路径，返回默认图片
}

</script>

<style scoped>
.wrap {
  display: flex;
  padding: 0 300px;
  width: 100%;
  min-height: 100vh;
  flex-direction: column;
  color: #424242;
}
</style>