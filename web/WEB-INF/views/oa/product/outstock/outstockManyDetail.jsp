<%@page import="com.hj.oa.bean.ProductOutstockDetail"%>
<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../../myheader.jsp"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
.div_content {
	position: fixed;
	top: 150px;
	left: 50%;
	width: 400px;
	margin-left: -240px;
	height: 100px;
	margin-top: -150px;
	padding: 0;
	border: 1px solid black;
	background-color: white;
	_position: absolute;
	z-index: 1011;
	overflow: hidden;
	text-align: center;
}

.div_overlay {
	position: fixed;
	top: 0%;
	left: 0%;
	width: 100%;
	height: 100%;
	background-color: black;
	z-index: 1010;
	-moz-opacity: 0.8;
	opacity: 0;
	filter: alpha(opacity = 0);
}

.way1, .way2, .way3 {
	width: 50%;
	position: relative;
	background: #ededed;
	border-radius: 4px;
	height: 45px;
	line-height: 45px;
}

.way1 input {
	display: block;
	width: 100%;
	height: 45px;
	opacity: 0;
	position: absolute;
	z-index: 3;
	top: 0;
	left: 0;
}

.way1 a {
	display: block;
	width: 100%;
	position: absolute;
	z-index: 2;
	top: 0;
	left: 0;
	text-align: center
}

