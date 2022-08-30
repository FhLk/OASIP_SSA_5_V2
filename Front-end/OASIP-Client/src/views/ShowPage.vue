<script setup>
import {onMounted,onBeforeMount,ref, onBeforeUpdate, onUpdated, computed} from 'vue';
import List from '../components/List.vue'
let DateFormat="YYYY-MM-DD HH:mm"
const isShow=ref(false)

</script>
 
<template>
<div class="bg1 font h-full ">
    <h1 class=" ccf cr text-5xl pt-32 mb-4 ml-5 flex justify-start rounded-md p-2">Schedule
        <img src="../assets/timetable.png" class="calendar ml-5 " ></h1>
    <List/>
</div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
.font{
    font-family: 'Mitr', sans-serif;
}
.bg{
    background-color: rgb(173, 216, 230);
}
.ccf {
    color: rgb(42, 39, 40);
}
.cr{
    --tw-ring-color: rgb(42, 39, 40);
}
.bg1{
    background-color: rgb(255, 255, 247);
}
.calendar {
    width: 3rem;
    height: 3rem;
    margin-top: 0.3rem;
}
</style>
