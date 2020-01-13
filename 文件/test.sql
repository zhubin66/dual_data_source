/*
Navicat MySQL Data Transfer

Source Server         : 自己
Source Server Version : 50722
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-08-18 22:43:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for document
-- ----------------------------
DROP TABLE IF EXISTS `document`;
CREATE TABLE `document` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_date` datetime DEFAULT NULL,
  `creator` int(11) DEFAULT NULL,
  `creator_name` varchar(20) DEFAULT '',
  `download_times` int(11) DEFAULT '0',
  `file_size` decimal(10,0) DEFAULT '0',
  `file_type` varchar(20) DEFAULT '',
  `modifier` int(11) DEFAULT NULL,
  `modify_date` datetime DEFAULT NULL,
  `name` varchar(50) DEFAULT '0',
  `path` varchar(100) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of document
-- ----------------------------
INSERT INTO `document` VALUES ('2', null, null, '', '1', '780831', 'jpg', null, null, '20190326115012388_Koala', '2019-08/20190818215653413_20190326115012388_Koala.jpg');
INSERT INTO `document` VALUES ('3', null, null, '', '0', '775702', 'jpg', null, null, '20190326144413326_Jellyfish', '2019-08/20190818215704927_20190326144413326_Jellyfish.jpg');
INSERT INTO `document` VALUES ('4', null, null, '', '0', '620888', 'jpg', null, null, '20190327084136478_Tulips', '2019-08/20190818215712943_20190327084136478_Tulips.jpg');
INSERT INTO `document` VALUES ('5', null, null, '', '0', '19968', 'xls', null, null, '复习时间', '2019-08/20190818223108099_复习时间.xls');
INSERT INTO `document` VALUES ('6', null, null, null, '0', '6656', 'xls', null, null, 'test', '2019-08/20190818223707289_test.xls');
