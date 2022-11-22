import { CloseAlert, ExceptionAlert, LoadingAlert } from "../Alert/alert";

const fetchUrl = import.meta.env.VITE_BASE_URL;
export const Events = async (page = 0) => {
    let res;
    try {
        if (page === 0) {
            res = await fetch(`${fetchUrl}/bookings`, {
                method: 'GET',
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem('access_token')}`
                }
            })
        }
        else {
            res = await fetch(`${fetchUrl}/bookings?page=${page}`, {
                method: 'GET',
                headers: {
                    "Authorization": `Bearer ${localStorage.getItem('access_token')}`
                }
            })
        }
        if (res.status === 200) {
            let events = await res.json()
            CloseAlert()
            return events
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

export const EventPast = async (page = 0) => {
    let res;
    try {
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
            CloseAlert()
            return events
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

export const EventCategory = async (id, page = 0) => {
    let res;
    try {
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
            CloseAlert()
            return events
        }
        else if (res.status === 403) {
            CloseAlert()
            return 403
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

export const EventDay = async (day, page = 0) => {
    let res
    try {
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
            CloseAlert()
            return events
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

export const EventSave = async (updateBooking) => {
    try {
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
    } catch (error) {
        ExceptionAlert("Failed")
        return 0
    }
}

export const EventDelete = async (id) => {
    try {
        const res = await fetch(`${fetchUrl}/bookings/${id}`, {
            method: 'DELETE',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
        return res.status
    } catch (error) {
        ExceptionAlert("Failed")
        return 0
    }
}

export const EventDetail = async (id) => {
    try {
        const res = await fetch(`${fetchUrl}/bookings/${id}`, {
            method: 'GET',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`
            }
        })
        if (res.status === 200) {
            let event = await res.json()
            CloseAlert()
            return event
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

export const createByRole = async (booking,file) => {
    try {
        let formData= new FormData()
        formData.append("event",
        JSON.stringify({
            id: 0,
            bookingName: booking.bookingName.trim(),
            bookingEmail: booking.bookingEmail.trim(),
            category: {
                id: booking.category.id,
                categoryName: booking.category.categoryName
            },
            startTime: `${booking.Date}T${booking.Time}`,
            bookingDuration: booking.bookingDuration,
            eventNote: booking.eventNote.trim(),
            user: { id: Number(localStorage.getItem("id")) }
        }))
        formData.append('file',file[0])
        const res = await fetch(`${fetchUrl}/bookings`, {
            method: 'POST',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`,
            },
            body: formData
        })
        return res.status
    } catch (error) {
        ExceptionAlert("Failed")
        return 0
    }
}

export const createByGuest = async (booking) => {
    try {
        const res = await fetch(`${fetchUrl}/bookings`, {
            method: 'POST',
            headers: {
                'content-type': 'application/json'
            },
            body: JSON.stringify({
                id: 0,
                bookingName: booking.bookingName.trim(),
                bookingEmail: booking.bookingEmail.trim(),
                category: {
                    id: booking.category.id,
                    categoryName: booking.category.categoryName
                },
                startTime: `${booking.Date}T${booking.Time}`,
                bookingDuration: booking.bookingDuration,
                eventNote: booking.eventNote.trim()
            })
        })
        return res.status
    } catch (error) {
        ExceptionAlert("Failed")
        return 0
    }
}