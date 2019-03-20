<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['222']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" method="post" action="${ctx}/web/oa/kq/search">
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:70px;">起止日期：</td>
			<td style="padding-left:0px;">
			从
			<input name="begin" id="begin" value="${begin}" class="Wdate" style="width:110px;" autocomplete="off" 
				onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			到
			<input name="end" id="end" value="${end}" class="Wdate" style="width:110px;" autocomplete="off" 
				onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			</td>
			
			<td class="right" style="width:70px;">打卡时间：</td>
			<td style="padding-left:0px;">
			早于
			<%@ include file="../public/beginTimeForKqSearch.jsp" %>
			<%-- 
			<input name="beginTime" id="beginTime" value="${beginTime}" class="Wdate" style="width:100px;" autocomplete="off" 
				onFocus="WdatePicker({dateFmt:'HH:mm',isShowToday:false})"/>
				--%>
			迟于
			<%@ include file="../public/endTimeForKqSearch.jsp" %>
			<%--
			<input name="endTime" id="endTime" value="${endTime}" class="Wdate" style="width:100px;" autocomplete="off" 
				onFocus="WdatePicker({dateFmt:'HH:mm',isShowToday:false})"/>
				--%>
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
	
	<div class="gridContainer" style="width:600px; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="3">
				<div class="l-float-left" style="font-weight:normal; font-size:15px; margin-right:20px;" >
				查询结果
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:33%;">序号</td>
			<td class="l-td-header " style="text-align:left; width:33%; padding-left:10px;">姓名</td>
			<td class="l-td-header l-td-last-right" style="width:33%">次数</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur" varStatus="st">
    	 	
    		<tr>
    			<td class="l-td">${st.count}</td>
    			<td class="l-td" style="text-align:left; padding-left:10px;">${cur.checkin}</td>
    			<td class="l-td l-td-last-right">${cur.checkinInt }</td>
    		</tr>	
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td l-td-last-right" colspan="3" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
			</tr>
    	</c:if>
	</tbody></table>

</div>
	</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#OA查询').addClass('ui-tabs-current');	
	$('#打卡查询').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#sBtn').click(function(){
		document.sForm.submit();
	});
});
</script>
</html>