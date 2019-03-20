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
	<!--  
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
	-->
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">我的固定资产</div>
	</div>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/prop/myHis">点击查看历史记录</a>
  	</div>
  	
	<form action="${ctx }/web/oa/prop/goGh" method="post" name="form1" >
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:8px; border-bottom:none;">
	
		<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-header l-td-left" style="width:30px;">
			<c:if test="${ghStatus==0 }">
			<input type="checkbox" name="selectAll" class="cbs" id="selectAll" />
			</c:if>
			</td>
			<td class="l-td-header" style="width:120px;">领用日期</td>
			<td class="l-td-header" style="width:160px;">资产编号</td>
			<td class="l-td-header l-td-left" style="width:120px;">资产名称</td>
			<td class="l-td-header l-td-left" style="width:160px;">规格型号</td>
			<td class="l-td-header l-td-left" style="width:100px;">放置地点</td>
			<td class="l-td-header l-td-left" style="">备注</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:80px;">状态</td>
			
		</tr>
    </tbody></table>
    </div>
    <div class="gridContainer" id="ctable" style="width:100%; height:400px; margin-left:0px; border-top:none;">
    <table class="lzzUIGrid"><tbody>
    	<c:forEach items="${list }" var="cur">	
    	
    		<tr>
    		<td class="l-td l-td-left"style="width:30px;">
    		<c:if test="${cur.ghStatus == 0}">
    		<input type="checkbox" name="ids"  id="ids" value="${cur.id}" class="cbs"/>
    		</c:if>
    		</td>
    		<td class="l-td " style="width:120px;">${cur.dayStr }</td>
    		<td class="l-td " style="width:160px;">${cur.code}</td>
    		<td class="l-td l-td-left" style="width:120px;">${cur.name}</td>
    		<td class="l-td l-td-left" style="width:160px;">
				${cur.spec}
			</td>
			<td class="l-td" style="width:100px;">
				${cur.placeName}
			</td>
			<td class="l-td" style="">${empty cur.bz ? '无' : cur.bz}</td>
    		<td class="l-td" style="width:80px;">
    		<c:if test="${cur.ghStatus == 0}">
    			未归还
    		</c:if>
    		<c:if test="${cur.ghStatus == 1}">
    			正在归还
    		</c:if>
    		
    		</td>
			
    	</tr>
    	</c:forEach>
    	
	</tbody></table>
		</div>
		</form>
		<c:if test="${ghStatus==0 }">
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitGh();" class="lzui-btn2" 
			style="height:30px; line-height:30px; float:left; width:100px;font-weight:bold; font-size:15px;">
    			确认归还
		</a>
		</div>	
		</c:if>
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
	$("#selectAll").click(function(){
		//版本太老的话用attr只能实现一次，用prop比较稳定
		$("[name=ids]:checkbox").prop("checked",this.checked);
		});
		$("[name=ids]:checkbox").click(function(){
		var flag=true;
		$("[name=ids]:checkbox").each(function(){
		if(!this.checked){
		flag=false;
		}
		});
		$("#selectAll").prop("checked",flag);
		});
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

function submitGh(){
	var cbs = $('.cbs:checked');
	
	if(cbs.length<1){
		alert('请至少选择一项数据');
		return;
	}
	var flag = confirm("您确定归还选中资产？");
	if(flag){
		document.form1.submit();
	}
}

function sbmitSearch(){
	document.sForm.submit();
}

</script>
</html>