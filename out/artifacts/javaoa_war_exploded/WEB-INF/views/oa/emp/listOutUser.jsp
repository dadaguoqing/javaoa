<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['2']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/user/addOutUser">添加外网访问码</a>
  	</div>
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="7">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				系统当前外网访问码列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:70px;">员工</td>
			<td class="l-td-header" style="width:170px;">创建时间</td>
			<td class="l-td-header" style="width:360px;">有效时间</td>
			<td class="l-td-header l-td-left" style="width:100px;">访问码</td>
			<td class="l-td-header" style="width:70px;">类型</td>
			<td class="l-td-header l-td-last-right" style="">操作</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">${cur.empName}</td>
    			<td class="l-td">${cur.createTime }</td>
    			<td class="l-td">
    			<c:if test="${cur.type == 0}">
    			${cur.beginTime } 到 ${cur.endTime }
    			</c:if>   
    			<c:if test="${cur.type == 1}">
    				<span style="color:red;">无时间限制</span>
    			</c:if> 			
    			</td>
    			<td class="l-td l-td-left">
    				${cur.accessCode}
				</td>
				<td class="l-td l-td-left">${cur.type == 0 ? '临时访问' : '永久访问' }</td>
    			<td class="l-td">
    				<a href="javascript:delOutUser('${cur.empName }',${cur.id });">删除</a>
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td-last-right" colspan="6" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
	</tbody></table>

</div>
	</div>
</div>
<script>
$(function(){
	$('#OA管理').addClass('ui-tabs-current');	
	$('#外网访问').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function delOutUser(name, id){
	var flag = confirm("确定要删除"+name+"的外网访问码？");
	if(flag){
		document.location.href = "${ctx }/web/oa/user/delOutUser/"+id;
	}
}
</script>
</body>
</html>