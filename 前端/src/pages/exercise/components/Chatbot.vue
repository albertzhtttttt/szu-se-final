<template>
  <!-- 圆形按钮，点击时触发 toggleChat -->
  <div class="circle-button" @click="toggleChat"></div>

  <div v-show="isChatVisible" :class="['chatbot-window', mode]">
    <div class="chat-header">
      <span>聊天窗口</span>
      <button @click="toggleChat" class="close-button">×</button>
    </div>
    <div class="chat-body">
      <div class="messages">
        <div v-for="(message, index) in messages" :key="index" class="message">
          <div v-if="message.sender === 'bot'" :class="['bot-message', mode]">{{ message.text }}</div>
          <div v-if="message.sender === 'user'" :class="['user-message', mode]">{{ message.text }}</div>
        </div>
      </div>
      <textarea v-model="userInput" @keyup.enter="sendMessage" placeholder="输入消息..." rows="3" :class="['user-input', mode]"></textarea>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Chatbot',
  props: {
    mode: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      isChatVisible: false,
      messages: [
        { sender: 'bot', text: '你好！有什么问题我可以帮助你解决吗？' }
      ],
      userInput: ''
    };
  },
  methods: {
    sendMessage() {
      if (this.userInput.trim()) {
        this.messages.push({ sender: 'user', text: this.userInput });
        const userInputCopy = this.userInput; // 直接复制字符串值
        this.userInput = '';
        // 模拟 Chatbot 响应
        setTimeout(() => {
          this.messages.push({ sender: 'bot', text: '这是我的回答：' + userInputCopy });
        }, 1000);
      }
    },
    toggleChat() {
      this.isChatVisible = !this.isChatVisible;
    }
  }
}

</script>

<style scoped>
.chatbot-window {
  display: flex;
  flex-direction: column;
  background: linear-gradient(145deg, #333, #444);
  color: white;
  border-radius: 10px;
  width: 300px;
  position: absolute;
  bottom: 20px;
  left: 20px;
  padding: 15px;
  box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
  transform: translateY(20px);
  transition: transform 0.3s ease, opacity 0.3s ease;
}
.chatbot-window.light{
  background: linear-gradient(145deg, #ddd, #eee);
  color: black;
}
.chatbot-window.dark{
  background: linear-gradient(145deg, #333, #444);
  color: white;
}
.chatbot-window.fade {
  opacity: 0;
  transform: translateY(50px);
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.chat-header span {
  font-weight: bold;
  font-size: 18px;
}

.close-button {
  background: none;
  border: none;
  color: white;
  font-size: 24px;
  cursor: pointer;
  transition: transform 0.2s ease;
}

.close-button:hover {
  transform: rotate(90deg);
}

.chat-body {
  display: flex;
  flex-direction: column;
}

.messages {
  flex: 1;
  overflow-y: auto;
  margin-bottom: 10px;
  max-height: 300px;
}

.message {
  margin: 10px 0;
  animation: fadeIn 0.5s ease-out;
}

.bot-message, .user-message {
  max-width: 80%;
  padding: 10px;
  border-radius: 10px;
  word-wrap: break-word;
}

.bot-message.light {
  background-color: #fff;
  align-self: flex-start;
}
.bot-message.dark {
  background-color: #555;
  align-self: flex-start;
}

.user-message.light {
  background-color: #4caf50;
  align-self: flex-end;
  color: black;
}
.user-message.dark {
  background-color: #4caf50;
  align-self: flex-end;
  color: white;
}

.user-input {
  width: 100%;
  padding: 10px;
  border-radius: 5px;
  border: none;
  resize: none;
  font-size: 14px;
  margin-top: 10px;
  transition: background-color 0.3s ease;
}
.user-input.light{
  background-color: #eee;
  color: black;
}
.user-input.dark{
  background-color: #333;
  color: white;
}
.user-input.light:focus {
  background-color: #fff;
}
.user-input.dark:focus {
  background-color: #444;
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

.circle-button {
  position: fixed;
  bottom: 100px;
  left: -15px;
  width: 30px;
  height: 30px;
  background-color: #444;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
  color: white;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.circle-button:hover {
  transform: translateX(15px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.3);
}
</style>
