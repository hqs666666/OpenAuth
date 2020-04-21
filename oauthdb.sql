/*
 Navicat Premium Data Transfer

 Source Server         : Mille
 Source Server Type    : MySQL
 Source Server Version : 100318
 Source Host           : 47.100.229.56:3306
 Source Schema         : oauthdb

 Target Server Type    : MySQL
 Target Server Version : 100318
 File Encoding         : 65001

 Date: 17/01/2020 17:35:18
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for access_token
-- ----------------------------
DROP TABLE IF EXISTS `access_token`;
CREATE TABLE `access_token`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `access_token` varchar(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Access Token',
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '关联的用户ID',
  `user_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `client_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接入的客户端ID',
  `expires_in` bigint(11) NOT NULL COMMENT '过期时间戳',
  `grant_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '授权类型，比如：authorization_code',
  `scope` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '可被访问的用户的权限范围，比如：basic、userinfo',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Access Token信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for auth_code
-- ----------------------------
DROP TABLE IF EXISTS `auth_code`;
CREATE TABLE `auth_code`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `code` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `client_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `expires_in` datetime(0) NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `status` int(2) NULL DEFAULT 0 COMMENT '0表示未使用；1表示已使用；2表示已被禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '授权码存储' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of auth_code
-- ----------------------------
INSERT INTO `auth_code` VALUES (7, 'fb6113c642934d9bb7fefb838e40f43c', '-1', 'clientId', '2020-01-17 08:00:29', '2020-01-17 08:00:29', 0);
INSERT INTO `auth_code` VALUES (8, '382e29f15c544da3a3c536deb5262854', '-1', 'clientId', '2020-01-17 08:52:50', '2020-01-17 08:52:50', 0);
INSERT INTO `auth_code` VALUES (9, '578dfec3fb5d443dba5db020586364c8', '-1', 'clientId', '2020-01-17 09:08:17', '2020-01-17 09:08:17', 0);

-- ----------------------------
-- Table structure for client
-- ----------------------------
DROP TABLE IF EXISTS `client`;
CREATE TABLE `client`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `client_id` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接入的客户端ID',
  `client_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `client_secret` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '接入的客户端的密钥',
  `redirect_uri` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '回调地址',
  `description` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述信息',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  `status` int(2) NULL DEFAULT 0 COMMENT '0表示未开通；1表示正常使用；2表示已被禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接入的客户端信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client
-- ----------------------------
INSERT INTO `client` VALUES ('0750f301382511ea8fdb00163e0a23b0', 'clientId', '测试客户端', 'abcdefg', 'https://blog.hqs.pub', '测试', '7c7bdae2381411ea8fdb00163e0a23b0', '2020-01-16 13:58:10', '', NULL, 1);

-- ----------------------------
-- Table structure for client_user
-- ----------------------------
DROP TABLE IF EXISTS `client_user`;
CREATE TABLE `client_user`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `client_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `scope` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户对某个接入客户端的授权信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client_user
-- ----------------------------
INSERT INTO `client_user` VALUES ('486580ae7832113c72b427ca7367e534', 'clientId', '-1', 'userinfo');
INSERT INTO `client_user` VALUES ('6b64e86ba4cdd77d522d20a88392963b', 'clientId', '-1', 'userinfo');
INSERT INTO `client_user` VALUES ('a6845fba2e02a767d34c8b756f1acdf7', 'clientId', '-1', 'userinfo');
INSERT INTO `client_user` VALUES ('c18ece7b5bd48181d8341038ef0a731e', 'clientId', '-1', 'userinfo');

-- ----------------------------
-- Table structure for refresh_token
-- ----------------------------
DROP TABLE IF EXISTS `refresh_token`;
CREATE TABLE `refresh_token`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'id',
  `token_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '表access_token对应的Access Token记录',
  `refresh_token` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'Refresh Token',
  `expires_in` bigint(11) NOT NULL COMMENT '过期时间戳',
  `create_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建用户',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '最后更新用户',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'Refresh Token信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for scope
-- ----------------------------
DROP TABLE IF EXISTS `scope`;
CREATE TABLE `scope`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '可被访问的用户的权限范围，比如：basic、userinfo',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '可被访问的用户权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of scope
-- ----------------------------
INSERT INTO `scope` VALUES ('45fda3c6382511ea8fdb00163e0a23b0', 'basic', '获取id');
INSERT INTO `scope` VALUES ('55afc4f0382511ea8fdb00163e0a23b0', 'userinfo', '获取详细资料');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `mobile` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `status` int(2) NOT NULL DEFAULT 1 COMMENT '0表示未开通；1表示正常使用；2表示已被禁用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('7c7bdae2381411ea8fdb00163e0a23b0', 'hqs', '123456', '17602132272', 'admin@hqs.pub', '2020-01-16 00:00:00', '2020-01-16 00:00:00', 1);

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(36) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `userId`(`user_id`) USING BTREE,
  CONSTRAINT `userId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;


DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gender` int(11) NOT NULL,
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime(0) NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `wx_user`;
CREATE TABLE `wx_user`  (
  `id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `create_time` datetime(0) NULL,
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nick_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `gender` int(11) NOT NULL,
  `province` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `city` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `country` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `union_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

