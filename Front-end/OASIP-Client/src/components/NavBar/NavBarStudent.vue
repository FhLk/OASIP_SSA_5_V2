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
        <div class="justify-self-end">
            <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                <router-link to="/event">Show Schedule </router-link>
            </button>
            <button @click="checkTimeOut" class="hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf mx-1">
                <router-link to="/event/create">Add New Schedule</router-link>
            </button>
            <!-- <button @click="signOut" class="btLogIn hover:bg-[#fda4af] rounded-md px-1 mt-4 h-8 cf mx-2">
                <router-link to="/login">Sign Out</router-link>
            </button> -->
        </div>
    </div>

</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
.router-link-active{
    color:#0547A5;
}
.font {
    font-family: 'Mitr', sans-serif;
}
.cf {
    color: rgb(0, 0, 0);
}
.btLogIn {
    background-color: rgb(251 113 133);
}
</style>