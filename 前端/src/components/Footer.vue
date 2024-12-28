<template>
  <div class="wrap">
    <el-space :size="20">
      <el-image style="width: 170px; height: 60px; margin-right: 20px; " src="src/assets/logoDark.png"></el-image>
      <div style="display: flex; flex-direction: column; gap: 10px;">
        <el-space :size="20">
          <el-button link>关于我们</el-button>
          <el-button link>服务条款</el-button>
          <el-button link>机构管理</el-button>
          <el-button link>免责声明</el-button>
          <el-button link>新闻资讯</el-button>
        </el-space>
        <el-space :size="20">
          <el-button link>友情链接</el-button>
          <el-button link>深大优课</el-button>
          <el-button link>广东学历提升网</el-button>
        </el-space>
        <el-space :size="20">
          <el-button link>粤ICP备16050337号</el-button>
          <el-button link>粤公网安备 44030502001269号</el-button>
          <el-button link>增值电信业务经营许可证B2-20221173</el-button>
        </el-space>
        <el-space :size="20">
          <el-button link>Copyright © 2015- 2024深圳市深大优课教育有限公司 (优课联盟)</el-button>
        </el-space>
      </div>
    </el-space>
    <div style="display: flex; flex-direction: column;">
      <el-text type="info" size="large">在线客服请点击右侧客服图标</el-text>
      <el-text type="info">客服工作时间：周一至周五 09:30-18:30</el-text>
    </div>
  </div>
</template>

<script setup>
import {useRoute, useRouter} from 'vue-router'
import {ref} from "vue"

const router = useRouter()
const currentRoute = useRoute()
const input = ref('')
// 路由跳转
const go = (path) => router.push(path)
</script>


<style scoped>
.wrap {
  display: flex;
  padding: 20px 200px;
  background-color: #313131;
  width: 100%;
  justify-content: space-between;
}
</style>