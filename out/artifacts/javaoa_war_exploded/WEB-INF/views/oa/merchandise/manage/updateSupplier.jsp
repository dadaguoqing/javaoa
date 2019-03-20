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
		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;修改供应商
		</div>
		<div class="navTitle2" url="${ctx }/web/oa/merchandise/manage/supplier">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">

		<div
			style="margin-bottom: 10px; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<div
				style="width: 120px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">新增供应商</div>
		</div>
		<form id="form1" name="form1" action="${ctx }/web/oa/merchandise/manage/updateSupplier"
			method="post">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">修改供应商</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 100px;">供应商编码<span
								style="color: red;">*</span>：
							</td>
							<td>
							<input name="id" value="${ms.id}" type="hidden"/>
							<input name="code" id="code" style="width: 300px;" value="${ms.code}" /></td>
						</tr>
						<tr>
							<td class="title">供应商类别<span style="color: red;">*</span>：
							</td>
							<td><input name="genre" id="genre" style="width: 300px;" value="${ms.genre}"/></td>
						</tr>
						<tr>
							<td class="title">供应商名称<span style="color: red;">*</span>：
							</td>
							<td><input name="name" id="name" style="width:300px;" value="${ms.name}"/></td>
						</tr>
						<tr>
							<td class="title">联系人<span style="color: red;">*</span>：
							</td>
							<td><input name="contact" id="contact" style="width: 300px;" value="${ms.contact}"/></td>
						</tr>
						<tr>
							<td class="title">地址<span style="color: red;">*</span>：
							</td>
							<td>
								<textarea rows="4" cols="41" name="address" id="address" >${ms.address}</textarea>
							</td>
						</tr>
						<tr>
							<td class="title">联系电话<span style="color: red;">*</span>：
							</td>
							<td><input name="phone" id="phone" style="width: 300px;" value="${ms.phone}"/></td>
						</tr>
						<tr>
							<td class="title">备注<span style="color: red;">*</span>：
							</td>
							<td>
								<textarea rows="4" cols="41" name="content" id="content" >${ms.content}</textarea></td>
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
		// 去除页面提交信息中的空格和换行
		$('#code').val().replace(/[ \r\n]/g,'');
		$('#genre').val().replace(/[ \r\n]/g,'');
		$('#name').val().replace(/[ \r\n]/g,'');
		$('#address').val().replace(/[ \r\n]/g,'');
		$('#contact').val().replace(/[ \r\n]/g,'');
		$('#phone').val().replace(/[ \r\n]/g,'');
		$('#content').val().replace(/[ \r\n]/g,'');
		if(!$('#code').val()){
			layer.msg('供应商编码不能为空');
			return;
		}
		if(!$('#genre').val()){
			layer.msg('供应商类别不能为空');
			return;
		}
		if(!$('#name').val()){
			layer.msg('供应商名称不能为空');
			return;
		}
		if(!$('#address').val()){
			layer.msg('地址不能为空');
			return;
		}
		if(!$('#contact').val()){
			layer.msg('联系人不能为空');
			return;
		}
		if(!$('#phone').val()){
			layer.msg('联系电话不能为空');
			return;
		}
		
		document.form1.submit();
	}

	$(function() {
		$('#管理').addClass('ui-tabs-current');
		$('#修改供应商').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>