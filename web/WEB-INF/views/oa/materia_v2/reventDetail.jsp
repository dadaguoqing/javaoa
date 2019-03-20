<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<style>
.mytitle {
	text-align: center;
	font-weight: bold;
}
</style>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>



		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;申请详情
		</div>
		<c:if test="${type == 1}">
			<div class="navTitle2" url="${ctx }/web/oa/materia_v2/myReventRecord">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
		<!-- 		审批记录 -->
		<c:if test="${type == 2}">
			<div class="navTitle2" url="${ctx }/web/oa/materia_v2/myPurRecord2">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
		<!-- 		审批 -->
		<c:if test="${type == 3}">
			<div class="navTitle2" url="${ctx }/web/oa/materia_v2/reventApprove">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
		<c:if test="${type == 4}">
			<div class="navTitle2" url="${ctx }/web/oa/materia_v2/reventApproveRecord">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>
		<div id="print">
			<form name="form1" action="${ctx }/web/oa/materia_v2/toRevent"
				method="post" onsubmit="return beforeSubmit();">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${app.id}" name="id" id="hid" /> <input
					type="hidden" name="status" id="status" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }返料入库单</span>
					</div>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							基本信息
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="width: 15%;">申请人：</td>
								<td style="width: 10%;">${AllUsers[app.empId].name }</td>
								<td class="title">所属部门：</td>
								<td style="">${AllUsers[app.empId].deptName }</td>
								<td class="title" style="">申请时间：</td>
								<td style="border-right: none;">${app.daystr }</td>
								<td class="title" style="width: 120px;">返料入库单编号：</td>
								<td><span>${app.requisitionCode }</span></td>
							</tr>
							<c:if test="${type==3 }">
								<tr>
									<td class="title" style="width: 15%; color: red">返料入库仓库：</td>
									<td><select name="warehouseId" id="warehouse">
											<option value="1">B1仓库</option>
											<option value="2">F4仓库</option>
											<option value="3">深圳仓库</option>
									</select></td>
								</tr>
							</c:if>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							返料详情
						</span>
					</div>
					<table class="table1" id='cdTable'>
						<tbody>
							<tr class="mytitle">
								<td class="" width="30px"><input type="checkbox" id="cAll"
									onclick="selectAll();" /></td>
								<td class="" width="30px">序号</td>
								<td class="" width="100px">物料编码</td>
								<td class="">品名分类</td>
								<td class="">品牌</td>
								<td class="">规格型号</td>
								<td class="">封装</td>
								<td class="">返料数量</td>
							</tr>
							<c:forEach items="${list}" var="materia" varStatus="index">
								<tr>
									<td align="center"><c:if test="${materia.isDeal == '未处理'}">
											<input name="maId" type="checkbox" value="${materia.id}" />
										</c:if></td>
									<td rowspan="2" align="center"><span>${index.count}</span>
										<input type="hidden" name="maCode" value="${materia.maCode}">
										<input type="hidden" name="classify"
										value="${materia.classify}"> <input type="hidden"
										name="brand" value="${materia.brand}"> <input
										type="hidden" name="spec" value="${materia.spec}"> <input
										type="hidden" name="package1" value="${materia.package1}">
										<input type="hidden" name="num" value="${materia.needNum}">
									</td>
									<td rowspan="2"><span>${materia.maCode}</span></td>
									<td rowspan="2"><span>${materia.classify}</span></td>
									<td rowspan="2"><span>${materia.brand}</span></td>
									<td rowspan="2"><span>${materia.spec}</span></td>
									<td rowspan="2"><span>${materia.package1}</span></td>
									<td rowspan="2"><span>${materia.needNum}</span></td>
								</tr>
								<tr></tr>
							</c:forEach>
						</tbody>
					</table>

					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							备注信息
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td style="border-right: none;"><textarea name="bz" id="bz"
										disabled="disabled"
										style="width: 50%; height: 80px; padding: 5px 10px;">${app.content}</textarea>
									<span style="color: red;"></span></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
		</div>
		<c:if test="${type == 3 and app.status == 1}">
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:toRevent(2);" class="lzui-btn lzui-corner-all">确认入库</a>
				<a href="javascript:toRevent(-1);" class="lzui-btn lzui-corner-all">不入库</a>
			</div>
		</c:if>
	</div>
</div>


</body>
</html>
<script>
	var index;
	var type = '${type}';
	$(function() {
		if (type == 1) {
			$('#OA申请').addClass('ui-tabs-current');
		} else {
			$('#物料管理').addClass('ui-tabs-current');
		}
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function selectAll() {
		$('input[name="maId"]').each(function(j, item) {
			if ($('#cAll').is(':checked')) {
				item.checked = true;
			} else {
				item.checked = false;
			}
		});
	}

	function toRevent(status) {

		var msg = '';
		if (status == 2) {
			if ($('input[name="maId"]:checked').length == 0) {
				layer.msg('请勾选要入库的物料');
				return;
			}
			msg = '请确认要入库的仓库！';
		} else {
			msg = '确认不入库物料吗?';
		}
		layer.confirm(msg, {
			btn : [ '确认', '取消' ]
		//按钮
		}, function() {
			$('#status').val(status);
			document.form1.submit();
		});
	}
</script>