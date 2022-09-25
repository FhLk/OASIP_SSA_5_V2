<script setup>
import { computed } from "@vue/reactivity";
import { onBeforeMount, onBeforeUnmount, onBeforeUpdate, onMounted, onUnmounted, onUpdated, ref } from "vue";
import NavBar from './components/NavBar.vue';
import moment from "moment";
import { expiresToken } from "./Store/local";
const token = ref("")
const isTimeOut=ref(false)
const sentToken = (t) => {
    token.value = t
}

onBeforeMount(()=>{
    isTimeOut.value=expiresToken()
    if(isTimeOut.value){
        token.value=''
    }
})

const timeOut = (t) => {
    token.value = t
}

</script>
 
<template>
    <NavBar :token="token" @signOut="token=''" @timeOut="timeOut" />
    <router-view @login="sentToken" @click="token=''"></router-view>
</template>


 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
</style>