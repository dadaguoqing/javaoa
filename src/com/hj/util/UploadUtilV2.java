package com.hj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.hj.commons.ResultBean;
import com.hj.oa.Consts;

/**
 * 上传文件
 * 
 * @author wqfang
 *
 */
public class UploadUtilV2 {
	private final static String MSG1 = "文件大小不能超过";
	private final static String MSG2 = "请上传文件";
	private final static String PATH = "/upload";
	private final static String PATH2 = "/excel";
	private final static String EXTERNAL = "/external";
	private final static long M = 1024 * 1024l;

	/** 压缩文件 */
	public final static String[] ZIPFILE = { ".zip", ".rar" };
	/** 图片文件 */
	public final static String[] IMGFILE = { ".png", ".jpg", ".JPG", ".PNG", ".jpeg", ".JPEG", ".gif", ".GIF" };
	/** excel文件 */
	public final static String[] EXCELFILE = { ".xls", ".xlsx" };
	public final static String[] WORDFILE = { ".doc", ".docx" };
	public final static String[] PDFFILE = { ".pdf", ".PDF"};
	public final static String[] COORIDNATE_FILE = { ".318" };
	public final static String[] CAD_FILE = { ".cad", ".CAD" };
	public final static String[] ALL = { ".pdf", ".PDF",".xls", ".xlsx", ".doc", ".docx" };
	public final static String[] ANY = null;
	public final static String MSG3 = "格式错误，请上传压缩文件";
	public final static String MSG4 = "格式错误，请上传图片文件";
	public final static String MSG5 = "格式错误，请上传Excel文件";
	
	
	public static String uploadFileByType(MultipartFile accessory, HttpServletRequest request, int fileSize, String[] arrs) throws Exception {
		String newFileName = checkFile2(accessory, fileSize, arrs);
		String path = DateUtil.getCurrentTime("/yyyyMMdd/");
		// 指定文件存储地址
		String savePath = request.getSession().getServletContext().getRealPath(EXTERNAL + path);
		String loc = savePath + File.separator + newFileName;
		// 判断文件地址是否存在
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		accessory.transferTo(file);
		System.out.println(loc);
		return EXTERNAL + path + newFileName;
	}
	

