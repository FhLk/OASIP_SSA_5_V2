<script setup>
import { ref } from 'vue';
const emits = defineEmits(['index'])

const signIn = () => {
    localStorage.clear()
}

const hold = "hover:bg-[#7dd3fc] hover:shadow-lg rounded-md px-1 mt-4 h-8 cf1 mx-1"
</script>
 
<template>
    <div>
        <div class="flex justify-between">
            <div class="justify-self-end">
                    <button :class="hold">
                        <router-link to="/category">Category</router-link>
                    </button>
                <button @click="signIn" class="btLogIn hover:bg-[#A53D59] rounded-md px-1 mt-4 h-8 cf1 mx-2">
                    <router-link to="/login">Sign In</router-link>
                </button>
            </div>
        </div>
    </div>

</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.cf {
    color: rgb(251, 251, 249);
}

.router-link-active {
    color: #0547A5;
}

.cf1 {
    color: rgb(0, 0, 0);
}


.btLogIn {
    background-color: rgb(251 113 133);
}
</style>