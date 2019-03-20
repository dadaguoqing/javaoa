<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>每月考勤汇总</title>
    
  </head>
  
  <body>
    
    <form action="web/oa/emp/huizong" method="post">
    	开始日期: <input name="begin" class="Wdate"  onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/> 
    		<br/><br/>
    	结束日期 : <input name="end" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
    		<br/><br/>
    	<input type="submit" value="提交"/>
    </form>
  </body>
</html>
<script language="javascript" type="text/javascript" src="<%=basePath%>resources/My97DatePicker/WdatePicker.js"></script>