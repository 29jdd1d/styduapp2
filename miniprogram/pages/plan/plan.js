const api = require('../../utils/api.js')

Page({
  data: {
    plans: [],
    currentPlan: null
  },

  onLoad() {
    this.loadPlans()
  },

  loadPlans() {
    const userInfo = wx.getStorageSync('userInfo')
    if (userInfo && userInfo.id) {
      api.getUserPlans(userInfo.id).then(data => {
        this.setData({ 
          plans: data,
          currentPlan: data.length > 0 ? data[0] : null
        })
        if (data.length > 0) {
          this.loadTasks(data[0].id)
        }
      })
    }
  },

  loadTasks(planId) {
    api.getPlanTasks(planId).then(data => {
      this.setData({ 
        'currentPlan.tasks': data
      })
    })
  },

  completeTask(e) {
    const taskId = e.currentTarget.dataset.id
    api.completeTask(taskId, 120).then(() => {
      wx.showToast({
        title: '任务已完成',
        icon: 'success'
      })
      this.loadPlans()
    })
  },

  createPlan() {
    wx.navigateTo({
      url: '/pages/create-plan/create-plan'
    })
  }
})
