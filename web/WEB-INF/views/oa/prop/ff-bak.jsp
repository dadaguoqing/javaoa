<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['42']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${param.msg == 1}">
	<div class="lzui-error">操作成功，请继续进行其他操作</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/prop/sff" method="post">
	<input type="hidden" name="ffType" id="ffType" />
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="8">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				待发放的固定资产
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:30px;">选择</td>
			<td class="l-td-header l-td-left" style="width:120px;">申领人</td>
			<td class="l-td-header l-td-left" style="width:80px;">资产编号</td>
			<td class="l-td-header l-td-left" style="width:80px">资产分类</td>
			<td class="l-td-header l-td-left" style="width:100px;">资产名称</td>
			<td class="l-td-header l-td-left" style="width:100px;">规格型号</td>
			<td class="l-td-header l-td-left" style="width:100px;">放置地点</td>
			<td class="l-td-header l-td-last-right" style="width:60px;">查看详情</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">
    				<input type="checkbox" name="ids" class="cbs" value="${cur.id},${cur.lyId},${cur.empId}"/>
    			</td>
    			<td class="l-td l-td-left">
    			${cur.empName }
    			</td>
    			<td class="l-td l-td-left">${cur.code }</td>
    			<td class="l-td l-td-left">${cur.type }</td>
    			<td class="l-td l-td-left">${cur.name }</td>
    			<td class="l-td l-td-left">${cur.spec }</td>
    			<td class="l-td l-td-left">
	    			<select>
		    			<c:forEach items="${places }" var="p">
		    			<c:if test="${cur.placeId == p.id}">
		    				<option value="${p.id }" selected>
		    			</c:if>
		    			<c:if test="${cur.placeId != p.id}">
		    				<option value="${p.id }">
		    			</c:if>
		    				${p.name }
		    			</option>
		    			</c:forEach>
	    			</select>
    			</td>
    			<td class="l-td">
    				<a href="${ctx }/web/oa/os/editSq?id=${cur.id}">查看详情</a>
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td-last-right" colspan="8" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
	</tbody></table>

</div>
</form>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitFf();" class="lzui-btn2" 
			style="height:30px; line-height:30px; float:left; width:100px;font-weight:bold; font-size:15px;">
			确认发放
		</a>
			 	
		<a href="javascript:CancelFf();" class="lzui-btn" 
			style="height:30px; line-height:30px; margin-left:15px; float:left; width:100px;font-weight:bold; font-size:15px;">
			取消发放
		</a>
	</div>
	</div>
</div>
<script>
$(function(){
	$('#资产管理').addClass('ui-tabs-current');	
	$('#固定资产发放').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function CancelFf(){
	var cbs = $('.cbs:checked');
	
	if(cbs.length<1){
		alert('请至少选择一项数据');
		return;
	}
	var flag = confirm("您确定取消发放所选的资产？");
	
	if(flag){
		$('#ffType').val(-1);
		document.form1.submit();
	}
}

function submitFf(){
	var cbs = $('.cbs:checked');
	
	if(cbs.length<1){
		alert('请至少选择一项数据');
		return;
	}
	
	var flag = confirm("请确保所有勾选的资产都已经发放");
	if(flag){
		$('#ffType').val(1);
		document.form1.submit();
	}
}
</script>
</body>
</html>