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
<link rel="stylesheet" href="${ctx }/resources/css/comm.css" />
<link rel="stylesheet" href="${ctx }/resources/css/index.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/ui.all.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/lzui.all.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/lzui.czexam.css" />

<script type="text/javascript" src="${ctx }/resources/js/jquery-1.10.1.min.js"></script>
<script>
$(function(){
	$('.ui-tabs-anchor').click(
		function(){
			var url = $(this).attr('clickUrl');
			$('.ui-tabs-anchor').attr('class','ui-tabs-anchor')
			$(this).attr('class','ui-tabs-anchor ui-tabs-current')
			$('#ifm').attr('src',url);
		}
	);
	
	
});
</script>
</head>

<body scroll="no" style="background-color:#efe;">

<div class="ui-widget-header" style="height:56px;position:relative;">
	<div style="font-size:18px;font-weight:normal;line-height:28px;width:400px;float:left;">
		<img src="${ctx }/resources/include/img/login_title.png" style="margin-top:4px;"/>
	</div>
	
	<div style="float:left;height:56px;">
		<ul class="lzui-cz-menu ui-tabs-nav ui-helper-reset ui-helper-clearfix" style="height:56px;">
			
				<li class="" style="height:56px; border:none;line-height:56px;cursor:pointer;float:left;margin-left:1px;">
					<a href="javascript:void(0);" 
						class="ui-tabs-anchor" style="padding:0px 8px;display:inline-block;height:56px;line-height:56px;"
						clickUrl="http://10.50.32.18:80/jgsw/main.do?method=initData">
						<img src="${ctx }/resources/images/home_1.png"/>首页
					</a>
				</li>
				
				<li class="" style="height:56px; border:none;line-height:56px;cursor:pointer;float:left;margin-left:1px;">
					<a href="javascript:void(0);" 
						class="ui-tabs-anchor" style="padding:0px 8px;display:inline-block;height:56px;line-height:56px;"
						clickUrl="http://10.50.32.18:80/jgsw/user.do?method=toSetPwd">
						<img src="${ctx }/resources/images/profile.png"/>员工管理
					</a>
				</li>
			
				<li class="" style="height:56px; border:none;line-height:56px;cursor:pointer;float:left;margin-left:1px;">
					<a href="javascript:void(0);" 
						class="ui-tabs-anchor" style="padding:0px 8px;display:inline-block;height:56px;line-height:56px;"
						clickUrl="http://10.50.32.18:80/jgsw/user.do?method=toSetPwd">
						<img src="${ctx }/resources/images/lock.png"/>密码修改
					</a>
				</li>
			
				<li class="" style="height:56px; border:none;line-height:56px;cursor:pointer;float:left;margin-left:1px;">
					<a href="javascript:void(0);" 
						class="ui-tabs-anchor" style="padding:0px 8px;display:inline-block;height:56px;line-height:56px;"
						clickUrl="http://10.50.32.18:80/jgsw/login.do?method=logout">
						<img src="${ctx }/resources/images/exit.png"/>退出
					</a>
				</li>
			
		</ul>
	</div>
</div>
<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="http://10.50.32.18:80/jgsw/main.do?method=initData"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;首页</div>
	<div class="navTitle2" url="http://10.50.32.18:80/jgsw/login.do?method=logout"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;退出登陆</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:64px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加菜单</div>
		<form name="form1" action="${ctx}/web/oa/menu/add" method="post">
		<div style="padding: 40px 0 20px 40px;">
			<div style="">
			<span style="font-size:13px">菜单名称</span> <input name="name" style="width:250px; border-style: outset ; padding:4px 5px; margin-left:30px;"/>
			<span style="color:#777; margin-left:30px;">请输入菜单的名称</span> 
			</div>
			<div style="padding:20px 0 0 0;">
			<span style="font-size:13px">菜单地址</span> <input name="url" style="width:250px; border-style: outset ; padding:4px 5px; margin-left:30px;"/>
			<span style="color:#777; margin-left:30px;">二级菜单必填</span> 
			</div>
			<div style="padding:20px 0 0 0;">
			<span style="font-size:13px">图标地址</span> <input name="icon" style="width:250px; border-style: outset ; padding:4px 5px; margin-left:30px;"/>
			<span style="color:#777; margin-left:30px;">最佳尺寸32*32px</span> 
			</div>
			<div style="padding:20px 0 0 0;">
			<span style="font-size:13px">上级菜单</span> 
			<select name="pid" style="margin-left:30px; padding:4px 5px;">
				<option value="0">-请选择-</option>
				<c:forEach items="${list}" var="cur">
				<option value="${cur.id }">${cur.name }</option>
				</c:forEach>
			</select>
			<span style="color:#777; margin-left:30px;">如果是一级菜单，不要选</span> 
			</div>
			<div style="padding:20px 0 0 0;">
			<span style="font-size:13px">菜单顺序</span> 
			<input name="seq" style="width:20px; border-style: outset ; padding:4px 5px; margin-left:30px;"/>
			<span style="color:#777; margin-left:30px;">请输入正整数</span> 
			</div>
			<div style="padding:20px 0 0 0;">
				<a href="javascript:submitForm();" style="margin-left:88px; " class="lzui-btn lzui-corner-all">确认提交</a>
			</div>
		</div>
		</form>
	</div>
</div>
</div>

</body>
</html>
<script>
	function submitForm(){
		document.form1.submit();
	}
</script>