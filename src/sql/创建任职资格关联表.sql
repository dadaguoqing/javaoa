SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- 创建任职资格表，员工id，和保存任职资格资料pdf的url
-- ----------------------------
DROP TABLE IF EXISTS `t_emp_competence`;
CREATE TABLE `t_emp_competence` (
  `empId` int(11) NOT NULL,
  `pdf` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;