<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style>
	.mytitle td {
		text-aligin : center;
		font-weight : bold;
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
		</div>	<div class="navTitle2" url="${ctx }/web/oa/materia_v2/dealInwareList">
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
		<div id="print">
			<form name="form1" action="${ctx }/web/oa/materia_v2/purApprove"
				method="post" onsubmit="return beforeSubmit();">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${app.id}" name="id" id="hid" /> <input
					type="hidden" value="cg" name="rtype" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 100%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }物料采购单</span>
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
								<td class="title" style="width: 120px;">采购单编号：</td>
								<td><span>${app.purchaseCode }</span></td>
							</tr>
							<tr>
								<td class="title" style="text-align: right; width: 15%;">
									采购依据：<span style="color: red;"></span>
								</td>
								<td colspan="3"><span id="qgCode">${app.requisitionCode }</span></td>
							</tr>
							<tr>
								<td class="title" style="text-align: right; width: 15%;">
									采购事由：</td>
								<td colspan="7">${app.reason }</td>
							</tr>
						</tbody>
					</table>


					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							采购详情
						</span>
					</div>
					<table class="table1" id='cdTable'>
						<tbody>
							<tr class="mytitle">
								<td class="" style="width: 80px;">物料编码</td>
								<td class="" style="width: 80px">品名分类</td>
								<td class="" style="width: 80px">品牌</td>
								<td class="" style="width: 80px">规格型号</td>
								<td class="" style="width: 80px">封装</td>
								<td class="" style="width: 40px">单位</td>
								<td class="" style="width: 60px">需求数</td>
								<c:if test="${loginUser.id != 1}">
									<td class="" style="width: 60px">库存数</td>
									<td class="" style="width: 80px">需求日期</td>
									<td class="" style="width: 80px">计划日期</td>
								</c:if>
								<td class="" style="width: 80px">采购数量</td>
								<td class="" style="width: 50px">单价</td>
								<td class="" style="width: 80px">其他费用</td>
								<td class="" style="width: 60px">供应商</td>
								<td class="" style="width: 40px">链接</td>
							</tr>
							<c:forEach items="${list}" var="materia" varStatus="index">
								<tr>
									<td class="limitText" title="${materia.maCode}">${materia.maCode}</td>
									<td class="limitText" title="${materia.classify}">${materia.classify}</td>
									<td class="limitText" title="${materia.brand}">${materia.brand}</td>
									<td class="limitText" title="${materia.spec}">${materia.spec}</td>
									<td class="limitText" title="${materia.package1}">${materia.package1}</td>
									<td class="limitText" title="${materia.unit}">${materia.unit}</td>
									<td class="limitText" title="${materia.needNum}">${materia.needNum}</td>
									<c:if test="${loginUser.id != 1}">
										<td class="limitText" title="${materia.stockNum}">${materia.stockNum}</td>
										<td class="limitText" title="${materia.needDate}">${materia.needDate}</td>
										<td class="limitText" title="${materia.useDate}">${materia.useDate}</td>
									</c:if>
									<td class="limitText" title="${materia.buynum}">${materia.buynum}</td>
									<td class="limitText" title="${materia.price}">${materia.price}</td>
									<td class="limitText" title="${materia.cost}">${materia.cost}</td>
									<td class="limitText" title="${materia.supplier}">${materia.supplier}</td>
									<td class="limitText" title="${materia.link}">${materia.link}</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="title" colspan="2">预算费用总计：</td>
								<td colspan="20"><span style="color: red">
									<fmt:formatNumber type="number" value="${totalMoney}" pattern="0.00" maxFractionDigits="2" />
								</span>元</td>
							</tr>
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
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							审批记录
						</span>
					</div>
					<table class="table1">
						<tr>
							<c:choose>
								<c:when test="${app.status == 1}">
									<td style="width: 10%;">一级主管审批结果<span style="color: red;">(${AllUsers[app.currentId].name})</span></td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									<td style="width: 10%;"></td>
								</c:when>
								<c:when test="${not empty approve1}">
									<td style="width: 10%;">一级主管审批结果 <span style="color: red;">(${AllUsers[approve1.approveId].name})</span>
									</td>
									<td style="width: 10%;">${approve1.approveOpinion}</td>
									<td style="width: 15%;"><span style="color: green;">${approve1.approveResult}</span></td>
									<td style="width: 10%;">${approve1.approveDate}</td>
								</c:when>
								<c:when test="${empty approve1 and app.status > 1}">
									<td style="width: 10%;">一级主管审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
									<td style="width: 10%;"></td>
								</c:when>
							</c:choose>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${app.status == 2}">
									<td style="width: 10%;">二级主管审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									<td style="width: 10%;"></td>
								</c:when>
								<c:when test="${empty approve2}">
									<td style="width: 10%;">二级主管审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
									<td style="width: 10%;"></td>
								</c:when>
								<c:when test="${not empty approve2}">
									<td style="width: 10%;">二级主管审批结果 <span style="color: red;">(${AllUsers[approve2.approveId].name})</span>
									</td>
									<td style="width: 10%;">${approve2.approveOpinion}</td>
									<td style="width: 15%;"><span style="color: green;">${approve2.approveResult}</span></td>
									<td style="width: 10%;">${approve2.approveDate}</td>
								</c:when>
								<c:when test="${app.status < 2 and app.status != -1}">
									<td style="width: 10%;">二级主管审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">暂未到达该流程</span></td>
									<td style="width: 10%;"></td>
								</c:when>
							</c:choose>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${app.status == 3}">
									<td style="width: 10%;">财务主管审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									<td style="width: 10%;"></td>
								</c:when>
								<c:when test="${empty approve3 and app.status > 3}">
									<td style="width: 10%;">财务主管审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
									<td style="width: 10%;"></td>
								</c:when>
								<c:when test="${not empty approve3}">
									<td style="width: 10%;">财务主管审批结果 <span style="color: red;">(${AllUsers[approve3.approveId].name})</span>
									</td>
									<td style="width: 10%;">${approve3.approveOpinion}</td>
									<td style="width: 15%;"><span style="color: green;">${approve3.approveResult}</span></td>
									<td style="width: 10%;">${approve3.approveDate}</td>
								</c:when>
								<c:when test="${app.status < 3 and app.status > 0}">
									<td style="width: 10%;">财务主管审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
									<td style="width: 10%;"></td>
								</c:when>
							</c:choose>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${app.status == 4}">
									<td style="width: 10%;">总经理审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									<td style="width: 10%;"></td>
								</c:when>
								<c:when test="${not empty approve4}">
									<td style="width: 10%;">总经理审批结果 <span style="color: red;">(${AllUsers[approve4.approveId].name})</span>
									</td>
									<td style="width: 10%;">${approve4.approveOpinion}</td>
									<td style="width: 15%;"><span style="color: green;">${approve4.approveResult}</span></td>
									<td style="width: 10%;">${approve4.approveDate}</td>
								</c:when>
								<c:when test="${app.status < 4 and app.status > 0}">
									<td style="width: 10%;">总经理审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
									<td style="width: 10%;"></td>
								</c:when>
							</c:choose>
						</tr>
					</table>
					<c:if test="${type == 2}">
						<div class="tableTitle" style="padding: 10px 20px;">
							<span> <img
								src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								审批意见
							</span>
						</div>
						<table class="table1">
							<tbody>
								<tr>

									<td style="width: 20%;">审批意见： <select id="tg" name="sp"
										onchange="yjChanged()">
											<option value="通过" style="color: green;" selected="selected">通过</option>
											<option value="不通过" style="color: red;">不通过</option>
									</select>
									</td>

									<td style="border-right: none;">补充意见：<input name="opinion"
										id="opinion" style="width: 50%;" value="ok" /> <span
										style="color: red;">不超过50个字符</span>
									</td>
								</tr>
							</tbody>
						</table>
					</c:if>
				</div>
					<div class="" style="margin-top: 10px; margin-bottom: 20px;">
