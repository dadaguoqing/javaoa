package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.CheckInRemote;

public interface CheckInMapper {
	
	//A3考勤机远程数据
	public void addRemoteCheckIn(@Param("remote") CheckInRemote remote);
	public CheckInRemote findRemoteCheckInByName(@Param("name") String name, @Param("dayStr") String dayStr);
	public List<CheckInRemote> findRemoteCheckInByDay(String dayStr);
	public void deleteRemoteCheckInByDay(@Param("dayStr")String dayStr,@Param("cs") List<CheckInRemote> cs);
	
	//A3结束
	public void addCheckIn(@Param("cs") List<CheckIn> cs);
	
	public void addACheckIn(@Param("item") CheckIn ci);
	
	public List<CheckIn> findByDay(String dayStr);
	
	public List<CheckIn> findByEmp(int empId);
	
	public CheckIn findByDayAndEmp(@Param("dayStr") String dayStr, @Param("empId") int empId);
	
	public List<CheckIn> findByEmpAndTime(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime,@Param("empId") int empId);
	
	public List<CheckIn> findByTimeSum(@Param("beginTime") String beginTime, @Param("endTime") String endTime,
			@Param("begin") String begin, @Param("end") String end);
	
	public List<CheckIn> findByTimeSumForD(@Param("dDeptId") int dDeptId, @Param("beginTime") String beginTime, @Param("endTime") String endTime,
			@Param("begin") String begin, @Param("end") String end);
	
	public List<CheckIn> findByTimeSumForDeptMgr(@Param("dDeptId") int dDeptId, @Param("beginTime") String beginTime, @Param("endTime") String endTime,
			@Param("begin") String begin, @Param("end") String end);
	
	//删除某一天的打卡数据
	public void deleteCheckinByDay(String day);
	//删除某一天的打卡数据
	public void deleteCheckinByDayAndEmpId(@Param("dayStr") String dayStr, @Param("empId") int empId);
	//编辑打卡数据
	public void updateCheckin(CheckIn checkin);
}
