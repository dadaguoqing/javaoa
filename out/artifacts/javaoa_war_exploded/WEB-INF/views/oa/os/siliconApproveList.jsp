<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${param.msg == 1}">
	<div class="lzui-error">操作成功，请继续进行其他操作</div>
	</c:if>
	
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="6">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				芯片审批记录
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header " style="width:20%;">申请人</td>
			<td class="l-td-header " style="width:20%;">状态</td>
			<td class="l-td-header" style="width:20%;">申领时间</td>
			<td class="l-td-header " style="width:20%;">需求时间</td>
			<td class="l-td-header " style="width:20%;">查看详情</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td">${cur.applyName }</td>
    			<td class="l-td ">
    				<c:choose>
    					<c:when test="${cur.status==0 or cur.status==1 or cur.status==2 or cur.status==3 or cur.status==4 }">
    						正在处理
    					</c:when>
    					<c:when test="${cur.status==5}">
    						申领成功
    					</c:when>
    					<c:when test="${cur.status==-1}">
    						已取消
    					</c:when>
    				</c:choose>
    			</td>
    			<td class="l-td">${cur.dayStr }</td>
    			<td class="l-td ">
    			${cur.useDayStr }
    			</td>
    			<td class="l-td">
    				<a href="${ctx }/web/oa/os/siliconApproveDetail?id=${cur.id}">查看详情</a>
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td-last-right" colspan="6" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
	</tbody></table>

</div>
	</div>
</div>
<script>
$(function(){
	$('#OA审批').addClass('ui-tabs-current');	
	$('#芯片申请审批').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
</script>
</body>
</html>