package com.hj.oa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.Gift;
import com.hj.oa.bean.GiftEvent;
import com.hj.oa.bean.User;
import com.hj.oa.dao.GiftMapper;


@Service
public class GiftService {
	
	@Autowired
	GiftMapper giftMapper;
	
	public void addEvent(GiftEvent evt){
		giftMapper.addEvent(evt);
	}
	
	public List<GiftEvent> findEvents(Integer status){
		return giftMapper.findEvents(status);
	}
	
	public List<GiftEvent> findEmpEvents(String time){
		return giftMapper.findEmpEvents(time);
	}
	
	public GiftEvent getEventById(int id){
		return giftMapper.getEventById(id);
	}
	
	public Integer getEmpTodo(Integer empId, String time){
		return this.giftMapper.getEmpTodo(empId, time);
	}
	
	public void updateEvent(GiftEvent evt){
		giftMapper.updateEvent(evt);
	}
	
	public void addGift(Gift gift){
		giftMapper.addGift(gift);
	}
	
	public void updateGift(Gift gift){
		giftMapper.updateGift(gift);
	}
	
	public void saveEmpGift(Gift og, int empId, int giftId, int evtId){
		giftMapper.delEmpGift(empId, evtId);//删除之前的选择
		
		if(null!=og){
			int om = og.getNum();
			og.setNum(om-1);
			this.giftMapper.updateGift(og);
		}
		
		giftMapper.saveEmpGift(empId, giftId, evtId);
		Gift gift = this.giftMapper.getById(giftId);
		int num = gift.getNum();
		gift.setNum(num+1);
		
		this.giftMapper.updateGift(gift);
	}
	
	/**
	 * gift 选择的详情
	 * @param giftId
	 * @return
	 */
	public List<Gift> findByGift(int giftId){
		return giftMapper.findByGift(giftId);
	}
	
	public List<Gift> getAllGift(int evtId){
		return giftMapper.getAllGift(evtId);
	}
	
	public Gift getByEmp(int empId, int evtId){
		return giftMapper.getByEmp(empId, evtId);
	}
	
	public Gift getById(int id){
		return giftMapper.getById(id);
	}
	
	public void delGift(int id){
		this.giftMapper.delGift(id);
	}
	
	public List<User> getUnEmps(int evtId){
		return giftMapper.getUnEmps(evtId);
	}
	
}
