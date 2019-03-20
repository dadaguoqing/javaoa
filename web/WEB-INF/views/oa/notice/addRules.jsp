<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
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
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	<form name="form1" action="${ctx }/web/upload" method="post" enctype="multipart/form-data">
	<div style="border:1px solid #2191C0; margin-top:10px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">添加规章制度</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:120px;">发布者：</td>	
			<td style="width:90px;">
				${loginUser.name }
			</td>	
			<td class="title" style="width:90px;">发布时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
			%>
			<td  style="border-right:none;">
				${now }
			</td>	
		</tr>
		<tr>
			<td class="title" style="width:120px;">规章制度名称：</td>	
				
			<td class="title" style="border-right:none; text-align:left; overflow:hidden;" colspan="3">
				<input name="name" id="name" style="width:300px;"/>
				<span style="color:red; margin-left:20px; font-weight:normal;">不超过50个字符</span>
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		上传文件</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<input type="file" name="uploadFile" id="uploadFile"/>
				<span style="color:red; margin-left:20px;">文件必须是pdf格式，且不能超过100Mb</span>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">发布规章制度</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#规章制度').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	
});


function submitForm(){
	var name = $('#name').val();
	name = $.trim(name);

	var uploadFile = $('#uploadFile').val();

	if(!name){
		alert('规章制度名称');
		return;
	}
	
	if(!uploadFile){
		alert('请选择上传文件');
		return;
	}
	//alert('ok');
	document.form1.submit();
}
</script>