<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon user">
              <el-icon size="40"><User /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.totalUsers || 0 }}</div>
              <div class="stat-label">总用户数</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon resource">
              <el-icon size="40"><Document /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.totalResources || 0 }}</div>
              <div class="stat-label">学习资源</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon question">
              <el-icon size="40"><Edit /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.totalQuestions || 0 }}</div>
              <div class="stat-label">题库题目</div>
            </div>
          </div>
        </el-card>
      </el-col>
      
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon post">
              <el-icon size="40"><ChatDotRound /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ statistics.totalPosts || 0 }}</div>
              <div class="stat-label">社区帖子</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" style="margin-top: 20px">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>快速操作</span>
            </div>
          </template>
          <el-space direction="vertical" fill style="width: 100%">
            <el-button type="primary" @click="$router.push('/resources')">
              <el-icon><Upload /></el-icon>上传学习资源
            </el-button>
            <el-button type="success" @click="$router.push('/questions')">
              <el-icon><Plus /></el-icon>添加题目
            </el-button>
            <el-button type="info" @click="$router.push('/community')">
              <el-icon><View /></el-icon>查看社区内容
            </el-button>
          </el-space>
        </el-card>
      </el-col>
      
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>系统信息</span>
            </div>
          </template>
          <div class="system-info">
            <p><strong>系统版本：</strong>1.0.0</p>
            <p><strong>后端框架：</strong>Spring Boot 3.1.5 + Java 17</p>
            <p><strong>前端框架：</strong>Vue 3 + Element Plus</p>
            <p><strong>数据库：</strong>MySQL 8.0</p>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { getStatistics } from '@/api/admin'

const statistics = ref({})

const loadStatistics = async () => {
  try {
    statistics.value = await getStatistics()
  } catch (error) {
    console.error('Failed to load statistics:', error)
  }
}

onMounted(() => {
  loadStatistics()
})
</script>

<style scoped>
.dashboard {
  width: 100%;
}

.stat-card {
  display: flex;
  align-items: center;
  padding: 10px 0;
}

.stat-icon {
  width: 70px;
  height: 70px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
  color: #fff;
}

.stat-icon.user {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.stat-icon.resource {
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
}

.stat-icon.question {
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
}

.stat-icon.post {
  background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
}

.stat-info {
  flex: 1;
}

.stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.stat-label {
  font-size: 14px;
  color: #909399;
}

.card-header {
  font-weight: bold;
}

.system-info p {
  margin: 10px 0;
  color: #606266;
}
</style>
