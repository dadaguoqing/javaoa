<%@ page language="java" import="java.util.*,com.hj.oa.bean.*,com.hj.util.*" pageEncoding="utf-8"%>
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

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">通知公告</div>
		
		<%!
			private boolean isIn(List<User> users, User user){
				boolean flag = false;
				for(User u : users){
					if(user.getId() == u.getId()){
						return true;
					}
				}
				return flag;
			}
		%>
		<%
			List<Role> roles = (List<Role>)session.getAttribute("loginUserRoles");
			User user = (User)session.getAttribute("loginUser");
			List<User> noticer = (List<User>)request.getAttribute("noticer");
			if( RoleUtil.hasRole(roles,"总经理") || isIn(noticer,user) ){
		%>
		<div class="" style="margin:10px 0;">
			<a href="${ctx }/web/oa/notice/add" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">发布公告</a>
			<!--  
			<a href="${ctx }/web/oa/notice/mgr" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">公告管理</a>
			-->
		</div>
		
		 
		<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
			<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
			查询条件</div>
		<form name="sForm" action="${ctx}/web/oa/notice/list" method="post" >
		<table id="baseInfoTable">
			<tr>
				<td class="right" style="width:110px;">所属分类：</td>
				<td>
				<select name="type" id="type">
					<option value="全部">全部</option>
					<option value="公司通知">公司通知</option>
					<option value="新增功能">新增功能</option>
					<option value="规章制度">规章制度</option>
					<option value="公文模板">公文模板</option>
					<option value="组织结构">组织结构</option>
				</select>
				</td>
			</tr>
		</table>
		</form>
		
		
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:10px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="6">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					通知公告列表
					</div>
				</td>
			</tr>
			
			<tr>
				
				<td class="l-td-header" style="width:190px;">发布时间</td>
				<td class="l-td-header" style="width:120px; padding-left:0; padding-right:0;">所属分类</td>
				<td class="l-td-header l-td-left" style="">标题</td>
				<td class="l-td-header l-td-left" style="width:80px;">发布人</td>
				<td class="l-td-header l-td-left" style="width:110px;">发布部门</td>
				<td class="l-td-header l-td-last-right" style="width:100px;">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur">
	    		<c:if test="${cur.status == -1}">
	    		<tr id="trd${cur.id }" style="color:red;">
	    		</c:if>
	    		<c:if test="${cur.status != -1}">
	    		<tr id="trd${cur.id }">
	    		</c:if>
	    			<td class="l-td">${cur.createTime }</td>
	    			<td class="l-td">${empty cur.type ? '暂未分类' :  cur.type} </td>
	    			<td class="l-td l-td-left">${cur.title } </td>
	    			<td class="l-td l-td-left">${cur.publisher }</td>
	    			<td class="l-td l-td-left">${cur.pubName }</td>
	    			<td class="l-td">
	    				<c:if test="${cur.showType==0}">
						<a href="javascript:_read(${cur.id },${cur.status});">查看</a> |
						</c:if>
						<c:if test="${cur.showType==1}">
						<a target="_blank" href="${ctx }/web/oa/notice/${cur.id }">查看</a> |
						</c:if>
						<a href="javascript:_del(${cur.id },'${cur.title}');">删除</a>
					</td>
	    		</tr>
	    		<c:if test="${cid == cur.id}">
	    		<tr class="cont" id="tr${cur.id }" style="color:blue; font-weight:bold;">
	    		</c:if>
	    		<c:if test="${cid != cur.id}">
	    		<tr class="cont" id="tr${cur.id }" style=" display:none;">
	    		</c:if>
	    			<td class="l-td" colspan="6" style="text-align:left; padding:15px; ">
	    				${cur.content }
	    			</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="6" style="color:red;">
					对不起，目前还没有相关数据。
				</td>
			</tr>
	    	</c:if>
		</tbody></table>
		</div>
		<%
			}else{
		%>
		 
		<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
			<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
			查询条件</div>
		<form name="sForm" action="${ctx}/web/oa/notice/list" method="post" >
		<table id="baseInfoTable">
			<tr>
				<td class="right" style="width:110px;">所属分类：</td>
				<td>
				<select name="type" id="type">
					<option value="全部">全部</option>
					<option value="公司通知">公司通知</option>
					<option value="新增功能">新增功能</option>
					<option value="规章制度">规章制度</option>
					<option value="公文模板">公文模板</option>
					<option value="组织结构">组织结构</option>
				</select>
				</td>
			</tr>
		</table>
		</form>
		
		
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:10px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="6">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					通知公告列表
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header" style="width:190px;">发布时间</td>
				<td class="l-td-header" style="width:120px; padding-left:0; padding-right:0;">所属分类</td>
				<td class="l-td-header l-td-left" style="">标题</td>
				<td class="l-td-header l-td-left" style="width:80px;">发布人</td>
				<td class="l-td-header l-td-left" style="width:110px;">发布部门</td>
				<td class="l-td-header l-td-last-right" style="width:100px;">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur">
	    		<c:if test="${cur.status == -1}">
	    		<tr id="trd${cur.id }" style="color:red;">
	    		</c:if>
	    		<c:if test="${cur.status != -1}">
	    		<tr id="trd${cur.id }">
	    		</c:if>
	    			<td class="l-td">${cur.createTime }</td>
	    			<td class="l-td">${empty cur.type ? '暂未分类' :  cur.type} </td>
	    			<td class="l-td l-td-left">${cur.title } </td>
	    			<td class="l-td l-td-left">${cur.publisher }</td>
	    			<td class="l-td l-td-left">${cur.pubName }</td>
	    			<td class="l-td">
						<c:if test="${cur.showType==0}">
						<a href="javascript:_read(${cur.id },${cur.status});">查看</a>
						</c:if>
						<c:if test="${cur.showType==1}">
						<a target="_blank" href="${ctx }/web/oa/notice/${cur.id }">查看</a>
						</c:if>
					</td>
	    		</tr>
	    		<c:if test="${cid == cur.id}">
	    		<tr class="cont" id="tr${cur.id }" style="color:blue; font-weight:bold;">
	    		</c:if>
	    		<c:if test="${cid != cur.id}">
	    		<tr class="cont" id="tr${cur.id }" style=" display:none;">
	    		</c:if>
	    			<td class="l-td" colspan="6" style="text-align:left; padding:15px;">
	    				${cur.content }
	    			</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="6" style="color:red;">
					对不起，目前还没有相关数据。
				</td>
			</tr>
	    	</c:if>
		</tbody></table>
		</div>
		
		<%
			}
		%>
	</div>
</div>
</div>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>

<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#通知公告').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#type').change(function(){
		document.sForm.submit();
	});

	//$('#type')

	$('#type').val('${type}');
});

function _del(id, title){
	var flag = confirm("确定要删除《"+title+"》？");
	if(flag){
		$.get("${ctx}/web/oa/notice/del/"+id,function(data){
				$('#tr'+id).remove();
				$('#trd'+id).remove();
				alert('删除成功');	
				location.reload();
		});		
	}
}

function _read(id, status){
	var dis = $('#tr'+id).css('display');
	$('.cont').css('display','none');
	if(dis == 'none'){
		$('#tr'+id).css('display','');
	}

	if(status == -1){

		$.get("${ctx}/web/oa/notice/read/?empId=${loginUser.id}&id="+id);
	}
}

</script>
</body>
</html>