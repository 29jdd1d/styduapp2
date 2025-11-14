<template>
  <div class="plan-management">
    <el-card>
      <template #header>
        <span>学习计划管理</span>
      </template>

      <el-table :data="planList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="title" label="计划名称" width="250" />
        <el-table-column prop="targetUniversity" label="目标院校" width="150" />
        <el-table-column prop="targetMajor" label="目标专业" width="150" />
        <el-table-column prop="progress" label="完成度" width="120">
          <template #default="{ row }">
            <el-progress :percentage="row.progress || 0" />
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 'ACTIVE' ? 'success' : 'info'">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small">查看</el-button>
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
import request from '@/utils/request'

const planList = ref([])
const loading = ref(false)

const loadPlans = async () => {
  loading.value = true
  try {
    // Get all plans - we'll need to create an endpoint for this
    const data = await request({
      url: '/plan/list',
      method: 'get'
    })
    planList.value = data || []
  } catch (error) {
    ElMessage.error('加载学习计划失败')
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadPlans()
})
</script>
