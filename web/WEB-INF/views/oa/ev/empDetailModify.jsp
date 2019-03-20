<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<style>
<!--
.tr1:hover {background-color: #ddd;}
-->
</style>
<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;员工自评详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/ev/subs?deptId=${param.deptId}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">

<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">主管评价</div>
	<c:if test="${empty ev.zgPoint}">
	<form name="form0" action="${ctx}/web/oa/ev/modify" method="post" enctype="multipart/form-data">
	</c:if>
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="4">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				宏晶科技2017年度员工主管评价
				<span style="color:red">（${emp.name }）</span>
				
				<input type="hidden" name="empName" value="${emp.name }">
				<input type="hidden" name="empId" value="${emp.id }">
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" >分类</td>
			<td class="l-td-header l-td-left" >评价项目</td>
			<td class="l-td-header l-td-left">自评得分</td>
			<td class="l-td-header l-td-left">补充说明</td>
		</tr>
		<c:set var="result" value="0"></c:set>
		<c:set var="ability" value="0"></c:set>
		<c:set var="attitude" value="0"></c:set>
		<c:set var="team" value="0"></c:set>
		
    	<c:forEach items="${list}" var="cur" varStatus="s">
    		<c:choose>
	    			<c:when test="${cur.type==0 }">
	    				<c:set var="result" value="${result+1 }"/>
	    			</c:when>
	    			<c:when test="${cur.type==1 }">
	    				<c:set var="ability" value="${ability+1 }"/>
	    			</c:when>
	    			<c:when test="${cur.type==2 }">
	    				<c:set var="attitude" value="${attitude+1 }"/>
	    			</c:when>
	    			<c:when test="${cur.type==3 }">
	    				<c:set var="team" value="${team+1 }"/>
	    			</c:when>
    			</c:choose>
    	</c:forEach>
    	<c:forEach items="${list}" var="cur" varStatus="s">
    		<c:if test="${not empty cur.point}">
    		<tr class="tr1">
    			<c:choose>
	    			<c:when test="${cur.type==0 && s.count==1}">
	    				<td class="l-td l-td-left" width="30px" rowspan="${result }" bordercolor="red" >工作结果<br/>┍权重55%┙</td>
	    			</c:when>
	    			<c:when test="${cur.type==1 && s.count==(result+1)}">
	    				<td class="l-td l-td-left" width="30px" rowspan="${ability }">工作能力<br/>┍权重15%┙</td>
	    			</c:when>
	    			<c:when test="${cur.type==2 && s.count==(result+ability+1)}">
	    				<td class="l-td l-td-left" width="30px" rowspan="${attitude }">工作态度<br/>┍权重15%┙</td>
	    			</c:when>
	    			<c:when test="${cur.type==3 && s.count==(result+ability+attitude+1)}">
	    				<td class="l-td l-td-left" width="30px" rowspan="${team }">团队精神及职业素养<br/>┍权重15%┙</td>
	    			</c:when>
    			</c:choose>
    			<td class="l-td l-td-left" width="450px">${s.count}. ${cur.title}</td>
    			<td class="l-td l-td-center" width="100px">
    				<c:if test="${empty ev.zgPoint}">
    				<input type="hidden" name="evaIds" value="${cur.id }">
    				<select class="poi" name="points" style="width:45px;">
    					<tag:loopInt times="${cur.point }"/>
    				</select>
    				</c:if>
    				<c:if test="${not empty ev.zgPoint}">
    					${cur.zgPoint }
    				</c:if>
    			</td>
    			<td class="l-td l-td-left">
    				<c:if test="${empty ev.zgPoint}">
    					<input type="text" class="ptxt" name="anses" size="60px" value="${cur.zgAns }"/>
    				</c:if>
    				<c:if test="${not empty ev.zgPoint}">
    					${cur.zgAns }
    				</c:if>
    			</td>
    		</tr>
    		</c:if>
    		
    		<c:if test="${empty cur.point}">
    		<tr>
    			<td class="l-td l-td-left" colspan=2 style="border-bottom:none;">${cur.title}</td>
    		</tr>
    		<tr>
    			<td class="l-td l-td-left" colspan=2>
    				<c:if test="${empty ev.point}">
    				<input type="hidden" name="evaIds" value="${cur.id }">
    				<textarea name="points" style="width:80%; height:50px; padding:3px 5px;"></textarea>
    				</c:if>
    				
    				<c:if test="${not empty ev.point}">
    				<textarea style="width:80%; height:70px; padding:3px 5px;">${cur.ans }</textarea>
    				</c:if>
    			</td>
    		</tr>
    		</c:if>
    	</c:forEach>
	</tbody></table>
	</div>
	<c:if test="${empty ev.zgPoint}">
	</form>
	</c:if>
	
	<c:if test="${empty ev.zgPoint}">
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交</a>
	</div>
	</c:if>
	
	</div>
</div>
</div>

</body>
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

function submitForm(){
	var al = $('.poi');
	var f = true;
	al.each(function(i){
		var p = parseInt($(this).val());
		if(p == 0){
			f = false;
		}
	});

	if(!f){
		alert("请对每一个自评项目进行打分。");
		return;
	}

	var f2 = true;
	var altxt = $('.ptxt');
	altxt.each(function(i){
		var t = $(this).val();
		if(t.length > 100){
			f2 = false;
		}
		t = $.trim(t);

	});

	if(!f2){
		alert("补充说明不能超过500个字符，请减少文字");
		return;
	}
	document.form0.submit();
}

</script>
</html>