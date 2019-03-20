<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<style>
<!--
.shortInput {width:40px;}
-->
</style>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/lab/myGwSqRecord"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>

</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div style="border:1px solid #2191C0; margin-top:10px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }钢网加工工艺要求说明书</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:120px;">文件名称：</td>	
			<td style="width:90px;">
				${pcb.fileName}
			</td>	
			<td class="title" style="width:90px;">投板时间：</td>
			
			<td  style="border-right:none; width:200px;">	
				${pcb.createTime}
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">文档目录：</td>	
			<td style="width:90px;">
				${pcb.filePath }
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">订单编号：</td>	
			<td style="width:90px;">
				${pcb.ddbh }
			</td>	
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:90px;">申请人：</td>	
			<td style="width:90px;" colspan="7">
				${sq.proposerName }
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		详细参数</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:105px;">数量：</td>	
			<td style="">
				${pcb.numSet}set
			</td>	
				
			<td class="title" style="border-left:none; width:120px;">尺寸：</td>	
			<td style="">
				${pcb.ccChang }mm &nbsp;*&nbsp;
				${pcb.ccKuang }mm
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">钢网厚度：</td>	
			<td style="">
				${pcb.cpbh}
				
			</td>
		</tr>
		
		<tr>
				
			<td class="title" style="width:90px;">钢网材料：</td>	
			<td style="">
				${pcb.cl }
			</td>	
			<td class="title" style="border-left:none; width:90px;">用途：</td>	
			<td style="">
				${pcb.bmtc}
			</td>	
			<td class="title" style="border-left:none; width:90px;">抛光工艺：</td>	
			<td style="">
				${pcb.bmtchd}
			</td>	
		</tr>
		
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		加工相关信息</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">加工厂商：</td>	
			<td style="">
				${sq.jgcs }
			</td>	
			<%-- 
			<td class="title" style="border-left:none; width:90px;">报价：</td>	
			<td style="">
				${sq.bj }元
			</td>	
			--%>
			<td class="title" style="border-left:none; width:120px;">预计交货时间：</td>	
			<td style="border-right:none;">
				${sq.jhsj }
			</td>	
		</tr>
	</tbody></table>
	<%-- 
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		备注信息</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled style="width:50%; height:80px; padding:5px 10px;">${sq.content }</textarea>
			</td>	
		</tr>
	</tbody></table>
	--%>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批记录</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			
			<td style="width:12%;">
				制板负责人意见
				<c:if test="${ not empty sq.zbId }">
					<span style="color:red;">（${AllUsers[sq.zbId].name}）</span>
				</c:if>
			</td>
			<td style="width:12%;color:red;">
			
				<c:choose>
				<c:when test="${ empty sq.zbId }">
					<span style=" color:#666;" >本次审核不需要经过制板负责人</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.zbId && sq.status == 1}">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.zbCmt ? '暂未到达该流程' : sq.zbCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			
			<td style="width:12%;">
				主管意见
				<c:if test="${ not empty sq.mgrId }">
					<span style="color:red;">（${AllUsers[sq.mgrId].name}）</span>
				</c:if>
			</td>
			<td style="width:12%;color:red;">
			
				<c:choose>
				<c:when test="${ empty sq.mgrId }">
					<span style=" color:#666;" >本次审核不需要经过主管</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.mgrId && sq.status == 2}">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.mgrCmt ? '暂未到达该流程' : sq.mgrCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			
			<td style=" width:12%; ">
				财务主管意见
				<c:if test="${ not empty sq.caiwuId }">
					<span style="color:red;">（${AllUsers[sq.caiwuId].name}）</span>
				</c:if>
			</td>
			<td style=" width:12%; color:red;">
			
				<c:choose>
				<c:when test="${ empty sq.caiwuId }">
					<span style=" color:#666;" >本次审批不需要经过财务主管</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.caiwuId && sq.status == 3}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.caiwuCmt ? '暂未到达该流程' : sq.caiwuCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			
			<td style="width:12%;">
				总经理意见
				<c:if test="${ not empty sq.bossId }">
					<span style="color:red;">（${AllUsers[sq.bossId].name}）</span>
				</c:if>
			</td>
			<td style="width:12%; color:red; border-right:none; ">
			
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
	
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#OA申请').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

</script>