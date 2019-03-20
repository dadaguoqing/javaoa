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
		<div class="navTitle2" url="${ctx }/web/oa/materia_v2/rejectList">
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
			<form name="form1" action="${ctx }/web/oa/materia_v2/maApprove"
				method="post" onsubmit="return beforeSubmit();">
				<input type="hidden" value="${submitCode}" name="submitCode" /> <input
					type="hidden" value="${ma.id}" name="id" />
				<div
					style="border: 1px solid #2191C0; margin-top: 10px; width: 90%;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }物料报废单</span>
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
								<td class="title" style="width: 10%;">申请人：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].name }</td>
								<td class="title" style="width: 10%;">所属部门：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].deptName }</td>
								<td class="title" style="width: 10%;">编号：</td>
								<td style="width: 10%;">${ma.requisitionCode}</td>
							</tr>
							<tr>
								<td class="title" style="width: 100px;">申请仓库：</td>
								<td style="width: 170px;"><a id="warehouse">${wh }</a></td>
								<td class="title" style="width: 100px;">申请时间：</td>
								<td style="width: 170px;" colspan="3">${ma.daystr}</td>
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
						<tbody>
							<tr>
								<td class="title" style="text-align: center;">
									物料编码</td>
								<td class="title" style="text-align: center; ">
									品名分类</td>
								<td class="title" style="text-align: center;">
									规格型号</td>
								<td class="title" style="text-align: center">
									封装</td>
								<td class="title" style="text-align: center;">
									数量</td>
							</tr>

							<c:forEach items="${list}" var="cur">
								<tr align="center">

									<td style="">${cur.maCode }<input type="hidden"
										value="${cur.id }" name="ids" />
									</td>
									<td class="limitText" title="${cur.classify }" align="left">${cur.classify }</td>
									<td class="limitText" title="${cur.spec }" align="left">${cur.spec }</td>
									<td class="limitText" title="${cur.package1 }" align="left">${cur.package1 }</td>
									<td class="limitText" title="${cur.needNum }">${cur.needNum }</td>
								</tr>
							</c:forEach>
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
										style="width: 50%; height: 80px; padding: 5px 10px;">${ma.content }</textarea>
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
								<c:when test="${ma.status == 1}">
									<td style="width: 10%;">一级主管审批结果
											<span style="color: red;">(${AllUsers[ma.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve1}">
									<td style="width: 10%;">一级主管审批结果
										<span style="color: red;">(${AllUsers[approve1.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve1.approveResult}</span></td>
								</c:when>
								<c:when test="${empty approve1 and ma.status> 1}">
									<td style="width: 10%;">一级主管审批结果
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve1.approveOpinion}</td>
							<td style="width: 10%;">${approve1.approveDate}</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${ma.status == 2}">
									<td style="width: 10%;">二级主管审批结果 
										<span style="color: red;">(${AllUsers[ma.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${empty approve2}">
								<td style="width: 10%;">二级主管审批结果 
										<span style="color: red;">(${AllUsers[ma.currentId].name})</span>
									<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
								</c:when>
								<c:when test="${not empty approve2}">
									<td style="width: 10%;">二级主管审批结果 
										<span style="color: red;">(${AllUsers[approve2.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve2.approveResult}</span></td>
								</c:when>
								<c:when test="${ma.status < 2 and ma.status != -1}">
									<td style="width: 10%;">二级主管审批结果 
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: green;">暂未到达该流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve2.approveOpinion}</td>
							<td style="width: 10%;">${approve2.approveDate}</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${ma.status == 3}">
									<td style="width: 10%;">财务主管审批结果 
										<span style="color: red;">(${AllUsers[ma.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve3}">
									<td style="width: 10%;">财务主管审批结果 
										<span style="color: red;">(${AllUsers[approve3.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve3.approveResult}</span></td>
								</c:when>
								<c:when test="${ma.status < 3 and ma.status > 0}">
									<td style="width: 10%;">财务主管审批结果 
											<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve3.approveOpinion}</td>
							<td style="width: 10%;">${approve3.approveDate}</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${ma.status == 4}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;">(${AllUsers[ma.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve4}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;">(${AllUsers[approve3.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve4.approveResult}</span></td>
								</c:when>
								<c:when test="${ma.status < 4 and ma.status > 0}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve4.approveOpinion}</td>
							<td style="width: 10%;">${approve4.approveDate}</td>
						</tr>
					</table>
		</div>
			</form>
		</div>
	</div>
</div>

</body>
</html>
<script>
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
</script>