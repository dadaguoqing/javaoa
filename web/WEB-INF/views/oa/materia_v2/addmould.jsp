<%@ page language="java" import="java.util.*,java.text.*"
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
		<c:forEach items="${loginUserMenuMap['6']}" var="cur">
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
				style="width: 72px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">模板上传</div>
		</div>


		<form name="form1" action="${ctx }/web/oa/materia_v2/uploadMould"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">模板上传</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						上传文件
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="border-left: none; width: 8%;">入库类型
								<span style="color: red;">*</span>：
							</td>
							<td><select id="type" name="type">
									<c:forEach items="${list}" var="var">
										<option value="${var.type}">${var.name}</option>
									</c:forEach>
							</select></td>
						</tr>
						<tr>
							<td class="title" style="border-left: none; width: 8%;">上传文件
								<span style="color: red;">*</span>：
							</td>
							<td style="width: 60%;" colspan="3"><input name="file"
								id="file" type="file" /><a style="color: red">（请上传excel文件）</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">点击添加</a>
			</div>

		</form>
	</div>

</div>

</body>
</html>
<script>
	$(function() {
		$('#物料管理').addClass('ui-tabs-current');
		$('#上传模板2').addClass('cur');

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