let mailFormat1 = /(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])/
let mailFormat2 = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
let mailFormat3 = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/
let mailFormat4 = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/
let mailFormat5 = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/



const checkEmail=(email)=>{

}
if (!user.email.match(mailFormat5)) {
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