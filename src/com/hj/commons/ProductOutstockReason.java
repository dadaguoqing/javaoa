/**   
* @Title: ProductOutstockReason.java 
* @Package com.hj.commons 
* @Description: TODO 
* @author mlsong   
* @date 2018年6月4日 上午8:24:45 
* @version V1.0   
*/
package com.hj.commons;

import java.util.ArrayList;
import java.util.List;

/** 
* @ClassName: ProductOutstockReason 
* @Description: 产品发货原因
* @author mlsong 
* @date 2018年6月4日 上午8:24:45 
*/
public enum ProductOutstockReason {
	
	PRODUCT_ORDER_OUTSTOCK			("销售订单出库");
	
    /**
     * 状态信息描述
     */
    private String message;
    
    ProductOutstockReason(String message) {
        this.message = message;
    }
    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * @Title: convertToMap
     * @Description: 类型转换
     * @return: Map<Integer,String>
     */
    public static List<String> convertToList() {
    	List<String> list = new ArrayList<String>();
    	
    	for (ProductOutstockReason type : ProductOutstockReason.values()) {
    		list.add(type.getMessage());
    	}
    	
    	return list;
    }
}
