-- 在‘OA申请’下，新增菜单项
INSERT INTO t_menu (id, seq, name, url, pid) VALUES (89, 23, '出差申请', 'web/oa/waichu/sq', 4);
INSERT INTO t_menu (id, seq, name, url, pid) VALUES (91, 26, '代理出差申请', 'web/oa/waichu/dlsq', 4);
-- 在‘OA审批’中，新增菜单项
INSERT INTO t_menu (id, seq, name, url, pid) VALUES (90, 53, '出差审批', 'web/oa/waichu/mysp', 5);
INSERT INTO t_menu (id, seq, name, url, pid) VALUES (93, 412, '出差注销审批', 'web/oa/waichu/myWaichuCancelSp', 5);
INSERT INTO t_menu (id, seq, name, url, pid) VALUES (94, 413, '销假审批', 'web/oa/leave/myLeaveCancelSp', 5);
-- 在‘考勤管理’中，新增菜单项
INSERT INTO t_menu (id, seq, name, url, pid) VALUES (92, 46, '出差注销', 'web/oa/waichu/goCancelBusiness', 17);