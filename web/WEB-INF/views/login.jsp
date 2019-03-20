<%@ page language="java" import="java.util.*,javax.servlet.http.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String ctx = request.getContextPath();
pageContext.setAttribute("ctx",ctx);

String uc = "";
String pwd = null;
String code = null;
Cookie[] cs = request.getCookies();
if(cs != null){
	int len = cs.length;
	for(int i=0; i<len; i++){
		Cookie c = cs[i];
		if(c.getName().equals("userCode")){
			uc = c.getValue();
		}else if(c.getName().equals("psw")){
			pwd = c.getValue();
		}else if(c.getName().equals("code")){
			code = c.getValue();
		}
	}
}



String cd = (String)request.getAttribute("code");
if(cd != null){
	uc = cd;
}

if(code !=null && code.length()>0 && pwd!=null && pwd.length()>0){
	response.sendRedirect(ctx + "/web/aLogin");
	return;
}
%>

<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>系统登陆</title>
	<link rel="stylesheet" href="${ctx }/resources/css/ui.all.css" />
	<link rel="stylesheet" href="${ctx }/resources/css/lzui.all.css" />
	<link rel="stylesheet" href="${ctx }/resources/css/lzui.czexam.css" />
	<style>
		.login-table{width:50%;border-collapse: collapse; border-spacing:0; margin:0; padding:0; line-height:28px; color:#666;}
		.login-table td{padding:7px 5px; text-align:left;}
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

				var uc = '<%=uc%>';
		  		if(uc){
		  			$("#password").focus();
		  		}else{
		  			$("#username").focus();
		  		}

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
					alert("密码不能为空");
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
	<form name="form1" action="${ctx }/web/login" method="post">
	<div style="width:620px;height:334px;position:absolute;left:50%;margin-left:-311px;top:120px;background-image:url('${ctx }/resources/images/login_fm.png')">
		<table class="login-table" style="width:300px;position:absolute;left:50%;margin-left:-311px;top:65px;"><tbody>
			<tr>
				<c:if test="${not empty msg}">
				<td style="padding-left:65px;font-weight:bold;color:red;" colspan="2">${msg } </td>
				</c:if>
				<c:if test="${empty msg}">
				<td style="padding-left:65px;font-weight:bold;color:#FA8406;" colspan="2">&nbsp;&nbsp;请输入账号和密码 <span style="color:red"></span></td>
				</c:if>	
			</tr>
			<tr>
				<td class="td-label"><p style="color:#024578;">账 号：</p></td>
				<td><input id="username" name="code" value="<%=uc%>" class="text" title="请在这里输入账号"/></td>
			</tr>
			<tr>
				<td class="td-label"><p style="color:#024578;">密 码：</p></td>
				<td><input id="password" name="password" type="password" class="text" title="请在这里输入密码" /></td>
			</tr>
			<tr>
				<td class="td-label" colspan="2" style="text-align:left; vertical-align:middle; width:300px;">
				<input style="margin-left:60px; vertical-align:middle;" type="checkbox" id="rm" name="rm"  value="rm"/>&nbsp;&nbsp;<label for="rm">记住密码</label>
				</td>
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
