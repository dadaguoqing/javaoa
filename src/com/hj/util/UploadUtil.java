package com.hj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.hj.oa.Consts;

/**
 * 上传文件
 * 
 * @author wqfang
 *
 */
public class UploadUtil {

	/**
	 * 上传压缩文件
	 * 
	 * @param request
	 * @return
	 */
	public static String uploadFile(MultipartFile accessory, HttpServletRequest request) {
		String msg = Consts.SUCCESS;
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > 5 * 1024 * 1024l) {
			msg = "文件不能大于5M";
		}
		if ("".equals(fileName)) {
			msg = "请上传文件";
		} else if (!(fileName.endsWith(".zip") || fileName.endsWith(".rar"))) {
			msg = "请上传压缩文件，文件后缀为rar或者zip";
		}
		String ext = FileUtil.getFileExtName(fileName);
		// String savePath = "zipFile";
		String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		try {
			accessory.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return filename + "," + msg;
	}

	/**
	 * 上传照片文件
	 * 
	 * @param request
	 * @return
	 */
	public static String uploadPhotoFile(MultipartFile accessory, HttpServletRequest request) {
		String msg = Consts.SUCCESS;
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > 5 * 1024 * 1024l) {
			msg = "文件不能大于5M";
			return msg+",";
		}
		if ("".equals(fileName)) {
			msg = "请上传文件";
		} else if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".JPG")
				|| fileName.endsWith(".PNG") || fileName.endsWith(".jpeg") || fileName.endsWith(".JPEG")
				|| fileName.endsWith(".gif") || fileName.endsWith(".GIF"))) {
			msg = "请上传照片，文件后缀为png、jpg、gif或者jpeg";
			return msg+",";
		}
		String ext = FileUtil.getFileExtName(fileName);
		// String savePath = "zipFile";
		String savePath = request.getSession().getServletContext().getRealPath("/photo");
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		try {
			accessory.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return  msg+ "," + filename;
	}
	
	/**
	 * 上传压缩文件
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String ,String> uploadFile2(MultipartFile accessory, HttpServletRequest request) {
		Map<String ,String > map = new HashMap<String, String>();
		String msg = Consts.SUCCESS;
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > 10 * 1024 * 1024l) {
			msg = "文件不能大于10M";
		}
		if ("".equals(fileName)) {
			msg = "请上传文件";
		} else if (!(fileName.endsWith(".zip") || fileName.endsWith(".rar"))) {
			msg = "请上传压缩文件，文件后缀为rar或者zip";
		}
		String ext = FileUtil.getFileExtName(fileName);
		// String savePath = "zipFile";
		String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		try {
			accessory.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("msg", msg);
		map.put("url", filename);
		return map;
	}
	
	/**
	 * 上传照片文件
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String ,String > uploadPhoto2(MultipartFile accessory, HttpServletRequest request) {
		Map<String ,String > map = new HashMap<String, String>();
		String msg = Consts.SUCCESS;
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > 5 * 1024 * 1024l) {
			msg = "文件不能大于5M";
		}
		if ("".equals(fileName)) {
			msg = "请上传文件";
		} else if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".JPG")
				|| fileName.endsWith(".PNG") || fileName.endsWith(".jpeg") || fileName.endsWith(".JPEG")
				|| fileName.endsWith(".gif") || fileName.endsWith(".GIF"))) {
			msg = "请上传照片，文件后缀为png、jpg、gif或者jpeg";
		}
		String ext = FileUtil.getFileExtName(fileName);
		// String savePath = "zipFile";
		String savePath = request.getSession().getServletContext().getRealPath("/photo");
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		try {
			accessory.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("msg", msg);
		map.put("url", filename);
		return  map;
	}
	/**
	 * 上传压缩文件
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String ,String> uploadExcel(MultipartFile accessory, HttpServletRequest request) {
		Map<String ,String > map = new HashMap<String, String>();
		String msg = Consts.SUCCESS;
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > 10 * 1024 * 1024l) {
			msg = "文件不能大于5M";
		}
		if ("".equals(fileName)) {
			msg = "请上传文件";
		} else if (!(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
			msg = "请上传Excel文件，文件后缀为xls或者xlsx";
		}
		String ext = FileUtil.getFileExtName(fileName);
		// String savePath = "zipFile";
		String savePath = request.getSession().getServletContext().getRealPath("/excel");
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		try {
			accessory.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("msg", msg);
		map.put("url", filename);
		map.put("path", savePath);
		return map;
	}

	/**
	 * 文件下载
	 * @throws IOException
	 */
	public static void download(String filePath,HttpServletResponse response) throws  IOException{
        //获取服务器文件
        File file = new File(filePath);
        InputStream ins = new FileInputStream(file);
        /* 设置文件ContentType类型，这样设置，会自动判断下载文件类型 */
        response.setContentType("multipart/form-data");
        /* 设置文件头：最后一个参数是设置下载文件名 */
        response.setHeader("Content-Disposition", "attachment;filename="+file.getName());
        OutputStream os = response.getOutputStream();
        try{
            byte[] b = new byte[1024];
            int len;
            while((len = ins.read(b)) > 0){
                os.write(b,0,len);
            }
            os.flush();
            os.close();
            ins.close();
        }catch (IOException ioe){
           ioe.printStackTrace();
        }finally {
            if(os != null){
                os.close();
            }
            if(ins != null){
                ins.close();
            }
        }
    }
	
	
	
	/**
	 * 上传excel文件
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static Map<String ,String> uploadExcel_v2(MultipartFile accessory, HttpServletRequest request) throws Exception {
		Map<String ,String > map = new HashMap<String, String>();
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > 10 * 1024 * 1024l) {
			throw new Exception("文件不能大于10M");
		}
		if ("".equals(fileName)) {
			throw new Exception("请上传文件");
		} else if (!(fileName.endsWith(".xls") || fileName.endsWith(".xlsx"))) {
			throw new Exception("请上传Excel文件，文件后缀为xls或者xlsx");
		}
		String ext = FileUtil.getFileExtName(fileName);
		String savePath = request.getSession().getServletContext().getRealPath("/excel");
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		accessory.transferTo(file);
		map.put("url", filename);
		map.put("path", savePath);
		return map;
	}
	
	
	/**
	 * 上传压缩文件
	 * 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public static Map<String ,String> uploadRar(MultipartFile accessory, HttpServletRequest request) throws Exception {
		Map<String ,String > map = new HashMap<String, String>();
		String msg = Consts.SUCCESS;
		String fileName = accessory.getOriginalFilename();
		long size = accessory.getSize();
		if (size > 10 * 1024 * 1024l) {
			throw new Exception("文件不能大于10M");
		}
		if ("".equals(fileName)) {
			throw new Exception("请上传文件");
		} else if (!(fileName.endsWith(".zip") || fileName.endsWith(".rar"))) {
			throw new Exception("请上传压缩文件，文件后缀为rar或者zip");
		}
		String ext = FileUtil.getFileExtName(fileName);
		// String savePath = "zipFile";
		String savePath = request.getSession().getServletContext().getRealPath("/upload");
		String filename = UUID.randomUUID() + ext;
		String loc = savePath + File.separator + filename;
		File file = new File(savePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(loc);
		try {
			accessory.transferTo(file);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		map.put("msg", msg);
		map.put("url", filename);
		return map;
	}
}
