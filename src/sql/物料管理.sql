INSERT INTO `t_menu` VALUES ('6', '66', '物料管理', 'web/oa/materia/maApplyList2', null, 'resources/images/accessories_text_editor.png');
INSERT INTO `t_menu` VALUES ('100', '211', '物料录入', 'web/oa/materia/add', '6', null);
INSERT INTO `t_menu` VALUES ('101', '211', '物料入库', 'web/oa/materia/received', '6', null);
INSERT INTO `t_menu` VALUES ('102', '112', '物料申领', 'web/oa/materia/mtApply', '4', null);
INSERT INTO `t_menu` VALUES ('103', '211', '物料申领审批', 'web/oa/materia/maApplyList', '5', null);
INSERT INTO `t_menu` VALUES ('104', '211', '物料申领处理', 'web/oa/materia/maApplyList2', '6', null);
INSERT INTO `t_menu` VALUES ('105', '112', '物料请购申请', 'web/oa/materia/purchase', '4', null);
INSERT INTO `t_menu` VALUES ('106', '211', '物料请购审批', 'web/oa/materia/purchaseList', '5', null);
INSERT INTO `t_menu` VALUES ('107', '112', '物料采购申请', 'web/oa/materia/purchase4', '4', null);
INSERT INTO `t_menu` VALUES ('108', '211', '物料采购审批', 'web/oa/materia/purchaseList2', '5', null);
INSERT INTO `t_menu` VALUES ('109', '112', '物料调拨申请', 'web/oa/materia/allot', '4', null);
INSERT INTO `t_menu` VALUES ('110', '211', '物料调拨审批', 'web/oa/materia/allotList', '5', null);
INSERT INTO `t_menu` VALUES ('111', '112', '物料出库申请', 'web/oa/materia/outware', '4', null);
INSERT INTO `t_menu` VALUES ('112', '211', '物料出库审批', 'web/oa/materia/outwareList', '5', null);
INSERT INTO `t_menu` VALUES ('113', '112', '物料报废处理申请', 'web/oa/materia/reject', '4', null);
INSERT INTO `t_menu` VALUES ('114', '211', '物料报废处理审批', 'web/oa/materia/rejectList', '5', null);
INSERT INTO `t_menu` VALUES ('115', '211', '物料查询', 'web/oa/materia/queryAll', '6', null);
INSERT INTO `t_menu` VALUES ('116', '211', '物料入库', 'web/oa/materia/receivedAll', '6', null);
INSERT INTO `t_menu` VALUES ('117', '211', '物料调拨处理', 'web/oa/materia/allotList2', '6', null);
INSERT INTO `t_menu` VALUES ('118', '211', '物料请购处理', 'web/oa/materia/purchaseDeal', '6', null);
INSERT INTO `t_menu` VALUES ('119', '211', '上传模板', 'web/oa/materia/uploadExcel', '6', null);
INSERT INTO `t_menu` VALUES ('120', '112', '入库申请', 'web/oa/materia/inware', '4', null);
INSERT INTO `t_menu` VALUES ('121', '211', '物料入库处理', 'web/oa/materia/inwareDeal', '6', null);
INSERT INTO `t_menu` VALUES ('122', '211', '物料入库审批', 'web/oa/materia/inwareList', '5', null);


