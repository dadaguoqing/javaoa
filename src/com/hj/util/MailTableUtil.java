package com.hj.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.hj.oa.Consts;
import com.hj.oa.bean.ApplySilicon;
import com.hj.oa.bean.ExternalApply;
import com.hj.oa.bean.ExternalApplyDetail;
import com.hj.oa.bean.ExternalDeal;
import com.hj.oa.bean.ExternalInbound;
import com.hj.oa.bean.Materia;
import com.hj.oa.bean.MateriaApply;
import com.hj.oa.bean.MateriaPurchase;
import com.hj.oa.bean.MateriaPurchaseDetail;
import com.hj.oa.bean.OsCgWupinVo;
import com.hj.oa.bean.OsYaopinVo;
import com.hj.oa.bean.SealApply;
import com.hj.oa.bean.SealApplyDetail;
import com.hj.oa.bean.User;
import com.hj.oa.bean.ZcPlace;
import com.hj.oa.bean.ZcPropCg;
import com.hj.oa.bean.ZcProperty;
import com.hj.oa.bean.ZcType;
import com.hj.oa.service.UserService;

/**
 * 创建邮件表格
 *
 */
public class MailTableUtil {
	@Autowired
	UserService userService;
	@Autowired
	MailUtil mailUtil;
	
	public static String createTr(String title, String str, Integer strWidth) {
		StringBuilder sb = new StringBuilder();
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"+ title +"</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:"+ strWidth +"px;'>" + str + "</td></tr>");
		return sb.toString();
	}
	public static String createTitle(String title, Integer strWidth) {
		StringBuilder sb = new StringBuilder();
		sb.append("<th style='width:"+ strWidth +"px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"+ title +"</th>");
		return sb.toString();
	}
	public static String createTd(String str) {
		StringBuilder sb = new StringBuilder();
		sb.append("<td style='text-align:center; padding:3px 7px; border:1px solid #888;'>"+ str +"</td>");
		return sb.toString();
	}
	
