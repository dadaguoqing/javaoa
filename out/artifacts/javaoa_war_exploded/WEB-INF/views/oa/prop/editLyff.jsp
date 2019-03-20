<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;申请详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/prop/ff"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">固定资产发放</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/prop/sff" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<input type="hidden" value="${sq.id}" name="lyId"/>
	<input type="hidden" value="1" name="ffType"/>
	<div style="border:1px solid #2191C0; margin-top:10px; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }固定资产申领单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:100px;">申领人：</td>	
			<td style="width:120px;">
				${sq.proposerName }
			</td>	
			<td class="title" style="width:100px;">所属部门：</td>	
			<td style="width:90px;">
				${AllDepts[propEmp.deptId].name }
			</td>
			<td class="title" style="width:100px;">申领时间：</td>
			<td style="border-right:none;">
				${sq.createTime }
			</td>	
			
		</tr>
		
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申领的固定资产</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:160px;">
				资产名称 
			</td>
			<td class="title" style="text-align:left; width:450px;">
				备注信息（如：规格型号、配置等）
			</td>
		</tr>
		<c:forEach items="${list}" var="cur">
		<tr>
			<td style="">
				${cur.name }
			</td>
			<td style="">
				${empty cur.bz ? '无' : cur.bz }
			</td>
		</tr>
		</c:forEach>
		
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申领事由</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" disabled style="width:50%; height:80px; padding:5px 10px;">${sq.content}</textarea>
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		审批记录</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td style=" width:16%; ">
				主管意见
				<c:if test="${ not empty sq.mgrId }">
					<span style="color:green;">（${AllUsers[sq.mgrId].name}）</span>
				</c:if>
			</td>
			<td style=" width:16%; color:green;">
			
				<c:choose>
				<c:when test="${ empty sq.mgrId }">
					<span style=" color:#666;" >本次审批不需要经过主管</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.mgrId && sq.status == 1}">
					<span style=" color:#green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.mgrCmt ? '暂未到达该流程' : sq.mgrCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总监意见
				<c:if test="${ not empty sq.directId }">
					<span style="color:green;">（${AllUsers[sq.directId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%;color:green;">
			
				<c:choose>
				<c:when test="${ empty sq.directId }">
					<span style=" color:#666;" >本次审核不需要经过总监</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.directId && sq.status == 2}">
					<span style=" color: green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.directCmt ? '暂未到达该流程' : sq.directCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
			<td style="width:16%;">
				总经理意见
				<c:if test="${ not empty sq.bossId }">
					<span style="color:green;">（${AllUsers[sq.bossId].name}）</span>
				</c:if>
			</td>
			<td style="width:16%; color:green; border-right:none; ">
			
				<c:choose>
				<c:when test="${ empty sq.bossId }">
					<span style=" color:#666;" >本次审核不需要经过总经理</span>
				</c:when>
				<c:when test="${ sq.currentId == sq.bossId && sq.status == 3}">
					<span style=" color:green;" >正在审批</span>
				</c:when>
				<c:otherwise>
					${ empty sq.bossCmt ? '暂未到达该流程' : sq.bossCmt  }
				</c:otherwise>
				</c:choose>
				
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px; color:red;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		请选择要发放的资产</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:120px;">
				资产名称 <input type="button" style="padding:3px 5px;" value="点击选择固定资产" id="crbtn"/>
			</td>
			<td class="title" style="text-align:left; width:120px;">
				规格型号
			</td>
			<td class="title" style="text-align:left; width:100px; ">
				备注
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
				<span id="priceSpan">&nbsp;</span>
			</td>
			<td style=" width:60px; border-right:none; ">
				<a href="javascript:addcd();">确定</a>
			</td>		
		</tr>
		
		
	</tbody></table>
	
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">发放资产</a>
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
		    title: '请选择您要发放的资产',
		    type: 2,
		    border: [3],
		    shadeClose: false,
		    iframe: {src : '${ctx}/web/oa/prop/chooseProp'},
		    area: ['850px', '500px'],
		    close: function(index){
		    	itv = setInterval(ajaxGetDb,len);
		    }
		});

	
	});
});

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
}

function beforeSubmit(){
	var tg = $('#tg').val();

	if(tg == '0'){
		alert('请选择审批意见');
		return false;
	}

	var yj = $('#yj').val();
	yj = $.trim(yj);
	if(!yj){
		alert('请输入补充意见');
		return false;
	}
		
	return true;
}

function submitForm(){

	var iptPrices = $('#cdTable input[name="ids"]');
	
	if(!iptPrices || iptPrices.length == 0){
		alert('请至少添加一个固定资产');
		return;
	}
	
	document.form1.submit();
}
</script>