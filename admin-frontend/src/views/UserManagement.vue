<template>
  <div class="user-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户列表</span>
        </div>
      </template>

      <el-table :data="userList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="nickname" label="昵称" width="150" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="email" label="邮箱" width="200" />
        <el-table-column prop="targetUniversity" label="目标院校" width="150" />
        <el-table-column prop="targetMajor" label="目标专业" width="150" />
        <el-table-column prop="studyDays" label="学习天数" width="100" />
        <el-table-column prop="totalStudyTime" label="总学习时长(分)" width="130" />
        <el-table-column label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatDate(row.createdTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="viewUser(row)">
              查看
            </el-button>
            <el-button type="danger" size="small" @click="deleteUser(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="loadUsers"
        @current-change="loadUsers"
        style="margin-top: 20px; justify-content: center"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getUserList } from '@/api/admin'
import { ElMessage, ElMessageBox } from 'element-plus'

const userList = ref([])
const loading = ref(false)
const currentPage = ref(1)
const pageSize = ref(20)
const total = ref(0)

const loadUsers = async () => {
  loading.value = true
  try {
    const data = await getUserList({
      page: currentPage.value - 1,
      size: pageSize.value
    })
    userList.value = data.content || []
    total.value = data.totalElements || 0
  } catch (error) {
    ElMessage.error('加载用户列表失败')
  } finally {
    loading.value = false
  }
}

const formatDate = (dateStr) => {
  if (!dateStr) return ''
  return new Date(dateStr).toLocaleString('zh-CN')
}

const viewUser = (row) => {
  ElMessage.info('查看用户详情: ' + row.nickname)
}

const deleteUser = (row) => {
  ElMessageBox.confirm(
    `确定要删除用户 ${row.nickname} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(() => {
    ElMessage.success('删除成功')
    loadUsers()
  }).catch(() => {
    ElMessage.info('已取消删除')
  })
}

onMounted(() => {
  loadUsers()
})
</script>

<style scoped>
.user-management {
  width: 100%;
}

.card-header {
  font-weight: bold;
  font-size: 16px;
}
</style>
