<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['42']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加资产</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	
	<form name="form1" action="${ctx }/web/oa/prop/addCg" method="post">
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">添加固定资产</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息<span style="color:red;font-weight:normal;"></span></span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">资产名称 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="name" id="nameIpt" disabled="disabled" value="${prop.name }"/>
			</td>	
			<td class="title" style="width:90px;">数量 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="num" id="numIpt" maxlength="6" disabled="disabled" value="${prop.num }"/>
			</td>	
			<td class="title" style="width:90px;">购买日期：</td>
			<td  style=" width:90px;">
				<input name="buyDate" id="buyDate" disabled="disabled"  value="${currentDate }" />
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属类别<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
			<input name="typeId" id="typeId" disabled="disabled" value="${prop.type }" />
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="border-left:none; width:90px;">规格型号：</td>	
			<td style="width:90px;">
				<input name="spec" id="spec" disabled="disabled" value="${prop.brand }"/>
			</td>	
			<td class="title" style="width:90px;">单位 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="unit" id="unitIpt" disabled="disabled" value="${prop.unit }"/>
			</td>	
			<td class="title" style="width:90px;">单价（元）<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="price" id="priceIpt" disabled="disabled" value="${prop.price }"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">放置地点<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="placeId" id="placeId" disabled="disabled" value="${prop.placeName }"/>
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
<script>
$(function(){
	$('#资产管理').addClass('ui-tabs-current');	
	$('#固定资产采购').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function submitForm(){
	document.form1.submit();
}
</script>