<script setup>
import { computed } from "@vue/reactivity";
import { onBeforeMount, ref } from "vue";
import NavBar from './views/NavBar.vue';
import { checkRole, expiresAccess, expiresToken } from "./Store/local";
import { reAuthen } from "./fetch/fetchUserAPI";
const token = ref("")
const isTimeOut = ref(false)
const role = ref(-1)
const user = ref({})
const sentToken = (data) => {
    token.value = data.token
    role.value = data.role
}

onBeforeMount(() => {
    isTimeOut.value = expiresToken()
    if (isTimeOut.value) {
        token.value = ''
    }
    else {
        if (expiresAccess()) {
            reAuthen()
        }
    }
    role.value = checkRole(localStorage.getItem("role"))
})

const timeOut = (t) => {
    token.value = t
}

const ReAuthen = async () => {
    if (expiresAccess()) {
        await reAuthen()
    }
}

</script>
 
<template>
    <NavBar :token="token" :role="role" :user="user" @signOut="token=''" @timeOut="timeOut" />
    <router-view @login="sentToken" @click="ReAuthen,token=''"></router-view>
</template>


 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
</style>