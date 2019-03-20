<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<!-- this is where zeroth_cat starts his code -->
<style>

#first_button{
	width: 70px;
	padding:3px 10px;
}

</style>
<!-- this is where zeroth cat finishes his style code -->

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
	<c:if test="${not empty msg}">
	<div class="lzui-error">
		${msg }
	</div>
	</c:if>
	
	<div style="width:135px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">订餐账户充值/扣款</div>
	<div class="" style="margin:10px 0 10px 0;">
		<button id="first_button" onclick="test_func()">导表</button>
	</div>
	<form name="form1" id="sForm" method="post" action="${ctx}/web/oa/dc/updateBalance">
	
	<div class="" style="margin:10px 0 10px 0;">
		<a href="${ctx }/web/oa/dc/cbRecord" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">查看充值/扣款记录</a>
	</div>
	
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  					请按照下面的流程操作。
  				</div>
  			
  				<div class="">
  				
					<div class="lzui-headers">
						<div class="t1"><span>1</span>第一步、请选择一个账户</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px; height:365px; overflow:scroll;">
			  			<table class="lzui-table" style="width:100%;">
			  			<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;"><input type="radio" disabled="disabled"/></th>
			  				<th style=" width:80px;"> | 姓名 </th>
			  				<th style="	width:120px;"> | 账户余额</th>
			  			</tr>
			  			
			  			<c:if test="${empty users}">
			  			<tr>
			  				<td style=" text-align:center; color:#27AE60;" colspan="3">
			  					系统中目前还没有相关账户!
			  				</td>
			  			</tr>
			  			</c:if>
			  			
			  			<c:forEach items="${users}" var="cur">
				  		<tr class="lzui-td utd" uid="${cur.empId }">
					    	<td style=" text-align:center;"><input type="radio" class="ids uids" name="empId" value="${cur.empId }" id="u${cur.empId}"/></td>
					    	<td>${cur.empName }</td>
					    	<td>${cur.balance }</td>
					    </tr>
					    </c:forEach>
					    </table>
					    
					    <!-- this is where zeroth cat's code begins -->
					 	<c:set var ="money_left_total" value="${0 }"/>
			  			<c:forEach items="${users}" var="cur">
				  			<c:set var="money_left_total" value="${money_left_total + cur.balance }"/>
					    </c:forEach>
					    总金额
					    ${money_left_total}
					    <!-- this is were zeroth cat's code ends -->
					    
					    </div>
						
						
						
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t2"><span>2</span>第二步、选择对应的操作（充值、扣款）</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
						
			  			<div style="margin-bottom:10px;">
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">请选择充值或扣款<span style="color:red">*</span></span>
						<select style="margin-left:15px;" name="type" id="type">
							<option value="0">请选择</option>
							<option value="充值">充值</option>
							<option value="扣款">扣款</option>
						</select>
						
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">请填写金额（单位：元）<span style="color:red">*</span></span>
						<input name="money" id="money" style="width:80px; margin-left:15px;" value="100"/>
						
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">请填写扣款原因<span style="color:red">（充值可不填）</span></span>
						<textarea name="bz" id="bz" style="width:100%; height:60px; padding:7px;"></textarea>
						</div>
						
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t3"><span>3</span>第三步、确认无误，点击提交。</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
			  			<a href="javascript:submitRoles();" class="lzui-btn2" style="height:30px; line-height:30px; width:100px;font-weight:bold; font-size:15px;">提 交</a>
			  			<!-- zeroth -->
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
//this is zeroth cat's start

function test_func(){
	window.location.replace("${ctx }/web/oa/dc/get_balance");
}

//this is zeroth cat's end

//zeroth cat more code
function removeAutoDingcan(){
	var ult = $('.uids:checked');
 	var l1 = ult.length;
 	if(l1==0){
    	alert("请选账户。");
		return;
  	}
 	
 	var formElem = document.getElementById("sForm");
	formElem.action = "${ctx }/web/oa/dc/managerRemoveAuto";
 	
 	document.form1.submit();
 	
}

function submitRoles(){
	
	var ult = $('.uids:checked');
 	var l1 = ult.length;
 	if(l1==0){
    	alert("请选账户。");
		return;
  	}

  	var money = $('#money').val();
  	money = $.trim(money);
  	
  	var bz = $('#bz').val();
  	bz = $.trim(bz);
  	var type = $('#type').val();

  	if(type == 0){
  	  	alert("请选择充值或扣款。");
  	  	return;
  	}

  	if(bz == '' && type=='扣款'){
  	  	alert("请填写扣款原因。");
  	  	return;
  	}

  	if(money == ''){
  	  	alert("请填写金额");
  	  	return;
  	}
  	
  	var formElem = document.getElementById("sForm");
	formElem.action = "${ctx }/web/oa/dc/updateBalance";
  	
  	$('#money').val(money);
  	
 	document.form1.submit();
}


$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#订餐管理').addClass('cur');	

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

	
});


</script>