	/**
	 * 创建物料入库表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createInwareTable(MateriaPurchase ma, List<MateriaPurchaseDetail> mpds, Integer status, User user, boolean flag) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append(createTr("申请时间", ma.getDaystr(), 450));
		sb.append(createTr("物料类型", ma.getPurchaseCode(), 450));
		if(status == 3) {
			sb.append(createTr("质检员", user.getName(), 450));
		}
		if(status == 4) {
			sb.append(createTr("质检员", user.getName(), 450));
			sb.append(createTr("入库时间", DateUtil.getCurrentTime(Consts.chinaDateTimeFmt), 450));
		}
		sb.append("</table><br/>");
		sb.append("<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr>");
		sb.append(createTitle("序号", 50));
		sb.append(createTitle("物料编码", 100));
		if(!flag) {
			sb.append(createTitle("品名分类", 200));
		}
		sb.append(createTitle("品牌", 200));
		sb.append(createTitle("规格型号", 200));
		if(!flag) {
			sb.append(createTitle("封装", 200));
		}
		sb.append(createTitle("单位", 100));
		sb.append(createTitle("供应商", 100));
		sb.append(createTitle("采购数量", 100));
		if(status == 3) {
			sb.append(createTitle("检验结果", 100));
		}
		if(status == 4) {
			sb.append(createTitle("检验结果", 100));
			sb.append(createTitle("实际入库数量", 150));
		}
		if(flag) {
			sb.append(createTitle("单价（元）", 100));
			sb.append(createTitle("其他费用（元）", 150));
			sb.append(createTitle("入库仓库", 100));
		}
		sb.append("</tr>");
		int i = 0;
		for(MateriaPurchaseDetail mpd : mpds) {
			i++;
			sb.append("<tr>");
			sb.append(createTd(i + ""));
			if(status == 4) {
				sb.append(createTd(mpd.getNeedDate()));
			} else {
				sb.append(createTd(mpd.getMaCode()));
			}
			if(!flag) {
				sb.append(createTd(mpd.getClassify()));
			}
			sb.append(createTd(mpd.getBrand()));
			sb.append(createTd(mpd.getSpec()));
			if(!flag) {
				sb.append(createTd(mpd.getPackage1()));
			}
			sb.append(createTd(mpd.getUnit()));
			sb.append(createTd(mpd.getSupplier()));
			sb.append(createTd(mpd.getBuynum() + ""));
			if(status == 3) {
				sb.append(createTd(mpd.getLink()));
			}
			if(status == 4) {
				sb.append(createTd(mpd.getLink()));
				sb.append(createTd(mpd.getNeedNum() + ""));
			}
			if(flag) {
				sb.append(createTd(mpd.getPrice() + ""));
				sb.append(createTd(mpd.getCost() + ""));
				sb.append(createTd(mpd.getUseDate() + ""));
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	
	public static String createExternalInboundTable(ExternalApply ea, ExternalApplyDetail eaDetail, List<ExternalInbound> eis) {
		String eaType = ea.getExternalType();
		String externalType = "";
		if(StringUtils.isNotBlank(eaType)) {
			if(eaType.indexOf("1") != -1) { 
				externalType += "  PCB加工";
			}
			if(eaType.indexOf("2") != -1) { 
				externalType += "  焊接加工";
			}
			if(eaType.indexOf("3") != -1) { 
				externalType += "  钢网加工";
			}
			if(eaType.indexOf("4") != -1) { 
				externalType += "  打白胶加工";
			}
			if(eaType.indexOf("5") != -1) { 
				externalType += "  三防漆加工";
			}
			if(eaType.indexOf("6") != -1) { 
				externalType += "  亚克力加工";
			}
			if(eaType.indexOf("7") != -1) { 
				externalType += "  机箱加工";
			}
			if(eaType.indexOf("8") != -1) { 
				externalType += "  线束加工";
			}
		}
		
		String urgent = eaDetail.getIsUrgent();
		String urgentTypes = "";
		if(StringUtils.isNotBlank(urgent)) {
			if(urgent.indexOf("1") != -1) { 
				urgentTypes += "  PCB加工";
			}
			if(urgent.indexOf("2") != -1) { 
				urgentTypes += "  焊接加工";
			}
			if(urgent.indexOf("3") != -1) { 
				urgentTypes += "  钢网加工";
			}
			if(urgent.indexOf("4") != -1) { 
				urgentTypes += "  打白胶加工";
			}
			if(urgent.indexOf("5") != -1) { 
				urgentTypes += "  三防漆加工";
			}
			if(urgent.indexOf("6") != -1) { 
				urgentTypes += "  亚克力加工";
			}
			if(urgent.indexOf("7") != -1) { 
				urgentTypes += "  机箱加工";
			}
			if(urgent.indexOf("8") != -1) { 
				urgentTypes += "  线束加工";
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>申请时间</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ ea.getApplyDate() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ ea.getContent() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>项目名称/项目号</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ ea.getProjectName() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工类型</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ externalType + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加急</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;' >"
				+ urgentTypes + "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工类型</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工数量</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>单位</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>入库日期</th>");
		sb.append("</tr>");
		
		if(StringUtils.isNotBlank(eaType)) {
			for(ExternalInbound ei :eis) {
				if(eaType.indexOf("1") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCB加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
				if(eaType.indexOf("2") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>焊接加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
				if(eaType.indexOf("3") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>钢网加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
				if(eaType.indexOf("4") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>打白胶加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
				if(eaType.indexOf("5") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>三防漆加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
				if(eaType.indexOf("6") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>亚克力加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
				if(eaType.indexOf("7") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>机箱加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
				if(eaType.indexOf("8") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>线束加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ ei.getInboundDate() + "</td>");
					sb.append("</tr>");
				}
			}
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	
	
	public static String createExternalDealTable(ExternalApply ea, ExternalDeal eaDeal,
						ExternalApplyDetail eaDetail) {
		String eaType = ea.getExternalType();
		String externalType = "";
		if(StringUtils.isNotBlank(eaType)) {
			if(eaType.indexOf("1") != -1) { 
				externalType += "  PCB加工";
			}
			if(eaType.indexOf("2") != -1) { 
				externalType += "  焊接加工";
			}
			if(eaType.indexOf("3") != -1) { 
				externalType += "  钢网加工";
			}
			if(eaType.indexOf("4") != -1) { 
				externalType += "  打白胶加工";
			}
			if(eaType.indexOf("5") != -1) { 
				externalType += "  三防漆加工";
			}
			if(eaType.indexOf("6") != -1) { 
				externalType += "  亚克力加工";
			}
			if(eaType.indexOf("7") != -1) { 
				externalType += "  机箱加工";
			}
			if(eaType.indexOf("8") != -1) { 
				externalType += "  线束加工";
			}
		}
		
		String urgent = eaDetail.getIsUrgent();
		String urgentTypes = "";
		if(StringUtils.isNotBlank(urgent)) {
			if(urgent.indexOf("1") != -1) { 
				urgentTypes += "  PCB加工";
			}
			if(urgent.indexOf("2") != -1) { 
				urgentTypes += "  焊接加工";
			}
			if(urgent.indexOf("3") != -1) { 
				urgentTypes += "  钢网加工";
			}
			if(urgent.indexOf("4") != -1) { 
				urgentTypes += "  打白胶加工";
			}
			if(urgent.indexOf("5") != -1) { 
				urgentTypes += "  三防漆加工";
			}
			if(urgent.indexOf("6") != -1) { 
				urgentTypes += "  亚克力加工";
			}
			if(urgent.indexOf("7") != -1) { 
				urgentTypes += "  机箱加工";
			}
			if(urgent.indexOf("8") != -1) { 
				urgentTypes += "  线束加工";
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>申请时间</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:800px;'>"
				+ ea.getApplyDate() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:800px;'>"
				+ ea.getContent() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>项目名称/项目号</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:800px;'>"
				+ ea.getProjectName() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工类型</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:800px;'>"
				+ externalType + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加急</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:800px;' >"
				+ urgentTypes + "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工类型</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工数量</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>单位</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工费用（元）</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>交期</th>");
		sb.append("</tr>");
		
		if(StringUtils.isNotBlank(eaType)) {
			if(eaType.indexOf("1") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCB加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getPcbNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getPcbCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getPcbDate() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("2") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>焊接加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getWeldNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getWeldCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getWeldDate() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("3") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>钢网加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getSteelNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getSteelCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getSteelDate() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("4") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>打白胶加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>-</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>-</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getGlueCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getGlueDate() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("5") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>三防漆加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>-</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>-</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getPaintCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getPaintDate() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("6") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>亚克力加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getAcrylicNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getAcrylicCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getAcrylicDate() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("7") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>机箱加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getChassisNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getChassisCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getChassisDate() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("8") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>线束加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getPencilNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getPencilCost() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDeal.getPencilDate() + "</td>");
					sb.append("</tr>");
			}
			sb.append("<tr>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>加工费用合计</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:50px;' colspan='4'>"
					+ eaDeal.getTotalCost().setScale(2, BigDecimal.ROUND_HALF_UP) + " 元</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	
	public static String createExternalApplyTable(ExternalApply ea, ExternalApplyDetail eaDetail) {
		String eaType = ea.getExternalType();
		String externalType = "";
		if(StringUtils.isNotBlank(eaType)) {
			if(eaType.indexOf("1") != -1) { 
				externalType += "  PCB加工";
			}
			if(eaType.indexOf("2") != -1) { 
				externalType += "  焊接加工";
			}
			if(eaType.indexOf("3") != -1) { 
				externalType += "  钢网加工";
			}
			if(eaType.indexOf("4") != -1) { 
				externalType += "  打白胶加工";
			}
			if(eaType.indexOf("5") != -1) { 
				externalType += "  三防漆加工";
			}
			if(eaType.indexOf("6") != -1) { 
				externalType += "  亚克力加工";
			}
			if(eaType.indexOf("7") != -1) { 
				externalType += "  机箱加工";
			}
			if(eaType.indexOf("8") != -1) { 
				externalType += "  线束加工";
			}
		}
		
		String urgent = eaDetail.getIsUrgent();
		String urgentTypes = "";
		if(StringUtils.isNotBlank(urgent)) {
			if(urgent.indexOf("1") != -1) { 
				urgentTypes += "  PCB加工";
			}
			if(urgent.indexOf("2") != -1) { 
				urgentTypes += "  焊接加工";
			}
			if(urgent.indexOf("3") != -1) { 
				urgentTypes += "  钢网加工";
			}
			if(urgent.indexOf("4") != -1) { 
				urgentTypes += "  打白胶加工";
			}
			if(urgent.indexOf("5") != -1) { 
				urgentTypes += "  三防漆加工";
			}
			if(urgent.indexOf("6") != -1) { 
				urgentTypes += "  亚克力加工";
			}
			if(urgent.indexOf("7") != -1) { 
				urgentTypes += "  机箱加工";
			}
			if(urgent.indexOf("8") != -1) { 
				urgentTypes += "  线束加工";
			}
		}
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>申请时间</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ ea.getApplyDate() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ ea.getContent() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>项目名称/项目号</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ ea.getProjectName() + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工类型</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>"
				+ externalType + "</td></tr>");
		sb.append("<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加急</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;' >"
				+ urgentTypes + "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工类型</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工数量</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>单位</th>");
		sb.append("<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工费用预算（元）</th>");
		sb.append("</tr>");
		
		if(StringUtils.isNotBlank(eaType)) {
			if(eaType.indexOf("1") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCB加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getPcbNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getPcbCost() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("2") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>焊接加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getWeldNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getWeldCost() + "</td>");
					sb.append("</tr>");
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>电子元器件</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>-</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getComponentCost() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("3") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>钢网加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getSteelNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getSteelCost() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("4") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>打白胶加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>-</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getGlueCost() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("5") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>三防漆加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>-</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getPaintCost() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("6") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>亚克力加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getAcrylicNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getAcrylicCost() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("7") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>机箱加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getChassisNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getChassisCost() + "</td>");
					sb.append("</tr>");
				}
			if(eaType.indexOf("8") != -1) { 
					sb.append("<tr>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>线束加工</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getPencilNum() + "</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>PCS</td>");
					sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
							+ eaDetail.getPencilCost() + "</td>");
					sb.append("</tr>");
				}
			sb.append("<tr>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>预算费用合计</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:50px;' colspan='3'>"
					+ eaDetail.getTotalCost().setScale(2, BigDecimal.ROUND_HALF_UP) + " 元</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	
	

	/**
	 * 固定资产审批
	 * 
	 * @param applyTime
	 * @param zcs
	 * @param reason
	 * @return
	 */
	public static  String buildApplyPropTable(List<ZcProperty> zcs, String reason) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>");
		sb.append(
				"<tr><td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请事由</b></td>"
						+ "<td style='width:550px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + reason
						+ "</td>" + "</tr>" + "</table>");
		sb.append("<br/>");
		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:50px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>序号</b></td>"
						+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>资产名称</b></td>"
						+ "<td style='background-color:#CECEFF;width:500px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注信息</b></td>"
						+ "</tr>");
		int i = 1;
		for (ZcProperty zc : zcs) {
			sb.append("<tr>" 
					+ "<td style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;'>"+ i++ + "</td>"
					+ "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>"+ zc.getName() + "</td>"
					+ "<td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + zc.getBz()
					+ "</td>" + "</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 固定资产申领
	 * 
	 * @return
	 */
	public static String buildApplyPropTable(String[] names, String[] bzs, String reason) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>");
		sb.append(
				"<tr><td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请事由</b></td>"
						+ "<td style='width:550px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + reason
						+ "</td>" + "</tr>" + "</table>");
		sb.append("<br/>");
		sb.append(
				"<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;'>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:50px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>序号</b></td>"
						+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>资产名称</b></td>"
						+ "<td style='background-color:#CECEFF;width:500px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注信息</b></td>"
						+ "</tr>");
		int j = 1;
		for (int i = 0; i < names.length; i++) {
			sb.append("<tr>" + "<td style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ j++ + "</td>");
			sb.append("<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + names[i]
					+ "</td>" + "<td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>"
					+ bzs[i] + "</td>" + "</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 固定资产采购审批
	 * 
	 * @param cg
	 * @param sqProps
	 * @param selectPlaces
	 * @param selecTypes
	 * @return
	 */
	public static String createMailTableForCg(ZcPropCg cg, List<ZcProperty> sqProps, List<ZcPlace> selectPlaces,
			List<ZcType> selecTypes) {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr><th style='width:200px;text-align:left; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;text-align:center;'>采购事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;'>" + cg.getContent()
				+ "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr>" + "<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "序号</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "资产名称</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "类别</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "规格型号</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "放置地点</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "单位</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "单价/元</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "数量</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "总价/元</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "备注</th></tr>");
		int i = 1;
		int sum = 0;
		for (ZcProperty zp : sqProps) {
			sum += zp.getMoney();
			sb.append("<tr>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ i++ + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getName() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>");
			for (ZcType zt : selecTypes) {
				if (zt.getId() == Integer.parseInt(zp.getType())) {
					sb.append(zt.getName());
					break;
				}
			}
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getBrand() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>");
			for (ZcPlace zpl : selectPlaces) {
				if (zpl.getId() == zp.getPlace()) {
					sb.append(zp.getName());
					break;
				}
			}
			double m = zp.getMoney();
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			nf.setRoundingMode(RoundingMode.UP);
			nf.format(m);
			
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getUnit() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getPrice() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ zp.getNum() + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ m + "</td>"
					+ "<td style='width:100px;text-left; padding:3px 7px; border:1px solid #888;'>" + zp.getBz()
					+ "</td>" + "</tr>");
		}
		sb.append("</table>");

		return sb.toString();
	}

	/**
	 * 固定资产采购申请
	 * 
	 * @return
	 */
	public static String createMailTableForCg(ZcPropCg sq, String[] names, String[] bzs, char[] units, double[] prices,
			int[] nums, double[] moneys, String[] brands, int[] types, int[] places, List<ZcPlace> selectPlaces,
			List<ZcType> selecTypes, String content) {
		StringBuilder sb = new StringBuilder();

		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr><th style='width:200px;text-align:left; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;text-align:center;'>采购事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:450px;'>" + content
				+ "</td></tr></table>");
		sb.append("<br/>");

		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr>" + "<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "序号</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "资产名称</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "类别</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "规格型号</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "放置地点</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "单位</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "单价/元</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "数量</th>"
						+ "<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "总价/元</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "备注</th></tr>");
		int j = 1;
		int sum = 0;
		for (int i = 0; i < names.length; i++) {
			sum += moneys[i];
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>" + j++ + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;'>" + names[i] + "</td>");
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			for (ZcType zt : selecTypes) {
				if (zt.getId() == types[i]) {
					sb.append(zt.getName());
					break;
				}
			}
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ brands[i] + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>");
			for (ZcPlace zp : selectPlaces) {
				if (zp.getId() == places[i]) {
					sb.append(zp.getName());
					break;
				}
			}
			sb.append("</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ units[i] + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + prices[i]
					+ "</td>" + "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>"
					+ nums[i] + "</td>"
					+ "<td style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + moneys[i]
					+ "</td>" + "<td style='width:100px;text-align:left; padding:3px 7px; border:1px solid #888;'>"
					+ bzs[i] + "</td>" + "</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 办公用品采购
	 * 
	 * @param list
	 * @param sqType
	 * @return
	 */
	public static String createBgCg(String content, List<OsCgWupinVo> wps) {
		StringBuilder sb = new StringBuilder();
		Double zjje = 0.0;
		int i = 1;
		for (OsCgWupinVo vo : wps) {
			zjje += vo.getNum() * vo.getMoney();
		}
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr><th style='width:200px;text-align:left; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;text-align:center;'>采购事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>" + content
				+ "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:left; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;text-align:center;'>采购总金额</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:600px;'>" + zjje
				+ "元</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr>" + "<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "序号</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "物品名称</th>"
						+ "<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "规格型号</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "数量单位</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "单价/元</th><th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "金额小计/元</th></tr>");
		for (OsCgWupinVo vo : wps) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(i++);
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getType());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getNum() + vo.getUnit());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getMoney());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getMoney() * vo.getNum());
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 应急药品采购
	 * 
	 * @param list
	 * @param sqType
	 * @return
	 */
	public static String createDrug(List<OsCgWupinVo> list, Integer sqType) {
		String type = sqType == 1 ? "药品" : "物品";
		StringBuilder sb = new StringBuilder();
		double zjje = 0.0;
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ type
						+ "名称</th><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "规格型号</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "数量</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "单位</th><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "单价（元）</th><th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ "金额小计（元）</th></tr>");
		for (OsCgWupinVo vo : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getType());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getNum());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getUnit());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getMoney());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getMoney() * vo.getNum());
			sb.append("</td></tr>");
			zjje += vo.getNum() * vo.getMoney();
		}
		sb.append(
				"<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'  colspan='5'>");
		sb.append("总计金额");
		sb.append("</td><td style='padding:3px 7px; border:1px solid #888;'>");
		sb.append("   " + zjje + "元");
		sb.append("</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 应急药品申领
	 * 
	 * @param list
	 * @param sqType
	 * @param slType
	 * @return
	 */
	public static String createGetDrug(List<OsYaopinVo> list, int sqType, Integer slType) {
		String type = sqType == 1 ? "药品" : "物品";
		StringBuilder sb = new StringBuilder();
		int i = 1;
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ type + "名称</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>规格型号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>数量单位</th>");
		for (OsYaopinVo vo : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(i++);
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getType());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getNum() + vo.getUnit());
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 应急药品损益申请
	 * 
	 * @param list
	 * @param sqType
	 * @param slType
	 * @return
	 */
	public static String createDrugSunYi(List<OsCgWupinVo> list, int sqType, Integer syType, String content) {
		String type = sqType == 1 ? "药品" : "物品";
		String slTypeStr = syType == 1 ? "增加库存" : "减少库存";
		int i = 1;
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>损益类型</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:450px' >" + slTypeStr
				+ "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>损益原因</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:450px' >" + content
				+ "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>"
						+ type + "名称</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>规格型号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>数量单位</th>");
		for (OsCgWupinVo vo : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(i++);
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getName());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getType());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(vo.getNum() + vo.getUnit());
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 芯片申领
	 * 
	 * @param list
	 * @param str
	 * @return
	 */
	public static String addSiliconApply(List<ApplySilicon> list, String str) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>需求时间</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:650px' >" + str + "</td><tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>芯片型号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>申请数量</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>单位</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>申请原因</th>");
		int i = 1;
		for (ApplySilicon li : list) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(i++);
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(li.getBrand());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(li.getNum());
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append("颗（PCS）");
			sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
			sb.append(li.getReason());
			sb.append("</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 外协加工申请
	 * 
	 * @param list
	 * @param str
	 * @return
	 */
	public static String addWxjg(String[] types, String str,Double yjzj) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";' >");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>投板时间</th>");
		sb.append(
				"<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;' >" + str + "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>加工类型</th>");
		String typeStr = "";
		for (String type : types) {
			if ("1".equals(type)) {
				typeStr += "PCB加工";
			}
			if ("2".equals(type)) {
				typeStr += " 焊接加工";
			}
			if ("3".equals(type)) {
				typeStr += " 钢网加工";
			}
			if ("4".equals(type)) {
				typeStr += " 打白胶加工";
			}
			if ("5".equals(type)) {
				typeStr += " 三防漆加工";
			}
		}
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;' >" + typeStr
				+ "</td></tr>");
		if(yjzj != null) {
			NumberFormat nf = NumberFormat.getNumberInstance();
			nf.setMaximumFractionDigits(2);
			nf.setRoundingMode(RoundingMode.UP);
			nf.format(yjzj);
			sb.append(
					"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>预计总价</th>");
			sb.append(
					"<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;' >" + yjzj + "元</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 创建物料申领表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createApplyTable(MateriaApply ma,List<Materia> mas) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>申领仓库</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:850px;'>"
				+ ma.getWarehouse() + "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>备注信息</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:850px;' >"
				+ ma.getBz() + "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append("<tr>");
		sb.append(
				"<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>物料编码</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>规格型号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>品名分类</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>封装</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>数量</th>");
		sb.append("</tr>");
		int i = 1;
		for(Materia m : mas) {
			sb.append("<tr>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
					+ i++ + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
					+ m.getMateriaCode() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
					+ m.getSpec() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
					+ m.getClassfiy() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
					+ m.getPackage1() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
					+ m.getNum() + "</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 创建返料入库表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createReventTable(MateriaPurchase ma) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>申请时间</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>" + ma.getDaystr()
				+ "</td>");
		sb.append(
				"<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>入库类型</th>");
		/** 物料入库类型：1-采购入库 2-调拨入库 3-返料入库 4-报修入库 5-归还入库 6-其他入库* */
		String type = "返料入库";
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>" + type
				+ "</td></tr>");
		sb.append(
				"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>备注信息</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:200px;' colspan='3'>"
				+ ma.getContent() + "</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 创建物料采购表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createPurchaseTable(MateriaPurchase mp,double zj) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setRoundingMode(RoundingMode.UP);
		nf.format(zj);
		
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0px 0px 5px; border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>采购依据</th>");
		String code = mp.getRequisitionCode().isEmpty() ? "-" : mp.getRequisitionCode();
 		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;'>" + code
				+ "</td></tr>");
 		String reason = mp.getReason().isEmpty() ? "-" : mp.getReason();
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>采购事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;'>"
				+ reason + "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>预算费用总计</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;'>"
				+ zj + "元</td></tr>");
		String content = mp.getContent().isEmpty() ? "-" : mp.getContent();
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>备注信息</th>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;'>"
					+ content + "</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 创建物料请购表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createPurchaseTable2(MateriaPurchase ma,double totalprice) {
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(2);
		nf.setRoundingMode(RoundingMode.UP);
		nf.format(totalprice);
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>请购事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;'>"
				+ ma.getReason() + "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>预算费用总计</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;'>"
				+ totalprice + "元</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>备注信息</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:500px;' >"
				+ ma.getContent() + "</td></tr>");
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 创建物料损益表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createSyTable(MateriaPurchase ma,String wh,List<Materia> mas) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>仓库</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:850px;'>"
				+ wh + "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>备注信息</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:850px;'>"
				+ ma.getContent() + "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<tr><th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>物料编码</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>品名分类</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>规格型号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>封装</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>数量</th></tr>");
		int i = 1;
			for(Materia m : mas) {
				sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
						+ i++ + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getMateriaCode() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getClassfiy() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getSpec() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getPackage1() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getNum() + "</td></tr>");
			}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 创建物料请购表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createSealTable(SealApply sa) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' >");
		sb.append(
				"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用章类型</th>");
		Integer type = sa.getSealType();
		String str = "";
		switch (type) {
		case 1:
			str = "内部用章";
			break;
		case 2:
			str = "临时用章";
			break;
		case 3:
			str = "外带用章";
			break;
		default:
			str = "";
		}
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:400px;' >" + str
				+ "</td></tr>");
		sb.append(
				"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用章事由</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:400px;' >" + sa.getContent()
				+ "</td></tr>");
		if (sa.getWdr() != null) {
			String wdr = sa.getWdr();
			sb.append(
					"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>外带人</th>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:400px;' >" + wdr
					+ "</td></tr>");
		}
		if (sa.getPtr() != null) {
			String ptr = sa.getPtr();
			sb.append(
					"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>陪同人</th>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:400px;' >" + ptr
					+ "</td></tr>");
		}
		if (!sa.getStartDate().isEmpty()) {
			sb.append(
					"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用章日期</th>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:400px;' >"
					+ sa.getStartDate() + "</td></tr>");
		}
		if (sa.getEndDate() != null) {
			String endDate = sa.getEndDate();
			sb.append(
					"<tr><th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用章时间</th>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:400px;' >" + endDate
					+ "</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	/**
	 * @Title: createMailTable   
	 * @Description: 固定资产发放
	 * @param: @param props
	 * @param: @return      
	 * @return: String      
	 * @throws
	 */
	public static String createPropsend(List<ZcProperty> props) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>资产编码</th>");
		sb.append(
				"<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>资产名称</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>规格型号</th></tr>");
		Integer i = 1;
		for (ZcProperty prop : props) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>" + i++
					+ "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:150px;'>"
					+ prop.getCode() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:150px;'>"
					+ prop.getName() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
					+ prop.getSpec() + "</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}

	/**
	 * 用章邮件表格
	 * 
	 * @param list
	 * @return
	 */
	public static String createSealDetailTable(List<SealApplyDetail> sads) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用章文件名</th>");
		sb.append(
				"<th style='width:150px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>文件详情</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用章单位</th>");
		sb.append(
				"<th style='width:100px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>印章类型</th>");
		sb.append(
				"<th style='width:130px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用印处</th>");
		sb.append(
				"<th style='width:80px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>文件份数</th>");
		sb.append(
				"<th style='width:80px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>用章数量</th></tr>");
		Integer i = 1;
		for (SealApplyDetail sad : sads) {
			sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>" + i++
					+ "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:150px;'>"
					+ sad.getFileName() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:150px;'>"
					+ sad.getFileDetail() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
					+ sad.getSealCompany() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:100px;'>"
					+ sad.getSealName() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:130px;'>"
					+ sad.getLoaction() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:80px;'>"
					+ sad.getFileNum() + "</td>");
			sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:80px;'>"
					+ sad.getUserNum() + "</td></tr>");
		}
		sb.append("</table>");
		return sb.toString();
	}
	
	
	/**
	 * @Title: createBTable   
	 * @Description: 创建物料报废表格
	 * @return: String      
	 * @throws
	 */
	public static String createBFTable(MateriaPurchase ma,String wh,List<MateriaPurchaseDetail> mpds) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>仓库</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:850px;'>"
				+ wh + "</td></tr>");
		sb.append(
				"<tr><th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>备注信息</th>");
		sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:left;width:850px;'>"
				+ ma.getContent() + "</td></tr></table>");
		sb.append("<br/>");
		sb.append(
				"<table style='margin:0px 0 0px 5px;border-collapse: collapse;border-spacing: 0;font-size:12px; font-family:\"宋体\";'' ><tr>");
		sb.append(
				"<tr><th style='width:50px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>序号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>物料编码</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>品名分类</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>规格型号</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>封装</th>");
		sb.append(
				"<th style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;BACKGROUND-COLOR:#CECEFF;'>数量</th></tr>");
		int i = 1;
			for(MateriaPurchaseDetail m : mpds) {
				sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;width:50px;'>"
						+ i++ + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getMaCode() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getClassify() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getSpec() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getPackage1() + "</td>");
				sb.append("<td style='padding:3px 7px; border:1px solid #888;text-align:center;width:200px;'>"
						+ m.getNeedNum() + "</td></tr>");
			}
		sb.append("</table>");
		return sb.toString();
	}
	

	/**
	 * 物料申请信息详情
	 * 
	 * @param user收件人
	 * @param user2
	 *            发件人
	 * @param type
	 *            邮件类型
	 * @param table
	 */
	public void sendMailTable(User user, User user2, String type, String table) {
		String toName = user.getName();
		String fromName = user2.getName();
		String to = user.getEmail();
		String deptName = user2.getDeptName();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(deptName).append(fromName).append("向您提交了一个").append(type)
				.append("，请及时登录系统审批。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批通知", text);
	}
	
	/**
	 * 
	 * @Title: sendFFMailToUser   
	 * @Description: 固定资产发放通知
	 * @param: @param toName
	 * @param: @param fromName
	 * @param: @param to
	 * @param: @param copyTo
	 * @param: @param table      
	 * @return: void      
	 * @throws
	 */
	public void sendFFMailToUser(String toName,String to, String copyTo, String table) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append("您申请的固定资产已发放，请及时登录系统查看，详情如下。");
		sb.append("</p>");
		sb.append(table);
		sb.append(
				"<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, copyTo, Consts.defaultFrom, "固定资产发放通知", text);
	}
	
	// 成功发放，通知所有上级
	public void sendFFMailToUserSups(List<User> sups, String lyName, String zcName, String table) {
		for (User u : sups) {
			StringBuilder sb = new StringBuilder();
			sb.append("<html><head>");
			sb.append(
					"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
					.append(u.getName()).append("。</p>");
			sb.append("<p style='padding:5px 0 0px 0px;'>资产管理员").append(zcName).append("给").append(lyName)
					.append("发放了资产,详情如下。");
			sb.append("</p>");
			sb.append(table);
			sb.append(
					"<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
			String text = sb.toString();
			mailUtil.sendEMail(u.getEmail(), null, Consts.defaultFrom, "固定资产发放通知", text);
		}
	}

	public void sendMaileResult2(User user, int status, String type, String table) {
		String toName = user.getName();
		String to = user.getEmail();
		String result = status == -1 ? "<a style='color:green;'>未通过审批</a>" : "<a style='color:red;'>已通过审批</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
				.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append("您的一个").append(type).append(result);
		sb.append("，请登录系统查看。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批结果提醒", text);
	}
	
	
	public void sendPropResult(User propManage, User apply, String type, String table) {
		String toName = propManage.getName();
		String to = propManage.getEmail();
		String result = "<a style='color:red;'>已通过审批</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
		.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(apply.getName()).append("的一个").append(type).append(result);
		sb.append("，请登录系统查看。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "审批结果提醒", text);
	}
	
	/**
	 * @Title: nianjia   
	 * @Description: 剩余年假不足发邮件
	 * @param: @param propManage
	 * @param: @param timeLen
	 * @param: @param flag      
	 * @return: void      
	 * @throws
	 */
	public void  nianjia(User propManage,Integer timeLen,boolean flag) {
		String toName = propManage.getName();
		String to = propManage.getEmail();
		Integer dId = null;
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		if(flag) {
			dId = this.userService.findAdminIdByDeptId(propManage.getDeptId());
			User user = this.userService.findById(dId);
			sb.append(
					"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
			.append(user.getName()).append("！</p>");
			sb.append("<p style='padding:5px 0 0px 5px;color:red'>").append(toName).append("的年假已不足，剩余年假为").append(timeLen + "分钟。");
		} else {
			sb.append(
					"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
			.append(toName).append("！</p>");
			sb.append("<p style='padding:5px 0 0px 5px;color:red'>").append("您的年假已不足，剩余年假为").append(timeLen + "分钟。");
		}
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, "剩余年假不足提醒", text);
	}
	
	
	public void sendDealNotice(User handler, User proposer, String type, String table) {
		String toName = handler.getName();
		String to = handler.getEmail();
		String result = "<a style='color:red;'>已通过审批</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
		.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(proposer.getName()).append("的一个").append(type).append(result);
		sb.append("，请及时登录系统处理。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "处理通知", text);
	}
	
	public void sendInboundNotice(User handler, User proposer, String type, String table) {
		String toName = handler.getName();
		String to = handler.getEmail();
		String result = "<a style='color:red;'>已完成入库</a>";
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
		.append(toName).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append(proposer.getName()).append("的一个").append(type).append(result);
		sb.append("，请及时登录系统查看。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "入库通知", text);
	}
	
	public void sendStartDealNotice(User proposer, String type, String table) {
		String to = proposer.getEmail();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
		.append(proposer.getName()).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append("您的一个").append(type);
		sb.append("已开始处理。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "处理通知", text);
	}
	
	
	public void sendEndDealNotice(User proposer, String type, String table) {
		String to = proposer.getEmail();
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head>");
		sb.append(
				"</head><body style='color:#222; font-size:12px; font-family:\"宋体\";'><p style='padding:5px; padding-bottom:0;'>您好，")
		.append(proposer.getName()).append("！</p>");
		sb.append("<p style='padding:5px 0 0px 5px;'>").append("您的一个").append(type);
		sb.append("已完成入库。详情如下：</p>");
		sb.append(table);
		sb.append("<p style='padding-left:5px; color:#333; font-size:12px;'>本邮件为OA系统提醒，请不要回复。</p></body></html>");
		String text = sb.toString();
		mailUtil.sendEMail(to, null, Consts.defaultFrom, type + "处理通知", text);
	}
	

}
