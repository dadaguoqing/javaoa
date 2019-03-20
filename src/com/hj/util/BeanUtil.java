package com.hj.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @ClassName: BeanUtil
 * @Description: Bean和Map之间的转换
 * @author: wqfang
 * @date: 2018年9月21日 上午10:33:14
 *
 */
public class BeanUtil {

	/**
	 * @Title: transBean2Map   
	 * @Description: JavaBean转Map
	 * @param: @param obj
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: Map<String,Object>      
	 * @throws
	 */
	public static Map<String, Object> transBean2Map(Object obj) throws Exception {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			throw new Exception("Bean转Map失败");
		}
		return map;
	}

}
