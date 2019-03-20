package com.hj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.hj.oa.Consts;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.DayLeave;

public class DateUtil {
	
	/**
	 * @Title: getMonthSpace   
	 * @Description: 计算两个日期之间月数
	 * @param: @param date1
	 * @param: @param date2
	 * @param: @return
	 * @param: @throws ParseException      
	 * @return: int      
	 * @throws
	 */
	public static int getMonthSpace(String date1, String date2)
            throws ParseException {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        return result == 0 ? 1 : Math.abs(result);
    }
	
	
	public int countMunitesForLeaveWithHoliday(Date bg, Date ed){

		Calendar bc = Calendar.getInstance();
		bc.setTime(bg);
		
		Calendar ec = Calendar.getInstance();
		ec.setTime(ed);
		
		int h = bc.get(Calendar.HOUR_OF_DAY);
		int m = bc.get(Calendar.MINUTE);
		System.out.println(h + ":" + m);
		if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
			bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
			bc.set(Calendar.MINUTE, Consts.amBeginMinute);
		}else if(h == 12){
			bc.set(Calendar.HOUR_OF_DAY, Consts.pmBeginHour);
			bc.set(Calendar.MINUTE, Consts.pmBeginMinute);
		}else if( h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
			bc.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
			bc.set(Calendar.MINUTE, Consts.pmEndMinute);
		}
		
