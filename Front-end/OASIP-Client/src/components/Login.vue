<script setup>
import { ref } from "vue";
const emits = defineEmits(['close'])
const props=defineProps({
  isPopup:{
    type: Boolean,
    require: true
  }
});
const username = ref("");
const password = ref("");
const errorMessage=ref("")
const isUser=ref(false)
const isPass=ref(false)

const checkLogin=(user,pass)=>{
  if(user===""&&pass===""){
    isUser.value=true
    isPass.value=true
    errorMessage.value="mb-2 text-[#FF0000] text-sm"
  }
  else if(user!==""&&pass===""){
    isUser.value=false
    isPass.value=true
    errorMessage.value="mb-2 text-[#FF0000] text-sm"
  }

  else if(user===""&&pass!==""){
    isPass.value=false
    isUser.value=true
    errorMessage.value="mb-2 text-[#FF0000] text-sm"
  }
  else{
    isPass.value=false
    isUser.value=false
  }
}

const reset=()=>{
  username.value="";
  password.value="";
  isPass.value=false
  isUser.value=false
  errorMessage.value=""
}

</script>
 
<template>
<div class="login" v-show="isPopup">
        <div class="login-header">
          <div class="login-title">Login</div>
          <button class="close-login" @click="$emit('close'),reset()">&times;</button>
        </div>
        <div class="login-body">
          <div class="login-input">
            <p>User Name</p>
            <input class="info-input" type="text" v-model="username" @click="isUser=false"/>
            <p :class="isUser ? errorMessage:''" v-if="isUser">*Plase Input your username*</p>
            <p>Password</p>
            <input class="info-input" type="password" v-model="password" @click="isPass=false"/>
            <p :class="isPass ? errorMessage:''" v-if="isPass">*Plase Input your password*</p>
          </div>
        </div>
        <div class="flex space-x-2 justify-center">
          <button class="login-button hover:bg-blue-700 hover:shadow-lg" type="button" @click="checkLogin(username,password)">Log-in</button>
        </div>
      </div>
</template>
 
<style scoped>

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
}
</style>