<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
	.leftpad {
		padding-left: 15px;
		margin-top: 2px;
	}
</style>
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;申请详情
		</div>
			<div class="navTitle2" url="${ctx }/web/oa/external/applyList">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>
		<form name="form1" action="${ctx }/web/oa/external/handApprove" id="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<input type="hidden" id="id" name="id" value="${ea.id}"/>
			<div style="border: 1px solid #2191C0;">
				<div id="content"></div>
			<c:if test="${ea.status >= 9}">
				<div class="tableTitle" style="padding: 10px 20px;">
							<span> <img
								src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								入库详情
							</span>
					</div>
					<table class="table1">
						<c:forEach items="${types}" var="type">
							<c:if test="${type == '1' }">
								<c:if test="${empty ei1.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">PCB入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei1.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">PCB入库数量:</td>
										<td >${ei1.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei1.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '2' }">
								<c:if test="${empty ei2.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">焊接入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei2.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">焊接入库数量:</td>
										<td >${ei2.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei2.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '3' }">
								<c:if test="${empty ei3.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">钢网入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei3.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">钢网入库数量:</td>
										<td >${ei3.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei3.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '4' }">
								<c:if test="${empty ei4.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">打白胶入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei4.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">打白胶入库数量:</td>
										<td >${ei4.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei4.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '5' }">
								<c:if test="${empty ei5.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">三防漆入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei5.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">三防漆入库数量:</td>
										<td >${ei5.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei5.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '6' }">
								<c:if test="${empty ei6.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">亚克力入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei6.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">亚克力入库数量:</td>
										<td >${ei6.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei6.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							<c:if test="${type == '7' }">
								<c:if test="${empty ei7.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">机箱入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei7.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">机箱入库数量:</td>
										<td >${ei7.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei7.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '8' }">
								<c:if test="${empty ei8.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">线束入库:</td>
										<td colspan="3">未入库</td>
									</tr>
								</c:if>
								<c:if test="${not empty ei8.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">线束入库数量:</td>
										<td >${ei8.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei8.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
						</c:forEach>
					</table>
			</c:if>
			</div>
		</form>
	</div>
</div>

</body>
</html>
<script type="text/javascript">
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#外协加工申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		
		getContent();
	});
	
	function getContent() {
		$.get("${ctx}/web/oa/external/getContent", {id : $('#id').val()}, function(result) {
			$('#content').html(result);
		})
	}
	
	

</script>