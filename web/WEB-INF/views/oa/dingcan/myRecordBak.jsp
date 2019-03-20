<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['7']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:2px;">
	
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" method="post" action="${ctx}/web/oa/dc/myRecord">
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">选择月份：</td>
			<td>
			<input name="yf" id="yf" value="${yf}" class="Wdate" style="width:100px;" autocomplete="off" 
				onFocus="WdatePicker({onpicked:sbmitSearch,dateFmt:'yyyy年MM月'})"/>
			</td>
			
		</tr>
	</table>
	</form>
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:8px; border-bottom:none;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="6">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				${yf}订餐详情
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:130px;">日期</td>
			<td class="l-td-header l-td-left" style="width:120px;">商家名称</td>
			<td class="l-td-header l-td-left" style="">描述</td>
			<td class="l-td-header l-td-left" style="width:60px;">价格</td>
			<td class="l-td-header l-td-left" style="width:80px">数量</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:80px;">总计消费</td>
			
		</tr>
    </tbody></table>
    </div>
    
    <div class="gridContainer" id="ctable" style="width:100%; height:400px; margin-left:0px; border-top:none;">
    <table class="lzzUIGrid"><tbody>
    	<c:forEach items="${list }" var="cur">	
    	<tr style="">
    		<td class="l-td" style="width:130px;">${cur.dayStr}</td>
    		<td class="l-td l-td-left" style="width:120px;">${cur.providerName }</td>
    		<td class="l-td l-td-left">${cur.description }</td>
    		<td class="l-td l-td-left" style="width:60px;">${cur.price }元</td>
    		
    		<td class="l-td l-td-left" style="width:80px">
				${cur.num }份
			</td>
    		<td class="l-td l-td-left l-td-last-right" style="width:80px;">${cur.priceAll }元</td>
			
    	</tr>
    	</c:forEach>
    	
	</tbody></table>

</div>
	</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#个人中心').addClass('ui-tabs-current');	
	$('#订餐记录').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	var dh = $(document).height();
	$('#ctable').css('height',(dh-219)+'px');
	
});

function sbmitSearch(){
	document.sForm.submit();
}

</script>
</html>