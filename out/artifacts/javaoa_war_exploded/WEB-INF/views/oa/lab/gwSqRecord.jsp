<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/lab/myGwSp">点击返回钢网加工审批</a>
  	</div>
  	
  	<c:if test="${ param.jg == 1 }">
	<div class="lzui-error">操作成功</div>
	</c:if>
  	
  	<c:if test="${zbfzr}">
  	<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" action="${ctx}/web/oa/lab/gwSqRecord" method="post" >
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">加工状态：</td>
			<td>
			<select name="jgStatus" id="jgStatus">
				<option value="-2">全部</option>
				<option value="0">未处理</option>
				<option value="1">已加工</option>
				<option value="-1">不加工</option>
			</select>
			</td>
			
		</tr>
	</table>
	</form>
	
	<form name="form2" action="${ctx}/web/oa/lab/gwJg" method="post" >
		<input type="hidden" name="id" id="JgId"/>
		<input type="hidden" name="status" id="JgStatus"/>
		<input type="hidden" name="bz" id="JgBz"/>
	</form>
	
	<div class="gridContainer" style="width:100%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		<tr>
			<td class="l-td-toolbar" colspan="8">
				<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
				员工钢网加工申请列表
				</div>
			</td>
		</tr>
		
		<tr>
			<td class="l-td-header" style="width:120px;">订单编号</td>
			<td class="l-td-header l-td-left" style="width:80px;">申请人</td>
			<td class="l-td-header l-td-left" style="width:100px;">文件名称</td>
			<td class="l-td-header l-td-left" style="width:120px;">文档目录</td>
			<td class="l-td-header l-td-left" style="width:60px;">报价</td>
			<td class="l-td-header l-td-left" style="width:130px;">状态</td>
			<td class="l-td-header l-td-left" style="">备注<span style="color:red;">（不超过50个字符）</span></td>
			<td class="l-td-header l-td-last-right" style="width:180px;">操作</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td">${empty cur.ddbh ? '暂无' : cur.ddbh}</td>
    			<td class="l-td l-td-left">${cur.proposerName }</td>
    			<td class="l-td l-td-left">${cur.fileName }</td>
    			<td class="l-td l-td-left">${cur.filePath }</td>
    			<td class="l-td l-td-left">${ empty cur.bj ? '暂无' : cur.bj  }${ empty cur.bj ? '' : '元'  }</td>
    			<td class="l-td l-td-left">
    				<c:choose>
    					<c:when test="${cur.status == -1}">
    					审批不通过
    					</c:when>
    					<c:when test="${cur.status == 1}">
    					制板负责人审批中
    					</c:when>
    					<c:when test="${cur.status == 2}">
    					部门主管审批中
    					</c:when>
    					<c:when test="${cur.status == 3}">
    					财务主管审批中
    					</c:when>
    					<c:when test="${cur.status == 4}">
    					总经理审批中
    					</c:when>
    					<c:when test="${cur.status == 5}">
    					审批通过
    					</c:when>
    				</c:choose>
    				
					<c:choose>
    					<c:when test="${cur.jgStatus == 1}">
    					<span style="color:green;">（已加工）</span>
    					</c:when>
    					<c:when test="${cur.jgStatus == -1}">
    					<span style="color:red;">（不加工）</span>
    					</c:when>
    				</c:choose>
				</td>
				<td class="l-td l-td-left">
				<c:if test="${cur.status == 5 && cur.jgStatus == 0}">
					<input id="bz${cur.id }"  type="text" placeHolder="不加工请在这里填写备注" style="width:200px;"/> 
                                         				</c:if>
				<c:if test="${cur.jgStatus != 0 || cur.status != 5}">
				${empty cur.jgBz ? '无' : cur.jgBz}
				</c:if>
				
				</td>
				<td class="l-td l-td-last-right" style="">
					
					<c:choose>
    					<c:when test="${cur.status == 5 && cur.jgStatus == 0}">
    					<a href="javascript:jg(${cur.id }, '${cur.fileName }');">已加工</a> | 
    					<a href="javascript:bjg(${cur.id }, '${cur.fileName }');">不加工</a> | 
    					</c:when>
    				</c:choose>
					
					<a href="${ctx }/web/oa/lab/gwSqDetail2/${cur.id}">详情</a>|
					
					<a href="${ctx }/web/oa/lab/gwDownload/${cur.id}">下载</a>
				</td>
    		</tr>
    	</c:forEach>
    	
    	<c:if test="${empty list}">
    		<tr>
			<td class="l-td l-td-last-right" colspan="8" style="color:red;">
				对不起，目前还没有相关数据。
			</td>
		</tr>
    	</c:if>
	</tbody></table>

	</div>
	</c:if>

	<c:if test="${not zbfzr}">
	<div class="gridContainer" style="width:95%; margin-left:0px;margin-top:15px;">
	<table class="lzzUIGrid"><tbody>
		
		<tr>
			<td class="l-td-header" style="width:175px;">申请时间</td>
			<td class="l-td-header" style="width:120px;">申请人</td>
			<td class="l-td-header" style="width:100px;">类型</td>
			<td class="l-td-header l-td-left" style="width:170px;">审批状态</td>
			<td class="l-td-header l-td-last-right" style="">操作</td>
		</tr>
    	
    	<c:forEach items="${list}" var="cur">
    		<tr>
    			<td class="l-td">${cur.createTime }</td>
    			<td class="l-td">${cur.proposerName }</td>
    			<td class="l-td l-td-left">${cur.type == 1 ? 'PCB加工' : '钢网加工' }</td>
    			<td class="l-td l-td-left">
    				<c:choose>
    					<c:when test="${cur.status == -1}">
    					审批不通过
    					</c:when>
    					<c:when test="${cur.status == 1}">
    					制板负责人审批中
    					</c:when>
    					<c:when test="${cur.status == 2}">
    					部门主管审批中
    					</c:when>
    					<c:when test="${cur.status == 3}">
    					财务主管审批中
    					</c:when>
    					<c:when test="${cur.status == 4}">
    					总经理审批中
    					</c:when>
    					<c:when test="${cur.status == 5}">
    					审批通过
    					</c:when>
    				</c:choose>
				</td>
				<td class="l-td l-td-last-right" style="">
					<a href="${ctx }/web/oa/lab/gwSqDetail2/${cur.id}">查看详情</a>
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
	
	</c:if>
	</div>
