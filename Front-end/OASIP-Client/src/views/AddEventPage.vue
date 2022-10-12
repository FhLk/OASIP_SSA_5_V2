<script setup>
import Create from '../components/Create.vue';
import { computed, onBeforeMount, ref } from 'vue';
import { checkRole, checkToken, expiresToken } from '../Store/local.js';
const isToken = ref(false)
const getListCategories = ref([]);
const role = ref(-1)

const getCategories = async () => {
    const res = await fetch(`${import.meta.env.VITE_BASE_URL}/categories`, {
        method: 'GET',
    })
    getListCategories.value = await res.json()
}

onBeforeMount(async () => {
    isToken.value = checkToken()
    role.value=checkRole(localStorage.getItem("role"))
    await getCategories()
})
</script>
 
<template>
    <div class="bg h-screen h-full">
        <h1 class="font text-5xl flex justify-center pt-28">Add New Schedule</h1>
        <div>
            <Create v-if="role!==1" :role="role" :getCategories="getListCategories" />
            <div v-else-if="role===1">
                <div class="font flex justify-center "> 
                    <h1 class="font text-4xl flex justify-center mt-10 text-red-700">Can not Add New Schedule.</h1>
                </div>
                <div class="font flex justify-center mt-2">
                    <button class="mx-10 px-4 py-2 btt cf hover:bg-[#A53D59] rounded-md">
                        <router-link to="/">Back</router-link>
                    </button>
                </div>
            </div>  
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.description {
    max-width: 35%;
    margin-left: 2%;
}

.btt {
    background-color: rgb(105, 1, 29);
}

.cf {
    color: rgb(251, 251, 249);
}

.font {
    font-family: 'Mitr', sans-serif;
}

.bg {
    background-color: rgb(255, 255, 247);
}

.ccf {
    color: rgb(42, 39, 40);
}

.bgcat {
    background-color: rgb(167, 214, 255);
}

.edit {
    background-color: rgb(21, 49, 126);
}
</style>