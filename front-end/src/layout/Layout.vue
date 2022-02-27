<template>
  <div>
    <Header :currentUser="JSON.parse(JSON.stringify($store.getters.userInfo))"></Header>
    <router-view style="max-width: 1000px; margin: 10px auto;"/>
  </div>
</template>

<script>
import Header from '@/components/Header.vue'
export default {
  name: "Layout",
  components: {
    Header
  }
}
</script>

<style scoped>

</style>