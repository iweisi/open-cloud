/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : open-platform

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-18 13:09:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for platform_app_details
-- ----------------------------
DROP TABLE IF EXISTS `platform_app_details`;
CREATE TABLE `platform_app_details` (
  `app_id` varchar(20) NOT NULL COMMENT '客户端ID',
  `app_secret` varchar(255) NOT NULL COMMENT '客户端秘钥',
  `app_name` varchar(255) NOT NULL COMMENT 'app名称',
  `app_name_en` varchar(255) NOT NULL COMMENT 'app英文名称',
  `app_icon` varchar(255) DEFAULT NULL COMMENT '应用图标',
  `app_type` varchar(50) NOT NULL COMMENT 'app类型:server-服务应用 app-手机应用 pc-PC网页应用 wap-手机网页应用',
  `os` varchar(25) DEFAULT NULL COMMENT '移动应用操作系统:ios-苹果 android-安卓',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID:默认为0',
  `tenant_type` int(1) NOT NULL DEFAULT '0' COMMENT '用户类型:0-内部用户 1-服务提供商 2-自研开发者',
  `description` varchar(255) DEFAULT NULL COMMENT 'app描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of platform_app_details
-- ----------------------------
INSERT INTO `platform_app_details` VALUES ('gateway', '123456', '开放云平台', 'ApiGateway', null, 'server', null, '0', '0', '开放云平台', '2018-11-12 17:48:45', '2018-11-16 18:46:31');

-- ----------------------------
-- Table structure for platform_dict
-- ----------------------------
DROP TABLE IF EXISTS `platform_dict`;
CREATE TABLE `platform_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` text NOT NULL COMMENT '数据值',
  `code` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `type_name` varchar(255) DEFAULT NULL,
  `description` varchar(100) NOT NULL COMMENT '描述',
  `parent_id` bigint(20) NOT NULL DEFAULT '0',
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sys_dict_label` (`code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典表';

-- ----------------------------
-- Records of platform_dict
-- ----------------------------

-- ----------------------------
-- Table structure for platform_gateway_rate_limit
-- ----------------------------
DROP TABLE IF EXISTS `platform_gateway_rate_limit`;
CREATE TABLE `platform_gateway_rate_limit` (
  `id` bigint(20) NOT NULL,
  `limit` bigint(11) NOT NULL DEFAULT '0' COMMENT '限制数量',
  `interval` bigint(11) NOT NULL DEFAULT '1' COMMENT '时间间隔(秒)',
  `service_id` varchar(100) NOT NULL,
  `enabled` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-禁用 1-启用',
  `type` varchar(10) DEFAULT 'url' COMMENT '限流规则类型:url,origin,user',
  `rules` text COMMENT '限流规则内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网关流量限制';

-- ----------------------------
-- Records of platform_gateway_rate_limit
-- ----------------------------

-- ----------------------------
-- Table structure for platform_gateway_routes
-- ----------------------------
DROP TABLE IF EXISTS `platform_gateway_routes`;
CREATE TABLE `platform_gateway_routes` (
  `id` bigint(20) NOT NULL,
  `route_id` varchar(100) DEFAULT NULL COMMENT '路由ID',
  `path` varchar(255) DEFAULT NULL COMMENT '路径',
  `service_id` varchar(255) DEFAULT NULL COMMENT '服务ID',
  `url` varchar(255) DEFAULT NULL COMMENT '完整地址',
  `strip_prefix` tinyint(1) DEFAULT '1' COMMENT '忽略前缀',
  `retryable` tinyint(1) DEFAULT '0' COMMENT '0-不重试 1-重试',
  `enabled` tinyint(1) DEFAULT '0' COMMENT '0-禁用 1-启用 ',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='网关动态路由';

-- ----------------------------
-- Records of platform_gateway_routes
-- ----------------------------

-- ----------------------------
-- Table structure for platform_resource_action
-- ----------------------------
DROP TABLE IF EXISTS `platform_resource_action`;
CREATE TABLE `platform_resource_action` (
  `action_id` bigint(20) NOT NULL COMMENT '资源ID',
  `action_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源编码',
  `action_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
  `url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '资源路径',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '资源父节点',
  `priority` int(10) NOT NULL DEFAULT '0' COMMENT '优先级 越小越靠前',
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
  `enabled` tinyint(1) NOT NULL COMMENT '是否可用',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作表';

-- ----------------------------
-- Records of platform_resource_action
-- ----------------------------
INSERT INTO `platform_resource_action` VALUES ('1', 'btnDetail', '详情', '/funcPages/adManager', '2', '0', '详情', '1', '2018-07-29 21:20:10', '2018-09-25 23:02:11');
INSERT INTO `platform_resource_action` VALUES ('2', 'btnSave', '保存', '/index', '2', '0', '保存', '1', '2018-07-29 21:20:13', '2018-09-25 23:02:11');

-- ----------------------------
-- Table structure for platform_resource_api
-- ----------------------------
DROP TABLE IF EXISTS `platform_resource_api`;
CREATE TABLE `platform_resource_api` (
  `api_id` bigint(20) NOT NULL COMMENT '资源ID',
  `api_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源编码',
  `api_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
  `service_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '服务ID',
  `url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '资源路径',
  `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级',
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
  `enabled` tinyint(1) NOT NULL COMMENT '是否可用',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='API资源表';

-- ----------------------------
-- Records of platform_resource_api
-- ----------------------------
INSERT INTO `platform_resource_api` VALUES ('522067499043258368', 'actions', '动作列表', 'platform-rbac-producer', '/actions', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:17');
INSERT INTO `platform_resource_api` VALUES ('522067499139727360', 'enableAction', '启用动作资源', 'platform-rbac-producer', '/actions/enable', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499198447616', 'removeAction', '移除动作', 'platform-rbac-producer', '/actions/remove', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499269750784', 'disableAction', '禁用动作资源', 'platform-rbac-producer', '/actions/disable', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:17');
INSERT INTO `platform_resource_api` VALUES ('522067499391385600', 'addAction', '添加动作资源', 'platform-rbac-producer', '/actions/add', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499466883072', 'updateAction', '编辑动作资源', 'platform-rbac-producer', '/actions/update', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499525603328', 'getAction', '获取动作资源', 'platform-rbac-producer', '/actions/{actionId}', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:17');
INSERT INTO `platform_resource_api` VALUES ('522067499601100800', 'apis', 'Api列表', 'platform-rbac-producer', '/apis', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499659821056', 'removeApi', '移除Api', 'platform-rbac-producer', '/apis/remove', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499714347008', 'addApi', '添加Api资源', 'platform-rbac-producer', '/apis/add', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499777261568', 'disableApi', '禁用Api资源', 'platform-rbac-producer', '/apis/disable', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499827593216', 'getApi', '获取Api资源', 'platform-rbac-producer', '/apis/{apiId}', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499865341952', 'updateApi', '编辑Api资源', 'platform-rbac-producer', '/apis/update', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499907284992', 'enableApi', '启用Api资源', 'platform-rbac-producer', '/apis/enable', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067499949228032', 'getApp', '获取应用信息', 'platform-rbac-producer', '/apps/{appId}', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500003753984', 'removeApp', '删除应用', 'platform-rbac-producer', '/apps/remove', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500049891328', 'addApp', '添加应用', 'platform-rbac-producer', '/apps/add', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067500100222976', 'apps', '应用列表', 'platform-rbac-producer', '/apps', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500167331840', 'updateApp', '编辑应用', 'platform-rbac-producer', '/apps/update', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:18');
INSERT INTO `platform_resource_api` VALUES ('522067500259606528', 'resetSecret', '重置秘钥', 'platform-rbac-producer', '/apps/reset', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500309938176', 'enableMenu', '启用菜单资源', 'platform-rbac-producer', '/menus/enable', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500360269824', 'updateMenu', '编辑菜单资源', 'platform-rbac-producer', '/menus/update', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500406407168', 'menus', '菜单列表', 'platform-rbac-producer', '/menus', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500460933120', 'addMenu', '添加菜单资源', 'platform-rbac-producer', '/menus/add', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500502876160', 'menusAll', '菜单列表', 'platform-rbac-producer', '/menus/all', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500544819200', 'disableMenu', '禁用菜单资源', 'platform-rbac-producer', '/menus/disable', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500582567936', 'getMenu', '获取菜单资源', 'platform-rbac-producer', '/menus/{menuId}', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500616122368', 'removeMenu', '移除菜单', 'platform-rbac-producer', '/menus/remove', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:19');
INSERT INTO `platform_resource_api` VALUES ('522067500666454016', 'permissions', '获取授权列表', 'platform-rbac-producer', '/permissions', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500704202752', 'userApis', '登录用户API权限', 'platform-rbac-producer', '/permissions/user/apis', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500746145792', 'userActions', '登录用户操作权限', 'platform-rbac-producer', '/permissions/user/actions', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500783894528', 'userMenus', '登录用户菜单权限', 'platform-rbac-producer', '/permissions/user/menus', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500825837568', 'addRole', '添加角色', 'platform-rbac-producer', '/roles/add', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500859392000', 'roles', '角色列表', 'platform-rbac-producer', '/roles', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500897140736', 'updateRole', '更新角色', 'platform-rbac-producer', '/roles/update', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500934889472', 'removeRole', '删除角色', 'platform-rbac-producer', '/roles/remove', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067500981026816', 'getRole', '获取角色信息', 'platform-rbac-producer', '/roles/{roleId}', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067501027164160', 'login', 'login', 'platform-rbac-producer', '/users/account/login', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067501077495808', 'addLoginLog', 'addLoginLog', 'platform-rbac-producer', '/users/account/logs/add', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:20');
INSERT INTO `platform_resource_api` VALUES ('522067501119438848', 'addUser', '添加用户', 'platform-rbac-producer', '/users/add', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:21');
INSERT INTO `platform_resource_api` VALUES ('522067501161381888', 'updateUser', '更新用户', 'platform-rbac-producer', '/users/update', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:21');
INSERT INTO `platform_resource_api` VALUES ('522067501194936320', 'users', '用户列表', 'platform-rbac-producer', '/users', '0', '', '1', '2018-12-11 15:09:51', '2018-12-12 01:54:21');
INSERT INTO `platform_resource_api` VALUES ('522068701403414528', 'clients', 'clients', 'platform-oauth-producer', '/clients', '0', '', '1', '2018-12-11 15:14:37', '2018-12-12 01:47:04');
INSERT INTO `platform_resource_api` VALUES ('522068701457940480', 'addClient', 'addClient', 'platform-oauth-producer', '/clients/add', '0', '', '1', '2018-12-11 15:14:37', '2018-12-12 01:47:04');
INSERT INTO `platform_resource_api` VALUES ('522068701508272128', 'getClient', 'getClient', 'platform-oauth-producer', '/clients/{clientId}', '0', '', '1', '2018-12-11 15:14:38', '2018-12-12 01:47:04');
INSERT INTO `platform_resource_api` VALUES ('522068701550215168', 'updateClient', 'updateClient', 'platform-oauth-producer', '/clients/update', '0', '', '1', '2018-12-11 15:14:38', '2018-12-12 01:47:04');
INSERT INTO `platform_resource_api` VALUES ('522068701600546816', 'resetSecret', 'resetSecret', 'platform-oauth-producer', '/clients/reset', '0', '', '1', '2018-12-11 15:14:38', '2018-12-12 01:47:04');
INSERT INTO `platform_resource_api` VALUES ('522068701709598720', 'removeClinet', 'removeClinet', 'platform-oauth-producer', '/clients/remove', '0', '', '1', '2018-12-11 15:14:38', '2018-12-12 01:47:04');
INSERT INTO `platform_resource_api` VALUES ('522068701755736064', 'getUser', '获取当前登录用户', 'platform-oauth-producer', '/me', '0', '', '1', '2018-12-11 15:14:38', '2018-12-12 01:47:05');
INSERT INTO `platform_resource_api` VALUES ('522519297684144128', 'actions', '动作列表', 'opencloud-rbac-producer', '/actions', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519298078408704', 'getAction', '获取动作资源', 'opencloud-rbac-producer', '/actions/{actionId}', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519298262958080', 'updateAction', '编辑动作资源', 'opencloud-rbac-producer', '/actions/update', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519298481061888', 'removeAction', '移除动作', 'opencloud-rbac-producer', '/actions/remove', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519298636251136', 'disableAction', '禁用动作资源', 'opencloud-rbac-producer', '/actions/disable', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519298824994816', 'addAction', '添加动作资源', 'opencloud-rbac-producer', '/actions/add', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519298984378368', 'enableAction', '启用动作资源', 'opencloud-rbac-producer', '/actions/enable', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519299194093568', 'enableApi', '启用Api资源', 'opencloud-rbac-producer', '/apis/enable', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519299374448640', 'apis', 'Api列表', 'opencloud-rbac-producer', '/apis', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519299550609408', 'removeApi', '移除Api', 'opencloud-rbac-producer', '/apis/remove', '0', '', '1', '2018-12-12 21:05:08', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519299751936000', 'addApi', '添加Api资源', 'opencloud-rbac-producer', '/apis/add', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519299907125248', 'getApi', '获取Api资源', 'opencloud-rbac-producer', '/apis/{apiId}', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519300070703104', 'disableApi', '禁用Api资源', 'opencloud-rbac-producer', '/apis/disable', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519300213309440', 'updateApi', '编辑Api资源', 'opencloud-rbac-producer', '/apis/update', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:12');
INSERT INTO `platform_resource_api` VALUES ('522519300381081600', 'updateApp', '编辑应用', 'opencloud-rbac-producer', '/apps/update', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519300536270848', 'resetSecret', '重置秘钥', 'opencloud-rbac-producer', '/apps/reset', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519300691460096', 'apps', '应用列表', 'opencloud-rbac-producer', '/apps', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519300905369600', 'addApp', '添加应用', 'opencloud-rbac-producer', '/apps/add', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519301110890496', 'getApp', '获取应用信息', 'opencloud-rbac-producer', '/apps/{appId}', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519301316411392', 'removeApp', '删除应用', 'opencloud-rbac-producer', '/apps/remove', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519301521932288', 'addMenu', '添加菜单资源', 'opencloud-rbac-producer', '/menus/add', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519301740036096', 'getMenu', '获取菜单资源', 'opencloud-rbac-producer', '/menus/{menuId}', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519302012665856', 'menus', '菜单列表', 'opencloud-rbac-producer', '/menus', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519302163660800', 'menusAll', '菜单列表', 'opencloud-rbac-producer', '/menus/all', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519302327238656', 'updateMenu', '编辑菜单资源', 'opencloud-rbac-producer', '/menus/update', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519302474039296', 'enableMenu', '启用菜单资源', 'opencloud-rbac-producer', '/menus/enable', '0', '', '1', '2018-12-12 21:05:09', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519304877375488', 'removeMenu', '移除菜单', 'opencloud-rbac-producer', '/menus/remove', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519305028370432', 'disableMenu', '禁用菜单资源', 'opencloud-rbac-producer', '/menus/disable', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519305200336896', 'permissions', '获取授权列表', 'opencloud-rbac-producer', '/permissions', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522519305347137536', 'userApis', '登录用户API权限', 'opencloud-rbac-producer', '/permissions/user/apis', '0', '', '1', '2018-12-12 21:05:10', '2018-12-13 00:27:18');
INSERT INTO `platform_resource_api` VALUES ('522519305519104000', 'userMenus', '登录用户菜单权限', 'opencloud-rbac-producer', '/permissions/user/menus', '0', '', '1', '2018-12-12 21:05:10', '2018-12-13 00:27:18');
INSERT INTO `platform_resource_api` VALUES ('522519305695264768', 'userActions', '登录用户操作权限', 'opencloud-rbac-producer', '/permissions/user/actions', '0', '', '1', '2018-12-12 21:05:10', '2018-12-13 00:27:18');
INSERT INTO `platform_resource_api` VALUES ('522519305867231232', 'getRole', '获取角色信息', 'opencloud-rbac-producer', '/roles/{roleId}', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522519306139860992', 'addRole', '添加角色', 'opencloud-rbac-producer', '/roles/add', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522519306366353408', 'roles', '角色列表', 'opencloud-rbac-producer', '/roles', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522519306529931264', 'removeRole', '删除角色', 'opencloud-rbac-producer', '/roles/remove', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522519306676731904', 'updateRole', '更新角色', 'opencloud-rbac-producer', '/roles/update', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522519306844504064', 'addLoginLog', 'addLoginLog', 'opencloud-rbac-producer', '/tenants/account/logs/add', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522519307012276224', 'login', 'login', 'opencloud-rbac-producer', '/tenants/account/login', '0', '', '1', '2018-12-12 21:05:10', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522519307196825600', 'addUser', '添加用户', 'opencloud-rbac-producer', '/users/add', '0', '', '1', '2018-12-12 21:05:10', '2018-12-13 00:27:18');
INSERT INTO `platform_resource_api` VALUES ('522519307368792064', 'users', '用户列表', 'opencloud-rbac-producer', '/users', '0', '', '1', '2018-12-12 21:05:10', '2018-12-13 00:27:18');
INSERT INTO `platform_resource_api` VALUES ('522519307544952832', 'updateUser', '更新用户', 'opencloud-rbac-producer', '/users/update', '0', '', '1', '2018-12-12 21:05:10', '2018-12-13 00:27:18');
INSERT INTO `platform_resource_api` VALUES ('522519387723268096', 'clients', 'clients', 'opencloud-oauth-producer', '/clients', '0', '', '1', '2018-12-12 21:05:29', '2018-12-13 21:17:59');
INSERT INTO `platform_resource_api` VALUES ('522519387899428864', 'resetSecret', 'resetSecret', 'opencloud-oauth-producer', '/clients/reset', '0', '', '1', '2018-12-12 21:05:30', '2018-12-13 21:17:59');
INSERT INTO `platform_resource_api` VALUES ('522519388075589632', 'addClient', 'addClient', 'opencloud-oauth-producer', '/clients/add', '0', '', '1', '2018-12-12 21:05:30', '2018-12-13 21:17:59');
INSERT INTO `platform_resource_api` VALUES ('522519388214001664', 'getClient', 'getClient', 'opencloud-oauth-producer', '/clients/{clientId}', '0', '', '1', '2018-12-12 21:05:30', '2018-12-13 21:17:59');
INSERT INTO `platform_resource_api` VALUES ('522519388385968128', 'updateClient', 'updateClient', 'opencloud-oauth-producer', '/clients/update', '0', '', '1', '2018-12-12 21:05:30', '2018-12-13 21:17:59');
INSERT INTO `platform_resource_api` VALUES ('522519388721512448', 'removeClinet', 'removeClinet', 'opencloud-oauth-producer', '/clients/remove', '0', '', '1', '2018-12-12 21:05:30', '2018-12-13 21:17:59');
INSERT INTO `platform_resource_api` VALUES ('522519388872507392', 'getUser', '获取当前登录租户', 'opencloud-oauth-producer', '/me', '0', '', '1', '2018-12-12 21:05:30', '2018-12-13 21:17:59');
INSERT INTO `platform_resource_api` VALUES ('522881233328275456', 'tenantApis', '登录租户API权限', 'opencloud-rbac-producer', '/permissions/tenant/apis', '0', '', '1', '2018-12-13 21:03:20', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522881233605099520', 'tenantActions', '登录租户操作权限', 'opencloud-rbac-producer', '/permissions/tenant/actions', '0', '', '1', '2018-12-13 21:03:20', '2018-12-18 10:43:13');
INSERT INTO `platform_resource_api` VALUES ('522881233877729280', 'tenantMenus', '登录租户菜单权限', 'opencloud-rbac-producer', '/permissions/tenant/menus', '0', '', '1', '2018-12-13 21:03:20', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522881235266043904', 'tenants', '租户列表', 'opencloud-rbac-producer', '/tenants', '0', '', '1', '2018-12-13 21:03:21', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522881235421233152', 'updateTenant', '更新租户', 'opencloud-rbac-producer', '/tenants/update', '0', '', '1', '2018-12-13 21:03:21', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522881235580616704', 'addTenant', '添加租户', 'opencloud-rbac-producer', '/tenants/add', '0', '', '1', '2018-12-13 21:03:21', '2018-12-18 10:43:14');
INSERT INTO `platform_resource_api` VALUES ('522897301509767168', 'clients', 'clients', 'opencloud-auth-producer', '/clients', '0', '', '1', '2018-12-13 22:07:11', '2018-12-18 10:43:27');
INSERT INTO `platform_resource_api` VALUES ('522897301698510848', 'updateClient', 'updateClient', 'opencloud-auth-producer', '/clients/update', '0', '', '1', '2018-12-13 22:07:11', '2018-12-18 10:43:27');
INSERT INTO `platform_resource_api` VALUES ('522897301849505792', 'addClient', 'addClient', 'opencloud-auth-producer', '/clients/add', '0', '', '1', '2018-12-13 22:07:11', '2018-12-18 10:43:27');
INSERT INTO `platform_resource_api` VALUES ('522897302004695040', 'getClient', 'getClient', 'opencloud-auth-producer', '/clients/{clientId}', '0', '', '1', '2018-12-13 22:07:11', '2018-12-18 10:43:27');
INSERT INTO `platform_resource_api` VALUES ('522897302168272896', 'resetSecret', 'resetSecret', 'opencloud-auth-producer', '/clients/reset', '0', '', '1', '2018-12-13 22:07:11', '2018-12-18 10:43:27');
INSERT INTO `platform_resource_api` VALUES ('522897302411542528', 'removeClinet', 'removeClinet', 'opencloud-auth-producer', '/clients/remove', '0', '', '1', '2018-12-13 22:07:11', '2018-12-18 10:43:27');
INSERT INTO `platform_resource_api` VALUES ('522897302575120384', 'principal', '获取当前登录租户', 'opencloud-auth-producer', '/principal', '0', '', '1', '2018-12-13 22:07:11', '2018-12-18 10:43:27');

-- ----------------------------
-- Table structure for platform_resource_menu
-- ----------------------------
DROP TABLE IF EXISTS `platform_resource_menu`;
CREATE TABLE `platform_resource_menu` (
  `menu_id` bigint(20) NOT NULL COMMENT '菜单Id',
  `menu_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单编码',
  `menu_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
  `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单标题',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单',
  `url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '请求路径',
  `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级 越小越靠前',
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
  `enabled` tinyint(1) NOT NULL DEFAULT '1' COMMENT '是否可用',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Records of platform_resource_menu
-- ----------------------------
INSERT INTO `platform_resource_menu` VALUES ('1', 'system', '系统安全', '系统安全', '0', '', '0', '系统安全', '1', '2018-07-29 21:20:10', '2018-12-12 01:50:30');
INSERT INTO `platform_resource_menu` VALUES ('2', 'authority', '权限管理', '权限管理', '1', '/authoritys/index', '0', '权限管理', '1', '2018-07-29 21:20:13', '2018-12-12 00:21:30');
INSERT INTO `platform_resource_menu` VALUES ('3', 'menu', '菜单管理', '菜单管理', '1', '/menus/index', '0', '菜单管理', '1', '2018-07-29 21:20:13', '2018-12-12 01:50:39');
INSERT INTO `platform_resource_menu` VALUES ('4', 'server', '服务运维', '服务运维', '0', '', '0', '服务运维', '1', '2018-07-29 21:20:13', '2018-12-12 00:17:26');
INSERT INTO `platform_resource_menu` VALUES ('5', 'route', '网关路由', '网关路由', '4', '/gateway/route/index', '0', '网关路由', '1', '2018-07-29 21:20:13', '2018-12-11 21:40:08');
INSERT INTO `platform_resource_menu` VALUES ('6', 'api', 'API管理', 'API管理', '4', '/gateway/api/index', '0', 'API管理', '1', '2018-07-29 21:20:13', '2018-12-12 00:02:50');
INSERT INTO `platform_resource_menu` VALUES ('7', 'trace', '服务追踪', '服务追踪', '4', 'http://localhost:7080', '0', '服务追踪', '1', '2018-11-30 02:11:18', '2018-11-30 02:11:20');

-- ----------------------------
-- Table structure for platform_resource_permission
-- ----------------------------
DROP TABLE IF EXISTS `platform_resource_permission`;
CREATE TABLE `platform_resource_permission` (
  `id` bigint(20) NOT NULL,
  `code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '授权编码: 资源类型+资源名称  API_INFO',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
  `url` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `resource_id` bigint(20) NOT NULL COMMENT '资源ID',
  `resource_pid` bigint(20) DEFAULT NULL COMMENT '资源父节点',
  `resource_type` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '资源类型:api,menu,button',
  `identity_id` bigint(20) NOT NULL COMMENT '授权身份ID',
  `identity_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '授权身份编码',
  `identity_prefix` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '授权身份前缀:用户(USER_) 、角色(ROLE_)',
  `service_id` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `enabled` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源授权表';

-- ----------------------------
-- Records of platform_resource_permission
-- ----------------------------
INSERT INTO `platform_resource_permission` VALUES ('1', 'MENU_SYSTEM', '系统安全', '', '1', '0', 'MENU', '1', 'ROLE_superAdmin', 'ROLE_', 'platform-rbac-producer', '1');
INSERT INTO `platform_resource_permission` VALUES ('2', 'MENU_AUTHORITY', '权限管理', '/authoritys/index', '2', '1', 'MENU', '1', 'ROLE_superAdmin', 'ROLE_', 'platform-rbac-producer', '1');
INSERT INTO `platform_resource_permission` VALUES ('3', 'MENU_MENU', '菜单管理', '/menus/index', '3', '1', 'MENU', '1', 'ROLE_superAdmin', 'ROLE_', 'platform-rbac-producer', '1');
INSERT INTO `platform_resource_permission` VALUES ('4', 'MENU_SERVER', '服务维护', null, '4', '0', 'MENU', '1', 'ROLE_superAdmin', 'ROLE_', 'platform-rbac-producer', '1');
INSERT INTO `platform_resource_permission` VALUES ('5', 'MENU_TRACE', '服务追踪', 'http://localhost:7080', '7', '4', 'MENU', '1', 'ROLE_superAdmin', 'ROLE_', 'platform-rbac-producer', '1');

-- ----------------------------
-- Table structure for platform_roles
-- ----------------------------
DROP TABLE IF EXISTS `platform_roles`;
CREATE TABLE `platform_roles` (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `role_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '角色编码',
  `role_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
  `description` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
  `enabled` int(10) NOT NULL COMMENT '是否可用',
  `create_time` datetime NOT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';

-- ----------------------------
-- Records of platform_roles
-- ----------------------------
INSERT INTO `platform_roles` VALUES ('1', 'superAdmin', '超级管理员', '', '1', '2018-07-29 21:14:50', '2018-07-29 21:14:53');
INSERT INTO `platform_roles` VALUES ('2', 'platfromAdmin', '平台管理员', '', '1', '2018-07-29 21:14:54', '2018-07-29 21:15:00');
INSERT INTO `platform_roles` VALUES ('3', 'developer', '普通开发者', '', '1', '2018-07-29 21:14:54', '2018-07-29 21:15:00');
INSERT INTO `platform_roles` VALUES ('4', 'companyDeveloper', '企业开发者', '', '1', '2018-07-29 21:14:54', '2018-07-29 21:15:00');

-- ----------------------------
-- Table structure for platform_roles_member
-- ----------------------------
DROP TABLE IF EXISTS `platform_roles_member`;
CREATE TABLE `platform_roles_member` (
  `tenant_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  KEY `fk_user` (`tenant_id`),
  KEY `fk_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色和用户关系表';

-- ----------------------------
-- Records of platform_roles_member
-- ----------------------------
INSERT INTO `platform_roles_member` VALUES ('521677655146233856', '4');
INSERT INTO `platform_roles_member` VALUES ('521677655146233856', '1');
INSERT INTO `platform_roles_member` VALUES ('521677655146233856', '2');

-- ----------------------------
-- Table structure for platform_tenant_account
-- ----------------------------
DROP TABLE IF EXISTS `platform_tenant_account`;
CREATE TABLE `platform_tenant_account` (
  `account_id` bigint(20) NOT NULL,
  `tenant_id` bigint(20) DEFAULT NULL COMMENT '用户Id',
  `account` varchar(255) DEFAULT NULL COMMENT '标识：手机号、邮箱、 用户名、或第三方应用的唯一标识',
  `password` varchar(255) DEFAULT NULL COMMENT '密码凭证：站内的保存密码、站外的不保存或保存token）',
  `account_type` varchar(255) DEFAULT NULL COMMENT '登录类型:password-密码、mobile-手机号、email-邮箱、weixin-微信、weibo-微博、qq-等等',
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户账号-用于登陆认证';

-- ----------------------------
-- Records of platform_tenant_account
-- ----------------------------
INSERT INTO `platform_tenant_account` VALUES ('521677655368531968', '521677655146233856', 'admin', '$2a$10$A7EHximvrsa4ESX1uSlkJupbg2PLO2StzDzy67NX4YV25MxmbGvXu', 'username');
INSERT INTO `platform_tenant_account` VALUES ('521677655444029440', '521677655146233856', '515608851@qq.com', '$2a$10$A7EHximvrsa4ESX1uSlkJupbg2PLO2StzDzy67NX4YV25MxmbGvXu', 'email');
INSERT INTO `platform_tenant_account` VALUES ('521677655586635776', '521677655146233856', '18518226890', '$2a$10$A7EHximvrsa4ESX1uSlkJupbg2PLO2StzDzy67NX4YV25MxmbGvXu', 'mobile');

-- ----------------------------
-- Table structure for platform_tenant_account_logs
-- ----------------------------
DROP TABLE IF EXISTS `platform_tenant_account_logs`;
CREATE TABLE `platform_tenant_account_logs` (
  `id` bigint(20) NOT NULL,
  `login_time` datetime NOT NULL,
  `login_ip` varchar(255) DEFAULT NULL COMMENT '登录Ip',
  `login_agent` varchar(500) DEFAULT NULL COMMENT '登录设备',
  `login_nums` int(11) DEFAULT NULL COMMENT '登录次数',
  `tenant_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号登录日志';

-- ----------------------------
-- Records of platform_tenant_account_logs
-- ----------------------------
INSERT INTO `platform_tenant_account_logs` VALUES ('521678652778217472', '2018-12-10 13:24:43', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '1', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521700727421140992', '2018-12-10 14:52:26', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '2', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521701216598622208', '2018-12-10 14:54:22', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '3', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521702505810231296', '2018-12-10 14:59:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '4', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521703012188553216', '2018-12-10 15:01:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '5', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521703778710192128', '2018-12-10 15:04:33', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '6', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521704361500344320', '2018-12-10 15:06:52', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '7', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521709048966414336', '2018-12-10 15:25:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '8', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521720875301470208', '2018-12-10 16:12:29', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '9', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521723312674439168', '2018-12-10 16:22:10', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '10', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521723358795005952', '2018-12-10 16:22:21', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '11', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521724113706811392', '2018-12-10 16:25:21', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '12', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521725911280648192', '2018-12-10 16:32:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '13', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521726048140787712', '2018-12-10 16:33:03', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '14', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521727283166183424', '2018-12-10 16:37:57', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '15', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521727431300612096', '2018-12-10 16:38:32', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '16', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521727479866458112', '2018-12-10 16:38:44', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '17', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521727934508040192', '2018-12-10 16:40:32', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '18', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521727964165963776', '2018-12-10 16:40:39', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '19', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521728024412946432', '2018-12-10 16:40:54', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '20', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521728055958306816', '2018-12-10 16:41:01', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '21', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521728092952068096', '2018-12-10 16:41:10', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '22', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521729635885514752', '2018-12-10 16:47:18', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '23', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521729678478671872', '2018-12-10 16:47:28', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '24', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521729696770031616', '2018-12-10 16:47:32', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '25', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521729726390206464', '2018-12-10 16:47:40', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '26', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521730125406928896', '2018-12-10 16:49:15', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '27', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521732306155601920', '2018-12-10 16:57:55', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '28', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521743094773710848', '2018-12-10 17:40:47', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '29', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521743505572233216', '2018-12-10 17:42:25', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '30', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521744963617161216', '2018-12-10 17:48:12', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '31', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521745043761922048', '2018-12-10 17:48:31', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '32', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521745216730824704', '2018-12-10 17:49:13', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '33', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521745385199239168', '2018-12-10 17:49:53', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '34', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521745725550231552', '2018-12-10 17:51:14', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '35', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521746125191905280', '2018-12-10 17:52:49', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '36', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521747699695878144', '2018-12-10 17:59:05', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '37', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('521748019318620160', '2018-12-10 18:00:21', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '38', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522001658952024064', '2018-12-11 10:48:13', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '39', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522160522737483776', '2018-12-11 21:19:29', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '40', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522193261394132992', '2018-12-11 23:29:35', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '41', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522207601920835584', '2018-12-12 00:26:34', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36', '42', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522207892330250240', '2018-12-12 00:27:43', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0', '43', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522209451176886272', '2018-12-12 00:33:55', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '44', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522209556814626816', '2018-12-12 00:34:20', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '45', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522210414658846720', '2018-12-12 00:37:45', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '46', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522214619675099136', '2018-12-12 00:54:27', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '47', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522220274985205760', '2018-12-12 01:16:55', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '48', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522223475109134336', '2018-12-12 01:29:38', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '49', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522225030243811328', '2018-12-12 01:35:49', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '50', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522226585256853504', '2018-12-12 01:42:00', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '51', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522228116207173632', '2018-12-12 01:48:05', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '52', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522519574495625216', '2018-12-12 21:06:14', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '53', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522521052031483904', '2018-12-12 21:12:06', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '54', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522522125676838912', '2018-12-12 21:16:22', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '55', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522881305742934016', '2018-12-13 21:03:37', '192.168.1.77', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '56', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522884963100524544', '2018-12-13 21:18:09', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '57', '521677655146233856');
INSERT INTO `platform_tenant_account_logs` VALUES ('522899530824286208', '2018-12-13 22:16:03', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '58', '521677655146233856');

-- ----------------------------
-- Table structure for platform_tenant_profile
-- ----------------------------
DROP TABLE IF EXISTS `platform_tenant_profile`;
CREATE TABLE `platform_tenant_profile` (
  `tenant_id` bigint(20) NOT NULL COMMENT '用户ID',
  `user_name` varchar(255) DEFAULT NULL COMMENT '登陆账号',
  `nick_name` varchar(50) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT '' COMMENT '头像',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) DEFAULT NULL COMMENT '手机号',
  `tenant_type` int(11) DEFAULT '0' COMMENT '用户类型:0-内部用户 1-服务提供商 2-自研开发者',
  `company_id` bigint(20) DEFAULT NULL COMMENT '企业ID',
  `description` varchar(255) DEFAULT '' COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `register_ip` varchar(100) DEFAULT NULL COMMENT '注册IP',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `state` int(11) DEFAULT '1' COMMENT '状态:0-禁用 1-启用 2-锁定',
  PRIMARY KEY (`tenant_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户信息';

-- ----------------------------
-- Records of platform_tenant_profile
-- ----------------------------
INSERT INTO `platform_tenant_profile` VALUES ('521677655146233856', 'admin', 'admin', null, '515608851@qq.com', '18518226890', '0', null, null, '2018-12-10 13:20:45', '2018-12-10 13:20:45', null, '2018-12-10 13:20:45', '1');
SET FOREIGN_KEY_CHECKS=1;
