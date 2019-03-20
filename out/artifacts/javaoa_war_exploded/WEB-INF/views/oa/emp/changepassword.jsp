<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
pageContext.setAttribute("ctx",path);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${compName }</title>
<link rel="stylesheet" href="${ctx }/resources/css/base.css" />
<link rel="stylesheet" href="${ctx }/resources/css/comm.css" />
<link rel="stylesheet" href="${ctx }/resources/css/style_new.css" />
<link rel="stylesheet" href="${ctx }/resources/css/index.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/ui.all.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/resources/lzzUI/css/base.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/resources/lzzUI/css/grid.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/lzui.all.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/lzui.czexam.css" />

<script type="text/javascript" src="${ctx }/resources/js/jquery-1.10.1.min.js"></script>
<script src="${ctx}/resources/layer/layer.min.js"></script>
<script>
</script>
</head>

<body style="background-color:#efe;">

<div class="ui-widget-header" style="height:56px;position:relative; min-width:1245px; line-height:56px; text-align:center;">
	<div style="font-size:18px;font-weight:normal;line-height:28px;width:400px;float:left;">
		<img src="${ctx }/resources/include/img/login_title.png" style="margin-top:4px;"/>
	</div>
	
	<span style="font-size:20px;">修改密码</span>
	
	<div style="font-size:18px;font-weight:normal;line-height:28px;width:400px;float:right;">
		&nbsp;
	</div>
</div>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<div class="navTitle2 cur" url="" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;修改密码</div>
	<div class="navTitle2" url="${ctx }/web/logout" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;系统登陆</div>
	
</div>

<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">修改密码</div>
	</div>
	
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  		为了您的信息安全，请立即修改密码。修改成功之后，系统将自动跳转到您的主页。
  	</div>
  				
	<form name="form1" action="${ctx }/web/oa/user/changePassword" method="post">
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
		<a href="${ctx }/web/logout" class="lzui-btn lzui-corner-all">返回</a>
	</div>
	
	</form>
	
	
</div>
</div>

</body>

<script>

$(function(){

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

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
