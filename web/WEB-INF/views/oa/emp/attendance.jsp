<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<style>
td.l-td{padding-top:5px; width:200px; padding-bottom:5px;}
</style>
<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<%@ include file="../../public/indexMenu.jsp" %>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:8px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div style="border:1px solid #2191C0; width:1003px; margin:10px 0 0px 0;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">员工出勤信息</span>
	</div>
	<c:forEach items="${depts}" var="cur">
	<div class="tableTitle" style="padding:2px 0px; font-size:15px;">
		<span >
		&nbsp;&nbsp;&nbsp;&nbsp;
		${cur.name }</span>
	</div>
	<table class="table1" ><tbody>
		<c:if test="${empty duMap[cur.id]}">
		<tr>
			<td class="l-td" style="">&nbsp;</td>
    		<td class="l-td" style="">&nbsp;</td>
    		<td class="l-td" style="">&nbsp;</td>
    		<td class="l-td" style="">&nbsp;</td>
    		<td class="l-td" style="">&nbsp;</td>
    		<td class="l-td" style="">&nbsp;</td>
		</tr>
		</c:if>
		<c:forEach items="${duMap[cur.id]}" var="c" varStatus="st">
			<c:if test="${st.count==0}"><tr></c:if>
    		
    		<td class="l-td" style="">
    			${c.name }
    			<c:if test="${not empty c.edu}">
    			<span class="infospan" uid="${c.id}" id="info${c.id}" style="color:red; cursor:pointer;" title="从${c.major }到${c.phone1}">
    				${c.edu == '0' ? '（请假）' : '（出差）'}
    			</span>
    			</c:if>
    		</td>
    		<c:if test="${st.count%6==0 && st.count!=0 && !st.last}"></tr><tr></c:if>
    		
    		<c:if test="${st.last}">
    			<c:if test="${st.count%6==0}">
    				</tr>
    			</c:if>
    			<c:if test="${st.count%6==5}">
    				<td class="l-td" style="">&nbsp;</td></tr>
    			</c:if>
    			<c:if test="${st.count%6==4}">
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td>
    				</tr>
    			</c:if>
    			<c:if test="${st.count%6==3}">
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td></tr>
    			</c:if>
    			<c:if test="${st.count%6==2}">
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td></tr>
    			</c:if>
    			<c:if test="${st.count%6==1}">
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td>
    				<td class="l-td" style="">&nbsp;</td></tr>
    			</c:if>
    		
    		</c:if>
    	</c:forEach>
	</tbody></table>
	</c:forEach>
	</div>
	
	
	
</div>
</div>
<!--  
<div style="width:100%; position:absolute; z-index:99; top:0; left:0; background-color:white; text-align:center;">
	<img src="${ctx}/resources/dw/dw.jpg" />
</div>
-->
<script src="${ctx}/resources/layer/layer.min.js"></script>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');
	$('#员工出勤').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	/*
	$.get('${ctx}/web/leave/alu?t='+Math.random(),function(data){
		var uids = eval(data);
		for(var i=0; i<uids.length;i++){
			$('#info'+uids[i]).html('（请假）');
		}
	});
	*/
	
	$('.infospan').click(function(){
		var uid = $(this).attr('uid');
		//查询用户的具体情况，判断用户是否是当前用户的直接下属
		$.get('${ctx}/web/leave/ajaxUld?uid='+uid+'&r='+Math.random(),function(data){
			data = $.trim(data);
			if(data == 'not'){
			}else{
				alert(data);
			}
		});
	});
});
</script>
</body>
</html>
