<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>



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
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>
		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc; width: 960px;">
			<div
				style="width: 120px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">物料详情</div>
		</div>


		<form name="form1" action="${ctx }/web/oa/materia_v2/updateinfo"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" /> <input
				type="hidden" value="${id}" name="id" />
			<div style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
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
							<td style=""><input name="materiaCode" id="materiaCode"
								value="${list.materiaCode}" /></td>
							<td class="title" style="text-align: center; width: 10%;">
								品名分类：</td>
							<td style=""><input name="classfiy" id="classfiy"
								value="${list.classfiy }"></td>
							<td class="title" style="text-align: center; width: 10%;">
								品牌：</td>
							<td style=""><input name="brand" id="brand"
								value="${list.brand }"></td>
							<td class="title" style="text-align: center; width: 10%;">
								规格型号：</td>
							<td style=""><input name="spec" id="spec"
								value="${list.spec }"></td>
						</tr>
						<tr>
							<td class="title" style="text-align: center; width: 10%;">
								封装：</td>
							<td style=""><input name="package1" id="package1"
								value="${list.package1 }"></td>
							<td class="title" style="text-align: center; width: 10%;">
								单位：</td>
							<td style=""><input name="unit" id="unit"
								value="${list.unit }"></td>
							<td class="title" style="text-align: center; width: 10%;">
								功能区分：</td>
							<td style=""><input name="diff" id="diff"
								value="${list.diff }"></td>
							<td class="title" style="text-align: center; width: 10%;">
								功能简称（中）：</td>
							<td style=""><input name="functionChina" id="functionChina"
								value="${list.functionChina }"></td>
						</tr>
						<tr>
							<td class="title" style="text-align: center; width: 100px;">
								功能简称（英）：</td>
							<td style="" colspan="3"><input name="functionEnglish"
								id="functionEnglish" value="${list.functionEnglish }"></td>
							<td class="title" style="text-align: center; width: 100px;">
								供应商）：</td>
							<td style=""><input name="supplier" id="supplier"
								value="${list.supplier }" colspan="3"></td>
<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
<!-- 								照片：</td> -->
<%-- 							<td style="" colspan="5"><c:if test="${not empty list.url}"> --%>
<%-- 									<a href="${ctx}/photo/${list.url }" target="_blank">查看图片</a> --%>
<%-- 								</c:if> <c:if test="${ empty list.url}"> --%>
<!-- 									<a style="color: red">未上传图片</a> -->
<%-- 								</c:if> <input type="file" id="file" name="file" /></td> --%>
						</tr>
						<!-- 						<tr> -->
						<!-- 							<td class="title" style="text-align: center; width: 10%;"> -->
						<!-- 								芯片研发中心：</td> -->
						<%-- 							<td style="">${list.warehouse1 }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 10%;"> -->
						<!-- 								工业控制项目：</td> -->
						<%-- 							<td style="">${list.warehouse2 }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 10%;"> -->
						<!-- 								深圳技术部：</td> -->
						<%-- 							<td style="">${list.warehouse3 }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								总库存：</td> -->
						<%-- 							<td style="">${list.warehouse1+list.warehouse2+list.warehouse3 }</td> --%>
						<!-- 						</tr> -->
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
						<%-- 							<td><input id="mininum" value="${mi.mininum }" --%>
						<!-- 								name="mininum" /></td> -->
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								附件</td> -->
						<%-- 							<td style="" ><c:if test="${not empty list.url2}"> --%>
						<%-- 									<a href="${ctx}/upload/${list.url2}" >下载附件</a> --%>
						<%-- 								</c:if> <c:if test="${ empty list.url2}"> --%>
						<!-- 									<a style="color: red">未上传附件</a> -->
						<%-- 								</c:if> <input type="file" id="file1" name="file1" /></td> --%>
						<!-- 						</tr> -->
						<!-- 												单批次采购量 小包数量 起订数量 订货周期 -->
						<!-- 						<tr> -->
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								单批次采购量：</td> -->
						<%-- 							<td><input id="noce" value="${mi.once }" name="once" /></td> --%>
						<%-- 														<td style="">${mi.once }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								小包数量：</td> -->
						<%-- 							<td><input id="spNum" value="${mi.spNum }" name="spNum" /></td> --%>
						<%-- 														<td style="">${mi.spNum }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								起订数量：</td> -->
						<%-- 							<td><input id="pcsNum" value="${mi.pcsNum }" name="pcsNum" /></td> --%>
						<%-- 														<td style="">${mi.pcsNum }</td> --%>
						<!-- 							<td class="title" style="text-align: center; width: 100px;"> -->
						<!-- 								订货周期：</td> -->
						<%-- 							<td><input id="oc" value="${mi.oc }" name="oc" /></td> --%>
						<%-- 														<td style="">${mi.oc }</td> --%>
						<!-- 						</tr> -->
					</tbody>
				</table>
			</div>
			<div>
				<a onclick="modify()" class="lzui-btn lzui-corner-all"
					style="padding: 1px 15px; margin: 10px 0 10px 0">确认</a>
			</div>
		</form>
	</div>

