package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.LabAll;
import com.hj.oa.bean.LabPcb;
import com.hj.oa.bean.LabPcbSq;

public interface LabMapper {
	
	public void addPcbSq(LabPcbSq sq);
	
	public void addPcb(LabPcb pcb);
	
	public void addAll(LabAll all);
	
	public LabPcbSq findPcbSqById(int id);
	
	public LabPcb findPcbBySqId(int sqId);
	
	public LabAll findPcbBySqId2(int sqId);
	
	public LabAll findLabAllBySqId(int sqId);
	
	public void updatePcbSq(LabPcbSq sq);
	
	public void updatePcb(LabPcb pcb);
	
	public void updatePcb2(LabAll pcb);
	
	public List<LabPcbSq> findMyPcbSp(@Param("empId") int empId, @Param("type") int type);//我的审批
	
	public List<LabPcbSq> findMyPcbSp2(@Param("empId") int empId);//我的审批
	
	public List<LabPcbSq> findMyPcbSq(@Param("empId") int empId, @Param("type") int type); //我的申请记录
	
	public List<LabPcbSq> findMyPcbSq2(@Param("empId") int empId); //我的申请记录
	
	public List<LabPcbSq> findAllPcbSq(@Param("jgStatus") Integer jgStatus, @Param("type") int type);
	
	public List<LabPcbSq> findAllPcbSq2(@Param("jgStatus") Integer jgStatus);
	
	public List<LabPcbSq> findPcbSqBySpEmpId(@Param("empId") Integer empId, @Param("type") int type);
	
	public LabPcb findPcbByFileName(String fileName);
}
