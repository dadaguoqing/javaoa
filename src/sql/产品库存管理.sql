
INSERT INTO `t_menu` VALUES ('311', '112', '供应商信息管理', 'web/oa/merchandise/manage/supplier', '2', null);
INSERT INTO `t_menu` VALUES ('312', '112', '产品申请信息管理', 'web/oa/merchandise/apply', '2', null);
INSERT INTO `t_menu` VALUES ('313', '113', '出入库原因管理', 'web/oa/merchandise/manage/stockReason', '2', null);

CREATE TABLE t_merchandise_supplier(
	id int  auto_increment PRIMARY KEY,
	code VARCHAR(50) not null COMMENT '供应商编码',
	genre VARCHAR(50) not null COMMENT '供应商类别',
	name VARCHAR(50) not null COMMENT '供应商名称',
	address VARCHAR(225) not null COMMENT '地址',
	contact VARCHAR(20) not null COMMENT '联系人',
	phone varchar(20) not null COMMENT '联系电话',
	content varchar(225) COMMENT '备注',
	status int(1) DEFAULT 1 COMMENT '状态 1 激活 -1 冻结',
	obligate varchar(225) COMMENT '预留字段',
	obligate1 varchar(225) COMMENT '预留字段'
);

CREATE TABLE t_merchandise_manage(
	id int PRIMARY KEY AUTO_INCREMENT,
	type varchar(20) not null COMMENT '类型',=
	content varchar(225) not null COMMENT '内容',
	descript varchar(225) COMMENT '描述',
	status int(1) DEFAULT 1 COMMENT '状态 1 激活 -1 冻结'
);

update t_menu set name = 'OA查询', url = 'web/oa/search' where id = 222;
-- 产品库存查询
INSERT INTO `t_menu` VALUES (316, '112', '产品库存查询', 'web/oa/merchandise/search', 222, null);
-- 给产品运营经理 产品库存查询权限
INSERT INTO t_role_menu VALUES (35,316);
INSERT INTO t_role_menu VALUES (35,222);


