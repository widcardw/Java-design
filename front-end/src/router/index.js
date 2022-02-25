import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [{
    path: '/',
    name: 'home',
    component: () => import('@/views/home.vue')
},{
    path: '/login',
    name: 'login',
    component: () => import('@/views/login.vue')
},{
    path: '/info',
    name: 'info',
    component: () => import('@/views/info.vue')
},{
    path: '/signup',
    name: 'signup',
    component: () => import('@/views/signup.vue')
},{
    path: '/writepost',
    name: 'writepost',
    component: () => import('@/views/writepost.vue')
},{
    path: '/postdetail',
    name: 'postdetail',
    component: () => import('@/views/postdetail.vue')
}]

const Router = createRouter({
    history: createWebHashHistory(),
    routes: routes
})

export default Router