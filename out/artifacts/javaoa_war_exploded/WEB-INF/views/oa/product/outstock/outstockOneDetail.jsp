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
		<div class="navTitle2" url="${ctx }/web/oa/product/outstock/list">
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
				href="${ctx}/web/oa/product/outstock/records">点击查看发货记录</a>
		</div>
		<c:if test="${outRecord.state == 1}">
			<form name="form1" action="${ctx }/web/oa/product/confirm/receive"
				method="post" enctype="multipart/form-data">
		</c:if>
		<c:if test="${outRecord.state == 0}">
			<form name="form1" action="${ctx }/web/oa/product/confirm/outstock"
				method="post" enctype="multipart/form-data">
		</c:if>
		<input type="hidden" value="${submitCode}" name="submitCode" /> <input
			type="hidden" value="${record.id}" name="id" /> <input type="hidden"
			value="${outRecord.id}" name="recordId" /> <input type="hidden"
			value="${type}" name="type" /> <input type="hidden"
			value="${record.oddNumber }" name="oddNumber" />
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
						<td class="title" style="color: red;">逾期未处理自动取消时间：</td>
						<td><fmt:formatDate value="${record.cancelTime }"
								pattern="yyyy年MM月dd日 HH时mm分" /></td>
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
						<td class="title" style="border-left: none; width: 90px;">联系电话：</td>
						<td>${record.tel }</td>
					</tr>
					<tr>
						<td class="title" style="border-left: none; width: 90px;">合同附件：</td>
						<td colspan="3"><a
							href="javascript:openPage('${outRecord.contract}');">${fn:split(record.contract, '/')[fn:length(fn:split(record.contract, '/'))-1]}</a></td>
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
							产品名称</td>
						<td class="title" style="text-align: left; width: 100px;">
							规格型号</td>
						<td class="title" style="text-align: left; width: 100px;">数量</td>
						<td class="title" style="text-align: left; width: 100px;">单位</td>
						<td class="title" style="text-align: left;">备注</td>
					</tr>
					<c:forEach var="product" varStatus="state" items="${details }">
						<tr>
							<td>${state.index + 1}</td>
							<td>${product.productName }</td>
							<td>${product.productModel }</td>
							<td>${product.count }</td>
							<td>${product.unit }</td>
							<td>${product.remark }</td>
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
					<c:if test="${not empty approvals}">
						<c:forEach var="approval" varStatus="state" items="${approvals}">
							<tr>
								<td class="title" style="border-left: none; width: 200px;">
									<c:if test="${state.index + 1 == 1}">产品业务部经理审批结果：</c:if> <c:if
										test="${state.index + 1 == 2}">总经理审批结果：</c:if> <c:if
										test="${state.index + 1 == 3}">财务部经理审批结果：</c:if> <c:if
										test="${state.index + 1 == 4}">生产运营部经理审批结果：</c:if>
								</td>
								<td style="width: 120px;"><c:if
										test="${state.index + 1 == 3}">
										<c:if test="${approval.state == 1}">收到货款</c:if>
									</c:if> <c:if test="${state.index + 1 != 3}">
										<c:if test="${approval.state == 1 }">同意</c:if>
										<c:if test="${approval.state == -1 }">不同意</c:if>
									</c:if></td>
								<td class="title" style="border-left: none; width: 200px;">批注：</td>
								<td>${approval.comment }</td>
								<td class="title" style="border-left: none; width: 200px;">审批时间：</td>
								<td><fmt:formatDate value="${approval.updateTime }"
										pattern="yyyy年MM月dd日 HH时mm分" /></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>

			<div id="detail">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						发货产品
					</span>
				</div>

				<table id="manytimeTable" class="table1">
					<tbody>
						<tr>
							<td class="title" style="border-left: none; width: 200px;">发货形式：</td>
							<td colspan="6">一次性发货</td>
						</tr>
						<tr id="onetime">
							<td class="title" style="border-left: none; width: 200px;">预计发货时间：</td>
							<td style="width: 220px;"><fmt:formatDate
									value="${outRecord.expectedOutstockTime }"
									pattern="yyyy年MM月dd日" /></td>
							<td class="title" style="border-left: none; width: 200px;">发货数量：</td>
							<td>${outRecord.count}</td>
							<td class="title" style="border-left: none; width: 200px;">单位：</td>
							<td>PCS</td>
						</tr>
					</tbody>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						仓库发货详情
					</span>
				</div>

				<table id="manytimeTable" class="table1">
					<tbody>
						<tr>
							<td class="title" style="text-align: left; width: 100px;">
								序号</td>
							<td class="title" style="text-align: left; width: 100px;">
								产品名称</td>
							<td class="title" style="text-align: left; width: 100px;">
								规格型号</td>
							<td class="title" style="text-align: left; width: 100px;">
								数量</td>
							<td class="title" style="text-align: left; width: 100px;">
								单位</td>
							<td class="title" style="text-align: left;">备注</td>
						</tr>
						<c:forEach var="product" varStatus="state" items="${details }">
							<tr>
								<td>${state.index + 1}</td>
								<td>${product.productName }</td>
								<td>${product.productModel }</td>
								<td>${product.count }</td>
								<td>${product.unit }</td>
								<td>${product.remark }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						发货信息
					</span>
				</div>
				<c:if test="${outRecord.state == 0}">
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
								<td><%@ include file="./beginTime.jsp"%></td>
								<td><select name="shipmentCompany" id="shipmentCompany">
										<option value="-1">- 请选择 -</option>
										<option value="顺丰快递">顺丰快递</option>
										<option value="嘉里大通">嘉里大通</option>
										<option value="速通物流">速通物流</option>
										<option value="德邦快递">德邦快递</option>
										<option value="联邦物流">联邦物流</option>
								</select></td>
								<td><input type="text" name="shipmentOrderNum"
									id="shipmentOrderNum"></td>
								<td><input type="file" name="shipmentOrderPic"
									id="shipmentOrderPic"></td>
								<td><input type="text" name="outstockOrderNum"
									id="outstockOrderNum"></td>
								<td><input type="file" name="outstockOrderPic"
									id="outstockOrderPic"></td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<c:if test="${outRecord.state == 1}">
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
								<td><fmt:formatDate value="${outRecord.outstockTime }"
										pattern="yyyy年MM月dd日 HH时mm分" /></td>
								<td>${outRecord.shipmentCompany }</td>
								<td>${outRecord.shipmentOrderNum}</td>
								<td><a
									href="javascript:openPage('${outRecord.shipmentOrderPic}');">(outRecord.shipmentOrderPic, '/')[fn:length(fn:split(outRecord.shipmentOrderPic, '/'))-1]}</a></td>
								<td>${outRecord.outstockOrderNum}</td>
								<td><a
									href="javascript:openPage('${outRecord.outstockOrderPic}');">${fn:split(outRecord.outstockOrderPic, '/')[fn:length(fn:split(outRecord.outstockOrderPic, '/'))-1]}</a></td>
							</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							回单信息
						</span>
					</div>
					<table id="receiveTable" class="table1">
						<tbody>
							<tr>
								<td class="title" style="text-align: left; width: 100px;">
									物流单号</td>
								<td class="title" style="text-align: left; width: 100px;">
									物流单照片</td>
							</tr>
							<tr>
								<td><input type="text" id="shipmentReceiveNum"
									name="shipmentReceiveNum" placeholder="物流单号"></td>
								<td><input type="file" id="shipmentReceivePic"
									name="shipmentReceivePic"></td>
							</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							发货确认
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="text-align: right; width: 100px;">发货确认完成：</td>
								<td>确认发货完成</td>
								<td class="title" style="text-align: right; width: 100px;">批注：</td>
								<td><input type="text" name="comment" value="- 无 -"></td>
								<td class="title" style="text-align: right; width: 100px;">确认时间：</td>
								<%
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
										Date n = new Date();
										String now = sdf.format(n);
										pageContext.setAttribute("now", now);
								%>
								<td>${now }<input type="hidden" name="confirmTime"
									value="${now }"></td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</div>
		</div>
		<c:if test="${outRecord.state == 0}">
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">确认发货</a>
			</div>
		</c:if>
		<c:if test="${outRecord.state == 1}">
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm1();" class="lzui-btn lzui-corner-all">提交发货完成确认</a>
			</div>
		</c:if>

		</form>
	</div>

</div>

<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
</body>
</html>
<script>
	// 提交发货确认
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
	}
	
	function  openPage(url) {
		url = encodeURIComponent(url);
		window.open('${ctx }/web/oa/preview?path='+url,'_blank');
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
	}

	$(function() {
		$('a#物料管理').addClass('ui-tabs-current');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>