<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="${ctx }/web/oa/prop/addType"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;添加资产类别</div>
	<div class="navTitle2" url="${ctx }/web/oa/prop/add"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">
		${msg }
	</div>
	</c:if>
	
	<div style="width:92px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加资产类别</div>
	<form name="form1" method="post" action="${ctx}/web/oa/prop/addType">
	<input name="submitCode" value="${submitCode }" type="hidden" />
	
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  					请先选择一个一级类别，然后填写类别名称，点击添加（本功能只用于添加二级类别）。
  				</div>
  			
  				<div class="">
  				
					<div class="lzui-headers">
						<div class="t1"><span>&nbsp;</span>一级类别</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;">
			  					<input type="radio" disabled="disabled"/>
			  				</th>
			  				<th style=" width:80px;"> | 类别名称 </th>
			  			</tr>
			  			
			  			<c:forEach items="${firstTypes}" var="cur" varStatus="st">
			  			<tr class="lzui-td rtd">
			  				<td style="text-align:center; padding-left:15px;">
			  					<input onclick="ajaxTypes();" ${ cur.id==cType ? 'checked' : ''} class="rids" name="pId" type="radio" value="${cur.id }"/>
			  				</td>
			  				<td>${cur.name }</td>
			  			</tr>
			  			</c:forEach>
			  			
					    </table>
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t2"><span>&nbsp;</span>二级类别</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;" id="secondTypes">
			  			
			  			
					    </table>
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t3"><span>&nbsp;</span>填写名称，新增二级资产类别</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">类别名称<span style="color:red">*</span></span>
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

	var rid = $('.rtd input:checked').val();
	if(!rid){
		alert("请选择一级类别。");
		return;
	}

	
	var name = $('#name').val();
 	if(!name){
    	alert("请填写资产类别名称。");
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

	//ie兼容
	$('.lzui-td input').click(function(evt){
		evt.stopPropagation();
	});

	$('.rtd').click(function(evt){
		var item = $(this).find("input");
		item.prop('checked',true);
		ajaxTypes();
	});

	var cType = '${cType}';

	if(cType){
		ajaxType(cType);
	}
});

function ajaxTypes(){
	var pid = $('.rtd input:checked').val();
	
	$.get('${ctx}/web/oa/prop/ajaxTypes/'+pid+"?t="+Math.random(), function(data){
		$('#secondTypes').html(data);
	});
}

function ajaxType(pid){
	//var pid = $('.rtd input:checked').val();
	
	$.get('${ctx}/web/oa/prop/ajaxTypes/'+pid+"?t="+Math.random(), function(data){
		$('#secondTypes').html(data);
	});
}
</script>