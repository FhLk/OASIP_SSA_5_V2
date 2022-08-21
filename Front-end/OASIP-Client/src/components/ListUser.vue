<script setup>
import { onBeforeMount, ref } from 'vue';
import moment from "moment"
const fetchUrl = import.meta.env.VITE_BASE_URL
let DateFormat = "YYYY-MM-DD HH:mm"

const getAllUser = ref([])
const isDetail = ref("")
const getUser = ref({})
const getUsers = async () => {
    const res = await fetch(`${fetchUrl}/users`, {
        method: 'GET'
    })
    if (res.status === 200) {
        getAllUser.value = await res.json()
    }
}

let count = ""
const detailUser = async (name) => {
    if (count !== name) {
        const res = await fetch(`${fetchUrl}/users/${name}`, {
            method: 'GET'
        })
        if (res.status === 200) {
            getUser.value = await res.json();
            getUser.value.createdOn = showTimeStampe(getUser.value.createdOn)
            getUser.value.updateOn = showTimeStampe(getUser.value.updateOn)
            count = name;
        }
    }
    isDetail.value = isDetail.value === name ? "" : name
}

const showTimeStampe = (datatime) => {
    return moment(datatime).local().format(DateFormat)
}

const deleteUser= async (user)=>{
    const res= await fetch(`${fetchUrl}/users/${user.name}`,{
        method :'DELETE'
    })
    if(res.status===200){
        await getUsers()
    }
    else{
        alert("Can't Delete this Booking")
    }
}

const reset=()=>{
    isDetail.value=""
    count=""
}

onBeforeMount(async () => {
    await getUsers()
})

const ced = " edit rounded-full px-2 text-white background-color: rgb(114, 143, 206) hover:bg-[#AECBFF]";
const ccl = " bg-red-600 rounded-full px-2 text-white hover:bg-[#F87171]";
const cdet = " bg-green-600 rounded-full px-2 text-white hover:bg-[#4ADE80]";



</script>
 
<template>
    <div class="font bgl rounded-xl px-10 mx-10 pt-7 pb-10">
        <ul>
            <li class="bgl2 my-2 pb-3 pt-2 pl-9 rounded-md" v-for="(user, index) in getAllUser" :key="index">
                <div>
                    <p class="text-2xl">User : {{ user.name }}</p>
                    <p class="text-[#5C5A5A]"><span class="text-black">E-mail :</span> {{ user.email }} <span
                            class="text-black">Role :</span> {{ user.role }} </p>
                </div>
                <div class="flex mt-1 cf">
                    <div class="mx-2 bg-green-600 px-2 rounded-xl">
                        <button @click="detailUser(user.name)">{{ isDetail === user.name ? "Closed" :"Detail"}}</button>
                    </div>
                     <div>
                        <img @click="deleteUser(user)" src="../assets/trash.png" 
                                class="del ring bg-[#FFFFFF] ring-[#FFFFFF] hover:bg-red-500 hover:ring-red-500 rounded-md cursor-pointer shadow-md hover:shadow-red-500">
                    </div>
                </div>
                <div v-if="isDetail === user.name" class="bgl3 px-5 pt-2 mt-2 pb-3 rounded-md">
                    <div>
                        <div class="flex">
                            <p class="pr-2">Name : </p>
                            <p>{{ getUser.name }}</p>
                        </div>
                        <div class="flex">
                            <p class="pr-2">E-mail : </p>
                            <p>{{ getUser.email }}</p>
                        </div>
                        <div class="flex">
                            <p class="pr-2">Role : </p>
                            <p>{{ getUser.role }}</p>
                        </div>
                        <div class="flex">
                            <p class="pr-2">Created : </p>
                            <p>{{ getUser.createdOn }}</p>
                        </div>
                        <div class="flex">
                            <p class="pr-2">Updated : </p>
                            <p>{{ getUser.updateOn }}</p>
                        </div>
                    </div>
                </div>
                <div>

                </div>
            </li>
        </ul>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.bgl3 {
    background-color: rgb(135, 206, 235);
}

.font {
    font-family: 'Mitr', sans-serif;
}

.bg {
    background-color: rgb(173, 216, 230);
}

.ccf {
    color: rgb(42, 39, 40);
}

.bgl2 {
    background-color: rgb(164, 220, 255);
}

.del {
    width: 2.5rem;
    height: 2.5rem;
    margin-top: 0.25rem;
}

.edit {
    background-color: rgb(114, 143, 206);
}

.bgde {
    background-color: rgb(254, 252, 255);
}

.bgl {
    background-color: rgb(92, 179, 255);
}

.bgl3 {
    background-color: rgb(135, 206, 235);
}

.btt {
    background-color: rgb(25, 25, 112);
}

.cf {
    color: rgb(251, 251, 249);
}

.cbts {
    background-color: rgb(0, 0, 128);
}

.cbtso {
    background-color: rgb(60, 60, 188);
}

.clear {
    background-color: rgb(88, 0, 88);
}
</style>