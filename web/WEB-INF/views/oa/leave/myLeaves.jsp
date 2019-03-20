<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['4']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/leave/afl">点击这里请假</a>
  	</div>
	
	<div class="gridContainer" style="width:95%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="6">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				我的请假列表
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
					<a href="${ctx }/web/oa/leave/info/${cur.id}">查看详情</a>
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

</body>
<script>
$(function(){
	$('a#OA申请').addClass('ui-tabs-current');	
	$('#请假申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
</script>
</html>