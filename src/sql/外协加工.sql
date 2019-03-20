UPDATE t_menu set pid = 4 where name = '固定资产转移';


-- external processing
-- 申请表
DROP TABLE IF EXISTS t_external_apply;
CREATE TABLE t_external_apply(
	id int primary key auto_increment,
	proposer int unsigned not null comment '申请人id',
	apply_date char(18) not null comment '申请时间',
	apply_code char(15) not null unique comment '申请编号',
	external_type varchar(20) not null comment '外协加工类型',
	content varchar(255) comment '加工事由',
	project_name varchar(100) comment '项目名称',
	status int(2)  not null comment '审批状态',
	current_id int(6) not null comment '当前审批人id'
) COMMENT '外协加工申请信息';

DROP TABLE IF EXISTS t_external_detail;
CREATE TABLE t_external_detail(
	id int primary key auto_increment,
	apply_code varchar(20) not null unique comment '申请编号',
	pcb_description varchar(100) comment 'pcb加工工艺要求文档',
	pcb_num int unsigned comment 'pcb加工数量',
	impedance_descript varchar(100) comment '阻抗说明书',
	pcb_gerber varchar(100) comment 'pcb gerber文件',
	weld_num int unsigned comment '焊接数量',
	weld_bom varchar(100) comment '焊接BOM单',
	weld_gerber varchar(100) comment '焊接gerber文件',
	weld_coordinate varchar(100) comment '焊接坐标文件',
	weld_silk_screen varchar(100) comment '焊接丝印文件',
	weld_descript varchar(100) comment '焊接工艺文件',
	steel_num int unsigned comment '钢网加工数量',
	steel_size1 decimal(9,2) comment '钢网加工尺寸',
	steel_size2 decimal(9,2) comment '钢网加工尺寸',
	steel_thinkness decimal(9,2) comment '钢网厚度',
	steel_material varchar(10) comment '钢网材料',
	steel_use varchar(10) comment '钢网用途',
	steel_polishing varchar(20) comment '抛光工艺',
	glue_descript varchar(100) comment '打白胶工艺要求文档',
	paint_descript varchar(100) comment '三防漆工艺要求文档',
	acrylic_num int unsigned comment '亚克力加工数量',
	acrylic_cad varchar(100) comment '亚克力加工图纸',
	chassis_num int unsigned comment '机箱数量',
	chassis_cad varchar(100) comment '机箱加工图纸',
	chassis_list varchar(100) comment '机箱清单',
	pencil_num int unsigned comment '线束加工数量',
	pencil_cad varchar(100) comment '线束加工图纸',
	pencil_list varchar(100) comment '线束清单',
	pcb_cost decimal(9,2) comment 'pcb加工费用',
	component_cost decimal(9,2) comment '电子元器件费用',
	weld_cost decimal(9,2) comment '焊接费用',
	steel_cost decimal(9,2) comment '钢网费用',
	glue_cost decimal(9,2) comment '打白胶费用',
	paint_cost decimal(9,2) comment '三防漆费用',
	acrylic_cost decimal(9,2) comment '亚克力费用',
	chassis_cost decimal(9,2) comment '机箱加工费用',
	pencil_cost decimal(9,2) comment '线束加工费用',
	total_cost decimal(9,2) comment '预算合计',
	is_urgent varchar(20) comment '是否加急'
) COMMENT '外协加工申请详情';

-- IC研发中心系统设计部、AC研发中心、产品技术部、宏晶信息实验室（朱玉斌）
-- 8 19 14 

-- 41 43 46 55  65 70 109
-- 2 69 76 108 112 132 138 139 154 183 184 185

INSERT INTO t_role VALUES (50, '外协加工申请人');
INSERT INTO t_emp_role VALUES (97, 50);
INSERT INTO t_emp_role VALUES (99, 50);
INSERT INTO t_emp_role VALUES (124, 50);
INSERT INTO t_emp_role VALUES (155, 50);
INSERT INTO t_emp_role VALUES (158, 50);
INSERT INTO t_emp_role VALUES (159, 50);
INSERT INTO t_emp_role VALUES (160, 50);
INSERT INTO t_emp_role VALUES (162, 50);
INSERT INTO t_emp_role VALUES (164, 50);
INSERT INTO t_emp_role VALUES (168, 50);
INSERT INTO t_emp_role VALUES (175, 50);
INSERT INTO t_emp_role VALUES (187, 50);
INSERT INTO t_emp_role VALUES (190, 50);
INSERT INTO t_emp_role VALUES (193, 50);
INSERT INTO t_emp_role VALUES (195, 50);
INSERT INTO t_emp_role VALUES (198, 50);

