<template>
  <div class="wrap">
    <el-space :size="20">
      <el-image style="width: 125px; height: 50px; margin-right: 20px; " src="src/assets/logo.png"></el-image>
      <el-button
          :type="currentRoute.path === '/home' ? 'primary' : 'info'"
          link
          @click="go('/home')"
      >
        首页
      </el-button>
      <el-button
          :type="currentRoute.path === '/course' ? 'primary' : 'info'"
          link
          @click="go('/course')"
      >
        课程
      </el-button>
      <el-button
          :type="currentRoute.path === '/college' ? 'primary' : 'info'"
          link
          @click="go('/college')"
      >
        联盟院校
      </el-button>
      <el-button
          :type="currentRoute.path === '/about' ? 'primary' : 'info'"
          link
          @click="go('/about')"
      >
        关于我们
      </el-button>
      <el-button
          :type="currentRoute.path === '/exercise' ? 'primary' : 'info'"
          link
          @click="go('/exercise')"
      >
        题库
      </el-button>
    </el-space>
    <el-space>
      <el-input v-model="searchInfo" style="width: 240px" placeholder="请输入课程/学校/主讲老师"/>
      <el-button @click="setSearch(searchInfo)">搜索</el-button>
      <div v-if="userStore.isAuthenticated">
        <!-- 显示头像 -->
        <el-avatar
            v-if="accountStore.accountData.avatar"
            :src="accountStore.accountData.avatar"
            style="margin-right: 20px;"
            @click="go('/user')"
        />
        <el-avatar
            v-else
            style="margin-right: 20px;"
            @click="go('/user')"
        >
          {{ accountStore.accountData.username }}
        </el-avatar>

        <el-button type="primary" plain @click="logout">登出</el-button>
      </div>

      <div v-else>
        <!-- 显示登录和注册按钮 -->
        <el-button type="primary" plain @click="login">登录</el-button>
        <el-button type="primary" @click="register">注册</el-button>
      </div>
    </el-space>
  </div>
  <!-- 登录 -->
  <el-dialog v-model="visible" width="20%" :show-close="false">
    <div style="padding: 20px; display: flex; justify-content: center; flex-direction: column; gap: 10px;">
      <el-form :model="userForm" label-width="auto" style="max-width: 600px" ref="userFormRef">
        <el-form-item label="账号" prop="username" :rules="[{required: true, message: '请输入用户名！'}]">
          <el-input v-model="userForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password" :rules="[{required: true, message: '请输入密码！'}]">
          <el-input v-model="userForm.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>
      </el-form>
      <el-button type="primary" @click="onConfirm">登录</el-button>
      <el-text>使用第三方账号直接登录</el-text>
      <div style="display: flex; justify-content: center;">
        <el-image src="src/assets/qq.png" style="width: 64px; height: 42px;" draggable="false"></el-image>
        <el-image src="src/assets/wx.png" style="width: 64px; height: 42px;" draggable="false"></el-image>
        <el-image src="src/assets/wb.png" style="width: 64px; height: 42px;" draggable="false"></el-image>
      </div>
    </div>
  </el-dialog>

  <!-- 注册 -->
  <el-dialog v-model="registerVisible" width="20%" :show-close="false">
    <div style="padding: 20px; display: flex; justify-content: center; flex-direction: column; gap: 10px;">
      <el-form :model="registerForm" label-width="auto" style="max-width: 600px" ref="registerFormRef">

        <!-- 角色选择 -->
        <el-form-item label="角色" prop="role" :rules="[{required: true, message: '请选择角色！'}]">
          <el-radio-group v-model="registerForm.role">
            <el-radio label="student">学生</el-radio>
            <el-radio label="teacher">教师</el-radio>
            <el-radio label="admin">系统管理员</el-radio>
          </el-radio-group>
        </el-form-item>

        <!-- 账号 -->
        <el-form-item label="账号" prop="username" :rules="[{required: true, message: '请输入用户名！'},
        { validator: checkSameUsername, trigger: 'blur'},
        { validator: validateUsername, trigger: 'blur'}]">
          <el-input v-model="registerForm.username" placeholder="请输入用户名"></el-input>
        </el-form-item>

        <!-- 密码 -->
        <el-form-item label="密码" prop="password" :rules="[{required: true, message: '请输入密码！'}]">
          <el-input v-model="registerForm.password" placeholder="请输入密码" show-password></el-input>
        </el-form-item>

        <!-- 确认密码 -->
        <el-form-item label="确认密码" prop="confirmPwd" :rules="[
        { required: true, message: '请确认密码！' },
        { validator: validatePwd, trigger: 'blur'}]">
          <el-input v-model="registerForm.confirmPwd" placeholder="请确认密码" show-password></el-input>
        </el-form-item>

      </el-form>
      <el-button type="primary" @click="onRegisterConfirm">注册</el-button>
    </div>
  </el-dialog>
