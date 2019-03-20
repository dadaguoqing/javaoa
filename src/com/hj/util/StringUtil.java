package com.hj.util;

import java.math.BigDecimal;

/**
 * StringUtil类
 * 
 * @author wqfang
 *
 */
public class StringUtil {
	/**
	 * 删除最后一字符
	 * 
	 * @param str
	 * @return
	 */
	public static String deleteEnd(String str) {
		return str.substring(0, str.length() - 1);
	}

	public static Double formatDouble(Double d) {
		BigDecimal b = new BigDecimal(d);
		double dd = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return dd;
	}
	
	public static void main(String[] args) {
		System.out.println(formatDouble(1.235567890));
	}
}