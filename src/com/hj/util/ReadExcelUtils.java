package com.hj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.piccolo.io.FileFormatException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.hj.oa.Consts;
import com.hj.oa.bean.MateriaExcel;

import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;

/**
 * @ClassName: ReadExcelUtils
 * @Description: 读取excel(兼容03和07格式)
 * @author wqfang
 */

public class ReadExcelUtils {

	private static final String EXTENSION_XLS = "xls";
	private static final String EXTENSION_XLSX = "xlsx";

	/**
	 * 读取excel文件内容
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public List<List<Map<String, String>>> readExcel(String filePath) throws Exception {

		// List<Map<String, String>> list = new LinkedList<Map<String,
		// String>>();
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String, String>>>();
		// 检查
		this.preReadCheck(filePath);
		// 获取workbook对象
		Workbook workbook = null;
		workbook = this.getWorkbook(filePath);
		// 读文件 一个sheet一个sheet地读取
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();
			// 读取首行 即,表头
			Row firstRow = sheet.getRow(firstRowIndex);
			if (null != firstRow) {
				// 读取数据行
				for (int rowIndex = firstRowIndex + 1; rowIndex <= lastRowIndex; rowIndex++) {
					// 当前行
					Row currentRow = sheet.getRow(rowIndex);
					if (currentRow == null) {
						continue;
					}
					// 首列
					int firstColumnIndex = currentRow.getFirstCellNum();
					// 最后一列
					int lastColumnIndex = currentRow.getLastCellNum();

					List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
					for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {
						Map<String, String> map2 = new HashMap<String, String>();
						// 标题列
						Cell titleCell = firstRow.getCell(columnIndex);
						// 标题列对应的值
						String titleCellValue = this.getCellValue(titleCell, true).trim();
						// 当前单元格
						Cell currentCell = currentRow.getCell(columnIndex);
						// 当前单元格的值
						String currentCellValue = this.getCellValue(currentCell, true);
						map2.put(titleCellValue, currentCellValue);
						rowList.add(map2);
					}
					list.add(rowList);
				}
			}
		}
		return list;
	}

	/**
	 * 读取excel文件内容（忽略标题栏）
	 * 
	 * @param filePath
	 * @throws Exception
	 */
	public List<List<Map<String, String>>> readExcel2(String filePath) throws Exception {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String, String>>>();
		// 检查
		this.preReadCheck(filePath);
		// 获取workbook对象
		Workbook workbook = null;
		workbook = this.getWorkbook(filePath);
		// 读文件 一个sheet一个sheet地读取
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();
			// 读取首行 即,表头
			Row firstRow = sheet.getRow(firstRowIndex + 1);
			if (null != firstRow) {
				// 读取数据行
				for (int rowIndex = firstRowIndex + 2; rowIndex <= lastRowIndex; rowIndex++) {
					// 当前行
					Row currentRow = sheet.getRow(rowIndex);
					if (currentRow == null) {
						continue;
					}
					// 首列
					int firstColumnIndex = currentRow.getFirstCellNum();
					// 最后一列
					int lastColumnIndex = currentRow.getLastCellNum();

					List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
					for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {
						Map<String, String> map2 = new HashMap<String, String>();
						// 标题列
						Cell titleCell = firstRow.getCell(columnIndex);
						// 标题列对应的值
						String titleCellValue = this.getCellValue(titleCell, true).trim();
						// 当前单元格
						Cell currentCell = currentRow.getCell(columnIndex);
						// 当前单元格的值
						String currentCellValue = this.getCellValue(currentCell, true);
						map2.put(titleCellValue, currentCellValue);
						rowList.add(map2);
					}
					list.add(rowList);
				}
			}
		}
		return list;
	}

	/**
	 * 读取excel文件内容（忽略标题栏）
	 * 
	 * @param filePath
	 *            文件地址
	 * @param n
	 *            忽略表头(n-1)行
	 * @throws Exception
	 */
	public List<List<Map<String, String>>> readExcel3(String filePath, Integer n) throws Exception {
		List<List<Map<String, String>>> list = new ArrayList<List<Map<String, String>>>();
		// 检查
		this.preReadCheck(filePath);
		// 获取workbook对象
		Workbook workbook = null;
		workbook = this.getWorkbook(filePath);
		// 读文件 一个sheet一个sheet地读取
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();
			// 读取首行 即,表头
			Row firstRow = sheet.getRow(firstRowIndex + n);
			if (null != firstRow) {
				// 读取数据行
				for (int rowIndex = firstRowIndex + 1 + n; rowIndex <= lastRowIndex; rowIndex++) {
					// 当前行
					Row currentRow = sheet.getRow(rowIndex);
					if (currentRow == null) {
						continue;
					}
					// 首列
					int firstColumnIndex = currentRow.getFirstCellNum();
					// 最后一列
					int lastColumnIndex = currentRow.getLastCellNum();

					List<Map<String, String>> rowList = new ArrayList<Map<String, String>>();
					for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {
						Map<String, String> map2 = new HashMap<String, String>();
						// 标题列
						Cell titleCell = firstRow.getCell(columnIndex);
						// 标题列对应的值
						String titleCellValue = this.getCellValue(titleCell, true).trim();
						// 当前单元格
						Cell currentCell = currentRow.getCell(columnIndex);
						// 当前单元格的值
						String currentCellValue = this.getCellValue(currentCell, true);
						map2.put(titleCellValue, currentCellValue);
						rowList.add(map2);
					}
					list.add(rowList);
				}
			}
		}
		return list;
	}

	/***
	 * <pre>
	 * 取得Workbook对象(xls和xlsx对象不同,不过都是Workbook的实现类)
	 *   xls:HSSFWorkbook
	 *   xlsx：XSSFWorkbook
	 * &#64;param filePath
	 * &#64;return
	 * &#64;throws IOException
	 * </pre>
	 */
	private Workbook getWorkbook(String filePath) throws IOException {

		Workbook workbook = null;
		InputStream is = null;

		try {
			is = new FileInputStream(filePath);
			if (filePath.endsWith(EXTENSION_XLS)) {
				workbook = new HSSFWorkbook(is);
			} else if (filePath.endsWith(EXTENSION_XLSX)) {
				workbook = new XSSFWorkbook(is);
			}

		} catch (FileNotFoundException e) {
			throw e;
		} catch (IOException e) {
			throw e;
		} finally {
			CommonsIOUtils.closeStream(is, null);
		}

		return workbook;
	}

	/**
	 * 文件检查
	 * 
	 * @param filePath
	 * @throws FileNotFoundException
	 * @throws FileFormatException
	 */
	private void preReadCheck(String filePath) throws FileNotFoundException, FileFormatException {
		// 常规检查
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("传入的文件不存在：" + filePath);
		}

		if (!(filePath.endsWith(EXTENSION_XLS) || filePath.endsWith(EXTENSION_XLSX))) {
			throw new FileFormatException("传入的文件不是excel");
		}
	}

	/**
	 * 取单元格的值
	 * 
	 * @param cell
	 *            单元格对象
	 * @param treatAsStr
	 *            为true时，当做文本来取值 (取到的是文本，不会把“1”取成“1.0”)
	 * @return
	 */
	private String getCellValue(Cell cell, boolean treatAsStr) {
		if (cell == null) {
			return "";
		}

		if (treatAsStr) {
			// 虽然excel中设置的都是文本，但是数字文本还被读错，如“1”取成“1.0”
			// 加上下面这句，临时把它当做文本来读取
			cell.setCellType(Cell.CELL_TYPE_STRING);
		}

		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}

	/**
	 * 修改Excel文件单元格
	 * 
	 * @param str
	 *            修改内容
	 * @param finalXlsxPath
	 *            文件地址
	 * @param i
	 *            行
	 * @param j
	 *            列
	 */
	public static void writeExcel(String str, String finalXlsxPath, int i, int j) {
		OutputStream out = null;
		try {
			// 读取Excel文档
			File finalXlsxFile = new File(finalXlsxPath);
			Workbook workBook = getWorkbok(finalXlsxFile);
			// sheet 对应一个工作页
			Sheet sheet = workBook.getSheetAt(0);
			// 修改单元格
			Row row = sheet.getRow(i);
			Cell cell = row.createCell(j);
			cell.setCellValue(str);
			CellStyle style = cell.getCellStyle();
			style.setFillForegroundColor(IndexedColors.RED.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			// 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
			out = new FileOutputStream(finalXlsxPath);
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断Excel的版本,获取Workbook
	 * 
	 * @param in
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static Workbook getWorkbok(File file) throws IOException {
		Workbook wb = null;
		FileInputStream in = new FileInputStream(file);
		if (file.getName().endsWith(Consts.EXCEL_XLS)) { // Excel 2003
			wb = new HSSFWorkbook(in);
		} else if (file.getName().endsWith(Consts.EXCEL_XLSX)) { // Excel
																	// 2007/2010
			wb = new XSSFWorkbook(in);
		}
		return wb;
	}

	/**
	 * 在excel中插入list
	 * 
	 * @param str
	 *            修改内容
	 * @param finalXlsxPath
	 *            文件地址
	 * @param i
	 *            行
	 * @param j
	 *            列
	 */
	public static void writeExcel1(String finalXlsxPath, List<MateriaExcel> list, int head) {
		OutputStream out = null;
		try {
			// 读取Excel文档
			File finalXlsxFile = new File(finalXlsxPath);
			Workbook workBook = getWorkbok(finalXlsxFile);
			// sheet 对应一个工作页
			Sheet sheet = workBook.getSheetAt(0);
			// 修改单元格
			Row row1 = sheet.getRow(head - 1);
			// 设置标题单元格格式
			XSSFCellStyle titleStyle = (XSSFCellStyle) workBook.createCellStyle();
			// 创建字体对象
			Font ztFont = workBook.createFont();
			ztFont.setFontHeightInPoints((short) 10); // 将字体大小设置为10px
			ztFont.setColor(Font.COLOR_RED); // 将字体设置为“红色”
			ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗
			titleStyle.setFont(ztFont);
			// 细边框
			titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
			titleStyle.setBorderTop(CellStyle.BORDER_THIN);
			titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
			titleStyle.setBorderRight(CellStyle.BORDER_THIN);
			titleStyle.setWrapText(true); // 自动换行
			titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
			// 单元格样式
			XSSFCellStyle cellStyle = (XSSFCellStyle) workBook.createCellStyle();
			Font cellFont = workBook.createFont();
			cellFont.setFontHeightInPoints((short) 10); // 将字体大小设置为10px
			cellStyle.setFont(cellFont);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setWrapText(true); // 自动换行
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中

			// 欠料数量
			Cell cell03 = row1.createCell(10);
			cell03.setCellValue("需求数量");
			cell03.setCellStyle(titleStyle);
			// 欠料数量
			Cell cell01 = row1.createCell(11);
			cell01.setCellValue("欠料数量");
			cell01.setCellStyle(titleStyle);
			// 库存备注
			Cell cell02 = row1.createCell(12);
			cell02.setCellValue("说明");
			cell02.setCellStyle(titleStyle);

			for (MateriaExcel me : list) {
				int rowNum = head++;
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					row = sheet.createRow(rowNum);
				}
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(me.getId());
				cell1.setCellStyle(cellStyle);
				Cell cell2 = row.createCell(1);
				cell2.setCellValue(me.getMateriaCode());
				cell2.setCellStyle(cellStyle);
				Cell cell3 = row.createCell(2);
				cell3.setCellValue(me.getNum());
				cell3.setCellStyle(cellStyle);
				Cell cell4 = row.createCell(3);
				cell4.setCellValue(me.getItemNum() + "");
				cell4.setCellStyle(cellStyle);
				Cell cell5 = row.createCell(4);
				cell5.setCellValue(me.getSpec());
				cell5.setCellStyle(cellStyle);
				Cell cell6 = row.createCell(5);
				cell6.setCellValue(me.getPackage1());
				cell6.setCellStyle(cellStyle);
				Cell cell7 = row.createCell(6);
				cell7.setCellValue(me.getType());
				cell7.setCellStyle(cellStyle);
				Cell cell8 = row.createCell(7);
				cell8.setCellValue(me.getClassify());
				cell8.setCellStyle(cellStyle);
				Cell cell9 = row.createCell(8);
				cell9.setCellValue(me.getBrand());
				cell9.setCellStyle(cellStyle);
				Cell cell13 = row.createCell(9);
				cell13.setCellValue(me.getContent());
				cell13.setCellStyle(cellStyle);
				Cell cell10 = row.createCell(10);
				cell10.setCellValue(me.getDemandNum());
				cell10.setCellStyle(cellStyle);
				Cell cell11 = row.createCell(11);
				if (me.getLackNum() == null) {
					cell11.setCellValue(0);
				} else {
					cell11.setCellValue(me.getLackNum());
				}
				cell11.setCellStyle(cellStyle);
				Cell cell12 = row.createCell(12);
				cell12.setCellValue(me.getRemark());
				cell12.setCellStyle(cellStyle);
			}
			// 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
			out = new FileOutputStream(finalXlsxPath);
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param fromFile
	 * @param toFile
	 * @throws IOException
	 */
	public static void copyFile(String fromFileUrl, String toFileUrl) throws IOException {
		File fromFile = new File(fromFileUrl);
		File toFile = new File(toFileUrl);
		if (!toFile.exists()) {
			toFile.createNewFile();
		}
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = ins.read(b)) != -1) {
			out.write(b, 0, n);
		}
		ins.close();
		out.close();
	}

	public static XSSFCellStyle setStyle(Workbook wb) {
		XSSFCellStyle ztStyle = (XSSFCellStyle) wb.createCellStyle();
		// 创建字体对象
		Font ztFont = wb.createFont();
		ztFont.setItalic(true); // 设置字体为斜体字
		ztFont.setColor(Font.COLOR_RED); // 将字体设置为“红色”
		ztFont.setFontHeightInPoints((short) 22); // 将字体大小设置为18px
		ztFont.setFontName("华文行楷"); // 将“华文行楷”字体应用到当前单元格上
		ztFont.setUnderline(Font.U_DOUBLE); // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）
		ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗
		// ztFont.setStrikeout(true); // 是否添加删除线
		ztStyle.setFont(ztFont);
		// CellStyle.BORDER_DOUBLE 双边线
		// CellStyle.BORDER_THIN 细边线
		// CellStyle.BORDER_MEDIUM 中等边线
		// CellStyle.BORDER_DASHED 虚线边线
		// CellStyle.BORDER_HAIR 小圆点虚线边线
		// CellStyle.BORDER_THICK 粗边线
		ztStyle.setBorderBottom(CellStyle.BORDER_THICK);
		ztStyle.setBorderTop(CellStyle.BORDER_DASHED);
		ztStyle.setBorderLeft(CellStyle.BORDER_DOUBLE);
		ztStyle.setBorderRight(CellStyle.BORDER_THIN);
		ztStyle.setWrapText(true); // 自动换行
		ztStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
		// ztCell.setCellStyle(ztStyle); // 样式应用到该单元格上
		return ztStyle;
	}

	public static void writeExcel2(String finalXlsxPath, List<MateriaExcel> list, int head) {
		OutputStream out = null;
		try {
			// 读取Excel文档
			File finalXlsxFile = new File(finalXlsxPath);
			Workbook workBook = getWorkbok(finalXlsxFile);
			// sheet 对应一个工作页
			Sheet sheet = workBook.getSheetAt(0);
			// 修改单元格
			Row row1 = sheet.getRow(head - 1);
			// 设置标题单元格格式
			XSSFCellStyle titleStyle = (XSSFCellStyle) workBook.createCellStyle();
			// 创建字体对象
			Font ztFont = workBook.createFont();
			ztFont.setFontHeightInPoints((short) 10); // 将字体大小设置为10px
			ztFont.setColor(Font.COLOR_RED); // 将字体设置为“红色”
			ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD); // 字体加粗
			titleStyle.setFont(ztFont);
			// 细边框
			titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
			titleStyle.setBorderTop(CellStyle.BORDER_THIN);
			titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
			titleStyle.setBorderRight(CellStyle.BORDER_THIN);
			titleStyle.setWrapText(true); // 自动换行
			titleStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
			// 单元格样式
			XSSFCellStyle cellStyle = (XSSFCellStyle) workBook.createCellStyle();
			Font cellFont = workBook.createFont();
			cellFont.setFontHeightInPoints((short) 10); // 将字体大小设置为10px
			cellStyle.setFont(cellFont);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setWrapText(true); // 自动换行
			cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中

			// 欠料数量
			Cell cell03 = row1.createCell(8);
			cell03.setCellValue("需求数量");
			cell03.setCellStyle(titleStyle);
			// 欠料数量
			Cell cell01 = row1.createCell(9);
			cell01.setCellValue("欠料数量");
			cell01.setCellStyle(titleStyle);
			// 库存备注
			Cell cell02 = row1.createCell(10);
			cell02.setCellValue("说明");
			cell02.setCellStyle(titleStyle);

			for (MateriaExcel me : list) {
				int rowNum = head++;
				Row row = sheet.getRow(rowNum);
				if (row == null) {
					row = sheet.createRow(rowNum);
				}
				Cell cell1 = row.createCell(0);
				cell1.setCellValue(me.getId());
				cell1.setCellStyle(cellStyle);
				Cell cell2 = row.createCell(1);
				cell2.setCellValue(me.getMateriaCode());
				cell2.setCellStyle(cellStyle);
				Cell cell3 = row.createCell(2);
				cell3.setCellValue(me.getClassify());
				cell3.setCellStyle(cellStyle);
				Cell cell4 = row.createCell(3);
				cell4.setCellValue(me.getSpec());
				cell4.setCellStyle(cellStyle);
				Cell cell5 = row.createCell(4);
				cell5.setCellValue(me.getPackage1());
				cell5.setCellStyle(cellStyle);
				Cell cell6 = row.createCell(5);
				cell6.setCellValue(me.getUnit());
				cell6.setCellStyle(cellStyle);
				Cell cell7 = row.createCell(6);
				cell7.setCellValue(me.getNum());
				cell7.setCellStyle(cellStyle);
				Cell cell8 = row.createCell(7);
				cell8.setCellValue(me.getContent());
				cell8.setCellStyle(cellStyle);
				Cell cell9 = row.createCell(8);
				cell9.setCellValue(me.getDemandNum());
				cell9.setCellStyle(cellStyle);
				Cell cell11 = row.createCell(9);
				if (me.getLackNum() == null) {
					cell11.setCellValue(0);
				} else {
					cell11.setCellValue(me.getLackNum());
				}
				cell11.setCellStyle(cellStyle);
				Cell cell12 = row.createCell(10);
				cell12.setCellValue(me.getRemark());
				cell12.setCellStyle(cellStyle);
			}
			// 创建文件输出流，输出电子表格：这个必须有，否则你在sheet上做的任何操作都不会有效
			out = new FileOutputStream(finalXlsxPath);
			workBook.write(out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public JSONArray getExcelJSON(String filePath, Integer n) throws Exception {
		JSONArray arr = new JSONArray();
		// 检查
		this.preReadCheck(filePath);
		// 获取workbook对象
		Workbook workbook = null;
		workbook = this.getWorkbook(filePath);
		// 读文件 一个sheet一个sheet地读取
		for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
			Sheet sheet = workbook.getSheetAt(numSheet);
			if (sheet == null) {
				continue;
			}
			int firstRowIndex = sheet.getFirstRowNum();
			int lastRowIndex = sheet.getLastRowNum();
			// 读取首行 即,表头
			Row firstRow = sheet.getRow(firstRowIndex + n);
			if (null != firstRow) {
				// 读取数据行
				for (int rowIndex = firstRowIndex + 1 + n; rowIndex <= lastRowIndex; rowIndex++) {
					// 当前行
					Row currentRow = sheet.getRow(rowIndex);
					if (currentRow == null) {
						continue;
					}
					// 首列
					int firstColumnIndex = currentRow.getFirstCellNum();
					// 最后一列
					int lastColumnIndex = currentRow.getLastCellNum();

					JSONObject obj = new JSONObject();
					for (int columnIndex = firstColumnIndex; columnIndex < lastColumnIndex; columnIndex++) {
						// 标题列
						Cell titleCell = firstRow.getCell(columnIndex);
						// 标题列对应的值
						String titleCellValue = this.getCellValue(titleCell, true).trim();
						// 当前单元格
						Cell currentCell = currentRow.getCell(columnIndex);
						// 当前单元格的值
						String currentCellValue = this.getCellValue(currentCell, true);
						obj.append(titleCellValue, currentCellValue);
					}
					arr.put(obj);
				}
			}
		}
		return arr;
	}

}
