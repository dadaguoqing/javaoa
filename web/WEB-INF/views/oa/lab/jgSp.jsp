<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<style>
<!--
.shortInput {
	width: 40px;
}

.redInput {
	border: 1px solid red;
}
-->
</style>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<%@ include file="../../public/lead.jsp"%>
<%-- 		<c:forEach items="${loginUserMenuMap['5']}" var="cur"> --%>
<%-- 			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"> --%>
<%-- 				<img src="${ctx }/resources/images/item.png" --%>
<%-- 					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name } --%>
<!-- 			</div> -->
<%-- 		</c:forEach> --%>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>
		<div class="" style="margin: 10px 0 0 0;">
			<a href="${ctx }/web/oa/lab/myPcbSp2"
				class="lzui-btn lzui-corner-all" style="padding: 8px 30px;">外协加工审批</a>

			<%-- 			<a href="${ctx }/web/oa/lab/myPcbSp" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">PCB加工审批</a> --%>
			<%-- 			<a href="${ctx }/web/oa/lab/myGwSp" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">钢网加工审批</a> --%>

		</div>
	</div>
</div>

</body>
</html>
<script>
	$(function() {
		$('a#OA审批').addClass('ui-tabs-current');
		$('#加工审批').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

	});
</script>