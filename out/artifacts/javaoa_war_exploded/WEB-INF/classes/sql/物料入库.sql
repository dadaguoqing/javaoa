-- 仓库
DROP TABLE IF EXISTS t_materia_warehouse;
CREATE TABLE t_materia_warehouse(
	id int primary key auto_increment,
	warehouse varchar(10) not null,
	status char(1) default 1
) COMMENT '仓库表';

INSERT INTO t_role VALUES (55, '质检员');
INSERT INTO t_emp_role VALUES(184, 55);

-- 物料入库申请权限