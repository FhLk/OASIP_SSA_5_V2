import moment from "moment"
import { useRoute, useRouter } from 'vue-router';
export const checkToken = () => {
    if (localStorage.getItem("access_token") === null) {
        return false;
    } else {
        // if(expiresToken()){
        //     return false
        // }
        return true;
    }
}

export const expiresToken=()=>{
    let expire_access=localStorage.getItem("expire_access")
    let expire_refresh=localStorage.getItem("expire_refresh")
    if(expire_refresh===null || expire_refresh===undefined ){
        return true
    }
    else if(new Date(expire_refresh) < new Date()){
        localStorage.clear()
        alert("Time Out.")
        return true
    }
    else{
        // if(new Date(expire_access)< new Date()){

        // }
    }
}

export const setToken = (token) => {
    let expires = new Date()
    expires.setMinutes(expires.getMinutes() + 30)
    localStorage.setItem("access_token", token.access_token)
    localStorage.setItem("expire_access",expires)
    expires =new Date()
    expires.setDate(expires.getDate() + 1)
    localStorage.setItem("refresh_token", token.refresh_token)
    localStorage.setItem("expire_refresh",expires)
}