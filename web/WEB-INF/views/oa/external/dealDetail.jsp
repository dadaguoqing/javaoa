<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;申请详情
		</div>
			<div class="navTitle2" url="${ctx }/web/oa/external/dealList">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>


		<form name="form1" action="${ctx }/web/oa/external/handDeal" id="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<input type="hidden" id="id" name="id" value="${ea.id}"/>
			<div style="border: 1px solid #2191C0;">
				<div id="content"></div>
					<div class="tableTitle" style="padding: 10px 20px;">
							<span> <img
								src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								处理详情
							</span>
					</div>
					<table class="table1">
						<c:forEach items="${types}" var="type">
							<c:if test="${type == '1' }">
								<tr>
									<td class="title" style="width: 150px">PCB加工费用:</td>
									<td style="width: 150px"><input name="pcbCost" id="pcbCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title" style="width: 150px">交期:</td>
									<td style="width: 150px">
										<input name="pcbDate" id="pcbDate" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="pcbPdf" name="pcbPdf"></td>
								</tr>
							</c:if>
							
							<c:if test="${type == '2' }">
								<tr>
									<td class="title" style="width: 150px">焊接加工费用:</td>
									<td><input name="weldCost"  id="weldCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title">交期:</td>
									<td>
										<input name="weldDate" id="weldDate" value="${beginDate }" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="weldPdf" name="weldPdf"></td>
								</tr>
							</c:if>
							
							<c:if test="${type == '3' }">
								<tr>
									<td class="title" style="width: 150px">钢网加工费用:</td>
									<td><input name="steelCost" id="steelCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title">交期:</td>
									<td>
										<input name="steelDate" id="steelDate" value="${beginDate }" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="steelPdf" name="steelPdf"></td>
								</tr>
							</c:if>
							
							<c:if test="${type == '4' }">
								<tr>
									<td class="title" style="width: 150px">打白胶加工费用:</td>
									<td><input name="glueCost" id="glueCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title">交期:</td>
									<td>
										<input name="glueDate" id="glueDate" value="${beginDate }" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="gluePdf" name="gluePdf"></td>
								</tr>
							</c:if>
							
							<c:if test="${type == '5' }">
								<tr>
									<td class="title" style="width: 150px">三防漆加工费用:</td>
									<td><input name="paintCost" id="paintCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title">交期:</td>
									<td>
										<input name="paintDate" id="paintDate" value="${beginDate }" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="paintPdf" name="paintPdf"></td>
								</tr>
							</c:if>
							
							<c:if test="${type == '6' }">
								<tr>
									<td class="title" style="width: 150px">亚克力加工费用:</td>
									<td><input name="acrylicCost" id="acrylicCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title">交期:</td>
									<td>
										<input name="acrylicDate" id="acrylicDate" value="${beginDate }" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="acrylicPdf" name="acrylicPdf"></td>
								</tr>
							</c:if>
							<c:if test="${type == '7' }">
								<tr>
									<td class="title" style="width: 150px">机箱加工费用:</td>
									<td><input name="chassisCost" id="chassisCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title">交期:</td>
									<td>
										<input name="chassisDate" id="chassisDate" value="${beginDate }" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="chassisPdf" name="chassisPdf"></td>
								</tr>
							</c:if>
							<c:if test="${type == '8' }">
								<tr>
									<td class="title" style="width: 150px">线束加工费用:</td>
									<td><input name="pencilCost" id="pencilCost" style="width: 100px" onblur="countMoney()">元</td>
									<td class="title">交期:</td>
									<td>
										<input name="pencilDate" id="pencilDate" value="${beginDate }" style="width:115px" 
										class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
									</td>
									<td class="title" style="width: 150px">费用明细附件:</td>
									<td><input type="file" id="pencilPdf" name="pencilPdf"></td>
								</tr>
							</c:if>
						</c:forEach>
						<tr>
							<td class="title" style="width: 150px">加工费用合计:</td>
							<td><input name="totalCost" id="totalCost" style="width: 100px" value="0" readonly="readonly">元</td>
						</tr>
					</table>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交处理结果</a>
			</div>
				
		</form>
	</div>
</div>
</body>
</html>

