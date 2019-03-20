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
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;客户信息新增
		</div>
		<div class="navTitle2"
			url="${ctx }/web/oa/product/receiver/list">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">客户管理</div>
	</div>
	<form id="form1" name="form1" action="${ctx }/web/oa/product/receiver/add" method="post">
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">客户信息新增</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:200px;">客户编号 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="companyCode" id="companyCode"/>
			</td>	
		</tr>
		<tr>
			<td class="title" style="width:200px;">公司名称 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="company" id="company"/>
			</td>	
		</tr>
		<tr>
			<td class="title">收货地址 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="addr" id="addr"/>
			</td>	
		</tr>
		<tr>
			<td class="title">收货人 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="receiver" id="receiver"/>
			</td>	
		</tr>
		<tr>
			<td class="title">联系电话 <span style="color:red;">*</span>：</td>	
			<td>
				<input name="tel" id="tel"/>
			</td>	
		</tr>
		<tr>
			<td class="title">状态 <span style="color:red;">*</span>：</td>	
			<td>
				<select id="state" name="state">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<span style="color:red;">*</span>号标注的为必填项。
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交新增</a>
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
	      companyCode: {
	        required: true,
	      },
	      company: {
	        required: true,
	      },
	      addr: {
	        required: true,
	      },
	      receiver: {
	        required: true,
	      },
	      tel: {
	        required: true,
	      }
	    },
	    messages: {
	    	companyCode: {
		        required: "请输入客户编号"
		      },
	    	company: {
		        required: "请输入公司名称"
		      },
	    	receiver: {
		        required: "请输入收货地址"
		      },
	    	unit: {
		        required: "请输入收货人"
		      },
	    	tel: {
		        required: "请输入联系电话"
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
		$('#收货单位管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>