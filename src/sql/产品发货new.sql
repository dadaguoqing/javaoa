/*
Navicat MySQL Data Transfer

Source Server         : aaa
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : oa_test

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-07-06 10:52:53
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq` int(11) NOT NULL,
  `name` varchar(10) NOT NULL,
  `url` varchar(50) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `icon` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=242 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', '1', '首页', 'web/oa/index', null, 'resources/images/home_1.png');
INSERT INTO `t_menu` VALUES ('3', '2', '员工管理', 'web/oa/user/list', null, 'resources/images/profile.png');
INSERT INTO `t_menu` VALUES ('4', '33', 'OA申请', 'web/oa/leave/afl', null, 'resources/images/27_edit_text.png');
INSERT INTO `t_menu` VALUES ('5', '44', 'OA审批', 'web/oa/leave/mysp', null, 'resources/images/button_check.png');
INSERT INTO `t_menu` VALUES ('6', '66', '物料管理', 'web/oa/materia_v2/maApplyList2', null, 'resources/images/accessories_text_editor.png');
INSERT INTO `t_menu` VALUES ('7', '88', '个人中心', 'web/oa/kq/mine', null, 'resources/images/info.png');
INSERT INTO `t_menu` VALUES ('8', '99', '退出登录', 'web/logout', null, 'resources/images/exit.png');
INSERT INTO `t_menu` VALUES ('9', '11', '添加员工', 'web/oa/user/add', '3', '');
INSERT INTO `t_menu` VALUES ('10', '12', '员工列表', 'web/oa/user/list', '3', '');
INSERT INTO `t_menu` VALUES ('11', '13', '角色管理', 'web/oa/role/mgr', '3', '');
INSERT INTO `t_menu` VALUES ('12', '14', '权限设置', 'web/oa/role/menu', '3', '');
INSERT INTO `t_menu` VALUES ('13', '21', '请假申请', 'web/oa/leave/afl', '4', '');
INSERT INTO `t_menu` VALUES ('15', '50', '个人信息', 'web/oa/user/info', '7', '');
INSERT INTO `t_menu` VALUES ('16', '55', '密码修改', 'web/oa/user/cp', '7', '');
INSERT INTO `t_menu` VALUES ('17', '3', '考勤管理', 'web/oa/kq/list', null, 'resources/images/cal1.png');
INSERT INTO `t_menu` VALUES ('18', '44', '考勤导入', 'web/oa/kq/import', '17', '');
INSERT INTO `t_menu` VALUES ('19', '44', '日历设置', 'web/oa/date/mgr', '17', '');
INSERT INTO `t_menu` VALUES ('20', '51', '请假审批', 'web/oa/leave/mysp', '5', '');
INSERT INTO `t_menu` VALUES ('21', '99', '权限变更审批', 'web/oa/asp/mysp', '5', '');
INSERT INTO `t_menu` VALUES ('22', '25', '代理请假申请', 'web/oa/leave/alfs', '4', '');
INSERT INTO `t_menu` VALUES ('23', '33', '权限申请记录', 'web/oa/asp/myAuthSq', '4', '');
INSERT INTO `t_menu` VALUES ('24', '29', '权限代理', 'web/oa/asp/dlsq', '4', '');
INSERT INTO `t_menu` VALUES ('25', '98', '权限代理审批', 'web/oa/asp/mydlsp', '5', '');
INSERT INTO `t_menu` VALUES ('26', '24', '加班申请', 'web/oa/jiaban/sq', '4', '');
INSERT INTO `t_menu` VALUES ('27', '88', '加班审批', 'web/oa/jiaban/mysp', '5', '');
INSERT INTO `t_menu` VALUES ('28', '31', '我的考勤', 'web/oa/kq/mine', '7', '');
INSERT INTO `t_menu` VALUES ('29', '41', '考勤查询', 'web/oa/kq/list', '17', '');
INSERT INTO `t_menu` VALUES ('30', '23', '外出申请', 'web/oa/waichu/sq', '4', '');
INSERT INTO `t_menu` VALUES ('31', '53', '外出审批', 'web/oa/waichu/mysp', '5', '');
INSERT INTO `t_menu` VALUES ('32', '22', '打卡补签申请', 'web/oa/kq/sq', '4', '');
INSERT INTO `t_menu` VALUES ('33', '52', '打卡补签审批', 'web/oa/kq/mysp', '5', '');
INSERT INTO `t_menu` VALUES ('34', '33', '年假记录', 'web/oa/nianjia/mine', '7', '');
INSERT INTO `t_menu` VALUES ('35', '42', '打卡查询', 'web/oa/kq/search', '17', '');
INSERT INTO `t_menu` VALUES ('36', '45', '年假损益', 'web/oa/nianjia/sy', '17', null);
INSERT INTO `t_menu` VALUES ('37', '26', '代理外出申请', 'web/oa/waichu/dlsq', '4', null);
INSERT INTO `t_menu` VALUES ('38', '46', '请假注销', 'web/oa/leave/goCancelLeave', '17', null);
INSERT INTO `t_menu` VALUES ('39', '15', '外网访问', 'web/oa/user/out', '3', null);
INSERT INTO `t_menu` VALUES ('40', '34', '订餐记录', 'web/oa/dc/myRecord', '7', null);
INSERT INTO `t_menu` VALUES ('41', '112', '办公用品申领', 'web/oa/os/sq', '4', null);
INSERT INTO `t_menu` VALUES ('42', '55', '资产管理', 'web/oa/os/seach', null, 'resources/images/cal.png');
INSERT INTO `t_menu` VALUES ('43', '112', '办公用品消耗查询', 'web/oa/os/seach', '42', null);
INSERT INTO `t_menu` VALUES ('44', '133', '办公用品申领处理', 'web/oa/os/sqList', '42', null);
INSERT INTO `t_menu` VALUES ('45', '43', '年假查询', 'web/oa/kq/njList', '17', null);
INSERT INTO `t_menu` VALUES ('46', '212', '应急药品申领', 'web/oa/os/medSq', '4', null);
INSERT INTO `t_menu` VALUES ('47', '233', '应急药品申领处理', 'web/oa/os/medSqList', '42', null);
INSERT INTO `t_menu` VALUES ('48', '212', '应急药品消耗查询', 'web/oa/os/medSearch', '42', null);
INSERT INTO `t_menu` VALUES ('49', '322', '固定资产申领', 'web/oa/prop/ly', '4', null);
INSERT INTO `t_menu` VALUES ('50', '322', '资产申领审批', 'web/oa/prop/myLySp', '5', null);
INSERT INTO `t_menu` VALUES ('51', '35', '固定资产发放', 'web/oa/prop/ff', '42', null);
INSERT INTO `t_menu` VALUES ('52', '32', '我的资产', 'web/oa/prop/mine', '7', null);
INSERT INTO `t_menu` VALUES ('53', '34', '固定资产管理', 'web/oa/prop/mgr', '42', null);
INSERT INTO `t_menu` VALUES ('55', '333', '资产维修审批', 'web/oa/prop/myWxSp', '5', null);
INSERT INTO `t_menu` VALUES ('56', '355', '资产注销审批', 'web/oa/prop/myZxSp', '5', null);
INSERT INTO `t_menu` VALUES ('57', '36', '固定资产归还', 'web/oa/prop/gh', '42', null);
INSERT INTO `t_menu` VALUES ('58', '40', '固定资产转移', 'web/oa/prop/zy', '42', null);
INSERT INTO `t_menu` VALUES ('59', '344', '资产转移审批', 'web/oa/prop/myZySp', '5', null);
INSERT INTO `t_menu` VALUES ('60', '33', '添加固定资产', 'web/oa/prop/add', '42', null);
INSERT INTO `t_menu` VALUES ('61', '41', '固定资产维修', 'web/oa/prop/goWx?id=30', '42', null);
INSERT INTO `t_menu` VALUES ('62', '42', '固定资产注销', 'web/oa/prop/goZx?id=30', '42', null);
INSERT INTO `t_menu` VALUES ('63', '34', '固定资产查询', 'web/oa/prop/search', '42', null);
INSERT INTO `t_menu` VALUES ('64', '311', '固定资产采购申请', 'web/oa/prop/cg', '4', null);
INSERT INTO `t_menu` VALUES ('65', '311', '资产采购审批', 'web/oa/prop/myCgSp', '5', null);
INSERT INTO `t_menu` VALUES ('66', '33', '固定资产采购', 'web/oa/prop/cgrk', '42', null);
INSERT INTO `t_menu` VALUES ('67', '43', '固定资产维修', 'web/oa/prop/wxRecord', '42', null);
INSERT INTO `t_menu` VALUES ('68', '111', '办公用品采购申请', 'web/oa/os/wpcg', '4', null);
INSERT INTO `t_menu` VALUES ('69', '211', '应急药品采购申请', 'web/oa/os/ypcg', '4', null);
INSERT INTO `t_menu` VALUES ('70', '111', '办公用品采购审批', 'web/oa/os/wpCgSp', '5', null);
INSERT INTO `t_menu` VALUES ('71', '211', '应急药品采购审批', 'web/oa/os/ypCgSp', '5', null);
INSERT INTO `t_menu` VALUES ('72', '113', '办公用品损益申请', 'web/oa/os/wpsy', '4', null);
INSERT INTO `t_menu` VALUES ('73', '213', '应急药品损益申请', 'web/oa/os/ypsy', '4', null);
INSERT INTO `t_menu` VALUES ('74', '111', '办公用品库存', 'web/oa/os/stock', '42', null);
INSERT INTO `t_menu` VALUES ('75', '211', '应急药品库存', 'web/oa/os/medStock', '42', null);
INSERT INTO `t_menu` VALUES ('76', '122', '办公用品损益审批', 'web/oa/os/myWpSySp', '5', null);
INSERT INTO `t_menu` VALUES ('77', '222', '应急药品损益审批', 'web/oa/os/myYpSySp', '5', null);
INSERT INTO `t_menu` VALUES ('78', '122', '办公用品采购处理', 'web/oa/os/wpCgRkList', '42', null);
INSERT INTO `t_menu` VALUES ('79', '222', '应急药品采购处理', 'web/oa/os/ypCgRkList', '42', null);
INSERT INTO `t_menu` VALUES ('80', '411', '加工申请', 'web/oa/lab/jgSq', '4', null);
INSERT INTO `t_menu` VALUES ('81', '411', '加工审批', 'web/oa/lab/jgSp', '5', null);
INSERT INTO `t_menu` VALUES ('82', '38', '节日福利', 'web/oa/gift/list', '7', null);
INSERT INTO `t_menu` VALUES ('83', '112', '办公用品特别申请', 'web/oa/os/applyNewArticle', '4', null);
INSERT INTO `t_menu` VALUES ('84', '211', '办公用品特别申请审批', 'web/oa/os/specialApplyApprove', '5', null);
INSERT INTO `t_menu` VALUES ('85', '112', '办公用品特别申请处理', 'web/oa/os/speApplyList', '42', null);
INSERT INTO `t_menu` VALUES ('86', '112', '办公用品变更审批', 'web/oa/os/modifyApprove', '5', null);
INSERT INTO `t_menu` VALUES ('87', '47', '考勤导出', 'web/oa/kq/export', '17', null);
INSERT INTO `t_menu` VALUES ('89', '23', '出差申请', 'web/oa/waichu/sq', '4', null);
INSERT INTO `t_menu` VALUES ('90', '53', '出差审批', 'web/oa/waichu/mysp', '5', null);
INSERT INTO `t_menu` VALUES ('91', '26', '代理出差申请', 'web/oa/waichu/dlsq', '4', null);
INSERT INTO `t_menu` VALUES ('92', '46', '出差注销', 'web/oa/waichu/goCancelBusiness', '17', null);
INSERT INTO `t_menu` VALUES ('93', '412', '出差注销审批', 'web/oa/waichu/myWaichuCancelSp', '5', null);
INSERT INTO `t_menu` VALUES ('94', '413', '销假审批', 'web/oa/leave/myLeaveCancelSp', '5', null);
INSERT INTO `t_menu` VALUES ('95', '112', '芯片申请', 'web/oa/os/siliconApply', '4', null);
INSERT INTO `t_menu` VALUES ('96', '211', '芯片申请审批', 'web/oa/os/siliconApprove', '5', null);
INSERT INTO `t_menu` VALUES ('97', '112', '技术支持申请', 'web/oa/os/tecSupport', '4', null);
INSERT INTO `t_menu` VALUES ('98', '211', '技术支持申请审批', 'web/oa/os/supportApproveList', '5', null);
INSERT INTO `t_menu` VALUES ('99', '500', '产品发货申请', 'web/oa/product/apply/add', '4', null);
INSERT INTO `t_menu` VALUES ('100', '500', '产品发货审批', 'web/oa/product/outstock/approval/list', '5', null);
INSERT INTO `t_menu` VALUES ('101', '213', '产品出库', 'web/oa/product/outstock/list', '102', null);
INSERT INTO `t_menu` VALUES ('102', '55', '产品管理', 'web/oa/product', null, 'resources/images/product.png');
INSERT INTO `t_menu` VALUES ('103', '211', '客户信息管理', 'web/oa/product/receiver/list', '102', null);
INSERT INTO `t_menu` VALUES ('104', '210', '产品信息管理', 'web/oa/product/list', '102', null);
INSERT INTO `t_menu` VALUES ('119', '211', '上传模板', 'web/oa/materia/uploadExcel', '6', null);
INSERT INTO `t_menu` VALUES ('222', '77', '物料查询', 'web/oa/materia_v2/query', null, 'resources/images/search.jpg');
INSERT INTO `t_menu` VALUES ('228', '112', '物料申领', 'web/oa/materia_v2/mtApply', '4', null);
INSERT INTO `t_menu` VALUES ('229', '211', '物料入库', 'web/oa/materia_v2/addmt', '6', null);
INSERT INTO `t_menu` VALUES ('230', '211', '物料申领处理', 'web/oa/materia_v2/maApplyList2', '6', null);
INSERT INTO `t_menu` VALUES ('231', '211', '物料入库查询', 'web/oa/materia_v2/queryIn', '222', null);
INSERT INTO `t_menu` VALUES ('232', '211', '物料消耗查询', 'web/oa/materia_v2/queryCost', '222', null);
INSERT INTO `t_menu` VALUES ('233', '211', '物料库存查询', 'web/oa/materia_v2/query', '222', null);
INSERT INTO `t_menu` VALUES ('234', '211', '物料申领审批', 'web/oa/materia_v2/maApplyList', '5', null);
INSERT INTO `t_menu` VALUES ('235', '112', '物料采购申请', 'web/oa/materia_v2/purchase', '4', null);
INSERT INTO `t_menu` VALUES ('236', '211', '物料采购审批', 'web/oa/materia_v2/purchaseList', '5', null);
INSERT INTO `t_menu` VALUES ('237', '112', '物料请购申请', 'web/oa/materia_v2/requisition', '4', null);
INSERT INTO `t_menu` VALUES ('238', '211', '物料请购审批', 'web/oa/materia_v2/requisitionList', '5', null);
INSERT INTO `t_menu` VALUES ('239', '112', '物料入库申请', 'web/oa/materia_v2/inware3', '4', null);
INSERT INTO `t_menu` VALUES ('240', '211', '物料入库审批', 'web/oa/materia_v2/inwareApproveList2', '5', null);
INSERT INTO `t_menu` VALUES ('241', '211', '物料入库处理', 'web/oa/materia_v2/toInware', '6', null);
