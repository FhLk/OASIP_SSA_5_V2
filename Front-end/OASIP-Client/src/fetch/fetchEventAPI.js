const fetchUrl = import.meta.env.VITE_BASE_URL;
export const Events = async (page = 0) => {
    let res;
    if (page === 0) {
        res = await fetch(`${fetchUrl}/bookings`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    else {
        res= await fetch(`${fetchUrl}/bookings?page=${page}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    if (res.status === 200) {
        let events = await res.json()
        return events
    }
    return []
}

export const EventPast = async (page = 0) => {
    let res;
    if (page === 0) {
        res = await fetch(`${fetchUrl}/bookings/sortByPast`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    else {
        res = await fetch(`${fetchUrl}/bookings/sortByPast?page=${page}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    if (res.status === 200) {
        let events = await res.json()
        return events
    }
    return []
}

export const EventCategory = async (id, page = 0) => {
    let res;
    if (page === 0) {
        res = await fetch(`${fetchUrl}/bookings/sortByCategory?category=${id}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    else {
        res = await fetch(`${fetchUrl}/bookings/sortByCategory?page=${page}&category=${id}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    if (res.status === 200) {
        let events = await res.json()
        return events
    }
    return []
}

export const EventDay = async (day, page = 0) => {
    let res
    if (page === 0) {
        res = await fetch(`${fetchUrl}/bookings/sortByDay?date=${day}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    else {
        res = await fetch(`${fetchUrl}/bookings/sortByDay?page=${page}&date=${day}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
    }
    if (res.status === 200) {
        let events = await res.json()
        return events
    }
    return []
}

export const EventSave = async (updateBooking) => {
    const res = await fetch(`${fetchUrl}/bookings/${updateBooking.id}`, {
        method: 'PUT',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem('access_token')}`,
            'content-type': 'application/json'
        },
        body: JSON.stringify({
            id: updateBooking.id,
            bookingName: updateBooking.bookingName.trim(),
            bookingEmail: updateBooking.bookingEmail.trim(),
            category: {
                id: updateBooking.category.id,
                categoryName: updateBooking.category.categoryName
            },
            startTime: updateBooking.startTime,
            bookingDuration: updateBooking.bookingDuration,
            eventNote: updateBooking.eventNote.trim()
        })
    })
    return res.status
}

export const EventDelete = async (id) => {
    const res = await fetch(`${fetchUrl}/bookings/${id}`, {
        method: 'DELETE',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem('access_token')}`
        }
    })
    return res.status
}

export const EventDetail = async (id) => {
    const res = await await fetch(`${fetchUrl}/bookings/${id}`, {
        method: 'GET',
        headers: {
            "Authorization": `Bearer ${localStorage.getItem('access_token')}`
        }
    })
    if (res.status === 200) {
        let event = await res.json()
        return event
    }
    return {}
}