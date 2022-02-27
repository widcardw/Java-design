import {createRouter, createWebHashHistory} from 'vue-router'

const routes = [{
    path: "/",
    name: "Layout",
    component: () => import("@/layout/Layout.vue"),
    redirect: "/home",
    children: [
        {
            path: '/home',
            name: 'home',
            component: () => import('@/views/home.vue')
        }, {
            path: '/login',
            name: 'login',
            component: () => import('@/views/login.vue')
        }, {
            path: '/info',
            name: 'info',
            component: () => import('@/views/info.vue')
        }, {
            path: '/signup',
            name: 'signup',
            component: () => import('@/views/signup.vue')
        }, {
            path: '/writepost',
            name: 'writepost',
            component: () => import('@/views/writepost.vue'),
            props: route => ({query: route.query.courseId})
        }, {
            path: '/postdetail',
            name: 'postdetail',
            component: () => import('@/views/postdetail.vue'),
            props: route => ({query: route.query.post})
        }, {
            path: '/editpost',
            name: 'editpost',
            component: () => import('@/views/editpost.vue'),
            props: route => ({query: route.query.post})
        },
    ]
}, {
    path: '/studentAdministration',
    name: 'studentAdministration',
    component: () => import('@/views/studentAdministration.vue')
}]

const Router = createRouter({
    history: createWebHashHistory(),
    routes: routes
})

export default Router