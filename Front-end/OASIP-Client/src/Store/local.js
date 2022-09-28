import jwt_decode from "jwt-decode";
export const checkToken = () => {
    if (localStorage.getItem("access_token") === null) {
        return false;
    } else {
        if(expiresToken()){
            return false
        }
        return true;
    }
}

export const checkRole=(role)=>{
    if(role==="ADMIN"){
        return 0
    }
    else if(role==="LECTURER"){
        return 1
    }
    else if(role==="STUDENT"){
        return 2
    }
    else{
        return -1
    }
}

export const expiresToken=()=>{
    let expire_refresh=localStorage.getItem("expire_refresh")
    if(expire_refresh===null){
        return false
    }
    else if(new Date(expire_refresh) < new Date()){
        localStorage.clear()
        return true
    }
    else{
        return false
    }
}

export const expiresAccess=()=>{
    let expire_access=localStorage.getItem("expire_access")
    if(expire_access===null){
        return false
    }
    if(new Date(expire_access) < new Date()){
        return true
    }
    else{
        return false
    }
}

export const setToken = (token) => {
    deCodeJWT(token)
    let expire_refresh=localStorage.getItem("expire_refresh")
    let expires = new Date()
    expires.setMinutes(expires.getMinutes() + 1)
    localStorage.setItem("access_token", token.access_token)
    localStorage.setItem("expire_access",expires)
    expires =new Date()
    if(new Date(expire_refresh)<expires||expire_refresh===null){
        expires.setDate(expires.getDate() + 1)
        localStorage.setItem("refresh_token", token.refresh_token)
        localStorage.setItem("expire_refresh",expires)
    }
}

const deCodeJWT=(token)=>{
    let deCodeToken=jwt_decode(token.access_token)
    let role=deCodeToken.roles[0]
    localStorage.setItem("role",role)
}