/*
Navicat MySQL Data Transfer

Source Server         : xuekai
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : truck_system

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-04-07 13:07:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `password` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) NOT NULL,
  `leancloudid` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `province` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `piecearea` varchar(255) DEFAULT NULL,
  `idcard` varchar(255) DEFAULT NULL,
  `avatarurl` varchar(255) DEFAULT NULL,
  `ischecked` int(11) DEFAULT NULL,
  `ismember` int(11) DEFAULT NULL,
  `introduce` varchar(255) DEFAULT NULL,
  `idcard_face_pic` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`phonenumber`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('111', '11111111111', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('121', '11111111112', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('212', '12121212121', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('212', '12121212125', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('212', '12121212126', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('212', '12121212127', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('11', '12121212134', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('111', '13131313130', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('111', '13131313131', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('111', '13131313133', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('xk3440395', '13546330395', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('111', '21111111111', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('1', '21212121211', null, null, null, null, null, null, null, null, null, null, null);
INSERT INTO `tb_user` VALUES ('y', '88880000887', null, null, null, null, null, null, null, null, null, null, null);
