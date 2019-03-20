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
	<div style="overflow:hidden; position:absolute; margin:0px; padding:0px; border:none; top:0px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight)); width:100%;">
  		<embed style="padding:0; margin:0; height:100%; width:100%;" 
  			name="plugin" src="<%=basePath %>web/oa/rules/${rule.id }.pdf" type="application/pdf" />
  	</div>
  </body>
  
</html>
