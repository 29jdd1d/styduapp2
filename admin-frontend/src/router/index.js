import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/store/auth'
import Layout from '@/views/Layout.vue'

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login.vue'),
    meta: { title: '登录' }
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    meta: { requiresAuth: true },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '数据概览', requiresAuth: true }
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import('@/views/UserManagement.vue'),
        meta: { title: '用户管理', requiresAuth: true }
      },
      {
        path: 'resources',
        name: 'Resources',
        component: () => import('@/views/ResourceManagement.vue'),
        meta: { title: '资源管理', requiresAuth: true }
      },
      {
        path: 'questions',
        name: 'Questions',
        component: () => import('@/views/QuestionManagement.vue'),
        meta: { title: '题库管理', requiresAuth: true }
      },
      {
        path: 'community',
        name: 'Community',
        component: () => import('@/views/CommunityManagement.vue'),
        meta: { title: '社区管理', requiresAuth: true }
      },
      {
        path: 'plans',
        name: 'Plans',
        component: () => import('@/views/PlanManagement.vue'),
        meta: { title: '学习计划管理', requiresAuth: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Navigation guard
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth && !authStore.isAuthenticated()) {
    // Redirect to login if route requires auth and user is not authenticated
    next('/login')
  } else if (to.path === '/login' && authStore.isAuthenticated()) {
    // Redirect to dashboard if user is already logged in and trying to access login page
    next('/dashboard')
  } else {
    next()
  }
})

export default router
