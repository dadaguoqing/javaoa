<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:15px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">${requestScope.type }记录</div>
		
		<div class="gridContainer" style="width:95%; margin-left:0px;margin-top:15px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="8">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					历史明细
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header l-td-left" style="width:10px;">序</td>
				<td class="l-td-header l-td-left" style="width:79px;">员工</td>
				<td class="l-td-header " style="width:179px;">日期</td>
				<td class="l-td-header " style="width:80px;">类型</td>
				<td class="l-td-header " style="width:80px;">增减</td>
				<td class="l-td-header l-td-left" style="width:120px;">时长</td>
				<td class="l-td-header l-td-left" style="width:120px;">剩余时长</td>
				<td class="l-td-header l-td-left l-td-last-right" style="">备注</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur" varStatus="st">
	    		<c:if test="${cur.timeLen < 0}">
	    		<tr style="color:green;">
	    		</c:if>
	    		<c:if test="${cur.timeLen >= 0}">
	    		<tr style="color:red;">
	    		</c:if>
	    			<td class="l-td">${st.count}</td>
	    			<td class="l-td">${cur.empName }</td>
	    			<td class="l-td">${cur.dayStr }</td>
	    			<td class="l-td">${cur.type }</td>
	    			<td class="l-td">${cur.timeLen < 0 ? '增加' : '扣除' }</td>
	    			<td class="l-td l-td-left">${cur.timeLenStr }</td>
	    			<td class="l-td l-td-left">${cur.timeLeftStr }</td>
	    			<td class="l-td l-td-left">
					<c:if test="${cur.bz == '请假扣除' }">
						<c:if test="${cur.leaveId !=0 }">
						<a href="${ctx}/web/oa/leave/showForNianjia/${cur.leaveId}">${cur.bz }</a>
						</c:if>
						<c:if test="${cur.leaveId ==0 }">
						<a href="javascript:submitForm('${cur.dayStr}',${cur.empId })">${cur.bz }</a>
						</c:if>
					</c:if>
					<c:if test="${cur.bz != '请假扣除'}">
						${cur.bz }
					</c:if>
					</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="6" style="color:red;">
					对不起，目前还没有相关数据。
				</td>
			</tr>
	    	</c:if>
		</tbody></table>
		</div>
	
	</div>
</div>
</div>
<form name="form1" action="${ctx }/web/oa/leave/showForNjRecordByDate" method="post" target="_blank" >
	<input type="hidden" id="dayStr" name="dayStr"/>
	<input type="hidden" id="empId" name="empId"/>
</form>
<script>
$(function(){
	$('#个人中心').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function submitForm(dayStr,empId){
	$('#dayStr').val(dayStr);
	$('#empId').val(empId);
	
	document.form1.submit();
}
</script>
</body>
</html>