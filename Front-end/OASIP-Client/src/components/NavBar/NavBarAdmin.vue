<script setup>
import { onBeforeMount, onBeforeUpdate, onMounted, onUpdated, ref } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { checkToken, expiresToken, expiresAccess } from '../../Store/local';
import { reAuthen } from "../../fetch/fetchUserAPI";
const emits = defineEmits(['signOut', 'timeOut'])
const props = defineProps({
    token: String,
    role: Number
})

const isToken = ref(false)
const time=ref(new Date())
setInterval(()=>{
    time.value= new Date()
},1000)

onBeforeMount(() => {
    isToken.value = checkToken()
    // isRole.value= checkRole()
})

onUpdated(() => {
    isToken.value = checkToken()
})

const myRouter = useRouter()
const GoSignIn = () => {
    myRouter.push({ name: 'LoginPage' })
}

const signOut = () => {
    localStorage.clear()
    isToken.value = false
    emits('signOut')
    alert("Sign Out Succes")
    GoSignIn()
}

const checkTimeOut = () => {
    isToken.value = checkToken()
    if(isToken.value===false){
        isToken.value=false
        emits('timeOut', "")
    }
    if (expiresToken() && (props.token !== '' || props.token === null)) {
        localStorage.clear()
        isToken.value=false
        emits('timeOut', "")
        GoSignIn()
    }
    else if (expiresAccess()) {
        reAuthen()
    }
}

</script>
 
<template>
    <div>
        <nav class="scd border-gray-200 px-2 sm:px-4 py-1.5 font fixed top-0 left-0 right-0">
            <div class="flex justify-between">
                <div class="flex">
                    <router-link to="/">
                        <h1 class="text-7xl cf px-2 ml-8 rounded-md hover:ring ring-[#FBFBF9] font-bold ">OASIP</h1>
                    </router-link>
                    <img src="../../assets/schedule.png" class="schedule flex justify-self-start ml-2">
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
                    <button @click="signOut"
                        class="btLogIn hover:bg-[#A53D59] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/LoginPage">Sign Out</router-link>
                    </button>
                    <button @click="checkTimeOut" class="btLogIn hover:bg-[#A53D59] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/AddUserPage">Create New Account</router-link>
                    </button>
                </div>
            </div>
            <h1>{{time}}</h1>
        </nav>

        <div class="navbar pt-0.5 pb-0.5 flex justify-end">
            <button class="text-black hover:bg-[#294592] rounded-md px-1 h-5 hover:text-white font">
                <router-link to="/AboutPage">About Us</router-link>
            </button>
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