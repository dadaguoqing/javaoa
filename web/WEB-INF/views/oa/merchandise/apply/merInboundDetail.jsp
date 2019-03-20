<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../../myheader.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
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
			<div class="navTitle2" url="${ctx }/web/oa/merchandise/apply/recordList/1/${type}?curPage=1">
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
			<form name="form1" action="${ctx }/web/oa/merchandise/apply/approve/${applyType}/${type}"
				method="post">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${app.id}" name="id" id="hid" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }成品入库申请单</span>
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
								<td style="width: 20%;">${AllUsers[app.empId].name }</td>
								<td class="title">所属部门：</td>
								<td style="">${AllUsers[app.empId].deptName }</td>
								<td class="title" style="">申请时间：</td>
								<td style="border-right: none;" colspan="3">${app.applyDate}</td>
							</tr>
							<tr>
								<td class="title" style="width: 120px;">编号：</td>
								<td colspan="3">${app.applyCode }</td>
								<td class="title" style="">预计入库时间：</td>
								<td style="border-right: none;">${app.expectDate}</td>
							</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							成品入库详情
						</span>
					</div>
					<table class="table1" id='cdTable'>
						<tbody>
							<tr>
								<td class="title" style="text-align: center; width: 50px;">序号</td>
							<td class="title" style="text-align: center; width: 150px;">产品型号</td>
							<td class="title" style="text-align: center; width: 50px;">数量</td>
							<td class="title" style="text-align: center; width: 50px;">单位</td>
							<td class="title" style="text-align: center; width: 100px;">生产信息代码</td>
							<td class="title" style="text-align: center; width: 130px;">生产周号</td>
							<td class="title" style="text-align: center; width: 80px;">入库来源</td>
							<td class="title" style="text-align: center; width: 80px;">外箱编号</td>
							<td class="title" style="text-align: center; width: 80px;">备注</td>
							</tr>
							<c:forEach items="${list}" var="detail" varStatus="i">
								<tr>
									<td align="center">${i.count}</td>
									<td class="limitText" title="${detail.productModel}">${detail.productModel}</td>
									<td class="limitText" title="${detail.quantity}">${detail.quantity}</td>
									<td class="limitText" title="${detail.unit}">${detail.unit}</td>
									<td class="limitText" title="${detail.productCode}">${detail.productCode}</td>
									<td class="limitText" title="${detail.weekCode}">${detail.weekCode}</td>
									<td class="limitText" title="${detail.inboundSource}">${detail.inboundSource}</td>
									<td class="limitText" title="${detail.packageCode}">${detail.packageCode}</td>
									<td class="limitText" title="${detail.content}">${detail.content}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							审批记录
						</span>
					</div>
					
					<table class="table1">
						<c:choose>
							<c:when test="${app.status == 1}">
								<tr>
									<td style="width: 10%;">产品运营经理审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									<td style="width: 10%;"></td>
								</tr>
							</c:when>
							<c:when test="${not empty approve1}">
								<tr>
									<td style="width: 10%;">产品运营经理审批结果 <span
										style="color: red;">(${AllUsers[approve1.approveId].name})</span>
									</td>
									<td style="width: 10%;">${approve1.approveOpinion}</td>
									<td style="width: 15%;"><span style="color: green;">${approve1.approveResult}</span></td>
									<td style="width: 10%;">${approve1.approveDate}</td>
								</tr>
							</c:when>
						</c:choose>
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
				<c:if test="${type == 2}">
					<div class="" style="margin-top: 10px; margin-bottom: 20px;">
						<a href="javascript:submitForm();"
							class="lzui-btn lzui-corner-all">提交审批结果</a>
					</div>
				</c:if>

			</form>
		</div>
<%-- 		<c:if test="${app.status == 2 && type == 1 && app.detailType == 1 || (app.status == 1 && type == 1)}"> --%>
<!-- 			<div class="" style="margin-top: 10px; margin-bottom: 20px;"> -->
<!-- 				<a href="javascript:cancle();" class="lzui-btn lzui-corner-all">撤销申请</a> -->
<!-- 			</div> -->
<%-- 		</c:if> --%>
	</div>
</div>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>

</body>
</html>
<script>
	var index;
	var type = '${type}';
	$(function() {
		if (type == 1) {
			$('#OA申请').addClass('ui-tabs-current');
		} else if (type == 4 || type == 5) {
			$('#管理').addClass('ui-tabs-current');
		} else {
			$('#OA审批').addClass('ui-tabs-current');
		}
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function submitForm() {
		var sp = $('#tg').val();
		if (sp == '通过') {
			var opinion = $('#opinion').val();
			if (!opinion) {
				layer.msg('请填写补充意见');
				return;
			}
		}
		document.form1.submit();
	}
</script>