</div>

</body>
</html>
<script>
	var index;

	$(function() {
		$('#物料管理').addClass('ui-tabs-current');
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function modify() {
		var modify = '${modify}';
		if (modify == 1) {
			var materiaCode = $('#materiaCode').val();
			if (!materiaCode) {
				alert('请输入物料编码');
				return;
			}
			if (materiaCode.length != 12) {
				alert('物料编码长度必须为12');
				return;
			}
		}
// 		var mininum = $('#mininum').val();
// 		var noce = $('#noce').val();
// 		var spNum = $('#spNum').val();
// 		var pcsNum = $('#pcsNum').val();
// 		var oc = $('#oc').val();

// 		var classfiy = $('#classfiy').val();
// 		var brand = $('#brand').val();
// 		var spec = $('#spec').val();
// 		var package1 = $('#package1').val();
// 		var unit = $('#unit').val();
// 		var functionChina = $('#functionChina').val();
// 		var functionEnglish = $('#functionEnglish').val();
// 		var diff = $('#diff').val();
// 		var supplier = $('#supplier').val();
// 		var num1 = /^([0-9]{1,}[.][0-9]*)$/;
// 		var num2 = /^([0-9]{1,})$/;
// 		if (!classfiy) {
// 			alert('品名分类不能为空');
// 			return;
// 		}
// 		if (!brand) {
// 			alert('品牌不能为空');
// 			return;
// 		}
// 		if (!spec) {
// 			alert('	规格型号不能为空');
// 			return;
// 		}
// 		if (!package1) {
// 			alert('封装不能为空');
// 			return;
// 		}
// 		if (!unit) {
// 			alert('单位不能为空');
// 			return;
// 		}
// 		if (!diff) {
// 			alert('功能区分不能为空');
// 			return;
// 		}
// 		if (!functionChina) {
// 			alert('功能简称（中）不能为空');
// 			return;
// 		}
// 		if (!functionEnglish) {
// 			alert('功能简称（英）不能为空');
// 			return;
// 		}
// 		if (!supplier) {
// 			alert('供应商不能为空');
// 			return;
// 		}

// 		if (!mininum) {
// 			alert('最低库存不能为空');
// 			return;
// 		}
// 		if (!num1.test(mininum) && !num2.test(mininum)) {
// 			alert('最低库存必须为整数');
// 			return;
// 		}

// 		if (!noce) {
// 			alert('单批次采购量不能为空');
// 			return;
// 		}
// 		if (!num1.test(noce) && !num2.test(noce)) {
// 			alert('单批次采购量必须为整数');
// 			return;
// 		}

// 		if (!spNum) {
// 			alert('小包数量不能为空');
// 			return;
// 		}
// 		if (!num1.test(spNum) && !num2.test(spNum)) {
// 			alert('小包数量必须为整数');
// 			return;
// 		}

// 		if (!pcsNum) {
// 			alert('起订数量不能为空');
// 			return;
// 		}
// 		if (!num1.test(pcsNum) && !num2.test(pcsNum)) {
// 			alert('起订数量必须为整数');
// 			return;
// 		}

// 		if (!oc) {
// 			alert('订货周期不能为空');
// 			return;
// 		}
// 		if (!num1.test(oc) && !num2.test(oc)) {
// 			alert('订货周期必须为整数');
// 			return;
// 		}

		document.form1.submit();
	}
</script>