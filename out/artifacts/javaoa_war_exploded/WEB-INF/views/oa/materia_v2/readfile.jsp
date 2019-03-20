<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">

	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['2']}" var="cur">
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
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>
		<%@ include file="../../public/hello.jsp"%>
		<div
			style="margin-bottom: 10px; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<div
				style="width: 72px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">物料入库</div>
		</div>


		<form name="form1" action="${ctx }/web/oa/materia_v2/testfile"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }物料入库单</span>
				</div>
				<input type="file" name="file" >
				<a href="${ctx}/upload/${url}" target="_blank">点击预览</a>
				<button type="submit" >提交</button>
		</form>
	</div>

</div>

</body>
</html>
<script>
	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#物料入库').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function submitForm() {
		var file = $('#file').val();
		if (!file) {
			alert('请上传文件');
		}
		document.form1.submit();
	}
</script>