<template>
  <div class="my-todos">
    <h3>我的作业</h3>

    <div class="tasks-container">
      <!-- 未完成作业 -->
      <div class="pending-tasks">
        <h4 class="section-title">未完成作业</h4>
        <div v-for="(task, index) in pendingTasks" :key="task.id" class="task-card pending">
          <div class="task-info">
            <p><strong class="task-name">{{ task.name }}</strong></p>
            <p><strong>课程：</strong>{{ task.course }}</p>
            <p><strong>提交时间：</strong>{{ task.dueTime }}</p>
            <span class="status pending-status">未完成</span>
          </div>
          <button @click="openSubmitDialog(task)" class="btn btn-primary">提交作业</button>
        </div>
      </div>

      <!-- 已完成作业 -->
      <div class="completed-tasks">
        <h4 class="section-title">已完成作业</h4>
        <div v-for="(task, index) in completedTasks" :key="task.id" class="task-card completed">
          <div class="task-info">
            <p><strong class="task-name">{{ task.name }}</strong></p>
            <p><strong>课程：</strong>{{ task.course }}</p>
            <p><strong>提交时间：</strong>{{ task.submitTime }}</p>
            <p><strong>成绩：</strong>{{ task.score }}</p>
            <span class="status completed-status">已完成</span>
          </div>
          <button @click="viewAssignment(task)" class="btn btn-secondary">查看作业</button>
        </div>
      </div>
    </div>

    <!-- 提交作业组件 -->
    <SubmitAssignment
      v-if="showSubmitDialog"
      :task="currentTask"
      @close="closeSubmitDialog"
      @submit="handleTaskCompletion"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { ElMessage } from 'element-plus'; // 引入 Element UI 的消息提示组件
import SubmitAssignment from './SubmitAssignment.vue';

const tasks = ref([
  { 
    id: 1, 
    name: 'Java编程作业', 
    course: '计算机科学基础', 
    dueTime: '2024-12-30 23:59', 
    completed: false
  },
  { 
    id: 2, 
    name: '心理学论文', 
    course: '心理学概论', 
    dueTime: '2024-12-28 23:59', 
    completed: true, 
    submitTime: '2024-12-27 18:00', 
    score: 90 
  },
  { 
    id: 3, 
    name: '算法分析报告', 
    course: '数据结构与算法', 
    dueTime: '2024-12-29 23:59', 
    completed: true, 
    submitTime: '2024-12-29 20:00', 
    score: 85 
  },
  { 
    id: 4, 
    name: '云计算作业', 
    course: '云计算基础', 
    dueTime: '2024-12-25 23:59', 
    completed: false
  },
]);

// 计算属性，分别获取已完成和未完成的任务
const completedTasks = computed(() => tasks.value.filter(task => task.completed));
const pendingTasks = computed(() => tasks.value.filter(task => !task.completed));

// 控制提交作业对话框的显示
const showSubmitDialog = ref(false);
const currentTask = ref(null);

// 打开提交作业对话框
const openSubmitDialog = (task) => {
  showSubmitDialog.value = true;
  currentTask.value = task;
};

// 关闭提交作业对话框
const closeSubmitDialog = () => {
  showSubmitDialog.value = false;
};

// 提交作业后更新任务状态
const handleTaskCompletion = (assignmentData) => {
  // 更新任务的状态
  const task = currentTask.value;
  task.completed = true;  // 将任务标记为已完成
  
  // 弹出成功提示
  ElMessage.success('作业提交成功！');

  // 关闭提交作业对话框
  closeSubmitDialog();
};

// 查看作业功能
const viewAssignment = (task) => {
  // 这里可以根据需求进行查看作业的功能，比如打开一个新的页面或者显示详细内容
  ElMessage.info(`查看作业: ${task.name}`);
};
</script>

<style scoped>
h3 {
  font-size: 1.8rem;
  font-weight: bold;
  color: #333;
  margin-bottom: 20px;
}

.my-todos {
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

.tasks-container {
  display: flex;
  justify-content: space-between;
  gap: 30px;
}

.pending-tasks, .completed-tasks {
  width: 48%;
}

.section-title {
  font-size: 1.4rem;
  margin-bottom: 15px;
  color: #333;
  border-bottom: 2px solid #ccc;
  padding-bottom: 5px;
}

.task-card {
  padding: 15px;
  margin-bottom: 15px;
  border: 1px solid #ccc;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 220px;
  position: relative; /* Make task card relative to position the label */
}

.completed {
  background-color: #ebeaea;
}

.pending {
  background-color: #f8f9fa;
}

.task-info {
  flex: 1;
  padding-right: 15px;
}

.task-name {
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
}

.task-info p {
  margin: 5px 0;
}

.status {
  position: absolute; /* Position label at the top-right corner */
  top: 10px;
  right: 10px;
  padding: 4px 8px;
  font-size: 0.9rem;
  border-radius: 4px;
}

.completed-status {
  background-color: #28a745;
  color: white;
}

.pending-status {
  background-color: #ffc107;
  color: white;
}

button {
  padding: 8px 12px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

button.btn-secondary {
  background-color: #6c757d;
}

button.btn-primary {
  background-color: #28a745;
}
</style>
