
DROP TABLE IF EXISTS t_property_firm;
CREATE TABLE t_property_firm(
	id int primary key auto_increment,
	firm_name varchar(255) comment '公司名称',
	status char(1) default '1' comment '状态'
) comment '公司';