</div>

</body>
<script>
$(function(){
	$('#OA审批').addClass('ui-tabs-current');	
	$('#加工审批').addClass('cur');	


	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#jgStatus').val('${jgStatus}');
	$('#jgStatus').change(function(){
		document.sForm.submit();
	});
	
});


function jg(id, fileName){

	var flag = confirm("确定已经加工《"+fileName+"》？");
	if(flag){
		var bz = $('#bz' + id).val();
		$('#JgId').val(id);
		$('#JgStatus').val(1);
		$('#JgBz').val(bz);
		document.form2.submit();
		//location.href = "${ctx }/web/oa/lab/pcbJg?id="+id;
	}
}

function bjg(id, fileName){
	var bz = $('#bz' + id).val();
	bz = $.trim(bz);
	if(!bz){
		alert("请输入备注信息");
		$('#bz' + id).focus();
		return;
	}
	if(bz.length > 50){
		alert("备注信息不能超过50个字符");
		$('#bz' + id).focus();
		return;
	}
	
	var flag = confirm("确定不加工《"+fileName+"》？");
	if(flag){
		
		$('#JgId').val(id);
		$('#JgStatus').val(-1);
		$('#JgBz').val(bz);
		document.form2.submit();
		//location.href = "${ctx }/web/oa/lab/pcbJg?id="+id;
	}
}
</script>
</html>