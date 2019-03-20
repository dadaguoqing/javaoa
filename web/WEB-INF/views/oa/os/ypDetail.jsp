<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	
	<div id="返回上级" class="navTitle2 cur" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">
	
	<form name="sForm" action="${ctx}/web/oa/os/medSearch" method="post" >
		<input type="hidden" name="end" value="${end}" />
		<input type="hidden" name="begin" value="${begin}"/>
		<input type="hidden" name="showType" value="${showType }"/>
	</form>
	
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				应急药品领用明细<span style="color:green;">（绿色表示公司消耗）</span>
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:140px;">领用日期</td>
			<td class="l-td-header l-td-left" style="width:100px;">申领人</td>
			<td class="l-td-header l-td-left" style="width:140px;">药品名称</td>
			<td class="l-td-header l-td-left" style="width:140px;">规格型号</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:140px">数量单位</td>
		</tr>
    	<c:forEach items="${list}" var="cur">
    		<c:if test="${cur.sqType == 1}">
    		<tr style="color:green;">
    		</c:if>
    		<c:if test="${cur.sqType == 0}">
    		<tr>
    		</c:if>
    		
    			<td class="l-td l-td-left">${cur.dayStr }</td>
    			<td class="l-td l-td-left">${cur.empName }</td>
    			<td class="l-td l-td-left">${cur.name }</td>
    			<td class="l-td l-td-left">${cur.type }</td>
				<td class="l-td l-td-left l-td-last-right" style="">
					${cur.num}${cur.unit}
				</td>
    		</tr>
    	</c:forEach>
    	
    		<tr style="color:red;">
    			<td class="l-td l-td-left">总计</td>
    			<td class="l-td l-td-left">&nbsp;</td>
    			<td class="l-td l-td-left">&nbsp;</td>
    			<td class="l-td l-td-left">&nbsp;</td>
				<td class="l-td l-td-left l-td-last-right" style="">
					${count}${total.unit}
				</td>
    		</tr>
    		
	</tbody></table>

	</div>
	
	</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#资产管理').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		document.sForm.submit();
	});
	
});


</script>
</html>