/*
 Navicat Premium Data Transfer

 Source Server         : localmysqldocker
 Source Server Type    : MySQL
 Source Server Version : 80025
 Source Host           : localhost:3306
 Source Schema         : custom

 Target Server Type    : MySQL
 Target Server Version : 80025
 File Encoding         : 65001

 Date: 02/09/2021 14:42:38
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for req_record
-- ----------------------------
DROP TABLE IF EXISTS `req_record`;
CREATE TABLE `req_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '请求记录表',
  `issuer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '签发者',
  `holder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '持有者',
  `union_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '唯一id',
  `expire` bigint DEFAULT NULL COMMENT '过期时间',
  `pieces` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '凭证数',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '凭证类型',
  `times` int DEFAULT NULL COMMENT '转让次数',
  `target_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用于指定发送mq队列的',
  `tdr_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标记tdr的类型',
  `biz_data` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数据载体',
  `serial_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '流水编号',
  `req_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]',
  `req_time` datetime DEFAULT NULL COMMENT '请求时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='请求记录';

-- ----------------------------
-- Table structure for res_record
-- ----------------------------
DROP TABLE IF EXISTS `res_record`;
CREATE TABLE `res_record` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '请求记录表',
  `issuer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '签发者',
  `holder` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '持有者',
  `expire` datetime DEFAULT NULL COMMENT '过期时间',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '凭证类型',
  `times` int DEFAULT NULL COMMENT '转让次数',
  `biz_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '数据载体',
  `serial_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '流水编号',
  `res_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]',
  `res_time` datetime DEFAULT NULL COMMENT '请求时间',
  `result` varchar(25) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '结果 success/fail\n\n',
  `claim` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '完整凭证',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='响应记录';

-- ----------------------------
-- Table structure for serial_number
-- ----------------------------
DROP TABLE IF EXISTS `serial_number`;
CREATE TABLE `serial_number` (
  `id` int NOT NULL AUTO_INCREMENT,
  `serial_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '流水编号',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `type` int DEFAULT NULL COMMENT '流水号类型[1:申请绑定数字身份 2:申请获取数据的授权 3:执行获取数据]',
  `number` int DEFAULT NULL COMMENT '通过这个流水号查询到一次数据就加1（+1）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='流水编号表';

SET FOREIGN_KEY_CHECKS = 1;
