# 管理端登录功能说明

## 新增功能

### 1. 管理端登录系统

管理端现已支持用户名密码登录，不再直接跳转到仪表盘。

#### 默认管理员账号
- 用户名: `admin`
- 密码: `admin123`

#### 登录流程
1. 启动管理端后，会自动跳转到登录页面
2. 输入用户名和密码
3. 点击"登录"按钮
4. 登录成功后跳转到数据概览页面

#### 退出登录
点击右上角用户信息下拉菜单，选择"退出登录"

### 2. 数据库初始化

首次运行时，需要执行以下SQL脚本创建默认管理员账号：

```sql
-- 文件位置: backend/src/main/resources/init-admin.sql
-- 该脚本会创建一个用户名为 admin，密码为 admin123 的管理员账号
```

或者手动执行：

```sql
INSERT INTO user (username, password, nickname, role, openid, gender, study_days, total_study_time, total_questions_answered, correct_answers, deleted, created_time, updated_time)
VALUES ('admin', '0192023a7bbd73250516f069df18b500', '管理员', 'ADMIN', null, 0, 0, 0, 0, 0, 0, NOW(), NOW());
```

### 3. 已完成的管理页面功能

所有管理页面已移除占位代码，实现了真实的数据加载：

#### 资源管理
- 按科目分类查看学习资源
- 支持全部、政治、英语、数学、专业课分类
- 显示资源ID、标题、类型、浏览量、状态等信息

#### 题库管理
- 支持按科目和难度筛选题目
- 显示题目ID、科目、题型、难度、内容等信息

#### 社区管理
- 按类型查看社区帖子
- 支持全部、考研资讯、备考经验、学习打卡分类
- 显示帖子ID、标题、类型、浏览量、点赞数、评论数等信息

#### 学习计划管理
- 查看所有用户的学习计划
- 显示计划ID、名称、目标院校、专业、完成度、状态等信息

#### 用户管理
- 查看所有用户列表
- 支持分页显示
- 显示用户ID、昵称、手机号、邮箱、目标院校、专业、学习天数、总学习时长等信息
- 支持查看用户详情和删除用户

#### 数据概览
- 实时显示系统统计数据
- 包括总用户数、学习资源数、题库题目数、社区帖子数
- 提供快捷操作入口

### 4. 技术实现

#### 后端
- 新增 `AdminAuthController` 处理管理员登录/登出
- 新增 `AdminService` 实现登录业务逻辑
- User 实体增加 `username` 和 `password` 字段
- 使用 MD5 加密密码
- 使用 JWT 进行身份认证

#### 前端
- 新增 `Login.vue` 登录页面
- 新增 Pinia store 管理登录状态
- 实现路由守卫，未登录自动跳转登录页
- 完善所有管理页面，移除占位代码
- 添加退出登录功能
- 请求拦截器处理 401 认证失败自动跳转登录页

## API 接口

### 登录
- **URL**: `/api/admin/auth/login`
- **Method**: `POST`
- **Body**:
```json
{
  "username": "admin",
  "password": "admin123"
}
```
- **Response**:
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "token": "jwt-token-string",
    "userId": 1,
    "username": "admin",
    "nickname": "管理员",
    "role": "ADMIN"
  }
}
```

### 登出
- **URL**: `/api/admin/auth/logout`
- **Method**: `POST`
- **Response**:
```json
{
  "code": 200,
  "message": "登出成功",
  "data": null
}
```

## 注意事项

1. 首次启动前，请确保数据库中已创建默认管理员账号
2. 密码使用 MD5 加密存储
3. 所有管理端接口都需要在 Header 中携带 JWT Token: `Authorization: Bearer <token>`
4. Token 过期时间为 7 天
5. 如需修改默认管理员密码，请使用 MD5 工具加密后更新数据库

## 安全建议

1. 首次登录后，建议立即修改默认密码
2. 定期更换管理员密码
3. 不要在生产环境使用默认密码
4. 建议使用更安全的密码加密算法（如 BCrypt）替代 MD5
