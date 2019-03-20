<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/prop/cgrk"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">资产采购申请</div>
	</div>
	
	<c:if test="${param.msg == 1}">
	<div class="lzui-error">资产添加成功，请继续添加，如果操作完成，请点击返回上级。</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/prop/cgSpvvv" method="post" onsubmit="return beforeSubmit();">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${sq.id}" name="id"/>
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
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:120px;">
				资产名称 
			</td>
			<td class="title" style="text-align:left; width:120px;">
				数量
			</td>
			<td class="title" style="text-align:left; width:350px;">
				备注信息（如：规格型号、配置等）
			</td>
			<td class="title" style="text-align:left; width:100px;">
				预算资金
			</td>
			<td class="title" style="text-align:left;">
				操作
			</td>
		</tr>
		<c:forEach items="${list}" var="cur">
		<tr>
			<td style="">
				${cur.name }
			</td>
			<td style="">
				${cur.num }
			</td>
			<td style="">
				${empty cur.bz ? '无' : cur.bz }
			</td>
			<td>
				${cur.money }元
			</td>
			<td>
				<c:if test="${cur.cgStatus==1}">
					<span style="color:green;">已入库</span>
				</c:if>
				<c:if test="${cur.cgStatus == -1}">
					<span style="color:red;">不入库</span>
					（原因：${cur.brk }）
				</c:if>
			
				<c:if test="${cur.cgStatus==0}">
				<a style="color:green;" href="javascript:rk(${sq.id}, ${cur.id },'${cur.name }');">入库</a>
				&nbsp;|
				<a style="color:red;" href="javascript:brk(${sq.id}, ${cur.id },'${cur.name }');">不入库</a>
				<input id="bz${cur.id}" placeholder="不入库请填写原因" style="width:150px;"/>
				
				</c:if>
			</td>
		</tr>
		</c:forEach>
		
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
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="${ctx }/web/oa/prop/compCg?cgId=${sq.id}" class="lzui-btn lzui-corner-all">该申请已经处理完成</a>
	</div>
	</form>
	</div>
	
			<form name="rkForm" id="rkForm" action="${ctx}/web/oa/prop/addCg" method="post">
				<input type="hidden" name="ocgId" id="ocgId"/>
				<input type="hidden" name="oid" id="oid"/>
				<input type="hidden" name="cgStatus" id="cgStatus" />
				<input type="hidden" name="bz" id="bz" />
			</form>
</div>

</body>
</html>
<script>
$(function(){
	$('#资产管理').addClass('ui-tabs-current');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

function goAdd(cgId, id){
	document.location.href = "${ctx}/web/oa/prop/addCg?ocgId="+cgId+"&oid="+id;
}

function rk(cgId, id, name){

	var flag = confirm("您确定入库[" + name + "]？");
	if(!flag){
		return;
	}

	$('#ocgId').val(cgId);
	$('#oid').val(id);
	$('#cgStatus').val('1');

	$('#rkForm').attr('action','${ctx}/web/oa/prop/addCg');
	document.rkForm.submit();
}

function brk(cgId, id, name){

	var flag = confirm("您确定不入库[" + name + "]？");
	if(!flag){
		return;
	}

	var bz = $('#bz'+id).val();
	if(!bz){
		alert("请填写不入库原因。");
		return;
	}

	$('#bz').val(bz);
	$('#ocgId').val(cgId);
	$('#oid').val(id);
	$('#cgStatus').val('-1');
	$('#rkForm').attr('action','${ctx}/web/oa/prop/brk');
	document.rkForm.submit();
}

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