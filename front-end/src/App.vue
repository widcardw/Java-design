<script setup>
// This starter template is using Vue 3 <script setup> SFCs
// Check out https://v3.vuejs.org/api/sfc-script-setup.html#sfc-script-setup
import Header from '@/components/Header.vue'

</script>

<template>
  <div>
    <Header :currentUser="JSON.parse(JSON.stringify($store.getters.userInfo))"></Header>
    <router-view style="max-width: 1000px; margin: 10px auto;"/>
  </div>
</template>

