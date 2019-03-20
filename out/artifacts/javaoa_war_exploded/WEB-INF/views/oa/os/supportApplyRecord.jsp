<%@ page language="java" import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/os/mySupportRecord"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:120px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">技术支持申请</div>
	</div>
		
	<form name="form1" action="${ctx }/web/oa/os/saveApproveResult" method="post" enctype="multipart/form-data">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${id}" name="id"/>
	<input type="hidden" value="${loginUserId}" name="loginUserId" id="loginUserId"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }技术支持申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:15%;">申请人：</td>	
			<td style="width:15%;">
				${list.applyName}
			</td>	
			<td class="title" style="width:15%;">所属部门：</td>	
			<td style="width:15%;">
				${list.deptName }
			</td>
			<td class="title" style="width:120px;">申请时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
				String tdy = now.substring(0,11);
			%>
			<td style="border-right:none;">
				${now }
			</td>	
			
		</tr>
		<tr>
			<td class="title" >优先级：</td>	
			<td style="width:90px;">
				${list.priority }
			</td>
			<td class="title" style="width:120px;">期望完成时间：</td>	
			<td style="width:90px;">
				${list.expectdTime }
			</td>
			<td class="title" style="width:120px;">最晚完成时间：</td>	
			<td style="width:90px;">
				${list.deadline }
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申请详情</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:20%;">
				客户单位(全称)
			</td>
			<td class="title" style="text-align:left; width:20%;">
				开案项目
			</td>
			<td class="title" style="text-align:left; width:20%; ">
				技术支持内容
			</td>
			<td class="title" style="text-align:left;width:20%; ">
				附件文档
			</td>		
		</tr>
		
		<tr id="addCdTr">
			<td>
				${list.customer }
			<td >
				${list.project }
			</td>
			<td >
				${list.supportContent }
			</td>
			<td style=" width:60px; border-right:none; ">
				<a href="${ctx }/upload/${list.accessory}">点击下载附件</a>
			</td>		
		</tr>
	</tbody></table>
		<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批详情</span>
	</div>
		<table class="table1" id='cdTable' >
		<tr>
			<td class="title" style="width:20%;text-align:left;">主管</td>
			<td class="title" style="width:20%;text-align:left;">审批结果</td>
			<td class="title" style="width:20%;text-align:left;">备注</td>
			<td class="title" style="width:20%;text-align:left;">审批时间</td>
		</tr>
		<tr>
			<td>业务部主管（<a style="color:red">${AllUsers[list.busManagerId].name}</a>）</td>
			<td >
			<c:if test="${empty list.approveResult01 and list.status==1}">
				<a style="color:red;">正在审批</a>
			</c:if>
			<c:if test="${empty list.approveResult01 and list.status!=1}">
				<a style="color:red;">暂未达到该流程</a>
			</c:if>
			<c:if test="${not empty list.approveResult01}">
				<a >${list.approveResult01}</a>
			</c:if>
			</td>
			<td>
			<c:if test="${empty list.approveOpinion01}">
				<a style="color:red;">暂无</a>
			</c:if>
			<c:if test="${not empty list.approveOpinion01}">
				<a >${list.approveOpinion01}</a>
			</c:if>
			</td>
			<td>
			<c:if test="${empty list.approveDate01}">
				<a style="color:red;">暂无</a>
			</c:if>
			<c:if test="${not empty list.approveDate01}">
				<a >${list.approveDate01}</a>
			</c:if>
			</td>
		</tr>
		</table>
		<table class="table1" id='cdTable' >
		
		<c:forEach items="${support}" var="cur">
		<tr>
			<td style="width:20%">技术部主管（<a style="color:red">${AllUsers[list.tecManagerId].name}</a>）</td>
			<td style="width:20%">
			<c:if test="${empty cur.approveResult02 and list.status==2}">
				<a style="color:red;">正在审批</a>
			</c:if>
			<c:if test="${empty cur.approveResult02 and list.status!=2}">
				<a style="color:red;">暂未达到该流程</a>
			</c:if>
			<c:if test="${not empty cur.approveResult02}">
				<a >${cur.approveResult02}</a>
			</c:if>
			</td>
			<td style="width:20%">
			<c:if test="${empty cur.approveOpinion02}">
				<a style="color:red;">暂无</a>
			</c:if>
			<c:if test="${not empty cur.approveOpinion02}">
				<a>${cur.approveOpinion02}</a>
			</c:if>
			</td>
			<td style="width:20%">
			<c:if test="${empty cur.approveDate02}">
				<a style="color:red;" >暂无</a>
			</c:if>
			<c:if test="${not empty cur.approveDate02}">
				<a>${cur.approveDate02}</a>
			</c:if>
			</td>
		</tr>
		</c:forEach>
		
		<c:if test="${ empty support}">
			<tr><td colspan="4" style="color:red"> 暂无数据</td></tr>
		</c:if>
		</table>
		<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		技术支持详情</span>
		</div>
		<table class="table1" id='cdTable' >
			<tr>
				<td class="title" style="width:12%;text-align:left;">第一负责人</td>
				<td class="title" style="width:12%;text-align:left;">第二负责人</td>
				<td class="title" style="width:12%;text-align:left;">处理完成时间</td>
				<td class="title" style="width:12%;text-align:left;">处理结果</td>
				<td class="title" style="width:12%;text-align:left;">附件文档</td>
				<td class="title" style="width:12%;text-align:left;">客户反馈时间</td>
				<td class="title" style="width:12%;text-align:left;">客户反馈结果</td>
				<td class="title" style="width:12%;text-align:left;">客户意见</td>
			</tr>
			<c:if test="${not empty support}">
			<c:forEach items="${support}" var="cur">
			<c:if test="${cur.FAEId!=0}">
			<tr>
				<td style="width:12%;text-align:left;"><a style="color:red">${AllUsers[cur.FAEId].name}</a></td>
				<td style="width:12%;text-align:left;"><a style="color:red">${AllUsers[cur.FAEId02].name}</a></td>
				<c:if test="${not empty cur.FAETime}">
				<td style="width:12%;text-align:left;">${cur.FAETime}</td>
				</c:if>
				<c:if test="${empty cur.FAETime}">
				<td style="width:12%;text-align:left;" style="color:red">暂无</td>
				</c:if>
				<c:if test="${not empty cur.FAEResult}">
				<td style="width:12%;text-align:left;">${cur.FAEResult}</td>
				</c:if>
				<c:if test="${empty cur.FAEResult}">
				<td style="width:12%;text-align:left;" style="color:red">暂无</td>
				</c:if>
				<c:if test="${not empty cur.file2}">
				<td style="width:12%;text-align:left;"><a href="${ctx }/upload/${cur.file2}">点击下载附件</a></td>
				</c:if>
				<c:if test="${empty cur.file2}">
				<td style="width:12%;text-align:left;">暂无</td>
				</c:if>
				<c:if test="${not empty cur.customerDate}">
				<td style="width:12%;text-align:left;">${cur.customerDate}</td>
				</c:if>
				<c:if test="${empty cur.customerDate}">
				<td style="width:12%;text-align:left;" style="color:red">暂无</td>
				</c:if>
				<c:if test="${not empty cur.customerResult}">
				<td style="width:12%;text-align:left;">${cur.customerResult}</td>
				</c:if>
				<c:if test="${empty cur.customerResult}">
				<td style="width:12%;text-align:left;" style="color:red">暂无</td>
				</c:if>
				<c:if test="${not empty cur.customerOpinion}">
				<td style="width:12%;text-align:left;">${cur.customerOpinion}</td>
				</c:if>
				<c:if test="${empty cur.customerOpinion}">
				<td style="width:12%;text-align:left;" style="color:red" >暂无</td>
				</c:if>
			</tr>
			</c:if>
			<c:if test="${cur.FAEId==0}">
			<tr>
				<td colspan="8" style="color:red">暂无数据</td>
			</tr>
			</c:if>
			</c:forEach>
			</c:if>
			<c:if test="${empty support}">
			<tr>
				<td colspan="8" style="color:red">暂无数据</td>
			</tr>
			</c:if>
		</table>
		<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		结案确认</span>
		</div>
		<table class="table1" id='cdTable' >
			<tr>
				<td class="title" style="width:20%;text-align:left;">确认人</td>
				<td class="title" style="width:20%;text-align:left;">确认时间</td>
				<td class="title" style="width:20%;text-align:left;">确认结果</td>
				<td class="title" style="width:20%;text-align:left;">备注</td>
			</tr>
			<c:if test="${ not empty list.finalResult}">
			<tr>
				<td  style="width:20%;text-align:left;">技术部主管（<a style="color:red;">${AllUsers[list.tecManagerId].name}</a>）</td>
				<c:if test="${not empty list.endTime}">
				<td  style="width:20%;text-align:left;">${list.endTime }</td>
				</c:if>
				<c:if test="${ empty list.endTime}">
				<td  style="width:20%;text-align:left;" style="color:red;">暂无</td>
				</c:if>
				<td  style="width:20%;text-align:left;">${list.finalResult }</td>
				<c:if test="${not empty list.content}">
				<td  style="width:20%;text-align:left;">${list.content }</td>
				</c:if>
				<c:if test="${ empty list.content}">
				<td  style="width:20%;text-align:left;" style="color:red;">暂无</td>
				</c:if>
			</tr>
			</c:if>
				<c:if test="${ empty list.finalResult}">
				<tr><td  colspan="8"><a style="color:red">暂无数据</a></td></tr>
				</c:if>
		</table>
	</form>
	</div>
</div>

</body>
</html>
<script>
var index;

$(function(){
	$('#OA申请').addClass('ui-tabs-current');	
	$('#技术支持申请').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	});


</script>