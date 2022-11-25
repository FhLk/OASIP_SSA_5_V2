<script setup>
import Swal from 'sweetalert2';
import { onBeforeMount, onBeforeUpdate, ref } from 'vue';
import { useRouter } from 'vue-router';
const emits = defineEmits(['signOut', 'timeOut', 'menu'])
const name = ref("")
const role = ref("")
const picUser = ref("")
const props = defineProps({
    isTogle: Boolean
})

onBeforeMount(() => {
    role.value = localStorage.getItem("role")
    name.value = localStorage.getItem("name").split(" ")
    if (role.value === "ADMIN") {
        picUser.value = "./admin.png"
    }
    else if (role.value === "LECTURER") {
        picUser.value = "./lecturer.png"
    }
    else {
        picUser.value = "./student.png"
    }
})

const signOut = () => {
    localStorage.clear()
    emits('signOut')
    Swal.fire({
        allowOutsideClick: false,
        icon: "success",
        title: "Success",
        text: "Thank you for use OASIP.",
        confirmButtonText: "Close"
    })
}
</script>
 
<template>
    <div class="block">
        <button>
            <div class="bgUser hover:bg-[#84C0F6] flex px-2 py-2 rounded-lg max-h-20"
                @click="isTogle = isTogle ? false : true, $emit('menu')">
                <img :src="picUser" class="imgid shadow-md shadow-neutral-600/50 ">
                <div class="ml-1 text-center">
                    <p>{{ name[0] }} {{ name[1].charAt(0) }}{{ name[1].charAt(1) }}.</p>
                    <p class="">{{ role }}</p>
                </div>
            </div>
        </button>
        <div v-if="isTogle" class="bg-[#4A87BE] mt-1 rounded-lg fixed ">
                <ul class=" text-sm text-gray-700 dark:text-gray-200"
                    aria-labelledby="dropdownInformdropdownAvatarNameButtonationButton">
                    <li>
                        <div v-if="role=== 'ADMIN'">
                            <router-link to="/user/create"
                            @click="isTogle=false"
                                class="block py-2 pl-4 pr-12 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-600 dark:hover:text-white">
                                Create User
                            </router-link>
                        </div>
                    </li>
                </ul>
            <div class="py-1">
                <router-link to="/login" @click="signOut()"
                    class="block py-2 pl-4 pr-12 rounded-lg text-sm text-gray-700 hover:bg-gray-100 dark:hover:bg-gray-600 dark:text-gray-200 dark:hover:text-white">
                    Sign Out</router-link>
            </div>
        </div>
    </div>
</template>
 
<style scoped>
.imgid {
    width: 50px;
    height: 50px;
    border-radius: 50%;
}

.bgUser {
    background-color: rgb(97, 176, 247);
    height: 85%;
}
</style>