/*
Navicat MySQL Data Transfer

Source Server         : xuekai
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : truck_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-07 13:06:59
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_truck_source`
-- ----------------------------
DROP TABLE IF EXISTS `tb_truck_source`;
CREATE TABLE `tb_truck_source` (
  `_id` int(11) NOT NULL AUTO_INCREMENT,
  `load_date` varchar(255) DEFAULT NULL,
  `start_place` varchar(255) DEFAULT NULL,
  `stop_place` varchar(255) DEFAULT NULL,
  `start_place_point` varchar(255) DEFAULT NULL,
  `introcduce` varchar(255) DEFAULT NULL,
  `publish_date` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `truck_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_truck_source
-- ----------------------------
INSERT INTO `tb_truck_source` VALUES ('1', '2017年04月07日', '北京市东城区', '北京市东城区', '0.0026437346731934897,0.001718417983647761', null, '1491533842535', '正常', '1');
INSERT INTO `tb_truck_source` VALUES ('2', '2017年04月07日', '北京市东城区', '北京市东城区', '39.947582316620526,116.54769488261748', null, '1491540972564', '正常', '1');
