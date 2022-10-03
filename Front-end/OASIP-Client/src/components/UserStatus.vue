<script setup>
import { onBeforeMount, ref } from 'vue';
// const pic = ref()
const name = ref("")
const role = ref("")
onBeforeMount(() => {
    role.value = localStorage.getItem("role")
    name.value = localStorage.getItem("name")
})
</script>
 
<template>
    <div class="bgUser hover:bg-[#14D3FF] flex p-1.5 rounded-lg">
        <img src="../assets/user.png" class="imgid shadow-gray-600 shadow-md">
        <div class="ml-1 text-center">
            <p>{{name}}</p>
            <p class="">{{role}}</p>
        </div>
    </div>
</template>
 
<style scoped>
.imgid {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}

.bgUser {
    background-color: rgb(0, 191, 255);
    height: 85%;
}
</style>