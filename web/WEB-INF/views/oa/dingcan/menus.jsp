<%@ page language="java" import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<<style>
<!--
.selectBak {background: #E74C3C; color:#eee; font-weight:bold;}
.selectBak:hover{background: #E74C3C; color:#eee; font-weight:normal;}
-->
</style>

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
		<div style="width:90px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">菜单管理</div>
		
		<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
				<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
				查询条件</div>
			<form name="sForm" method="post" action="${ctx}/web/oa/dc/addMenu">
			<table id="baseInfoTable">
				<tr>
					<td class="right" style="width:80px;">供应商：</td>
					<td>
						<select name="pId" id="pId">
							<option value="0">-选择-</option>
			    			<c:forEach items="${providers}" var="cur">
			    			<option value="${cur.id }">${cur.name }</option>
			    			</c:forEach>
			    		</select>
					</td>
				</tr>
			</table>
			</form>
	
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
		
		<form name="form1" action="${ctx}/web/oa/dc/addMenu" method="post" onsubmit="return beforeSubmit();">
		<input type="hidden" value="${submitCode}" name="submitCode"/>
		<input type="hidden" value="${pId}" name="pId"/>
		<div class="lzui-table-wapper" style="margin-top:20px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:160px;">供应商名称</th>
	  				<th style="padding-left:15px; width:280px;"> | 菜单描述</th>
	  				<th style=" width:140px;"> | 价格</th>
	  				<th style=""> | 操作</th>
	  			</tr>
	  			
	  			<tr class="lzui-td ttd" id="t2">
			    	<td>
			    		<select name="providerId" id="providerId">
			    			<c:forEach items="${providers}" var="cur">
			    			<option value="${cur.id }">${cur.name }</option>
			    			</c:forEach>
			    		</select>
			    	</td>
			    	<td>
						<input name="description" id="description" style='width:250px;'/>
					</td>
			    	<td>
						<input name="price" id="price" style='width:120px;' placeHolder="价格必须为正整数"/>
					</td>
			    	<td>
						<a href="javascript:addProvider();">点击添加</a>
					</td>
			    </tr>
	  			
	  			<c:forEach items="${menus}" var="cur">
	  			
		  		<tr class="lzui-td ttd" id="t1">
		  			<td>${cur.providerName }</td>
			    	<td>${cur.description }</td>
			    	<td>${cur.price }</td>
			    	<td>
						<a href="javascript:delMenu('${cur.description}',${cur.id});">删除</a>
					</td>
			    </tr>
			    </c:forEach>
			   
			    </table>
			</div>
			</form>
	</div>
</div>
</div>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#订餐管理').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#pId').change(function(){
		document.sForm.submit();
	});

	$('#pId').val('${pId}');

	$('#providerId').val('${0 == pId ? 4 : pId}');

});

function beforeSubmit(){
	var description = $('#description').val();

	if(!description){
		alert('请输入菜单名称');
		return false;
	}

	var price = $('#price').val();
	if(!price){
		alert('请输入价格');
		return false;
	}
		
	return true;
}

function delMenu(name,id){
	var f = confirm("确定要删除菜单《"+name+"》？");
	if(f){
		//alert(1);
		document.location.href = "${ctx}/web/oa/dc/delMenu?id="+id+"&pId=${pId}";
	}
}

function addProvider(){
	var flag = beforeSubmit();
	if(flag){
		document.form1.submit();
	}
}
</script>
</body>
</html>