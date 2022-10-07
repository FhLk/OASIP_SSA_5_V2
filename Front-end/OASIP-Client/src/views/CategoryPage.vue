<script setup>
import { onBeforeMount, ref } from 'vue';
import Category from '../components/Category.vue';
import {getCategories} from '../fetch/fetchCategory.js'
const listCategories= ref([])
onBeforeMount( async ()=>{
    listCategories.value=await getCategories()
})

const getList= async()=>{
    listCategories.value=await getCategories()
}
</script>
 
<template>
    <Category :categories="listCategories" @save="getList"/>
</template>
 
<style>

</style>