<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
		<c:forEach items="${loginUserMenuMap['14']}" var="cur">
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

		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>

		<form name="form1" action="${ctx }/web/oa/seal/dealSealApply"
			method="post">
			<div class="gridContainer"
				style="width: 99%; margin-left: 0px; margin-top: 15px;">
				<table class="lzzUIGrid">
					<tr>
						<td class="l-td-toolbar" colspan="20">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								待处理列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header " style="width: 50px;">序号</td>
						<td class="l-td-header" style="width: 100px;">申请人</td>
						<td class="l-td-header" style="width: 150px;">编号</td>
						<td class="l-td-header" style="width: 250px;">请购事由</td>
						<td class="l-td-header" style="width: 150px;">申请时间</td>
						<td class="l-td-header " style="width: 80px;">查看详情</td>
					</tr>
					<c:forEach items="${list}" var="mp" varStatus="i">
						<tr>
							<td class="l-td">${i.count}</td>
							<td class="l-td">${AllUsers[mp.empId].name }</td>
							<td class="l-td">${mp.requisitionCode}</td>
							<td class="l-td limitText" title="${mp.reason}">${mp.reason}</td>
							<td class="l-td">${mp.daystr}</td>
							<td class="l-td"><a href="${ctx}/web/oa/materia_v2/dealPurchaseDetail?id=${mp.id}">查看详情</a></td>
						</tr>
					</c:forEach>

					<tr id="emptyMsg" style="display: none">
						<td class="l-td-last-right" colspan="20" style="color: red;">
							对不起，目前还没有相关数据。</td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</div>

<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	var data = '${list}';
	$(function() {
		$('#OA处理').addClass('ui-tabs-current');
		$('#物料采购处理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

</script>
</body>
</html>