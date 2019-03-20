-- 研发人员 角色
-- 42 97 99 124 155 158 159 160 161 162 164 168 175 187 190 193 195 197
INSERT INTO t_role VALUES(40,'研发人员');
INSERT INTO t_emp_role VALUES(42, 40);
INSERT INTO t_emp_role VALUES(97, 40);
INSERT INTO t_emp_role VALUES(99, 40);
INSERT INTO t_emp_role VALUES(124, 40);
INSERT INTO t_emp_role VALUES(155, 40);
INSERT INTO t_emp_role VALUES(158, 40);
INSERT INTO t_emp_role VALUES(159, 40);
INSERT INTO t_emp_role VALUES(160, 40);
INSERT INTO t_emp_role VALUES(161, 40);
INSERT INTO t_emp_role VALUES(162, 40);
INSERT INTO t_emp_role VALUES(164, 40);
INSERT INTO t_emp_role VALUES(168, 40);
INSERT INTO t_emp_role VALUES(175, 40);
INSERT INTO t_emp_role VALUES(187, 40);
INSERT INTO t_emp_role VALUES(190, 40);
INSERT INTO t_emp_role VALUES(193, 40);
INSERT INTO t_emp_role VALUES(195, 40);
INSERT INTO t_emp_role VALUES(197, 40);
-- 2 36 37 84 156 172 174 178 181 188 200
INSERT INTO t_emp_role VALUES(2, 40);
INSERT INTO t_emp_role VALUES(36, 40);
INSERT INTO t_emp_role VALUES(37, 40);
INSERT INTO t_emp_role VALUES(84, 40);
INSERT INTO t_emp_role VALUES(156, 40);
INSERT INTO t_emp_role VALUES(172, 40);
INSERT INTO t_emp_role VALUES(174, 40);
INSERT INTO t_emp_role VALUES(178, 40);
INSERT INTO t_emp_role VALUES(181, 40);
INSERT INTO t_emp_role VALUES(188, 40);
INSERT INTO t_emp_role VALUES(200, 40);
-- 41 43 46 55 65 70 109
INSERT INTO t_emp_role VALUES(41, 40);
INSERT INTO t_emp_role VALUES(43, 40);
INSERT INTO t_emp_role VALUES(46, 40);
INSERT INTO t_emp_role VALUES(55, 40);
INSERT INTO t_emp_role VALUES(65, 40);
INSERT INTO t_emp_role VALUES(70, 40);
INSERT INTO t_emp_role VALUES(109, 40);
-- 14 15 21 24 26 91 104 105 153 173 186
INSERT INTO t_emp_role VALUES(14, 40);
INSERT INTO t_emp_role VALUES(15, 40);
INSERT INTO t_emp_role VALUES(21, 40);
INSERT INTO t_emp_role VALUES(24, 40);
INSERT INTO t_emp_role VALUES(26, 40);
INSERT INTO t_emp_role VALUES(91, 40);
INSERT INTO t_emp_role VALUES(104, 40);
INSERT INTO t_emp_role VALUES(105, 40);
INSERT INTO t_emp_role VALUES(153, 40);
INSERT INTO t_emp_role VALUES(173, 40);
INSERT INTO t_emp_role VALUES(186, 40);
-- 69 76 108 112 132 138 139 154 183 184 185
INSERT INTO t_emp_role VALUES(69, 40);
INSERT INTO t_emp_role VALUES(76, 40);
INSERT INTO t_emp_role VALUES(108, 40);
INSERT INTO t_emp_role VALUES(112, 40);
INSERT INTO t_emp_role VALUES(132, 40);
INSERT INTO t_emp_role VALUES(138, 40);
INSERT INTO t_emp_role VALUES(139, 40);
INSERT INTO t_emp_role VALUES(154, 40);
INSERT INTO t_emp_role VALUES(183, 40);
INSERT INTO t_emp_role VALUES(184, 40);
INSERT INTO t_emp_role VALUES(185, 40);
-- 35 177 189 196
INSERT INTO t_emp_role VALUES(35, 40);
INSERT INTO t_emp_role VALUES(177, 40);
INSERT INTO t_emp_role VALUES(189, 40);
INSERT INTO t_emp_role VALUES(196, 40);
-- 28 74 113 118 137 167 182
INSERT INTO t_emp_role VALUES(28, 40);
INSERT INTO t_emp_role VALUES(74, 40);
INSERT INTO t_emp_role VALUES(113, 40);
INSERT INTO t_emp_role VALUES(118, 40);
INSERT INTO t_emp_role VALUES(137, 40);
INSERT INTO t_emp_role VALUES(167, 40);
INSERT INTO t_emp_role VALUES(182, 40);
-- 45 194 202
INSERT INTO t_emp_role VALUES(45, 40);
INSERT INTO t_emp_role VALUES(194, 40);
INSERT INTO t_emp_role VALUES(202, 40);
INSERT INTO t_emp_role VALUES(148, 40);



