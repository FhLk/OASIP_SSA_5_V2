<script setup>
import { onBeforeMount, onBeforeUpdate, onMounted, onUpdated, ref } from "vue";
// import { useRoute, useRouter } from 'vue-router';
import { checkToken, expiresToken, expiresAccess } from '../Store/local';
// import { reAuthen } from "../fetch/fetchUserAPI";
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

const Test=()=>{
    console.log(props.user)
}

const time = ref(new Date())
const timeFormat1=ref(`
        ${time.value.toLocaleString('default', {weekday: 'short'})}
        ${time.value.getDate()}
        ${time.value.toLocaleString('default', {month: 'short'})}
        ${time.value.getFullYear()}
        ${time.value.getHours()}:${time.value.getMinutes()}:${time.value.getSeconds()}
        `)
setInterval(() => {
    time.value = new Date()
    timeFormat1.value=`
        ${time.value.toLocaleString('default', {weekday: 'short'})}
        ${time.value.getDate()}
        ${time.value.toLocaleString('default', {month: 'short'})}
        ${time.value.getFullYear()}
        ${time.value.getHours()}:${time.value.getMinutes().toString().length!==2 ? 0:""}${time.value.getMinutes()}:${time.value.getSeconds().toString().length!==2 ? 0:""}${time.value.getSeconds()}
        `
}, 1000)

const isToken = ref(false)

onBeforeMount(() => {
    isToken.value = checkToken()
})

onUpdated(() => {
    isToken.value = checkToken()
    // console.log(props.user)
})

</script>
 
<template>
    <div>
        <nav class="scd border-gray-200 px-2 sm:px-4 py-1.5 font fixed top-0 left-0 right-0">
            <NavBarAdmin v-if="role===0 && isToken" :token="token" @sign-out="role=-1,token=''" />
            <NavBarLecturer v-else-if="role===1 && isToken" :token="token" @sign-out="role=-1,token=''" />
            <NavBarStudent v-else-if="role===2 && isToken" :token="token" @sign-out="role=-1,token=''" />
            <UserStatus v-if="role!== -1 && isToken"/>
            <NavBarSignin v-else/>
            <div>
                <h1>{{timeFormat1}}</h1>
            </div>
            <div class="navbar pt-0.5 pb-0.5 flex justify-end">
                <button class="text-black hover:bg-[#294592] rounded-md px-1 h-5 hover:text-white font">
                    <router-link to="/AboutPage">About Us</router-link>
                </button>
            </div>
        </nav>
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