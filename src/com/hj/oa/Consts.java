package com.hj.oa;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class Consts {
	private static ResourceBundle res = ResourceBundle.getBundle("config");
	
	public static Integer labGly = 42;//实验室管理员,制板负责人
	
	//public static Integer zcCgy = 3;
	
	public static String ksurl = res.getString("ksurl");
	
	public static String devStr = res.getString("devMode");
	public static int devMode = 1;//1表示开发模式，测试模式。改为0表示实际应用模式
	
	public static String  compName = "合肥宏晶微电子科技股份有限公司";
	public static String sysName = "WE网";
	
	public static String submitCode = "submitCode";
	
	public static String techDeptMgrs = res.getString("techDeptMgr");
	public static List<Integer> techDeptMgr = new ArrayList<Integer>();
	
	public static String specialEmps = res.getString("specialEmps");
	public static List<Integer> specialEmp = new ArrayList<Integer>();
	
	static{
		if(techDeptMgrs != null)
			techDeptMgrs = techDeptMgrs.trim();
		if(StringUtils.isNotEmpty(techDeptMgrs)){
			String[] ids = techDeptMgrs.split(",");
			if(ids != null && ids.length>0){
				for(String id : ids){
					id = id.trim();
					try{
						if(StringUtils.isNotEmpty(id)){
							int idInt = Integer.parseInt(id);
							techDeptMgr.add(idInt);
						}
					}catch(Exception e){
						
					}
				}
			}
		}
		
		if(specialEmps != null)
			specialEmps = specialEmps.trim();
		if(StringUtils.isNotEmpty(specialEmps)){
			String[] ids = specialEmps.split(",");
			if(ids != null && ids.length>0){
				for(String id : ids){
					id = id.trim();
					try{
						if(StringUtils.isNotEmpty(id)){
							int idInt = Integer.parseInt(id);
							specialEmp.add(idInt);
						}
					}catch(Exception e){
						
					}
				}
			}
		}
	}
	
	static{
		devMode = Integer.parseInt(devStr);
		if( devMode == 0){
			compName = "合肥宏晶微电子科技股份有限公司";
		}else{
			compName = "合肥宏晶微电子科技股份有限公司";
		}
	}
	
	public static final String uploadRoot = res.getString("upload.path.root");
	public static final String uploadFileRootLoc = uploadRoot+ "docs\\";
	
	public static final String YaopinRootLoc = uploadRoot+ "yp\\";
	public static final String ProductRootLoc = uploadRoot+ "product\\contract\\";
	
	public static final int shouyeId = 1;//首页菜单的id
	public static final int tuchuId = 8;//退出登录菜单的id
	
	public static final int managerId = 1;//总经理id
	public static final int caiwuId = 7;//财务总监
	public static final String bossEmail = "wei.liu@macrosilicon.com";//总经理邮箱
	public static final int directorId = 2;//研发总监id
	public static final int sellDirectorId = 41;//销售总监id
	public static final int mgrOfXz = 8;//行政主管id
	public static final int techDeptId = 2;//技术部一级部门的id
	public static final int sellDeptId = 15;//营销中心一级部门的id
	public static final int nonTechDeptId = 1;//非技术部一级部门的id
	
	public static final String lessfour = "四小时以内";
	public static final String four2oneday = "四小时到一天";
	public static final String one2twoday = "一到两天";
	public static final String twodaymore = "两天以上";
	
	public static final String defaultFrom = "oa@macrosilicon.com";//"msilicon@163.com";
	
	public static final String nameOfHoliday = "节假日";
	public static final String nameOfWorkDay = "工作日";
	
	public static final String BUSINESS_TRIP = "出差";
	
	public static final int CANCEL_LEAVE = 0;
	
	public static final int CANCEL_BUSINESS = 1;
	
	//工作起始时间
	public static int amBeginHour = 8;
	public static int amBeginMinute = 30;
	public static int amEndHour = 12;
	public static int amEndMinute = 0;
	public static int pmBeginHour = 13;
	public static int pmBeginMinute = 0;
	public static int pmEndHour = 17;
	public static int pmEndMinute = 30;
	
	//审批type对应的审批类型
	public static int type_qj = 1; //请假
	public static int type_jb = 2; //加班
	/*
	public static int type_qxxg = 3;
	public static int type_qxdl = 4;*/
	public static int type_wc = 5; //外出
	public static int type_xj = 6; //外出
	
	
	//目录的名称（判断是否代理某个权限的时候用）
	public static String qjsp = "请假审批"; //请假审批
	public static String jbsp = "加班审批"; //加班审批
	public static String qxsp = "权限变更审批"; //权限发生变更的时候审批（刘伟审批）
	public static String qxdlsp = "权限代理审批"; //权限代理审批 
	public static String wcsp = "外出审批"; //外出或者出差审批
	public static String bkq = "打卡补签审批"; //补考勤，打卡补签
	public static String gdzcsl = "资产申领审批";
	public static String gdzczx = "资产注销审批";
	public static String gdzcwx = "资产维修审批";
	public static String xpsqsp = "芯片申请审批";
	
	
	public static String chinaDateTimeFmt = "yyyy年MM月dd日 HH时mm分";
	public static String chinaDateFmt = "yyyy年MM月dd日";
	public static String DateFmt = "yyyy/MM/dd";
	
	/**邮件*/
	public static final String REJECT = "物料报废申请";
	public static final String MAIL_PURCHASE = "物料请购申请";
	public static final String MAIL_PURCHASE2 = "物料采购申请";
	public static final String ALLOT = "物料调拨申请";
	public static final String OUTWARE = "物料出库申请";
	public static final String INWARE_APPLY = "物料入库申请";
	public static final String MAIL_APPLY = "物料申领";
	
	/**消息*/
	public static final  String ERROR_EXCEL = "读取失败，请检查Excel文件";
	public static final  String ERROR_EXCEL2 = "模板格式错误，请检查文件";
	public static final  String ERROR_EXCEL3 = "文件数据为空";
	public static final  String ERROR_EXCEL4 = "物料编码长度必须为12";
	public static final String SUCCESS = "操作成功";
	public static final String CHECK_EXCEL = "请查看下载文件";
	public static final String MATERIA_LACK = "当前申请仓库物料库存不足";
	public static final String MATERIA_NOTEXIT = "物料不存在";
	
	/**excel文件参数*/
	public static final String MATERIA_CODE = "物料编码";
	public static final String MATERIA_NUM = "数量";
	public static final String MATERIA_UNIT = "单位";
	public static final String MATERIA_PRICE = "单价";
	public static final String MATERIA_SUPPLIER = "供应商";
	public static final String MATERIA_CHINESESHORT = "功能简称（中）";
	public static final String MATERIA_ENGLISHSHORT = "功能简称（英）";
	public static final String MATERIA_ID = "序号";
	public static final String MATERIA_ITEM = "位号";
	public static final String MATERIA_SPEC = "规格型号";
	public static final String MATERIA_PACKAGE = "封装";
	public static final String MATERIA_TYPE = "类型";
	public static final String MATERIA_CLASSIFY = "品名分类";
	public static final String MATERIA_CLASSIFY2 = "品名";
	public static final String MATERIA_BRAND = "品牌";
	public static final String MATERIA_DEMANDNUM = "需求数量";
	public static final String MATERIA_PURCHASENUM = "采购数量";
	public static final String MATERIA_PURCHASENREASON= "采购事由";
	public static final String MATERIA_LACKNUM = "欠料数量";
	public static final String MATERIA_REMARK = "备注";
	public static final String MATERIA_FUNCTIONDIFF = "功能区分";
	public static final String MATERIA_ELSE = "其他要求";
	public static final String MATERIA_ACCESS = "附件名称";
	public static final String MATERIA_OTHERCOST = "其他费用";
	public static final String MATERIA_ARRIVALDATE = "需求日期";
	
	/**excel最后一行结束判断字符*/
	public static final String Materia_MSG1 = "领料：";// 出库
	public static final String Materia_MSG2 = "申报人：";// 报废
	public static final String Materia_MSG3 = "领料：";// 调拨
	
	/**excel文件格式*/
	public static final String EXCEL_XLS = "xls";
	public static final String EXCEL_XLSX = "xlsx";
	
	/**物料申请类型：1-物料申领	2-报废申请	3-调拨申请	4-出库申请	5-入库申请* */
	/**物料入库类型：1-采购入库	2-调拨入库	3-返料入库	4-报修入库	5-归还入库	6-其他入库* */
	/**excel解析参数*/
	public static final Integer ONE = 1;
	public static final Integer TWO = 2;
	public static final Integer THREE = 3;
	public static final Integer FOUR = 4;
	public static final Integer FIVE = 5;
	public static final Integer SIX = 6;
	public static final Integer SEVEN = 7;
	public static final Integer EIGHT = 8;
	public static final Integer NINE = 9;
	public static final Integer TEN = 10;
	
	
	/**模板类型*/
	public static final String MATERIA_REJECT= "reject";//入库
	public static final String MATERIA_ALLOT = "allot";//入库
	public static final String INWARE = "inware";//入库
	public static final String APPLY = "apply";// 物料申领
	public static final String APPLY2 = "apply2";// 物料申领2
	public static final String PURCHASE = "purchase";// 请购
	public static final String PURCHASE2 = "purchase2";// 采购 
	public static final String ENTER = "enter";// 采购 
	public static final String MATERIA_OUTWARE = "outware";// 出库
	public static final String MATERIA_RETURN = "return";// 采购 
	
	/**角色*/
	public static final String MATERIA_MANAGER = "仓库管理员";// 采购 
	
	/**仓库*/
	public static final String MATERIA_WAREHOUSE1 = "芯片研发中心仓库";
	public static final String MATERIA_WAREHOUSE2 = "工业控制中心仓库";
	public static final String MATERIA_WAREHOUSE3 = "深圳技术部仓库";
	
	
	/***********************************NEW MAIL*************************************/
	public static final String MAIL_DRUG_PURCHASE = "应急药品采购申请";
	public static final String MAIL_DRUG_APPLY = "应急药品申领";
	public static final String MAIL_DRUG_SUNYI = "应急药品损益申请";
	public static final String MAIL_WP_SUNYI = "办公用品损益申请";
	public static final String MAIL_SILICON_APPLY = "芯片申请";
	public static final String MAIL_WXJG_APPLY = "外协加工申请";
	public static final String MAIL_WP_PURCHASE = "办公用品采购申请";
	public static final String MAIL_WP_APPLY = "办公用品申领";
	
	public static final String STOREWARE_MANAGER = "新版仓库管理员";
	
	
}
