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

<body style="background-color:#efe; padding:0px; overflow:scroll; overflow-x:hidden;">

	<div class="lzui-table-wapper" style="margin-bottom:15px; margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:160px;">商家名称</th>
	  				<th style=""> | 描述</th>
	  				<th style=" width:80px;"> | 价格</th>
	  				<th style=" width:80px;"> | 数量</th>
	  				<th style=" width:100px;"> | 金额</th>
	  			</tr>
	  			<c:forEach items="${empCaidans}" var="cd">
		  		<tr class="lzui-td" >
			    	<td>${cd.providerName }</td>
			    	<td>
			    		${cd.description }
			    	</td>
			    	<td>${cd.price }元</td>
			    	<td>${cd.num}份</td>
			    	<td>
			    		${cd.price }元
					</td>
			    </tr>
			    </c:forEach>
			    <tr class="lzui-td"  style="color:red;">
			    	<td>${total.providerName }</td>
			    	<td>
			    		&nbsp;
			    	</td>
			    	<td>&nbsp;</td>
			    	<td>${total.num}份</td>
			    	<td>
			    		${total.price }元
					</td>
			    </tr>
			    </table>
			</div>
</body>
</html>
<script>

$(function(){
	$('input.uids').click(function(evt){
		var us = $(this);
		//alert(us);
		window.parent.addAWp(us.val(), us.attr('nameValue'), us.attr('typeValue'), us.attr('unitValue'));
	});
});
</script>