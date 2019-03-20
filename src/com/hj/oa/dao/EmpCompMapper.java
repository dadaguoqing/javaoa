package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.EmpCompetence;

public interface EmpCompMapper {
	List<EmpCompetence> selectCompById(@Param("empId") int empId);
	
	void deleteAllCompById(@Param("empId") int empId);
	
	void addEmpComp(@Param("empId") int empId, @Param("pdf") String pdf);
}
