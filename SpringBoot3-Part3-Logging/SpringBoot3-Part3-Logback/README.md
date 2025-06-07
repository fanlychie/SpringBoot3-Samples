# Spring Boot 3.x 日志使用
演示在Spring Boot工程项目中使用Logback来记录日志的使用样例。

## 开发环境
| 开发工具      | 版本号    |
| ------------- |--------|
| Java          | 21     |
| Maven         | 3.9.9  |
| Spring Boot   | 3.5.0  |
| IntelliJ IDEA | 2025.1 |

## Logback 1.5

Logback中三大类型的组件Logger、Appender、Layout。Logger是日志的记录器，Appender是日志输出的目的地，Layout是用户自定义日志的格式。

1、**Logger**可以通过`LoggerFactory.getLogger()`静态方法获取；

2、**Appender**常用的有`ch.qos.logback.core.ConsoleAppender`（输出到控制台）、`ch.qos.logback.core.FileAppender`（输出到文件）、`ch.qos.logback.core.rolling.RollingFileAppender`（滚动式生成文件）。

RollingFileAppender之滚动策略TimeBasedRollingPolicy（基于时间的滚动策略）

| 属性名称            | 属性类型 | 描述                                                         |
| ------------------- | -------- | ------------------------------------------------------------ |
| fileNamePattern     | String   | 文件名模式，使用%d转换符，遵循java.text.SimpleDateFormat模式，默认%d为yyyy-MM-dd格式 |
| maxHistory          | int      | 保留最近历史文件的最大数量，超出这个阈值的最旧文件会被异步删除。如果设置成0则不会删除历史文件，默认值为0 |
| totalSizeCap        | int      | 文件（所有文件包括历史文件）的总大小。当超过总大小上限时，将异步删除最旧的文件。单位可以使用KB、MB和GB，如果不带后缀的值则以字节为单位，默认为0即没有总大小上限。设置此项要求必须设置maxHistory项 |
| cleanHistoryOnStart | boolean  | 在启动时删除历史文件，默认为false即不删除                    |

其中，按fileNamePattern滚动文件计划效果

| fileNamePattern示例         | 滚动计划                       |
| --------------------------- | ------------------------------ |
| foo.%d                      | 默认yyyy-MM-dd模式，即按日滚动 |
| %d{yyyy/MM}/foo.log         | 按月滚动，每月月初             |
| foo.%d{yyyy-ww}.log         | 按周滚动，每周的第一天         |
| foo%d{yyyy-MM-dd_HH}.log    | 每小时结束后滚动               |
| foo%d{yyyy-MM-dd_HH-mm}.log | 每分钟开始时滚动               |

RollingFileAppender之滚动策略SizeAndTimeBasedRollingPolicy（基于大小和时间的滚动策略），该策略基于TimeBasedRollingPolicy扩展的属性列表：

| 属性名称    | 属性类型 | 描述                                                         |
| ----------- | -------- | ------------------------------------------------------------ |
| maxFileSize | FileSize | 每当当前日志文件在当前时间段结束之前达到maxFileSize时，它将被存档，索引用`%i`表示且从0开始。单位可以使用KB、MB和GB，如果不带后缀的值则以字节为单位 |

注：SizeAndTimeBasedRollingPolicy（基于大小和时间的滚动策略）的`%i`和`%d`转换符都是必需使用的。

3、**Layout**常用的是`PatternLayout`可以灵活定义日志格式，常用的有：

| 转换符                       | 描述                                                         | 示例<br/>mainPackage.sub.sample.Bar                          | 描述                                                         |
| ---------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ |
| c{length}<br/>logger{length} | 输出记录日志的Logger名称。名称中最右边的单词（类名）永远不会缩写，其他段（包名）最多可以缩短为单个字符，但永远不会删除。 | %logger{0}<br/> %logger{10} <br/>%logger{15} <br/>%logger{26} | Bar<br/>m.s.s.Bar<br/>m.s.sample.Bar<br/>mainPackage.sub.sample.Bar |
| C{length}<br/>class{length}  | 输出发出日志记录请求的调用方的完全限定类名                   | 【规则和上面logger一样】                                     |                                                              |
| d{pattern}<br/>date{pattern} | 用于输出日志记录事件的日期。模式语法与`java.text.SimpleDateFormat`格式兼容 （在 logback 1.2.x 中）和`java.time.format.DateTimeFormatter`（在 LOGBack 1.3.x 中） | %d{yyyy-MM-dd HH:mm:ss.SSS}                                  | 2025-06-05 23:00:40.134                                      |
| L<br/>line                   | 输出发出日志记录请求的行号                                   | %line                                                        | 20                                                           |
| m<br/>msg<br/>message        | 输出应用程序提供的与日志记录事件关联的消息                   | %msg                                                         | LogbackApplication started!                                  |
| M<br/>method                 | 输出发出日志记录请求的方法名称                               | %method                                                      | run                                                          |
| p<br/>le<br/>level           | 输出日志记录事件的级别                                       | %-5level                                                     | INFO                                                         |
| t<br/>thread                 | 输出生成日志记录事件的线程的名称                             | %15thread                                                    | Thread-5                                                     |
| n                            | 输出与平台相关的行分隔符或字符                               | %n                                                           |                                                              |

数据段宽度和对齐方式，以logger为示例，`%[-][a][.b]logger`，其中`-`表示左对齐否则右对齐，`a`表示最小宽度，`.b`表示最大宽度

| 示例<br/>org.fanlychie.springboot3.part3.LogbackApplication | 结果                                                   |
| ----------------------------------------------------------- | ------------------------------------------------------ |
| [%52logger]                                                 | [  org.fanlychie.springboot3.part3.LogbackApplication] |
| [%-52logger]                                                | [org.fanlychie.springboot3.part3.LogbackApplication  ] |
| [%.48logger]                                                | [g.fanlychie.springboot3.part3.LogbackApplication]     |
| [%.60logger]                                                | [org.fanlychie.springboot3.part3.LogbackApplication]   |
| [%52.60logger]                                              | [  org.fanlychie.springboot3.part3.LogbackApplication] |
| [%-52.60logger]                                             | [org.fanlychie.springboot3.part3.LogbackApplication  ] |

## Spring Boot 3 + Logback 1.5

Spring Boot 3 默认使用Logback+SLF4J作为日志实现，同时支持Log4j2和JUL（Java Util Logging）。在默认情况下，如果使用了starter启动器，则会使用Logback进行日志记录，就不需要再显示的声明`spring-boot-starter-logging`日志依赖，因为`spring-boot-starter`已经依赖了这个启动器。

## 启动应用

执行LogbackApplication.java主程序，访问路径

| URL样例地址       | 描述         |
| ----------------- | ------------ |
| http://localhost/ | 返回欢迎信息 |

```
curl http://localhost/
```

查看控制台及各个日志文件信息
