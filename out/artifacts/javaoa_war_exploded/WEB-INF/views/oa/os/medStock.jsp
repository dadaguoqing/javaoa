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
		<a href="${ctx }/web/oa/os/addYp" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">新增应急药品</a>
	</div>
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="5">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				应急药品库存列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:140px;">药品名称</td>
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
					<a href="${ctx }/web/oa/os/goEditYp/${cur.id}">编辑</a> 
					<!--  
					|
					<a href="#">查看出入库明细</a>	
					-->
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
	$('#应急药品库存').addClass('cur');		

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	
	$('#uType').val(${uType});

	$('#showType').val(${showType});

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
	    title: '请选择员工',
	    type: 2,
	    border: [3],
	    shadeClose: false,
	    iframe: {src : '${ctx}/web/oa/user/chooseUserNew?single=single'},
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

function setUser(uid, uname){
	layer.close(index);
	$('#uid').val(uid);
	$('#uname').val(uname);
	$('#nameSpan').html(uname);
	itv = setInterval(ajaxGetDb,len);
}

function goDetail(id){
	$('#wpId').val(id);
	document.detailForm.submit();
}
</script>
</html>