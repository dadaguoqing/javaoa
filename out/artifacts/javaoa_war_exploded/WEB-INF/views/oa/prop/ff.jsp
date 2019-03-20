<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['14']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${param.msg == 1}">
	<div class="lzui-error">操作成功，请继续进行其他操作</div>
	</c:if>
	
	<form action="${ctx }/web/oa/prop/exportRecord1" method="post" id="form1" name="form1">
	<div class="" style="margin:10px 0 10px 0;">
		开始时间：	<input name="beginDate" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
		结束时间：<input name="endDate" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
		
		<a  id="smbtn" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">导出发放记录</a>
	</div>
	</form>
	
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="4">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				待处理的固定资产发放
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:70px;">申领人</td>
			<td class="l-td-header" style="width:170px;">申领时间</td>
			<td class="l-td-header l-td-left" style="">申领事由</td>
			<td class="l-td-header l-td-last-right" style="width:100px;">操作</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">${cur.proposerName }</td>
    			<td class="l-td">${cur.createTime }</td>
    			<td class="l-td l-td-left">${cur.content }</td>
    			<td class="l-td">
    				<a href="${ctx }/web/oa/prop/editLyff/${cur.id}">查看详情</a>
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td-last-right" colspan="4" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
	</tbody></table>

</div>
	</div>
</div>
<script>
$(function(){
	$('#OA处理').addClass('ui-tabs-current');	
	$('#固定资产发放').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#smbtn').click(function(){
				document.form1.submit();
			});
});
</script>
</body>
</html>