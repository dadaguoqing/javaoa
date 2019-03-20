INSERT INTO t_menu (id, seq, name, url, pid, icon) VALUES (null, 112, '技术支持申请', 'web/oa/os/tecSupport', 4, null);
INSERT INTO t_menu (id, seq, name, url, pid, icon) VALUES (null, 211, '技术支持申请审批', 'web/oa/os/supportApproveList', 5, null); 

CREATE TABLE
    t_os_support
    (
        id INT NOT NULL AUTO_INCREMENT,
        applyId INT,
        supportId INT,
        dayStr VARCHAR(20),
        customer VARCHAR(200),
        project VARCHAR(100),
        supportContent VARCHAR(1000),
        accessory VARCHAR(1000),
        priority VARCHAR(5),
        expectdTime VARCHAR(20),
        deadline VARCHAR(20),
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    CREATE TABLE
    t_os_support_approve
    (
        supportId INT DEFAULT '0' NOT NULL,
        busManagerId INT,
        approveDate01 VARCHAR(30),
        approveOpinion01 VARCHAR(1000),
        tecManagerId INT,
        status INT,
        currentId INT,
        approveResult01 VARCHAR(10),
        endTime VARCHAR(20),
        content VARCHAR(1000),
        finalResult VARCHAR(50),
        currentId2 INT,
        PRIMARY KEY (supportId)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
    CREATE TABLE
    t_os_support_record
    (
        id INT NOT NULL AUTO_INCREMENT,
        supportId INT,
        approveDate02 VARCHAR(20),
        approveResult02 VARCHAR(10),
        file2 VARCHAR(200),
        FAEId INT,
        FAEId02 INT,
        FAEResult VARCHAR(1000),
        FAETime VARCHAR(20),
        customerResult VARCHAR(10),
        customerOpinion VARCHAR(1000),
        customerDate VARCHAR(20),
        approveOpinion02 VARCHAR(1000),
        currentStatus INT,
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
    

--  新设置一个角色“技术支持审批”
    insert into t_role values(null,"技术支持审批");19
    
--  给技术部14和业务部16人员审批权限，
    insert into t_emp_role values(44,19);
    insert into t_emp_role values(48,19);
    insert into t_emp_role values(61,19);
    insert into t_emp_role values(67,19);
    insert into t_emp_role values(69,19);
    insert into t_emp_role values(71,19);
    insert into t_emp_role values(76,19);
    insert into t_emp_role values(88,19);
    insert into t_emp_role values(94,19);
    insert into t_emp_role values(108,19);
    insert into t_emp_role values(112,19);
    insert into t_emp_role values(132,19);
    insert into t_emp_role values(133,19);
    insert into t_emp_role values(134,19);
    insert into t_emp_role values(136,19);
    insert into t_emp_role values(138,19);
    insert into t_emp_role values(139,19);
    insert into t_emp_role values(140,19);
    insert into t_emp_role values(154,19);
    insert into t_emp_role values(155,19);
    
    
   -- 44 48 61 67 69 71 76 88 94 108 112 132 133 134 136 138 139 140 154 155
    
    --技术支持审批角色可以看到技术支持审批页面
    insert into t_role_menu valus(19,98);
    