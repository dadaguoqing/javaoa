package com.hj.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.hj.oa.bean.OutAccessCode;
import com.hj.oa.bean.User;

public class LoginFilter implements Filter{
	
	private List<String> whiteUrls = new ArrayList<String>();

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		HttpSession session = request.getSession();
		
		String reqUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = reqUri.substring(contextPath.length());
		
		//外网登陆控制（开始）
		
		String serverName = request.getServerName();
		String remoteIp = request.getRemoteAddr();
		String remoteUser = request.getRemoteUser();
		OutAccessCode OutConfrimd = (OutAccessCode)session.getAttribute("OutConfrimd");
		if(null != remoteUser && OutConfrimd == null){
			OutConfrimd = new OutAccessCode();
			OutConfrimd.setEmpCode(remoteUser);
			session.setAttribute("OutConfrimd", OutConfrimd);
		}
		
		if(serverName == null)
			serverName = "";
		
		serverName = serverName.toLowerCase();
		if( !remoteIp.startsWith("192.168.") && !"220.180.239.165".equals(remoteIp) && !"36.33.24.77".equals(remoteIp) && !"/web/loginOutside".equals(url) && !"/web/oa/kq/uploadKq".equals(url) && (serverName.contains("macrosilicon") || "36.33.24.77".equals(serverName) || "l-pc".equals(serverName)) ){//需要外网访问码
			if(OutConfrimd == null){
				//response.sendRedirect(contextPath+"/web/loginOutside");
				response.sendRedirect(contextPath+"/loginOutside.jsp");
				return;
			}
		}
		
		//外网登陆控制（结束）
		
		//cookie自动登录
		
		
		//自动登录结束
		
		if(whiteUrls.contains(url)){
			chain.doFilter(req, resp);
			return;
		}
		
		User loginUser = (User)session.getAttribute("loginUser");
		
		if(loginUser == null){
			response.sendRedirect(contextPath+"/");
		}else{
			chain.doFilter(request, response);
		}
		
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		whiteUrls.add("");
		whiteUrls.add("/");
		
		String list = filterConfig.getInitParameter("whiteList");
		if(! StringUtils.isEmpty(list)) {
			String[] ary = list.split(",");
			for(String s : ary) {
				whiteUrls.add(s.trim());
			}
		}
	}
	
	@Override
	public void destroy() {
		
	}

}
