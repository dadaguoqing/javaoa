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
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;节日礼品活动</div>
	<div class="navTitle2" url="${ctx }/web/oa/user/wh"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>

</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">活动管理</div>
		
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
		
		<div class="" style="margin:10px 0 10px 0;">
			<a href="${ctx }/web/oa/gift/evt/add" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">添加活动</a>
		</div>
	
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="6">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					活动列表
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header l-td-left" style="width:140px;">活动名称</td>
				<td class="l-td-header l-td-left" style="width:60px;">管理员</td>
				<td class="l-td-header" style="width:155px;">开始时间</td>
				<td class="l-td-header" style="width:155px;">结束时间</td>
				<td class="l-td-header l-td-left" style="width:60px;">状态</td>
				<td class="l-td-header l-td-last-right" style="">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur" >
	    		<tr>
	    			<td class="l-td l-td-left">${cur.name }</td>
	    			<td class="l-td l-td-left">${AllUsers[cur.managerId].name }</td>
	    			<td class="l-td">${cur.beginTime }</td>
	    			<td class="l-td">${cur.endTime }</td>
	    			<td class="l-td l-td-left">
	    				<c:choose>
	    					<c:when test="${cur.status == -1}">
	    					已删除
	    					</c:when>
	    					<c:when test="${cur.status == 0}">
	    					未启动
	    					</c:when>
	    					<c:when test="${cur.status == 1}">
	    					进行中
	    					</c:when>
	    					<c:when test="${cur.status == 2}">
	    					已结束
	    					</c:when>
    					</c:choose>
	    			</td>
	    			
	    			<td class="l-td">
						<a href="${ctx}/web/oa/gift/gift/list?evtId=${cur.id}">设置礼品</a>
						<c:choose>
	    					<c:when test="${cur.status == 0}">
	    					| &nbsp;&nbsp;<a href="${ctx}/web/oa/gift/evt/begin?evtId=${cur.id}">启动活动</a>
	    					</c:when>
	    					<c:when test="${cur.status == 1}">
	    					| &nbsp;&nbsp;<a href="${ctx}/web/oa/gift/evt/end?evtId=${cur.id}">结束活动</a>
	    					</c:when>
    					</c:choose>
						| &nbsp;&nbsp;<a href="javascript:delEvt(${cur.id},'${cur.name }')">删除</a>
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
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	
});

function delEvt(id,name){
	var flag = window.confirm('您确定要删除活动：'+name+"？");

	if(flag){
		document.location.href = "${ctx}/web/oa/gift/evt/del?evtId="+id;
	}
	//
}


</script>
</body>
</html>