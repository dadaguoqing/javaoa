<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['2']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>
<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加资产</div>
	</div>
	
	<div class="" style="margin:10px 0 10px 0;">
		<a href="${ctx }/web/oa/prop/addPlace" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">放置地点</a>
		<a href="${ctx }/web/oa/prop/updatePlace" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">修改放置地点</a>
		<a href="${ctx }/web/oa/prop/addType" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">添加资产类别</a>
	</div>
	
	<form action="${ctx }/web/oa/prop/exportAddRecord" method="post" id="form2" name="form2">
		<div class="" style="margin:10px 0 10px 0;">
		开始时间：<input name="begin" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
		结束时间：<input name="end" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
		<a  id="smbtn" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">导出添加记录</a>
	</div>
	</form>
	
	
	
	<form name="form1" action="${ctx }/web/oa/prop/add" method="post">
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">固定资产信息</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">资产名称 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="name" id="nameIpt"/>
			</td>	
			<td class="title" style="width:90px;">数量 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="num" id="numIpt" maxlength="6"/>
			</td>	
			<td class="title" style="width:90px;">购买日期：</td>
			<td  style=" width:90px;">
				<input name="buyDate" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" />
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属类别<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<select name="typeId" id="typeId">
					<option value="">-请选择-</option>
					<c:forEach items="${types}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
				</select>
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="border-left:none; width:90px;">规格型号：</td>	
			<td style="width:90px;">
				<input name="spec" />
			</td>	
			<td class="title" style="width:90px;">单位 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="unit" id="unitIpt"/>
			</td>	
			<td class="title" style="width:90px;">单价（元）<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="price" id="priceIpt"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">放置地点<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<select name="placeId" id="placeId">
					<option value="">-请选择-</option>
					<c:forEach items="${places}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
				</select>
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		资产备注信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="bz" id="cnt" style="width:50%; height:80px; padding:5px 10px;"></textarea>
				<span style="color:red;">不超过150个字符（可不填）</span>
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
	$('#OA管理').addClass('ui-tabs-current');	
	$('#添加固定资产').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	
	$('#smbtn').click(function(){
		document.form2.submit();
	});
});

function submitForm(){
	
	var name = $('#nameIpt').val();
	var num = $('#numIpt').val();
	var typeId = $('#typeId').val();
	
	var unit = $('#unitIpt').val();
	var price = $('#priceIpt').val();

	var placeId = $('#placeId').val();

	name = $.trim(name);
	$('#nameIpt').val(name);
	if(!name){
		alert('请输入资产名称。');
		return;
	}

	if(!num){
		alert('请输入数量。');
		return;
	}

	if(!typeId){
		alert('请选择所属类别。');
		return;
	}

	unit = $.trim(unit);
	$('#unitIpt').val(unit);
	if(!unit){
		alert('请填写单位');
		return;
	}

	price = $.trim(price);
	$('#priceIpt').val(price);
	if(!price){
		alert('请填写价格');
		return;
	}

	if(!placeId){
		alert('请选择放置地点。');
		return;
	}

	
	document.form1.submit();
}
</script>