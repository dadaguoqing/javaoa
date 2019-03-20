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
	
<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<c:if test="${not empty msg}">
	<div class="lzui-error">
		${msg }
	</div>
	</c:if>
	
	<div style="width:92px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加外网访问</div>
	<form name="form1" method="post" action="${ctx}/web/oa/user/addOutUser">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  					请按照下面的流程操作。
  				</div>
  			
  				<div class="">
  				
					<div class="lzui-headers">
						<div class="t1"><span>1</span>第一步、请选择一名员工</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="radio" disabled="disabled"/></th>
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
					    	<td style=" text-align:center;"><input type="radio" class="ids uids" name="empId" value="${cur.id }" id="u${cur.id}"/></td>
					    	<td>${cur.name }</td>
					    	<td>${cur.code }</td>
					    </tr>
					    </c:forEach>
					    </table>
					    </div>
						
						
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t2"><span>2</span>第二步、选择对应的操作（类型、时间）</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
						
			  			<div style="margin-bottom:10px;">
			  			<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">访问码类型<span style="color:red">*</span></span>
						<select style="margin-left:15px;" name="type" id="type">
							<option value="0">临时访问</option>
							<option value="1">永久访问</option>
						</select>
						<div id="timeDiv">
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">生效时间<span style="color:red">*</span></span>
							<span style="margin-left:15px;"></span><%@ include file="../public/beginTime.jsp" %>
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">失效时间<span style="color:red">*</span></span>
							<span style="margin-left:15px;"></span><%@ include file="../public/endTime.jsp" %>
						</div>	
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">生成方式<span style="color:red">*</span></span>
						<select style="margin-left:15px;" name="codeType" id="codeType">
							<option value="0">随机生成</option>
							<option value="1">手动填写</option>
						</select>
						<div id="accessCodeDiv" style="display:none;">
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">请填写访问码（8位）<span style="color:red">*</span></span>
							<span style="margin-left:15px;"></span><input name="accessCode" id="accessCode"/>
						</div>
						
						</div>
						
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
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
function submitRoles(){
	var ult = $('.uids:checked');
 	var l1 = ult.length;
 	if(l1==0){
    	alert("请选员工。");
		return;
  	}

  	var codeType = $('#codeType').val();
  	var type = $('#type').val();

  	if(type == 0){
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
  	}

  	var accessCode = $('#accessCode').val();
	accessCode = $.trim(accessCode);
	
  	if(codeType == 1){
  	  	if(!accessCode || accessCode.length != 8){
			alert('请输入8位访问码');
			return;
  	  	}
  	}
  	
  	$('#accessCode').val(accessCode);
  	
 	document.form1.submit();
}


$(function(){
	$('#OA管理').addClass('ui-tabs-current');
	$('#外网访问').addClass('cur');	

	//ie兼容
	$('.lzui-td input').click(function(evt){
		evt.stopPropagation();
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

	$('#type').change(function(evt){
		var v = $(this).val();
		if(v == '0'){
			$('#timeDiv').show();
		}else{
			$('#timeDiv').hide();
		}
	});
	
	$('#codeType').change(function(evt){
		var v = $(this).val();
		if(v == '1'){
			$('#accessCodeDiv').show();
		}else{
			$('#accessCodeDiv').hide();
		}
	});
});


</script>