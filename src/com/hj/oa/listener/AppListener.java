package com.hj.oa.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hj.oa.bean.Dept;
import com.hj.oa.bean.User;
import com.hj.oa.service.UserService;

public class AppListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent evt) {
		
	}

	public void contextInitialized(ServletContextEvent evt) {
		ServletContext ctx = evt.getServletContext();
		ApplicationContext appCtx = WebApplicationContextUtils.getWebApplicationContext(ctx);
		UserService userSer = appCtx.getBean(UserService.class);
		List<Dept> depts = userSer.findAllDept();
		Map<Integer, Dept> map = new HashMap<Integer, Dept>();
		for(Dept dept : depts){
			map.put(dept.getId(), dept);
		}
		ctx.setAttribute("AllDepts", map);
		
		List<User> users = userSer.findAllUsersByDeptOrder();
		Map<Integer, User> uMap = new HashMap<Integer, User>();
		for(User user: users){
			uMap.put(user.getId(), user);
		}
		ctx.setAttribute("AllUsers", uMap);
	}

}
