package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.SpRecord;

public interface SpRecordMapper {

	//添加一个审批记录
	public void addSpRecord(@Param("empId") int empId,@Param("dailiId") Integer dailiId,
			@Param("type") int type, @Param("tid") int tid, @Param("createTime") String createTime);
	//根据审批人已经审批类型查询审批记录
	public List<SpRecord> findSpRecordByEmpAndType(@Param("empId") int empId, @Param("type") int type);
	
	public SpRecord findSpRecordByEmpAndSpId( @Param("empId") int empId, @Param("tid") int tid);
}
