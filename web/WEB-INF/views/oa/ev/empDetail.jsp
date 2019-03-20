<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
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
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;员工自评详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/ev/subs?deptId=${param.deptId}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">

<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">下属自评</div>
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				宏晶科技2017年度员工自我评价
				<span style="color:red">（${emp.name }）</span>
				<input type="hidden" name="empName" value="${emp.name }">
				<input type="hidden" name="empId" value="${emp.id }">
				</div>
			</td>
		</tr>
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
    				<select class="poi" name="points" style="width:45px;">
    					<tag:loopInt times="${cur.point }"/>
    				</select>
    				</c:if>
    				<c:if test="${not empty ev.point}">
    					<fmt:formatNumber type="number" value="${cur.empPoint }" pattern="0.00" maxFractionDigits="2"/>
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
	</tbody></table>

	</div>
	
	</div>
</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function(){
		$('#首页').addClass('ui-tabs-current');
	
		$('.navTitle2').click(function(){
			var url = $(this).attr('url');
			if(url)
				document.location.href=url;
		});
	});
</script>
</html>