<script setup>
import { onBeforeMount, ref } from 'vue';
import moment from "moment"
import { EventPast, EventCategory, EventDay, EventDelete, EventDetail, EventSave, Events } from '../fetch/fetchEventAPI';
import { delAlert, sureAlert, deniedAlert, accessAlert, LoadingAlert, ExceptionAlert } from '../Alert/alert';
import { checkRole } from '../Store/local';
const fetchUrl = import.meta.env.VITE_BASE_URL
let DateFormat = "YYYY-MM-DD HH:mm"

const getBooking = ref({});
const isDetail = ref(-1)
const isEdit = ref(false)
const isEditId = ref(0)
const isSortByPast = ref(false)
const isSortByDate = ref(false)
const isSortByCategory = ref(false)
const isClear = ref(true)
const sortDay = ref(moment().local().format(DateFormat).slice(0, 10).trim())
const categoryID = ref(1)
const role = ref(-1)
const isDenide = ref(false)
const isOwner = ref(false)

const getListBooking = ref([])
const Page = async (page = 0) => {
    LoadingAlert()
    if (page >= 0) {
        if (isSortByPast.value) {
            getListBooking.value = await EventPast(page)
        }
        else if (isSortByCategory.value) {
            getListBooking.value = await EventCategory(categoryID.value, page)
        }
        else if (isSortByDate.value) {
            getListBooking.value = await EventDay(sortDay.value, page)
        }
        else {
            getListBooking.value = await Events(page)
        }
        getListBooking.value.forEach((data) => {
            data.startTime = ShowDateTime(data.startTime)
        })
        if (isSortByDate.value) {
            getListBooking.value = SortByDateTimeASC(getListBooking.value)
        }
        else {
            getListBooking.value = SortByDateTimeDESC(getListBooking.value)
        }
    }
}

const page = ref(0)
const NextPage = () => {
    if (page.value < 0) {
        page.value = 0
    }
    reset()
    Page(page.value += 1)
}

const BackPage = () => {
    if (page.value < 0) {
        page.value = 0
    }
    reset()
    Page(page.value -= 1)
}

const ShowDateTime = (datatime) => {
    return moment(datatime).local().format(DateFormat)
}

const SortByDateTimeDESC = (list) => {
    return list.sort((a, b) => {
        return new Date(b.startTime) - new Date(a.startTime)
    })
}

const SortByDateTimeASC = (list) => {
    return list.sort((a, b) => {
        return new Date(a.startTime) - new Date(b.startTime)
    })
}

onBeforeMount(async () => {
    role.value = checkRole(localStorage.getItem("role"))
    if (role.value === 1) {
        isDenide.value = true
        isOwner.value = true
    }
    await Page()
})

let count = 0
const showDetail = async (id) => {
    if (id !== count) {
        LoadingAlert()
        getBooking.value = await EventDetail(id)
        getBooking.value.startTime = ShowDateTime(getBooking.value.startTime)
        count = id
    }
    isDetail.value = isDetail.value === id ? -1 : id
    isEdit.value = false
}


const EditNote = ref("")
const EditDate = ref("")
const EditTime = ref("")
const EditDateTime = ref("")
const EditEvent = (booking) => {
    isEdit.value = isEdit.value ? false : true
    if (isEdit.value) {
        isEditId.value = booking.id
        EditNote.value = booking.eventNote
        EditDateTime.value = booking.startTime
        EditDate.value = EditDateTime.value.slice(0, 10)
        EditTime.value = EditDateTime.value.slice(10).trim()
    }
    else {
        isEdit.value = false
        isEditId.value = 0
        EditNote.value = getBooking.value.eventNote
        EditDateTime.value = getBooking.value.startTime
        EditDate.value = ""
        EditTime.value = ""
    }
}

const reset = () => {
    isOwner.value = false
    isEditId.value = 0
    isDetail.value = -1
    count = 0
    isEdit.value = false
    EditDate.value = ""
    EditNote.value = ""
    isEdit.value = false
}

