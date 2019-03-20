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
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<script type="text/javascript" src="${ctx }/resources/include/js/jquery-1.8.2.js"></script>
</head>

<body style="background-color:#efe; padding:0px; overflow:scroll; overflow-x:hidden;">

	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
				<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
				供应商信息</div>
		<form name="sForm" method="post" action="${ctx}/web/oa/dc/editProvider">
			<input type="hidden" name="id" id="tid" value="${p.id }"/>
			<table id="baseInfoTable">
				<tr>
					<td class="right" style="width:90px;">供应商名称：</td>
					<td>
						<input name="name" id="tname" value="${p.name }"/>
					</td>
				</tr>
				<tr>
					<td class="right" style="width:90px;">联系电话：</td>
					<td>
						<input name="phone" id="tphone" value="${p.phone }"/>
					</td>
				</tr>
				<tr>
					<td class="right" style="width:90px;">备注：</td>
					<td>
						<input name="bz" id="tbz" value="${p.bz }"/>
					</td>
				</tr>
			</table>
		</form>

		<div class="" style="margin-top:10px; margin-left:20px; margin-bottom:20px;">
			<a href="javascript:editProvider();" class="lzui-btn lzui-corner-all">保存</a>
		</div>
</body>
</html>
<script>

$(function(){
	
});


function editProvider(){
	var id = $('#tid').val();
	var name = $('#tname').val();
	var phone = $('#tphone').val();
	var bz = $('#tbz').val();
	
	window.parent.submitEditP(id, name, phone, bz);
}

</script>