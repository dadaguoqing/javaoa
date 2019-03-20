package com.hj.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hj.oa.Consts;
import com.hj.oa.bean.Dept;
import com.hj.oa.bean.Menu;
import com.hj.oa.bean.User;
import com.hj.oa.service.UserService;

public class OtherUtil {
	
	public static String getDeptName(User user, UserService userSer){
		String deptName = "";
		Dept dept = userSer.findDeptById(user.getDeptId());
		if(user.getId() == Consts.directorId){
			deptName = "研发中心";
		}
		if(dept!=null)
			deptName = dept.getName();
		
		return deptName;
	}
	
	public static String getDeptName(User user, Dept dept){
		String deptName = "";
		if(user.getId() == Consts.directorId){
			deptName = "研发中心";
		}
		if(dept!=null)
			deptName = dept.getName();
		
		return deptName;
	}
	
	private static int[] count2(int[] ary1, int[] ary2, int[] ary3){
		int tlen = 0;
		
		int cin = ary1[0];
		int cout = ary1[1];
		int st = ary2[0];
		int ed = ary2[1];
		int st2 = ary3[0];
		int ed2 = ary3[1];
		
		int max = 0;
		int min = 0;
		
		min = cin;
		
		if( max( new int[]{ed, ed2} , cout)){
			max = cout;
			tlen = 0;
		}else{
			max = ed2;
			
			int j1 = getJg(ary1,ary2);
			int j2 = getJg(ary1,ary3);
			int j3 = getJg(ary2,ary3);
			
			if(j2<j3){
				tlen = j1 + j2; 
			}else{
				tlen = j1 + j3;
			}
			
		}
		
		
		return new int[]{tlen, min, max};
	}
	//计算三个数组的时间间隔
	public static int countTimeJg(int[] ary1, int[] ary2, int[] ary3){
		//ary1 为打卡， ary2 第一次请假， ary3第二次请假
		int tlen = 0;
		
		int cin = ary1[0];
		//int cout = ary1[1];
		int st = ary2[0];
		//int ed = ary2[1];
		int st2 = ary3[0];
		//int ed2 = ary3[1];
		
		int max = 0;
		int min = 0;
		
		int[] r = null;
		
		if( min( new int[]{st, st2} , cin) ){//最合理的排序
			r = count2(ary1,ary2,ary3);
		}else if( min( new int[]{ cin ,st2} , st) && cin < st2 ){ //cin在中间
			r = count2(ary2,ary1,ary3);
		}else{ //反序
			r = count2(ary2,ary3,ary1);
		}
		
		tlen = r[0];
		min = r[1];
		max = r[2];
		
		tlen = ( (min - 510)<0 ? 0 : (min - 510) );
		tlen += ( ( 1050 - max )<0 ? 0 : ( 1050 - max ) );
		
		return tlen;
	}
	
	private static boolean min(int[] ary, int min){
		
		for(int i : ary){
			if(min > ary[i]){
				return false;
			}
		}
		return true;
	}
	
	private static boolean max(int[] ary, int max){
		
		for(int i : ary){
			if(max < ary[i]){
				return false;
			}
		}
		return true;
	}
	
	//获取间隔时间
	private static int getJg(int[] ary1, int[] ary2 ){
		int count = 0;
		
		int b1 = ary1[0];
		int e1 = ary1[1];
		int b2 = ary2[0];
		int e2 = ary2[1];
		
		if(b1 > 12*60 && b1 < 13*60 ){
			b1 = 12*60;
		}
		
		if(b2 > 12*60 && b2 < 13*60 ){
			b2 = 12*60;
		}
		
		if(e1 > 12*60 && e1 < 13*60 ){
			e1 = 13*60;
		}
		
		if(e2 > 12*60 && e2 < 13*60 ){
			e2 = 13*60;
		}
		
		if(b1 > b2){
			count = b2-e1;
			if(e1 <= 12*60 && b2>= 13*60){
				count -= 60;
			}
		}else{
			count = b1 - e2;
			if(e2 <= 12*60 && b1>= 13*60){
				count -= 60;
			}
		}
		count =  count>0 ? count : 0;
		return count;
	}

	public static int countTimeJg(int[] ary1, int[] ary2){
		int tlen = 0;
		
		int checkinInt = ary1[0];
		int checkioutInt = ary1[1];
		int bg = ary2[0];
		int ed = ary2[1];
		
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
		
		tlen = ( (min - 510)<0 ? 0 : (min - 510) );
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
		
		return tlen;
	}
	
	public static boolean containsMenu(String menuName, List<Menu> list){
		for(Menu menu : list){
			if(menuName.equals(menu.getName())){
				return true;
			}
		}
		return false;
	}
	
	public static int time2Int(String time){
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
	
	public static String miniute2String(int mits){
		if(mits<0){
			mits = -mits;
		}
		int days = mits/480;
		int minutes = mits%480;
		int hours = minutes/60;
		minutes = minutes%60;
		String str = "";
		str += (days == 0) ? "" : (days + "天");
		str += (hours == 0) ? "" : (hours + "小时");
		str += (minutes == 0) ? "" : (minutes + "分钟");
		if(StringUtils.isEmpty(str)){
			str = "0";
		}
 		return str;
	}
	
	public static String miniute2StringWithF(int mits){
		boolean flag = false;
		if(mits<0){
			mits = -mits;
			flag = true;
		}
		int days = mits/480;
		int minutes = mits%480;
		int hours = minutes/60;
		minutes = minutes%60;
		String str = "";
		str += (days == 0) ? "" : (days + "天");
		str += (hours == 0) ? "" : (hours + "小时");
		str += (minutes == 0) ? "" : (minutes + "分钟");
		if(StringUtils.isEmpty(str)){
			str = "0";
		}
		if(flag){
			str = "负" + str;
		}
 		return str;
	}
	
	/**
	 * 是否有类似于部门主管的待遇
	 * @return
	 */
	public static boolean isAsDeptMgr(int empId){
		
		/*
		for(Integer id : Consts.techDeptMgr){
			if(id == empId){
				return true;
			}
		}*/
		
		return false;
	}
	
	/**
	 * 是否是特殊员工，不用打卡，只显示是员工
	 * @return
	 */
	public static boolean isSpecialEmp(int empId){
		for(Integer id : Consts.specialEmp){
			if(id == empId){
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String[] args){
		int[] ary2 = new int[]{12*60 + 30,17*60 + 32};
		int[] ary1 = new int[]{530, 12*60 + 0};
		
		Integer id1 = new Integer(2);
		int id2 = 2;
		System.out.println(id1 == id2);
	}
}
