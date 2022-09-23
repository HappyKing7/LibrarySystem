import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import axios from "axios";

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home,
    children:[
      {
        path: '/index',
        name: 'Index',
        component: () => import('../views/Index.vue')
      },
      {
        path: '/authorInfo',
        name: 'AuthorInfo',
        component: () => import('../views/sys/Book/AuthorInfo')
      },
      {
        path: '/bookInfo',
        name: 'BookInfo',
        component: () => import('../views/sys/Book/BookInfo')
      },
      {
        path: '/bookTypeInfo',
        name: 'BookTypeInfo',
        component: () => import('../views/sys/Book/BookTypeInfo')
      },
      {
        path: '/pressInfo',
        name: 'PressInfo',
        component: () => import('../views/sys/Book/PressInfo')
      },
      {
        path: '/pressInfo',
        name: 'PressInfo',
        component: () => import('../views/sys/Book/PressInfo')
      },
      {
        path: '/borrowedInfo',
        name: 'BorrowedInfo',
        component: () => import('../views/sys/Borrowed/BorrowedInfo')
      },
      {
        path: '/userLogin',
        name: 'UserLogin',
        component: () => import('../views/sys/User/UserLogin')
      },
      {
        path: '/userInfo',
        name: 'UserInfo',
        component: () => import('../views/sys/User/UserInfo')
      },
      {
        path: '/userManga',
        name: 'UserManga',
        component: () => import('../views/sys/User/UserManga')
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
