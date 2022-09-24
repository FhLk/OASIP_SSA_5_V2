<script setup>
import { onBeforeMount, onUpdated, ref } from 'vue';
import Login from '../components/Login.vue';
import { useRoute, useRouter } from 'vue-router';
import { checkToken } from '../Store/local';
const emits=defineEmits(['login'])
const isTimeOut=ref(false)
const sentToken=(t)=>{
   emits('login',t)
}

const myRouter = useRouter()
const GoIndex = () => {
  myRouter.push({ name: 'indexPage' })
}

onBeforeMount(()=>{
  isTimeOut.value=checkToken()
  if(isTimeOut.value){
    GoIndex()
  }
})

</script>
 
<template>
<div class="font">
    <div class="header mt-32 ">
      Welcome to OASIP
    </div>
    <Login @login="sentToken"/>
</div>
</template>
 
<style scoped>
.font {
  font-family: 'Mitr', sans-serif;
}
.header {
  display: flex;
  justify-content: center;
  font-size: 50px;
}
</style>