<!-- 						<a onclick="toInware()" class="lzui-btn lzui-corner-all">转到入库申请</a> -->
						<a onclick="exportCg()" class="lzui-btn lzui-corner-all">导出采购详情</a>
						<a onclick="toDeal()" class="lzui-btn lzui-corner-all">已处理</a>
					</div>
			</form>
		</div>
	</div>
</div>
<br />


</body>
</html>
<script>
	var index;
	$(function() {
		$('#OA处理').addClass('ui-tabs-current');
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function submitForm() {
		var opinion = $('#opinion').val();
		yj = $.trim(opinion);
		if (!yj) {
			alert('请输入补充意见');
			return;
		}
		document.form1.submit();
	}

	function selectIds() {
		$('input[name="checkId"]').each(function(index, obj) {
			if ($('#selectAll').is(":checked")) {
				obj.checked = true;
			} else {
				obj.checked = false;
			}
		});
	}

	function toInware() {
		if ($('input:checked').length == 0) {
			alert('请至少选择一项物料');
			return;
		}
		form1.action = '${ctx }/web/oa/materia_v2/toInware2';
		document.form1.submit();
	}

	function toDeal() {
		form1.action = '${ctx }/web/oa/materia_v2/toDeal?type=2';
		document.form1.submit();
	}
	
	function exportCg() {
		form1.action = '${ctx }/web/oa/materia_v2/exportCg';
		document.form1.submit();
	}
</script>