<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../public/header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['14']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
		</c:forEach>
		<%-- <%@ include file="../../../public/lead.jsp"%> --%>
		<%-- 		<jsp:include page="../public/lead.jsp"> --%>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/product/outstock/records">查看出库记录</a>
		</div>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="7">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								待出库单号列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header l-td-left" style="width: 200px;">预计发货时间</td>
						<td class="l-td-header l-td-left" style="width: 200px;">发货状态</td>
						<td class="l-td-header l-td-last-right" style="">查看详情</td>
					</tr>

					<c:forEach items="${list}" var="cur">
						<tr>
							<td class="l-td l-td-left">
								<fmt:formatDate value="${cur.expectedOutstockTime}" pattern="yyyy年MM月dd日"/>
							</td>
							<td class="l-td l-td-left">
								<c:if test="${cur.state == 0}">未发货</c:if>
								<c:if test="${cur.state == 1}">已发，待确认</c:if>
								<c:if test="${cur.state == 2}">已收货，回单待确认</c:if>
								<c:if test="${cur.state == 3}">已完成</c:if>
							</td>
							<td class="l-td"><a
								href="${ctx }/web/oa/product/outstock/${cur.id}">查看详情</a></td>
						</tr>
					</c:forEach>

					<c:if test="${empty list}">
						<tr>
							<td class="l-td-last-right" colspan="4" style="color: red;">
								对不起，目前还没有相关数据。</td>
						</tr>
					</c:if>
				</tbody>
			</table>

		</div>
	</div>
</div>
<script>
	$(function() {
		$('#OA处理').addClass('ui-tabs-current');
		$('#产品出库').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>