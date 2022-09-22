export const checkToken = () => {
    if (localStorage.getItem("token") === null) {
        return false;
    } else {
        return true;
    }
};