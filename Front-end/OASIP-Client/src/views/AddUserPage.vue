<script setup>
import { computed, onBeforeMount, ref } from 'vue';
const fetchUrl = import.meta.env.VITE_BASE_URL
import CreateUser from '../components/CreateUser.vue';
const getAllUser=ref([])
const getUsers = async () => {
    const res = await fetch(`${fetchUrl}/users`, {
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
<div>
    <CreateUser :getUsers="getAllUser"/>
</div>
</template>
 
<style>

</style>