<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
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
		<c:if test="${isManager == 1}">
		<div class="navTitle2" url="${ctx }/web/oa/materia_v2/maApplyList2">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
		</c:if>
		<c:if test="${isManager == 0}">
		<div class="navTitle2" url="${ctx }/web/oa/materia_v2/maApplyList">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
		</c:if>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
			<a href="javascript:void(0);" onclick="exportApplyRecord()" >导出Excel</a>
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
						<span style="color: #eee;">${compName }物料申领单</span>
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
								<td class="title" style="width: 10%;">申领人：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].name }</td>
								<td class="title" style="width: 10%;">所属部门：</td>
								<td style="width: 10%;">${AllUsers[ma.empId].deptName }</td>
								<td class="title" style="width: 10%;">编号：</td>
								<td style="width: 10%;">${ma.code }</td>
							</tr>
							<tr>
								<td class="title" style="width: 100px;">申领仓库：</td>
								<td style="width: 170px;"><a id="warehouse">${ma.warehouse }</a></td>
								<td class="title" style="width: 100px;">申领时间：</td>
								<td style="width: 170px;" colspan="3">${ma.dayStr }</td>
							</tr>
						</tbody>
					</table>


					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							物料详细信息
						</span>
					</div>

					<table class="table1" id="data">
						<tbody>
							<tr>
								<td class="title" style="text-align: center;">
									物料编码</td>
								<td class="title" style="text-align: center; ">
									品名分类</td>
								<td class="title" style="text-align: center;">
									品牌</td>
								<td class="title" style="text-align: center;">
									规格型号</td>
								<td class="title" style="text-align: center">
									封装</td>
								<td class="title" style="text-align: center;">
									数量</td>
							</tr>

							<c:forEach items="${list}" var="cur">
								<tr align="center">

									<td style="">${cur.materiaCode }<input type="hidden"
										value="${cur.id }" name="ids" />
									</td>
									<td style="" align="left">${cur.classfiy }</td>
									<td style="" align="left">${cur.brand }</td>
									<td style="" align="left">${cur.spec }</td>
									<td style="" align="left">${cur.package1 }</td>
									<td style="">${cur.num }</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="title" style="text-align: right; width: 100px;">
									附件:</td>
								<td colspan="10"><c:if test="${not empty ma.url2 }">
										<a href="${ctx }/upload/${ma.url2}">下载附件</a>
									</c:if> <c:if test="${empty ma.url2 }">
										<a style="color: red">未上传附件</a>
									</c:if></td>
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
										style="width: 50%; height: 80px; padding: 5px 10px;">${ma.bz }</textarea>
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
							<td style="width: 10%;">一级主管审批结果 <c:if
									test="${ma.dId  != 0 }">
									<span style="color: red;">(${AllUsers[ma.dId].name})</span>
								</c:if>
							</td>
							<td style="width: 10%;"><span>${ma.dResult }</span></td>
							<td style="width: 10%;"><c:if
									test="${ ma.currentId == ma.dId && ma.status == 1}">
									<span style="color: green;">正在审批</span>
								</c:if> <c:if test="${ not empty ma.dOpinion}">
									<span style="">${ma.dOpinion}</span>
								</c:if> <c:if test="${ ma.dId == ma.pId || ma.dId == ma.mId}">
									<span style="color: #666;">本次审批不需要经过一级主管</span>
								</c:if></td>
							<td style="width: 15%;">${ma.dDayStr }</td>
						</tr>
						<tr>
							<td style="width: 5%;">二级主管审批结果 <c:if
									test="${ma.pId  != 0 }">
									<span style="color: red;">(${AllUsers[ma.pId].name})</span>
								</c:if>
							</td>
							<td><span>${ma.pResult }</span></td>
							<td style="width: 15%;"><c:choose>
									<c:when test="${ ma.currentId == ma.pId && ma.status == 2}">
										<span style="color: green;">正在审批</span>
									</c:when>
									<c:when test="${ not empty ma.pOpinion}">
										<span style="">${ma.pOpinion}</span>
									</c:when>
									<c:when test="${ ma.pId == ma.mId}">
										<span style="color: #666;">本次审批不需要经过二级主管</span>
									</c:when>
									<c:when
										test="${ empty ma.pOpinion && ma.status < 2 && ma.status != -1 }">
										<span style="color: #666;">暂未到达该流程</span>
									</c:when>
								</c:choose></td>
						</tr>
						<tr>
							<td style="width: 5%;">总经理审批结果 <c:if test="${ma.mId  != 0 }">
									<span style="color: red;">(${AllUsers[ma.mId].name})</span>
								</c:if>
							</td>
							<td><span>${ma.mResult }</span></td>
							<td style="width: 15%;"><c:if
									test="${ ma.currentId == ma.mId && ma.status == 3}">
									<span style="color: green;">正在审批</span>
								</c:if> <c:if test="${ not empty ma.mOpinion}">
									<span style="">${ma.mOpinion}</span>
								</c:if> <c:if
									test="${ empty ma.mOpinion && ma.status < 3 && ma.status != -1 }">
									<span style="color: #666;">暂未到达该流程</span>
								</c:if></td>
							<td style="width: 15%;">${ma.mDayStr }</td>
						</tr>
						<tr>
							<td style="width: 5%;">仓库管理员确认结果 <c:if
									test="${ma.aId  != 0 }">
									<span style="color: red;">(${AllUsers[ma.aId].name})</span>
								</c:if>
							</td>
							<td><span>${ma.aResult }</span></td>
							<td style="width: 15%;"><c:if
									test="${ ma.currentId == ma.aId && ma.status == 4}">
									<span style="color: green;">正在确认</span>
								</c:if> <c:if test="${ not empty ma.aOpinion}">
									<span style="">${ma.aOpinion}</span>
								</c:if> <c:if
									test="${ empty ma.aOpinion && ma.status < 4 && ma.status != -1 }">
									<span style="color: #666;">暂未到达该流程</span>
								</c:if></td>
							<td style="width: 15%;">${ma.aDayStr }</td>
						</tr>
					</table>
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
										<option value="审批通过" style="color: green;" selected="selected">通过</option>
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

				</div>
				<div class="" style="margin-top: 10px; margin-bottom: 20px;">
					<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交审批结果</a>
					<a href="javascript:printHtml();" class="lzui-btn lzui-corner-all">打印</a>
				</div>
			</form>
		</div>
	</div>

