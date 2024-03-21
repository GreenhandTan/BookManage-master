/*
Navicat MySQL Data Transfer

Source Server         : msyql
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : bookm

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2022-12-08 18:00:08
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `book`
-- ----------------------------
DROP TABLE IF EXISTS `book`;
CREATE TABLE `book` (
  `bid` int(255) NOT NULL AUTO_INCREMENT,
  `bookname` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '书名',
  `author` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '作者',
  `detail` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '详情',
  `status` int(255) NOT NULL COMMENT '书的状态分为两种，第一种是存在1，第二种是被借0',
  `price` int(255) NOT NULL COMMENT '价格',
  `category` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '类别',
  PRIMARY KEY (`bid`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of book
-- ----------------------------
INSERT INTO `book` VALUES ('134', '高等数学', '汤家凤', '高等教育', '1', '50', '高等教育');
INSERT INTO `book` VALUES ('135', '红楼梦', '曹雪芹', '经典名著', '1', '45', '经典名著');
INSERT INTO `book` VALUES ('136', '西游记', '吴承恩', '经典名著', '1', '45', '经典名著');
INSERT INTO `book` VALUES ('137', '三国演义', '罗贯中', '经典名著', '1', '45', '经典名著');
INSERT INTO `book` VALUES ('138', '悲惨世界', '维克多·雨果', '外国名著', '1', '45', '外国名著');
INSERT INTO `book` VALUES ('139', '巴黎圣母院', '维克多·雨果', '外国名著', '1', '45', '外国名著');
INSERT INTO `book` VALUES ('140', '童年', '高尔基', '外国名著', '1', '45', '外国名著');

-- ----------------------------
-- Table structure for `bookorder`
-- ----------------------------
DROP TABLE IF EXISTS `bookorder`;
CREATE TABLE `bookorder` (
  `orderid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) NOT NULL,
  `bid` int(11) NOT NULL,
  `orderstatus` int(255) NOT NULL,
  PRIMARY KEY (`orderid`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of bookorder
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `grade` int(255) NOT NULL COMMENT '0是超级管理员：能够进入系统管理界面，1是普通用户，进入普通用户界面，能够进行借书还书操作',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('59', 'admin', 'b9d11b3be25f5a1a7dc8ca04cd310b28', '0');
INSERT INTO `user` VALUES ('60', 'zhangsan', 'c1cb843f3929978af615fe7dfbf532cb', '1');
INSERT INTO `user` VALUES ('61', 'lishi', '5ef9f07cac696a44fedfd6a86e9d0ef2', '1');
