<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;资产详情</div>
	<div class="navTitle2" url="mgr"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>
<form name="sForm" id="sForm" action="${ctx}/web/oa/prop/mgr" method="post" >
	<input type="hidden" value="${empId }" name="empId"/>
	<input type="hidden" value="${uname }" name="uname"/>
	<input type="hidden" value="${uType }" name="uType"/>
	<input type="hidden" value="${showType }" name="showType"/>
	<input type="hidden" value="${placeId }" name="placeId"/>
	<input type="hidden" value="${typeId }" name="typeId"/>
</form>
<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加资产</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/prop/edit" method="post">
	<input type="hidden" name="id" value="${prop.id }"/>
	
	<input type="hidden" value="${empId }" name="empId2"/>
	<input type="hidden" value="${uname }" name="uname" />
	<input type="hidden" value="${uType }" name="uType"/>
	<input type="hidden" value="${showType }" name="showType"/>
	<input type="hidden" value="${placeId }" name="placeId2"/>
	<input type="hidden" value="${typeId }" name="typeId2"/>
	
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
			<td class="title" style="border-left:none; width:90px;">资产名称 ：</td>	
			<td style="width:90px;">
				${prop.name }
			</td>	
			<td class="title" style="width:90px;">资产编号 ：</td>	
			<td style="width:90px;">
				${prop.code }
			</td>	
			<td class="title" style="width:90px;">购买日期：</td>
			<td  style=" width:90px;">
				${prop.buyDate }
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属类别：</td>	
			<td style="width:90px;">
				<select disabled="disabled" name="typeId" id="typeId">
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
				<input name="spec" value="${prop.spec}"/>
			</td>	
			<td class="title" style="width:90px;">单位 ：</td>	
			<td style="width:90px;">
				${prop.unit}
			</td>	
			<td class="title" style="width:90px;">单价（元）：</td>	
			<td style="width:90px;">
				${prop.price}
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
				<textarea name="bz" id="cnt" style="width:50%; height:80px; padding:5px 10px;">${prop.bz}</textarea>
				<span style="color:red;">不超过150个字符（可不填）</span>
			</td>	
		</tr>
	</tbody></table>
	
	</div>
	<span style="color:red;">*</span>号标注的为必填项。
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">保存修改</a>
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
		if(url == 'mgr')
			document.sForm.submit();
	});

	$('#typeId').val(${prop.typeId});
	$('#placeId').val(${prop.placeId});
});

function submitForm(){
	
	var placeId = $('#placeId').val();

	if(!placeId){
		alert('请选择放置地点。');
		return;
	}

	
	document.form1.submit();
}
</script>