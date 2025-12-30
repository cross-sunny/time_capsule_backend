#### 2. 后端文档 (`backend/README.md`)


```markdown
# ☕ 时光胶囊 - 后端工程 (Spring Boot 3)

## 🛠 技术栈
- **核心框架**: Spring Boot 3.2.x (Java 21)
- **数据库**: MySQL 8.0 (MyBatis-Plus)
- **核心功能**: 邮件发送 (JavaMail), 定时任务 (@Scheduled), 文件上传

## ⚙️ 环境配置
在 `application.yml` 中配置：
- MySQL 连接信息 (注意区分本地和云端配置)
- SMTP 邮件服务 (推荐使用 465 SSL 端口)

## 🚀 部署说明
本项目集成了 **Shell 运维脚本**。
服务器端脚本位置: `/home/time_capsule/sh/restart.sh`
本地更新请直接运行根目录下的 `一键更新部署.bat`。