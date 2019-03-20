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

<script type="text/javascript" src="${ctx }/resources/js/jquery-1.10.1.min.js"></script>
</head>

<body onbeforeunload="setUids()" style="background-color:#efe; padding:10px 0 10px 10px; overflow:scroll; overflow-x:hidden;">

	<div style="border:1px solid #2191C0;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">选择职位</span>
	</div>
	<div style="margin:15px 0px 0 10px;">
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		${cur.name }</span>
	</div>
	<table class="table1" ><tbody>
		<c:forEach items="${empRoles}" var="role" varStatus="st">
			<c:if test="${st.count==0}"><tr></c:if>
    		<c:if test="${role.check == 'unchecked' }">
	    		<td class="l-td" style="padding-top:10px; width:100px; padding-bottom:10px;">
	    			<label for="cb${role.id}">${role.name }</label> <input id="cb${role.id}" type="checkbox" value="${role.id}" nameValue="${role.name }" class="uids"/>
	    		</td>
    		</c:if>
    		<c:if test="${role.check == 'checked' }">
	    		<td class="l-td" style="padding-top:10px; width:100px; padding-bottom:10px;">
	    			<label for="cb${role.id}">${role.name }</label> <input id="cb${role.id}" type="checkbox" checked="checked" value="${role.id}" nameValue="${role.name }" class="uids"/>
	    		</td>
    		</c:if>
    		<c:if test="${st.count%5==0 && st.count!=0}"></tr><tr></c:if>
    		<c:if test="${st.last}"></tr></c:if>
    	</c:forEach>
	</tbody></table>
	</div>
</body>
</html>
<script>

$(function(){
	$('#qx').click(function(){

		var ch = $(this).prop('checked');

		$('.uids').prop('checked',ch);
	});

	/*
	$('#fx').click(function(){

		
		var ch = $(this).prop('checked');

		if(ch){
			var us = $('input.uids')

			$.each(us,function(i,n){
				var p = $(this).prop('checked');
				$(this).prop('checked',!p);
			});
		}

	});*/
	
});
	function setUids(){
		//alert(1);
		//alert();
		var us = $('input.uids:checked');

		var str = '';
		var nams = '';
		$.each(us,function(i,n){
			str += ($(n).val() + ",");
			nams += ($(n).attr('nameValue') + ",");
		});

		/*
		var parentWin=window.dialogArguments
		if(parentWin){
			parentWin.setUNames(nams);
		}else{
			window.opener.setUNames(nams);
		}*/
		
		window.returnValue = str;
	}
</script>