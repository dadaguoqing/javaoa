<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
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
				<c:forEach items="${loginUserMenuMap['14']}" var="cur">
					<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
						<img src="${ctx }/resources/images/item.png"
							style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
					</div>
				</c:forEach>
			</c:when>
		</c:choose>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<c:choose>
				<c:when test="${type == 1}">
					<a style="color: #C0392B;"
						href="${ctx}/web/oa/materia_v2/reventMateria">返回申请</a>
				</c:when>
				<c:when test="${type == 2}">
					<a style="color: #C0392B;"
						href="${ctx}/web/oa/materia_v2/requisitionList">返回审批</a>
				</c:when>
				<c:when test="${type == 3}">
					<a style="color: #C0392B;"
						href="${ctx}/web/oa/materia_v2/reventApproveRecord">查看记录</a>
				</c:when>
			</c:choose>
		</div>
		<c:if test="${not empty ljchen }">
			<div>
				<span><b>审批状态：</b> <select name="status"
					onchange="javascript:requisitionAjax();" id="selectStatus">
						<option value="0">全部</option>
						<option value="1">仓库管理员处理中</option>
						<option value="2">审批成功</option>
						<option value="-1">审批取消</option>
				</select> </span>
			</div>
		</c:if>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="7">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								待处理列表</div>
						</td>
					</tr>
					<tr>
						<td class="l-td-header" style="width: 20%;">申请人</td>
						<td class="l-td-header" style="width: 20%;">申请时间</td>
						<td class="l-td-header" style="width: 20%;">返料入库单号</td>
						<td class="l-td-header" style="width: 20%;">状态</td>
						<td class="l-td-header" style="">查看详情</td>
					</tr>
						<c:forEach items="${list}" var="cur">
							<tr>
								<td class="l-td">${AllUsers[cur.empId].name}</td>
								<td class="l-td">${cur.daystr}</td>
								<td class="l-td">${cur.requisitionCode}</td>
								<td class="l-td"><c:if test="${cur.status == 1}">
									仓库管理员处理中
								</c:if>
								<c:if test="${cur.status == 2}">
									审批通过
								</c:if> <c:if test="${cur.status == -1}">
									审批取消
								</c:if></td>
								<td class="l-td"><a
									href="${ctx }/web/oa/materia_v2/reventDetail?id=${cur.id}&type=${type}">查看详情</a></td>
							</tr>
						</c:forEach>

				</tbody>
				<tbody id="data">
				</tbody>
				<c:if test="${empty list}">
					<tr>
						<td class="l-td-last-right" colspan="7" style="color: red;">
							对不起，目前还没有相关数据。</td>
					</tr>
				</c:if>
			</table>

		</div>
	</div>
</div>
<script>
	$(function() {
		var type = '${type}';
		var ljcgen = '${ljchen}';
		if (type == 1) {
			$('#OA申请').addClass('ui-tabs-current');
			$('#返料入库申请').addClass('cur');
		} else {
			$('#OA处理').addClass('ui-tabs-current');
			$('#返料入库处理').addClass('cur');
		}

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function requisitionAjax() {
		$('#data').empty();
		var type = '${type}';
		var status = $('#selectStatus').val();
		$
				.post(
						"requisitionAjax",
						{
							status : status
						},
						function(result) {
							var objs = JSON.parse(result);
							$
									.each(
											objs,
											function(i, obj) {
												var tr = '<tr>';
												tr += '<td class="l-td">'
														+ obj.empName + '</td>';
												tr += '<td class="l-td">'
														+ obj.daystr + '</td>';
												tr += '<td class="l-td">'
														+ obj.requisitionCode
														+ '</td>';
												switch (obj.status) {
												case 1:
													tr += '<td class="l-td">一级主管审批中</td>';
													break;
												case 2:
													tr += '<td class="l-td">二级主管审批中</td>';
													break;
												case 3:
													tr += '<td class="l-td">总经理审批中</td>';
													break;
												case 4:
													tr += '<td class="l-td">审批成功</td>';
													break;
												case -1:
													tr += '<td class="l-td">审批取消</td>';
													break;
												default:
													tr += '<td class="l-td">状态异常</td>';
												}
												tr += '<td class="l-td"><a href="${ctx}/web/oa/materia_v2/purDetail2?id='
														+ obj.id
														+ '&type='
														+ type
														+ '" target="_blank">查看详情</a></td>';
												tr += '</tr>';
												$('#data').append(tr);
											});
						});
	}
</script>
</body>
</html>