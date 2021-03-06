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
		<div class="navTitle2" url="${ctx }/web/oa/materia_v2/myRecord">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
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
			<form name="form1" action="${ctx }/web/oa/materia/maApprove"
				method="post" onsubmit="return beforeSubmit();">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${ma.id}" name="id" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }物料申领单</span>
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
								<td class="title" style="width: 10%;">申领人：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].name }</td>
								<td class="title" style="width: 10%;">所属部门：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].deptName }</td>
								<td class="title" style="width: 100px;">申领时间：</td>
								<td style="width: 170px;">${ma.dayStr }</td>
							</tr>
							<tr>
								<td class="title" style="width: 10%;">申领用途：</td>
								<td style="width: 10%;">${ma.use1 }</td>
								<td class="title" style="width: 10%;">编号：</td>
								<td style="width: 10%;">${ma.code }</td>
								<td class="title" style="width: 100px;">申领仓库：</td>
								<td style="width: 170px;">${ma.warehouse }</td>
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
								<td class="title" style="text-align: center; width: 10%;">
									物料编码</td>
								<td class="title" style="text-align: center; width: 10%;">
									品名分类</td>
								<td class="title" style="text-align: center; width: 10%;">
									品牌</td>
								<td class="title" style="text-align: center; width: 100px;">
									规格型号</td>
								<td class="title" style="text-align: center; width: 100px;">
									封装</td>
								<td class="title" style="text-align: center; width: 100px;">
									申请数量</td>
							</tr>

							<c:forEach items="${list}" var="cur">
								<tr align="center">
									<td style="">${cur.materiaCode }</td>
									<td style="">${cur.classfiy }</td>
									<td style="">${cur.brand }</td>
									<td style="">${cur.spec }</td>
									<td style="">${cur.package1 }</td>
									<td style="">${cur.num }</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="title" style="text-align: right; width: 100px;">
									附件:</td>
								<td colspan="10"><c:if test="${not empty ma.url2 }">
										<a href="${ctx }/upload/${ma.url2}">下载附件</a>
									</c:if> <c:if test="${empty ma.url2 }">
										<a style="color: red">未上传附件</a>
									</c:if></td>
							</tr>
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
										style="width: 50%; height: 80px; padding: 5px 10px;">${ma.bz }</textarea>
									<span style="color: red;"></span></td>
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
							<td style="width: 10%;">仓库管理员确认结果 <c:if
									test="${ma.aId  != 0 }">
									<span style="color: red;">(${AllUsers[ma.aId].name})</span>
								</c:if>
							</td>
							<td style="width: 10%;"><span>${ma.aResult }</span></td>
							<td style="width: 15%;"><c:if
									test="${ ma.currentId == ma.aId && ma.status == 4}">
									<span style="color: green;">正在确认</span>
								</c:if> <c:if test="${ not empty ma.aOpinion}">
									<span style="">${ma.aOpinion}</span>
								</c:if> <c:if
									test="${ empty ma.aOpinion && ma.status < 4 && ma.status != -1 }">
									<span style="color: #666;">暂未到达该流程</span>
								</c:if></td>
							<td style="width: 15%;">${ma.aDayStr }</td>
						</tr>
					</table>
				</div>
			</form>
		</div>
		<div class="" style="margin-top: 10px; margin-bottom: 20px;">
			<a href="javascript:printHtml();" class="lzui-btn lzui-corner-all">打印</a>
		</div>
	</div>

</div>

</body>
</html>
<script>
	var index;

	$(function() {
		$('#物料管理').addClass('ui-tabs-current');
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
</script>