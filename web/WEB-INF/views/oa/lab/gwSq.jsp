<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<style>
<!--
.shortInput {width:40px;}
.redInput {border:1px solid red;}
-->
</style>

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
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/lab/myGwSqRecord">点击查看申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/lab/gwSq" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }钢网加工工艺要求说明书</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:120px;">文件名称：</td>	
			<td style="width:90px;">
				<input name="fileName" id="fileName" onblur="checkFileName();"/>
				<span id="fileNameId" style="color:red; font-weight:bold;"></span>
			</td>	
			<td class="title" style="width:90px;">投板时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
			%>
			<td  style=" width:200px;">
				<input type="hidden" name="createTime" value="${now }"/>
				${now }
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">文档目录：</td>	
			<td style="border-right:none;" colspan="3">
				<input name="filePath" id="filePath" style="width:200px;"/>
			</td>	
			
			<%-- 
			<td class="title" style="border-left:none; width:90px;">订单编号：</td>	
			<td style="width:90px;">
				<input name="ddbh" id="ddbh"/>
			</td>	
			--%>
		</tr>
		<%-- 
		<tr>
			<td class="title" style="border-left:none; width:120px;">PCB加工工艺要求：</td>	
			<td style="border-right:none;" colspan="7">
				<select name="gyyq" id="gyyq">
					<option value="新投（新文件）">新投（新文件）</option>
					<option value="加做 （文件与上一版完全相同，以下内容只须写数量和交货日期。）">加做 （文件与上一版完全相同，以下内容只须写数量和交货日期。）</option>
					<option value="改版 （局部修改请说明修改部位；新文件覆盖旧文件请写明新文件名。）">改版 （局部修改请说明修改部位；新文件覆盖旧文件请写明新文件名。）</option>
				</select>
			</td>	
		</tr>
		--%>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		详细参数</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:105px;">数量：</td>	
			<td style="">
				<input name="numSet" id="numSet" class="shortInput"/>set
			</td>	
			
			<td class="title" style="border-left:none; width:120px;">尺寸：</td>	
			<td style="border-left:none;">
				<input name="ccChang" id="ccChang" class="shortInput"/>mm &nbsp;*&nbsp;
				<input name="ccKuang" id="ccKuang" class="shortInput"/>mm
			</td>	
			<td class="title" style="border-left:none; width:90px;">钢网厚度：</td>	
			<td style="border-right:none;">
				<select name="cpbh" id="cpbh">
					<option value="0.1mm">0.1mm</option> <option value="0.12mm">0.12mm</option> 
					<option value="0.13mm">0.13mm</option>
					<option value="0.15mm">0.15mm</option> <option value="0.18mm">0.18mm</option>
					<option value="0.2mm">0.2mm</option>
				</select>
			</td>
		</tr>
		
		<tr>
				
			<td class="title" style="width:90px;">钢网材料：</td>	
			<td style="">
				<select name="cl" id="cl">
					<option value="金属">金属</option> <option value="塑料">塑料</option>
				</select>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">用途：</td>	
			<td style="">
				<select name="bmtc" id="bmtc">
					<option value="印刷锡膏">印刷锡膏</option> <option value="印刷红胶">印刷红胶</option> 
					<option value="印刷胶水">印刷胶水</option>  
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">抛光工艺：</td>	
			<td style="border-right:none;">
				<select name="bmtchd" id="bmtchd">
					 <option value="打磨抛光">打磨抛光</option> <option value="电解抛光">电解抛光</option>
				</select>
			</td>	
		</tr>
		
		
	</tbody></table>
	<%-- 
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		备注信息</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="bz" id="bz" style="width:50%; height:120px; padding:5px 10px;">${leave.content }</textarea>
				<span style="color:red;">可不填，不超过150个字符，ctrl+Enter提交</span>
			</td>	
		</tr>
	</tbody></table>
	--%>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
	</div>
	
	</form>
	</div>
	
	
	<div id="cfmlay" style="display:none;">
		<div style="border:1px solid #2191C0; margin-top:0px; width:675px;">
		<div class="tableTitle" style="padding:10px 20px;">
			<span>
			<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
			申请信息详情：</span>
		</div>
		<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:120px;">文件名称：</td>	
			<td style="width:90px;" >
				<span id="sbFileName"></span>
			</td>	
			<td class="title" style="width:90px;">投板时间：</td>
			
			<td  style="border-right:none; width:200px;">	
				<span id="sbCreateTime">${now }</span>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">文档目录：</td>	
			<td style="width:90px;">
				<span id="sbFilePath"></span>
			</td>	
			
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		详细参数</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:105px;">数量：</td>	
			<td style="">
				<span id="sbNumSet"></span>
				set 
			</td>	
			<td class="title" style="border-left:none; width:120px;">尺寸：</td>	
			<td style="border-left:none;">
				<span id="sbCcChang"></span>
				mm &nbsp;*&nbsp;
				<span id="sbCcKuang"></span>mm
			</td>	
			<td class="title" style="border-left:none; width:90px;">钢网厚度：</td>	
			<td style="">
				<span id="sbCpbh"></span>
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="width:90px;">钢网材料：</td>	
			<td style="">
				<span id="sbCl"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">用途：</td>	
			<td style="">
				<span id="sbBmtc"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">抛光工艺：</td>	
			<td style="">
				<span id="sbBmtchd"></span>
			</td>
		</tr>
		
		
	</tbody></table>
		</div>
		</div>
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('a#OA申请').addClass('ui-tabs-current');	
	$('#加工申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

});

