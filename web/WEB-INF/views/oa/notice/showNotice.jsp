<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    
    <title>${compName}</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	
  </head>
  
  <body>
	<div style="margin:auto; margin-top:20px; width:1000px; padding:0px 20px;  border-left:1px solid #eee; border-right:1px solid #eee;">
  		<h1 style="text-align:center;">${notice.title }</h1>
  		
  		<div style="margin-top:30px;">
  			${notice.content }
  		</div>
  	</div>
  </body>
  
</html>
