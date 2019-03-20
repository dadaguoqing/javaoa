<%@ page language="java" import="java.util.*,javax.servlet.http.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String ctx = request.getContextPath();
pageContext.setAttribute("ctx",ctx);
%>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>外网访问验证</title>
	<link rel="stylesheet" href="${ctx }/resources/css/ui.all.css" />
	<link rel="stylesheet" href="${ctx }/resources/css/lzui.all.css" />
	<link rel="stylesheet" href="${ctx }/resources/css/lzui.czexam.css" />
	<style>
		.login-table{width:50%;border-collapse: collapse; border-spacing:0; margin:0; padding:0; line-height:28px; color:#666;}
		.login-table td{padding:10px 5px; text-align:left;}
		.login-table .td-label{text-align:right;width:100px;}
		.login-table td input{  }
		.login-table td select{ border:1px solid #ccc; }
		.login-table td input.text{height:26px; line-height:26px; padding:0px 5px; border:1px solid #ccc; width:160px;}
		.login-table td input.large{height:26px; line-height:26px; border:1px solid #ccc; width:300px;}
	</style>
	<script src="${ctx }/resources/js/jquery-1.10.1.min.js"></script>
	<script>
			function istop(){
				if(window.top != window.self){
					window.top.location.href = '${ctx }/web/login';
				}
			}
			istop();
			
			$(function(){
				$("#password").keypress(
					function(evt){
						if(evt.keyCode==13){
							userLogin();
						}
					}
				);

				$("#username").focus();
				

			});
			
			function userLogin(){
				var username = $('#username').val();
				var password = $('#password').val();
				if(username==''){
					alert("用户名不能为空");
					$('#username').focus();
					return;
				}
				if(password==''){
					alert("访问码不能为空");
					$('#password').focus();
					return;
				}
				document.form1.submit();
			}
	</script>
</head>
<body style="background:url('${ctx }/resources/images/login_bg.png') repeat;">
<div id="body">
	<div style="width:690px;position:absolute;left:50%;margin-left:-311px; top:70px;">
		<img src="${ctx }/resources/images/login_title.png" style="float:left;margin-left:10px; height:40px;">
	</div>
	<form name="form1" action="${ctx }/web/loginOutside" method="post">
	<div style="width:620px;height:334px;position:absolute;left:50%;margin-left:-311px;top:120px;background-image:url('${ctx }/resources/images/login_fm2.png')">
		<div style="color:#fff; font-size:14px; font-weight:normal; line-height:45px; margin-top:10px; padding-left:30px;">
		系统检测到您当前正通过公司外部网络访问本系统。
		</div>
		<table class="login-table" style="width:300px;position:absolute;left:50%;margin-left:-311px;top:65px;"><tbody>
			<tr>
				<c:if test="${not empty msg}">
				<td style="padding-left:65px;font-weight:bold;color:red;" colspan="2">${msg } </td>
				</c:if>
				<c:if test="${empty msg}">
				<td style="padding-left:65px;font-weight:bold;color:red;" colspan="2">&nbsp;&nbsp;请输入您的外网访问码 <span style="color:red"></span></td>
				</c:if>	
			</tr>
			<tr>
				<td class="td-label"><p style="color:#024578;">账&nbsp;&nbsp;&nbsp;号：</p></td>
				<td><input id="username" name="code" class="text" title="请在这里输入账号"/></td>
			</tr>
			<tr>
				<td class="td-label"><p style="color:#024578;">访问码：</p></td>
				<td><input id="password" name="aceessCode" type="password" class="text" title="请在这里输入访问码" /></td>
			</tr>
		</tbody></table>
		<table class="login-table" style="width:300px;position:absolute;left:50%;margin-left:-11px;top:65px;"><tbody>
			<tr>
				<td style="padding-left:65px" colspan="2" ><p>如果您有任何疑问，请联系技术支持：</p><p style="font-weight:bold;">${compName } IT-朱明。</p></td>
			</tr>
		</tbody></table>
	</div>
	<div style="width:620px;position:absolute;left:60%;margin-left:-311px; top:350px;color:white;">
		<img src="${ctx }/resources/images/login_btn.png" style="float:left;margin-right:7px;" onclick="javascript:userLogin();">
	</div>
	</form>
	
	<div style="width:690px;position:absolute;left:50%;margin-left:-345px; top:450px;color:white;">
		<p style="padding:10px 0px; text-align:center;">
			技术支持：朱明 | 邮箱：ming.zhu@macrosilicon.com
		</p>
		<p style="padding:0px 0px; text-align:center;">版权所有 © 2014 ${compName } 保留所有权利</p>
	</div>
</div>
</body>
</html>
