<template>
  <div class="wrap">
    <div class="content">
      <Menu />
      <RecCourse/>
      <OnlineCourse />
      <ComputerCourse />
      <EduCourse />
    </div>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import Menu from "@/pages/home/components/Menu.vue"
import RecCourse from "@/pages/home/components/RecCourse.vue"
import OnlineCourse from "@/pages/home/components/OnlineCourse.vue"
import ComputerCourse from "@/pages/home/components/ComputerCourse.vue"
import EduCourse from "@/pages/home/components/EduCourse.vue"

const carouselItems = ref([
  'src/assets/homeBg1.png',
  'src/assets/homeBg2.png',
  'src/assets/homeBg3.png',
])
</script>

<style scoped>
.wrap {
  display: flex;
  padding: 0 300px;
  width: 100%;
  min-height: 100vh;
  flex-direction: column;
}

.content {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

</style>