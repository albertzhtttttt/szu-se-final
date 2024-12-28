<template>
  <div class="container">
    <el-menu
        default-active="0"
        class="menu"
    >
      <el-menu-item index="1">
        <el-icon>
          <Suitcase/>
        </el-icon>
        <span>理学·工学</span>
      </el-menu-item>
      <el-menu-item index="2">
        <el-icon>
          <Monitor/>
        </el-icon>
        <span>计算机</span>
      </el-menu-item>
      <el-menu-item index="3">
        <el-icon>
          <ChatDotRound/>
        </el-icon>
        <span>教育·语言</span>
      </el-menu-item>
      <el-menu-item index="4">
        <el-icon>
          <PictureRounded/>
        </el-icon>
        <span>文学·艺术</span>
      </el-menu-item>
      <el-menu-item index="5">
        <el-icon>
          <User/>
        </el-icon>
        <span>职业·职场</span>
      </el-menu-item>
      <el-menu-item index="6">
        <el-icon>
          <Medal/>
        </el-icon>
        <span>哲史·文化</span>
      </el-menu-item>
      <el-menu-item index="7">
        <el-icon>
          <PieChart/>
        </el-icon>
        <span>经济·管理</span>
      </el-menu-item>
    </el-menu>
    <el-carousel class="carousel" height="392px" trigger="click" arrow="never">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <el-image :src="item" alt="Carousel Image" style="height: 392px;" fit="fill"/>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup>
import {ref} from 'vue'

const carouselItems = ref([
  'src/assets/homeBg1.png',
  'src/assets/homeBg2.png',
  'src/assets/homeBg3.png',
])
</script>

<style scoped>
.container {
  margin-top: 20px;
  display: flex;
  flex-direction: row;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  border-radius: 12px;
  overflow: hidden;
}

.menu {
  background-color: rgba(0, 0, 0, 0.7);
  color: #ffffff;
}

.menu .el-menu-item {
  color: #ffffff;
  padding: 0 100px;
}

.menu .el-menu-item:hover {
  background-color: rgba(0, 0, 0, 0.5);
}

.menu .el-menu-item.is-active {
  background-color: rgba(0, 0, 0, 0.5);
  color: #ffffff;
}

.carousel {
  width: 100%;
}

.el-carousel-item {
  width: 100%;
}
</style>