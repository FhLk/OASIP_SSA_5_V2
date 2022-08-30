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
  <div class="font">
    <div class="header mt-32 ">
     Welcome to OASIP
    </div>
    <div class="login mt-8">
      <h1 class="login-header bg-red-500">
        LOGIN
      </h1>
      <div class="login-body">
        <div class="login-input mt-14">
          <div class="flex justify-center mt-5 py-1">
            <img src="../assets/mail_user.png" class="user mx-2" />
            <input class="info-input mt-2 px-1" type="text" placeholder="Username" v-model="login.email" @click="isUser = false" />
            <p :class="isUser ? errorMessage : ''" v-if="isUser">*Plase Input your username*</p>
          </div>
          <div class="flex justify-center py-1">
            <img src="../assets/padlock.png" class="password mx-2" />
            <input class="info-input mt-2 px-1" type="password" placeholder="Password" v-model="login.password" @click="isPass = false" />
            <p :class="isPass ? errorMessage : ''" v-if="isPass">*Plase Input your password*</p>
          </div> 
        </div>
      </div>
      <div class="flex space-x-2 justify-center mt-4">
        <button class="login-button hover:bg-[#99C0D0] hover:shadow-lg px-4"
          @click="checkLogin(login)">submit</button>
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
  background-color: rgba(93, 143, 164) ;
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