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
				查询条件</div>
			<form name="sForm" method="post" action="${ctx}/web/oa/dc/ajaxMenus">
			<table id="baseInfoTable">
				<tr>
					<td class="right" style="width:80px;">供应商：</td>
					<td>
						<select name="pId" id="pId">
							<option value="0">-选择-</option>
			    			<c:forEach items="${providers}" var="cur">
			    			<option value="${cur.id }">${cur.name }</option>
			    			</c:forEach>
			    		</select>
					</td>
				</tr>
			</table>
			</form>

	<div class="lzui-table-wapper" style="margin-bottom:15px; margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:30px;">
						<input type="checkbox" id="checkAll"/>
					</th>
	  				<th style="width:160px;">商家名称</th>
	  				<th style=""> | 描述</th>
	  				<th style=" width:80px;"> | 价格</th>
	  			</tr>
	  			<c:forEach items="${menus}" var="cd">
		  		<tr class="lzui-td" >
		  			<td>
		  				<input type="checkbox" value="${cd.id}" piVal="${cd.providerId}" pnVal="${cd.providerName}" dVal="${cd.description}" pVal="${cd.price}" name="ms"/>
		  			</td>
			    	<td>${cd.providerName }</td>
			    	<td>
			    		${cd.description }
			    	</td>
			    	<td>${cd.price }元</td>
			    </tr>
			    </c:forEach>
			    </table>
			</div>
			
			<div class="" style="margin-top:10px; margin-left:20px; margin-bottom:20px;">
			<a href="javascript:addMenus();" class="lzui-btn lzui-corner-all">确定</a>
			</div>
</body>
</html>
<script>

$(function(){

	$('#pId').change(function(){
		document.sForm.submit();
	});

	$('#pId').val('${pId}');

	$('#checkAll').click(function(){
		var ca = $(this);
		var f = ca.prop("checked");
		
		if(f){
			$("input[name='ms']").prop("checked", true); 
		}else{
			$("input[name='ms']").prop("checked", false); 
		}
		
	});
});


function addMenus(){
	var ms = $("input[name='ms']:checked");//.attr("pnVal");
	if(ms.length>0){

		ms.each(function(){
			var thi = $(this);
			var id = thi.val();
			var pn = thi.attr("pnVal");
			var pid = thi.attr("piVal");
			var p = thi.attr("pVal");
			var d = thi.attr("dVal");

			//alert(id+","+pn+","+p+","+d+".");

			window.parent.addcd2(id,pn,pid,d,p);
		});
		
	}

	window.parent.closeIvt();
	//alert(ms.length);
}

</script>