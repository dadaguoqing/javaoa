<%@ page language="java" import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<<style>
<!--
.selectBak {background: #E74C3C; color:#eee; font-weight:bold;}
.selectBak:hover{background: #E74C3C; color:#eee; font-weight:normal;}
-->
</style>

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
		<div style="width:90px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">供应商管理</div>
		
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
		
		<form name="form1" action="${ctx}/web/oa/dc/addProvider" method="post" onsubmit="return beforeSubmit();">
		<input type="hidden" value="${submitCode}" name="submitCode"/>
		<div class="lzui-table-wapper" style="margin-top:20px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:160px;">供应商名称</th>
	  				<th style="padding-left:15px; width:120px;"> | 商家差评率</th>
	  				<th style=" width:140px;"> | 联系电话</th>
	  				<th style=""> | 备注</th>
	  				<th style=" width:140px;"> | 操作</th>
	  			</tr>
	  			
	  			<tr class="lzui-td ttd" id="t2">
			    	<td>
			    		<input name="name" id="name" style='width:100px;'/>
			    	</td>
			    	<td>0%</td>
			    	<td>
						<input name="phone" id="phone" style='width:120px;'/>
					</td>
			    	<td>
			    		<input name="bz" style='width:250px;'/>
			    	</td>
			    	<td>
						<a href="javascript:addProvider();">点击添加</a>
					</td>
			    </tr>
	  			
	  			<c:forEach items="${list}" var="cur">
	  			
		  		<tr class="lzui-td ttd" id="t1">
			    	<td>${cur.name }</td>
			    	<td>${cur.cpl }</td>
			    	<td>${cur.phone }</td>
			    	<td>
			    	${cur.bz }
			    	</td>
			    	<td>
						<a href="javascript:editProvider(${cur.id });">修改编辑</a> | 
						<a href="#">删除</a>
					</td>
			    </tr>
			    </c:forEach>
			   
			    </table>
			</div>
			</form>
			
			<form name="edForm" method="post" action="${ctx}/web/oa/dc/editProvider">
				<input type="hidden" id="pid" name="id" />
				<input type="hidden" id="pname" name="name" />
				<input type="hidden" id="pphone" name="phone" />
				<input type="hidden" id="pbz" name="bz" />
			
			</form>
	</div>
</div>
</div>
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

function submitEditP(id, name, phone, bz){
	$('#pid').val(id);
	$('#pname').val(name);
	$('#pphone').val(phone);
	$('#pbz').val(bz);
	document.edForm.submit();
}

function beforeSubmit(){
	var name = $('#name').val();

	if(!name){
		alert('请输入供应商名称');
		return false;
	}

	var phone = $('#phone').val();
	if(!phone){
		alert('请输入联系电话');
		return false;
	}
		
	return true;
}

function addProvider(){
	var flag = beforeSubmit();
	if(flag){
		document.form1.submit();
	}
}

var index;

function editProvider(pid){
	clearInterval(itv);
	index = $.layer({
		shade : [0.7, '#000'],
	    fix: false,
	    title: '编辑供应商信息',

	    type: 2,
	    border: [3],
	    shadeClose: true,
	    iframe: {src : '${ctx}/web/oa/dc/showProvider?id='+pid},
	    area: ['500px', '400px'],
	    close: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    }
	});
}
</script>
</body>
</html>