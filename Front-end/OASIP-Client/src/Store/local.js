import moment from "moment"
export const checkToken = () => {
    let getExpires=document.cookie.split("; ")
    if (localStorage.getItem("access_token") === null) {
        return false;
    } else {
        return true;
    }
};

export const setToken = (token) => {
    let expires = new Date()
    let accessTime=expires.setMinutes(expires.getMinutes()+30)
    let refreshTime=expires.setHours(expires.getHours()+24)
    localStorage.setItem("access_token", token.access_token)
    document.cookie = `access_token=${token.access_token}; expires=${accessTime}`
    localStorage.setItem("refresh_token", token.refresh_token)
    document.cookie = `refresh_token=${token.refresh_token}; expires=${refreshTime}` 
}