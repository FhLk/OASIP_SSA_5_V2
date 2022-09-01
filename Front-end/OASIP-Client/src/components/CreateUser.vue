<script setup>
import { computed, onBeforeMount, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
const fetchUrl = import.meta.env.VITE_BASE_URL
const props = defineProps({
    getUsers: {
        type: Array,
        require: true
    }
})
const newUser = ref({
    name: "",
    email: "",
    password: "",
    confirm: "",
    role: "student"
})
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
const isPasswordEmpty = ref(false)
const isPasswordlength = ref(false)
const isConfirm = ref(false)
const isConfirmEmpty = ref(false)
const isMatch= ref(false)

const checkInfor = async (user) => {
    let isCheck = true;
    let getUserName = []
    let getUserEmail = []
    let getRole = ["student", "lecturer", "admin"]
    props.getUsers.forEach((user) => {
        getUserName.push(user.name.toLowerCase())
        getUserEmail.push(user.email.toLowerCase())
    })
    if (user.name === "") {
        isCheck = false
        isNameEmpty.value = true
    }
    else if (getUserName.includes(user.name.toLowerCase().trim())) {
        isCheck = false
        isDuplicateName.value = true
    }
    if (user.email === "") {
        isCheck = false
        isEmailEmpty.value = true
    }
    else if (getUserEmail.includes(user.email.toLowerCase().trim())) {
        isCheck = false
        isDuplicateEmail.value = true
    }
    else if (!user.email.match(mailFormat5)) {
        if (!user.email.match(mailFormat4)) {
            if (!user.email.match(mailFormat3)) {
                if (!user.email.match(mailFormat2)) {
                    if (!user.email.match(mailFormat1)) {
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
    else if (user.email.match(mailFormat5)) {
        if (user.email.match(mailFormat4)) {
            if (user.email.match(mailFormat3)) {
                if (user.email.match(mailFormat2)) {
                    if (user.email.match(mailFormat1)) {
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
    if (user.password === "") {
        isPasswordEmpty.value = true
    }
    else if (user.password.length < 8 || user.password.length > 14) {
        isCheck = false
        isPasswordlength.value = true
    }
    else if (user.password !== user.confirm) {
        isCheck = false
        isConfirm.value = true
    }
    else if(user.password===user.confirm && user.confirm.length !==0){
        isMatch.value=true
    }
    if (user.confirm === "") {
        isCheck = false
        isConfirmEmpty.value = true
    }
    if (!getRole.includes(user.role.toLowerCase().trim())) {
        isCheck = false
        isHaveRole.value = false
        alert("Not Have this role.")
        newUser.value.role = "student"
    }
    if (isCheck) {
        isEmailEmpty.value = false
        isEmailNotFormat.value = false
        isNameEmpty.value = false
        isDuplicateName.value = false
        isDuplicateEmail.value = false
        if (confirm("Are You sure ?")) {
            await createUser(user)
            reset()
        }
    }
}

const createUser = async (user) => {
    const res = await fetch(`${fetchUrl}/users`, {
        method: "POST",
        headers: {
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            name: user.name.trim(),
            email: user.email.trim(),
            password: user.password,
            role: user.role
        })
    })
    if (res.status === 201) {
        alert("You have a new User")
    }
}
const reset = () => {
    newUser.value = {
        name: "",
        email: "",
        password: "",
        confirm:"",
        role: "student"
    }
    isEmailEmpty.value = false
    isEmailNotFormat.value = false
    isNameEmpty.value = false
    isDuplicateName.value = false
    isDuplicateEmail.value = false
    GoUsers()
}

const myRouter = useRouter()
const GoUsers = () => {
    myRouter.push({ name: 'UserPage' })
}

const countName = computed(() => {
    return 100 - newUser.value.name.length
})

const countEmail = computed(() => {
    return 100 - newUser.value.email.length
})

// const isMatch =computed(()=>{
//     return newUser.value.password === newUser.value.confirm ? true:false
// })
</script>
 
<template>
    <div class="font ccf text-lg bg pb-96 ">
        <div class="flex justify-center">
            <div class="bgc px-10 py-3 pb-6 mt-10 rounded-lg">
                <div class="mr-2 mt-2">
                    <p>Username : <input type="text" placeholder="Name..." v-model="newUser.name" maxlength="100"
                            @click="isNameEmpty = false, isDuplicateName = false"
                            @keydown="isDuplicateName = false"
                            class="px-1 rounded-sm" />
                    </p>
                    <p class="text-sm text-stone-500">(Number of Character : {{ countName }})</p>
                    <p v-if="isNameEmpty" class="text-xs text-red-600">*Plase Input your name.*
                    </p>
                    <p v-else-if="isDuplicateName" class="text-xs text-red-600">*This Username is already use.*</p>
                </div>
                <div class="mr-2 mt-1">
                    <p>Password <span class="text-sm text-stone-500">
                            ({{ newUser.password.length }}) </span> :
                        <input type="password" placeholder="password" v-model="newUser.password" minlength="8"
                            @click="isPasswordEmpty = false, isPasswordlength = false"
                            @keydown="isPasswordlength = false" maxlength="50"
                            class="px-1 rounded-sm" />
                    </p>
                    <p v-if="isPasswordEmpty" class="text-xs text-red-600">*Plase Input your password.*</p>
                    <p v-else-if="isPasswordlength" class="text-xs text-red-600">*Password have length less/more than
                        required.*</p>
                    <p class="text-sm text-stone-500">(The password must be between 8-14 characters.)</p>
                </div>
                <div class="mr-2 mt-1">
                    <p>Confirm Password :
                         <input type="password" v-model="newUser.confirm" 
                         @click="isConfirm = false, isConfirmEmpty = false,isMatch=false"
                        @keydown="isConfirm = false,isMatch=false" 
                        class="px-1 rounded-sm" />
                    </p>
                    <p v-if="isConfirmEmpty" class="text-xs text-red-600">*Plase confirm your password.*</p>
                    <p v-else-if="isConfirm" class="text-xs text-red-600">*Password Not Match.*</p>
                    <p v-else-if="isMatch" class="text-xs text-green-600">Password is match.</p>
                </div>
                <div class="mr-2 mt-2">
                    <p>E-mail : <input type="email" placeholder="example@example.com" v-model="newUser.email"
                            maxlength="100"
                            @click="isEmailEmpty = false, isDuplicateEmail = false, isEmailNotFormat = false"
                            @keydown="isDuplicateEmail = false, isEmailNotFormat = false"
                            class="px-1 rounded-sm" /></p>
                    <p class="text-sm text-stone-500">(Number of Character : {{ countEmail }})</p>
                    <p v-if="isEmailEmpty" class="text-xs text-red-600">*Plase Input your
                        e-mail.*</p>
                    <p v-else-if="isEmailNotFormat" class="text-xs text-red-600">*Your Email address is not follow
                        format.*</p>
                    <p v-else-if="isDuplicateEmail" class="text-xs text-red-600">*This Email is already use.*</p>
                </div>
                <div class="mr-2 mt-1">
                    <p>Role:
                        <select v-model="newUser.role" class="ring-2 ring-offset-2 ring-black ml-2 mt-2 rounded-md">
                            <option :value="'admin'">ADMIN</option>
                            <option :value="'lecturer'">LECTURER</option>
                            <option :value="'student'">STUDENT</option>
                        </select>
                    </p>
                </div>
                <div class="mt-4">
                    <button class="bg-green-600 rounded-full px-2 text-white mx-1 hover:bg-[#4ADE80]"
                        @click="checkInfor(newUser)">OK</button>
                    <button class="bg-red-600 rounded-full px-2 text-white mx-1 hover:bg-[#F87171]"
                        @click="GoUsers()">Cancle</button>
                </div>
            </div>
        </div>
    </div>


</template>
 
<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Itim&family=Mali:wght@600&family=Mitr:wght@600;700&family=Titan+One&display=swap');

.font {
    font-family: 'Mitr', sans-serif;
}

.bgc {
    background-color: rgb(132, 212, 255);
}

.ccf {
    color: rgb(42, 39, 40);
}

.bg {
    background-color: rgb(255, 255, 247);
}
</style>