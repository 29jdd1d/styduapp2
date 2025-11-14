const app = getApp()

function request(url, method = 'GET', data = {}) {
  return new Promise((resolve, reject) => {
    const token = wx.getStorageSync('token')
    
    wx.request({
      url: `${app.globalData.baseUrl}${url}`,
      method: method,
      data: data,
      header: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
      },
      success(res) {
        if (res.data.code === 200) {
          resolve(res.data.data)
        } else {
          wx.showToast({
            title: res.data.message || '请求失败',
            icon: 'none'
          })
          reject(res.data)
        }
      },
      fail(err) {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        })
        reject(err)
      }
    })
  })
}

// 用户相关API
export const login = (data) => request('/user/login/wechat', 'POST', data)
export const getUserInfo = (userId) => request(`/user/${userId}`, 'GET')
export const updateUserInfo = (userId, data) => request(`/user/${userId}`, 'PUT', data)
export const getUserDashboard = (userId) => request(`/user/${userId}/dashboard`, 'GET')

// 学习资源API
export const getResourceList = () => request('/resource/list', 'GET')
export const getResourcesByCategory = (categoryId) => request(`/resource/category/${categoryId}`, 'GET')
export const getResourceDetail = (id) => request(`/resource/${id}`, 'GET')

// 题库API
export const getQuestionsBySubject = (subject) => request(`/question/subject/${subject}`, 'GET')
export const submitAnswer = (data) => request('/question/submit', 'POST', data)
export const getWrongQuestions = (userId) => request(`/question/wrong/${userId}`, 'GET')

// 学习计划API
export const getUserPlans = (userId) => request(`/plan/user/${userId}`, 'GET')
export const generatePlan = (data) => request('/plan/generate', 'POST', data)
export const getPlanTasks = (planId) => request(`/plan/${planId}/tasks`, 'GET')
export const completeTask = (taskId, actualTime) => request(`/plan/task/${taskId}/complete?actualTime=${actualTime}`, 'PUT')

// 社区API
export const getPostsByType = (type) => request(`/community/post/type/${type}`, 'GET')
export const getPostDetail = (id) => request(`/community/post/${id}`, 'GET')
export const createPost = (data) => request('/community/post', 'POST', data)
export const getPostComments = (postId) => request(`/community/post/${postId}/comments`, 'GET')
export const createComment = (data) => request('/community/comment', 'POST', data)
export const likePost = (postId) => request(`/community/post/${postId}/like`, 'POST')
