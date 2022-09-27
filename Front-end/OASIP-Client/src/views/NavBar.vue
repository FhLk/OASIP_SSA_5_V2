<script setup>
import { onBeforeMount, onBeforeUpdate, onMounted, onUpdated, ref } from "vue";
// import { useRoute, useRouter } from 'vue-router';
import { checkToken, expiresToken, expiresAccess } from '../Store/local';
// import { reAuthen } from "../fetch/fetchUserAPI";
import NavBarAdmin from "../components/NavBar/NavBarAdmin.vue";
import NavBarSignin from '../components/NavBar/NavBarSignin.vue';
import NavBarStudent from "../components/NavBar/NavBarStudent.vue";
import NavBarLecturer from "../components/NavBar/NavBarLecturer.vue";
const emits = defineEmits(['signOut', 'timeOut'])
const props = defineProps({
    token: String,
    role: Number
})

const isToken = ref(false)

onBeforeMount(() => {
    isToken.value = checkToken()
})

onUpdated(() => {
    isToken.value = checkToken()
})
</script>
 
<template>
    <div>
        <NavBarAdmin v-if="role===0 && isToken" :token="token" @sign-out="role=-1,token=''" />
        <NavBarLecturer v-else-if="role===1 && isToken" :token="token" @sign-out="role=-1,token=''" />
        <NavBarStudent v-else-if="role===2 && isToken" :token="token" @sign-out="role=-1,token=''" />
        <NavBarSignin v-else />
    </div>
    <!-- <div>
        <nav class="scd border-gray-200 px-2 sm:px-4 py-1.5 font fixed top-0 left-0 right-0">
            <div class="flex justify-between">
                <div class="flex">
                    <router-link to="/">
                        <h1 class="text-7xl cf px-2 ml-8 rounded-md hover:ring ring-[#FBFBF9] font-bold ">OASIP</h1>
                    </router-link>
                    <img src="../assets/schedule.png" class="schedule flex justify-self-start ml-2">
                </div>
                <div class="justify-self-end">
                    <button @click="checkTimeOut" class="btAddNew hover:bg-[#294592] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/EventPage">Show Schedule </router-link>
                    </button>
                    <button @click="checkTimeOut" class="btAddNew hover:bg-[#294592] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/AddEventPage">Add New Schedule</router-link>
                    </button>
                    <button @click="checkTimeOut" class="btAddNew hover:bg-[#294592] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/UserPage">User</router-link>
                    </button>
                    <button v-if="isToken===false && token ===''" @click="signIn"
                        class="btLogIn hover:bg-[#A53D59] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/LoginPage">Sign In</router-link>
                    </button>
                    <button v-else @click="signOut"
                        class="btLogIn hover:bg-[#A53D59] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/LoginPage">Sign Out</router-link>
                    </button>
                    <button @click="checkTimeOut" class="btLogIn hover:bg-[#A53D59] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/AddUserPage">Create New Account</router-link>
                    </button>
                </div>
            </div>
        </nav>

        <div class="navbar pt-0.5 pb-0.5 flex justify-end">
            <button class="text-black hover:bg-[#294592] rounded-md px-1 h-5 hover:text-white font">
                <router-link to="/AboutPage">About Us</router-link>
            </button>
        </div>
    </div> -->

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