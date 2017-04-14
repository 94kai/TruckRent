/*
Navicat MySQL Data Transfer

Source Server         : xuekai
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : truck_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-07 13:06:54
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_truck`
-- ----------------------------
DROP TABLE IF EXISTS `tb_truck`;
CREATE TABLE `tb_truck` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `truckbirth` varchar(255) DEFAULT NULL,
  `length` int(11) DEFAULT NULL,
  `width` int(11) DEFAULT NULL,
  `height` int(11) DEFAULT NULL,
  `weight` int(11) DEFAULT NULL,
  `variety` varchar(255) DEFAULT NULL,
  `truckcardnumber` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_truck
-- ----------------------------
INSERT INTO `tb_truck` VALUES ('1', '2017年04月', '1', '1', '1', '1', '厢式', 'sssss');
