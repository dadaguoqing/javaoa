<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
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
		<c:choose>
			<c:when test="${type == 1 }">
				<div class="navTitle2" url="${ctx }/web/oa/materia_v2/myInwareList">
					<img src="${ctx }/resources/images/item.png"
						style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
				</div>
			</c:when>
			<c:when test="${type == 2 }">
				<div class="navTitle2" url="${ctx }/web/oa/materia/myInwareList">
					<img src="${ctx }/resources/images/item.png"
						style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
				</div>
			</c:when>
			<c:when test="${type == 3 }">
				<div class="navTitle2" url="${ctx }/web/oa/materia/myInwareList">
					<img src="${ctx }/resources/images/item.png"
						style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
				</div>
			</c:when>
		</c:choose>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<div class="" style="margin-top: 10px; margin-bottom: 20px;">
			<a href="${ctx}/excel/${ma.url}" class="lzui-btn lzui-corner-all">导出Excel</a>
		</div>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>
		<div id='print'>
			<form name="form1" action="${ctx }/web/oa/materia_v2/maApprove"
				method="post" onsubmit="return beforeSubmit();">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${ma.id}" name="id" id="hid" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
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
								<td>${AllUsers[ma.empId].name }</td>
								<td class="title">所属部门：</td>
								<td>${AllUsers[ma.empId].deptName }</td>
								<td class="title">申请时间：</td>
								<td>${ma.daystr }</td>
							</tr>
							<tr>
								<td class="title">编号：</td>
								<td>${ma.requisitionCode }</td>
								<td class="title">质检员：</td>
								<td>${jyr.name}</td>
								<td class="title">物料类型：</td>
								<td >${ma.purchaseCode}</td>
							</tr>
						</tbody>
					</table>

					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							物料详细信息
						</span>
					</div>

					<table class="table1">
						<tbody>
							<tr>
							<td class="title" style="text-align: center;width: 40px;">序号</td>
							<td class="title" style="text-align: center;width: 120px;">
								物料编码</td>
							<td class="title" style="text-align: center;width: 120px;">品名分类</td>
							<td class="title" style="text-align: center;width: 120px;">品牌</td>
							<td class="title" style="text-align: center;width: 120px;">规格型号</td>
							<td class="title" style="text-align: center;width: 120px;">封装</td>
							<td class="title" style="text-align: center;width: 50px;">单位</td>
							<td class="title" style="text-align: center;width: 120px;">供应商</td>
							<td class="title" style="text-align: center;width: 100px;">单价（元）</td>
							<td class="title" style="text-align: center;width: 140px;">其他费用（元）</td>
							<td class="title" style="text-align: center;width: 80px;">采购数量</td>
						</tr>

							<c:forEach items="${list}" var="cur" varStatus="i">
								<tr>
									<td style="text-align:center;">${i.count}</td>
									<td title="${cur.maCode }">${cur.maCode }</td>
									<td title="${cur.classify }">${cur.classify }</td>
									<td title="${cur.brand }">${cur.brand }</td>
									<td title="${cur.spec }">${cur.spec }</td>
									<td title="${cur.package1 }">${cur.package1 }</td>
									<td title="${cur.unit }">${cur.unit }</td>
									<td title="${cur.supplier }">${cur.supplier }</td>
									<td title="${cur.price }">${cur.price }</td>
									<td title="${cur.cost }">${cur.cost }</td>
									<td title="${cur.buynum }">${cur.buynum }</td>
								</tr>
							</c:forEach>
						</tbody>
						<tr>
							<td class="title" colspan="2">来料检验报告照片：</td>
							<td colspan="10"><c:if test="${ not  empty  ma.url2}">
									<a href="${ctx}/upload${ma.url2}" target="_blank">查看来料检验报告</a>
								</c:if> <c:if test="${ empty  ma.url2}">
									暂无附件
								</c:if></td>
						</tr>
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
		<div class="" style="margin-top: 10px; margin-bottom: 20px;">
			<c:if test="${ma.status == 1}">
					<a href="javascript:cancelInbound();" class="lzui-btn lzui-corner-all">撤销申请</a>
			</c:if>
			<a href="javascript:printHtml();" class="lzui-btn lzui-corner-all">打印</a>
		</div>
	</div>
</div>
</body>
</html>
<script>
	var index;
	$(function() {
		var type = '${type}';
		if (type == 1) {
			$('#OA申请').addClass('ui-tabs-current');
		} else {
			$('#物料管理').addClass('ui-tabs-current');
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

	function submitForm() {
		var opinion = $('#opinion').val();
		yj = $.trim(opinion);
		if (!yj) {
			alert('请输入补充意见');
			return;
		}
		document.form1.submit();
	}
	
	
	function cancelInbound() {
		$.post("cancelInbound", {id : $('#hid').val()}, function(res) {
			location.href = "${ctx}/web/oa/materia_v2/myInwareList?msg=1";
		});
	}
</script>