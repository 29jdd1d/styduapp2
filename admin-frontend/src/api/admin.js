import request from '@/utils/request'

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
    url: '/resource/list',
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
    url: '/community/post/list',
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