</template>

<script setup>
import {useRoute, useRouter} from 'vue-router'
import {reactive, ref} from "vue"
import {useUserStore} from '/src/store/user.js'
import {useAccountStore} from "@/store/account.js";
import {ElMessage} from "element-plus";
import {registerValidateApi} from '@/pages/user/utils/api.js'
import {getStatus, setStatus, findUidByUsername} from "@/pages/user/utils/api.js";
import {useCourseStore} from "@/store/store.js"

const router = useRouter()
const currentRoute = useRoute()
const userStore = useUserStore()
const accountStore = useAccountStore();
const courseStore = useCourseStore()
const visible = ref(false)
const registerVisible = ref(false)
const searchInfo = ref('')
const userForm = reactive({
  username: 'rose',
  password: '123456',
})
const registerForm = reactive({
  role: '',
  username: '',
  password: '',
  confirmPwd: ''
})
const userFormRef = ref(null)
const registerFormRef = ref(null)
let errorCount = 0
// 路由跳转
const go = (path) => router.push(path)

const login = () => {
  visible.value = true
}
const register = () => {
  registerVisible.value = true
}
const setSearch = (searchInfo) => {
  courseStore.setInfo(searchInfo)
  go('/course')
}

const onConfirm = () => {
  if (userFormRef.value) {
    userFormRef.value.validate(async (valid) => {
      if (valid) {
        const uid = await findUidByUsername(userForm.username); // 获取用户 UID
        console.log(uid)
        const status = await getStatus(uid); // 获取账户状态

        if (status === 1) {  // 1 表示账户被冻结
          ElMessage.error('账户已被冻结，无法登录！');
          return;
        }

        try {
          await userStore.login(userForm); // 尝试登录
          ElMessage.success('登录成功！');
          visible.value = false;
          errorCount = 0
          await accountStore.downloadAccountProfile(userStore.currentUser.uid)
        } catch (e) {
          errorCount++; // 假设loginResult返回错误次数
          const remainingAttempts = 5 - errorCount;  // 剩余尝试次数

          if (remainingAttempts > 0) {
            ElMessage.error(`密码错误！剩余${remainingAttempts}次机会`);
          } else {
            await setStatus(uid, 1); // 错误次数超过5次，冻结账户
            ElMessage.error('错误次数过多，账户已被冻结！');
          }
        }
      }
    });
  }
};

const onRegisterConfirm = () => {
  if (registerFormRef.value) {
    registerFormRef.value.validate(async (valid) => {
      if (valid) {
        // 设置为await等待isAuthenticated置真
        await userStore.register(registerForm)
        if (userStore.isAuthenticated) {
          ElMessage.success('注册成功！')
          registerVisible.value = false;
          await accountStore.downloadAccountProfile(userStore.currentUser.uid)
        }
        // 错误判断已在响应拦截器处理：utils/request.js
      }
    });
  }
}
// 检验用户名是否重名
const checkSameUsername = async (rule, value, callback) => {
  try {
    const res = await registerValidateApi(value)
  } catch (e) {
    callback(new Error('用户名已占用！'))
  }
}
// 检验用户名是否合法
const validateUsername = (rule, value, callback) => {
  // 判断用户名是否为空
  if (value.length !== 10) {
    callback(new Error('学号或工号长10位！'));
  } else if (!/^\d{10}$/.test(value)) { // 判断是否是纯数字
    callback(new Error('学号或工号必须为纯数字！'));
  } else {
    callback(); // 验证通过
  }
};
// 比较密码是否一致
const validatePwd = (rule, value, callback) => {
  if (value !== registerForm.password) {
    callback(new Error('密码并不一致！')) // 自定义错误提示
  } else {
    callback() // 验证通过
  }
}
// 退出登录
const logout = () => {
  userStore.logout()
  go('/home')
  ElMessage.success('登出成功！')
}
</script>


<style scoped>
.wrap {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 300px;
  background-color: #ffffff;
  width: 100%;
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.1);
  border-bottom: 2px solid #ddd;
}
</style>