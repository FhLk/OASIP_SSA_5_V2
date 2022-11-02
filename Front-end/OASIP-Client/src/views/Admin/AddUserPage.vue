<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import CreateUser from '../../components/CreateUser.vue';
import { AllUser, reAuthen } from '../../fetch/fetchUserAPI.js'
import { checkRole, checkToken, expiresAccess, expiresToken } from '../../Store/local.js';
const getAllUser = ref([])

const isToken = ref(false)
const isTimeOut = ref(false)
const role = ref(-1)

onBeforeMount(async () => {
  isToken.value = checkToken()
  isTimeOut.value = expiresToken()
  if (isTimeOut.value) {
    isToken.value = false
  }
  else {
    if (expiresAccess()) {
      await reAuthen()
    }
  }
  role.value = checkRole(localStorage.getItem("role"))
  if (role.value === 0) {
    getAllUser.value = await AllUser()
  }
})
</script>
 
<template>
  <div class="bg pt-28">
    <h1 class="font text-5xl flex justify-center">Add New Account</h1>
    <CreateUser v-if="isToken && role === 0" :getUsers="getAllUser" />
    <div v-else-if="role !== 0 && isToken">
      <div class="font flex justify-center ">
        <h1 class="font text-4xl flex justify-center mt-10 text-red-700">Only "ADMIN" Role.</h1>
      </div>
      <div class="font flex justify-center mt-2">
        <button class="mx-10 px-4 py-2 btt cf hover:bg-[#A53D59] rounded-md">
          <router-link to="/">Back</router-link>
        </button>
      </div>
    </div>
    <div v-else>
      <div class="font flex justify-center ">
        <h1 class="font text-4xl flex justify-center mt-10 text-red-700">Can not Add New Account. Plase Sign-in as "ADMIN".</h1>
      </div>
      <div class="font flex justify-center mt-2">
        <button class="mx-10 px-4 py-2 btt cf hover:bg-[#A53D59] rounded-md">
          <router-link to="/LoginPage">Sign In</router-link>
        </button>
      </div>
    </div>
  </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.btt {
  background-color: rgb(105, 1, 29);
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