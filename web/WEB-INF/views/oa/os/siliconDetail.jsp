<%@ page language="java" import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/os/siliconApplyList"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:120px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">芯片申请</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/os/saveSiliconApprove" method="post" onsubmit="return beforeSubmit();">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${app.id}" name="id"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:99%;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }芯片申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:10%;">申领人：</td>	
			<td style="width:10%;">
				${app.empName }
			</td>	
			<td class="title" style="width:10%;">所属部门：</td>	
			<td style="width:10%;">
				${AllDepts[app.deptId].name }
			</td>
			<td class="title" style="width:100px;">申领时间：</td>
			<td style="width:170px;	">
				${app.dayStr }
			</td>	
			<td class="title" style="width:100px;" name="useTime">需求时间：</td>
			<td>${app.useDayStr }</td>
		</tr>
		
	</tbody></table>
	
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		芯片详细信息</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:20%;">
				芯片型号 
			</td>
 			<td class="title" style="text-align:left; width:20%; ">
				申请数量
			</td>
 			<td class="title" style="text-align:left; width:20%; ">
				单位
			</td>
			<td class="title" style="text-align:left;  width:120px; ">
				申请原因
			</td>		
		</tr>
		
		<c:forEach items="${list}" var="cur">
		<tr>
			<td style="">
				${cur.brand }
			</td>
			<td style="">
				${cur.num }
			</td>
			<td style="">
				颗(PCS)
			</td>
			<td style="">
				${cur.reason }
			</td>
		</tr>
		</c:forEach>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批记录</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td style=" width:20%; ">
				${AllDepts[app.deptId].name }一级审批
				<c:if test="${app.approveId01 != 0 }">
					<span style="color:red;">(${AllUsers[app.approveId01].name})</span>
				</c:if>
			</td>
			<td style=" width:75%;color:red; ">
				<c:choose>
			<c:when test="${  app.approveId01 == 0 }">
					<span style=" color:#666;" >本次审批不需要经过此流程</span>
				</c:when>
				<c:when test="${ app.currentId == app.approveId01 && app.status == 0}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty app.approveReason01 ? '暂未到达该流程' : app.approveReason01  }
				</c:otherwise>
				</c:choose>
			</td>
			</tr><tr>
			<td style=" width:5%; ">
				${AllDepts[app.deptId].name }二级审批
				<c:if test="${app.approveId02 != 0}">
					<span style="color:red;">(${AllUsers[app.approveId02].name})</span>
				</c:if>
			</td>
			<td style=" width:16%;color:red;">
				<c:choose>
				<c:when test="${ app.approveId02 == 0 }">
					<span style=" color:#666;" >本次审批不需要经过此流程</span>
				</c:when>
				<c:when test="${ app.currentId == app.approveId02 && app.status == 1}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty app.approveReason02 ? '暂未到达该流程' : app.approveReason02  }
				</c:otherwise>
				</c:choose>
			</td>
			</tr><tr>
			<td style=" width:5%; ">
				生产运营部二级审批
				<c:if test="${app.approveId03 != 0}">
					<span style="color:red;">(${AllUsers[app.approveId03].name})</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
				<c:choose>
				<c:when test="${ app.approveId03 == 0 }">
					<span style=" color:#666;" >本次审批不需要经过此流程</span>
				</c:when>
				<c:when test="${ app.currentId == app.approveId03 && app.status == 2}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty app.approveReason03 ? '暂未到达该流程' : app.approveReason03  }
				</c:otherwise>
				</c:choose>
			</td>
			</tr><tr>
			<td style=" width:5%; ">
				生产运营部一级审批
				<c:if test="${app.approveId04 != 0}">
					<span style="color:red;">(${AllUsers[app.approveId04].name})</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
				<c:choose>
				<c:when test="${ app.currentId == app.approveId04 && app.status == 3}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:when test="${ app.approveId04 == 0 }">
					<span style=" color:#666;" >本次审批不需要经过此流程</span>
				</c:when>
				<c:otherwise>
					${ empty app.approveReason04? '暂未到达该流程' : app.approveReason04  }
				</c:otherwise>
				</c:choose>
			</td>
			</tr><tr>
			<td style=" width:5%; ">
				芯片管理员
				<c:if test="${app.approveId05 != 0}">
					<span style="color:red;">(${AllUsers[app.approveId05].name})</span>
				</c:if>
			</td>
			<td style=" width:20%; color:red;">
				<c:choose>
				<c:when test="${ app.currentId == app.approveId05 && app.status == 4}">
					<span style=" color:green;" >正在处理</span>
				</c:when>
				<c:otherwise>
					${ empty app.approveReason05? '暂未到达该流程' : app.approveReason05  }
				</c:otherwise>
				</c:choose>
			</td>
			
		</tr>
	</tbody></table>
	
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script>
var index;

$(function(){
	$('#OA申请').addClass('ui-tabs-current');	
	$('#芯片申请').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	});

function beforeSubmit(){
	var tg = $('#tg').val();

	if(tg == '0'){
		alert('请选择审批意见');
		return false;
	}

	var yj = $('#opinion').val();
	yj = $.trim(opinion);
	if(!opinion){
		alert('请输入补充意见');
		return false;
	}
		
	return true;
}

function submitForm(){

	var flag = beforeSubmit();
	if(flag){
		document.form1.submit();
	}
}
</script>