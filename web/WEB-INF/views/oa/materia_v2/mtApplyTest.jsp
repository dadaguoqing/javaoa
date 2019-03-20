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
		<div class="navTitle2" url="${ctx }/web/oa/materia_v2/mtApply">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<div id='print'>
			<form name="form1" action="${ctx }/web/oa/materia/maApprove"
				method="post" onsubmit="return beforeSubmit();">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${ma.id}" name="id" id="hid" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }物料申领测试结果</span>
					</div>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							物料详细信息
						</span>
					</div>

					<table class="table1">
						<tbody>
							<c:if test="${type == 1}">
								<tr>
									<td class="title" style="text-align: center;">序号</td>
									<td class="title" style="text-align: center;">物料编码</td>
									<td class="title" style="text-align: center;">数量</td>
									<td class="title" style="text-align: center;">位号</td>
									<td class="title" style="text-align: center;">规格型号</td>
									<td class="title" style="text-align: center;">封装</td>
									<td class="title" style="text-align: center;">类型</td>
									<td class="title" style="text-align: center;">品名分类</td>
									<td class="title" style="text-align: center;">品牌</td>
									<td class="title" style="text-align: center;">备注</td>
									<td class="title" style="text-align: center;">需求数量</td>
									<td class="title" style="text-align: center;">欠料数量</td>
									<td class="title" style="text-align: center; width: 10%">说明</td>
								</tr>
							</c:if>
							<c:if test="${type == 2}">
								<tr>
									<td class="title" style="text-align: center;">序号</td>
									<td class="title" style="text-align: center;">物料编码</td>
									<td class="title" style="text-align: center;">品名分类</td>
									<td class="title" style="text-align: center;">规格型号</td>
									<td class="title" style="text-align: center;">封装</td>
									<td class="title" style="text-align: center;">单位</td>
									<td class="title" style="text-align: center;">数量</td>
									<td class="title" style="text-align: center;">备注</td>
									<td class="title" style="text-align: center;">需求数量</td>
									<td class="title" style="text-align: center;">欠料数量</td>
									<td class="title" style="text-align: center; width: 10%">说明</td>
								</tr>
							</c:if>
							<c:if test="${not empty list and type == 2}">
								<c:forEach items="${list}" var="cur" varStatus="status">
									<tr align="center">
										<td style="">${status.count }</td>
										<td style="">${cur.materiaCode }</td>
										<td style="">${cur.classify }</td>
										<td style="">${cur.spec }</td>
										<td style="">${cur.package1 }</td>
										<td style="">${cur.unit }</td>
										<td style="">${cur.num }</td>
										<td style="">${cur.content }</td>
										<td style="">${cur.demandNum }</td>
										<c:if test="${cur.lackNum > 0.0}">
											<td style=""><a style="color: red">${cur.lackNum }</a></td>
										</c:if>
										<c:if test="${cur.lackNum == 0 or empty cur.lackNum}">
											<td style="">${cur.lackNum }</td>
										</c:if>
										<c:if test="${empty cur.remark}">
											<td style="">无</td>
										</c:if>
										<c:if test="${not empty cur.remark}">
											<td style=""><a style="color: red">${cur.remark }</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${not empty list and type == 1}">
								<c:forEach items="${list}" var="cur" varStatus="status">
									<tr align="center">
										<td style="">${status.count }</td>
										<td style="">${cur.materiaCode }</td>
										<td style="">${cur.num }</td>
										<td style="">${cur.itemNum }</td>
										<td style="">${cur.spec }</td>
										<td style="">${cur.package1 }</td>
										<td style="">${cur.type }</td>
										<td style="">${cur.classify }</td>
										<td style="">${cur.brand }</td>
										<td style="">${cur.content }</td>
										<td style="">${cur.demandNum }</td>
										<c:if test="${cur.lackNum > 0.0}">
											<td style=""><a style="color: red">${cur.lackNum }</a></td>
										</c:if>
										<c:if test="${cur.lackNum == 0 or empty cur.lackNum}">
											<td style="">${cur.lackNum }</td>
										</c:if>
										<c:if test="${empty cur.remark}">
											<td style="">无</td>
										</c:if>
										<c:if test="${not empty cur.remark}">
											<td style=""><a style="color: red">${cur.remark }</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</c:if>
							<c:if test="${empty list}">
								<tr>
									<td colspan="13"><a style="color: red">暂无数据。</a></td>
								</tr>
							</c:if>
						</tbody>
					</table>
				</div>
			</form>
		</div>
	</div>

</div>

</body>
</html>
<
<script type="text/javascript">
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
