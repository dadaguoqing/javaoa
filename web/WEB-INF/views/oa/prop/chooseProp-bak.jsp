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
.ulb{*float:left; *display:block; *width:250px;}
.hrefs{float:right; display:block; *width:120px; margin-right:15px; }
.uids{float:right; display:block;}
</style>
</head>

<body onbeforeunload="setUids()" style="background-color:#efe; padding:0px; overflow-x:hidden;">

	<div style="border:1px solid #2191C0;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">选择固定资产</span>
	</div>
	
	<table class="table1" ><tbody>
		
		<c:forEach items="${subTypes}" var="t" >
		<tr>	
			<td class="l-td" colspan="4" style="padding-top:5px; padding-bottom:5px; font-weight:bold; padding-left:20px;">
				${t.name }
			</td>
		</tr>
		
		<c:forEach items="${map[t.id]}" var="c" varStatus="st">
		
		<%-- 
			<c:if test="${st.count==0}"><tr></c:if>
    	--%>
    	<tr>	
    		<td class="l-td" style="padding-top:5px; width:800px; padding-bottom:5px;">
    			<label class="ulb" style="cursor:pointer;" for="cb${c.id}">${c.code } - ${c.name }（${c.spec}）</label> 
    			
    			<input style="cursor:pointer;" name="wuId" id="cb${c.id}" type="radio" value="${c.id}" unitValue="${c.unit }" typeValue="${c.spec }" priceValue="${c.price }" nameValue="${c.name }" class="uids"/>
    			
    			
    		</td>
    	</tr>
    	
    	<%-- 
    		<c:if test="${st.count%3==0 && st.count!=0}"></tr><tr></c:if>
    		<c:if test="${st.last}"></tr></c:if>
    	--%>
    	</c:forEach>
		</c:forEach>
	</tbody></table>
	</div>
</body>
</html>
<script>

$(function(){
	$('input.uids').click(function(evt){
		var us = $(this);
		//alert(us);
		window.parent.addAProp(us.val(), us.attr('nameValue'), us.attr('typeValue'), us.attr('priceValue'));
	});
});


function showDetail(id){
	//window.open('${ctx}/web/oa/os/showMedPdf?id='+id);
	/*
	var trs = $('#tr'+id);
	var disp = trs.css('display');
	if(disp == 'none'){
		trs.show();
	}else{
		trs.hide();
	}
	*/
}
</script>