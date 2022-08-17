<script setup>
import { ref } from "vue";
const username = ref("");
const password = ref("");
const popUpLogin=ref(true);

</script>
 
<template>
<div class="login" v-show="popUpLogin">
        <div class="login-header">
          <div class="login-title">Login</div>
          <button class="close-login" @click="popUpLogin=false">&times;</button>
        </div>
        <div class="login-body">
          <div class="login-input">
            <p>User Name</p>
            <input class="info-input mb-5" type="text" v-model="username"/>
            <p>Password</p>
            <input class="info-input mb-5" type="password" v-model="password"/>
          </div>
        </div>
        <div class="flex space-x-2 justify-center">
          <button class="login-button hover:bg-blue-700 hover:shadow-lg" type="button">Log-in</button>
        </div>
        <!-- <div class="flex space-x-2 justify-center">
  <button type="button" class="inline-block px-6 py-2.5 bg-blue-600 text-white font-medium text-xs leading-tight uppercase rounded shadow-md hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg transition duration-150 ease-in-out">Button</button>
</div> -->
      </div>
</template>
 
<style scoped>

.login-input{
  text-align: center;
}
.login-button {
  display: inline-block;
  background-color: lightblue;
  width: 20%;
  height: 50%;
  margin-bottom: 2%;
  border-radius: 10px;
  font-size: 30px;
  color: antiquewhite;
}
.info-input {
  border-style: solid;
  border-width: 5px;
  border-radius: 5px;
  border-color: black;
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