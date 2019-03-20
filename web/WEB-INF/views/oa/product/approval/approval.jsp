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
		<div class="navTitle2"
			url="${ctx }/web/oa/product/outstock/approval/list">
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
				href="${ctx}/web/oa/product/approval/records/${record.id}">点击查看审批记录</a>
		</div>
		<form name="form1" action="${ctx }/web/oa/product/outstock/approval"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" /> <input
				type="hidden" value="${record.id}" name="recordId" />
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
							<td class="title" colspan="2"></td>
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
							<td class="title" style="text-align: left; width: 100px;">
								序号</td>
							<td class="title" style="text-align: left; width: 100px;">
								产品类型</td>
							<td class="title" style="text-align: left; width: 100px;">
								产品型号</td>
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
								审批结果：</td>
							<c:if test="${not empty approvals}">
								<td><c:if test="${approvals[0].state == 1 }">同意</c:if> <c:if
										test="${approvals[0].state == 0 }">不同意</c:if></td>
							</c:if>
							<c:if test="${empty approvals}">
								<td><select id="state" name="state"
									onchange="onSelect(this);">
										<option value="1">同意</option>
										<option value="-1">不同意</option>
								</select></td>
							</c:if>

							<td class="title" style="border-left: none; width: 100px;">批注：</td>
							<c:if test="${not empty approvals}">
								<td>${approvals[0].comment }</td>
							</c:if>
							<c:if test="${empty approvals}">
								<td><input id="comment" name="comment" type="text"
									placeholder="- 无 -"></td>
							</c:if>
							<td class="title" style="border-left: none; width: 100px;">审批时间：</td>
							<td><c:if test="${empty approvals}">
									<%
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
											Date n = new Date();
											String now = sdf.format(n);
											pageContext.setAttribute("now", now);
									%> ${now} <input type="hidden" value="${now}" name="createTime">
								</c:if> <c:if test="${not empty approvals}">
									<fmt:formatDate value="${approvals[0].updateTime }"
										pattern="yyyy年MM月dd日 HH时mm分" />
								</c:if></td>
						</tr>
					</tbody>
				</table>

				<c:if test="${not empty outRecords}">
					<div id="outstock" class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
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
									<td><fmt:formatDate
											value="${record.expectedOutstockTime }" pattern="yyyy年MM月dd日" /></td>
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
									<td class="title" style="text-align: left; width: 100px;">
										单位</td>
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
												<td class="title" style="text-align: center; width: 100px;"
													colspan="2">发货时间</td>
												<td colspan="3"><fmt:formatDate
														value="${outRecord.outstockTime }"
														pattern="yyyy年MM月dd日 HH时mm分" /></td>
											</tr>
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
												<td><a href="javascript:openPage('${outRecord.shipmentOrderPic}');">${fn:split(outRecord.shipmentOrderPic, '/')[fn:length(fn:split(outRecord.shipmentOrderPic, '/'))-1]}</a></td>
												<td>${outRecord.outstockOrderNum}</td>
												<td><a href="javascript:openPage('${outRecord.outstockOrderPic}');">${fn:split(outRecord.outstockOrderPic, '/')[fn:length(fn:split(outRecord.outstockOrderPic, '/'))-1]}</a></td>
											</tr>
										</c:if>
									</c:forEach>
								</c:if>


							</c:forEach>
						</tbody>
					</table>
				</c:if>

				<div id="detail">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							发货产品
						</span>
					</div>

					<table id="manytimeTable" class="table1">
						<tbody id="data">
							<tr>
								<td class="title" style="text-align: left; width: 100px;">
									产品名称-规格型号</td>
								<td class="title" style="text-align: left; width: 100px;">
									数量</td>
								<td class="title" style="text-align: left; width: 100px;">
									单位</td>
								<td class="title" style="text-align: left; width: 100px;">
									发货日期</td>
								<td class="title" style="text-align: left; width: 100px;">
									外箱编号</td>
								<td class="title" style="text-align: left; width: 100px;">
									标签纸照片</td>
								<td class="title" style="text-align: left; width: 100px;">
									操作</td>
							</tr>
							<tr>
								<td><select id="name" onchange="selectProduct(this);">
										<option value="-1">- 请选择 -</option>
								</select> <input id="productId" type="hidden" /></td>
								<td><input type="text" id="count"></td>
								<td><span id="unit"></span></td>
								<td><%@ include file="./beginTime.jsp"%></td>
								<td title="请填写发货外箱编号"><input placeholder="发货外箱编号"
									name="wxbh" id="wxbh" /></td>
									<td id="filetd">
										<input type="file" name="bqzs" id="file">
									</td>
<!-- 								<td title="请上传标签纸照片">请添加后上传文件</td> -->
								<td style="width: 60px; border-right: none;"><a
									href="javascript:addcd();">确定</a></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交审批结果</a>
			</div>

		</form>
	</div>

</div>
<div class="div_overlay" style="display: none;"></div>
<div class="div_content" style="display: none;">
	<div style="margin-top: 20px;" id="content"></div>
	<div
		style="position: relative; margin-top: 20px; margin-right: 30px; float: right;">
		<button id="alertBtn" class="lzui-btn lzui-corner-all">确定</button>
	</div>