	/**
	 * 上传文件
	 * 
	 * @param accessory
	 *            压缩文件
	 * @param request
	 * @param fileSize
	 *            上传文件大小限制
	 * @param arrs
	 *            文件格式数组
	 * @param msg
	 *            错误信息
	 * @return
	 */
	public static ResultBean<Map<String, String>> uploadFile(MultipartFile accessory, HttpServletRequest request,
			int fileSize, String[] arrs, String msg) {
		ResultBean<Map<String, String>> result = new ResultBean<Map<String, String>>();
		result.setCode(ResultBean.SUCCESS);
		result.setMsg(Consts.SUCCESS);
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > fileSize * M) {
			result.setCode(ResultBean.FAIL);
			result.setMsg(MSG1 + fileSize + "M");
		}
		if ("".equals(fileName)) {
			result.setCode(ResultBean.FAIL);
			result.setMsg(MSG2);
		}
		int i = 0;
		for (String arr : arrs) {
			if (fileName.endsWith(arr)) {
				i++;
			}
		}
		if (i == 0) {
			result.setCode(ResultBean.FAIL);
			result.setMsg(msg);
		}
		String ext = FileUtil.getFileExtName(fileName);
		String savePath = request.getSession().getServletContext().getRealPath(PATH);
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		Map<String, String> map = new HashMap<String, String>();
		map.put("url", filename);
		map.put("path", savePath);
		result.setData(map);
		try {
			accessory.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 文件下载
	 * 
	 * @throws IOException
	 */
	public static void download(String filePath, HttpServletResponse response) throws IOException {
		// 获取服务器文件
		File file = new File(filePath);
		InputStream ins = new FileInputStream(file);
		/* 设置文件ContentType类型，这样设置，会自动判断下载文件类型 */
		response.setContentType("multipart/form-data");
		/* 设置文件头：最后一个参数是设置下载文件名 */
		response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
		try {
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int len;
			while ((len = ins.read(b)) > 0) {
				os.write(b, 0, len);
			}
			os.flush();
			os.close();
			ins.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 上传文件
	 * 
	 * @param accessory
	 *            压缩文件
	 * @param request
	 * @param fileSize
	 *            上传文件大小限制
	 * @param arrs
	 *            文件格式数组
	 * @param msg
	 *            错误信息
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	public static String uploadFile2(MultipartFile accessory, HttpServletRequest request, int fileSize, String[] arrs) throws Exception {
		String newFileName = checkFile(accessory, fileSize, arrs);
		// 指定文件存储地址
		String savePath = request.getSession().getServletContext().getRealPath(PATH);
		String loc = savePath + File.separator + newFileName;
		// 判断文件地址是否存在
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		accessory.transferTo(file);
		return newFileName;
	}
	
	
	public static String uploadFile22(MultipartFile accessory, HttpServletRequest request, int fileSize, String[] arrs) throws Exception {
		String newFileName = checkFile(accessory, fileSize, arrs);
		// 指定文件存储地址
		String savePath = request.getSession().getServletContext().getRealPath(PATH2);
		String loc = savePath + File.separator + newFileName;
		// 判断文件地址是否存在
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		accessory.transferTo(file);
		return newFileName;
	}

	/**
	 * 检查文件格式和大小
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String checkFile(MultipartFile accessory, Integer fileSize, String[] arrs) throws Exception {
		String fileName = accessory.getOriginalFilename();
		String ext = FileUtil.getFileExtName(fileName);
		String newFileName = UUID.randomUUID() + ext;
		// 判断文件大小
		long size = accessory.getSize();
		if (size > fileSize * M) {
			throw new Exception("文件不小不能超过" + fileSize + "M");
		}
		// 获取文件格式
		// 判断是不是符合格式要求
		if(arrs != null && arrs.length != 0) {
			List<String> list = Arrays.asList(arrs);
			if (!list.contains(ext)) {
				throw new Exception("文件格式错误");
			}
		}
		return newFileName;
	}
	
	
	/**
	 * 
	 * @Title: uploadFile   
	 * @Description: 上传文件
	 * @param: @param accessory
	 * @param: @param request
	 * @param: @param fileSize
	 * @param: @param arrs
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: String      
	 * @throws
	 */
	public static String uploadFile(MultipartFile accessory, HttpServletRequest request, int fileSize, String[] arrs) throws Exception {
		String newFileName = checkFile(accessory, fileSize, arrs);
		// 指定文件存储地址
		String savePath = request.getSession().getServletContext().getRealPath(PATH);
		String loc = savePath + File.separator + newFileName;
		// 判断文件地址是否存在
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		accessory.transferTo(file);
		return loc;
	}
	
	
	/**
	 * 获取上传后文件名称
	 * @param path
	 * @return
	 */
	public  static String getFileName(String path){
		String fileName = path.substring(path.lastIndexOf("\\"), path.length());
		System.out.println(fileName);
		return fileName;
	}
	
	
	/**
	 * @Title: checkFile2   
	 * @Description: 保留原文件名
	 * @return: String      
	 * @throws
	 */
	public static String checkFile2(MultipartFile accessory, Integer fileSize, String[] arrs) throws Exception {
		String fileName = accessory.getOriginalFilename();
		String ext = FileUtil.getFileExtName(fileName);
		// 判断文件大小
		long size = accessory.getSize();
		if (size > fileSize * M) {
			throw new Exception("文件不小不能超过" + fileSize + "M");
		}
		// 获取文件格式
		// 判断是不是符合格式要求
		if(arrs != null && arrs.length != 0) {
			List<String> list = Arrays.asList(arrs);
			if (!list.contains(ext)) {
				throw new Exception("文件格式错误");
			}
		}
		return fileName;
	}
	
	
	/**
	 * @Title: downloadLocal   
	 * @Description: 下载本地文件
	 * @return: void      
	 * @throws
	 */
	public static void downloadLocal(String path, HttpServletResponse response) throws FileNotFoundException {
        // 下载本地文件
		if(path.indexOf("/") != -1) {
			String fileName = path.substring(path.lastIndexOf("/") + 1, path.length()); // 文件的默认保存名
			// 读到流中
			InputStream inStream = new FileInputStream(path);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("bin");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			// 循环取出流中的数据
			byte[] b = new byte[100];
			int len;
			try {
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("文件路径有误！");
		}
    }
	
	public static void main(String[] args) {
		String path = "oa\\external\\20181210\\9688f4f0-154e-4713-bd92-979c72ea6386.doc";
		String fileName = path.substring(path.lastIndexOf("\\") + 1, path.length());
		System.out.println(fileName);
	}

}
