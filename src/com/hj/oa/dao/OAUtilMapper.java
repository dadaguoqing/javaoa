package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.EmpDaySum;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.LeaveDayDetail;
import com.hj.oa.bean.NianjiaRecord;

public interface OAUtilMapper {

	//请假获得批准之后，添加员工每日请假明细记录
	public void addLeaveDayDetail(@Param("list") List<LeaveDayDetail> list);
	
	//根据员工id，开始和结束时间，查询所有的员工每日请假/外出（出差）明细
	public List<LeaveDayDetail> findLeaveDayDetailByTime(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("empId") int empId);
	
	//查询某日的所有员工的外出、请假明细
	public List<LeaveDayDetail> findLeaveDayDetailByDay(String dayStr);
	
	//查询某日的某个员工的外出、请假明细
	public List<LeaveDayDetail> findLeaveDayDetailByDayAndEmp(@Param("dayStr") String dayStr, @Param("empId") int empId);
	
	/**
	 * 批量导入打卡数据
	 * @param list
	 */
	public void addCheckin(@Param("list") List<CheckIn> list);
	//删除某一天的打卡数据
	public void deleteCheckinByDay(String day);
	//编辑打卡数据
	public void updateCheckin(CheckIn checkin);
	
	//获取某个员工病假，年假的剩余信息
	public EmpNianjia getEmpNianjiaById(int empId);
	
	//添加用户年假或者病假信息（用于初始化）
	public void addEmpNianjia(@Param("list") List<EmpNianjia> list);
	
	//添加用户年假或者病假信息（用于初始化）
	public void addAnEmpNianjia(@Param("item") EmpNianjia nj);
	
	public void addAnEmpNianjiaBak(@Param("item") EmpNianjia nj);
	
	//更新年假信息
	public void updateEmpNianjia(EmpNianjia nianjia);
	
	public List<EmpNianjia> findAllNianjia();
	
	public List<EmpNianjia> findAllNianjia2();
	
	public List<EmpNianjia> findAllNianjiaByDept(@Param("depts") String depts);
	
	public List<EmpNianjia> findAllNianjiaByTechDept();
	
	//添加年假或者病假消除记录
	public void addNianjiaRecord(@Param("list") List<NianjiaRecord> list);
	
	public List<NianjiaRecord> findNianjiaRecordByEmpId(@Param("empId") int empId, @Param("type") String type);
	
	public List<NianjiaRecord> findAllNianjiaRecord();
	
	public List<NianjiaRecord> findHistoryRecod();
	
	//删除当日的信息
	public void deleteEmpDaySummer(@Param("empId") int empId, @Param("list") List<EmpDaySum> list);
	//删除当日的信息,根据empId
	public void deleteEmpDaySumByDayAndEmps(@Param("dayStr") String dayStr, @Param("list") List<EmpDaySum> list);
	
	public void deleteEmpDaySumByEmpAndDay(@Param("dayStr") String dayStr, @Param("empId") int empId);
	//添加
	public void addEmpDaySummer(@Param("list") List<EmpDaySum> list);
	
	public void deleteByDayAndEmpId(@Param("dayStr") String dayStr, @Param("empId") int empId);
	
	public void deleteLeaveDayDetailByDay(@Param("dayStr") String dayStr, @Param("empId") int empId);
	
	public void addEmpDaySum(@Param("item") EmpDaySum item);
}
