<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>


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

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${param.msg == 1}">
	<div class="lzui-error">操作成功，请继续进行其他操作</div>
	</c:if>
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	<form name="form1" action="${ctx }/web/oa/prop/sgh" method="post">
	<input type="hidden" name="ffType" id="ffType" />
	<div class="" style="margin:10px 0 10px 0;">
		开始时间：	<input name="beginDate" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
		结束时间：<input name="endDate" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
		
		<a  id="smbtn" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">导出归还记录</a>
	</div>
	<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="8">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				申请归还的固定资产
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header l-td-left" style="width:30px;">
			<input type="checkbox" name="selectAll" class="cbs" id="selectAll" />
			</td>
			<td class="l-td-header l-td-left" style="width:120px;">负责人</td>
			<td class="l-td-header l-td-left" style="width:80px;">资产编号</td>
			<td class="l-td-header l-td-left" style="width:80px">资产分类</td>
			<td class="l-td-header l-td-left" style="width:100px;">资产名称</td>
			<td class="l-td-header l-td-left" style="width:100px;">规格型号</td>
			<td class="l-td-header l-td-left" style="width:100px;">放置地点</td>
			<td class="l-td-header l-td-left l-td-last-right" style="width:60px;">备注</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td l-td-left">
    				<input type="checkbox" name="ids" class="cbs"  id="ids" value="${cur.id},${cur.empId}" />
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
    			<td class="l-td l-td-left">
    				${empty cur.bz ? '无' : cur.bz }
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
		<a href="javascript:submitGh();" class="lzui-btn2" 
			style="height:30px; line-height:30px; float:left; width:100px;font-weight:bold; font-size:15px;">
			确认归还
		</a>
			 	
		<a href="javascript:CancelGh();" class="lzui-btn" 
			style="height:30px; line-height:30px; margin-left:15px; float:left; width:100px;font-weight:bold; font-size:15px;">
			取消归还
		</a>
	</div>
	</div>
</div>
<script>
$(function(){
	$('#OA管理').addClass('ui-tabs-current');	
	$('#固定资产归还').addClass('cur');	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	
	$("#selectAll").click(function(){
		//版本太老的话用attr只能实现一次，用prop比较稳定
		$("[name=ids]:checkbox").prop("checked",this.checked);
		});
		$("[name=ids]:checkbox").click(function(){
		var flag=true;
		$("[name=ids]:checkbox").each(function(){
		if(!this.checked){
		flag=false;
		}
		});
		$("#selectAll").prop("checked",flag);
		});
		
		$('#smbtn').click(function(){
			document.form1.action="${ctx }/web/oa/prop/exportRecord3";
			document.form1.submit();
		});
	});
	


function CancelGh(){
	var cbs = $('.cbs:checked');
	
	if(cbs.length<1){
		alert('请至少选择一项数据');
		return;
	}
	var flag = confirm("您确定取消归还所选的资产？");
	
	if(flag){
		$('#ffType').val(-1);
		document.form1.submit();
	}
}



function submitGh(){
	var cbs = $('.cbs:checked');
	
	if(cbs.length<1){
		alert('请至少选择一项数据');
		return;
	}
	
	var flag = confirm("请确保所有勾选的资产都已经归还");
	if(flag){
		$('#ffType').val(1);
		document.form1.submit();
	}
	}
</script>
</body>
</html>