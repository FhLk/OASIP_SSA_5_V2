<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { Match, Authen } from '../fetch/fetchUserAPI.js'
import jwt_decode from "jwt-decode";
import { checkRole } from '../Store/local';
const emits = defineEmits(['login'])
const errorMessage = ref("")
const isEmail = ref(false)
const isPass = ref(false)
const isLogin = ref(false)
const isEmailLogin = ref(false)
const token = ref("")
const role = ref(-1)
const isPassLogin = ref(false)
const login = ref({
  email: "",
  password: ""
})

const checkLogin = async (log) => {
  let isCheck = true
  if (log.email === "" && log.password === "") {
    isCheck = false
    isEmail.value = true
    isPass.value = true
    errorMessage.value = "mb-2 text-[#FF0000] text-sm"
  }
  else if (log.email !== "" && log.password === "") {
    isCheck = false
    isEmail.value = false
    isPass.value = true
    errorMessage.value = "mb-2 text-[#FF0000] text-sm"
  }
  else if (log.email === "" && log.password !== "") {
    isCheck = false
    isPass.value = false
    isEmail.value = true
    errorMessage.value = "mb-2 text-[#FF0000] text-sm"
  }
  if (isCheck) {
    isPass.value = false
    isEmail.value = false
    const resLogin = await Match(log)
    if (resLogin === 200) {
      token.value = await Authen(log)
      if (token.value.access_token === "") {
        alert("Login unsuccss")
        login.value.password = ""
      }
      else {
        role.value=checkRole(localStorage.getItem("role"))
        alert("Login success")
        emits('login', {token:token.value,role:role.value})
        GoIndex()
        reset()
      }
    }
    else if (resLogin === 404) {
      isLogin.value = true
      isEmailLogin.value = true
      login.value.password = ""
    }
    else if (resLogin === 401) {
      isLogin.value = true
      isPassLogin.value = true
      login.value.password = ""
    }
  }
}

const reset = () => {
  login.value.email = ""
  login.value.password = ""
  isPass.value = false
  isEmail.value = false
  isLogin.value = false
  isEmailLogin.value = false
  isPassLogin.value = false
  errorMessage.value = ""
}

const myRouter = useRouter()
const GoIndex = () => {
  myRouter.push({ name: 'indexPage' })
}
</script>
 
<template>
  <div class="font">
    <div class="login mt-1">
      <h1 class="login-header bg-red-500">
        LOGIN
      </h1>
      <div class="login-body">
        <div class="login-input mt-14">
          <div class="flex justify-center mt-5 py-1">
            <img src="../assets/mail_user.png" class="user mx-2" />
            <input class="info-input mt-2 px-1" type="text" placeholder="Username" v-model="login.email"
              @click="isEmail = false,isLogin=false,isEmailLogin=false,isPassLogin=false"
              @keydown="isLogin=false,isEmailLogin=false,isPassLogin=false" />
          </div>
          <p :class="isEmail ? errorMessage : ''" v-if="isEmail">*Plase Input your username*</p>
          <div class="flex justify-center py-1">
            <img src="../assets/padlock.png" class="password mx-2" />
            <input class="info-input mt-2 px-1" type="password" placeholder="Password" v-model="login.password"
              @click="isPass = false,isLogin=false,isEmailLogin=false,isPassLogin=false"
              @keydown="isLogin=false,isEmailLogin=false,isPassLogin=false" />
          </div>
          <p :class="isPass ? errorMessage : ''" v-if="isPass">*Plase Input your password*</p>
        </div>
        <div v-show="isLogin" class="text-center text-red-500">
          <p v-if="isEmailLogin">Email does not exist</p>
          <p v-else-if="isPassLogin">Your Email or Password incorrect</p>
          <p v-else>Something is Wrong. Plase Try Again.</p>
        </div>
      </div>
      <div class="flex space-x-2 justify-center mt-4">
        <button class="login-button hover:bg-[#99C0D0] hover:shadow-lg px-4"
          @click="checkLogin(login),isLogin=false,isEmailLogin=false,isPassLogin=false">submit</button>
      </div>
    </div>
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