function checkFileName(){
	var fileName = $('#fileName').val();

	$.post('${ctx}/web/oa/lab/ajaxPcbName?hb?t='+Math.random(),{fileName:fileName},function(data){
		
		//alert(data);
		var d = eval(data);
		if(d.ret == 'ok'){
			//window.location.href
			$('#fileNameId').html("");
			$('#fileName').removeClass('redInput');
		}else{
			$('#fileNameId').html("该文件名已被使用");
			$('#fileName').addClass('redInput');
		}
	});
	
	
}


function smtCofirm(){

	$('#sbFileName').html($('#fileName').val());
	$('#sbFilePath').html($('#filePath').val());
	$('#sbNumSet').html($('#numSet').val());
	$('#sbCcChang').html($('#ccChang').val());
	$('#sbCcKuang').html($('#ccKuang').val());
	$('#sbCpbh').html($('#cpbh').val());
	$('#sbCl').html($('#cl').val());
	$('#sbBmtc').html($('#bmtc').val());
	$('#sbBmtchd').html($('#bmtchd').val());
	
	clearInterval(itv);
	
	$.layer({
		shade : [0.7, '#000'],
	    title: '请确认您要提交的信息',
	    btns:2,
	    btn: ['确定', '取消'],
	    type: 1,
	    closeBtn: [1, true],
	    border: [3],
	    shadeClose: true,
	    page: {dom : '#cfmlay'},
	    area: ['auto', '490px'],
	    no: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    },yes: function(index){
	    	document.form1.submit();
		}
	});
}

function submitForm(){
	var fileName = $('#fileName').val();
	var filePath = $('#filePath').val();

	var numSet = $('#numSet').val();
	var ccChang = $('#ccChang').val();
	var ccKuang = $('#ccKuang').val();

	var fnm = $('#fileNameId').html();

	if(fnm){
		alert('文件名称不能重复');
		return;
	}

	if(!fileName){
		alert('请填写文件名称');
		return;
	}
	if(!filePath){
		alert('请填写文档目录');
		return;
	}
	

	if(!numSet){
		alert('请填写数量（set）');
		return;
	}

	if(! /^\d+$/.test(numSet) ){
		alert('数量必须是整数');
		return;
	}

	if(!ccChang){
		alert('请填写尺寸（长）');
		return;
	}
	if(!ccKuang){
		alert('请填写尺寸（宽）');
		return;
	}

	if(! /^[0-9]*(\.[0-9]{1,2})?$/.test(ccChang) || ! /^[0-9]*(\.[0-9]{1,2})?$/.test(ccKuang)) {
		alert('尺寸必须是有效数字');
		return;
	}
	
	smtCofirm();
	//document.form1.submit();
}
</script>