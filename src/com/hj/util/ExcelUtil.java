package com.hj.util;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.Model;

import jxl.Cell;
import jxl.CellView;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.hj.oa.Consts;
import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.CheckIn;
import com.hj.oa.bean.Day;
import com.hj.oa.bean.DcCaidanVo;
import com.hj.oa.bean.DcEmpBalance;
import com.hj.oa.bean.DcEmpBalanceRecord;
import com.hj.oa.bean.Evaluation;
import com.hj.oa.bean.JiaBan;
import com.hj.oa.bean.LabAll;
import com.hj.oa.bean.LabPcb;
import com.hj.oa.bean.LabPcbSq;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.Materia;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.MateriaPurchaseDetail;
import com.hj.oa.bean.OsWupin;
import com.hj.oa.bean.OsWupinVo;
import com.hj.oa.bean.PurchaseRecord;
import com.hj.oa.bean.SealApplyDetail;
import com.hj.oa.bean.User;
import com.hj.oa.bean.ZcPropCg;
import com.hj.oa.bean.ZcPropLog;
import com.hj.oa.bean.ZcPropLy;
import com.hj.oa.bean.ZcPropWbVo;
import com.hj.oa.bean.ZcProperty;
import com.hj.oa.service.DateService;

public class ExcelUtil {

