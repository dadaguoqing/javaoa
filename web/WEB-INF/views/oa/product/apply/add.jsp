<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	pageContext.setAttribute("ctx", path);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>合肥宏晶微电子科技股份有限公司WE网</title>
<link rel="stylesheet" href="${ctx }/resources/css/base.css" />
<link rel="stylesheet" href="${ctx }/resources/css/comm.css" />
<link rel="stylesheet" href="${ctx }/resources/css/style_new.css" />
<link rel="stylesheet" href="${ctx }/resources/css/index.css" />
<link rel="stylesheet"
	href="${ctx }/resources/include/css/start/ui.all.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/resources/lzzUI/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx }/resources/lzzUI/css/grid.css" />
<link rel="stylesheet"
	href="${ctx }/resources/include/css/start/lzui.all.css" />
<link rel="stylesheet"
	href="${ctx }/resources/include/css/start/lzui.czexam.css" />
<link rel="stylesheet"
	href="${ctx }/resources/css/modal.css" />
<!-- 会影响其他页面样式 -->
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<script type="text/javascript"
	src="${ctx }/resources/js/jquery-1.10.1.min.js"></script>
<!-- 最新的 Bootstrap4 核心 JavaScript 文件 -->
<script src="${ctx}/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/resources/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/jszip.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/xlsx.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/readExcel.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/json2.js"></script>
<script>
	var itv;
	var len = 60000 * 3;
	$(function() {

		itv = setInterval(ajaxGetDb, len);

		setInterval(hb, len);
	});

	//保持session， 心跳
	function hb() {
		$.get('${ctx}/web/oa/user/hb?t=' + Math.random(), function(data) {

			//layer.open({title: "提醒", content:data);
			if (data !== 'ok') {
				//window.location.href
			}
		});
	}

	function ajaxGetDb() {
		$.get('${ctx}/web/oa/daiban/has?t=' + Math.random(), function(data) {
			var d = eval(data);
			if (d.ret == '1') {
				clearInterval(itv);
				setInterval(funTitle, 1000);
				$.layer({
					shade : [ 0.7, '#000' ],
					fix : false,
					title : '您有待办事项急需处理',
					type : 2,
					border : [ 3 ],
					shadeClose : false,
					iframe : {
						src : '${ctx}/web/oa/daiban/mine'
					},
					area : [ '680px', '400px' ],
					close : function(index) {
						itv = setInterval(ajaxGetDb, len);
					}
				});
			}
		});
	}

	var str = "您有新的消息";
	var c = 0;
	var flag = true;

	function funTitle() {

		for (var i = 0; i < c % 6; i++) {
			str = '>' + str;
		}

		if (flag) {
			c = c + 1;
		} else {
			c = c - 1;
		}

		if (c == 5) {
			flag = false;
		}

		if (c == 0) {
			flag = true;
		}
		document.title = str;
		str = "您有新的消息";

	}
</script>
<style>
 .div_content 
   {
            position: fixed;
            top: 150px;
            left: 50%;
            width: 400px;
            margin-left:-240px;
            height: 100px;
            margin-top:-150px;
            padding: 0;
            border: 1px solid black;
            background-color: white;
            _position:absolute;
            z-index:1011;
            overflow: hidden;
            text-align: center;
}
.div_overlay
{
            position: fixed;
            top: 0%;
            left: 0%;
            width: 100%;
            height: 100%;
            background-color: black;
            z-index:1010;
            -moz-opacity: 0.8;
            opacity:0;
            filter: alpha(opacity=0);
}
</style>
</head>

<body
	style="background-color: #efe; min-width: 1245px; position: relative;">

	<div class="ui-widget-header"
		style="height: 56px; position: relative; min-width: 1245px;">
		<div
			style="font-size: 18px; font-weight: normal; line-height: 28px; width: 400px; float: left;">
			<img src="${ctx }/resources/include/img/login_title.png"
				style="margin-top: 4px;" />
		</div>

		<div style="float: left; height: 56px;">
			<ul
				class="lzui-cz-menu ui-tabs-nav ui-helper-reset ui-helper-clearfix"
				style="height: 56px;">
				<c:forEach items="${loginUserMenus}" var="cur">
					<li class=""
						style="height: 56px; border: none; line-height: 56px; cursor: pointer; float: left; margin-left: 1px;">
						<a href="${ctx }/${cur.url}" class="ui-tabs-anchor"
						id="${cur.name }"
						style="padding: 0px 8px; display: inline-block; height: 56px; line-height: 56px;">
							<img src="${ctx }/${cur.icon}" />${cur.name }
					</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>

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


 
        <div class="div_overlay" style="display: none;"></div>
        <div class="div_content" style="display: none;">
        	<div style="margin-top: 20px;" id="content"></div>
        	<div style="position:relative; margin-top: 20px; margin-right:30px; float: right;">
        		<button id="alertBtn" class="lzui-btn lzui-corner-all">确定</button>
        	</div>
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
			<td class="title" colspan="2"></td>	
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
				<select onchange="selectCompany(this);">
					<option value="-1">- 请选择 -</option>
					<c:forEach items="${companys}" var="company" varStatus="status">
						<option value="${company.id}">${company.company }</option>
					</c:forEach>
				</select>
				<input id="receiverCompany" type="hidden" name="receiverCompany">
				<input id="contract" type="hidden" name="contract" value="1234567890">
			</td>	
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:90px;">收货地址：</td>	
			<td style="width:260px;" colspan="3">
