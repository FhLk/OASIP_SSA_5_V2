<script setup>

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
        <img src="../assets/user.png" class="user-img"/>
        <input class="info-input" type="text" placeholder="Username" v-model="username" @click="isUser=false"/>
        <p :class="isUser ? errorMessage:''" v-if="isUser">*Plase Input your username*</p>
        <img src="../assets/padlock.png" class="pass-img"/>
        <input class="info-input" type="password" placeholder="Password" v-model="password" @click="isPass=false"/>
        <p :class="isPass ? errorMessage:''" v-if="isPass">*Plase Input your password*</p>
        </div>
    </div>
    <div class="flex space-x-2 justify-center">
        <button class="login-button hover:bg-blue-700 hover:shadow-lg" type="button" @click="checkLogin(username,password)">Log-in</button>
    </div>
</div>
</div>
</template>
 
<style scoped>
.header{
    display: flex;
    justify-content: center;
    font-size: 70px;
}
.pass-img{
    height: 10%;
    width: 10%;
    margin-right: 5%;
}
.user-img{
    display: inline-block;
    height: 10%;
    width: 10%;
    margin-right: 5%;
}
.login-button {
  display: inline-block;
  background-color: lightblue;
  height: 50%;
  margin-top: 2%;
  border-radius: 10px;
  font-size: 30px;
  color: antiquewhite;
  width: 55%;
}
.login-input{
    text-align: center;
}
.info-input {
  border-style: solid;
  border-width: 5px;
  border-radius: 5px;
  border-color: black;
  width: 60%;
}
.login-header{
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
    height: 40%;
    color: black;
    border: black 2px solid;
    box-shadow: 5px 5px 10px 2px rgba(36, 36, 36, 0.507);
}
</style>