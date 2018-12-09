/*
Navicat MySQL Data Transfer

Source Server         : Ami
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : atm

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-12-07 17:11:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `card_no` varchar(22) COLLATE utf8_unicode_ci DEFAULT NULL,
  `pwd` varchar(22) COLLATE utf8_unicode_ci DEFAULT NULL,
  `balance` double(22,0) DEFAULT NULL,
  `identity` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('123', '123', '455', 'user');
INSERT INTO `account` VALUES ('abc123', '1234', '9900', 'user');
INSERT INTO `account` VALUES ('123456', null, null, 'admin');

-- ----------------------------
-- Table structure for transaction
-- ----------------------------
DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `card_no` varchar(22) COLLATE utf8_unicode_ci DEFAULT NULL,
  `operation` varchar(22) COLLATE utf8_unicode_ci DEFAULT NULL,
  `account` double DEFAULT NULL,
  `date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of transaction
-- ----------------------------
