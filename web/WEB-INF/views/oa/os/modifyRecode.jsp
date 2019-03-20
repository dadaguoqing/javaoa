<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;办公用品变更申请记录</div>
	<div class="navTitle2" url="${ctx }/web/oa/os/stock"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="4">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				办公用品变更申请记录
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:120px;">申请时间</td>
			<td class="l-td-header" style="width:120px;">变更类型</td>
			<td class="l-td-header" style="width:120px;">申请结果</td>
			<td class="l-td-header l-td-last-right" style="width:100px;">查看详情</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td">${cur.createTime }</td>
    			<td class="l-td ">
    				<c:choose>
    					<c:when test="${cur.operate==1}">
    						删除
    					</c:when>
    					<c:when test="${cur.operate==2}">
    						修改
    					</c:when>
    				</c:choose>
    			</td>
    			<td class="l-td">
    			<c:choose>
    				<c:when test="${cur.status==1}">正在审批</c:when>
    				<c:when test="${cur.status==2}">审批通过</c:when>
    				<c:when test="${cur.status==-1}">审批不通过</c:when>
    			</c:choose>
				</td>
    			<td class="l-td">
    				<a href="${ctx }/web/oa/os/modifyRecodeDetail?id=${cur.id}">查看详情</a>
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td-last-right" colspan="4" style="color:red;">
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
	$('#资产管理').addClass('ui-tabs-current');	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
</script>
</body>
</html>