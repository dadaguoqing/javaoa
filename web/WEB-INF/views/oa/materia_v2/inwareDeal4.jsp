<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<c:forEach items="${loginUserMenuMap['5']}" var="cur">
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
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="7">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								记录列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header " style="width: 20%;">申领人</td>
						<td class="l-td-header" style="width: 20%;">申请时间</td>
						<td class="l-td-header" style="width: 20%;">入库编号</td>
						<td class="l-td-header " style="width: 20%;">状态</td>
						<td class="l-td-header " style="">查看详情</td>
					</tr>

					<c:forEach items="${list}" var="cur">
						<tr>
							<td class="l-td">${AllUsers[cur.empId].name }</td>
							<td class="l-td">${cur.daystr }</td>
							<td class="l-td">${cur.requisitionCode }</td>
							<td class="l-td">
								<c:if test="${cur.status == 1}">
									检验员处理中
								</c:if>
								<c:if test="${cur.status == 2}">
									一级主管审批中
								</c:if>
								<c:if test="${cur.status == 3}">
									仓库管理员审批中
								</c:if>
								<c:if test="${cur.status == 4}">
									审批成功
								</c:if>
								<c:if test="${cur.status == 5}">
									审批成功
								</c:if>
								<c:if test="${cur.status == -1}">
									审批取消
								</c:if>
							</td>
							<td class="l-td"><a
								href="${ctx }/web/oa/materia_v2/inwareDetailRecord?id=${cur.id}&type=2">查看详情</a></td>
						</tr>
					</c:forEach>
					<c:if test="${empty list}">
						<tr>
							<td class="l-td-last-right" colspan="7" style="color: red;">
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
		$('#OA审批').addClass('ui-tabs-current');
		$('#物料入库审批').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>