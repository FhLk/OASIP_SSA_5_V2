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
  return 
}

export const AllUser = async () => {
  let all=[]
  const res = await fetch(`${fetchUrl}/users/check`, {
      method: 'GET',
      headers: {
        "Authorization": `Bearer ${localStorage.getItem('token')}`
    }
  })
  if (res.status === 200) {
      return all = await res.json()
  }
}