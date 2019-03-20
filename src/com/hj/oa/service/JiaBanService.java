package com.hj.oa.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.Consts;
import com.hj.oa.bean.EmpNianjia;
import com.hj.oa.bean.JiaBan;
import com.hj.oa.bean.NianjiaRecord;
import com.hj.oa.bean.User;
import com.hj.oa.dao.JiaBanMapper;
import com.hj.oa.dao.OAUtilMapper;
import com.hj.oa.dao.SpRecordMapper;
import com.hj.util.DateUtil;
import com.hj.util.ExcelUtil;

@Service
public class JiaBanService {

	@Autowired
	private JiaBanMapper jiabanMapper;
	@Autowired
	private OAUtilMapper oaUtilMapper;
	@Autowired
	private SpRecordMapper spRecordMapper;
	
	public void addJiaban(JiaBan jiaban){
		this.jiabanMapper.addJiaBan(jiaban);
	}
	
	//导入加班记录
	public void importJiaban(List<File> list, Map<String,User> uMap){
		try{
			for(File xsl : list){
				List<JiaBan> jbs = ExcelUtil.getJiabanFromFile(new FileInputStream(xsl), uMap);
				addJiaban(jbs);
			}
			
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	//导入加班记录
	public void addJiaban(List<JiaBan> list){
		
		for(JiaBan jb : list){
			this.jiabanMapper.addJiaBan(jb);
			this.jiabanMapper.addJianBanRecord(jb.getProposer(), jb.getId(), jb.getDayte(), jb.getHours()*60);
		}
		
	}
	
	
	public JiaBan findByEmpAndDayStr(int empId,String dayStr){
		return jiabanMapper.findByEmpAndDayStr(empId, dayStr);
	}
	
	public List<JiaBan> findMySp(int id){
		return this.jiabanMapper.findMySp(id);
	}
	
	public List<JiaBan> findByEmpId(int id){
		return this.jiabanMapper.findByEmpId(id);
	}
	
	public JiaBan findById(int id){
		return this.jiabanMapper.findById(id);
	}
	
	public void updateJiaBan(JiaBan jiaban, int currentSpId, Integer dailiId){
		//添加审批记录
		spRecordMapper.addSpRecord( currentSpId, dailiId, Consts.type_jb, 
				jiaban.getId(), DateUtil.getCurrentTime("yyyy年MM月dd日 HH时mm分"));
		
		this.jiabanMapper.updateJiaBan(jiaban);
	}
	
	public void updateSth(JiaBan jiaban, int currentSpId, Integer dailiId){
		try{
			int empId = jiaban.getProposer();
			int timeLen = jiaban.getHours() * 60;
			//1，添加年假、调休记录
			EmpNianjia nianjia = oaUtilMapper.getEmpNianjiaById(empId);
			int nj = nianjia.getNianjia();
			nj += timeLen;
			nianjia.setNianjia(nj);
			oaUtilMapper.updateEmpNianjia(nianjia);
			
			List<NianjiaRecord> list = new ArrayList<NianjiaRecord>();
			NianjiaRecord njr = new NianjiaRecord();
			njr.setBz("加班增加调休时间");
			njr.setDayStr(DateUtil.getCurrentTime("yyyy年MM月dd日"));
			njr.setEmpId(empId);
			njr.setTimeLeft(nj);
			njr.setTimeLen( -timeLen );
			njr.setType("年假");
			njr.setLeaveId(jiaban.getId());
			list.add(njr);
			
			this.oaUtilMapper.addNianjiaRecord(list);
			this.jiabanMapper.addJianBanRecord(empId, jiaban.getId(), jiaban.getDayte(), timeLen);
			this.updateJiaBan(jiaban, currentSpId, dailiId);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
}
