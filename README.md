# 🎓 校园时光胶囊 - 后端项目

这是一个基于 Spring Boot 3 (Java 21) 和 MyBatis-Plus 构建的后端项目，为前端提供 API 服务。

## ✨ 技术栈

- **后端框架**: Spring Boot 3.5.9 (Java 21)
- **数据库**: MySQL 8.x
- **ORM 框架**: MyBatis-Plus 3.5.5
- **依赖管理**: Maven
- **辅助工具**: Lombok (简化代码)

## 🚀 运行项目

1.  **数据库设置**:
    - 确保 MySQL 已安装并运行。
    - 执行 `docs/sql/schema.sql` (如果存在) 或按照文档创建 `Time_Capsule` 数据库及 `user`, `capsule` 表。
    - 更新 `src/main/resources/application.yml` 中的数据库连接信息（用户名、密码）。

2.  **构建项目**:
    ```bash
    mvn clean package
    ```
    (在 IDEA 中直接点击 Maven 面板的 `clean` 和 `package` 也可以)

3.  **启动应用程序**:
    在 IDEA 中直接运行 `BackendApplication.java`。
    或者通过命令行运行 Jar 包 (推荐在服务器上部署时使用)：
    ```bash
    java -jar target/backend-0.0.1-SNAPSHOT.jar
    ```
    项目将在 `http://localhost:8081` 端口启动。

## 📁 项目结构