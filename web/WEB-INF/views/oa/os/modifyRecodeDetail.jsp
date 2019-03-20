<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;办公用品变更申请记录</div>
	<div class="navTitle2" url="${ctx }/web/oa/os/modifyRecode"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:108px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">办公用品</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/os/modifyResult?id=${list.id}" method="post">
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
				${list.name }
			</td>	
				
			<td class="title" style="width:90px;">规格型号 <span style="color:red;">*</span>：</td>
			<td  style=" width:90px;">
				${list.type }
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">单位<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				${list.unit}
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="width:90px;">物品特征 <span style="color:red;">*</span>：</td>
			<td  style=" width:90px;">
			<c:choose>
				<c:when test="${list.companyOnly==0 }">
				普通物品
				</c:when>
				<c:when test="${list.companyOnly==1 }">
				公司专用
				</c:when>
				<c:when test="${list.companyOnly==2 }">
				财务专用
				</c:when>
			</c:choose>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">物品分类 <span style="color:red;">*</span>：</td>	
			<td colspan="3">
				${list.classify }				
			</td>
		</tr>
		
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<c:choose>
			<c:when test="${list.operate==1}">
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		删除原因
			</c:when>
			<c:when test="${list.operate==2}">
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		修改原因
			</c:when>
		</c:choose>
		</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled="disabled" name="content" id="cnt" style="width:50%; height:120px; padding:5px 10px;">${list.remark}</textarea>
				<span style="color:red;">不超过150个字符，ctrl+Enter提交</span>
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批记录</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td style=" width:16%; ">
				行政主管意见
				<c:if test="${ not empty ma.adminDirectorId }">
					<span style="color:red;">(${AllUsers[list.adminDirectorId].name})</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
			
				<c:choose>
				<c:when test="${list.status == 1}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:when test="${list.status == 2}">
					<span style=" color:#green;" >审批通过</span>
				</c:when>
				<c:when test="${list.status == -1}">
					<span style=" color:#green;" >审批未通过</span>
				</c:when>
				</c:choose>
			</td>
			
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批意见</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td style="width:60px;">
				补充意见
			</td>
			<td style="border-right:none;">
			<c:if test="${empty list.approveOpinion}">
			无</c:if>
			<c:if test="${list.approveOpinion} !=  null"></c:if>
				${list.approveOpinion}
			</td>
		</tr>
	</tbody></table>
	
<!-- 	这是表单div -->
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
});
</script>