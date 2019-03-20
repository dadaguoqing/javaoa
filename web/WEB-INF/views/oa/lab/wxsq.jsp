<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<style>
<!--
.shortInput {
	width: 40px;
}

.redInput {
	border: 1px solid red;
}
-->
</style>

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
		<%@ include file="../../public/hello.jsp"%>

		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/lab/myPcbSqRecord2">点击查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/lab/wxSq" method="post">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<div style="border: 1px solid #2191C0; margin-top: 10px;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }加工工艺要求说明书</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="border-left: none; width: 120px;">文件名称：</td>
							<td style="width: 90px;"><input name="fileName"
								id="fileName" /> <span id="fileNameId"
								style="color: red; font-weight: bold;"></span></td>
							<td class="title" style="width: 90px;">投板时间：</td>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
								Date n = new Date();
								String now = sdf.format(n);
								pageContext.setAttribute("now", now);
							%>
							<td style="width: 200px;"><input type="hidden"
								name="createTime" value="${now }" /> ${now }</td>

							<td class="title" style="border-left: none; width: 90px;">文档目录：</td>
							<td style="border-right: none;" colspan="3"><input
								name="filePath" id="filePath" style="width: 200px;" /></td>

							<%-- 
			<td class="title" style="border-left:none; width:90px;">订单编号：</td>	
			<td style="width:90px;">
				<input name="ddbh" id="ddbh"/>
			</td>	
			--%>
						</tr>
						<tr>
							<td class="title" style="border-left: none; width: 120px;">加工类型：</td>
							<td style="border-right: none;" colspan="7">
								<!-- 				<select name="gyyq" id="gyyq"> --> <!-- 					<option value="新投（新文件）">新投（新文件）</option> -->
								<!-- 					<option value="加做 （文件与上一版完全相同，以下内容只须写数量和交货日期。）">加做 （文件与上一版完全相同，以下内容只须写数量和交货日期。）</option> -->
								<!-- 					<option value="改版 （局部修改请说明修改部位；新文件覆盖旧文件请写明新文件名。）">改版 （局部修改请说明修改部位；新文件覆盖旧文件请写明新文件名。）</option> -->
								<!-- 				</select> --> <label><input id="processType1"
									name="processType" type="checkbox" value="1">PCB加工</label> <label><input
									id="processType2" name="processType" type="checkbox" value="2">焊接加工</label>
								<label><input id="processType3" name="processType"
									type="checkbox" value="3">钢网加工</label> <label><input
									id="processType4" name="processType" type="checkbox" value="4">打白胶加工</label>
								<label><input id="processType5" name="processType"
									type="checkbox" value="5">三防漆加工</label>
							</td>
						</tr>
					</tbody>
				</table>

				<!-- 	PCB加工申请详情 -->
				<div id="PCBprocess" style="display: none;">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							PCB加工详细参数
						</span>
					</div>
					<jsp:include page="processType1.jsp" />
				</div>

				<!-- 	焊接加工申请详情 -->
				<div id="HJprocess"
					style="display: none; background-color: #ccfff5;">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							焊接加工详细参数
						</span>
					</div>
					<jsp:include page="processType2.jsp" />
				</div>

				<!-- 	钢网加工申请详情 -->
				<div id="GWprocess" style="display: none; background-color: #99ffeb">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							钢网加工详细参数
						</span>
					</div>
					<jsp:include page="processType3.jsp" />
				</div>

				<!-- 	打白胶加工申请详情 -->
				<div id="DBJprocess" style="display: none">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							打白胶加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="tableTitle">打白胶工艺要求文档:</td>
							<td><a style="color: red">请确保文档目录下有打白胶工艺要求文档</a></td>
						</tr>
					</table>
				</div>

				<!-- 	三防漆加工申请详情 -->
				<div id="SFQprocess" style="display: none">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							三防漆加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="tableTitle">三防漆工艺要求文档:</td>
							<td><a style="color: red">请确保文档目录下有三防漆工艺要求文档</a></td>
						</tr>
					</table>
				</div>

			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
			</div>
		</form>

		<!-- 	弹出确认表单 -->
		<div id="cfmlay" style="display: none;">

			<div
				style="border: 1px solid #2191C0; margin-top: 0px; width: 675px;">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						申请信息详情：
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="border-left: none; width: 120px;">文件名称：</td>
							<td style="width: 90px;"><span id="sbFileName"></span></td>
							<td class="title" style="width: 90px;">投板时间：</td>

							<td style="border-left: none; width: 200px;"><span
								id="sbCreateTime">${now }</span></td>

							<td class="title" style="border-left: none; width: 90px;">文档目录：</td>
							<td style="width: 90px;"><span id="sbFilePath"></span></td>

						</tr>
					</tbody>
				</table>

				<!-- 	PUB加工 -->
				<div id="sbPubProcess">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							PCB加工申请详细参数
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="border-left: none; width: 105px;">数量：</td>
								<td style=""><span id="sbNumSet"></span> set &nbsp;&nbsp; <span
									id="sbNumUnit"></span> unit</td>
								<td class="title" style="width: 105px;">层数：</td>
								<td style=""><span id="sbCs"></span></td>
								<td class="title" style="border-left: none; width: 120px;">尺寸：</td>
								<td style="border-right: none;"><span id="sbCcChang"></span>
									mm &nbsp;*&nbsp; <span id="sbCcKuang"></span>mm</td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">成品板厚：</td>
								<td style=""><span id="sbCpbh"></span></td>
								<td class="title" style="width: 90px;">材料：</td>
								<td style=""><span id="sbCl"></span></td>
								<td class="title" style="border-left: none; width: 90px;">成品铜铂厚度：</td>
								<td style="border-right: none;">内层：<span id="sbTbNei"></span>
									<br /> 外层：<span id="sbTbWai"></span>
								</td>
							</tr>

							<tr>

								<td class="title" style="border-left: none; width: 90px;">阻
									焊：</td>
								<td style=""><span id="sbZh"></span> <br /> 颜色： <span
									id="sbZhColor"></span></td>
								<td class="title" style="border-left: none; width: 90px;">字符：</td>
								<td style=""><span id="sbZf"></span> <br /> 颜色： <span
									id="sbZfColor"></span></td>
								<td class="title" style="border-left: none; width: 90px;">测试通断：</td>
								<td style="border-right: none;"><span id="sbCstd"></span></td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">阻外形加工方式：</td>
								<td style=""><span id="sbWxjgfs"></span></td>
								<td class="title" style="border-left: none; width: 90px;">金手指要求：</td>
								<td style=""><span id="sbJszyq"></span></td>
								<td class="title" style="border-left: none; width: 90px;">电测报告：</td>
								<td style="border-right: none;"><span id="sbDcbg"></span></td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">阻抗测试报告：</td>
								<td style=""><span id="sbZkcsbg"></span></td>
								<td class="title" style="border-left: none; width: 90px;">成品检验报告：</td>
								<td style=""><span id="sbCpjcbg"></span></td>
								<td class="title" style="border-left: none; width: 90px;">过孔是否覆盖阻焊：</td>
								<td style="border-right: none;"><span id="sbFgzh"></span></td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">表面涂层：</td>
								<td style=""><span id="sbBmtc"></span></td>
								<td class="title" style="border-left: none; width: 90px;">表面涂层厚度：</td>
								<td style=""><span id="sbBmtchd"></span></td>
								<td class="title" style="border-left: none; width: 90px;">阻抗描述：</td>
								<td style="border-right: none;"><span id="sbKzms"></span></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<!-- 		焊接加工 -->
			<div id="sbhjProcess" style="background-color: #ccfff5;">
				<div id="hj01">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							焊接加工详细参数
						</span>
					</div>
					<table class="table1">
						<tr>
							<td class="title" style="border-left: none; width: 105px">焊接类型：</td>
							<td colspan="6"><span id="sbweldType"></span></td>
						</tr>
					</table>
				</div>
				<div id="sbweld">
					<table class="table1">
						<tr>
							<td class="title" style="border-left: none; width: 105px">数量：</td>
							<td width="97px"><span id="sbnum2"></span></td>
							<td class="title" style="border-left: none;width:105px">层数：</td>
							<td width="80px"><span id="sbpiles"></span></td>
							<td class="title" style="border-left: none;width:120px">焊接点数:</td>
							<td>贴片点数:<span id="sbpaster"></span>,插件点数:<span
								id="sbpaster2"></span></td>
						</tr>
						<tr>
							<td class="title" style="border-left: none; width: 105px">焊接工艺要求：</td>
							<td><span id="sbgyType"></span></td>
							<td class="title" style="border-left: none; width: 90px">是否有浮高、卧倒等特殊要求：</td>
							<td><span id="sbhjgy"> </span> <span id="sbhjsm"
								style="color: red"></span></td>
						</tr>
					</table>
				</div>

				<div id="sbbga">
					<table class="table1">
						<tr>
							<td class="title" style="border-left: none; width: 105px">是否有BGA焊接：</td>
							<td><span id="sbBGAType"></span></td>
						</tr>
					</table>
				</div>
				<div id="sbbga2">
					<table class="table1">
						<tr>
							<td class="title" style="border-left: none; width: 105px">是否有BGA焊接：</td>
							<td style=" width: 97px"><span id="sbBGAType2"></span></td>
							<td  style="">
							最多点数:<span id="sbmaxSize"></span>,
							间距:<span id="sbju"></span>mm,
							锡珠直径:<span id="sbxzzj"></span>mm
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!-- 			钢网加工 -->
			<div id="sbgwProcess" style="background-color: #99ffeb">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						钢网加工详细参数
					</span>
				</div>
				<table class="table1">
					<tr>
						<td class="title" style="border-left: none; width: 105px;">数量：</td>
						<td width="97px"><span id="sbNumset2"></span>set</td>
						<td class="title" style="border-left: none; width: 105px;">尺寸：</td>
						<td style="border-left: none; width: 80px"><span
							id="sbccChang2"></span>mm &nbsp;*&nbsp; <span id="sbsize"></span>mm
						</td>
						<td class="title" style="border-left: none; width: 120px;">钢网厚度：</td>
						<td style="border-right: none;"><span id="sbcpbh2"></span></td>
					</tr>
					<tr>
						<td class="title" style="width: 90px;">钢网材料：</td>
						<td style=""><span id="sbcl2"></span></td>
						<td class="title" style="border-left: none; width: 90px;">用途：</td>
						<td style=""><span id="sbbmtc2"> </span></td>
						<td class="title" style="border-left: none; width: 90px;">抛光工艺：</td>
						<td style="border-right: none;"><span id="sbbmtchd2">
						</span></td>
					</tr>
				</table>
			</div>
			<!-- 			打白胶 -->
			<div id="sbdbjProcess">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						打白胶加工详细参数
					</span>
				</div>
				<table class="table1">
					<tr>
						<td class="title" style="border-left: none; width: 105px;">
							打白胶工艺要求文档:</td>
							<td ><a style="color:red">请确保文档目录下有打白胶工艺要求文档</a></td>
					</tr>
				</table>
			</div>
			<!-- 			三防漆 -->
			<div id="sbsfqProcess">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						三防漆加工详细参数
					</span>
				</div>
				<table class="table1">
					<tr>
						<td class="title" style="border-left: none; width: 105px;">
							三防漆工艺要求文档:</td>
							<td ><a style="color:red">请确保文档目录下有三防漆工艺要求文档</a></td>
					</tr>
				</table>
			</div>

		</div>
	</div>
