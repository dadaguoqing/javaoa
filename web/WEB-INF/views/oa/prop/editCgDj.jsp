<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/prop/myCgSp"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">资产采购申请</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/prop/cgSpDj?applyId=${sq.id}" method="post" onsubmit="return beforeSubmit();">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }固定资产采购申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:100px;">申请人：</td>	
			<td style="width:120px;">
				${sq.proposerName }
			</td>	
			<td class="title" style="width:100px;">所属部门：</td>	
			<td style="width:90px;">
				${AllDepts[propEmp.deptId].name }
			</td>
			<td class="title" style="width:100px;">申请时间：</td>
			<td style="border-right:none;">
				${sq.createTime }
			</td>	
			
		</tr>
		
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		采购资产</span>
		<span style="color:red">（请填写预算资金）</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:160px;">
				资产名称 
			</td>
			<td class="title" style="text-align:left; width:450px;">
				备注信息（如：规格型号、配置等）
			</td>
			<td class="title" style="text-align:left; width:60px;">
				单位
			</td>
			<td class="title" style="text-align:left; width:60px;">
				单价
			</td>
			<td class="title" style="text-align:left; width:60px;">
				数量
			</td>
			<td class="title" style="text-align:left; width:100px;color:red;">
				预算资金(元)
			</td>
		</tr>
		<c:forEach items="${list}" var="cur">
		<tr>
			<td style="">
				${cur.name }
			</td>
			<td style="">
				${empty cur.bz ? '无' : cur.bz }
			</td>
			<td>
				${cur.unit }
			</td>
			<td>
				${cur.price }元
			</td>
			<td>
				${cur.num }
			</td>
			<td>
			<input type="hidden" value="${cur.id }" name="ids" id = "ids"/>
				<span class="jrc">${cur.money }</span>元
			</td>
		</tr>
		</c:forEach>
		
		<tr style="color:red;font-weight:bold;">
			<td style="">
				金额总计
			</td>
			<td style="" colspan="4">
				
			</td>
			<td>
				<span id="zjje">0.0</span>元
			</td>
		</tr>	
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		采购事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" disabled style="width:50%; height:80px; padding:5px 10px;">${sq.content}</textarea>
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批记录</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			
			<td style="width:16%;">
				总监意见
				<c:if test="${ not empty sq.directId }">
					<span style="color:red;">（${AllUsers[sq.directId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%;color:red;">
			
				<c:choose>
				<c:when test="${ empty sq.directId }">
					<span style=" color:#666;" >本次审核不需要经过总监</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.directId && sq.status == 1}">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.directCmt ? '暂未到达该流程' : sq.directCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			
			<td style=" width:16%; ">
				财务主管意见
				<c:if test="${ not empty sq.caiwuId }">
					<span style="color:red;">（${AllUsers[sq.caiwuId].name}）</span>
				</c:if>
			</td>
			<td style=" width:16%; color:red;">
			
				<c:choose>
				<c:when test="${ empty sq.caiwuId }">
					<span style=" color:#666;" >本次审批不需要经过主管</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.caiwuId && sq.status == 3}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.caiwuCmt ? '暂未到达该流程' : sq.caiwuCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			
			<td style="width:16%;">
				总经理意见
				<c:if test="${ not empty sq.bossId }">
					<span style="color:red;">（${AllUsers[sq.bossId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%; color:red; border-right:none; ">
			
				<c:choose>
				<c:when test="${ empty sq.bossId }">
					<span style=" color:#666;" >本次审核不需要经过总经理</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.bossId && sq.status == 4}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.bossCmt ? '暂未到达该流程' : sq.bossCmt  }
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
$(function(){
	$('#OA审批').addClass('ui-tabs-current');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

function jsje(){
	var zjje = 0.0;
	var al = $('.jrc');
	al.each(function(i){
		var jr = $(this).val();
		if(jr){
			var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		    if(!exp.test(jr)){
		    	alert('请正确填写预算资金');
				return;
		    }
			zjje += parseFloat(jr);
		}
	});
	$('#zjje').html(zjje);
}
jsje();
function beforeSubmit(){
	return true;
}

function submitForm(){

	var flag = beforeSubmit();
	if(flag){
		document.form1.submit();
	}
}
</script>