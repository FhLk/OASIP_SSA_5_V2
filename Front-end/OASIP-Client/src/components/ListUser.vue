<script setup>
import { ref } from 'vue';
const role = ref(['adimin', 'lecturer', 'student'])

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