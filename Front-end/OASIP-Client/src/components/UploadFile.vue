<script setup>
import { computed, ref } from 'vue';

const emits= defineEmits(['attemt'])
const fileStore = ref([])
const totalSizeFile = ref(0)
const isLarger = ref(false)

const Attemt = (e) => {
    if (Number((e.target.files[0].size / 1048576).toFixed(2)) <= 10) {
        totalSizeFile.value = Number((e.target.files[0].size / 1048576).toFixed(2))
        fileStore.value[0]=e.target.files[0]
    }
    else {
        isLarger.value = true
    }
    emits('attemt',fileStore.value)
}

const sizeFile = computed(() => {
    return totalSizeFile.value <= 10 ? 10 - totalSizeFile.value : totalSizeFile.value
})

const Clear=(index)=>{
    totalSizeFile.value-= Number((fileStore.value[index].size/1048576).toFixed(2))
    fileStore.value=fileStore.value.filter((e,i)=>{
        return i!==index
    })
}

const ClearAll=()=>{
    fileStore.value=[]
    totalSizeFile.value=0
    isLarger.value=false
}

</script>
 
<template>
    <div class="bg-grey-lighter mx-3 flex">
        <label
            class="w-32 h-13 pt-1 mt-1 flex flex-col items-center px-2 bg-white text-blue rounded-lg shadow-lg tracking-wide uppercase border border-blue cursor-pointer hover:bg-black hover:text-white">
            <svg class="w-6 h-6 mt-1" fill="currentColor" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20">
                <path
                    d="M16.88 9.1A4 4 0 0 1 16 17H5a5 5 0 0 1-1-9.9V7a3 3 0 0 1 4.52-2.59A4.98 4.98 0 0 1 17 8c0 .38-.04.74-.12 1.1zM11 11h3l-4-4-4 4h3v3h2v-3z" />
            </svg>
            <span class="text-sm leading-normal">Select a file</span>
            <input type='file' class="hidden" @change="Attemt" @click="isLarger=false"/>
        </label>
        <div v-if="fileStore.length > 0" class="flex bg-white ml-3 mr-3 mt-1 pt-1 rounded-lg  max-w-md">
            <div v-for="(file, index) in fileStore" :key="index" class="ml-1 mr-1 mb-1">
                <img src="../assets/file.png" class="h-8"/>
                <p>{{file.name}}</p>
            </div>
        </div>
        <div v-if="fileStore.length > 0" class="bg-red-600 h-7 rounded-lg mt-1 px-2 text-white mx-1 hover:bg-[#F87171]">
            <button @click="ClearAll">Clear File</button>
        </div>
    </div>
    <div class="text-sm text-stone-500 my-2 mx-4">
        <p>(Size of file : {{ sizeFile.toFixed(2) }} MB)</p>
        <p v-if="isLarger" class="text-xs text-red-600">Total of file size is larger more than 10 MB</p>
    </div>
</template>
 
<style scoped>

</style>