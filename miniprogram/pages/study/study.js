const api = require('../../utils/api.js')

Page({
  data: {
    activeTab: 0,
    subjects: ['政治', '英语', '数学', '专业课'],
    resources: []
  },

  onLoad() {
    this.loadResources()
  },

  loadResources() {
    api.getResourceList().then(data => {
      this.setData({ resources: data })
    })
  },

  onTabChange(e) {
    this.setData({ activeTab: e.detail.index })
  },

  viewResource(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/resource-detail/resource-detail?id=${id}`
    })
  }
})
