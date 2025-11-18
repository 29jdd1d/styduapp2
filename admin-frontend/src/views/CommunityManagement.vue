<template>
  <div class="community-management">
    <el-card>
      <template #header>
        <span>社区内容管理</span>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="全部帖子" name="all"></el-tab-pane>
        <el-tab-pane label="考研资讯" name="news"></el-tab-pane>
        <el-tab-pane label="备考经验" name="experience"></el-tab-pane>
        <el-tab-pane label="学习打卡" name="checkin"></el-tab-pane>
      </el-tabs>

      <el-table :data="postList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" width="250" />
        <el-table-column prop="type" label="类型" width="120" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="likeCount" label="点赞数" width="100" />
        <el-table-column prop="commentCount" label="评论数" width="100" />
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleView(row)">查看</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, watch, onMounted } from 'vue'
import { getPostList, deletePost } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const activeTab = ref('all')
const postList = ref([])
const loading = ref(false)

const loadPosts = async () => {
  loading.value = true
  try {
    const params = {}
    if (activeTab.value !== 'all') {
      params.type = activeTab.value.toUpperCase()
    }
    const data = await getPostList(params)
    postList.value = data || []
  } catch (error) {
    ElMessage.error('加载帖子列表失败')
  } finally {
    loading.value = false
  }
}

const handleView = (row) => {
  ElMessage.info(`查看帖子: ${row.title}`)
}

const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除帖子 "${row.title}" 吗？`,
      '提示',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    await deletePost(row.id)
    ElMessage.success('删除成功')
    loadPosts()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

watch(activeTab, () => {
  loadPosts()
})

onMounted(() => {
  loadPosts()
})
</script>
