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
    let expire_refresh=localStorage.getItem("expire_refresh")
    let expires = new Date()
    expires.setMinutes(expires.getMinutes() + 1)
    localStorage.setItem("access_token", token.access_token)
    localStorage.setItem("expire_access",expires)
    expires =new Date()
    if(new Date(expire_refresh)<expires||expire_refresh===null){
        expires.setSeconds(expires.getSeconds() + 10)
        localStorage.setItem("refresh_token", token.refresh_token)
        localStorage.setItem("expire_refresh",expires)
    }
}