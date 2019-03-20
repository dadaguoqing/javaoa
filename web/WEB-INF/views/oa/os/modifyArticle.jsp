<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:choose>
		<c:when test="${type==1}">
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;删除办公用品</div>
		</c:when>
		<c:when test="${ type==2}">
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;修改办公用品</div>
		</c:when>
	</c:choose>
	<div class="navTitle2" url="${ctx }/web/oa/os/stock"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<c:choose>
		<c:when test="${type==1 }">
		<div style="width:108px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">删除办公用品</div>
		</c:when>
		<c:when test="${type==2 }">
		<div style="width:108px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">修改办公用品</div>
		</c:when>
	</c:choose>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/os/articleApprove" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${app.id}" name="id"/>
	<input type="hidden" value="${type}" id="operateType" name="operateType"/>
	
	<div style="border:1px solid #2191C0; ">
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">物品名称 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
			<c:choose>
				<c:when test="${type==1}">
				${app.name}
				</c:when>
				<c:when test="${type==2}">
				<input name="name" id="nameIpt" value="${app.name} "/>
				</c:when>
			</c:choose>
			</td>	
				
			<td class="title" style="width:90px;">规格型号 <span style="color:red;">*</span>：</td>
			<td  style=" width:90px;">
			<c:choose>
			<c:when test="${type==1}">
				${app.type}
				</c:when>
			<c:when test="${type==2}">
				<input name="type" id="typeIpt" value="${app.type}" />
				</c:when>
				</c:choose>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">单位<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
			<c:choose>
			<c:when test="${type==1}">
				${app.unit}
				</c:when>
			<c:when test="${type==2}">
				<input name="unit" id="unitIpt" value="${app.unit}"/>
				</c:when>
				</c:choose>
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="width:90px;">物品特征 <span style="color:red;">*</span>：</td>
			<td  style=" width:90px;">
			<c:choose>
		<c:when test="${type==1 && app.companyOnly==0 }">
					普通物品
					</c:when>
				<c:when test="${type==1 && app.companyOnly==1} ">
					公司专用
				</c:when>
				<c:when test="${type==1 && app.companyOnly==2} ">
					财务专用
				</c:when>
		<c:when test="${ type==2}">
				<input type="hidden" value="${app.companyOnly }" id="select">
				<select name="companyOnly" id="companyOnlyIpt" >
					<option value="0">普通物品</option>
					<option value="1">公司专用</option>
					<option value="2">财务专用</option>
				</select>
		</c:when>
	</c:choose>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">物品分类 <span style="color:red;">*</span>：</td>	
			<td colspan="3">
			<c:choose>
		<c:when test="${ type==1}">
			${app.classify}
		</c:when>
		<c:when test="${ type==2}">
		<input type="hidden" value="${app.classify }" id="classifyId">
				<select  name="classify" id="classifyIpt"> 
 					<c:forEach items="${classifys}" var="cur"> 
	 				<option value="${cur }">${cur }</option>
 					</c:forEach>
 					<option value="新增分类">新增分类</option>
				</select>&nbsp;&nbsp;
				<input name="newClassify" id="newClassify" placeHolder="请在填写新分类名称"/>
		</c:when>
		</c:choose>
			</td>
		</tr>
		
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<c:choose>
			<c:when test="${type==1}">
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		删除原因
			</c:when>
			<c:when test="${type==2}">
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		修改原因
			</c:when>
		</c:choose>
		</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea  name="content" id="cnt" style="width:50%; height:120px; padding:5px 10px;"></textarea>
				<span style="color:red;">不超过150个字符，ctrl+Enter提交</span>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
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
	
	var classify = $('#classifyId').val();
	$('#classifyIpt').val(classify);

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	var select = $('#select').val();
	$('#companyOnlyIpt').val(select);
	
	
	hideClassify();
	$('#classifyIpt').change(function(){
		hideClassify();
	});
});

function hideClassify(){
		var cf = $('#classifyIpt').val();
		$('#newClassify').hide();
		if(cf == '新增分类'){
			$('#newClassify').show();
		}
}
function submitForm(){
	
	if($('#operateType')==1){
	$('#nameIpt').val('');
	$('#typeIpt').val('');
	$('#unitIpt').val('');
	$('#classifyIpt').val('');
	$('#companyOnlyIpt').val('');
	$('#newClassify').val('');
	}
	var content = $('#cnt').val();
	if(!content){
		$('#cnt').val('');
	}
	document.form1.submit();
}
</script>