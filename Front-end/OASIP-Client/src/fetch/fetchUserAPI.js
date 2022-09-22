import { setToken } from "../Store/local";

const fetchUrl = import.meta.env.VITE_BASE_URL;

export const Match= async (log)=>{
  const res = await fetch(`${fetchUrl}/match`,{
    method:"POST",
    headers:{
      'content-type':'application/json'
    },
    body: JSON.stringify({
      email: log.email.trim(),
      password: log.password
    })
  })
  return res.status;
}

export const Authen = async (log) => {
  const res = await fetch(`${fetchUrl}/login`, {
    method: "POST",
    headers: {
      'content-type': 'application/x-www-form-urlencoded'
    },
    body: `email=${log.email.trim()}&password=${log.password}`
  })
  if (res.status === 200) {
    let token = await res.json()
    setToken(token)
    return token.access_token
  }
  else {
    alert("Can't Authentication")
  }
  return 
}

export const AllUser = async () => {
  let all=[]
  const res = await fetch(`${fetchUrl}/users/check`, {
      method: 'GET',
      headers: {
        // "Authorization": `Bearer ${localStorage.getItem('token')}`
      }
  })
  if (res.status === 200) {
      return all = await res.json()
  }
}

export const getUsers = async (page = 0) => {
  let users=[]
  const res = await fetch(`${fetchUrl}/users?page=${page}`, {
      method: 'GET',
      headers: {
          // "Authorization": `Bearer ${localStorage.getItem('token')}`
      }
  })
  if (res.status === 200) {
      users = await res.json()
  }
  else {
      users = []
  }
  return users
}

export const deleteUser = async (user) => {
  if (confirm("Are you sure?")) {
      const res = await fetch(`${fetchUrl}/users/${user.id}`, {
          method: 'DELETE',
          headers: {
              // "Authorization": `Bearer ${localStorage.getItem('token')}`
          }
      })
      if (res.status === 200) {
        return 200
      }
      else {
        alert("Can't Delete this Booking")
      }
      return 
  }
}

