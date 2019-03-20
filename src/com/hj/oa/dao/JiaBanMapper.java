package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.JiaBan;

public interface JiaBanMapper {

	public void addJiaBan(JiaBan jiaban);
	
	public List<JiaBan> findMySp(int id);
	
	public JiaBan findByEmpAndDayStr(@Param("empId") int empId, @Param("dayStr") String dayStr);
	
	public List<JiaBan> findByEmpId(int id);
	
	public JiaBan findById(int id);
	
	public void updateJiaBan(JiaBan jiaban);
	
	public void addJianBanRecord(@Param("empId") int empId, @Param("jiabanId") int jiabanId, 
			@Param("dayStr") String dayStr, @Param("timeLen") int timeLen);
	
	
}
