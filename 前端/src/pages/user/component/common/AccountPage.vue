<template>
  <div class="profile">
    <div class="header">
      <!-- 显示当前头像 -->
      <el-image
          v-if="account.avatar"
          :src="account.avatar"
          fit="cover"
          draggable="false"
          class="avatar"
      />
      <div class="info">
        <h2>{{ account.nickname }}</h2>
        <p>{{ account.signature }}</p>
        <el-form-item>
          <!-- 上传头像 -->
          <el-upload
              class="avatar-upload"
              :show-file-list="false"
              :before-upload="beforeAvatarUpload"
              :on-change="handleAvatarChange"
          >
            <el-button>上传头像</el-button>
          </el-upload>
        </el-form-item>
      </div>
    </div>

    <el-tabs v-model="activeTab" type="card" @tab-change="handleTabClick">
      <el-tab-pane label="个人信息" name="info">
        <el-form :model="account" label-width="100px">
          <el-form-item label="昵称">
            <el-input v-model="account.nickname"></el-input>
          </el-form-item>
          <el-form-item label="签名档">
            <el-input v-model="account.signature"></el-input>
          </el-form-item>
          <el-button style="margin-left: 100px;width:88px" @click="saveProfile">保存</el-button>
        </el-form>
      </el-tab-pane>

      <el-tab-pane label="待办事项" name="todo">
        <todo-list :todos="account.todos"/>
      </el-tab-pane>

      <el-tab-pane label="学习历史" name="history">
        <learning-history :history="account.history"/>
      </el-tab-pane>

      <el-tab-pane label="学习笔记" name="notes">
        <learning-notes :notes="account.notes"/>
      </el-tab-pane>

      <el-tab-pane label="我的收藏" name="favorites">
        <favorites :items="account.favorites"/>
      </el-tab-pane>

      <el-tab-pane label="我的错题" name="wrong-questions">
        <wrong-questions :questions="account.wrongQuestions"/>
      </el-tab-pane>

      <el-tab-pane label="我的讨论" name="discussions">
        <discussions :topics="account.discussions"/>
      </el-tab-pane>

      <!-- 学生 tab -->
      <el-tab-pane v-if="userStore.isStudent" label="学习中心" name="student">
      </el-tab-pane>

      <!-- 管理员或教师 tab -->
      <el-tab-pane v-if="userStore.isTeacher || userStore.isAdmin" label="管理" name="management">
        <management :topics="account.management"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {useAccountStore} from '@/store/account.js';
import {useUserStore} from '@/store/user.js'
import {ElImage, ElButton, ElForm, ElFormItem, ElInput, ElTabs, ElTabPane, ElUpload} from 'element-plus';
import TodoList from './TodoList.vue';
import LearningHistory from './LearningHistory.vue';
import LearningNotes from './LearningNotes.vue';
import Favorites from './Favorites.vue';
import WrongQuestions from './WrongQuestions.vue';
import Discussions from './Discussions.vue';
import Management from './Management.vue'
import {onMounted} from "vue";
import router from "@/router/index.js";

const accountStore = useAccountStore();
const userStore = useUserStore();
const account = ref(accountStore.accountData);
const activeTab = ref('info');

// 挂载时访问后台加载个人信息
onMounted(() => {
  accountStore.downloadAccountProfile(userStore.currentUser.uid)
});

const saveProfile = () => {
  accountStore.saveAccountProfile();
};

// 处理文件选择（显示预览）
const handleAvatarChange = (file, fileList) => {
  const reader = new FileReader();
  reader.onload = () => {
    accountStore.accountData.avatar = reader.result;  // 将图片转为 Data URL 格式，显示预览
  };
  reader.readAsDataURL(file.raw);  // 将选择的文件转换为 Base64 格式
};
const beforeAvatarUpload = (file) => {
  const isImage = file.type.startsWith('image/');
  if (!isImage) {
    this.$message.error('只能上传图片');
  }
  return isImage;  // 返回 true 继续上传，false 阻止上传
};
// 学生标签点击跳转
const handleTabClick = (tab) => {
  if (tab === "student" && userStore.isStudent) {
    router.push('/student'); // 跳转到学生页面
  }
};

</script>

<style scoped>
.profile {
  padding: 20px;
  background-color: #f9f9f9;
}

.header {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
}

.avatar {
  border-radius: 50%;
  width: 100px;
  height: 100px;
  margin-right: 20px;
}

.info {
  flex-grow: 1;
}

.info h2 {
  margin: 0;
  font-size: 1.5rem;
  color: #181818;
}

.info p {
  color: #888;
  margin-bottom: 20px;
}

.el-button {
  background-color: #409eff;
  color: white;
}

.el-button:hover {
  background-color: #338cc0;
}
</style>
