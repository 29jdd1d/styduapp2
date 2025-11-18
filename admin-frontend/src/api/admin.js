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

// Resource Management
export function getResourceList(params) {
  return request({
    url: '/admin/resource/list',
    method: 'get',
    params
  })
}

export function getResource(id) {
  return request({
    url: `/admin/resource/${id}`,
    method: 'get'
  })
}

export function createResource(data) {
  return request({
    url: '/admin/resource',
    method: 'post',
    data
  })
}

export function updateResource(id, data) {
  return request({
    url: `/admin/resource/${id}`,
    method: 'put',
    data
  })
}

export function deleteResource(id) {
  return request({
    url: `/admin/resource/${id}`,
    method: 'delete'
  })
}

export function getResourceCategories() {
  return request({
    url: '/admin/resource/category',
    method: 'get'
  })
}

export function createResourceCategory(data) {
  return request({
    url: '/admin/resource/category',
    method: 'post',
    data
  })
}

export function getQuestionList(params) {
  return request({
    url: '/question/list',
    method: 'get',
    params
  })
}

// Community Management
export function getPostList(params) {
  return request({
    url: '/admin/community/post/list',
    method: 'get',
    params
  })
}

export function getPost(id) {
  return request({
    url: `/admin/community/post/${id}`,
    method: 'get'
  })
}

export function deletePost(id) {
  return request({
    url: `/admin/community/post/${id}`,
    method: 'delete'
  })
}

export function getPostComments(postId) {
  return request({
    url: `/admin/community/post/${postId}/comments`,
    method: 'get'
  })
}

export function deleteComment(id) {
  return request({
    url: `/admin/community/comment/${id}`,
    method: 'delete'
  })
}

export function getPlanList(userId) {
  return request({
    url: `/plan/user/${userId}`,
    method: 'get'
  })
}
