<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<!-- 代理权限申请页面 -->
<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['4']}" var="cur">
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
		申请已经提交，等待上级审核。
		</c:if>
	</div>
	</c:if>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">
		${msg }
	</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/asp/dlRecord">查看申请记录</a>
  	</div>
	
	<div style="width:92px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">权限代理申请</div>
	<form name="form1" method="post" action="${ctx}/web/oa/asp/dlsq">
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  					请按照下面的流程操作。
  				</div>
  			
  				<div class="">
					<div class="lzui-headers">
						<div class="t1"><span>1</span>第一步、选择代理员工</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="radio" id="checkAll" disabled="disabled"/></th>
			  				<th style=" "> | 员工姓名</th>
			  			</tr>
			  			<c:forEach items="${usres}" var="cur">
			  			<tr class="lzui-td rtd">
			  				<td style="text-align:center; padding-left:15px;"><input class="rids" name="uid" type="radio" value="${cur.id }"/></td>
			  				<td>${cur.name }</td>
			  			</tr>
			  			</c:forEach>
					    </table>
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t2"><span>2</span>第二步、选择代理员工可使用的权限</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="checkbox" id="checkAllMenu" /></th>
			  				<th style=" width:80px;"> | 菜单名称 </th>
			  			</tr>
			  			
			  			
			  			<c:forEach items="${menus}" var="cur">
				  		<tr class="lzui-td utd" uid="${cur.id }">
					    	<td style=" text-align:center;"><input type="checkbox" class="ids uids pids" name="mids" value="${cur.id }" id="u${cur.id}"/></td>
					    	<td>${cur.name }</td>
					    </tr>
					    	<c:forEach items="${map[cur.id]}" var="c">
					    		<tr class="lzui-td utd" uid="${cur.id }">
							    	<td style=" text-align:center; padding-left:40px;"><input pid="u${cur.id}" type="checkbox" class="sids ids uids u${cur.id}" name="mids" value="${c.id }" id="u${c.id}"/></td>
							    	<td style="padding-left:30px;">${c.name }</td>
							    </tr>
					    	</c:forEach>
					    </c:forEach>
					    </table>
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t3"><span>3</span>第三步、填写原因以及期限，确认提交。</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
						
						<div style="margin-bottom:10px;">
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">生效时间<span style="color:red">*</span></span>
							<span style="margin-left:15px;"></span><%@ include file="../public/beginTime.jsp" %>
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">失效时间<span style="color:red">*</span></span>
							<span style="margin-left:15px;"></span><%@ include file="../public/endTime.jsp" %>
							
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">请在这里输入原因<span style="color:red">*</span>：<span style="font-weight:normal; color:red;">（不超过100个字）</span></span>
						<textarea name="reason" id="reason" style="width:100%; height:60px; padding:7px;"></textarea>
						</div>
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
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
function submitForm(){
	document.form1.submit();
}

function submitRoles(){
	var rid = $('.rtd input:checked').val();
	if(!rid){
		alert("请选择一个用户。");
		return;
	}
	var ult = $('.uids:checked');
 	var l1 = ult.length;
 	if(l1==0){
    	alert("请选代理权限。");
		return;
  	}

 	var bt = $('#bt').val();
	var et = $('#et').val();
		
		if(!bt){
			alert('请选择生效时间');
			return;
		}
		if(!et){
			alert('请选择失效时间');
			return;
		}

  	var rs = $('#reason').val();
  	rs = $.trim(rs);
  	$('#reason').val(rs);

  	$('#u${shouye}').prop('checked',true);
  	$('#u${tuichu}').prop('checked',true);

 	document.form1.submit();
}

$(function(){

	$('#u${shouye}').prop('checked',true);
  	$('#u${tuichu}').prop('checked',true);
  	
	$('#OA申请').addClass('ui-tabs-current');
	$('#权限代理').addClass('cur');	
		
	$('.lzui-td input').click(function(evt){
		evt.stopPropagation();
	});

	$('.rtd').click(function(evt){
		var item = $(this).find("input");
		item.prop('checked',true);
	});

	$('.utd').click(function(evt){
		var item = $(this).find("input");
		var ic =  item.prop('checked');
		var pid = item.attr('pid');
		if(ic){
			item.prop('checked',false);
			$('.'+item.attr('id')).prop('checked',false);
			
		}else{
			item.prop('checked',true);
			$('.'+item.attr('id')).prop('checked',true);
			if(pid){
				$('#'+pid).prop('checked',true);
			}
		}
	});
	
	$('.pids').click(function(){
		var item = $(this);
		var ic =  item.prop('checked');
		if(ic){
			$('.'+item.attr('id')).prop('checked',true);
		}else{
			$('.'+item.attr('id')).prop('checked',false);
		}
	});

	$('.sids').change(function(){
		var item = $(this);
		var ic =  item.prop('checked');
		var pid =  item.attr('pid');
		if(ic){
			$('#'+pid).prop('checked',true);
		}
	});

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#checkAllMenu').click(function(evt){
		var item = $(this);
		var ic =  item.prop('checked');
		if(ic){
			$('.utd input:checkbox').prop('checked',true);
		}else{
			$('.utd input:checkbox').prop('checked',false);
		}
	});

	
});


</script>