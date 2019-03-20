<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
pageContext.setAttribute("ctx",path);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
<title>礼品详情</title>

<style>
img { }
</style>
</head>
  
<body>
	<div style="margin:auto; margin-top:20px; text-align:center;">
		<img src="${ctx }/resources/gift/1.bmp" /> <br/>
	</div>
    
</body>
</html>
