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
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">办公用品申领</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }办公用品申领单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:100px;">申领人：</td>	
			<td style="width:120px;">
				${sq.empName }
			</td>	
			<td class="title" style="width:100px;">所属部门：</td>	
			<td style="width:90px;">
				${AllDepts[sq.deptId].name }
			</td>
			<td class="title" style="width:100px;">申领时间：</td>
			<td style="border-right:none;">
				${sq.createTime }
			</td>	
		</tr>
		<tr>
			<td class="title" style="width:120px;">申领类型：</td>	
			<td style="width:90px;border-right:none;" colspan="5">
				${sq.type == 0 ? '个人使用' : '公务/部门/办公地点使用' }
			</td>	
		</tr>
		
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申领物品</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:120px;">
				物品名称
			</td>
			<td class="title" style="text-align:left; width:120px;">
				规格型号
			</td>
			<td class="title" style="text-align:left; width:60px; ">
				数量
			</td>
			<td class="title" style="text-align:left; width:60px; ">
				单位
			</td>
		</tr>
		<c:forEach items="${list}" var="cur">
		<tr>
			<td style="">
				${cur.name }
			</td>
			<td style="">
				${cur.type }
			</td>
			<td style="">
				${cur.num }
			</td>
			<td style="">
				${cur.unit}
			</td>
		</tr>
		</c:forEach>
		
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		备注信息</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" disabled style="width:50%; height:80px; padding:5px 10px;">${sq.bz}</textarea>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
	<input type="hidden" value="${isEnough}" id="isEnough"/>
		<a href="javascript:submitSq();" class="lzui-btn2" 
			style="height:30px; line-height:30px; float:left; width:100px;font-weight:bold; font-size:15px;">
			确认领用
		</a>
		<a href="javascript:CancelSq();" class="lzui-btn" 
			style="height:30px; line-height:30px; margin-left:15px; float:left; width:100px;font-weight:bold; font-size:15px;">
			取消申领
		</a>
	</div>
	
	</div>
	
</div>

</body>
</html>
<script>
$(function(){
	$('#资产管理').addClass('ui-tabs-current');	
	$('#办公用品申领处理').addClass('cur');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

function CancelSq(){
	var flag = confirm("您确定取消当前的办公物品申领？");
	if(flag){
		document.location.href = "${ctx}/web/oa/os/sp?id=${sq.id}&status=-1";
	}
}

function submitSq(){
// 	alert($('#isEnough').val());
	if($('#isEnough').val()=='true'){
		var flag = confirm("请确保用户申领的物品都已经送达。");
		if(flag){
		document.location.href = "${ctx}/web/oa/os/sp?id=${sq.id}&status=1";
				}
		}else{
		alert("该办公用品目前库存不足，请行政人员核对！");	
		}
}
</script>