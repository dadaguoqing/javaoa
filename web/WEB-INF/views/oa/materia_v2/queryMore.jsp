<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
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
		<div class="navTitle2" url="${ctx }/web/oa/materia_v2/query">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">

		<c:if test="${not empty queryMsg}">
			<div class="lzui-error">${queryMsg}</div>
		</c:if>
		
		
		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc; width: 960px;">
			<div
				style="width: 120px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">物料详情</div>
		</div>

		<form name="form1" action="${ctx }/web/oa/materia_v2/queryMore2"
			method="post" onsubmit="return beforeSubmit();">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<input type="hidden" value="${list.id}" name="id" />
			<input type="hidden" value="${whId}" name="whId" />
			<div style="border: 1px solid #2191C0; margin-top: 10px; wid=th: 90%;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }物料详情单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						物料详细信息
					</span>
				</div>

				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="text-align: center; width: 10%;">
								物料编码：</td>
							<td style="width: 10%;">${list.materiaCode }</td>
							<td class="title" style="text-align: center; width: 10%;">
								品名分类：</td>
							<td style="width: 10%;">${list.classfiy }</td>
							<td class="title" style="text-align: center; width: 5%;">
								品牌：</td>
							<td style="width: 10%;">${list.brand }</td>
							<td class="title" style="text-align: center; width: 5%;">
								规格型号：</td>
							<td style="width: 10%;">${list.spec }</td>
						</tr>
						<tr>
							<td class="title" style="text-align: center; width: 5%;">
								封装：</td>
							<td style="">${list.package1 }</td>
							<td class="title" style="text-align: center; width: 10%;">
								单位：</td>
							<td style="">${list.unit }</td>
							<td class="title" style="text-align: center; width: 10%;">
								功能区分：</td>
							<td style="">${list.diff }</td>
							<td class="title" style="text-align: center; width: 10%;">
								功能简称（中）：</td>
							<td style="">${list.functionChina }</td>
						</tr>
						<tr>
							<td class="title" style="text-align: center; width: 100px;">
								功能简称（英）：</td>
							<td style="">${list.functionEnglish }</td>
							<td class="title" style="text-align: center; width: 100px;">
								供应商：</td>
							<td style="">${list.supplier }</td>
							<td class="title" style="text-align: center; width: 100px;">
								物料说明书：</td>
							<td style="" colspan="3"><c:if test="${not empty list.url}">
									<a href="${ctx}/upload${list.url }" target="_blank">查看物料说明书</a>
								</c:if> <c:if test="${ empty list.url}">
									<a style="color: red">未物料说明书</a>
								</c:if></td>
						</tr>
						<tr>
							<td class="title" style="text-align: center; width: 100px;">
								价格：</td>
							<td style="">
								<fmt:formatNumber value="${empty price ? 0.0 : price}" pattern="0.00" maxFractionDigits="2"/>元
							</td>
						</tr>
						<!-- 							<td class="title" style="text-align: center; width: 10%;"> -->
						<!-- 								芯片研发中心：</td> -->
						<%-- 							<td style="">${list.warehouse1 }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 10%;"> -->
						<!-- 								工业控制项目：</td> -->
						<%-- 							<td style="">${list.warehouse2 }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 10%;"> -->
						<!-- 								深圳技术部：</td> -->
						<%-- 							<td style="">${list.warehouse3 }</td> --%>
						<%-- 							<c:if test="${isManager == 1 }"> --%>
						<!-- 								<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 									总库存：</td> -->
						<%-- 								<td style="">${list.warehouse1+list.warehouse2+list.warehouse3 }</td> --%>
<!-- 												</tr> -->
						<!-- 						<tr> -->
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								年度初期数量：</td> -->
						<%-- 							<td style="">${stock }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								入库数量：</td> -->
						<%-- 							<td style="">${in }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								出库数量：</td> -->
						<%-- 							<td style="">${out }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								期末结存：</td> -->
						<%-- 							<td style="">${in-out }</td> --%>
						<!-- 						</tr> -->
						<!-- 						<tr> -->
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								平均单价：</td> -->
						<%-- 							<td style="">${list.price }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								库存金额：</td> -->
						<%-- 							<td style="">${list.price * (in-out) }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								最低库存：</td> -->
						<%-- 							<td style="">${mi.mininum }</td> --%>
						<%-- 							</c:if> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								附件：</td> -->
						<%-- 							<td style=""><c:if test="${not empty list.url2}"> --%>
						<%-- 									<a href="${ctx}/upload/${list.url2}">下载附件</a> --%>
						<%-- 								</c:if> <c:if test="${ empty list.url2}"> --%>
						<!-- 									<a style="color: red">未上传附件</a> -->
						<%-- 								</c:if></td> --%>
						<!-- 						</tr> -->
						<%-- 						<c:if test="${isManager ==1 }"> --%>
						<!-- 													单批次采购量 小包数量 起订数量 订货周期 -->
						<!-- 							<tr> -->
						<!-- 								<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 									单批次采购量：</td> -->
						<%-- 								<td style="">${mi.once }</td> --%>
						<!-- 								<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 									小包数量：</td> -->
						<%-- 								<td style="">${mi.spNum }</td> --%>
						<!-- 								<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 									起订数量：</td> -->
						<%-- 								<td style="">${mi.pcsNum }</td> --%>
						<!-- 								<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 									订货周期：</td> -->
						<%-- 								<td style="">${mi.oc }&nbsp;天</td> --%>
						<!-- 							</tr> -->
						<%-- 						</c:if> --%>
					</tbody>
				</table>
			</div>
			<c:if test="${xg != 0}">
				<div>
					<a onclick="modify()" class="lzui-btn lzui-corner-all"
						style="padding: 1px 15px; margin: 10px 0 10px 0">修改</a>
				</div>
			</c:if>
		</form>
	</div>

