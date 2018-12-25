# 微服务开放平台
---
**基于vue的前后分离开发的运维管理系统**      
[开放平台运维系统UI代码](https://gitee.com/liuyadu/open-cloud-ui)  
#### 前言
- 搭建基于OAuth2的开放云平台、 
- 搭建基于计费的API数据平台、 
- 为APP端提供统一接口管控平台、 
- 为第三方合作伙伴的业务对接提供授信可控的技术对接平台
#### 技术架构
- springCloud Finchley.RELEASE  
- springBoot 2.0.4.RELEASE  
- redis  
- rabbitmq  
- mysql  
- 阿里巴巴nacos  [Nacos服务发现和配置中心](https://nacos.io/en-us/) 
- vue.js [ivew前端UI框架](https://www.iviewui.com/docs/guide/install)
#### 项目结构
![Alt text](/docs/云服务开放平台.png)
#### 模块说明
``` lua
open-cloud
├── docs -- 文档说明
├── bin           -- 执行脚本  
├── generator     -- 代码生成器  
├── sql           -- sql文件  
├── opencloud-common -- 公共模块(提供微服务相关依赖包、springCloud必要配置类、工具类、统一全局异常解析)
├── opencloud-gateway -- 开放API
     ├── opencloud-gateway-client    -- 开放API网关(对外接口)
     ├── opencloud-gateway-producer  -- 开放API网关(资源服务器)(port = 8888)  
├── opencloud-upms -- 权限认证模块
     ├── opencloud-base-client    -- 基础服务(对外接口)
     ├── opencloud-base-producer  -- 基础服务(资源服务器)(port = 8233)  
     ├── opencloud-auth-client    -- 认证服务(对外接口)
     ├── opencloud-auth-producer  -- 认证服务(认证授权器)(资源服务器)(port = 8211)  
├── opencloud-zipkin -- 链路追踪 
├── opencloud-msg  -- 消息服务 待开发...  
      ├── opencloud-msg-client    -- 消息服务(对外接口)
      ├── opencloud-msg-producer  -- 消息服务(资源服务器)(port = 8266)  
├── opencloud-acms -- APP内容管理 待开发...  
      ├── opencloud-acms-client   -- APP内容管理(对外接口)
      ├── opencloud-acms-producer -- APP内容管理(资源服务器) (port = 8255)
```
#### 部署打包
``` lua
 -- 打包前配置,修改主pom.xml中profiles配置属性
 <config.server-addr>127.0.0.1:8848</config.server-addr> -- 配置中心地址
 <discovery.server-addr>127.0.0.1:8848</discovery.server-addr> -- 服务发现地址
 <auth.server-addr>http://localhost:8211</auth.server-addr> -- 认证授权地址
 <gateway.server-addr>http://localhost:8888</gateway.server-addr>  -- 网关服务地址
 -- 多环境打包(dev|test|online)
    mvn clean install package -P dev
 -- 项目启动
    ./startup.sh {start|stop|restart|status} {service}.jar
 -- 启动顺序   
   1. 安装并启动nacos服务发现  
   2. open-base-producer  
   3. open-auth-producer  
   4. open-gateway-producer  
```

   

