package com.hj.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.LabAll;
import com.hj.oa.bean.LabGw;
import com.hj.oa.bean.LabHj;
import com.hj.oa.bean.LabPcb;
import com.hj.oa.bean.LabPcbSq;
import com.hj.oa.controller.PropertyController;
import com.hj.oa.dao.LabMapper;
import com.hj.util.DateUtil;

@Service
public class LabService {

	@Autowired
	LabMapper labMapper;
	@Autowired
	private PropertyService propService;
	
	public void addPcbSq(LabPcbSq sq, LabPcb pcb){
		this.labMapper.addPcbSq(sq);
		pcb.setSqId(sq.getId());
		this.labMapper.addPcb(pcb);
	}
	
	public void addAllSq(LabPcbSq sq, LabAll all){
		this.labMapper.addPcbSq(sq);
		all.setSqId(sq.getId());
		this.labMapper.addAll(all);
	}
	
	
	public LabPcbSq findPcbSqById(int id){
		return this.labMapper.findPcbSqById(id);
	}
	
	public LabPcb findPcbBySqId(int sqId){
		return this.labMapper.findPcbBySqId(sqId);
	}
	
	
	public LabAll findPcbBySqId2(int sqId){
		return this.labMapper.findPcbBySqId2(sqId);
	}
	
	public LabAll findLabAllBySqId(int sqId){
		return this.labMapper.findLabAllBySqId(sqId);
	}
	
	public LabPcb findPcbByFileName(String fileName){
		return this.labMapper.findPcbByFileName(fileName);
	}
	
	public void updatePcbSq(LabPcbSq sq){
		this.labMapper.updatePcbSq(sq);
		
	}
	
	//  生成订单号
	public void updatePcbBySqId(LabPcbSq sq){
		LabPcb pcb = this.labMapper.findPcbBySqId(sq.getId());
		if(sq.getType() == 1){
			Integer codeSeq = PropertyController.getCode("pbCode", propService);
			String code = "PCB" + DateUtil.getCurrentTime("yyyyMMdd") +  String.format("%03d", codeSeq);
			pcb.setDdbh(code);
		}else if(sq.getType() == 2){
			Integer codeSeq = PropertyController.getCode("gwCode", propService);
			String code = "GW" + DateUtil.getCurrentTime("yyyyMMdd") +  String.format("%03d", codeSeq);
			pcb.setDdbh(code);
		}
	}
	//  生成订单号
	public void updatePcbBySqId2(LabPcbSq sq){
		LabAll pcb = this.labMapper.findPcbBySqId2(sq.getId());
			Integer codeSeq = PropertyController.getCode("pbCode", propService);
			String code = "WX" + DateUtil.getCurrentTime("yyyyMMdd") +  String.format("%03d", codeSeq);
			pcb.setDdbh(code);
		this.labMapper.updatePcb2(pcb);
	}
	
	public List<LabPcbSq> findMyPcbSp(int empId, int type){//我的审批
		return this.labMapper.findMyPcbSp(empId, type);
	}
	
	public List<LabPcbSq> findMyPcbSp2(int empId){//我的审批
		return this.labMapper.findMyPcbSp2(empId);
	}
	
	public List<LabPcbSq> findMyPcbSq(int empId, int type){ //我的申请记录
		return this.labMapper.findMyPcbSq(empId, type);
	}
	public List<LabPcbSq> findMyPcbSq2(int empId){ //我的申请记录
		return this.labMapper.findMyPcbSq2(empId);
	}
	
	public List<LabPcbSq> findAllPcbSq(Integer jgStatus, int type){
		return this.labMapper.findAllPcbSq(jgStatus, type);
	}
	public List<LabPcbSq> findAllPcbSq2(Integer jgStatus){
		return this.labMapper.findAllPcbSq2(jgStatus);
	}
	
	public List<LabPcbSq> findPcbSqBySpEmpId(Integer empId, int type){
		return this.labMapper.findPcbSqBySpEmpId(empId, type);
	}
	
	/**
	 * 添加pcb加工信息
	 * @param all
	 * @param pcb
	 */
	public void addPcbPro(LabAll all, LabPcb pcb){
		all.setSqId(pcb.getSqId());
		all.setGyyq(pcb.getGyyq());
		all.setDdbh(pcb.getDdbh());
		all.setNumSet(pcb.getNumSet());
		all.setNumUnit(pcb.getNumUnit());
		all.setCs(pcb.getCs());
		all.setCcChang(pcb.getCcChang());
		all.setCcKuang(pcb.getCcKuang());
		all.setCpbh(pcb.getCpbh());
		all.setCl(pcb.getCl());
		all.setTbNei(pcb.getTbNei());
		all.setTbWai(pcb.getTbWai());
		all.setZh(pcb.getZh());
		all.setZhColor(pcb.getZhColor());
		all.setZf(pcb.getZf());
		all.setZfColor(pcb.getZfColor());
		all.setCstd(pcb.getCstd());
		all.setCstdType(pcb.getCstdType());
		all.setWxjgfs(pcb.getWxjgfs());
		all.setJszyq(pcb.getJszyq());
		all.setDcbg(pcb.getDcbg());
		all.setZkcsbg(pcb.getZkcsbg());
		all.setCpjcbg(pcb.getCpjcbg());
		all.setFgzh(pcb.getFgzh());
		all.setBmtc(pcb.getBmtc());
		all.setBmtchd(pcb.getBmtchd());
		all.setKzms(pcb.getKzms());
		all.setBz(pcb.getBz());
	}
	/**
	 * 添加焊接加工信息
	 * @param all
	 * @param hj
	 */
	public void addHjPro(LabAll all, LabHj hj){
		all.setWeldType(hj.getWeldType());
		if(hj.getWeldType()==2){
			all.setNum(hj.getNum());
			all.setPiles(hj.getPiles());
			all.setPaster(hj.getPaster());
			all.setPaster2(hj.getPaster2());
			all.setGyType(hj.getGyType());
			all.setBGAType(hj.getBGAType());
			all.setHjgy(hj.getHjgy());
		}
		if(hj.getBGAType()==2){
			all.setMaxSize(hj.getMaxSize());
			all.setJu(hj.getJu());
			all.setXzzj(hj.getXzzj());
		}
	} 
	
	/**
	 * 添加钢网加工
	 * @param all
	 * @param gw
	 */
	public void addGwPro(LabAll all, LabGw gw){
		all.setNumSet2(gw.getNumSet2());
		all.setCcChang2(gw.getCcChang2());
		all.setCpbh2(gw.getCpbh2());
		all.setCl2(gw.getCl2());
		all.setBmtc2(gw.getBmtc2());
		all.setBmtchd2(gw.getBmtchd2());
	} 
	
	
}
