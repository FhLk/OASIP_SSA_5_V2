import { createRouter, createWebHistory } from 'vue-router'
import indexPage from '../views/indexPage.vue'
import AddEventPage from '../views/AddEventPage.vue'
import EventPage from '../views/EventPage.vue'
import AboutPage from '../views/AboutPage.vue'
import UserPage from '../views/Admin/UserPage.vue'
import LoginPage from '../views/LoginPage.vue'
import AddUserPage from '../views/Admin/AddUserPage.vue'
import CategoryPage from '../views/CategoryPage.vue'
const history = createWebHistory('ssa5')
const routes = [
    {path: '/',name: 'indexPage',component: indexPage},
    {path: '/event/create/:category',name: 'AddEventByCategoryPage',component: AddEventPage},
    {path: '/event/create',name: 'AddEventPage',component: AddEventPage},
    {path: '/event',name: 'EventPage',component: EventPage},
    {path: '/event/:id',name: 'EventPageId',component: EventPage},
    {path: '/about',name:'AboutPage',component:AboutPage},
    {path: '/user',name: 'UserPage',component: UserPage},
    {path: '/user/:id',name: 'UserPageId',component: UserPage},
    {path: '/login',name: 'LoginPage',component: LoginPage},
    {path: '/user/create',name: 'AddUserPage',component: AddUserPage},
    {path: '/category', name:'CategoryPage',component: CategoryPage}
]
const router = createRouter({ history, routes })
export default router