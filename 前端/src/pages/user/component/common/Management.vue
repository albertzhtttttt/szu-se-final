<template>
  <div>
    <el-table
        :data="managementStore.getData"
        style="width: 100%"
        v-loading="loading"
        @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="角色" prop="role" width="140"></el-table-column>
      <el-table-column label="学号" prop="username" width="140"></el-table-column>
      <el-table-column label="昵称" prop="nickname" width="140"></el-table-column>
      <el-table-column label="状态" prop="status" width="140"></el-table-column>
      <el-table-column label="操作" width="250">
        <template v-slot="scope">
          <el-button @click="resetPwd(scope.row)" type="primary" size="small" style="margin-right: 10px;">
            重置密码
          </el-button>
          <el-button @click="toggleStatus(scope.row)" type="warning" size="small" style="margin-right: 10px;">
            {{ scope.row.status === '启用' ? '禁用' : '启用' }}
          </el-button>
          <el-button @click="deleteAcc(scope.row)" type="danger" size="small">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 批量操作 -->
    <el-row class="batch-actions" type="flex" justify="end" style="margin-top: 20px;">
      <el-button @click="resetSelected" type="primary" :disabled="selectedStudents.length === 0">批量重置密码
      </el-button>
      <el-button @click="toggleSelectedStatus" type="warning" :disabled="selectedStudents.length === 0">
        批量{{ selectedStudents.length > 0 && selectedStudents[0].status === '启用' ? '禁用' : '启用' }}
      </el-button>
      <el-button @click="deleteSelected" type="danger" :disabled="selectedStudents.length === 0">批量删除</el-button>
    </el-row>

    <!-- 删除确认对话框 -->
    <el-dialog
        title="删除确认"
        :visible.sync="deleteDialogVisible"
        width="400px"
    >
      <span>确定要删除选中的账号吗？此操作不可恢复。</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteDialogVisible = false">取消</el-button>
        <el-button type="danger" @click="deleteConfirmed">确认删除</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import {getStatus, setStatus, resetPassword, deleteAccount} from '@/pages/user/utils/api.js'
import {useUserStore} from "@/store/user.js";
import {useManagementStore} from "@/store/management.js";
import {useRoute, useRouter} from 'vue-router'
import {onMounted} from "vue";

const userStore = useUserStore();
const managementStore = useManagementStore();
const router = useRouter()
// 选中的学生
const selectedStudents = ref([]);
// 是否正在加载
const loading = ref(false);
// 删除确认对话框的显示状态
const deleteDialogVisible = ref(false);

onMounted(() => {
  managementStore.fetchManagementData()
})


// 处理选中的学生
const handleSelectionChange = (selection) => {
  selectedStudents.value = selection;
};
// 重置密码
const resetPwd = async (student) => {
  try {
    let flag = await userStore.checkToken();
    if (!flag) {
      await router.push('/home');
      return
    }
    let pwd = userStore.encryptPassword({"password": "123456"}).password;
    await resetPassword(student.uid, pwd)
    ElMessage.success(`重置了 ${student.username} 的密码`);
  } catch (e) {
    console.log(e)
  }
};
// 切换启用/禁用状态
const toggleStatus = async (student) => {
  const status = student.status === '启用' ? 0 : 1;
  try {
    let flag = await userStore.checkToken();
    if (!flag) {
      await router.push('/home');
      return
    }
    await setStatus(student.uid, status);
    student.status = student.status === '启用' ? '禁用' : '启用';
    ElMessage.success(`${student.username} 的状态已更新为 ${student.status}`);
  } catch (e) {
    console.log(e)
  }
};
// 删除账号
const deleteAcc = async (student) => {
  try {
    const confirmed = await ElMessageBox.confirm(
        `确定要删除 ${student.username} 的账号吗？此操作不可恢复。`,
        '删除确认',
        {
          confirmButtonText: '删除',
          cancelButtonText: '取消',
          type: 'warning',
        }
    );
    if (confirmed) {
      let flag = await userStore.checkToken();
      if (!flag) {
        await router.push('/home');
        return;
      }
      // 调用删除 API
      await deleteAccount(student.uid);
      // 更新 Pinia 数据，删除对应记录
      managementStore.deleteStudent(student.uid);  // 从 Pinia 中删除学生记录
      ElMessage.success(`${student.username} 的账号已删除`);
    }
  } catch (e) {
    console.log(e);
  }
};

// 批量重置密码
const resetSelected = () => {
  selectedStudents.value.forEach((student) => {
    resetPwd(student);
  });
};

// 批量切换状态
const toggleSelectedStatus = () => {
  const newStatus = selectedStudents.value[0].status === '启用' ? '禁用' : '启用';
  selectedStudents.value.forEach((student) => {
    student.status = newStatus;
  });
  ElMessage.success(`批量更新了 ${newStatus} 状态`);
};

// 批量删除
const deleteSelected = async () => {
  // 确认删除操作
  const confirmed = await ElMessageBox.confirm(
      '确定要删除选中的学生账号吗？此操作不可恢复。',
      '删除确认',
      {
        confirmButtonText: '删除',
        cancelButtonText: '取消',
        type: 'warning',
      }
  );
  if (confirmed) {
    for (const student of selectedStudents.value) {
      try {
        try {
          let flag = await userStore.checkToken();
          if (!flag) {
            await router.push('/home');
            return;
          }
          // 调用删除 API
          await deleteAccount(student.uid);
          // 更新 Pinia 数据，删除对应记录
          managementStore.deleteStudent(student.uid);  // 从 Pinia 中删除学生记录
          ElMessage.success(`${student.username} 的账号已删除`);
        } catch (e) {
          console.log(e);
        }
        managementStore.deleteStudent(student.uid);  // 从 Pinia 中删除该学生
      } catch (e) {
        console.log(`删除学生 ${student.username} 失败`, e);
      }
    }
    ElMessage.success('已删除选中的学生账号');
  }
};
</script>

<style scoped>
.batch-actions {
  margin-top: 20px;
}
</style>
