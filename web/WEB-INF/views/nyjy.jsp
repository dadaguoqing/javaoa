<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'nyjy.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

	<style>
		th,td{border:1px solid #777; padding:3px 5px;}
	</style>
  </head>
  
  <body>
    
    	<table>
    		<tr>
    			<th>姓名</th>
    			<th>年假剩余</th>
    			<th>病假剩余</th>
    		</tr>
    		<c:forEach items="${list}" var="cur">
    		<tr>
    			<td>${cur.empName }</td>
    			<td>${cur.nianjiaStr }</td>
    			<td>${cur.bingjiaStr }</td>
    		</tr>
    		</c:forEach>
    	</table>
    
  </body>
</html>
