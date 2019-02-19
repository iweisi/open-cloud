/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : open-platform

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2018-12-29 23:09:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for oauth_access_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_access_token`;
CREATE TABLE `oauth_access_token` (
                                    `token_id` varchar(256) DEFAULT NULL,
                                    `token` blob,
                                    `authentication_id` varchar(128) NOT NULL,
                                    `user_name` varchar(256) DEFAULT NULL,
                                    `client_id` varchar(256) DEFAULT NULL,
                                    `authentication` blob,
                                    `refresh_token` varchar(256) DEFAULT NULL,
                                    PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_access_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_approvals
-- ----------------------------
DROP TABLE IF EXISTS `oauth_approvals`;
CREATE TABLE `oauth_approvals` (
                                 `userId` varchar(256) DEFAULT NULL,
                                 `clientId` varchar(256) DEFAULT NULL,
                                 `scope` varchar(256) DEFAULT NULL,
                                 `status` varchar(10) DEFAULT NULL,
                                 `expiresAt` datetime DEFAULT NULL,
                                 `lastModifiedAt` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_approvals
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
                                      `client_id` varchar(128) NOT NULL,
                                      `client_secret` varchar(256) DEFAULT NULL,
                                      `resource_ids` varchar(256) DEFAULT NULL,
                                      `scope` varchar(1024) DEFAULT NULL,
                                      `authorized_grant_types` varchar(256) DEFAULT NULL,
                                      `web_server_redirect_uri` varchar(256) DEFAULT NULL,
                                      `authorities` varchar(2048) DEFAULT NULL,
                                      `access_token_validity` int(11) DEFAULT NULL,
                                      `refresh_token_validity` int(11) DEFAULT NULL,
                                      `additional_information` varchar(4096) DEFAULT NULL,
                                      `autoapprove` varchar(256) DEFAULT NULL,
                                      PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_details
-- ----------------------------
INSERT INTO `oauth_client_details` VALUES ('gateway', '$2a$10$jIgCsWeTRrWXw7Cf6p26Yu5lP9WLnXbwTEPAmy4vPMuByx3tJGjsK', null, 'userProfile', 'refresh_token,authorization_code,password,client_credentials', 'http://localhost:8888/webjars/springfox-swagger-ui/o2c.html', 'APP_all', '43200', '604800', '{\"appDesc\":\"微服务开放平台\",\"appIcon\":\"\",\"appId\":\"gateway\",\"appName\":\"微服务开放平台\",\"appNameEn\":\"ApiGateway\",\"appOs\":\"\",\"appSecret\":\"123456\",\"appType\":\"server\",\"createTime\":1542016125000,\"redirectUrls\":\"http://localhost:8888/login,http://localhost:8888/webjars/springfox-swagger-ui/o2c.html\",\"status\":1,\"updateTime\":1545927416000,\"userId\":0,\"userType\":\"platform\",\"website\":\"http://www.baidu.com\"}', '');

-- ----------------------------
-- Table structure for oauth_client_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_client_token`;
CREATE TABLE `oauth_client_token` (
                                    `token_id` varchar(256) DEFAULT NULL,
                                    `token` blob,
                                    `authentication_id` varchar(128) NOT NULL,
                                    `user_name` varchar(256) DEFAULT NULL,
                                    `client_id` varchar(256) DEFAULT NULL,
                                    PRIMARY KEY (`authentication_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_client_token
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_code
-- ----------------------------
DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
                            `code` varchar(256) DEFAULT NULL,
                            `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_code
-- ----------------------------

-- ----------------------------
-- Table structure for oauth_refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `oauth_refresh_token`;
CREATE TABLE `oauth_refresh_token` (
                                     `token_id` varchar(256) DEFAULT NULL,
                                     `token` blob,
                                     `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of oauth_refresh_token
-- ----------------------------
SET FOREIGN_KEY_CHECKS=1;
