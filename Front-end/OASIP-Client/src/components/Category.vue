<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import { getCategories, getCategory, saveCategory } from '../fetch/fetchCategory';

const emits = defineEmits(['save'])
const props = defineProps({
    categories: Array
})
const isDetail = ref(-1)
const getCategoryDetail = ref({})
const isEdit = ref(false)
const isEditId = ref(0)
const EditDescription = ref("")
const EditName = ref("")
const EditDuration = ref(0)

const EditCategoryOpen = (category) => {
    isEdit.value = true
    isEditId.value = category.id
    EditName.value = category.categoryName;
    EditDescription.value = category.description
    EditDuration.value = category.duration
}

const EditCategoryClose = () => {
    isDuration.value = false
    isNameEmpty.value = false
    isEditId.value = 0
    isEdit.value = false
}

const reset = () => {
    isEdit.value = false
    isEditId.value = 0
    EditName.value = ""
    EditDescription.value = ""
    EditDuration.value = 0
}

const isNameEmpty = ref(false)
const isDuration = ref(false)
const CheckInput = async (updateCategory) => {
    // EditDescription.value = updateCategory.description
    // console.log(updateCategory)
    // console.log(EditDescription.value);
    // console.log(updateCategory)
    let isCheck = true
    if (EditName.value === '') {
        isCheck = false
        isNameEmpty.value = true
    }
    if (EditDuration.value < 1 || EditDuration.value > 480) {
        isCheck = false
        isDuration.value = true
    }
    else if (EditDuration.value >= 1 && EditDuration.value <= 480) {
        isDuration.value = false
    }
    if (isCheck) {
        updateCategory.description = EditDescription.value
        updateCategory.duration = EditDuration.value
        updateCategory.categoryName = EditName.value
        isDuration.value = false
        isNameEmpty.value = false
        if (confirm("Are you sure")) {
            await save(updateCategory)
            reset()
        }
    }
}
const save = async (updateCategory) => {
    let res = await saveCategory(updateCategory)
    if (res === 200) {
        emits('save')
        reset()
    }
}
const ced = " edit rounded-full px-2 text-white hover:bg-[#AECBFF]";
const ccl = " bg-red-600 rounded-full px-2 text-white hover:bg-[#F87171]";

const countDescription = computed(() => {
    return 500 - EditDescription.value.length
})

const countName = computed(() => {
    return 100 - EditName.value.length
})

let count = 0
const categoryDetail = async (id) => {
    if (count !== id) {
        getCategoryDetail.value = await getCategory(id)
        count = id
    }
    isDetail.value = isDetail.value === id ? -1 : id
    isEdit.value = false
    reset()
}

const close = () => {
    isDetail.value = -1
    isEdit.value = false
    isEditId.value = -1
    reset()
}

</script>
 
<template>
    <div class="mt-32">
        <h1>Category</h1>
        <div class="font ccf bgcat mx-7 py-5 rounded-lg">
            <h2 class="ml-10 text-xl">Definition</h2>
            <div v-for="(category, index) in categories" :key="index" class="flex">
                <img src="../assets/logo.png" @click="categoryDetail(category.id)" />
                <div v-if="isDetail===category.id">
                    <button class="test" @click="close">&times;</button>
                    <p>Name:
                        <span v-if="isEdit && isEditId===category.id">
                            <input type="text" v-model="EditName" maxlength="100" class="rounded-sm px-1" />
                            <p class="text-sm text-stone-500">(Number of Character : {{countName}})</p>
                            <p v-if="isNameEmpty && countName===100" class="text-xs text-red-600">Plase Input Category
                                Name.
                            </p>
                        </span>
                        <span v-else>
                            {{ category.categoryName }}
                        </span>
                    </p>
                    <p>Description:
                        <span v-if="isEdit && isEditId===category.id">
                            <textarea rows="5" cols="50" v-model="EditDescription" maxlength="500"
                                class="rounded-sm px-1"></textarea>
                            <p class="text-sm text-stone-500">(Number of Character : {{countDescription}})</p>
                        </span>
                        <span v-else>
                            <div class="description">
                                {{  category.description }}
                            </div>
                        </span>
                    </p>
                    <p>Duration:
                        <span v-if="isEdit && isEditId===category.id">
                            <input type="number" v-model="EditDuration" min="1" max="480" class="rounded-sm pl-1" />
                            : <span class="text-sm text-stone-500">(1 to 480 mins)</span>
                            <p v-if="isDuration" class="text-xs text-red-600">Duration have time less/more than range
                            </p>
                        </span>
                        <span v-else>
                            {{  category.duration }} (mins.)
                        </span>
                    </p>
                    <div>
                        <button v-if="isEditId!==category.id" @click="EditCategoryOpen(category)"
                            :class="ced ">Edit</button>
                        <div v-if="isEdit && isEditId===category.id" class="mt-2">
                            <button @click="CheckInput(category)"
                                class="bg-green-600 rounded-full px-2 mx-2 text-white hover:bg-[#4ADE80]">Save</button>
                            <button @click="EditCategoryClose" :class="ccl">Cancel</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.test {
    cursor: pointer;
    border: none;
    outline: none;
    background: none;
    font-size: 35px;
    font-weight: bold;
}

.test:hover {
    color: red;
}

.description {
    max-width: 35%;
    margin-left: 2%;
}

.font {
    font-family: 'Mitr', sans-serif;
}

.ccf {
    color: rgb(42, 39, 40);
}

.bgcat {
    background-color: rgb(167, 214, 255);
}

.edit {
    background-color: rgb(21, 49, 126);
}
</style>