# 项目说明
* code-generator是个人开源项目的代码生成器，支持在线编辑动态模板
* 可在线生成model、dto、xml、dao、service、html、controller甚至前端代码，减少70%以上的开发任务

# 本地部署
* 通过git下载源码
* 安装lombok插件
* 修改application.yml，更新MySQL账号和密码、数据库名称
* Eclipse、IDEA运行com.hwy.CodeGeneratorApplication.java，则可启动项目
* 项目访问路径：http://localhost:8080

# 使用教程
1. 用 sql/init.sql 生成表结构
2. 修改 application.yml 数据库连接，然后启动com.hwy.CodeGeneratorApplication.java
3. 看主页帮助文档 （配置模板时用到的系统参数）
4. 菜单：
```
 模板组 - > 添加 （配置模板组）
 模板 - > 添加 （配置模板并绑定所属模板组）
 数据源 - > 添加 （配置数据源）
 表列表 - > 选择数据源 - > 生成代码 - > 选择模板组 - > 生成代码 （根据数据源查对应的表列表，并选择使用的模板组，然后生成代码）
```