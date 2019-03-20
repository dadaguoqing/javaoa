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

		<c:forEach items="${loginUserMenuMap['14']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
			</div>
		</c:forEach>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/materia_v2/maApplyList2">返回物料申领处理</a>
		</div>

		<form name="sForm" id="sForm"
			action="${ctx}/web/oa/materia/queryRecord" method="post">
			<table id="baseInfoTable">
				<tr>
					<td><select id="empId" name="empId">
							<option value=0>===请选择申请人===</option>
							<c:forEach items="${empIds}" var="empId">
								<option value="${empId}">${AllUsers[empId].name }</option>
							</c:forEach>
					</select></td>
					<td style="padding: 1px 15px; margin: 0 0 5px 0"><input
						type="text" name="code" id="code" placeholder="编号"></td>
					<td><a onclick="qureyRecord()"
						class="lzui-btn lzui-corner-all"
						style="padding: 1px 15px; margin: 0 0 5px 0">查询</a></td>
				</tr>
			</table>
		</form>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="7">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								已审批列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header " style="width: 70px;">申领人</td>
						<td class="l-td-header" style="width: 180px;">申领时间</td>
						<td class="l-td-header" style="width: 170px;">编号</td>
						<td class="l-td-header" style="width: 175px;">申领用途</td>
						<td class="l-td-header " style="width: 130px;">备注</td>
						<td class="l-td-header " style="">查看详情</td>
					</tr>
				</tbody>
				<tbody id="allData">
					<c:forEach items="${list}" var="cur">
						<tr>
							<td class="l-td">${AllUsers[cur.empId].name }</td>
							<td class="l-td">${cur.dayStr }</td>
							<td class="l-td">${cur.code }</td>
							<td class="l-td">${cur.use1 }</td>
							<td class="l-td">${cur.bz }</td>
							<td class="l-td"><a
								href="${ctx }/web/oa/materia_v2/applyRecordDetail3?id=${cur.id}">查看详情</a></td>
						</tr>
					</c:forEach>
				</tbody>
				<tbody>
					<tr id="nodata">
						<td class="l-td" colspan="20"
							style="color: red; text-align: center;">对不起，没有相关数据</td>
					</tr>
				</tbody>
			</table>

		</div>
	</div>
</div>
<script>
	$(function() {
		$('#物料管理').addClass('ui-tabs-current');
		$('#物料申领处理2').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function qureyRecord() {
		$('#allData').remove();
		$
				.ajax({
					type : "POST",
					dataType : "json",
					url : "${ctx}/web/oa/materia/queryRecordAjax",
					data : {
						empId : $('#empId').val(),
						code : $('#code').val()
					},
					success : function(data) {
						var count = 0;
						$('#dataBody').remove();
						tbody = "<tbody id='dataBody'>"
						$
								.each(
										data,
										function(index, li) {
											count++;
											var tr = "<tr id='queryData'>";
											tr += "<td class='l-td'>" + li.name
													+ "</td>";
											tr += "<td class='l-td'>"
													+ li.dayStr + "</td>";
											tr += "<td class='l-td'>" + li.code
													+ "</td>";
											tr += "<td class='l-td'>" + li.use1
													+ "</td>";
											tr += "<td class='l-td'>" + li.bz
													+ "</td>";
											tr += "<td class='l-td'> <a href='${ctx }/web/oa/materia/applyRecordDetail3?id="
													+ li.id + "'>查看详情</a></td>";
											tr += "</tr>";
											tbody += tr;
										});
						if (count != 0) {
							$('#nodata').hide();
						} else {
							$('#nodata').show();
						}
						tbody += "</tbody>";
						$('.lzzUIGrid').append(tbody);

					},
					error : function() {
						alert('出错');
					}
				});
	}
</script>
</body>
</html>