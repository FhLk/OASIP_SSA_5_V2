<script setup>
import { onBeforeMount, ref } from 'vue';
const pic=ref("src/assets/user.png")
const name=ref("")
const role=ref("")
onBeforeMount(()=>{
    // role.value=props.user.role
    // name.value=props.user.name
    // if(role.value==="" || name.value===""){
        role.value=localStorage.getItem("role")
        name.value=localStorage.getItem("name")
    // }
})
</script>
 
<template>
    <div class="bg-teal-600 hover:bg-[#A53D59]">
        <img :src="pic" class="imgid shadow-gray-600 shadow-md">
        <p>{{name}}</p>
        <p>{{role}}</p>
    </div>
</template>
 
<style scoped>
.imgid {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}
</style>