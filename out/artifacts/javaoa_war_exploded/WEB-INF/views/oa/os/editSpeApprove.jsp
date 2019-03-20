<%@ page language="java" import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/os/specialApplyApprove"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:120px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">办公用品特别申请</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/os/speApprove" method="post" onsubmit="return beforeSubmit();">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${app.id}" name="id"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }办公用品特别申请申请单</span>
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
				${app.empName }
			</td>	
			<td class="title" style="width:100px;">所属部门：</td>	
			<td style="width:90px;">
				${AllDepts[app.deptId].name }
			</td>
			<td class="title" style="width:100px;">申领时间：</td>
			<td style="width:170px;	">
				${app.createTime }
			</td>	
			<td class="title" style="width:100px;" name="useTime">领用时间：</td>
			<td>${useTime }</td>
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
				<textarea disabled="disabled" name="content" id="cnt" style="width:50%; height:80px; padding:5px 10px;">${content}</textarea>
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
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批记录</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			
			<td style=" width:16%; ">
				部门主管意见
				<c:if test="${ not empty app.deptDirectorId }">
					<span style="color:red;">（${AllUsers[app.deptDirectorId].name}）</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
			
				<c:choose>
			<c:when test="${ empty app.deptDirectorId || app.deptDirectorId == app.adminDirectorId}">
					<span style=" color:#666;" >本次审批不需要经过部门主管</span>
				</c:when>
				<c:when test="${ app.currentId == app.deptDirectorId && app.status == 1}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty app.deptOpinion ? '暂未到达该流程' : app.deptOpinion  }
				</c:otherwise>
				</c:choose>
				
			</td>
			
			<td style=" width:16%; ">
				行政主管意见
				<c:if test="${ not empty app.adminDirectorId }">
					<span style="color:red;">(${AllUsers[app.adminDirectorId].name})</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
			
				<c:choose>
				<c:when test="${ empty app.adminDirectorId }">
					<span style=" color:#666;" >本次审批不需要经过行政主管</span>
				</c:when>
				<c:when test="${ app.currentId == app.adminDirectorId && app.status == 2}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty app.adminOpinion ? '暂未到达该流程' : app.adminOpinion  }
				</c:otherwise>
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
			
			<td style="width:320px;">
				审批意见：
				<select id="tg" name="sp" onchange="yjChanged()" >
					<option value="审批通过" style="color:green;" selected="selected">通过</option>
					<option value="不通过" style="color:red;">不通过</option>
				</select>
			</td>
			
			<td style="border-right:none;">
				补充意见：<input name="opinion" id="opinion" style="width:180px;" value="ok"/> <span style="color:red;">不超过50个字符</span>
			</td>
		</tr>
	</tbody></table>
	
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交审批结果</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script>
var index;

$(function(){
	$('#OA审批').addClass('ui-tabs-current');	
	$('#办公用品特别申请审批').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	});

function beforeSubmit(){
	var tg = $('#tg').val();

	if(tg == '0'){
		alert('请选择审批意见');
		return false;
	}

	var yj = $('#opinion').val();
	yj = $.trim(opinion);
	if(!opinion){
		alert('请输入补充意见');
		return false;
	}
		
	return true;
}

function submitForm(){

	var flag = beforeSubmit();
	if(flag){
		document.form1.submit();
	}
}
</script>