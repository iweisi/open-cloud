#### 搭建基于OAuth2的开放云平台
1. 搭建基于API的生态体系 
2. 利用开放平台，搭建基于计费的API数据平台 
3. 为APP端提供统一接口管控平台
4. 为第三方合作伙伴的业务对接提供授信可控的技术对接平台
#### 技术架构
   版本全面升级spring boot2.0+  
   springCloud  Finchley.RELEASE  
   springBoot   2.0.4.RELEASE  
   redis  
   rabbitmq  
   mysql  
   alibaba  [nacos](https://nacos.io/en-us/) 服务发现和配置中心   
   [layui](https://www.layui.com/) 前端UI框架     
  
   
    对于市面上很多项目,片面演示vue.js和oauth2前后分离开发，忽略了数据安全问题、  
    1. 不能直接在vue.js中使用oauth2密码模式。使用basic方式认证,将会暴露clientId,clientSecret.
    2. 提供nodejs或javaWeb等中间服务器保证秘钥安全.提供基于oauth2(password)登录接口、提供参数签名接口、
    3. 使用code模式跳转到认证服务统一登陆,获取code码,再去获取access_token
#### 项目结构
* docs(文档说明)
    1. bin          执行脚本
    2. generator    代码生成器
    3. sql          sql文件
* opencloud-admin  开放平台运维系统-UI (port = 8801)
    1. 服务器:基于oauth2单点登录,提供参数签名(保证秘钥安全)
    2. 前端:基于layui前端UI框架,ajax调用API网关获取数据
* opencloud-common(公共包)
    1. 提供微服务相关依赖包
    2. 封装微服务必要配置类并自动注入
    3. 工具类、全局异常解析、自定义错误码(支持国际化)、自定义属性配置、
* opencloud-gateway(API网关)
   1. opencloud-gateway-client    API网关-接口
   2. opencloud-gateway-producer  API网关-资源服务器 (port = 8888)
      1. 提供统一接口调用:路由代理、API限流、API鉴权、API验证签名、API开发调试文档(生产环境不生效)
      2. 自定义一键刷新网关配置(支持路由配置、限流配置、验签白名单...) 
        [刷新路由/actuator/refresh-gateway](POST http://localhost:8888/actuator/refresh-gateway )  
        [灰度刷新/actuator/refresh-gateway?destination=application:* ](POST http://localhost:8888/actuator/refresh-gateway?destination=application:* )
* opencloud-zipkin(微服务链路追踪)
* opencloud-upms(权限管理)
   1. opencloud-oauth-client   认证管理-接口
   2. opencloud-oauth-producer 认证管理-认证授权器和资源服务器 (port = 8211)
      1. 颁发客户端令牌和用户令牌
      2. 认证客户端管理
      3. 提供sso认证用户登录信息
   3. opencloud-rbac-client    权限管理-接口
   4. opencloud-rbac-producer  权限管理-资源服务器 (port = 8233)
      1. 用户信息和登录账户管理
      2. 角色管理、授权
      3. 菜单资源、操作资源、API资源管理
      4. 应用管理、授权
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
   2. opencloud-rbac-producer  
   3. opencloud-oauth-producer  
   4. opencloud-gateway-producer  
   5. opencloud-admin  
访问接口文档:[http://localhost:8888/](http://localhost:8888/)  
访问运维后台:[http://localhost:8801/](http://localhost:8801/)

   

