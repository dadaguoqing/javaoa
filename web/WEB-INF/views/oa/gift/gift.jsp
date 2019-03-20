<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
pageContext.setAttribute("ctx",path);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    
<title>福利品详情</title>
<link rel="stylesheet" href="${ctx }/resources/gift/base.css" />
</head>
  
<body>
	<div class="main">
		<img src="${ctx }/resources/gift/${gift.id}.jpg" /> <br/>
		<c:if test="${gift.id==3}">
		<img src="${ctx }/resources/gift/3-2.jpg" /> <br/>
		</c:if>
	</div>
    
</body>
</html>
