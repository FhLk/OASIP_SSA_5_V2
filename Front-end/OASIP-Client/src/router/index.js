import { createRouter, createWebHistory } from 'vue-router'
import indexPage from '../views/indexPage.vue'
import AddPage from '../views/AddPage.vue'
import ShowPage from '../views/ShowPage.vue'
import AboutPage from '../views/AboutPage.vue'
import UserPage from '../views/UserPage.vue'
import LoginPage from '../views/LoginPage.vue'
const history = createWebHistory('ssa5')
const routes = [
    {path: '/',name: 'indexPage',component: indexPage},
    {path: '/AddPage',name: 'AddPage',component: AddPage},
    {path: '/ShowPage',name: 'ShowPage',component: ShowPage},
    {path: '/AboutPage',name:'AboutPage',component:AboutPage},
    {path: '/UserPage',nmae: 'UserPage',component: UserPage},
    {path: '/LoginPage',nmae: 'LoginPage',component: LoginPage},
]
const router = createRouter({ history, routes })
export default router