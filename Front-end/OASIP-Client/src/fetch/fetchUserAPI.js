import { setToken } from "../Store/local";
import { accessAlert, CloseAlert, delAlert, deniedAlert, ExceptionAlert, LoadingAlert } from "../Alert/alert.js";

const fetchUrl = import.meta.env.VITE_BASE_URL;

export const Match = async (log) => {
  try {
    const res = await fetch(`${fetchUrl}/match`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('access_token')}`,
        'content-type': 'application/json'
      },
      body: JSON.stringify({
        email: log.email.trim(),
        password: log.password
      })
    })
    return res.status;
  } catch (error) {
    ExceptionAlert("Failed")
    return 0
  }
}

export const checkAuthen = async (log) => {
  try {
    const res = await fetch(`${fetchUrl}/checkAuthen`, {
      method: "POST",
      headers: {
        'content-type': 'application/json'
      },
      body: JSON.stringify({
        email: log.email.trim(),
        password: log.password
      })
    })
    return res.status;
  } catch (error) {
    ExceptionAlert("Failed")
    return 0
  }
}

export const Authen = async (log) => {
  try {
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
      await reAuthen()
      return token.access_token
    }
    else {
      ExceptionAlert(res.status)
      return ""
    }
  } catch (error) {
    ExceptionAlert("Failed")
    return ""
  }
}

export const reAuthen = async () => {
  try {
    const res = await fetch(`${fetchUrl}/token/refresh`, {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('refresh_token')}`
      }
    })
    if (res.status === 200) {
      let newtoken = await res.json()
      setToken(newtoken)
    }
    else {
      ExceptionAlert(res.status)
    }
  } catch (error) {
    ExceptionAlert("Failed")
  }
}

export const AllUser = async () => {
  let all = []
  LoadingAlert()
  try {
    const res = await fetch(`${fetchUrl}/users/check`, {
      method: 'GET',
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('access_token')}`
      }
    })
    if (res.status === 200) {
      CloseAlert()
      return all = await res.json()
    }
    else {
      ExceptionAlert(res.status)
      return []
    }
  } catch (error) {
    ExceptionAlert("Failed")
    return []
  }
}

export const getUsers = async (page = 0) => {
  let users = []
  LoadingAlert()
  try {
    const res = await fetch(`${fetchUrl}/users?page=${page}`, {
      method: 'GET',
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('access_token')}`
      }
    })
    if (res.status === 200) {
      CloseAlert()
      users = await res.json()
      return users
    }
    else {
      ExceptionAlert(res.status)
      return []
    }
  } catch (error) {
    ExceptionAlert("Failed")
    return []
  }
}

export const deleteUser = async (user) => {
  try {
    if (await delAlert()) {
      const res = await fetch(`${fetchUrl}/users/${user.id}`, {
        method: 'DELETE',
        headers: {
          "Authorization": `Bearer ${localStorage.getItem('access_token')}`
        }
      })
      return res.status
    }
  }
  catch (error) {
    ExceptionAlert("Failed")
    return 0
  }
}

export const detail = async (id) => {
  try {
    const res = await fetch(`${fetchUrl}/users/${id}`, {
      method: 'GET',
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('access_token')}`
      }
    })
    if (res.status === 200) {
      CloseAlert()
      let user = await res.json()
      return user
    }
    else {
      ExceptionAlert(res.status)
      return {}
    }
  } catch (error) {
    ExceptionAlert("Failed")
    return {}
  }
}

export const save = async (updateUser) => {
  try {
    const res = await fetch(`${fetchUrl}/users/${updateUser.id}`, {
      method: "PUT",
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('access_token')}`,
        'content-type': 'application/json'
      },
      body: JSON.stringify({
        name: updateUser.name.trim(),
        email: updateUser.email.trim(),
        role: updateUser.role
      })
    })
    if (res.status === 200) {
      await accessAlert("Updated")
      return 200
    }
    else if (res.status > 400 && res.status < 500) {
      await deniedAlert("change", "Uses")
      return res.status
    }
    else {
      ExceptionAlert(res.status)
      return res.status
    }
  } catch (error) {
    ExceptionAlert("Failed")
    return 0
  }
}

export const create = async (user) => {
  try {
    const res = await fetch(`${fetchUrl}/users`, {
      method: "POST",
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('access_token')}`,
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
      accessAlert("Created")
      return 201
    }
    else if (res.status > 400 && res.status < 500) {
      deniedAlert("create", "User")
      return res.status
    }
    else {
      ExceptionAlert(res.status)
      return res.status
    }
  } catch (error) {
    ExceptionAlert("Failed")
    return 0
  }
}

