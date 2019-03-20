<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;物流公司信息修改
		</div>
		<div class="navTitle2" url="${ctx }/web/oa/product/logistics">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<div
			style="margin-bottom: 10px; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<div
				style="width: 72px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">产品管理</div>
		</div>
		<form id="form1" name="form1" action="${ctx }/web/oa/product/updateLog"
			method="post">
			<div style="border: 1px solid #2191C0;">
				<input type="hidden" value="${submitCode}" name="submitCode" />
				<div class="main-header" id="id1">
					<span style="color: #eee;">物流公司信息修改</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 150px;">物流公司名称<span
								style="color: red;">*</span>：
							</td>
							<td><input type="hidden" name="id" value="${seal.id }">
								<input name="company" id="company" value="${seal.company}"
								style="width: 300px" /></td>
						</tr>
						<tr>
							<td class="title">联系电话<span style="color: red;">*</span>：
							</td>
							<td><input name="telephone" id="telephone"
								value="${seal.telephone }" style="width: 300px" /></td>
						</tr>
						<tr>
							<td class="title">联系人<span style="color: red;">*</span>：
							</td>
							<td><input name="contacts" id="contacts"
								value="${seal.contacts }" style="width: 300px" /></td>
						</tr>
						<tr>
							<td class="title">地址 <span style="color: red;">*</span>：
							</td>
							<td><input name="address" id="address"
								value="${seal.address}" style="width: 300px" /></td>
						</tr>
						<tr>
							<td class="title">备注 <span style="color: red;">*</span>：
							</td>
							<td><textarea id="content" name="content" cols="41" rows="4">${seal.content}</textarea></td>
						</tr>
					</tbody>
				</table>
			</div>
			<span style="color: red;">*</span>号标注的为必填项。
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交</a>
			</div>

		</form>
	</div>
</div>
<script type="text/javascript"
	src="${ctx }/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/js/validate/messages_zh.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script>
	function submitForm() {
		$('#company').val().replace(/\r\n\s/g,"");
		$('#telephone').val().replace(/\r\n\s/g,"");
		$('#contacts').val().replace(/\r\n\s/g,"");
		$('#address').val().replace(/\r\n\s/g,"");
		$('#content').val().replace(/\r\n\s/g,"");
	
		if (!$('#company').val()) {
			layer.msg('请填写物流公司名称');
			return;
		}
		if (!$('#telephone').val()) {
			layer.msg('请填写联系电话');
			return;
		}
		if (!$('#contacts').val()) {
			layer.msg('请填写联系人');
			return;
		}
		if (!$('#address').val()) {
			layer.msg('请填写地址');
			return;
		}
		document.form1.submit();
	}
	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#物流公司管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>