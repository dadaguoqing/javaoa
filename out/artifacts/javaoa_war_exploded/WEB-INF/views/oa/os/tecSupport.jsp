<%@ page language="java" import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>


<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['4']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc; width:960px;">
		<div style="width:93px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">技术支持申请</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/os/mySupportRecord">点击查看申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/os/tecSupportAction" method="post" enctype="multipart/form-data">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px; width:960px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }技术支持申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1"><tbody>
		<tr>
			<td class="title" style="width:15%;">申请人：</td>	
			<td style="width:15%;">
				${loginUser.name }
			</td>	
			<td class="title" style="width:15%;">所属部门：</td>	
			<td style="width:15%;">
				${loginUserDept.name }
			</td>
			<td class="title" style="width:120px;">申请时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
				String tdy = now.substring(0,11);
			%>
			<td style="border-right:none;">
				${now }
			</td>	
			
		</tr>
		<tr>
			<td class="title" >优先级：</td>	
			<td style="width:90px;">
				<select id="priority" name="priority">
					<option selected="selected">==请选择优先级==</option>
					<option>高</option>
					<option>中</option>
					<option>低</option>
				</select>
			</td>
			<td class="title" style="width:120px;">期望完成时间：</td>	
			<td style="width:90px;">
				<input name="expectdTime" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			</td>
			<td class="title" style="width:120px;">最晚完成时间：</td>	
			<td style="width:90px;">
				<input name="deadline" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		申请详情</span>
	</div>
	
	<table class="table1" id='cdTable' ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:120px;">
				客户单位(全称)
			</td>
			<td class="title" style="text-align:left; width:120px;">
				开案项目
			</td>
			<td class="title" style="text-align:left; width:100px; ">
				技术支持内容
			</td>
			<td class="title" style="text-align:left; border-right:none; ">
				附件文档<a style="color:red">（小于5M的压缩文件）</a>
			</td>		
		</tr>
		
		<tr id="addCdTr">
			<td style="">
				<input type="text" id="customer" name="customer"/>
			<td style="color:red;">
				<span id="typeSpan">&nbsp;</span>
				<input type="text" id="project" name="project"/>
			</td>
			<td style="color:red;">
				<input type="text" id="supportContent" name="supportContent"/>
			</td>
			<td style=" width:60px; border-right:none; ">
				<input type="file" id="file" name="file"/>
			</td>		
		</tr>
		
		
	</tbody></table>
	
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申领</a>
	</div>
	</form>
	</div>
</div>

</body>
</html>
<script>
var index;

$(function(){
	$('#OA申请').addClass('ui-tabs-current');	
	$('#技术支持申请').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});

function submitForm(){
	if('${loginUserDept.name }'== null ){
		alert("您不需要申请");
		return;
	}
// 	if('${loginUserDept.name }'==null || '${loginUserDept.name }'!="业务部"){
// 		alert("您不需要申请");
// 		return;
// 	}
	var expectdTime = $('#expectdTime').val();
	var deadline = $('#deadline').val();
	var priority = $('#priority').val();
	var customer = $('#customer').val();
	var project = $('#project').val();
	var file  = $('#file').val();
	var supportContent = $('#supportContent').val();
	if(priority=='==请选择优先级=='){
		alert("请选择优先级");
		return;
	}
	$.ajax(
		{
		url:"chooseDate",
		type:"post",
		data:{
			expectdTime:expectdTime,
			deadline:deadline
		},
		sucess:function(data){
			alert(data);
		}
		});
	
	if(!customer){
		alert("请填写客户单位");
		return;
	}
	if(!project){
		alert("请填写开案项目");
		return;
	}
	if(!supportContent){
		alert("请填写技术支持内容");
		return;
	}
	if(!file){
		alert("请上传文件");
		return;
	}
	
	document.form1.submit();
}
</script>