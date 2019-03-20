<%@ page language="java" import="java.util.*,com.hj.oa.bean.*,com.hj.util.*" pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<style>
<!--
.tr1:hover {background-color: #ddd;}
-->
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

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">

<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">年度评价</div>
	<%--
		<%
			List<Role> roles = (List<Role>)session.getAttribute("loginUserRoles");
			User user = (User)session.getAttribute("loginUser");
			List<User> noticer = (List<User>)request.getAttribute("noticer");
			if( RoleUtil.hasRole(roles,"总经理") || RoleUtil.hasRole(roles,"技术总监")  || RoleUtil.hasRole(roles,"部门主管") || user.getId()==35 ){
		%>
	<div class="" style="margin:10px 0 0 0;">
		<a href="${ctx }/web/oa/ev/subsZg" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">查看下属自评及主管评价</a>
	</div>
		<%
			}
		%>
		--%>
	<c:if test="${empty ev.point}">
	<form name="form0" action="${ctx}/web/oa/ev/myModify" method="post" >
    	<input type="hidden" name="tabId" value="2">
	</c:if>
	
	<div style="color:red; font-weight:bold;margin-top:10px;">
	请注意：所有文字描述项，不超过100个字符。
	</div>
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:10px;">
	<table class="lzzUIGrid"><tbody>
		<c:if test="${not empty ev.point}">
		<tr>
			<td class="l-td-toolbar" colspan="3">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				
				<span style="color:red">（已完成）</span>
				
				</div>
			</td>
		</tr>
		</c:if>
		<tr>
			<td class="l-td-header l-td-left" >评价项目</td>
			<td class="l-td-header l-td-left">自评得分</td>
			<td class="l-td-header l-td-left">补充说明</td>
		</tr>
    	<c:forEach items="${list}" var="cur" varStatus="s">
    		<c:if test="${not empty cur.point}">
    		<tr class="tr1">
    			<td class="l-td l-td-left" width="450px">${s.count}. ${cur.title}</td>
    			<td class="l-td l-td-center" width="100px">
    				<c:if test="${empty ev.point}">
    				<input type="hidden" name="evaIds" value="${cur.id }">
    				<input type="hidden" name="types" value="${cur.type }">
    				<select class="poi" name="points" style="width: 65px">
    					<option value="-1">请选择</option>
    					<tag:loopInt times="${cur.point }"/>
    				</select>
    				</c:if>
    				<c:if test="${not empty ev.point}">
    					${cur.empPoint }
    				</c:if>
    			</td>
    			<td class="l-td l-td-left">
    				<c:if test="${empty ev.point}">
    					<input type="text" class="ptxt" name="anses" size="60px" value="${cur.ans }"/>
    				</c:if>
    				<c:if test="${not empty ev.point}">
    					${cur.ans }
    				</c:if>
    			</td>
    		</tr>
    		</c:if>
    		
    		<c:if test="${empty cur.point}">
    		<tr>
    			<td class="l-td l-td-left" colspan=2 style="border-bottom:none;">${cur.title}</td>
    		</tr>
    		<tr>
    			<td class="l-td l-td-left" colspan=2>
    				<c:if test="${empty ev.point}">
    				<input type="hidden" name="evaIds" value="${cur.id }">
    				<textarea class="ptxt" name="points" style="width:80%; height:50px; padding:3px 5px;"></textarea>
    				</c:if>
    				
    				<c:if test="${not empty ev.point}">
    				<textarea style="width:80%; height:40px; padding:3px 5px;">${cur.ans }</textarea>
    				</c:if>
    			</td>
    		</tr>
    		</c:if>
    	</c:forEach>
    	<c:if test="${not empty ev.point}" >
    		<tr style='color:red;'>
    			<td class="l-td l-td-right" >合计得分：</td>
    			<td class="l-td l-td-left" colspan="2">
    				${ev.point}<span style="font-weight: bold;color: green;"></span>
    			</td>
    		</tr>
    	</c:if>
	</tbody></table>

	</div>
	<c:if test="${empty ev.point}">
	</form>
	</c:if>
	
	<c:if test="${empty ev.point}">
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交</a>
	</div>
	</c:if>
	
	</div>
</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');
	$('#自我评价').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	
});

function submitForm(){
	var al = $('.poi');
	var f = true;
	al.each(function(i){
		var p = parseInt($(this).val());
		if(p == -1){
			f = false;
		}
		
	});

	if(!f){
		alert("请对每一个自评项目进行打分。");
		return;
	}

	var f2 = true;
	var altxt = $('.ptxt');
	altxt.each(function(i){
		var t = $(this).val();
		if(t.length > 100){
			f2 = false;
		}
		t = $.trim(t);

	});

	if(!f2){
		alert("补充说明不能超过100个字符，请减少文字");
		return;
	}

	
	document.form0.submit();
}

</script>
</html>