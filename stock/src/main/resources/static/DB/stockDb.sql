/*
 Navicat Premium Data Transfer

 Source Server         : root_pyh546482
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : localhost:3306
 Source Schema         : stock

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 07/07/2019 14:02:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for stock
-- ----------------------------
DROP TABLE IF EXISTS `stock`;
CREATE TABLE `stock`  (
  `save_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '存储编号',
  `stock_num` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票代码',
  `stock_search_num` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票搜索带字符代码',
  `stock_name` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '股票名称',
  `pull_status` int(11) NOT NULL DEFAULT 0 COMMENT '历史记录抓取行为记录',
  PRIMARY KEY (`save_id`) USING BTREE,
  INDEX `stock_num`(`stock_num`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4098 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for stock_detail
-- ----------------------------
DROP TABLE IF EXISTS `stock_detail`;
CREATE TABLE `stock_detail`  (
  `save_id` int(11) NOT NULL AUTO_INCREMENT,
  `up_time` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stock_num` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `open` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `high` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `low` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `close` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `volume` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`save_id`) USING BTREE,
  INDEX `stock_Num`(`stock_num`) USING BTREE,
  INDEX `stock_num_time`(`up_time`, `stock_num`) USING BTREE,
  CONSTRAINT `stock_Num` FOREIGN KEY (`stock_num`) REFERENCES `stock` (`stock_num`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB AUTO_INCREMENT = 1106756 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
