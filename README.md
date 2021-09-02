# JalorX


JalorX是集成业界流行和成熟的技术框架和良好用户体验的应用快熟开发框架

## 1.引言
* JalorX基于Micronaut底层框架技术，完成单体到分布式服务的快速构建开发
* 可以与Spring-*家族完美结合
* 运行时采用dapr架构
* 原生支持nomad调度和consul服务注册
* 使用gradle完成构建

### 1.1	愿景使命    
- 不是简单的技术框架堆砌而成的脚手架，而是提供高效的业务开发框架
- 把应用系统从纷繁复杂的框架选型，基础功能开发等重复劳动中解放出来，从而聚焦业务本身的功能

### 1.2	框架介绍    
[Micronaut](https://micronaut.io)是一个现代的、基于jvm的、完整的堆栈java框架，用于构建模块化的、易于测试的jvm应用程序，支持java、kotlin和groovy语言。

使用Microaut，您可以构建消息驱动的应用程序、命令行应用程序、http服务器等，而对于微服务，特别是microaut还提供：

* 分布式配置
* 服务发现
* http路由
* 客户端负载均衡

## 2.设计原则
### 2.1	轻量
技术选型及架构上避免选择重型的框架和架构
### 2.2	前后端分离
前端完成UI相关的动作，服务端只负责数据处理
### 2.3	Rest风格
标准的restful风格架构
### 2.4	约定俗成
约定优先，规则优先（接口定义，命名等等）。统一代码风格。
### 2.5	移动互联
### 2.6	支持分布式部署
### 2.7	面向接口
### 2.8	高内聚低耦合

## 3.本地开发

### Eclipse and Gradle

导入jalor代码格式化规范（eclipse）

```
Windows->Preferences->Java->Code Style->Formatter->Import...
```
 
 选择导入eclipse-java-jalor-style.xml文件

### IntelliJ IDEA

[-] TODO

从源代码生成，签出代码并运行:
  
```
./jalorx-generator-plugin/gradlew publishToMavenLocal
./gradlew publishToMavenLocal

```


### 3.1 jalorx-boot 

框架核心类库


### 3.2 jalorx-services 

基础服务套件集


### 3.2 jalorx-starters 

扩展插件集


### 3.2 jalorx-demo

demo示例

1.安装dolt工具(https://docs.dolthub.com/introduction/quickstart)

2.下载jalorx数据库

```
dolt clone chenjpu/jalorx
```
3.启动数据库库

```
dolt sql-server -p=123
```
4.启动应用


```
./gradlew run

```
5.浏览器访问

```
http://127.0.0.1:8080

user: root
passward : 123
```
**1、导入jalorx-demo工程之前，需要demo工程根目录运行  gradle eclipse**

**2、出现非预期的行为，可以通过根目录运行gradle eclipse和项目中Refresh Gradle Project刷新解决**

