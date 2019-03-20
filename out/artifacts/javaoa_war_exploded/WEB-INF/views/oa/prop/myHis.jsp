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
	
	<%-- 
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
			<td class="left" style="margin-left:30px; color:#C0392B; font-weight:bold;">您目前账户余额：${balance.balance }元</td>
		</tr>
	</table>
	</form>
	--%>
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">我的资产记录</div>
	</div>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/prop/mine">点击返回我的资产</a>
  	</div>
  	
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:8px; border-bottom:none;">
	<table class="lzzUIGrid"><tbody>
		
		<tr>
			<td class="l-td-header" style="width:110px;">发生日期</td>
			<td class="l-td-header l-td-left" style="width:60px;">类型</td>
			<td class="l-td-header l-td-left" style="width:120px;">资产编号</td>
			<td class="l-td-header l-td-left" style="width:100px;">资产名称</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:160px;">规格型号</td>
			
		</tr>
    </tbody></table>
    </div>
    
    <div class="gridContainer" id="ctable" style="width:100%; height:400px; margin-left:0px; border-top:none;">
    <table class="lzzUIGrid"><tbody>
    	<c:forEach items="${list }" var="cur">	
    	
    	<c:if test="${cur.type == '领用'}">
    		<tr style="color:red;">
    	</c:if>
    	<c:if test="${cur.type == '归还'}">
    		<tr style="">
    	</c:if>
    		<td class="l-td" style="width:110px;">${cur.dayStr}</td>
    		<td class="l-td l-td-left" style="width:60px;">${cur.type }</td>
    		<td class="l-td l-td-left" style="width:120px;">${cur.propCode}</td>
    		<td class="l-td l-td-left" style="width:100px;">${cur.propName}</td>
    		<td class="l-td l-td-left" style="width:160px;">${cur.propSpec}</td>
			
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
	$('#我的资产').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	var dh = $(document).height();
	$('#ctable').css('height',(dh-219)+'px');
	
});
var index;

function showDetail(dayStr){
	clearInterval(itv);
	$.layer({
		shade : [0.7, '#000'],
	    fix: false,
	    title: dayStr+'订餐详情',
	    type: 2,
	    border: [3],
	    shadeClose: true,
	    iframe: {src : '${ctx}/web/oa/dc/showDetail?dayStr='+dayStr},
	    area: ['700px', '400px'],
	    close: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    }
	});
}

function sbmitSearch(){
	document.sForm.submit();
}

</script>
</html>