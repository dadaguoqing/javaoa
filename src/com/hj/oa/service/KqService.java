package com.hj.oa.service;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.EmpDayVo;
import com.hj.oa.bean.ParamBean;
import com.hj.oa.dao.KqMapper;
@Service
public class KqService {

	@Autowired
	private KqMapper kqMapper;
	
	public List<EmpDayVo> findByEmpAndDay(int empId, String begin, String end, String orderType){
		if(StringUtils.isEmpty(orderType)){
			orderType = "";
		}
		return this.kqMapper.findByEmpAndDay(empId, begin, end, orderType);
	}
	
	public List<EmpDayVo> findUnEmpDay(String begin, String end){
		return this.kqMapper.findUnEmpDay(begin, end);
	}
	
	public List<BKqSp> findRetroactiveRecord(ParamBean pb){
		return this.kqMapper.findRetroactiveRecord(pb);
	}
	
	public List<HashMap> findByEmpAndDayHuiZong(int empId, String begin, String end){
		return this.kqMapper.findByEmpAndDayHuiZong(empId, begin, end);
	}
	
	public List<EmpDayVo> findByEmpAll(int empId){
		return this.kqMapper.findByEmpAll(empId);
	}
	
	public List<EmpDayVo> findByEmpAndDayAll(String begin, String end){
		return this.kqMapper.findByEmpAndDayAll(begin, end);
	}
	
	public List<EmpDayVo> findEmpAndDayById(String begin, String end, int empId){
		return this.kqMapper.findEmpAndDayById(begin, end, empId);
	}
	
	public List<EmpDayVo> findSumAll( String begin, String end){
		return this.kqMapper.findSumAll(begin, end);
	}
	
	public List<EmpDayVo> findByDayAndDeptSum( int deptId, String begin, String end ){
		return this.kqMapper.findByDayAndDeptSum( deptId, begin, end);
	}
	
	public void addBKqSp(BKqSp sp){
		String[] DateStr = sp.getDayStr().split(",");
		String[] beginStr = sp.getCheckin().split(",");
		String[] endStr = sp.getCheckout().split(",");
		for(int i = 0 ;i<DateStr.length;i++){
			sp.setDayStr(DateStr[i]);
			sp.setCheckin(beginStr[i]);
			sp.setCheckout(endStr[i]);
			kqMapper.addBKqSp(sp);
		}
	}
	
	public void updateBKqSp(BKqSp sp){
		kqMapper.updateBKqSp(sp);
	}
	
	public void insertBKqSp(BKqSp sp){
		kqMapper.insertBKqSp(sp);
	}
	
	public BKqSp findBKqById(int id){
		return kqMapper.findBKqById(id);
	}
	
	public List<BKqSp> findMySp(int empId){
		return kqMapper.findMySp(empId);
	}
	
	public List<CheckIn> findCheckInByEmpAndDay(CheckIn ci){
		return kqMapper.findCheckInByEmpAndDay(ci);
	}
	
	
	public List<BKqSp> findMySq(int empId){
		return kqMapper.findMySq(empId);
	}
	
	public List<BKqSp> findBKqSq(String style,int curpage){
		return kqMapper.findBKqSq(style,(curpage-1)*20);
	}
	
	public List<BKqSp> findMySpRecord(int empId){
		return kqMapper.findMySpRecord(empId);
	}
	/**
	 * 计算总页数
	*2017年6月8日
	 * @return
	 */
	public int countBkqSq(String style) {
		int count = kqMapper.countBKqSq(style);
		if(count%20==0){
			return count/20;
		}else{
			return count/20+1;
		}
	}
	
//	public List<EmpDayVo> 
}
