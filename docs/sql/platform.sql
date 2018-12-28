/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : open-platform

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-28 16:51:23
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
INSERT INTO `system_account_logs` VALUES ('526229825120108544', '2018-12-23 02:49:27', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '65', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('526863892366753792', '2018-12-24 20:49:00', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '66', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('526873579518689280', '2018-12-24 21:27:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '67', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('526897012117864448', '2018-12-24 23:00:36', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '68', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('526913673294249984', '2018-12-25 00:06:49', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '69', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('527126245792546816', '2018-12-25 14:11:30', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '70', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('527126877173710848', '2018-12-25 14:14:01', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '71', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('527505289012838400', '2018-12-26 15:17:41', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '72', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('527514862318780416', '2018-12-26 15:55:43', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '73', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('527515074034663424', '2018-12-26 15:56:34', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '74', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('527986549564899328', '2018-12-27 23:10:02', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Postman/6.6.1 Chrome/59.0.3071.115 Electron/1.8.4 Safari/537.36', '75', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('528215254287515648', '2018-12-28 14:18:50', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '76', '521677655146233856');
INSERT INTO `system_account_logs` VALUES ('528222838822273024', '2018-12-28 14:48:58', '0:0:0:0:0:0:0:1', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 UBrowser/6.2.4094.1 Safari/537.36', '77', '521677655146233856');

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
  `api_category` varchar(20) COLLATE utf8_bin DEFAULT '0' COMMENT '接口分类:默认为0未分类',
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
INSERT INTO `system_api` VALUES ('528217376529842176', 'client', 'client', '0', '', 'opencloud-auth-producer', '/client', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:21');
INSERT INTO `system_api` VALUES ('528217376626311168', 'getClient', 'getClient', '0', '', 'opencloud-auth-producer', '/client/{clientId}', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:21');
INSERT INTO `system_api` VALUES ('528217376701808640', 'addClient', 'addClient', '0', '', 'opencloud-auth-producer', '/client/add', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:21');
INSERT INTO `system_api` VALUES ('528217376768917504', 'resetSecret', 'resetSecret', '0', '', 'opencloud-auth-producer', '/client/reset', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:21');
INSERT INTO `system_api` VALUES ('528217376886358016', 'removeClinet', 'removeClinet', '0', '', 'opencloud-auth-producer', '/client/remove', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:21');
INSERT INTO `system_api` VALUES ('528217376957661184', 'updateClient', 'updateClient', '0', '', 'opencloud-auth-producer', '/client/update', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:21');
INSERT INTO `system_api` VALUES ('528217378207563776', 'authUser', '当前已认证用户', '0', '', 'opencloud-auth-producer', '/user', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:21');
INSERT INTO `system_api` VALUES ('528217378551496704', 'login', 'login', '0', '', 'opencloud-base-producer', '/account/login', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217378606022656', 'addLoginLog', 'addLoginLog', '0', '', 'opencloud-base-producer', '/account/logs/add', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217378677325824', 'getAction', '获取动作资源', '0', '', 'opencloud-base-producer', '/action/{actionId}', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217378744434688', 'action', '动作列表', '0', '', 'opencloud-base-producer', '/action', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217378790572032', 'updateStatus', '更新状态', '0', '', 'opencloud-base-producer', '/role/update/status', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217378840903680', 'actionList', '操作列表', '0', '', 'opencloud-base-producer', '/action/list', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217378895429632', 'addAction', '添加动作资源', '0', '', 'opencloud-base-producer', '/action/add', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217378941566976', 'updateAction', '编辑动作资源', '0', '', 'opencloud-base-producer', '/action/update', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217378991898624', 'removeAction', '移除动作', '0', '', 'opencloud-base-producer', '/action/remove', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217379038035968', 'api', '接口列表', '0', '', 'opencloud-base-producer', '/api', '0', '1', '2018-12-28 14:27:16', '2018-12-28 16:26:22');
INSERT INTO `system_api` VALUES ('528217379197419520', 'getApi', '获取接口资源', '0', '', 'opencloud-base-producer', '/api/{apiId}', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379260334080', 'updateApi', '编辑Api资源', '0', '', 'opencloud-base-producer', '/api/update', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379310665728', 'addApi', '添加Api资源', '0', '', 'opencloud-base-producer', '/api/add', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379360997376', 'apiList', '接口列表', '0', '', 'opencloud-base-producer', '/api/list', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:43:36');
INSERT INTO `system_api` VALUES ('528217379411329024', 'removeApi', '移除接口', '0', '', 'opencloud-base-producer', '/api/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379470049280', 'addApp', '添加应用', '0', '', 'opencloud-base-producer', '/app/add', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379532963840', 'removeApp', '删除应用', '0', '', 'opencloud-base-producer', '/app/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379583295488', 'app', '应用列表', '0', '', 'opencloud-base-producer', '/app', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379637821440', 'updateApp', '编辑应用', '0', '', 'opencloud-base-producer', '/app/update', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379679764480', 'getApp', '获取应用信息', '0', '', 'opencloud-base-producer', '/app/{appId}', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379725901824', 'resetSecret', '重置秘钥', '0', '', 'opencloud-base-producer', '/app/reset', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379772039168', 'grantAccess', '获取授权列表', '0', '', 'opencloud-base-producer', '/grant/access', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379897868288', 'getMenu', '获取菜单资源', '0', '', 'opencloud-base-producer', '/menu/{menuId}', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217379960782848', 'updateMenu', '编辑菜单资源', '0', '', 'opencloud-base-producer', '/menu/update', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380006920192', 'menu', '菜单列表', '0', '', 'opencloud-base-producer', '/menu', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380053057536', 'menuList', '菜单列表', '0', '', 'opencloud-base-producer', '/menu/list', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380095000576', 'removeMenu', '移除菜单', '0', '', 'opencloud-base-producer', '/menu/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380141137920', 'addMenu', '添加菜单资源', '0', '', 'opencloud-base-producer', '/menu/add', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380241801216', 'role', '角色列表', '0', '', 'opencloud-base-producer', '/role', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380489265152', 'getRole', '获取角色信息', '0', '', 'opencloud-base-producer', '/role/{roleId}', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380527013888', 'addRole', '添加角色', '0', '', 'opencloud-base-producer', '/role/add', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380573151232', 'updateRole', '更新角色', '0', '', 'opencloud-base-producer', '/role/update', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380753506304', 'removeRole', '删除角色', '0', '', 'opencloud-base-producer', '/role/remove', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380808032256', 'user', '系统用户列表', '0', '', 'opencloud-base-producer', '/user', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380854169600', 'addUser', '添加系统用户', '0', '', 'opencloud-base-producer', '/user/add', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380900306944', 'userGrantMenus', '当前用户可访问菜单资源', '0', '', 'opencloud-base-producer', '/user/grant/menus', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380938055680', 'updateUser', '更新系统用户', '0', '', 'opencloud-base-producer', '/user/update', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217380975804416', 'userGrantActions', '当前用户可访问操作资源', '0', '', 'opencloud-base-producer', '/user/grant/actions', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');
INSERT INTO `system_api` VALUES ('528217381013553152', 'userGrantApis', '当前用户可访问API资源', '0', '', 'opencloud-base-producer', '/user/grant/apis', '0', '1', '2018-12-28 14:27:17', '2018-12-28 16:26:23');

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
  `web_site` varchar(255) NOT NULL COMMENT '官网地址',
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
-- Table structure for system_grant_access
-- ----------------------------
DROP TABLE IF EXISTS `system_grant_access`;
CREATE TABLE `system_grant_access` (
  `id` bigint(20) NOT NULL,
  `path` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '请求地址',
  `authority` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '权限标识',
  `authority_prefix` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '权限前缀:用户(USER_) 、角色(ROLE_)、APP(APP_)',
  `authority_owner` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '授权所有者ID',
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
INSERT INTO `system_grant_access` VALUES ('2', 'access/index', 'ROLE_superAdmin', 'ROLE_', '1', '2', '1', 'menu', '{\"createTime\":1532870413000,\"icon\":\"\",\"menuCode\":\"system_access\",\"menuDesc\":\"菜单、操作、接口授权\",\"menuId\":2,\"menuName\":\"访问权限\",\"parentId\":1,\"path\":\"access/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545895711000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('3', 'resource/menus/index', 'ROLE_superAdmin', 'ROLE_', '1', '3', '1', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"system_menu\",\"menuDesc\":\"菜单资源管理\",\"menuId\":3,\"menuName\":\"菜单资源\",\"parentId\":1,\"path\":\"resource/menus/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545892252000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('4', '', 'ROLE_superAdmin', 'ROLE_', '1', '4', '0', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"system_server\",\"menuDesc\":\"服务运维\",\"menuId\":4,\"menuName\":\"服务维护\",\"parentId\":0,\"path\":\"\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545895371000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('5', 'www.baidu.com', 'ROLE_superAdmin', 'ROLE_', '1', '7', '4', 'menu', '{\"createTime\":1543515078000,\"menuCode\":\"system_trace\",\"menuDesc\":\"服务追踪\",\"menuId\":7,\"menuName\":\"服务追踪\",\"parentId\":4,\"path\":\"www.baidu.com\",\"prefix\":\"http://\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545669051000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('7', 'resource/api/index', 'ROLE_superAdmin', 'ROLE_', '1', '6', '1', 'menu', '{\"createTime\":1532870413000,\"menuCode\":\"system_api\",\"menuDesc\":\"开发API接口资源\",\"menuId\":6,\"menuName\":\"接口资源\",\"parentId\":1,\"path\":\"resource/api/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545895515000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('8', 'role/index', 'ROLE_superAdmin', 'ROLE_', '1', '8', '1', 'menu', '{\"createTime\":1545895614000,\"icon\":\"\",\"menuCode\":\"system_role\",\"menuDesc\":\"角色管理\",\"menuId\":8,\"menuName\":\"角色控制\",\"parentId\":1,\"path\":\"role/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545895614000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('9', 'app/index', 'ROLE_superAdmin', 'ROLE_', '1', '9', '1', 'menu', '{\"createTime\":1545896512000,\"icon\":\"\",\"menuCode\":\"system_app\",\"menuDesc\":\"应用信息、授权\",\"menuId\":9,\"menuName\":\"应用信息\",\"parentId\":1,\"path\":\"app/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545896535000}', '1', 'opencloud-base-producer');
INSERT INTO `system_grant_access` VALUES ('10', 'user/index', 'ROLE_superAdmin', 'ROLE_', '1', '10', '1', 'menu', '{\"createTime\":1545896789000,\"icon\":\"\",\"menuCode\":\"system_user\",\"menuDesc\":\"\",\"menuId\":10,\"menuName\":\"系统用户\",\"parentId\":1,\"path\":\"user/index\",\"prefix\":\"/\",\"priority\":0,\"status\":1,\"target\":\"_self\",\"updateTime\":1545896789000}', '1', 'opencloud-base-producer');

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
INSERT INTO `system_menu` VALUES ('2', '1', 'system_access', '访问权限', '菜单、操作、接口授权', '/', 'access/index', '', '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-27 15:28:31');
INSERT INTO `system_menu` VALUES ('3', '1', 'system_menu', '菜单资源', '菜单资源管理', '/', 'resource/menus/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-27 14:30:52');
INSERT INTO `system_menu` VALUES ('4', '0', 'system_server', '服务维护', '服务运维', '/', '', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-27 15:22:51');
INSERT INTO `system_menu` VALUES ('5', '4', 'system_route', '网关路由', '网关路由', '/', 'gateway/route/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-28 16:23:25');
INSERT INTO `system_menu` VALUES ('6', '1', 'system_api', '接口资源', '开发API接口资源', '/', 'resource/api/index', null, '_self', '0', '1', '2018-07-29 21:20:13', '2018-12-27 15:25:15');
INSERT INTO `system_menu` VALUES ('7', '4', 'system_trace', '服务追踪', '服务追踪', 'http://', 'www.baidu.com', null, '_self', '0', '1', '2018-11-30 02:11:18', '2018-12-25 00:30:51');
INSERT INTO `system_menu` VALUES ('8', '1', 'system_role', '角色控制', '角色管理', '/', 'role/index', '', '_self', '0', '1', '2018-12-27 15:26:54', '2018-12-27 15:26:54');
INSERT INTO `system_menu` VALUES ('9', '1', 'system_app', '应用信息', '应用信息、授权', '/', 'app/index', '', '_self', '0', '1', '2018-12-27 15:41:52', '2018-12-27 15:42:15');
INSERT INTO `system_menu` VALUES ('10', '1', 'system_user', '系统用户', '', '/', 'user/index', '', '_self', '0', '1', '2018-12-27 15:46:29', '2018-12-27 15:46:29');

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
INSERT INTO `system_role` VALUES ('1', 'superAdmin', '超级管理员', '1', '拥有所有权限,责任重大', '2018-07-29 21:14:50', '2018-12-28 16:41:05');
INSERT INTO `system_role` VALUES ('2', 'admin', '普通管理员', '1', '', '2018-07-29 21:14:54', '2018-12-28 16:42:56');
INSERT INTO `system_role` VALUES ('3', 'dev', '自研开发者', '1', '', '2018-07-29 21:14:54', '2018-12-28 16:42:27');
INSERT INTO `system_role` VALUES ('4', 'isp', '服务提供商', '1', '', '2018-07-29 21:14:54', '2018-12-28 16:42:14');

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
INSERT INTO `system_user_role` VALUES ('521677655146233856', '4');
INSERT INTO `system_user_role` VALUES ('521677655146233856', '1');
INSERT INTO `system_user_role` VALUES ('521677655146233856', '2');
SET FOREIGN_KEY_CHECKS=1;
