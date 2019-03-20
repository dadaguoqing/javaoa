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
					<div class="col-md-6 column">
						<table class="table">
							<tr>
								<th colspan="3">外协加工管理</th>
							</tr>
							<tbody>
						<c:if test="${shenchanyuan == 1 }">
								<tr>
									<td>PCB加工工期说明及其他技术要求</td>
									<td><a href="${ctx}${mc}" target="_blank">查看当前工期说明</a></td>
									<td>
										<a href="javascript:void(0)" onclick="add(1)">修改</a>
									</td>
								</tr>
								<tr>
									<td>焊接加工工期说明及其他技术要求</td>
									<td><a href="${ctx}${mc2}" target="_blank">查看当前工期说明</a></td>
									<td>
										<a href="javascript:void(0)" onclick="add(2)">修改</a>
									</td>
								</tr>
								</c:if>
								<c:if test="${caiwu == 1 }">
								<tr>
									<td>项目预算额度</td>
									<td>${mc3} 元</td>
									<td>
										<a href="javascript:void(0)" onclick="add(3)">修改</a>
									</td>
								</tr>
								<tr>
									<td>加工费用额度</td>
									<td>${mc4} 元</td>
									<td>
										<a href="javascript:void(0)" onclick="add(4)">修改</a>
									</td>
								</tr>
								</c:if>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<div style="display: none;">
	<div id="data">
		<form name="form2"  id="form2" method="post" 
		action="${ctx}/web/oa/external/updateFile" enctype="multipart/form-data">
			<input type="file" name="file" id="file"/>
			<input type="hidden" name="fileId" id="fileId"/>
		</form>
	</div>
</div>
<div style="display: none;">
	<div id="data2">
		<form name="form2"  id="form2" method="post" 
		action="${ctx}/web/oa/external/updateFile">
			请填写额度：<input type="number" name="cost" id="cost" style="width: 100px" />
			<input type="hidden" name="fileId" id="fileId"/>
		</form>
	</div>
</div>
<script>
	var type = '${type}';
	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#外协加工管理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function add(id) {
		if(id < 3) {
			layer.open({
				title : '<b>上传文件<b>', 
				content : $('#data').html(),
				btn : [ '确认', '取消' ],
				yes : function(index, layero) {
						$(layero).find('#fileId').val(id);
							var file = $(layero).find('#file').val();
							var suffix = file.substr(file.lastIndexOf(".")).toLowerCase();
							if('.jpg' != suffix && '.png' != suffix) {
								layer.msg('请上传jpg或png格式文件！', {type : 1});
								return;
							}
							if(!file) {
								layer.msg('请上传文件！', {type : 1});
								return;
							}
						$(layero).find('#form2').submit();
					}
			});
		} else {
			layer.open({
				title : '<b>填写信息<b>', 
				content : $('#data2').html(),
				btn : [ '确认', '取消' ],
				yes : function(index, layero) {
						$(layero).find('#fileId').val(id);
							var num = $(layero).find('#cost').val();
							var reg = /^([0-9]{1,}[.][0-9]*)$/;
							var reg2 = /^([0-9]{1,})$/;
							if (!num) {
								layer.alert('不能为空！', {icon : 2});
								return;
							}
							if ((!reg.test(num) && !reg2.test(num)) || num < 0) {
								layer.alert('格式错误，请填写数字！', {icon : 2});
								return;
							}
						$(layero).find('#form2').submit();
					}
			});
		}
	}

</script>
</body>
</html>