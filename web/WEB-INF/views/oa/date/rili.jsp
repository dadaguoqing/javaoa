<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<style>
<!--
	.curDay{background-color: #F39C12; }
-->
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

<div style="margin: 10px 5px 0 195px;">
	
	<div style="line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">工作月历</div>
	</div>
	
	<div class="gridContainer" style="width:80%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="7" style="position:relative; padding:0; font-weight:bold; text-align:center; line-height:32px; height:32px; ">
				<div style="position:absolute; left:5px; top:0px;">
					<img id="preMonth" title="上一个月" alt="上一个月" src="${ctx}/resources/images/back.png" style="width:32px; height:32px;">
				</div>
				<input id="yam" name="yam" value="${day.year }年${day.month }月" class="Wdate" onFocus="WdatePicker({onpicked:dateSeted,dateFmt:'yyyy年M月'})" style="width:85px;"/>
				工作日历<span style="color:red;">（红色代表节假日）</span>
				<div style="position:absolute; right:5px; top:0px;">
					<img id="nextMonth" title="下一个月" alt="下一个月" src="${ctx}/resources/images/forward.png" style="width:32px; height:32px;">
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header">周日</td>
			<td class="l-td-header">周一</td>
			<td class="l-td-header">周二</td>
			<td class="l-td-header">周三</td>
			<td class="l-td-header">周四</td>
			<td class="l-td-header">周五</td>
			<td class="l-td-header l-td-last-right">周六</td>
		</tr>
    	
    	<!-- color:#E74C3C -->
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td ${cur.sun.dayStr}" ${cur.sun.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>${cur.sun.day }</td>
    			<td class="l-td ${cur.mon.dayStr}" ${cur.mon.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>${cur.mon.day }</td>
    			<td class="l-td ${cur.tue.dayStr}" ${cur.tue.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>${cur.tue.day }</td>
    			<td class="l-td ${cur.wed.dayStr}" ${cur.wed.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>${cur.wed.day }</td>
    			<td class="l-td ${cur.thu.dayStr}" ${cur.thu.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>${cur.thu.day }</td>
    			<td class="l-td ${cur.fri.dayStr}" ${cur.fri.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>${cur.fri.day }</td>
    			<td class="l-td ${cur.sat.dayStr}" ${cur.sat.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>${cur.sat.day }</td>
    		</tr>
    	</c:forEach>
    	
	</tbody></table>
	</div>
	
</div>
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>

function dateSeted(){
	var y = $('#yam').val();
	//提交
	document.location.href="${ctx}/web/oa/date/cal?yam="+encodeURIComponent(y);
}

$(function(){
	$('#首页').addClass('ui-tabs-current');
	$('#工作日历').addClass('cur');

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#preMonth').click(function(){
		var y = "${day.year }年${day.month }月";
		document.location.href="${ctx}/web/oa/date/cal?pn=pre&yam="+encodeURIComponent(y);
	});

	$('#nextMonth').click(function(){
		var y = "${day.year }年${day.month }月";
		document.location.href="${ctx}/web/oa/date/cal?pn=next&yam="+encodeURIComponent(y);
	});
});
</script>