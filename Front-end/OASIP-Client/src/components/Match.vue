<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { accessAlert, deniedAlert } from '../Alert/alert';
import { Match, Authen } from '../fetch/fetchUserAPI.js'
import { checkRole } from '../Store/local';
const props=defineProps({
  user: Object
})
const isPass=ref(true)
const isPassMatch=ref(true)
const errorMessage=ref("")
const userInfor=props.user
const password=ref("")

const checkMatch = async (user,pass) => {
  let isCheck = true
  if (password.value === "") {
    isCheck = false
    isPass.value=false
    isPassMatch.value=false
    errorMessage.value = "mb-2 text-[#FF0000] text-sm"
  }
  if (isCheck) {
    const resLogin = await Match({email:user.email,password:pass})
    if (resLogin === 200) {
        accessAlert("Matched")
        reset()
    }
    else if (resLogin === 401) {
      deniedAlert("match","password")
      errorMessage.value = "mb-2 text-[#FF0000] text-sm"
      isPass.value=true
      password.value = ""
      reset()
    }
  }
}

const reset = () => {
  password.value=""
  isPass.value = true
  errorMessage.value = ""
}

const cdet = " bg-green-600 rounded-full px-2 text-white hover:bg-[#4ADE80]";
</script>
 
<template>
  <div>
    <input type="password" placeholder="password" v-model="password" 
    @click="isPass=true,isPassMatch=true" @keydown="isPass=true,isPassMatch=true"/>
    <button @click="checkMatch(user,password)" :class="cdet">Check</button>
    <p :class="isPass===false ? errorMessage : ''" v-if="isPass===false">*Plase Input password.*</p>
  </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
  font-family: 'Mitr', sans-serif;
}

.user {
  width: 2.5rem;
  height: 2.5rem;
  margin-top: 0.25rem;
}

.password {
  width: 2.5rem;
  height: 2.5rem;
  margin-top: 0.25rem;
}

.header {
  display: flex;
  justify-content: center;
  font-size: 50px;
}

.login-button {
  /* display: inline-block; */
  background-color: rgba(93, 143, 164);
  height: 50%;
  margin-top: 2%;
  border-radius: 10px;
  font-size: 30px;
  color: antiquewhite;
  margin-top: 5%;
}

.login-input {
  text-align: center;
}

.info-input {
  border-style: solid;
  border-width: 1px;
  border-radius: 1px;
  border-color: black;
  width: 50%;
}

.login-header {
  font-size: 30px;
  text-align: center;
}

.login {
  position: fixed;
  align-items: center;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  z-index: 10;
  background-color: white;
  width: 40%;
  height: 50%;
  max-height: 50%;
  color: black;
  border: black 2px solid;
  box-shadow: 5px 5px 10px 2px rgba(36, 36, 36, 0.507);
}

/* 
.login-input{
  text-align: center;
}
.login-button {
  display: inline-block;
  background-color: lightblue;
  height: 50%;
  margin-top: 2%;
  margin-bottom: 2%;
  margin-left: 3.5%;
  border-radius: 10px;
  font-size: 30px;
  color: antiquewhite;
  width: 55%;
}
.info-input {
  border-style: solid;
  border-width: 5px;
  border-radius: 5px;
  border-color: black;
  width: 60%;
}
.login {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border-radius: 10px;
  z-index: 10;
  background-color: white;
  width: 500px;
  max-width: 80%;
  color: black;
  border: black 2px solid;
  box-shadow: 5px 5px 10px 2px rgba(36, 36, 36, 0.507);
}
.login-header {
  padding: 10px 15px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  border-bottom: 1px solid black;
}
.login-header .login-title {
  font-size: 35px;
  font-weight: bold;
  padding-left: 1%;
}
.login-header .close-login {
  cursor: pointer;
  border: none;
  outline: none;
  background: none;
  font-size: 35px;
  font-weight: bold;
}
.login-header .close-login:hover {
  color: red;
}
.login-body {
  padding: 10px 15px;
  font-size: 20px;
  font-weight: bold;
  padding-left: 6%;
} */
</style>