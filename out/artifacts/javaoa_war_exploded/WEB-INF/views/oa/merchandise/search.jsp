<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>

		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['222']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
			</div>
		</c:forEach>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px; padding-bottom: 20px;">
		<%@ include file="../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>
		<form name="sForm" id="sForm" action="${ctx}/web/oa/prop/mgr"
			method="post">
			<input type="hidden" value="${empId }" name="empId" id="uid" /> <input
				type="hidden" value="${uname }" name="uname" id="uname" />
			<table id="baseInfoTable">
				<tr>
					<td style="border-right: none;"></td>
					<td><input type="text" name="materiaCode" id="materiaCode"
						placeholder="物料编码" class="form-controller"></td>
					<td><input type="text" name="spec" id="spec"
						placeholder="规格型号" class="form-controller"></td>
					<td><input type="text" name="all" id="all" placeholder="全部条件"
						class="form-controller"></td>
					<td><a onclick="queryIn(1)" class="lzui-btn lzui-corner-all"
						style="padding: 1px 15px; margin: 0 0 10px 0">查询</a></td>
				</tr>
			</table>
		</form>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 0px;overflow: auto;max-height: 400px">
			<table class="lzzUIGrid" >
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="20">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								物料管理</div>
						</td>
					</tr>
			</table>
			<table class="table1" id='cdTable'>
				<tbody>
					<tr>
						<td class="l-td-header " style="width: 5%;">序号</td>
						<td class="l-td-header " style="width: 9%;">物料编码</td>
						<td class="l-td-header tooLong" style="width: 9%;">规格型号</td>
						<td class="l-td-header tooLong" style="width: 9%;">品名分类</td>
						<td class="l-td-header tooLong" style="width: 9%;">品牌</td>
						<td class="l-td-header tooLong" style="width: 9%;">封装</td>
						<td class="l-td-header " style="width: 7%;">仓库</td>
						<td class="l-td-header " style="width: 7%">数量</td>
						<td class="l-td-header " style="width: 10%">操作</td>
					</tr>
					<tr id="nodata">
						<td class="l-td" colspan="20"
							style="color: red; text-align: center;">对不起，没有相关数据</td>
					</tr>
				</tbody>
			</table>
		</div>
				<div class="pageTest"></div>
	</div>
</div>

</body>
<style>
.tooLong {
	overflow: hidden; /*自动隐藏文字*/
	text-overflow: ellipsis; /*文字隐藏后添加省略号*/
	white-space: nowrap; /*强制不换行*/
	width: 20em; /*不允许出现半汉字截断*/
	max-width: 150px;
}
form {
	margin: 0px;
}
</style>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/page/page.css">
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/materiaSearch.js"></script>
<script type="text/javascript" src="${ctx}/resources/page/page.js"></script>

<script>
	var index = 0;

	$(function() {
		$('#OA查询').addClass('ui-tabs-current');
		$('#产品库存查询').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function exportMateria() {
		$.post("exportMateria", "", "");
	}

	function queryIn(curPage) {
		var isManager = '${manager}';
		$
				.ajax({
					type : "POST",
					dataType : "json",
					url : 'queryAjax',
					data : {
						materiaCode : $('#materiaCode').val(),
						spec : $('#spec').val(),
						all : $('#all').val(),
						curPage : curPage
					},
					success : function(data) {
						$('.pageTest').page({
							leng : data.pageCount,//分页总数
							activeClass : 'activP', //active 类样式定义
							clickBack : function(page) {
								queryIn(page);
							}
						});

						var count = 0;
						$('#dataBody').remove();
						tbody = "<tbody id='dataBody'>"
						$
								.each(
										data.list,
										function(index, li) {
											var tr = "<tr id='queryData' align='center'>";
											tr += "<td rowspan='2'>" + ++count
													+ "</td>";
											tr += "<td rowspan='2'>"
													+ li.materiaCode + "</td>";
											tr += "<td  title="+ li.spec +" rowspan='2' >"
													+ li.spec + "</td>";
											tr += "<td class='tooLong' title="+ li.classfiy +" rowspan='2'>"
													+ li.classfiy + "</td>";
											tr += "<td class='tooLong' title="+ li.brand +" rowspan='2'>"
													+ li.brand + "</td>";
											tr += "<td class='tooLong' title="+ li.package1 +" rowspan='2'>"
													+ li.package1 + "</td>";
											tr += "<td rowspan='2'>"
													+ li.warehouse + "</td>";
											tr += "<td rowspan='2'>" + li.num
													+ "</td>";
											tr += "<td rowspan='2'><a href='${ctx }/web/oa/materia_v2/queryMore?id="
													+ li.id
													+ "&whId="
													+ li.warehouseId
													+ "  ' target='_blank'>查看详情</a></td>";
											tr += "</tr><tr></tr>";
											tbody += tr;
										});
						if (count != 0) {
							$('#nodata').hide();
						} else {
							$('#nodata').show();
						}
						tbody += "</tbody>";
						$('#cdTable').append(tbody);
					},
					error : function() {
						alert('出错');
					}
				});
	}
</script>
</html>