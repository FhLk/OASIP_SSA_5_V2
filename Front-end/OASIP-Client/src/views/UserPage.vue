<script setup>
import ListUser from '../components/ListUser.vue';
</script>

<template>
<div class="pt-36 font bg pb-56">
    <h1 class="text-5xl mb-4 ml-5 flex justify-between rounded-md p-2">List ALL User</h1>
    <ListUser/>
</div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
.font{
    font-family: 'Mitr', sans-serif;
}

.bg{
    background-color: rgb(255, 255, 247);
}
</style>