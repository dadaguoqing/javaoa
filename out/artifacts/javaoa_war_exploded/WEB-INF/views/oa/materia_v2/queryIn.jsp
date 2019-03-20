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

	<div style="margin: 10px 5px 0 195px; padding-bottom: 0px;">
		<%@ include file="../../public/hello.jsp"%>
		<form name="sForm" id="sForm" action="${ctx}/web/oa/materia/queryIn"
			method="post">
			<input type="hidden" value="${empId }" name="empId" id="uid" /> <input
				type="hidden" value="${uname }" name="uname" id="uname" />
			<table id="baseInfoTable">
				<tr>
					<td style="width: 200px;"><%@ include
							file="../public/startDate.jsp"%></td>
					<td style="width: 200px;"><%@ include
							file="../public/endDate.jsp"%></td>
					<td style="border-right: none;"></td>
					<td><input type="text" name="materiaCode" id="materiaCode"
						placeholder="物料编码" class="form-controller"></td>
					<td><select id="warehouse" name="warehouseId"
						style="width: 150px">
							<option value="">所有仓库</option>
							<c:forEach items="${warehouses}" var="wh">
								<option value="${wh.id}">${wh.warehouse}</option>
							</c:forEach>
					</select></td>
					<td><select id="receivedType" name="receivedType">
							<!-- 						/**物料入库类型：1-采购入库	2-调拨入库	3-返料入库	4-报修入库	5-归还入库	6-其他入库* */ -->
							<option value="">所有类型</option>
							<option value="1">采购录入</option>
							<option value="2">调拨入库</option>
							<option value="3">返料入库</option>
							<option value="4">报修入库</option>
							<option value="5">归还入库</option>
							<option value="6">其他入库</option>
					</select></td>
					<td><a onclick="queryIn(1)" class="lzui-btn lzui-corner-all"
						style="padding: 1px 15px; margin: 0 0 10px 0">查询</a></td>
				</tr>
			</table>
		</form>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 0px;max-height: 400px">
			<table class="lzzUIGrid table1">
				<tbody id="tbody">
					<tr>
						<td class="l-td-toolbar" colspan="20">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								物料管理</div>
						</td>
					</tr>
					<tr>
						<!-- 		*物料编码	品名分类	*品牌	*规格型号	*封装	*单位	功能简称（中文）	功能简称（英文）	功能区分 -->
						<td class="l-td-header " style="width: 9%;">序号</td>
						<td class="l-td-header " style="width: 9%;">物料编码</td>
						<td class="l-td-header tooLong" style="width: 9%;">规格型号</td>
						<td class="l-td-header tooLong" style="width: 9%;">品牌</td>
						<td class="l-td-header tooLong" style="width: 9%;">封装</td>
						<td class="l-td-header " style="width: 9%;">入库人</td>
						<td class="l-td-header " style="width: 9%;">入库时间</td>
						<td class="l-td-header " style="width: 9%;">入库仓库</td>
						<td class="l-td-header " style="width: 9%;">入库数量</td>
						<td class="l-td-header " style="width: 9%">入库类型</td>
					</tr>
					<tr id="nodata">
						<td class="l-td" colspan="20"
							style="color: red; text-align: center;">对不起，没有相关数据</td>
					</tr>
				</tbody>
			</table>
		</div>
			<div style="text-align:right">
	    	<div class="pager"></div>
			</div>
	</div>
</div>

</body>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/mypage/page.css">
<script type="text/javascript" src="${ctx}/resources/mypage/page.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/materiaSearch.js"></script>
<script>
	var index;

	$(function() {
		$('#OA查询').addClass('ui-tabs-current');
		$('#物料入库查询').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function formatDate(str) {
		var year = str.substring(0, 4);
		var month = str.substring(5, 7);
		var day = str.substring(8, 10);
		var hour = str.substring(11, 13);
		var min = str.substring(14, 16);
		str = year + '年' + month + '月' + day + '日' + ' ' + hour + '时' + min
				+ '分';
		return str;
	}

	function getTimeInt(str) {
		var year = str.substring(0, 4);
		var month = str.substring(5, 7);
		var day = str.substring(8, 10);
		var hour = str.substring(12, 14);
		var min = str.substring(15, 17);
		str = year + month + day + hour + min;
		return str;
	}

	function queryIn(curPage) {
		var startTime = $('#startDate').val();
		var endTime = $('#endDate').val();

		if (startTime) {
			startTime = formatDate(startTime);
		}
		if (endTime) {
			endTime = formatDate(endTime);
		}
		if (!startTime && endTime) {
			alert('请选择开始时间');
			return;
		}
		if (startTime && !endTime) {
			alert('请选择结束时间');
			return;
		}
		if (startTime && endTime) {
			var start = getTimeInt(startTime);
			var end = getTimeInt(endTime);
			if (Number(start) >= Number(end)) {
				alert('起止时间不合法');
			}
		}

		$.ajax({
			type : "POST",
			dataType : "json",
			url : "${ctx}/web/oa/materia_v2/queryInAjax",
			data : {
				warehouseId : $('#warehouse').val(),
				receivedType : $('#receivedType').val(),
				startTime : startTime,
				endTime : endTime,
				type : 1,
				materiaCode : $('#materiaCode').val(),
				curPage : curPage
			},
			success : function(data) {
				var count = 0;
				$('#dataBody').remove();
				tbody = "<tbody id='dataBody'>"
				$.each(data.list, function(index, li) {
					var tr = "<tr id='queryData'>";
					tr += "<td>" + ++count + "</td>";
					tr += "<td>" + li.materiaCode + "</td>";
					tr += "<td class='limitText' title="+ li.spec +">" + li.spec + "</td>";
					tr += "<td class='limitText' title="+ li.brand +">" + li.brand + "</td>";
					tr += "<td class='limitText' title="+ li.package1 +">" + li.package1 + "</td>";
					tr += "<td class='limitText' title="+ li.personName +">" + li.personName + "</td>";
					tr += "<td class='limitText' title="+ li.dayStr +">" + li.dayStr + "</td>";
					tr += "<td>" + li.warehouse + "</td>";
					tr += "<td>" + li.num + "</td>";
					tr += "<td>采购入库</td>";
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

				// 回调页面
				function go(p) {
			    	queryIn(p);
			    }
				$('.pager').pager(Number(data.currentPage), Number(data.pageCount), go);
			},
			error : function() {
				alert('出错');
			}
		});
	}
</script>
</html>