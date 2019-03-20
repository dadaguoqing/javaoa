<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

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

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">修改密码</div>
	</div>
	<form name="form1" action="${ctx }/web/oa/user/cp" method="post">
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">修改密码</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		原始密码</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">原始密码：</td>	
			<td style="width:90px; border-right:none;" colspan="3">
				<input type="password" name="pw" id="pw"/>
			</td>	
		</tr>
		<tr>
			<td class="title" style="width:90px; border-left:none; ">新密码：</td>	
			<td style="width:90px;">
				<input type="password" name="npw" id="npw"/>
			</td>	
			<td class="title" style="width:90px;">确认密码：</td>
			<td  style="border-right:none; width:90px;">
				<input type="password" name="cnpw" id="cnpw"/>
			</td>	
		</tr>
	</tbody></table>
	
	
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交</a>
		<a href="javascript:window.history.back();" class="lzui-btn lzui-corner-all">返回</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#个人中心').addClass('ui-tabs-current');	
	$('#密码修改').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$("#cnpw").keypress(
			function(evt){
				if(evt.keyCode==13){
					submitForm();
				}
			}
		);

	var msg = '${msg}';
	if(msg != ''){
		alert(msg);
	}
});
function submitForm(){
	var cp = $('#pw').val();
	var ncp = $('#npw').val();
	var cncp = $('#cnpw').val();

	if(!cp){
		alert('请输入原始密码。');
		return;
	}

	if(!ncp){
		alert('请输入新密码。');
		return;
	}

	if(ncp != cncp){
		alert('两次输入的密码不一致。');
		return;
	}
	
	document.form1.submit();
}
</script>