<!-- 				<textarea id="addr"  name="addr" readonly="readonly" -->
<!-- 									style="width: 50%; height: 80px; padding: 5px 10px;"> -->
				<input id="addr" type="text" name="addr" readonly="readonly" style="width:80%">
			</td>	
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:90px;">收货人：</td>	
			<td>
				<input id="receiver" type="text" name="receiver" readonly="readonly" >
			</td>	
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:90px;">联系电话：</td>	
			<td>
				<input id="tel" type="text" name="tel" readonly="readonly">
			</td>
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		添加发货产品</span>
	</div>
	<div id="parent">
	<table id="products" class="table1" ><tbody id="body">
		<tr>
			<td class="title" style="text-align:left; width:90px;">
				序号
			</td>
			<td class="title" style="text-align:left; width:90px;">
				产品类型
			</td>
			<td class="title" style="text-align:left; width:150px;">
				产品型号
			</td>
			<td class="title" style="text-align:left; width:90px;">
				数量
			</td>
			<td class="title" style="text-align:left; width:90px;">
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
			<td></td>
			<td style="width:90px;">
				<select id="productName" onchange="showProductModel(this);">
					<option value="-1">- 请选择 -</option>
					<c:forEach var="product" items="${products}">
						<option value="${product.key}">${product.key }</option>
					</c:forEach>
				</select>
			</td>
			<td>
				<select id="productModel" class="infospan" onchange="showUnit(this);" title="-">
					<option value="-1">- 请选择 -</option>
				</select>
			</td>
			<td>
				<input id="count" type="text">
			</td>
			<td><span id="unit"></span></td>
			<td>
				<input id="remark" type="text" placeholder="- 无 -">
				<input id="tempRemark" type="hidden" />
			</td>
			<td style=" width:60px; border-right:none; ">
				<a href="javascript:addcd();">确定</a>
			</td>
		</tr>
	</tbody></table>
	</div>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
	</div>
	
	</form>
	</div>
	
</div>

<div id="modal" class="modal">  
    <div class="modal-content">  
        <header class="modal-header">  
            <h4>发货申请确认</h4>  
            <span class="close">×</span>  
        </header>  
        <div class="modal-body">  
        </div>  
        <footer class="modal-footer">  
            <button id="cancel">取消</button>  
            <button id="sure">确定</button>  
        </footer>  
    </div>  
</div>  
<style>
.cll
{
	text-align:center;
	font-weight: bold;
}
</style>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<%-- <script src="${ctx}/resources/js/alert.js" type="text/javascript"></script> --%>
<script>  
	$(function(){
		$('#cancel').click(function(){
			modal.style.display = "none";
		});
		$('.close').click(function(){
			modal.style.display = "none";
		});
		$('#sure').click(function(){
			modal.style.display = "none";
			document.form1.submit();
		});
	});
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
		layer.open({title: "提醒", content:'请选择收货单位'});
		return;
	}
	if (!addr) {
		layer.open({title: "提醒", content:'请填写收货地址'});
		return;
	}
	if (!receiver) {
		layer.open({title: "提醒", content:'请填写收货人'});
		return;
	}
	if (!tel) {
		layer.open({title: "提醒", content:'请填写联系电话'});
		return;
	}
	// 判断是否添加了发货产品
	if (index == 1) {
		layer.open({title: "提醒", content:'请添加发货产品'});
		return;
	}
	var str = '<table class="table1">';
	str += '<tr><td class="cll" width="40px;">序号</td><td class="cll" width="80px;">产品类型</td><td class="cll" width="100px;">产品型号</td><td class="cll" width="50px;">数量</td><td class="cll" width="50px;">单位</td><td class="cll" width="300px;">备注</td></tr>';
			
	var data = $('#products').clone();
	var length = $(data).children().children().length;
	var trs = $(data).children().children();
	for (var i = 2; i < length; i++ ) {
		var item = $(trs[i]).children();
		str += '<tr><td>'+(i-1)+'</td><td>' + item.eq(1).text()+
		'</td><td>'+ item.eq(2).text() + 
		'</td><td>'+ item.eq(3).text() +
		'</td><td>'+ item.eq(4).text() +
		'</td><td><input value="'+item.eq(5).text()+'" readonly size="50" />' +
		'</td></tr>'
	}
	str += '</table>';
	layer.open({
		title : '信息确认',
		content : str,
		area : ['800px'],
		btn : ['确认','取消']
		,yes:function(){
			document.form1.submit();
		}
		,cancel:function(){
			
		}
	});
	
