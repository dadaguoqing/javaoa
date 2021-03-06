<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
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
		<%@ include file="../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		<form id="form1" name="form1" method="post">
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;" href="${ctx}/web/oa/seal/addSeal">新增印章信息</a>
			</div>

			<div class="gridContainer"
				style="width: 99%; margin-left: 0px; margin-top: 15px;">
				<table class="lzzUIGrid">
					<tbody>
						<tr>
							<td class="l-td-toolbar" colspan="8">
								<div class="l-float-left"
									style="font-weight: bold; font-size: 15px; margin-right: 20px;">
									印章信息列表</div>
							</td>
						</tr>

						<tr>
							<td class="l-td-header l-td-center" style="width: 40px;">序号</td>
							<td class="l-td-header" style="width: 300px;">公司名称</td>
							<td class="l-td-header" style="width: 150px;">印章名称</td>
							<td class="l-td-header" style="width: 50px;">单位</td>
							<td class="l-td-header" style="width: 50px;">数量</td>
							<td class="l-td-header l-td-last-right" style="width: 200px;">操作</td>
						</tr>

						<c:forEach items="${list}" var="seal" varStatus="status">
							<tr class="data"> 
								<td class="l-td">${status.count}</td>
								<td class="l-td l-td-left">${seal.sealCompany }</td>
								<td class="l-td l-td-left">${seal.sealName}</td>
								<td class="l-td">${seal.sealUnit}</td>
								<td class="l-td">${seal.sealNum}</td>
								<td class="l-td"><a
									href="${ctx}/web/oa/seal/updateSeal?id=${seal.id}"
									class="lzui-btn lzui-corner-all">修改</a> <a
									onclick="del(${seal.id})" class="lzui-btn lzui-corner-all">删除</a>
								</td>
							</tr>
						</c:forEach>

						<c:if test="${empty list}">
							<tr>
								<td class="l-td-last-right" colspan="8" style="color: red;">
									对不起，目前还没有相关数据。</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</div>
<style>
.data td {
	padding: 2px;
}
</style>
<script>

	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#印章信息管理').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function del(id) {
		layer.open({
			content : '确认删除此条印章信息吗',
			btn : ['确认','取消'],
			yes : function(){
				form1.action = "${ctx}/web/oa/seal/delSeal?id="+id;
				document.form1.submit();
			}
		});
	}
</script>
</body>
</html>