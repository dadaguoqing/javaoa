<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['222']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" method="post" action="${ctx}/web/oa/kq/njList">
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">选择部门：</td>
			<td>
			<select name="deptId" id="cDept">
				<option value="0">所有人员</option>
				<c:forEach items="${depts}" var="cur">
				<option value="${cur.id }">${cur.name }</option>
				</c:forEach>
			</select>
			</td>
			<td class="right" style="width:80px;">年假剩余：</td>
			<td>
			<select name="njsy" id="njsy">
				<option value="0">所有数据</option>
				<option value="1">0天以下</option>
				<option value="2">0到2天</option>
				<option value="3">2天以上</option>
			</select>
			</td>
			<td>
				<div style="padding:0px 10px;">
					<div class="buttonDiv saveBtn" id="sBtn">
						<span>搜索</span>
						<img src="${ctx}/resources/images/server_add.png" style="width:16px;height:16px;"/>
					</div>
			</div>
			</td>	
		</tr>
	</table>
	</form>
	
	<form name="detailForm" action="${ctx}/web/oa/nianjia/empRecord" method="post">
		<input type="hidden" id="empId" name="empId"/>
		<input type="hidden" id="jtype" name="jtype"/>
	</form>
	
	<form name="leaveForm" action="${ctx}/web/oa/leave/empRecords" method="post">
		<input type="hidden" id="empId2" name="empId"/>
	</form>
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="4">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				员工年假（病假）剩余
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:140px;">员工姓名</td>
			<td class="l-td-header l-td-left" style="width:140px;">年假剩余</td>
			<td class="l-td-header l-td-left" style="width:140px">病假剩余</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:140px">操作</td>
		</tr>
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">${cur.empName }</td>
    			<td class="l-td l-td-left">
    			<a href="javascript:goDetail(${cur.empId}, 0)">
    			${cur.nianjiaStr }
    			</a>
    			</td>
				<td class="l-td l-td-left" style="">
				<a href="javascript:goDetail(${cur.empId}, 1)">
					${cur.bingjiaStr }
				</a>
				</td>
				<td class="l-td l-td-left l-td-last-right" style="">
					<a href="javascript:goDetail2(${cur.empId})">查看请假记录</a>					
				</td>
    		</tr>
    	</c:forEach>
    		
	</tbody></table>

	</div>
	
	</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#OA查询').addClass('ui-tabs-current');	
	$('#年假查询').addClass('cur');		

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#cDept').val('${deptId}');
	$('#njsy').val('${njsy}');
	
	$('#sBtn').click(function(){
		document.sForm.submit();
	});
});

function goDetail(empId, type){
	$('#empId').val(empId);
	$('#jtype').val(type);

	document.detailForm.submit();
}

function goDetail2(empId){
	$('#empId2').val(empId);

	document.leaveForm.submit();
}
</script>
</html>