INSERT INTO `t_menu` VALUES ('310', '112', '印章申请信息管理', 'web/oa/seal/sealManage', '2', null);
INSERT INTO t_emp_role VALUES (310,33);
alter table t_seal_else modify status INT(1) default 1 COMMENT '状态'

alter table t_materia_purchase add projectCode varchar(225) COMMENT '项目名称';
alter table t_seal_apply add wdr varchar(10) COMMENT '外带人';

-- 更新 applicationContext.xml
-- 注入mailTableUtil 和 添加定时任务

INSERT INTO t_role VALUES (35,'产品运营经理');
-- 设置角色人员
INSERT INTO t_emp_role VALUES (35,35);

INSERT INTO t_role_menu VALUES (35,311);
INSERT INTO t_role_menu VALUES (35,312);
INSERT INTO t_role_menu VALUES (35,313);

-- 给ljchenOA管理权限
INSERT INTO `t_menu` VALUES ('314', '112', '物料采购处理', 'web/oa/materia_v2/dealPurchaseList', '14', null);
INSERT INTO `t_menu` VALUES ('315', '112', '物料入库处理', 'web/oa/materia_v2/dealInwareList', '14', null);
INSERT INTO t_role_menu VALUES (23,314);
INSERT INTO t_role_menu VALUES (23,315);
INSERT INTO t_role_menu VALUES (23,14);
update t_menu set url = 'web/oa/deal' where id = 14;

alter table t_mt modify materiaCode varchar(20) not null;

INSERT INTO `t_menu` VALUES ('350', '112', '物料入库检验', 'web/oa/materia_v2/checkInbound', '14', null);
INSERT INTO t_role_menu VALUES (40,350);
INSERT INTO t_role_menu VALUES (40,14);

-- 未更新
-- seq表增加id
INSERT INTO t_seal_date VALUES(10,'12时00分',-1,1);

CREATE TABLE t_menu_permission(
	id int  auto_increment PRIMARY KEY,
	menu_id int NOT NULL COMMENT '菜单名称',
	menu_name VARCHAR(20) NOT NULL COMMENT '菜单名称',
	role_id int NOT NULL COMMENT '角色id',
	role_name VARCHAR(20) NOT NULL COMMENT '角色名称',
	url VARCHAR(100) NOT NULL COMMENT '页面地址',
	status int(1) default 1 COMMENT '状态'
);


--- 物料缓存
CREATE TABLE t_materia_cache(
	 id INT PRIMARY KEY  AUTO_INCREMENT COMMENT 'id',
	 apply_code VARCHAR(20) NOT NULL COMMENT '申请编号',
	 materia_id INT NOT NULL COMMENT '物料id',
	 num DOUBLE NOT NULL COMMENT '数量',
	 warehouse_id INT NOT NULL COMMENT '仓库id',
	 status INT(1) default 1 COMMENT '状态'
);


