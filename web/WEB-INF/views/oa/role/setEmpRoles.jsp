<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div id="角色管理" class="navTitle2 cur" url="${ctx }/web/oa/role/emp?empId=${emp.id}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;角色管理</div>
	<div class="navTitle2" url="${ctx }/web/oa/user/list"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
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
	<form name="form1" method="post" action="${ctx}/web/oa/role/setEmp">
	<input type="hidden" name="empId" value="${emp.id }" />
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  					请按照下面的流程操作。
  				</div>
  			
  				<div class="">
  				
					<div class="lzui-headers">
						<div class="t1"><span>1</span>第一步、用户信息</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="checkbox" id="checkAll" disabled="disabled"/></th>
			  				<th style=" width:80px;"> | 姓名 </th>
			  				<th style="	width:120px;"> | 登陆账号</th>
			  			</tr>
			  			
			  			<tr class="lzui-td rtd">
			  				<td style="text-align:center; padding-left:15px;"><input class="rids" checked="checked" type="radio" disabled="disabled"/></td>
			  				<td>${emp.name }</td>
			  				<td>${emp.code }</td>
			  			</tr>
			  			
			  			
					    </table>
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t2"><span>2</span>第二步、选择员工对应的角色</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="checkbox" id="checkAllUser" /></th>
			  				<th style=""> | 角色名称</th>
			  			</tr>
			  			
			  			<c:if test="${empty roles}">
			  			<tr>
			  				<td style=" text-align:center; color:#27AE60;" colspan="3">
			  					系统中目前还没有相关角色!
			  				</td>
			  			</tr>
			  			</c:if>
			  			
			  			<c:forEach items="${roles}" var="cur">
				  		<tr class="lzui-td utd" uid="${cur.id }">
					    	<td style=" text-align:center;"><input type="checkbox" class="ids uids" name="roleIds" value="${cur.id }" id="u${cur.id}"/></td>
					    	<td>${cur.name }</td>
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

function submitRoles(){
	var ult = $('.uids:checked');
 	var l1 = ult.length;
 	if(l1==0){
    	alert("请选角色。");
		return;
  	}
  	
 	document.form1.submit();
}

var rids = null;
<c:if test="${not empty rids}">
rids = ${rids};
</c:if>

$(function(){
	$('#员工管理').addClass('ui-tabs-current');
	
	$('.lzui-td input').click(function(evt){
		evt.stopPropagation();
	});

	$('#checkAllUser').click(function(evt){
		
		var ic =  $(this).prop('checked');
		if(ic){
			$('.utd input').prop('checked',true);
		}else{
			$('.utd input').prop('checked',false);
		}
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

	
	if(rids){
		for(var i=0; i<rids.length; i++){
			$('#u'+rids[i]).prop('checked',true);
		}
	}
});
</script>