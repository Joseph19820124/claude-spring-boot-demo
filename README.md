# Spring Boot Todo List 应用

这是一个使用Spring Boot和SQLite数据库构建的简单to-do list web应用程序。

## 功能特性

- ✅ 添加新任务
- ✅ 查看所有任务
- ✅ 编辑任务
- ✅ 删除任务
- ✅ 标记任务为完成/未完成
- ✅ 按状态过滤任务（全部/已完成/未完成）
- ✅ 搜索任务
- ✅ 响应式设计（支持移动设备）

## 技术栈

- **后端**: Spring Boot 3.2.0
- **数据库**: SQLite
- **ORM**: Spring Data JPA / Hibernate
- **模板引擎**: Thymeleaf
- **前端**: Bootstrap 5 + Font Awesome
- **构建工具**: Maven
- **Java版本**: 17

## 项目结构

```
src/
├── main/
│   ├── java/com/example/todolist/
│   │   ├── TodoListApplication.java          # 主应用类
│   │   ├── controller/
│   │   │   └── TodoController.java           # Web控制器
│   │   ├── model/
│   │   │   └── Todo.java                     # 实体类
│   │   ├── repository/
│   │   │   └── TodoRepository.java           # 数据访问层
│   │   └── service/
│   │       └── TodoService.java              # 业务逻辑层
│   └── resources/
│       ├── application.properties            # 应用配置
│       └── templates/
│           ├── index.html                    # 主页模板
│           └── edit.html                     # 编辑页面模板
└── test/                                     # 测试代码
```

## 快速开始

### 前置要求

- Java 17 或更高版本
- Maven 3.6 或更高版本

### 运行步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/Joseph19820124/claude-spring-boot-demo.git
   cd claude-spring-boot-demo
   ```

2. **编译项目**
   ```bash
   mvn clean compile
   ```

3. **运行应用**
   ```bash
   mvn spring-boot:run
   ```

4. **访问应用**
   
   打开浏览器访问: http://localhost:8080

### 使用Maven构建可执行JAR

```bash
mvn clean package
java -jar target/todo-list-0.0.1-SNAPSHOT.jar
```

## 数据库

应用使用SQLite作为数据库，数据库文件会自动创建在项目根目录下（`todolist.db`）。

### 数据库表结构

```sql
CREATE TABLE todos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at DATETIME NOT NULL,
    updated_at DATETIME
);
```

## API端点

| 方法 | 路径 | 描述 |
|------|------|------|
| GET | `/` | 显示所有任务（支持过滤） |
| POST | `/add` | 添加新任务 |
| POST | `/toggle/{id}` | 切换任务状态 |
| POST | `/delete/{id}` | 删除任务 |
| GET | `/edit/{id}` | 显示编辑页面 |
| POST | `/update/{id}` | 更新任务 |
| GET | `/search?query=keyword` | 搜索任务 |

## 配置说明

主要配置在 `src/main/resources/application.properties`:

```properties
# SQLite数据库配置
spring.datasource.url=jdbc:sqlite:todolist.db
spring.datasource.driver-class-name=org.sqlite.JDBC
spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect

# JPA/Hibernate配置
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# 服务器配置
server.port=8080
```

## 开发说明

### 添加新功能

1. 在`Todo`实体类中添加新字段
2. 在`TodoRepository`中添加查询方法
3. 在`TodoService`中添加业务逻辑
4. 在`TodoController`中添加Web端点
5. 更新Thymeleaf模板

### 自定义样式

修改模板文件中的CSS或者添加新的静态资源文件到 `src/main/resources/static/` 目录。

## 许可证

本项目采用MIT许可证。详见LICENSE文件。

## 贡献

欢迎提交Issue和Pull Request来改进这个项目！

## 联系

如有问题，请通过GitHub Issues联系。
