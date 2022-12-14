import Swal from 'sweetalert2'
export const delAlert = async () => {
    let test = await Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        allowOutsideClick: false,
        confirmButtonText: 'Yes, delete it!'
    })
    if (test.isConfirmed) {
        return true
    }
    return false
}
export const sureAlert = async () => {
    const sure = await Swal.fire({
        title: 'Are you sure?',
        allowOutsideClick: false,
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No'
    })
    if (sure.isConfirmed) {
        return true
    }
    return false
}

export const deniedAlert = async (action, event) => {
    await Swal.fire({
        icon: 'error',
        allowOutsideClick: false,
        title: `Can't ${action} this ${event}`,
        text: 'Something wrong!',
        confirmButtonText: 'Close',
    })
}

export const accessAlert = async (action) => {
    await Swal.fire({
        icon: 'success',
        allowOutsideClick: false,
        confirmButtonText: 'Close',
        title: `${action}`
    })
}

export const ExceptionAlert = async (code) => {
    await Swal.fire({
        icon: "error",
        allowOutsideClick: false,
        title: typeof(code) === Number() ? "HTTP Error":"Disconnection to Server",
        text: `Status ${code}`
    })
}

export const LoadingAlert = () => {
    Swal.fire({
        title: "Please Wait",
        allowEscapeKey: false,
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading()
        }
    })
}

export const CloseAlert =()=>{
    Swal.close()
}