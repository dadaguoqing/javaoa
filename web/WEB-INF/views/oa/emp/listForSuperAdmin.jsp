<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['3']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">员工管理</div>
		
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="7">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					员工列表
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header l-td-left" style="width:60px;">姓名</td>
				<td class="l-td-header l-td-left" style="width:60px;">登录帐号</td>
				<td class="l-td-header" style="width:60px;">性别</td>
				<td class="l-td-header" style="width:120px;">访问密码</td>
				<td class="l-td-header l-td-left" style="width:80px;">所属部门</td>
				<td class="l-td-header l-td-left" style="width:200px;">手机号码</td>
				<td class="l-td-header l-td-last-right" style="">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur" >
	    		<tr>
	    			<td class="l-td l-td-left">${cur.name }</td>
	    			<td class="l-td l-td-left">${cur.code }</td>
	    			<td class="l-td">${cur.gender }</td>
	    			<td class="l-td">${cur.password }</td>
	    			<td class="l-td l-td-left">${cur.deptName }</td>
	    			<td class="l-td l-td-left">${cur.phone1 }</td>
	    			<td class="l-td">
						<a href="${ctx}/web/oa/user/edit?id=${cur.id}">详情</a> 
					</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="7" style="color:red;">
					对不起，目前还没有相关数据。
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
	$('#员工管理').addClass('ui-tabs-current');
	$('#员工列表').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});


function delUser(id,name){
	var flag = window.confirm('您确定要删除用户：'+name+"？");

	if(flag){
		document.location.href = "${ctx}/web/oa/user/delete?id="+id;
	}
	//
}
</script>
<%@ include file="../../public/footer.jsp" %>