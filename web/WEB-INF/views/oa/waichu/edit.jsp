<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/waichu/mysp"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<%-- <form name="form1" action="${ctx }/web/oa/waichu/sp" method="post"> --%>
	<form name="form1" action="${ctx }/web/oa/waichu/chuchaisp" method="post">
	<input type="hidden" value="${leave.id}" name="id"/>
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }出差申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">外出类型：</td>	
			<td style="width:90px;">
				${leave.type }
			</td>	
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				${leave.proposerName }
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td>
				<span id="deptSpan">${AllDepts[leave.deptId].name}</span>
			</td>	
			<td class="title" style="width:90px;">申请时间：</td>
			<td  style="border-right:none; width:200px;">
				${leave.createTime }
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		起始时间</span>
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
			<td class="title" style="width:90px;">出差时长：</td>	
			<td style="width:160px;">
				${leave.days }天${leave.hours }小时${leave.minutes }分钟
			</td>
				
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		出差事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled="disabled" name="content" style="width:50%; height:120px; padding:5px 10px;">${leave.content }</textarea>
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批记录</span>
	</div>
	<table class="table1" >
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
	</table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批意见</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td style="width:400px;">
				<input name="yj" id="yj" style="width:180px;" value="ok"/> <span style="color:red;">不超过50个字符</span>
			</td>
			<td style="width:160px;">
				<input type="radio" value="审批通过" name="sp" id="tg"/> <label for="tg">通过</label>&nbsp;&nbsp;
				<input type="radio" value="不通过" name="sp" id="btg"/> <label for="btg">不通过</label>
			</td>
			<td  style="border-right:none; ">
				
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
function submitForm(){
	var tg = $('#tg').prop('checked');
	var btg = $('#btg').prop('checked');

	var yj = $('#yj').val();
	yj = $.trim(yj);
	if(!yj){
		alert('请输入审批意见。');
		return;
	}

	if(tg || btg){
		document.form1.submit();
	}else{
		alert('请选择是否通过审核。');
	}
}
</script>