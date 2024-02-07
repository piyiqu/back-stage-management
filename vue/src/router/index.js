import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect:"/goods",
    children:[
      {
        path: 'user',
        name: 'User',
        component: () =>import("@/views/User"),
      },
      {
        path: 'purchase',
        name: 'Purchase',
        component: () =>import("@/views/Purchase"),
      },
      {
        path: 'order',
        name: 'Order',
        component: () =>import("@/views/Order"),
      },
      {
        path: 'staff',
        name: 'Staff',
        component: () =>import("@/views/Staff"),
      },
      {
        path: '/person',
        name: 'Person',
        component: ()=> import("@/views/Person")
      },
      {
        path: '/goods',
        name: 'Goods',
        component: ()=> import("@/views/Goods")
      },

    ]
  },
  {
    path: '/Login',
    name: 'Login',
    component: ()=> import("@/views/Login")
  },
  {
    path: '/register',
    name: 'Register',
    component: ()=> import("@/views/Register")
  },

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