</div>

<div id="data" style="display: none">
<form name="form2" id="form2" action="${ctx }/web/oa/materia_v2/updateMateriaInfo" method="post" enctype="multipart/form-data">
	<div class="main-header" id="">
					<span style="color: #eee;">修改物料详情</span>
	</div>
	<table class="table1">
		<tbody>
			<tr>
				<td class="title" style="width: 200px">物料编码：</td>
				<td >
					<input id="materiaCode" name="materiaCode" style="width:300px">
					<input id="id" name="id" type="hidden">
					<input id="whId" name="whId" type="hidden">
				</td>
			</tr>
			<tr>
				<td class="title">品名分类：</td>
				<td ><input id="classfiy" name="classfiy" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">品牌：</td>
				<td ><input id="brand" name="brand" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">规格型号：</td>
				<td ><input id="spec" name="spec" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">封装：</td>
				<td ><input id="package1" name="package1" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">单位：</td>
				<td ><input id="unit" name="unit" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">功能区分：</td>
				<td ><input id="diff" name="diff" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">功能简称（中）：</td>
				<td ><input id="functionChina" name="functionChina" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">功能简称（英）：</td>
				<td ><input id="functionEnglish" name="functionEnglish" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">供应商：</td>
				<td ><input id="supplier" name="supplier" style="width:300px"></td>
			</tr>
			<tr>
				<td class="title">物料说明书：</td>
				<td class="sms" colspan="3">
				</td>
			</tr>
			<tr>
				<td colspan="2"><span style="color:red">注：物料说明书必须为PDF文档</span></td>
			</tr>
		</tbody>
	</table>
</form>
</div>

</body>
</html>
<script>
	var index;

	$(function() {
		$('#OA查询').addClass('ui-tabs-current');
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function modify() {
// 		document.form1.submit();
		layer.open({
			btn : ['确认','取消'] ,
			title : false ,
			closeBtn : false ,
			content : $('#data').html() ,
			area : ['500px',''] ,
			success : function(layero, index) {
				layero.find('#id').val('${list.id }');
				layero.find('#whId').val('${whId }');
				layero.find('#materiaCode').val('${list.materiaCode }');
				layero.find('#classfiy').val('${list.classfiy }');
				layero.find('#brand').val('${list.brand }');
				layero.find('#spec').val('${list.spec }');
				layero.find('#package1').val('${list.package1 }');
				layero.find('#unit').val('${list.unit }');
				layero.find('#diff').val('${list.diff }');
				layero.find('#functionChina').val('${list.functionChina }');
				layero.find('#functionEnglish').val('${list.functionEnglish }');
				layero.find('#supplier').val('${list.supplier }');
				layero.find('#supplier').val('${list.supplier }');
				layero.find('.sms').append('<input id="url" name="file" type="file" >');
			} ,
			yes : function(index, layero) {
				$(layero).find('#form2').submit();
// 				layer.close(layer.index);
// 				// 刷新父页面
// 				window.parent.location.reload();
			}
		});
	}

	function clearMa() {
		var materiaCode = '${list.materiaCode }';
		var warehouse = '${warehouse}';
		var rs = confirm('确定将物料编码为 \'' + materiaCode + '\' 的物料库存清零吗？');
		if (rs == true) {
			$.ajax({
				type : "POST",
				dataType : "json",
				url : 'clearMateria',
				data : {
					materiaCode : materiaCode,
					warehouse : warehouse
				},
				success : function(data) {
					alert(data);
					window.location.reload();
				},
				error : function(data) {
					alert('失败');
				}
			});
		}
	}
	function deleteMa() {
		var materiaCode = '${list.materiaCode }';
		var warehouse = '${warehouse}';
		var rs = confirm('确定删除物料编码为 \'' + materiaCode + '\' 的物料吗？');
		if (rs == true) {
			$
					.ajax({
						type : "POST",
						dataType : "json",
						url : 'deleteMateria',
						data : {
							materiaCode : materiaCode,
							warehouse : warehouse
						},
						success : function(data) {
							alert(data);
							var result = (data == "操作成功") ? window.location.href = "${ctx }/web/oa/materia/query"
									: window.location.reload();
						},
						error : function(data) {
							alert('查询失败');
						}
					});
		}
	}
</script>