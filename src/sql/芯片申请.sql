--芯片申请表
CREATE TABLE
    t_os_silicon
    (
        id INT NOT NULL AUTO_INCREMENT,
        applyId INT NOT NULL,
        brand VARCHAR(100),
        num INT,
        reason VARCHAR(1000)
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
--芯片申请审批
	CREATE TABLE
    t_os_silicon_approve
    (
        id INT NOT NULL AUTO_INCREMENT,
        applyId INT NOT NULL,
        currentId INT NOT NULL,
        status INT NOT NULL,
        approveId01 INT NOT NULL,
        approveReason01 VARCHAR(1000),
        approveId02 INT NOT NULL,
        approveReason02 VARCHAR(1000),
        approveId03 INT NOT NULL,
        approveReason03 VARCHAR(1000),
        approveId04 INT NOT NULL,
        approveReason04 VARCHAR(1000),
        approveId05 INT NOT NULL,
        approveReason05 VARCHAR(1000),
        empId INT NOT NULL,
        useDayStr VARCHAR(20),
        dayStr VARCHAR(20),
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
-- 分配权限时，tids字段长度不够报错
    alter table t_auth_sp modify column tids varchar(500);
    
-- 1.设置“芯片申请”(普通员工)和“芯片审批”(技术总监、部门主管)权限
-- 2.增加“芯片管理员”角色
-- 3.设置“葛明静”为“芯片管理员”
INSERT INTO `t_menu` VALUES ('95', '112', '芯片申请', 'web/oa/os/siliconApply', '4', null);
INSERT INTO `t_menu` VALUES ('96', '211', '芯片申请审批', 'web/oa/os/siliconApprove', '5', null);
    
-- 为陈利静、葛明静增加“芯片申请审批”权限

-- 设置王莉君生产运营部的账号为芯片管理员
	update t_emp_role set empId = 35 where empId = 117 and roleId = 18 
    
   
    
    
    
    