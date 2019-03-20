<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>

<!-- layerUI -->
<%-- <link rel="stylesheet" href="${ctx }/resources/layui/css/layui.css" --%>
<!-- 	media="all"> -->
<%-- <script src="${ctx }/resources/layui/layui.js" charset="utf-8"></script> --%>

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
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>

		<form name="form1" action="${ctx }/web/oa/seal/dealSealApply"
			method="post">
			<div style="padding-top: 10px">
				<jsp:include page="../public/sealDate.jsp" />
				<a onclick="addList()" class="lzui-btn lzui-corner-all">查询</a> <a
					href="javascript:printHtml();" class="lzui-btn lzui-corner-all">打印</a>
				<a href="javascript:daochu();" class="lzui-btn lzui-corner-all">导出表格</a>
			</div>

			<div class="gridContainer"
				style="width: 99%; margin-left: 0px; margin-top: 15px;">
				<table class="lzzUIGrid">
					<tr>
						<td class="l-td-toolbar" colspan="20">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								用章信息列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header" style="width: 40px;"><input
							type="checkbox" id="selectCheck" onclick="selectAll()" /></td>
						<td class="l-td-header " style="width: 40px;">序号</td>
						<td class="l-td-header" style="width: 80px;">申请人</td>
						<td class="l-td-header" style="width: 80px;">用章类型</td>
						<td class="l-td-header" style="width: 130px;">用章时间</td>
						<td class="l-td-header" style="width: 150px;">用章文件名</td>
						<td class="l-td-header" style="width: 150px;">文件详情</td>
						<td class="l-td-header" style="width: 150px;">用章单位</td>
						<td class="l-td-header" style="width: 120px;">印章类型</td>
						<td class="l-td-header " style="width: 120px;">用印处</td>
						<td class="l-td-header" style="width: 80px;">文件份数</td>
						<td class="l-td-header " style="width: 80px;">用章数量</td>
					</tr>
					<tbody id="data">

					</tbody>

					<tr id="emptyMsg" style="display: none">
						<td class="l-td-last-right" colspan="20" style="color: red;">
							对不起，目前还没有相关数据。</td>
					</tr>
				</table>
			</div>
			<a onclick="deal()" class="lzui-btn lzui-corner-all"
				style="margin: 5px 0">已处理</a>
			<a onclick="deal()" class="lzui-btn lzui-corner-all"
				style="margin: 5px 0">取消</a>
		</form>
	</div>
</div>

<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	var data = '${list}';
	$(function() {
		$('#OA处理').addClass('ui-tabs-current');
		$('#用章申请处理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		addList();
	});

	function daochu() {
		form1.action = "${ctx}/web/oa/seal/exportRecord";
		document.form1.submit();
	}

	function printHtml() {
		var body1 = "<div align='center' style='width:100%'>"
				+ $('.gridContainer').html() + "</div>";
		$('body').html(body1);
		window.print();
	}

	//enter键提交
	$(window).keydown(function(e) {
		var curKey = e.which;
		if (curKey == 13) {
			addList();
		}
	})

	function selectAll() {
		$('input[name="checkBoxs"]').each(function(j, item) {
			if ($('#selectCheck').is(':checked')) {
				item.checked = true;
			} else {
				item.checked = false;
			}
		});
	}

	function addList() {
		var date = $('#bt').val();
		$('#data').empty();
		var list = '${list}';
		list = JSON.parse(list);
		var tr = '';
		var y = 0;
		$
				.each(
						list,
						function(i, obj) {
							if (!date || date == obj.date.substring(0,11)) {
								y = y + 1;
								tr += '<tr><td class="l-td"><input type="checkbox" value="'+ obj.id +'" name="checkBoxs"/></td>';
								tr += '<td class="l-td">' + y + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.name+'">'
										+ obj.name + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.sealType+'">'
										+ obj.sealType + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.date+obj.time  +'">'
										+ obj.date + obj.time + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.fileName+'">'
										+ obj.fileName + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.fileDetail+'">'
										+ obj.fileDetail + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.sealCompany+'">'
										+ obj.sealCompany + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.sealName+'">'
										+ obj.sealName + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.loaction+'">'
										+ obj.loaction + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.fileNum+'">'
										+ obj.fileNum + '</td>';
								tr += '<td class="l-td limitText" title="'+obj.userNum+'">'
										+ obj.userNum + '</td>';
								tr += '</tr>';
							}
						});
		$('#data').append(tr);
		if (tr == '') {
			$('#emptyMsg').show();
		}
	}

	function deal() {
		if ($('input[name="checkBoxs"]').length == 0) {
			layer.msg('请至少勾选一项');
			return;
		}
		form1.action = '${ctx }/web/oa/seal/dealSealApply';
		document.form1.submit();
	}
</script>
</body>
</html>