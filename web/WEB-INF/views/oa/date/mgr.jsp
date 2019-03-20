<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['2']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
	
	<!--  
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="http://10.50.32.18:80/jgsw/main.do?method=initData"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;首页</div>
	<div class="navTitle2" url="http://10.50.32.18:80/jgsw/login.do?method=logout"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;退出登陆</div>
	-->
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">工作月历</div>
	</div>
	
	<form name="form1" action="${ctx}/web/oa/date/set" method="post">
	<input type="hidden" name="type" id="typeId"/>
	<input type="hidden" name="year" value="${day.year }"/>
	<input type="hidden" name="month" value="${day.month }"/>
	<div class="gridContainer" style="width:80%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="7" style="position:relative; padding:0; font-weight:bold; text-align:center; line-height:32px; height:32px; ">
				<div style="position:absolute; left:5px; top:0px;">
					<img id="preMonth" title="上一个月" alt="上一个月" src="${ctx}/resources/images/back.png" style="width:32px; height:32px;">
				</div>
				<input id="yam" name="yam" value="${day.year }年${day.month }月" class="Wdate" onFocus="WdatePicker({onpicked:dateSeted,dateFmt:'yyyy年M月'})" style="width:80px;"/>
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
    			<td class="l-td" ${cur.sun.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>
    				<c:if test="${not empty cur.sun}">
    					<input id="${cur.sun.day}" type="checkbox" name="days" value="${cur.sun.day}"/>
    				</c:if>
    				<label for="${cur.sun.day}">${cur.sun.day }</label> 
    			</td>
    			<td class="l-td" ${cur.mon.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>
    				<c:if test="${not empty cur.mon}">
    					<input id="${cur.mon.day}" type="checkbox" name="days" value="${cur.mon.day}"/>
    				</c:if>
    				<label for="${cur.mon.day}">${cur.mon.day }</label> 
    			</td>
    			<td class="l-td" ${cur.tue.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>
    				<c:if test="${not empty cur.tue}">
    					<input id="${cur.tue.day}" type="checkbox" name="days" value="${cur.tue.day}"/>
    				</c:if>
    				<label for="${cur.tue.day}">${cur.tue.day }</label> 
    			</td>
    			<td class="l-td" ${cur.wed.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>
    				<c:if test="${not empty cur.wed}">
    					<input id="${cur.wed.day}" type="checkbox" name="days" value="${cur.wed.day}"/>
    				</c:if>
    				<label for="${cur.wed.day}">${cur.wed.day }</label> 
    			</td>
    			<td class="l-td" ${cur.thu.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>
    				<c:if test="${not empty cur.thu}">
    					<input id="${cur.thu.day}" type="checkbox" name="days" value="${cur.thu.day}"/>
    				</c:if>
    				<label for="${cur.thu.day}">${cur.thu.day }</label> 
    			</td>
    			<td class="l-td" ${cur.fri.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>
    				<c:if test="${not empty cur.fri}">
    					<input id="${cur.fri.day}" type="checkbox" name="days" value="${cur.fri.day}"/>
    				</c:if>
    				<label for="${cur.fri.day}">${cur.fri.day }</label> 
    			</td>
    			<td class="l-td" ${cur.sat.type=='节假日' ? 'style="color:red;font-weight:bold;"' : '' }>
    				<c:if test="${not empty cur.sat}">
    					<input id="${cur.sat.day}" type="checkbox" name="days" value="${cur.sat.day}"/>
    				</c:if>
    				<label for="${cur.sat.day}">${cur.sat.day }</label> 
    			</td>
    		</tr>
    	</c:forEach>
    	
	</tbody></table>

	</div>
	</form>
	
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm1();" class="lzui-btn lzui-corner-all">设为节假日</a>
		<a href="javascript:submitForm2();" class="lzui-btn lzui-corner-all">设为工作日</a>
	</div>
</div>
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
function submitForm1(){
	$('#typeId').val('节假日');
	document.form1.submit();
	
}

function submitForm2(){
	$('#typeId').val('工作日');
	document.form1.submit();
}

function dateSeted(){
	var y = $('#yam').val();
	//提交
	document.location.href="${ctx}/web/oa/date/mgr?yam="+encodeURIComponent(y);
}

$(function(){
	$('#OA管理').addClass('ui-tabs-current');	
	$('#日历设置').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#preMonth').click(function(){
		var y = "${day.year }年${day.month }月";
		document.location.href="${ctx}/web/oa/date/mgr?pn=pre&yam="+encodeURIComponent(y);
	});

	$('#nextMonth').click(function(){
		var y = "${day.year }年${day.month }月";
		document.location.href="${ctx}/web/oa/date/mgr?pn=next&yam="+encodeURIComponent(y);
	});
	
});
</script>