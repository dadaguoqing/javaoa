<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;添加活动</div>
	<div class="navTitle2" url="${ctx }/web/oa/gift/evt/list"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">添加活动</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/gift/evt/add" method="post">
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">活动详细信息</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息<span style="color:red;font-weight:normal;"></span></span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">活动名称 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="name" id="nameIpt"/>
			</td>	
				
			<td class="title" style="width:90px;">管理员 <span style="color:red;">*</span>：</td>	
			<td style="width:120px;">
				<a href="javascript:chooseUser();">请选择</a>
				<span id="empName"></span>
				<input type="hidden" id="empId" name="managerId"/>
			</td>	
			
			<td class="title" style="width:90px;">开始时间：</td>
			<td  style=" width:90px;">
				<input name="beginTime" id="beginTime" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时'})" />
			</td>	
			
			<td class="title" style="width:90px;">结束时间：</td>
			<td  style=" width:90px;">
				<input name="endTime" id="endTime" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日 HH时'})" />
			</td>	
			
		</tr>
		
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		活动说明</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="memo" id="cnt" style="width:50%; height:80px; padding:5px 10px;"></textarea>
				<span style="color:red;">不超过150个字符（可不填）</span>
			</td>	
		</tr>
	</tbody></table>
	
	</div>
	<span style="color:red;">*</span>号标注的为必填项。
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">点击添加</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

var index;

function chooseUser(t){
	
	clearInterval(itv);
	
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
	
}

function setUser(uid, uname){
	layer.close(index);
	$('#empId').val(uid);
	$('#empName').html(uname);
	itv = setInterval(ajaxGetDb,len);
}

function submitForm(){
	
	var name = $('#nameIpt').val();
	var empId = $('#empId').val();
	
	var edTime = $('#endTime').val();

	var beginTime = $('#beginTime').val();


	name = $.trim(name);
	$('#nameIpt').val(name);
	if(!name){
		alert('请输入活动名称。');
		return;
	}

	if(!empId){
		alert('请选择管理员。');
		return;
	}

	if(!beginTime){
		alert('请选择开始时间。');
		return;
	}

	if(!edTime){
		alert('请选择结束时间。');
		return;
	}

	document.form1.submit();
}
</script>