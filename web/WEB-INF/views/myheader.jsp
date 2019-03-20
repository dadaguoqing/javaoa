<%@ page language="java" import="java.util.*,com.hj.oa.Consts" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("ctx", path);
	pageContext.setAttribute("mode", Consts.devMode);
%>
<c:set var="now" value="<%=new java.util.Date()%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>合肥宏晶微电子科技股份有限公司WE网</title>
<link rel="stylesheet" href="${ctx }/resources/css/base.css" />
<link rel="stylesheet" href="${ctx }/resources/css/comm.css" />
<link rel="stylesheet" href="${ctx }/resources/css/style_new.css" />
<link rel="stylesheet" href="${ctx }/resources/css/index.css" />
<link rel="stylesheet"
	href="${ctx }/resources/include/css/start/ui.all.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/resources/lzzUI/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/resources/lzzUI/css/grid.css" />
<link rel="stylesheet"
	href="${ctx }/resources/include/css/start/lzui.all.css" />
<link rel="stylesheet"
	href="${ctx }/resources/include/css/start/lzui.czexam.css" />
	
<link rel="stylesheet" href="${ctx }/resources/bootstrap/css/bootstrap.min.css"/>


<!-- 会影响其他页面样式 -->
<script type="text/javascript"
	src="${ctx }/resources/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/myjs/common.js"></script>
<!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
<script src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>

<script src="${ctx}/resources/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/jszip.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/xlsx.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/readExcel.js"></script>
</head>

<body
	style="background-color: #efe; min-width: 1245px; position: relative;">

	<div class="ui-widget-header"
		style="height: 56px; position: relative; min-width: 1245px;">
		<div
			style="font-size: 18px; font-weight: normal; line-height: 28px; width: 400px; float: left;">
			<img src="${ctx }/resources/include/img/login_title.png"
				style="margin-top: 4px;" />
		</div>

		<div style="float: left; height: 56px;">
			<ul
				class="lzui-cz-menu ui-tabs-nav ui-helper-reset ui-helper-clearfix"
				style="height: 56px;">
				<c:forEach items="${loginUserMenus}" var="cur">
					<li class=""
						style="height: 56px; border: none; line-height: 56px; cursor: pointer; float: left; margin-left: 1px;">
						<a href="${ctx }/${cur.url}" class="ui-tabs-anchor"
						id="${cur.name }"
						style="padding: 0px 8px; display: inline-block; height: 56px; line-height: 56px;">
							<img src="${ctx }/${cur.icon}" />${cur.name }
					</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>