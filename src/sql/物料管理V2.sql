CREATE TABLE
    t_materia_dept
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT 'ID',
        warehouse VARCHAR(10) NOT NULL COMMENT '仓库名称',
        deptId INT COMMENT '部门ID',
        adminId INT COMMENT '仓库管理员ID',
        purchaseId INT COMMENT '仓库采购员ID',
        status INT DEFAULT '1' COMMENT '是否启用：1 启用，-1不启用',
        PRIMARY KEY (warehouse),
        CONSTRAINT id UNIQUE (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	
	CREATE TABLE
    t_materia_record
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT,
        mt_apply_id INT COMMENT '申请人ID',
        mt_createdate VARCHAR(50) COMMENT '创建时间',
        mt_type INT COMMENT '类型',
        mt_warehouse INT COMMENT '仓库ID',
        code VARCHAR(20) COMMENT '关联记录表code',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	CREATE TABLE
    t_materia_record_detail
    (
        id INT(10) unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
        mt_code VARCHAR(12) COMMENT '物料编码',
        code VARCHAR(20) COMMENT '关联记录表code',
        mt_num DOUBLE COMMENT '物料数量',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	
	CREATE TABLE
    t_materia_stock
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT 'id',
        mt_code VARCHAR(50) COMMENT '物料编码',
        mt_warehouse INT COMMENT '仓库ID',
        mt_cache DOUBLE DEFAULT '0' COMMENT '缓存',
        mt_stock DOUBLE COMMENT '库存数',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
	
	CREATE TABLE
    t_mt
    (
        id INT NOT NULL AUTO_INCREMENT,
        materiaCode VARCHAR(12) NOT NULL,
        classfiy VARCHAR(50),
        brand VARCHAR(50),
        createTime VARCHAR(20),
        spec VARCHAR(300),
        package1 VARCHAR(100),
        unit VARCHAR(8),
        price DOUBLE DEFAULT '0',
        supplier VARCHAR(30) DEFAULT '0',
        functionChina VARCHAR(50),
        functionEnglish VARCHAR(50),
        diff VARCHAR(200),
        url VARCHAR(50),
        bz VARCHAR(500),
        url2 VARCHAR(50),
        status INT DEFAULT '1',
        PRIMARY KEY (id),
        CONSTRAINT materiaCode UNIQUE (materiaCode)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
--    2018年6月6日15:27:25
    DROP TABLE t_materia_stock;
CREATE TABLE
    t_materia_stock
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT 'id',
        mt_code VARCHAR(50) COMMENT '物料编码',
        mt_warehouse INT COMMENT '仓库ID',
        mt_cache DOUBLE DEFAULT '0' COMMENT '缓存',
        mt_stock DOUBLE COMMENT '库存数',
        mt_price double COMMENT '价格' not null,
        mt_date varchar(20) comment '入库时间' not null,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
--    2018年6月7日10:13:26
    create table t_materia_cost(
        id int primary key auto_increment,
        cost_code varchar(20) comment '物料申领编号',
        materiaId int comment '物料编号',
        warehouseId int comment '仓库编号',
        cost_num double comment '消耗数量',
        cost_price double comment '消耗单价'
);
alter table t_mt_macode add price double comment '物料价格';

--2018-6-12 15:44:00
create table t_materia_seq(
        id int primary key auto_increment,
        date varchar(12) comment '上次日期',
        currentId int comment '序号'
);
insert into t_materia_seq (date,currentId) values ('2018年6月12日',1);

--2018年6月14日09:27:40
DROP table t_materia_purchase;
CREATE TABLE
    t_materia_purchase
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT 'id',
        emp_id INT COMMENT '申请人',
        requisition_code VARCHAR(20) COMMENT '请购单编号',
        reason VARCHAR(200) COMMENT '采购事由',
        dayStr VARCHAR(20) COMMENT '申请时间',
        purchase_code VARCHAR(20) COMMENT '采购单编号',
        count_money DOUBLE(11,2) COMMENT '采购总价',
        content VARCHAR(500) COMMENT '备注',
        current_id INT COMMENT '当前审批人Id',
        status INT COMMENT '审批状态',
        type INT COMMENT '采购类型：1采购、2请购',
        url varchar(50) COMMENT '请购表格',
        url2 varchar(50) COMMENT '请购附件',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table t_materia_approve(
         id int primary key auto_increment comment 'id',
         code varchar(20) comment '审批类型单号',
         approve_id int comment '审批人id',
         approve_status int comment '审批状态，顺序',
         approve_result varchar(3) comment '审批结果',
         approve_date varchar(20) comment '审批时间',
         approve_opinion varchar(100) comment '审批意见'
);

create table t_materia_purchase_detail(
        id int primary key auto_increment comment 'id',
        code varchar(20) comment '审批类型单号',
        ma_code varchar(20) comment '物料编码',
        classify varchar(200) comment '品名分类',
        brand varchar(200) comment '品牌',
        spec varchar(200) comment '规格型号', 
        package1 varchar(20) comment '封装',
        unit varchar(10) comment '单位',
        need_num double comment '需求数',
        stock_num double comment '库存数',
        need_date varchar(20) comment '需求日期',
        use_date varchar(20) comment '计划日期',
        buyNum double comment '采购数量',
        price double comment '单价',
        cost double comment '其他费用',
        supplier varchar(100) comment '供应商',
        link varchar(500) comment '链接地址'
);


--2018年7月2日16:17:59
--创建物料返料入库菜单
INSERT INTO `t_menu` VALUES ('300', '112', '返料入库申请', 'web/oa/materia_v2/reventMateria', '4', null);
INSERT INTO `t_menu` VALUES ('301', '211', '返料入库处理', 'web/oa/materia_v2/reventApprove', '6', null);
--申请权限  物料申请角色
insert into t_role_menu values (22,300);
--审批权限  新版仓库管理员
insert into t_role_menu values (27,301);

-- 生成返料入库编号
insert into t_materia_seq values (4,'20180630',1);


-- 2018年7月11日08:58:52
--损益申请菜单
INSERT INTO `t_menu` VALUES ('302', '112', '物料损益申请', 'web/oa/materia_v2/applySy', '4', null);
INSERT INTO `t_menu` VALUES ('303', '112', '物料损益审批', 'web/oa/materia_v2/syApproveList', '5', null);
--申请权限  仓库管理员
insert into t_role_menu values (27,302);
--审批权限  部门主管
insert into t_role_menu values (4,303);


-- 2018年7月20日08:55:34
DROP TABLE
    `oa`.`t_mt_purchase`;
DROP TABLE
    `oa`.`t_mt_purchase_detail`;
DROP TABLE
    `oa`.`t_mt_record`;
DROP TABLE
    `oa`.`t_mt_record_detail`;
DROP TABLE
    `oa`.`t_mt_stock`;



