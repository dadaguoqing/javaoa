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
	  				<th style="padding-left:15px; width:160px;">资产编号</th>
	  				<th style="width:120px;">负责人</th>
	  				<th style="width:120px;">放置地点</th>
	  				<th style=""> | 备注信息</th>
	  			</tr>
		  		<tr class="lzui-td" >
			    	<td>${prop.code }</td>
			    	<td>
			    		${prop.empName}
			    	</td>
			    	<td>
			    		${prop.placeName}
			    	</td>
			    	<td>
			    		${empty prop.bz ? '无' : prop.bz }
			    	</td>
			    </tr>
			    
			    </table>
			</div>
			
		<div style="padding-left:15px; height:30px; line-height:30px; font-weight:bold; font-size:15px; color:red;">资产日志：</div>
	<div class="lzui-table-wapper" style="margin-bottom:15px; margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:160px;">发生日期</th>
	  				<th style=""> | 记录</th>
	  			</tr>
	  			<c:forEach items="${logs}" var="cd">
		  		<tr class="lzui-td" >
			    	<td>${cd.dayStr }</td>
			    	<td>
			    		${cd.info}
			    	</td>
			    </tr>
			    </c:forEach>
			    
			    </table>
			</div>
</body>
</html>
<script>


</script>