const savebooking = async (updateBooking) => {
    if (moment(updateBooking.startTime).local().format(DateFormat) <= sortDay.value) {
        await Page(page.value)
        deniedAlert("change", "Booking in past.")
        reset()
    }
    else if (await sureAlert()) {
        updateBooking.startTime = `${EditDate.value}T${EditTime.value}`
        updateBooking.eventNote = EditNote.value
        try {
            LoadingAlert()
            const res = await EventSave(updateBooking)
            if (res === 200) {
                await accessAlert("Updated")
                Page(page.value)
                reset()
            }
            else if(res>400 && res< 500){
                reset()
                await deniedAlert("change", "Booking")
            }
            else {
                ExceptionAlert(res)
            }
        } catch (error) {
            await deniedAlert("change", "Booking")
            reset()
        }
    }
}

const deleteBooking = async (id) => {
    if (await delAlert()) {
        LoadingAlert()
        try {
            const res = await EventDelete(id)
            if (res === 200) {
                await accessAlert("Delete")
                await Page(page.value)
                reset()
            }
            else if(res>400 && res <500) {
                await deniedAlert("delete", "Booking")
                reset()
            }
            else {
                ExceptionAlert(res)
            }
        } catch (error) {
            await deniedAlert("delete", "Booking")
            reset()
        }
    }
}

const ced = " edit rounded-full px-2 text-white background-color: rgb(114, 143, 206) hover:bg-[#AECBFF]";
const ccl = " bg-red-600 rounded-full px-2 text-white hover:bg-[#F87171]";
const cdet = " bg-green-600 rounded-full px-2 text-white hover:bg-[#4ADE80]";
const note = " bgde px-1 mx-1 rounded-md ";
const nonote = "";

const SortByPast = async () => {
    LoadingAlert()
    page.value = 0
    isClear.value = false
    isSortByCategory.value = false
    isSortByDate.value = false
    categoryID.value = 1
    if (isSortByPast.value === false) {
        isSortByPast.value = true
        getListBooking.value = await EventPast()
        getListBooking.value.forEach((data) => {
            data.startTime = ShowDateTime(data.startTime)
        })
        getListBooking.value = SortByDateTimeDESC(getListBooking.value)
    }
}

const isSortDate = () => {
    reset()
    isSortByDate.value = true
    isSortByPast.value = false
    isSortByCategory.value = false
    isClear.value = false
    categoryID.value = 1
    SortByDate()
}

const SortByDate = async (StartDate = sortDay.value) => {
    LoadingAlert()
    page.value = 0
    if (isSortByDate.value) {
        getListBooking.value = await EventDay(StartDate)
        getListBooking.value.forEach((data) => {
            data.startTime = ShowDateTime(data.startTime)
        })
        getListBooking.value = SortByDateTimeASC(getListBooking.value)
    }
}

const isSortCategory = () => {
    LoadingAlert()
    reset()
    isSortByCategory.value = true
    isSortByDate.value = false
    isSortByPast.value = false
    isClear.value = false
    sortDay.value = moment().local().format(DateFormat).slice(0, 10).trim()
    SortByCategory()
}

const SortByCategory = async (id = 1) => {
    LoadingAlert()
    isOwner.value = true
    page.value = 0
    if (isSortByCategory.value) {
        getListBooking.value = await EventCategory(id)
        if (getListBooking.value === 403) {
            isDenide.value = true
            isOwner.value = false
            getListBooking.value = []
        }
        getListBooking.value.forEach((data) => {
            data.startTime = ShowDateTime(data.startTime)
        })
        getListBooking.value = SortByDateTimeDESC(getListBooking.value)

    }
}

const GetAll = () => {
    reset()
    page.value = 0
    isSortByCategory.value = false
    isSortByDate.value = false
    isSortByPast.value = false
    isClear.value = true
    Page()
}

const btso1 = "cbts rounded-md px-2 text-white hover:bg-[#5050D0] mx-2";
const btso2 = "cbtso rounded-md px-2 text-white hover:bg-[#5050D0] mx-2";

</script>
 
