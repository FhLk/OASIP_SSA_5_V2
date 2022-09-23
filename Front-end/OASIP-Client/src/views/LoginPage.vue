<script setup>
import { onBeforeMount, onUpdated, ref } from 'vue';
import Login from '../components/Login.vue';
import NavBar from '../components/NavBar.vue';
const emits=defineEmits(['login'])
const token=ref("")
const sentToken=(t)=>{
   emits('login',t)
}

</script>
 
<template>
<div class="font">
    <div class="header mt-32 ">
      Welcome to OASIP
    </div>
    <Login @login="sentToken"/>
</div>
</template>
 
<style scoped>
.font {
  font-family: 'Mitr', sans-serif;
}
.header {
  display: flex;
  justify-content: center;
  font-size: 50px;
}
</style>