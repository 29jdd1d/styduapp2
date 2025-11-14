const api = require('../../utils/api.js')

Page({
  data: {
    activeTab: 'NEWS',
    tabs: [
      { value: 'NEWS', label: '考研资讯' },
      { value: 'EXPERIENCE', label: '备考经验' },
      { value: 'CHECK_IN', label: '学习打卡' }
    ],
    posts: []
  },

  onLoad() {
    this.loadPosts()
  },

  onTabChange(e) {
    const tab = e.currentTarget.dataset.tab
    this.setData({ activeTab: tab })
    this.loadPosts()
  },

  loadPosts() {
    api.getPostsByType(this.data.activeTab).then(data => {
      this.setData({ posts: data })
    })
  },

  viewPost(e) {
    const id = e.currentTarget.dataset.id
    wx.navigateTo({
      url: `/pages/post-detail/post-detail?id=${id}`
    })
  },

  createPost() {
    wx.navigateTo({
      url: '/pages/create-post/create-post'
    })
  }
})
