<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

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
<div style="margin: 10px 5px 0 195px;">
	
<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<c:if test="${not empty msg}">
	<div class="lzui-error">
		${msg }
	</div>
	</c:if>
	
	
	<div style="width:97px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">考勤数据导出</div>
	
	<form name="form1" method="post" action="${ctx}/web/oa/kq/export">
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  					请按照下面的流程操作。
  				</div>
  			
  				<div class="">
  				
					
					<div class="lzui-headers">
						<div class="t2"><span>1</span>第一步、选择开始以及结束日期</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
						
			  			<div style="margin-bottom:10px;">
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">开始日期<span style="color:red">*</span></span>
						<input name="begin" id="begin" class="Wdate" readonly="readonly" autocomplete="off" onFocus="WdatePicker({ dateFmt:'yyyy年MM月dd日'})"/>
						
						<span style="font-weight:bold; line-height:25px; display:block; margin-bottom:10px; margin-top:10px;">结束日期<span style="color:red">*</span></span>
						<input name="end" id="end" class="Wdate" readonly="readonly" autocomplete="off" onFocus="WdatePicker({ dateFmt:'yyyy年MM月dd日'})"/>
						
						
						</div>
						
					    </div>
					    
					</div>
					
					<div class="lzui-headers">
						<div class="t3"><span>2</span>第二步、确认无误，点击提交</div>
						
						<div class="lzui-table-wapper" style="margin-top:7px;">
			  			<a href="javascript:submitRoles();" id="smbtn" class="lzui-btn2" style="height:30px; line-height:30px; width:100px;font-weight:bold; font-size:15px;">导出</a>
					    </div>
					    
					</div>
					<div style="clear:both;"></div>
  				
  				</div>	</form>
</div>
</div>

</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>



$(function(){
	$('#OA管理').addClass('ui-tabs-current');
	$('#考勤导出').addClass('cur');
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