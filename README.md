
# 介绍

这是一个使用 java 操作 mongodb 的项目。

# 注意事项

本项目仅供学习交流，请不要在生产环境中使用。

# 测试数据1

测试数据是抓取 [https://springboot.io/](https://springboot.io/) 网站的标题数据。

抓取数据代码 : [src/main/java/com/xh/mongodb/data/article/Crawl.java](https://github.com/ChaseDreamBoy/mongodb-use/blob/master/src/main/java/com/xh/mongodb/data/article/Crawl.java)

# 测试数据2

测试数据是抓取 [http://mongoing.com/anspress?ap_s=&sort=newest](http://mongoing.com/anspress?ap_s=&sort=newest) 网站的数据。

抓取数据代码 : [src/main/java/com/xh/mongodb/data/article/MongoCrawl.java](https://github.com/ChaseDreamBoy/mongodb-use/blob/master/src/main/java/com/xh/mongodb/data/article/MongoCrawl.java)

### 抓取数据到 mysql

修改 `application.yml` 中的数据库配置，然后在对应数据库中运行 `resources/db/schema.sql` 中创建 article 表的 sql 语句。

然后运行测试用例 : [src/test/java/com/xh/mongodb/data/article/CrawlTest.java](https://github.com/ChaseDreamBoy/mongodb-use/blob/master/src/test/java/com/xh/mongodb/data/article/CrawlTest.java)

# 搭建一个单独的 mongodb 测试库

参照 : [安装 mongodb](md/install/install.md)

