<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
	.red {
		color: red;
	}
</style>
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
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
		<%@ include file="../../public/hello.jsp"%>
		<c:if test="${param.msg1 == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/external/applyList">查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/external/saveExternalApply" id="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" /> <input
				type="hidden" id="number1" name="number" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }外协加工申请单</span>
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
							<td>
								<!-- hh表示12小时制，HH表示24小时制 -->
								<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日 HH时mm分"  />
							</td>
							<td class="title">编号：</td>
							<td id="numberStr"></td>
						</tr>
						<tr>
							<td class="title">加工事由：</td>
							<td colspan="5"><input id="content" name="content" style="width: 98%;"></td>
							<td class="title">项目名称/项目号：</td>
							<td ><input id="projectName" name="projectName" style="width: 98%;"></td>
							
						</tr>
						<tr>
							<td class="title">加工类型：</td>
							<td colspan="7">
								<input type="checkbox" id="pcb" name="type" value="1" onchange="showDiv(this)" />
								<label for="pcb">PCB加工&nbsp;&nbsp;</label>
								<input type="checkbox" id="weld" name="type" value="2" onchange="showDiv(this)" />
								<label for="weld">焊接加工&nbsp;&nbsp;</label>
								<input type="checkbox" id="steel" name="type" value="3"  onchange="showDiv(this)"/>
								<label for="steel">钢网加工&nbsp;&nbsp;</label>
								<input type="checkbox" id="glue" name="type" value="4" onchange="showDiv(this)" />
								<label for="glue">打白胶加工&nbsp;&nbsp;</label>
								<input type="checkbox" id="paint" name="type" value="5" onchange="showDiv(this)" />
								<label for="paint">三防漆加工&nbsp;&nbsp;</label>
								<input type="checkbox" id="acrylic" name="type" value="6" onchange="showDiv(this)" />
								<label for="acrylic">亚克力加工&nbsp;&nbsp;</label>
								<input type="checkbox" id="chassis" name="type" value="7" onchange="showDiv(this)" />
								<label for="chassis">机箱加工&nbsp;&nbsp;</label>
								<input type="checkbox" id="pencil" name="type" value="8" onchange="showDiv(this)" />
								<label for="pencil">线束加工&nbsp;&nbsp;</label>
							</td>
						</tr>
						<tr>	
							<td class="title">备注： </td>
							<td class="red" colspan="7">以下有（*）标注为必填项，所有小数均保留小数点后两位</td>
						</tr>
					</tbody>
				</table>
				
				<div  id="pcbTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							PCB加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;">加工数量：<span class="red">*</span></td>
							<td><input type="text" id="pcbNum" name="pcbNum" style="width: 100px">PCS </td>
							<td class="title" style="width: 180px;" >PCB加工工艺说明书：<span class="red">*</span></td>
							<td ><input type="file" id="pcbDescript" name="pcbDescript" > </td>
						</tr>
						<tr>
							<td class="title" style="width: 180px;">Gerber文件：<span class="red">*</span></td>
							<td ><input type="file" id="pcbGerber" name="pcbGerber"> </td>
							<td class="title" style="width: 180px;">阻抗说明：</td>
							<td ><input type="file" id="impedanceDescript" name="impedanceDescript"></td>
						</tr>
						<tr>
							<td class="title">工期说明及其他要求说明：</td>
							<td colspan="7"><a href="${ctx}${mc}" target="_blank">查看PCB加工工期说明</a></td>
						</tr>
					</table>
				</div>
				
				<div  id="weldTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							焊接加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;">加工数量：<span class="red">*</span></td>
							<td><input type="text" id="weldNum" name="weldNum" style="width: 100px">PCS </td>
							<td class="title" style="width: 100px;">BOM单：<span class="red">*</span></td>
							<td ><input type="file" id="weldBom" name="weldBom" style="width: 200px;"> </td>
							<td class="title" style="width: 180px;">Gerber文件：<span class="red">*</span></td>
							<td ><input type="file" id="weldGerber" name="weldGerber" style="width: 100px;"> </td>
						</tr>
						<tr>
							<td class="title" >坐标文件：<span class="red">*</span></td>
							<td ><input type="file" id="weldCoordinate" name="weldCoordinate" style="width: 200px;"> </td>
							<td class="title">丝印文件：<span class="red">*</span></td>
							<td ><input type="file" id="weldSilkScreen" name="weldSilkScreen" style="width: 200px;"> </td>
							<td class="title" style="width: 180px;">焊接加工工艺文件：</td>
							<td ><input type="file" id="weldDescript" name="weldDescript" style="width: 100px;"> </td>
						</tr>
						<tr>
							<td class="title">焊接加工工期说明：</td>
							<td colspan="7"><a href="${ctx}${mc2}" target="_blank">查看焊接加工工期说明</a></td>
						</tr>
					</table>
				</div>
				
				<div  id="steelTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							钢网加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;" >加工数量：<span class="red">*</span></td>
							<td><input type="text" id="steelNum" name="steelNum" style="width: 100px;">PCS </td>
							<td class="title">尺寸：<span class="red">*</span></td>
							<td>
								<input type="text" id="steelSize1" name="steelSize1" style="width: 100px;">mm &nbsp; * &nbsp;
								<input type="text" id="steelSize2" name="steelSize2" style="width: 100px;">mm
							</td>
							<td class="title">钢网厚度：<span class="red">*</span></td>
							<td><input type="text" id="steelThinkness" name="steelThinkness" style="width: 100px;">mm</td>
						</tr>
						<tr>
							<td class="title" style="width: 180px;">钢网材料：<span class="red">*</span></td>
							<td>
								<select id="steelMaterial" name="steelMaterial">
									<option value="金属">金属</option>
									<option value="塑料">塑料</option>
								</select>
							</td>
							<td class="title">用途：<span class="red">*</span></td>
							<td>
								<select id="steelUse" name="steelUse">
									<option value="锡膏">锡膏</option>
									<option value="红胶">红胶</option>
									<option value="胶水">胶水</option>
								</select>
							</td>
							<td class="title">抛光工艺：<span class="red">*</span></td>
							<td>
								<select id="steelPolishing" name="steelPolishing">
									<option value="打磨抛光">打磨抛光</option>
									<option value="电解抛光">电解抛光</option>
								</select>
							</td>
						</tr>
					</table>
				</div>
				
				<div  id="glueTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							打白胶加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;">打白胶工艺要求文档：<span class="red">*</span></td>
							<td colspan="3" ><input type="file" id="glueDescript" name="glueDescript"></td>
						</tr>
					</table>
				</div>
				
				<div  id="paintTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							三防漆加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;">三防漆工艺要求文档：<span class="red">*</span></td>
							<td ><input type="file" id="paintDescript" name="paintDescript"></td>
						</tr>
					</table>
				</div>
				
				<div  id="acrylicTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							亚克力加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;">加工数量：<span class="red">*</span></td>
							<td><input type="text" id="acrylicNum" name="acrylicNum" style="width: 100px">PCS</td>
							<td class="title" >加工文件：<span class="red">*</span></td>
							<td ><input type="file" id="acrylicCad" name="acrylicCad"></td>
						</tr>
					</table>
				</div>
				
				<div  id="chassisTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							机箱加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;">加工数量：<span class="red">*</span></td>
							<td><input type="text" id="chassisNum" name="chassisNum" style="width: 100px">PCS</td>
							<td class="title" >加工文件：<span class="red">*</span></td>
							<td ><input type="file" id="chassisCad" name="chassisCad"></td>
						</tr>
					</table>
				</div>
				
				<div  id="pencilTable" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							线束加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="width: 180px;">加工数量：<span class="red">*</span></td>
							<td><input type="text" id="pencilNum" name="pencilNum" style="width: 100px">PCS</td>
							<td class="title">加工文件：<span class="red">*</span></td>
							<td ><input type="file" id="pencilCad" name="pencilCad"></td>
						</tr>
					</table>
				</div>
				
				<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							加工费用预算
						</span>
					</div>
					<table class="table1">
						<tr ><td colspan="10" style="padding: 1 1">
							<span id="pcbCostBody" style="display: none;padding-left: 10px;">
									<label>PCB板加工：<span class="red">*</span></label>
									<input type="text" id="pcbCost" name="pcbCost" style="width: 100px;" onblur="countMoney()">元
							</span>
							
							<span id="weldCostBody" style="display: none; padding-left: 10px;">
									<label>电子元器件：<span class="red">*</span></label>
									<input type="text" id="componentCost" name="componentCost" style="width: 100px;" onblur="countMoney()">元
									<label style="padding-left: 10px;">焊接加工：<span class="red">*</span></label>
									<input type="text" id="weldCost" name="weldCost" style="width: 100px;" onblur="countMoney()">元
							</span>
							
							<span id="steelCostBody" style="display: none;padding-left: 10px;">
									<label>钢网加工：<span class="red">*</span></label>
									<input type="text" id="steelCost" name="steelCost" style="width: 100px;" onblur="countMoney()">元
							</span>
							<span id="glueCostBody" style="display: none;padding-left: 10px;">
									<label>打白胶加工：<span class="red">*</span></label>
									<input type="text" id="glueCost" name="glueCost" style="width: 100px;" onblur="countMoney()">元
							</span>
							<span id="paintCostBody" style="display: none;padding-left: 10px;">
									<label>三防漆加工：<span class="red">*</span></label>
									<input type="text" id="paintCost" name="paintCost" style="width: 100px;" onblur="countMoney()">元
							</span>
							<span id="acrylicCostBody" style="display: none;padding-left: 10px;">
									<label>亚克力加工：<span class="red">*</span></label>
									<input type="text" id="acrylicCost" name="acrylicCost" style="width: 100px;" onblur="countMoney()">元
							</span>
							<span id="chassisCostBody" style="display: none;padding-left: 10px;">
									<label>机箱加工：<span class="red">*</span></label>
									<input type="text" id="chassisCost" name="chassisCost" style="width: 100px;" onblur="countMoney()">元
							</span>
							<span id="pencilCostBody" style="display: none;padding-left: 10px;">
									<label>线束加工：<span class="red">*</span></label>
									<input type="text" id="pencilCost" name="pencilCost" style="width: 100px;" onblur="countMoney()">元
							</span>
								</td></tr>
							<tr>
								<td class="title" style="width: 180px;">
									预算合计：
								</td>
								<td>
									<input type="text" id="totalCost" name="totalCost" style="width: 100px;" readonly="readonly" value="0">元
								</td>
							</tr>
							
							<tr>
								<td class="title" style="width: 180px;">是否加急：</td>
								<td>
									<span id="pcbUrgent" style="display: none;">
										<input type="checkbox" id="pcb2" name="isUrgent" value="1" />
										<label for="pcb2">PCB加工&nbsp;&nbsp;</label>
									</span>
									<span id="weldUrgent" style="display: none;">
										<input type="checkbox" id="weld2" name="isUrgent" value="2" />
										<label for="weld2">焊接加工&nbsp;&nbsp;</label>
									</span>
									<span id="steelUrgent" style="display: none;">
										<input type="checkbox" id="steel2" name="isUrgent" value="3"/>
										<label for="steel2">钢网加工&nbsp;&nbsp;</label>
									</span>
									<span id="glueUrgent" style="display: none;">
										<input type="checkbox" id="glue2" name="isUrgent" value="4"  />
										<label for="glue2">打白胶加工&nbsp;&nbsp;</label>
									</span>
									<span id="paintUrgent" style="display: none;">
										<input type="checkbox" id="paint2" name="isUrgent" value="5" />
										<label for="paint2">三防漆加工&nbsp;&nbsp;</label>
									</span>
									<span id="acrylicUrgent" style="display: none;">
										<input type="checkbox" id="acrylic2" name="isUrgent" value="6" />
										<label for="acrylic2">亚克力加工&nbsp;&nbsp;</label>
									</span>
									<span id="chassisUrgent" style="display: none;">
										<input type="checkbox" id="chassis2" name="isUrgent" value="7"/>
										<label for="chassis2">机箱加工&nbsp;&nbsp;</label>
									</span>
									<span id="pencilUrgent" style="display: none;">
										<input type="checkbox" id="pencil2" name="isUrgent" value="8"/>
										<label for="pencil2">线束加工&nbsp;&nbsp;</label>
									</span>
								</td>
							</tr>
					</table>
				
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:void(0);" onclick="submitForm()"   class="lzui-btn lzui-corner-all">提交申请</a>
			</div>
		</form>
	</div>
