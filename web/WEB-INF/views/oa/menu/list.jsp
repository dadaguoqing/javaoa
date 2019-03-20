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
<link rel="stylesheet" href="${ctx }/resources/css/index.css" />
<link rel="stylesheet" href="${ctx }/resources/include/css/start/ui.all.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/resources/lzzUI/css/base.css" />
<link rel="stylesheet" type="text/css" href="${ctx }/resources/lzzUI/css/grid.css" />
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
	
	<div class="gridContainer" style="width:95%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<c:if test="${empty menu}">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				一级菜单列表
				</div>
				</c:if>
				<c:if test="${not empty menu}">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				《${menu.name }》二级菜单列表
				</div>
				</c:if>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:40px;">编号</td>
			<td class="l-td-header l-td-left" style="width:160px;">菜单名称</td>
			<td class="l-td-header l-td-left">访问地址</td>
			<td class="l-td-header l-td-left">icon地址</td>
			<td class="l-td-header l-td-last-right" style="width:160px;">上级菜单</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td">${cur.seq }</td>
    			<td class="l-td l-td-left">
    			<c:if test="${empty menu}">
    			<a title="点击查看详情" href="${ctx }/web/oa/menu/list?id=${cur.id}">${cur.name }</a>
    			</c:if>
    			<c:if test="${not empty menu}">
    			${cur.name }
    			</c:if>
    			</td>
    			<td class="l-td l-td-left">${cur.url }</td>
    			<td class="l-td l-td-left">${cur.icon }</td>
    			<td class="l-td">${cur.pid }</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td-header l-td-last-right" colspan="5" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
	</tbody></table>

</div>
	</div>
</div>

</body>
</html>