<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<!-- 引入bootstrap -->
<script type="text/javascript"
	src="${ctx }/resources/bootstrap/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${ctx }/resources/bootstrap/css/bootstrap.css" />
<link rel="stylesheet"
	href="${ctx }/resources/bootstrap/css/bootstrap.min.css" />
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

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<form name="form1" method="post">
		<div style="margin: 10px 5px 0 195px;">
			<%@ include file="../../public/hello.jsp"%>

			<c:if test="${not empty msg}">
				<div class="lzui-error">${msg}</div>
			</c:if>
			<c:if test="${param.msg == 1}">
				<div class="lzui-error">操作成功</div>
			</c:if>

			<div class="container">
				<div class="row clearfix">
					<div class="col-md-5 column">
						<table class="table">
							<tr>
								<th>用章事由</th>
							</tr>
							<tbody>
								<tr>
									<td><input id="yzsy" name="yzsy" placeholder="添加用章事由"/></td>
									<td><a class="lzui-btn lzui-corner-all"
										onclick="add(1)">添加</a></td>
								</tr>
								<c:forEach items="${yzsys}" var="yzsy">
									<tr>
										<td class="limitText" title="${yzsy.content}">${yzsy.content}</td>
										<td><a onclick="update(${yzsy.id},-1)"
											class="lzui-btn lzui-corner-all">删除</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>

					<div class="col-md-5 column">
						<table class="table">
							<tr>
								<th>用印处</th>
							</tr>
							<tbody>
								<tr>
									<td><input id="yyc" name="yyc" placeholder="添加用印处"/></td>
									<td><a class="lzui-btn lzui-corner-all" onclick="add(2)">添加</a></td>
								</tr>
								<c:forEach items="${yycs}" var="yyc">
									<tr>
										<td class="limitText" title="${yyc.content}">${yyc.content}</td>
										<td><a onclick="update(${yyc.id},-1)"
											class="lzui-btn lzui-corner-all">删除</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-md-2 column">
						<table class="table">
							<tr>
								<th>陪同人</th>
							</tr>
							<tbody>
								<tr>
									<td><input id="ptr" name="ptr" style="width: 60px" placeholder="陪同人"/></td>
									<td><a class="lzui-btn lzui-corner-all" onclick="add(3)">添加</a></td>
								</tr>
								<c:forEach items="${ptrs}" var="yyc">
									<tr>
										<td class="limitText" title="${yyc.content}">${yyc.content}</td>
										<td><a onclick="update(${yyc.id},-1)"
											class="lzui-btn lzui-corner-all">删除</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>



				</div>
			</div>
		</div>
	</form>
</div>
<script>
	var type = '${type}';
	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#用章申请信息管理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function update(id,status) {
		var msg;
		if (status == -1) {
			msg = '确认删除?';
		}
		layer.open({
			content : msg,
			btn : [ '确认', '取消' ],
			yes : function() {
				form1.action = '${ctx}/web/oa/seal/deleteSealElse?id='+id+'&status='+status;
				document.form1.submit();
				}
		}
		);
	}
	
	function add(str) {
		var content = '';
		if(str == 1) {
			content = $('#yzsy').val();
			if(!content) {
				layer.msg('用章事由不能为空');
				return;
			}
		}
		if(str == 2) {
			content = $('#yyc').val();
			if(!content) {
				layer.msg('用印处不能为空');
				return;
			}
		}
		if(str == 3) {
			content = $('#ptr').val();
			if(!content) {
				layer.msg('陪同人不能为空');
				return;
			}
		}
		layer.open({
			content : '确认添加"'+ content +'"',
			btn : [ '确认', '取消' ],
			yes : function() {
				form1.action = '${ctx}/web/oa/seal/addSealElse?type='+ str;
				document.form1.submit();
				}
		}
		);
	}

</script>
</body>
</html>