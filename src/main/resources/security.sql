/*
Navicat MySQL Data Transfer

Source Server         : 本地
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : security

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2019-12-25 14:41:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `a_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `a_account` varchar(20) DEFAULT NULL COMMENT '登录名',
  `a_password` varchar(32) DEFAULT NULL COMMENT '密码',
  PRIMARY KEY (`a_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='账户表';

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `account` VALUES ('2', 'wangyu', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `account` VALUES ('3', 'wangyu1', 'e10adc3949ba59abbe56e057f20f883e');

-- ----------------------------
-- Table structure for account_role
-- ----------------------------
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE `account_role` (
  `ar_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `a_id` int(11) DEFAULT NULL COMMENT '账户id',
  `r_id` int(11) DEFAULT NULL COMMENT '角色id',
  PRIMARY KEY (`ar_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='账户角色表';

-- ----------------------------
-- Records of account_role
-- ----------------------------
INSERT INTO `account_role` VALUES ('1', '1', '1');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `l_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `l_account` int(11) DEFAULT NULL COMMENT '账户id',
  `l_ip` varchar(13) DEFAULT NULL COMMENT '操作ip',
  `l_description` varchar(100) DEFAULT NULL COMMENT '操作描述',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`l_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', '1', '192.168.1.99', '【admin】用户登录', '2019-12-25 14:38:51');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `m_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `m_name` varchar(32) DEFAULT NULL COMMENT '菜单名称',
  `m_url` varchar(50) DEFAULT NULL COMMENT '权限字符串',
  `m_parentId` int(11) DEFAULT NULL COMMENT '父id',
  `m_needPermission` tinyint(2) DEFAULT NULL COMMENT '是否需要判断权限（true 需要, false 不需要）',
  `m_delete` tinyint(2) DEFAULT NULL COMMENT '是否正常(true 正常，false 已删除)',
  PRIMARY KEY (`m_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '首页', '/home', '0', '1', '1');
INSERT INTO `menu` VALUES ('2', '系统管理', '/system', '0', '1', '1');
INSERT INTO `menu` VALUES ('3', '用户列表', '/allUser', '1', '1', '1');
INSERT INTO `menu` VALUES ('4', '查看日志', '/selectLog', '2', '1', '1');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `r_name` varchar(32) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '系统管理员');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `rm_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `r_id` int(11) DEFAULT NULL COMMENT '角色id',
  `m_id` int(11) DEFAULT NULL COMMENT '菜单id',
  PRIMARY KEY (`rm_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色菜单表';

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('1', '1', '3');
INSERT INTO `role_menu` VALUES ('2', '1', '4');
