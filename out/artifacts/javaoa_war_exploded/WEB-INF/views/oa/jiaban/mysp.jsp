<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<%@ include file="../../public/lead.jsp"%>
<%-- 	<c:forEach items="${loginUserMenuMap['5']}" var="cur"> --%>
<%-- 	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div> --%>
<%-- 	</c:forEach> --%>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${param.msg == 1}">
	<div class="lzui-error">操作成功，请继续审批。或进行其他操作</div>
	</c:if>
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="6">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				待审批加班列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:60px;">申请人</td>
			<td class="l-td-header" style="width:170px;">申请时间</td>
			<td class="l-td-header" style="width:170px;">加班申请</td>
			<td class="l-td-header l-td-left" style="width:170px;">加班时长</td>
			<td class="l-td-header" style="width:80px;">加班类型</td>
			<td class="l-td-header l-td-last-right" style="">查看详情</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">${cur.proposerName }</td>
    			<td class="l-td">${cur.createTime }</td>
    			<td class="l-td">${cur.dayte }</td>
    			<td class="l-td l-td-left">${cur.hours }小时</td>
    			<td class="l-td l-td-left">${cur.type == 0 ?  '普通加班' : '法定假日' }</td>
    			<td class="l-td">
    				<a href="${ctx }/web/oa/jiaban/edit/${cur.id}">查看详情</a>
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
	$('#加班审批').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
</script>
</body>
</html>