package com.hj.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.EmpDay;
import com.hj.oa.service.CheckInService;
import com.hj.oa.service.EmpDayService;

public class AutoAddCheckIn {

	
	public static void addCheckin(CheckInService ciService, EmpDayService edService, String dayStr){
		
		List<CheckIn> cins = ciService.findByDay(dayStr);
		List<EmpDay> eds = edService.findByDayStr(dayStr);
		
		List<EmpDay> upds = new ArrayList<EmpDay>();
		List<EmpDay> adds = new ArrayList<EmpDay>();
		
		for(CheckIn ci : cins){
			EmpDay empDay = new EmpDay();
			empDay.setDay(dayStr);
			empDay.setEmpId(ci.getEmpId());
			
			boolean flag = false;
			
			EmpDay ed1 = null;
			for(EmpDay ed : eds){
				if(empDay.equals(ed)){
					ed1 = ed;
					flag = true; 
					break;
				}
			}
			
			String checkin = ci.getCheckin();
			String checkout = ci.getCheckout();
			
			Integer checkinInt = null;
			Integer checkoutInt = null;
			if(StringUtils.isNotEmpty(checkout)){
				checkoutInt = time2Int(checkout);
			}
			if(StringUtils.isNotEmpty(checkin)){
				checkinInt = time2Int(checkin);
			}
			if(flag){//update
				empDay = ed1;
				
				empDay.setCheckin(checkin);
				empDay.setCheckinInt(checkinInt);
				empDay.setCheckout(checkout);
				empDay.setCheckoutInt(checkoutInt);
				
				if(checkinInt != null && checkoutInt!= null){
					if(checkinInt <= 510 && checkoutInt >= 1050){
						empDay.setType(1);//全勤
						empDay.setUnCommonTime(0);
						
					}else if( (checkinInt < 510 && checkoutInt <510) || (checkinInt > 1050 && checkoutInt>1050) || ( (checkinInt>12*60 &&checkinInt<13*60) && (checkoutInt>12*60 &&checkoutInt<13*60) ) ){
						//empDay.setUnCommonTime(480);
						//empDay.setType(-1);//考勤不正常
						 //相当于没打卡
					}else{
						
						if(empDay.getQjstInt() == null){//根本没有请假，直接算考勤时间
							//int checkinInt = empDay.getCheckinInt();
							int checkioutInt = empDay.getCheckoutInt();
							if(checkinInt > 12*60 && checkinInt < 13*60 ){
								checkinInt = 12*60;
							}
							if(checkioutInt > 12*60 && checkioutInt < 13*60 ){
								checkioutInt = 13*60;
							}
							
							int tlen = ( (checkinInt - 510)<0 ? 0 : (checkinInt - 510) );
							tlen += ( ( 1050 - checkioutInt )<0 ? 0 : ( 1050 - checkioutInt ) );
							
							empDay.setUnCommonTime(tlen);
							empDay.setType(-1);
						}else if(empDay.getQjst2Int() == null){ //只请一次假
							//int checkinInt = empDay.getCheckinInt();
							int checkioutInt = empDay.getCheckoutInt();
							
							int bg = empDay.getQjstInt();
							int ed = empDay.getQjedInt();
							
							int[] ary2 = new int[]{12*60 + 30,17*60 + 32};
							int[] ary1 = new int[]{530, 12*60 + 0};
							
							if(checkinInt > 12*60 && checkinInt < 13*60 ){
								checkinInt = 12*60;
							}
							if(bg > 12*60 && bg < 13*60 ){
								bg = 12*60;
							}
							if(checkioutInt > 12*60 && checkioutInt < 13*60 ){
								checkioutInt = 13*60;
							}
							if(ed > 12*60 && ed < 13*60 ){
								ed = 13*60;
							}
							
							int min = bg>checkinInt ? checkinInt : bg;
							int max = ed>checkioutInt ? ed : checkioutInt;
							
							int tlen = ( (min - 510)<0 ? 0 : (min - 510) );
							tlen += ( ( 1050 - max )<0 ? 0 : ( 1050 - max ) );
							
							if( bg>checkinInt ){
								if(bg > checkioutInt){//计算间隔时间
									tlen += (bg - checkioutInt);
									if(checkioutInt <= 12*60 && bg>= 13*60){
										tlen -= 60;
									}
								}
							}else{
								if(checkinInt > ed){//计算间隔时间
									tlen += (checkinInt - ed);
									if(ed <= 12*60 && checkinInt>= 13*60){
										tlen -= 60;
									}
								}
							}
							empDay.setUnCommonTime(tlen);
						}else {//请过两次假期
							
							int[] ary1 = new int[]{empDay.getCheckinInt(), empDay.getCheckoutInt()};
							int[] ary2 = new int[]{empDay.getQjstInt(), empDay.getQjedInt()};
							int[] ary3 = new int[]{empDay.getQjst2Int(), empDay.getQjed2Int()};
							
							//是否有无效打卡，如果有另外计算。
							
							//如果打卡有效
							int tlen = OtherUtil.countTimeJg(ary1, ary2, ary3);
							empDay.setUnCommonTime(tlen);
							if(tlen>0) empDay.setType(-1);
							
						}
						
						//empDay.setType(-1);//考勤不正常
					}
				}else{
					empDay.setType(-1);//考勤不正常
					empDay.setUnCommonTime(480);
				}
				
				upds.add(empDay);
			}else{ //add
				
				empDay.setCheckin(checkin);
				empDay.setCheckinInt(checkinInt);
				empDay.setCheckout(checkout);
				empDay.setCheckoutInt(checkoutInt);
				
				if(checkinInt != null && checkoutInt!= null){
					if(checkinInt <= 510 && checkoutInt >= 1050){
						empDay.setType(1);//全勤
					}else{
						if(checkinInt > 12*60 && checkinInt < 13*60 ){
							checkinInt = 12*60;
						}
						if(checkoutInt > 12*60 && checkoutInt < 13*60 ){
							checkoutInt = 13*60;
						}
						
						int tlen = ( (checkinInt - 510)<0 ? 0 : (checkinInt - 510) );
						tlen += ( ( 1050 - checkoutInt )<0 ? 0 : ( 1050 - checkoutInt ) );
						
						empDay.setUnCommonTime(tlen);
						empDay.setType(-1);//考勤不正常
					}
				}else{
					empDay.setType(-1);//考勤不正常
					empDay.setUnCommonTime(480);
				}
				
				adds.add(empDay);
			}
			
		}
		
		edService.addUpdate(adds, upds);
	}
	
	private static int time2Int(String time){
		String[] ss = time.split(":");
		
		String hStr = ss[0];
		String mStr = ss[1];
		
		if(hStr.startsWith("0")){
			hStr = hStr.substring(1);
		}
		if(mStr.startsWith("0")){
			mStr = mStr.substring(1);
		}
		int h = Integer.parseInt(hStr);
		int m = Integer.parseInt(mStr);
		
		return h * 60 + m;
	}
}
