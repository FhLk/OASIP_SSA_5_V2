<script setup>
import { onMounted, onBeforeMount, ref, onBeforeUpdate, onUpdated, computed } from 'vue';
import List from '../components/List.vue'
import { checkToken, expiresToken } from '../Store/local.js';
let DateFormat = "YYYY-MM-DD HH:mm"
const isToken = ref(false)


onBeforeMount(() => {
    isToken.value = checkToken()
})
</script>
 
<template>
    <div class="bg1 font h-full ">
        <h1 class=" ccf cr text-5xl pt-28 mb-4 ml-5 flex justify-start rounded-md p-2">Schedule
            <img src="../assets/timetable.png" class="calendar ml-5 ">
        </h1>
        <List v-if="isToken" />
        <div v-else class="font bgl2 rounded-xl px-10 mx-10 pt-5 pb-11">
            <div class="flex justify-center text-2xl">
                <p>No Events.</p>
            </div>
            <div class="flex justify-center text-2xl pt-4">
                <p>Plase Sign-in for use OASIP.</p>
            </div>
            <div class="flex justify-center pt-2">
                <button class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">
                    <router-link to="/login">Sign In</router-link>
                </button>
            </div>
        </div>
    </div>
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
