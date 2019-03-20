<%@ page language="java" import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<!-- between this style is where zeroth cat's code lays it needs to be improved-->
<style>
#zuyi_div{
	position: absolute;
	top: 110px;
	right: 0;
	width: 480px;
	border: 1px solid #73AD21

}

</style>

<!-- zeroth cat's code ends for now -->

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
	
		<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
		<form name="sForm" method="post" action="${ctx}/web/oa/dc/sDetail">
		<table id="baseInfoTable">
			<tr>
				<td class="right" style="width:80px;">选择日期：</td>
				<td>
				<input name="yf" id="yf" value="${yf}" class="Wdate" style="width:115px;" autocomplete="off" 
					onFocus="WdatePicker({onpicked:sbmitSearch,dateFmt:'yyyy年MM月dd日'})"/>
				</td>
				
			</tr>
		</table>
		</form>
		
		
		
		<div class="lzui-table-wapper" style="margin-top:12px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:60px;">姓名</th>
	  				<th style=""> | 商家名称</th>
	  				<th style=""> | 描述</th>
	  				<th style=" width:80px;"> | 价格</th>
	  				<th style=" width:80px;"> | 数量</th>
	  				<th style=" width:120px;"> | 总金额</th>
	  			</tr>
	  			<c:if test="${empty list }">
	  				<td colspan="6" style="text-align:center; color:red;">系统中没有当日的订餐数据</td>
	  			</c:if>
	  			<c:if test="${not empty list }">
	  			
	  			<!-- prints out all the dollar values, zeroth_cat -->
	  			<c:forEach items="${list }" var="cur">
	  			
		  		<tr class="lzui-td ttd" >
		  			<td>${cur.empName}</td>
			    	<td>${cur.providerName }</td>
			    	<td>
			    	${cur.description }
			    	</td>
			    	<td>${cur.price }元</td>
			    	<td>${cur.num }份</td>
			    	<td>
						${cur.priceAll }元
					</td>
			    </tr>
			   
			    </c:forEach>
			    
			    
			     <tr class="lzui-td ttd selectBak">
			    	<td>总计</td>
			    	<td>
			    	&nbsp;
			    	</td>
			    	<td>&nbsp;</td>
					<td>
						&nbsp;
					</td>
					<td>${total.num }份</td>
			    	<td>
						${total.priceAll}元
					</td>
			    </tr>
			    </c:if>
			    </table>
			</div>
			
			<c:if test="${dc.status == 1}">
			<div class="lzui-table-wapper" style="margin-top:12px; height:60px;">
			 	<a href="${ctx }/web/oa/dc/kcDc?id=${dc.id }" class="lzui-btn2" 
			 	style="height:30px; line-height:30px; float:left; width:120px;font-weight:bold; font-size:15px;">
			 	扣除本次订餐费用
			 	</a>
			 	
			 	<a href="javascript:cancelDc(${dc.id });" class="lzui-btn" 
			 	style="height:30px; line-height:30px; margin-left:10px; float:left; width:100px;font-weight:bold; font-size:15px;">
			 	取消本次订餐
				</a>
			</div>
			</c:if>
			
	</div>
</div>


<!-- my div where zeroth_cat start -->

<div id="zuyi_div">
	<tr>
		<td class="right" style="width:80px;">选择日期：</td>
		<td>
		<input name="another" id="another" class="Wdate" style="width:115px;" autocomplete="off" 
			onFocus="WdatePicker({onpicked:first_date,dateFmt:'yyyy年MM月dd日'})"/>
		</td>
		<td class="right" style="width:80px;">选择日期：</td>
		<td>
		<input name="s_date" id="s_date" class="Wdate" style="width:115px;" autocomplete="off" 
			onFocus="WdatePicker({onpicked:second_date,dateFmt:'yyyy年MM月dd日'})"/>
		</td>
				
	</tr>
	
	<button id="excel_button" onclick="excel_func()">导表</button>
	
</div>
<!-- this is where zeroth cat ends for the div -->

<script>
//this is where zeroth cat's code starts

//first date from where to calculate the total amount
var first_date;

//second date from where to calculate the total amount 
var second_date;

//zuyi's function
function second_date(){
	second_date = document.getElementById("s_date");
}

//zuyi's function
function first_date(){
	first_date = document.getElementById("another");
}

//zuyi's function
function test_func(){
	var replacement = "${ctx }/web/oa/dc/newpath/" + first_date.value +"/" + second_date.value;
	window.location.replace(replacement);
}

//zuyi's function for excel
function excel_func(){
	var replacement = "${ctx }/web/oa/dc/din_can_record/" + first_date.value +"/" + second_date.value;
	window.location.replace(replacement);
}

//this is where zeroth cat's code ends

$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#订餐管理').addClass('cur');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

function sbmitSearch(){
	document.sForm.submit();
}

function cancelDc(id){
	var flag = confirm("取消本次订餐将删除与本次订餐相关的所有数据（无法恢复），确定取消？");
	if(flag){
		document.location.href = "${ctx}/web/oa/dc/cancelDc?dcId="+id;
	}
}
</script>
</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>