App({
  onLaunch() {
    // 小程序启动时执行
    console.log('App launched')
    
    // 检查登录状态
    const token = wx.getStorageSync('token')
    if (token) {
      this.globalData.isLogin = true
    }
  },
  
  globalData: {
    isLogin: false,
    userInfo: null,
    baseUrl: 'http://localhost:8080/api'
  }
})