<template>
    <div class="font ccf pt-3 rounded-md mx-10 mb-4 pb-3 bgl text-lg">
        <div class="bg-white flex py-2 justify-between">
            <p class="ml-5">Sort By : </p>
            <div :class="isSortByPast ? '' : 'text-white'">
                <span v-if="isSortByPast">{{ `> >` }}</span>
                <button @click="SortByPast" :class="isSortByPast ? btso2 : btso1" :disabled="isSortByPast">Past</button>
                <span v-if="isSortByPast">{{ `< <` }}</span>
            </div>
            <div :class="isSortByCategory ? '' : 'text-white'">
                <span v-if="isSortByCategory">{{ `> >` }}</span>
                <button @click="isSortCategory" :class="isSortByCategory ? btso2 : btso1"
                    :disabled="isSortByCategory">Category</button>
                <span v-if="isSortByCategory">{{ `< <` }}</span>
            </div>
            <div :class="isSortByDate ? '' : 'text-white'">
                <span v-if="isSortByDate">{{ `> >` }}</span>
                <button @click="isSortDate" :class="isSortByDate ? btso2 : btso1" :disabled="isSortByDate">Day</button>
                <span v-if="isSortByDate">{{ `< <` }}</span>
            </div>
            <div :class="isClear ? '' : 'text-white'">
                <span v-if="isClear">{{ `> >` }}</span>
                <button @click="GetAll" class="clear rounded-md px-2 text-white hover:bg-[#763276] mx-2"
                    :disabled="isClear">All</button>
                <span v-if="isClear">{{ `< <` }}</span>
            </div>
        </div>
        <div v-if="!isSortByDate === false">
            <input type="date" v-model="sortDay" @change="SortByDate(sortDay)"
                class="ring-2 ring-offset-2 ring-black ml-2 mt-2 rounded-md" />
        </div>
        <div v-if="!isSortByCategory === false">
            <select v-model="categoryID" @change="SortByCategory(categoryID)"
                class="ring-2 ring-offset-2 ring-black ml-2 mt-2 rounded-md">
                <option :value="1">Project Management</option>
                <option :value="2">DevOps/Infra</option>
                <option :value="3">Database</option>
                <option :value="4">Client-side</option>
                <option :value="5">Server-side</option>
            </select>
        </div>
        <div v-if="getListBooking.length !== 0" class="mt-4 pd-10">
            <ul v-if="isSortByDate === false">
                <li v-for="(data, index) in getListBooking" :key="index" class="bgl2 mb-5 px-8 mx-5  rounded-lg pt-2">
                    {{ data.startTime }}
                    ({{ data.bookingDuration }} mins.) {{ data.category.categoryName.toLocaleUpperCase() }}
                    {{ data.bookingName }}
                    <div>
                        <div class="flex justify-between mt-1">
                            <div>
                                <button @click="showDetail(data.id)" :class="isDetail === data.id ? ccl : cdet"
                                    class="mt-4">{{ isDetail ===
                                            data.id ? "Closed" : "Detail"
                                    }}</button>
                            </div>
                            <div v-if="!isDenide">
                                <img @click="deleteBooking(data.id)" src="../assets/trash.png"
                                    class="del ring bg-[#FFFFFF] ring-[#FFFFFF] hover:bg-red-500 hover:ring-red-500 rounded-md cursor-pointer shadow-md hover:shadow-red-500">
                            </div>
                        </div>
                        <div v-if="isDetail === data.id" class="bgl3 px-5 pt-2 mt-2 pb-3 rounded-md">
                            <div>
                                <div class="flex">
                                    <p class="pr-2">Name : </p>
                                    <p>{{ getBooking.bookingName }}</p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">E-mail : </p>
                                    <p>{{ getBooking.bookingEmail }}</p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">Category : </p>
                                    <p>{{ getBooking.category.categoryName }}</p>
                                </div>
                                <div class="flex">
                                    <p>Date & Time :
                                        <span v-if="isEdit && isEditId === data.id" class="pl-2">
                                            <input type="date" v-model="EditDate"
                                                :min="new Date().toISOString().split('T')[0]" class="px-1 rounded-sm" />
                                            |
                                            <input type="time" v-model="EditTime" class="px-1 pl-1 rounded-sm" />
                                        </span>
                                        <span v-else>
                                            {{ getBooking.startTime }}
                                        </span>
                                    </p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">Duration : </p>
                                    <p>{{ getBooking.bookingDuration }} mins.</p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">Note :</p>
                                    <span v-if="isEdit && isEditId === data.id"
                                        :class="getBooking.eventNote ? note : nonote">
                                        <textarea rows="5" cols="50" v-model="EditNote" maxlength="500"
                                            class=" px-1 rounded-sm"></textarea>
                                    </span>
                                    <span v-else>
                                        <div>
                                            {{ getBooking.eventNote }}
                                        </div>
                                    </span>
                                </div>
                            </div>
                            <div class="mt-2" v-if="!isDenide">
                                <button @click="savebooking(data)" v-if="isEdit"
                                    class="bg-green-600 rounded-full px-2 text-white mr-2 hover:bg-[#4ADE80]">Save</button>
                                <button @click="EditEvent(data)" :class="isEdit ? ccl : ced">{{ isEdit ?
                                        "Cancel" :
                                        "Edit"
                                }}</button>
                            </div>
                        </div>

                    </div>
                    <br />
                </li>
            </ul>
            <ul v-else>
                <li v-for="(data, index) in getListBooking" :key="index" class="bgl2 mb-5 px-8 mx-5  rounded-lg pt-2">
                    {{ data.startTime }}
                    ({{ data.bookingDuration }} mins.)
                    {{ data.bookingName }}
                    <div>
                        <div class="flex justify-between mt-1">
                            <div>
                                <button @click="showDetail(data.id)" :class="isDetail === data.id ? ccl : cdet"
                                    class="mt-4">{{ isDetail ===
                                            data.id ? "Closed" : "Detail"
                                    }}</button>
                            </div>
                            <div v-if="!isDenide">
                                <img @click="deleteBooking(data)" src="../assets/trash.png"
                                    class="del ring bg-[#FFFFFF] ring-[#FFFFFF] hover:bg-red-500 hover:ring-red-500 rounded-md cursor-pointer shadow-md hover:shadow-red-500">
                            </div>
                        </div>
                        <div v-if="isDetail === data.id" class="bgl3 px-5 pt-2 mt-2 pb-3 rounded-md">
                            <div>
                                <div class="flex">
                                    <p class="pr-2">Name : </p>
                                    <p>{{ getBooking.bookingName }}</p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">E-mail : </p>
                                    <p>{{ getBooking.bookingEmail }}</p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">Category : </p>
                                    <p>{{ getBooking.category.categoryName }}</p>
                                </div>
                                <div class="flex">
                                    <p>Date & Time :
                                        <span v-if="isEdit && isEditId === data.id" class="pl-2">
                                            <input type="date" v-model="EditDate" /> |
                                            <input type="time" v-model="EditTime" />
                                        </span>
                                        <span v-else>
                                            {{ getBooking.startTime }}
                                        </span>
                                    </p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">Duration : </p>
                                    <p>{{ getBooking.bookingDuration }} mins.</p>
                                </div>
                                <div class="flex">
                                    <p class="pr-2">Note :</p>
                                    <span v-if="isEdit && isEditId === data.id"
                                        :class="getBooking.eventNote ? note : nonote">
                                        <textarea rows="5" cols="50" v-model="EditNote" maxlength="500"></textarea>
                                    </span>
                                    <span v-else>
                                        <div>
                                            {{ getBooking.eventNote }}
                                        </div>
                                    </span>
                                </div>
                            </div>
                            <div class="mt-2" v-if="!isDenide">
                                <button @click="savebooking(data)" v-if="isEdit"
                                    class="bg-green-600 rounded-full px-2 text-white mr-2 hover:bg-[#4ADE80]">Save</button>
                                <button @click="EditEvent(data)" :class="isEdit ? ccl : ced">{{ isEdit ?
                                        "Cancel" :
                                        "Edit"
                                }}</button>
                            </div>
                        </div>

                    </div>
                    <br />
                </li>
            </ul>
        </div>
        <div v-else-if="isOwner === false && role === 1 && isSortByCategory" class="flex justify-center">
            <h2>You are not Lecturer of this Clinic.</h2>
        </div>
        <div v-else class="flex justify-center">
            <h2>No Scheduled Events.</h2>
        </div>
        <div class="flex justify-center pb-5">
            <button v-if="page !== 0" @click="BackPage"
                class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">Back</button>
            <button v-if="getListBooking.length === 5" @click="NextPage"
                class="mx-10 px-4 py-2 btt cf hover:bg-[#5555AC] rounded-md">Next</button>
        </div>
    </div>
</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
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
    background-color: rgb(135, 206, 235);
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