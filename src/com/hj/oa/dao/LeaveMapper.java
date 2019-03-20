package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveCancel;

public interface LeaveMapper {

	public List<Leave> findAllLeaves();

	public List<Leave> findByEmpId(@Param("id") int id, @Param("waichu") int waichu);

	public List<Leave> findMyDaili(int empId);
	
	public List<Leave> findMyDailiChuChai(int empId);

	public List<Leave> findMineAtSp(int empId);

	public List<Leave> findMySp(@Param("id") int id, @Param("waichu") int waichu);

	// 获取某一天的所有请假（审批过的）
	public List<Leave> findLeaveByDay(@Param("beginTime") String beginTime, @Param("endTime") String endTime);

	// 查询所有的待审批（包括出差，请假）。
	public List<Leave> findMySpTypeAll(@Param("id") int id);

	// 查询所有的待审批的取消请假（包括出差，请假）。
	public List<LeaveCancel> findMyLeaveCancelSpTypeAll(@Param("id") int id);

	/** 查询所有销假记录 */
	public List<LeaveCancel> findAllLeaveCancel(@Param("begin") int begin);

	/** 查询所有销假记录数量 */
	public int countLeaveCancel();

	public LeaveCancel findLeaveCancelById(int id);

	public void addLeaveCancel(LeaveCancel leaveCancel);

	public void updateLeaveCancel(LeaveCancel leaveCancel);

	public Leave findById(int id);

	public void addLeave(Leave leave);

	public void updateLeave(Leave leave);

	public void updateLeaveForCancel(Leave leave);

	public List<Leave> findLeaveByUserAndTime(@Param("uid") int uid, @Param("time") String time);

	// 获取用户年假
	public Integer getEmpNianjia(int empId);

	public void setEmpNianjia(@Param("empId") int empId, @Param("nianjia") int minutes);

	// 添加年假调休
	public void addNianjian(@Param("empId") int empId, @Param("nianjia") int nianjia);

	// 添加一个审批记录
	public void addSpRecord(@Param("empId") int empId, @Param("dailiId") Integer dailiId, @Param("tid") int tid,
			@Param("createTime") String createTime);

	// 查询用户在这段时间内是否请过假
	public List<Leave> findIsBeenLeave(@Param("empId") int empId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);

	// 查询用户在这两个时间，是否有请假（所有没有被审批结束的，还有审批通过的）用于判断连续请假
	public List<Leave> findByEmpAndTime(@Param("empId") int empId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);

	public List<Leave> findBeenLeaveByTimeAndEmp(@Param("empId") int empId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime);

	/*
	 * 根据请假类型：出差/请假，查询该用户该事件段内的请假记录
	 */
	public List<Leave> findLeaveByType(@Param("empId") int empId, @Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("waichu") int waichu);
	
	public List<Leave> findLeaveByDate(@Param("beginTime") String beginTime,
			@Param("endTime") String endTime, @Param("waichu") int waichu);
	
	/*
	 * 根据注销类型查找该id下的注销申请
	 */
	public List<LeaveCancel> findMyLeaveCancelSpByType(@Param("id") int id, @Param("type")int type);
	
	/** 根据类型查询所有注销记录 */
	public List<LeaveCancel> findLeaveCancelByType(@Param("begin") int begin, @Param("waichu") int waichu);
	
	/** 根据类型查询所有注销记录数量 */
	public int countLeaveCancelByType(@Param("type") int type);
	
	public void cancelLeaveById(Integer id);
}
