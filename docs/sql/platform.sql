/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : open-platform

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2019-01-08 17:11:28
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
-- Records of system_account_logs
-- ----------------------------
INSERT INTO `system_account_logs` VALUES ('531869194862460928', '2019-01-07 16:18:17', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '27', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531869207306960896', '2019-01-07 16:18:20', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '28', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531869644680593408', '2019-01-07 16:20:04', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '29', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531870695139835904', '2019-01-07 16:24:15', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '30', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531870820994121728', '2019-01-07 16:24:45', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '31', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531900093666689024', '2019-01-07 18:21:04', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '32', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531900097223458816', '2019-01-07 18:21:05', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '33', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531900099500965888', '2019-01-07 18:21:05', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '34', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531900907290361856', '2019-01-07 18:24:18', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '35', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531901406592892928', '2019-01-07 18:26:17', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0', '36', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531901762005630976', '2019-01-07 18:27:42', '127.0.0.1', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36', '37', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('531904456246165504', '2019-01-07 18:38:24', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '38', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('532189002577477632', '2019-01-08 13:29:05', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '39', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('532189047498473472', '2019-01-08 13:29:16', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '40', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('532243024441245696', '2019-01-08 17:03:45', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '41', '521677655146233856', 'admin', 'username');
INSERT INTO `system_account_logs` VALUES ('532243028329365504', '2019-01-08 17:03:46', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '42', '521677655146233856', 'admin', 'username');

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
  `is_persist` tinyint(3) NOT NULL DEFAULT '0' COMMENT '保留数据0-否 1-是 不允许删除',
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作表';

-- ----------------------------
-- Records of system_action
-- ----------------------------
INSERT INTO `system_action` VALUES ('1', 'btnDetail', '详情', '详情222', '/funcPages/adManager', '2', '0', '1', '2018-07-29 21:20:10', '2018-12-27 13:17:36', '0');
INSERT INTO `system_action` VALUES ('2', 'btnSave', '保存', '保存', '/index', '2', '0', '1', '2018-07-29 21:20:13', '2018-09-25 23:02:11', '0');

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
  `is_persist` tinyint(3) NOT NULL DEFAULT '0' COMMENT '保留数据0-否 1-是 不允许删除',
  PRIMARY KEY (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='API资源表';

-- ----------------------------
-- Records of system_api
-- ----------------------------
INSERT INTO `system_api` VALUES ('1', 'all', '全部', 'default', '', 'opencloud-base-producer', '/action/update', '0', '1', '2018-12-28 14:27:16', '2019-01-02 23:00:08', '1');
INSERT INTO `system_api` VALUES ('2', 'actuator', '监控端点', 'default', '监控端点', 'opencloud-base-producer', '/actuator/**', '0', '1', '2018-12-28 14:27:16', '2019-01-02 23:00:08', '1');
INSERT INTO `system_api` VALUES ('530807941222105088', 'sign', '内部应用请求签名', 'default', '只适用于内部应用,外部不允许使用。返回clientId,nonce,timestamp,signType,sign', 'opencloud-gateway-producer', '/sign', '0', '1', '2019-01-04 18:01:15', '2019-01-08 17:00:07', '1');
INSERT INTO `system_api` VALUES ('530807941326962688', 'login', '平台登录', 'default', '基于oauth2密码模式登录,无需签名,返回access_token。', 'opencloud-gateway-producer', '/login/token', '0', '1', '2019-01-04 18:01:15', '2019-01-08 17:00:07', '1');
INSERT INTO `system_api` VALUES ('530807941402460160', 'accessCache', '获取网关缓存的访问限制列表', 'default', '获取网关缓存的访问限制列表', 'opencloud-gateway-producer', '/access/cache', '0', '1', '2019-01-04 18:01:15', '2019-01-08 17:00:07', '1');
INSERT INTO `system_api` VALUES ('530807941473763328', 'limitCache', '获取网关缓存的限流列表', 'default', '获取网关缓存的限流列表', 'opencloud-gateway-producer', '/limit/cache', '0', '1', '2019-01-04 18:01:15', '2019-01-08 17:00:07', '1');
INSERT INTO `system_api` VALUES ('530807941540872192', 'routeCache', '获取网关缓存的路由列表', 'default', '获取网关缓存的路由列表', 'opencloud-gateway-producer', '/route/cache', '0', '1', '2019-01-04 18:01:15', '2019-01-08 17:00:08', '1');
INSERT INTO `system_api` VALUES ('530807950076280832', 'action', '获取操作分页列表', 'default', '获取操作分页列表', 'opencloud-base-producer', '/action', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950172749824', 'updateStatus', '更新角色状态', 'default', '更新角色状态', 'opencloud-base-producer', '/role/update/status', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807950239858688', 'actionList', '获取操作列表', 'default', '获取操作列表', 'opencloud-base-producer', '/action/list', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950311161856', 'getAction', '获取操作资源', 'default', '获取操作资源', 'opencloud-base-producer', '/action/{actionId}', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950428602368', 'removeAction', '移除操作资源', 'default', '移除操作资源', 'opencloud-base-producer', '/action/remove', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950487322624', 'updateAction', '编辑操作资源', 'default', '添加操作资源', 'opencloud-base-producer', '/action/update', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950533459968', 'addAction', '添加操作资源', 'default', '添加操作资源', 'opencloud-base-producer', '/action/add', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950579597312', 'api', '获取接口分页列表', 'default', '获取接口分页列表', 'opencloud-base-producer', '/api', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950869004288', 'apiList', '获取接口列表', 'default', '获取接口列表', 'opencloud-base-producer', '/api/list', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807950944501760', 'getApi', '获取接口资源', 'default', '获取接口资源', 'opencloud-base-producer', '/api/{apiId}', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951015804928', 'addApi', '添加接口资源', 'default', '添加接口资源', 'opencloud-base-producer', '/api/add', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951087108096', 'updateApi', '编辑接口资源', 'default', '编辑接口资源', 'opencloud-base-producer', '/api/update', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951150022656', 'removeApi', '移除接口资源', 'default', '移除接口资源', 'opencloud-base-producer', '/api/remove', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951212937216', 'updateApp', '编辑应用信息', 'default', '编辑应用信息', 'opencloud-base-producer', '/app/update', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951275851776', 'addApp', '添加应用信息', 'default', '添加应用信息', 'opencloud-base-producer', '/app/add', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951330377728', 'resetSecret', '重置应用秘钥', 'default', '重置应用秘钥', 'opencloud-base-producer', '/app/reset', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951431041024', 'app', '获取应用分页列表', 'default', '获取应用分页列表', 'opencloud-base-producer', '/app', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951472984064', 'getAppDevInfo', '获取应用开发配置信息', 'default', '获取应用开发配置信息', 'opencloud-base-producer', '/app/dev/{appId}', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951523315712', 'getApp', '获取应用信息', 'default', '获取应用信息', 'opencloud-base-producer', '/app/{appId}', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951879831552', 'removeApp', '删除应用信息', 'default', '删除应用信息', 'opencloud-base-producer', '/app/remove', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951925968896', 'grantAccess', '获取已授权访问列表', 'default', '获取已授权访问列表', 'opencloud-base-producer', '/grant/access', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807951984689152', 'grantAccessList', '获取已授权访问列表', 'default', '获取已授权访问列表', 'opencloud-base-producer', '/grant/access/list', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807952106323968', 'getMenu', '获取菜单资源', 'default', '获取菜单资源', 'opencloud-base-producer', '/menu/{menuId}', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952156655616', 'menu', '菜单资源分页列表', 'default', '菜单资源分页列表', 'opencloud-base-producer', '/menu', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952194404352', 'addMenu', '添加菜单资源', 'default', '添加菜单资源', 'opencloud-base-producer', '/menu/add', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:58', '1');
INSERT INTO `system_api` VALUES ('530807952290873344', 'updateMenu', '编辑菜单资源', 'default', '编辑菜单资源', 'opencloud-base-producer', '/menu/update', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952324427776', 'removeMenu', '移除菜单资源', 'default', '移除菜单资源', 'opencloud-base-producer', '/menu/remove', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952366370816', 'menuList', '菜单资源列表', 'default', '菜单资源列表', 'opencloud-base-producer', '/menu/list', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952488005632', 'getRole', '获取角色信息', 'default', '获取角色信息', 'opencloud-base-producer', '/role/{roleId}', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952521560064', 'role', '获取角色分页列表', 'default', '获取角色分页列表', 'opencloud-base-producer', '/role', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952559308800', 'addRole', '添加角色', 'default', '添加角色', 'opencloud-base-producer', '/role/add', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952592863232', 'updateRole', '编辑角色', 'default', '编辑角色', 'opencloud-base-producer', '/role/update', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952639000576', 'removeRole', '删除角色', 'default', '删除角色', 'opencloud-base-producer', '/role/remove', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952680943616', 'user', '系统用户分页列表', 'default', '系统用户分页列表', 'opencloud-base-producer', '/user', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952722886656', 'addUser', '添加系统用户', 'default', '添加系统用户', 'opencloud-base-producer', '/user/add', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952760635392', 'userGrantActions', '当前用户可访问操作资源', 'default', '当前用户可访问操作资源', 'opencloud-base-producer', '/user/grant/actions', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952810967040', 'userGrantApis', '当前用户可访问接口资源', 'default', '当前用户可访问接口资源', 'opencloud-base-producer', '/user/grant/apis', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952857104384', 'updateUser', '更新系统用户', 'default', '更新系统用户', 'opencloud-base-producer', '/user/update', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530807952894853120', 'userGrantMenus', '当前用户可访问菜单资源', 'default', '当前用户可访问菜单资源', 'opencloud-base-producer', '/user/grant/menus', '0', '1', '2019-01-04 18:01:17', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('530810061228867584', 'client', 'client', 'default', 'client', 'opencloud-auth-producer', '/client', '0', '1', '2019-01-04 18:09:40', '2019-01-08 16:59:52', '1');
INSERT INTO `system_api` VALUES ('530810061270810624', 'addClient', 'addClient', 'default', 'addClient', 'opencloud-auth-producer', '/client/add', '0', '1', '2019-01-04 18:09:40', '2019-01-08 16:59:52', '1');
INSERT INTO `system_api` VALUES ('530810061316947968', 'updateClient', 'updateClient', 'default', 'updateClient', 'opencloud-auth-producer', '/client/update', '0', '1', '2019-01-04 18:09:40', '2019-01-08 16:59:52', '1');
INSERT INTO `system_api` VALUES ('530810061375668224', 'getClient', 'getClient', 'default', 'getClient', 'opencloud-auth-producer', '/client/{clientId}', '0', '1', '2019-01-04 18:09:40', '2019-01-08 16:59:52', '1');
INSERT INTO `system_api` VALUES ('530810061463748608', 'removeClinet', 'removeClinet', 'default', 'removeClinet', 'opencloud-auth-producer', '/client/remove', '0', '1', '2019-01-04 18:09:40', '2019-01-08 16:59:52', '1');
INSERT INTO `system_api` VALUES ('530810061530857472', 'userProfile', '平台登录信息', 'default', '平台登录信息', 'opencloud-auth-producer', '/user', '0', '1', '2019-01-04 18:09:40', '2019-01-08 16:59:52', '1');
INSERT INTO `system_api` VALUES ('532187928768544768', 'roleGrantMenu', '菜单授权', 'default', '菜单授权', 'opencloud-base-producer', '/role/grant/menu', '0', '1', '2019-01-08 13:24:49', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('532187929431244800', 'roleGrantAction', '操作授权', 'default', '操作授权', 'opencloud-base-producer', '/role/grant/action', '0', '1', '2019-01-08 13:24:49', '2019-01-08 16:15:59', '1');
INSERT INTO `system_api` VALUES ('532187929896812544', 'roleGrantApi', '接口授权', 'default', '接口授权', 'opencloud-base-producer', '/role/grant/api', '0', '1', '2019-01-08 13:24:50', '2019-01-08 16:15:59', '1');

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
  `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
  `is_persist` tinyint(3) NOT NULL DEFAULT '0' COMMENT '保留数据0-否 1-是 不允许删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_app
-- ----------------------------
INSERT INTO `system_app` VALUES ('gateway', '123456', '开放云平台', 'ApiGateway', '', 'server', '开放云平台', '', 'http://www.baidu.com', 'http://localhost:8888/login,http://localhost:8888/webjars/springfox-swagger-ui/o2c.html', '0', 'platform', '2018-11-12 17:48:45', '2019-01-08 16:33:53', '1', '1');

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
INSERT INTO `system_grant_access` VALUES ('1', '', 'ROLE_all', 'ROLE_', '1', '1', '0', 'menu', '{\"createTime\":1532870410000,\"menuCode\":\"system\",\"menuDesc\":\"系统安全\",\"menuId\":1,\"menuName\":\"系统安全\",\"parentId\":0,\"path\":\"\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545669022000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('2', 'system/grant-access/index', 'ROLE_all', 'ROLE_', '1', '2', '1', 'menu', '{\"createTime\":1532870413000,\"icon\":\"\",\"menuCode\":\"SystemGrantAccess\",\"menuDesc\":\"权限分配,菜单资源、操作资源、接口资源列表\",\"menuId\":2,\"menuName\":\"访问控制\",\"parentId\":1,\"path\":\"system/grant-access/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546435827000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('3', 'system/menus/index', 'ROLE_all', 'ROLE_', '1', '3', '1', 'menu', '{\"createTime\":1532870413000,\"isPersist\":1,\"menuCode\":\"SystemMenu\",\"menuDesc\":\"菜单资源管理\",\"menuId\":3,\"menuName\":\"菜单资源\",\"parentId\":1,\"path\":\"system/menus/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546928289000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('4', '', 'ROLE_all', 'ROLE_', '1', '4', '0', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"Service\",\"menuDesc\":\"服务维护\",\"menuId\":4,\"menuName\":\"服务维护\",\"parentId\":0,\"path\":\"\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066732000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('5', 'www.baidu.com', 'ROLE_all', 'ROLE_', '1', '7', '4', 'menu', '{\"createTime\":1543515078000,\"menuCode\":\"ServiceTrace\",\"menuDesc\":\"服务追踪\",\"menuId\":7,\"menuName\":\"服务追踪\",\"parentId\":4,\"path\":\"www.baidu.com\",\"prefix\":\"http://\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066767000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('7', 'system/api/index', 'ROLE_all', 'ROLE_', '1', '6', '1', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"SystemApi\",\"menuDesc\":\"开发API接口资源\",\"menuId\":6,\"menuName\":\"接口资源\",\"parentId\":1,\"path\":\"system/api/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066533000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('8', 'system/role/index', 'ROLE_all', 'ROLE_', '1', '8', '1', 'menu', '{\"createTime\":1545895614000,\"icon\":\"\",\"menuCode\":\"SystemRole\",\"menuDesc\":\"角色管理\",\"menuId\":8,\"menuName\":\"角色信息\",\"parentId\":1,\"path\":\"system/role/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546070995000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('9', 'system/app/index', 'ROLE_all', 'ROLE_', '1', '9', '1', 'menu', '{\"createTime\":1545896512000,\"icon\":\"\",\"menuCode\":\"SystemApp\",\"menuDesc\":\"应用信息、授权\",\"menuId\":9,\"menuName\":\"应用信息\",\"parentId\":1,\"path\":\"system/app/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066571000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('10', 'system/user/index', 'ROLE_all', 'ROLE_', '1', '10', '1', 'menu', '{\"createTime\":1545896789000,\"icon\":\"\",\"menuCode\":\"SystemUser\",\"menuDesc\":\"\",\"menuId\":10,\"menuName\":\"系统用户\",\"parentId\":1,\"path\":\"system/user/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1546066588000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('11', 'action/update', 'APP_all', 'APP_', 'gateway', '1', '0', 'api', '{\"apiCategory\":\"default\",\"apiCode\":\"all\",\"apiDesc\":\"\",\"apiId\":1,\"apiName\":\"全部\",\"createTime\":1545978436000,\"isPersist\":1,\"path\":\"/action/update\",\"priority\":0,\"serviceId\":\"opencloud-base-producer\",\"status\":1,\"updateTime\":1546441208000}', '1', 'opencloud-base-producer');

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
  `is_persist` tinyint(3) NOT NULL DEFAULT '0' COMMENT '保留数据0-否 1-是 不允许删除',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('1', '0', 'system', '系统安全', '系统安全', '/', '', null, '_self', '0', '1', '2018-07-29 21:20:10', '2018-12-25 00:30:22', '1');
INSERT INTO `system_menu` VALUES ('2', '1', 'SystemGrantAccess', '访问控制', '权限分配,菜单资源、操作资源、接口资源列表', '/', 'system/grant-access/index', '', '_self', '0', '1', '2018-07-29 21:20:13', '2019-01-02 21:30:27', '1');
INSERT INTO `system_menu` VALUES ('3', '1', 'SystemMenu', '菜单资源', '菜单资源管理', '/', 'system/menus/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2019-01-08 14:18:09', '1');
INSERT INTO `system_menu` VALUES ('4', '0', 'Service', '服务维护', '服务维护', '/', '', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 14:58:52', '1');
INSERT INTO `system_menu` VALUES ('5', '4', 'GatewayRoute', '网关路由', '网关路由', '/', 'gateway/route/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 15:00:47', '1');
INSERT INTO `system_menu` VALUES ('6', '1', 'SystemApi', '接口资源', '开发API接口资源', '/', 'system/api/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-29 14:55:33', '1');
INSERT INTO `system_menu` VALUES ('7', '4', 'ServiceTrace', '服务追踪', '服务追踪', 'http://', 'www.baidu.com', null, '_self', '0', '1', '2018-11-30 02:11:18', '2018-12-29 14:59:27', '1');
INSERT INTO `system_menu` VALUES ('8', '1', 'SystemRole', '角色信息', '角色管理', '/', 'system/role/index', '', '_self', '0', '1', '2018-12-27 15:26:54', '2018-12-29 16:09:55', '1');
INSERT INTO `system_menu` VALUES ('9', '1', 'SystemApp', '应用信息', '应用信息、授权', '/', 'system/app/index', '', '_self', '0', '1', '2018-12-27 15:41:52', '2018-12-29 14:56:11', '1');
INSERT INTO `system_menu` VALUES ('10', '1', 'SystemUser', '系统用户', '', '/', 'system/user/index', '', '_self', '0', '1', '2018-12-27 15:46:29', '2018-12-29 14:56:28', '1');

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
  `is_persist` tinyint(3) NOT NULL DEFAULT '0' COMMENT '保留数据0-否 1-是 不允许删除',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', 'all', '超级管理员', '1', '超级管理员,拥有所有权限,责任重大', '2018-07-29 21:14:50', '2019-01-02 23:15:21', '1');
INSERT INTO `system_role` VALUES ('2', 'admin', '普通管理员', '1', '普通管理员', '2018-07-29 21:14:54', '2019-01-02 23:15:16', '1');
INSERT INTO `system_role` VALUES ('3', 'dev', '自研开发者', '1', '第三方开发者', '2018-07-29 21:14:54', '2018-12-29 17:22:21', '1');
INSERT INTO `system_role` VALUES ('4', 'isp', '服务提供商', '1', '第三方开发者', '2018-07-29 21:14:54', '2018-12-29 17:22:29', '1');

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
