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
		<span style="color:#eee;">选择药品<span style="color:#E67E22;">（使用前请仔细查看详情）</span></span>
	</div>
	
	<table class="table1" ><tbody>
		
		<c:forEach items="${list}" var="c" varStatus="st">
		
		<%-- 
			<c:if test="${st.count==0}"><tr></c:if>
    	--%>
    	<tr>	
    		<td class="l-td" style="padding-top:5px; width:800px; padding-bottom:5px;">
    			<label class="ulb" style="cursor:pointer;" for="cb${c.id}">${c.name }（${c.type}）</label> 
    			
    			<input style="cursor:pointer;" name="wuId" id="cb${c.id}" type="radio" value="${c.id}" unitValue="${c.unit }" typeValue="${c.type }" nameValue="${c.name }" class="uids"/>
    			
    			<a class="hrefs" href="javascript:showDetail(${c.id })">点击查看详情</a>
    		</td>
    	</tr>
    	<tr id="tr${c.id}" style="display:none;">	
    		<td class="l-td" style="padding-top:2px; color:#C0392B; padding-bottom:2px; ">
    			<span style="font-weight:bold;">主治功能：</span>
    			${c.zhuzhi }
    			<br/>
    			<span style="font-weight:bold;">使用说明：</span>
    			${c.shuoming }
    			<br/>
    			<span style="font-weight:bold;">注意事项：</span>
    			${c.zhuyi }
    			<br/>
    		</td>
    	</tr>
    	<%-- 
    		<c:if test="${st.count%3==0 && st.count!=0}"></tr><tr></c:if>
    		<c:if test="${st.last}"></tr></c:if>
    	--%>
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
		var f = confirm("请确保您在使用之前仔细阅读药品注意事项");

		if(f){
			window.open('${ctx}/web/oa/os/showMedPdf?id='+us.val());
		}
		window.parent.addAYp(us.val(), us.attr('nameValue'), us.attr('typeValue'), us.attr('unitValue'));
	});
});


function showDetail(id){
	window.open('${ctx}/web/oa/os/showMedPdf?id='+id);
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