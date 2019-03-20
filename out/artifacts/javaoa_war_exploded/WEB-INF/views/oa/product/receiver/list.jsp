<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../public/header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['2']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
			</div>
		</c:forEach>
		<%-- 		<jsp:include page="../public/lead.jsp"> --%>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/product/receiver/add">新增客户信息</a>
		</div>


		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="8">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								客户信息列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header l-td-left" style="width: 70px;">序号</td>
						<td class="l-td-header" style="width: 100px;">客户编号</td>
						<td class="l-td-header" style="width: 200px;">公司名称</td>
						<td class="l-td-header" style="width: 200px;">收货地址</td>
						<td class="l-td-header" style="width: 100px;">收货人</td>
						<td class="l-td-header" style="width: 100px;">电话</td>
						<td class="l-td-header l-td-last-right" style="width: 200px;">操作</td>
					</tr>

					<c:forEach items="${companys}" var="company" varStatus="status">
						<tr>
							<td class="l-td">${status.index + 1 }</td>
							<td class="l-td">${company.companyCode }</td>
							<td class="l-td l-td-left">${company.company }</td>
							<td class="l-td">${company.addr }</td>
							<td class="l-td">${company.receiver }</td>
							<td class="l-td">${company.tel }</td>
							<td class="l-td"><a
								href="${ctx}/web/oa/product/receiver/update/${company.id}"
								class="lzui-btn lzui-corner-all">修改</a></td>
						</tr>
					</c:forEach>

					<c:if test="${empty companys}">
						<tr>
							<td class="l-td-last-right" colspan="8" style="color: red;">
								对不起，目前还没有相关数据。</td>
						</tr>
					</c:if>
				</tbody>
			</table>

		</div>
	</div>
</div>
<script>
	function changeState(rowId, id) {
		var productModel = $('#state' + rowId).text().trim();
		$.get('${ctx }/web/oa/product/changeState/' + id, function(data) {
			if (data.state == 0) {
				$('.lzui-error').text('修改成功');
				if (productModel == '启用') {
					$('#state' + rowId).html('<font color="red">禁用</font>');
					$('#oper' + rowId).text('启用');
				} else if (productModel == '禁用') {
					$('#state' + rowId).text('启用');
					$('#oper' + rowId).text('禁用');
				}
			} else {
				$('.lzui-error').text(data.data);
			}
		}, 'json');
	}

	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#客户信息管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
</script>
</body>
</html>