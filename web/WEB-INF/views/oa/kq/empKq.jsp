<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<div class="navTitle2 cur" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;人员详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/kq/list?deptId=${deptId}&month=${month}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div class="gridContainer" style="width:95%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="6">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				${emp.name }${yf }考勤列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:140px;">日期</td>
			<td class="l-td-header" style="width:140px;">上班打卡</td>
			<td class="l-td-header" style="width:140px;">下班打卡</td>
			<td class="l-td-header l-td-left" style="width:140px;">请假时长</td>
			  
			<td class="l-td-header l-td-left" style="width:140px;">出差时长</td>
			<!--
			<td class="l-td-header l-td-left" style="width:170px;">加班</td>
			-->
			<td class="l-td-header l-td-left l-td-last-right" style="width:140px">不正常打卡</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    	 	
    		<c:choose>
    	 		<c:when test="${cur.type=='节假日'}">
    	 			<tr style='color:green;'>
    	 		</c:when>
    	 		<c:when test="${cur.uncLen > 0}">
    	 			<tr style='color:red;'>
    	 		</c:when>
    	 		<c:otherwise>
    	 			<tr style=''>
    	 		</c:otherwise>
    	 	</c:choose>
    			<td class="l-td">${cur.dayStr }</td>
    			<td class="l-td">${ empty cur.checkin ? '未打卡' : cur.checkin}</td>
    			<td class="l-td">${ empty cur.checkout ? '未打卡' : cur.checkout }</td>
    			  
    			<td class="l-td l-td-left">${cur.timeLen}分钟</td>
    			
    			<td class="l-td l-td-left">${cur.wcLen}分钟</td>
    			<!--
    			<td class="l-td l-td-left">
    				${ cur.jiaban  }分钟
				</td>-->
				<td class="l-td l-td-left l-td-last-right" style="">
					${cur.uncLen }分钟
				</td>
    		</tr>
    	</c:forEach>
    	
    	
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td l-td-last-right" colspan="6" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
    	
    	<tr style="color:red;">
    			<td class="l-td">总计:</td>
    			<td class="l-td">&nbsp;</td>
    			<td class="l-td">&nbsp;</td>
    			<td class="l-td l-td-left">${total.timeLenStr }</td>
    			  
    			<td class="l-td l-td-left">${total.wcLenStr }</td>
    			<!--
    			<td class="l-td l-td-left">
    				${ total.jiabanStr }
				</td>-->
				<td class="l-td l-td-left l-td-last-right" style="">
					${total.uncLenStr }
				</td>
    		</tr>
	</tbody></table>

</div>
	</div>
</div>

</body>
<script>
$(function(){
	$('#考勤管理').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
</script>
</html>