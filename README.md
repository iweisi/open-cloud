#### 搭建基于OAuth2的开放云平台
1. 搭建基于API的生态体系 
2. 利用开放平台，搭建基于计费的API数据平台 
3. 为APP端提供统一接口管控平台
4. 为第三方合作伙伴的业务对接提供授信可控的技术对接平台
#### 技术架构
   全新升级spring boot2.0+ 稳定版 
   springCloud  Finchley.RELEASE  
   springBoot   2.0.4.RELEASE  
   redis  
   rabbitmq  
   mysql  
   alibaba  [nacos](https://nacos.io/en-us/) 服务发现和配置中心   
   [layui](https://www.layui.com/) 前端UI框架     
  
#### 项目结构
* docs(文档说明)
    1. bin          执行脚本
    2. generator    代码生成器
    3. sql          sql文件
* opencloud-common(公共模块)
    1. 提供微服务相关依赖包
    2. 封装微服务必要配置类并自动注入
    3. 工具类、全局异常解析、自定义错误码(支持国际化)、自定义属性配置、
* opencloud-gateway(API网关)
   1. opencloud-gateway-client    API网关-接口
   2. opencloud-gateway-producer  API网关-资源服务器 (port = 8888)
      1. 提供接口调用统一入口:路由代理、API限流、API访问限制、API数字验签、API开发调试文档(生产环境不生效)
      2. 提供基于oauth2密码模式登录,返回access_token
      3. 提供内部数字签名接口
      3. 自定义刷新网关端点(支持一键刷新路由、限流等配置) 
        [刷新路由/actuator/refresh-gateway](POST http://localhost:8888/actuator/refresh-gateway )  
        [灰度刷新/actuator/refresh-gateway?destination=application:* ](POST http://localhost:8888/actuator/refresh-gateway?destination=application:* )
* opencloud-upms(权限认证模块)
   1. opencloud-base-client    基础服务-接口
   2. opencloud-base-producer  基础服务-资源服务器 (port = 8233)
      1. 平台系统用户资料和登录账号管理
      2. 角色管理、授权
      3. 菜单资源、操作资源、API资源管理
      4. 应用管理、授权
   3. opencloud-auth-client   认证管理-接口
   4. opencloud-auth-producer 认证管理-认证授权器和资源服务器 (port = 8211)
      1. 身份认证、授权,颁发有效令牌
      2. SSO单点认证登录,系统用户登录信息
      3. 客户端、令牌、授权管理
  
* opencloud-zipkin(微服务链路追踪)
* opencloud-msg(消息管理) 开发中...
   1. opencloud-msg-client   消息服务-接口
   2. opencloud-msg-producer 消息服务-资源服务器 (port = 8266)
      1. 邮件消息
      2. 短信消息
      3. 推送消息
* opencloud-acms(APP内容管理) 开发中...
   1. opencloud-acms-client   APP内容管理-接口
   2. opencloud-acms-producer APP内容管理-资源服务器 (port = 8255)
      1. 版本升级
      2. 闪屏管理
      3. banner管理
      3. 广告管理
      4. 等...
              

#### 项目配置
支持多环境(dev、test、online):修改主项目pom.xml中的profiles节点  
修改关键配置项:  
   1. 配置中心地址:<code><config.server-addr>127.0.0.1:8848</config.server-addr></code>  
   2. 服务发现地址:<code><discovery.server-addr>127.0.0.1:8848</discovery.server-addr></code>  
   3. 认证授权地址:<code><auth.server-addr>http://localhost:8211</auth.server-addr></code>  
   4. 网关服务地址:<code><gateway.server-addr>http://localhost:8888</gateway.server-addr></code>  
#### 项目部署
打包不同环境:<code>mvn clean install package -P {dev|test|online}</code>  
启动脚本:<code>./start.sh {start|stop|restart|status} {service}.jar</code>    
启动顺序:   
   1. 安装并启动nacos服务发现  
   2. opencloud-base-producer  
   3. opencloud-auth-producer  
   4. opencloud-gateway-producer  
访问API网关:[http://localhost:8888/](http://localhost:8888/)  

   

