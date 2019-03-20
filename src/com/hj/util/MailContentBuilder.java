/**   
* @Title: MailContentBuilder.java 
* @Package com.hj.util 
* @Description: email内容构建工具 
* @author mlsong   
* @date 2018年5月25日 上午11:34:03 
* @version V1.0   
*/
package com.hj.util;

import java.text.SimpleDateFormat;
import java.util.List;

import com.hj.commons.ApprovalStatus;
import com.hj.commons.EmailType;
import com.hj.oa.bean.ApplySupport;
import com.hj.oa.bean.ApplySupportApprove;
import com.hj.oa.bean.BKqSp;
import com.hj.oa.bean.Leave;
import com.hj.oa.bean.LeaveCancel;
import com.hj.oa.bean.OsSq;
import com.hj.oa.bean.OsWupin;
import com.hj.oa.bean.OsWupinVo;
import com.hj.oa.bean.ProductOutstockDetail;
import com.hj.oa.bean.ProductOutstockRecord;
import com.hj.oa.bean.Role;
import com.hj.oa.service.OfficeSupplyService;

/** 
* @ClassName: MailContentBuilder 
* @Description: 构造email内容 
* @author mlsong 
* @date 2018年5月25日 上午11:34:03 
*/
public class MailContentBuilder {
	/** 
	* @Description: 构建Email内容 
	* @param type
	* 			0：审批提醒邮件
	* 			1：审批结果通知邮件
	* @param spName
	* 			审批人
	* @param sqName
	* 			申请人
	* @param deptName
	* 			申请人部门
	* @param approvalType
	* 			申请审批类型
	* @param status
	* 			审批状态0：审批通过；-1：未通过审批
	* @param objs
	* 			传入参数列表
	* @return String
	* @author mlsong
	* @date 2018年5月25日 上午10:18:01
	*/
	public static String buildEmailContent(EmailType type, String spName, String sqName, String deptName, String approvalType, ApprovalStatus status, Object...objs) {
		StringBuilder sb = new StringBuilder();
		// 邮件头
		if (type == EmailType.OUTSTOCK_READ_ONLY_NOTICE) {
			sb.append(buildEmailHeaderContent(type, spName, sqName, deptName, approvalType, status, (String) objs[3]));
		} else if (type == EmailType.OUTSTOCK_EVERYDAY_APPROVAL_NOTICE) {
			sb.append(buildEmailHeaderContent(type, spName, sqName, deptName, approvalType, status, (String) objs[0]));
		} else {
			sb.append(buildEmailHeaderContent(type, spName, sqName, deptName, approvalType, status, null));
		}
		
		// 邮件表格（可以扩展）
		if (objs[0] instanceof Leave) { // 请假/出差
			Leave leave = (Leave) objs[0];
			if (leave.getWaichu() == 0) { // 请假
				sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>请假类别<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + (leave.getWaichu() == 0 ? leave.getType() : "外出" ) + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>开始时间<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + leave.getBeginTime() + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>结束时间<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + leave.getEndTime() + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>请假时长</b></td><td style='width:500px;padding:3px 7px; border:1px solid #888;text-align:left;' >" + leave.getDays() + "天" + leave.getHours() + "小时" + leave.getMinutes() + "分钟" + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>请假事由</b></td><td style='width:500px;padding:3px 7px; border:1px solid #888;text-align:left;' >" + leave.getContent() + "</td>"
						+ "</tr>"
						+ "</table>");
			} else if(leave.getWaichu() == 1) { // 出差
				sb.append("<table style='font-size:12px; font-family:\"宋体\" ;margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>外出类别<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>出差</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>开始时间<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + leave.getBeginTime() + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>结束时间<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + leave.getEndTime() + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>出差时长</b></td><td style='width:500px;padding:3px 7px; border:1px solid #888;text-align:left;'>" + leave.getDays() + "天" + leave.getHours() + "小时" + leave.getMinutes() + "分钟" + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>出差事由</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + leave.getContent() + "</td>"
						+ "</tr>"
						+ "</table>");
			} else {
				throw new RuntimeException("请假类型错误，未知请假类型！");
			}
		} else if (objs[0] instanceof LeaveCancel) { // 请假注销/出差注销
			if (objs.length < 2) {
				throw new RuntimeException("传入参数个数错误！应该传入的参数数量为2，实际传入参数数量为" + objs.length);
			}
			
			LeaveCancel leaveCancel = (LeaveCancel) objs[0];
			Leave leave = (Leave) objs[1];
			if (leaveCancel.getWaichu() == 0) { // 请假注销
				sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>请假类型<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + (leave.getWaichu() == 0 ? leave.getType() : "外出" ) + "</td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>销假方式<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + (leaveCancel.getCancelType() == 0 ? "提前回来" : "取消请假") + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>开始时间<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + leave.getBeginTime() + "</td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>结束时间<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + leave.getEndTime() + "</td>"
						+ "</tr>"
						+ "<tr>");
				if(leaveCancel.getCancelType() == 0) {
					sb.append("<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>销假开始时间<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + leaveCancel.getBeginTime() + "</td>"
							+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>销假结束时间<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + leaveCancel.getEndTime() + "</td>"
							+ "</tr>");
				}
				sb.append("</table>");
			} else if (leaveCancel.getWaichu() == 1) { // 出差注销
				sb.append("<table style='font-size:12px; font-family:\"宋体\" ;margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>外出类别<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>出差</td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + leave.getCreateTime() + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>开始时间<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;' >" + leave.getBeginTime() + "</td>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>结束时间<b></td><td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;' >" + leave.getEndTime() + "</td>"
						+ "</tr>"
						+ "<tr>"
						+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>出差事由</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' colspan=\"3\">" + leave.getContent() + "</td>"
						+ "</tr>"
						+ "</table>");
			} else {
				throw new RuntimeException("请假类型错误，未知请假类型！");
			}
		} else if (objs[0] instanceof BKqSp) { 	// 打卡补签
			BKqSp sp = (BKqSp) objs[0];
			String style = sp.getStyle();
			if ("1".equals(style)) {
				style = "迟到打卡补签";
			} else if ("2".equals(style)) {
				style = "忘记打卡补签";
			} else if ("3".equals(style)) {
				style = "公事出差打卡补签";
			} else if ("4".equals(style)) {
				style = "工牌丢失打卡补签";
			} else if ("5".equals(style)) {
				style = "先研院授课打卡补签";
			} else if ("6".equals(style)) {
				style = "公事外出打卡补签";
			} else if ("7".equals(style)) {
				style = "其他原因";
			}
			sb.append("<table style='font-size:12px; font-family:\"宋体\"; margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>补签类型<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + style + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>开始日期<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + sp.getDayStr() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>结束日期<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + sp.getDayStr() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>上班打卡时间<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + sp.getCheckin() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>下班打卡时间<b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + sp.getCheckout() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>补打卡事由</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + sp.getContent() + "</td>"
					+ "</tr>"
					+ "</table>");
		} else if (objs[0] instanceof ApplySupport) {
			ApplySupport as = (ApplySupport) objs[0];
			sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>优先级</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + as.getPriority() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>期望完成时间</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + as.getExpectdTime() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>最晚完成时间</b></td><td style='width:500px; text-align:left; padding:3px 7px; border:1px solid #888;'>" + as.getDeadline() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>客户单位(全称)</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + as.getCustomer() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>开案项目</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + as.getProject() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>技术支持内容</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' >" + as.getSupportContent() + "</td>"
					+ "</tr>");
			sb.append("</table>");
		} else if (objs[0] instanceof ApplySupportApprove) { // 技术支持审批
			ApplySupportApprove list = (ApplySupportApprove) objs[0];
			sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>优先级</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + list.getPriority() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间</b></td><td style='width:500px; text-align:left; padding:3px 7px; border:1px solid #888;'>" + list.getDayStr() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>期望完成时间</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + list.getExpectdTime() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>最晚完成时间</b></td><td style='width:500px; text-align:left; padding:3px 7px; border:1px solid #888;'>" + list.getDeadline() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:150px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>客户单位(全称)</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' colspan=\"3\">" + list.getCustomer() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>开案项目</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' colspan=\"3\">" + list.getProject() + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>技术支持内容</b></td><td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;' colspan=\"3\">" + list.getSupportContent() + "</td>"
					+ "</tr>");
			sb.append("</table>");
		} else if (objs[0] instanceof OsSq) {
			OsSq sq = (OsSq) objs[0];
			List<OsWupinVo> wps = (List<OsWupinVo>) objs[1];
			OfficeSupplyService osService = (OfficeSupplyService) objs[2];
			sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申领时间</b></td>"
					+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + sq.getCreateTime() + "</td>"
					+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申领类型</b></td>"
					+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>办公用品申领</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>物品名称</b></td>"
					+ "<td style='background-color:#CECEFF;width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>规格型号</b></td>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>数量</b></td>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单位</b></td>"
					+ "</tr>");
			for (OsWupinVo vo : wps) {
				OsWupin wp = osService.findWupinById(vo.getId());
				sb.append("<tr><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
				sb.append(wp.getName());
				sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
				sb.append(wp.getType());
				sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
				sb.append(vo.getNum());
				sb.append("</td><td style='padding:3px 7px; border:1px solid #888;text-align:center;'>");
				sb.append(wp.getUnit());
				sb.append("</td></tr>");
			}
			sb.append("</table>");
		} else if (objs[0] instanceof ProductOutstockRecord) { // 产品发货
			ProductOutstockRecord record = (ProductOutstockRecord) objs[0];
			List<ProductOutstockDetail> products = (List<ProductOutstockDetail>) objs[1];
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
			sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>申请时间<b></td>"
					+ "<td style='width:500px;text-align:left; padding:3px 7px; border:1px solid #888;'>" + sdf.format(record.getCreateTime()) + "</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>收货单位<b></td>"
					+ "<td style='width:g00px;text-align:left; padding:3px 7px; border:1px solid #888;' colspan='5'>" + record.getReceiverCompany() + "</td>"
					+ "</tr>"
					+ "</table><br/>");
					sb.append("<table style='font-size:12px; font-family:\"宋体\";margin:5px 5px 5px 0px; border-collapse: collapse;border-spacing: 0;' >"
							+ "<tr>"
							+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>产品名称<b></td>"
							+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>规格型号<b></td>"
							+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>数量<b></td>"
							+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>单位<b></td>"
							+ "<td style='background-color:#CECEFF;width:100px;text-align:center; padding:3px 7px; border:1px solid #888;'><b>备注<b></td>"
							+ "</tr>");
					for (ProductOutstockDetail product : products) {
						sb.append("<tr>"
								+ "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + product.getProductName() + "</td>"
								+ "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + product.getProductModel() + "</td>"
								+ "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + product.getCount() + "</td>"
								+ "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + product.getUnit() + "</td>"
								+ "<td style='width:200px;text-align:center; padding:3px 7px; border:1px solid #888;'>" + product.getRemark() + "</td>"
								+ "</tr>");
					}
					sb.append("</table>");
		}
		
		// 邮件尾
		sb.append(buildEmailFooterContent());
		return sb.toString();
	}
	
	/** 
	* @Description: 构建Email内容 
	* @param type
	* 			0：审批提醒邮件
	* 			1：审批结果通知邮件
	* @param spName
	* 			审批人
	* @param sqName
	* 			申请人
	* @param deptName
	* 			申请人部门
	* @param approvalType
	* 			申请审批类型
	* @param status
	* 			审批状态0：审批通过；-1：未通过审批
	* @param table
	* 			table字符串
	* @return String
	* @author mlsong
	* @date 2018年5月25日 上午10:18:01
	*/
	public static String buildEmailContent(EmailType type, String spName, String sqName, String deptName, String approvalType, ApprovalStatus status, String table) {
		StringBuilder sb = new StringBuilder();
		sb.append(buildEmailHeaderContent(type, spName, sqName, deptName, approvalType, status, null));
		sb.append(table);
		sb.append(buildEmailFooterContent());
		return sb.toString();
	}
	
	/** 
	* @Description: 构造email头部内容 
	* @param type
	* 			0：审批提醒邮件
	* 			1：审批结果通知邮件
	* @param spName
	* 			审批人
	* @param sqName
	* 			申请人
	* @param deptName
	* 			申请人部门
	* @param approvalType
	* 			申请审批类型
	* @param status
	* 			审批状态0：审批通过；-1：未通过审批
	* @return String
	* @author mlsong
	* @date 2018年5月25日 上午10:02:14
	*/
	public static String buildEmailHeaderContent(EmailType type, String spName, String sqName, String deptName, String approvalType, ApprovalStatus status, String roleName) {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head></head><body style='color:#222; font-size:12px; font-family:\"宋体\";'>");
		if (type == EmailType.APPROVAL_RESULT_NOTICE) { // 审批结果通知
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + sqName + "！</p>"
					+ "<p style=' padding:5px 0px;'>您的一个" + approvalType
					+ status.getMessage()
					+ "。请登录系统查看。详情如下：");
			
		} else if (type == EmailType.APPROVAL_REMIND_NOTICE) { // 审批提醒邮件
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "向您提交了一个" + approvalType + "，请及时登录系统审批。详情如下：</p>");
		} else if (type == EmailType.READ_ONLY_NOTICE) { // 仅做通知邮件
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交了一个" + approvalType + "，（仅作为通知，不需要您处理）。详情如下：</p>");
		} else if (type == EmailType.OUTSTOCK_READ_ONLY_NOTICE) { // 产品发货只读通知邮件
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交的一个" + approvalType + "，" + roleName + (status == ApprovalStatus.APPROVAL_ACCESS ? "审批通过" : "审批未通过") + "（仅作为通知，不需要您处理）。详情如下：</p>");
		} else if (type == EmailType.OUTSTOCK_REMIND_NOTICE) { // 产品发货提醒
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交的一个" + approvalType + "已经审核通过，等待您发货。详情如下：</p>");
		} else if (type == EmailType.CONFIRM_OUTSTOCK_NOTICE) {
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交的一个" + approvalType + "已经确认发货。详情如下：</p>");
		} else if (type == EmailType.CONFIRM_RECEIVE_NOTICE) {
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交的一个" + approvalType + "已经发货，并确认收到回单。详情如下：</p>");
		} else if (type == EmailType.OUTSTOCK_TIMEOUT_NOTICE) {
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>您的一个产品发货申请超时未处理已经取消，请登录系统重新提交申请。详情如下：</p>");
		} else if (type == EmailType.OUTSTOCK_EVERYDAY_APPROVAL_NOTICE) {
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交的一个" + approvalType + "，" + roleName + "尚未进行审批（仅作为通知，不需要您处理）。详情如下：</p>");
		} else if (type == EmailType.OUTSTOCK_CONFIRM_REMIND_NOTICE) {
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交的一个" + approvalType + "，尚未完成产品出库（仅作为通知，不需要您处理）。详情如下：</p>");
		} else if (type == EmailType.RECEIVE_CONFIRM_REMIND_NOTICE) {
			sb.append("<p style='padding:5px; padding-bottom:0;'>您好，" + spName + "！</p>"
					+ "<p style='padding:15px 0 15px 0px;'>" + deptName + sqName
					+ "提交的一个" + approvalType + "，尚未完成产品出库回单确认，请及时跟踪订单。详情如下：</p>");
		} else {
			throw new RuntimeException("邮件类型错误，暂无次类型邮件头！");
		}
		return sb.toString();
	}
	
	/** 
	* @Description: 构造邮件尾部内容 
	* @return String
	* @author mlsong
	* @date 2018年5月25日 上午10:03:56
	*/
	public static String buildEmailFooterContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<p style='padding:5px;color:#666;'>本邮件由OA系统自动发送，请不要回复。</p></body></html>");
		return sb.toString();
	}
}
