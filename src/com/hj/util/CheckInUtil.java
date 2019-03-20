package com.hj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.hj.ke.bean.CheckInFromKs;
import com.hj.oa.bean.CheckIn;

public class CheckInUtil {
	
	public static List<CheckIn> mergeCheckIn(List<CheckIn> list1, List<CheckIn> list2){
		List<CheckIn> list = new ArrayList<CheckIn>();
		
		HashMap<String, CheckIn> map1 = new HashMap<String,CheckIn>();
		HashMap<String, CheckIn> map2 = new HashMap<String,CheckIn>();
		
		Set<String> names = new HashSet<String>();
		
		for(CheckIn ci: list1){
			if(ci==null){
				continue;
			}
			names.add(ci.getName());
			map1.put(ci.getName(), ci);
		}
		for(CheckIn ci: list2){
			if(ci==null){
				continue;
			}
			names.add(ci.getName());
			map2.put(ci.getName(), ci);
		}
		
		for(String name : names){
			CheckIn c1 = map1.get(name);
			CheckIn c2 = map2.get(name);
			CheckIn ci = CheckInUtil.mergeCheckIn(c1, c2);
			list.add(ci);
		}
		
		return list;
	}

	public static CheckIn mergeCheckIn(CheckIn ch1, CheckIn c2){
		if(ch1 == null){
			return c2;
		}
		if(c2 == null){
			return ch1;
		}
		
		CheckIn ci = new CheckIn();
		ci.setEmpId(ch1.getEmpId());
		ci.setDayStr(ch1.getDayStr());
		ci.setName(ch1.getName());
		
		//比较出这四个数据里面的最大以及最小值。
		TreeSet<String> ts = new TreeSet<String>();
		
		String ch1In = ch1.getCheckin();
		String c2In = c2.getCheckin();
		String ch1Out = ch1.getCheckout();
		String c2Out = c2.getCheckout();
		
		if(!isEmpty(ch1In)){
			ts.add(ch1In);
		}
		
		if( !isEmpty(c2In)){
			ts.add(c2In );
		}
		
		if( !isEmpty(ch1Out)){
			ts.add( ch1Out);
		}
		
		if(!isEmpty(c2Out)){
			ts.add( c2Out);
		}
		
		int len = ts.size();
		if(len ==0){
			return ci;
		}
		ci.setCheckin(ts.first());
		int checkinInt = OtherUtil.time2Int(ci.getCheckin());
		ci.setCheckinInt(checkinInt);
		if(len>1){
			ci.setCheckout(ts.last());
			int checkoutInt = OtherUtil.time2Int(ci.getCheckout());
			ci.setCheckoutInt(checkoutInt);
		}
		
		return ci;
	}
	
	public static List<CheckInFromKs> mergeCheckIn2(List<CheckInFromKs> list1, List<CheckInFromKs> list2){
		List<CheckInFromKs> list = new ArrayList<CheckInFromKs>();
		
		HashMap<String, CheckInFromKs> map1 = new HashMap<String,CheckInFromKs>();
		HashMap<String, CheckInFromKs> map2 = new HashMap<String,CheckInFromKs>();
		
		Set<String> names = new HashSet<String>();
		
		for(CheckInFromKs ci: list1){
			if(ci==null){
				continue;
			}
			names.add(ci.getName());
			map1.put(ci.getName(), ci);
		}
		for(CheckInFromKs ci: list2){
			if(ci==null){
				continue;
			}
			names.add(ci.getName());
			map2.put(ci.getName(), ci);
		}
		
		for(String name : names){
			CheckInFromKs c1 = map1.get(name);
			CheckInFromKs c2 = map2.get(name);
			CheckInFromKs ci = CheckInUtil.mergeCheckIn2(c1, c2);
			list.add(ci);
		}
		
		return list;
	}
	
	public static CheckInFromKs mergeCheckIn2(CheckInFromKs ch1, CheckInFromKs c2){
		if(ch1 == null){
			return c2;
		}
		if(c2 == null){
			return ch1;
		}
		
		CheckInFromKs ci = new CheckInFromKs();
		ci.setName(ch1.getName());
		
		//比较出这四个数据里面的最大以及最小值。
		TreeSet<String> ts = new TreeSet<String>();
		
		String ch1In = ch1.getCheckin();
		String c2In = c2.getCheckin();
		String ch1Out = ch1.getCheckout();
		String c2Out = c2.getCheckout();
		
		if(!isEmpty(ch1In)){
			ts.add(ch1In);
		}
		
		if( !isEmpty(c2In)){
			ts.add(c2In );
		}
		
		if( !isEmpty(ch1Out)){
			ts.add( ch1Out);
		}
		
		if(!isEmpty(c2Out)){
			ts.add( c2Out);
		}
		
		int len = ts.size();
		if(len ==0){
			return ci;
		}
		ci.setCheckin(ts.first());
		if(len>1){
			ci.setCheckout(ts.last());
		}
		
		return ci;
	}
	
	public static boolean isEmpty(String str){
		return str == null ? true : str.length() == 0; 
	}
}
