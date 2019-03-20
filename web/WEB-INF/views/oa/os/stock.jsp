<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

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

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">
	
	<div class="" style="margin:10px 0 10px 0;">
		<a href="${ctx }/web/oa/os/addWp" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">新增办公用品</a>
		<a href="${ctx }/web/oa/os/modifyRecode" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">办公用品变更申请记录</a>
		<a href="${ctx }/web/oa/os/exportStockRecord" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">导出库存记录</a>
	</div>
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" action="${ctx}/web/oa/os/stock" method="post" >
	<input type="hidden" value="${uid }" name="wpId" id="uid"/>
	<table id="baseInfoTable">
		<tr>
						
			<td class="right" style="width:80px;">所属分类：</td>
			<td>
			<select name="type" id="type">
			<option value="0">所有分类</option>
			<c:forEach items="${classifys}" var="cur">
				<option value="${cur }">${cur }</option>
			</c:forEach>
				
			</select>
			
			<td class="right" style="width:80px;">选择物品：</td>
			<td>
			<select name="uType" id="uType">
				<option value="0">所有物品</option>
				<option value="1">特定物品</option>
			</select>
			
			<span id="chooseUserA" style="${ uType == 1 ? '' : 'display:none;'}">
			<a href="javascript:chooseUser();">点击选择物品</a>
			<span id="nameSpan">${uname }</span>
			</span>
			</td>
			
			<td>
				<div style="padding:0px 10px;">
					<div class="buttonDiv saveBtn" id="sBtn">
						<span>搜索</span>
						<img src="${ctx}/resources/images/server_add.png" style="width:16px;height:16px;"/>
					</div>
			</div>
			</td>	
		</tr>
	</table>
	</form>
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				办公用品库存列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:140px;">物品名称</td>
			<td class="l-td-header l-td-left" style="width:140px;">规格型号</td>
			<td class="l-td-header l-td-left" style="width:60px">库存数</td>
			<td class="l-td-header l-td-left" style="width:60px">单位</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:140px">操作</td>
		</tr>
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">${cur.name }</td>
    			<td class="l-td l-td-left">${cur.type }</td>
				<td class="l-td l-td-left" style="">
					${cur.stock}
				</td>
				<td class="l-td l-td-left" style="">
					${cur.unit}
				</td>
				<td class="l-td l-td-left l-td-last-right" style="">
					
					<a href="#">查看出入库明细</a>
				<a href="${ctx }/web/oa/os/modifyArticle?id=${cur.id}&type=1" >删除</a>		
				<a href="${ctx }/web/oa/os/modifyArticle?id=${cur.id}&type=2" >修改</a>		
				</td>
    		</tr>
    	</c:forEach>
    	<c:if test="${empty list}">
    		<tr>
    			<td class="l-td" colspan="5" style="color:red; text-align:center;">对不起，没有相关数据</td>
    		</tr>
    	</c:if>	
	</tbody></table>

	</div>
	
	</div>
</div>

</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
var index;

$(function(){
	$('#OA管理').addClass('ui-tabs-current');	
	$('#办公用品库存').addClass('cur');		

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	
	$('#uType').val(${uType});

	$('#type').val('${type}');

	$('#uType').change(function(){
		var v = $(this).val();

		if(v == '1'){
			$('#chooseUserA').show();
		}else{
			$('#chooseUserA').hide();
		}
		
		
	});

	$('#sBtn').click(function(){
		document.sForm.submit();
	});
});

function chooseUser(){

	clearInterval(itv);
	//setInterval(funTitle,1000);
	index = $.layer({
		shade : [0.7, '#000'],
	    fix: false,
	    title: '请选择物品',
	    type: 2,
	    border: [3],
	    shadeClose: false,
	    iframe: {src : '${ctx}/web/oa/os/chooseWp?companyOnly=1'},
	    area: ['650px', '500px'],
	    close: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    }
	});
	
	/*
	var r = window.showModalDialog('${ctx}/web/oa/user/chooseUser?single=single','','status:0;help:0;dialogWidth:650px;dialogHeight:500px;toolbar=yes');
	r = $.trim(r);
	if(r){
		var ss = r.split(':');
		$('#uid').val(ss[0]);
		$('#uname').val(ss[1]);
		$('#nameSpan').html(ss[1]);
	}*/
}

function addAWp(uid, uname, type, unit){
	layer.close(index);
	$('#uid').val(uid);
	$('#uname').val(uname);
	$('#nameSpan').html(uname);
	itv = setInterval(ajaxGetDb,len);
}


</script>
</html>