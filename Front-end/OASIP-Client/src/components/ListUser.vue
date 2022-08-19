<script setup>
import { ref } from 'vue';
const role = ref(['adimin', 'lecturer', 'student'])
const users = [{
                username : "note" ,
                email : "note@gmail.com" ,  
                role : "ADMIN"
                },
               {
                username : "fight" ,
                email : "fight@gmail.com" ,  
                role : "STUDENT" 
                }] ;

</script>
 
<template>
    <div class="font bgl rounded-xl px-10 mx-10 pt-7 pb-10">
        <ul>
            <li class="bgl2 my-2 pb-3 pt-2 pl-9 rounded-md" v-for="(user, index) in users" :key="index" >
                <div>
                    <p class="text-2xl" >User : {{ user.username }}</p>
                    <p class="text-[#5C5A5A]"><span class="text-black">E-mail :</span>  {{ user.email }}   <span class="text-black">Role :</span> {{ user.role }} </p> 
                </div>
                <div class="flex mt-1 cf">
                    <div class="mx-2 bg-green-600 px-2 rounded-xl">
                        <button>Detail</button>
                    </div>
                    <div class="mx-2 bg-red-600 px-2 rounded-xl">
                        <button>Delete</button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');
.font{
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
.del{
    width: 2.5rem ;
    height: 2.5rem ;
    margin-top: 0.25rem;
}
.edit {
    background-color: rgb(114, 143, 206);
}
.bgde {
    background-color: rgb(254, 252, 255);
}
.bgl{
    background-color: rgb(92, 179, 255);
}
.bgl3{
    background-color: rgb(135, 206, 235);
}
.btt{
    background-color: rgb(25, 25, 112);
}
.cf {
    color: rgb(251, 251, 249);
}
.cbts{
    background-color: rgb(0, 0, 128);
}
.cbtso{
    background-color: rgb(60, 60, 188);
}
.clear{
    background-color: rgb(88, 0, 88);
}
</style>