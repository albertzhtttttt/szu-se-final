<template>
  <div class="wrap">
    <div style="margin-top: 10px;">
      <el-text>课程分类：</el-text>
      <el-space>
        <el-button
            :type="currentCategory === 'all' ? 'primary' : 'info'"
            link
            @click="setCategory('all')"
        >
          全部
        </el-button>
        <el-button
            :type="currentCategory === '理学·工学' ? 'primary' : 'info'"
            link
            @click="setCategory('理学·工学')"
        >
          理学·工学
        </el-button>
        <el-button
            :type="currentCategory === '计算机' ? 'primary' : 'info'"
            link
            @click="setCategory('计算机')"
        >
          计算机
        </el-button>
        <el-button
            :type="currentCategory === '文学·艺术' ? 'primary' : 'info'"
            link
            @click="setCategory('文学·艺术')"
        >
          文学·艺术
        </el-button>
      </el-space>
    </div>
    <div style="margin-top: 10px;">
      <el-text>供课院校：</el-text>
      <el-space>
        <el-button
            :type="currentCollege === 'all' ? 'primary' : 'info'"
            link
            @click="setCollege('all')"
        >
          全部
        </el-button>
        <el-button
            :type="currentCollege === '深圳大学' ? 'primary' : 'info'"
            link
            @click="setCollege('深圳大学')"
        >
          深圳大学
        </el-button>
        <el-button
            :type="currentCollege === '湖北大学' ? 'primary' : 'info'"
            link
            @click="setCollege('湖北大学')"
        >
          湖北大学
        </el-button>
        <el-button
            :type="currentCollege === '广州大学' ? 'primary' : 'info'"
            link
            @click="setCollege('广州大学')"
        >
          广州大学
        </el-button>
        <el-button
            :type="currentCollege === '西南石油大学' ? 'primary' : 'info'"
            link
            @click="setCollege('西南石油大学')"
        >
          西南石油大学
        </el-button>
        <el-button
            :type="currentCollege === '昆明理工大学' ? 'primary' : 'info'"
            link
            @click="setCollege('昆明理工大学')"
        >
          昆明理工大学
        </el-button>

      </el-space>
    </div>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="综合排序" name="com"></el-tab-pane>
      <el-tab-pane label="名称升序" name="asc"></el-tab-pane>
      <el-tab-pane label="名称降序" name="desc"></el-tab-pane>
    </el-tabs>
    <course-item v-for="(course, index) in sortedCourses" :key="index" :course="course"></course-item>
  </div>
</template>

<script setup>
import {onMounted, ref, computed} from 'vue'
import {useCourseStore} from '/src/store/store.js'
import CourseItem from "@/pages/home/components/CourseItem.vue"

const activeName = ref('com')
const currentCategory = ref('all')
const currentCollege = ref('all')
const courseStore = useCourseStore()

const sortedCourses = computed(() => {
  // 返回排序后的副本
  let coursesCopy = [...courseStore.courses]

  // 根据课程分类进行筛选
  if (currentCategory.value !== 'all') {
    coursesCopy = coursesCopy.filter(course => course.keyWord === currentCategory.value)
  }

  // 根据供课院校进行筛选
  if (currentCollege.value !== 'all') {
    coursesCopy = coursesCopy.filter(course => course.teacher.includes(currentCollege.value))
  }

  //搜索
  if (courseStore.searchInfo !== '') {
    coursesCopy = coursesCopy.filter(course => course.teacher.includes(courseStore.searchInfo) || course.name.includes(courseStore.searchInfo))
  }

  // 根据当前排序条件处理
  if (activeName.value === 'asc') {
    return coursesCopy.sort((a, b) => a.name.localeCompare(b.name))
  } else if (activeName.value === 'desc') {
    return coursesCopy.sort((a, b) => b.name.localeCompare(a.name))
  } else {
    return coursesCopy
  }
})

const handleClick = async (tab, event) => {
  // 更新当前选中的排序标签
  activeName.value = tab.name
}

const setCategory = (category) => {
  currentCategory.value = category
}
const setCollege = (college) => {
  currentCollege.value = college
}

onMounted(() => {
    courseStore.loadCourses()
})

const courses = computed(() => courseStore.courses)

// 添加新课程的函数
const addNewCourse = () => {
  const newCourse = {
    image: 'src/assets/rec14.png',
    name: '新课程名称',
    teacher: '新课程老师',
    status: '开课中',
    info: '新课程简介',
    keyWord: '理学·工学'
  }
  courseStore.addCourse(newCourse)  // 调用 store 中的 addCourse 方法
}

</script>

<style scoped>
.wrap {
  display: flex;
  padding: 0 300px;
  width: 100%;
  min-height: 100vh;
  flex-direction: column;
}
</style>