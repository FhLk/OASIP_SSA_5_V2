<script setup>
import { ref } from 'vue';
const role = ref(['adimin', 'lecturer', 'student'])
const users = ref([
    { username: 'Somchai Jaidee', emil: 'somchai.jai@kmutt.ac.th', role: 'lecturer' },
    { username: 'Komkrid Rakdee', emil: 'komkrid.rak@mail.kmutt.ac.th', role: 'student' },
    { username: 'OASIP ADMIN', emil: 'oasip.admin@kmutt.ac.th', role: 'admin' },
    { username: 'Paramat Pet-in', emil: 'paramat10042545@hotmail.com', role: 'student' },
    { username: 'Jhon Michel', emil: 'JhonM@hotmail.com', role: 'lecturer' },
    { username: 'Alex Max', emil: 'Alexander@kmutt.ac.th', role: 'lecturer' },
])
</script>
 
<template>
    <div class="p-5">
        <h1 class="text-6xl font-medium">List ALL User</h1>
        <ul>
            <li class="m-5 bg-sky-500 pl-5 pt-3 pb-3 text-2xl" v-for="(user, index) in users" :key="index" >
                <div>
                    Name: {{ user.username }}<br>
                    E-mail: {{ user.emil }}<br>
                    Role: {{ user.role }}
                </div>
                <div>
                    <div class="p-1 bg-white mt-2 inline-block">
                        <button>Detail</button>
                    </div>
                    <div class="p-1 bg-white mt-2 inline-block ml-5">
                        <button>Delete</button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</template>
 
<style scoped>
.p-1{
    text-align: center;
    width: 7%;
    border-radius: 20px;
}
.m-5 {
    border-radius: 20px;
}
</style>