# Spring Boot 3.x 日志使用
演示在Spring Boot工程项目中使用Log4j2来记录日志的使用样例。

## 开发环境
| 开发工具      | 版本号    |
| ------------- |--------|
| Java          | 21     |
| Maven         | 3.9.9  |
| Spring Boot   | 3.5.0  |
| IntelliJ IDEA | 2025.1 |

## Log4j2 2.24

[Log4j2](https://logging.apache.org/log4j/2.x/manual/implementation.html)中主要的配置元素

| 配置元素  | 描述                                                         |
| --------- | ------------------------------------------------------------ |
| Loggers   | 日志记录管道的入口。配置指定日志的级别和发送到哪些Appender   |
| Appenders | 日志记录管道的出口。决定将日志发送到哪些资源，如控制台、文件等 |
| Layouts   | 告诉Appenders如何格式化日志：文本、JSON、XML等               |

Loggers配置

| 属性            | 类型    | 描述                                                         |
| --------------- | ------- | ------------------------------------------------------------ |
| name            | String  | 指定记录器配置的名称                                         |
| level           | Level   | 日志级别<br/>`OFF`不会记录任何事件<br/>`FATAL`将阻止应用程序继续的致命事件<br/>`ERROR`应用程序中的错误，可能可恢复<br/>`WARN`可能导致错误的事件<br/>`INFO`用于信息目的的事件<br/>`DEBUG`常规调试事件<br/>`TRACE`精细的调试消息，通常捕获通过应用程序的流<br/>`ALL`应记录所有事件 |
| additivity      | boolean | 如果为 true（默认）则消息也将传输到其父记录器，false 则只记录到当前记录器 |
| includeLocation | boolean | 位置信息。默认值为true（使用异步AsyncRoot或AsyncLogger则默认值为false） |

AppenderRef配置

| 属性  | 类型   | 描述                                       |
| ----- | ------ | ------------------------------------------ |
| ref   | String | 指定要使用的 appender 的名称               |
| level | Level  | 指定日志级别低于此设置的日志信息将被过滤掉 |

Appenders之Console Appender控制台输出

| 属性             | 类型    | 默认值        | 描述                                                         |
| ---------------- | ------- | ------------- | ------------------------------------------------------------ |
| name             | String  | 无            | Appender的名称                                               |
| bufferSize       | int     | 8192（即8KB） | 字节缓冲区，缓冲区满了之后再写出目的地                       |
| direct           | boolean | false         | 如果设置为true，会绕过System.out或System.err的缓冲直接写入FileDescriptor.out或FileDescriptor.err。正常是通过使用System.out或System.err再写入FileDescriptor.out或FileDescriptor.err |
| ignoreExceptions | boolean | true          | 忽略异常。如果设置为false将转发给调用者                      |
| immediateFlush   | boolean | true          | 立即刷新缓冲区同步数据到目的地                               |

Appenders之File Appender文件输出，部分通用属性配置

| 属性             | 类型    | 默认值        | 描述                                     |
| ---------------- | ------- | ------------- | ---------------------------------------- |
| name             | String  | 无            | Appender的名称                           |
| fileName         | String  | 无            | 文件名，路径中如果目录不存在则会自动创建 |
| bufferSize       | int     | 8192（即8KB） | 字节缓冲区，缓冲区满了之后再写出目的地   |
| ignoreExceptions | boolean | true          | 忽略异常。如果设置为false将转发给调用者  |
| immediateFlush   | boolean | true          | 立即刷新缓冲区同步数据到目的地           |

Appenders之RollingFile Appender滚动文件输出，部分通用属性配置

| 属性             | 类型    | 默认值        | 描述                                                         |
| ---------------- | ------- | ------------- | ------------------------------------------------------------ |
| filePattern      | String  | 无            | 存档文件的模式。如果fileName没有配置，则此模式也用于当前日志文件的命名 |
| fileName         | String  | 无            | 文件名，路径中如果目录不存在则会自动创建                     |
| name             | String  | 无            | Appender的名称                                               |
| bufferSize       | int     | 8192（即8KB） | 字节缓冲区，缓冲区满了之后再写出目的地                       |
| ignoreExceptions | boolean | true          | 忽略异常。如果设置为false将转发给调用者                      |
| immediateFlush   | boolean | true          | 立即刷新缓冲区同步数据到目的地                               |

## Spring Boot 3 + Log4j 2.24

Spring Boot 3 默认使用Logback+SLF4J作为日志实现，使用log4j作为日志记录器首先需要排除logback的`spring-boot-starter-logging`日志依赖，然后引入log4j依赖`spring-boot-starter-log4j2`。

```
<!--剔除logback日志启动器-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!--引入log4j2日志启动器-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-log4j2</artifactId>
</dependency>
```



## 启动应用

执行Log4j2Application.java主程序，访问路径

| URL样例地址       | 描述         |
| ----------------- | ------------ |
| http://localhost/ | 返回欢迎信息 |

```
curl http://localhost/
```

查看控制台及各个日志文件信息
