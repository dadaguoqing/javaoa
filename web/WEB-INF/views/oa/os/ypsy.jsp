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
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">应急药品损益</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/os/myYpSyRecord">点击查看申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/os/ypsy" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }应急药品损益申请单</span>
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
		
		<tr>
			<td class="title" style="width:120px;">损益类型：</td>
			<td style="border-right:none;" colspan="5">
			<select name="syType" id="syType">
				<option value="0">-请选择-</option>
				<option value="-1">减少库存</option>
				<option value="1">增加库存</option>
			</select>
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		损益药品</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:120px;">
				药品名称 <input type="button" style="padding:3px 5px;" value="点击选择药品" id="crbtn"/>
			</td>
			<td class="title" style="text-align:left; width:120px;">
				规格型号
			</td>
			<td class="title" style="text-align:left; width:100px; ">
				数量单位
			</td>
			<td class="title" style="text-align:left; border-right:none; ">
				操作
			</td>		
		</tr>
		
		<tr id="addCdTr">
			<td style="">
				<input type="hidden" id="idSpan" />
				<span id="nameSpan" style="color:red;"></span>
			</td>
			<td style="color:red;">
				<span id="typeSpan">&nbsp;</span>
			</td>
			<td style="color:red;">
				<input style="width:20px;" id="numSpan"/><span id="unitSpan">&nbsp;</span>
			</td>
			<td style=" width:60px; border-right:none; ">
				<a href="javascript:addcd();">确定</a>
			</td>		
		</tr>
		
		
	</tbody></table>
	
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		损益原因</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" style="width:50%; height:80px; padding:5px 10px;"></textarea>
				<span style="color:red;">不超过150个字符（必填）</span>
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
	$('#应急药品损益申请').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	
	$('#crbtn').click(function(){

		clearInterval(itv);
		
		index = $.layer({
			shade : [0.7, '#000'],
		    fix: false,
		    title: '请选择您要申领的药品',
		    type: 2,
		    border: [3],
		    shadeClose: false,
		    iframe: {src : '${ctx}/web/oa/os/chooseMedForCg'},
		    area: ['850px', '500px'],
		    close: function(index){
		    	itv = setInterval(ajaxGetDb,len);
		    }
		});

	
	});
	
});

function addAYp(id, name, type, unit){
	//console.info( "Info, gogogo." );
	layer.close(index);
	$('#idSpan').val(id);
	$('#nameSpan').html(name);
	$('#typeSpan').html(type);
	$('#unitSpan').html(unit);
	itv = setInterval(ajaxGetDb,len);
}

var count = 3;

function addcd(){
	var idSpan = $('#idSpan');
	var nameSpan = $('#nameSpan');
	var typeSpan = $('#typeSpan');
	var unitSpan = $('#unitSpan');
	var numSpan = $('#numSpan');
	//var priceSpan = $('#priceSpan');

	var thId = idSpan.val();
	var num = numSpan.val();
	//var price = priceSpan.val();
	
	var name = nameSpan.html();
	var type = typeSpan.html();
	var unit = unitSpan.html();

	if(!thId){
		alert('请选择药品');
		return;
	}

	if(!num){
		alert('请填写数量');
		return;
	}

	var reg = new RegExp("^[1-9][0-9]*$");
	if(!reg.test(num)){
		alert('请正确填写数量（数量必须是正整数）');
		return;
	}
	
	count++;
	var html = '<tr id="cd'+count+'">'+
		'<td style="">'+name+
			'<input type="hidden" value="'+thId+'" name="ids"/>'+
			'<input type="hidden" value="'+name+'" name="names"/>'+
			'<input type="hidden" value="'+type+'" name="types"/>'+
			'<input type="hidden" value="'+unit+'" name="units"/>'+
			'<input type="hidden" value="'+num+'" name="nums"/>'+
		'</td>'+
		'<td style="">'+type+'</td>'+
		'<td style="">'+num+unit+'</td>'+
		'<td style=" border-right:none; ">'+
			'<a href="javascript:delcd('+count+');">删除</a>'+
		'</td>'+
		'</tr>';

	$('#addCdTr').after(html);
	
	idSpan.val('');
	numSpan.val('');
	nameSpan.html('');
	typeSpan.html('');
	unitSpan.html('');
	//priceSpan.val('');
	
}

function delcd(tid){
	$('#cd'+tid).remove();
}

function submitForm(){

	var syType = $('#syType').val();
	if(syType == 0){
		alert('请选择损益类型');
		return;
	}
	
	var cnt = $('#cnt').val();
	if(!cnt){
		alert('请填写损益原因');
		return;
	}

	
	var iptPrices = $('#cdTable input[name="ids"]');
	//alert(iptPrices.length);
	if(!iptPrices || iptPrices.length == 0){
		alert('请至少添加一个损益药品');
		return;
	}

	document.form1.submit();
}
</script>