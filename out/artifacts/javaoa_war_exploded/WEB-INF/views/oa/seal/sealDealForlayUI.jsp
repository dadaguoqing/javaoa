<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>

<!-- layerUI -->
<link rel="stylesheet" href="${ctx }/resources/layui/css/layui.css"
	media="all">
<script src="${ctx }/resources/layui/layui.js" charset="utf-8"></script>

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

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

		<div class="lzui-error" id="ajaxmsg" style="display: none">操作成功</div>

		<c:if test="${param.msg == 1}">
			<div class="lzui-error"></div>
		</c:if>

		<c:if test="${type == 4}">
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;" href="${ctx}/web/oa/seal/sealDealRecord">查看记录</a>
			</div>
		</c:if>
		<div>
			<table class="" id="test" style="none"></table>
		</div>
	</div>
</div>
<script>
	var data = '${list}';
	$(function() {
		$('#管理').addClass('ui-tabs-current');
		$('#用章申请处理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	layui.use('table', function() {
		var table = layui.table;
		table.render({
			elem : '#test',
			url : '${ctx}/web/oa/seal/sealDealAjax',
			cols : [ [ {
				field : 'fileName',
				width : 120,
				title : '用章文件名',
			}, {
				field : 'sealCompany',
				width : 240,
				title : '用章单位'
			}, {
				field : 'sealName',
				width : 120,
				title : '印章类型'
			}, {
				field : 'fileNum',
				title : '文件份数',
				width : 120
			}, {
				field : 'userNum',
				width : 120,
				title : '用章数量'
			}, {
				field : 'loaction',
				width : 100,
				title : '用印处'
			}, {
				field : 'content',
				width : 200,
				title : '说明'
			} ] ],
			page : true,
			response : {
				statusName : 'code' //数据状态的字段名称，默认：code
				,
				statusCode : 0 //成功的状态码，默认：0
				,
				msgName : 'msg' //状态信息的字段名称，默认：msg
				,
				countName : 'count' //数据总数的字段名称，默认：count
				,
				dataName : 'data' //数据列表的字段名称，默认：data
			},
			done: function (res, curr, count) {
                $('th').css({ 'border': '2px solid #9CBAE7','font-weight':'bold','border-bottom': 'none','background' : '#CEDFEF url("../images/panel-toolbar.gif") repeat-x'});
                $('td').css({ 'border': '2px solid #9CBAE7','border-top': 'none'});
            }
		});
	});
</script>
</body>
</html>