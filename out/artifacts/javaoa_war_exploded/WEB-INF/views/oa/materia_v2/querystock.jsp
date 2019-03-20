<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
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
		<div class="tableTitle"
			style="border-bottom: 1px dotted #aaaaaa; padding-bottom: 5px;">
			<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
			查询条件
		</div>
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
					<td><a onclick="queryIn()" class="lzui-btn lzui-corner-all"
						style="padding: 1px 15px; margin: 0 0 10px 0">查询</a></td>
					<c:choose>
						<c:when test="${isManager == 1 or isManager == 0}">
							<td><a href="${ctx }/web/oa/materia/exportMateria?type=1"
								class="lzui-btn lzui-corner-all"
								style="padding: 1px 15px; margin: 0 0 10px 0">导出excel</a></td>
						</c:when>
						<c:when test="${isManager == 3 }">
							<td><a href="${ctx }/web/oa/materia/exportMateria?type=2"
								class="lzui-btn lzui-corner-all"
								style="padding: 1px 15px; margin: 0 0 10px 0">导出excel</a></td>
						</c:when>
					</c:choose>
				</tr>
			</table>
		</form>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
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
						<td class="l-td-header " style="width: 7%;">芯片研发中心</td>
						<td class="l-td-header " style="width: 7%">工业控制项目</td>
						<td class="l-td-header " style="width: 7%">深圳技术部</td>
						<c:if test="${isManager == 0  ||  isManager == 1}">
							<td class="l-td-header " style="width: 7%">总库存</td>
						</c:if>
						<td class="l-td-header " style="width: 10%">操作</td>
					</tr>
					<tr id="nodata">
						<td class="l-td" colspan="20"
							style="color: red; text-align: center;">对不起，没有相关数据</td>
					</tr>
				</tbody>
			</table>

		</div>

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
</style>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/materiaSearch.js"></script>
<script>
	var index = 0;

	$(function() {
		$('#物料查询').addClass('ui-tabs-current');
		$('#物料库存查询').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function exportMateria() {
		$.post("exportMateria", "", "");
	}

	function queryIn() {
		var isManager = '${isManager}';
		$.ajax({
			type : "POST",
			dataType : "json",
			url : 'queryAjax',
			data : {
				materiaCode : $('#materiaCode').val(),
				spec : $('#spec').val(),
				all : $('#all').val()
			},
			success : function(data) {
				var count = 0;
				$('#dataBody').remove();
				tbody = "<tbody id='dataBody'>"
				$.each(data, function(index, li) {
					var num = li.warehouse1 + li.warehouse2 + li.warehouse3;
					var tr = "<tr id='queryData' align='center'>";
					tr += "<td>" + ++count + "</td>";
					tr += "<td>" + li.materiaCode + "</td>";
					tr += "<td class='tooLong'>" + li.spec + "</td>";
					tr += "<td class='tooLong'>" + li.classfiy + "</td>";
					tr += "<td class='tooLong'>" + li.brand + "</td>";
					tr += "<td class='tooLong'>" + li.package1 + "</td>";
					tr += "<td>" + li.warehouse1 + "</td>";
					tr += "<td>" + li.warehouse2 + "</td>";
					tr += "<td>" + li.warehouse3 + "</td>";
					if (isManager == 1 || isManager == 0) {
						tr += "<td>" + num + "</td>";
					}
					tr += "<td><a href='${ctx }/web/oa/materia/queryMore?id="
							+ li.id + "' target='_blank'>查看详情</a></td>";
					tr += "</tr>";
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