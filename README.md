<p align="center">
  <a target="_blank" href="https://nacos.io/en-us/"><img src="https://img.shields.io/badge/Nacos-0.2.1-blue.svg" alt="Nacos"></a>
  <a><img src="https://img.shields.io/badge/Spring%20Cloud-%20Finchley.SR2-brightgreen.svg" alt="SpringCloud"></a>
  <a><img src="https://img.shields.io/badge/Spring%20Boot-2.0.6-brightgreen.svg" alt="SpringBoot"></a>
  <a><img src="https://img.shields.io/badge/Redis-orange.svg"></a>
  <a><img src="https://img.shields.io/badge/RabbitMq-orange.svg"></a>
  <a target="_blank" href="https://www.iviewui.com/docs/guide/install"><img src="https://img.shields.io/badge/iview-3.1.3-brightgreen.svg?style=flat-square" alt="iview"></a>
  <a><img src="https://img.shields.io/badge/vue-2.5.10-brightgreen.svg?style=flat-square" alt="vue"></a>
  <a><img src="https://img.shields.io/npm/l/express.svg" alt="License"></a>
</p>  

# open-cloud
---

<a target="_blank" href="https://gitee.com/liuyadu/open-admin-ui">开放平台运营系统UI</a>

<a target="_blank" href="http://39.106.187.125/admin">在线访问</a>
默认登录账号:admin 123456
#### 简介
搭建基于OAuth2的开放云平台、为APP端提供统一接口管控平台、为第三方合作伙伴的业务对接提供授信可控的技术对接平台

#### 功能
![Alt text](/docs/云服务开放平台.png)

#### 模块
``` lua
open-cloud
├── docs
    ├── bin           -- 执行脚本  
    ├── generator     -- 代码生成器  
    ├── sql           -- sql文件  
├── opencloud-common  -- 公共模块(提供微服务相关依赖包、springCloud必要配置类、工具类、统一全局异常解析)
├── opencloud-gateway 
     ├── opencloud-gateway-client    -- 开放API网关(对外接口)
     ├── opencloud-gateway-producer  -- 开放API网关(资源服务器)(port = 8888)  
├── opencloud-upms
     ├── opencloud-base-client    -- 基础服务(对外接口)
     ├── opencloud-base-producer  -- 基础服务(资源服务器)(port = 8233)  
     ├── opencloud-auth-client    -- 认证服务(对外接口)
     ├── opencloud-auth-producer  -- 认证服务(认证授权器)(资源服务器)(port = 8211)  
├── opencloud-zipkin  -- 链路追踪 
├── opencloud-msg     -- 待开发...  
      ├── opencloud-msg-client    -- 消息服务(对外接口)
      ├── opencloud-msg-producer  -- 消息服务(资源服务器)(port = 8266)  
├── opencloud-acms    -- 待开发...  
      ├── opencloud-acms-client   -- APP内容管理(对外接口)
      ├── opencloud-acms-producer -- APP内容管理(资源服务器) (port = 8255)
```

#### 部署
``` lua
-- 依赖服务Nacos服务发现 
-- 依赖服务Redis,RabbitMq 

-- 修改主pom.xml
<config.server-addr>127.0.0.1:8848</config.server-addr> -- 配置中心地址
<discovery.server-addr>127.0.0.1:8848</discovery.server-addr> -- 服务发现地址
<auth.server-addr>http://localhost:8211</auth.server-addr> -- 认证授权地址
<gateway.server-addr>http://localhost:8888</gateway.server-addr>  -- 网关服务地址

-- 多环境打包(dev|test|online)
mvn clean install package -P dev

-- 项目启动 (start|stop|restart|status)
./startup.sh start open-base-producer.jar
./startup.sh start open-auth-producer.jar
./startup.sh start open-gateway-producer.jar
```
#### 问题反馈
    学习交流群：<img src="/docs/1548831206525.png">760809808
    邮箱：515608851@qq.com    

#### 项目进度
    项目部分功能还在完善中，尚未正式发版。基础功能基本完善。
