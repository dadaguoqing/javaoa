<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



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
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加订餐</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/dc/addDc" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">添加员工订餐</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:120px;">发布者：</td>	
			<td style="width:90px;">
				${loginUser.name }
			</td>	
			<td class="title" style="width:120px;">发布时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
				String tdy = now.substring(0,11);
			%>
			<td style="width:200px;">
				${now }
			</td>	
			<td class="title" style="width:120px;">预计结束时间：</td>
			<td style="border-right:none;">
				<%=tdy %> <%@ include file="../public/dingcanEndTime.jsp" %>
			</td>	
		</tr>
		<tr>
			<td class="title" style="width:120px;">请选择菜单：</td>	
				
			<td style="border-right:none; text-align:left; overflow:hidden;" colspan="5">
				<input type="button" onclick="addMenu();" value="点击添加菜单"/>
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		今日菜单</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody id="addbody">
		<tr>
			<td class="title" style="text-align:left;">
				供应商
			</td>
			<td class="title" style="text-align:left;">
				描述
			</td>
			<td class="title" style="text-align:left; width:100px;">
				价格
			</td>
			<td class="title" style="text-align:left; width:120px; border-right:none; ">
				操作
			</td>		
		</tr>
		
		
		<tr id="cd1">
			<td style="">
				8元套餐（一荤两素）<input type="hidden" value="8元套餐（一荤两素）" name="description"/>
			</td>
			<td style="width:100px;">
				8元<input type="hidden" value="8" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(1);">删除</a>
			</td>		
		</tr>
		<tr id="cd2">
			<td style="">
				10元套餐（两荤两素）<input type="hidden" value="10元套餐（两荤两素）" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(2);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd3">
			<td style="">
				香菇青菜煲仔<input type="hidden" value="香菇青菜煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				8元<input type="hidden" value="8" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(3);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd4">
			<td style="">
				美味滑鸡煲仔<input type="hidden" value="美味滑鸡煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(4);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd5">
			<td style="">
				香辣滑鸡煲仔<input type="hidden" value="香辣滑鸡煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(5);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd6">
			<td style="">
				辣味香肠煲仔<input type="hidden" value="辣味香肠煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(6);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd7">
			<td style="">
				美味排骨煲仔<input type="hidden" value="美味排骨煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(7);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd8">
			<td style="">
				香辣排骨煲仔<input type="hidden" value="香辣排骨煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(8);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd9">
			<td style="">
				滑水鱼片煲仔<input type="hidden" value="滑水鱼片煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(9);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd10">
			<td style="">
				辣爽滑鱼煲仔<input type="hidden" value="辣爽滑鱼煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(10);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd11">
			<td style="">
				腊肉煲仔<input type="hidden" value="腊肉煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				10元<input type="hidden" value="10" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(11);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd12">
			<td style="">
				香菇滑鸡煲仔<input type="hidden" value="香菇滑鸡煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				12元<input type="hidden" value="12" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(12);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd13">
			<td style="">
				美味牛肉煲仔<input type="hidden" value="美味牛肉煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				12元<input type="hidden" value="12" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(13);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd14">
			<td style="">
				香辣牛肉煲仔<input type="hidden" value="香辣牛肉煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				12元<input type="hidden" value="12" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(14);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd15">
			<td style="">
				香菇牛肉煲仔<input type="hidden" value="香菇牛肉煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				13元<input type="hidden" value="13" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(15);">删除</a>
			</td>		
		</tr>
		
		<tr id="cd16">
			<td style="">
				豆豉牛肉煲仔<input type="hidden" value="豆豉牛肉煲仔" name="description"/>
			</td>
			<td style="width:100px;">
				13元<input type="hidden" value="13" name="price"/>
			</td>
			<td style="width:120px; border-right:none; ">
				<a href="javascript:delcd(16);">删除</a>
			</td>		
		</tr>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">开始订餐</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#订餐管理').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	
});

var count = 100;

function addcd(){
	var tDesc = $('#decs');
	var tPris = $('#pris');
	var decs = tDesc.val();
	var pris = tPris.val();

	decs = $.trim(decs);

	if(!decs){
		alert('请填写该菜单的描述');
		return;
	}

	if(!pris){
		alert('请填写价格');
		return;
	}

	var reg = new RegExp("^[1-9][0-9]*$");
	if(!reg.test(pris)){
		alert('请正确填写价格（价格必须是正整数）');
		return;
	}
	
	count++;
	var html = '<tr id="cd'+count+'">'+
		'<td style="">'+decs+
			'<input type="hidden" value="'+decs+'" name="description"/>'+
		'</td>'+
		'<td style="width:100px;">'+pris+
			'元<input type="hidden" value="'+pris+'" name="price"/>'+
		'</td>'+
		'<td style="width:120px; border-right:none; ">'+
			'<a href="javascript:delcd('+count+');">删除</a>'+
		'</td>'+
		'</tr>';

	$('#addCdTr').after(html);
	tDesc.val('');
	tPris.val('');
}

function addcd2(id, pn, d, p){
	
	count++;
	var html = '<tr id="cd'+count+'">'+
		'<td style="">'+pn+
			'<input type="hidden" value="'+pn+'" name="providerName"/>'+
		'</td>'+
		'<td style="">'+d+
			'<input type="hidden" value="'+d+'" name="description"/>'+
		'</td>'+
		'<td style="width:100px;">'+p+
			'元<input type="hidden" value="'+p+'" name="price"/>'+
		'</td>'+
		'<td style="width:120px; border-right:none; ">'+
			'<a href="javascript:delcd('+count+');">删除</a>'+
		'</td>'+
		'</tr>';

	$('#addbody').append(html);
}

function delcd(tid){
	$('#cd'+tid).remove();
}

function submitForm(){
	var provider = $('#provider').val();
	if(provider == '0'){
		alert('请选择供应商');
		return;
	}

	var iptPrices = $('#cdTable input[name="price"]');
	//alert(iptPrices.length);
	if(!iptPrices || iptPrices.length == 0){
		alert('请至少添加一个菜单');
		return;
	}

	document.form1.submit();
}

var index;

function addMenu(){
	clearInterval(itv);
	$.layer({
		shade : [0.7, '#000'],
	    fix: false,
	    title: '选择菜单',

	    type: 2,
	    border: [3],
	    shadeClose: true,
	    iframe: {src : '${ctx}/web/oa/dc/ajaxMenus'},
	    area: ['500px', '600px'],
	    close: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    }
	});
}
</script>