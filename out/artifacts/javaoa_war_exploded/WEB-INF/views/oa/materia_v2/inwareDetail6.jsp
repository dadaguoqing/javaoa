<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
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
		<div class="navTitle2" url="${ctx }/web/oa/materia_v2/myRk">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
			<a href="javascript:void(0);" onclick="exportInbound()">导出Excel</a>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>
		<div id='print'>
			<form name="form1" action="${ctx }/web/oa/materia_v2/inwareApprove2"
				method="post" onsubmit="return beforeSubmit();">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${ma.id}" name="id" id="hid" />
				<div style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }物料入库申请单</span>
					</div>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							基本信息
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title">申请人：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].name }</td>
								<td class="title">所属部门：</td>
								<td>${AllUsers[ma.empId].deptName }</td>
								<td class="title">申请时间：</td>
								<td>${ma.daystr }</td>
							</tr>
							<c:if test="${type != 3 }">
								<tr>
									<td class="title">编号：</td>
									<td>${ma.requisitionCode }</td>
									<td class="title">入库类型：</td>
									<td colspan="3">采购入库</td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							物料详细信息
						</span>
					</div>
					<table class="table1" id="data">
						<tbody>
							<tr>
								<td class="title" style="text-align: center; width: 60px;">序号</td>
								<td class="title" style="width: 100px;text-align: center;">物料编码</td>
								<td class="title" style="text-align: center;width: 120px;">品名分类</td>
								<td class="title" style="text-align: center;width: 120px;">品牌</td>
								<td class="title" style="text-align: center;width: 120px;">规格型号</td>
								<td class="title" style="text-align: center;width: 120px;">封装</td>
								<td class="title" style="text-align: center;width: 60px;">单位</td>
								<td class="title" style="text-align: center;width: 120px;">供应商</td>
								<td class="title" style="text-align: center;width: 80px;">采购数量</td>
								<td class="title" style="text-align: center;width: 100px;">检验结果</td>
								<td class="title" style="text-align: center;width: 100px;">实际入库数量</td>
								<td class="title" style="text-align: center;width: 100px;">入库仓库</td>
							</tr>
							<c:forEach items="${list}" var="cur" varStatus="i">
								<tr>
										<td style="text-align:center;">${i.count}</td>
										<td>${cur.needDate}</td>
										<td style="" align="left">${cur.classify }</td>
										<td style="" align="left">${cur.brand }</td>
										<td style="" align="left">${cur.spec }</td>
										<td style="" align="left">${cur.package1 }</td>
										<td style="">${cur.unit }</td>
										<td style="">${cur.supplier}</td>
										<td style="">${cur.buynum }</td>
										<td style="">${cur.link}</td>
										<td>${cur.needNum}</td>
										<td>
											<c:forEach items="${whs}" var="wh">
												<c:if test="${wh.id ==  cur.useDate}">
													${wh.warehouse}
												</c:if>
											</c:forEach>
										</td>
									</tr>
							</c:forEach>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							备注信息
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td style="border-right: none;"><textarea name="bz" id="bz"
										disabled="disabled"
										style="width: 50%; height: 80px; padding: 5px 10px;">${ma.content}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							审批记录
						</span>
					</div>
					<table class="table1">
						<tr>
								<c:choose>
									<c:when test="${ma.purchaseCode == '研发物料'}">
										<td style="width: 10%;">质检员处理结果</td>
										<td style="width: 15%;"><span style="color: gray;">此次处理不需要经过此流程</span></td>
									</c:when>
									<c:when test="${ma.status == 1}">
										<td style="width: 10%;">质检员处理结果<span style="color: red;">
										（${AllUsers[ma.currentId].name}）</span></td>
										<td style="width: 15%;"><span style="color: green;">正在处理</span></td>
									</c:when>
									<c:when test="${not empty approve1}">
										<td style="width: 10%;">质检员处理结果
											<span style="color: red;">（${AllUsers[approve1.approveId].name}）</span></td>
										<td style="width: 10%;">${approve1.approveOpinion}</td>
									</c:when>
								</c:choose>
							<td style="width: 15%;"><span style="color: green;">${approve1.approveResult}</span></td>
							<td style="width: 10%;">${approve1.approveDate}</td>
						</tr>
						<tr>
								<c:choose>
									<c:when test="${ma.status == 3}">
										<td style="width: 10%;">仓库管理员处理结果 <span style="color: red;">
										（${AllUsers[ma.currentId].name}）</span></td>
										<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									</c:when>
									<c:when test="${not empty approve3}">
										<td style="width: 10%;">仓库管理员处理结果 <span style="color: red;">
										（${AllUsers[approve3.approveId].name}）</span></td>
										<td style="width: 15%;"><span style="color: green;">${approve3.approveResult}</span></td>
									</c:when>
									<c:when test="${ma.status < 3}">
										<td style="width: 10%;">仓库管理员处理结果</td>
										<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
									</c:when>
								</c:choose>
							<td style="width: 10%;">${approve2.approveOpinion}</td>
							<td style="width: 10%;">${approve2.approveDate}</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
	</div>
</div>
</body>
</html>
<script>
	var index;
	var type = '${type}';

	$(function() {
		if (type == 1 || type == 3) {
			$('#物料管理').addClass('ui-tabs-current');
		} else {
			$('#OA审批').addClass('ui-tabs-current');
		}
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function printHtml() {
		var body1 = "<div align='center' style='width:80%'>"
				+ $('#print').html() + "</div>";
		$('body').html(body1);
		window.print();
	}

	function cancle() {
		var id = $('#hid').val();
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "cancleApply",
			data : {
				type : "apply",
				id : id
			},
			success : function(data) {
				layer.alert(data);
				location.reload(location.href);
			},
			error : function() {
				layer.alert('出错');
			}
		});
	}
	function inware() {
		var flag = false;
		$('input[name="maCode"]').each(function(i, obj) {
			var item = obj.value;
			if (!item || item.length != 12) {
				flag = true;
			}
		});
		if (flag) {
			layer.alert('物料编码为空或长度不为12');
			return;
		}

		var flag1 = true;
		$('input[name="buynum"]').each(function(i, obj) {
			var item = obj.value;
			var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
			if (!reg.test(item)) {
				layer.alert('数量格式错误');
				flag1 = true;
			}
			if (!item || Number(item) <= 0) {
				layer.alert('数量不能为空或小于0');
				flag1 = true;
			}
		});

		if (flag1) {
			return;
		}

		if ($('#warehouseId').val() == 0) {
			layer.alert('请选择入库仓库');
			return;
		}
		layer.open({
			content : '确认入库？',
			btn : [ '确认', '取消' ],
			yes : function() {
				form1.action = "${ctx }/web/oa/materia_v2/doInware";
				document.form1.submit();
			}
		});
	}
	
	function exportInbound() {
		var table = $('#data').html();
		var date = new Date();
		var day = date.getFullYear() + '' + Number(date.getMonth()+1) + date.getDate();
		var fileName = 'inboundRecord-' + day + '.xls';
		exportExcel(table, fileName);
	}
	
	function submitForm() {
		if (type == 3) {
			var warehouseId = $('#warehouseId').val();
			if (warehouseId == '0') {
				layer.alert('请选择入库仓库');
				return;
			}
		}

		var sp = $('#tg').val();
		if (sp == '通过') {
			var opinion = $('#opinion').val();
			if (!opinion) {
				layer.alert('请填写补充意见');
			}
		}
		document.form1.submit();
	}
</script>