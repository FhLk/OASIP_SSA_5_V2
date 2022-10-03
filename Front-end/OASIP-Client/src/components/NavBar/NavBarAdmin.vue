<script setup>
import { onBeforeMount, onBeforeUpdate, onMounted, onUpdated, ref } from "vue";
import { useRoute, useRouter } from 'vue-router';
import { checkToken, expiresToken, expiresAccess } from '../../Store/local';
import { reAuthen } from "../../fetch/fetchUserAPI";
import UserStatus from "../UserStatus.vue";
const emits = defineEmits(['signOut', 'timeOut'])
const props = defineProps({
    token: String,
    role: Number
})

const isToken = ref(false)

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
    if (isToken.value === false) {
        isToken.value = false
        emits('timeOut', "")
    }
    if (expiresToken() && (props.token !== '' || props.token === null)) {
        localStorage.clear()
        isToken.value = false
        emits('timeOut', "")
        GoSignIn()
    }
    else if (expiresAccess()) {
        reAuthen()
    }
}

const Test = () => {
    Toggle(false)
    console.log("Wow")
}

const Toggle = (condition) => {
    if (condition) {
        return ""
    }
    return "hidden"
}

</script>
 
<template>
    <div>
        <div class="flex justify-between">
            <div class="flex">
                <router-link to="/">
                    <h1 class="text-6xl cf px-2 ml-8 rounded-md hover:ring ring-[#000000] hover:shadow-lg">OASIP</h1>
                </router-link>
                <img src="../../assets/schedule.png" class="schedule flex justify-self-start ml-2">
            </div>
            <div class="flex">
                <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                    <router-link to="/EventPage">Show Schedule </router-link>
                </button>
                <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                    <router-link to="/AddEventPage">Add New Schedule</router-link>
                </button>
                <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                    <router-link to="/UserPage">User</router-link>
                </button>
                <div>
                    <UserStatus/>
                </div>
                <div>
                    <button @click="signOut" class="btLogIn hover:bg-[#fda4af] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/LoginPage">Sign Out</router-link>
                    </button>
                    <button @click="checkTimeOut" class="btLogIn hover:bg-[#fda4af] rounded-md px-1 mt-4 h-8 cf mx-2">
                        <router-link to="/AddUserPage">Create New Account</router-link>
                    </button>
                </div>
            </div>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.imgid {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}

.font {
    font-family: 'Mitr', sans-serif;
}

.scd {
    background-color: rgb(86, 165, 236);
}

.cf {
    color: rgb(0,0,0);
}

.btAddNew {
    background-color: rgb(125, 211, 252);
}

.btLogIn {
    background-color: rgb(251 113 133);
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
    width: 3.5rem;
    height: 3.5rem;
    margin-top: 0.35rem;
}
</style>