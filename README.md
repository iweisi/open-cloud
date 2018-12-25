# 微服务开放平台
---
**开放平台运维系统**  

<a target="_blank" href="https://gitee.com/liuyadu/open-cloud-ui">后台UI代码仓库</a>

<a target="_blank" href="https://gitee.com/liuyadu/open-cloud-ui">在线访问</a>

#### 简介
搭建基于OAuth2的开放云平台、为APP端提供统一接口管控平台、为第三方合作伙伴的业务对接提供授信可控的技术对接平台

#### 技术架构
- springCloud Finchley.RELEASE  
- springBoot 2.0.4.RELEASE  
- redis  
- rabbitmq  
- mysql  
- 阿里巴巴nacos  <a target="_blank" href="https://nacos.io/en-us/">Nacos服务发现和配置中心</a> 
- vue.js  <a target="_blank" href="https://www.iviewui.com/docs/guide/install">ivew前端UI框架</a>
 
#### 功能
![Alt text](/docs/云服务开放平台.png)

#### 模块
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

#### 打包部署
``` lua
-- 安装并启动nacos服务发现 

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
   

