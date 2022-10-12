<script setup>
import { computed } from '@vue/reactivity';
import { onBeforeMount, ref } from 'vue';
import { checkRole } from '../Store/local';
const role=ref(-1)
const slideImg = ref([
    "./index1.png",
    "./index2.png",
    "./index3.png",
    "./index4.png",
    "./index5.png",
])

onBeforeMount(()=>{
    role.value=checkRole(localStorage.getItem("role"))
})

const numberSlide=ref(1)
const imgIndex = ref(0)
const play = ref(setInterval(() => {
    numberSlide.value++
    imgIndex.value++
    if (imgIndex.value > 4) {
        imgIndex.value = 0
        numberSlide.value=1
    }
}, 3000)
)

const changeSlide = () => {
    clearInterval(play.value)
    if (imgIndex.value < 0) {
        numberSlide.value=5
        imgIndex.value = 4
    }
    if (imgIndex.value > 4) {
        numberSlide.value=1
        imgIndex.value = 0
    }
    setTimeout(autoPlay(), 5000)
}

const autoPlay = () => {
    play.value = setInterval(() => {
        imgIndex.value++
        if (imgIndex.value > 2) {
            numberSlide.value=1
            imgIndex.value = 0
        }
    }, 3000)
}
</script>
 
<template>
    <div class="bg h-screen h-full">
        <div class="bg h-screen h-full pt-28 font text-5xl flex justify-center">
            <h2> Online Appointment Scheduling </h2>
        </div>
        <div class="bg h-screen h-full flex justify-center font">
            <button v-if="role!==1" class="btnindex hover:bg-[#00A1E1] rounded-md px-1 mt-4 h-8 cf mx-14">
                <router-link to="/AddEventPage">Add New Schedule</router-link>
            </button>
            <button class="btnindex hover:bg-[#00A1E1] rounded-md px-1 mt-4 h-8 cf mx-14">
                <router-link to="/EventPage"> Show Schedule </router-link>
            </button>
            <button class="btnindex hover:bg-[#00A1E1] rounded-md px-1 mt-4 h-8 cf mx-14">
                <router-link to="/CategoryPage">Category</router-link>
            </button>
        </div>
        <div class="flex justify-between mb-1 pl-5 pt-7 bg h-full h-screen font">
            <div class="flex">
                <button class="btnindex hover:bg-[#00A1E1] rounded-md px-1 mt-36 h-8 cf mx-14"
                    @click="imgIndex--,numberSlide--,changeSlide()">
                       Back 
                </button>
            </div>
                <div class="flex">
                    <img :src="slideImg[imgIndex]" class="imgid rounded-2xl shadow-md shadow-black/25" />
                </div>
            <div class="flex">
                <button class="btnindex hover:bg-[#00A1E1] rounded-md px-1 mt-36 h-8 cf mx-14"
                   @click="imgIndex++,numberSlide++,changeSlide()">
                      Next
               </button>
            </div>    
        </div>
            <div class="font flex justify-center">
                {{numberSlide}}/5
            </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
    font-family: 'Mitr', sans-serif;
}

.bg {
    background-color: rgb(255, 255, 247);
}

.imgid {
    width: 664px;
    height: 465px;
}

.btnindex {
    background-color: rgb(0, 191, 255);
}

.cf {
    color: rgb(248 250 252);
}

.slider {
    width: 300px;
    text-align: center;
    overflow: hidden;
}

.slides {
    display: flex;
    overflow-x: auto;
    scroll-snap-type: x mandatory;
    scroll-behavior: smooth;
    -webkit-overflow-scrolling: touch;
}

.slides::-webkit-scrollbar {
    width: 10px;
    height: 10px;
}

.slides::-webkit-scrollbar-thumb {
    background: #666;
    border-radius: 10px;
}

.slides::-webkit-scrollbar-track {
    background: transparent;
}

.slides>div {
    scroll-snap-align: start;
    flex-shrink: 0;
}

.slider>a {
    display: inline-flex;
    width: 1.5rem;
    height: 1.5rem;
    background: white;
    text-decoration: none;
    align-items: center;
    justify-content: center;
    border-radius: 50%;
    position: relative;
}

.slider>a:active {
    top: 1px;
    color: #1c87c9;
}

.slider>a:focus {
    background: #eee;
}
</style>