</div>

</body>
</html>
<script>
	var index;

	$(function() {
		$('#OA审批').addClass('ui-tabs-current');
		$('#').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	function printHtml() {
		var body1 = "<div align='center' style='width:80%'>" + $('#print').html() + "</div>";
		$('body').html(body1);
		window.print();
	}
	
	function checkStock(id) {
		var warehouse = $('#warehouse').html();
		var num = $('#'+id).val();
		$.ajax({
			type : "POST",
			dataType : "json",
			url :'${ctx}/web/oa/materia/getStock2',//请求地址
			data : {//数据，多个数据用逗号隔开
				id : id,
				warehouse: '${ma.warehouse}'
			},
			error  : function(){
				alert('库存查询失败，请重试');
			},
			success : function(data) {
				if (data  < num) {
					alert('库存不足');
					$('#'+id).val('');
				}  
			}
		});
	
	}
	
	function exportApplyRecord() {
		var table = $('#data').html();
		var date = new Date();
		var day = date.getFullYear() + '' + Number(date.getMonth()+1) + date.getDate();
		var fileName = 'applyRecord-' + day + '.xls';
		exportExcel(table, fileName);
	}
	

	function submitForm() {
		var sp = $('#tg').val();
		if (sp=='不通过') {
			document.form1.submit();
		}
		
		var realNums = document.getElementsByName("realNum");
		for (var i = 0;i < realNums.length;i++) {
			if (!realNums[i].value) {
				alert('请填写实际领用数量');
				return;
			}
		}
		
		var opinion = $('#opinion').val();
		yj = $.trim(opinion);
		if (!yj) {
			alert('请输入补充意见');
			return;
		}
		document.form1.submit();
	}
</script>