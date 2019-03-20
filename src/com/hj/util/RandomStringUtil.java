package com.hj.util;

import java.util.Random;

public class RandomStringUtil {

	//59个数字字母，不包含0 o O ,'I','l' '1',
	private static char[] elements = new char[]{'2','3','4','5','6','7','8','9'
			,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z'};
	
	public static String getRandomStr8(){
		StringBuilder sb = new StringBuilder();
		
		Random random = new Random();
		for(int i=0; i<8; i++){
			char c = elements[random.nextInt(elements.length)];
			sb.append(c);
		}
		
		return sb.toString();
	}
	
	public static void main(String[] args){
		for(int i=0; i<5; i++)
			System.out.println(getRandomStr8());
	}
}