/*
Navicat MySQL Data Transfer

Source Server         : oa
Source Server Version : 50520
Source Host           : 192.168.188.91:3306
Source Database       : oa_test2

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2018-01-05 14:28:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_mt
-- ----------------------------
DROP TABLE IF EXISTS `t_mt`;
CREATE TABLE `t_mt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `materiaCode` varchar(50) DEFAULT NULL,
  `classfiy` varchar(50) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `createTime` varchar(20) DEFAULT NULL,
  `spec` varchar(300) DEFAULT NULL,
  `package1` varchar(50) DEFAULT NULL,
  `unit` varchar(8) DEFAULT NULL,
  `price` double DEFAULT '0',
  `supplier` varchar(30) DEFAULT '0',
  `functionChina` varchar(50) DEFAULT NULL,
  `functionEnglish` varchar(50) DEFAULT NULL,
  `diff` varchar(200) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `bz` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11476 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_macode
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_macode`;
CREATE TABLE `t_mt_macode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(25) DEFAULT NULL,
  `materiaCode` varchar(50) DEFAULT NULL,
  `num` double DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `realNum` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_mould
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_mould`;
CREATE TABLE `t_mt_mould` (
  `type` varchar(20) NOT NULL,
  `url` varchar(50) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_mtapply
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_mtapply`;
CREATE TABLE `t_mt_mtapply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empId` int(11) DEFAULT NULL,
  `use1` varchar(50) DEFAULT NULL,
  `code` varchar(25) DEFAULT NULL,
  `warehouse` varchar(50) DEFAULT NULL,
  `warehouse2` varchar(50) DEFAULT NULL,
  `dayStr` varchar(50) DEFAULT NULL,
  `bz` varchar(500) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `dId` int(11) DEFAULT NULL,
  `dResult` varchar(8) DEFAULT NULL,
  `dOpinion` varchar(50) DEFAULT NULL,
  `dDayStr` varchar(50) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `pResult` varchar(8) DEFAULT NULL,
  `pOpinion` varchar(50) DEFAULT NULL,
  `pDayStr` varchar(50) DEFAULT NULL,
  `mId` int(11) DEFAULT NULL,
  `mResult` varchar(8) DEFAULT NULL,
  `mOpinion` varchar(50) DEFAULT NULL,
  `mDayStr` varchar(50) DEFAULT NULL,
  `currentId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `aId` int(11) DEFAULT NULL,
  `aResult` varchar(8) DEFAULT NULL,
  `aOpinion` varchar(50) DEFAULT NULL,
  `aDayStr` varchar(50) DEFAULT NULL,
  `fId` int(11) DEFAULT NULL,
  `fResult` varchar(8) DEFAULT NULL,
  `fOpinion` varchar(50) DEFAULT NULL,
  `fDayStr` varchar(50) DEFAULT NULL,
  `faId` int(11) DEFAULT NULL,
  `faResult` varchar(8) DEFAULT NULL,
  `faOpinion` varchar(50) DEFAULT NULL,
  `faDayStr` varchar(50) DEFAULT NULL,
  `isDeal` int(11) DEFAULT '0',
  `url` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_purchase
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_purchase`;
CREATE TABLE `t_mt_purchase` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `empId` int(11) DEFAULT NULL,
  `dayStr` varchar(30) DEFAULT NULL,
  `number` varchar(19) DEFAULT NULL,
  `reason` varchar(50) DEFAULT NULL,
  `deadline` varchar(22) DEFAULT NULL,
  `url` varchar(50) DEFAULT NULL,
  `bz` varchar(500) DEFAULT NULL,
  `totalMoney` double DEFAULT NULL,
  `purType` int(11) DEFAULT NULL,
  `dId` int(11) DEFAULT NULL,
  `dResult` varchar(8) DEFAULT NULL,
  `dOpinion` varchar(50) DEFAULT NULL,
  `dDayStr` varchar(30) DEFAULT NULL,
  `pId` int(11) DEFAULT NULL,
  `pResult` varchar(8) DEFAULT NULL,
  `pOpinion` varchar(50) DEFAULT NULL,
  `pDayStr` varchar(30) DEFAULT NULL,
  `mId` int(11) DEFAULT NULL,
  `mResult` varchar(8) DEFAULT NULL,
  `mOpinion` varchar(50) DEFAULT NULL,
  `mDayStr` varchar(30) DEFAULT NULL,
  `fId` int(11) DEFAULT NULL,
  `fResult` varchar(8) DEFAULT NULL,
  `fOpinion` varchar(50) DEFAULT NULL,
  `fDayStr` varchar(30) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `currentId` int(11) DEFAULT NULL,
  `isDeal` int(11) DEFAULT '0',
  `outId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_purchase_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_purchase_detail`;
CREATE TABLE `t_mt_purchase_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `number` varchar(19) DEFAULT NULL,
  `name` varchar(30) DEFAULT NULL,
  `brand` varchar(30) DEFAULT NULL,
  `spec` varchar(30) DEFAULT NULL,
  `package1` varchar(30) DEFAULT NULL,
  `unit` varchar(8) DEFAULT NULL,
  `others` varchar(100) DEFAULT NULL,
  `needNum` int(11) DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `purNum` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `totalMoney` double DEFAULT NULL,
  `otherCost` double DEFAULT NULL,
  `deadline` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_record
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_record`;
CREATE TABLE `t_mt_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `personId` int(11) DEFAULT NULL,
  `dayStr` varchar(20) DEFAULT NULL,
  `warehouse` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `receivedType` int(11) DEFAULT NULL,
  `bz` varchar(200) DEFAULT NULL,
  `url` varchar(100) DEFAULT NULL,
  `temp` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_record_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_record_detail`;
CREATE TABLE `t_mt_record_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `materiaCode` varchar(50) DEFAULT NULL,
  `num` double DEFAULT NULL,
  `stock` double DEFAULT NULL,
  `temp` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5429 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_mt_stock
-- ----------------------------
DROP TABLE IF EXISTS `t_mt_stock`;
CREATE TABLE `t_mt_stock` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `materiaCode` varchar(50) NOT NULL,
  `warehouse1` double DEFAULT '0',
  `warehouse2` double DEFAULT '0',
  `warehouse3` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2708 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for t_stock_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_stock_detail`;
CREATE TABLE `t_stock_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `warehouse` varchar(50) DEFAULT NULL,
  `mininum` int(11) DEFAULT '0',
  `once` int(11) DEFAULT '0',
  `spNum` int(11) DEFAULT '0',
  `pcsNum` int(11) DEFAULT '0',
  `oc` int(11) DEFAULT '0',
  `materiaCode` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=36718 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_mt_mould
-- ----------------------------
INSERT INTO `t_mt_mould` VALUES ('allot', null, '物料调拨模板');
INSERT INTO `t_mt_mould` VALUES ('apply', '', '物料申领模板');
INSERT INTO `t_mt_mould` VALUES ('enter', null, '物料录入模板');
INSERT INTO `t_mt_mould` VALUES ('inware', null, '物料入库模板');
INSERT INTO `t_mt_mould` VALUES ('outware', null, '物料出库模板');
INSERT INTO `t_mt_mould` VALUES ('purchase', '', '物料请购模板');
INSERT INTO `t_mt_mould` VALUES ('purchase2', null, '物料采购模板');
INSERT INTO `t_mt_mould` VALUES ('reject', null, '物料报废模板');
INSERT INTO `t_mt_mould` VALUES ('return', '', '物料申领反馈模板');

-- ----------------------------
-- 权限分配
-- ----------------------------
-- 物料申请 
-- 人员设置：工业控制项目、芯片集成设计部、系统设计部、软件设计部、模拟设计部、技术部全体人员。
-- 权限页面：物料申领、物料请购申请、物料入库处理、物料请购处理。

-- 新增角色 ： 物料审批
-- 人员设置：总账会计、财务部主管，工业控制项目、芯片集成设计部、系统设计部、软件设计部、模拟设计部、技术部部门主管，总监，总经理。
-- 权限页面：物料申领审批、物料请购审批、物料采购审批、物料调拨审批、物料出库审批、物料报废处理审批、物料入库审批。

-- 仓库管理员
-- 人员设置：夏红、黄小玉、孟欣。
-- 页面：物料申领、物料请购申请、物料出库申请、物料采购申请、物料报废申请、物料调拨申请、物料入库申请。


-- 物料采购员
-- 人员设置：夏红、李鑫、孟欣。
-- 页面：物料采购申请、物料入库申请。


alter table t_mt_purchase drop outId;
alter table t_mt_purchase add url2 varchar(50);
alter table t_mt_purchase add access varchar(50);

-- 2018年2月6日14:18:15
alter table t_mt_mtapply add url2 varchar(50);
alter table t_mt add url2 varchar(50);
alter table t_mt_purchase_detail add reason varchar(200);

-- 2018-5-26 17:04:31增加仓库部门关系表
CREATE TABLE
    t_materia_dept
    (
        id INT NOT NULL AUTO_INCREMENT comment 'ID',
        warehouse VARCHAR(10) NOT NULL comment '仓库名称',
        deptId INT comment '部门ID',
        adminId INT comment '仓库管理员ID',
        purchaseId INT comment '仓库采购员ID',
        PRIMARY KEY (warehouse),
        CONSTRAINT id UNIQUE (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
INSERT INTO t_materia_dept (warehouse,deptId,adminId,purchaseId)  VALUES ('芯片研发中心仓库',7,42,140);
INSERT INTO t_materia_dept (warehouse,deptId,adminId,purchaseId)  VALUES ('工业控制中心仓库',3,124,140);
INSERT INTO t_materia_dept (warehouse,deptId,adminId,purchaseId)  VALUES ('深圳技术部仓库',14,124,140);












