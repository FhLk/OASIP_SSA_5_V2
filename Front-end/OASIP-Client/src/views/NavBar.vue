<script setup>
import { onBeforeMount, onBeforeUpdate, onMounted, onUpdated, ref } from "vue";
import Swal from 'sweetalert2'
import { checkToken, expiresToken, expiresAccess } from '../Store/local';
import NavBarAdmin from "../components/NavBar/NavBarAdmin.vue";
import NavBarSignin from '../components/NavBar/NavBarSignin.vue';
import NavBarStudent from "../components/NavBar/NavBarStudent.vue";
import NavBarLecturer from "../components/NavBar/NavBarLecturer.vue";
import UserStatus from "../components/UserStatus.vue";
const emits = defineEmits(['signOut', 'timeOut'])
const props = defineProps({
    token: String,
    role: Number,
    user: Object
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
        <nav class="bgNav border-b-4 borderColor px-2 sm:px-4 py-1.5 font fixed top-0 left-0 right-0">
            <div class="flex justify-between">
                <div class="flex">
                    <router-link to="/">
                        <h1 class="cf text-7xl px-1 ml-8 rounded-md hover:ring-4 ring-[#000000]">OASIP</h1>
                    </router-link>
                    <img src="../assets/schedule.png" class="schedule flex justify-self-start ml-2">
                </div>
                <div class="flex">
                    <NavBarAdmin v-if="role === 0 && isToken" :token="token" />
                    <NavBarLecturer v-else-if="role === 1 && isToken" :token="token" />
                    <NavBarStudent v-else-if="role === 2 && isToken" :token="token" />
                    <NavBarSignin v-else />
                    <UserStatus v-if="role !== -1 && isToken" @sign-out="role = -1, token = ''" />
                </div>
            </div>
        </nav>
        <div class="navbar pt-0.5 pb-0.5 flex justify-end">
            <button class="text-black hover:bg-[#294592] rounded-md px-1 h-5 hover:text-white font">
                <router-link to="/about">About Us</router-link>
            </button>
        </div>
    </div>

</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
    font-family: 'Mitr', sans-serif;
}

.bgNav {
    background-color: rgb(255, 255, 255);
    max-height: 13%;
}

.borderColor {
    border-color: rgb(86, 165, 236);
}

.cf {
    color: rgb(000);
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
    background-color: rgb(71, 188, 231);
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