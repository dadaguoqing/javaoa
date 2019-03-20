<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../public/header.jsp"%>
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
	<form name="form1" method="post" id="form1" >
		<div style="margin: 10px 5px 0 195px;">
			<%@ include file="../../../public/hello.jsp"%>
			<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
			<div class="container">
				<div class="row clearfix">
					<div class="col-md-6 column">
						<div class="lzui-tooltips"
							style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
							<a style="color: #C0392B;"  href="javascript:add(1)">新增出库原因</a>
						</div>
						<table class="table">
							<tr>
								<th>出库原因</th>
							</tr>
							<tbody>
								<c:forEach items="${list1}" var="sd">
									<tr>
										<c:if test="${sd.status == 1}">
											<td class="limitText" title="${sd.content}">${sd.content}</td>
											<td class="limitText" title="${sd.descript}">${sd.descript}</td>
											<td><a  href="javascript:update(${sd.id})">删除</a></td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<div class="col-md-6 column">
					<div class="lzui-tooltips"
							style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
							<a style="color: #C0392B;" href="javascript:add(2)">新增入库原因</a>
						</div>
						<table class="table">
							<tr>
								<th>入库原因</th>
							</tr>
							<tbody>
								<c:forEach items="${list2}" var="sd">
									<tr>
										<c:if test="${sd.status == 1}">
											<td class="limitText" title="${sd.content}">${sd.content}</td>
											<td class="limitText" title="${sd.descript}">${sd.descript}</td>
											<td><a  href="javascript:update(${sd.id})">删除</a></td>
										</c:if>
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
<div style="display: none">
	<div id="add">
		<div class="main-header">
			<span style="color: #eee;" id="title"></span>
		</div>
		<table class="table1">
			<tbody>
				<tr>
					<td class="title" style="width: 120px;"><span id="reason"></span><span
						style="color: red;">*</span>：
					</td>
					<td>
					<input name="content" id="content" style="width: 300px;" />
					<input name="insertType" id="id" style="width: 300px;" type="hidden" />
					</td>
				</tr>
				<tr>
					<td class="title">备注：
					</td>
					<td><textarea rows="3" cols="40" name="descript" id="descript"></textarea></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>


<script>
	var type = '${type}';
	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#出入库原因管理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function add(id) {
		var form = '<form id="formAdd" name="formAdd" method="post"'
			+ 'action="${ctx}/web/oa/merchandise/manage/addReason">';
		layer.open({
			closeBtn: 0 ,
			title : false,
			area : ['500px',''],
			content : form + $('#add').html() + "</form>",
			btn : [ '确认', '取消' ],
			success : function(layero,index) {
				$(layero).find('#title').html(id == 1 ? '新增出库原因' : '新增入库原因');
				$(layero).find('#reason').html(id == 1 ? '出库原因' : '入库原因');
				$(layero).find('#id').val(id);
			},
			yes : function(index,layero) {
				var content = $(layero).find('#content').val();
				var str = '';
				if(id == 1) {
					str = '出库原因';
				} else {
					str = '入库原因';
				}
				if(!content) {
					layer.msg('请填写' + str + "!",{type : 1});
					return;
				}
				var descript = $(layero).find('#descript').val();
				if(descript) {
					$(layero).find('#descript').val().replace(/[ \r\n]/g,'');
				}
				$(layero).find('#formAdd').submit();
			}
		}
		);
	}
	
	function update(id) {
		layer.open({
			content : '确认删除？',
			icon : 3,
			btn : ['确认','取消'],
			yes : function() {
				form1.action = '${ctx}/web/oa/merchandise/manage/deleteReason?id='+id;
				$('#form1').submit();
			}
		});
	}

</script>
</body>
</html>