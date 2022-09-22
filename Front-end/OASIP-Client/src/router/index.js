import { createRouter, createWebHistory } from 'vue-router'
import indexPage from '../views/indexPage.vue'
import AddEventPage from '../views/AddEventPage.vue'
import EventPage from '../views/EventPage.vue'
import AboutPage from '../views/AboutPage.vue'
import UserPage from '../views/UserPage.vue'
import LoginPage from '../views/LoginPage.vue'
import AddUserPage from '../views/AddUserPage.vue'
const history = createWebHistory('/ssa5')
const routes = [
    {path: '/',name: 'indexPage',component: indexPage},
    {path: '/AddEventPage',name: 'AddEventPage',component: AddEventPage},
    {path: '/EventPage',name: 'EventPage',component: EventPage},
    {path: '/AboutPage',name:'AboutPage',component:AboutPage},
    {path: '/UserPage',name: 'UserPage',component: UserPage},
    {path: '/LoginPage',name: 'LoginPage',component: LoginPage},
    {path: '/AddUserPage',name: 'AddUserPage',component: AddUserPage},
]
const router = createRouter({ history, routes })
export default router