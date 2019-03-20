<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
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
		<%@ include file="../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;" href="javascript:void(0)" onclick="add()" >新增菜单权限</a>
			</div>
			<div style="display: none">
			<div id="addPer" >
				<form id="form1" name="form1" method="post" action="${ctx}/web/oa/menu/addPer">
					<div class="main-header">
					<span style="color: #eee;" id="title">新增菜单权限</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title">菜单：</td>
							<td>
								<select id="menuId" name="menuId">
									<option value="2">OA管理</option>
									<option value="14">OA处理</option>
									<option value="222">OA查询</option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">角色：</td>
							<td>
								<select id="roleId" name="roleId">
									<c:forEach items="${roles}" var="role">
										<option value="${role.id}">${role.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td class="title">页面地址：</td>
							<td>
								<select id="page" name="page">
									<c:forEach items="${pages}" var="page">
										<option value="${page.url}">${page.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
					</table>
				</form>
			</div>
			</div>

			<div class="gridContainer"
				style="width: 99%; margin-left: 0px; margin-top: 15px;">
				<table class="lzzUIGrid">
					<tbody>
						<tr>
							<td class="l-td-toolbar" colspan="8">
								<div class="l-float-left"
									style="font-weight: bold; font-size: 15px; margin-right: 20px;">
									印章信息列表</div>
							</td>
						</tr>

						<tr>
							<td class="l-td-header l-td-center" style="width: 60px;">序号</td>
<!-- 							<td class="l-td-header" style="width: 80px;">菜单ID</td> -->
							<td class="l-td-header" style="width: 100px;">菜单名称</td>
<!-- 							<td class="l-td-header" style="width: 80px;">角色ID</td> -->
							<td class="l-td-header" style="width: 300px;">角色名称</td>
							<td class="l-td-header" style="width: 150px;">页面地址</td>
							<td class="l-td-header" style="width: 50px;">状态</td>
							<td class="l-td-header l-td-last-right" style="width: 100px;">操作</td>
						</tr>

						<c:forEach items="${list}" var="per" varStatus="status">
							<tr class="data"> 
								<td class="l-td">${status.count}</td>
<%-- 								<td class="l-td l-td-left">${per.menuId}</td> --%>
								<td class="l-td l-td-left">${per.menuName}</td>
<%-- 								<td class="l-td">${per.roleId}</td> --%>
								<td class="l-td">${per.roleName}</td>
								<td class="l-td">${per.url}</td>
								<td class="l-td">${per.status==1?'启用':'禁用'}</td>
								<td class="l-td">
									<a onclick="del(${per.id})" href="javascript:void(0)" >删除</a>
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
		
	</div>
</div>
<script>

	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#菜单权限').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function add() {
		layer.open({
			title : false,
			content : $('#addPer').html(),
			btn : ['确定', '取消'],
			area : ['500px', ''],
			yes : function(index, layero) {
				$(layero).find('#form1').submit();
			}
		});
	}
	
	function del(id) {
		layer.open({
			title : false,
			closeBtn : false,
			content : '确定删除此权限？',
			icon : 3,
			btn : ['确定', '取消'],
			yes : function() {
				$.post("delPermission",{id : id}, function(result) {
					if('${mode}' == 1) {
						result = JSON.parse(result);
					}
					if(result.success == 1) {
						location.reload();
					}
				});
			}
		});
	}
</script>
</body>
</html>