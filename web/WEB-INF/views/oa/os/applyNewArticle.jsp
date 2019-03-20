<%@ page language="java" import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>


<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['4']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:120px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">特别申请</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/os/mySpeApply">点击查看办公用品特别申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/os/applyNewArticle" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }办公用品特别申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:100px;">申领人：</td>	
			<td style="width:90px;">
				${loginUser.name }
			</td>	
			<td class="title" style="width:100px;">所属部门：</td>	
			<td style="width:90px;">
				${loginUserDept.name }
			</td>
			<td class="title" style="width:100px;">申领时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
				String tdy = now.substring(0,11);
			%>
			<td style="border-right:none;	">
				${now }
			</td>	
			<td class="title" style="width:100px;" name="useTime">领用时间：</td>
			<td>
			<input name="beginDate" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			</td>
		</tr>
		
		<%
			List<Role> roless = (List<Role>)session.getAttribute("loginUserRoles");
			if( RoleUtil.hasRole(roless,"考勤管理员") ){
		%>
		<tr>
			<td class="title" style="width:120px;">申领类型：</td>
			<td style="border-right:none;" colspan="5">
			<select name="type" id="sqType">
				<option value="-1">-请选择-</option>
				<option value="0">个人申领</option>
				<option value="1">公司申领</option>
			</select>
			</td>	
		</tr>
		<%
			}
		%>
	</tbody></table>
	
		<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申请原因</span>
		</div>
	
		<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" style="width:50%; height:80px; padding:5px 10px;"></textarea>
				<span style="color:red;">不超过150个字符（可不填）</span>
			</td>	
		</tr>
		</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		物品详细信息</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:120px;">
				名称 
			</td>
			<td class="title" style="text-align:left; width:120px;">
				品牌 
			</td>
			<td class="title" style="text-align:left; width:120px;">
				规格尺寸
			</td>
			<td class="title" style="text-align:left; width:120px; ">
				数量
			</td>
			<td class="title" style="text-align:left;  width:120px; ">
				单位
			</td>		
			<td class="title" style="text-align:left;  width:120px; ">
				备注
			</td>		
			<td class="title" style="text-align:left; border-right:none; ">
				操作
			</td>		
		</tr>
		
		<tr id="addCdTr">
			<td style="">
				<input style="width:100px;" id="nameSpan"/>
			</td>
			<td style="">
				<input style="width:100px;" id="brandSpan"/>
			</td>
			<td style="">
				<input style="width:100px;" id="artSizeSpan"/>
			</td>
			<td style="">
				<input style="width:100px;" id="numSpan"/>
			</td>
			<td style="">
				<input style="width:100px;" id="unitSpan"/>
			</td>
			<td style="">
				<input style="width:100px;" id="remarkSpan"/>
			</td>
			<td style=" width:60px; border-right:none; ">
				<a href="javascript:addTable();">确定</a>
			</td>		
		</tr>
		
		
	</tbody></table>
	
	
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申领</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
var index;

$(function(){
	$('#OA申请').addClass('ui-tabs-current');	
	$('#办公用品特别申请').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	});


var count = 3;

function addTable(){
	var name = $("#nameSpan").val();
	var brand = $("#brandSpan").val();
	var artSize = $("#artSizeSpan").val();
	var num = $("#numSpan").val();
	var unit = $("#unitSpan").val();
	var remark = $("#remarkSpan").val();

	if(name=='' || name==null || name==undefined){
		alert('请填写名称！');
		return;
	}
	if(brand=='' || brand==null || brand==undefined){
		alert('请填写品牌！');
		return;
	}
	if(artSize=='' || artSize==null || artSize==undefined){
		alert('请填写规格尺寸！');
		return;
	}

	if(num=='' || num==null || num==undefined){
		alert('请填写数量！');
		return;
	}
	if(unit=='' || unit==null || unit==undefined){
		alert('请填写单位！');
		return;
	}

	var reg = new RegExp("^[1-9][0-9]*$");
	if(!reg.test(num)){
		alert('请正确填写数量（数量必须是正整数）');
		return;
	}
	
	count++;
	var html = '<tr id="cd'+count+'">'+
		'<td style="">'+name+
		'<input type="hidden" value="'+name+'" name="names"/>'+
		'<input type="hidden" value="'+brand+'" name="brands"/>'+
		'<input type="hidden" value="'+artSize+'" name="artSizes"/>'+
		'<input type="hidden" value="'+num+'" name="nums"/>'+
		'<input type="hidden" value="'+unit+'" name="units"/>'+
		'<input type="hidden" value="'+remark+'" name="remarks"/>'+
		'</td>'+
		'<td style="">'+brand+'</td>'+
		'<td style="">'+artSize+'</td>'+
		'<td style="">'+num+'</td>'+
		'<td style="">'+unit+'</td>'+
		'<td style="">'+remark+'</td>'+
		'<td style=" border-right:none; ">'+
			'<a href="javascript:delcd('+count+');">删除</a>'+
		'</td>'+
		'</tr>';

	$('#addCdTr').after(html);
	clearInput();
	
}

function clearInput(){
	$("#nameSpan").val('');
	$("#brandSpan").val('');
	$("#artSizeSpan").val('');
	$("#numSpan").val('');
	$("#unitSpan").val('');
	$("#remarkSpan").val('');
}
function delcd(tid){
	$('#cd'+tid).remove();
}

function submitForm(){
	if(count<=3){
		alert('请至少添加一种物品');
		return;
	}

	document.form1.submit();
}
</script>