	/**
	 * zeroth_cat create excel for cb_recrod
	 * 
	 * @param emp_bal
	 * @param start_date
	 * @param end_date
	 */
	public static File cb_rec_excel(List<DcEmpBalanceRecord> emp_bal, String start_date, String end_date) {
		try {
			String date_now = DateUtil.getCurrentTime(Consts.chinaDateFmt);

			String[] title = { "交易时间", "姓名", "类型", "金额", "余额", "备注" };

			File path = new File("D:/cb_rec");
			if (!path.exists()) {
				path.mkdirs();
			}

			String file_str = start_date + "-" + end_date + ".xls";

			File file = new File("D:/cb_rec/" + file_str);
			// System.out.println(file.getName());
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("testing", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int col = 0;
			for (int i = 0; i < emp_bal.size(); i++) {
				int r = i + 1;

				DcEmpBalanceRecord prop = emp_bal.get(i);

				label = new Label(col++, r, prop.getDayTime());
				ws.addCell(label);

				label = new Label(col++, r, prop.getEmpName());
				ws.addCell(label);

				label = new Label(col++, r, prop.getType());
				ws.addCell(label);

				label = new Label(col++, r, Double.toString(prop.getMoney()));
				ws.addCell(label);

				label = new Label(col++, r, Double.toString(prop.getBalance()));
				ws.addCell(label);

				label = new Label(col++, r, prop.getBz());
				ws.addCell(label);

				col = 0;
			}

			// write in the data
			wb.write();
			// close the path
			wb.close();

			return file;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * zeroth_cat creates excel of all di_can records between start date and end
	 * date
	 * 
	 * @param d_can_rec
	 * @param start_date
	 * @param end_date
	 */
	public static File di_can_rec(List<DcCaidanVo> d_can_rec, String start_date, String end_date) {
		try {
			String date_now = DateUtil.getCurrentTime(Consts.chinaDateFmt);

			String[] title = { "日期", "姓名", "商家名称", "描述", "价格", "数量", "总金额" };

			File path = new File("D:/dian_can_rec");
			if (!path.exists()) {
				path.mkdirs();
			}

			String file_str = start_date + "-" + end_date + ".xls";

			File file = new File("D:/dian_can_rec/" + file_str);
			// System.out.println(file.getName());
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("testing", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int col = 0;
			for (int i = 0; i < d_can_rec.size(); i++) {
				int r = i + 1;

				DcCaidanVo prop = d_can_rec.get(i);

				label = new Label(col++, r, prop.getDayStr());
				ws.addCell(label);

				label = new Label(col++, r, prop.getEmpName());
				ws.addCell(label);

				label = new Label(col++, r, prop.getProviderName());
				ws.addCell(label);

				label = new Label(col++, r, prop.getDescription());
				ws.addCell(label);

				jxl.write.NumberFormat nf = new jxl.write.NumberFormat("#"); // 设置数字格式
				jxl.write.WritableCellFormat wcfN = new jxl.write.WritableCellFormat(nf); // 设置表单格式

				double price = prop.getPrice();
				jxl.write.Number labelPrice = new jxl.write.Number(col++, r, price, wcfN); // 格式化数值
				ws.addCell(labelPrice);

				double num = prop.getNum();
				jxl.write.Number labelNum = new jxl.write.Number(col++, r, num, wcfN); // 格式化数值
				ws.addCell(labelNum);

				double priceAll = prop.getPriceAll();
				jxl.write.Number labelAll = new jxl.write.Number(col++, r, priceAll, wcfN); // 格式化数值
				ws.addCell(labelAll);

				col = 0;
			}

			// write in the data
			wb.write();
			// close the path
			wb.close();

			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * zeroth_cat based on the balances give an excel representation at
	 * D:/emp_chong_zhi/{date}
	 * 
	 * @param balances
	 */
	public static File chong_zhi_excel(List<DcEmpBalance> balances) {
		try {
			String date_now = DateUtil.getCurrentTime(Consts.chinaDateFmt);

			String[] title = { "姓名", "账户余额" };

			File path = new File("D:/emp_chong_zhi");
			if (!path.exists()) {
				path.mkdirs();
			}

			String file_str = date_now + ".xls";

			File file = new File("D:/emp_chong_zhi/" + file_str);
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("testing", 0);
			// System.out.println(file.getName());
			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int col = 0;
			double sum = 0;
			for (int i = 0; i < balances.size(); i++) {
				int r = i + 1;

				DcEmpBalance prop = balances.get(i);

				label = new Label(col++, r, prop.getEmpName());
				ws.addCell(label);

				label = new Label(col++, r, Double.toString(prop.getBalance()));
				sum += prop.getBalance();
				ws.addCell(label);

				col = 0;
			}

			int r = balances.size() + 1;
			label = new Label(col++, r, "总金额");
			ws.addCell(label);
			label = new Label(col++, r, Double.toString(sum));
			ws.addCell(label);
			col = 0;

			// write in the data
			wb.write();
			// close the path
			wb.close();

			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void generateForProp(List<ZcProperty> list, HashMap<Integer, String> deptMap) {
		try {
			String[] title = { "资产类别", "资产编号", "购买时间", "负责人", "部门", "放置地点", "资产名称", "规格型号", "单价", "备注" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/资产汇总.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("资产汇总", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				ZcProperty prop = list.get(i);
				String type = prop.getType();
				String code = prop.getCode();
				String buyDate = prop.getBuyDate();
				String empName = prop.getEmpName();
				String deptName = deptMap.get(prop.getEmpId());
				String place = prop.getPlaceName();
				String name = prop.getName();
				String spec = prop.getSpec();
				String price = prop.getPrice() + "";
				String bz = prop.getBz();

				label = new Label(column++, r, type);
				ws.addCell(label);

				label = new Label(column++, r, code);
				ws.addCell(label);

				label = new Label(column++, r, buyDate);
				ws.addCell(label);

				label = new Label(column++, r, empName);
				ws.addCell(label);

				label = new Label(column++, r, deptName);
				ws.addCell(label);

				label = new Label(column++, r, place);
				ws.addCell(label);

				label = new Label(column++, r, name);
				ws.addCell(label);

				label = new Label(column++, r, spec);
				ws.addCell(label);

				label = new Label(column++, r, price);
				ws.addCell(label);

				label = new Label(column++, r, bz);
				ws.addCell(label);

				column = 0;
			}

			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static File exportPurchaseProperty(List<ZcPropCg> list) {
		try {
			String[] title = { "申请人", "申请时间", "资产名称", "价格", "备注" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/exportPurchaseProperty.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("资产采购记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				ZcPropCg prop = list.get(i);
				String proposerName = prop.getProposerName();
				String createTime = prop.getCreateTime();
				String name = prop.getName();
				String money = prop.getMoney() + "";
				String content = prop.getContent();

				label = new Label(column++, r, proposerName);
				ws.addCell(label);
				label = new Label(column++, r, createTime);
				ws.addCell(label);
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, money);
				ws.addCell(label);
				label = new Label(column++, r, content);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static File exportAllProperty(List<ZcProperty> list) {
		try {
			String[] title = { "入库时间", "购买时间", "资产类别", "资产编号", "资产名称", "规格型号", "单位", "单价", "负责人", "放置地点", "备注" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/exportAllProperty.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("全部资产记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				ZcProperty prop = list.get(i);
				String storeTime = prop.getStoreTime();
				String buyDate = prop.getBuyDate();
				String type = prop.getType();
				String code = prop.getCode();
				String name = prop.getName();
				String spec = prop.getSpec();
				String unit = prop.getUnit();
				String price = prop.getPrice() + "";
				String empName = prop.getEmpName();
				String place = prop.getPlaceName();
				String bz = prop.getBz();
				label = new Label(column++, r, storeTime);
				ws.addCell(label);
				label = new Label(column++, r, buyDate);
				ws.addCell(label);
				label = new Label(column++, r, type);
				ws.addCell(label);
				label = new Label(column++, r, code);
				ws.addCell(label);
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, spec);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				label = new Label(column++, r, price);
				ws.addCell(label);
				label = new Label(column++, r, empName);
				ws.addCell(label);
				label = new Label(column++, r, place);
				ws.addCell(label);
				label = new Label(column++, r, bz);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 导出物料信息
	 * 
	 * @param list
	 * @return
	 */
	public static File exportMateria(List<Materia> list, int type) {
		try {
			String[] title;
			if (type == 1) {
				title = new String[] { "物料编号", "品名分类", "品牌", "录入时间", "规格型号", "封装", "单位", "单价", "供应商", "中文简称", "英文简称",
						"功能区分", "芯片研发中心仓库", "工业控制中心仓库", "深圳技术部仓库" };
			} else {
				title = new String[] { "物料编号", "品名分类", "品牌", "录入时间", "规格型号", "封装", "单位", "供应商", "中文简称", "英文简称",
						"功能区分" };
			}
			File path = new File("D:/web/upload/materias/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/materias/materias.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("物料", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				Materia ma = list.get(i);
				String materiaCode = ma.getMateriaCode();
				String classfiy = ma.getClassfiy();
				String brand = ma.getBrand();
				String createTime = ma.getCreateTime();
				String spec = ma.getSpec();
				String package1 = ma.getPackage1();
				String unit = ma.getUnit();
				Double price = ma.getPrice();
				if (price == null) {
					price = 0.0;
				}
				String supplier = ma.getSupplier();
				String functionChina = ma.getFunctionChina();
				String functionEnglish = ma.getFunctionEnglish();
				String diff = ma.getDiff();
				String warehouse1 = ma.getWarehouse1() + "";
				String warehouse2 = ma.getWarehouse2() + "";
				String warehouse3 = ma.getWarehouse3() + "";

				label = new Label(column++, r, materiaCode);
				ws.addCell(label);
				label = new Label(column++, r, classfiy);
				ws.addCell(label);
				label = new Label(column++, r, brand);
				ws.addCell(label);
				label = new Label(column++, r, createTime);
				ws.addCell(label);
				label = new Label(column++, r, spec);
				ws.addCell(label);
				label = new Label(column++, r, package1);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				if (type == 1) {
					label = new Label(column++, r, price.toString());
					ws.addCell(label);
				}
				label = new Label(column++, r, supplier);
				ws.addCell(label);
				label = new Label(column++, r, functionChina);
				ws.addCell(label);
				label = new Label(column++, r, functionEnglish);
				ws.addCell(label);
				label = new Label(column++, r, diff);
				ws.addCell(label);
				if (type == 1) {
					label = new Label(column++, r, warehouse1);
					ws.addCell(label);
					label = new Label(column++, r, warehouse2);
					ws.addCell(label);
					label = new Label(column++, r, warehouse3);
					ws.addCell(label);
				}
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static File propAddRecord(List<ZcProperty> list) {
		try {
			String[] title = { "入库时间", "购买时间", "资产类别", "资产编号", "资产名称", "规格型号", "单位", "单价", "负责人", "放置地点", "备注" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/addPropertyRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("资产添加记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				ZcProperty prop = list.get(i);
				String storeTime = prop.getStoreTime();
				String buyDate = prop.getBuyDate();
				String type = prop.getType();
				String code = prop.getCode();
				String name = prop.getName();
				String spec = prop.getSpec();
				String unit = prop.getUnit();
				String price = prop.getPrice() + "";
				String empName = prop.getEmpName();
				String place = prop.getPlaceName();
				String bz = prop.getBz();
				label = new Label(column++, r, storeTime);
				ws.addCell(label);
				label = new Label(column++, r, buyDate);
				ws.addCell(label);
				label = new Label(column++, r, type);
				ws.addCell(label);
				label = new Label(column++, r, code);
				ws.addCell(label);
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, spec);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				label = new Label(column++, r, price);
				ws.addCell(label);
				label = new Label(column++, r, empName);
				ws.addCell(label);
				label = new Label(column++, r, place);
				ws.addCell(label);
				label = new Label(column++, r, bz);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static File propRecord(List<ZcPropLog> list) {
		try {
			String[] title = { "负责人", "资产名称", "资产编号", "资产类别", "规格型号", "放置地点", "时间", "备注" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/propertyReventRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("资产记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				ZcPropLog prop = list.get(i);
				String empName = prop.getEmpName();
				String name = prop.getName();
				String code = prop.getCode();
				String type = prop.getType();
				String spec = prop.getSpec();
				String place = prop.getPlaceName();
				String dayStr = prop.getDayStr();
				String info = prop.getInfo();

				label = new Label(column++, r, empName);
				ws.addCell(label);
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, code);
				ws.addCell(label);
				label = new Label(column++, r, type);
				ws.addCell(label);
				label = new Label(column++, r, spec);
				ws.addCell(label);
				label = new Label(column++, r, place);
				ws.addCell(label);
				label = new Label(column++, r, dayStr);
				ws.addCell(label);
				label = new Label(column++, r, info);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO
	public static File exportStock(List<OsWupin> list) {
		try {
			String[] title = { "物品名称", "物品编号", "类型", "规格型号", "单位", "库存数量" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/stockRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("办公用品库存记录", 0);
			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				OsWupin prop = list.get(i);
				String name = prop.getName();
				String code = prop.getCode();
				String classify = prop.getClassify();
				String type = prop.getType();
				String unit = prop.getUnit();
				String stock = prop.getStock() + "";

				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, code);
				ws.addCell(label);
				label = new Label(column++, r, classify);
				ws.addCell(label);
				label = new Label(column++, r, type);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				label = new Label(column++, r, stock);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO
	public static File exportPurchase(List<PurchaseRecord> list) {
		try {
			String[] title = { "采购人", "采购时间", "名称", "类型", "规格型号", "单位", "数量", "金额", "备注" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/purchaseRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("办公用品采购记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				PurchaseRecord prop = list.get(i);
				String proposerName = prop.getProposerName();
				String createTime = prop.getCreateTime();
				String name = prop.getName();
				String type = prop.getType();
				String classify = prop.getClassify();
				String unit = prop.getUnit();
				String num = prop.getNum() + "";
				String money = prop.getMoney() + "";
				String bz = prop.getBz();

				label = new Label(column++, r, proposerName);
				ws.addCell(label);
				label = new Label(column++, r, createTime);
				ws.addCell(label);
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, type);
				ws.addCell(label);
				label = new Label(column++, r, classify);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				label = new Label(column++, r, num);
				ws.addCell(label);
				label = new Label(column++, r, money);
				ws.addCell(label);
				label = new Label(column++, r, bz);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO
	public static File exportConsume(List<OsWupinVo> list) {
		try {
			String[] title = { "物品名称", "规格型号", "数量", "单位" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/consumeRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("办公用品消耗记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				OsWupinVo prop = list.get(i);
				String name = prop.getName();
				String type = prop.getType();
				String num = prop.getNum() + "";
				String unit = prop.getType();
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, type);
				ws.addCell(label);
				label = new Label(column++, r, num);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO
	public static File propGrantRecord(List<ZcPropLy> list) {
		try {
			String[] title = { "申领人", "申领时间", "资产名称", "备注" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/propertyGrantRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("资产发放记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				ZcPropLy prop = list.get(i);
				String proposerName = prop.getProposerName();
				String createTime = prop.getCreateTime();
				String name = prop.getName();
				String bz = prop.getBz();
				label = new Label(column++, r, proposerName);
				ws.addCell(label);
				label = new Label(column++, r, createTime);
				ws.addCell(label);
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, bz);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO
	public static File retroactiveRecord(List<BKqSp> list) {
		try {
			String[] title = { "补签类型", "申请人", "申请时间", "考勤日期", "上班打卡", "下班打卡", "审批状态" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/checkinRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("考勤打卡记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				BKqSp prop = list.get(i);
				String style = prop.getStyle();
				String proposerName = prop.getProposerName();
				String createTime = prop.getCreateTime();
				String dayStr = prop.getDayStr();
				String checkIn = prop.getCheckin();
				String checkOut = prop.getCheckout();
				String statusName = prop.getStatusName();
				label = new Label(column++, r, style);
				ws.addCell(label);
				label = new Label(column++, r, proposerName);
				ws.addCell(label);
				label = new Label(column++, r, createTime);
				ws.addCell(label);
				label = new Label(column++, r, dayStr);
				ws.addCell(label);
				label = new Label(column++, r, checkIn);
				ws.addCell(label);
				label = new Label(column++, r, checkOut);
				ws.addCell(label);
				label = new Label(column++, r, statusName);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// TODO
	public static File propRepairRecord(List<ZcPropWbVo> list) {
		try {
			String[] title = { "申请人", "申请时间", "资产名称", "维修价格", "资产编号", "备注", "审批结果" };
			File path = new File("D:/web/upload/props/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/props/propertyRepairRecord.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("资产维修记录", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				ZcPropWbVo prop = list.get(i);
				String proposerName = prop.getProposerName();
				String createTime = prop.getCreateTime();
				String name = prop.getName();
				String money = prop.getMoney() + "";
				String code = prop.getCode();
				String ApproveStatus = prop.getApproveStatus();
				String content = prop.getContent();
				label = new Label(column++, r, proposerName);
				ws.addCell(label);
				label = new Label(column++, r, createTime);
				ws.addCell(label);
				label = new Label(column++, r, name);
				ws.addCell(label);
				label = new Label(column++, r, money);
				ws.addCell(label);
				label = new Label(column++, r, code);
				ws.addCell(label);
				label = new Label(column++, r, ApproveStatus);
				ws.addCell(label);
				label = new Label(column++, r, content);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static File generateForKqHuiZong(List<HashMap> list, String yf, String userName, String url) {
		try {
			String[] title = { "姓名", "日期", "是否休息", "上班打卡", "下班打卡", "请假时长（分钟）", "不正常（分钟）", "出差时长（分钟）", "考勤类型", "开始时间",
					"结束时间", "补打卡原因" };
			File path = new File(url + yf + "/");// "D:/web/upload/kq/"
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File(url + yf + "/" + userName + "考勤.xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("考勤", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}

			int size = list.size();
			int column = 0;
			for (int i = 0; i < list.size(); i++) {
				int r = i + 1;
				HashMap map = list.get(i);
				String empName = map.get("empName") == null ? "" : map.get("empName").toString();
				String dayStr = map.get("dayStr") == null ? "" : map.get("dayStr").toString();
				String dayType = map.get("dayType") == null ? "" : map.get("dayType").toString();
				String checkin = map.get("checkin") == null ? "" : map.get("checkin").toString();
				String checkout = map.get("checkout") == null ? "" : map.get("checkout").toString();
				String qingjiaLen = map.get("qingjiaLen") == null ? "" : map.get("qingjiaLen").toString();
				String uncLen = map.get("uncLen") == null ? "" : map.get("uncLen").toString();
				String wcLen = map.get("wcLen") == null ? "" : map.get("wcLen").toString();
				String qjType = map.get("qjType") == null ? "" : map.get("qjType").toString();
				String beginTime = map.get("beginTime") == null ? "" : map.get("beginTime").toString();
				String endTime = map.get("endTime") == null ? "" : map.get("endTime").toString();
				String qj = map.get("qj") == null ? "" : map.get("qj").toString();
				String bkq = map.get("bkq") == null ? "" : map.get("bkq").toString();

				label = new Label(column++, r, empName);
				ws.addCell(label);

				label = new Label(column++, r, dayStr);
				ws.addCell(label);

				label = new Label(column++, r, dayType);
				ws.addCell(label);

				label = new Label(column++, r, checkin);
				ws.addCell(label);

				label = new Label(column++, r, checkout);
				ws.addCell(label);

				if ("产假".equals(qjType) || "婚假".equals(qjType)) {
					label = new Label(column++, r, "480");
				} else {
					label = new Label(column++, r, qingjiaLen);
				}
				ws.addCell(label);

				label = new Label(column++, r, uncLen);
				ws.addCell(label);

				label = new Label(column++, r, wcLen);
				ws.addCell(label);

				label = new Label(column++, r, qjType);
				ws.addCell(label);

				label = new Label(column++, r, beginTime);
				ws.addCell(label);

				label = new Label(column++, r, endTime);
				ws.addCell(label);

				label = new Label(column++, r, bkq);
				ws.addCell(label);

				column = 0;
			}

			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// 解析请假、或者外出
	public static List<Leave> getQjFromFile(int empId, InputStream is, int waichu, DateService dateService) {

		List<Leave> list = new ArrayList<Leave>();
		Workbook wb = null;

		try {
			wb = Workbook.getWorkbook(is);
			if (wb == null) {
				return list;
			}
			Sheet[] sheets = wb.getSheets();
			if (sheets == null || sheets.length == 0) {
				return list;
			}
			Sheet sheet = wb.getSheet(0);
			int rows_len = sheet.getRows();

			for (int j = 0; j < rows_len; j++) {

				if (j == 0) {// 忽略第一行
					continue;
				}

				// 获取当前行所有的列
				Cell[] cells = sheet.getRow(j);
				if (cells == null || cells.length < 5) {
					break;
				}

				String proposerName = cells[0].getContents().trim();
				String createTime = cells[1].getContents().trim();
				String beginTime = cells[1].getContents().trim();
				String endTime = cells[2].getContents().trim();
				String type = cells[3].getContents().trim();
				String content = cells[4].getContents().trim();

				Leave l = new Leave();

				l.setProposer(empId);
				l.setProposerName(proposerName);
				l.setBeginTime(beginTime);
				l.setContent(content);
				l.setCreateTime(createTime);
				l.setContent(content);
				l.setEndTime(endTime);
				l.setWaichu(waichu);
				l.setType(type);
				l.setBossCmt("同意");
				l.setBossId(1);
				l.setStatus(4);
				// l.set

				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

				Date d1 = sdf1.parse(l.getBeginTime());
				Date d2 = sdf1.parse(l.getEndTime());
				List<Day> days = dateService.findDays(sdf2.format(d1), sdf2.format(d2));
				DateUtil du = new DateUtil();
				int mits = du.countMunitesForLeave(d1, d2, days);

				int _days = mits / 480;
				int minutes = mits % 480;
				int hours = minutes / 60;
				minutes = minutes % 60;

				l.setDays(_days);
				l.setHours(hours);
				l.setMinutes(minutes);

				list.add(l);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}

		return list;
	}

	// 解析考勤加班
	public static List<JiaBan> getJiabanFromFile(int empId, InputStream is) {
		List<JiaBan> list = new ArrayList<JiaBan>();
		Workbook wb = null;

		try {
			wb = Workbook.getWorkbook(is);
			if (wb == null) {
				return list;
			}
			Sheet[] sheets = wb.getSheets();
			if (sheets == null || sheets.length == 0) {
				return list;
			}
			Sheet sheet = wb.getSheet(0);
			int rows_len = sheet.getRows();

			for (int j = 0; j < rows_len; j++) {

				if (j == 0) {// 忽略第一行
					continue;
				}

				// 获取当前行所有的列
				Cell[] cells = sheet.getRow(j);
				if (cells == null || cells.length < 4) {
					continue;
				}

				String name = cells[0].getContents();
				String dayStr = cells[1].getContents();
				String timeLenStr = cells[2].getContents();
				String bz = cells[3].getContents();

				int timeLen = Integer.parseInt(timeLenStr);

				if (StringUtils.isEmpty(dayStr)) {
					continue;
				}

				JiaBan jb = new JiaBan();

				jb.setProposer(empId);
				jb.setProposerName(name);

				dayStr = dayStr.trim();
				jb.setDayte(dayStr);
				jb.setCreateTime(dayStr + " 20时30分");
				jb.setContent(bz);
				jb.setHours(timeLen);
				jb.setBossCmt("同意");
				jb.setBossId(1);
				jb.setStatus(4);

				list.add(jb);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}

		return list;
	}

	// 解析请假、或者外出
	public static List<Leave> getQjFromFile(InputStream is, int waichu, DateService dateService,
			Map<String, User> uMap) {

		List<Leave> list = new ArrayList<Leave>();
		Workbook wb = null;

		try {
			wb = Workbook.getWorkbook(is);
			if (wb == null) {
				return list;
			}
			Sheet[] sheets = wb.getSheets();
			if (sheets == null || sheets.length == 0) {
				return list;
			}
			Sheet sheet = wb.getSheet(0);
			int rows_len = sheet.getRows();

			for (int j = 0; j < rows_len; j++) {

				if (j == 0) {// 忽略第一行
					continue;
				}

				// 获取当前行所有的列
				Cell[] cells = sheet.getRow(j);
				if (cells == null || cells.length < 5) {
					break;
				}

				String proposerName = cells[0].getContents().trim();
				String createTime = cells[1].getContents().trim();
				String beginTime = cells[1].getContents().trim();
				String endTime = cells[2].getContents().trim();
				String type = cells[3].getContents().trim();
				String content = cells[4].getContents().trim();

				Leave l = new Leave();

				l.setProposer(uMap.get(proposerName).getId());
				l.setProposerName(proposerName);
				l.setBeginTime(beginTime);
				l.setContent(content);
				l.setCreateTime(createTime);
				l.setContent(content);
				l.setEndTime(endTime);
				l.setWaichu(waichu);
				l.setType(type);
				l.setBossCmt("同意");
				l.setBossId(1);
				l.setStatus(4);
				// l.set

				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

				Date d1 = sdf1.parse(l.getBeginTime());
				Date d2 = sdf1.parse(l.getEndTime());
				List<Day> days = dateService.findDays(sdf2.format(d1), sdf2.format(d2));
				DateUtil du = new DateUtil();
				int mits = du.countMunitesForLeave(d1, d2, days);

				int _days = mits / 480;
				int minutes = mits % 480;
				int hours = minutes / 60;
				minutes = minutes % 60;

				l.setDays(_days);
				l.setHours(hours);
				l.setMinutes(minutes);

				list.add(l);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}

		return list;
	}

	// 解析考勤加班
	public static List<JiaBan> getJiabanFromFile(InputStream is, Map<String, User> uMap) {
		List<JiaBan> list = new ArrayList<JiaBan>();
		Workbook wb = null;

		try {
			wb = Workbook.getWorkbook(is);
			if (wb == null) {
				return list;
			}
			Sheet[] sheets = wb.getSheets();
			if (sheets == null || sheets.length == 0) {
				return list;
			}
			Sheet sheet = wb.getSheet(0);
			int rows_len = sheet.getRows();

			for (int j = 0; j < rows_len; j++) {

				if (j == 0) {// 忽略第一行
					continue;
				}

				// 获取当前行所有的列
				Cell[] cells = sheet.getRow(j);
				if (cells == null || cells.length < 4) {
					continue;
				}

				String name = cells[0].getContents().trim();
				String dayStr = cells[1].getContents();
				String timeLenStr = cells[2].getContents();
				String bz = cells[3].getContents();

				int timeLen = Integer.parseInt(timeLenStr);

				if (StringUtils.isEmpty(dayStr)) {
					continue;
				}

				JiaBan jb = new JiaBan();

				User u = uMap.get(name);

				jb.setProposer(u.getId());
				jb.setProposerName(name);

				dayStr = dayStr.trim();
				jb.setDayte(dayStr);
				jb.setCreateTime(dayStr + " 20时30分");
				jb.setContent(bz);
				jb.setHours(timeLen);
				jb.setBossCmt("同意");
				jb.setBossId(1);
				jb.setStatus(4);

				list.add(jb);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}

		return list;
	}

	public static List<CheckIn> getCheckInFromFile(InputStream is, Map<String, User> uMap) {

		List<CheckIn> list = new ArrayList<CheckIn>();
		Workbook wb = null;

		try {
			wb = Workbook.getWorkbook(is);
			if (wb == null) {
				return list;
			}
			Sheet[] sheets = wb.getSheets();
			if (sheets == null || sheets.length == 0) {
				return list;
			}
			Sheet sheet = wb.getSheet(0);
			int rows_len = sheet.getRows();

			for (int j = 0; j < rows_len; j++) {

				if (j == 0) {// 忽略第一行
					continue;
				}

				// 获取当前行所有的列
				Cell[] cells = sheet.getRow(j);
				if (cells == null || cells.length < 2) {
					continue;
				}

				String uName = cells[0].getContents().trim();
				String dayStr = cells[1].getContents();
				String checkin = null;
				String checkout = null;

				if (cells.length >= 3) {
					checkin = cells[2].getContents();
					if (checkin.length() == 4) {
						checkin = "0" + checkin;
					}
				}
				if (cells.length >= 4) {
					checkout = cells[3].getContents();
					if (checkout.length() == 4) {
						checkout = "0" + checkout;
					}
				}

				if (StringUtils.isEmpty(dayStr)) {
					continue;
				}
				CheckIn ci = new CheckIn();

				User u = uMap.get(uName);
				if (u == null) {
					throw new RuntimeException("系统不存在该用户");
				}
				ci.setEmpId(u.getId());

				dayStr = dayStr.trim();
				ci.setDayStr(dayStr);
				if (StringUtils.isNotEmpty(checkin)) {
					checkin = checkin.trim();
					ci.setCheckin(checkin);
					ci.setCheckinInt(OtherUtil.time2Int(checkin));
				}
				if (StringUtils.isNotEmpty(checkout)) {
					checkout = checkout.trim();
					ci.setCheckout(checkout);
					ci.setCheckoutInt(OtherUtil.time2Int(checkout));
				}
				list.add(ci);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}

		return list;
	}

	// 解析考勤打卡
	public static List<CheckIn> getFromFile(int empId, InputStream is) {
		List<CheckIn> list = new ArrayList<CheckIn>();
		Workbook wb = null;

		try {
			wb = Workbook.getWorkbook(is);
			if (wb == null) {
				return list;
			}
			Sheet[] sheets = wb.getSheets();
			if (sheets == null || sheets.length == 0) {
				return list;
			}
			Sheet sheet = wb.getSheet(0);
			int rows_len = sheet.getRows();

			for (int j = 0; j < rows_len; j++) {

				if (j == 0) {// 忽略第一行
					continue;
				}

				// 获取当前行所有的列
				Cell[] cells = sheet.getRow(j);
				if (cells == null || cells.length < 2) {
					continue;
				}

				String dayStr = cells[1].getContents();
				String checkin = null;
				String checkout = null;

				if (cells.length >= 3) {
					checkin = cells[2].getContents();
					if (checkin.length() == 4) {
						checkin = "0" + checkin;
					}
				}
				if (cells.length >= 4) {
					checkout = cells[3].getContents();
					if (checkout.length() == 4) {
						checkout = "0" + checkout;
					}
				}

				if (StringUtils.isEmpty(dayStr)) {
					continue;
				}
				CheckIn ci = new CheckIn();

				ci.setEmpId(empId);

				dayStr = dayStr.trim();
				ci.setDayStr(dayStr);
				if (StringUtils.isNotEmpty(checkin)) {
					checkin = checkin.trim();
					ci.setCheckin(checkin);
					ci.setCheckinInt(OtherUtil.time2Int(checkin));
				}
				if (StringUtils.isNotEmpty(checkout)) {
					checkout = checkout.trim();
					ci.setCheckout(checkout);
					ci.setCheckoutInt(OtherUtil.time2Int(checkout));
				}
				list.add(ci);
				// System.out.println(ci);

			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}

		return list;
	}

	public static File generateForPcb(LabAll pcb, LabPcbSq sq) {
		try {

			File path = new File("D:/web/upload/pcb/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/pcb/" + sq.getDdbh() + ".xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("PCB", 0);

			Label label;
			/*
			 * for (int i = 0; i < title.length; i++) { //
			 * Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z //
			 * 在Label对象的子对象中指明单元格的位置和内容 label = new Label(i, 0, title[i]);
			 * ws.addCell(label);// 将定义好的单元格添加到sheet表中 }
			 */

			int row = 0;

			String fileName = pcb.getFileName();
			ExcelUtil.addARow(ws, "文件名：", fileName, row++);

			Integer numSet = pcb.getNumSet();
			int numUnit = pcb.getNumUnit();
			String value = pcb.getCs();
			if (numSet != null) {
				ExcelUtil.addARow(ws, "数量：", numSet + "set" + ", " + numUnit + "unit", row++);

				ExcelUtil.addARow(ws, "层数：", value, row++);

				float ccChang = pcb.getCcChang();
				float ccKuang = pcb.getCcKuang();
				ExcelUtil.addARow(ws, "尺寸：", ccChang + "mm" + " * " + ccKuang + "mm", row++);

				value = pcb.getCpbh();
				ExcelUtil.addARow(ws, "成品板厚：", value, row++);

				value = pcb.getCl();
				ExcelUtil.addARow(ws, "材料：", value, row++);

				value = "内层：" + pcb.getTbNei() + "，外层：" + pcb.getTbWai();
				ExcelUtil.addARow(ws, "成品铜铂厚度：", value, row++);

				value = pcb.getZh() + "，颜色：" + pcb.getZhColor();
				ExcelUtil.addARow(ws, "阻焊：", value, row++);

				value = pcb.getCstd();
				ExcelUtil.addARow(ws, "测试通断：", value, row++);

				value = pcb.getWxjgfs();
				ExcelUtil.addARow(ws, "阻外形加工方式：", value, row++);

				value = pcb.getJszyq();
				ExcelUtil.addARow(ws, "金手指要求：", value, row++);

				value = pcb.getDcbg();
				ExcelUtil.addARow(ws, "电测报告：", value, row++);

				value = pcb.getZkcsbg();
				ExcelUtil.addARow(ws, "阻抗测试报告：", value, row++);

				value = pcb.getCpjcbg();
				ExcelUtil.addARow(ws, "成品检验报告：", value, row++);

				value = pcb.getFgzh();
				ExcelUtil.addARow(ws, "过孔是否覆盖阻焊：", value, row++);

				value = pcb.getBmtc();
				ExcelUtil.addARow(ws, "表面涂层：", value, row++);

				value = pcb.getBmtchd();
				ExcelUtil.addARow(ws, "表面涂层厚度：", value, row++);

				value = pcb.getKzms();
				ExcelUtil.addARow(ws, "阻抗描述：", value, row++);
			}

			if (pcb.getWeldType() != null) {
				value = pcb.getWeldType() == 1 ? "只焊接阻容" : "全部焊接";
				ExcelUtil.addARow(ws, "焊接类型：", value, row++);

				value = pcb.getNum().toString();
				ExcelUtil.addARow(ws, "数量：", value, row++);

				value = pcb.getPiles() == 1 ? "双面" : "单面";
				ExcelUtil.addARow(ws, "层数：", value, row++);

				value = "贴片点数：" + pcb.getPaster() + "插件点数：" + pcb.getPaster2();
				ExcelUtil.addARow(ws, "焊接点数:", value, row++);

				value = pcb.getGyType() == 1 ? "有铅" : "无铅";
				ExcelUtil.addARow(ws, "焊接工艺要求：", value, row++);

				value = pcb.getBGAType() == 1 ? "无" : "有";
				ExcelUtil.addARow(ws, "是否有BGA焊接：", value, row++);

				value = pcb.getHjgy() == 1 ? "无" : "见焊接加工工艺要求说明书";
				ExcelUtil.addARow(ws, "是否有浮高、卧倒等特殊要求：", value, row++);

				value = pcb.getMaxSize().toString();
				ExcelUtil.addARow(ws, "最多点数:", value, row++);

				value = pcb.getJu().toString();
				ExcelUtil.addARow(ws, "间距:", value, row++);

				value = pcb.getXzzj().toString();
				ExcelUtil.addARow(ws, "锡珠直径:", value, row++);
			}

			if (pcb.getNumSet2() != null) {
				value = pcb.getNumSet2().toString();
				ExcelUtil.addARow(ws, "数量：", value, row++);

				value = pcb.getCcChang2() + "mm*" + pcb.getCcKuang() + "mm";
				ExcelUtil.addARow(ws, "尺寸：", value, row++);

				value = pcb.getCpbh2();
				ExcelUtil.addARow(ws, "钢网厚度：", value, row++);

				value = pcb.getCl2();
				ExcelUtil.addARow(ws, "钢网材料：", value, row++);

				value = pcb.getBmtc2();
				ExcelUtil.addARow(ws, "用途：", value, row++);

				value = pcb.getBmtchd2();
				ExcelUtil.addARow(ws, "抛光工艺：", value, row++);
			}

			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();

			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static File generateForGw(LabPcb pcb, LabPcbSq sq) {
		try {

			File path = new File("D:/web/upload/pcb/");
			if (!path.exists()) {
				path.mkdirs();
			}
			File file = new File("D:/web/upload/pcb/" + sq.getDdbh() + ".xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("钢网", 0);

			Label label;
			/*
			 * for (int i = 0; i < title.length; i++) { //
			 * Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z //
			 * 在Label对象的子对象中指明单元格的位置和内容 label = new Label(i, 0, title[i]);
			 * ws.addCell(label);// 将定义好的单元格添加到sheet表中 }
			 */

			int row = 0;

			String fileName = pcb.getFileName();
			ExcelUtil.addARow(ws, "文件名：", fileName, row++);

			int numSet = pcb.getNumSet();
			ExcelUtil.addARow(ws, "数量：", numSet + "set", row++);

			float ccChang = pcb.getCcChang();
			float ccKuang = pcb.getCcKuang();
			ExcelUtil.addARow(ws, "尺寸：", ccChang + "mm" + " * " + ccKuang + "mm", row++);

			String value = pcb.getCpbh();
			ExcelUtil.addARow(ws, "钢网厚度：", value, row++);

			value = pcb.getCl();
			ExcelUtil.addARow(ws, "钢网材料：", value, row++);

			value = pcb.getBmtc();
			ExcelUtil.addARow(ws, "用途：", value, row++);

			value = pcb.getBmtchd();
			ExcelUtil.addARow(ws, "抛光工艺：", value, row++);

			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();

			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void addARow(WritableSheet ws, String key, String value, int r) {
		try {
			Label label = new Label(0, r, key);
			ws.addCell(label);
			label = new Label(1, r, value);
			ws.addCell(label);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static void generateForEv(Evaluation ev, List<Evaluation> list, String userName) {
		// try{
		// File path = new File("D:/web/upload/ev/");
		// if(!path.exists()){
		// path.mkdirs();
		// }
		// File file = new File("D:/web/upload/ev/"+userName+".xls");
		// WritableWorkbook wb = Workbook.createWorkbook(file);
		// WritableSheet ws = wb.createSheet("自我评价",0);
		//
		// int size = list.size();
		//
		// for(int i=0; i<list.size(); i++){
		// int r = i;
		// Evaluation e = list.get(i);
		//
		// if(e.getPoint() != null){
		// ExcelUtil.addARow(ws, e.getTitle()+"：", e.getEmpPoint()+"", r);
		// }else{
		// ExcelUtil.addARow(ws, e.getTitle()+"：", e.getAns(), r);
		// }
		//
		// }
		//
		// int row = size;
		// String gr = ev.getContent();
		// String zg = ev.getZgContent();
		// String gs = ev.getGsContent();
		//
		// ExcelUtil.addARow(ws, "自我评价：", gr, row++);
		// ExcelUtil.addARow(ws, "对直属主管评价：", zg, row++);
		// ExcelUtil.addARow(ws, "对公司评价：", gs, row++);
		//
		// // 写入数据
		// wb.write();
		// // 关闭文件
		// wb.close();
		//
		//// }catch(Exception e){
		// throw new RuntimeException(e);
		// }
	}

	public static File exportExcel(List<OsWupinVo> list, File file, Model model, HttpServletResponse response) {
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static File exportExcel(File file, Model model, HttpServletResponse response) {
		model.addAttribute("file", file);
		try {
			ServletUtil.downloadFile(response, file, new String(file.getName().getBytes("iso8859-1"), "utf-8"),
					"application/octet-stream");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static File exportSealRecord(List<SealApplyDetail> list) {
		try {
			String[] title = { "序号", "申请人", "用章类型", "用章时间", "用章文件名", "文件详情", "用章单位", "印章类型", "用印处", "文件份数", "用章数量" };
			File path = new File("D:/web/upload/seal/");
			if (!path.exists()) {
				path.mkdirs();
			}
			String str = DateUtil.getTimeString(new Date(), "yyyyMMdd");
			File file = new File(path + "/用章申请处理-" + str + ".xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("用章申请处理", 0);
			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				WritableFont wFont = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD, false);
				WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);
				wCellFormatC.setBorder(Border.ALL, BorderLineStyle.THIN, jxl.format.Colour.BLACK);
				label = new Label(i, 0, title[i], wCellFormatC);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int column = 0;
			int r = 0;

			WritableFont wFont = new WritableFont(WritableFont.createFont("宋体"), 10);
			WritableCellFormat wCellFormatC = new WritableCellFormat(wFont);
			wCellFormatC.setBorder(Border.ALL, BorderLineStyle.THIN, jxl.format.Colour.BLACK);

			ws.setColumnView(0, 5);
			ws.setColumnView(1, 8);
			ws.setColumnView(2, 10);
			ws.setColumnView(3, 33);
			ws.setColumnView(4, 20);
			ws.setColumnView(5, 20);
			ws.setColumnView(6, 20);
			ws.setColumnView(7, 10);
			ws.setColumnView(8, 15);
			ws.setColumnView(9, 8);
			ws.setColumnView(10, 8);
			for (SealApplyDetail sad : list) {
				r++;
				String name = sad.getName();
				String sealTypeStr = sad.getSealTypeStr();
				String date = sad.getYzDate() + sad.getEndDate();
				String fileName = sad.getFileName();
				String fileDetail = sad.getFileDetail();
				String sealCompany = sad.getSealCompany();
				String sealName = sad.getSealName();
				Integer fileNum = sad.getFileNum();
				Integer userNum = sad.getUserNum();
				String location = sad.getLoaction();

				label = new Label(column++, r, r + "", wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, name, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, sealTypeStr, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, date, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, fileName, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, fileDetail, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, sealCompany, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, sealName, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, location, wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, fileNum + "", wCellFormatC);
				ws.addCell(label);
				label = new Label(column++, r, userNum + "", wCellFormatC);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * @Title: propRepairRecord   
	 * @Description: 导出请购详情
	 * @param: @param list
	 * @param: @return      
	 * @return: File      
	 * @throws
	 */
	public static File exportQg(List<MateriaPurchaseDetail> list) {
		try {
			String[] title = { "物料编码", "品名分类", "品牌", "规格型号", "封装", "单位", "其他参数要求","需求数量","需求日期","预计单价" };
			File path = new File("D:/web/upload/materia/");
			if (!path.exists()) {
				path.mkdirs();
			}
			String str = DateUtil.getTimeString(new Date(), "yyyyMMdd");
			File file = new File(path + "/请购申请详情-" + str + ".xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("请购申请详情", 0);

			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				MateriaPurchaseDetail ma = list.get(i);
				String materiCode = ma.getMaCode();
				String classify = ma.getClassify();
				String brand = ma.getBrand();
				String spec = ma.getSpec();
				String package1 = ma.getPackage1();
				String unit = ma.getUnit();
				String other = ma.getLink();
				String needNUm = ma.getNeedNum() + "";
				String needDate = ma.getNeedDate();
				String price = ma.getPrice() + "";
				label = new Label(column++, r, materiCode);
				ws.addCell(label);
				label = new Label(column++, r, classify);
				ws.addCell(label);
				label = new Label(column++, r, brand);
				ws.addCell(label);
				label = new Label(column++, r, spec);
				ws.addCell(label);
				label = new Label(column++, r, package1);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				label = new Label(column++, r, other);
				ws.addCell(label);
				label = new Label(column++, r, needNUm);
				ws.addCell(label);
				label = new Label(column++, r, needDate);
				ws.addCell(label);
				label = new Label(column++, r, price);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	
	/**
	 * @Title: propRepairRecord   
	 * @Description: 导出采购详情
	 * @param: @param list
	 * @param: @return      
	 * @return: File      
	 * @throws
	 */
	public static File exportCg(List<MateriaPurchaseDetail> list) {
		try {
			String[] title = { "物料编码", "品名分类", "品牌", "规格型号", "封装", "单位",
					"需求数","库存数","需求日期","计划日期","采购数量","单价","其他费用","供应商","链接" };
			File path = new File("D:/web/upload/materia/");
			if (!path.exists()) {
				path.mkdirs();
			}
			String str = DateUtil.getTimeString(new Date(), "yyyyMMdd");
			File file = new File(path + "/采购申请详情-" + str + ".xls");
			WritableWorkbook wb = Workbook.createWorkbook(file);
			WritableSheet ws = wb.createSheet("采购申请详情", 0);
			Label label;
			for (int i = 0; i < title.length; i++) {
				// Label(x,y,z)其中x代表单元格的第x+1列，第y+1行, 单元格的内容是z
				// 在Label对象的子对象中指明单元格的位置和内容
				label = new Label(i, 0, title[i]);
				ws.addCell(label);// 将定义好的单元格添加到sheet表中
			}
			int size = list.size();
			int column = 0;
			for (int i = 0; i < size; i++) {
				int r = i + 1;
				MateriaPurchaseDetail ma = list.get(i);
				String materiCode = ma.getMaCode();
				String classify = ma.getClassify();
				String brand = ma.getBrand();
				String spec = ma.getSpec();
				String package1 = ma.getPackage1();
				String unit = ma.getUnit();
				String needNum = ma.getNeedNum() + "";
				String stockNum = ma.getStockNum() + "";
				String needDate = ma.getNeedDate();
				String userDate = ma.getUseDate();
				String buyNum = ma.getBuynum() + "";
				String price = ma.getPrice() + "";
				String cost = ma.getCost() + "";
				String supplier = ma.getSupplier();
				String link = ma.getLink();
				label = new Label(column++, r, materiCode);
				ws.addCell(label);
				label = new Label(column++, r, classify);
				ws.addCell(label);
				label = new Label(column++, r, brand);
				ws.addCell(label);
				label = new Label(column++, r, spec);
				ws.addCell(label);
				label = new Label(column++, r, package1);
				ws.addCell(label);
				label = new Label(column++, r, unit);
				ws.addCell(label);
				label = new Label(column++, r, needNum);
				ws.addCell(label);
				label = new Label(column++, r, stockNum);
				ws.addCell(label);
				label = new Label(column++, r, needDate);
				ws.addCell(label);
				label = new Label(column++, r, userDate);
				ws.addCell(label);
				label = new Label(column++, r, buyNum);
				ws.addCell(label);
				label = new Label(column++, r, price);
				ws.addCell(label);
				label = new Label(column++, r, cost);
				ws.addCell(label);
				label = new Label(column++, r, supplier);
				ws.addCell(label);
				label = new Label(column++, r, link);
				ws.addCell(label);
				column = 0;
			}
			// 写入数据
			wb.write();
			// 关闭文件
			wb.close();
			return file;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
