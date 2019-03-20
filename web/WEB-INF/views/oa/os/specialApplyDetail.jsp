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
  		<a style="color:#C0392B;" href="${ctx}/web/oa/os/mySpeApply">点击返回办公用品特别申请记录</a>
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
			<td style="width:170px;	">
				${sq.createTime }
			</td>	
			<td class="title" style="width:100px;" name="useTime">领用时间：</td>
			<td>${useTime}</td>
		</tr>
		
	</tbody></table>
	
		<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申请原因</span>
		</div>
	
		<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled="disabled" name="content" id="cnt" style="width:50%; height:80px; padding:5px 10px;">${sq.content}</textarea>
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
		</tr>
		
		<c:forEach items="${list}" var="cur">
		<tr>
			<td style="">
				${cur.name }
			</td>
			<td style="">
				${cur.brand }
			</td>
			<td style="">
				${cur.artSize }
			</td>
			<td style="">
				${cur.num}
			</td>
			<td style="">
				${cur.unit}
			</td>
			<td style="">
				${cur.remark}
			</td>
		</tr>
		</c:forEach>
		
		
	</tbody></table>
	
	
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


</script>