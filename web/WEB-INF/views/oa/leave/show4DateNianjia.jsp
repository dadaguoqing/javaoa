<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">

	<c:if test="${empty list}">
		<div class="lzui-error">当日没有相关请假</div>
	</c:if>
	
	<c:if test="${size>0}">
	
	<c:if test="${size>1}">
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px; margin-bottom:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="6">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				当日相关请假列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:175px;">申请时间</td>
			<td class="l-td-header" style="width:175px;">开始时间</td>
			<td class="l-td-header" style="width:175px;">结束时间</td>
			<td class="l-td-header" style="width:40px;">类型</td>
			<td class="l-td-header l-td-left" style="width:170px;">审批状态</td>
			<td class="l-td-header l-td-last-right" style="">操作</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td">${cur.createTime }</td>
    			<td class="l-td">${cur.beginTime }</td>
    			<td class="l-td l-td-left">${cur.endTime }</td>
    			<td class="l-td l-td-left">${cur.type }</td>
    			<td class="l-td l-td-left">
    				<c:choose>
    					<c:when test="${cur.status == -1}">
    					审批不通过
    					</c:when>
    					<c:when test="${cur.status == 1}">
    					主管审批中
    					</c:when>
    					<c:when test="${cur.status == 2}">
    					总监审批中
    					</c:when>
    					<c:when test="${cur.status == 3}">
    					总经理审批中
    					</c:when>
    					<c:when test="${cur.status == 4}">
    					审批通过
    					</c:when>
    				</c:choose>
				</td>
				<td class="l-td l-td-last-right" style="">
					<a href="javascript:submitForm(${cur.id},${cur.proposer })">查看详情</a>
				</td>
    		</tr>
    	</c:forEach>
    	
	</tbody></table>
	</div>
	</c:if>

	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }请假申请单</span>
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
				<span id="deptSpan">${loginUserDept.name }</span>
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
			<td class="title" style="width:90px;">请假时长：</td>	
			<td style="width:160px;">
				${leave.days }天${leave.hours }小时${leave.minutes }分钟
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
				<textarea disabled="disabled" name="content" style="width:50%; height:120px; padding:5px 10px;">${leave.content }</textarea>
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
			</td>
			<td style="width:16%;color:red;">
			
				<c:choose>
				<c:when test="${ empty leave.directId }">
					<span style=" color:#666;" >本次审核不需要经过总监</span>
				</c:when>
				<c:when test="${ leave.currentId == leave.directId && leave.status == 2 }">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty leave.directCmt ? '暂未到达该流程' : leave.directCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总经理意见
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
	</div>
	
	</c:if>
	</div>
	
</div>
<form name="form1" action="${ctx }/web/oa/leave/showForNjRecordByDate2" method="post">
	<input type="hidden" id="dayStr" name="dayStr" value="${dayStr }"/>
	<input type="hidden" id="leaveId" name="id" />
	<input type="hidden" id="empId" name="empId"/>
</form>

<form name="detailForm" action="${ctx}/web/oa/nianjia/empRecord" method="post">
	<input type="hidden" id="empId" name="empId" value="${leave.proposer }"/>
	<c:if test="${leave.type != '病假' }">
		<input type="hidden" value="0" name="jtype"/>
	</c:if>
	<c:if test="${leave.type == '病假' }">
		<input type="hidden" value="1" name="jtype"/>
	</c:if>
</form>
</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#考勤管理').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		document.detailForm.submit();
	});
});

function submitForm(leaveId,empId){
	$('#leaveId').val(leaveId);
	$('#empId').val(empId);
	document.form1.submit();
}
</script>