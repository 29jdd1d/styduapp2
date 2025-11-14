const api = require('../../utils/api.js')
const app = getApp()

Page({
  data: {
    userInfo: null,
    dashboard: null,
    banners: [
      { id: 1, image: '/images/banner1.jpg', title: '考研加油' },
      { id: 2, image: '/images/banner2.jpg', title: '备考指南' }
    ],
    quickLinks: [
      { icon: '📚', title: '学习资源', path: '/pages/study/study' },
      { icon: '📝', title: '题库练习', path: '/pages/study/study' },
      { icon: '📅', title: '学习计划', path: '/pages/plan/plan' },
      { icon: '💬', title: '社区交流', path: '/pages/community/community' }
    ]
  },

  onLoad() {
    this.checkLogin()
  },

  onShow() {
    if (app.globalData.isLogin) {
      this.loadDashboard()
    }
  },

  checkLogin() {
    const token = wx.getStorageSync('token')
    if (!token) {
      this.showLoginDialog()
    } else {
      app.globalData.isLogin = true
      this.loadDashboard()
    }
  },

  showLoginDialog() {
    wx.showModal({
      title: '登录提示',
      content: '请先登录以使用完整功能',
      confirmText: '去登录',
      success: (res) => {
        if (res.confirm) {
          this.wxLogin()
        }
      }
    })
  },

  wxLogin() {
    wx.getUserProfile({
      desc: '用于完善用户资料',
      success: (res) => {
        const userInfo = res.userInfo
        
        wx.login({
          success: (loginRes) => {
            api.login({
              code: loginRes.code,
              nickName: userInfo.nickName,
              avatarUrl: userInfo.avatarUrl,
              gender: userInfo.gender
            }).then(data => {
              wx.setStorageSync('token', data.token)
              wx.setStorageSync('userInfo', data.user)
              app.globalData.isLogin = true
              app.globalData.userInfo = data.user
              
              this.setData({ userInfo: data.user })
              this.loadDashboard()
              
              wx.showToast({
                title: '登录成功',
                icon: 'success'
              })
            })
          }
        })
      }
    })
  },

  loadDashboard() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo && userInfo.id) {
      api.getUserDashboard(userInfo.id).then(data => {
        this.setData({ 
          dashboard: data,
          userInfo: userInfo
        })
      })
    }
  },

  navigateTo(e) {
    const path = e.currentTarget.dataset.path
    wx.navigateTo({ url: path })
  }
})
