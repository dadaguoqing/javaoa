<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 " url="${ctx}/web/oa/index" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;首页</div>
	<div class="navTitle2 cur" url="${ctx }/web/oa/notice/list/${loginUser.id}/0" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;通知公告</div>
	<div class="navTitle2" url="${ctx }/web/oa/rules/list" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;规章制度</div>
	<div class="navTitle2" url="${ctx }/web/oa/date/cal" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;工作日历</div>

</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">通知公告</div>
		
		<div class="gridContainer" style="width:95%; margin-left:0px;margin-top:10px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="6">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					通知公告列表
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header" style="width:190px;">发布时间</td>
				<td class="l-td-header l-td-left" style="width:180px;">标题</td>
				<td class="l-td-header l-td-left" style="width:80px;">发布人</td>
				<td class="l-td-header l-td-left" style="width:150px;">发布部门</td>
				<td class="l-td-header l-td-last-right" style="">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur">
	    		<c:if test="${cur.status == -1}">
	    		<tr style="color:red;">
	    		</c:if>
	    		<c:if test="${cur.status != -1}">
	    		<tr>
	    		</c:if>
	    			<td class="l-td">${cur.createTime }</td>
	    			<td class="l-td l-td-left">${cur.title } </td>
	    			<td class="l-td l-td-left">${cur.publisher }</td>
	    			<td class="l-td l-td-left">${cur.pubName }</td>
	    			<td class="l-td">
						<a href="javascript:_read(${cur.id },${cur.status});">查看</a>
					</td>
	    		</tr>
	    		<c:if test="${cid == cur.id}">
	    		<tr class="cont" id="tr${cur.id }" style="color:blue; font-weight:bold;">
	    		</c:if>
	    		<c:if test="${cid != cur.id}">
	    		<tr class="cont" id="tr${cur.id }" style=" display:none;">
	    		</c:if>
	    			<td class="l-td" colspan="5" style="text-align:left; padding:15px;">
	    				${cur.content }
	    			</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="5" style="color:red;">
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
	$('#首页').addClass('ui-tabs-current');	
	$('#通知公告').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function _read(id, status){
	var dis = $('#tr'+id).css('display');
	$('.cont').css('display','none');
	if(dis == 'none'){
		$('#tr'+id).css('display','');
	}

	if(status == -1){
		//ajax，已读
		$.get("${ctx}/web/oa/notice/read?empId=${loginUser.id}&id="+id);
	}
}
</script>
</body>
</html>