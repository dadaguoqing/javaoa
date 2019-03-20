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
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/leave/mydl">点击查看代理记录</a>
  	</div>
  	
	<form name="form1" action="${ctx }/web/oa/leave/alfs" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }请假申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">请假类型：</td>	
			<td style="width:90px;">
				<select name="type" id="_type">
					<option value="0">-请选择-</option>
					<option value="事假">事假</option>
					<option value="病假">病假</option>
					<option value="婚假">婚假</option>
					<option value="产假">产假</option>
					<option value="丧假">丧假</option>
					<option value="年假">年假</option>
					<option value="调休">调休</option>
				</select>
			</td>	
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				<!--  
				<input type="hidden" name="proposer" value="${loginUser.id }"/>
				<input type="hidden" name="proposerName" value="${loginUser.name }"/>
				-->
				<select name="proposer" id="proposer">
					<option value="0">-请选择-</option>
					<c:forEach items="${users}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
					
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td>
				<span id="deptSpan"></span>
			</td>	
			<td class="title" style="width:90px;">申请时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
			%>
			<td  style="border-right:none; width:200px;">
				<input type="hidden" name="createTime" value="${now }"/>
				${now }
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		起始时间</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">开始时间：</td>	
			<td style="width:260px;">
				<%@ include file="../public/beginTime.jsp" %>
			</td>	
			<td class="title" style="width:90px;">结束时间：</td>	
			<td style="width:260px;">
				<%@ include file="../public/endTime.jsp" %>
			</td>	
			<td  style="border-right:none; ">
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		请假事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" style="width:50%; height:120px; padding:5px 10px;"></textarea>
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

function setDept(){
	
	
}

$(function(){
	$('a#OA申请').addClass('ui-tabs-current');	
	$('#代理请假申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

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
	});

	$('#cnt').keypress(function(e){ 
        if(e.ctrlKey && e.which == 13 || e.which == 10) { 
        	submitForm()
        } else if (e.shiftKey && e.which==13 || e.which == 10) { 
        	submitForm() 
        } 
	 })
});
function submitForm(){
	var type = $('#_type').val();
	var uid = $('#proposer').val();
	var bt = $('#bt').val();
	var et = $('#et').val();
	var cnt = $('#cnt').val();
	cnt = $.trim(cnt);
	$('#cnt').val(cnt);

	if('0' == type){
		alert('请选择请假类型');
		return;
	}

	if('0' == uid){
		alert('请选择申请人');
		return;
	}

	if(!bt){
		alert('请选择开始时间');
		return;
	}
	if(!et){
		alert('请选择结束时间');
		return;
	}
	if(!cnt){
		alert('请填写请假事由');
		return;
	}

	if(cnt.length > 150){
		alert('请假事由不能超过150个字符');
		return;
	}
	
	document.form1.submit();
}
</script>