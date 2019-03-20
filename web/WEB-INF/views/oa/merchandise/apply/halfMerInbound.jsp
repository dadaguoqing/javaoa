<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">

	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['4']}" var="cur">
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
		<%@ include file="../../../public/hello.jsp"%>
		<c:if test="${param.msg1 == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/merchandise/apply/recordList/3/1?curPage=1">查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/merchandise/apply/saveHalfMerInbound" id="form1"
			method="post" >
			<input type="hidden" value="${submitCode}" name="submitCode" /> 
			<input type="hidden" id="number1" name="number" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }半成品入库申请单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 100px">申请人：</td>
							<td>${loginUser.name }</td>
							<td class="title" style="width: 100px">所属部门：</td>
							<td>${loginUserDept.name }</td>
							<td class="title">申请时间：</td>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
								Date n = new Date();
								String now = sdf.format(n);
								pageContext.setAttribute("now", now);
								String tdy = now.substring(0, 11);
							%>
							<td style="border-right: none;" colspan="3">${now}</td>
						</tr>
						<tr>
							<td class="title">编号：</td>
							<td id="numberStr" colspan="3"><input type="hidden" id="applyCode" name="applyCode"></td>
							<td class="title">预计入库时间：</td>
							<td >
<!-- 							name="startDate2" -->
								<%@ include file="../../public/sealDate.jsp" %>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						半成品入库详情
					</span>
				</div>
				<table class="table1" id='cdTable'>
					<tbody id="data">
						<tr>
							<td class="title" style="text-align: center; width: 50px;">序号</td>
							<td class="title" style="text-align: center; width: 150px;">产品型号</td>
							<td class="title" style="text-align: center; width: 50px;">数量</td>
							<td class="title" style="text-align: center; width: 50px;">单位</td>
							<td class="title" style="text-align: center; width: 100px;">生产信息代码</td>
							<td class="title" style="text-align: center; width: 130px;">生产周号</td>
							<td class="title" style="text-align: center; width: 80px;">入库来源</td>
							<td class="title" style="text-align: center; width: 80px;">备注</td>
							<td class="title" style="text-align: center; width: 50px;">操作</td>
						</tr>
						<tr class="data">
							<td align="center" style="width: 50px"></td>
							<td align="center">
								<select name="" id="productModel">
									<option value="-1">=请选择产品型号=</option>
								</select>
							</td>
							<td align="center" >
								<input  type="number" value="1" id="quantity" style="width: 50px" min="0" onchange="testnum(this)" >
							</td>
							<td align="center">
								<input name="" id="unit" style="width: 50px" value="PCS" disabled="disabled">
							</td>
							<td align="center">
								<input name="" id="productCode" style="width: 120px">
							</td>
							<td align="center">
								<input name="" id="weekCode" style="width: 120px">
							</td>
							<td align="center">
								<select name="" id="inboundSource">
									<option value="-1">=请选择入库来源=</option>
								</select>
							</td>
							<td align="center">
								<input name="" id="content" style="width: 120px" value="无">
							</td>
							<td align="center">
								<a onclick="add(this)" href="javascript:void(0)">添加</a>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
			</div>
		</form>
	</div>
</div>

