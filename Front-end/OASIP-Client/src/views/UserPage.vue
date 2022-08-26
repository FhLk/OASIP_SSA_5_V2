<script setup>
import ListUser from '../components/ListUser.vue';
import { computed, onBeforeMount, ref } from 'vue';
const fetchUrl = import.meta.env.VITE_BASE_URL
const getAllUser=ref([])
const getUsers = async () => {
    const res = await fetch(`${fetchUrl}/users/check`, {
        method: 'GET'
    })
    if (res.status === 200) {
        getAllUser.value = await res.json()
    }
}

onBeforeMount(async () => {
    await getUsers()
})
</script>

<template>
<div class="pt-36 font bg pb-56">
    <h1 class="text-5xl mb-4 ml-5 flex justify-between rounded-md p-2">List ALL User</h1>
    <ListUser :getUsers="getAllUser"/>
</div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
.font{
    font-family: 'Mitr', sans-serif;
}

.bg{
    background-color: rgb(255, 255, 247);
}
</style>