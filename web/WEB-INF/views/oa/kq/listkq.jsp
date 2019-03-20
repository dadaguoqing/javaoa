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
	<form name="sForm" action="${ctx}/web/oa/kq/list" method="post" >
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">考勤月份：</td>
			<td>
			<input name="yf" id="yf" value="${yf}" class="Wdate" style="width:100px;" readonly="readonly" autocomplete="off" 
				onFocus="WdatePicker({dateFmt:'yyyy年MM月'})"/>
			</td>
			
			<td class="right" style="width:80px;">选择部门：</td>
			<td>
			<select name="deptId" id="cDept">
				<c:forEach items="${depts}" var="cur">
				<option value="${cur.id }">${cur.name }</option>
				</c:forEach>
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
	
	<form name="detailForm" action="${ctx}/web/oa/kq/emp" method="post">
		<input type="hidden" id="dtId" name="id"/>
		<input type="hidden" id="dtDept" name="deptId"/>
		<input type="hidden" id="dtYf" name="yf"/>
	</form>
	
	<c:if test="${not empty total}">
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				${ empty dept ? '公司' : dept.name }${yf }考勤汇总
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:140px;">员工姓名</td>
			<td class="l-td-header l-td-left" style="width:140px;">请假时长</td>
			  
			<td class="l-td-header l-td-left" style="width:140px;">出差时长</td>
			<!--
			<td class="l-td-header l-td-left" style="width:140px;">加班时长</td>
			-->
			<td class="l-td-header l-td-left" style="width:140px">不正常打卡</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:140px">操作</td>
		</tr>
    	<c:forEach items="${all}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">${cur.empName }</td>
    			<td class="l-td l-td-left">${cur.timeLenStr }</td>
    			<td class="l-td l-td-left">${cur.wcLenStr }</td>
    			<!--
    			<td class="l-td l-td-left">
    				${ cur.jiabanStr  }
				</td>-->
				<td class="l-td l-td-left" style="">
					${cur.uncLenStr }
				</td>
				<td class="l-td l-td-left l-td-last-right" style="">
					<a href="javascript:goDetail(${cur.empId}, '${yf}', '${dept.id}')">查看详情</a>					
				</td>
    		</tr>
    	</c:forEach>
    		<tr style='color:red;'>
    			<td class="l-td l-td-left">总计：</td>
    			<td class="l-td l-td-left">${total.timeLenStr}</td>
    			  
    			<td class="l-td l-td-left">${total.wcLenStr}</td>
    			<!--
    			<td class="l-td l-td-left">
    				${ total.jiabanStr}
				</td>-->
				<td class="l-td l-td-left" style="">
					${total.uncLenStr }
				</td>
				<td class="l-td l-td-left l-td-last-right" style="">
					&nbsp;
				</td>
    		</tr>
	</tbody></table>

	</div>
	</c:if>
	
	</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#OA查询').addClass('ui-tabs-current');	
	$('#考勤查询').addClass('cur');		

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#cDept').val(${dept.id});

	$('#sBtn').click(function(){
		document.sForm.submit();
	});
});

function goDetail(id, yf, deptId){
	$('#dtId').val(id);
	$('#dtYf').val(yf);
	if(deptId){
		$('#dtDept').val(deptId);
	}

	document.detailForm.submit();
}
</script>
</html>