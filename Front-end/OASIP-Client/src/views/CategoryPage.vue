<script setup>
import { onBeforeMount, ref } from 'vue';
import Category from '../components/Category.vue';
import {getCategories} from '../fetch/fetchCategory.js'
const listCategories= ref([])
const isToken=ref(false)
onBeforeMount( async ()=>{
    listCategories.value=await getCategories()
    isToken.value = checkToken()
})

const getList= async()=>{
    listCategories.value=await getCategories()
}
</script>
 
<template>
    <Category v-if="isToken" :categories="listCategories" @save="getList"/>
    <div v-else class="font bgl2 rounded-xl px-10 mx-10 pt-5 pb-11">   
                <div class="flex justify-center text-2xl">
                    <p>No Category.</p>
                </div>
                <div class="flex justify-center text-2xl pt-4">
                    <p>Plase Sign-in for use OASIP.</p>
                </div>
                <div class="flex justify-center pt-2">
                    <button class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">
                        <router-link to="/LoginPage">Sign In</router-link>
                    </button>
                </div>
        </div>
</template>
 
<style>

</style>