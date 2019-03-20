<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<%
request.setAttribute("now", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
%>

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
  		<a style="color:#C0392B;" href="${ctx}/web/oa/leave/myLeaves">点击查看申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/leave/afl" method="post">
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
			<td class="title" style="border-left:none; width:90px;">申请人: </td>	
			<td style="width:90px;">${loginUser.name }</td>	
			<td class="title" style="width:120px;">所属部门：</td>	
			<td style="width:90px;">
				${loginUserDept.name }
			</td>	
			<td class="title" style="border-left:none; width:90px;">申请时间：</td>	
			<td>
			     ${now }
			</td>	
		</tr>
		<tr>
			<td class="title" style="width:90px;">采购事由：</td>
			<td class="" colspan="5">
			     <input type="text" width="500">
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
			<td class="title">序号</td>	
			<td class="title">资产名称</td>	
			<td class="title">资产类别</td>	
			<td class="title">型号</td>	
			<td class="title">单位</td>	
			<td class="title">使用人</td>	
			<td class="title">使用部门</td>	
			<td class="title">数量</td>	
			<td class="title">单价</td>	
			<td class="title">小计</td>	
			<td class="title">操作</td>	
		</tr>
		<tr>
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title">
			     <select>
			         <option>- 请选择 -</option>
			         <c:forEach items="${types }" var="type">
			             <option value="${type.id }">${type.name }</option>
			         </c:forEach>
			     </select>
			</td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><input style="width:50px;" type="text"></td>	
			<td class="title"><button id="add">+</button></td>	
		</tr>
		<tr>
			<td class="title" colspan="2">备注</td>	
			<td colspan="9"></td>	
		</tr>
		<tr>
			<td class="title" colspan="2">采购预算合计</td>	
			<td colspan="9"></td>	
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
				<textarea name="content" id="cnt" style="width:50%; height:120px; padding:5px 10px;">${leave.content }</textarea>
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
	$('#请假申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#_type').val("${empty leave.type ? '0': leave.type}");

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
	var bt = $('#bt').val();
	var et = $('#et').val();
	var cnt = $('#cnt').val();
	cnt = $.trim(cnt);
	$('#cnt').val(cnt);

	if('0' == type){
		alert('请选择请假类型');
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