<template>
  <div class="question-list">
    <div
        v-for="(set, setIndex) in questionSets"
        :key="set.name"
        :class="[
          'question-item',
          {
            active: setIndex === selectedSetIndex,
            dark: mode === 'dark',
            light: mode === 'light'
          }]"
        @click="selectQuestionSet(setIndex)"
    >
      <h3>{{ set.name }}</h3> <!-- 显示题目集的名称 -->
    </div>
  </div>

</template>

<script>
import {useUserStore} from '/src/store/user.js'
import { useExerciseStore } from '/src/store/exercise.js';
export default {
  name: 'QuestionList',
  props: {
    questionSets: {
      type: Array,
      required: true
    },
    mode: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      selectedSetIndex: 0, // 当前选中的题目集的索引
      userStore: useUserStore(),  //pinia：用户登录数据
      exerciseStore: useExerciseStore(),  //pinia：题目集数据
    }
  },
  methods: {
    // 选择题目集
    selectQuestionSet(setIndex) {
      this.$emit('selectQuestionSet', setIndex)
      this.selectedSetIndex = setIndex
    },
  }
}
</script>

<style scoped>
.question-list {
  display: flex;
  flex-direction: column;
  gap: 5px;
}

.question-item {
  padding: 8px;
  background-color: #f7f7f7;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 5px;
}

/*
把light/dark放在active前面，这样可以让active覆盖light/dark
hover放在最后面，防止被任何状态覆盖
*/
.question-item.light {
  background-color: #f7f7f7;
  color: #333;
}
.question-item.dark {
  background-color: #303030;
  color: #ddd;
}
.question-item.active {
  background-color: #4caf50;
  color: white;
}
.question-item:hover {
  background-color: #4caf50;
  color: white;
}


</style>