-- 序号	产品型号	数量	单位	生产信息代码	生产周号	供应商	入库时间	存储位置
-- 序号	产品型号	数量	单位	生产信息代码	外箱编号	供应商	入库时间	存储位置
-- 序号	产品型号	数量	单位	生产信息代码	入库原因	入库来源	入库时间	存储位置
create table t_merchandise_stock(
        id INT NOT NULL AUTO_INCREMENT,
        product_type INT COMMENT '产品类型',
        product_model VARCHAR(50) COMMENT '产品型号',
        quantity INT COMMENT '数量',
        unit VARCHAR(10) COMMENT '单位',
        product_code VARCHAR(50) COMMENT '生产信息代码',
        week_code VARCHAR(50) COMMENT '生产周号',
        package_code VARCHAR(50) COMMENT '外箱编号',
        inbound_reason VARCHAR(200) COMMENT '入库原因',
        supplier VARCHAR(50) COMMENT '供应商',
        inbound_source VARCHAR(50) COMMENT '入库来源',
        inbound_date VARCHAR(20) COMMENT '入库时间',
        location VARCHAR(20) COMMENT '存储位置',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 成品入库申请
INSERT INTO `t_menu` VALUES (317, '112', '成品入库申请', 'web/oa/merchandise/apply/merInbound', 4, null);
INSERT INTO `t_menu` VALUES (318, '112', '不良品入库申请', 'web/oa/merchandise/apply/rejectInbound', 4, null);
INSERT INTO `t_menu` VALUES (319, '112', '半成品入库申请', 'web/oa/merchandise/apply/halfMerInbound', 4, null);
INSERT INTO t_role VALUES(37,'产品仓库管理员');
INSERT INTO t_emp_role VALUES(61, 37);
INSERT INTO t_role_menu VALUES (37, 317);
INSERT INTO t_role_menu VALUES (37, 318);
INSERT INTO t_role_menu VALUES (37,319);

INSERT INTO `t_menu` VALUES (320, '112', '成品入库审批', 'web/oa/merchandise/apply/recordList/1/2', 5, null);
INSERT INTO t_role_menu VALUES (35,320);
INSERT INTO `t_menu` VALUES (321, '112', '不良品入库审批', 'web/oa/merchandise/apply/recordList/5/2', 5, null);
INSERT INTO t_role_menu VALUES (35,321);
INSERT INTO `t_menu` VALUES (322, '112', '半成品入库审批', 'web/oa/merchandise/apply/recordList/3/2', 5, null);
INSERT INTO t_role_menu VALUES (35,322);

INSERT INTO `t_menu` VALUES (323, '112', '成品入库处理', 'web/oa/merchandise/handle/1/1', 14, null);
INSERT INTO t_role_menu VALUES (37,323);
INSERT INTO `t_menu` VALUES (324, '112', '不良品入库处理', 'web/oa/merchandise/handle/5/1', 14, null);
INSERT INTO t_role_menu VALUES (37,324);
INSERT INTO `t_menu` VALUES (325, '112', '半成品入库处理', 'web/oa/merchandise/handle/3/1', 14, null);
INSERT INTO t_role_menu VALUES (37,325);

-- 申请表
CREATE TABLE
    t_merchandise_apply
    (
        id INT NOT NULL AUTO_INCREMENT,
        emp_id INT NOT NULL COMMENT '申请人',
        apply_code VARCHAR(20) NOT NULL UNIQUE COMMENT '申请编号',
        apply_date VARCHAR(20) NOT NULL COMMENT '申请时间',
        mer_type INT NOT NULL COMMENT '申请类型',
        current_id INT NOT NULL COMMENT '当前审批人',
        status INT NOT NULL COMMENT '审批状态',
        content VARCHAR(500) COMMENT '备注',
        expect_date VARCHAR(30) COMMENT '预计日期',
        send_status VARCHAR(30) default 0 COMMENT '分批发货状态(0 未发货，1 发货一次)',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
-- 申请详情表
    create table t_merchandise_apply_detail(
        id INT NOT NULL AUTO_INCREMENT,
        apply_code VARCHAR(20) NOT NULL COMMENT '申请编号',
        product_type INT COMMENT '产品类型',
        product_model VARCHAR(50) COMMENT '产品型号',
        quantity INT COMMENT '数量',
        unit VARCHAR(10) COMMENT '单位',
        product_code VARCHAR(50) COMMENT '生产信息代码',
        week_code VARCHAR(50) COMMENT '生产周号',
        package_code VARCHAR(50) COMMENT '外箱编号',
        inbound_reason VARCHAR(200) COMMENT '出入库原因',
        supplier VARCHAR(50) COMMENT '供应商',
        inbound_source VARCHAR(50) COMMENT '入库来源',
        location VARCHAR(20) COMMENT '存储位置',
        content VARCHAR(500) COMMENT '备注',
        suplus_quantity int COMMENT '未入库数量',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    
-- 出入库记录表
    create table t_merchandise_record(
        id INT NOT NULL AUTO_INCREMENT,
        apply_code VARCHAR(20) NOT NULL COMMENT '申请编号',
        record_type VARCHAR(2) NOT NULL COMMENT '出入库',
        change_date VARCHAR(20) NOT NULL COMMENT '出入库时间',
        apply_detail_id int NOT NULL COMMENT '详情表ID',
        quantity INT COMMENT '数量',
        send_status INT NOT NULL COMMENT '分批发货批次',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    

INSERT INTO `t_menu` VALUES (326, '112', '成品出库申请', 'web/oa/merchandise/apply/merOutbound', 4, null);
INSERT INTO `t_menu` VALUES (327, '112', '不良品出库申请', 'web/oa/merchandise/apply/rejectMerOutbound', 4, null);
INSERT INTO `t_menu` VALUES (328, '112', '半成品出库申请', 'web/oa/merchandise/apply/halfMerOutbound', 4, null);
INSERT INTO t_role_menu VALUES (37, 326);
INSERT INTO t_role_menu VALUES (37, 327);
INSERT INTO t_role_menu VALUES (37, 328);
INSERT INTO `t_menu` VALUES (329, '112', '成品出库审批', 'web/oa/merchandise/apply/recordList/2/2', 5, null);
INSERT INTO `t_menu` VALUES (330, '112', '不良品出库审批', 'web/oa/merchandise/apply/recordList/6/2', 5, null);
INSERT INTO `t_menu` VALUES (331, '112', '半成品出库审批', 'web/oa/merchandise/apply/recordList/4/2', 5, null);
INSERT INTO t_role_menu VALUES (35,329);
INSERT INTO t_role_menu VALUES (35,330);
INSERT INTO t_role_menu VALUES (35,331);
INSERT INTO `t_menu` VALUES (332, '112', '成品出库处理', 'web/oa/merchandise/handle/2/1', 14, null);
INSERT INTO t_role_menu VALUES (37,332);



















