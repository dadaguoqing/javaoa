package com.hj.util;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {

	public static List<Integer> notIn(List<Integer> l1, List<Integer> l2){
		List<Integer> list = new ArrayList<Integer>();
		
		for(Integer i : l1){
			if(!l2.contains(i)){
				list.add(i);
			}
		}
		
		return list;
	}
	
	public static String toString(String split, Integer[] ary){
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<ary.length; i++){
			sb.append(ary[i]);
			if(i != ary.length-1)
				sb.append(split);
		}
		
		return sb.toString();
	}
	
	public static Integer[] string2Array(String split, String str){
		String[] ss = str.split(split);
		Integer[] ints = new Integer[ss.length];
		
		for(int i=0; i<ss.length; i++){
			String s = ss[i];
			ints[i] = Integer.parseInt(s);
		}
		
		return ints;
	}
	
	public static String toString(String split, List<Integer> list){
		StringBuilder sb = new StringBuilder();
		int len = list.size();
		for(int i=0; i<len; i++){
			sb.append(list.get(i));
			if(i != len-1)
				sb.append(split);
		}
		
		return sb.toString();
	}
	
	public static boolean contains( Integer[] ary, int i ){
		for(int j : ary){
			if( j== i){
				return true;
			}
		}
		return false;
	}

}
