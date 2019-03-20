-- 印章申请
INSERT INTO `t_menu` VALUES ('304', '112', '用章申请', 'web/oa/seal/applySeal', '4', null);
INSERT INTO `t_menu` VALUES ('305', '112', '用章审批', 'web/oa/seal/approveList', '5', null);
INSERT INTO `t_menu` VALUES ('306', '112', '印章信息管理', 'web/oa/seal/manage', '2', null);
INSERT INTO `t_menu` VALUES ('2', '20', '管理', 'web/oa/seal/manage', null, 'resources/images/profile.png');

--新增角色
insert into t_role values(32,"印章申请");
insert into t_role values(33,"印章管理员");
--设置 用章申请 角色

--常规用章权限人：、、、、
--临时用章权限人：各部门主管、张丽、陈利静
--外带用章权限人：财务部主管
-- 行政人事部
insert into t_emp_role values(8,32);
insert into t_emp_role values(9,32);
insert into t_emp_role values(13,32);
insert into t_emp_role values(60,32);
insert into t_emp_role values(98,32);
insert into t_emp_role values(110,32);
insert into t_emp_role values(157,32);
insert into t_emp_role values(169,32);

-- 综合发展部
insert into t_emp_role values(10,32);
insert into t_emp_role values(12,32);
insert into t_emp_role values(78,32);
insert into t_emp_role values(116,32);
insert into t_emp_role values(128,32);
insert into t_emp_role values(163,32);

-- 财务部
insert into t_emp_role values(5,32);
insert into t_emp_role values(6,32);
insert into t_emp_role values(7,32);
insert into t_emp_role values(90,32);
insert into t_emp_role values(96,32);
insert into t_emp_role values(171,32);
insert into t_emp_role values(179,32);
insert into t_emp_role values(180,32);

--信息技术中心、
insert into t_emp_role values(3,32);
insert into t_emp_role values(4,32);

-- 产品业务部、
insert into t_emp_role values(67,32);
insert into t_emp_role values(71,32);
insert into t_emp_role values(94,32);
insert into t_emp_role values(176,32);

-- 品牌运营部
insert into t_emp_role values(61,32);

--王莉君、陈利静、戴炫蕊、马慧琳
insert into t_emp_role values(35,32);
insert into t_emp_role values(42,32);
insert into t_emp_role values(107,32);
insert into t_emp_role values(149,32);

-- 设置 用章管理员 角色
insert into t_emp_role values(61,33);
-- 印章管理员权限
insert into t_role_menu values (33,2);
insert into t_role_menu values (33,306);
--申请权限  用章申请
insert into t_role_menu values (32,304);
--审批权限  部门主管
insert into t_role_menu values (4,304);
insert into t_role_menu values (4,305);


-- 印章类型表
create table t_seal_type(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	seal_type varchar(20) NOT NULL COMMENT '用章类型',
	status int default 1 COMMENT '用章状态' 
);
insert into t_seal_type(seal_type) values ("常规用章");
insert into t_seal_type(seal_type) values ("临时用章");
insert into t_seal_type(seal_type) values ("外带用章");



-- 新增公司印章信息
create table t_seal_company(
     id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
     seal_company VARCHAR(255) NOT NULL COMMENT '公司名称',
     seal_name VARCHAR(50) NOT NULL COMMENT '印章名称',
     seal_unit VARCHAR(10) NOT NULL COMMENT '印章单位',
     seal_num int COMMENT '印章数量',
     content VARCHAR(225) COMMENT '印章备注',
     status int default 1 COMMENT '印章状态'
);

-- 用章申请
create table t_seal_apply(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	emp_id int not null COMMENT '申请人',
	number varchar(20) not null COMMENT '申请编号',
	dayStr varchar(20) not null COMMENT '申请时间',
	seal_type int not null COMMENT '用章类型',
	current_id int not null COMMENT '当前审批人',
	status int not null COMMENT  '审批状态',
	content varchar(500) COMMENT '备注'
);

create table t_seal_apply_detail(
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
	number varchar(20) NOT NULL COMMENT '申请编号',
	file_name varchar(100) NOT NULL COMMENT '用章文件名',
	seal_company VARCHAR(255) NOT NULL COMMENT '公司名称',
    seal_name VARCHAR(50) NOT NULL COMMENT '印章名称',
    file_num int  NOT NULL COMMENT '文件份数',
    user_num int NOT NULL COMMENT '用章数量',
   	loaction varchar(50) NOT NULL COMMENT '用印处',
   	content varchar(100) COMMENT '备注'
);

