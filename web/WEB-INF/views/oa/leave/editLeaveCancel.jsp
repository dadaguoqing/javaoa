<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/leave/myLeaveCancelSp"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="javascript:showLeave();">点击展示（隐藏）原始请假详情</a>
  	</div>
  	
  	<div style="border:1px solid #E67E22; display:none; margin-bottom:10px;" id="leaveDiv">
  	<div class="main-header" id="id11">
		<span style="color: #E67E22">原始请假申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">请假类型：</td>	
			<td style="width:90px;">
				${l.type }
			</td>	
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				${l.proposerName }
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td>
				<span id="deptSpan">${AllDepts[l.deptId].name  }</span>
			</td>	
			<td class="title" style="width:90px;">申请时间：</td>
			<td  style="border-right:none; width:200px;">
				${l.createTime }
			</td>	
		</tr>
	</tbody>
	</table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		起始时间</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">开始时间：</td>	
			<td style="width:160px;">
				${l.beginTime }
			</td>	
			<td class="title" style="width:90px;">结束时间：</td>	
			<td style="width:160px;">
				${l.endTime }
			</td>	
			<td class="title" style="width:90px;">请假时长：</td>	
			<td style="width:160px;">
				${l.days }天${l.hours }小时${l.minutes }分钟
			</td>
				
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		请假事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled="disabled" name="content" style="width:50%; height:120px; padding:5px 10px;">${l.content }</textarea>
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
			<td style=" width:16%; ">
				主管意见
				<c:if test="${ not empty l.mgrId }">
					<span style="color:red;">（${AllUsers[l.mgrId].name}）</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
			
				<c:choose>
				<c:when test="${ empty l.mgrId }">
					<span style=" color:#666;" >本次审批不需要经过主管</span>
				</c:when>
				
				<c:when test="${ l.currentId == l.mgrId && l.status == 1}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty l.mgrCmt ? '暂未到达该流程' : l.mgrCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总监意见
				<c:if test="${ not empty l.directId }">
					<span style="color:red;">（${AllUsers[l.directId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%;color:red;">
			
				<c:choose>
				<c:when test="${ empty l.directId }">
					<span style=" color:#666;" >本次审核不需要经过总监</span>
				</c:when>
				<c:when test="${ l.currentId == l.directId && l.status == 2 }">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty l.directCmt ? '暂未到达该流程' : l.directCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总经理意见
				<c:if test="${ not empty l.bossId }">
					<span style="color:red;">（${AllUsers[l.bossId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%; color:red; border-right:none; ">
			
				<c:choose>
				<c:when test="${ empty l.bossId }">
					<span style=" color:#666;" >本次审核不需要经过总经理</span>
				</c:when>
				<c:when test="${ l.currentId == l.bossId && l.status == 3}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty l.bossCmt ? '暂未到达该流程' : l.bossCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
	</tbody></table>
	</div>
	
	<form name="form1" action="${ctx }/web/oa/leave/spLeaveCancel" method="post" onsubmit="return beforeSubmit();">
	
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${leave.id}" name="id"/>
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }<span style=" ">销假申请单</span></span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">请假类型：</td>	
			<td style="width:90px;">
				${leave.type }
			</td>	
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				${leave.proposerName }
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td>
				<span id="deptSpan">${AllDepts[leave.deptId].name  }</span>
			</td>	
			<td class="title" style="width:90px;">申请时间：</td>
			<td  style="border-right:none; width:200px;">
				${leave.createTime }
			</td>
			<td class="title" style="border-left:none; width:80px;">销假操作人：</td>	
			<td style="width:180px;">
				${leave.updateName }
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		实际请假时间</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">开始时间：</td>	
			<td style="width:160px;">
				${leave.beginTime }
			</td>	
			<td class="title" style="width:90px;">结束时间：</td>
			<td style="width:160px;">
				${leave.endTime }
			</td>	
			<td class="title" style="width:90px;">请假时长：</td>
			<td style="width:160px;">
				${leave.days }天${leave.hours }小时${leave.minutes }分钟
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
				主管意见
				<c:if test="${ not empty leave.mgrId }">
					<span style="color:red;">（${AllUsers[leave.mgrId].name}）</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
			
				<c:choose>
				<c:when test="${ empty leave.mgrId }">
					<span style=" color:#666;" >本次审批不需要经过主管</span>
				</c:when>
				<c:when test="${ leave.currentId == leave.mgrId && leave.status == 1}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty leave.mgrCmt ? '暂未到达该流程' : leave.mgrCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总监意见
				<c:if test="${ not empty leave.directId }">
					<span style="color:red;">（${AllUsers[leave.directId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%;color:red;">
			
				<c:choose>
				<c:when test="${ empty leave.directId }">
					<span style=" color:#666;" >本次审核不需要经过总监</span>
				</c:when>
				<c:when test="${ leave.currentId == leave.directId && leave.status == 2}">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty leave.directCmt ? '暂未到达该流程' : leave.directCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总经理意见
				<c:if test="${ not empty leave.bossId }">
					<span style="color:red;">（${AllUsers[leave.bossId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%; color:red; border-right:none; ">
			
				<c:choose>
				<c:when test="${ empty leave.bossId }">
					<span style=" color:#666;" >本次审核不需要经过总经理</span>
				</c:when>
				<c:when test="${ leave.currentId == leave.bossId && leave.status == 3}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty leave.bossCmt ? '暂未到达该流程' : leave.bossCmt  }
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
				补充意见：<input name="yj" id="yj" style="width:180px;" value="ok"/> <span style="color:red;">不超过50个字符</span>
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
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#OA审批').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	
});

function showLeave(){
	var lDiv = $('#leaveDiv');
	var disp = lDiv.css('display');

	if('none' == disp){
		lDiv.show();
	}else{
		lDiv.hide();
	}
}

function yjChanged(){
	$('#yj').val("");
}

function beforeSubmit(){
	var tg = $('#tg').val();

	if(tg == '0'){
		alert('请选择审批意见');
		return false;
	}

	var yj = $('#yj').val();
	yj = $.trim(yj);
	if(!yj){
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