INSERT INTO t_emp_role VALUES (41, 50);
INSERT INTO t_emp_role VALUES (43, 50);
INSERT INTO t_emp_role VALUES (46, 50);
INSERT INTO t_emp_role VALUES (55, 50);
INSERT INTO t_emp_role VALUES (65, 50);
INSERT INTO t_emp_role VALUES (70, 50);
INSERT INTO t_emp_role VALUES (109, 50);
INSERT INTO t_emp_role VALUES (2, 50);
INSERT INTO t_emp_role VALUES (69, 50);
INSERT INTO t_emp_role VALUES (76, 50);
INSERT INTO t_emp_role VALUES (108, 50);
INSERT INTO t_emp_role VALUES (112, 50);
INSERT INTO t_emp_role VALUES (132, 50);
INSERT INTO t_emp_role VALUES (138, 50);
INSERT INTO t_emp_role VALUES (139, 50);
INSERT INTO t_emp_role VALUES (154, 50);
INSERT INTO t_emp_role VALUES (183, 50);
INSERT INTO t_emp_role VALUES (184, 50);
INSERT INTO t_emp_role VALUES (185, 50);
INSERT INTO t_emp_role VALUES (148, 50);


INSERT INTO `t_menu` VALUES (360, '112', '外协加工申请', 'web/oa/external/apply', 4, null);
INSERT INTO t_role_menu VALUES (50, 360);


INSERT INTO `t_menu` VALUES (361, '112', '外协加工审批', 'web/oa/external/approveList', 5, null);
INSERT INTO t_role_menu VALUES (4, 361);

-- 权限给制版负责人
INSERT INTO `t_menu` VALUES (362, '112', '外协加工处理', 'web/oa/external/dealList', 14, null);
INSERT INTO t_role_menu VALUES (13, 362);

DROP TABLE IF EXISTS t_external_deal;
CREATE TABLE t_external_deal(
	id int primary key auto_increment,
	apply_code varchar(20) not null unique comment '申请编号',
	pcb_cost decimal(9, 2) comment 'pcb加工费用',
	pcb_date char(11) comment 'pcb加工交期',
	pcb_pdf varchar(225) comment 'pcb费用明细',
	bom_cost decimal(9, 2) comment 'bom加工费用',
	bom_date char(11) comment 'bom加工交期',
	bom_pdf varchar(225) comment 'bom费用明细',
	weld_cost decimal(9, 2) comment '焊接加工费用',
	weld_date char(11) comment '焊接加工交期',
	weld_pdf varchar(225) comment '焊接费用明细',
	steel_cost decimal(9, 2) comment '钢网加工费用',
	steel_date char(11) comment '钢网加工交期',
	steel_pdf varchar(225) comment '钢网费用明细',
	glue_cost decimal(9, 2) comment '打白胶加工费用',
	glue_date char(11) comment '打白胶加工交期',
	glue_pdf varchar(225) comment '打白胶费用明细',
	paint_cost decimal(9, 2) comment '三防漆加工费用',
	paint_date char(11) comment '三防漆加工交期',
	paint_pdf varchar(225) comment '三防漆费用明细',
	acrylic_cost decimal(9, 2) comment '亚克力加工费用',
	acrylic_date char(11) comment '亚克力加工交期',
	acrylic_pdf varchar(225) comment '亚克力费用明细',
	chassis_cost decimal(9, 2) comment '机箱加工费用',
	chassis_date char(11) comment '机箱加工交期',
	chassis_pdf varchar(225) comment '机箱费用明细',
	pencil_cost decimal(9, 2) comment '线束加工费用',
	pencil_date char(11) comment '线束加工交期',
	pencil_pdf varchar(225) comment '线束费用明细',
	total_cost decimal(9, 2) comment '花费总计'
) COMMENT '外协加工处理';

INSERT INTO t_role VALUES ('52', '生产主管');
INSERT INTO t_emp_role VALUES (112, 52);
INSERT INTO t_role VALUES ('53', '生产总监');
INSERT INTO t_emp_role VALUES (1, 53);

-- serena
INSERT INTO t_role VALUES ('54', '财务会计');
INSERT INTO t_emp_role VALUES (96, 54);

INSERT INTO `t_menu` VALUES (363, '112', '外协加工处理审批', 'web/oa/external/dealApproveList', 5, null);
INSERT INTO t_role_menu VALUES (52, 363);
INSERT INTO t_role_menu VALUES (53, 363);
INSERT INTO t_role_menu VALUES (54, 363);

INSERT INTO `t_menu` VALUES (364, '112', '外协加工入库', 'web/oa/external/inboundList', 14, null);
INSERT INTO t_role_menu VALUES (13, 364);

DROP TABLE IF EXISTS t_external_inbound;
CREATE TABLE t_external_inbound(
	id int primary key auto_increment,
	apply_code varchar(20) not null comment '申请编号',
	inbound_num int comment '入库数量',
	inbound_date char(11) comment '入库时间',
	type char(1) comment '加工类型'
)comment '外协加工入库';

INSERT INTO `t_menu` VALUES (365, '112', '外协加工管理', 'web/oa/external/manage', 2, null);
INSERT INTO t_role_menu VALUES (13, 365);
INSERT INTO t_role_menu VALUES (13, 2);

DROP TABLE IF EXISTS t_manage_config;
create table t_manage_config(
	id int primary key auto_increment,
	content varchar(255) comment '内容',
	descript varchar(255) comment '描述',
	status char(1) default '1' comment '状态'
);













