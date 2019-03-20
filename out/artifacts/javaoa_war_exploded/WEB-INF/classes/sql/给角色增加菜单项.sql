-- 给角色增加外出申请菜单
INSERT INTO t_role_menu VALUES 
(1, 89), (2, 89), (3, 89), (4, 89), (5, 89),(6, 89), (7, 89), (8, 89), (9, 89), 
(10, 89),(11, 89), (12, 89), (13, 89), (14, 89), (15, 89),(16, 89),(17, 89);
-- 给总经理，总监，主管增加出差审批菜单
INSERT INTO t_role_menu VALUES (1,90), (2,90), (4,90);
-- 给总主管增加代理出差审批菜单
INSERT INTO t_role_menu VALUES (4,91);
-- 给考勤管理员添加出差注销菜单
INSERT INTO t_role_menu VALUES (6,92);
-- 给总经理，总监，主管增加销假，注销出差审批菜单
INSERT INTO t_role_menu VALUES (1,93), (2,93), (4,93), (1,94), (2,94), (4,94);
-- 删除总经理外出审批菜单
DELETE FROM t_role_menu WHERE roleId = 1 AND menuId = 31;