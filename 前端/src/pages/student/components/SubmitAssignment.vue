<template>
  <div class="submit-assignment">
    <h2>提交作业</h2>

    <!-- 文本输入区 -->
    <div class="form-group">
      <label for="assignmentContent">作业内容</label>
      <textarea v-model="assignmentContent" id="assignmentContent" rows="5" class="form-control"></textarea>
    </div>

    <!-- 文件上传区 -->
    <div class="form-group">
      <label for="fileUpload">上传文件</label>
      <input type="file" @change="handleFileChange" multiple id="fileUpload" class="form-control-file">
      <ul v-if="files.length > 0" class="list-group mt-2">
        <li v-for="(file, index) in files" :key="index" class="list-group-item d-flex justify-content-between align-items-center">
          {{ file.name }}
          <button @click="removeFile(index)" class="btn btn-sm btn-danger">移除</button>
        </li>
      </ul>
    </div>

    <!-- 预览区 -->
    <div class="preview-area">
      <h3>预览</h3>
      <pre v-if="assignmentContent">{{ assignmentContent }}</pre>
      <div v-if="files.length > 0">
        <p>附件：</p>
        <ul>
          <li v-for="(file, index) in files" :key="index">
            <a :href="file.previewUrl" target="_blank">{{ file.name }}</a>
          </li>
        </ul>
      </div>
    </div>

    <!-- 提交按钮 -->
    <button @click="submitAssignment" class="btn btn-primary">提交</button>
  </div>
</template>

<script setup>
import { ref, defineEmits } from 'vue';

const assignmentContent = ref('');
const files = ref([]);

// 定义 emit 事件
const emit = defineEmits(['submit', 'close']);

// 处理文件选择
const handleFileChange = (event) => {
  const selectedFiles = event.target.files;
  for (let i = 0; i < selectedFiles.length; i++) {
    const file = selectedFiles[i];
    const previewUrl = URL.createObjectURL(file);
    files.value.push({ name: file.name, file, previewUrl });
  }
};

// 移除文件
const removeFile = (index) => {
  files.value.splice(index, 1);
};

// 提交作业
const submitAssignment = () => {
  // 提交数据
  const assignmentData = {
    content: assignmentContent.value,
    files: files.value,
  };

  // 触发父组件事件，通知作业已提交
  emit('submit', assignmentData);

  // 清空输入框内容
  assignmentContent.value = '';
  files.value = [];
};
</script>

<style scoped>
.submit-assignment {
  padding: 20px;
  background-color: #f8f9fa;
  border-radius: 8px;
}

.form-group {
  margin-bottom: 1rem;
}

.form-control,
.form-control-file {
  width: 100%;
  padding: 0.375rem 0.75rem;
  font-size: 1rem;
  line-height: 1.5;
  color: #495057;
  background-color: #fff;
  background-clip: padding-box;
  border: 1px solid #ced4da;
  border-radius: 0.25rem;
  transition: border-color 0.15s ease-in-out, box-shadow 0.15s ease-in-out;
}

.list-group-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.preview-area {
  margin-top: 20px;
  padding: 10px;
  background-color: #fff;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.preview-area pre {
  white-space: pre-wrap;
  word-wrap: break-word;
}

.btn {
  padding: 0.5rem 1rem;
  font-size: 1rem;
  line-height: 1.5;
  border-radius: 0.25rem;
  cursor: pointer;
}
</style>
