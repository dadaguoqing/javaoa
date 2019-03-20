package com.hj.oa.bean;

import java.io.Serializable;
import java.util.Calendar;

public class Day implements Serializable {
	
	public Day(){}
	
	public Day(boolean isToday){
		Calendar c = Calendar.getInstance();
		this.day = c.get(Calendar.DATE);
		this.year = c.get(Calendar.YEAR);
		this.month = c.get(Calendar.MONTH) + 1;
	}

	@Override
	public String toString() {
		return "Day [day=" + day + ", month=" + month  +  ", type=" + type + ", dayStr=" + dayStr 
				+ ", dayStrChina=" + dayStrChina + ", year=" + year + "]";
	}
	private static final long serialVersionUID = 8568272340570209931L;
	
	private String dayStr;
	private String dayStrChina;
	private int year;
	private int month;
	private int day;
	private int dayOfWeek;
	private String type;
	
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setDayOfWeek(int dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public int getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getDayStr() {
		return this.dayStr;
	}

	public void setDayStrChina(String dayStrChina) {
		this.dayStrChina = dayStrChina;
	}

	public String getDayStrChina() {
		return dayStrChina;
	}
	
}
