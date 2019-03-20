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
	<form name="sForm" method="post" action="${ctx}/web/oa/kq/mine">
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">考勤月份：</td>
			<td>
			<input name="yf" id="yf" value="${yf}" class="Wdate" style="width:100px;" autocomplete="off" 
				onFocus="WdatePicker({onpicked:sbmitSearch,dateFmt:'yyyy年MM月'})"/>
			</td>
			
			<td>
				<select onchange="showAll(this)" style="border: 1px solid #AECAF0; height:24px; line-height: 22px;">
					<option value="all">查看全部</option>
					<option value="workDay">只看工作日</option>
				</select>
				<!--  
				<div style="padding:0px 10px;">
					<div class="buttonDiv saveBtn" id="sBtn">
						<span>搜索</span>
						<img src="${ctx}/resources/images/server_add.png" style="width:16px;height:16px;"/>
					</div>
			</div>
			-->
			</td>	
		</tr>
	</table>
	</form>
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:8px; border-bottom:none;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				${yf}考勤数据
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:200px;">日期</td>
			<td class="l-td-header" style="width:110px;">上班打卡</td>
			<td class="l-td-header" style="width:110px;">下班打卡</td>
			<td class="l-td-header l-td-left" style="width:130px;">请假时长</td>
			<!--  
			<td class="l-td-header l-td-left" style="width:130px;">外出时长</td>
			<td class="l-td-header l-td-left" style="width:170px;">加班</td>
			-->
			<td class="l-td-header l-td-left l-td-last-right" style="width:140px">不正常打卡</td>
		</tr>
    </tbody></table>
    </div>
    
    <div class="gridContainer" id="ctable" style="width:100%; height:400px; margin-left:0px; border-top:none;">
    <table class="lzzUIGrid"><tbody>	
    	<c:forEach items="${list}" var="cur">
    	 	
    	 	<c:choose>
    	 		<c:when test="${cur.type=='节假日'}">
    	 			<tr style='color:green;' class="hlds">
    	 		</c:when>
    	 		<c:when test="${cur.uncLen > 0}">
    	 			<tr style='color:red;'>
    	 		</c:when>
    	 		<c:otherwise>
    	 			<tr style=''>
    	 		</c:otherwise>
    	 	</c:choose>
    		
    			<td class="l-td" style="width:200px;">
    			${cur.dayStr }（${cur.type}）
    			<%-- 
    			<c:if test="${cur.type=='节假日'}">
    			（${cur.type}）
    			</c:if>
    			--%>
    			</td>
    			<td class="l-td" style="width:110px;">${ empty cur.checkin ? '未打卡' : cur.checkin}</td>
    			<td class="l-td" style="width:110px;">${ empty cur.checkout ? '未打卡' : cur.checkout }</td>
    			<td class="l-td l-td-left" style="width:130px;">${cur.timeLen}分钟</td>
    			
    			<!--<td class="l-td l-td-left" style="width:130px;">${cur.wcLen}分钟</td>
    			
    			<td class="l-td l-td-left" style="width:170px;">
    				${ cur.jiaban  }分钟
				</td>
				-->
				<td class="l-td l-td-left l-td-last-right" style="width:140px;">
					${cur.uncLen }分钟
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td l-td-last-right" colspan="5" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
    	
    	<tr style="color:red;">
    			<td class="l-td">总计:</td>
    			<td class="l-td">&nbsp;</td>
    			<td class="l-td">&nbsp;</td>
    			<td class="l-td l-td-left">${total.timeLenStr }</td>
    			<!--  
    			<td class="l-td l-td-left">${total.wcLenStr }</td>
    			<td class="l-td l-td-left">
    				${ total.jiabanStr }
				</td>-->
				<td class="l-td l-td-left l-td-last-right" style="">
					${total.uncLenStr }
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
	$('#个人中心').addClass('ui-tabs-current');	
	$('#我的考勤').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	/*
	$('#sBtn').click(function(){
		document.sForm.submit();
	});
	*/
	//alert($(document).height());
	var dh = $(document).height();
	$('#ctable').css('height',(dh-219)+'px');
	
});

function sbmitSearch(){
	document.sForm.submit();
}

function showAll(_this){
	var v = $(_this).val();
//	alert(v);
	if('all' == v){
		$('.hlds').show();
	}else{
		$('.hlds').hide();
	}
	
	
}
</script>
</html>