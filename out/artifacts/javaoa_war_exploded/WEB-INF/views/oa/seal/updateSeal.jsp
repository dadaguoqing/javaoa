<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
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
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;印章信息修改
		</div>
		<div class="navTitle2"
			url="${ctx }/web/oa/seal/manage">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">产品管理</div>
	</div>
	<form id="form1" name="form1" action="${ctx }/web/oa/seal/update" method="post">
	<div style="border:1px solid #2191C0; ">
	<input type="hidden" value="${submitCode}" name="submitCode" />
	<div class="main-header" id="id1">
		<span style="color:#eee;">印章信息修改</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:200px;">公司名称 <span style="color:red;">*</span>：</td>	
			<td>
				<input type="hidden" name="id" value="${seal.id }">
				<input name="sealCompany" id="sealCompany" value="${seal.sealCompany}" style="width:300px"/>
			</td>	
		</tr>
		<tr>
			<td class="title">印章名称 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="sealName" id="sealName" value="${seal.sealName }" style="width:300px"/>
			</td>	
		</tr>
		<tr>
			<td class="title">单位 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="sealUnit" id="sealUnit" value="${seal.sealUnit }"/>
			</td>	
		</tr>
		<tr>
			<td class="title">数量 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="sealNum" id="sealNum" value="${seal.sealNum}"/>
			</td>	
		</tr>
		<tr>
			<td class="title">状态 <span style="color:red;">*</span>：</td>	
			<td>
				<select id="status" name="status">
					<option value="1" <c:if test="${seal.status == 1}">selected</c:if>>启用</option>
					<option value="-1" <c:if test="${seal.status == -1}">selected</c:if>>禁用</option>
				</select>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<span style="color:red;">*</span>号标注的为必填项。
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交</a>
	</div>
	
	</form>
	</div>
</div>
<script type="text/javascript" src="${ctx }/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/validate/messages_zh.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script>
	function submitForm() {
		$('#sealCompany').val($('#sealCompany').val().replace(/[ \r\n]/g,''));
		$('#sealName').val($('#sealName').val().replace(/[ \r\n]/g,''));
		$('#sealUnit').val($('#sealUnit').val().replace(/[ \r\n]/g,''));
		$('#sealNum').val($('#sealNum').val().replace(/[ \r\n]/g,''));
		if(!$('#sealCompany').val()){
			layer.msg('请填写公司名称');
			return;
		}
		if(!$('#sealName').val()){
			layer.msg('请填写印章名称');
			return;
		}
		if(!$('#sealUnit').val()){
			layer.msg('请填写单位');
			return;
		}
		if(!$('#sealNum').val()){
			layer.msg('请填写数量');
			return;
		}
		var reg = /^[1-9]\d*$/;
		if(!reg.test($('#sealNum').val())){
			layer.msg('数量格式错误');
			return;
		}
		document.form1.submit();
	}
	$(function() {
		$('#管理').addClass('ui-tabs-current');
		$('#印章信息管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>