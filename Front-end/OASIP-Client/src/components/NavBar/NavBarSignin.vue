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

.cf {
    color: rgb(251, 251, 249);
}


.btLogIn {
    background-color: rgb(105, 1, 29);
}
</style>