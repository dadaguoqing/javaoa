<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	
	<div class="gridContainer" style="width:95%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				待审批权限列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:60px;">申请人</td>
			<td class="l-td-header" style="width:160px;">申请时间</td>
			<td class="l-td-header l-td-left" style="">审批内容</td>
			<td class="l-td-header" style="width:80px;">审批意见</td>
			<td class="l-td-header l-td-last-right" style="width:130px;">操作</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">${cur.sender }</td>
    			<td class="l-td">${cur.createTime }</td>
    			<td class="l-td l-td-left" title="${cur.content}">
	    			<c:choose>  
					    <c:when test="${fn:length(cur.content) > 45}">  
					        <c:out value="${fn:substring(cur.content, 0, 40)}..." />  
					    </c:when>  
					   <c:otherwise>  
					      <c:out value="${cur.content}" />  
					    </c:otherwise>  
					</c:choose>
				</td>
    			<td class="l-td"><input id="yj${cur.id }" placeholder="请输入审批意见"/></td>
    			<td class="l-td">
    				<a href="javascript:showDetail(${cur.id });">详情</a> |
    				<a href="javascript:submitSp(${cur.id},1);">同意</a> |
    				<a href="javascript:submitSp(${cur.id},0);">不同意</a>
				</td>
    		</tr>
    		<tr class="detailTr" id="t${cur.id}" style="display:none;">
    			<td class="l-td l-td-left" colspan="5" style="color:blue; font-weight:bold;">
    				${cur.content}
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td-last-right" colspan="5" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
	</tbody></table>

</div>
	</div>
</div>
<script>

function showDetail(id){
	$('.detailTr').hide();
	$('#t'+id).show();
}
$(function(){
	$('#OA审批').addClass('ui-tabs-current');	
	$('#权限变更审批').addClass('cur');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function submitSp(id,sp){
	var yj = $('#yj'+id).val();
	yj = $.trim(yj);

	if(yj.length>30){
		alert('审批意见不能超过30个字符。');
		return;
	}
	
	var msg = ( sp == 1 ? "同意之后，用户的权限将发生改变。确定操作？" : "您确定不同意这个提议？" );
	if(confirm(msg)){
		yj = encodeURIComponent(yj);
		url = "${ctx}/web/oa/asp/sp/"+id+"/"+sp+"?yj="+yj;
		location.href= url;
	}
}
</script>
</body>
</html>