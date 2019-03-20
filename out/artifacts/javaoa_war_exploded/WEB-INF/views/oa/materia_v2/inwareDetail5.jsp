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
		<c:choose>
			<c:when test="${type == 1 }">
				<div class="navTitle2" url="${ctx }/web/oa/materia_v2/inwareRecordList">
					<img src="${ctx }/resources/images/item.png"
						style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
				</div>
			</c:when>
			<c:when test="${type == 2 }">
				<div class="navTitle2" url="${ctx }/web/oa/materia_v2/checkInbound">
					<img src="${ctx }/resources/images/item.png"
						style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
				</div>
			</c:when>
			<c:when test="${type == 3 }">
				<div class="navTitle2" url="${ctx }/web/oa/materia_v2/toInware?type=2">
					<img src="${ctx }/resources/images/item.png"
						style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
				</div>
			</c:when>
			<c:when test="${type == 4 }">
				<div class="navTitle2" url="${ctx }/web/oa/materia_v2/checkInboundRecord">
					<img src="${ctx }/resources/images/item.png"
						style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
				</div>
			</c:when>
		</c:choose>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<c:if test="${not empty errormsg}">
			<div class="lzui-error">${errormsg}</div>
		</c:if>
		<div id='print'>
			<form name="form1" action="${ctx }/web/oa/materia_v2/inwareApprove2"
				method="post" onsubmit="return beforeSubmit();" enctype="multipart/form-data">
				<input type="hidden" value="${submitCode}" name="submitCode" /> 
				<input type="hidden" value="${ma.id}" name="id" id="hid" />
				<div style="border: 1px solid #2191C0; margin-top: 10px; width: 99%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }物料入库申请单</span>
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
								<td class="title">申请人：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].name }</td>
								<td class="title">所属部门：</td>
								<td>${AllUsers[ma.empId].deptName }</td>
								<td class="title">申请时间：</td>
								<td>${ma.daystr }</td>
							</tr>
								<tr>
									<td class="title">编号：</td>
									<td>${ma.requisitionCode }</td>
									<td class="title">物料类型：</td>
									<td >${ma.purchaseCode}</td>
									<td class="title">质检员：</td>
									<td >${jyr.name}</td>
								</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							物料详细信息
						</span>
					</div>
					<table class="table1">
						<c:if test="${type == 2 || type == 4}">
							<tr>
								<td class="title" style="text-align: center; width: 50px;">序号</td>
								<td class="title" style="width: 100px;text-align: center;">物料编码</td>
								<td class="title" style="text-align: center;width: 120px;">品名分类</td>
								<td class="title" style="text-align: center;width: 120px;">品牌</td>
								<td class="title" style="text-align: center;width: 120px;">规格型号</td>
								<td class="title" style="text-align: center;width: 120px;">封装</td>
								<td class="title" style="text-align: center;width: 60px;">单位</td>
								<td class="title" style="text-align: center;width: 120px;">供应商</td>
								<td class="title" style="text-align: center;width: 80px;">采购数量</td>
								<td class="title" style="text-align: center;width: 100px;">检验结果</td>
							</tr>
							<c:forEach items="${list}" var="cur" varStatus="i">
								<tr>
									<td style="text-align:center;">${i.count}</td>
									<td title="${cur.maCode }">${cur.maCode }</td>
									<td title="${cur.classify }" align="left">${cur.classify }</td>
									<td title="${cur.brand }" align="left">${cur.brand }</td>
									<td title="${cur.spec }" align="left">${cur.spec }</td>
									<td title="${cur.package1 }" align="left">${cur.package1 }</td>
									<td title="${cur.unit }">${cur.unit }</td>
									<td title="${cur.supplier }">${cur.supplier}</td>
									<td title="${cur.buynum }">${cur.buynum }</td>
									<td >
										<c:if test="${ma.purchaseCode == '生产物料' }">
											<c:if test="${empty cur.link }">
												<input  name="ids" value="${cur.id}" type="hidden" />
												<select name="link">
													<option value="合格">合格</option>
													<option value="不合格">不合格</option>
													<option value="挑选使用">挑选使用</option>
													<option value="特采">特采</option>
												</select>
											</c:if>
											<c:if test="${not empty cur.link }">
												${cur.link}
											</c:if>
										</c:if>
									</td>
							</c:forEach>
						</c:if>
						
						<c:if test="${type == 3}">
							<tr>
								<td style="text-align: center; width: 40px;">
									<select id="qx" onchange="qx2()">
											<option value="0"></option>
											<c:forEach items="${whs}" var="wh">
												<option value="${wh.id}">${wh.warehouse}</option>
											</c:forEach>
										</select>
								</td>
								<td class="title" style="text-align: center; width: 60px;">序号</td>
								<td class="title" style="width: 100px;text-align: center;">物料编码</td>
								<td class="title" style="text-align: center;width: 120px;">品名分类</td>
								<td class="title" style="text-align: center;width: 120px;">品牌</td>
								<td class="title" style="text-align: center;width: 120px;">规格型号</td>
								<td class="title" style="text-align: center;width: 120px;">封装</td>
								<td class="title" style="text-align: center;width: 60px;">单位</td>
								<td class="title" style="text-align: center;width: 120px;">供应商</td>
								<td class="title" style="text-align: center;width: 80px;">采购数量</td>
								<td class="title" style="text-align: center;width: 100px;">检验结果</td>
								<td class="title" style="text-align: center;width: 100px;">实际入库数量</td>
							</tr>
							<c:forEach items="${list}" var="cur" varStatus="i">
								<c:if test="${cur.link != '不合格' }">
									<tr>
										<td>
											<select name="warehouseId" >
												<c:forEach items="${whs}" var="wh">
												<option value="${wh.id}">${wh.warehouse}</option>
											</c:forEach>
											</select>
										</td>
										<td style="text-align:center;">${i.count}</td>
										<td style="">
											<input name="ids" value="${cur.id}" type="hidden" />
											<input name="maCode" value="${cur.maCode}" />
											<input name="price" value="${cur.price}" type="hidden" />
										</td>
										<td style="" align="left">${cur.classify }</td>
										<td style="" align="left">${cur.brand }</td>
										<td style="" align="left">${cur.spec }</td>
										<td style="" align="left">${cur.package1 }</td>
										<td style="">${cur.unit }</td>
										<td style="">${cur.supplier}</td>
										<td style="">${cur.buynum }</td>
										<td style="">${cur.link}</td>
										<td>
											<input name="realNum" style="width:100px">
										</td>
									</tr>
								</c:if>
								<c:if test="${cur.link == '不合格' }">
									<tr>
										<td>
											<select name="warehouseId" >
											</select>
										</td>
										<td style="text-align:center;">${i.count}</td>
										<td style="">${cur.maCode}</td>
										<td style="" align="left">${cur.classify }</td>
										<td style="" align="left">${cur.brand }</td>
										<td style="" align="left">${cur.spec }</td>
										<td style="" align="left">${cur.package1 }</td>
										<td style="">${cur.unit }</td>
										<td style="">${cur.supplier}</td>
										<td style="">${cur.buynum }</td>
										<td style=""><span style="color:red">${cur.link}</span></td>
										<td>
										</td>
									</tr>
								</c:if>
							</c:forEach>
						</c:if>
							<tr>
								<td class="title" colspan="2">来料检验报告照片：</td>
								<td colspan="10"><c:if test="${ not  empty  ma.url2}">
										<a href="${ctx}/upload${ma.url2}" target="_blank">查看来料检验报告</a>
									</c:if> <c:if test="${ empty  ma.url2}">
										暂无附件
									</c:if></td>
							</tr>
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
								<td style="border-right: none;">
									<textarea name="bz" id="bz" disabled="disabled"
										style="width: 50%; height: 80px; padding: 5px 10px;">${ma.content}
									</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							审批记录
						</span>
					</div>
					<table class="table1">
						<tr>
								<c:choose>
									<c:when test="${ma.purchaseCode == '研发物料'}">
										<td style="width: 10%;">质检员处理结果</td>
										<td style="width: 15%;"><span style="color: gray;">此次处理不需要经过此流程</span></td>
									</c:when>
									<c:when test="${ma.status == 1}">
										<td style="width: 10%;">质检员处理结果<span style="color: red;">
										（${AllUsers[ma.currentId].name}）</span></td>
										<td style="width: 15%;"><span style="color: green;">正在处理</span></td>
									</c:when>
									<c:when test="${not empty approve1}">
										<td style="width: 10%;">质检员处理结果
											<span style="color: red;">（${AllUsers[approve1.approveId].name}）</span></td>
										<td style="width: 10%;">${approve1.approveOpinion}</td>
									</c:when>
								</c:choose>
							<td style="width: 15%;"><span style="color: green;">${approve1.approveResult}</span></td>
							<td style="width: 10%;">${approve1.approveDate}</td>
						</tr>
						<tr>
								<c:choose>
									<c:when test="${ma.status == 3}">
										<td style="width: 10%;">仓库管理员处理结果 <span style="color: red;">
										（${AllUsers[ma.currentId].name}）</span></td>
										<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									</c:when>
									<c:when test="${not empty approve3}">
										<td style="width: 10%;">仓库管理员处理结果 <span style="color: red;">
										（${AllUsers[approve3.approveId].name}）</span></td>
										<td style="width: 15%;"><span style="color: green;">${approve3.approveResult}</span></td>
									</c:when>
									<c:when test="${ma.status < 3}">
										<td style="width: 10%;">仓库管理员处理结果</td>
										<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
									</c:when>
								</c:choose>
							<td style="width: 10%;">${approve2.approveOpinion}</td>
							<td style="width: 10%;">${approve2.approveDate}</td>
						</tr>
					</table>
				<c:if test="${type != 4}">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							审批意见
						</span>
					</div>

					<table class="table1">
						<tbody>
							<c:if test="${ma.purchaseCode == '生产物料' and type == 2}">
								<tr>
									<td class="title" >
										来料检验报告：
									</td>
									<td>
										<input name="file1" id="file1" type="file" title="请上传来料检验照片">
									</td>
								</tr>
							</c:if>
							
							<c:if test="${ma.purchaseCode != '生产物料' || type != 2}">
								<div id="zp" style="display: none">
										<input name="file1" id="file1" type="file" title="请上传来料检验照片">
										</div>
							</c:if>
							<tr>
								<td style="width: 20%;">审批意见： <select id="tg" name="sp"
									onchange="yjChanged()">
										<option value="通过" style="color: green;" selected="selected">通过</option>
										<option value="不通过" style="color: red;">不通过</option>
								</select>
								</td>
								<td style="border-right: none;">补充意见：
									<input name="opinion" id="opinion" style="width: 50%;" value="ok"/> 
									<span style="color: red;">不超过50个字符</span>
								</td>
							</tr>
						</tbody>
					</table>
			</c:if>
				</div>
			<c:if test="${type != 4}">
				<div class="" style="margin-top: 10px; margin-bottom: 20px;">
					<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交处理结果</a>
				</div>
			</c:if>
			</form>
		</div>
	</div>
