import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/admin/auth/login',
    method: 'post',
    data
  })
}

export function logout() {
  return request({
    url: '/admin/auth/logout',
    method: 'post'
  })
}

export function getStatistics() {
  return request({
    url: '/admin/statistics/overview',
    method: 'get'
  })
}

export function getUserList(params) {
  return request({
    url: '/admin/user/list',
    method: 'get',
    params
  })
}

export function getResourceList(params) {
  return request({
    url: '/admin/resource/list',
    method: 'get',
    params
  })
}

export function getQuestionList(params) {
  return request({
    url: '/question/list',
    method: 'get',
    params
  })
}

export function getPostList(params) {
  return request({
    url: '/admin/community/post/list',
    method: 'get',
    params
  })
}

export function getPlanList(userId) {
  return request({
    url: `/plan/user/${userId}`,
    method: 'get'
  })
}