</div>

</body>
</html>
<script>
	var mode = '${mode}';
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#外协加工申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getCode();
	});

	function getCode() {
		$.get("getApplyCode", {
			str : 'WXJG',
			type : 16
		}, function(result) {
			// 部署删除
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$('#number1').val(result);
			$('#numberStr').html(result);
		});
	}
	
	function showDiv(obj) {
		var id = $(obj).attr("id");
		if($(obj).is(':checked')) {
			$('#' + id + "Table").show();
			$('#' + id + "CostBody").show();
			$('#' + id + "Urgent").show();
		} else {
			$('#' + id + "Table").hide();
			$('#' + id + "CostBody").hide();
			$('#' + id + "Urgent").hide();
		}
	}
	
	function countMoney() {
		var pcbCost = $('#pcbCost').val();
		var componentCost = $('#componentCost').val();
		var weldCost = $('#weldCost').val();
		var steelCost = $('#steelCost').val();
		var glueCost = $('#glueCost').val();
		var paintCost = $('#paintCost').val();
		var acrylicCost = $('#acrylicCost').val();
		var chassisCost = $('#chassisCost').val();
		var pencilCost = $('#pencilCost').val();
		var totalMoney = 0;
		if(!pcbCost) {
			pcbCost = 0;
		} 
		if(!componentCost) {
			componentCost = 0;
		} 
		if(!weldCost) {
			weldCost = 0;
		} 
		if(!steelCost) {
			steelCost = 0;
		} 
		if(!glueCost) {
			glueCost = 0;
		} 
		if(!paintCost) {
			paintCost = 0;
		} 
		if(!acrylicCost) {
			acrylicCost = 0;
		} 
		if(!chassisCost) {
			chassisCost = 0;
		} 
		if(!pencilCost) {
			pencilCost = 0;
		} 
		totalMoney = Number(pcbCost) + Number(componentCost) + Number(weldCost) + Number(steelCost)
				+ Number(glueCost) + Number(paintCost) + Number(acrylicCost) 
				+ Number(chassisCost) + Number(pencilCost);
		$('#totalCost').val(totalMoney.toFixed(2));
	}
	
	function submitForm() {
		
		if(testStr('content', '加工事由')) {
			return;
		}
		if(testStr('projectName', '项目名称/项目号')) {
			return;
		}
		
		if($("input[type='checkbox']:checked").length == 0) {
			layer.alert('请选择加工类型！',{icon : 2});
			return;
		}
		
		var types = $("input[type='checkbox']:checked");
		
		for(var i in types){
			// PCB验证
			if('1' == types[i].value) {
				if(testStr('pcbDescript', 'PCB加工工艺说明书') 
						|| testStr('pcbGerber', 'PCB加工Gerber文件')
						|| testNum('pcbNum', 'PCB加工数量')
						|| testDouble('pcbCost', 'PCB加工费用')) {
					return;
				}
			}
			
			// 焊接加工验证
			if('2' == types[i].value) {
				if(testNum('weldNum', '焊接加工数量') || testStr('weldBom', '焊接加工BOM单')
						|| testStr('weldGerber', '焊接加工Gerber文件')
						|| testStr('weldCoordinate', '焊接加工坐标文件')
						|| testStr('weldSilkScreen', '焊接加工丝印文件')
						|| testDouble('componentCost', '电子元器件费用')
						|| testDouble('weldCost', '焊接加工费用')) {
					return;
				}
			}
			// 钢网加工
			if('3' == types[i].value) {
				if(testNum('steelNum', '钢网加工数量')
						|| testDouble('steelSize1', '钢网加工尺寸1')
						|| testDouble('steelSize2', '钢网加工尺寸2')
						|| testDouble('steelThinkness', '钢网厚度')
						|| testStr('steelMaterial', '钢网材料')
						|| testStr('steelUse', '钢网用途')
						|| testStr('steelPolishing', '抛光工艺')
						|| testDouble('steelCost', '钢网加工费用')) {
					return;
				}
			}
			// 打白胶
			if('4' == types[i].value) {
				if(testStr('glueDescript', '打白胶工艺要求文档')
						|| testDouble('glueCost', '打白胶加工费用')) {
					return;
				}
			}
			// 三防漆
			if('5' == types[i].value) {
				if(testStr('paintDescript', '三防漆工艺要求文档')
						|| testDouble('paintCost', '三防漆加工费用')) {
					return;
				}
			}
			// 亚克力
			if('6' == types[i].value) {
				if(testNum('acrylicNum', '亚克力加工数量')
						|| testStr('acrylicCad', '亚克力加工文件')
						|| testDouble('acrylicCost', '亚克力加工费用')) {
					return;
				}
			}
			
			if('7' == types[i].value) {
				if(testNum('chassisNum', '机箱加工数量')
						|| testStr('chassisCad', '机箱加工文件')
						|| testDouble('chassisCost', '机箱加工费用')) {
					return;
				}
			}
			
			if('8' == types[i].value) {
				if(testNum('pencilNum', '线束加工数量')
						|| testStr('pencilCad', '线束加工文件')
						|| testDouble('pencilCost', '线束加工费用')) {
					return;
				}
			}
		}
		layer.open({
			content : '请再次确认：以上所有附件符合《硬件设计开发规范》等相关规定。',
			btn : ['确定', '取消'],
			icon : 1,
			yes : function() {
				$('#form1').submit();
			}
		});
		
	}

</script>