<script setup>
import { useRoute, useRouter } from 'vue-router';
const myRouter = useRouter()
const GoSignIn = () => {
    myRouter.push({ name: 'LoginPage' })
}

const signIn = () => {
    localStorage.clear()
    GoSignIn()
}

</script>
 
<template>
    <div>
        <div class="flex justify-between">
            <div class="flex">
                <router-link to="/">
                    <h1 class="text-7xl cf px-2 ml-8 rounded-md hover:ring ring-[#FBFBF9] font-bold ">OASIP</h1>
                </router-link>
                <img src="../../assets/schedule.png" class="schedule flex justify-self-start ml-2">
            </div>
            <div class="justify-self-end">
                <button @click="signIn" class="btLogIn hover:bg-[#A53D59] rounded-md px-1 mt-4 h-8 cf mx-2">
                    <router-link to="/LoginPage">Sign In</router-link>
                </button>
            </div>
        </div>
    </div>

</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
    font-family: 'Mitr', sans-serif;
}

.scd {
    background-color: rgb(86, 165, 236);
}

.cf {
    color: rgb(251, 251, 249);
}

.btAddNew {
    background-color: rgb(21, 49, 126);
}

.btLogIn {
    background-color: rgb(105, 1, 29);
}

.hoa {
    background-color: rgb(101, 129, 206);
}

.navbar {
    background-color: rgb(189, 237, 255);
    overflow: hidden;
    position: fixed;
    bottom: 0;
    width: 100%;
}

.schedule {
    width: 4rem;
    height: 4rem;
    margin-top: 0.4rem;
}
</style>