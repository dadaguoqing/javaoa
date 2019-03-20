/**  
 * Copyright © 2017 SML. All rights reserved.
 *
 * @Title: Token.java
 * @Prject: crm2
 * @Package: com.itek.crm.interceptor
 * @Description: TODO
 * @author: mlsong  
 * @date: 2017年9月26日 下午2:06:54
 * @version: V1.0  
 */
package com.hj.oa.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  
@Retention(RetentionPolicy.RUNTIME)  
public @interface Token {  
    boolean save() default false;  
    boolean remove() default false;  
}
