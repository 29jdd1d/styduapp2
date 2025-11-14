<template>
  <div class="question-management">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>题库管理</span>
          <el-button type="primary" @click="addQuestion">
            <el-icon><Plus /></el-icon>添加题目
          </el-button>
        </div>
      </template>

      <el-form :inline="true" :model="searchForm">
        <el-form-item label="科目">
          <el-select v-model="searchForm.subject" placeholder="选择科目">
            <el-option label="全部" value="" />
            <el-option label="政治" value="POLITICS" />
            <el-option label="英语" value="ENGLISH" />
            <el-option label="数学" value="MATH" />
            <el-option label="专业课" value="PROFESSIONAL" />
          </el-select>
        </el-form-item>
        <el-form-item label="难度">
          <el-select v-model="searchForm.difficulty" placeholder="选择难度">
            <el-option label="全部" value="" />
            <el-option label="简单" value="EASY" />
            <el-option label="中等" value="MEDIUM" />
            <el-option label="困难" value="HARD" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search">搜索</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="questionList" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="subject" label="科目" width="100" />
        <el-table-column prop="type" label="题型" width="120" />
        <el-table-column prop="difficulty" label="难度" width="100" />
        <el-table-column prop="content" label="题目内容" show-overflow-tooltip />
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
import { ref, reactive, onMounted } from 'vue'
import { getQuestionList } from '@/api/admin'
import { ElMessage } from 'element-plus'

const questionList = ref([])
const loading = ref(false)
const searchForm = reactive({
  subject: '',
  difficulty: ''
})

const loadQuestions = async () => {
  loading.value = true
  try {
    const params = {}
    if (searchForm.subject) {
      params.subject = searchForm.subject
    }
    if (searchForm.difficulty) {
      params.difficulty = searchForm.difficulty
    }
    const data = await getQuestionList(params)
    questionList.value = data || []
  } catch (error) {
    ElMessage.error('加载题目列表失败')
  } finally {
    loading.value = false
  }
}

const addQuestion = () => {
  ElMessage.info('添加题目功能')
}

const search = () => {
  loadQuestions()
}

onMounted(() => {
  loadQuestions()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
