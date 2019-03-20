package com.hj.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.EmpDay;
import com.hj.oa.dao.EmpDayMapper;

/**
 * 逻辑关系比较复杂，谨慎处理
 * @author L
 *
 */
@Service
public class EmpDayService {
	
	@Autowired
	private EmpDayMapper edMapper;

	/**
	 * 添加一个用户工作日考勤详情，添加的同时必须计算相关参数。
	 * @param empDay
	 */
	public void addEmpDay(EmpDay empDay){
		edMapper.addEmpDay(empDay);
	}
	
	public void addEmpDay(List<EmpDay> eds){
		for(EmpDay ed : eds){
			this.addEmpDay(ed);
		}
	}
	
	public EmpDay findByEmpIdAndDay(String dayStr, int empId){
		return this.edMapper.findByEmpIdAndDay(dayStr, empId);
	}
	
	public void addUpdate(List<EmpDay> adds, List<EmpDay> upds){
		this.addEmpDay(adds);
		this.updateEmpDay(upds);
	}
	
	public List<EmpDay> findByEmpIdAndDayStr(String begin, String end, int empId){
		return edMapper.findByEmpIdAndDayStr(begin, end, empId);
	}
	
	public EmpDay findByDayStr(String begin, String end){
		return edMapper.findByDayStr2(begin, end);
	}
	
	public List<EmpDay> findByDaySum(String begin, String end){
		return edMapper.findByDaySum(begin, end);
	}
	
	public List<EmpDay> findByDayAndDeptSum(String begin, String end, int deptId){
		return edMapper.findByDayAndDeptSum(begin, end, deptId);
	}
	
	public EmpDay findByDayStrAndDept(String begin, String end, int deptId){
		return edMapper.findByDayStrAndDept(begin, end, deptId);
	}
	
	public List<EmpDay> findByDayStr(String dayStr){
		return edMapper.findByDayStr(dayStr);
	}
	
	public void updateEmpDay(EmpDay empDay){
		edMapper.updateEmpDay(empDay);
	}
	
	public void updateEmpDay(List<EmpDay> eds){
		for(EmpDay ed : eds){
			this.updateEmpDay(ed);
		}
	}
}
