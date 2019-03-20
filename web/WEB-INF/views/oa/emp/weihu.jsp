<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<style>
<!--
.shortInput {width:40px;}
.redInput {border:1px solid red;}
-->
</style>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<%@ include file="../../public/indexMenu.jsp" %>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
		<div class="" style="margin:10px 0 0 0;">
			<a href="${ctx }/web/oa/user/passwordReset" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">密码重置</a>
			
			<a href="${ctx }/web/oa/user/setNoticer" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">设置公告管理员</a>
			
			<a href="${ctx }/web/oa/kq/reset" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">修正打卡记录</a>
			
			<a href="${ctx }/web/oa/kq/export" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">考勤数据导出</a>
			
			<a href="${ctx }/web/oa/gift/evt/list" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">节日礼品活动</a>
		</div>
</div>
</div>

</body>
</html>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');
	$('#日常维护').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

</script>