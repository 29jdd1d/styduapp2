const api = require('../../utils/api.js')

Page({
  data: {
    userInfo: null,
    dashboard: null
  },

  onLoad() {
    this.loadUserInfo()
  },

  onShow() {
    this.loadDashboard()
  },

  loadUserInfo() {
    const userInfo = wx.getStorageSync('userInfo')
    this.setData({ userInfo })
  },

  loadDashboard() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo && userInfo.id) {
      api.getUserDashboard(userInfo.id).then(data => {
        this.setData({ dashboard: data })
      })
    }
  },

  editProfile() {
    wx.navigateTo({
      url: '/pages/edit-profile/edit-profile'
    })
  },

  logout() {
    wx.showModal({
      title: '确认退出',
      content: '确定要退出登录吗？',
      success: (res) => {
        if (res.confirm) {
          wx.clearStorageSync()
          wx.reLaunch({
            url: '/pages/index/index'
          })
        }
      }
    })
  }
})