</div>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script src="${ctx}/resources/js/alert.js" type="text/javascript"></script>
<script>
	var countObj = {};
	var approvalState = 0;
	<c:forEach var="detail" items="${details}">
		if(countObj['${detail.productId}']) {
			countObj['${detail.productId}'] = Number(countObj['${detail.productId}']) + Number('${detail.count}');
		} else {
			countObj['${detail.productId}'] = '${detail.count}';
		}
	</c:forEach>
	<c:if test="${not empty outRecords}">
	<c:forEach var="record" items="${outRecords}">
	<c:forEach var="det" items="${record.details}">
	countObj['${det.productId}'] = parseInt(countObj['${det.productId}'])
			- parseInt('${det.count}');
	</c:forEach>
	</c:forEach>
	</c:if>
	<c:if test="${ not empty outRecords}">
	approvalState = 1;
	</c:if>
</script>

<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
</body>
</html>
<script>
	var count = 3;
	var index = 1;
	
	function  openPage(url) {
		url = encodeURIComponent(url);
		window.open('${ctx }/web/oa/preview?path='+url,'_blank');
	}
	
	function addcd() {
		var name = $('#name').val();
		var nameText = $('#name').find("option:selected").text();
		var productId = $('#productId').val();

		var fhDate = $('#bt').val();
		var wxbh = $('#wxbh').val();

		if (!fhDate) {
			layer.open({
				title : "提醒",
				content : '请选择发货日期'
			});
			return;
		}

		if (!wxbh) {
			layer.open({
				title : "提醒",
				content : '请填写外箱编号'
			});
			return;
		}

		if (name == -1) {
			layer.open({
				title : "提醒",
				content : '请选择产品'
			});
			return;
		}
		var productCount = $.trim($('#count').val());
		if (!productCount) {
			layer.open({
				title : "提醒",
				content : '请输入数量'
			});
			return;
		}
		var regu = /^[1-9]\d*$/;
		if (!regu.test(productCount)) {
			layer.open({
				title : "提醒",
				content : '请输入正确数量'
			});
			return;
		}

		var unit = $('#unit').text();
		
		if ((parseInt(countObj[productId]) - parseInt(productCount)) < 0) {
			layer.open({
				title : "提醒",
				content : '发货数量不能大于剩余待发货数量！'
			});
			return;
		}

		countObj[productId] = parseInt(countObj[productId])
				- parseInt(productCount);

		if (productId == -1) {
			layer.open({
				title : "提醒",
				content : '请选择发货产品！'
			});
			return;
		}
		if (productCount == -1) {
			layer.open({
				title : "提醒",
				content : '请选择发货数量！'
			});
			return;
		}
		
		var file = $('#file').clone(true);
		
		var fileV = $('#file').val();
		if (!fileV) {
			layer.open({
				title : "提醒",
				content : '请选择上传图片！'
			});
			return;
		}
		count++;
		var html = '<tr id="cd'+count+'" class="cd'+count+'">'
				+ '<td style="">'
				+ nameText
				+ '</td>'
				+ '<input type="hidden" name="productIds" value="' + name + '"/>'
				+ '<td style="">'
				+ productCount
				+ '</td>'
				+ '<input type="hidden" name="counts" value="' + productCount + '"/>'
				+ '<td style="">'
				+ unit
				+ '</td>'
				+ '<input type="hidden" name="units" value="' + unit + '"/>'
				+ '<td>'
				+ fhDate
				+ '<input type="hidden" name="fhDates" value="' + fhDate + '"/></td>'
				+ '<td>'
				+ wxbh
				+ '<input type="hidden" name="wxbhs" value="' + wxbh + '"/></td>'
				+ '<td id="newFile'+count+'"></td>'
				+ '<td style=" border-right:none; "><a href="javascript:delcd('
				+ count + ',' + name + ');">删除</a></td>' + '</tr>';
		$('#data').append(html);
		$('#newFile'+count).append(file);
		
		$('#name').val("-1");
		$('#count').val('');
		$('#bt').val('');
		$('#wxbh').val('');
		$('#file').val('');
		index++;
		
		
	}

	function delcd(tid, productId) {
		var num = $('.cd' + tid).children('td').eq(1).text();
		countObj[productId] = parseInt(countObj[productId]) + parseInt(num);
		$('.cd' + tid).remove();
		index--;
	}

	function onSelect(obj) {
		var val = $(obj).val();
		if (val == 1) {
			$('#detail').show();
		} else {
			$('#detail').hide();
		}
	}

	// 表单提交
	function submitForm() {
		// 未审批
		if (approvalState == 0) {
			var comment = $('#comment').val();
			if (!comment) {
				$('#comment').val('- 无 -');
			}

			var state = $('#state').val();
			if (state == 1) {
				if (index == 1) {
					layer.open({
						title : "提醒",
						content : '请添加发货产品！'
					});
					return;
				}
			}
		} else { // 已审批
			if (index == 1) {
				layer.open({
					title : "提醒",
					content : '请添加发货产品！'
				});
				return;
			}
		}
		document.form1.submit();
	}

	$(function() {
		$('#产品管理').addClass('ui-tabs-current');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		$('#manytime').show();
		//		$('#onetime').show();

		// 加载选择数据
		var detailsStr = '${jsonDetails}';
		var details = JSON.parse(detailsStr);
		for (var i = 0; i < details.length; i++) {
			$('#name').append(
					'<option value="' + details[i].id + '">'
							+ details[i].productName + '-'
							+ details[i].productModel + '</option>');
		}
	});

	function selectProduct(obj) {
		var val = $(obj).val();
		if (val == -1) {
			$('#count').val('');
		} else {
			$('#count').val('');
			var detailsStr = '${jsonDetails}';
			var details = JSON.parse(detailsStr);
			for (var i = 0; i < details.length; i++) {
				if (val == details[i].id) {
					$('#unit').text(details[i].unit);
					$('#productId').val(details[i].productId);
					return;
				}
			}

		}
	}
</script>