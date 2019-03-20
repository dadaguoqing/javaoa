package com.hj.util;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * Servlet工具类
 * created at 2013-6-14
 * @author brady
 */
public class ServletUtil {
	
	/**
	 * 下载文件
	 * @param response
	 * @param file 下载的源文件
	 * @param displayName 显示在浏览器下载对话框中的文件名
	 */
	public static void downloadFile(HttpServletResponse response, 
			File file, String displayName) {
		
		if(file == null || !file.exists()) {
			return;
		}
		
		try {
			response.setContentType("application/x-download");  
			String filename = URLEncoder.encode(displayName,"utf-8");  
			response.addHeader("Content-Disposition","attachment;filename=" + filename);
			response.setContentLength((int)file.length()); 
			
			FileUtil.copy(new FileInputStream(file), response.getOutputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载文件
	 * @param response
	 * @param file 下载的源文件
	 * @param displayName 显示在浏览器下载对话框中的文件名
	 */
	public static void downloadFile(HttpServletResponse response, 
			File file, String displayName, String contentType) {
		
		if(file == null || !file.exists()) {
			return;
		}
		
		try {
			//设置响应头和客户端保存文件名
			response.setContentType(contentType);//设置为下载application/x-download  
			String filename = URLEncoder.encode(displayName,"UTF-8");  
			response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"; filename*=utf-8''" + filename);
			response.setContentLength((int)file.length());
			FileUtil.copy(new FileInputStream(file), response.getOutputStream());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
