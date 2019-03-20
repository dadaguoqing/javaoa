package com.hj.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.hj.commons.ResultBean;

/**
 * 检查Excel
 * 
 * @author wqfang
 *
 */
public class CheckExcelUtil {
	
	/**
	 * 检查单元格是否存在
	 * @param list excel解析之后的list
	 * @param col 单元格列数
	 * @param cellStr 单元格名称
	 * @param result 结果及
	 * @param i 数据行数
	 * @return
	 */
	public static  String checkCell(List<Map<String,String>> list,int col,
			String cellStr,ResultBean<T> result,int i) {
		String str = "";
		if (col < list.size() && list.get(col).containsKey(cellStr)) {
			str = list.get(col).get(cellStr);
		} else {
			result.setCode(ResultBean.FAIL);
			result.setMsg("第'" + i + "'行且列名为'" + cellStr + "'的数据出错。");
		}
		return str;
	}
	
}