</body>
</html>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/submitForm.js"></script>
<script>
	var mode = '${mode}';
	var data;
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#半成品入库申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getCode();
		getProductType();
		getSuppliers();
	});

	function getCode() {
		$.get("getApplyCode", {
			str : 'BCPRK',
			id : 11
		}, function(result) {
			// 部署删除
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$('#applyCode').val(result);
			$('#numberStr').html(result);
		});
	}
	
	function getProductType() {
		$.get("getProductCode",function(result) {
			// 部署删除
			if (mode == 1) {
				result = JSON.parse(result);
			}
			var option = '';
			$.each(result,function(i,obj){
				option += '<option value="'+  obj.productModel +'">'+ obj.productModel +'</option>';
			});
			$('#productModel').append(option);
		});
	}
	
	function getSuppliers() {
		$.get("getSuppliers",function(result) {
			// 部署删除
			if (mode == 1) {
				result = JSON.parse(result);
			}
			var option = '';
			$.each(result,function(i,obj){
				option += '<option value="'+  obj.name +'">'+ obj.name +'</option>';
			});
			$('#inboundSource').append(option);
		});
	}
	
	function testnum(obj) {
		var reg = /^\d+$/;
		var num = $(obj).val();
		if (!num) {
			layer.msg('不能为空！');
			$(obj).val(1);
			return;
		}
		if (!reg.test(num)) {
			layer.msg('格式错误，请填写非负整数！');
			$(obj).val(1);
			return;
		}
	}


	var i = 0;

	function add(obj) {
		i++;
		var reg = /^[1-9]\d*$/;
		var productModel = $('#productModel').val();
		if (productModel == -1) {
			layer.msg('请选择产品型号！');
			return;
		}
		var quantity = $('#quantity').val();
		if (!quantity) {
			layer.msg('请填写数量！');
			return;
		}
		var productCode = $('#productCode').val();
		if (!productCode) {
			layer.msg('请填写生成信息代码！');
			return;
		}
		var weekCode = $('#weekCode').val();
		if (!weekCode) {
			layer.msg('请填写生产周号！');
			return;
		}
		var inboundSource = $('#inboundSource').val();
		if (inboundSource == -1) {
			layer.msg('请选择入库来源！');
			return;
		}
		var content = $('#content').val();
		if(!content) {
			content = '无';
		}
		tr = '<tr>';
		tr += '<td name="index" align="center"></td>';
		tr += '<td class="limitText" title="'+productModel+'">'
				+ productModel
				+ '<input name="productModel" value="'+productModel+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+quantity+'">'
				+ quantity
				+ '<input name="quantity" value="'+quantity+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="PCS">PCS'
				+ '<input name="unit" value="PCS" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+productCode+'">'
				+ productCode
				+ '<input name="productCode" value="'+productCode+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+weekCode+'">'
				+ weekCode
				+ '<input name="weekCode" value="'+weekCode+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+inboundSource+'">'
				+ inboundSource
				+ '<input name="inboundSource" value="'+inboundSource+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+content+'">'
				+ content
				+ '<input name="content" value="'+content+'" type="hidden" /></td>';
		tr += '<td align="center"><a onclick="del(this)" href="javascript:void(0)">删除</a></td>';
		tr += '</tr>';
		$('#data').append(tr);
		sortIndex();
		$('#productModel').val(-1);
		$('#quantity').val(1);
		$('#productCode').val('');
		$('#weekCode').val('');
		$('#inboundSource').val(-1);
		$('#content').val('无');
	}

	function del(obj) {
		$(obj).parent().parent().remove();
		sortIndex();
	}

	function sortIndex() {
		$.each($('td[name="index"]'), function(i, obj) {
			$(obj).html(i + 1);
		});
	}

	function submitForm() {
		var bt = $('#bt').val();
		if (!bt) {
			layer.msg('请选择预计入库时间！');
			return;
		}

		if ($('td[name="index"]').length == 0) {
			layer.msg('请添加入库信息');
			return;
		}
		$('#form1').submit();
		
// 		var str = '<table class="table1" style="background-color:rgb(238, 255, 238)"><tr align="center">';
// 		str += '<td class="l-td-header" style="width: 50px">序号</td>';
// 		str += '<td class="l-td-header" style="width: 150px">产品型号</td>';
// 		str += '<td class="l-td-header" style="width: 50px">数量</td>';
// 		str += '<td class="l-td-header" style="width: 50px">单位</td>';
// 		str += '<td class="l-td-header" style="width: 120px">生成信息代码</td>';
// 		str += '<td class="l-td-header" style="width: 120px">生产周号</td>';
// 		str += '<td class="l-td-header" style="width: 80px">入库来源</td>';
// 		str += '<td class="l-td-header" style="width: 120px">外箱编号</td>';
// 		str += '<td class="l-td-header" style="width: 120px">备注</td>';
// 		str += '</tr>';
// 		var data = $('#data').clone();
// 		var dataChild = data.children();
// 		// 删除最后一行
// 		dataChild.each(function(i, obj) {
// 			if (i < 2) {
// 				obj.remove();
// 			}
// 			obj.lastChild.remove();
// 		});
// 		layer.open({
// 			title : '<p style="font-weight:bold">请确认成品入库申请信息</p>',
// 			content : str + data.html() + '</table>',
// 			area : [ '900px', '' ],
// 			btn : [ '确定', '取消' ],
// 			yes : function() {
// 			}
// 		});
	}
</script>