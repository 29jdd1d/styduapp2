<template>
  <div class="resource-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>学习资源管理</span>
          <el-button type="primary" @click="addResource">
            <el-icon><Plus /></el-icon>添加资源
          </el-button>
        </div>
      </template>

      <el-tabs v-model="activeTab">
        <el-tab-pane label="全部资源" name="all"></el-tab-pane>
        <el-tab-pane label="政治" name="politics"></el-tab-pane>
        <el-tab-pane label="英语" name="english"></el-tab-pane>
        <el-tab-pane label="数学" name="math"></el-tab-pane>
        <el-tab-pane label="专业课" name="professional"></el-tab-pane>
      </el-tabs>

      <el-table :data="resourceList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="标题" width="200" />
        <el-table-column prop="type" label="类型" width="100" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 'PUBLISHED' ? 'success' : 'info'">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small">编辑</el-button>
            <el-button type="danger" size="small">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

const activeTab = ref('all')
const resourceList = ref([])
const loading = ref(false)

const addResource = () => {
  ElMessage.info('添加资源功能')
}

onMounted(() => {
  // Load resources
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
