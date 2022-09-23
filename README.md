该项目为图书管理系统，可以对图书、图书类型、作者类型、出版社类型和用户进行管理。可以对图书进行借阅和归还。

1.该项目前端使用vue搭建，后端使用springboot框架，数据库为H2数据库

2. 在src/main/resources/sql 中初始化了数据库以及添加了测试数据，重跑项目时要将application.yml文件中的“新增测试数据”部分注释掉，避免产生冲突

3. 用户权限分为普通用户、员工和管理员三类用户
    
    2.1 普通用户只有查询的权限，可以对自己的信息进行修改（头像部分的功能还没有实现，现在所有用户都是统一头像）

    2.2 员工查询权限和对图书的借阅和归还权限

    2.3 管理员有查询、修改、删除和新增权限

4. 后端启用了日志Slf4j，对出入参，以及中间sql语句的执行都做了说明

5. 项目启动后，使用"http://localhost:8080/login"访问登录页面，使用“http://localhost:8087/swagger-ui.html”可以访问swagger，“http://localhost:8087/h2”访问数据库
    
    5.1 管理员账户密码：admin/admin
    
    5.2: 员工账号密码：w962104789/67813831
    
    5.3: 用户账号密码：u962104789/67813831

