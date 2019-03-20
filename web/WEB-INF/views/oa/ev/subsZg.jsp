<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag"%>
<%@ include file="../../public/header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>  
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<style>
	.l-td a {
        color:red;
        text-decoration:none;
        margin:0;
        padding:5px 20px;
        border:1px solid #e8f3f8;
      }
      .l-td a:hover {
       border:1px solid #97B9C9;
       color:#067DB5;
       background-color:#CDDDE4;
     }
     .look{
     	color:black;
     }
     .tr1:hover {background-color: #eee;}
</style>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	
	<div class="navTitle">系统导航</div>
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;主管评价详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/ev/subs"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;下属自评详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/ev/mine"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px; padding-bottom:20px;">

<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	
	<div style="width:172px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">主管评价</div>
	
		
	
	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" action="${ctx}/web/oa/ev/subsZg" method="post" >
	<table id="baseInfoTable">
		<tr>
			
			<td class="right" style="width:80px;">选择部门：</td>
			<td>
			<select name="deptId" id="cDept">
				<c:forEach items="${depts}" var="cur">
				<option value="${cur.id }">${cur.name }</option>
				</c:forEach>
			</select>
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
	
	<form name="detailForm" action="${ctx}/web/oa/kq/emp" method="post">
		<input type="hidden" id="dtId" name="id"/>
		<input type="hidden" id="dtDept" name="deptId"/>
	</form>
	
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px; margin-bottom:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-header l-td-left" >所属部门</td>
			<td class="l-td-header l-td-left" >员工姓名</td>
			<td class="l-td-header l-td-left">主管评分</td>
		</tr>
    	<c:forEach items="${list}" var="cur">
    	<c:if test="${(loginUser.id==96 &&( cur.empId==6 || cur.empId==5 || cur.empId==90)) || 
    	(loginUser.id==61 &&( cur.empId==94 || cur.empId==67 || cur.empId==71 || cur.empId==88 || cur.empId==98)) || 
    	(loginUser.id==35 &&( cur.empId==130 || cur.empId==98))  
    	}">
    		<tr class="tr1">
    			<td class="l-td l-td-left">${cur.bz}</td>
    			<td class="l-td l-td-left">${cur.empName}</td>
	    		<c:if test="${not empty cur.point}">
	    			<td style="color:green;" class="l-td l-td-left">
		    				<a href="${ctx }/web/oa/ev/empDetailZg/${cur.empId}?deptId=${dept.id}&zgId=${cur.zgId}" title="查看详情"  style="color: black;">
		    				<fmt:formatNumber type="number" value="${cur.score }" pattern="0.00" maxFractionDigits="2"/>
		    				</a>
	    			</td>
	    		</c:if>
   				<c:if test="${empty cur.point}">
    				<td style="color:red;" class="l-td l-td-left" colspan="1">
    					<c:if test="${cur.empId!=loginUser.id }">
    					<a href="${ctx }/web/oa/ev/empDetailZg/${cur.empId}?deptId=${dept.id}&zgId=${cur.zgId}" title="主管评价" >去评价</a>
    					</c:if>
    				</td>
   				</c:if>
    		</tr>
    		</c:if>
    	<c:if test="${loginUser.id!=96 &&loginUser.id!=61&&loginUser.id!=35}">
    		<tr class="tr1">
    			<td class="l-td l-td-left">${cur.bz}</td>
    			<td class="l-td l-td-left">${cur.empName}</td>
	    		<c:if test="${not empty cur.point}">
	    			<td style="color:green;" class="l-td l-td-left">
		    				<a href="${ctx }/web/oa/ev/empDetailZg/${cur.empId}?deptId=${dept.id}&zgId=${cur.zgId}" title="查看详情"  style="color: black;">
		    				<fmt:formatNumber type="number" value="${cur.score }" pattern="0.00" maxFractionDigits="2"/>
		    				</a>
	    			</td>
	    		</c:if>
   				<c:if test="${empty cur.point}">
    				<td style="color:red;" class="l-td l-td-left" colspan="1">
    					<c:if test="${cur.empId!=loginUser.id}">
    					<c:if test="${(loginUser.id==8 &&( cur.empId==163 || cur.empId==128 || cur.empId==10 || cur.empId==4 || dept.id==4 || dept.id==21))||
    					 loginUser.id==1}">
    					<a href="${ctx }/web/oa/ev/empDetailZg/${cur.empId}?deptId=${dept.id}&zgId=${cur.zgId}" title="主管评价" >去评价</a>
    					</c:if>
    					<c:if test="${loginUser.id!=1 && loginUser.id!=8 }">
    					<a href="${ctx }/web/oa/ev/empDetailZg/${cur.empId}?deptId=${dept.id}&zgId=${cur.zgId}" title="主管评价" >去评价</a>
    					</c:if>
    					</c:if>
    				</td>
   				</c:if>
    		</tr>
    		</c:if>
    	</c:forEach>
	</tbody></table>

	</div>
	
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

	$('#sBtn').click(function(){
		document.sForm.submit();
	});
	$('#cDept').val('${dept.id}');
	
});

</script>
</html>