<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import { getCategories, getCategory, saveCategory } from '../fetch/fetchCategory';

const emits = defineEmits(['save'])
const props = defineProps({
    categories: Array,
    role: Number
})
const clinicImg = ref([
    "./clinic1.png",
    "./clinic2.png",
    "./clinic3.png",
    "./clinic4.png",
    "./clinic5.png"
])
const isDetail = ref(-1)
const isEdit = ref(false)
const isEditId = ref(0)
const isNameEmpty = ref(false)
const isDuration = ref(false)
const Edit = ref({
    name: "",
    description: "",
    duration: 0
})
const getCategoryDetail = ref({})

const EditCategory = (category) => {
    isEdit.value = isEdit.value ? false : true
    if (isEdit.value) {
        isEditId.value = category.id
        Edit.value.name = category.categoryName
        Edit.value.description = category.description
        Edit.value.duration = category.duration
    }
    else {
        isEdit.value = false
        isEditId.value = 0
        Edit.value.name = getCategoryDetail.value.categoryName
        Edit.value.description = getCategoryDetail.value.description
        Edit.value.duration = getCategoryDetail.value.duration
        isNameEmpty.value = false
        isDuration.value = false
    }

}

const reset = () => {
    isEdit.value = false
    isEditId.value = 0
    Edit.value.name = ""
    Edit.value.description = ""
    Edit.value.duration = 0
}

const check = computed(() => {
    return Edit.value.name !== getCategoryDetail.value.categoryName
        || Edit.value.description !== getCategoryDetail.value.description
        || Edit.value.duration !== getCategoryDetail.value.duration
        ? false : true
});
const CheckInput = async (updateCategory) => {
    let isCheck = true
    if (Edit.value.name === '') {
        isCheck = false
        isNameEmpty.value = true
    }
    if (Edit.value.duration < 1 || Edit.value.duration > 480) {
        isCheck = false
        isDuration.value = true
    }
    else if (Edit.value.duration >= 1 && Edit.value.duration <= 480) {
        isDuration.value = false
    }
    if (isCheck) {
        if (confirm("Are you sure")) {
            updateCategory.description = Edit.value.description
            updateCategory.duration = Edit.value.duration
            updateCategory.categoryName = Edit.value.name
            isDuration.value = false
            isNameEmpty.value = false
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
    return 500 - Edit.value.description.length
})

const countName = computed(() => {
    return 100 - Edit.value.name.length
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
    <div class="mt-28 font">
            <button class="font btnindex hover:bg-[#00A1E1] rounded-md px-1 h-8 cf mx-14">
                        <router-link to="/">{{`<< Back`}}</router-link>
                </button>
            <div class="font flex justify-center">
                <h1 class="text-5xl mb-5">Category</h1>
            </div>
        <div class="font ccf bgcat mx-7 pt-7 rounded-t-lg">
            <h2 class="ml-10 text-xl mb-3">Definition</h2>
            <div class="flex justify-between ml-6">
                <div v-for="(category, index) in categories" :key="index">
                    <div class="mx-10" >
                        <img :src="clinicImg[index]" @click="categoryDetail(category.id)" class="cliImg" />
                    </div>
                    <div class="text-xl flex justify-center mx-2 mt-1">
                        <p> {{ category.categoryName }} </p>
                    </div>
                    <div class="flex justify-center">
                        <button class="btnindex hover:bg-[#00A1E1] rounded-md px-1 mt-4 h-8 cf mx-14">
                            <router-link to="/AddEventPage">{{`Add Booking`}}</router-link>
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="font ccf bgcat mx-7 py-5 rounded-b-lg mb-4 flex justify-center">
            <div v-for="(category, index) in categories" :key="index">
                <div v-if="isDetail===category.id">
                    <div class="flex justify-between">
                        <div>
                            <p>
                                <span v-if="isEdit && isEditId===category.id">
                                    <input type="text" v-model="Edit.name" maxlength="100" class="rounded-sm px-1" />
                                    <p class="text-sm text-stone-500">(Number of Character : {{countName}})</p>
                                    <p v-if="isNameEmpty && countName===100" class="text-xs text-red-600">Plase Input Category
                                        Name.
                                    </p>
                                </span>
                                <span v-else class="text-xl">
                                    {{ category.categoryName }}
                                </span>
                            </p>
                        </div>
                        <button class="test text-5xl justify-self-end h-0" @click="close">&times;</button>
                        </div>
                        <div>
                            <p>Description:
                                <span v-if="isEdit && isEditId===category.id">
                                    <textarea rows="5" cols="50" v-model="Edit.description" maxlength="500"
                                        class="rounded-sm px-1"></textarea>
                                    <p class="text-sm text-stone-500">(Number of Character : {{countDescription}})</p>
                                </span>
                                <span v-else>
                                    <div class="">
                                        {{ getCategoryDetail.description }}
                                    </div>
                                </span>
                            </p>
                            <p>Duration:
                                <span v-if="isEdit && isEditId===category.id">
                                    <input type="number" v-model="Edit.duration" min="1" max="480" class="rounded-sm pl-1"
                                        @click="isDuration=false" />
                                    : <span class="text-sm text-stone-500">(1 to 480 mins)</span>
                                    <p v-if="isDuration" class="text-xs text-red-600">Duration have time less/more than range
                                    </p>
                                </span>
                                <span v-else>
                                    {{ getCategoryDetail.duration }} (mins.)
                                </span>
                            </p>
                        </div>
                        <div>
                            <button v-if="isEdit" @click="CheckInput(category)"
                                class="bg-green-600 rounded-full px-2 mx-2 text-white hover:bg-[#4ADE80] disabled:bg-[#8F9892]"
                                :disabled="check">
                                Save
                            </button>
                            <button v-if="role===1||role===0" @click="EditCategory(category)" :class="isEdit ? ccl : ced">
                                {{ isEdit ? "Cancel":"Edit"}}
                            </button>
                        </div>
                    </div>
                </div>
            <div>
        </div>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.btnindex {
    background-color: rgb(0, 191, 255);
}

.cf {
    color: rgb(248 250 252);
}

.font {
    font-family: 'Mitr', sans-serif;
}

.test {
    cursor: pointer;
    border: none;
    outline: none;
    background: none;
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

.cliImg {
    width: 10rem;
    height: 10rem;
    margin-top: 0.1rem;
}
</style>