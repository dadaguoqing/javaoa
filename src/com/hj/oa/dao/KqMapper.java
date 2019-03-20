package com.hj.oa.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.EmpDayVo;
import com.hj.oa.bean.ParamBean;

public interface KqMapper {

	public List<EmpDayVo> findByEmpAndDay(@Param("empId") int empId, 
			@Param("begin") String begin, @Param("end") String end, @Param("orderType") String orderType);
	
	public List<EmpDayVo> findUnEmpDay(@Param("begin") String begin, @Param("end") String end);
	
	public List<HashMap> findByEmpAndDayHuiZong(@Param("empId") int empId, 
			@Param("begin") String begin, @Param("end") String end);
	
	public List<EmpDayVo> findByEmpAndDayAll(@Param("begin") String begin, @Param("end") String end);
	
	public List<EmpDayVo> findByEmpAll(int empId);
	
	public List<BKqSp> findRetroactiveRecord(ParamBean pb);
	
	public List<EmpDayVo> findSumAll(@Param("begin") String begin, @Param("end") String end);
	
	public List<EmpDayVo> findByDayAndDeptSum(@Param("deptId") int deptId, @Param("begin") String begin, @Param("end") String end);

	
	public void addBKqSp(BKqSp sp);
	
	public void updateBKqSp(BKqSp sp);
	
	public void insertBKqSp(BKqSp sp);
	
	public BKqSp findBKqById(int id);
	
	public List<BKqSp> findMySp(int empId);
	
	public List<CheckIn> findCheckInByEmpAndDay(CheckIn ci);

	public List<BKqSp> findMySq(int empId);
	/**
	 * 计算总数，分页
	*2017年6月8日
	 * @return
	 */
	public int countBKqSq(@Param("style")String style);
	
	public List<BKqSp> findBKqSq(@Param("style")String style, @Param("begin")int begin);
	
	public List<BKqSp> findMySpRecord(int empId);
	
	public List<EmpDayVo> findEmpAndDayById(@Param("begin") String begin, @Param("end") String end, @Param("emdId") int empId);
}
