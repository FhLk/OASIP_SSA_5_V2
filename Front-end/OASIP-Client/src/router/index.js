import { createRouter, createWebHistory } from 'vue-router'
import indexPage from '../views/indexPage.vue'
import AddPage from '../views/AddPage.vue'
import ShowPage from '../views/ShowPage.vue'
import AboutPage from '../views/AboutPage.vue'
import UserPage from '../views/UserPage.vue'
import LoginPage from '../views/LoginPage.vue'
import AddUserPage from '../views/AddUserPage.vue'
const history = createWebHistory('ssa5')
const routes = [
    {path: '/',name: 'indexPage',component: indexPage},
    {path: '/AddPage',name: 'AddPage',component: AddPage},
    {path: '/ShowPage',name: 'ShowPage',component: ShowPage},
    {path: '/AboutPage',name:'AboutPage',component:AboutPage},
    {path: '/UserPage',name: 'UserPage',component: UserPage},
    {path: '/LoginPage',name: 'LoginPage',component: LoginPage},
    {path: '/AddUserPage',name: 'AddUserPage',component: AddUserPage},
]
const router = createRouter({ history, routes })
export default router