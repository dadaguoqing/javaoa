<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
	.leftpad {
		padding-left: 15px;
		margin-top: 2px;
	}
	.titleWidth {
		width : 180px;
	}
</style>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
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
							<td>${AllUsers[ea.proposer].name }</td>
							<td class="title" style="width: 100px">所属部门：</td>
							<td>${AllUsers[ea.proposer].deptName }</td>
							<td class="title">申请时间：</td>
							<td>
								${ea.applyDate}
							</td>
							<td class="title">编号：</td>
							<td id="numberStr">${ea.applyCode}</td>
						</tr>
						<tr>
							<td class="title">加工事由：</td>
							<td colspan="5">${ea.content}</td>
							<td class="title">项目名称/项目号：</td>
							<td >${ea.projectName}</td>
							
						</tr>
						<tr>
							<td class="title">加工类型：</td>
							<td colspan="7">
								<c:forEach items="${types}" var="type">
									<c:if test="${type == '1' }">
										<label for="pcb" class="leftpad">PCB加工</label>
									</c:if>
									<c:if test="${type == '2' }">
										<label for="weld" class="leftpad">焊接加工</label>
									</c:if>
									<c:if test="${type == '3' }">
										<label for="steel" class="leftpad">钢网加工</label>
									</c:if>
									<c:if test="${type == '4' }">
										<label for="gule" class="leftpad">打白胶加工</label>
									</c:if>
									<c:if test="${type == '5' }">
										<label for="paint" class="leftpad">三防漆加工</label>
									</c:if>
									<c:if test="${type == '6' }">
										<label for="acrylic" class="leftpad">亚克力加工</label>
									</c:if>
									<c:if test="${type == '7' }">
										<label for="acrylic" class="leftpad">机箱加工</label>
									</c:if>
									<c:if test="${type == '8' }">
										<label for="acrylic" class="leftpad">线束加工</label>
									</c:if>
								</c:forEach>
							</td>
						</tr>
					</tbody>
				</table>
				
				<c:forEach items="${types}" var="type">
					<c:if test="${type == '1' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								PCB加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;">加工数量：</td>
								<td>${detail.pcbNum} PCS</td>
								<td class="title" style="width: 180px;">PCB加工工艺说明书：</td>
								<td>
									<a href="javascript:download('${ctx}/web/oa/external/download','${detail.pcbDescription}');" onclick="download(${ctx}${detail.pcbDescription})">点击下载</a>
								</td>
							</tr>
							<tr>
								<td class="title" style="width: 180px;">Gerber文件：</td>
								<td><a href="${ctx}${detail.pcbGerber}">点击下载</a></td>
								<td class="title" style="width: 180px;">阻抗说明：</td>
								<td>
									<c:if test="${ empty detail.impedanceDescript}">暂无附件</c:if>
									<c:if test="${not empty detail.impedanceDescript}">
										<a href="${ctx}${detail.impedanceDescript}">点击下载</a>
									</c:if>
								</td>
							</tr>
						</table>
					</c:if>
					
					<c:if test="${type == '2' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								焊接加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;">焊接数量：</td>
								<td>${detail.weldNum} PCS </td>
								<td class="title" style="width: 180px;" >BOM单：</td>
								<td><a href="${ctx}${detail.weldBom}">点击下载</a></td>
								<td class="title" style="width: 180px;">焊接加工工艺文件：</td>
								<td >
									<c:if test="${ empty detail.weldDescript}">暂无附件</c:if>
									<c:if test="${ not empty detail.weldDescript}">
										<a href="${ctx}${detail.weldDescript}">点击下载</a>
									</c:if>
								</td>
							</tr>
							<tr>
								<td class="title" >Gerber文件：</td>
								<td><a href="${ctx}${detail.weldGerber}">点击下载</a></td>
								<td class="title" >坐标文件：</td>
								<td><a href="${ctx}${detail.weldCoordinate}">点击下载</a></td>
								<td class="title" >丝印文件：</td>
								<td><a href="${ctx}${detail.weldSilkScreen}">点击下载</a></td>
							</tr>
						</table>
					</c:if>
					
					<c:if test="${type == '3' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								钢网加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;" >加工数量：</td>
								<td>${detail.steelNum} PCS </td>
								<td class="title" style="width: 180px;">尺寸：</td>
								<td>${detail.steelSize1}mm &nbsp; * &nbsp;${detail.steelSize2}mm</td>
								<td class="title" style="width: 180px;">钢网厚度：</td>
								<td>${detail.steelThinkness}mm</td>
							</tr>
							<tr>
								<td class="title" style="width: 180px;">钢网材料：</td>
								<td>${detail.steelMaterial}</td>
								<td class="title">用途：</td>
								<td>${detail.steelUse}</td>
								<td class="title">抛光工艺：</td>
								<td>${detail.steelPolishing}</td>
							</tr>
						</table>
					</c:if>
					
					<c:if test="${type == '4' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								打白胶加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;">打白胶工艺要求文档：</td>
								<td><a href="${ctx}${detail.glueDescript}">点击下载</a></td>
							</tr>
						</table>
					</c:if>
					
					<c:if test="${type == '5' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								三防漆加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;">三防漆工艺要求文档：</td>
								<td><a href="${ctx}${detail.paintDescript}">点击下载</a></td>
							</tr>
						</table>
					</c:if>
					
					<c:if test="${type == '6' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								亚克力加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;">加工数量：</td>
								<td>${detail.acrylicNum} PCS</td>
								<td class="title">加工图纸：</td>
								<td><a href="${ctx}${detail.acrylicCad}">点击下载</a></td>
							</tr>
						</table>
					</c:if>
					
					<c:if test="${type == '7' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								机箱加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;">加工数量：</td>
								<td>${detail.chassisNum} PCS</td>
								<td class="title">加工图纸：</td>
								<td><a href="${ctx}${detail.chassisCad}">点击下载</a></td>
							</tr>
						</table>
					</c:if>
					
					<c:if test="${type == '8' }">
						<div class="tableTitle" style="padding: 10px 20px; background-color: #D1EEEE;">
							<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								线束加工详细参数
							</span>
						</div>
						<table class="table1">
							<tr>
								<td class="title" style="width: 180px;">加工数量：</td>
								<td>${detail.pencilNum} PCS</td>
								<td class="title">加工图纸：</td>
								<td><a href="${ctx}${detail.pencilCad}">点击下载</a></td>
							</tr>
						</table>
					</c:if>
				</c:forEach>
				
				
				<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							加工费用预算
						</span>
				</div>
				<table class="table1">
					<tr><td >
						<c:forEach items="${types}" var="type">
							<c:if test="${type == '1' }">
								<span class="leftpad"><label>PCB板加工：</label>${detail.pcbCost} 元</span>
							</c:if>
							<c:if test="${type == '2' }">
								<span class="leftpad"><b >电子元器件加工</b>：${detail.componentCost} 元</span>
								<span class="leftpad"><b >焊接加工</b>：${detail.weldCost} 元</span>
							</c:if>
							<c:if test="${type == '3' }">
								<span class="leftpad"><b >钢网加工</b>：${detail.steelCost} 元</span>
							</c:if>
							<c:if test="${type == '4' }">
								<span class="leftpad"><b >打白胶加工</b>：${detail.glueCost} 元</span>
							</c:if>
							<c:if test="${type == '5' }">
								<span class="leftpad"><b >三防漆加工</b>：${detail.paintCost} 元</span>
							</c:if>
							<c:if test="${type == '6' }">
								<span class="leftpad"><b >亚克力加工</b>：${detail.acrylicCost} 元</span>
							</c:if>
							<c:if test="${type == '7' }">
								<span class="leftpad"><b >机箱加工</b>：${detail.chassisCost} 元</span>
							</c:if>
							<c:if test="${type == '8' }">
								<span class="leftpad"><b >线束加工</b>：${detail.pencilCost} 元</span>
							</c:if>
						</c:forEach>
					</td></tr>
					<tr>
						<td style="align: left;;"><b class="leftpad">预算总计：</b>${detail.totalCost} 元</td>
					</tr>
					<tr>
							<td style="align: left;" colspan="6"><label class="leftpad">加急：</label>
								<c:forEach items="${isUrgents}" var="type" >
									<c:if test="${type == '1' }">
										<label for="pcb" class="leftpad">PCB加工</label>
									</c:if>
									<c:if test="${type == '2' }">
										<label for="weld" class="leftpad">焊接加工</label>
									</c:if>
									<c:if test="${type == '3' }">
										<label for="steel" class="leftpad">钢网加工</label>
									</c:if>
									<c:if test="${type == '4' }">
										<label for="gule" class="leftpad">打白胶加工</label>
									</c:if>
									<c:if test="${type == '5' }">
										<label for="paint" class="leftpad">三防漆加工</label>
									</c:if>
									<c:if test="${type == '6' }">
										<label for="acrylic" class="leftpad">亚克力加工</label>
									</c:if>
									<c:if test="${type == '7' }">
										<label for="acrylic" class="leftpad">机箱加工</label>
									</c:if>
									<c:if test="${type == '8' }">
										<label for="acrylic" class="leftpad">线束加工</label>
									</c:if>
								</c:forEach>
								</td>
						</tr>
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
								<c:when test="${ea.status == 1}">
									<td style="width: 10%;">一级主管审批结果
											<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve1}">
									<td style="width: 10%;">一级主管审批结果
										<span style="color: red;">(${AllUsers[approve1.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve1.approveResult}</span></td>
								</c:when>
								<c:when test="${empty approve1 and (ea.status> 1 or ea.status == -1)}">
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
								<c:when test="${ea.status == 2}">
									<td style="width: 10%;">二级主管审批结果 
										<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${empty approve2 and (ea.status > 2 or ea.status == -1)}">
									<td style="width: 10%;">二级主管审批结果 
										<span style="color: red;"></span>
									</td>
									<td style="width: 10%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
								</c:when>
								<c:when test="${not empty approve2}">
									<td style="width: 10%;">二级主管审批结果 
										<span style="color: red;">(${AllUsers[approve2.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve2.approveResult}</span></td>
								</c:when>
								<c:when test="${ea.status < 2 and ea.status != -1}">
									<td style="width: 10%;">二级主管审批结果
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve2.approveOpinion}</td>
							<td style="width: 10%;">${approve2.approveDate}</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${ea.status == 3}">
									<td style="width: 10%;">财务主管审批结果 
										<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve3}">
									<td style="width: 10%;">财务主管审批结果 
										<span style="color: red;">(${AllUsers[approve3.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve3.approveResult}</span></td>
								</c:when>
								<c:when test="${ea.status < 3 and ea.status > 0}">
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
								<c:when test="${ea.status == 4}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve4}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;">(${AllUsers[approve4.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve4.approveResult}</span></td>
								</c:when>
								<c:when test="${ea.status < 4 and ea.status > 0}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
								</c:when>
								<c:when test="${empty approve4 and (ea.status > 4 or ea.status == -1)}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;"></span>
									</td>
									<td style="width: 10%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve4.approveOpinion}</td>
							<td style="width: 10%;">${approve4.approveDate}</td>
						</tr>
					</table>
				<c:if test="${ea.status > 5 }">
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
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">PCB加工交期:</td>
										<td>${deal.pcbDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">PCB加工费用:</td>
										<td style="width: 150px">${deal.pcbCost } 元</td>
										<td class="title" style="width: 150px">交期:</td>
										<td style="width: 150px">${deal.pcbDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.pcbPdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
							
							<c:if test="${type == '2' }">
								<tr>
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">焊接加工交期:</td>
										<td>${deal.weldDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">焊接加工费用:</td>
										<td style="width: 150px">${deal.weldCost } 元</td>
										<td class="title">交期:</td>
										<td style="width: 150px">${deal.weldDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.weldPdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
							
							<c:if test="${type == '3' }">
								<tr>
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">钢网加工交期:</td>
									<td>${deal.steelDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">钢网加工费用:</td>
										<td style="width: 150px">${deal.steelCost } 元</td>
										<td class="title">交期:</td>
										<td style="width: 150px">${deal.steelDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.steelPdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
							
							<c:if test="${type == '4' }">
								<tr>
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">打白胶交期:</td>
										<td>${deal.glueDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">打白胶加工费用:</td>
										<td style="width: 150px">${deal.glueCost } 元</td>
										<td class="title">交期:</td>
										<td style="width: 150px">${deal.glueDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.gluePdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
							
							<c:if test="${type == '5' }">
								<tr>
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">三防漆加工交期:</td>
										<td >${deal.paintDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">三防漆加工费用:</td>
										<td style="width: 150px">${deal.paintCost } 元</td>
										<td class="title">交期:</td>
										<td style="width: 150px">${deal.paintDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.paintPdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
							
							<c:if test="${type == '6' }">
								<tr>
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">亚克力加工交期:</td>
										<td >${deal.acrylicDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">亚克力加工费用:</td>
										<td style="width: 150px">${deal.acrylicCost } 元</td>
										<td class="title">交期:</td>
										<td style="width: 150px">${deal.acrylicDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.acrylicPdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
							<c:if test="${type == '7' }">
								<tr>
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">机箱加工交期:</td>
										<td >${deal.chassisDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">机箱加工费用:</td>
										<td style="width: 150px">${deal.chassisCost } 元</td>
										<td class="title">交期:</td>
										<td style="width: 150px">${deal.chassisDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.chassisPdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
							<c:if test="${type == '8' }">
								<tr>
									<c:if test="${isProposer == 1}">
										<td class="title" style="width: 150px">线束加工交期:</td>
										<td >${deal.pencilDate }</td>
									</c:if>
									<c:if test="${isProposer != 1}">
										<td class="title" style="width: 150px">线束加工费用:</td>
										<td style="width: 150px">${deal.pencilCost } 元</td>
										<td class="title">交期:</td>
										<td style="width: 150px">${deal.pencilDate }</td>
										<td class="title" style="width: 150px">费用明细附件:</td>
										<td><a href="${ctx}${deal.pencilPdf}" target="_blank">点击查看</a></td>
									</c:if>
								</tr>
							</c:if>
						</c:forEach>
						<c:if test="${isProposer != 1}">
							<tr>
								<td class="title" style="width: 150px">加工费用合计:</td>
								<td style="width: 150px" colspan="5">${deal.totalCost } 元</td>
							</tr>
						</c:if>
					</table>
					
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							处理审批记录
						</span>
					</div>
					<table class="table1">
						<tr>
							<c:choose>
								<c:when test="${ea.status == 6}">
									<td style="width: 10%;">生产主管审批结果
											<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve6}">
									<td style="width: 10%;">生产主管审批结果
										<span style="color: red;">(${AllUsers[approve6.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve6.approveResult}</span></td>
								</c:when>
								<c:when test="${empty approve6 and (ea.status> 6 or ea.status == -1)}">
									<td style="width: 10%;">生产主管审批结果
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve6.approveOpinion}</td>
							<td style="width: 10%;">${approve6.approveDate}</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${ea.status == 7}">
									<td style="width: 10%;">生产总监审批结果 
										<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${empty approve7 and (ea.status > 7 or ea.status == -1)}">
									<td style="width: 10%;">生产总监审批结果 
										<span style="color: red;"></span>
									</td>
									<td style="width: 10%;"><span style="color: gray;">本次审批不需要经过此流程</span></td>
								</c:when>
								<c:when test="${not empty approve7}">
									<td style="width: 10%;">生产总监审批结果 
										<span style="color: red;">(${AllUsers[approve7.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve7.approveResult}</span></td>
								</c:when>
								<c:when test="${ea.status < 7 and ea.status != -1}">
									<td style="width: 10%;">生产总监审批结果
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve7.approveOpinion}</td>
							<td style="width: 10%;">${approve7.approveDate}</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${ea.status == 8}">
									<td style="width: 10%;">财务会计审批结果 
										<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve8}">
									<td style="width: 10%;">财务会计审批结果
										<span style="color: red;">(${AllUsers[approve8.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve8.approveResult}</span></td>
								</c:when>
								<c:when test="${ea.status < 8 and ea.status > 0}">
									<td style="width: 10%;">财务会计审批结果 
											<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve8.approveOpinion}</td>
							<td style="width: 10%;">${approve8.approveDate}</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${ea.status == 9}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;">(${AllUsers[ea.currentId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">正在审批</span></td>
								</c:when>
								<c:when test="${not empty approve9}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;">(${AllUsers[approve9.approveId].name})</span>
									</td>
									<td style="width: 15%;"><span style="color: green;">${approve9.approveResult}</span></td>
								</c:when>
								<c:when test="${ea.status < 9 and ea.status > 0}">
									<td style="width: 10%;">总经理审批结果 
										<span style="color: red;"></span>
									</td>
									<td style="width: 15%;"><span style="color: gray;">暂未到达该流程</span></td>
								</c:when>
							</c:choose>
							<td style="width: 10%;">${approve9.approveOpinion}</td>
							<td style="width: 10%;">${approve9.approveDate}</td>
						</tr>
					</table>
				</c:if>
			</div>
			
