<script setup>
import { onBeforeMount, ref } from 'vue';
import { onBeforeRouteLeave, onBeforeRouteUpdate, useRoute, useRouter } from 'vue-router';
import Category from '../components/Category.vue';
import { getCategories } from '../fetch/fetchCategory.js'
import { checkRole, checkToken } from '../Store/local.js';
const myRouter = useRouter()
const myRoute = useRoute()
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
    <Category :role="isRole" :categories="listCategories" @save="getList"/>
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