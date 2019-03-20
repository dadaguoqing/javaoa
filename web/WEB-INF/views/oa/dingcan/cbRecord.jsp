<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<style>

#first_button{
	width: 70px;
	padding:0px 15px;
}


</style>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	
	<%@ include file="../../public/indexMenu.jsp" %>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:2px;">
	<div class="" style="margin:10px 0 10px 0;">
		<button id="first_button" onclick="test_func()">导表</button>
	</div>
	<div class="" style="margin:10px 0 10px 0;">
		<a href="${ctx }/web/oa/dc/cBalance" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">返回充值/扣款</a>	
	</div>
	
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" method="post" action="${ctx}/web/oa/dc/cbRecord">
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">开始时间：</td>
			<td>
			<input name="begin" id="begin" value="${begin}" class="Wdate" style="width:115px;" autocomplete="off" 
				onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			</td>
			<td class="right" style="width:80px;">结束时间：</td>
			<td>
			<input name="end" id="end" value="${end}" class="Wdate" style="width:115px;" autocomplete="off" 
				onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			</td>
			
			<td>
				<div style="padding:0px 10px;">
					<div class="buttonDiv saveBtn" id="sBtn">
						<span>搜索</span>
						<img src="${ctx}/resources/images/server_add.png" style="width:16px;height:16px;"/>
					</div>
			</div>
			</td>	
		</tr>
	</table>
	</form>
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:8px; border-bottom:none;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="6">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				充值/扣款记录
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:180px;">交易时间</td>
			<td class="l-td-header l-td-left" style="width:60px">姓名</td>
			<td class="l-td-header l-td-left" style="width:80px;">类型</td>
			<td class="l-td-header l-td-left" style="width:60px;">金额</td>
			<td class="l-td-header l-td-left" style="width:60px;">余额</td>
			<td class="l-td-header l-td-left l-td-last-right" style="">备注</td>
			
		</tr>
    </tbody></table>
    </div>
    
    <div class="gridContainer" id="ctable" style="width:100%; height:400px; margin-left:0px; border-top:none;">
    <table class="lzzUIGrid"><tbody>
    	<c:forEach items="${list }" var="cur">	
    	
    	<c:if test="${cur.type == '订餐扣除' || cur.type == '扣款'}">
    		<tr style="color:red;">
    	</c:if>
    	<c:if test="${cur.type == '充值'}">
    		<tr style="">
    	</c:if>
    		<td class="l-td" style="width:180px;">${cur.dayTime}</td>
    		<td class="l-td l-td-left" style="width:60px;">${cur.empName}</td>
    		<td class="l-td l-td-left" style="width:80px;">${cur.type }</td>
    		<td class="l-td l-td-left" style="width:60px;">${cur.money}元</td>
    		
    		<td class="l-td l-td-left" style="width:60px;">
				${cur.balance }元
			</td>
			<td class="l-td l-td-left">${cur.bz }</td>
			
    	</tr>
    	</c:forEach>
    	
	</tbody></table>

</div>
	</div>
</div>


</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>

//functions of zeroth cat begins

function test_func(){
	
	
	var begin_date = document.getElementById("begin").value;

	var end_date = document.getElementById("end").value;
	
	window.location.replace("${ctx }/web/oa/dc/cbRecExcel/"+begin_date+"/"+end_date);
}
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#订餐管理').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	var dh = $(document).height();
	$('#ctable').css('height',(dh-219)+'px');


	$('#sBtn').click(function(){
		document.sForm.submit();
	});
	
});


</script>
</html>