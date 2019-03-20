<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test3.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    手工提交考勤机中的考勤数据。两个日期应该是同一天。
    
    <form action="web/oa/emp/kq" method="post">
    	考勤机日期: <input name="realDay" class="Wdate"  onFocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/> 考勤机中的日期<br/><br/>
    	系统中日期 : <input name="day" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>导入到系统中的日期
    	<br/><br/>
    	<input type="submit" value="提交"/>
    </form>
  </body>
</html>
<script language="javascript" type="text/javascript" src="<%=basePath%>resources/My97DatePicker/WdatePicker.js"></script>