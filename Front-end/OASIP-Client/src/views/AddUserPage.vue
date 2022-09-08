<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import CreateUser from '../components/CreateUser.vue';
const fetchUrl = import.meta.env.VITE_BASE_URL
const getAllUser=ref([])
const getUsers = async () => {
    const res = await fetch(`${fetchUrl}/users/check`, {
        method: 'GET',
        headers: {
        "Authorization": `Bearer ${localStorage.getItem('token')}`
    }
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
<div class="bg">
    <h1 class="font text-5xl flex justify-center pt-36">Add new user</h1>
    <CreateUser :getUsers="getAllUser"/>
</div>
</template>
 
<style>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
    font-family: 'Mitr', sans-serif;
}
body {
    background-color: (255, 255, 247);
}
</style>