package com.hj.oa.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.LeaveDayDetail;
import com.hj.oa.bean.NianjiaRecord;
import com.hj.oa.dao.OAUtilMapper;

@Service
public class OAUtilService {
	
	@Autowired
	private OAUtilMapper oaMapper;
	
	//请假获得批准之后，添加员工每日请假明细记录
	public void addLeaveDayDetail(List<LeaveDayDetail> list){
		
	}
	
	public List<NianjiaRecord> findHistoryRecod(){
		return oaMapper.findHistoryRecod();
	}
	
	//年假损益
	public void nianjiaSy(EmpNianjia nj, NianjiaRecord nr){
		oaMapper.updateEmpNianjia(nj);
		List<NianjiaRecord> list = new ArrayList<NianjiaRecord>();
		list.add(nr);
		oaMapper.addNianjiaRecord(list);
	}
	
	public void nianjiaClear(List<EmpNianjia> njs, List<NianjiaRecord> records){
		for(EmpNianjia nj : njs){
			oaMapper.updateEmpNianjia(nj);
		}
		oaMapper.addNianjiaRecord(records);
	}
	
	public void updataNianjia(Collection<EmpNianjia> list){
		
		for(EmpNianjia nj : list){
			this.oaMapper.updateEmpNianjia(nj);
		}
	}
	
	//添加年假或者病假消除记录
	public void addNianjiaRecord(List<NianjiaRecord> list){
		this.oaMapper.addNianjiaRecord(list);
	}
	
	public void addEmpNianjia(List<EmpNianjia> list){
		oaMapper.addEmpNianjia(list);
	}
	
	public void addAnEmpNianjia(EmpNianjia nj){
		oaMapper.addAnEmpNianjia(nj);
	}
	
	public void addAnEmpNianjiaBak(List<EmpNianjia> njs){
		for(EmpNianjia nj : njs)
			oaMapper.addAnEmpNianjiaBak(nj);
	}
	
	public List<NianjiaRecord> findNianjiaRecordByEmpId(int empId, String type){
		return oaMapper.findNianjiaRecordByEmpId(empId, type);
	}
	
	public List<NianjiaRecord> findAllNianjiaRecord(){
		return oaMapper.findAllNianjiaRecord();
	}
	
	public EmpNianjia getEmpNianjiaById(int empId){
		return oaMapper.getEmpNianjiaById(empId);
	}
	
	public List<EmpNianjia> findAllNianjia(){
		return this.oaMapper.findAllNianjia();
	}

	public List<EmpNianjia> findAllNianjia2(){
		return this.oaMapper.findAllNianjia2();
	}
	
	public List<EmpNianjia> findAllNianjiaByDept(String depts){
		return this.oaMapper.findAllNianjiaByDept(depts);
	}
	
	public List<EmpNianjia> findAllNianjiaByTechDept(){
		return this.oaMapper.findAllNianjiaByTechDept();
	}
}
