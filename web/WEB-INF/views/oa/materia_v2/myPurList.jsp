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
		<c:choose>
			<c:when test="${type == 1}">
				<c:forEach items="${loginUserMenuMap['4']}" var="cur">
					<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
						<img src="${ctx }/resources/images/item.png"
							style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
					</div>
				</c:forEach>
			</c:when>
			<c:when test="${type != 1}">
				<%@ include file="../../public/lead.jsp"%>
			</c:when>
		</c:choose>
		<%-- 		<%@ include file="../../public/lead.jsp"%> --%>
		<%-- 		<c:forEach items="${loginUserMenuMap['4']}" var="cur"> --%>
		<%-- 			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"> --%>
		<%-- 				<img src="${ctx }/resources/images/item.png" --%>
		<%-- 					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name } --%>
		<!-- 			</div> -->
		<%-- 		</c:forEach> --%>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>

		<c:if test="${type == 1}">
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;" href="${ctx}/web/oa/materia_v2/purchase">返回物料采购</a>
			</div>
		</c:if>
		<c:if test="${type == 2}">
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;"
					href="${ctx}/web/oa/materia_v2/purchaseList">返回物料审批</a>
			</div>
		</c:if>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="8">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								待审批列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header " style="width: 5%;">申请人</td>
						<td class="l-td-header " style="width: 5%;">请购人</td>
						<td class="l-td-header" style="width: 13%;">项目编号</td>
						<td class="l-td-header" style="width: 13%;">编号</td>
						<td class="l-td-header" style="width: 10%;">审批状态</td>
						<td class="l-td-header" style="width: 25%;">采购事由</td>
						<td class="l-td-header" style="width: 18%;">申领时间</td>
						<td class="l-td-header " style="">查看详情</td>
					</tr>

					<c:forEach items="${list}" var="cur">
						<tr>
							<td class="l-td">${AllUsers[cur.empId].name }</td>
							<td class="l-td">${AllUsers[cur.qgId].name }</td>
							<td class="l-td">${cur.projectCode }</td>
							<td class="l-td">${cur.purchaseCode }</td>
							<td class="l-td"><c:if test="${cur.status == 1}">
									一级主管审批中
								</c:if> <c:if test="${cur.status == 2}">
									二级主管审批中
								</c:if> <c:if test="${cur.status == 3}">
									财务主管审批中
								</c:if> <c:if test="${cur.status == 4}">
									总经理审批中
								</c:if> <c:if test="${cur.status == 5}">
									审批通过
								</c:if> <c:if test="${cur.status == -1}">
									审批取消
								</c:if></td>
							<td class="l-td limitText" title="${cur.reason}">
								${cur.reason}</td>
							<td class="l-td">${cur.daystr }</td>
							<c:choose>
								<c:when test="${type == 2 }">
									<td class="l-td"><a
										href="${ctx }/web/oa/materia_v2/purDetail?id=${cur.id}&type=3">查看详情</a></td>
								</c:when>
								<c:when test="${type == 1 }">
									<td class="l-td"><a
										href="${ctx }/web/oa/materia_v2/purDetail?id=${cur.id}&type=1">查看详情</a></td>
								</c:when>
							</c:choose>
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
	var type = '${type}';
	$(function() {
		if (type == 2) {
			$('#OA审批').addClass('ui-tabs-current');
			$('#物料采购审批').addClass('cur');
		} else {
			$('#OA申请').addClass('ui-tabs-current');
			$('#物料采购申请').addClass('cur');
		}
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>