CREATE TABLE
    t_seal_approve
    (
        id INT NOT NULL AUTO_INCREMENT COMMENT 'id',
        code VARCHAR(20) COMMENT '审批类型单号',
        approve_id INT COMMENT '审批人id',
        approve_status INT COMMENT '审批状态，顺序',
        approve_result VARCHAR(3) COMMENT '审批结果',
        approve_date VARCHAR(20) COMMENT '审批时间',
        approve_opinion VARCHAR(100) COMMENT '审批意见',
        start_date VARCHAR(20) COMMENT '用章时间开始',
        end_date VARCHAR(20) COMMENT '用章时间结束',
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    

--    2018年8月1日08:59:03
alter table t_seal_apply add deal_status int default 1;

INSERT INTO `t_menu` VALUES ('307', '112', '用章申请处理', 'web/oa/seal/sealDeal', '2', null);
INSERT INTO `t_role_menu` VALUES(33,307);
INSERT INTO `t_menu` VALUES ('308', '112', '用章日期管理', 'web/oa/seal/sealDate', '2', null);
INSERT INTO `t_role_menu` VALUES(33,308);

-- 创建用章时间表
CREATE TABLE T_SEAL_DATE(
	ID INT PRIMARY KEY AUTO_INCREMENT ,
	WEEKDAY VARCHAR(20) NOT NULL COMMENT '星期',
	WEEKNUM INT NOT NULL COMMENT '星期对应的数字',
	STATUS INT DEFAULT 1 COMMENT '状态'
);

INSERT INTO T_SEAL_DATE VALUES(1,'周日',0,1);
INSERT INTO T_SEAL_DATE VALUES(2,'周一',1,1);
INSERT INTO T_SEAL_DATE VALUES(3,'周二',2,1);
INSERT INTO T_SEAL_DATE VALUES(4,'周三',3,1);
INSERT INTO T_SEAL_DATE VALUES(5,'周四',4,1);
INSERT INTO T_SEAL_DATE VALUES(6,'周五',5,1);
INSERT INTO T_SEAL_DATE VALUES(7,'周六',6,1);
INSERT INTO T_SEAL_DATE VALUES(8,'周日',-1,1);



alter table t_outstock_detail add fhDate varchar(20) COMMENT '发货日期';
alter table t_outstock_detail add wxbh varchar(50) COMMENT '外箱编号';
alter table t_outstock_detail add bqz varchar(50) COMMENT '标签纸照片';

--以下未更新

--2018-8-8 13:38:15

alter table t_seal_apply_detail add status int default 1;
INSERT INTO `t_menu` VALUES ('14', '21', 'OA处理', 'web/oa/seal/sealDeal', null, 'resources/images/tips.png');
update t_menu set name='OA管理' where id = 2;
update t_menu set pid = 14 where id = 307;
insert into t_role_menu values(33,14);

-- 2018年8月13日09:31:26
alter table t_seal_apply add ptr varchar(10) COMMENT '陪同人';

-- 2018年8月14日08:53:47
create table t_seal_else(
	id INT  primary key  AUTO_INCREMENT COMMENT 'id',
	type varchar(20) not null COMMENT '类型',
	content varchar(200) COMMENT '内容',
	status int(1) COMMENT '状态'
);
-- 2018年8月15日10:13:35

alter table t_seal_apply_detail add fileDetail varchar(100) COMMENT '文件详情';

-- 物流表
create table t_logistics(
	id INT  primary key  AUTO_INCREMENT COMMENT 'id',
	company varchar(20) not null COMMENT '物流公司名称',
	telephone varchar(20) not null COMMENT '联系电话',
	contacts varchar(10) not null COMMENT '联系人',
	address varchar(100) not null COMMENT '地址',
	content varchar(225) COMMENT '备注',
	status int(1) not null default 1 COMMENT '使用状态'
);

-- 权限页面
insert INTO t_role_menu values(21,2);
insert INTO t_role_menu values(21,309);
INSERT INTO `t_menu` VALUES ('309', '112', '物流公司管理', 'web/oa/product/logistics', '2', null);
    
    
    
    
    
    
    
