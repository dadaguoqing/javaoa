<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
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
			<div class="container">
				<div class="row clearfix">
					<div class="col-md-6 column">
						<table class="table">
							<tr>
								<th>用章日期</th>
							</tr>
							<tbody>
								<c:forEach items="${list}" var="sd">
									<tr>
										<c:if test="${sd.status == 1}">
											<td style="color: green">${sd.weekday}</td>
											<td><a onclick="update(${sd.id},-1)"
												class="lzui-btn lzui-corner-all">禁用</a></td>
										</c:if>
										<c:if test="${sd.status == -1}">
											<td style="color: red">${sd.weekday}</td>
											<td><a onclick="update(${sd.id},1)"
												class="lzui-btn lzui-corner-all">启用</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-md-6 column">
						<table class="table">
							<tr>
								<th>用章时间</th>
							</tr>
							<tbody>
								<tr>
									<td>内部用章时间</td>
									<td>${date.weekday}</td>
								</tr>
								<tr>
									<td><select name="bHour" id="bHour">
											<option value="08时">08时</option>
											<option value="09时">09时</option>
											<option value="10时">10时</option>
											<option value="11时">11时</option>
											<option value="12时">12时</option>
											<option value="13时">13时</option>
											<option value="14时">14时</option>
											<option value="15时">15时</option>
											<option value="16时">16时</option>
											<option value="17时">17时</option>
									</select> <select name="bMin" id="bMin">
											<option value="00分">00分</option>
											<option value="30分">30分</option>
									</select> -- <select name="eHour" id="eHour">
											<option value="08时">08时</option>
											<option value="09时">09时</option>
											<option value="10时">10时</option>
											<option value="11时">11时</option>
											<option value="12时">12时</option>
											<option value="13时">13时</option>
											<option value="14时">14时</option>
											<option value="15时">15时</option>
											<option value="16时">16时</option>
											<option value="17时">17时</option>
									</select> <select name="eMin" id="eMin">
											<option value="30分">30分</option>
											<option value="00分">00分</option>
									</select></td>
									<td><a onclick="modifyTime()"
										class="lzui-btn lzui-corner-all">修改</a></td>
								</tr>
								<tr>
									<td>内部用章申请截止时间</td>
									<td>${date2.weekday}分钟</td>
								</tr>
								<tr>
									<td><input name="jzsj" id="jzsj" type="number"  value="0"
									min="0" placeholder="请填写数字" onblur="testnum(this)">分钟</td>
									<td>
										<a onclick="modifyJsTime()" class="lzui-btn lzui-corner-all">修改</a>
									</td>
								</tr>
								<tr>
									<td>外带用章申请截止时间</td>
									<td>${date3.weekday}</td>
								</tr>
								<tr>
									<td><input name="jzsj2" id="jzsj2" type="number"  value="0"
											min="0" placeholder="请填写数字" onblur="testnum(this)">分钟</td>
									<td>
										<a onclick="modifyWdTime()" class="lzui-btn lzui-corner-all">修改</a>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
<script>

function testnum(obj) {
	var reg = /^\d+$/;
	var num = $(obj).val();
	if (!num) {
		layer.msg('不能为空！');
		$(obj).val(0);
		return;
	}
	if (!reg.test(num)) {
		layer.msg('格式错误，请填写非负整数！');
		$(obj).val(0);
		return;
	}
}



	var type = '${type}';
	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#用章日期管理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function update(id,status) {
		var msg;
		if (status == -1) {
			msg = '确认禁用该日期吗?';
		} else {
			msg = '确认启用该日期吗?';
		}
		layer.open({
			content : msg,
			btn : [ '确认', '取消' ],
			icon : 3 ,
			yes : function() {
				form1.action = '${ctx}/web/oa/seal/dealSealDate?id='+id+'&status='+status;
				document.form1.submit();
				}
		}
		);
	}
	
	function modifyJsTime() {
		layer.open({
			content : '确认将内部用章申请截止时间改为 ' + $('#jzsj').val() + '分钟',
			btn : [ '确认', '取消' ],
			icon : 3 ,
			yes : function() {
				form1.action = '${ctx}/web/oa/seal/dealSealTime2';
				document.form1.submit();
				}
		});
	}
	
	function modifyTime() {
		var bHour = $('#bHour').val();
		var bMin = $('#bMin').val();
		var eHour = $('#eHour').val();
		var eMin = $('#eMin').val();
		var bTime = bHour+bMin;
		var eTime = eHour+eMin;
		if(bTime >= eTime) {
			layer.msg('该时间段不合法');
			return;
		}
		layer.open({
			content : '确认将内部用章时间改为'+bHour+bMin +'--'+eHour+eMin+ "?",
			icon : 3 ,
			btn : [ '确认', '取消' ],
			yes : function() {
				form1.action = '${ctx}/web/oa/seal/dealSealTime';
				document.form1.submit();
				}
		});
	}
	
	
	
	function modifyWdTime() {
		
		layer.open({
			content : '确认将外带用章申请截止时间修改为' + $('#jzsj2').val() + "分钟?",
			icon : 3 ,
			btn : [ '确认', '取消' ],
			yes : function() {
				form1.action = '${ctx}/web/oa/seal/dealWdTime';
				document.form1.submit();
				}
		});
	}

</script>
</body>
</html>