package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Gift;
import com.hj.oa.bean.GiftEvent;
import com.hj.oa.bean.User;

public interface GiftMapper {
	
	//活动相关
	public void addEvent(GiftEvent evt);
	
	public List<GiftEvent> findEvents(Integer status);
	
	public List<GiftEvent> findEmpEvents(@Param("time") String time);
	
	public GiftEvent getEventById(int id);
	
	public void updateEvent(GiftEvent evt);
	
	public void addGift(Gift gift);
	
	public void updateGift(Gift gift);
	
	public void delGift(int id);
	
	public void delEmpGift(@Param("empId") int empId, @Param("eventId") int eventId);

	public void saveEmpGift(@Param("empId") int empId, @Param("giftId") int giftId, @Param("eventId") int eventId);
	
	public List<Gift> findByGift(@Param("giftId") int giftId);
	
	public List<Gift> getAllGift(int eventId);
	
	public Gift getByEmp(@Param("empId") int empId, @Param("eventId") int eventId);
	
	public Integer getEmpTodo(@Param("empId") int empId, @Param("time") String time);
	
	public Gift getById(int id);
	
	public List<User> getUnEmps(@Param("eventId") int eventId);
	
}
