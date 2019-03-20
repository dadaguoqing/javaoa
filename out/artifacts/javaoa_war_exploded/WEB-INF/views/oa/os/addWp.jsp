<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;新增办公用品</div>
	<div class="navTitle2" url="${ctx }/web/oa/os/stock"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:108px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">新增办公用品</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/os/addWp" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">办公用品信息</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">物品名称 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="name" id="nameIpt"/>
			</td>	
				
			<td class="title" style="width:90px;">规格型号 <span style="color:red;">*</span>：</td>
			<td  style=" width:90px;">
				<input name="type" id="typeIpt" />
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">单位<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="unit" id="unitIpt" />
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="width:90px;">物品特征 <span style="color:red;">*</span>：</td>
			<td  style=" width:90px;">
				<select name="companyOnly" id="companyOnlyIpt">
					<option value="0">普通物品</option>
					<option value="1">公司专用</option>
					<option value="2">财务专用</option>
				</select>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">物品分类 <span style="color:red;">*</span>：</td>	
			<td colspan="3">
				<select name="classify" id="classifyIpt">
					<c:forEach items="${classifys}" var="cur">
					<option value="${cur }">${cur }</option>
					</c:forEach>
					<option value="新增分类">新增分类</option>
				</select>&nbsp;&nbsp;
				<input name="newClassify" id="newClassify" placeHolder="请在填写新分类名称"/>
				
			</td>
		</tr>
		
	</tbody></table>
	
	</div>
	<span style="color:red;">*</span>号标注的为必填项。
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">点击添加</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#资产管理').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#classifyIpt').change(function(){
		var cf = $('#classifyIpt').val();

		if(cf == '新增分类'){
			$('#newClassify').show();
		}else{
			$('#newClassify').hide();
		}
	});
});

function submitForm(){
	
	var name = $('#nameIpt').val();
	var type = $('#typeIpt').val();
	var unit = $('#unitIpt').val();

	var cf = $('#classifyIpt').val();

	name = $.trim(name);
	$('#nameIpt').val(name);
	if(!name){
		alert('请输入物品名称。');
		return;
	}

	if(!type){
		alert('请输入规格型号。');
		return;
	}

	unit = $.trim(unit);
	$('#unitIpt').val(unit);
	if(!unit){
		alert('请填写单位');
		return;
	}

	if(cf == '新增分类'){
		var ncf = $('#newClassify').val();

		ncf = $.trim(ncf);
		$('#newClassify').val(ncf);
		if(!ncf){
			alert('请填新增分类名称。');
			return;
		}
	}
	
	document.form1.submit();
}
</script>