.way2 label, .way3 label {
	display: block;
	width: 100%;
	height: 45px;
	text-align: center;
}
</style>
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
						items="${outRecord.details }">
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

			<c:if test="${not empty outRecord.wxbh }">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						审批信息
					</span>
				</div>
				<table id="manytimeTable" class="table1">
					<tbody>
						<tr>
							<td class="title" style="text-align: right; width: 100px;">发货时间</td>
							<td><fmt:formatDate
									value="${outRecord.expectedOutstockTime }"
									pattern="yyyy年MM月dd日 " /></td>
							<td class="title" style="text-align: right; width: 100px;">外箱编号</td>
							<td>${outRecord.wxbh }</td>
							<td class="title" style="text-align: right; width: 100px;">标签纸照片</td>
							<td><a href="${ctx }/upload/${outRecord.bqz}"
								target="_blank">点击查看照片</a></td>
						</tr>

					</tbody>
				</table>
			</c:if>
			<div id="detail">
				<c:if test="${outRecord.state == 0}">
					<form name="form1" action="${ctx }/web/oa/product/confirm/outstock"
						method="post" enctype="multipart/form-data">
						<input type="hidden" value="${submitCode}" name="submitCode" /> <input
							type="hidden" value="${record.id}" name="id" /> <input
							type="hidden" value="${outRecord.id}" name="recordId" /> <input
							type="hidden" value="${type}" name="type" /> <input
							type="hidden" value="${record.oddNumber }" name="oddNumber" />
						<div class="tableTitle" style="padding: 10px 20px;">
							<span> <img
								src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								发货信息
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
									<td><%@ include file="./beginTime.jsp"%></td>
									<td><select name="shipmentCompany" id="shipmentCompany">
											<option value="-1">- 请选择 -</option>
											<c:forEach items="${logistics}" var="logistic">
												<option value="${logistic.company}">${logistic.company}</option>
											</c:forEach>
									</select></td>
									<td><input type="text" name="shipmentOrderNum"
										id="shipmentOrderNum"></td>
									<td><input class="infospan" title="请上传jpg格式图片，文件大小不超过2MB"
										type="file" name="shipmentOrderPic" id="shipmentOrderPic"></td>
									<td><input type="text" name="outstockOrderNum"
										id="outstockOrderNum"></td>
									<td><input class="infospan" title="请上传jpg格式图片，文件大小不超过2MB"
										type="file" name="outstockOrderPic" id="outstockOrderPic"></td>
								</tr>
							</tbody>
						</table>
						<div class="" style="margin-top: 10px; margin-bottom: 20px;">
							<a href="javascript:submitForm();"
								class="lzui-btn lzui-corner-all">确认发货</a>
						</div>
						<c:set var="exitId" value="0"></c:set>
				</c:if>



				<c:if test="${outRecord.state >= 1}">
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
									href="javascript:openPage('${outRecord.shipmentOrderPic}');">${fn:split(outRecord.shipmentOrderPic, '/')[fn:length(fn:split(outRecord.shipmentOrderPic, '/'))-1]}</a></td>
								<td>${outRecord.outstockOrderNum}</td>
								<td><a
									href="javascript:openPage('${outRecord.outstockOrderPic}');">${fn:split(outRecord.outstockOrderPic, '/')[fn:length(fn:split(outRecord.outstockOrderPic, '/'))-1]}</a></td>
							</tr>
						</tbody>
					</table>
				</c:if>

				<c:if test="${not empty shTime }">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							物流信息
						</span>
					</div>
					<table id="manytimeTable" class="table1">
						<tbody>
							<tr>
								<td class="title" style="text-align: right; width: 100px;">已收货：</td>
								<td>已收货</td>
								<td class="title" style="text-align: right; width: 100px;">确认时间：</td>
								<td>${shTime}</td>
							</tr>
						</tbody>
					</table>

				</c:if>
				<c:if test="${outRecord.state == 2}">
					<form name="form1" action="${ctx }/web/oa/product/confirm/receive"
						method="post" enctype="multipart/form-data">
						<input type="hidden" value="${submitCode}" name="submitCode" /> <input
							type="hidden" value="${record.id}" name="id" /> <input
							type="hidden" value="${outRecord.id}" name="recordId" /> <input
							type="hidden" value="${type}" name="type" /> <input
							type="hidden" value="${record.oddNumber }" name="oddNumber" />
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
									<td><input class="infospan" title="请上传jpg格式图片，文件大小不超过2MB"
										type="file" id="shipmentReceivePic" name="shipmentReceivePic"></td>
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
						<div class="" style="margin-top: 10px; margin-bottom: 20px;">
							<a href="javascript:submitForm1();"
								class="lzui-btn lzui-corner-all">提交发货完成确认</a>
						</div>
						<c:set var="exitId" value="0"></c:set>
				</c:if>

				<c:if test="${outRecord.state == 1}">
						<div class="tableTitle" style="padding: 10px 20px;">
							<span> <img
								src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								物流信息
							</span>
						</div>
					<form name="form2" action="${ctx }/web/oa/product/gaizhuangtai"
						method="post" enctype="multipart/form-data">
						<input type="hidden" value="${submitCode}" name="submitCode" /> <input
							type="hidden" value="${record.id}" name="id" /> <input
							type="hidden" value="${outRecord.id}" name="recordId" /> <input
							type="hidden" value="${type}" name="type" /> <input
							type="hidden" value="${record.oddNumber }" name="oddNumber" />
						<table id="manytimeTable" class="table1">
							<tbody>
								<tr>
									<td class="title" style="text-align: right; width: 100px;">已收货：</td>
									<td>已收货</td>
									<td class="title" style="text-align: right; width: 100px;">确认时间：</td>
									<td><jsp:include page="../../public/beginTime.jsp" /></td>
								</tr>
							</tbody>
						</table>
						<div class="" style="margin-top: 10px; margin-bottom: 20px;">
							<a href="javascript:submitForm2();"
								class="lzui-btn lzui-corner-all">收货确认</a>
						</div>
				</c:if>
			</div>
		</div>

		</form>
	</div>

