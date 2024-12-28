<template>
  <div class="about-page">
    <header class="page-header">
      <h1>INTRODUCTION</h1>
      <h2>联盟简介</h2>
    </header>
    <main class="page-content">
      <p>
        UOOC联盟作为首个全国地方高校优质MOOC课程资源共享平台，在国家相关政策引导下，本着共创、共建、共享的原则，旨在整合全国地方高校优质教学资源，建设大规模开放在线课程，形成优质课程共建共享机制，为联盟高校学生及社会学员提供课程学习的选择和服务，力争提升地方高校人才培养水平和社会服务能力，促进我国高等教育均衡化发展。
        联盟成立以来，稳步推进，快速发展。加盟高校规模不断扩大，成员高校达134所，遍布全国29个省市，69座城市，覆盖师生人数360万；MOOC课程数量不断增多，上线MOOC课程数量达555门；学校参与度广泛，供课学校已有68所；学生选课人数不断增加，累计选课人次近141万；运行机制不断完善，制定了《地方高校UOOC（优课）联盟章程》、《地方高校UOOC（优课）联盟建设与运行管理办法》、《地方高校UOOC（优课）联盟在线课程质量与学分互认管理办法》等规章制度，不断规范和完善联盟的各项管理；培训体系日趋完善，共举办了十一期UOOC联盟教师培训和两期课程平台管理员培训，受益教师近千人。实现了地方高校间优质MOOC课程资源的共建共享以及学分互认，开展了在线教育的理论研究，并形成了较大的社会影响。
      </p>
    </main>
    <footer class="page-footer">
      <ul class="stats">
        <li><strong>134</strong> 所高校</li>
        <li><strong>29</strong> 个省市自治区</li>
        <li><strong>360万</strong> 万师生人数</li>
        <li><strong>555</strong> 门MOOC课程</li>
        <li><strong>141万</strong> 万选课人次</li>
      </ul>
    </footer>
  </div>
</template>

<script>
export default {
  name: 'AboutPage',
};
</script>

<style scoped>
.about-page {
  font-family: Arial, sans-serif;
  margin: 0 auto;
  min-height: 100vh;
  max-width: 1200px;
  padding: 20px;
}

.page-header {
  text-align: center;
  color: #007BFF;
}

.page-content {
  margin-top: 20px;
  line-height: 1.6;
  color: #424242;
}

.stats {
  display: flex;
  justify-content: space-around;
  list-style-type: none;
  padding: 0;
  margin-top: 20px;
  background-color: #f5f5f5; /* 浅灰色背景 */
  padding: 20px; /* 内边距以确保内容不紧贴边缘 */
  border-radius: 8px; /* 圆角 */
  color: #424242;
}

.stats li {
  margin-bottom: 10px;
  flex-grow: 1;
  text-align: center;
}

.stats li strong {
  color: #007BFF; /* 数字颜色 */
  font-size: 1.5em; /* 增大字体大小 */
}
</style>