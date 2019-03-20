<%@ page language="java"
	import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<style>
<!--
.selectBak {
	background: #E74C3C;
	color: #eee;
	font-weight: bold;
}

.selectBak:hover {
	background: #E74C3C;
	color: #eee;
	font-weight: normal;
}

.lzui-table th {
	padding-top: 0px;
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

		<%@ include file="../../public/indexMenu.jsp"%>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">

		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<div
				style="width: 100px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">今日订餐详情</div>



			<div class="lzui-table-wapper" style="margin-top: 10px;">
				<table class="lzui-table" style="width: 100%;">
					<tr>
						<th style="padding-left: 15px; width: 50px;">姓名</th>
						<th style="width: 160px;">|商家名称</th>
						<th style="width: 200px">| 描述</th>
						<th style="width: 80px;">| 价格</th>
						<th style="width: 80px;">| 数量</th>
						<th style="width: 120px;">| 总金额</th>
						<th style=" width:50px;"> | 操作</th>
					</tr>
					<c:forEach items="${list }" var="cur">
						<tr class="" id="t1">
							<td>${cur.empName }</td>
							<td>${cur.providerName }</td>
							<td>${cur.description }</td>
							<td>${cur.price }元</td>
							<td>${cur.num }份</td>
							<td>${cur.priceAll }元</td>
							<td>
								<a href="${ctx }/web/oa/dc/cancelOrder?empDcId=${cur.empDcId}&cdId=${cur.cdId}">取消</a>
							</td>
						</tr>
					</c:forEach>
				</table>
				<a style="color: red;">自动订餐用户：</a> <a>${autoOrder}</a><br /> <a
					style="color: red;">未订餐用户：</a> <a>${order}</a>
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		$('#首页').addClass('ui-tabs-current');
		$('#订餐管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url) {
				document.location.href = url;
			}
		});
	});
</script>
</body>
</html>