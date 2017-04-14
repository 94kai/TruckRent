/*
Navicat MySQL Data Transfer

Source Server         : xuekai
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : truck_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-07 13:07:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user_truck`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_truck`;
CREATE TABLE `tb_user_truck` (
  `userid` varchar(255) DEFAULT NULL,
  `turckid` int(11) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_truck
-- ----------------------------
INSERT INTO `tb_user_truck` VALUES ('13546330395', '1', '1');
