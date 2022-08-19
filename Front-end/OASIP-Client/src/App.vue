<script setup>
import Login from './components/Login.vue';
import { ref } from "vue";
import NavBar from './components/NavBar.vue';
import LoginPage from './views/LoginPage.vue';
const isLoginPage=ref(true)
</script>
 
<template>

<NavBar v-if="!isLoginPage"/>
<router-view></router-view> 
</template>


 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
</style>