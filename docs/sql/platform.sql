/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : open-platform

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-29 18:05:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for system_account
-- ----------------------------
DROP TABLE IF EXISTS `system_account`;
CREATE TABLE `system_account` (
  `account_id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户Id',
  `account` varchar(255) DEFAULT NULL COMMENT '标识：手机号、邮箱、 用户名、或第三方应用的唯一标识',
  `password` varchar(255) DEFAULT NULL COMMENT '密码凭证：站内的保存密码、站外的不保存或保存token）',
  `account_type` varchar(255) DEFAULT NULL COMMENT '登录类型:password-密码、mobile-手机号、email-邮箱、weixin-微信、weibo-微博、qq-等等',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账号-用于登陆认证';

-- ----------------------------
-- Records of system_account
-- ----------------------------
INSERT INTO `system_account` VALUES ('521677655368531968', '521677655146233856', 'admin', '$2a$10$A7EHximvrsa4ESX1uSlkJupbg2PLO2StzDzy67NX4YV25MxmbGvXu', 'username');
INSERT INTO `system_account` VALUES ('521677655444029440', '521677655146233856', '515608851@qq.com', '$2a$10$A7EHximvrsa4ESX1uSlkJupbg2PLO2StzDzy67NX4YV25MxmbGvXu', 'email');
INSERT INTO `system_account` VALUES ('521677655586635776', '521677655146233856', '18518226890', '$2a$10$A7EHximvrsa4ESX1uSlkJupbg2PLO2StzDzy67NX4YV25MxmbGvXu', 'mobile');


-- ----------------------------
-- Table structure for system_account_logs
-- ----------------------------
DROP TABLE IF EXISTS `system_account_logs`;
CREATE TABLE `system_account_logs` (
  `id` bigint(20) NOT NULL,
  `login_time` datetime NOT NULL,
  `login_ip` varchar(255) NOT NULL COMMENT '登录Ip',
  `login_agent` varchar(500) NOT NULL COMMENT '登录设备',
  `login_nums` int(11) NOT NULL COMMENT '登录次数',
  `user_id` bigint(20) NOT NULL,
  `account` varchar(100) NOT NULL,
  `account_type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号登录日志';


-- ----------------------------
-- Table structure for system_action
-- ----------------------------
DROP TABLE IF EXISTS `system_action`;
CREATE TABLE `system_action` (
  `action_id` bigint(20) NOT NULL COMMENT '资源ID',
  `action_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源编码',
  `action_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
  `action_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
  `path` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '资源路径',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '资源父节点',
  `priority` int(10) NOT NULL DEFAULT '0' COMMENT '优先级 越小越靠前',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作表';

-- ----------------------------
-- Records of system_action
-- ----------------------------
INSERT INTO `system_action` VALUES ('1', 'btnDetail', '详情', '详情222', '/funcPages/adManager', '2', '0', '1', '2018-07-29 21:20:10', '2018-12-27 13:17:36');
INSERT INTO `system_action` VALUES ('2', 'btnSave', '保存', '保存', '/index', '2', '0', '1', '2018-07-29 21:20:13', '2018-09-25 23:02:11');

-- ----------------------------
-- Table structure for system_api
-- ----------------------------
DROP TABLE IF EXISTS `system_api`;
CREATE TABLE `system_api` (
  `api_id` bigint(20) NOT NULL COMMENT '接口ID',
  `api_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '接口编码',
  `api_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '接口名称',
  `api_category` varchar(20) COLLATE utf8_bin DEFAULT 'default' COMMENT '接口分类:default-默认分类',
  `api_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
  `service_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '服务ID',
  `path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '请求路径',
  `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='API资源表';

-- ----------------------------
-- Records of system_api
-- ----------------------------
INSERT INTO `system_api` VALUES ('528217376529842176', 'client', 'client', 'default', '', 'opencloud-auth-producer', '/client', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:39:18');
INSERT INTO `system_api` VALUES ('528217376626311168', 'getClient', 'getClient', 'default', '', 'opencloud-auth-producer', '/client/{clientId}', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:39:19');
INSERT INTO `system_api` VALUES ('528217376701808640', 'addClient', 'addClient', 'default', '', 'opencloud-auth-producer', '/client/add', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:39:19');
INSERT INTO `system_api` VALUES ('528217376768917504', 'resetSecret', 'resetSecret', 'default', '', 'opencloud-auth-producer', '/client/reset', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:39:19');
INSERT INTO `system_api` VALUES ('528217376886358016', 'removeClinet', 'removeClinet', 'default', '', 'opencloud-auth-producer', '/client/remove', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:39:19');
INSERT INTO `system_api` VALUES ('528217376957661184', 'updateClient', 'updateClient', 'default', '', 'opencloud-auth-producer', '/client/update', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:39:19');
INSERT INTO `system_api` VALUES ('528217378207563776', 'authUser', '当前已认证用户', 'default', '', 'opencloud-auth-producer', '/user', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:39:19');
INSERT INTO `system_api` VALUES ('528217378551496704', 'login', 'login', 'default', '', 'opencloud-base-producer', '/account/login', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217378606022656', 'addLoginLog', 'addLoginLog', 'default', '', 'opencloud-base-producer', '/account/logs/add', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217378677325824', 'getAction', '获取操作资源', 'default', '', 'opencloud-base-producer', '/action/{actionId}', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217378744434688', 'action', '操作列表', 'default', '', 'opencloud-base-producer', '/action', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217378790572032', 'updateStatus', '更新状态', 'default', '', 'opencloud-base-producer', '/role/update/status', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217378840903680', 'actionList', '操作列表', 'default', '', 'opencloud-base-producer', '/action/list', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217378895429632', 'addAction', '添加操作资源', 'default', '', 'opencloud-base-producer', '/action/add', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217378941566976', 'updateAction', '编辑操作资源', 'default', '', 'opencloud-base-producer', '/action/update', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217378991898624', 'removeAction', '移除操作', 'default', '', 'opencloud-base-producer', '/action/remove', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379038035968', 'api', '接口列表', 'default', '', 'opencloud-base-producer', '/api', '0', '1', '2018-12-28 14:27:16', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379197419520', 'getApi', '获取接口资源', 'default', '', 'opencloud-base-producer', '/api/{apiId}', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379260334080', 'updateApi', '编辑Api资源', 'default', '', 'opencloud-base-producer', '/api/update', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379310665728', 'addApi', '添加Api资源', 'default', '', 'opencloud-base-producer', '/api/add', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379360997376', 'apiList', '接口列表', 'default', '', 'opencloud-base-producer', '/api/list', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379411329024', 'removeApi', '移除接口', 'default', '', 'opencloud-base-producer', '/api/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379470049280', 'addApp', '添加应用', 'default', '', 'opencloud-base-producer', '/app/add', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:21');
INSERT INTO `system_api` VALUES ('528217379532963840', 'removeApp', '删除应用', 'default', '', 'opencloud-base-producer', '/app/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217379583295488', 'app', '应用列表', 'default', '', 'opencloud-base-producer', '/app', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217379637821440', 'updateApp', '编辑应用', 'default', '', 'opencloud-base-producer', '/app/update', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217379679764480', 'getApp', '获取应用信息', 'default', '', 'opencloud-base-producer', '/app/{appId}', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217379725901824', 'resetSecret', '重置秘钥', 'default', '', 'opencloud-base-producer', '/app/reset', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217379772039168', 'grantAccess', '获取已授权访问列表', 'default', '', 'opencloud-base-producer', '/grant/access', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217379897868288', 'getMenu', '获取菜单资源', 'default', '', 'opencloud-base-producer', '/menu/{menuId}', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217379960782848', 'updateMenu', '编辑菜单资源', 'default', '', 'opencloud-base-producer', '/menu/update', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380006920192', 'menu', '菜单列表', 'default', '', 'opencloud-base-producer', '/menu', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380053057536', 'menuList', '菜单列表', 'default', '', 'opencloud-base-producer', '/menu/list', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380095000576', 'removeMenu', '移除菜单', 'default', '', 'opencloud-base-producer', '/menu/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380141137920', 'addMenu', '添加菜单资源', 'default', '', 'opencloud-base-producer', '/menu/add', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380241801216', 'role', '角色列表', 'default', '', 'opencloud-base-producer', '/role', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380489265152', 'getRole', '获取角色信息', 'default', '', 'opencloud-base-producer', '/role/{roleId}', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380527013888', 'addRole', '添加角色', 'default', '', 'opencloud-base-producer', '/role/add', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380573151232', 'updateRole', '更新角色', 'default', '', 'opencloud-base-producer', '/role/update', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380753506304', 'removeRole', '删除角色', 'default', '', 'opencloud-base-producer', '/role/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380808032256', 'user', '系统用户列表', 'default', '', 'opencloud-base-producer', '/user', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380854169600', 'addUser', '添加系统用户', 'default', '', 'opencloud-base-producer', '/user/add', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380900306944', 'userGrantMenus', '当前用户可访问菜单资源', 'default', '', 'opencloud-base-producer', '/user/grant/menus', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380938055680', 'updateUser', '更新系统用户', 'default', '', 'opencloud-base-producer', '/user/update', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217380975804416', 'userGrantActions', '当前用户可访问操作资源', 'default', '', 'opencloud-base-producer', '/user/grant/actions', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528217381013553152', 'userGrantApis', '当前用户可访问API资源', 'default', '', 'opencloud-base-producer', '/user/grant/apis', '0', '1', '2018-12-28 14:27:17', '2018-12-29 17:59:22');
INSERT INTO `system_api` VALUES ('528595369890676736', 'grantAccessList', '获取已授权访问列表', 'default', '', 'opencloud-base-producer', '/grant/access/list', '0', '1', '2018-12-29 15:29:17', '2018-12-29 17:59:22');

-- ----------------------------
-- Table structure for system_app
-- ----------------------------
DROP TABLE IF EXISTS `system_app`;
CREATE TABLE `system_app` (
  `app_id` varchar(20) NOT NULL COMMENT '客户端ID',
  `app_secret` varchar(255) NOT NULL COMMENT '客户端秘钥',
  `app_name` varchar(255) NOT NULL COMMENT 'app名称',
  `app_name_en` varchar(255) NOT NULL COMMENT 'app英文名称',
  `app_icon` varchar(255) NOT NULL COMMENT '应用图标',
  `app_type` varchar(50) NOT NULL COMMENT 'app类型:server-服务应用 app-手机应用 pc-PC网页应用 wap-手机网页应用',
  `app_desc` varchar(255) DEFAULT NULL COMMENT 'app描述',
  `app_os` varchar(25) DEFAULT NULL COMMENT '移动应用操作系统:ios-苹果 android-安卓',
  `website` varchar(255) NOT NULL COMMENT '官网地址',
  `redirect_uris` varchar(255) NOT NULL COMMENT '第三方授权回掉地址,多个,号隔开',
  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID:默认为0',
  `user_type` varchar(20) NOT NULL DEFAULT 'platform' COMMENT '用户类型:platform-平台 isp-服务提供商 dev-自研开发者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_app
-- ----------------------------
INSERT INTO `system_app` VALUES ('gateway', '123456', '开放云平台', 'ApiGateway', '', 'server', '开放云平台', '', 'http://www.baidu.com', 'http://localhost:8888/login,http://localhost:8888/webjars/springfox-swagger-ui/o2c.html', '0', 'platform', '2018-11-12 17:48:45', '2018-12-28 00:16:56', '1');

-- ----------------------------
-- Table structure for system_gateway_rate_limit
-- ----------------------------
DROP TABLE IF EXISTS `system_gateway_rate_limit`;
CREATE TABLE `system_gateway_rate_limit` (
  `id` bigint(20) NOT NULL,
  `limit` bigint(11) NOT NULL DEFAULT '0' COMMENT '限制数量',
  `interval` bigint(11) NOT NULL DEFAULT '1' COMMENT '时间间隔(秒)',
  `service_id` varchar(100) NOT NULL,
  `type` varchar(10) DEFAULT 'url' COMMENT '限流规则类型:url,origin,user',
  `rules` text COMMENT '限流规则内容',
  `limit_desc` varchar(255) DEFAULT NULL,
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网关流量限制';

-- ----------------------------
-- Records of system_gateway_rate_limit
-- ----------------------------

-- ----------------------------
-- Table structure for system_gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `system_gateway_route`;
CREATE TABLE `system_gateway_route` (
  `id` bigint(20) NOT NULL,
  `route_id` varchar(100) DEFAULT NULL COMMENT '路由ID',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `service_id` varchar(255) DEFAULT NULL COMMENT '服务ID',
  `url` varchar(255) DEFAULT NULL COMMENT '完整地址',
  `strip_prefix` tinyint(1) DEFAULT '1' COMMENT '忽略前缀',
  `retryable` tinyint(1) DEFAULT '0' COMMENT '0-不重试 1-重试',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  `route_desc` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网关动态路由';

-- ----------------------------
-- Records of system_gateway_route
-- ----------------------------

-- ----------------------------
-- Table structure for system_grant_access
-- ----------------------------
DROP TABLE IF EXISTS `system_grant_access`;
CREATE TABLE `system_grant_access` (
  `id` bigint(20) NOT NULL,
  `path` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '请求地址',
  `authority` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '权限标识',
  `authority_prefix` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限拥有者类型:用户(USER_) 、角色(ROLE_)、APP(APP_)',
  `authority_owner` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '权限拥有者ID',
  `resource_id` bigint(20) NOT NULL COMMENT '资源ID',
  `resource_pid` bigint(20) DEFAULT NULL COMMENT '资源父节点',
  `resource_type` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '资源类型:api,menu,button',
  `resource_info` varchar(4096) COLLATE utf8_bin DEFAULT NULL COMMENT '资源详细信息:必须为JSON字符串',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  `service_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '所属服务',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源授权访问表:角色授权,个人授权,APP授权';

-- ----------------------------
-- Records of system_grant_access
-- ----------------------------
INSERT INTO `system_grant_access` VALUES ('1', '', 'ROLE_superAdmin', 'ROLE_', '1', '1', '0', 'menu', '{\"createTime\":1532870410000,\"menuCode\":\"system\",\"menuDesc\":\"系统安全\",\"menuId\":1,\"menuName\":\"系统安全\",\"parentId\":0,\"path\":\"\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545669022000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('2', 'system/grant-access/index', 'ROLE_superAdmin', 'ROLE_', '1', '2', '1', 'menu', '{\"createTime\":1532870413000,\"icon\":\"\",\"menuCode\":\"SystemGrantAccess\",\"menuDesc\":\"网关服务动态权限控制,菜单资源、操作资源、接口资源列表\",\"menuId\":2,\"menuName\":\"网关访问控制\",\"parentId\":1,\"path\":\"system/grant-access/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546073964000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('3', 'system/menus/index', 'ROLE_superAdmin', 'ROLE_', '1', '3', '1', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"SystemMenu\",\"menuDesc\":\"菜单资源管理\",\"menuId\":3,\"menuName\":\"菜单资源\",\"parentId\":1,\"path\":\"system/menus/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066519000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('4', '', 'ROLE_superAdmin', 'ROLE_', '1', '4', '0', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"Service\",\"menuDesc\":\"服务维护\",\"menuId\":4,\"menuName\":\"服务维护\",\"parentId\":0,\"path\":\"\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066732000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('5', 'www.baidu.com', 'ROLE_superAdmin', 'ROLE_', '1', '7', '4', 'menu', '{\"createTime\":1543515078000,\"menuCode\":\"ServiceTrace\",\"menuDesc\":\"服务追踪\",\"menuId\":7,\"menuName\":\"服务追踪\",\"parentId\":4,\"path\":\"www.baidu.com\",\"prefix\":\"http://\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066767000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('7', 'system/api/index', 'ROLE_superAdmin', 'ROLE_', '1', '6', '1', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"SystemApi\",\"menuDesc\":\"开发API接口资源\",\"menuId\":6,\"menuName\":\"接口资源\",\"parentId\":1,\"path\":\"system/api/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066533000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('8', 'system/role/index', 'ROLE_superAdmin', 'ROLE_', '1', '8', '1', 'menu', '{\"createTime\":1545895614000,\"icon\":\"\",\"menuCode\":\"SystemRole\",\"menuDesc\":\"角色管理\",\"menuId\":8,\"menuName\":\"角色信息\",\"parentId\":1,\"path\":\"system/role/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546070995000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('9', 'system/app/index', 'ROLE_superAdmin', 'ROLE_', '1', '9', '1', 'menu', '{\"createTime\":1545896512000,\"icon\":\"\",\"menuCode\":\"SystemApp\",\"menuDesc\":\"应用信息、授权\",\"menuId\":9,\"menuName\":\"应用信息\",\"parentId\":1,\"path\":\"system/app/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066571000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('10', 'system/user/index', 'ROLE_superAdmin', 'ROLE_', '1', '10', '1', 'menu', '{\"createTime\":1545896789000,\"icon\":\"\",\"menuCode\":\"SystemUser\",\"menuDesc\":\"\",\"menuId\":10,\"menuName\":\"系统用户\",\"parentId\":1,\"path\":\"system/user/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066588000}', '1', 'opencloud-base-producer');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单Id',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单',
  `menu_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `menu_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `prefix` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '路径前缀',
  `path` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '请求路径',
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单标题',
  `target` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '_self' COMMENT '打开方式:_self窗口内,_blank新窗口',
  `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级 越小越靠前',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('1', '0', 'system', '系统安全', '系统安全', '/', '', null, '_self', '0', '1', '2018-07-29 21:20:10', '2018-12-25 00:30:22');
INSERT INTO `system_menu` VALUES ('2', '1', 'SystemGrantAccess', '网关访问控制', '网关服务动态权限控制,菜单资源、操作资源、接口资源列表', '/', 'system/grant-access/index', '', '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 16:59:24');
INSERT INTO `system_menu` VALUES ('3', '1', 'SystemMenu', '菜单资源', '菜单资源管理', '/', 'system/menus/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 14:55:19');
INSERT INTO `system_menu` VALUES ('4', '0', 'Service', '服务维护', '服务维护', '/', '', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 14:58:52');
INSERT INTO `system_menu` VALUES ('5', '4', 'GatewayRoute', '网关路由', '网关路由', '/', 'gateway/route/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 15:00:47');
INSERT INTO `system_menu` VALUES ('6', '1', 'SystemApi', '接口资源', '开发API接口资源', '/', 'system/api/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 14:55:33');
INSERT INTO `system_menu` VALUES ('7', '4', 'ServiceTrace', '服务追踪', '服务追踪', 'http://', 'www.baidu.com', null, '_self', '0', '1', '2018-11-30 02:11:18', '2018-12-29 14:59:27');
INSERT INTO `system_menu` VALUES ('8', '1', 'SystemRole', '角色信息', '角色管理', '/', 'system/role/index', '', '_self', '0', '1', '2018-12-27 15:26:54', '2018-12-29 16:09:55');
INSERT INTO `system_menu` VALUES ('9', '1', 'SystemApp', '应用信息', '应用信息、授权', '/', 'system/app/index', '', '_self', '0', '1', '2018-12-27 15:41:52', '2018-12-29 14:56:11');
INSERT INTO `system_menu` VALUES ('10', '1', 'SystemUser', '系统用户', '', '/', 'system/user/index', '', '_self', '0', '1', '2018-12-27 15:46:29', '2018-12-29 14:56:28');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  `role_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', 'superAdmin', '超级管理员', '1', '平台超级管理员,拥有所有权限,责任重大', '2018-07-29 21:14:50', '2018-12-29 17:23:01');
INSERT INTO `system_role` VALUES ('2', 'admin', '普通管理员', '1', '平台普通管理员', '2018-07-29 21:14:54', '2018-12-29 17:23:15');
INSERT INTO `system_role` VALUES ('3', 'dev', '自研开发者', '1', '第三方开发者', '2018-07-29 21:14:54', '2018-12-29 17:22:21');
INSERT INTO `system_role` VALUES ('4', 'isp', '服务提供商', '1', '第三方开发者', '2018-07-29 21:14:54', '2018-12-29 17:22:29');

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '登陆账号',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `user_type` varchar(20) DEFAULT 'platform' COMMENT '用户类型:platform-平台 isp-服务提供商 dev-自研开发者',
  `company_id` bigint(20) DEFAULT NULL COMMENT '企业ID',
  `register_ip` varchar(100) DEFAULT NULL COMMENT '注册IP',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态:0-禁用 1-启用 2-锁定',
  `user_desc` varchar(255) DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户信息';

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('521677655146233856', 'admin', 'admin', null, '515608851@qq.com', '18518226890', 'platform', null, null, '2018-12-10 13:20:45', '1', null, '2018-12-10 13:20:45', '2018-12-10 13:20:45');

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role` (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  KEY `fk_user` (`user_id`),
  KEY `fk_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色和用户关系表';

-- ----------------------------
-- Records of system_user_role
-- ----------------------------
INSERT INTO `system_user_role` VALUES ('521677655146233856', '1');
SET FOREIGN_KEY_CHECKS=1;
