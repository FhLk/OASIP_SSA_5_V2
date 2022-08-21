<script setup>
import { ref } from "vue";
import NavBar from './components/NavBar.vue';
const isLoginPage=ref(false)
const checkLoginPage=(login)=>{
    isLoginPage.value=login
}
</script>
 
<template>
    <NavBar :hidden="isLoginPage" @login="checkLoginPage"/>
    <router-view></router-view> 
</template>


 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
</style>