-- 物料报废申请
INSERT INTO `t_menu` VALUES (351, '112', '物料报废申请', 'web/oa/materia_v2/reject', 4, null);
INSERT INTO `t_menu` VALUES (352, '112', '物料报废审批', 'web/oa/materia_v2/rejectApprove', 5, null);
INSERT INTO t_role_menu VALUES (27, 351);
INSERT INTO t_role_menu VALUES (4, 352);


-- 更新菜单
update t_menu set pid = 2 where name in 
('角色管理', '添加员工', '员工列表', '权限设置', '外网访问', '物料入库', '考勤导入','添加固定资产',
'日历设置', '年假损益', '请假注销', '考勤导出', '出差注销', '固定资产管理', '客户信息管理', 
'产品信息管理', '办公用品库存', '应急药品库存');

update t_menu set pid = 14 where name in ('物料申领处理', '物料入库处理', '返料入库处理', '办公用品申领处理',
'应急药品申领处理', '固定资产发放', '固定资产归还', '固定资产转移',  '固定资产维修', '固定资产注销', 
'固定资产采购', '产品出库', '办公用品采购处理', '应急药品采购处理');

update t_menu set pid = 222 where name in ('物料入库查询', '物料消耗查询', '物料库存查询', '产品库存查询', 
'考勤查询', '打卡查询', '年假查询', '办公用品消耗查询', '应急药品消耗查询', '固定资产查询');

--给角色添加OA管理、OA处理、OA查询页面
INSERT INTO t_role_menu VALUES (1 , 2);
INSERT INTO t_role_menu VALUES (30 , 2);
INSERT INTO t_role_menu VALUES (31 , 2);
INSERT INTO t_role_menu VALUES (37 , 2);
INSERT INTO t_role_menu VALUES (37 , 14);
INSERT INTO t_role_menu VALUES (17 , 2);
INSERT INTO t_role_menu VALUES (27 , 2);
INSERT INTO t_role_menu VALUES (27 , 14);
INSERT INTO t_role_menu VALUES (6 , 2);
INSERT INTO t_role_menu VALUES (6 , 14);
INSERT INTO t_role_menu VALUES (6 , 222);
INSERT INTO t_role_menu VALUES (15 , 2);
INSERT INTO t_role_menu VALUES (15 , 14);
INSERT INTO t_role_menu VALUES (15 , 222);
INSERT INTO t_role_menu VALUES (9 , 2);
INSERT INTO t_role_menu VALUES (9 , 14);
INSERT INTO t_role_menu VALUES (1 , 222);
INSERT INTO t_role_menu VALUES (2 , 222);
INSERT INTO t_role_menu VALUES (4 , 222);
INSERT INTO t_role_menu VALUES (21 , 14);

-- 删除不用的菜单
-- 3 员工管理, 6 物料管理, 17 考勤管理, 42 资产管理, 102 产品管理
delete from t_role_menu where menuId in (3, 6, 17, 42, 102);