</div>
</body>
</html>
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function() {

		$('a#OA申请').addClass('ui-tabs-current');
		$('#加工申请').addClass('cur');
		$('#bga').hide();
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

		$('#hjgy').click(function() {
			if ($("#hjgy").val() == 2) {
				$('#hjsm').html('请确保目录文档中有焊接加工工艺要求说明书');
			}
		});
		$('#BGAType').click(function() {
			if ($("#BGAType").val() == 2) {
				$('#bga').show();
			} else {
				$('#bga').hide();
			}
		});
		$('#weldType').click(function() {
			if ($("#weldType").val() == 1) {
				$('#weld').css('display', 'none');
			} else {
				$('#weld').css('display', 'block');
			}
		});
		$('#processType1').click(function() {
			if ($("#processType1[type='checkbox']").is(':checked')) {
				$('#PCBprocess').css('display', 'block');

			} else {
				$('#PCBprocess').css('display', 'none');
			}
		});
		$('#processType2').click(function() {
			if ($("#processType2[type='checkbox']").is(':checked')) {
				$('#HJprocess').css('display', 'block');
			} else {
				$('#HJprocess').css('display', 'none');

			}
		});
		$('#processType3').click(function() {
			if ($("#processType3[type='checkbox']").is(':checked')) {
				$('#GWprocess').css('display', 'block');
			} else {
				$('#GWprocess').css('display', 'none');
			}
		});
		$('#processType4').click(function() {
			if ($("#processType4[type='checkbox']").is(':checked')) {
				$('#DBJprocess').css('display', 'block');
			} else {
				$('#DBJprocess').css('display', 'none');
			}
		});
		$('#processType5').click(function() {
			if ($("#processType5[type='checkbox']").is(':checked')) {
				$('#SFQprocess').css('display', 'block');
			} else {
				$('#SFQprocess').css('display', 'none');
			}
		});

		$('#kzms').change(function() {
			var v = $(this).val();
			//alert(v);
			if (v == '见阻抗说明文档') {
				$('#kzmsId').html("请确保文档目录下有阻抗说明文档");

				//$('#kzms').removeClass('redInput');
			} else {
				$('#kzmsId').html("");
				//$('#kzms').addClass('redInput');
			}
		});

		$('#cs').change(function() {
			showCcpl();
		});
	});

	// function checkFileName(){
	// 	var fileName = $('#fileName').val();

	// 	$.post('${ctx}/web/oa/lab/ajaxPcbName?hb?t='+Math.random(),{fileName:fileName},function(data){

	// 		//alert(data);
	// 		var d = eval(data);
	// 		if(d.ret == 'ok'){
	// 			//window.location.href
	// 			$('#fileNameId').html("");
	// 			$('#fileName').removeClass('redInput');
	// 		}else{
	// 			$('#fileNameId').html("该文件名已被使用");
	// 			$('#fileName').addClass('redInput');
	// 		}
	// 	});

	// }

	function showCcpl() {
		var v = $('#cs').val();
		if (v == '双层') {
			showCcpl2(2);
		} else if (v == '4层') {
			showCcpl2(4);
		}
		if (v == '6层') {
			showCcpl2(6);
		}
	}

	function showCcpl2(tid) {
		clearInterval(itv);

		$.layer({
			shade : [ 0.7, '#000' ],
			title : '层次排列',
			type : 1,
			closeBtn : [ 1, true ],
			border : [ 3 ],
			shadeClose : true,
			page : {
				dom : '#' + tid + 'Layers'
			},
			area : [ 'auto', 'auto' ],
			close : function(index) {
				itv = setInterval(ajaxGetDb, len);
			}
		});
	}

	function smtCofirm() {

		$('#sbFileName').html($('#fileName').val());
		$('#sbFilePath').html($('#filePath').val());
		$('#sbGyyq').html($('#gyyq').val());
		$('#sbNumSet').html($('#numSet').val());
		$('#sbNumUnit').html($('#numUnit').val());
		$('#sbCs').html($('#cs').val());
		$('#sbCcChang').html($('#ccChang').val());
		$('#sbCcKuang').html($('#ccKuang').val());
		$('#sbCpbh').html($('#cpbh').val());
		$('#sbCl').html($('#cl').val());
		$('#sbTbNei').html($('#tbNei').val());
		$('#sbTbWai').html($('#tbWai').val());
		$('#sbZh').html($('#zh').val());
		$('#sbZhColor').html($('#zhColor').val());
		$('#sbZf').html($('#zf').val());
		$('#sbZfColor').html($('#zfColor').val());
		$('#sbCstd').html($('#cstd').val());
		$('#sbCstdType').html($('#cstdType').val());
		$('#sbWxjgfs').html($('#wxjgfs').val());
		$('#sbJszyq').html($('#jszyq').val());
		$('#sbDcbg').html($('#dcbg').val());
		$('#sbZkcsbg').html($('#zkcsbg').val());
		$('#sbCpjcbg').html($('#cpjcbg').val());
		$('#sbFgzh').html($('#fgzh').val());
		$('#sbBmtc').html($('#bmtc').val());
		$('#sbBmtchd').html($('#bmtchd').val());
		$('#sbKzms').html($('#kzms').val());
		// 	焊接加工
		$('#sbweldType').html($('#weldType').val() == 1 ? '只焊接阻容' : '全部焊接');
		$('#sbnum2').html($('#num').val());
		$('#sbpiles').html($('#piles').val() == 1 ? '单面' : '双面');
		$('#sbpaster').html($('#paster').val());
		$('#sbpaster2').html($('#paster2').val());
		$('#sbgyType').html($('#gyType').val() == 1 ? '有铅' : '无铅');
		$('#sbBGAType').html($('#BGAType').val() == 1 ? '无' : '有');
		$('#sbBGAType2').html($('#BGAType').val() == 1 ? '无' : '有');
		$('#sbhjgy').html($('#hjgy').val() == 1 ? '无' : '见焊接加工工艺要求说明书');
		$('#sbhjsm').html($('#hjsm').val());
		$('#sbmaxSize').html($('#maxSize').val());
		$('#sbju').html($('#ju').val());
		$('#sbxzzj').html($('#xzzj').val());
		//钢网加工
		$('#sbNumset2').html($('#numSet2').val());
		$('#sbccChang2').html($('#ccChang2').val());
		$('#sbsize').html($('#size').val());
		$('#sbcpbh2').html($('#cpbh2').val());
		$('#sbcl2').html($('#cl2').val());
		$('#sbbmtc2').html($('#bmtc2').val());
		$('#sbbmtchd2').html($('#bmtchd2').val());

		clearInterval(itv);
		// 		pcb
		$('#sbPubProcess').hide();
		// 		焊接
		$('#hj01').hide();
		$('#sbweld').hide();
		$('#sbbga').hide();
		$('#sbbga2').hide();
		// 		钢网
		$('#sbgwProcess').hide();
		//打白胶
		$('#sbdbjProcess').hide();
		//三防漆
		$('#sbsfqProcess').hide();
		

		var height = 300;
		if ($("#processType1[type='checkbox']").is(':checked')) {
			$('#sbPubProcess').show();
			height += 100;
		}
		if ($("#processType2[type='checkbox']").is(':checked')) {
			$('#hj01').show();
			if ($('#weldType').val() == 2) {
				$('#sbweld').show();
				$('#sbbga').show();
			}
			if ($("#BGAType").val() == 2) {
				$('#sbbga2').show();
				$('#sbbga').hide();
			}
			height += 60;
		}
		if ($("#processType3[type='checkbox']").is(':checked')) {
			$('#sbgwProcess').show();
			height += 100;
		}
		if ($("#processType4[type='checkbox']").is(':checked')) {
			$('#sbdbjProcess').show();
			height += 100;
		}
		if ($("#processType5[type='checkbox']").is(':checked')) {
			$('#sbsfqProcess').show();
			height += 100;
		}
		if ($('#weldType').val() == 2) {
			height += 150;
		}
		//弹出表单 	sbPubProcess

		$.layer({
			shade : [ 0.7, '#000' ],
			title : '请确认您要提交的信息',
			btns : 2,
			btn : [ '确定', '取消' ],
			type : 1,
			closeBtn : [ 1, true ],
			border : [ 3 ],
			shadeClose : true,
			fix : false,
			page : {
				dom : '#cfmlay'
			},
			area : [ 'auto', height ],
			no : function(index) {
				itv = setInterval(ajaxGetDb, len);
			},
			yes : function(index) {
				document.form1.submit();
			}
		});
	}
	var checkedNum = 0;
	// 校验填写内容
	function submitForm() {
		checkedNum = 0;
		var fileName = $('#fileName').val();
		var filePath = $('#filePath').val();

		var numSet = $('#numSet').val();
		var numUnit = $('#numUnit').val();
		var ccChang = $('#ccChang').val();
		var ccKuang = $('#ccKuang').val();

		if (!fileName) {
			alert('请填写文件名称');
			return;
		}
		if (!filePath) {
			alert('请填写文档目录');
			return;
		}

		if ($("#processType1[type='checkbox']").is(':checked')) {
			checkedNum += 1;
			if (!numSet) {
				alert('请填写数量（set）');
				return;
			}
			if (!numUnit) {
				alert('请填写数量（unit）');
				return;
			}

			if (!/^\d+$/.test(numSet) || !/^\d+$/.test(numUnit)) {
				alert('数量必须是整数');
				return;
			}

			if (!ccChang) {
				alert('请填写尺寸（长）');
				return;
			}
			if (!ccKuang) {
				alert('请填写尺寸（宽）');
				return;
			}

			if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(ccChang)
					|| !/^[0-9]*(\.[0-9]{1,2})?$/.test(ccKuang)) {
				alert('尺寸必须是有效数字');
				return;
			}
		}

		if ($("#processType2[type='checkbox']").is(':checked')) {
			checkedNum += 1;
			if ($('#weldType').val() == 2) {
				var num = $('#num').val();
				var paster = $('#paster').val();
				var paster2 = $('#paster2').val();
				if (!num) {
					alert('请填写焊接加工数量');
					return;
				}
				if (!paster) {
					alert('请填写焊接加工贴片点数');
					return;
				}
				if (!paster2) {
					alert('请填写焊接加工插件点数');
					return;
				}
				if (!/^\d+$/.test(num)) {
					alert('焊接加工数量必须是整数');
					return;
				}
				if (!/^\d+$/.test(paster)) {
					alert('焊接加工贴片点数必须是整数');
					return;
				}
				if (!/^\d+$/.test(paster2)) {
					alert('焊接加工插件点数必须是整数');
					return;
				}
				if ($('#BGAType').val() == 2) {
					var maxSize = $('#maxSize').val();
					var ju = $('#ju').val();
					var xzzj = $('#xzzj').val();
					if (!maxSize) {
						alert('请填写焊接加工最多点数');
						return;
					}
					if (!ju) {
						alert('请填写焊接加工间距');
						return;
					}
					if (!xzzj) {
						alert('请填写焊接加工锡珠直径');
						return;
					}
					if (!/^\d+$/.test(maxSize)) {
						alert('焊接加工最多点数必须是整数');
						return;
					}
					if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(ju)) {
						alert('焊接加工间距必须是有效数字');
						return;
					}
					if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(xzzj)) {
						alert('焊接加工锡珠直径必须是有效数字');
						return;
					}
				}
			}
		}

		if ($("#processType3[type='checkbox']").is(':checked')) {
			checkedNum += 1;
			var numSet2 = $('#numSet2').val();
			var ccChang2 = $('#ccChang2').val();
			var size = $('#size').val();
			if (!numSet2) {
				alert('请填写钢网加工数量');
				return;
			}
			if (!/^\d+$/.test(numSet2)) {
				alert('钢网加工数量必须是整数');
				return;
			}
			if (!ccChang2) {
				alert('请填写钢网加工尺寸（长）');
				return;
			}
			if (!size) {
				alert('请填写钢网加工尺寸（宽）');
				return;
			}

			if (!/^[0-9]*(\.[0-9]{1,2})?$/.test(ccChang2)
					|| !/^[0-9]*(\.[0-9]{1,2})?$/.test(size)) {
				alert('钢网加工尺寸必须是有效数字');
				return;
			}
		}

		if ($("#processType4[type='checkbox']").is(':checked')) {
			checkedNum += 1;
		}
		if ($("#processType5[type='checkbox']").is(':checked')) {
			checkedNum += 1;
		}

		if (checkedNum == 0) {
			alert("请至少选择一项加工类型");
			return;
		}
		smtCofirm();
		//document.form1.submit();
		// 	if(confirm("请确认申请信息")){
		// 		document.form1.submit();
		// 	}else{
		// 		return;
		// 	}
	}
</script>