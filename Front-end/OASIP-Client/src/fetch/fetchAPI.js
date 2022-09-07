const fetchUrl = import.meta.env.VITE_BASE_URL;


export const logIn = async (log) => {
  const res = await fetch(`${fetchUrl}/login`, {
    method: "POST",
    headers: {
      'content-type': 'application/json'
    },
    body: JSON.stringify({
      username: log.email.trim(),
      password: log.password
    })
  })
  if (res.status === 200) {
    let token = await res.json()
    localStorage.setItem("token", token.token)
  }
  else {
    alert("Can't Authentication")
  }
  return res.status
}

export const AllUser = async () => {
  let all=[]
  const res = await fetch(`${fetchUrl}/users/check`, {
      method: 'GET'
  })
  if (res.status === 200) {
      return all = await res.json()
  }
}

// export const getUsers = async (page = 0) => {
//   let users=[];
//   const res = await fetch(`${fetchUrl}/users?page=${page}`, {
//       method: 'GET'
//   })
//   if (res.status === 200) {
//       return users = await res.json()
//   }
//   else {
//     return users=[]
//   }
// }
// export const authen = async (log) => {
//   let token;
//   const res = await fetch(`http://localhost:8080/authenticate`, {
//     method: "POST",
//     headers: {
//       'content-type': 'application/json'
//     },
//     body: JSON.stringify({
//       username: log.email.trim(),
//       password: log.password
//     })
//   })
//   if (res.status === 200) {
//     token = await res.json()
//     localStorage.setItem("token", token)
//   }
//   else {
//     alert("Can't Authentication")
//   }
// }