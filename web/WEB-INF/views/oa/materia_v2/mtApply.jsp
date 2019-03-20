<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../oa/materia_v2/header.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
.shortInput {
	width: 40px;
}
</style>
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">

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
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/materia_v2/applyRecord">查看申领记录</a>
		</div>
		<div style="border: 1px solid #2191C0;">
			<form name="form1" action="${ctx }/web/oa/materia_v2/applyMateria"
				method="post" enctype="multipart/form-data">
				<input type="hidden" value="${submitCode}" name="submitCode" />
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }物料申领单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 10%;">申领人：</td>
							<td style="width: 90px;">${loginUser.name }</td>
							<td class="title" style="width: 120px;">所属部门：</td>
							<td style="width: 90px;">${loginUserDept.name }</td>
							<!-- 							<td class="title" style="width: 120px;">申领时间：</td> -->
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
								Date n = new Date();
								String now = sdf.format(n);
								pageContext.setAttribute("now", now);
								String tdy = now.substring(0, 11);
							%>
							<%-- 							<td style="border-right: none;">${now }</td> --%>
							<td class="title" style="width: 120px;">编号：</td>
							<%
								String timeStamp = System.currentTimeMillis() + "";
								int len = timeStamp.length();
								String ms = timeStamp.substring(len - 3, len);
								SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
								String str = sdf2.format(n);
								pageContext.setAttribute("str", "SL" + str + ms);
							%>
							<td><input type="hidden" name="code" id="code"
								value="${str }" />${str }</td>
						</tr>
						<tr>
							<td class="title" style="width: 120px;">申领用途：</td>
							<td><input name="use1" id="use1" /></td>
							<td class="title" style="width: 120px;">申领仓库：</td>
							<td style="width: 120px;"><select name="warehouseId"
								id="warehouse">
									<c:forEach items="${warehouses}" var="wh">
										<option value="${wh.id}">${wh.warehouse}</option>
									</c:forEach>
							</select></td>
							<td class="title" style="width: 120px;">物料份数：</td>
							<td><input id="times" name="times" value="1" width="30px" /></td>
						</tr>
						<tr>
							<td class="title" style="width: 120px;">申领类型：</td>
							<td><select id="type" name="type">
									<option value="1">BOM申领</option>
									<option value="2">零散申领</option>
							</select></td>
							<td class="title" style="width: 120px;">上传文件：</td>
							<td><input type="file" name="file" id="file"
								onchange="xlsxUpload(this,4)"></td>
							<td class="title" style="width: 120px;">申领测试：</td>
							<td><a href="javascript:submitForm2();"
								class="lzui-btn lzui-corner-all">测试申领</a></td>
						</tr>
						<tr>
							<td class="title" style="width: 120px;">附件：</td>
							<td colspan="3"><input type="file" name="file1" id="file1"><a
								style="color: red">请上传小于10M的压缩文件</a></td>
							<td class="title" style="width: 120px;">模板下载：</td>
							<td align="center"><a href="${ctx }/excel/${url}">点击下载BOM模板</a>
								<a href="${ctx }/excel/${url2}">点击下载零散模板</a></td>
						</tr>
				</table>


				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						备注信息<a style="color: red">（必填）</a>
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td style="border-right: none;"><textarea name="bz" id="bz"
									style="width: 50%; height: 80px; padding: 5px 10px;"></textarea>
								<span style="color: red;">不超过150个字符</span></td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		<span style="color: red;">*</span>号标注的为必填项。
		<div class="" style="margin-top: 10px; margin-bottom: 20px;">
			<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申领</a>
		</div>
	</div>
</div>


</body>
</html>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/submitForm.js"></script>
<script>
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#物料申领').addClass('cur');
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
			return;
		}
		var times = $('#times').val();
		if (!times) {
			alert('物料份数不能为空');
			return;
		}
		if (!/^\+?[1-9][0-9]*$/.test(times)) {
			alert('物料份数必须为正整数');
			return;
		}
		var bz = $('#bz').val();
		if (!bz) {
			alert('请填写备注');
			return;
		}
		document.form1.submit();
	}

	function submitForm2() {
		var file = $('#file').val();
		if (!file) {
			alert('请上传文件');
			return;
		}
		var times = $('#times').val();
		if (!times) {
			alert('物料份数不能为空');
			return;
		}
		if (!/^\+?[1-9][0-9]*$/.test(times)) {
			alert('物料份数必须为正整数');
			return;
		}
		document.form1.action = "${ctx}/web/oa/materia_v2/applyMateria2";
		form1.submit();
	}
</script>