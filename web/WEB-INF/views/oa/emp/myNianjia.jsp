<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['7']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
			</div>
		</c:forEach>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px; padding-bottom: 15px;">

		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<div
				style="width: 72px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">年假记录</div>

			<c:if test="${empty nianjia}">
				<div class="lzui-tooltips"
					style="padding: 10px; color: #C0392B; font-weight: bold; font-size: 15px;">
					目前系统中还没有您的年假信息，如果有疑问请联系管理员。</div>
			</c:if>

			<c:if test="${not empty msg}">
				<div class="lzui-error">${msg }</div>
			</c:if>

			<c:if test="${not empty nianjia}">
				<div class="lzui-tooltips"
					style="padding: 10px; color: #C0392B; font-weight: bold; font-size: 15px;">
					点击剩余时间查看明细。</div>

				<div class="">

					<div class="lzui-headers">
						<div class="t1" style="text-align: center">剩余年假</div>

						<div class="lzui-table-wapper" style="margin-top: 7px;">
							<table class="lzui-table" style="width: 100%;">
								<tr class="lzui-td">
									<td style="text-align: center;" t_type="nj">
										${nianjia.nianjiaStr }</td>
								</tr>

							</table>
						</div>

					</div>

					<div class="lzui-headers">
						<div class="t2" style="text-align: center">剩余病假</div>

						<div class="lzui-table-wapper" style="margin-top: 7px;">
							<table class="lzui-table" style="width: 100%;">
								<tr class="lzui-td">
									<td style="text-align: center;" t_type="bj">
										${nianjia.bingjiaStr }</td>
								</tr>
							</table>
						</div>

					</div>
					<c:if test="${not empty devMode}">
						<div style="clear: both;"></div>
						<div class="lzui-headers">
							<div class="t1" style="text-align: center">手动日结</div>

							<div class="lzui-table-wapper" style="margin-top: 7px;">
								<table class="lzui-table" style="width: 100%;">
									<tr class="lzui-td">
										<td style="text-align: center; color: red;" t_type="rjs">
											点击确认日结</td>
									</tr>

								</table>
							</div>
						</div>
						<div class="lzui-headers">
							<div class="t2" style="text-align: center">手动年假结算</div>

							<div class="lzui-table-wapper" style="margin-top: 7px;">
								<table class="lzui-table" style="width: 100%;">
									<tr class="lzui-td">
										<td style="text-align: center; color: red;" t_type="njs">
											点击确认年假结算</td>
									</tr>
								</table>
							</div>
						</div>
					</c:if>
				</div>
				<div class="lzui-table-wapper" style="margin-top: 7px;">
					<a href="javascript:addNianjia();" class="lzui-btn2"
								style="height: 30px; line-height: 30px; width: 100px; font-weight: bold; font-size: 15px;">新入职员工结算假期</a>
				</div>
				<div class="lzui-table-wapper" style="margin-top: 7px;">
					<a href="javascript:clearSeal();" class="lzui-btn2"
								style="height: 30px; line-height: 30px; width: 100px; font-weight: bold; font-size: 15px;">清理印章申请</a>
				</div>
			</c:if>
		</div>
	</div>
</div>
<script>
	function addNianjia() {
		$.post('${ctx}/web/oa/nianjia/addNianjia');
	}
	function clearSeal() {
		$.post('${ctx}/web/oa/nianjia/clearSeal');
	}

	$(function() {
		$('#个人中心').addClass('ui-tabs-current');
		$('#年假记录').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

		$(".lzui-td td").click(function() {
			$this = $(this);
			var type = $this.attr("t_type");

			if ('nj' === type) {
				document.location.href = "${ctx}/web/oa/nianjia/record";
			} else if ('bj' === type) {
				document.location.href = "${ctx}/web/oa/bingjia/record";
			} else if ('rjs' === type) {
				document.location.href = "${ctx}/web/oa/kq/rjs";
			} else if ('njs' === type) {
				document.location.href = "${ctx}/web/oa/kq/njs";
			}
		});
	});
</script>
</body>
</html>