//	$('#modal').show();
}

$(function(){
	$('a#OA申请').addClass('ui-tabs-current');	
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
	var remarkTemp = $('#tempRemark').val();
	
	var name = $.trim(nameSpan.val());
	if(name == '-1'){
		layer.open({title: "提醒", content:'请选择产品名称'});
		return;
	}
	var model = $.trim(modelSpan.val());
	if(model == '-1'){
		layer.open({title: "提醒", content:'请选择规格型号'});
		return;
	}
	var num = $.trim(countSpan.val());
	if (!num) {
		layer.open({title: "提醒", content:'请输入数量'});
		return;
	}
	var regu = /^[1-9]\d*$/;
	if (!regu.test(num)) {
		layer.open({title: "提醒", content:'请输入正确数量'});
		return;
	}
	
	var remark = $.trim(remarkSpan.val());
	if (!remark) {
		remark = '- 无 -';
	}
	
	count++;
	var html = 
		'<tr id="cd'+count+'">'+
			'<td></td>' +
			'<td style="">' + name + '<input type="hidden" value="'+name+'" name="names"/></td>' +
			'<td style=""><span class="infospan" style="cursor:pointer;" title="' + remarkTemp + '">' + modelSpan.find("option:selected").text() + '<input type="hidden" value="'+model+'" name="ids"/></span>' + '<input type="hidden" value="'+modelSpan.find("option:selected").text()+'" name="models"/></td>' +
			'<td style="">' + num + '<input type="hidden" value="'+num+'" name="nums"/></td>' +
			'<td style="">' + 'PCS' + '<input type="hidden" value="PCS" name="units"/></td>'+
			'<td style="">' + remark + '<input type="hidden" value="'+remark+'" name="remarks"/></td>'+
			'<td style=" border-right:none; "><a href="javascript:delcd('+count+');">删除</a></td>'+
		'</tr>';

	$('#addCdTr').after(html);
	index++;
	var len = $('#body').children().length;
	if (len - 2 > 0) {
		for (var i = 2; i < len; i++) {
			$('#body').children('tr').eq(i).children().eq(0).text(i - 1);
		}
	}
}

function delcd(tid){
	$('#cd'+tid).remove();
	index--;
	var len = $('#body').children().length;
	if (len - 2 > 0) {
		for (var i = 2; i < len; i++) {
			$('#body').children('tr').eq(i).children().eq(0).text(i - 1);
		}
	}
}

function showProductModel(obj) {
	var products = '${products}';
	products = JSON.parse(products.replace('\r\n',' '));
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

function showUnit(obj) {
	var products = '${products}';
	products = JSON.parse(products.replace('\r\n',' '));
	if (obj.value == '-1') {
		$('#unit').empty();
		return;
	}
	
	for (var key in products) {
		for (var i in products[key]) {
			if (obj.value == products[key][i].id) {
				$('#unit').text(products[key][i].unit);
				$('#tempRemark').val(products[key][i].remark);
			}
		}
	}
	
	var remarkTemp = $('#tempRemark').val();
	$('#productModel').attr('title', remarkTemp);
}

function selectCompany(obj) {
	// 清空
	if (obj.value == '-1') {
		$('#receiverCompany').val('');
		$('#addr').val('');
		$('#receiver').val('');
		$('#tel').val('');
	} else {
		<c:forEach items="${companys}" var="company">
			var id = '${company.id}';
			var receiverCompany = '${company.company}';
			var addr = '${company.addr}';
			var receiver = '${company.receiver}';
			var tel = '${company.tel}';
			if (id == obj.value) {
				$('#receiverCompany').val(receiverCompany);
				$('#addr').val(addr);
				$('#receiver').val(receiver);
				$('#tel').val(tel);
			}
		</c:forEach>
	}
	
}

</script>
</body>
</html>
