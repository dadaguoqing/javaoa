<%@ page language="java" import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
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
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">规章制度</div>
		
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
	
		<%
			List<Role> roles = (List<Role>)session.getAttribute("loginUserRoles");
			if( RoleUtil.hasRole(roles,"制度管理员") ){
		%>
		<div class="" style="margin:10px 0 0 0;">
			<a href="${ctx }/web/oa/rules/add" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">添加规章制度</a>
		</div>
		
		<div class="lzui-table-wapper" style="margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px">规章制度名称</th>
	  				<th style=" width:200px;"> | 颁布日期</th>
	  				<th style=" width:120px;"> | 操作</th>
	  			</tr>
	  			
	  			<c:if test="${empty list}">
	  			<tr>
	  				<td style=" text-align:center; color:red;" colspan="3">
	  					目前系统中还没有添加规章制度。
	  				</td>
	  			</tr>
	  			</c:if>
	  			
	  			<c:forEach items="${list}" var="cur">
		  		<tr class="lzui-td" id="${cur.id }">
			    	<td>
			    	${cur.name }
			    	</td>
			    	<td>${cur.createTime }</td>
			    	<td>
						<a class="hrefs" href="javascript:delRule(${cur.id}, '${cur.name }');">删除</a>
						<!--  
						 |
						<a class="hrefs" href="${ctx }/web/oa/rules/download/${cur.id}">下载</a>
						-->
					</td>
			    </tr>
			    </c:forEach>
			    </table>
			</div>
		<%
			}else{
		%>
		
			<div class="lzui-table-wapper" style="margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px">规章制度名称</th>
	  				<th style=" width:200px;"> | 颁布日期</th>
	  			</tr>
	  			
	  			<c:if test="${empty list}">
	  			<tr>
	  				<td style=" text-align:center; color:red;" colspan="2">
	  					目前系统中还没有添加规章制度。
	  				</td>
	  			</tr>
	  			</c:if>
	  			
	  			<c:forEach items="${list}" var="cur">
		  		<tr class="lzui-td" id="${cur.id }">
			    	<td>
			    	${cur.name }
			    	</td>
			    	<td>${cur.createTime }</td>
			    </tr>
			    </c:forEach>
			    </table>
			</div>
		<%
			}
		%>
	
	</div>
</div>
</div>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#规章制度').addClass('cur');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('.hrefs').click(function(evt){
		evt.stopPropagation();
	});

	$('.lzui-td').click(function(){
		var $this = $(this);
		var id = $this.attr("id");
		var url = "${ctx}/web/oa/rules/"+id;
		window.open(url);
	});
});

function delRule(rid, name){
	var flag = confirm("您确定要删除《"+ name + "》？");

	if(flag){
		document.location.href= "${ctx}/web/oa/rules/del/"+rid;
	}
}
</script>
</body>
</html>