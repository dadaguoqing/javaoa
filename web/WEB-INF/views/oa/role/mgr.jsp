<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<script type="text/javascript" src="${ctx}/resources/fancybox/jquery.fancybox.js"></script>
<link rel="stylesheet" href="${ctx }/resources/fancybox/jquery.fancybox.css" />

<div id="inline1" style="display: none;">
<form name="form2" action="${ctx}/web/oa/role/add" method="post" style="padding:20px 40px 10px 40px; line-height:20px;">
	角色名称： <input name="roleName" id="roleName" style="padding:3px 5px;"/>
</form>
<p style="padding:15px 40px 20px 40px; line-height:20px;">
	<a href="javascript:addRole();" class="lzui-btn2" style="height:30px; line-height:30px; width:100px;font-weight:bold; font-size:15px;">提 交</a>
</p>
</div>

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
	
<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<c:if test="${not empty param.msg}">
	<div class="lzui-error">
		<c:if test="${ param.msg == 1}">
		权限设置成功。
		</c:if>
		<c:if test="${ param.msg == 2}">
		权限没有变化。
		</c:if>
		<c:if test="${ param.msg == 3}">
		权限设置已经提交总经理审核。
		</c:if>
	</div>
	</c:if>
	
	<div style="width:92px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">员工角色管理</div>
	<form name="form1" method="post" action="${ctx}/web/oa/role/setRoles">
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  					请按照下面的流程操作。或者点击这里，<a class="fancybox" href="#inline1" >添加角色</a>
  				</div>
  			
  				<div class="">
  				
					<div class="lzui-headers">
						<div class="t1"><span>1</span>第一步、选择一个角色</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="checkbox" id="checkAll" disabled="disabled"/></th>
			  				<th style=" "> | 角色名称</th>
			  			</tr>
			  			<c:forEach items="${roles}" var="cur">
			  			<tr class="lzui-td rtd">
			  				<td style="text-align:center; padding-left:15px;"><input onclick="ajaxEmps();" class="rids" name="rid" type="radio" value="${cur.id }"/></td>
			  				<td>${cur.name }</td>
			  			</tr>
			  			</c:forEach>
					    </table>
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t2"><span>2</span>第二步、选择该角色对应的员工</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="checkbox" id="checkAllUser" /></th>
			  				<th style=" width:80px;"> | 姓名 </th>
			  				<th style="	width:120px;"> | 登陆账号</th>
			  			</tr>
			  			
			  			<c:if test="${empty users}">
			  			<tr>
			  				<td style=" text-align:center; color:#27AE60;" colspan="3">
			  					系统中目前还没有相关用户!
			  				</td>
			  			</tr>
			  			</c:if>
			  			
			  			<c:forEach items="${users}" var="cur">
				  		<tr class="lzui-td utd" uid="${cur.id }">
					    	<td style=" text-align:center;"><input type="checkbox" class="ids uids" name="uids" value="${cur.id }" id="u${cur.id}"/></td>
					    	<td>${cur.name }</td>
					    	<td>${cur.code }</td>
					    </tr>
					    </c:forEach>
					    </table>
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t3"><span>3</span>第三步、确认无误，点击提交。</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
			  			<a href="javascript:submitRoles();" class="lzui-btn2" style="height:30px; line-height:30px; width:100px;font-weight:bold; font-size:15px;">提 交</a>
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

function addRole(){
	var roleName = $('#roleName').val();
	roleName = $.trim(roleName);
	$('#roleName').val(roleName);
	if(!roleName){
		alert('请输入角色名称');
		return;
	}
	document.form2.submit();
}


function submitRoles(){
	var rid = $('.rtd input:checked').val();
	if(!rid){
		alert("请选择一个角色。");
		return;
	}
	var ult = $('.uids:checked');
 	var l1 = ult.length;
 	if(l1==0){
    	alert("请选员工。");
		return;
  	}

 	document.form1.submit();
}


$(function(){
	$('#OA管理').addClass('ui-tabs-current');
	$('#角色管理').addClass('cur');

	$('.fancybox').fancybox();

	//ie兼容
	$('.lzui-td input').click(function(evt){
		evt.stopPropagation();
	});

	$('.rtd').click(function(evt){
		var item = $(this).find("input");
		item.prop('checked',true);
		ajaxEmps();
	});

	$('.utd').click(function(evt){
		var item = $(this).find("input");
		var ic =  item.prop('checked');
		if(ic){
			item.prop('checked',false);
		}else{
			item.prop('checked',true);
		}
	});

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	//全选
	$('#checkAllUser').click(function(evt){
		var item = $(this);
		var ic =  item.prop('checked');
		if(ic){
			$('.utd input:checkbox').prop('checked',true);
		}else{
			$('.utd input:checkbox').prop('checked',false);
		}
	});
	
});

function ajaxEmps(){
	var rid = $('.rtd input:checked').val();
	
	$.get('${ctx}/web/oa/role/ajaxEmpsByRole/'+rid+"?t="+Math.random(), function(data){
		$('.uids').prop('checked',false);
		var emps = eval(data);
		for(var i=0; i<emps.length; i++){
			$('#u'+emps[i]).prop('checked',true);
		}
	});
}
</script>