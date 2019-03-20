<%@page import="com.hj.oa.bean.ProductOutstockDetail"%>
<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../../public/header.jsp"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;申请详情
		</div>
		<div class="navTitle2"
			url="${ctx }/web/oa/product/outstock/apply/records">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../../public/hello.jsp"%>

		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;"
				href="${ctx}/web/oa/product/outstock/apply/records">点击查看申请记录</a>
		</div>

		<div style="border: 1px solid #2191C0; margin-top: 10px;">
			<div class="main-header" id="id1">
				<span style="color: #eee;">${compName }产品发货申请单</span>
			</div>
			<div class="tableTitle" style="padding: 10px 20px;">
				<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
					基本信息
				</span>
			</div>
			<table class="table1">
				<tbody>
					<tr>
						<td class="title" style="width: 100px;">申请人：</td>
						<td style="width: 100px;">${record.proposer }</td>
						<td class="title" style="border-left: none; width: 100px;">部门：</td>
						<td>${dept }</td>
						<td class="title" style="border-left: none; width: 180px;">申请单号：</td>
						<td>${record.oddNumber }</td>

					</tr>
					<tr>
						<td class="title" style="width: 60px;">申请原因：</td>
						<td style="width: 90px;">${record.reason }</td>
						<td class="title" style="width: 100px;">申请时间：</td>
						<td style="border-right: none; width: 200px;"><fmt:formatDate
								value="${record.createTime }" pattern="yyyy年MM月dd日 HH时mm分" /></td>
						<td class="title"></td>
					</tr>
				</tbody>
			</table>

			<div class="tableTitle" style="padding: 10px 20px;">
				<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
					发货详细
				</span>
			</div>
			<table class="table1">
				<tbody>
					<tr>
						<td class="title" style="border-left: none; width: 90px;">收货单位：</td>
						<td style="width: 260px;" colspan="3">
							${record.receiverCompany }</td>
					</tr>
					<tr>
						<td class="title" style="border-left: none; width: 90px;">收货地址：</td>
						<td style="width: 260px;" colspan="3">${record.addr }</td>
					</tr>
					<tr>
						<td class="title" style="border-left: none; width: 90px;">收货人：</td>
						<td>${record.receiver }</td>
					</tr>
					<tr>
						<td class="title" style="border-left: none; width: 90px;">联系电话：</td>
						<td>${record.tel }</td>
					</tr>
				</tbody>
			</table>
			<div class="tableTitle" style="padding: 10px 20px;">
				<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
					发货产品
				</span>
			</div>

			<table class="table1">
				<tbody>
					<tr>
						<td class="title" style="text-align: left; width: 100px;">序号</td>
						<td class="title" style="text-align: left; width: 100px;">
							产品类型</td>
						<td class="title" style="text-align: left; width: 100px;">
							产品型号</td>
						<td class="title" style="text-align: left; width: 100px;">数量</td>
						<td class="title" style="text-align: left; width: 100px;">单位</td>
						<td class="title" style="text-align: left;">备注</td>
					</tr>
					<c:forEach var="product" varStatus="state" items="${details }">
						<tr>
							<td>${state.index + 1}</td>
							<td>${product.productName }</td>
							<td><span class="infospan" style="cursor: pointer;"
								title="${product.oriRemark }">${product.productModel }</span></td>
							<td>${product.count }</td>
							<td>${product.unit }</td>
							<td><span class="infospan" style="cursor: pointer;"
								title="${product.remark }"> <c:if
										test="${fn:length(product.remark) < 20 }">
											${product.remark }
										</c:if> <c:if test="${fn:length(product.remark) >= 20 }">
											${fn:substring(product.remark,0, 19) }...
										</c:if>
							</span></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>


			<div class="tableTitle" style="padding: 10px 20px;">
				<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
					审批记录
				</span>
			</div>

			<table class="table1">
				<tbody>
					<tr>
						<td class="title" style="border-left: none; width: 200px;">
							生产运营部经理审批结果：</td>
						<c:if test="${not empty approvals[0]}">
							<td><c:if test="${approvals[0].state == 1 }">同意</c:if> <c:if
									test="${approvals[0].state == -1 }">不同意</c:if></td>
						</c:if>
						<c:if test="${empty approvals[0]}">
							<td>暂未达到该流程</td>
						</c:if>
						<td class="title" style="border-left: none; width: 200px;">批注：</td>
						<c:if test="${not empty approvals[0]}">
							<td>${approvals[0].comment }</td>
						</c:if>
						<c:if test="${empty approvals[0]}">
							<td>暂未达到该流程</td>
						</c:if>
						<td class="title" style="border-left: none; width: 200px;">审批时间：</td>
						<c:if test="${not empty approvals[0]}">
							<td><fmt:formatDate value="${approvals[0].updateTime }"
									pattern="yyyy年MM月dd日 HH时mm分" /></td>
						</c:if>
						<c:if test="${empty approvals[0]}">
							<td>暂未达到该流程</td>
						</c:if>
					</tr>
				</tbody>
			</table>

			<c:if test="${not empty outRecords}">
				<div id="outstock" class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						已提交发货记录
					</span>
				</div>

				<table id="outstockTable" class="table1">
					<tbody>
						<c:forEach items="${outRecords}" var="record" varStatus="status">
							<tr>
								<td rowspan="${fn:length(record.details) + 3 }"
									style="width: 100px; text-align: center;">${status.index + 1}</td>
								<td class="title" style="text-align: right; width: 100px;">预计发货时间</td>
								<td><fmt:formatDate value="${record.expectedOutstockTime }"
										pattern="yyyy年MM月dd日" /></td>
								<td class="title" style="text-align: right; width: 100px;">发货状态</td>
								<td style="color: red;"><c:if test="${record.state == 0}">未发货</c:if>
									<c:if test="${record.state == 1}">已发货，未收到回单</c:if> <c:if
										test="${record.state == 2}">已收货，回单待确认</c:if> <c:if
										test="${record.state == 3}">发货已完成</c:if></td>
							</tr>
							<tr>
								<td class="title" style="text-align: right; width: 100px;">确认发货时间</td>
								<td><c:if test="${not empty record.outstockTime}">
										<fmt:formatDate value="${record.outstockTime }"
											pattern="yyyy年MM月dd日 HH时mm分" />
									</c:if> <c:if test="${empty record.outstockTime}"> - </c:if></td>
								<td class="title" style="text-align: right; width: 100px;">确认回单时间</td>
								<td><c:if test="${not empty record.receiveTime}">
										<fmt:formatDate value="${record.receiveTime }"
											pattern="yyyy年MM月dd日 HH时mm分" />
									</c:if> <c:if test="${empty record.receiveTime}"> - </c:if></td>
							</tr>
							<tr>
								<td class="title" style="text-align: left; width: 100px;">
									产品名称</td>
								<td class="title" style="text-align: left; width: 100px;">
									规格型号</td>
								<td class="title" style="text-align: left; width: 100px;">
									数量</td>
								<td class="title" style="text-align: left; width: 100px;">单位</td>
							</tr>
							<c:forEach items="${record.details }" var="detail">
								<tr>
									<td>${detail.productName }</td>
									<td>${detail.productModel }</td>
									<td>${detail.count }</td>
									<td>${detail.unit }</td>
								</tr>
							</c:forEach>
							<c:if test="${not empty outRecords && record.state > 0}">
								<c:forEach items="${outRecords}" var="outRecord">
									<c:if
										test="${not empty outRecord && outRecord.id == record.id }">
										<tr>
											<td class="title" style="text-align: center; width: 100px;">物流公司</td>
											<td class="title" style="text-align: center; width: 100px;">物流单号</td>
											<td class="title" style="text-align: center; width: 100px;">物流单照片</td>
											<td class="title" style="text-align: center; width: 100px;">出库单号</td>
											<td class="title" style="text-align: center; width: 100px;">出库单照片</td>
										</tr>
										<tr>

											<td>${outRecord.shipmentCompany }</td>
											<td>${outRecord.shipmentOrderNum}</td>
											<td><a target="blank"
												href="javascript:openPage('${outRecord.shipmentOrderPic}');">${fn:split(outRecord.shipmentOrderPic, '/')[fn:length(fn:split(outRecord.shipmentOrderPic, '/'))-1]}</a></td>
											<td>${outRecord.outstockOrderNum}</td>
											<td><a target="blank"
												href="javascript:openPage('${outRecord.outstockOrderPic}');">${fn:split(outRecord.outstockOrderPic, '/')[fn:length(fn:split(outRecord.outstockOrderPic, '/'))-1]}</a></td>
										</tr>
										<tr>
											<td class="title" style="text-align: center; width: 100px;">发货时间</td>
											<td colspan="4"><fmt:formatDate
													value="${outRecord.outstockTime }"
													pattern="yyyy年MM月dd日 HH时mm分" /></td>
										</tr>
									</c:if>
									<c:if test="${not empty outRecord.shipmentReceiveNum }">
										<tr>
											<td class="title" style="text-align: right; width: 100px;">
												回单信息</td>
											<td class="title" style="text-align: right; width: 100px;">
												物流单号</td>
											<td>${outRecord.shipmentReceiveNum }</td>
											<td class="title" style="text-align: right; width: 100px;">
												物流单照片</td>
											<td><a target="blank" href="javascript:openPage('${outRecord.shipmentReceivePic}');">${fn:split(outRecord.shipmentReceivePic, '/')[fn:length(fn:split(outRecord.shipmentReceivePic, '/'))-1]}</a></td>
										</tr>
									</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>

	</div>

