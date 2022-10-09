import Swal from 'sweetalert2'
export const delAlert = async () => {
    let test = await Swal.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, delete it!'
    })
    if (test.isConfirmed) {
        Swal.fire(
            'Deleted!',
            'Your file has been deleted.',
            'success'
        )
        return true
    }
    return false
}
export const sureAlert = async () => {
    const sure = await Swal.fire({
        title: 'Do you want to save the changes?',
        showCancelButton: true,
        confirmButtonText: 'Yes',
        cancelButtonText: 'No'
    })
    if (sure.isConfirmed) {
        Swal.fire('Saved!', '', 'success')
        return true
    }
    return false
}