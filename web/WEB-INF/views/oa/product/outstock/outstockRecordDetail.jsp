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
		<div class="navTitle2" url="${ctx }/web/oa/product/outstock/records">
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
			<a style="color: #C0392B;" href="${ctx}/web/oa/product/outstock/list">点击这里返回</a>
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
						<td colspan="2"></td>
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
						<td class="title" style="text-align: left; width: 20px;">序号</td>
						<td class="title" style="text-align: left; width: 60px;">
							产品类型</td>
						<td class="title" style="text-align: left; width: 80px;">
							产品型号</td>
						<td class="title" style="text-align: left; width: 50px;">数量</td>
						<td class="title" style="text-align: left; width: 50px;">单位</td>
						<td class="title" style="text-align: left; width: 100px;">发货日期</td>
						<td class="title" style="text-align: left; width: 150px;">外箱编号</td>
						<td class="title" style="text-align: left; width: 100px;">标签纸照片</td>
						<td class="title" style="text-align: left; width: 200px;">备注</td>
					</tr>
					<c:forEach var="product" varStatus="state"
						items="${outRec.details }">
						<tr>
							<td>${state.index + 1}</td>
							<td>${product.productName }</td>
							<td><span class="infospan" style="cursor: pointer;"
								title="${product.oriRemark }">${product.productModel }</span></td>
							<td>${product.count }</td>
							<td>${product.unit }</td>
							<td><c:if test="${not empty product.fhDate}">
								${product.fhDate}
								</c:if> <c:if test="${empty product.fhDate}">
								-
								</c:if></td>
							<td><c:if test="${not empty product.wxbh}">
								${product.wxbh}
								</c:if> <c:if test="${empty product.wxbh}">
								-
								</c:if></td>
							<td><c:if test="${not empty product.bqz}">
									<a href="${ctx}/upload/${product.bqz}" target="_blank">点击查看</a>
								</c:if> <c:if test="${empty product.bqz}">
									-
								</c:if></td>
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
						<td><c:if test="${approvals[0].state == 1 }">同意</c:if> <c:if
								test="${approvals[0].state == -1 }">不同意</c:if></td>
						<td class="title" style="border-left: none; width: 200px;">批注：</td>
						<td>${approvals[0].comment }</td>
						<td class="title" style="border-left: none; width: 200px;">审批时间：</td>
						<td><fmt:formatDate value="${approvals[0].updateTime }"
								pattern="yyyy年MM月dd日 HH时mm分" /></td>
					</tr>
				</tbody>
			</table>

			<div id="detail">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						发货详情
					</span>
				</div>
				<table id="manytimeTable" class="table1">
					<tbody>
						<tr>
							<td class="title" style="text-align: center; width: 100px;"
								rowspan="2">发货时间</td>
							<td class="title" style="text-align: center; width: 100px;"
								colspan="3">物流信息</td>
							<td class="title" style="text-align: center; width: 100px;"
								colspan="2">出库单信息</td>
						</tr>
						<tr>
							<td class="title" style="text-align: center; width: 100px;">物流公司</td>
							<td class="title" style="text-align: center; width: 100px;">物流单号</td>
							<td class="title" style="text-align: center; width: 100px;">物流单照片</td>
							<td class="title" style="text-align: center; width: 100px;">出库单号</td>
							<td class="title" style="text-align: center; width: 100px;">出库单照片</td>
						</tr>
						<tr>
							<td><fmt:formatDate value="${outRec.outstockTime }"
									pattern="yyyy年MM月dd日 HH时mm分" /></td>
							<td>${outRec.shipmentCompany }</td>
							<td>${outRec.shipmentOrderNum}</td>
							<td><a
								href="javascript:openPage('${outRec.shipmentOrderPic}');">${fn:split(outRec.shipmentOrderPic, '/')[fn:length(fn:split(outRec.shipmentOrderPic, '/'))-1]}</a></td>
							<td>${outRec.outstockOrderNum}</td>
							<td><a
								href="javascript:openPage('${outRec.outstockOrderPic}');">${fn:split(outRec.outstockOrderPic, '/')[fn:length(fn:split(outRec.outstockOrderPic, '/'))-1]}</a></td>
						</tr>
					</tbody>
				</table>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						回单信息
					</span>
				</div>
				<table id="receiveTable" class="table1">
					<tbody>
						<tr>
							<td class="title" style="text-align: right; width: 100px;">
								物流单号</td>
							<td>${outRec.shipmentReceiveNum }</td>
							<td class="title" style="text-align: right; width: 100px;">
								物流单照片</td>
							<td><a
								href="javascript:openPage('${outRec.shipmentReceivePic}');">${fn:split(outRec.shipmentReceivePic, '/')[fn:length(fn:split(outRec.shipmentReceivePic, '/'))-1]}</a></td>
						</tr>
					</tbody>
				</table>

			</div>
		</div>
	</div>
</div>

<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
</body>
</html>
<script>
	$(function() {
		$('#物料管理').addClass('ui-tabs-current');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	function  openPage(url) {
		url = encodeURIComponent(url);
		window.open('${ctx }/web/oa/preview?path='+url,'_blank');
	}
</script>