<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$('#OA处理').addClass('ui-tabs-current');
		$('#外协加工审批').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getContent();
	});
	
	function getContent() {
		$.get("${ctx}/web/oa/external/getContent", {id : $('#id').val()}, function(result) {
			$('#content').html(result);
		})
	}
	
	function countMoney() {
		var pcbCost = $('#pcbCost').val();
		var weldCost = $('#weldCost').val();
		var steelCost = $('#steelCost').val();
		var glueCost = $('#glueCost').val();
		var paintCost = $('#paintCost').val();
		var acrylicCost = $('#acrylicCost').val();
		var chassisCost = $('#chassisCost').val();
		var pencilCost = $('#pencilCost').val();
		if(!pcbCost) {
			pcbCost = 0;
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
		var totalMoney = Number(pcbCost) + Number(weldCost) + Number(steelCost) + Number(glueCost) 
				+ Number(paintCost) + Number(acrylicCost) + Number(chassisCost) + Number(pencilCost);
		$('#totalCost').val(totalMoney.toFixed(2));
	}
	
	function submitForm() {
		countMoney();
		var externalType = '${ea.externalType}';
		var types = externalType.split(",");
		for (var i in types) {
			if(types[i] == '1') {
				if(testDouble('pcbCost', 'PCB加工费用')
						|| testStr('pcbDate', 'PCB加工交期')) {
					return;
				}
				if(testStr('pcbPdf', 'PCB加工费用明细附件')) {
					return;
				} else if(!testFileFormat('pcbPdf', pdf_file, 'PCB加工费用明细附件')){
					return;
				}
				continue;
			}
			if(types[i] == '2') {
				if(testDouble('weldCost', '焊接加工费用')
						|| testStr('weldDate', '焊接加工交期')) {
					return;
				}
				if(testStr('weldPdf', '焊接加工费用明细附件')) {
					return;
				} else if(!testFileFormat('weldPdf', pdf_file, '焊接加工费用明细附件')){
					return;
				}
				continue;
			}
			
			if(types[i] == '3') {
				if(testDouble('steelCost', '钢网加工费用') 
						|| testStr('steelDate', '钢网加工交期')) {
					return;
				}
				if(testStr('steelPdf', '钢网加工费用明细附件')) {
					return;
				} else if(!testFileFormat('steelPdf', pdf_file, '钢网加工费用明细附件')){
					return;
				}
				continue;
			}
			
			if(types[i] == '4') {
				console.log(1);
				if(testDouble('glueCost', '打白胶加工费用')
						|| testStr('glueDate', '打白胶加工交期')) {
					return;
				}
				if(testStr('gluePdf', '打白胶加工费用明细附件')) {
					return;
				} else if(!testFileFormat('gluePdf', pdf_file, '打白胶加工费用明细附件')){
					return;
				}
				continue;
			}
			
			if(types[i] == '5') {
				if(testDouble('paintCost', '三防漆加工费用')
						|| testStr('paintDate', '三防漆加工交期')) {
					return;
				}
				if(testStr('paintPdf', '三防漆加工费用明细附件')) {
					return;
				} else if(!testFileFormat('paintPdf', pdf_file, '三防漆加工费用明细附件')){
					return;
				}
				continue;
			}
			
			if(types[i] == '6') {
				if(testDouble('acrylicCost', '亚克力加工费用')
						|| testStr('acrylicDate', '亚克力加工交期')) {
					return;
				}
				if(testStr('acrylicPdf', '亚克力加工费用明细附件')) {
					return;
				} else if(!testFileFormat('acrylicPdf', pdf_file, '亚克力加工费用明细附件')){
					return;
				}
				continue;
			}
			
			if(types[i] == '7') {
				if(testDouble('chassisCost', '机箱加工费用')
						|| testStr('chassisDate', '机箱加工交期')) {
					return;
				}
				if(testStr('chassisPdf', '机箱加工费用明细附件')) {
					return;
				} else if(!testFileFormat('chassisPdf', pdf_file, '机箱加工费用明细附件')){
					return;
				}
				continue;
			}
			if(types[i] == '8') {
				if(testDouble('pencilCost', '线束加工费用')
						|| testStr('pencilDate', '线束加工交期')) {
					return;
				}
				if(testStr('pencilPdf', '线束加工费用明细附件')) {
					return;
				} else if(!testFileFormat('pencilPdf', pdf_file, '线束加工费用明细附件')){
					return;
				}
				continue;
			}
		}
		$('#form1').submit();
	}
</script>