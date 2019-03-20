<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../myheader.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

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
		<%-- 		<jsp:include page="../public/lead.jsp"> --%>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../../public/hello.jsp"%>
		<div class="lzui-error" id="msg" style="display: none"></div>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		<form id="form1" name="form1" method="post">
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;" href="javascript:add()">新增供应商信息</a>
			</div>

			<div class="gridContainer"
				style="width: 99%; margin-left: 0px; margin-top: 15px;">
				<table class="lzzUIGrid">
					<tbody>
						<tr>
							<td class="l-td-toolbar" colspan="10">
								<div class="l-float-left"
									style="font-weight: bold; font-size: 15px; margin-right: 20px;">
									印章信息列表</div>
							</td>
						</tr>
						<tr>
							<td class="l-td-header l-td-center" style="width: 50px;">序号</td>
							<td class="l-td-header" style="width: 150px;">供应商编码</td>
							<td class="l-td-header" style="width: 150px;">供应商类别</td>
							<td class="l-td-header" style="width: 150px;">供应商名称</td>
							<td class="l-td-header" style="width: 150px;">地址</td>
							<td class="l-td-header" style="width: 100px;">联系人</td>
							<td class="l-td-header" style="width: 100px;">联系电话</td>
							<td class="l-td-header" style="width: 150px;">备注</td>
							<td class="l-td-header l-td-last-right" style="width: 200px;">操作</td>
						</tr>

						<c:forEach items="${list}" var="supplier" varStatus="status">
							<tr class="data">
								<td class="l-td">${status.count}</td>
								<td class="l-td l-td-left" class="limitText"
									title="${supplier.code }">${supplier.code }</td>
								<td class="l-td l-td-left" class="limitText"
									title="${supplier.genre }">${supplier.genre}</td>
								<td class="l-td" class="limitText" title="${supplier.name }">${supplier.name}</td>
								<td class="l-td" class="limitText" title="${supplier.address }">${supplier.address}</td>
								<td class="l-td" class="limitText" title="${supplier.contact }">${supplier.contact}</td>
								<td class="l-td" class="limitText" title="${supplier.phone }">${supplier.phone}</td>
								<td class="l-td" class="limitText" title="${supplier.content }">${supplier.content}</td>
								<td class="l-td">
									<a href="javascript:modify(${supplier.id})">修改</a>
									<a href="javascript:del(${supplier.id})">删除</a>
								</td>
							</tr>
						</c:forEach>

						<c:if test="${empty list}">
							<tr>
								<td class="l-td-last-right" colspan="8" style="color: red;">
									对不起，目前还没有相关数据。</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</form>
	</div>
</div>
</body>
</html>



