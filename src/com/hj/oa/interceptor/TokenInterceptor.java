/**  
 * Copyright © 2017 SML. All rights reserved.
 *
 * @Title: AuthInterceptor.java
 * @Prject: spring-mybatis
 * @Package: com.sml.spring.mybatis.web.interceptors
 * @Description: TODO
 * @author: mlsong  
 * @date: 2017年3月23日 下午5:35:46
 * @version: V1.0  
 */
package com.hj.oa.interceptor;
import org.springframework.web.method.HandlerMethod;  
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;  
  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import java.lang.reflect.Method;  
import java.util.UUID;  
  
public class TokenInterceptor extends HandlerInterceptorAdapter {  
    @Override  
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {  
        if (handler instanceof HandlerMethod) {  
            HandlerMethod handlerMethod = (HandlerMethod) handler;  
            Method method = handlerMethod.getMethod();  
            Token annotation = method.getAnnotation(Token.class);  
            if (annotation != null) {  
                boolean needSaveSession = annotation.save();  
                if (needSaveSession) {  
                    request.getSession(false).setAttribute("token", UUID.randomUUID().toString());  
                }  
                boolean needRemoveSession = annotation.remove();  
                if (needRemoveSession) {  
                    if (isRepeatSubmit(request)) {  
                        request.getSession().setAttribute("msg", "数据不能重复提交！");
                        response.sendRedirect("/crm2/index");
                        return false;  
                    }  
                    request.getSession(false).removeAttribute("token");  
                }  
            }  
            return true;  
        } else {  
            return super.preHandle(request, response, handler);  
        }  
    }  
  
    private boolean isRepeatSubmit(HttpServletRequest request) {  
        String serverToken = (String) request.getSession(false).getAttribute("token");  
        if (serverToken == null) {  
            return true;  
        }  
        String clinetToken = request.getParameter("token");  
        if (clinetToken == null) {  
            return true;  
        }  
        if (!serverToken.equals(clinetToken)) {  
            return true;  
        }  
        return false;  
    }  
}  