</div>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script src="${ctx}/resources/js/alert.js" type="text/javascript"></script>
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
			layer.open({
				title : "提醒",
				content : '请选择发货时间'
			});
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
			layer.open({
				title : "提醒",
				content : '发货时间不能晚于当前时间'
			});
			return;
		}
		if ($('#shipmentCompany').val() == '-1') {
			layer.open({
				title : "提醒",
				content : '请选择物流公司'
			});
			return;
		}
		if ($('#shipmentCompany').val() != '客户自提') {
			if (!$('#shipmentOrderNum').val()) {
				layer.open({
					title : "提醒",
					content : '请填写物流单号'
				});
				return;
			}
			if (!$('#shipmentOrderPic').val()) {
				layer.open({
					title : "提醒",
					content : '请上传物流单照片'
				});
				return;
			}
			if (!/\.(jpg)$/.test($('#shipmentOrderPic').val())) {
				layer.open({
					title : "提醒",
					content : "请上传jpg格式图片"
				});
				return;
			}
			// 校验图片大小
			var shipmentOrderPic = document.getElementById("shipmentOrderPic");
			if (shipmentOrderPic.files) {
				//读取图片数据
				var f = shipmentOrderPic.files[0];
				var size = f.size / (1024 * 1024);
				if (size > 2) {
					layer.open({
						title : "提醒",
						content : '上传文件大小超过2MB限制，请重新上传！'
					});
					return;
				}
			} else {
				layer.open({
					title : "提醒",
					content : '请上传物流单图片'
				});
				return;
			}
		}
		if (!$('#outstockOrderNum').val()) {
			layer.open({
				title : "提醒",
				content : '请填写出库单号'
			});
			return;
		}
		if (!$('#outstockOrderPic').val()) {
			layer.open({
				title : "提醒",
				content : '请上传出库单照片'
			});
			return;
		}
		if (!/\.(jpg)$/.test($('#outstockOrderPic').val())) {
			layer.open({
				title : "提醒",
				content : "请上传jpg格式图片"
			});
			return;
		}
		// 校验图片大小
		var outstockOrderPic = document.getElementById("outstockOrderPic");
		if (outstockOrderPic.files) {
			//读取图片数据
			var f = outstockOrderPic.files[0];
			var size = f.size / (1024 * 1024);
			if (size > 2) {
				layer.open({
					title : "提醒",
					content : '上传文件大小超过2MB限制，请重新上传！'
				});
				return;
			}
		} else {
			layer.open({
				title : "提醒",
				content : '请上传出库单图片'
			});
			return;
		}
		document.form1.submit();
		//	$("form1").submit();
	}
	// 提交收到回执确认
	function submitForm1() {
		// 参数校验
		if (!$('#shipmentReceiveNum').val()) {
			layer.open({
				title : "提醒",
				content : '请填写物流单号'
			});
			return;
		}
		if (!$('#shipmentReceivePic').val()) {
			layer.open({
				title : "提醒",
				content : '请上传物流单照片'
			});
			return;
		}
		if (!/\.(jpg)$/.test($('#shipmentReceivePic').val())) {
			layer.open({
				title : "提醒",
				content : "请上传jpg格式图片"
			});
			return;
		}
		// 校验图片大小
		var shipmentReceivePic = document.getElementById("shipmentReceivePic");
		if (shipmentReceivePic.files) {
			//读取图片数据
			var f = shipmentReceivePic.files[0];
			var size = f.size / (1024 * 1024);
			if (size > 2) {
				layer.open({
					title : "提醒",
					content : '上传文件大小超过2MB限制，请重新上传！'
				});
				return;
			}
		} else {
			layer.open({
				title : "提醒",
				content : '请上传出库单图片'
			});
			return;
		}
		if (!$('#comment').val() || $('comment').val == '') {
			$('#comment').val('- 无 -');
		}
		document.form1.submit();
		//	$("form1").submit();
	}

	$(function() {
		$('a#产品管理').addClass('ui-tabs-current');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function submitForm2() {
		if(!$('#bt').val()) {
			layer.open({
				title : "提醒",
				content : '请选择确认时间'
			});
			return;
		}
		document.form2.submit();
	}
</script>