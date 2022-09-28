<script setup>
import ListUser from '../../components/ListUser.vue';
import { AllUser, reAuthen } from '../../fetch/fetchUserAPI.js'
import { computed, onBeforeMount, onBeforeUnmount, onBeforeUpdate, ref } from 'vue';
import { checkToken, expiresAccess, expiresToken } from '../../Store/local.js';

const getAllUser = ref([])
const isToken = ref(false)
const isExpire = ref(false)

onBeforeMount(async () => {
    isToken.value = checkToken()
    isExpire.value = expiresAccess()
    if (isExpire.value) {
        await reAuthen()
    }
    getAllUser.value = await AllUser()
})

</script>

<template>
    <div class="pt-28 font bg pb-56">
        <h1 class="text-5xl mb-4 ml-5 flex justify-start rounded-md p-2">List ALL User
            <img src="../../assets/team.png" class="user ml-5 ">
        </h1>
        <ListUser v-if="isToken" :getUsers="getAllUser" />
        <div v-else class="font bgl rounded-xl px-10 mx-10 pt-7 pb-10">
            <div class="flex justify-center text-2xl">
                <p>Plase Sign-in for use OASIP.</p>
            </div>
            <div class="flex justify-center pt-2">
                <button class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">
                    <router-link to="/LoginPage">Sign In</router-link>
                </button>
            </div>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.btt {
    background-color: rgb(25, 25, 112);
}

.cf {
    color: rgb(251, 251, 249);
}

.bgl {
    background-color: rgb(92, 179, 255);
}

.font {
    font-family: 'Mitr', sans-serif;
}

.bg {
    background-color: rgb(255, 255, 247);
}

.user {
    width: 3.5rem;
    height: 3.5rem;
    margin-top: 0.35rem;
}
</style>