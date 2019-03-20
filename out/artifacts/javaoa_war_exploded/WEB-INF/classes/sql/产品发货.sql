/*
Navicat MySQL Data Transfer

Source Server         : aaa
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : oa_test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-01 13:35:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_approval_list
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_list`;
CREATE TABLE `t_approval_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL,
  `role_list` varchar(255) NOT NULL,
  `role_name_list` varchar(255) DEFAULT NULL,
  `next_id` int(11) DEFAULT NULL,
  `state` int(4) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_approval_list
-- ----------------------------
INSERT INTO `t_approval_list` VALUES ('1', 'PRODUCT_OUTSTOCK', '31', '生产运营经理', null, '1', '产品出库发货审批流程');

-- ----------------------------
-- Table structure for t_approval_record
-- ----------------------------
DROP TABLE IF EXISTS `t_approval_record`;
CREATE TABLE `t_approval_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(50) NOT NULL,
  `emp_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `comment` varchar(255) NOT NULL,
  `apply_id` int(11) NOT NULL,
  `state` int(4) NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=208 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_approval_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_company
-- ----------------------------
DROP TABLE IF EXISTS `t_company`;
CREATE TABLE `t_company` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company` varchar(255) NOT NULL,
  `addr` varchar(255) NOT NULL,
  `receiver` varchar(50) NOT NULL,
  `tel` varchar(50) DEFAULT NULL,
  `state` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_outstock_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_outstock_detail`;
CREATE TABLE `t_outstock_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` int(11) NOT NULL,
  `product_name` varchar(150) NOT NULL,
  `product_model` varchar(150) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `count` int(11) NOT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_outstock_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_outstock_record
-- ----------------------------
DROP TABLE IF EXISTS `t_outstock_record`;
CREATE TABLE `t_outstock_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `record_id` int(11) NOT NULL,
  `count` int(11) NOT NULL,
  `expected_outstock_time` datetime NOT NULL,
  `outstock_time` datetime DEFAULT NULL,
  `shipment_company` varchar(150) DEFAULT NULL,
  `shipment_order_num` varchar(100) DEFAULT NULL,
  `shipment_order_pic` varchar(255) DEFAULT NULL,
  `outstock_order_num` varchar(50) DEFAULT NULL,
  `outstock_order_pic` varchar(255) DEFAULT NULL,
  `type` varchar(50) NOT NULL,
  `state` int(11) DEFAULT NULL,
  `operator` varchar(50) DEFAULT NULL,
  `operator_id` int(11) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `outstock_type` tinyint(1) NOT NULL COMMENT '发货形式，0，一次性发货；1：分批发货',
  `order_num` int(11) DEFAULT NULL COMMENT '发货批次',
  `comment` varchar(255) DEFAULT NULL COMMENT '批注',
  `shipment_receive_num` varchar(100) DEFAULT NULL COMMENT '物流回执单号',
  `shipment_receive_pic` varchar(255) DEFAULT NULL COMMENT '收到回执时间',
  `receive_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_outstock_record
-- ----------------------------

-- ----------------------------
-- Table structure for t_product
-- ----------------------------
DROP TABLE IF EXISTS `t_product`;
CREATE TABLE `t_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(255) NOT NULL,
  `product_model` varchar(100) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `remark` varchar(255) DEFAULT NULL,
  `type` varchar(50) NOT NULL,
  `state` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product
-- ----------------------------
INSERT INTO `t_product` VALUES ('1', '集成芯片', 'MS9282', 'PCS', '', 'IC', '1');
INSERT INTO `t_product` VALUES ('2', '集成芯片', 'MS9288', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('3', '集成芯片', 'MS9288A', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('4', '集成芯片', 'MS1820', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('5', '集成芯片', 'MS1850', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('6', '集成芯片', 'MS1858S', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('7', '集成芯片', 'MS1830', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('8', '集成芯片', 'MS1835', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('9', '集成芯片', 'MS2100 LQFP', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('10', '集成芯片', 'MS2100 BGA', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('11', '集成芯片', 'MS2100E', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('12', '集成芯片', 'MS2106', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('13', '集成芯片', 'MS2106S', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('14', '集成芯片', 'MS7024', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('15', '集成芯片', 'MS8005F', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('16', '集成芯片', 'MS7241', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('17', '集成芯片', 'ES7240S', 'PCS', null, 'IC', '1');
INSERT INTO `t_product` VALUES ('18', '集成芯片', 'ES7144LV', 'PCS', null, 'IC', '1');

-- ----------------------------
-- Table structure for t_product_outstock_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_product_outstock_detail`;
CREATE TABLE `t_product_outstock_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_model` varchar(100) NOT NULL,
  `count` int(11) NOT NULL,
  `unit` varchar(50) NOT NULL,
  `remark` varchar(255) NOT NULL,
  `state` int(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product_outstock_detail
-- ----------------------------

-- ----------------------------
-- Table structure for t_product_outstock_record
-- ----------------------------
DROP TABLE IF EXISTS `t_product_outstock_record`;
CREATE TABLE `t_product_outstock_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `odd_number` varchar(50) DEFAULT NULL,
  `proposer` varchar(50) DEFAULT NULL,
  `proposer_id` int(11) NOT NULL,
  `dept_id` int(11) NOT NULL,
  `create_time` datetime NOT NULL,
  `reason` varchar(255) NOT NULL,
  `cancel_time` datetime DEFAULT NULL,
  `receiver_company` varchar(255) NOT NULL,
  `addr` varchar(255) NOT NULL,
  `receiver` varchar(50) NOT NULL,
  `tel` varchar(255) DEFAULT NULL,
  `contract` varchar(255) DEFAULT NULL,
  `state` int(4) NOT NULL,
  `approval_id` int(11) NOT NULL,
  `approval_type` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_product_outstock_record
-- ----------------------------

-- 新增菜单
--INSERT INTO `t_menu` VALUES ('99', '113', '产品发货申请', 'web/oa/product/apply/add', '102', null);
--INSERT INTO `t_menu` VALUES ('100', '212', '产品发货审批', 'web/oa/product/outstock/approval/list', '102', null);
--INSERT INTO `t_menu` VALUES ('101', '213', '产品出库', 'web/oa/product/outstock/list', '102', null);
--INSERT INTO `t_menu` VALUES ('102', '55', '产品管理', 'web/oa/product/apply/add', null, 'resources/images/product.png');
--INSERT INTO `t_menu` VALUES ('103', '211', '客户信息管理', 'web/oa/product/receiver/list', '102', null);
--INSERT INTO `t_menu` VALUES ('104', '210', '产品信息管理', 'web/oa/product/list', '102', null);

-- 新增角色
--INSERT INTO `t_role` VALUES ('30', '产品发货人员');
--INSERT INTO `t_role` VALUES ('31', '生产运营经理');
--
---- 角色菜单
--INSERT INTO `t_role_menu` VALUES ('30', '99');
--INSERT INTO `t_role_menu` VALUES ('31', '100');
--INSERT INTO `t_role_menu` VALUES ('30', '102');
--INSERT INTO `t_role_menu` VALUES ('30', '104');
--INSERT INTO `t_role_menu` VALUES ('31', '102');
--INSERT INTO `t_role_menu` VALUES ('31', '103');
--INSERT INTO `t_role_menu` VALUES ('31', '104');
--
---- 员工角色
--INSERT INTO `t_emp_role` VALUES ('98', '30');
--INSERT INTO `t_emp_role` VALUES ('35', '31');

