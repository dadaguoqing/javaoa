package com.hj.oa.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hj.oa.bean.Day;

public interface DateMapper {
	
	public Day findByDayStr(String dayStr);
	
	public Day findByChinaDayStr(String dayStrChina);

	public List<Day> findByMonth(Day day);
	
	public void addDate(Day day);
	
	public List<Day> findDays(@Param("beginTime") String beginTime, @Param("endTime") String endTime);
	
	public void updateDate(Day day);
	
	public Day isHoliday(String date);
}
