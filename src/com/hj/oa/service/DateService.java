package com.hj.oa.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hj.oa.bean.Day;
import com.hj.oa.dao.DateMapper;

@Service
public class DateService {

	@Autowired
	DateMapper dateMapper;

	public Day findByDayStr(String dayStr) {
		return dateMapper.findByDayStr(dayStr);
	}

	public Day findByDayStrChina(String dayStr) {
		return dateMapper.findByChinaDayStr(dayStr);
	}

	public List<Day> setByMonth(Day day) {
		List<Day> list = null;

		list = dateMapper.findByMonth(day);

		if (list == null || list.isEmpty()) {
			list = makeMonth(day);
			addDate(list);
		}

		// list = dateMapper.findByMonth(day);

		return list;
	}

	private List<Day> makeMonth(Day day) {
		List<Day> list = new ArrayList<Day>();

		int i = 1;
		int mon = day.getMonth();
		int year = day.getYear();
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, mon - 1);
		c.set(Calendar.DATE, i);

		while (c.get(Calendar.MONTH) == (mon - 1)) {

			Day d = new Day();
			d.setYear(year);
			d.setMonth(mon);
			d.setDay(i);

			String mo = mon < 10 ? ("0" + mon) : (mon + "");
			String da = i < 10 ? ("0" + i) : (i + "");
			d.setDayStr(year + "-" + mo + "-" + da);
			d.setDayStrChina(year + "年" + mo + "月" + da + "日");

			int zj = c.get(Calendar.DAY_OF_WEEK);

			switch (zj) {
			case Calendar.SUNDAY:
				d.setType("节假日");
				d.setDayOfWeek(7);
				break;
			case Calendar.MONDAY:
				d.setType("工作日");
				d.setDayOfWeek(1);
				break;
			case Calendar.TUESDAY:
				d.setType("工作日");
				d.setDayOfWeek(2);
				break;
			case Calendar.WEDNESDAY:
				d.setType("工作日");
				d.setDayOfWeek(3);
				break;
			case Calendar.THURSDAY:
				d.setType("工作日");
				d.setDayOfWeek(4);
				break;
			case Calendar.FRIDAY:
				d.setType("工作日");
				d.setDayOfWeek(5);
				break;
			case Calendar.SATURDAY:
				d.setType("节假日");
				d.setDayOfWeek(6);
				break;
			}
			list.add(d);

			i++;
			c.set(Calendar.DATE, i);
		}

		return list;
	}

	public void addDate(List<Day> list) {
		for (Day d : list) {
			dateMapper.addDate(d);
		}
	}

	/*
	 * public void addDate(Day day){ dateMapper.addDate(day); }
	 */

	public List<Day> findDays(String beginTime, String endTime) {
		return dateMapper.findDays(beginTime, endTime);
	}

	public void updateDate(List<Day> list) {
		for (Day d : list) {
			dateMapper.updateDate(d);
		}
	}

	public void updateDate(Day day) {
		dateMapper.updateDate(day);
	}

	/**
	 * 设置日期为前n个工作日
	 * 
	 * @param cal
	 * @param n
	 */
	public Calendar setCalDate(Calendar cal, int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String date = null;
		while (n > 0) {
			cal.add(Calendar.DATE, -1);
			date = sdf.format(cal.getTime());
			Day day = this.dateMapper.isHoliday(date);
			n = day.getType().equals("节假日") ? n : n - 1;
		}
		return cal;
	}
	
	
	/**
	 * 设置日期为前n个工作日
	 * 
	 * @param cal
	 * @param n
	 */
	public Calendar addWorkDate(Calendar cal, int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		String date = null;
		while (n > 0) {
			cal.add(Calendar.DATE, 1);
			date = sdf.format(cal.getTime());
			Day day = this.dateMapper.isHoliday(date);
			n = day.getType().equals("节假日") ? n : n - 1;
		}
		return cal;
	}
}
