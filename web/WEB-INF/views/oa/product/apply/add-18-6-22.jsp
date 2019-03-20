<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['102']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../../public/hello.jsp" %>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/product/outstock/apply/records">点击查看申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/product/apply/add" method="post" enctype="multipart/form-data">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }产品发货申请单</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:100px;">申请人：</td>	
			<td style="width:100px;">
				<input type="hidden" name="name" value="${loginUser.name }"/>
				<input type="hidden" name="id" value="${loginUser.id }"/>
				${loginUser.name }
			</td>	
			<td class="title" style="border-left:none; width:100px;">部门：</td>	
			<td>
				<input type="hidden" name="deptId" value="${loginUser.deptId }"/>
				<span id="deptSpan">${loginUserDept.name }</span>
			</td>	
			<td class="title" style="border-left:none; width:180px;">申请单号：</td>	
			<td>
				<input type="hidden" name="oddNumber" value="${oddNumber }"/>
				<span>${oddNumber }</span>
			</td>
				
		</tr>
		<tr>
			<td class="title" style="width:60px;">申请原因：</td>	
			<td style="width:90px;">
				<select name="reason">
					<c:forEach var="reason" items="${reasons}">
						<option value="${reason}">${reason}</option>
					</c:forEach>
				</select>
			</td>
			<td class="title" style="width:100px;">申请时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
			%>
			<td  style="border-right:none; width:200px;">
				<input type="hidden" name="createTime" value="${now }"/>
				${now }
			</td>
			<td class="title" style="color: red;" >逾期未处理自动取消时间：</td>	
			<td>
			<%
			Calendar cal = Calendar.getInstance();//使用默认时区和语言环境获得一个日历。    
			cal.add(Calendar.DAY_OF_MONTH, +15);//取当前日期的后一天.    
			//通过格式化输出日期    
			java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy年MM月dd日");
			String cancelDate = format.format(cal.getTime());
			pageContext.setAttribute("cancelDate",cancelDate);
			%>
				<span>${cancelDate} 24时00分</span>
				<input type="hidden" name="cancelTime" value="${cancelDate} 24时00分"/>
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		发货详细</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">收货单位：</td>	
			<td style="width:260px;" colspan="3">
				<input id="receiverCompany" type="text" name="receiverCompany">
			</td>	
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:90px;">收货地址：</td>	
			<td style="width:260px;" colspan="3">
				<input id="addr" type="text" name="addr">
			</td>	
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:90px;">收货人：</td>	
			<td >
				<input id="receiver" type="text" name="receiver">
			</td>	
			<td class="title" style="border-left:none; width:90px;">联系电话：</td>	
			<td >
				<input id="tel" type="text" name="tel">
			</td>	
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:90px;">合同附件：</td>	
			<td colspan="3">
				<input id="contract" type="file" name="uploadFile"> <span style="color: red;">请上传pdf格式合同文件</span>
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		添加发货产品</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="text-align:left; width:60px;">
				产品名称
			</td>
			<td class="title" style="text-align:left; width:60px;">
				规格型号
			</td>
			<td class="title" style="text-align:left; width:60px;">
				数量
			</td>
			<td class="title" style="text-align:left; width:60px;">
				单位
			</td>
			<td class="title" style="text-align:left;">
				备注
			</td>
			<td class="title" style="width:100px; text-align:left; border-right:none; ">
				操作
			</td>		
		</tr>
		
		<tr id="addCdTr">
			<td style="width:90px;">
				<select id="productName" onchange="showProductModel(this);">
					<option value="-1">- 请选择 -</option>
					<c:forEach var="product" items="${products}">
						<option value="${product.key}">${product.key }</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select id="productModel">
					<option value="-1">- 请选择 -</option>
				</select>
			</td>
			<td>
				<input id="count" type="text">
			</td>
			<td></td>
			<td>
				<input id="remark" type="text" value="- 无 -">
			</td>
			<td style=" width:60px; border-right:none; ">
				<a href="javascript:addcd();">确定</a>
			</td>		
		</tr>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script>
var index = 1;
var count = 3;
function submitForm() {
	// 参数校验
	var receiverCompany = $('#receiverCompany').val();
	var receiver = $('#receiver').val();
	var addr = $('#addr').val();
	var tel = $('#tel').val();
	var contract = $('#contract').val();
	if (!receiverCompany) {
		alert('请填写收货单位');
		return;
	}
	if (!addr) {
		alert('请填写收货地址');
		return;
	}
	if (!receiver) {
		alert('请填写收货人');
		return;
	}
	if (!tel) {
		alert('请填写联系电话');
		return;
	}
	if (!contract) {
		alert('请上传合同附件');
		return;
	}
	if (!/\.(pdf)$/.test(contract)) {    
        alert("请上传pdf格式文件");    
        return;    
    } 
	// 判断是否添加了发货产品
	if (index == 1) {
		alert('请添加发货产品');
		return;
	}
	
	document.form1.submit();
}

$(function(){
	$('a#产品管理').addClass('ui-tabs-current');	
	$('#产品发货申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});



function addcd(){
	var nameSpan = $('#productName');
	var modelSpan = $('#productModel');
	var countSpan = $('#count');
	var remarkSpan = $('#remark');
	
	var name = $.trim(nameSpan.val());
	if(name == '-1'){
		alert('请选择产品名称');
		return;
	}
	var model = $.trim(modelSpan.val());
	if(model == '-1'){
		alert('请选择规格型号');
		return;
	}
	var num = $.trim(countSpan.val());
	if (!num) {
		alert('请输入数量');
		return;
	}
	var regu = /^[1-9]\d*$/;
	if (!regu.test(num)) {
		alert('请输入正确数量');
		return;
	}
	
	var remark = $.trim(remarkSpan.val());
	if (!remark) {
		remark = '- 无 -';
	}
	
	count++;
	var html = 
		'<tr id="cd'+count+'">'+
			'<td style="">' + name + '<input type="hidden" value="'+name+'" name="names"/></td>' +
			'<td style="">' + modelSpan.find("option:selected").text() + '<input type="hidden" value="'+model+'" name="ids"/></td>' + '<input type="hidden" value="'+modelSpan.find("option:selected").text()+'" name="models"/></td>' +
			'<td style="">' + num + '<input type="hidden" value="'+num+'" name="nums"/></td>' +
			'<td style="">' + 'PCS' + '<input type="hidden" value="PCS" name="units"/></td>'+
			'<td style="">' + remark + '<input type="hidden" value="'+remark+'" name="remarks"/></td>'+
			'<td style=" border-right:none; "><a href="javascript:delcd('+count+');">删除</a></td>'+
		'</tr>';

	$('#addCdTr').after(html);
	index++;
}

function delcd(tid){
	$('#cd'+tid).remove();
	index--;
}

function showProductModel(obj) {
	var products = '${products}';
	products = JSON.parse(products);
	if (obj.value == '-1') {
		$('#productModel').empty();
		$('#productModel').append('<option value="-1">- 请选择 -</option>');
		return;
	}
	
	for (var key in products) {
		if (key == obj.value) {
			for (var i in products[key]) {
				$('#productModel').append('<option value="' + products[key][i].id + '">' + products[key][i].productModel + '</option>');
			}
		}
	}
}
</script>