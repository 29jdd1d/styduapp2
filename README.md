# 考研学习平台 (Graduate Exam Study Platform)

## 项目简介

这是一个基于 Java 17 + Spring Boot 和 Vue 3 以及微信小程序开发的考研学习平台，旨在为考研学生提供全方位的学习支持。

## 技术栈

### 后端
- **Java 17**
- **Spring Boot 3.1.5**
- **Spring Data JPA**
- **Spring Security**
- **MySQL 8.0**
- **Redis**
- **JWT** - 用户认证
- **Hutool** - MD5 加密和工具类
- **腾讯云 COS** - 文件存储
- **微信小程序 SDK** - 微信登录

### 管理端前端
- **Vue 3**
- **Vite**
- **Element Plus**
- **Pinia** - 状态管理
- **Vue Router**
- **Axios**

### 客户端
- **微信小程序**
- **WeChat Mini Program SDK**

## 功能模块

### 1. 用户管理模块
- ✅ 微信一键登录
- ✅ 个人信息维护
- ✅ 学习数据看板
- ✅ 目标院校和专业设置

### 2. 学习资源管理模块
- ✅ 资源分类（政治、英语、数学、专业课）
- ✅ 视频、文档、题库上传
- ✅ 资源发布与管理
- ✅ 资源浏览和下载统计

### 3. 个性化学习计划模块
- ✅ 根据目标院校和专业生成学习计划
- ✅ 动态任务分配
- ✅ 进度跟踪
- ✅ 任务完成统计

### 4. 题库与刷题模块
- ✅ 按知识点、章节、年份分类
- ✅ 在线答题
- ✅ 错题自动收录
- ✅ 答题统计和正确率分析

### 5. 社区交流模块
- ✅ 考研资讯发布
- ✅ 备考经验分享
- ✅ 学习打卡
- ✅ 互动评论和点赞

### 6. 系统后台管理模块
- ✅ 用户管理
- ✅ 资源管理
- ✅ 题库管理
- ✅ 学习计划管理
- ✅ 社区内容管理
- ✅ 数据统计与分析

## 项目结构

```
styduapp2/
├── backend/                    # 后端项目
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/study/exam/
│   │   │   │       ├── config/         # 配置类
│   │   │   │       ├── controller/     # 控制器
│   │   │   │       ├── entity/         # 实体类
│   │   │   │       ├── repository/     # 数据访问层
│   │   │   │       ├── service/        # 业务逻辑层
│   │   │   │       ├── dto/            # 数据传输对象
│   │   │   │       ├── utils/          # 工具类
│   │   │   │       └── common/         # 通用类
│   │   │   └── resources/
│   │   │       └── application.yml     # 配置文件
│   │   └── test/
│   └── pom.xml                         # Maven 配置
├── admin-frontend/             # Vue 3 管理端
│   ├── src/
│   │   ├── api/               # API 接口
│   │   ├── components/        # 组件
│   │   ├── router/            # 路由配置
│   │   ├── store/             # 状态管理
│   │   ├── utils/             # 工具类
│   │   ├── views/             # 页面视图
│   │   ├── App.vue
│   │   └── main.js
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
└── miniprogram/               # 微信小程序
    ├── pages/                 # 页面
    │   ├── index/            # 首页
    │   ├── study/            # 学习页
    │   ├── plan/             # 计划页
    │   ├── community/        # 社区页
    │   └── user/             # 用户页
    ├── utils/                # 工具类
    ├── app.js
    ├── app.json
    └── app.wxss
```

## 快速开始

### 后端启动

1. 安装 MySQL 8.0 和 Redis

2. 创建数据库
```sql
CREATE DATABASE exam_study CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. 修改配置文件 `backend/src/main/resources/application.yml`
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/exam_study
    username: your_username
    password: your_password
  redis:
    host: localhost
    port: 6379

wechat:
  miniapp:
    appid: your_miniapp_appid
    secret: your_miniapp_secret

tencent:
  cos:
    secret-id: your_secret_id
    secret-key: your_secret_key
    region: your_region
    bucket-name: your_bucket_name
```

4. 启动后端
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

后端将在 http://localhost:8080 启动

### 管理端前端启动

```bash
cd admin-frontend
npm install
npm run dev
```

管理端将在 http://localhost:3000 启动

### 微信小程序启动

1. 下载并安装微信开发者工具
2. 打开微信开发者工具，导入 `miniprogram` 目录
3. 修改 `miniprogram/app.js` 中的 `baseUrl` 为你的后端地址
4. 在微信开发者工具中点击编译运行

## API 文档

### 用户模块

- `POST /api/user/login/wechat` - 微信登录
- `GET /api/user/{id}` - 获取用户信息
- `PUT /api/user/{id}` - 更新用户信息
- `GET /api/user/{id}/dashboard` - 获取用户学习数据看板

### 学习资源模块

- `GET /api/resource/list` - 获取资源列表
- `GET /api/resource/category/{categoryId}` - 按分类获取资源
- `POST /api/resource` - 创建资源
- `POST /api/resource/upload` - 上传资源文件
- `PUT /api/resource/{id}` - 更新资源
- `DELETE /api/resource/{id}` - 删除资源

### 学习计划模块

- `GET /api/plan/user/{userId}` - 获取用户学习计划
- `POST /api/plan/generate` - 生成学习计划
- `GET /api/plan/{planId}/tasks` - 获取计划任务
- `PUT /api/plan/task/{taskId}/complete` - 完成任务

### 题库模块

- `GET /api/question/list` - 获取题目列表
- `GET /api/question/subject/{subject}` - 按科目获取题目
- `POST /api/question/submit` - 提交答案
- `GET /api/question/wrong/{userId}` - 获取错题

### 社区模块

- `GET /api/community/post/list` - 获取帖子列表
- `GET /api/community/post/type/{type}` - 按类型获取帖子
- `POST /api/community/post` - 创建帖子
- `GET /api/community/post/{postId}/comments` - 获取评论
- `POST /api/community/comment` - 发表评论
- `POST /api/community/post/{id}/like` - 点赞帖子

### 管理后台模块

- `GET /api/admin/statistics/overview` - 获取数据概览
- `GET /api/admin/user/list` - 获取用户列表
- `DELETE /api/admin/user/{id}` - 删除用户

## 开发指南

### 后端开发

1. 实体类使用 JPA 注解进行 ORM 映射
2. 所有实体继承 `BaseEntity` 以获得基础字段（id, createdTime, updatedTime, deleted）
3. 使用 `@Transactional` 注解处理事务
4. 统一返回 `Result<T>` 格式的响应

### 前端开发

1. 使用 Vue 3 Composition API
2. 组件化开发，复用性强的组件放在 `components` 目录
3. API 请求统一放在 `api` 目录
4. 使用 Element Plus 组件库

### 小程序开发

1. 页面文件包含 `.js`, `.json`, `.wxml`, `.wxss` 四个文件
2. API 请求统一使用 `utils/api.js` 中的封装方法
3. 使用 `wx.getStorageSync` 和 `wx.setStorageSync` 进行本地存储

## 部署

### 后端部署

1. 打包
```bash
cd backend
mvn clean package
```

2. 运行
```bash
java -jar target/exam-backend-1.0.0.jar
```

### 前端部署

1. 打包
```bash
cd admin-frontend
npm run build
```

2. 部署 `dist` 目录到 Nginx 或其他静态服务器

### 小程序部署

1. 在微信开发者工具中点击上传
2. 登录微信公众平台提交审核
3. 审核通过后发布

## 许可证

MIT License

## 联系方式

如有问题，请提交 Issue 或联系开发团队。
