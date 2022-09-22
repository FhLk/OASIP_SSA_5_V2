<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import CreateUser from '../components/CreateUser.vue';
import { AllUser } from '../fetch/fetchUserAPI.js'
import {checkToken} from '../Store/local.js';
const getAllUser = ref([])

const isToken = ref(false)


onBeforeMount(async () => {
  getAllUser.value = await AllUser()
  isToken.value= checkToken()
})
</script>
 
<template>
  <div class="bg">
    <h1 class="font text-5xl flex justify-center pt-36">Add new user</h1>
    <CreateUser v-if="isToken" :getUsers="getAllUser" />
    <div v-else>
      <h1 class="font text-5xl flex justify-center mt-10">Plase Sign-in</h1>
      <div class="font flex justify-center ">
        <button class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">
          <router-link to="/LoginPage">Sign In</router-link>
        </button>
      </div>
    </div>
  </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.btt {
  background-color: rgb(25, 25, 112);
}

.cf {
  color: rgb(251, 251, 249);
}

.font {
  font-family: 'Mitr', sans-serif;
}

body {
  background-color: (255, 255, 247);
}
</style>