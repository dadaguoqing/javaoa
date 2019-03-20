package com.hj.oa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.DaiLiSP;
import com.hj.oa.dao.DaiLiSPMapper;
import com.hj.oa.dao.RoleMapper;

@Service
public class DaiLiSPService {

	@Autowired
	private DaiLiSPMapper daiLiSPMapper;
	@Autowired
	private RoleService roleService;
	
	public void add(DaiLiSP sp) {
		daiLiSPMapper.add(sp);
	}

	public DaiLiSP findById(int id) {
		return daiLiSPMapper.findById(id);
	}

	public List<DaiLiSP> findMySP() {
		return daiLiSPMapper.findMySP();
	}

	public List<DaiLiSP> findMine(int empId) {
		return daiLiSPMapper.findMine(empId);
	}
	
	public List<DaiLiSP> findMineAtSP(int id){
		return this.daiLiSPMapper.findMineAtSP(id);
	}
	
	//查出所有与我相关的，我申请代理的，申请要求我代理的，审批通过后的。并且未添加实际代理的
	public List<DaiLiSP> findByEmpIdForAddDaili(int empId){
		return this.daiLiSPMapper.findByEmpIdForAddDaili(empId);
	}
	
	//查出所有与我相关的，我申请代理的，申请要求我代理的，审批通过后的。已经添加过实际代理的。准备删除
	public List<DaiLiSP> findByEmpIdForDeleteDaili(int empId){
		return this.daiLiSPMapper.findByEmpIdForDeleteDaili(empId);
	}
	
	//自动添加代理
	public void addDailiBySp(List<DaiLiSP> sps){
		
		for(DaiLiSP sp : sps){
			String ms = sp.getMids();
			String[] ss = ms.split(",");
			List<Integer> ml = new ArrayList<Integer>();
			
			for (int i = 0; i < ss.length; i++) {
				String s = ss[i];
				ml.add(Integer.parseInt(s));
			}
			
			sp.setLifeStatus(1);
			
			daiLiSPMapper.update(sp);
			roleService.addDaili(sp.getUid(), ml, sp.getSenderId());
		}
	}
	
	//自动解除代理
	public void deleteDailiBySp(List<DaiLiSP> sps){
		
		for(DaiLiSP sp : sps){
			sp.setLifeStatus(-1);
			
			daiLiSPMapper.update(sp);
			roleService.deleteDaili(sp.getSenderId());
		}
	}
	
	/**
	 * @Title: findMyApply   
	 * @Description: 我的代理审批记录
	 * @param: @param id
	 * @param: @return      
	 * @return: List<DaiLiSP>      
	 * @throws
	 */
	public List<DaiLiSP> findMyApply(Integer id) {
		return this.daiLiSPMapper.findMyApply(id);
	}

	public void update(DaiLiSP sp, RoleService roleService) {
		daiLiSPMapper.update(sp);

		if (sp.getStatus() != 1) {// 审核不通过
			return;
		}
		
		/*
		String ms = sp.getMids();
		String[] ss = ms.split(",");
		List<Integer> ml = new ArrayList<Integer>();
		
		for (int i = 0; i < ss.length; i++) {
			String s = ss[i];
			ml.add(Integer.parseInt(s));
		}
		
		roleService.addDaili(sp.getUid(), ml, sp.getSenderId());
		*/
	}
}
