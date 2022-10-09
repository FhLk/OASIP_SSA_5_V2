<script setup>
import { onBeforeMount, ref } from 'vue';
import Category from '../components/Category.vue';
import { getCategories } from '../fetch/fetchCategory.js'
import { checkRole, checkToken } from '../Store/local.js';
const listCategories = ref([])
const isToken = ref(false)
const isRole=ref(-1)
onBeforeMount(async () => {
    listCategories.value = await getCategories()
    isToken.value = checkToken()
    isRole.value=checkRole(localStorage.getItem("role"))
})

const getList = async () => {
    listCategories.value = await getCategories()
}
</script>
 
<template>
    <Category :role="isRole" :categories="listCategories" @save="getList" />
    <!-- <div v-else class="font bgl2 rounded-xl px-10 mx-10 pt-5 pb-11 mt-32">
        <div class="flex justify-center text-2xl">
            <p>No Category.</p>
        </div>
        <div class="flex justify-center text-2xl pt-4">
            <p>Plase Sign-in for use OASIP.</p>
        </div>
        <div class="flex justify-center pt-2">
            <button class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">
                <router-link to="/LoginPage">Sign In</router-link>
            </button>
        </div>
    </div> -->
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');



.bgl2 {
    background-color: rgb(164, 220, 255);
}

.btt {
    background-color: rgb(25, 25, 112);
}

.cf {
    color: rgb(251, 251, 249);
}

.font {
    font-family: 'Mitr', sans-serif;
}

.ccf {
    color: rgb(0, 0, 0);
}

.cr {
    --tw-ring-color: rgb(42, 39, 40);
}

.bg1 {
    background-color: rgb(255, 255, 247);
}

.calendar {
    width: 3rem;
    height: 3rem;
    margin-top: 0.3rem;
}
</style>