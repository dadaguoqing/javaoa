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
				style="width: 72px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">订餐管理</div>

			<c:if test="${not empty msg}">
				<div class="lzui-error">${msg }</div>
			</c:if>

			<c:if test="${param.msg == 2}">
				<div class="lzui-error">今日订餐扣款成功</div>
			</c:if>

			<c:if test="${param.msg == -1}">
				<div class="lzui-error">成功取消订餐</div>
			</c:if>


			<div class="" style="margin: 10px 0 0 0;">
				<a href="${ctx }/web/oa/dc/adds" class="lzui-btn lzui-corner-all"
					style="padding: 8px 30px;">添加订餐</a> <a
					href="${ctx }/web/oa/dc/listProviders"
					class="lzui-btn lzui-corner-all" style="padding: 8px 30px;">供应商管理</a>

				<a href="${ctx }/web/oa/dc/addMenu" class="lzui-btn lzui-corner-all"
					style="padding: 8px 30px;">菜单管理</a> <a
					href="${ctx }/web/oa/dc/sDetail" class="lzui-btn lzui-corner-all"
					style="padding: 8px 30px;">历史查询</a> <a
					href="${ctx }/web/oa/dc/cBalance" class="lzui-btn lzui-corner-all"
					style="padding: 8px 30px;">充值/扣款</a>

				<!--  
			<a href="${ctx }/web/oa/dc/khDingcan" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">客户订餐</a>
			-->
			</div>

			<c:if test="${empty dc}">
				<div class="lzui-tooltips"
					style="padding: 10px; color: #C0392B; font-weight: bold; font-size: 15px;">
					您今天还没有发布订餐。</div>
			</c:if>

			<c:if test="${dc != null}">
				<div class="lzui-tooltips"
					style="padding: 10px; color: #C0392B; font-weight: bold; font-size: 15px;">
					<c:if test="${dc.status == 1}">今日订餐已经结束，</c:if>
					<c:if test="${dc.status == 2}">今日订餐已经扣款成功，</c:if>
					订餐详情（下表）。
				</div>

				<div class="lzui-table-wapper" style="margin-top: 0px;">
					<table class="lzui-table" style="width: 100%;">
						<tr>
							<th style="padding-left: 15px; width: 160px;">商家名称</th>
							<th style="">| 描述</th>
							<th style="width: 80px;">| 价格</th>
							<th style="width: 80px;">| 数量</th>
							<th style="width: 120px;">| 总金额</th>
							<th style="width: 50px;">| 详情</th>
						</tr>
						<c:forEach items="${list }" var="cur">
							<tr class="lzui-td ttd" id="t1">
								<td>${cur.providerName }</td>
								<td>${cur.description }</td>
								<td>${cur.price }元</td>
								<td>${cur.num }份</td>
								<td>${cur.priceAll }元</td>
								<td><a href="${ctx }/web/oa/dc/cDetail?id=${cur.id}">详情</a></td>
							</tr>
						</c:forEach>



						<tr class="lzui-td ttd selectBak">
							<td>总计</td>
							<td>&nbsp;</td>
							<td>&nbsp;</td>
							<td>${total.num }份</td>
							<td>${total.priceAll}元</td>
							<td>&nbsp;</td>
						</tr>
					</table>
				</div>

				<c:if test="${dc.status == 0}">
					<div class="lzui-table-wapper" style="margin-top: 12px;">
						<a href="javascript:endDc(${dc.id });" class="lzui-btn2"
							style="height: 30px; line-height: 30px; float: left; width: 100px; font-weight: bold; font-size: 15px;">
							结束本次订餐 </a> <a href="javascript:cancelDc(${dc.id });"
							class="lzui-btn"
							style="height: 30px; line-height: 30px; margin-left: 10px; float: left; width: 100px; font-weight: bold; font-size: 15px;">
							取消本次订餐 </a>
					</div>
				</c:if>

				<c:if test="${dc.status == 1}">
					<div class="lzui-table-wapper" style="margin-top: 12px;">
						<a href="${ctx }/web/oa/dc/kcDc?id=${dc.id }" class="lzui-btn2"
							style="height: 30px; line-height: 30px; float: left; width: 120px; font-weight: bold; font-size: 15px;">
							扣除本次订餐费用 </a> <a href="javascript:cancelDc(${dc.id });"
							class="lzui-btn"
							style="height: 30px; line-height: 30px; margin-left: 10px; float: left; width: 100px; font-weight: bold; font-size: 15px;">
							取消本次订餐 </a>
					</div>
				</c:if>


			</c:if>
		</div>
	</div>
</div>
<script>
	$(function() {
		$('#首页').addClass('ui-tabs-current');
		$('#订餐管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

	});

	function endDc(id) {

		var flag = confirm("您确定结束本次订餐？");
		if (flag) {
			document.location.href = "${ctx}/web/oa/dc/endDc?id=" + id;
		}
	}

	function cancelDc(id) {
		var flag = confirm("取消本次订餐将删除与本次订餐相关的所有数据（无法恢复），确定取消？");
		if (flag) {
			document.location.href = "${ctx}/web/oa/dc/cancelDc?dcId=" + id;
		}
	}
</script>
</body>
</html>