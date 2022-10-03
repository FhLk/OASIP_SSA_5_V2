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
    <div class="bgUser hover:bg-[#14D3FF] flex p-1 rounded-lg">
        <img :src="pic" class="imgid shadow-gray-600 shadow-md">
        <div class="ml-1">
            <p>{{name}}</p>
            <p>{{role}}</p>
        </div>
    </div>
</template>
 
<style scoped>
.imgid {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}
.bgUser{
    background-color: rgb(0, 191, 255);
}
</style>