/*
Navicat MySQL Data Transfer

Source Server         : xuekai
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : truck_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-07 13:07:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user_trucksource`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_trucksource`;
CREATE TABLE `tb_user_trucksource` (
  `userid` varchar(255) DEFAULT NULL,
  `trucksourceid` varchar(255) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_trucksource
-- ----------------------------
INSERT INTO `tb_user_trucksource` VALUES ('13546330395', '1', '1');
INSERT INTO `tb_user_trucksource` VALUES ('13546330395', '2', '2');
