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

</script>
 
<template>
    <div>
        <div class="flex">
            <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                <router-link to="/EventPage">Show Schedule </router-link>
            </button>
            <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                <router-link to="/CategoryPage">Category </router-link>
            </button>
            <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                <router-link to="/AddEventPage">Add New Schedule</router-link>
            </button>
            <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                <router-link to="/UserPage">User</router-link>
            </button>
            <div>
                <button @click="checkTimeOut" class="btLogIn hover:bg-[#fda4af] rounded-md px-1 mt-4 h-8 cf mx-2">
                    <router-link to="/AddUserPage">Create New Account</router-link>
                </button>
                <button @click="signOut" class="btLogIn hover:bg-[#fda4af] rounded-md px-1 mt-4 h-8 cf mx-2">
                    <router-link to="/LoginPage">Sign Out</router-link>
                </button>
            </div>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.router-link-active{
    color:#0547A5;
}
.cf {
    color: rgb(0, 0, 0);
}


.btLogIn {
    background-color: rgb(251 113 133);
}
</style>