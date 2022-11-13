const fetchUrl = import.meta.env.VITE_BASE_URL;
import { accessAlert, CloseAlert, delAlert, deniedAlert, ExceptionAlert, LoadingAlert } from "../Alert/alert.js";
export const getCategories = async () => {
    let getCategories = []
    LoadingAlert()
    try {
        const res = await fetch(`${fetchUrl}/categories`, {
            method: 'GET',
        })
        if (res.status === 200) {
            getCategories = await res.json()
            CloseAlert()
            return getCategories
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

export const getCategory = async (id) => {
    try {
        LoadingAlert()
        const res = await fetch(`${fetchUrl}/categories/${id}`, {
            method: 'GET',
        })
        if (res.status === 200) {
            CloseAlert()
            let category = await res.json()
            return category
        }
        else {
            ExceptionAlert(res.status)
            return
        }
    } catch (error) {
        ExceptionAlert("Failed")
        return
    }
}

export const saveCategory = async (updateCategory) => {
    try {
        const res = await fetch(`${import.meta.env.VITE_BASE_URL}/categories/${updateCategory.id}`, {
            method: 'PUT',
            headers: {
                "Authorization": `Bearer ${localStorage.getItem('access_token')}`,
                'content-type': 'application/json'
            },
            body: JSON.stringify({
                id: updateCategory.id,
                categoryName: updateCategory.categoryName.trim(),
                description: updateCategory.description.trim(),
                duration: updateCategory.duration
            })
        })
        return res.status
    } catch (error) {
        ExceptionAlert("Failed")
        return 
    }
}