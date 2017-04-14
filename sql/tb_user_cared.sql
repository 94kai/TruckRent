/*
Navicat MySQL Data Transfer

Source Server         : xuekai
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : truck_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-07 13:07:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user_cared`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_cared`;
CREATE TABLE `tb_user_cared` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) DEFAULT NULL,
  `caredphone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user_cared
-- ----------------------------
