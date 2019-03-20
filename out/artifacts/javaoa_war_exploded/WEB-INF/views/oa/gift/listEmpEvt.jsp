<%@ page language="java" import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<style>
<!--
.selectBak {background: #E74C3C; color:#eee; font-weight:bold;}
.selectBak:hover{background: #E74C3C; color:#eee; font-weight:normal;}
-->
</style>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['7']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">节日福利</div>
		
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
		
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="4">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					福利列表
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header l-td-left" style="width:140px;">福利名称</td>
				<td class="l-td-header" style="width:160px;">开始时间</td>
				<td class="l-td-header" style="width:160px;">结束时间</td>
				<td class="l-td-header l-td-last-right" style="">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur" >
	    		<tr>
	    			<td class="l-td l-td-left">${cur.name }</td>
	    			<td class="l-td">${cur.beginTime }</td>
	    			<td class="l-td">${cur.endTime }</td>
	    			
	    			<td class="l-td">
	    				
	    				<c:choose>
	    					<c:when test="${cur.status == 0}">
	    					活动还未开始
	    					</c:when>
	    					<c:when test="${cur.status == 1}">
	    					<a href="${ctx}/web/oa/gift/mine?evtId=${cur.id}">开始挑选</a>
	    					</c:when>
	    					<c:when test="${cur.status == 2}">
	    					活动已经结束
	    					</c:when>
    					</c:choose>
						<c:if test="${loginUser.id == cur.managerId}">
							| &nbsp;&nbsp;<a href="${ctx}/web/oa/gift/emps?evtId=${cur.id}">查看活动</a>
						</c:if>
					</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="4" style="color:red;">
					对不起，目前还没有相关数据。
				</td>
			</tr>
	    	</c:if>
		</tbody></table>
		</div>
	
	</div>
</div>
</div>
<script>
$(function(){
	$('#个人中心').addClass('ui-tabs-current');	
	$('#节日福利').addClass('cur');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	
});


</script>
</body>
</html>