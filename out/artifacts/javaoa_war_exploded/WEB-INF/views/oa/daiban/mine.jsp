<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
</head>
<body style="background-color:#efe;">

<div style="border:1px solid #2191C0; width:678px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">我的待办事件</span>
	</div>
	
	<c:if test="${not empty mines}">
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		与我相关</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
    		
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			序号
    		</td>
    		<td class="l-td" style="padding:7px; width:200px; font-weight:bold;">
    			待办事件名称
    		</td>
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			数目
    		</td>
    		<td class="l-td" style="padding:7px; font-weight:bold;">
    			操作
    		</td>
    	</tr>
		<c:forEach items="${mines}" var="cur" varStatus="st">
			<tr>
    		
    		<td class="l-td" style="padding:7px;">
    			${st.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.name}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			<a href="javascript: pGo('${ctx }/${cur.url}');">前往处理</a>
    		</td>
    		</tr>
    	</c:forEach>
	</tbody></table>
	</c:if>
	
	
	<c:if test="${not empty dlnames}">
	<c:forEach items="${dlnames}" var="cname">
	<c:if test="${not empty dls[cname]}">
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		代理${cname }的事件</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
    		
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			序号
    		</td>
    		<td class="l-td" style="padding:7px; width:200px; font-weight:bold;">
    			待办事件名称
    		</td>
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			数目
    		</td>
    		<td class="l-td" style="padding:7px; font-weight:bold;">
    			操作
    		</td>
    	</tr>
		<c:forEach items="${dls[cname]}" var="cur" varStatus="st">
			<tr>
    		
    		<td class="l-td" style="padding:7px;">
    			${st.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.name}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			<a href="javascript: pGo('${ctx }/web/dlogin?oid=${cur.dialiId}');">前往处理</a>
    		</td>
    		</tr>
    	</c:forEach>
	</tbody></table>
	</c:if>
	</c:forEach>
	</c:if>
	</div>
</body>
</html>
<script type="text/javascript">
function pGo(url){
	window.parent.location.href=url;
}
</script>


