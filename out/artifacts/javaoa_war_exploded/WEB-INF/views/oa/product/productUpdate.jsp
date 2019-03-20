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
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;产品修改
		</div>
		<div class="navTitle2"
			url="${ctx }/web/oa/product/list">
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
	<form id="form1" name="form1" action="${ctx }/web/oa/product/update" method="post">
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">产品修改</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:200px;">产品型号 <span style="color:red;">*</span>：</td>	
			<td>
				<input type="hidden" name="id" value="${product.id }">
				<input name="productModel" id="productModel" value="${product.productModel }"/>
			</td>	
		</tr>
		<tr>
			<td class="title">产品类型 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="productName" id="productName" value="${product.productName }"/>
			</td>	
		</tr>
		<tr>
			<td class="title">单位 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="unit" id="unit" value="${product.unit }"/>
			</td>	
		</tr>
		<tr>
			<td class="title">状态 <span style="color:red;">*</span>：</td>	
			<td>
				<select id="state" name="state">
					<option value="1" <c:if test="${product.state == 1}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${product.state == 0}">selected</c:if>>禁用</option>
				</select>
			</td>	
		</tr>
		<tr>
			<td class="title">包装信息 ：</td>	
			<td>
				<textarea id="remark" name="remark" cols="22" rows="5" placeholder="100个字符以内">${product.remark }</textarea>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<span style="color:red;">*</span>号标注的为必填项。
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交修改</a>
	</div>
	
	</form>
	</div>
</div>
<script type="text/javascript" src="${ctx }/resources/js/validate/jquery.validate.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/validate/messages_zh.js"></script>
<script>
/** 参数校验 jquery validate */
function valContent(formName) {
	return $('#' + formName).validate({
	    rules: {
	      productName: {
	        required: true,
	      },
	      productModel: {
	        required: true,
	      },
	      unit: {
	        required: true,
	      }
	    },
	    messages: {
	    	productName: {
		        required: "请输入产品名"
		      },
	    	productModel: {
		        required: "请输入产品型号"
		      },
	    	unit: {
		        required: "请输入单位"
		      }
	    }
	  }).form();
}

	function submitForm() {
		// TODO 参数校验
		if(!valContent('form1')) {
			return;
		}
		document.form1.submit();
	}

	$(function() {
		$('#产品管理').addClass('ui-tabs-current');
		$('#产品信息管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>