</div>

<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
</body>
</html>
<script>
	function openPage(url) {
		url = encodeURIComponent(url);
		window.open('${ctx }/web/oa/preview?path=' + url, '_blank');
	}
	//提交发货确认
	function submitForm() {
		// 参数校验
		if (!$('#bt').val()) {
			alert('请选择发货时间');
			return;
		}
		var outTimeStr = $('#bt').val() + ' ' + $('#beginHour').val()
				+ $('#beginMin').val() + '00';
		outTimeStr = outTimeStr.replace(new RegExp("年", "gm"), "/");
		outTimeStr = outTimeStr.replace(new RegExp("月", "gm"), "/");
		outTimeStr = outTimeStr.replace(new RegExp("日", "gm"), "");
		outTimeStr = outTimeStr.replace(new RegExp("时", "gm"), ":");
		outTimeStr = outTimeStr.replace(new RegExp("分", "gm"), ":");
		var now = new Date();
		if (now.getTime() < (new Date(outTimeStr)).getTime()) {
			alert('发货时间不能晚于当前时间');
			return;
		}
		if ($('#shipmentCompany').val() == '-1') {
			alert('请选择物流公司');
			return;
		}
		if (!$('#shipmentOrderNum').val()) {
			alert('请填写物流单号');
			return;
		}
		if (!$('#shipmentOrderPic').val()) {
			alert('请上传物流单照片');
			return;
		}
		if (!/\.(jpg)$/.test($('#shipmentOrderPic').val())) {
			alert("请上传jpg格式图片");
			return;
		}
		if (!$('#outstockOrderNum').val()) {
			alert('请填写出库单号');
			return;
		}
		if (!$('#outstockOrderPic').val()) {
			alert('请上传出库单照片');
			return;
		}
		if (!/\.(jpg)$/.test($('#outstockOrderPic').val())) {
			alert("请上传jpg格式图片");
			return;
		}
		document.form1.submit();
		//	$("form1").submit();
	}
	// 提交收到回执确认
	function submitForm1() {
		// 参数校验
		if (!$('#shipmentReceiveNum').val()) {
			alert('请填写物流单号');
			return;
		}
		if (!$('#shipmentReceivePic').val()) {
			alert('请上传物流单照片');
			return;
		}
		if (!/\.(jpg)$/.test($('#shipmentReceivePic').val())) {
			alert("请上传jpg格式图片");
			return;
		}
		if (!$('#comment').val() || $('comment').val == '') {
			$('#comment').val('- 无 -');
		}
		document.form1.submit();
		//	$("form1").submit();
	}

	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#产品发货申请').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>