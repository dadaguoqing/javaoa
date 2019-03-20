<%@ page language="java" import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
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
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">资产采购申请</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/prop/myCgRecords">点击查看申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/prop/cg" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }固定资产采购申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:120px;">申请人：</td>	
			<td style="width:90px;">
				${loginUser.name }
			</td>	
			<td class="title" style="width:120px;">所属部门：</td>	
			<td style="width:90px;">
				${loginUserDept.name }
			</td>
			<td class="title" style="width:120px;">申请时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
				String tdy = now.substring(0,11);
			%>
			<td style="border-right:none;">
				${now }
			</td>	
			
		</tr>
		
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		采购资产<span style="color:red; font-weight:normal;">（请填写资产名称以及备注信息，然后点击确定）</span></span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:160px;">
				资产名称 
			</td>
			<td class="title" style="text-align:left; width:60px;">
				类别
			</td>
			<td class="title" style="text-align:left; width:60px;">
				型号规格
			</td>
			<td class="title" style="text-align:left; width:60px;">
				放置地点
			</td>
			<td class="title" style="text-align:left; width:60px;">
				单位
			</td>
			<td class="title" style="text-align:left; width:60px;">
				单价
			</td>
			<td class="title" style="text-align:left; width:60px;">
				数量
			</td>
			<td class="title" style="text-align:left; width:100px;">
				总价(元)
			</td>
			<td class="title" style="text-align:left;">
				备注信息
			</td>
			<td class="title" style="width:100px; text-align:left; border-right:none; ">
				操作
			</td>		
		</tr>
		
		<tr id="addCdTr">
			<td style="">
				<input style="width:100px;" id="nameSpan"/>
			</td>
			<td style="width:90px;">
				<select name="typeSpan" id="typeSpan">
					<option value="">-请选择-</option>
					<c:forEach items="${types}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
				</select>
			</td>
			<td style="">
				<input style="width:100px;" id="brandSpan"/>
			</td>
			<td style="width:90px;">
				<select name="placeSpan" id="placeSpan">
					<option value="">-请选择-</option>
					<c:forEach items="${places}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
				</select>
			</td>
			<td style="">
				<select style="width: 60px" id="unitSpan">
				<option value="台">台</option>
				<option value="个">个</option>
				<option value="块">块</option>
				<option value="辆">辆</option>
				</select>
			</td>
			<td style="">
				<input style="width:60px;" id="priceSpan" onblur="check(this);"/>
			</td>
			<td style="">
				<input style="width:60px;" id="numSpan" onblur="check(this);" value="1"/>
			</td>
			<td style="">
				<input style="width:100px;" id="moneySpan" readonly="readonly" value="0.0"/>
			</td>
			<td style="color:red;">
				<input style="width:90px;" id="bzSpan"/>
			</td>
			<td style=" width:60px; border-right:none; ">
				<a href="javascript:addcd();">确定</a>
			</td>		
		</tr>
		
		
	</tbody></table>
	  
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		采购事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" style="width:50%; height:80px; padding:5px 10px;"></textarea>
				<span style="color:red;">不超过150个字符（可不填）</span>
			</td>	
		</tr>
	</tbody></table>
	
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script>
var index;

$(function(){
	$('#OA申请').addClass('ui-tabs-current');	
	$('#固定资产采购申请').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	
});

var count = 3;

function addcd(){
	var nameSpan = $('#nameSpan');
	var bzSpan = $('#bzSpan');
	var unitSpan = $('#unitSpan');
	var priceSpan = $('#priceSpan');
	var numSpan = $('#numSpan');
	var moneySpan = $('#moneySpan');
	var brandSpan = $('#brandSpan');
	var typeSpan = $('#typeSpan');
	var placeSpan = $('#placeSpan');
	
	var name = $.trim(nameSpan.val());
	var bz = $.trim(bzSpan.val());
	var unit = $.trim(unitSpan.val());
	var price = $.trim(priceSpan.val());
	var num = $.trim(numSpan.val());
	var money = $.trim(moneySpan.val());
	var brand = $.trim(brandSpan.val());
	var type = $.trim(typeSpan.val());
	var place = $.trim(placeSpan.val());

	if(!name){
		alert('请输入资产名称');
		return;
	}
	if(!type){
		alert('请选择类别');
		return;
	}
	if(!brand){
		alert('请填写规格型号');
		return;
	}
		if(!place){
			alert('请填写放置地点');
			return;
		}

	if(!bz){
		alert('请填写备注信息，如果没有请填写“无”');
		return;
	}
	if(!unit){
		alert('请输入单位');
		return;
	}
	if(!price){
		alert('请输入单价');
		return;
	}
	if(!num){
		alert('请输入数量');
		return;
	}
	if(!money){
		alert('请输入预算金额');
		return;
	}

	count++;
	var html = '<tr id="cd'+count+'">'+
		'<td style="">'+name+
			'<input type="hidden" value="'+name+'" name="names"/>'+
		'</td>'+
		'<td style="">'+type+
			'<input type="hidden" value="'+type+'" name="types"/>'+
		'</td>'+
		'</td>'+
		'<td style="">'+brand+
			'<input type="hidden" value="'+brand+'" name="brands"/>'+
		'</td>'+
		'<td style="">'+place+
			'<input type="hidden" value="'+place+'" name="places"/>'+
		'</td>'+
		'<td style="">'+unit+
		'<input type="hidden" value="'+unit+'" name="units"/>'+
		'</td>'+
		'<td style="">'+price+
		'<input type="hidden" value="'+price+'" name="prices"/>'+
		'</td>'+
		'<td style="">'+num+
		'<input type="hidden" value="'+num+'" name="nums"/>'+
		'</td>'+
		'<td style="">'+money+
		'<input type="hidden" value="'+money+'" name="moneys"/>'+
		'</td>'+
		'<td style="">'+bz+
		'<input type="hidden" value="'+bz+'" name="bzs"/>'+
		'</td>'+
		'<td style=" border-right:none; ">'+
			'<a href="javascript:delcd('+count+');">删除</a>'+
		'</td>'+
		'</tr>';

	$('#addCdTr').after(html);
	
	nameSpan.val('');
	bzSpan.val('');
	
}

function delcd(tid){
	$('#cd'+tid).remove();
}

function submitForm(){
	
	var iptPrices = $('#cdTable input[name="names"]');
	//alert(iptPrices.length);
	if(!iptPrices || iptPrices.length == 0){
		alert('请至少添加一个固定资产');
		return;
	}

	document.form1.submit();
}
function check(al){
	var zjje = 0.0;
	$(al).each(function(i){
		var jr = $(this).val();
		if(jr){
			var exp = /^([1-9][\d]{0,7}|0)(\.[\d]{1,2})?$/;
		    if(!exp.test(jr)){
		    	alert('请输入数字');
				return;
		    }

			var price = $("#priceSpan").val();
			var num = $("#numSpan").val();
			zjje = parseFloat(price*num);
		}
	});
	$('#moneySpan').val(zjje);
}
</script>