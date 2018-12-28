/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : open-platform

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-28 02:17:17
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

-- ----------------------------
-- Table structure for system_action
-- ----------------------------
DROP TABLE IF EXISTS `system_action`;
CREATE TABLE `system_action` (
                               `action_id` bigint(20) NOT NULL COMMENT '资源ID',
                               `action_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源编码',
                               `action_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
                               `path` varchar(200) COLLATE utf8_bin NOT NULL COMMENT '资源路径',
                               `menu_id` bigint(20) DEFAULT NULL COMMENT '资源父节点',
                               `priority` int(10) NOT NULL DEFAULT '0' COMMENT '优先级 越小越靠前',
                               `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                               `action_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
                               `create_time` datetime NOT NULL,
                               `update_time` datetime DEFAULT NULL,
                               PRIMARY KEY (`action_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='操作表';

-- ----------------------------
-- Records of system_action
-- ----------------------------
INSERT INTO `system_action` VALUES ('1', 'btnDetail', '详情', '/funcPages/adManager', '2', '0', '1', '详情222', '2018-07-29 21:20:10', '2018-12-27 13:17:36');
INSERT INTO `system_action` VALUES ('2', 'btnSave', '保存', '/index', '2', '0', '1', '保存', '2018-07-29 21:20:13', '2018-09-25 23:02:11');

-- ----------------------------
-- Table structure for system_api
-- ----------------------------
DROP TABLE IF EXISTS `system_api`;
CREATE TABLE `system_api` (
                            `api_id` bigint(20) NOT NULL COMMENT '资源ID',
                            `api_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源编码',
                            `api_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '资源名称',
                            `service_id` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '服务ID',
                            `path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '资源路径',
                            `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级',
                            `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                            `api_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '资源描述',
                            `create_time` datetime NOT NULL,
                            `update_time` datetime DEFAULT NULL,
                            PRIMARY KEY (`api_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='API资源表';

-- ----------------------------
-- Records of system_api
-- ----------------------------
INSERT INTO `system_api` VALUES ('526924635598487552', 'login', 'login', 'opencloud-base-producer', '/account/login', '0', '1', '', '2018-12-25 00:50:22', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924635824979968', 'addLoginLog', 'addLoginLog', 'opencloud-base-producer', '/account/logs/add', '0', '1', '', '2018-12-25 00:50:22', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924635992752128', 'action', '动作列表', 'opencloud-base-producer', '/action', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924636160524288', 'updateStatus', '更新状态', 'opencloud-base-producer', '/role/update/status', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924636416376832', 'getAction', '获取动作资源', 'opencloud-base-producer', '/action/{actionId}', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924636655452160', 'addAction', '添加动作资源', 'opencloud-base-producer', '/action/add', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924636831612928', 'removeAction', '移除动作', 'opencloud-base-producer', '/action/remove', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924636974219264', 'updateAction', '编辑动作资源', 'opencloud-base-producer', '/action/update', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924637133602816', 'api', 'Api列表', 'opencloud-base-producer', '/api', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924637515284480', 'getApi', '获取Api资源', 'opencloud-base-producer', '/api/{apiId}', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924637733388288', 'addApi', '添加Api资源', 'opencloud-base-producer', '/api/add', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924637901160448', 'removeApi', '移除Api', 'opencloud-base-producer', '/api/remove', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('526924638047961088', 'apiAll', 'Api列表', 'opencloud-base-producer', '/api/all', '0', '1', null, '2018-12-25 00:50:23', '2018-12-25 15:43:37');
INSERT INTO `system_api` VALUES ('526924638517723136', 'updateApi', '编辑Api资源', 'opencloud-base-producer', '/api/update', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924638710661120', 'app', '应用列表', 'opencloud-base-producer', '/app', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924638853267456', 'getApp', '获取应用信息', 'opencloud-base-producer', '/app/{appId}', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924639021039616', 'addApp', '添加应用', 'opencloud-base-producer', '/app/add', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924639163645952', 'updateApp', '编辑应用', 'opencloud-base-producer', '/app/update', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924639335612416', 'removeApp', '删除应用', 'opencloud-base-producer', '/app/remove', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924639486607360', 'resetSecret', '重置秘钥', 'opencloud-base-producer', '/app/reset', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924639792791552', 'menuAll', '菜单列表', 'opencloud-base-producer', '/menu/all', '0', '1', null, '2018-12-25 00:50:23', '2018-12-25 15:43:37');
INSERT INTO `system_api` VALUES ('526924639964758016', 'addMenu', '添加菜单资源', 'opencloud-base-producer', '/menu/add', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924640107364352', 'updateMenu', '编辑菜单资源', 'opencloud-base-producer', '/menu/update', '0', '1', '', '2018-12-25 00:50:23', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924640279330816', 'menu', '菜单列表', 'opencloud-base-producer', '/menu', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924640426131456', 'removeMenu', '移除菜单', 'opencloud-base-producer', '/menu/remove', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924640593903616', 'getMenu', '获取菜单资源', 'opencloud-base-producer', '/menu/{menuId}', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924641055277056', 'role', '角色列表', 'opencloud-base-producer', '/role', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924641223049216', 'getRole', '获取角色信息', 'opencloud-base-producer', '/role/{roleId}', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924641369849856', 'removeRole', '删除角色', 'opencloud-base-producer', '/role/remove', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924641537622016', 'updateRole', '更新角色', 'opencloud-base-producer', '/role/update', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:02');
INSERT INTO `system_api` VALUES ('526924641684422656', 'addRole', '添加角色', 'opencloud-base-producer', '/role/add', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('526924641848000512', 'user', '系统用户列表', 'opencloud-base-producer', '/user', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:02');
INSERT INTO `system_api` VALUES ('526924641998995456', 'userMenus', '当前用户可访问菜单资源', 'opencloud-base-producer', '/user/menus', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:02');
INSERT INTO `system_api` VALUES ('526924642166767616', 'updateUser', '更新系统用户', 'opencloud-base-producer', '/user/update', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:02');
INSERT INTO `system_api` VALUES ('526924642313568256', 'userActions', '当前用户可访问操作资源', 'opencloud-base-producer', '/user/actions', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:02');
INSERT INTO `system_api` VALUES ('526924642477146112', 'userApis', '当前用户可访问API资源', 'opencloud-base-producer', '/user/apis', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:02');
INSERT INTO `system_api` VALUES ('526924642623946752', 'addUser', '添加系统用户', 'opencloud-base-producer', '/user/add', '0', '1', '', '2018-12-25 00:50:24', '2018-12-28 00:11:02');
INSERT INTO `system_api` VALUES ('526925129653944320', 'client', 'client', 'opencloud-auth-producer', '/client', '0', '1', '', '2018-12-25 00:52:20', '2018-12-28 01:58:22');
INSERT INTO `system_api` VALUES ('526925129855270912', 'addClient', 'addClient', 'opencloud-auth-producer', '/client/add', '0', '1', '', '2018-12-25 00:52:20', '2018-12-28 01:58:22');
INSERT INTO `system_api` VALUES ('526925130048208896', 'updateClient', 'updateClient', 'opencloud-auth-producer', '/client/update', '0', '1', '', '2018-12-25 00:52:20', '2018-12-28 01:58:23');
INSERT INTO `system_api` VALUES ('526925130207592448', 'resetSecret', 'resetSecret', 'opencloud-auth-producer', '/client/reset', '0', '1', '', '2018-12-25 00:52:20', '2018-12-28 01:58:23');
INSERT INTO `system_api` VALUES ('526925130517970944', 'removeClinet', 'removeClinet', 'opencloud-auth-producer', '/client/remove', '0', '1', '', '2018-12-25 00:52:20', '2018-12-28 01:58:23');
INSERT INTO `system_api` VALUES ('526925130685743104', 'getClient', 'getClient', 'opencloud-auth-producer', '/client/{clientId}', '0', '1', '', '2018-12-25 00:52:20', '2018-12-28 01:58:22');
INSERT INTO `system_api` VALUES ('527145934195261440', 'user', '获取当前登录系统用户', 'opencloud-auth-producer', '/user', '0', '1', '', '2018-12-25 15:29:44', '2018-12-28 01:58:23');
INSERT INTO `system_api` VALUES ('527146617162170368', 'access', '获取授权列表', 'opencloud-base-producer', '/access', '0', '1', '', '2018-12-25 15:32:27', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('527472326279692288', 'actionList', '操作列表', 'opencloud-base-producer', '/action/list', '0', '1', '', '2018-12-26 13:06:42', '2018-12-28 00:11:00');
INSERT INTO `system_api` VALUES ('527472327386988544', 'apiList', 'Api列表', 'opencloud-base-producer', '/api/list', '0', '1', '', '2018-12-26 13:06:42', '2018-12-28 00:11:01');
INSERT INTO `system_api` VALUES ('527472328460730368', 'menuList', '菜单列表', 'opencloud-base-producer', '/menu/list', '0', '1', '', '2018-12-26 13:06:43', '2018-12-28 00:11:01');

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
                            `web_site` varchar(255) DEFAULT NULL COMMENT '官网地址',
                            `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户ID:默认为0',
                            `user_type` varchar(20) NOT NULL DEFAULT 'platform' COMMENT '用户类型:platform-平台 isp-服务提供商 dev-自研开发者',
                            `create_time` datetime NOT NULL COMMENT '创建时间',
                            `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                            `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_app
-- ----------------------------
INSERT INTO `system_app` VALUES ('gateway', '123456', '开放云平台', 'ApiGateway', '', 'server', '开放云平台', '', null, '0', 'platform', '2018-11-12 17:48:45', '2018-12-28 00:16:56', '1');

-- ----------------------------
-- Table structure for system_grant_access
-- ----------------------------
DROP TABLE IF EXISTS `system_grant_access`;
CREATE TABLE `system_grant_access` (
                                     `id` bigint(20) NOT NULL,
                                     `code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '授权编码: 资源类型+资源名称  API_INFO',
                                     `name` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '名称',
                                     `prefix` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '访问前缀:/,http://,https://',
                                     `path` varchar(255) COLLATE utf8_bin DEFAULT '' COMMENT '请求地址',
                                     `icon` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '图标',
                                     `target` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '打开方式:_self,_blank',
                                     `resource_id` bigint(20) NOT NULL COMMENT '资源ID',
                                     `resource_pid` bigint(20) DEFAULT NULL COMMENT '资源父节点',
                                     `resource_type` varchar(255) COLLATE utf8_bin NOT NULL COMMENT '资源类型:api,menu,button',
                                     `grant_owner_id` bigint(20) NOT NULL COMMENT '授权所有者ID',
                                     `grant_owner_code` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '授权所有者编码',
                                     `grant_owner_type` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '授权所有者类型:用户(USER_) 、角色(ROLE_)',
                                     `service_id` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '所属服务',
                                     `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资源授权访问表';

-- ----------------------------
-- Records of system_grant_access
-- ----------------------------
INSERT INTO `system_grant_access` VALUES ('1', 'menu_System', '系统安全', '/', '', null, '_self', '1', '0', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('2', 'menu_SystemAccess', '访问权限', '/', 'access/index', '', '_self', '2', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('3', 'menu_SystemMenu', '菜单资源', '/', 'resource/menus/index', null, '_self', '3', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('4', 'menu_SystemServer', '服务维护', '/', '', null, '_self', '4', '0', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('5', 'menu_SystemTrace', '服务追踪', 'http://', 'www.baidu.com', null, '_self', '7', '4', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('7', 'menu_SystemApi', '接口资源', '/', 'resource/api/index', null, '_self', '6', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('8', 'menu_SystemRole', '角色控制', '/', 'role/index', null, '_self', '8', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('9', 'menu_SystemApp', '应用信息', '/', 'app/index', null, '_self', '9', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');
INSERT INTO `system_grant_access` VALUES ('10', 'menu_SystemUser', '系统用户', '/', 'user/index', null, '_self', '10', '1', 'menu', '1', 'ROLE_superAdmin', 'ROLE_', 'opencloud-base-producer', '1');

-- ----------------------------
-- Table structure for system_menu
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
                             `menu_id` bigint(20) NOT NULL COMMENT '菜单Id',
                             `parent_id` bigint(20) DEFAULT NULL COMMENT '父级菜单',
                             `menu_code` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单编码',
                             `menu_name` varchar(100) COLLATE utf8_bin NOT NULL COMMENT '菜单名称',
                             `prefix` varchar(20) COLLATE utf8_bin NOT NULL COMMENT '路径前缀',
                             `path` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '请求路径',
                             `icon` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单标题',
                             `target` varchar(20) COLLATE utf8_bin NOT NULL DEFAULT '_self' COMMENT '打开方式:_self窗口内,_blank新窗口',
                             `priority` bigint(20) NOT NULL DEFAULT '0' COMMENT '优先级 越小越靠前',
                             `status` tinyint(3) NOT NULL DEFAULT '1' COMMENT '状态:0-无效 1-有效',
                             `menu_desc` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '描述',
                             `create_time` datetime NOT NULL COMMENT '创建时间',
                             `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                             PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='菜单表';

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('1', '0', 'System', '系统安全', '/', '', null, '_self', '0', '1', '系统安全', '2018-07-29 21:20:10', '2018-12-25 00:30:22');
INSERT INTO `system_menu` VALUES ('2', '1', 'SystemAccess', '访问权限', '/', 'access/index', '', '_self', '0', '1', '菜单、操作、接口授权', '2018-07-29 21:20:13', '2018-12-27 15:28:31');
INSERT INTO `system_menu` VALUES ('3', '1', 'SystemMenu', '菜单资源', '/', 'resource/menus/index', null, '_self', '0', '1', '菜单资源管理', '2018-07-29 21:20:13', '2018-12-27 14:30:52');
INSERT INTO `system_menu` VALUES ('4', '0', 'SystemServer', '服务维护', '/', '', null, '_self', '0', '1', '服务运维', '2018-07-29 21:20:13', '2018-12-27 15:22:51');
INSERT INTO `system_menu` VALUES ('5', '4', 'SystemRoute', '网关路由', '/', 'gateway/route/index', null, '_self', '0', '1', '网关路由', '2018-07-29 21:20:13', '2018-12-27 13:09:40');
INSERT INTO `system_menu` VALUES ('6', '1', 'SystemApi', '接口资源', '/', 'resource/api/index', null, '_self', '0', '1', '开发API接口资源', '2018-07-29 21:20:13', '2018-12-27 15:25:15');
INSERT INTO `system_menu` VALUES ('7', '4', 'SystemTrace', '服务追踪', 'http://', 'www.baidu.com', null, '_self', '0', '1', '服务追踪', '2018-11-30 02:11:18', '2018-12-25 00:30:51');
INSERT INTO `system_menu` VALUES ('8', '1', 'SystemRole', '角色控制', '/', 'role/index', '', '_self', '0', '1', '角色管理', '2018-12-27 15:26:54', '2018-12-27 15:26:54');
INSERT INTO `system_menu` VALUES ('9', '1', 'SystemApp', '应用信息', '/', 'app/index', '', '_self', '0', '1', '应用信息、授权', '2018-12-27 15:41:52', '2018-12-27 15:42:15');
INSERT INTO `system_menu` VALUES ('10', '1', 'SystemUser', '系统用户', '/', 'user/index', '', '_self', '0', '1', '', '2018-12-27 15:46:29', '2018-12-27 15:46:29');

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
INSERT INTO `system_role` VALUES ('1', 'superAdmin', '超级管理员', '1', '', '2018-07-29 21:14:50', '2018-07-29 21:14:53');
INSERT INTO `system_role` VALUES ('2', 'platfromAdmin', '平台管理员', '1', '', '2018-07-29 21:14:54', '2018-07-29 21:15:00');
INSERT INTO `system_role` VALUES ('3', 'developer', '普通开发者', '1', '', '2018-07-29 21:14:54', '2018-07-29 21:15:00');
INSERT INTO `system_role` VALUES ('4', 'companyDeveloper', '企业开发者', '1', '', '2018-07-29 21:14:54', '2018-07-29 21:15:00');

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
INSERT INTO `system_user` VALUES ('521677655146233856', 'admin', 'admin', null, '515608851@qq.com', '18518226890', '0', null, null, '2018-12-10 13:20:45', '1', null, '2018-12-10 13:20:45', '2018-12-10 13:20:45');

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
