###　输入文件：
sql.sql      (存放建表语句)
comment.sql  (存放注解语句)


### 已发现的BUG：

1. sql.sql中不支持 with 关键字 

"IS_URGENT" CHAR(1) WITH DEFAULT '0'
 
需将with之后的内容删除。

2. sql.sql中不支持 in 关键字

 IN "AASDB_DAT" INDEX IN "AASDB_IDX"
 
需将其全部删除。


该项目是通过 https://github.com/wjsite/sql2excel 改造的。