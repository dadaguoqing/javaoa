<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['4']}" var="cur">
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
  		<a style="color:#C0392B;" href="${ctx}/web/oa/jiaban/mysq">点击查看申请记录</a>
  	</div>
  	-->
	<form name="form1" action="${ctx }/web/oa/jiaban/sq" method="post">
	<div style="border:1px solid #2191C0; margin-top:0px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }加班申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<!--  
			<td class="title" style="border-left:none; width:90px;">加班类型：</td>	
			<td style="width:90px;">
				<select name="type" id="_type">
					<option value="-1">-请选择-</option>
					<option value="0">工作日</option>
					<option value="0">公休日</option>
					<option value="1">法定假日</option>
				</select>
			</td>	
			-->
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				<%-- 
				${loginUser.name }
				--%>
				<select name="proposer" id="proposer">
					<option value="0">-请选择-</option>
					<c:forEach items="${users}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td style="width:90px;">
				<span id="deptSpan"></span>
				<!--  
				<input type="hidden" name="deptId" value="${loginUser.deptId }"/>
				<span id="deptSpan">${loginUserDept.name }</span>
				-->
			</td>	
			<td class="title" style="width:90px;">申请时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
			%>
			<td  style="border-right:none;">
				<input type="hidden" name="createTime" value="${now }"/>
				${now }
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		加班时间</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">加班日期：</td>	
			<td style="width:160px;">                                                                       <!-- ,minDate:'%y-%M-{%d-3}',maxDate:'%y-%M-{%d}' -->
				<input name="dayte" id="dt" value="${jiaban.dayte }" class="Wdate" readonly="readonly" autocomplete="off" onFocus="WdatePicker({ onpicked:dchange, dateFmt:'yyyy年MM月dd日'})"/>
			</td>	
			<td class="title" style="width:90px;">加班时长：</td>	
			<td style="width:100px;">
				<select name="hours" id="hs">
				<% 
					for(int i=2; i<=24; i++){
				%>
				<option value="<%=i%>"><%=i%></option>
				<% 
					}
				%>
				</select>小时
			</td>	
			<td  style="border-right:none;" id="checkout">
				
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		加班事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" style="width:50%; height:120px; padding:5px 10px;">${jiaban.content }</textarea>
				<span style="color:red;">不超过150个字符，ctrl+Enter提交</span>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('a#OA申请').addClass('ui-tabs-current');	
	$('#加班申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#hs').val("${ jiaban.hours == 0 ? '2' : jiaban.hours}");

	$('#proposer').change(function(){
		var uid = $(this).val();
		if(uid && uid != 0){
			$.get('${ctx}/web/oa/user/ajaxDept?uid='+uid,function(data){
				//var d = eval(data);
				$('#deptSpan').html(data);
			});
		}else{
			$('#deptSpan').html('');
		}

		var dt = $('#dt').val();
		if(dt && '0' != uid){
			ajaxGetCheckout(dt,uid);
		}
	});

	$('#cnt').keypress(function(e){ 
        if(e.ctrlKey && e.which == 13 || e.which == 10) { 
        	submitForm()
        } else if (e.shiftKey && e.which==13 || e.which == 10) { 
        	submitForm() 
        } 
	 })
});

function dchange(){
	var str = $(this).val();
	var empId = $('#proposer').val();
	if(str && '0' != empId){
		ajaxGetCheckout(str,empId);
	}
}

function ajaxGetCheckout(dayStr, empId){
	var url = "${ctx}/web/oa/kq/day?t="+Math.random();
	$.post(url, {dayStr:dayStr, empId:empId}, function(data){
	
		data = eval(data);
		
		if(data.ret == '1'){
			$('#checkout').html("下班打卡时间：" + data.checkout);
		}else{
			$('#checkout').html("系统中没有该用户当前日期的打卡记录。");
		}
	});
}

function submitForm(){
	//var type = $('#_type').val();
	var dt = $('#dt').val();
	var hs = $('#hs').val();
	var cnt = $('#cnt').val();
	var uid = $('#proposer').val();
	
	cnt = $.trim(cnt);
	$('#cnt').val(cnt);

	if('0' == uid){
		alert('请选择申请人');
		return;
	}

	if(!dt){
		alert('请选择加班日期');
		return;
	}
	if(!hs){
		alert('请选择加班时长');
		return;
	}
	if(!cnt){
		alert('请填写加班事由');
		return;
	}

	if(cnt.length > 150){
		alert('加班事由不能超过150个字符');
		return;
	}
	
	document.form1.submit();
}
</script>