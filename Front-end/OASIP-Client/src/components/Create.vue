<script setup>
import moment from 'moment';
import { computed, onBeforeMount, onUpdated, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { accessAlert, deniedAlert, ExceptionAlert, LoadingAlert, sureAlert } from '../Alert/alert';
import { checkRole, checkToken } from '../Store/local';
import { createByRole } from '../fetch/fetchEventAPI.js'
const getFile=ref([])
let mailFormat1 = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/
let mailFormat2 = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
let mailFormat3 = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
let mailFormat4 = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
let mailFormat5 = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/

const isEmailNotFormat = ref(false)
const isNameEmpty = ref(false);
const isEmailEmpty = ref(false);
const isCategoryEmpty = ref(false);
const isDateEmpty = ref(false);
const isTimeEmpty = ref(false);
const isDatePast = ref(false);
let { params } = useRoute()
const isCategory = ref(params.category);
let DateFormat = "YYYY-MM-DD HH:mm"

const emits = defineEmits(['add'])

const props = defineProps({
    getCategories: Array,
    role: Number
})

const myRouter = useRouter()
const GoHome = () => {
    myRouter.push({ name: 'indexPage' })
}

const newbooking = ref({
    bookingName: "",
    bookingEmail: "",
    Date: "",
    Time: "",
    category: {},
    eventNote: "",
    bookingDuration: 0
});

const isInfor = computed(() => {
    return newbooking.value.bookingName === "" ||
        newbooking.value.bookingEmail === "" ||
        newbooking.value.Date === "" ||
        newbooking.value.Time === "" ||
        Object.keys(newbooking.value.category).length === 0 ||
        newbooking.value.bookingDuration === 0 ? true : false
})

const reset = () => {
    newbooking.value = {
        bookingName: "",
        bookingEmail: "",
        Date: "",
        Time: "",
        category: {},
        eventNote: "",
        bookingDuration: 0
    }
}

const CheckInput = async (booking) => {
    let isCheck = true
    if (booking.bookingName === "") {
        isCheck = false
        isNameEmpty.value = true
    }
    if (booking.bookingEmail === "") {
        isCheck = false
        isEmailEmpty.value = true
    }
    else if (!booking.bookingEmail.match(mailFormat5)) {
        if (!booking.bookingEmail.match(mailFormat4)) {
            if (!booking.bookingEmail.match(mailFormat3)) {
                if (!booking.bookingEmail.match(mailFormat2)) {
                    if (!booking.bookingEmail.match(mailFormat1)) {
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
    else if (booking.bookingEmail.match(mailFormat5)) {
        if (booking.bookingEmail.match(mailFormat4)) {
            if (booking.bookingEmail.match(mailFormat3)) {
                if (booking.bookingEmail.match(mailFormat2)) {
                    if (booking.bookingEmail.match(mailFormat1)) {
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
    if (Object.keys(booking.category).length === 0) {
        isCheck = false
        isCategoryEmpty.value = true
    }
    if (booking.Date === "") {
        isCheck = false
        isDateEmpty.value = true
    }
    else if (booking.Date !== "") {
        isDateEmpty.value = false
        isTimeEmpty.value = true
        let getDate = moment(`${booking.Date} ${booking.Time}`).local().format(DateFormat);
        let now = moment().local().format(DateFormat)
        if (getDate <= now) {
            isDateEmpty.value = false
            isDatePast.value = true
            isCheck = false
        }
        else {
            isDatePast.value = false
        }
    }
    if (booking.Time === "") {
        isCheck = false
        isTimeEmpty.value = true
    }
    if (isCheck) {
        isEmailEmpty.value = false
        isDatePast.value = false
        isEmailNotFormat.value = false
        isNameEmpty.value = false
        isCategoryEmpty.value = false
        isDateEmpty.value = false
        isTimeEmpty.value = false
        if (await sureAlert()) {
            LoadingAlert()
            await createBooking(booking)
        }
    }
}



const createBooking = async (booking) => {
    let res = await createByRole(booking,getFile.value);
    if (res === 201) {
        accessAlert("Created")
        reset()
        GoHome()
    }
    else if (res >= 400 && res <= 500) {
        await deniedAlert("create", "Booking")
    }
    else {
        ExceptionAlert(res)
    }
}

const countName = computed(() => {
    return 100 - newbooking.value.bookingName.length
})

const countNote = computed(() => {
    return 500 - newbooking.value.eventNote.length
})

const countEmail = computed(() => {
    return 100 - newbooking.value.bookingEmail.length
})

const isLogin = ref(false)
onBeforeMount(async () => {
    if (checkRole(localStorage.getItem("role")) === 2) {
        isLogin.value = true
        newbooking.value.bookingName = localStorage.getItem("name")
        newbooking.value.bookingEmail = localStorage.getItem("email")
    }
})
onUpdated(async () => {
    if (isCategory.value !== undefined) {
        newbooking.value.category = await props.getCategories[Number(isCategory.value) - 1]
    }
})

</script>
 
<template>
    <div class="font ccf text-lg pt-28 mb-24">
        <button class="font btnindex hover:bg-[#00A1E1] rounded-md px-1 h-8 cf mx-14">
            <router-link to="/">{{ `<< Back` }}</router-link>
        </button>
        <div v-if="getCategories.length !== 0">
            <h1 class="font text-5xl flex justify-center">Add New Schedule</h1>
            <div class="flex justify-center">
                <div class="bgc px-10 py-3 rounded-lg mt-1">
                    <div class="flex">
                        <div class="ml-3">
                            <div class="mr-2 mt-2">
                                <p>Full Name : <input type="text" placeholder="Name..." v-model="newbooking.bookingName"
                                        maxlength="100" class="px-1 rounded-sm" @click="isNameEmpty = false"></p>
                                <p class="text-sm text-stone-500">(Number of Character : {{ countName }})</p>
                                <p v-if="isNameEmpty && countName === 100" class="text-xs text-red-600">Plase Input your
                                    name
                                </p>
                            </div>
                            <div class="mr-2 mt-1">
                                <p>E-mail : <input type="email" placeholder="example@example.com"
                                        v-model="newbooking.bookingEmail" maxlength="100" class="px-1 rounded-sm"
                                        :disabled="isLogin" @click="isEmailEmpty = false, isEmailNotFormat = false"></p>
                                <p class="text-sm text-stone-500">(Number of Character : {{ countEmail }})</p>
                                <p v-if="isEmailEmpty && countEmail === 100" class="text-xs text-red-600">Plase Input
                                    your
                                    e-mail
                                </p>
                                <p v-else-if="isEmailNotFormat" class="text-xs text-red-600">Your Email address is not
                                    follow
                                    format</p>
                            </div>
                            <div class="mt-2">
                                <label>Date : </label>
                                <input type="date" v-model="newbooking.Date"
                                    :min="new Date().toISOString().split('T')[0]" class="px-1 rounded-sm"
                                    @click="isDateEmpty = false,isDatePast=false">
                                <p v-if="isDateEmpty && newbooking.Date === ''" class="text-xs text-red-600">Plase Input
                                    your
                                    date.</p>
                                <p v-else-if="isDatePast" class="text-xs text-red-600">Can't choose Date in Past or
                                    Present
                                </p>
                            </div>
                            <div class="mt-2">
                                <label> Start (Time) : </label>
                                <input type="time" v-model="newbooking.Time" class="px-1 pl-1 rounded-sm"
                                    @click="isTimeEmpty = false">
                                <p v-if="isTimeEmpty && newbooking.Time === ''" class="text-xs text-red-600">Plase Input
                                    your
                                    time
                                </p>
                            </div>
                        </div>
                        <div class="ml-4">
                            <div>
                                <p class="mr-2 mt-1">Category : </p>
                                <ul v-for="(category, index) in getCategories " :key="index">
                                    <input type="radio" :id="index" :value="category" v-model="newbooking.category"
                                        @click="isCategoryEmpty = false" :checked="category.id === isCategory"
                                        :disabled="isCategory">
                                    - <label :for="index">{{ category.categoryName }}</label>
                                </ul>
                                <p v-if="isCategoryEmpty && Object.keys(newbooking.category).length === 0"
                                    class="text-xs text-red-600">Plase select category</p>
                            </div>
                            <div class="mt-2">
                                <label class="mr-2 mt-5">Duration (Minute) : {{ newbooking.bookingDuration =
                                        newbooking.category.duration === undefined ? 0 : newbooking.category.duration
                                }}</label>
                            </div>
                        </div>
                    </div>
                    <div class="ml-3.5">
                        <p class="mr-2 mt-2">Note : </p>
                        <textarea rows="5" cols="55" v-model="newbooking.eventNote" maxlength="500"
                            class="px-1 rounded-sm"></textarea>
                        <p class="text-sm text-stone-500">(Number of Character : {{ countNote }})</p>
                    </div>
                </div>
            </div>
            <div class="mt-3 flex justify-center">
                <button @click="CheckInput(newbooking)"
                    class="bg-green-600 rounded-full px-2 text-white mx-1 hover:bg-[#4ADE80] disabled:bg-[#999999]"
                    :disabled="isInfor">OK</button>
                <button @click="reset, GoHome()"
                    class="bg-red-600 rounded-full px-2 text-white mx-1 hover:bg-[#F87171]">Cancle</button>
            </div>
        </div>
        <div v-else>
            <div class="font flex justify-center">
                <h1 class="text-5xl mb-5">Not Available Now</h1>
            </div>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.btnindex {
    background-color: rgb(0, 191, 255);
}

.font {
    font-family: 'Mitr', sans-serif;
}

.bgc {
    background-color: rgb(132, 212, 255);
}

.ccf {
    color: rgb(42, 39, 40);
}

.cf {
    color: rgb(248 250 252);
}
</style>