</div>

</body>
</html>
<script>
	var index;
		var type = '${type}';

	$(function() {
		if (type == 1 || type == 3) {
			$('#物料管理').addClass('ui-tabs-current');
		} else {
			$('#OA处理').addClass('ui-tabs-current');
		}
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function qx2() {
		$.each($('select[name="warehouseId"]'),function(i, obj) {
			$(this).val($('#qx').val());
		})
	}
	
	function selectAll() {
		$('input[name="maId"]').each(function(j, item) {
			if ($('#cAll').is(':checked')) {
				item.checked = true;
			} else {
				item.checked = false;
			}
		});
	}

	function printHtml() {
		var body1 = "<div align='center' style='width:80%'>"
				+ $('#print').html() + "</div>";
		$('body').html(body1);
		window.print();
	}

	function cancle() {
		var id = $('#hid').val();
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "cancleApply",
			data : {
				type : "apply",
				id : id
			},
			success : function(data) {
				layer.alert(data);
				location.reload(location.href);
			},
			error : function() {
				layer.alert('出错');
			}
		});
	}
	
	function inware() {
		var flag = false;
		$('input[name="maCode"]').each(function(i, obj) {
			var item = obj.value;
			if (!item || item.length != 12) {
				flag = true;
			}
		});
		if (flag) {
			layer.alert('物料编码为空或长度不为12');
			return;
		}

		var flag1 = true;
		$('input[name="buynum"]').each(function(i, obj) {
			var item = obj.value;
			var reg = /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/;
			if (!reg.test(item)) {
				layer.alert('数量格式错误');
				flag1 = true;
			}
			if (!item || Number(item) <= 0) {
				layer.alert('数量不能为空或小于0');
				flag1 = true;
			}
		});

		if (flag1) {
			return;
		}

		if ($('#warehouseId').val() == 0) {
			layer.alert('请选择入库仓库');
			return;
		}
		layer.open({
			content : '确认入库？',
			btn : [ '确认', '取消' ],
			yes : function() {
				form1.action = "${ctx }/web/oa/materia_v2/doInware";
				document.form1.submit();
			}
		});
	}

	function submitForm() {
		var sp = $('#tg').val();
		if (sp == '通过') {
			var opinion = $('#opinion').val();
			if (!opinion) {
				layer.alert('请填写补充意见');
				return ;
			}
			
			if('${ma.purchaseCode}' == '生产物料' && '${type}' == 2){
				if(!$('#file1').val()) {
					layer.alert('请上传来料检验报告!', {
						icon : 2
					});
					return ;
				}
			}
			
			if('${type}' == 3) {
				var warehouseIds = $('select[name="warehouseId"]');
				for(var i = 0; i < warehouseIds.length; i++) {
					if(warehouseIds[i].value == 0) {
						layer.alert('仓库不能为空！',{
							icon : 2
						});
						return;
					}
				}
				
				var codes = $('input[name="maCode"]');
				for(var i = 0; i < codes.length; i++) {
					if(!codes[i].value) {
						layer.alert('物料编码不能为空！',{
							icon : 2
						});
						return;
					}
				}
				
				var reg = /^([0-9]{1,}[.][0-9]*)$/;
				var reg2 = /^([0-9]{1,})$/;
				var nums = $('input[name="realNum"]');
				for(var i = 0; i < nums.length; i++) {
					var num = nums[i].value;
					if(!num) {
						layer.alert('实际入库数量不能为空！',{
							icon : 2
						});
						return;
					}
					if ((!reg.test(num) && !reg2.test(num)) || num < 0) {
						layer.alert('实际入库数量格式错误！', {
							icon : 2
						});
						return;
					}
				}
			}
		}	
		document.form1.submit();
	}
</script>