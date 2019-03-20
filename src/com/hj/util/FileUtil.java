/**
 * 文件操作工具类
 * Author: 张国敬
 * Create at: 2012-7-17
 */
package com.hj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtil {

	public static void main(String[] args) {
		deleteDirectory("E:\\06_soft\\02_Java\\apache-tomcat-6.0.37\\webapps\\oa\\upload");
	}

	/**
	 * 取文件后缀名 返回如.txt的后缀名
	 */
	public static String getFileExtName(String fileName) {
		if (fileName == null) {
			return "";
		}

		String ext = "";
		int pos = fileName.lastIndexOf(".");
		if (pos > 0) {
			ext = fileName.substring(pos);
		}
		return ext;
	}

	/**
	 * 取文件名称，不带任何路径
	 */
	public static String getAbstractFileName(String fileName) {
		if (fileName == null) {
			return "";
		}

		String ext = fileName;
		int pos = fileName.lastIndexOf(File.separator);
		if (pos > 0) {
			ext = fileName.substring(pos + 1);
		}
		return ext;
	}

	/**
	 * 文件复制操作
	 */
	public static void copy(InputStream is, OutputStream os) throws IOException {
		if (is == null || os == null) {
			throw new IOException("input file or output file is null.");
		}
		byte buf[] = new byte[1024];
		int len = 0;
		while (true) {
			len = is.read(buf);
			if (len <= 0) {
				break;
			}
			os.write(buf, 0, len);
		}

		is.close();
		os.flush();
		os.close();
	}

	/**
	 * 压缩文件
	 * 
	 * @author LiuXH
	 * @param files
	 *            需要压缩d文件
	 * @param path
	 *            压缩文件存放路径
	 * @param yf
	 *            存放具体目录
	 * @throws IOException
	 */
	public static File zipFile(Set<File> files, String path, String yf) throws Exception {
//		yf = yf.replace("年", "").replace("月", "").replace("日", "");
		File zipFile = new File(path + yf.substring(0, yf.lastIndexOf("-")) + new String(".zip"));
		byte[] buf = new byte[1024];
		int len;
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFile));
		for (File file : files) {
			FileInputStream in = new FileInputStream(file);
			zout.putNextEntry(new ZipEntry(file.getName()));
			while ((len = in.read(buf)) > 0) {
				zout.write(buf, 0, len);
			}
			zout.setEncoding(System.getProperty("sun.jnu.encoding"));
			zout.closeEntry();
			in.close();
		}
		zout.close();
		return zipFile;
	}

	/**
	 * 删除文件
	 */

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();// "删除单个文件"+name+"成功！"
			return true;
		} // "删除单个文件"+name+"失败！"
		return false;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String directorydir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!directorydir.endsWith(File.separator)) {
			directorydir = directorydir + File.separator;
		}
		File dirFile = new File(directorydir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// "删除目录失败"+name+"目录不存在！"
			return true;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			// System.out.println("删除目录失败");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			// System.out.println("删除目录"+directorydir+"成功！");
			return true;
		} else {
			// System.out.println("删除目录"+directorydir+"失败！");
			return false;
		}
	}

	// 删除文件夹 folderPath 文件夹完整绝对路径
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除指定文件夹下所有文件 path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {// 判断该路径是否是一个目录
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}
}
