package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.DaiLiSP;

public interface DaiLiSPMapper {
	
	public void add(DaiLiSP sp);
	
	public DaiLiSP findById(int id);
	
	public List<DaiLiSP> findMySP();
	
	public List<DaiLiSP> findMine(int id);
	
	public List<DaiLiSP> findMineAtSP(int id);
	
	//查出所有与我相关的，我申请代理的，申请要求我代理的，审批通过后的。
	public List<DaiLiSP> findByEmpIdForAddDaili(int empId);
	
	//查出所有与我相关的，我申请代理的，申请要求我代理的，审批通过后的。
	public List<DaiLiSP> findByEmpIdForDeleteDaili(int empId);
	
	public void update(DaiLiSP sp);
	
	public List<DaiLiSP> findMyApply(@Param("senderId") Integer senderId);
}
