<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
pageContext.setAttribute("ctx",path);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${compName }</title>
<link rel="stylesheet" href="${ctx }/resources/css/base.css" />
<link rel="stylesheet" href="${ctx }/resources/css/comm.css" />
<link rel="stylesheet" href="${ctx }/resources/css/style_new.css" />
<link rel="stylesheet" href="${ctx }/resources/css/index.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/ui.all.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/resources/lzzUI/css/base.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/resources/lzzUI/css/grid.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/lzui.all.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/lzui.czexam.css" />

<script type="text/javascript" src="${ctx }/resources/include/js/jquery.min.js"></script>

<style>
.ulb{float:left; display:block; width:60px;}
.uids{float:left; display:block;}
</style>
</head>

<body style="background-color:#efe; overflow-x:hidden;">

	<div style="border:1px solid #2191C0;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">选择员工</span>
	</div>
	
	<c:forEach items="${depts}" var="cur">
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		${cur.name }</span>
	</div>
	<table class="table1" ><tbody>
		<c:forEach items="${duMap[cur.id]}" var="c" varStatus="st">
			<c:if test="${st.count==0}"><tr></c:if>
    		
    		<td class="l-td" style="padding-top:3px; width:100px; padding-bottom:3px;">
    			<label  class="ulb" for="cb${c.id}">${c.name }</label> <input name="empId" id="cb${c.id}" type="radio" value="${c.id}" nameValue="${c.name }" class="uids"/>
    		</td>
    		<c:if test="${st.count%6==0 && st.count!=0}"></tr><tr></c:if>
    		<c:if test="${st.last}"></tr></c:if>
    	</c:forEach>
	</tbody></table>
	</c:forEach>
	</div>
</body>
</html>
<script>

$(function(){
	$('input.uids').click(function(evt){
		var us = $(this);
		//alert(us);
		window.parent.setUser(us.val(), us.attr('nameValue'));
	});
});

	function setUids(){
		//alert(1);
		//alert();
		var us = $('input.uids:checked');

		var str = '';
		var nams = '';

		if(us!=null && us.val()){
			str = us.val() + ":" + us.attr('nameValue');
		}
		
		window.returnValue = str;
	}
</script>