import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/views/Layout.vue'

const routes = [
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/Dashboard.vue'),
        meta: { title: '数据概览' }
      },
      {
        path: 'users',
        name: 'Users',
        component: () => import('@/views/UserManagement.vue'),
        meta: { title: '用户管理' }
      },
      {
        path: 'resources',
        name: 'Resources',
        component: () => import('@/views/ResourceManagement.vue'),
        meta: { title: '资源管理' }
      },
      {
        path: 'questions',
        name: 'Questions',
        component: () => import('@/views/QuestionManagement.vue'),
        meta: { title: '题库管理' }
      },
      {
        path: 'community',
        name: 'Community',
        component: () => import('@/views/CommunityManagement.vue'),
        meta: { title: '社区管理' }
      },
      {
        path: 'plans',
        name: 'Plans',
        component: () => import('@/views/PlanManagement.vue'),
        meta: { title: '学习计划管理' }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
