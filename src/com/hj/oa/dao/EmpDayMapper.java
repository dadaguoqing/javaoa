package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.EmpDay;

public interface EmpDayMapper {

	/**
	 * 根据起始时间查询汇总数据
	 * @param begin
	 * @param end
	 * @return
	 */
	public List<EmpDay> findByDaySum(@Param("begin") String begin, @Param("end") String end);
	
	public EmpDay findByEmpIdAndDay(@Param("dayStr") String dayStr, @Param("empId") int empId);
	
	public List<EmpDay> findByDayAndDeptSum(@Param("begin") String begin, 
			@Param("end") String end,@Param("deptId") int deptId);
	
	public List<EmpDay> findByEmpIdAndDayStr(@Param("begin") String begin, 
			@Param("end") String end, @Param("empId") int empId);
	
	
	public EmpDay findByDayStr2(@Param("begin") String begin, @Param("end") String end);
	
	public EmpDay findByDayStrAndDept(@Param("begin") String begin, 
			@Param("end") String end,@Param("deptId") int deptId);
	
	public List<EmpDay> findByDayStr(String dayStr);
	
	public void addEmpDay(EmpDay empDay);
	
	public void updateEmpDay(EmpDay empDay);
	
}
