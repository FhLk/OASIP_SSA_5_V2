<script setup>
import { computed } from "@vue/reactivity";
import { onBeforeMount, onBeforeUnmount, onBeforeUpdate, onMounted, onUnmounted, onUpdated, ref } from "vue";
import NavBar from './views/NavBar.vue';
import moment from "moment";
import { checkRole, expiresToken } from "./Store/local";
const token = ref("")
const isTimeOut = ref(false)
const role = ref(-1)
const sentToken = (data) => {
    token.value = data.token
    role.value = data.role
}

onBeforeMount(() => {
    isTimeOut.value = expiresToken()
    if (isTimeOut.value) {
        token.value = ''
    }
    role.value=checkRole(localStorage.getItem("role"))
})

const timeOut = (t) => {
    token.value = t
}

</script>
 
<template>
    <NavBar :token="token" :role="role" @signOut="token=''" @timeOut="timeOut" />
    <router-view @login="sentToken" @click="token=''"></router-view>
</template>


 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
</style>