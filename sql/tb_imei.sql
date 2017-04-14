/*
Navicat MySQL Data Transfer

Source Server         : xuekai
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : truck_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-07 13:06:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_imei`
-- ----------------------------
DROP TABLE IF EXISTS `tb_imei`;
CREATE TABLE `tb_imei` (
  `phonenumber` varchar(255) NOT NULL,
  `imei` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`phonenumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_imei
-- ----------------------------
INSERT INTO `tb_imei` VALUES ('11111111111', '');
INSERT INTO `tb_imei` VALUES ('12121212134', '');
INSERT INTO `tb_imei` VALUES ('13546330395', '');
INSERT INTO `tb_imei` VALUES ('21212121211', '867992029296902');
INSERT INTO `tb_imei` VALUES ('88880000887', '867992029296902');
