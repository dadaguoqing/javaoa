<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>	
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<!--  
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/kq/mysp">点击返回打卡补签申请审批</a>
  	</div>
  	-->
	<form name="form1" action="${ctx }/web/oa/kq/sp" method="post" onsubmit="return beforeSubmit();">
	<input type="hidden" value="${bqk.id}" name="id"/>
	<div style="border:1px solid #2191C0; margin-top:0px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }打卡补签申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				${bqk.proposerName}
			</td>	
				
			<td class="title" style="width:90px;">申请时间：</td>
			<td  style="border-right:none; ">
				${bqk.createTime }
			</td>	
			<td class="title">补签类型</td>	
			<td  style="border-right:none; " >
				<select name="style" disabled="disabled">
					<option value="0"  <c:if test="${bqk.style=='0' }">selected="selected"</c:if>>请选择补签类型</option>
					<option value="1" <c:if test="${bqk.style=='1' }">selected="selected"</c:if>>迟到打卡补签</option>
					<option value="2" <c:if test="${bqk.style=='2' }">selected="selected"</c:if>>忘记打卡补签</option>
					<option value="3" <c:if test="${bqk.style=='3' }">selected="selected"</c:if>>公事出差打卡补签</option>
					<option value="4" <c:if test="${bqk.style=='4' }">selected="selected"</c:if>>工牌丢失打卡补签</option>
					<option value="5" <c:if test="${bqk.style=='5' }">selected="selected"</c:if>>先研院授课打卡补签</option>
					<option value="6" <c:if test="${bqk.style=='6' }">selected="selected"</c:if>>公事外出打卡补签</option>
					<option value="7" <c:if test="${bqk.style=='7' }">selected="selected"</c:if>>其他原因</option>
				</select>
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		考勤详情</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:120px;">打卡补签日期：</td>	
			<td style="width:160px;">
				${bqk.dayStr}
			</td>	
			<td class="title" style="border-left:none; width:120px;">上班打卡时间：</td>	
			<td style="width:160px;">
				${bqk.checkin}
			</td>	
			<td class="title" style="border-left:none; width:120px;">下班打卡时间：</td>	
			<td style="width:160px;">
				${bqk.checkout }
			</td>
			<td  style="border-right:none; ">
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		补打卡事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled="disabled" name="content" style="width:50%; height:120px; padding:5px 10px;">${bqk.content }</textarea>
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批意见</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			
			<td style="width:320px;">
				审批意见：
				<select id="tg" name="sp" onchange="yjChanged()" >
					<option value="审批通过" style="color:green;" selected="selected">通过</option>
					<option value="不通过" style="color:red;">不通过</option>
				</select>
			</td>
			
			<td style="border-right:none;">
				补充意见：<input name="yj" id="yj" style="width:180px;" value="ok"/> <span style="color:red;">不超过50个字符</span>
			</td>
		</tr>
	</tbody></table>
	
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交审批结果</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('a#OA审批').addClass('ui-tabs-current');	
	$('#打卡补签审批').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function yjChanged(){
	$('#yj').val("");
}

function beforeSubmit(){
	var tg = $('#tg').val();

	if(tg == '0'){
		alert('请选择审批意见');
		return false;
	}

	var yj = $('#yj').val();
	yj = $.trim(yj);
	if(!yj){
		alert('请输入补充意见');
		return false;
	}
		
	return true;
}

function submitForm(){

	var flag = beforeSubmit();
	if(flag){
		document.form1.submit();
	}
}
</script>