<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<c:forEach items="${loginUserMenuMap['4']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
			</div>
		</c:forEach>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>
		<div class="gridContainer"
			style="width: 95%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="7">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								申请记录</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header" style="width: 80px;">申请人</td>
						<td class="l-td-header" style="width: 80px;">代理人</td>
						<td class="l-td-header" style="width: 200px;">申请时间</td>
						<td class="l-td-header" style="width: 200px;">开始时间</td>
						<td class="l-td-header" style="width: 200px;">结束时间</td>
						<td class="l-td-header" style="width: 200px;">代理详情</td>
						<td class="l-td-header l-td-left" style="width: 170px;">审批状态</td>
					</tr>

					<c:forEach items="${list}" var="cur">
						<tr>
							<td class="l-td">${cur.sender}</td>
							<td class="l-td">${empty AllUsers[cur.uid].name ? '-' : AllUsers[cur.uid].name}</td>
							<td class="l-td l-td-left">${cur.createTime}</td>
							<td class="l-td l-td-left">${cur.beginTime}</td>
							<td class="l-td l-td-left">${cur.endTime}</td>
							<td class="l-td l-td-left limitText" title="${cur.content}">${cur.content}</td>
							<td class="l-td l-td-left"><c:choose>
									<c:when test="${cur.status == -1}">
										<span style="color: gray">审批不通过</span>
									</c:when>
									<c:when test="${cur.status == 0}">
										<span style="color: red">审批中</span>
									</c:when>
									<c:when test="${cur.status == 1}">
										<span style="color: green">审批通过</span>
									</c:when>
								</c:choose></td>
						</tr>
					</c:forEach>

					<c:if test="${empty list}">
						<tr>
							<td class="l-td l-td-last-right" colspan="6" style="color: red;">
								对不起，目前还没有相关数据。</td>
						</tr>
					</c:if>
				</tbody>
			</table>

		</div>
	</div>
</div>

</body>
<script>
	$(function() {
		$('a#OA申请').addClass('ui-tabs-current');
		$('#权限代理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</html>