<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="${ctx }/web/oa/prop/addPlace"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;放置地点</div>
	<div class="navTitle2" url="${ctx }/web/oa/prop/add"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	
	<c:if test="${not empty param.msg}">
	<div class="lzui-error">
		<c:if test="${ param.msg == 1}">
		操作成功。
		</c:if>
	</div>
	</c:if>
	<c:if test="${not empty msg}">
	<div class="lzui-error">
		${msg }
	</div>
	</c:if>
	
	<div style="width:92px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">放置地点</div>
	<form name="form1" method="post" action="${ctx}/web/oa/prop/addPlace">
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  				</div>
  			
  				<div class="">
  				
					<div class="lzui-headers">
						<div class="t1"><span>&nbsp;</span>当前放置地点</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:160px; text-align:center; padding-left:15px;">
			  					序号
			  				</th>
			  				<th style=" width:180px;"> | 放置地点 </th>
			  				<th style=" width:180px;"> | 操作</th>
			  			</tr>
			  			
			  			<c:forEach items="${places}" var="cur" varStatus="st">
			  			<tr class="lzui-td">
			  				<td style="text-align:center; padding-left:15px;">
			  					${st.count }
			  				</td>
			  				<td>${cur.name }</td>
			  				<td><a href="${ctx }/web/oa/prop/deletePlace?id=${cur.id}" >删除</a></td>
			  			</tr>
			  			</c:forEach>
			  			
					    </table>
					    </div>
					    
					</div>
					
					
					<div class="lzui-headers">
						<div class="t3"><span>&nbsp;</span>填写名称，新增放置地点</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">放置地点名称<span style="color:red">*</span></span>
						<input name="name" id="name"/>
						</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
			  			<a href="javascript:submitRoles();" class="lzui-btn2" style="height:30px; line-height:30px; width:100px;font-weight:bold; font-size:15px;">添 加</a>
					    </div>
					    
					</div>
					
					<div style="clear:both;"></div>
  				
  				</div>	</form>
</div>
</div>

</div>

</body>
</html>
<script>

function submitRoles(){
	var name = $('#name').val();
 	if(!name){
    	alert("请填写放置地点名称。");
		return;
  	}
  	
 	document.form1.submit();
}

$(function(){
	$('#资产管理').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	
});
</script>