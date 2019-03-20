<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['222']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">
	
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" action="${ctx}/web/oa/prop/search" method="post" >
	<input type="hidden" value="${empId }" name="empId" id="uid"/>
	<input type="hidden" value="${uname }" name="uname" id="uname"/>
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">查询范围：</td>
			<td>
			<select name="uType" id="uType">
				<option value="0">所有员工</option>
				<option value="1">特定员工</option>
			</select>
			
			<span id="chooseUserA" style="${ uType == 1 ? '' : 'display:none;'}">
			<a href="javascript:chooseUser();">点击选择员工</a>
			<span id="nameSpan">${uname }</span>
			</span>
			</td>
			
			<td class="right" style="width:80px;">展示内容：</td>
			<td>
			<select name="showType" id="showType">
				<option value="0">所有资产</option>
				<option value="1">已领用</option>
				<option value="2">未领用</option>
			</select>
			
			<td class="right" style="width:80px;">放置地点：</td>
			<td>
			<select name="placeId" id="placeId">
				<option value="0">所有位置</option>
				<c:forEach items="${places}" var="p">
				<option value="${p.id }">${p.name }</option>
				</c:forEach>
			</select>
			
			<td class="right" style="width:80px;">资产分类：</td>
			<td>
			<select name="typeId" id="typeId">
				<option value="0">所有类别</option>
				<c:forEach items="${fTypes}" var="t">
				<option value="${t.id }">${t.name }</option>
				</c:forEach>
			</select>
			
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
	
	<form name="detailForm" action="${ctx}/web/oa/os/wpDetail" method="post">
		<input type="hidden" name="wpId" id="wpId"/>
		<input type="hidden" name="end" value="${end}" />
		<input type="hidden" name="begin" value="${begin}"/>
		<input type="hidden" name="showType" value="${showType }"/>
	</form>
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="7">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				固定资产查询
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:5px;">&nbsp;</td>
			<td class="l-td-header l-td-left" style="width:80px;">资产种类</td>
			<td class="l-td-header l-td-left" style="width:142px;">资产编码</td>
			<td class="l-td-header l-td-left" style="width:120px;">资产名称</td>
			<td class="l-td-header l-td-left" style="width:140px;">规格型号</td>
			<td class="l-td-header l-td-left" style="width:60px">负责人</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:140px">操作</td>
		</tr>
    	<c:forEach items="${list}" var="cur" varStatus="st">
    		<tr>
    			<td class="l-td l-td-left">${st.count }</td>
    			<td class="l-td l-td-left">${cur.type }</td>
    			<td class="l-td l-td-left">${cur.code }</td>
				<td class="l-td l-td-left" style="">
					${cur.name}
				</td>
				<td class="l-td l-td-left" style="">
					${cur.spec}
				</td>
				<td class="l-td l-td-left">${ empty cur.empName ? '-' : cur.empName}</td>
				<td class="l-td l-td-left l-td-last-right" style="">
					<a href="javascript:showDetail('${cur.name }',${cur.id})">查看详情</a>
				
					
				</td>
    		</tr>
    	</c:forEach>
    	<c:if test="${empty list}">
    		<tr>
    			<td class="l-td" colspan="8" style="color:red; text-align:center;">对不起，没有相关数据</td>
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
	$('#OA查询').addClass('ui-tabs-current');	
	$('#固定资产查询').addClass('cur');		
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	//$('#deptId').val(${deptId});
	$('#typeId').val(${typeId});
	$('#placeId').val(${placeId});
	
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

function showDetail(name,id){
	clearInterval(itv);
	$.layer({
		shade : [0.7, '#000'],
	    fix: false,
	    title: name+'资产详情',
	    type: 2,
	    border: [3],
	    shadeClose: true,
	    iframe: {src : '${ctx}/web/oa/prop/showDetail?id='+id},
	    area: ['700px', '400px'],
	    close: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    }
	});
}
</script>
</html>