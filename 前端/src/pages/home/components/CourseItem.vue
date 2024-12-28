<template>
  <div>
    <el-space :size="20">
      <div class="image-container" @click="goToCourseDetail(course)">
        <el-image :src="course.image" style="height: 100%; width: 100%;" fit="cover"
                  draggable="false"></el-image>
      </div>
      <div style="display: flex; flex-direction: column; justify-content: space-between; height: 180px;">
        <h3 @click="goToCourseDetail(course)">{{ course.name }}</h3>
        <el-space :size="20">
          <p>讲师：{{ course.teacher }}</p>
          <p>{{ course.status }}</p>
        </el-space>
        <div>
          <el-text style="text-align: left;" type="info">{{ course.info }}</el-text>
        </div>
      </div>
    </el-space>
    <el-divider />
  </div>
</template>

<script setup>
import { useCourseStore } from '/src/store/store.js'
import { useRouter } from 'vue-router'

const router = useRouter()
const courseStore = useCourseStore()
defineProps({
  course: {
    type: Object,
    required: true
  }
})
const goToCourseDetail = (course) => {
  courseStore.setCourse(course)
  router.push({ path: '/course/detail' })
}
</script>

<style scoped>
.image-container {
  width: 250px;
  height: 180px;
  border-radius: 12px;
  transition: transform 0.3s ease;
  overflow: hidden;
  position: relative;
}

.image-container:hover {
  transform: translateY(-5px);
  cursor: pointer;
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