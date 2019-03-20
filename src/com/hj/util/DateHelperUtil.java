package com.hj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateHelperUtil {
	public final static String FMT = "yyyy年MM月dd日";
	public final static String FMT1 = "yyyy年MM月dd日HH时mm分";
	public static List<String> getDates(String beginDay, String endDay) throws ParseException{
		
		List<String> dateList = new ArrayList<String>();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date beginDate = sdf.parse(beginDay);
		cal.setTime(beginDate);
		Date endDate = sdf.parse(endDay);
		while(cal.getTime().before(endDate)){
			String formated = sdf.format(cal.getTime());
			dateList.add(formated);
			cal.add(Calendar.DATE, 1);
		}
		
		String formated = sdf.format(cal.getTime());
		dateList.add(formated); 
		
		return dateList;
	}
	
	public static List<String> getChineseDates(List<String> dates){
		List<String> dateList = new ArrayList<String>();
		
		for (String curDay: dates){
			String newDay = curDay.substring(0,4);
			newDay = newDay+"年";
			newDay = newDay+curDay.substring(5,7);
			newDay = newDay+"月";
			newDay = newDay + curDay.substring(8,10);
			newDay = newDay + "日";
			
			dateList.add(newDay);
		}
		
		return dateList;
	}

	public static Date stringToDate(String str){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date dateToLong(String str,String fmt){
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