		h = ec.get(Calendar.HOUR_OF_DAY);
		m = ec.get(Calendar.MINUTE);
		System.out.println(h + ":" + m);
		if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
			ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
			ec.set(Calendar.MINUTE, Consts.amBeginMinute);
		}else if(h==12){
			ec.set(Calendar.HOUR_OF_DAY, Consts.amEndHour);
			ec.set(Calendar.MINUTE, Consts.amEndMinute);
		}else if(h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
			ec.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
			ec.set(Calendar.MINUTE, Consts.pmEndMinute);
		}
		
		System.out.println("bc:"+new Date(bc.getTimeInMillis())+",ec:"+new Date(ec.getTimeInMillis()));
		
		if(bc.compareTo(ec) >=0 ){
			return 0;
		}
		
		int count = 0;
		
		while(true){
			
			int bm = bc.get(Calendar.MONTH);
			int bd = bc.get(Calendar.DAY_OF_MONTH);
			int bh = bc.get(Calendar.HOUR_OF_DAY);
			
			int em = ec.get(Calendar.MONTH);
			int ed2 = ec.get(Calendar.DAY_OF_MONTH);
			int eh = ec.get(Calendar.HOUR_OF_DAY);
			
			if(bm==em && bd==ed2){//同一天了
				if(bc.compareTo(ec) >0 ){
					count += (int)( (ec.getTimeInMillis()-bc.getTimeInMillis())/1000/60);
					if(bh>=13 && eh<=12){
						count += 60;
					}
				}else{
					count += (int)( (ec.getTimeInMillis()-bc.getTimeInMillis())/1000/60);
					if(eh>=13 && bh<=12){
						count += -60;
					}
				}
				
				break;
			}else{
				bc.add(Calendar.DAY_OF_MONTH, 1);
				count += 480;
			}
			
		}
		
		return count;
	}
	
	/**
	 * 计算请假时长（单位：分钟）
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public int countMunitesForLeave(Date bg, Date ed, List<Day> days){
		try{
			/*
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			Date bg = sdf.parse(beginTime);
			Date ed = sdf.parse(endTime);
			*/
			
			Calendar bc = Calendar.getInstance();
			bc.setTime(bg);
			
			Calendar ec = Calendar.getInstance();
			ec.setTime(ed);
			
			int h = bc.get(Calendar.HOUR_OF_DAY);
			int m = bc.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h == 12){
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmBeginHour);
				bc.set(Calendar.MINUTE, Consts.pmBeginMinute);
			}else if( h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				bc.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			
			h = ec.get(Calendar.HOUR_OF_DAY);
			m = ec.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h==12){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amEndHour);
				ec.set(Calendar.MINUTE, Consts.amEndMinute);
			}else if(h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				ec.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				ec.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			
			System.out.println("bc:"+new Date(bc.getTimeInMillis())+",ec:"+new Date(ec.getTimeInMillis()));
			
			if(bc.compareTo(ec) >=0 ){
				return 0;
			}
			
			int count = 0;
			
			boolean flag = isHolady(days, bc);
			
			if(flag){
				bc.add(Calendar.DAY_OF_MONTH, 1);
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			}
			
			flag = isHolady(days, ec);
			
			if(flag){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}
			
			if(bc.compareTo(ec) >0 ){
				return 0;
			}
			
			while(true){
				
				int bm = bc.get(Calendar.MONTH);
				int bd = bc.get(Calendar.DAY_OF_MONTH);
				int bh = bc.get(Calendar.HOUR_OF_DAY);
				int bmi = bc.get(Calendar.MINUTE);
				
				int em = ec.get(Calendar.MONTH);
				int ed2 = ec.get(Calendar.DAY_OF_MONTH);
				int eh = ec.get(Calendar.HOUR_OF_DAY);
				int emi = ec.get(Calendar.MINUTE);
				
				if(bm==em && bd==ed2){//同一天了
					if(bc.compareTo(ec) >0 ){
						count += (int)( (ec.getTimeInMillis()-bc.getTimeInMillis())/1000/60);
						if(bh>=13 && eh<=12){
							count += 60;
						}
					}else{
						count += (int)( (ec.getTimeInMillis()-bc.getTimeInMillis())/1000/60);
						if(eh>=13 && bh<=12){
							count += -60;
						}
					}
					
					break;
				}else{
					flag = isHolady(days, bc);
					bc.add(Calendar.DAY_OF_MONTH, 1);
					if(!flag){
						count += 480;
					}
				}
				
			}
			
			return count;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 计算请假时长（单位：分钟）
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public int countMunitesForWaichu(Date bg, Date ed, List<Day> days){
		try{
			/*
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			Date bg = sdf.parse(beginTime);
			Date ed = sdf.parse(endTime);
			 */
			
			Calendar bc = Calendar.getInstance();
			bc.setTime(bg);
			
			Calendar ec = Calendar.getInstance();
			ec.setTime(ed);
			
			int h = bc.get(Calendar.HOUR_OF_DAY);
			int m = bc.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h == 12){
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmBeginHour);
				bc.set(Calendar.MINUTE, Consts.pmBeginMinute);
			}else if( h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				bc.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			
			h = ec.get(Calendar.HOUR_OF_DAY);
			m = ec.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h==12){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amEndHour);
				ec.set(Calendar.MINUTE, Consts.amEndMinute);
			}else if(h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				ec.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				ec.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			
			System.out.println("bc:"+new Date(bc.getTimeInMillis())+",ec:"+new Date(ec.getTimeInMillis()));
			
			if(bc.compareTo(ec) >=0 ){
				return 0;
			}
			
			int count = 0;
			
			boolean flag = isHolady(days, bc);
			
			/*if(flag){
				bc.add(Calendar.DAY_OF_MONTH, 1);
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			}
			
			flag = isHolady(days, ec);
			
			if(flag){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}*/
			
			if(bc.compareTo(ec) >0 ){
				return 0;
			}
			
			while(true){
				
				int bm = bc.get(Calendar.MONTH);
				int bd = bc.get(Calendar.DAY_OF_MONTH);
				int bh = bc.get(Calendar.HOUR_OF_DAY);
				int bmi = bc.get(Calendar.MINUTE);
				
				int em = ec.get(Calendar.MONTH);
				int ed2 = ec.get(Calendar.DAY_OF_MONTH);
				int eh = ec.get(Calendar.HOUR_OF_DAY);
				int emi = ec.get(Calendar.MINUTE);
				
				if(bm==em && bd==ed2){//同一天了
					if(bc.compareTo(ec) >0 ){
						count += (int)( (ec.getTimeInMillis()-bc.getTimeInMillis())/1000/60);
						if(bh>=13 && eh<=12){
							count += 60;
						}
					}else{
						count += (int)( (ec.getTimeInMillis()-bc.getTimeInMillis())/1000/60);
						if(eh>=13 && bh<=12){
							count += -60;
						}
					}
					
					break;
				}else{
//					flag = isHolady(days, bc);
					bc.add(Calendar.DAY_OF_MONTH, 1);
//					if(!flag){
						count += 480;
//					}
				}
				
			}
			
			return count;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	//
	public List<DayLeave> getDayLeaves(Date bg, Date ed, List<Day> days, int empId){
		try{
			
			List<DayLeave> list = new ArrayList<DayLeave>();
			
			Calendar bc = Calendar.getInstance();
			bc.setTime(bg);
			
			Calendar ec = Calendar.getInstance();
			ec.setTime(ed);
			
			int h = bc.get(Calendar.HOUR_OF_DAY);
			int m = bc.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h == 12){
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmBeginHour);
				bc.set(Calendar.MINUTE, Consts.pmBeginMinute);
			}else if( h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				bc.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			
			h = ec.get(Calendar.HOUR_OF_DAY);
			m = ec.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h==12){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amEndHour);
				ec.set(Calendar.MINUTE, Consts.amEndMinute);
			}else if(h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				ec.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				ec.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			
			
			
			
			int count = 0;
			
			boolean flag = isHolady(days, bc);
			
			if(flag){
				bc.add(Calendar.DAY_OF_MONTH, 1);
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			}
			
			flag = isHolady(days, ec);
			
			if(flag){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}
			
			System.out.println("bc:"+new Date(bc.getTimeInMillis())+",ec:"+new Date(ec.getTimeInMillis()));
			
			
			while(true){
				
				int by = bc.get(Calendar.YEAR);
				int bm = bc.get(Calendar.MONTH)+1;
				int bd = bc.get(Calendar.DAY_OF_MONTH);
				int bh = bc.get(Calendar.HOUR_OF_DAY);
				int bmi = bc.get(Calendar.MINUTE);
				
				int ey = ec.get(Calendar.YEAR); 
				int em = ec.get(Calendar.MONTH)+1;
				int ed2 = ec.get(Calendar.DAY_OF_MONTH);
				int eh = ec.get(Calendar.HOUR_OF_DAY);
				int emi = ec.get(Calendar.MINUTE);
				
				//System.out.println("bd:"+bd+",ed:"+ed2);
					
				if(by==ey && bm==em && bd==ed2){ //同一天了
					
					flag = isHolady(days, bc);
					if(flag){//如果结束日期还是周末，直接结束
						break;
					}
					
					DayLeave dl = new DayLeave();
					if(count == 0 ){
						dl.setBeginTime( (bh < 10 ? ("0" + bh) : bh) + ":" + (bmi < 10 ? ("0" + bmi) : bmi) );
						dl.setEndTime( (eh < 10 ? ("0" + eh) : eh) + ":" + (emi < 10 ? ("0" + emi) : emi) );
						dl.setBeginTimeInt(bh*60 + bmi);
						dl.setEndTimeInt(eh*60 + emi);
						
					}else{
						bh = 8;
						dl.setBeginTime("08:30");
						dl.setEndTime( (eh < 10 ? ("0" + eh) : eh) + ":" + (emi < 10 ? ("0" + emi) : emi) );
						dl.setBeginTimeInt(510);
						dl.setEndTimeInt(eh*60 + emi);
					}
					
					int tlen = dl.getEndTimeInt() - dl.getBeginTimeInt();
					if(eh>=13 && bh<=12){
						tlen -= 60;
					}
					dl.setTimeLen(tlen);
					
					dl.setDayStr(by+"年"+( bm < 10 ? ("0" + bm) : bm )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
					
					list.add(dl);
					break;
				}else{
					DayLeave dl = new DayLeave();
					flag = isHolady(days, bc);
					bc.add(Calendar.DAY_OF_MONTH, 1);
					if(!flag){
						
						if(count == 0){
							dl.setBeginTime((bh < 10 ? ("0" + bh) : bh) + ":" + (bmi < 10 ? ("0" + bmi) : bmi));
							dl.setEndTime("17:30");
							dl.setBeginTimeInt(bh*60 + bmi);
							dl.setEndTimeInt(1050);
							
							dl.setDayStr(by+"年"+( bm < 10 ? ("0" + bm) : bm )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
							
							int tlen = dl.getEndTimeInt() - dl.getBeginTimeInt();
							if(bh<=12){
								tlen -= 60;
							}
							dl.setTimeLen(tlen);
							
						}else{
							dl.setBeginTime("08:30");
							dl.setEndTime("17:30");
							dl.setBeginTimeInt(510);
							dl.setEndTimeInt(1050);
							
							dl.setDayStr(by+"年"+( bm < 10 ? ("0" + bm) : bm )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
							dl.setTimeLen(480);
						}
						
						list.add(dl);
					}
					
					
				}
				
				count++;
				//System.out.println(count);
				
			}
			
			return list;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private boolean isHolady(List<Day> days, Calendar c){
		if(days == null){
			return false;
		}
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		for(Day d : days){
			if(d.getDay() == day && d.getMonth() == month && d.getYear() == year && Consts.nameOfHoliday.equals(d.getType())){
				return true;
			}
		}
		
		return false;
	}
	
	public static String getCurrentTime(String fmt){
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		Date d = new Date();
		return sdf.format(d);
	}
	
	public static String getTimeString(Date d, String fmt){
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		//Date d = new Date();
		return sdf.format(d);
	}
	public static Date stringToDate(String d, String fmt) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(fmt);
		return sdf.parse(d);
	}
	
	public static void main(String[] args) throws Exception{
		List<Day> days = new ArrayList<Day>();
		Day d = new Day();
		d.setDay(13);
		d.setMonth(4);
		d.setYear(2014);
		d.setType(Consts.nameOfHoliday);
		days.add(d);
		DateUtil du = new DateUtil();
		//System.out.println(du.countMunitesForLeave("2014-04-12 12:30", "2014-04-13 14:30",days));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String beginStr = "2014-04-12 14:30";
		String endStr = "2014-04-13 14:30";
		Date bd = sdf.parse(beginStr);
		Date ed = sdf.parse(endStr);
		System.out.println(du.countMunitesForLeave(bd, ed, days));
	}
	
	
	
	public List<DayLeave> getDayLeaves2(Date bg, Date ed, List<Day> days, int empId){
		try{
			List<DayLeave> list = new ArrayList<DayLeave>();
			Calendar bc = Calendar.getInstance();
			bc.setTime(bg);
			Calendar ec = Calendar.getInstance();
			ec.setTime(ed);
			int h = bc.get(Calendar.HOUR_OF_DAY);
			int m = bc.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h == 12){
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmBeginHour);
				bc.set(Calendar.MINUTE, Consts.pmBeginMinute);
			}else if( h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				bc.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				bc.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			h = ec.get(Calendar.HOUR_OF_DAY);
			m = ec.get(Calendar.MINUTE);
			System.out.println(h + ":" + m);
			if( h < Consts.amBeginHour || (h==Consts.amBeginHour && m < Consts.amBeginMinute) ){ //如果是最早时间之前，设置为上班时间
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}else if(h==12){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amEndHour);
				ec.set(Calendar.MINUTE, Consts.amEndMinute);
			}else if(h>Consts.pmEndHour || ( h==Consts.pmEndHour && m>Consts.pmEndMinute) ){//第一天时间为0
				ec.set(Calendar.HOUR_OF_DAY, Consts.pmEndHour);
				ec.set(Calendar.MINUTE, Consts.pmEndMinute);
			}
			int count = 0;
			boolean flag = isHolady(days, bc);
//			if(flag){
//				bc.add(Calendar.DAY_OF_MONTH, 1);
//				bc.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
//				bc.set(Calendar.MINUTE, Consts.amBeginMinute);
//			}
			
//			flag = isHolady(days, ec);
			
			if(flag){
				ec.set(Calendar.HOUR_OF_DAY, Consts.amBeginHour);
				ec.set(Calendar.MINUTE, Consts.amBeginMinute);
			}
			
			System.out.println("bc:"+new Date(bc.getTimeInMillis())+",ec:"+new Date(ec.getTimeInMillis()));
			
			
			while(true){
				
				int by = bc.get(Calendar.YEAR);
				int bm = bc.get(Calendar.MONTH)+1;
				int bd = bc.get(Calendar.DAY_OF_MONTH);
				int bh = bc.get(Calendar.HOUR_OF_DAY);
				int bmi = bc.get(Calendar.MINUTE);
				int ey = ec.get(Calendar.YEAR); 
				int em = ec.get(Calendar.MONTH)+1;
				int ed2 = ec.get(Calendar.DAY_OF_MONTH);
				int eh = ec.get(Calendar.HOUR_OF_DAY);
				int emi = ec.get(Calendar.MINUTE);
				if(by==ey && bm==em && bd==ed2){ //同一天了
					
					flag = isHolady(days, bc);
					if(flag){//如果结束日期还是周末，直接结束
						break;
					}
					
					DayLeave dl = new DayLeave();
					if(count == 0 ){
						dl.setBeginTime( (bh < 10 ? ("0" + bh) : bh) + ":" + (bmi < 10 ? ("0" + bmi) : bmi) );
						dl.setEndTime( (eh < 10 ? ("0" + eh) : eh) + ":" + (emi < 10 ? ("0" + emi) : emi) );
						dl.setBeginTimeInt(bh*60 + bmi);
						dl.setEndTimeInt(eh*60 + emi);
						
					}else{
						bh = 8;
						dl.setBeginTime("08:30");
						dl.setEndTime( (eh < 10 ? ("0" + eh) : eh) + ":" + (emi < 10 ? ("0" + emi) : emi) );
						dl.setBeginTimeInt(510);
						dl.setEndTimeInt(eh*60 + emi);
					}
					
					int tlen = dl.getEndTimeInt() - dl.getBeginTimeInt();
					if(eh>=13 && bh<=12){
						tlen -= 60;
					}
					dl.setTimeLen(tlen);
					
					dl.setDayStr(by+"年"+( bm < 10 ? ("0" + bm) : bm )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
					
					list.add(dl);
					break;
				}else{
					DayLeave dl = new DayLeave();
					flag = isHolady(days, bc);
					bc.add(Calendar.DAY_OF_MONTH, 1);
					if(!flag){
						if(count == 0){
							dl.setBeginTime((bh < 10 ? ("0" + bh) : bh) + ":" + (bmi < 10 ? ("0" + bmi) : bmi));
							dl.setEndTime("17:30");
							dl.setBeginTimeInt(bh*60 + bmi);
							dl.setEndTimeInt(1050);
							dl.setDayStr(by+"年"+( bm < 10 ? ("0" + bm) : bm )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
							int tlen = dl.getEndTimeInt() - dl.getBeginTimeInt();
							if(bh<=12){
								tlen -= 60;
							}
							dl.setTimeLen(tlen);
						}else{
							dl.setBeginTime("08:30");
							dl.setEndTime("17:30");
							dl.setBeginTimeInt(510);
							dl.setEndTimeInt(1050);
							dl.setDayStr(by+"年"+( bm < 10 ? ("0" + bm) : bm )+"月"+ ( bd < 10 ? ("0" + bd) : bd ) + "日");
							dl.setTimeLen(480);
						}
						list.add(dl);
					} 
				}
				
				count++;
				//System.out.println(count);
				
			}
			return list;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
}
