<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;审批详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/jiaban/mysq"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }加班申请单</span>
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
				${jiaban.type == 0 ? '普通加班' : '法定假日' }
			</td>	
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				${jiaban.proposerName }
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td>
				<span id="deptSpan">${loginUserDept.name }</span>
			</td>	
			<td class="title" style="width:90px;">申请时间：</td>
			<td  style="border-right:none; width:200px;">
				${jiaban.createTime }
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		加班时间</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">加班日期：</td>	
			<td style="width:160px;">
				${jiaban.dayte }
			</td>	
			<td class="title" style="width:90px;">加班时长：</td>	
			<td style="width:160px;">
				${jiaban.hours }小时
			</td>	
			<td  style="border-right:none; ">
				
			</td>
				
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		加班事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled="disabled" name="content" style="width:50%; height:120px; padding:5px 10px;">${jiaban.content }</textarea>
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
			</td>
			<td style=" width:16%; color:red;">
			
				<c:choose>
				<c:when test="${ empty jiaban.mgrId }">
					<span style=" color:#666;" >本次审批不需要经过主管</span>
				</c:when>
				<c:when test="${ jiaban.currentId == jiaban.mgrId && jiaban.status == 1}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty jiaban.mgrCmt ? '暂未到达该流程' : jiaban.mgrCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总监意见
			</td>
			<td style="width:16%;color:red;">
			
				<c:choose>
				<c:when test="${ empty jiaban.directId }">
					<span style=" color:#666;" >本次审核不需要经过总监</span>
				</c:when>
				<c:when test="${ jiaban.currentId == jiaban.directId && jiaban.status == 2}">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty jiaban.directCmt ? '暂未到达该流程' : jiaban.directCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总经理意见
			</td>
			<td style="width:16%; color:red; border-right:none; ">
			
				<c:choose>
				<c:when test="${ empty jiaban.bossId }">
					<span style=" color:#666;" >本次审核不需要经过总经理</span>
				</c:when>
				<c:when test="${ jiaban.currentId == jiaban.bossId && jiaban.status == 3}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty jiaban.bossCmt ? '暂未到达该流程' : jiaban.bossCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
	</tbody></table>
	
	
	</div>
	
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#OA申请').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
</script>