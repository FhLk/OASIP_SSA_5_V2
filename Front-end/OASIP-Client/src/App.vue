<script setup>
import { computed } from "@vue/reactivity";
import { onBeforeMount, onBeforeUpdate, onUpdated, ref } from "vue";
import NavBar from './components/NavBar.vue';
const token=ref("")
const sentToken=(t)=>{
    token.value=t
}
</script>
 
<template>
    <NavBar :token="token"/>
    <router-view @login="sentToken" ></router-view> 
</template>


 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
</style>