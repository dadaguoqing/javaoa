<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://itek.com/role" prefix="ro" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['2']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
		</c:forEach>
		<%-- 		<jsp:include page="../public/lead.jsp"> --%>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>
		
		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/product/add">新增产品</a>
		</div>
			
		<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">产品型号：</td>
			<td>
			<input id="param1" name="productModel" value="${prod.productModel }" />
			</td>
			
			<td class="right" style="width:80px;">选择状态：</td>
			<td>
			<select id="param2" name="state">
				<option value="-1" <c:if test="${prod.state == -1 }">selected</c:if>>全部</option>
				<option value="0" <c:if test="${prod.state == 0 }">selected</c:if>>禁用</option>
				<option value="1" <c:if test="${prod.state == 1 }">selected</c:if>>启用</option>
			</select>
			</td>
			
			<td>
				<div style="padding:0px 10px;">
					<div class="buttonDiv saveBtn" id="sBtn" onclick="reList();">
						<span>搜索</span>
						<img src="${ctx}/resources/images/server_add.png" style="width:16px;height:16px;"/>
					</div>
			</div>
			</td>	
		</tr>
	</table>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="8">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								产品信息列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header l-td-left" style="width: 70px;">编号</td>
						<td class="l-td-header" style="width: 200px;">产品型号</td>
						<td class="l-td-header" style="width: 200px;">产品类型</td>
						<td class="l-td-header" style="width: 100px;">单位</td>
						<td class="l-td-header" style="width: 100px;">是否可用</td>
						<td class="l-td-header" style="width: 200px;">包装信息</td>
						<td class="l-td-header l-td-last-right" style="width: 200px;">操作</td>
					</tr>

					<c:forEach items="${products}" var="product" varStatus="status">
						<tr>
							<td class="l-td">${status.index + 1 }</td>
							<td class="l-td l-td-left">${product.productModel }</td>
							<td class="l-td">${product.productName }</td>
							<td class="l-td">${product.unit }</td>
							<td id="state${status.index + 1}" class="l-td">
								<c:if test="${product.state == 1}">启用</c:if>
								<c:if test="${product.state == 0}"><font color="red">禁用</font></c:if>
							</td>
							<td class="l-td">
								<c:if test="${empty product.remark }">- 无 -</c:if>
								<c:if test="${not empty product.remark }">${product.remark }</c:if>
							</td>
							<td class="l-td">
								<ro:role hasRole="生产运营经理">
									<a href="${ctx}/web/oa/product/update/${product.id}" class="lzui-btn lzui-corner-all" >修改</a>
									<a id="oper${status.index +1 }" href="javascript:;" class="lzui-btn lzui-corner-all" onclick="changeState(${status.index + 1 }, ${product.id });">
										<c:if test="${product.state == 1}">禁用</c:if>
										<c:if test="${product.state == 0}">启用</c:if>
									</a>
								</ro:role>
							</td>
						</tr>
					</c:forEach>

					<c:if test="${empty products}">
						<tr>
							<td class="l-td-last-right" colspan="8" style="color: red;">
								对不起，目前还没有相关数据。</td>
						</tr>
					</c:if>
					<c:if test="${not empty products}">
						<tr>
							<td class="l-td-last-right" colspan="8" style="color: red;">
								<!-- 上一页 -->
								<c:if test="${prod.curPage <= 1 }">
									<a href="list?curPage=1&pageSize=${prod.pageSize }&productModel=${prod.productModel}&state=${prod.state}" class="lzui-btn lzui-corner-all">上一页</a>
								</c:if>
								<c:if test="${prod.curPage > 1 }">
									<a href="list?curPage=${prod.curPage - 1}&pageSize=${prod.pageSize }&productModel=${prod.productModel}&state=${prod.state}" class="lzui-btn lzui-corner-all">上一页</a>
								</c:if>
								
								<c:if test="${prod.totalPage <= 5 }">
									<c:forEach var="size" step="1" begin="1" end="${prod.totalPage}">
										<c:if test="${size == prod.curPage }">
											<a href="javascript:;" style="background: white;" class="lzui-btn lzui-corner-all"><font style="color: black">${size}</font></a>
										</c:if>
										<c:if test="${size != prod.curPage }">
											<a href="list?curPage=${size}&pageSize=${prod.pageSize }&productModel=${prod.productModel}&state=${prod.state}"  class="lzui-btn lzui-corner-all">${size}</a>
										</c:if>
									</c:forEach>
								</c:if>
								<c:if test="${prod.totalPage > 5 }">
									<c:forEach var="size" step="1" begin="${(prod.curPage - 2 > 0) ? (prod.curPage - 2) : 1}" end="${(prod.curPage + 2 > prod.totalPage) ? prod.totalPage : (prod.curPage + 2)}">
										<c:if test="${size == prod.curPage }">
											<a href="javascript:;" style="background: white;" class="lzui-btn lzui-corner-all"><font style="color: black">${size}</font></a>
										</c:if>
										<c:if test="${size != prod.curPage }">
											<a href="list?curPage=${size}&pageSize=${prod.pageSize}&productModel=${prod.productModel}&state=${prod.state}"  class="lzui-btn lzui-corner-all">${size}</a>
										</c:if>
									</c:forEach>
								</c:if>
								
								<!-- 下一页 -->
								<c:if test="${prod.curPage >= prod.totalPage }">
									<a href="list?curPage=${prod.curPage}&pageSize=${prod.pageSize }&productModel=${prod.productModel}&state=${prod.state}" class="lzui-btn lzui-corner-all">下一页</a>
								</c:if>
								<c:if test="${prod.curPage < prod.totalPage }">
									<a href="list?curPage=${prod.curPage + 1}&pageSize=${prod.pageSize }&productModel=${prod.productModel}&state=${prod.state}" class="lzui-btn lzui-corner-all">下一页</a>
								</c:if>
							</td>
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
		$('#产品信息管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function reList() {
		var productModel = $('#param1').val();
		var state = $('#param2').val();
		location.href = 'list?productModel='+productModel+'&state='+state;
	}
</script>
</body>
</html>