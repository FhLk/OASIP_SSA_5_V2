<script setup>
import { computed, onBeforeMount, onBeforeUpdate, ref } from 'vue';
import Swal from 'sweetalert2';
import moment from "moment";
import { deleteUser, getUsers, detail, save } from '../fetch/fetchUserAPI';
import { accessAlert, ExceptionAlert, LoadingAlert, sureAlert } from '../Alert/alert.js';
let DateFormat = "YYYY-MM-DD HH:mm"
const emits = defineEmits(['save'])
const props = defineProps({
    getUsers: {
        type: Array,
        require: true
    },
    role: Number
})

const getAllUser = ref([])
const isDetail = ref(-1)
const getUser = ref({})
const isEdit = ref(false)
const isEditId = ref(0)

let count = 0
const detailUser = async (id) => {
    if (count !== id) {
        LoadingAlert()
        getUser.value = await detail(id);
        getUser.value.createdOn = showTimeStampe(getUser.value.createdOn)
        getUser.value.updateOn = showTimeStampe(getUser.value.updateOn)
        count = id;
    }
    isDetail.value = isDetail.value === id ? -1 : id
    isEdit.value = false
}

const showTimeStampe = (datatime) => {
    return moment(datatime).local().format(DateFormat)
}

const page = ref(0)
const NextPage = async () => {
    if (page.value < 0) {
        page.value = 0
    }
    reset()
    getAllUser.value = await getUsers(page.value += 1)
}

const BackPage = async () => {
    if (page.value < 0) {
        page.value = 0
    }
    reset()
    getAllUser.value = await getUsers(page.value -= 1)
}

const del = async (user) => {
    try {
        const res = await deleteUser(user)
        if (res === 200) {
            accessAlert("Deleted.")
            getAllUser.value = await getUsers(page.value)
            reset()
        }
        else if (res > 400 && res < 500) {
            deniedAlert("delete", "User")
            getAllUser.value = await getUsers(page.value)
            reset()
        }
        else {
            ExceptionAlert(res.status)
        }
    } catch (error) {
        getAllUser.value = await getUsers(page.value)
        reset()
    }
}

let mailFormat1 = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/
let mailFormat2 = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
let mailFormat3 = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
let mailFormat4 = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
let mailFormat5 = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/

const isEmailNotFormat = ref(false)
const isNameEmpty = ref(false);
const isEmailEmpty = ref(false);
const isDuplicateName = ref(false)
const isDuplicateEmail = ref(false)
const isHaveRole = ref(true)

const EditName = ref("")
const EditEmail = ref("")
const EditRole = ref("")
const EditEvent = (user) => {
    isEdit.value = isEdit.value ? false : true
    if (isEdit.value) {
        isEditId.value = user.id
        EditName.value = user.name
        EditEmail.value = user.email
        EditRole.value = user.role
    }
    else {
        isEdit.value = false
        isEditId.value = 0
        EditName.value = getUser.value.name
        EditEmail.value = getUser.value.email
        EditRole.value = getUser.value.role
        isNameEmpty.value = false
        isDuplicateName.value = false
        isDuplicateEmail.value = false
        isEmailEmpty.value = false
    }
}
const isUserOld = computed(() => {
    return EditName.value !== getUser.value.name
        || EditEmail.value !== getUser.value.email
        || EditRole.value !== getUser.value.role
        ? false : true
});
const checkInfor = async (user) => {
    let isCheck = true;
    let getRole = ["STUDENT", "LECTURER", "ADMIN"]
    if (EditName.value === "") {
        isCheck = false
        isNameEmpty.value = true
    }
    props.getUsers.forEach((u) => {
        if (user.id !== u.id) {
            if (EditEmail.value === u.email) {
                isCheck = false
                isDuplicateEmail.value = true
            }
            if (EditName.value === u.name) {
                isCheck = false
                isDuplicateName.value = true
            }
        }
    })
    if (EditEmail.value === "") {
        isCheck = false
        isEmailEmpty.value = true
    }
    else if (!EditEmail.value.match(mailFormat5)) {
        if (!EditEmail.value.match(mailFormat4)) {
            if (!EditEmail.value.match(mailFormat3)) {
                if (!EditEmail.value.match(mailFormat2)) {
                    if (!EditEmail.value.match(mailFormat1)) {
                        isCheck = false
                        isEmailNotFormat.value = true
                        isEmailEmpty.value = false
                    }
                }
                isCheck = false
                isEmailNotFormat.value = true
                isEmailEmpty.value = false
            }
            isCheck = false
            isEmailNotFormat.value = true
            isEmailEmpty.value = false
        }
        isCheck = false
        isEmailNotFormat.value = true
        isEmailEmpty.value = false
    }
    else if (EditEmail.value.match(mailFormat5)) {
        if (EditEmail.value.match(mailFormat4)) {
            if (EditEmail.value.match(mailFormat3)) {
                if (EditEmail.value.match(mailFormat2)) {
                    if (EditEmail.value.match(mailFormat1)) {
                        isEmailNotFormat.value = false
                        isEmailEmpty.value = false
                    }
                }
                isEmailNotFormat.value = false
                isEmailEmpty.value = false
            }
            isEmailNotFormat.value = false
            isEmailEmpty.value = false
        }
        isEmailNotFormat.value = false
        isEmailEmpty.value = false
    }
    if (!getRole.includes(EditRole.value.trim())) {
        isCheck = false
        isHaveRole.value = false
        Swal.fire({
            icon: 'error',
            title: 'Role',
            text: 'Not Have this role.',
        })
        EditRole.value = "STUDENT"
    }
    if (isCheck) {
        isEmailEmpty.value = false
        isEmailNotFormat.value = false
        isNameEmpty.value = false
        isDuplicateName.value = false
        isDuplicateEmail.value = false
        if (await sureAlert()) {
            user.name = EditName.value
            user.email = EditEmail.value
            user.role = EditRole.value
            LoadingAlert()
            await saveUser(user)
            reset()
        }
    }
}