<!-- 				弹出层 -->
<div style="display: none">
	<div id="add">
		<div class="main-header">
			<span style="color: #eee;" id="title">新增供应商</span>
		</div>
		<table class="table1">
			<tbody>
				<tr>
					<td class="title" style="width: 120px;">供应商编码<span
						style="color: red;">*</span>：
					</td>
					<td>
					<input name="id" id="id" type="hidden" />
					<input name="code" id="code" style="width: 300px;" />
					
					</td>
				</tr>
				<tr>
					<td class="title">供应商类别<span style="color: red;">*</span>：
					</td>
					<td><input name="genre" id="genre" style="width: 300px;" /></td>
				</tr>
				<tr>
					<td class="title">供应商名称<span style="color: red;">*</span>：
					</td>
					<td><input name="name" id="name" style="width: 300px;" /></td>
				</tr>
				<tr>
					<td class="title">联系人<span style="color: red;">*</span>：
					</td>
					<td><input name="contact" id="contact" style="width: 300px;" /></td>
				</tr>
				<tr>
					<td class="title">地址<span style="color: red;">*</span>：
					</td>
					<td><textarea rows="3" cols="40" name="address" id="address"></textarea></td>
				</tr>
				<tr>
					<td class="title">联系电话<span style="color: red;">*</span>：
					</td>
					<td><input name="phone" id="phone" style="width: 300px;" /></td>
				</tr>
				<tr>
					<td class="title">备注：
					</td>
					<td><textarea rows="3" cols="40" name="content" id="content"></textarea></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<script>
	var mode = '${mode}';
	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#供应商信息管理').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		
	});
	
	function del(id) {
		layer.open({
			icon: 3, // 不显示关闭按钮
			content: "确定删除该供应商吗？",
			btn : ["确定","取消 "],
			yes : function() { // 确定按钮方法
				$.post("deleteSupplier",{id : id},function(result) {
					if(mode == 1) {
						result = JSON.parse(result);
					}
					if(result.success == 1) {
						location.reload();
					} else {
						$("#msg").html(result.msg);
						$('#msg').show();
					}
				});
			}
		});
	}
	
	
	function add() {
		var form = '<form id="formAdd" name="formAdd" method="post"'
		+ 'action="${ctx}/web/oa/merchandise/manage/addSupplier">';
		layer.open({
			closeBtn: 0 ,
			title: false,
			content: form + $('#add').html() + '</form>',
			btn : ['确定','取消'],
			area : ['500px','490px'],
			yes : function(index,layero) {
				var code = $(layero).find('#code').val();
				if(!code) {
					layer.msg('供应商编码不能为空', {type:1});
					return ;
				}
				if(!$(layero).find('#genre').val()) {
					layer.msg('供应商类别不能为空', {type:1});
					return ;
				}
				if(!$(layero).find('#name').val()) {
					layer.msg('供应商名称不能为空', {type:1});
					return ;
				}
				if(!$(layero).find('#address').val()) {
					layer.msg('地址不能为空', {type:1});
					return ;
				}
				if(!$(layero).find('#contact').val()) {
					layer.msg('联系人不能为空', {type:1});
					return ;
				}
				if(!$(layero).find('#phone').val()) {
					layer.msg('联系电话不能为空', {type:1});
					return ;
				}
				$(layero).find('#formAdd').submit();
			}
		});
	}
	
	
	function modify(id) {
		$.get("getSupplier",{id : id},function(result){
			if(mode == 1) {
				result = eval("(" + result + ")");
			}
			if(result.success == 1) {
				var ms = result.data;
				
				var form = '<form id="formModify" name="formModify" method="post"'
					+ 'action="${ctx}/web/oa/merchandise/manage/updateSupplier">';
					layer.open({
						closeBtn: 0 ,
						title: false,
						content: form + $('#add').html() + '</form>',
						btn : ['确定','取消'],
						area : ['500px','490px'],
						success : function(layero,index) {
							$(layero).find('#title').html("修改供应商");
							$(layero).find('#id').val(ms.id);
							$(layero).find('#code').val(ms.code);
							$(layero).find('#genre').val(ms.genre);
							$(layero).find('#name').val(ms.name);
							$(layero).find('#address').val(ms.address);
							$(layero).find('#contact').val(ms.contact);
							$(layero).find('#phone').val(ms.phone);
							$(layero).find('#content').val(ms.content);
						},
						yes : function(index,layero) {
							var code = $(layero).find('#code').val();
							if(!code) {
								layer.msg('供应商编码不能为空', {type:1});
								return ;
							}
							if(!$(layero).find('#genre').val()) {
								layer.msg('供应商类别不能为空', {type:1});
								return ;
							}
							if(!$(layero).find('#name').val()) {
								layer.msg('供应商名称不能为空', {type:1});
								return ;
							}
							if(!$(layero).find('#address').val()) {
								layer.msg('地址不能为空', {type:1});
								return ;
							}
							if(!$(layero).find('#contact').val()) {
								layer.msg('联系人不能为空', {type:1});
								return ;
							}
							if(!$(layero).find('#phone').val()) {
								layer.msg('联系电话不能为空', {type:1});
								return ;
							}
							$(layero).find('#formModify').submit();
						}
					});
			} else {
				$('#msg').html(result.msg);
				$('#msg').show();
			}
		});
		
		
	}
	
</script>