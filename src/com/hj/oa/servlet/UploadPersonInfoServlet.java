package com.hj.oa.servlet;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.InvalidContentTypeException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.web.context.WebApplicationContext;

import com.hj.oa.Consts;
import com.hj.oa.bean.Rule;
import com.hj.oa.bean.User;
import com.hj.oa.service.NoticeService;
import com.hj.oa.service.UserService;
import com.hj.util.FileUtil;

public class UploadPersonInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static SimpleDateFormat dFormat = new SimpleDateFormat("yyyy年MM月dd日");
	
	private UserService userService;
	
	//支持的文件类型
	private String[] types = new String[]{".pdf"};
	
	private boolean isValidType(String type){
		
		for(String str : types){
			if(str.equals(type))
				return true;
		}
		
		return false;
	}
	
	public void init(){
		ServletContext ctx = this.getServletContext();
		WebApplicationContext appCtx = (WebApplicationContext)ctx.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
		userService = appCtx.getBean(UserService.class);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException,IOException {
		
		HttpSession session = request.getSession();
		//获取登录用户信息
		User loginUser = (User)session.getAttribute("loginUser");
		
		String msg = "上传成功，请进个人信息查看。";
		//设置最大文件大小 10Mb
		int uploadSize = 10;
		try{
			
			DiskFileItemFactory factory = new DiskFileItemFactory();

			ServletFileUpload upload = new ServletFileUpload(factory);

			//设置最大文件大小 10Mb
			upload.setSizeMax(uploadSize * 1024 * 1024);

			// Parse the request
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
			
			while (iter.hasNext()) {
			    FileItem item = iter.next();
			    if (item.isFormField()) {//普通字段
			    	
			        
			    } else {
			    	//String fieldName = item.getFieldName();
			        String fileName = item.getName();
			        if(fileName.contains("\\")){
			        	int index = fileName.lastIndexOf('\\');
			        	fileName = fileName.substring(index+1);
			        }
			        String contentType = item.getContentType().toLowerCase();
			        
			        //判断文档格式。
			        
			        
			        String ext = FileUtil.getFileExtName(fileName);//文件后缀名
			        
			        boolean isValid = isValidType(ext.toLowerCase());
			        
			        if(!isValid){
			        	throw new InvalidContentTypeException();
			        }
			        
			        String savePath = "empInfo";
			        String filename = UUID.randomUUID()+ext;
			        String loc = savePath + File.separator +filename;
			        
			        File file = new File(Consts.uploadFileRootLoc + savePath);
					if(!file.exists()){
						file.mkdirs();
					}
					
					file = new File(Consts.uploadFileRootLoc + loc);
			        item.write(file);
			        
			        loginUser.setPdf(loc);
			        
			    }
			    
			}
			
			this.userService.updatePdf(loginUser);
			//this.noticeService.addRule(rule);
		}catch (SizeLimitExceededException e) {
			msg = "您上传的文件超过规定大小（" + uploadSize + "MB）。";
			e.printStackTrace();
		} catch (InvalidContentTypeException e) {
			msg = "您上传文件的格式不符合规定。（目前只支持上传pdf文件）";
			e.printStackTrace();
		} catch (FileUploadException e) {
			msg = "上传失败，请求异常";
			e.printStackTrace();
		} catch (Exception e) {
			msg = "上传失败，未知异常。";
			e.printStackTrace();
		}
		
		request.setAttribute("msg", msg);
		
		//跳转页面
		request.getRequestDispatcher("/WEB-INF/views/oa/emp/changePShow.jsp").forward(request, response);
		
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public UserService getUserService() {
		return userService;
	}
}