const saveUser = async (updateUser) => {
    const res = await save(updateUser)
    if (res === 200) {
        emits('save')
        getAllUser.value = await getUsers(page.value)
        reset()
    }
    else {
        getAllUser.value = await getUsers(page.value)
        reset()
    }
}

const reset = () => {
    isDetail.value = -1
    count = 0
    isEdit.value = false
    isEditId.value = 0
    EditName.value = ""
    EditEmail.value = ""
    EditRole.value = ""
    isDuplicateName.value = false
    isDuplicateEmail.value = false
}

onBeforeMount(async () => {
    if (props.role === 0) {
        getAllUser.value = await getUsers()
    }
})

const countName = computed(() => {
    return 100 - EditName.value.length
})

const countEmail = computed(() => {
    return 100 - EditEmail.value.length
})

const ced = " edit rounded-full px-2 text-white background-color: rgb(114, 143, 206) hover:bg-[#AECBFF]";
const ccl = " bg-red-600 rounded-full px-2 text-white hover:bg-[#F87171]";
const cdet = " bg-green-600 rounded-full px-2 text-white hover:bg-[#4ADE80]";
</script>
 
<template>
    <div class="font bgl rounded-xl px-10 mx-10 pt-7 pb-10">
        <div v-if="getAllUser.length !== 0">
            <ul>
                <li class="bgl2 my-2 pt-2 pb-4 px-9 rounded-md" v-for="(user, index) in getAllUser" :key="index">
                    <div class="flex justify-between">
                        <div>
                            <div>
                                <p class="text-3xl">{{ user.name }}</p>
                                <p class="text-[#5C5A5A] mt-1 mx-4 text-xl "><span class="text-black">E-mail :</span> {{
                                        user.email
                                }} <span class="text-black">Role :</span> {{ user.role }} </p>
                            </div>
                            <div class="flex cf">
                                <div class="mx-2 bg-green-600 hover:bg-green-400 rounded-xl text-s mt-4 mb-1">
                                    <button @click="detailUser(user.id)" :class="isDetail === user.id ? ccl : cdet">
                                        {{ isDetail === user.id ? "Closed" : "Detail" }}
                                    </button>
                                </div>
                            </div>
                        </div>
                        <div class="mr-5 mt-9">
                            <img @click="del(user)" src="../assets/trash.png"
                                class="del ring bg-[#FFFFFF] ring-[#FFFFFF] hover:bg-red-500 hover:ring-red-500 rounded-md cursor-pointer shadow-md hover:shadow-red-500">
                        </div>
                    </div>
                    <div v-if="isDetail === user.id" class="bgl3 px-5 pt-2 mt-2 pb-3 rounded-md text-lg">
                        <div>
                            <div class="flex">
                                <p class="pr-2">Name : </p>
                                <input v-if="isEdit && isEditId === user.id" type="text" v-model="EditName"
                                    @click="isNameEmpty = false, isDuplicateName = false"
                                    @keydown.backspace="isDuplicateName = false" class="px-1 rounded-sm" size="20" />
                                <p v-else class="text-[#535252]">{{ getUser.name }}</p>
                                <p v-if="isNameEmpty && countName === 100" class="text-xs text-red-600">*Plase Input
                                    your name*
                                </p>
                                <p v-else-if="isDuplicateName" class="text-xs text-red-600">*This Username is already
                                    use*</p>
                                <p v-show="isEdit" class="text-sm text-stone-500 mx-1 mt-1"> (Number of Character : {{
                                        countName
                                }})</p>
                            </div>
                            <div class="flex mt-2">
                                <p class="pr-2">E-mail : </p>
                                <input v-if="isEdit && isEditId === user.id" type="email" v-model="EditEmail"
                                    maxlength="100"
                                    @click="isEmailEmpty = false, isDuplicateEmail = false, isEmailNotFormat = false"
                                    @keydown.backspace="isDuplicateEmail = false, isEmailNotFormat = false"
                                    class="px-1 rounded-sm" size="25" />
                                <p v-else class="text-[#535252]">{{ getUser.email }}</p>
                                <p v-if="isEmailEmpty && countEmail === 100" class="text-xs text-red-600">*Plase Input
                                    your
                                    e-mail*</p>
                                <p v-else-if="isEmailNotFormat" class="text-xs text-red-600">Your Email address is not
                                    follow
                                    format</p>
                                <p v-else-if="isDuplicateEmail" class="text-xs text-red-600">*This Email is already use*
                                </p>
                                <p v-show="isEdit" class="text-sm text-stone-500 mx-1 mt-1">(Number of Character : {{
                                        countEmail
                                }})</p>
                            </div>
                            <div class="flex mt-2">
                                <p class="pr-2">Role : </p>
                                <select v-if="isEdit && isEditId === user.id" v-model="EditRole"
                                    class="ring-2 ring-offset-2 ring-black ml-2 mt-2 rounded-md">
                                    <option :value="'ADMIN'">ADMIN</option>
                                    <option :value="'LECTURER'">LECTURER</option>
                                    <option :value="'STUDENT'">STUDENT</option>
                                </select>
                                <p v-else class="text-[#535252]">{{ getUser.role }}</p>
                            </div>
                            <div class="flex mt-2">
                                <p class="pr-2">Created : </p>
                                <p class="text-[#535252]">{{ getUser.createdOn }}</p>
                            </div>
                            <div class="flex mt-2">
                                <p class="pr-2">Updated : </p>
                                <p class="text-[#535252]">{{ getUser.updateOn }}</p>
                            </div>
                        </div>
                        <div class="mt-3">
                            <button @click="checkInfor(user)" v-if="isEdit"
                                class="bg-green-600 rounded-full px-2 text-white mr-2 hover:bg-[#4ADE80] disabled:bg-[#8F9892]"
                                :disabled="isUserOld">Save
                            </button>
                            <button @click="EditEvent(user)" :class="isEdit ? ccl : ced">
                                {{ isEdit ? "Cancel" : "Edit" }}
                            </button>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <div v-else class="flex justify-center">
            No Users.
        </div>
        <div class="flex justify-center pb-5">
            <button v-if="page !== 0" @click="BackPage"
                class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">Back</button>
            <button v-if="getAllUser.length === 5" @click="NextPage"
                class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">Next</button>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
    font-family: 'Mitr', sans-serif;
}

.bgl3 {
    background-color: rgb(135, 206, 235);
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

.del {
    width: 2.5rem;
    height: 2.5rem;
    margin-top: 0.25rem;
}

.edit {
    background-color: rgb(114, 143, 206);
}

.bgde {
    background-color: rgb(254, 252, 255);
}

.bgl {
    background-color: rgb(92, 179, 255);
}

.bgl3 {
    background-color: rgb(130, 219, 248);
}

.btt {
    background-color: rgb(25, 25, 112);
}

.cf {
    color: rgb(251, 251, 249);
}

.cbts {
    background-color: rgb(0, 0, 128);
}

.cbtso {
    background-color: rgb(60, 60, 188);
}

.clear {
    background-color: rgb(88, 0, 88);
}
</style>