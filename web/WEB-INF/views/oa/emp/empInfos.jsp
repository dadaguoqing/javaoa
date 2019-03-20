<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<%@ include file="../../public/indexMenu.jsp" %>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:2px;">
	
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" method="post" action="${ctx}/web/oa/user/emp/infos">
	<table id="baseInfoTable">
		<tr>
			<!--  
			<td class="right" style="width:80px;">员工姓名：</td>
			<td>
			<input name="name" id="name" value="${name}" style="width:100px; line-height: 24px;"
			</td>
			-->
			<td class="right">
				<select name="deptId" id="deptId" onchange="_search();" style="border: 1px solid #AECAF0; height: 24px;">
					<option value="0">全体员工</option>
					<c:forEach items="${depts}" var="cur">
						<option value="${cur.id }">${cur.name}</option>
					</c:forEach>
					
				</select>
				
			</td>
			
			<!--  
			<td>
				<div style="padding:0px 10px;">
					<div class="buttonDiv saveBtn" id="sBtn">
						<span>查询</span>
						<img src="${ctx}/resources/images/server_add.png" style="width:16px;height:16px;"/>
					</div>
			</div>
			</td>-->
		</tr>
	</table>
	</form>
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:0px solid #ccc;">
		
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
			<c:if test="${loginUser.id == 8 or loginUser.id == 1 }">
				<td class="l-td-toolbar" colspan="7">
			</c:if>	
			<c:if test="${loginUser.id != 8 and loginUser.id != 1}">
				<td class="l-td-toolbar" colspan="6">
			</c:if>	
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					员工通讯录
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header" style="width:50px;">照片</td>
				<td class="l-td-header l-td-left" style="width:60px;">姓名</td>
				<td class="l-td-header" style="width:60px;">性别</td>
				<td class="l-td-header l-td-left" style="width:120px;">所属部门</td>
				<td class="l-td-header l-td-left" style="width:110px;">手机号码</td>
				<td class="l-td-header l-td-left" style="width:200px;">Email</td>
				<c:if test="${loginUser.id == 8 or loginUser.id == 1 }">
				<td class="l-td-header l-td-left" style="width:110px;">个人风采</td>
				</c:if>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur" >
	    		<tr>
	    			<td class="l-td">
	    			<c:if test="${empty cur.photo}">
	    				未上传
	    				<%-- 
		    			<c:if test="${cur.gender == '女' }">
		    			<img src="${ctx}/resources/images/girl.png" style="width:50px; height:50px;"/>
		    			</c:if>
		    			<c:if test="${cur.gender == '男' }">
		    			<img src="${ctx}/resources/images/boys.png" style="width:50px; height:50px;"/>
		    			</c:if>
		    			--%>
	    			</c:if>
	    			<c:if test="${not empty cur.photo}">
		    			<img src="${ctx}/web/oa/user/photo/${cur.id}" style="width:50px; height:50px;"/>
	    			</c:if>
	    			</td>
	    			<td class="l-td l-td-left">${cur.name }</td>
	    			<td class="l-td">${cur.gender }</td>
	    			<td class="l-td l-td-left">${cur.deptName }</td>
	    			<td class="l-td l-td-left">${cur.phone1 }</td>
	    			<td class="l-td l-td-left">${cur.email }</td>
	    			<c:if test="${loginUser.id == 8 or loginUser.id == 1 }">
	    			<td class="l-td l-td-left">
	    				<c:if test="${not empty cur.pdf}">
	    					<a target="_blank" href="${ctx}/web/oa/user/showPdf?id=${cur.id}">预览</a>
	    				</c:if>
	    				<c:if test="${ empty cur.pdf}">
	    					未上传
	    				</c:if>
	    			</td>
	    			</c:if>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="6" style="color:red;">
					对不起，没有找到相关数据。
				</td>
			</tr>
	    	</c:if>
		</tbody></table>
		</div>
	
	</div>
</div>
</div>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');
	$('#员工通讯录').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#deptId').val('${deptId}');
});

function _search(){
	document.sForm.submit();
}

</script>
<%@ include file="../../public/footer.jsp" %>