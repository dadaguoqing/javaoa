<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
.mytitle {
	text-align: center;
	font-weight: bold;
}

.limitText {
	overflow: hidden; /*自动隐藏文字*/
	text-overflow: ellipsis; /*文字隐藏后添加省略号*/
	white-space: nowrap; /*强制不换行*/
	max-width: 100px;
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
			<div class="navTitle2" url="${ctx }/web/oa/seal/sealList">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
		<!-- 		审批记录 -->
		<c:if test="${type == 2}">
			<div class="navTitle2" url="${ctx }/web/oa/seal/approveList">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
		<!-- 		审批 -->
		<c:if test="${type == 3}">
			<div class="navTitle2" url="${ctx }/web/oa/seal/approveRecord">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
		<c:if test="${type == 4}">
			<div class="navTitle2" url="${ctx }/web/oa/seal/sealDeal">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
		</c:if>
		<c:if test="${type == 5}">
			<div class="navTitle2" url="${ctx }/web/oa/seal/sealDealRecord">
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
			<form name="form1" action="${ctx }/web/oa/seal/saveApprove"
				method="post">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${app.id}" name="id" id="hid" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }用章申请单</span>
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
								<td class="title" style="width: 120px;">编号：</td>
								<td><span>${app.number }</span></td>
							</tr>
							<tr>
								<td class="title" style="text-align: right; width: 15%;">
									用章类型：<span style="color: red;"></span>
								</td>
								<td><c:if test="${app.sealType == 1}">
										内部用章
									</c:if> <c:if test="${app.sealType == 2}">
										临时用章
									</c:if> <c:if test="${app.sealType == 3}">
										外带用章
									</c:if></td>
								<td class="title" style="">申请时间：</td>
								<td style="border-right: none;" colspan="3">${app.daystr}</td>
							</tr>
							<tr>
								<td class="title">用章事由：</td>
								<td colspan="10">${app.content}</td>
							</tr>
							<tr>
								<c:if test="${not empty app.wdr}">
									<td class="title">外带人：</td>
									<td>${app.wdr}</td>
								</c:if>
								<c:if test="${not empty app.ptr}">
									<td class="title">陪同人：</td>
									<td>${app.ptr}</td>
								</c:if>
							</tr>
							<tr id="yzDate">
								<c:if test="${not empty app.startDate }">
									<td class="title">用章日期：</td>
									<td style="width: 300px">${app.startDate}</td>
								</c:if>
								<c:if test="${not empty app.endDate }">
									<td class="title">用章时间：</td>
									<td colspan="10">${app.endDate}</td>
								</c:if>
							</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							用章详情
						</span>
					</div>
					<table class="table1" id='cdTable'>
						<tbody>
							<tr>
								<td class="title" style="text-align: center; width: 50px;">序号</td>
								<td class="title" style="text-align: center; width: 150px;">用章文件名</td>
								<td class="title" style="text-align: center; width: 150px;">文件详情</td>
								<td class="title" style="text-align: center; width: 200px;">用章单位</td>
								<td class="title" style="text-align: center; width: 100px;">印章类型</td>
								<td class="title" style="text-align: center; width: 130px;">用印处</td>
								<td class="title" style="text-align: center; width: 80px;">文件份数</td>
								<td class="title" style="text-align: center; width: 80px;">用章数量</td>
							</tr>
							<c:forEach items="${list}" var="seal" varStatus="index">
								<tr>
									<td align="center">${index.count}</td>
									<td class="limitText" title="${seal.fileName}">${seal.fileName}</td>
									<td class="limitText" title="${seal.fileDetail}">${seal.fileDetail}</td>
									<td class="limitText" title="${seal.sealCompany}">${seal.sealCompany}</td>
									<td class="limitText" title="${seal.sealName}">${seal.sealName}</td>
									<td class="limitText" title="${seal.loaction}">${seal.loaction}</td>
									<td class="limitText" title="${seal.fileNum}">${seal.fileNum}</td>
									<td class="limitText" title="${seal.userNum}">${seal.userNum}</td>
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
						<tr>
							<td style="width: 10%;">一级主管审批结果</td>
							<td style="width: 10%;"></td>
							<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
							<td style="width: 10%;"></td>
						</tr>
						<tr>
							<td style="width: 10%;">二级主管审批结果</td>
							<td style="width: 10%;"></td>
							<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
							<td style="width: 10%;"></td>
						</tr>
						<c:choose>
							<c:when test="${app.sealType == 1}">
								<tr>
									<td style="width: 10%;">印章管理员审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
									<td style="width: 10%;"></td>
								</tr>
							</c:when>
							<c:when test="${app.status == 1}">
								<tr>
									<td style="width: 10%;">印章管理员审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									<td style="width: 10%;"></td>
								</tr>
							</c:when>
							<c:when test="${not empty approve1}">
								<tr>
									<td style="width: 10%;">印章管理员审批结果 <span
										style="color: red;">(${AllUsers[approve1.approveId].name})</span>
									</td>
									<td style="width: 10%;">${approve1.approveOpinion}</td>
									<td style="width: 15%;"><span style="color: green;">${approve1.approveResult}</span></td>
									<td style="width: 10%;">${approve1.approveDate}</td>
								</tr>
								<c:if test="${not empty approve1.startDate}">
									<tr>
										<td style="width: 10%;">用章时间</td>
										<td style="width: 10%;" colspan="20">${approve1.startDate}
										</td>
									</tr>
								</c:if>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${app.status == 1}">
								<tr>
									<td style="width: 10%;">总经理审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
									<td style="width: 10%;"></td>
								</tr>
							</c:when>
							<c:when test="${app.status == 2}">
								<tr>
									<td style="width: 10%;">总经理审批结果</td>
									<td style="width: 10%;"></td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
									<td style="width: 10%;"></td>
								</tr>
							</c:when>
							<c:when test="${not empty approve2}">
								<tr>
									<td style="width: 10%;">总经理审批结果 <span style="color: red;">(${AllUsers[approve2.approveId].name})</span>
									</td>
									<td style="width: 10%;">${approve2.approveOpinion}</td>
									<td style="width: 15%;"><span style="color: green;">${approve2.approveResult}</span></td>
									<td style="width: 10%;">${approve2.approveDate}</td>
								</tr>
								<c:if test="${not empty approve2.startDate}">
									<tr>
										<td style="width: 10%;">用章时间</td>
										<td style="width: 10%;" colspan="20">${approve2.startDate}
										</td>
									</tr>
								</c:if>
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
								<c:if test="${app.status == 1 and app.sealType == 3}">
									<tr>
										<td align="right">陪同人：</td>
										<td><select id="ptr" name="ptr">
												<option value="-1">==请选择陪同人==</option>
												<c:forEach items="${ptrs}" var="ptr">
													<option value="${ptr.content}">${ptr.content}</option>
												</c:forEach>
										</select></td>
									</tr>
								</c:if>
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
		<c:if test="${app.status == 2 && type == 1 && app.sealType == 1 || (app.status == 1 && type == 1)}">
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:cancle();" class="lzui-btn lzui-corner-all">撤销申请</a>
			</div>
		</c:if>
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
	var sealType = '${app.sealType}';
	var status = '${app.status}';
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
		if (status == 1 && sealType == 3) {
			var ptr = $('#ptr').val();
			if (ptr == '-1') {
				layer.msg('请选择陪同人');
				return;
			}
		}
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
	
	function cancle() {
		form1.action = '${ctx }/web/oa/seal/cancleApply';
		document.form1.submit();
	}
</script>