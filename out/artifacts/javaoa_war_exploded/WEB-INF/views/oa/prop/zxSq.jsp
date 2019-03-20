<%@ page language="java" import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['42']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
	<div id="固定资产注销" class="navTitle2" url=""><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;固定资产注销</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:123px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">固定资产注销申请</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<!--  
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/os/mySq">点击查看申领记录</a>
  	</div>
  	-->
	
	<form name="form1" action="${ctx }/web/oa/prop/zxSq" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${prop.id}" name="propId"/>
	
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }固定资产注销申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:80px;">申请人：</td>
			<td style="width:90px;">
				${loginUser.name }
			</td>	
			<td class="title" style="width:80px;">所属部门：</td>	
			<td style="width:90px;">
				${loginUserDept.name }
			</td>
			<td class="title" style="width:80px;"><span style="color:red;">*</span>注销原因：</td>	
			<td style="width:90px;">
				<select name="reason" id="reason">
					<option value="0">-请选择-</option>
					<option value="资产报废">资产报废</option>
					<option value="资产丢失">资产丢失</option>
				</select>
			</td>
			<td class="title" style="width:80px;">申请时间：</td>
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
		资产信息</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:120px;">
				资产编号
			</td>
			<td class="title" style="text-align:left; width:100px;">
				资产名称
			</td>
			<td class="title" style="text-align:left; width:120px;">
				规格型号
			</td>
			<td class="title" style="text-align:left; width:100px; ">
				单价（元）
			</td>	
		</tr>
		
		<tr id="addCdTr">
			<td style="">
				<input type="hidden" id="idSpan" />
				${prop.code }
			</td>
			<td style="">
				<span id="typeSpan">${prop.name }</span>
			</td>
			<td style="">
				<span id="priceSpan">${prop.spec}</span>
			</td>
			<td style=" width:60px; border-right:none; ">
				${prop.price }
			</td>	
		</tr>
		
		
	</tbody></table>
	  
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		注销原因说明</span>
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
	$('#资产管理').addClass('ui-tabs-current');	
	$('#固定资产注销').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	/*
	$('#crbtn').click(function(){

		clearInterval(itv);
		
		index = $.layer({
			shade : [0.7, '#000'],
		    fix: false,
		    title: '请选择您要申领的资产',
		    type: 2,
		    border: [3],
		    shadeClose: false,
		    iframe: {src : '${ctx}/web/oa/prop/chooseProp'},
		    area: ['850px', '500px'],
		    close: function(index){
		    	itv = setInterval(ajaxGetDb,len);
		    }
		});

	
	});*/
	
});

/*
function addAProp(id, name, type, price){
	//console.info( "Info, gogogo." );
	layer.close(index);
	$('#idSpan').val(id);
	$('#nameSpan').html(name);
	$('#typeSpan').html(type);
	$('#priceSpan').html(price);
	itv = setInterval(ajaxGetDb,len);
}

var count = 3;

function addcd(){
	var idSpan = $('#idSpan');
	var nameSpan = $('#nameSpan');
	var typeSpan = $('#typeSpan');
	var priceSpan = $('#priceSpan');

	var thId = idSpan.val();
	var name = nameSpan.html();
	var type = typeSpan.html();
	var price = priceSpan.html();

	if(!thId){
		alert('请选择固定资产');
		return;
	}

	count++;
	var html = '<tr id="cd'+count+'">'+
		'<td style="">'+name+
			'<input type="hidden" value="'+thId+'" name="ids"/>'+
		'</td>'+
		'<td style="">'+type+'</td>'+
		'<td style="">'+price+'</td>'+
		'<td style=" border-right:none; ">'+
			'<a href="javascript:delcd('+count+');">删除</a>'+
		'</td>'+
		'</tr>';

	$('#addCdTr').after(html);
	
	idSpan.val('');
	nameSpan.html('');
	typeSpan.html('');
	priceSpan.html('');
	
}

function delcd(tid){
	$('#cd'+tid).remove();
}*/

function submitForm(){
	var reason = $('#reason').val();
	
	//var iptPrices = $('#cdTable input[name="ids"]');
	//alert(iptPrices.length);
	if(0 == reason){
		alert('请选择注销原因');
		return;
	}	

	document.form1.submit();
}
</script>