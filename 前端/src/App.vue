<template>
  <div class="common-layout">
    <Header/>
    <div>
      <router-view/>
    </div>
    <Footer />
  </div>
</template>
<script setup>
import Header from "@/components/Header.vue"
import Footer from "@/components/Footer.vue"
</script>

<style scoped>
.common-layout {
  display: flex;
  width: 100%;
  height: 100vh;
  flex-direction: column;
  background-color: #f1f5fa;
  overflow-y: auto;
}
</style>
