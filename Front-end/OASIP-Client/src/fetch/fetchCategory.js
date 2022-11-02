const fetchUrl = import.meta.env.VITE_BASE_URL;
export const getCategories = async () => {
    let getCategories = []
    try {
        const res = await fetch(`${fetchUrl}/categories`, {
            method: 'GET',
        })
        if (res.status === 200) {
            getCategories = await res.json()
            return getCategories
        }
        else {
            return []
        }
    } catch (error) {
        return []
    }
}

export const getCategory = async (id) => {
    try {
        const res = await fetch(`${fetchUrl}/categories/${id}`, {
            method: 'GET',
        })
        if (res.status === 200) {
            let category = await res.json()
            return category
        }
        else {
            return
        }
    } catch (error) {
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
        return 
    }
}