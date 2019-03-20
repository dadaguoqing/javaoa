package com.hj.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hj.oa.Consts;

public class CharsetFilter implements Filter{
	private String charset = "utf-8";

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		req.setCharacterEncoding(charset);
		chain.doFilter(req, resp);
		resp.setCharacterEncoding(charset);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		config.getServletContext().setAttribute("compName", Consts.compName);
		config.getServletContext().setAttribute("sysName", Consts.sysName);
		String cs = config.getInitParameter("charset");
		if(cs!=null){
			cs = cs.trim();
		}
		if(cs.length()!=0)
			charset = cs;
		
	}
	
	@Override
	public void destroy() {
		
	}

}
