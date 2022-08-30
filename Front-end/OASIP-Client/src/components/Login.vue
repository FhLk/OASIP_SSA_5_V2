<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const fetchUrl = import.meta.env.VITE_BASE_URL

const username = ref("");
const password = ref("");
const errorMessage = ref("")
const isUser = ref(false)
const isPass = ref(false)
const login=ref({
  email:"",
  password:""
})
const checkLogin = (login) => {
  let isCheck=true
  if (login.email === "" && login.password === "") {
    isCheck=false
    isUser.value = true
    isPass.value = true
    errorMessage.value = "mb-2 text-[#FF0000] text-sm"
  }
  else if (login.email !== "" && login.password === "") {
    isCheck=false
    isUser.value = false
    isPass.value = true
    errorMessage.value = "mb-2 text-[#FF0000] text-sm"
  }
  else if (login.email === "" && login.password !== "") {
    isCheck=false
    isPass.value = false
    isUser.value = true
    errorMessage.value = "mb-2 text-[#FF0000] text-sm"
  }
  if(isCheck) {
    isPass.value = false
    isUser.value = false
    logIn(login)
    reset()
  }
}

const logIn=async (login)=>{
  const res = await fetch(`${fetchUrl}/login`, {
        method: "POST",
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
          email:login.email,
          password:login.password
        })
    })
    if (res.status === 200) {
        alert("Match")
    }
    else{
      alert("Not Match")
    }
}

const reset = () => {
  username.value = "";
  password.value = "";
  isPass.value = false
  isUser.value = false
  errorMessage.value = ""
}

const myRouter = useRouter()
const GoIndex = () => {
  myRouter.push({ name: 'indexPage' })
}

</script>
 
<template>
  <div>
    <div class="header ">
      OASIP
    </div>
    <div class="login">
      <h1 class="login-header bg-red-500">
        LOGIN
      </h1>
      <div class="login-body">
        <div class="login-input">
          <!-- <img src="../assets/user.png" class="user-img" /> -->
          <input class="info-input" type="text" placeholder="Username" v-model="login.email" @click="isUser = false" />
          <p :class="isUser ? errorMessage : ''" v-if="isUser">*Plase Input your username*</p>
          <!-- <img src="../assets/padlock.png" class="pass-img" /> -->
          <input class="info-input" type="password" placeholder="Password" v-model="login.password" @click="isPass = false" />
          <p :class="isPass ? errorMessage : ''" v-if="isPass">*Plase Input your password*</p>
        </div>
      </div>
      <div class="flex space-x-2 justify-center">
        <button class="login-button hover:bg-blue-700 hover:shadow-lg"
          @click="checkLogin(login)">Log-in</button>
      </div>
    </div>
  </div>
</template>
 
<style scoped>
.header {
  display: flex;
  justify-content: center;
  font-size: 70px;
}

.login-button {
  /* display: inline-block; */
  background-color: lightblue;
  height: 50%;
  margin-top: 2%;
  border-radius: 10px;
  font-size: 30px;
  color: antiquewhite;
  width: 55%;
  margin-top: 5%;
}

.login-input {
  text-align: center;
}

.info-input {
  border-style: solid;
  border-width: 5px;
  border-radius: 5px;
  border-color: black;
  width: 60%;
  margin-top: 5%;
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
  height: 45%;
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