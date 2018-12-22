/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : open-platform

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-23 02:32:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for gateway_rate_limit
-- ----------------------------
DROP TABLE IF EXISTS `gateway_rate_limit`;
CREATE TABLE `gateway_rate_limit` (
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
-- Records of gateway_rate_limit
-- ----------------------------

-- ----------------------------
-- Table structure for gateway_route
-- ----------------------------
DROP TABLE IF EXISTS `gateway_route`;
CREATE TABLE `gateway_route` (
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
-- Records of gateway_route
-- ----------------------------

-- ----------------------------
-- Table structure for system_access
-- ----------------------------
DROP TABLE IF EXISTS `system_access`;
CREATE TABLE `system_access` (
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
                               `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源授权表';

-- ----------------------------
-- Records of system_access
-- ----------------------------
INSERT INTO `system_access` VALUES ('1', 'menu_system', '系统安全', '', '1', '0', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_access` VALUES ('2', 'menu_authority', '权限管理', '/authoritys/index', '2', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_access` VALUES ('3', 'menu_menus', '菜单管理', '/menus/index', '3', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_access` VALUES ('4', 'menu_server', '服务维护', null, '4', '0', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_access` VALUES ('5', 'menu_trace', '服务追踪', 'http://localhost:7080', '7', '4', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');

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
                                     `login_ip` varchar(255) DEFAULT NULL COMMENT '登录Ip',
                                     `login_agent` varchar(500) DEFAULT NULL COMMENT '登录设备',
                                     `login_nums` int(11) DEFAULT NULL COMMENT '登录次数',
                                     `user_id` bigint(20) NOT NULL,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='账号登录日志';

-- ----------------------------
-- Records of system_account_logs
-- ----------------------------
INSERT INTO `system_account_logs` VALUES ('521678652778217472', '2018-12-10 13:24:43', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '1', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521700727421140992', '2018-12-10 14:52:26', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '2', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521701216598622208', '2018-12-10 14:54:22', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '3', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521702505810231296', '2018-12-10 14:59:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '4', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521703012188553216', '2018-12-10 15:01:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '5', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521703778710192128', '2018-12-10 15:04:33', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '6', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521704361500344320', '2018-12-10 15:06:52', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '7', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521709048966414336', '2018-12-10 15:25:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '8', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521720875301470208', '2018-12-10 16:12:29', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '9', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521723312674439168', '2018-12-10 16:22:10', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '10', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521723358795005952', '2018-12-10 16:22:21', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '11', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521724113706811392', '2018-12-10 16:25:21', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '12', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521725911280648192', '2018-12-10 16:32:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '13', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521726048140787712', '2018-12-10 16:33:03', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '14', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521727283166183424', '2018-12-10 16:37:57', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '15', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521727431300612096', '2018-12-10 16:38:32', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '16', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521727479866458112', '2018-12-10 16:38:44', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '17', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521727934508040192', '2018-12-10 16:40:32', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '18', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521727964165963776', '2018-12-10 16:40:39', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '19', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521728024412946432', '2018-12-10 16:40:54', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '20', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521728055958306816', '2018-12-10 16:41:01', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '21', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521728092952068096', '2018-12-10 16:41:10', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '22', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521729635885514752', '2018-12-10 16:47:18', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '23', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521729678478671872', '2018-12-10 16:47:28', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '24', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521729696770031616', '2018-12-10 16:47:32', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '25', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521729726390206464', '2018-12-10 16:47:40', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '26', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521730125406928896', '2018-12-10 16:49:15', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '27', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521732306155601920', '2018-12-10 16:57:55', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '28', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521743094773710848', '2018-12-10 17:40:47', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '29', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521743505572233216', '2018-12-10 17:42:25', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '30', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521744963617161216', '2018-12-10 17:48:12', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '31', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521745043761922048', '2018-12-10 17:48:31', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '32', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521745216730824704', '2018-12-10 17:49:13', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '33', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521745385199239168', '2018-12-10 17:49:53', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '34', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521745725550231552', '2018-12-10 17:51:14', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '35', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521746125191905280', '2018-12-10 17:52:49', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '36', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521747699695878144', '2018-12-10 17:59:05', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '37', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('521748019318620160', '2018-12-10 18:00:21', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '38', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522001658952024064', '2018-12-11 10:48:13', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '39', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522160522737483776', '2018-12-11 21:19:29', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '40', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522193261394132992', '2018-12-11 23:29:35', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '41', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522207601920835584', '2018-12-12 00:26:34', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36', '42', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522207892330250240', '2018-12-12 00:27:43', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0', '43', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522209451176886272', '2018-12-12 00:33:55', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '44', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522209556814626816', '2018-12-12 00:34:20', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '45', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522210414658846720', '2018-12-12 00:37:45', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '46', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522214619675099136', '2018-12-12 00:54:27', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '47', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522220274985205760', '2018-12-12 01:16:55', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '48', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522223475109134336', '2018-12-12 01:29:38', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '49', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522225030243811328', '2018-12-12 01:35:49', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '50', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522226585256853504', '2018-12-12 01:42:00', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '51', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522228116207173632', '2018-12-12 01:48:05', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '52', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522519574495625216', '2018-12-12 21:06:14', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '53', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522521052031483904', '2018-12-12 21:12:06', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '54', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522522125676838912', '2018-12-12 21:16:22', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '55', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522881305742934016', '2018-12-13 21:03:37', '192.168.1.77', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '56', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522884963100524544', '2018-12-13 21:18:09', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '57', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('522899530824286208', '2018-12-13 22:16:03', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '58', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('525302912906166272', '2018-12-20 13:26:14', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '59', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('525337416806957056', '2018-12-20 15:43:20', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '60', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('525423532281167872', '2018-12-20 21:25:31', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '61', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('525426126474969088', '2018-12-20 21:35:50', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '62', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('525780476409937920', '2018-12-21 21:03:54', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '63', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('525842751758008320', '2018-12-22 01:11:21', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '64', '521677655146233856');

-- ----------------------------
-- Table structure for system_action
-- ----------------------------
DROP TABLE IF EXISTS `system_action`;
CREATE TABLE `system_action` (
                               `action_id` bigint(20) NOT NULL COMMENT '资源ID',
                               `action_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源编码',
                               `action_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
                               `action_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
                               `url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '资源路径',
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
INSERT INTO `system_action` VALUES ('1', 'btnDetail', '详情', '详情', '/funcPages/adManager', '2', '0', '1', '2018-07-29 21:20:10', '2018-09-25 23:02:11');
INSERT INTO `system_action` VALUES ('2', 'btnSave', '保存', '保存', '/index', '2', '0', '1', '2018-07-29 21:20:13', '2018-09-25 23:02:11');

-- ----------------------------
-- Table structure for system_api
-- ----------------------------
DROP TABLE IF EXISTS `system_api`;
CREATE TABLE `system_api` (
                            `api_id` bigint(20) NOT NULL COMMENT '资源ID',
                            `api_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源编码',
                            `api_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
                            `api_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
                            `service_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '服务ID',
                            `url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '资源路径',
                            `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级',
                            `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                            `create_time` datetime NOT NULL,
                            `update_time` datetime DEFAULT NULL,
                            PRIMARY KEY (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='API资源表';

-- ----------------------------
-- Records of system_api
-- ----------------------------
INSERT INTO `system_api` VALUES ('525425413166465024', 'client', 'client', null, 'opencloud-auth-producer', '/client', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:44');
INSERT INTO `system_api` VALUES ('525425413371985920', 'updateClient', 'updateClient', null, 'opencloud-auth-producer', '/client/update', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:44');
INSERT INTO `system_api` VALUES ('525425413543952384', 'addClient', 'addClient', null, 'opencloud-auth-producer', '/client/add', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:44');
INSERT INTO `system_api` VALUES ('525425413690753024', 'resetSecret', 'resetSecret', null, 'opencloud-auth-producer', '/client/reset', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:44');
INSERT INTO `system_api` VALUES ('525425413850136576', 'getClient', 'getClient', null, 'opencloud-auth-producer', '/client/{clientId}', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:44');
INSERT INTO `system_api` VALUES ('525425414009520128', 'removeClinet', 'removeClinet', null, 'opencloud-auth-producer', '/client/remove', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:44');
INSERT INTO `system_api` VALUES ('525425414164709376', 'user', '获取当前登录系统用户', null, 'opencloud-auth-producer', '/user', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:44');
INSERT INTO `system_api` VALUES ('525425414911295488', 'access', '获取授权列表', null, 'opencloud-base-producer', '/access', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:29');
INSERT INTO `system_api` VALUES ('525425415074873344', 'addLoginLog', 'addLoginLog', null, 'opencloud-base-producer', '/account/logs/add', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:30');
INSERT INTO `system_api` VALUES ('525425415397834752', 'login', 'login', null, 'opencloud-base-producer', '/account/login', '0', '1', '2018-12-20 21:33:00', '2018-12-22 00:05:30');
INSERT INTO `system_api` VALUES ('525425415561412608', 'action', '动作列表', null, 'opencloud-base-producer', '/action', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:30');
INSERT INTO `system_api` VALUES ('525425415750156288', 'getAction', '获取动作资源', null, 'opencloud-base-producer', '/action/{actionId}', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:30');
INSERT INTO `system_api` VALUES ('525425415959871488', 'updateAction', '编辑动作资源', null, 'opencloud-base-producer', '/action/update', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425416123449344', 'removeAction', '移除动作', null, 'opencloud-base-producer', '/action/remove', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425416425439232', 'enableAction', '启用动作资源', null, 'opencloud-base-producer', '/action/enable', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425416622571520', 'addAction', '添加动作资源', null, 'opencloud-base-producer', '/action/add', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425416786149376', 'disableAction', '禁用动作资源', null, 'opencloud-base-producer', '/action/disable', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425416970698752', 'api', 'Api列表', null, 'opencloud-base-producer', '/api', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425417146859520', 'removeApi', '移除Api', null, 'opencloud-base-producer', '/api/remove', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425417306243072', 'addApi', '添加Api资源', null, 'opencloud-base-producer', '/api/add', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:32');
INSERT INTO `system_api` VALUES ('525425417457238016', 'updateApi', '编辑Api资源', null, 'opencloud-base-producer', '/api/update', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:32');
INSERT INTO `system_api` VALUES ('525425417620815872', 'disableApi', '禁用Api资源', null, 'opencloud-base-producer', '/api/disable', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425417771810816', 'getApi', '获取Api资源', null, 'opencloud-base-producer', '/api/{apiId}', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:31');
INSERT INTO `system_api` VALUES ('525425417935388672', 'enableApi', '启用Api资源', null, 'opencloud-base-producer', '/api/enable', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:32');
INSERT INTO `system_api` VALUES ('525425418191241216', 'app', '应用列表', null, 'opencloud-base-producer', '/app', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425418434510848', 'getApp', '获取应用信息', null, 'opencloud-base-producer', '/app/{appId}', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:32');
INSERT INTO `system_api` VALUES ('525425418614865920', 'addApp', '添加应用', null, 'opencloud-base-producer', '/app/add', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425418774249472', 'updateApp', '编辑应用', null, 'opencloud-base-producer', '/app/update', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425418925244416', 'removeApp', '删除应用', null, 'opencloud-base-producer', '/app/remove', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425419088822272', 'resetSecret', '重置秘钥', null, 'opencloud-base-producer', '/app/reset', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:32');
INSERT INTO `system_api` VALUES ('525425419239817216', 'updateMenu', '编辑菜单资源', null, 'opencloud-base-producer', '/menu/update', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425419399200768', 'menu', '菜单列表', null, 'opencloud-base-producer', '/menu', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425419550195712', 'menuAll', '菜单列表', null, 'opencloud-base-producer', '/menu/all', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425419713773568', 'disableMenu', '禁用菜单资源', null, 'opencloud-base-producer', '/menu/disable', '0', '1', '2018-12-20 21:33:01', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425419868962816', 'getMenu', '获取菜单资源', null, 'opencloud-base-producer', '/menu/{menuId}', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425420028346368', 'addMenu', '添加菜单资源', null, 'opencloud-base-producer', '/menu/add', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425420179341312', 'enableMenu', '启用菜单资源', null, 'opencloud-base-producer', '/menu/enable', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425420313559040', 'removeMenu', '移除菜单', null, 'opencloud-base-producer', '/menu/remove', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425420464553984', 'getRole', '获取角色信息', null, 'opencloud-base-producer', '/role/{roleId}', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425420628131840', 'role', '角色列表', null, 'opencloud-base-producer', '/role', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425420779126784', 'updateRole', '更新角色', null, 'opencloud-base-producer', '/role/update', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');
INSERT INTO `system_api` VALUES ('525425420946898944', 'removeRole', '删除角色', null, 'opencloud-base-producer', '/role/remove', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');
INSERT INTO `system_api` VALUES ('525425421089505280', 'addRole', '添加角色', null, 'opencloud-base-producer', '/role/add', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:33');
INSERT INTO `system_api` VALUES ('525425421215334400', 'user', '系统用户列表', null, 'opencloud-base-producer', '/user', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');
INSERT INTO `system_api` VALUES ('525425421332774912', 'userActions', '当前用户可访问操作资源', null, 'opencloud-base-producer', '/user/actions', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');
INSERT INTO `system_api` VALUES ('525425421462798336', 'userApis', '当前用户可访问API资源', null, 'opencloud-base-producer', '/user/apis', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');
INSERT INTO `system_api` VALUES ('525425421617987584', 'userMenus', '当前用户可访问菜单资源', null, 'opencloud-base-producer', '/user/menus', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');
INSERT INTO `system_api` VALUES ('525425421777371136', 'updateUser', '更新系统用户', null, 'opencloud-base-producer', '/user/update', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');
INSERT INTO `system_api` VALUES ('525425421890617344', 'addUser', '添加系统用户', null, 'opencloud-base-producer', '/user/add', '0', '1', '2018-12-20 21:33:02', '2018-12-22 00:05:34');

-- ----------------------------
-- Table structure for system_app
-- ----------------------------
DROP TABLE IF EXISTS `system_app`;
CREATE TABLE `system_app` (
                            `app_id` varchar(20) NOT NULL COMMENT '客户端ID',
                            `app_secret` varchar(255) NOT NULL COMMENT '客户端秘钥',
                            `app_name` varchar(255) NOT NULL COMMENT 'app名称',
                            `app_name_en` varchar(255) NOT NULL COMMENT 'app英文名称',
                            `app_icon` varchar(255) DEFAULT NULL COMMENT '应用图标',
                            `app_type` varchar(50) NOT NULL COMMENT 'app类型:server-服务应用 app-手机应用 pc-PC网页应用 wap-手机网页应用',
                            `app_desc` varchar(255) DEFAULT NULL COMMENT 'app描述',
                            `app_os` varchar(25) DEFAULT NULL COMMENT '移动应用操作系统:ios-苹果 android-安卓',
                            `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID:默认为0',
                            `user_type` tinyint(3) NOT NULL DEFAULT '0' COMMENT '用户类型:0-内部用户 1-服务提供商 2-自研开发者',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_app
-- ----------------------------
INSERT INTO `system_app` VALUES ('gateway', '123456', '开放云平台', 'ApiGateway', null, 'server', '开放云平台', null, '0', '0', '2018-11-12 17:48:45', '2018-11-16 18:46:31', '1');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
                             `menu_id` bigint(20) NOT NULL COMMENT '菜单Id',
                             `menu_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单编码',
                             `menu_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
                             `menu_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
                             `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单标题',
                             `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单',
                             `url` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '请求路径',
                             `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级 越小越靠前',
                             `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('1', 'system', '系统安全', '系统安全', '系统安全', '0', '', '0', '1', '2018-07-29 21:20:10', '2018-12-12 01:50:30');
INSERT INTO `system_menu` VALUES ('2', 'authority', '权限管理', '权限管理', '权限管理', '1', '/authoritys/index', '0', '1', '2018-07-29 21:20:13', '2018-12-12 00:21:30');
INSERT INTO `system_menu` VALUES ('3', 'menu', '菜单管理', '菜单管理', '菜单管理', '1', '/menus/index', '0', '1', '2018-07-29 21:20:13', '2018-12-12 01:50:39');
INSERT INTO `system_menu` VALUES ('4', 'server', '服务运维', '服务运维', '服务运维', '0', '', '0', '1', '2018-07-29 21:20:13', '2018-12-12 00:17:26');
INSERT INTO `system_menu` VALUES ('5', 'route', '网关路由', '网关路由', '网关路由', '4', '/gateway/route/index', '0', '1', '2018-07-29 21:20:13', '2018-12-11 21:40:08');
INSERT INTO `system_menu` VALUES ('6', 'api', 'API管理', 'API管理', 'API管理', '4', '/gateway/api/index', '0', '1', '2018-07-29 21:20:13', '2018-12-12 00:02:50');
INSERT INTO `system_menu` VALUES ('7', 'trace', '服务追踪', '服务追踪', '服务追踪', '4', 'http://localhost:7080', '0', '1', '2018-11-30 02:11:18', '2018-11-30 02:11:20');

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
                             `role_id` bigint(20) NOT NULL COMMENT '角色ID',
                             `role_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '角色编码',
                             `role_name` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色名称',
                             `role_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '角色描述',
                             `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                             `create_time` datetime NOT NULL,
                             `update_time` datetime DEFAULT NULL,
                             PRIMARY KEY (`role_id`),
                             UNIQUE KEY `role_code` (`role_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='角色';

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('1', 'superAdmin', '超级管理员', '', '1', '2018-07-29 21:14:50', '2018-07-29 21:14:53');
INSERT INTO `system_role` VALUES ('2', 'platfromAdmin', '平台管理员', '', '1', '2018-07-29 21:14:54', '2018-07-29 21:15:00');
INSERT INTO `system_role` VALUES ('3', 'developer', '普通开发者', '', '1', '2018-07-29 21:14:54', '2018-07-29 21:15:00');
INSERT INTO `system_role` VALUES ('4', 'companyDeveloper', '企业开发者', '', '1', '2018-07-29 21:14:54', '2018-07-29 21:15:00');

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
                             `user_type` int(11) DEFAULT '0' COMMENT '用户类型:0-内部用户 1-服务提供商 2-自研开发者',
                             `user_desc` varchar(255) DEFAULT '' COMMENT '描述',
                             `company_id` bigint(20) DEFAULT NULL COMMENT '企业ID',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             `register_ip` varchar(100) DEFAULT NULL COMMENT '注册IP',
                             `register_time` datetime DEFAULT NULL COMMENT '注册时间',
                             `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态:0-禁用 1-启用 2-锁定',
                             PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录用户信息';

-- ----------------------------
-- Records of system_user
-- ----------------------------
INSERT INTO `system_user` VALUES ('521677655146233856', 'admin', 'admin', null, '515608851@qq.com', '18518226890', '0', null, null, '2018-12-10 13:20:45', '2018-12-10 13:20:45', null, '2018-12-10 13:20:45', '1');

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
INSERT INTO `system_user_role` VALUES ('521677655146233856', '4');
INSERT INTO `system_user_role` VALUES ('521677655146233856', '1');
INSERT INTO `system_user_role` VALUES ('521677655146233856', '2');
SET FOREIGN_KEY_CHECKS=1;
