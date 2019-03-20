<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="${ctx }/web/oa/notice/list" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">公告管理</div>
		
				
		<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
			<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
			查询条件</div>
		<form name="sForm" action="${ctx}/web/oa/notice/list" method="post" >
		<table id="baseInfoTable">
			<tr>
				<td class="right" style="width:95px;">发布时间，从：</td>
				<td>
				<input name="begin" id="begin" value="${begin}" class="Wdate" style="width:115px;" readonly="readonly" autocomplete="off" 
					onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
				</td>
				
				<td class="right" style="width:25px;">到：</td>
				<td>
				<input name="end" id="end" value="${end}" class="Wdate" style="width:115px;" readonly="readonly" autocomplete="off" 
					onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
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
		
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:10px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="6">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					通知公告列表
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header" style="width:190px;">发布时间</td>
				<td class="l-td-header l-td-left" style="width:180px;">标题</td>
				<td class="l-td-header l-td-left" style="width:80px;">发布人</td>
				<td class="l-td-header l-td-left" style="width:150px;">发布部门</td>
				<td class="l-td-header l-td-last-right" style="">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur">
	    		<tr>
	    			<td class="l-td">${cur.createTime }</td>
	    			<td class="l-td l-td-left">${cur.title }</td>
	    			<td class="l-td l-td-left">${cur.publisher }</td>
	    			<td class="l-td l-td-left">${cur.pubName }</td>
	    			<td class="l-td">
						<a href="${ctx}/web/oa/user/edit?id=${cur.id}">详情</a> | <a href="${ctx}/web/oa/notice/del/${cur.id}">删除</a>
					</td>
	    		</tr>
	    	</c:forEach>
	    	
	    	<c:if test="${empty list}">
	    		<tr>
				<td class="l-td l-td-last-right" colspan="5" style="color:red;">
					对不起，目前还没有相关数据。
				</td>
			</tr>
	    	</c:if>
		</tbody></table>
		</div>
		
	</div>
</div>
</div>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>

<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#通知公告').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#sBtn').click(function(){
		document.sForm.submit();
	});

	$('#status').val('${status}');
	$('#begin').val('${begin}');
	$('#end').val('${end}');
});
</script>
</body>
</html>