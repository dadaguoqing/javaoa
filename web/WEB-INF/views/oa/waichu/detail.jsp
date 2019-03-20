<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;审批详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/waichu/mysq"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<input type="hidden" value="${leave.id}" name="id"/>
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }出差申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">外出类型：</td>	
			<td style="width:90px;">
				${leave.type }
			</td>	
			<td class="title" style="width:60px;">申请人：</td>	
			<td style="width:90px;">
				${leave.proposerName }
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td>
				<span id="deptSpan">${AllUsers[leave.proposer].deptName}</span>
			</td>	
			<td class="title" style="width:90px;">申请时间：</td>
			<td  style="border-right:none; width:200px;">
				${leave.createTime }
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		起始时间</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">开始时间：</td>	
			<td style="width:160px;">
				${leave.beginTime }
			</td>	
			<td class="title" style="width:90px;">结束时间：</td>	
			<td style="width:160px;">
				${leave.endTime }
			</td>	
			<td class="title" style="width:90px;">出差时长：</td>	
			<td style="width:160px;">
				${leave.days }天${leave.hours }小时${leave.minutes }分钟
			</td>
				
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		出差事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled="disabled" name="content" style="width:50%; height:120px; padding:5px 10px;">${leave.content }</textarea>
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批意见</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td style="">
				主管意见
			</td>
			<td style=" color:red;">
				${ empty leave.mgrCmt ? '无' : leave.mgrCmt  }
			</td>
			<td style="">
				总监意见
			</td>
			<td style="color:red;">
				${ empty leave.directCmt ? '无' : leave.directCmt  }
			</td>
			<td style="">
				总经理意见
			</td>
			<td style="color:red; border-right:none; ">
				${ empty leave.bossCmt ? '无' : leave.bossCmt  }
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
	$('#出差申请').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
</script>