-- ----------------------------
-- Records of t_menu_permission
-- ----------------------------
INSERT INTO `t_menu_permission` VALUES ('1', '2', 'OA管理', '33', '印章管理员', '/web/oa/seal/manage', '1');
INSERT INTO `t_menu_permission` VALUES ('2', '2', 'OA管理', '30', '产品发货人员', '/web/oa/product/logistics', '1');
INSERT INTO `t_menu_permission` VALUES ('3', '2', 'OA管理', '35', '产品运营经理', '/web/oa/merchandise/manage/supplier', '1');
INSERT INTO `t_menu_permission` VALUES ('4', '14', 'OA处理', '33', '印章管理员', '/web/oa/seal/sealDeal', '1');
INSERT INTO `t_menu_permission` VALUES ('5', '14', 'OA处理', '23', '物料采购员', '/web/oa/materia_v2/dealPurchaseList', '1');
INSERT INTO `t_menu_permission` VALUES ('6', '14', 'OA处理', '40', '研发人员', '/web/oa/materia_v2/checkInbound', '1');
INSERT INTO `t_menu_permission` VALUES ('7', '222', 'OA查询', '40', '研发人员', '/web/oa/materia_v2/query', '1');
INSERT INTO `t_menu_permission` VALUES ('8', '222', 'OA查询', '35', '产品运营经理', '/web/oa/merchandise/search', '1');
INSERT INTO `t_menu_permission` VALUES ('9', '2', 'OA管理', '37', '产品仓库管理员', '/web/oa/merchandise/manage/stockReason', '1');
INSERT INTO `t_menu_permission` VALUES ('10', '2', 'OA管理', '1', '总经理', '/web/oa/role/mgr', '1');
INSERT INTO `t_menu_permission` VALUES ('11', '2', 'OA管理', '17', '员工管理', '/web/oa/user/add', '1');
INSERT INTO `t_menu_permission` VALUES ('12', '2', 'OA管理', '27', '新版仓库管理员', '/web/oa/materia_v2/addmt', '1');
INSERT INTO `t_menu_permission` VALUES ('13', '2', 'OA管理', '6', '考勤管理员', '/web/oa/date/mgr', '1');
INSERT INTO `t_menu_permission` VALUES ('14', '2', 'OA管理', '9', '固定资产管理员', '/web/oa/prop/mgr', '1');
INSERT INTO `t_menu_permission` VALUES ('15', '2', 'OA管理', '31', '生产运营经理', '/web/oa/product/receiver/list', '1');
INSERT INTO `t_menu_permission` VALUES ('16', '2', 'OA管理', '15', '办公用品管理员', '/web/oa/os/stock', '1');
INSERT INTO `t_menu_permission` VALUES ('18', '14', 'OA处理', '15', '办公用品管理员', '/web/oa/os/sqList', '1');
INSERT INTO `t_menu_permission` VALUES ('19', '14', 'OA处理', '27', '新版仓库管理员', '/web/oa/materia_v2/maApplyList2', '1');
INSERT INTO `t_menu_permission` VALUES ('20', '14', 'OA处理', '9', '固定资产管理员', '/web/oa/prop/ff', '1');
INSERT INTO `t_menu_permission` VALUES ('21', '14', 'OA处理', '21', '仓库管理员', '/web/oa/product/outstock/list', '1');
INSERT INTO `t_menu_permission` VALUES ('22', '222', 'OA查询', '1', '总经理', '/web/oa/kq/list', '1');
INSERT INTO `t_menu_permission` VALUES ('23', '222', 'OA查询', '2', '技术总监', '/web/oa/kq/list', '1');
INSERT INTO `t_menu_permission` VALUES ('24', '222', 'OA查询', '4', '部门主管', '/web/oa/kq/list', '1');
INSERT INTO `t_menu_permission` VALUES ('25', '222', 'OA查询', '15', '办公用品管理员', '/web/oa/os/seach', '1');
INSERT INTO `t_menu_permission` VALUES ('26', '222', 'OA查询', '9', '固定资产管理员', '/web/oa/prop/search', '1');
INSERT INTO `t_menu_permission` VALUES ('27', '2', 'OA管理', '1', '总经理', '/web/oa/index', '-1');



-- 制版管理员Oa审批页面换位加工处理页面     13
-- 删除资产采购员审批页面  固定资产管理员

INSERT INTO `t_menu` VALUES (354, '112', '外协加工处理', 'web/oa/lab/myPcbSp3', 14, null);

update t_menu set url = 'web/oa/lab/wxSq' where name = '加工申请';

-- 芯片管理员审批页面换芯片申请处理页面 18

INSERT INTO `t_menu` VALUES (355, '112', '芯片申请处理', 'web/oa/os/siliconApprove2', 14, null);


-- 配置文件中清理印章定时任务未设置











