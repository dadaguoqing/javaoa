
<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/bootstrap/js/bootstrap-suggest.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">

	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:forEach items="${loginUserMenuMap['4']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
			</div>
		</c:forEach>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/materia_v2/myPurList">查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/materia_v2/savePurchase" id="form1"
			method="post" enctype="multipart/form-data" >
			<input type="hidden" value="${submitCode}" name="submitCode" /> <input
				type="hidden" value="${mp.empId}" name="qgId" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName}物料采购申请单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 10%;">申请人：</td>
							<td style="width: 90px;">${loginUser.name }</td>
							<td class="title">所属部门：</td>
							<td style="">${loginUserDept.name }</td>
							<td class="title" style="">申请时间：</td>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
								Date n = new Date();
								String now = sdf.format(n);
								pageContext.setAttribute("now", now);
								String tdy = now.substring(0, 11);
							%>
							<td style="border-right: none;">${now }</td>
						</tr>
						<tr>
							<td class="title" style="text-align: right; width: 10%;">
								采购依据：<span style="color: red;"></span>
							</td>
							<td><input name="requisitionCode" id="requisitionCode"></td>
							<td class="title" style="text-align: right; width: 10%;">
								物料查询：<span style="color: red;"></span>
							</td>
							<td><a href="${ctx}/web/oa/materia_v2/query"
								target="_blank">查询物料</a></td>
								<td class="title" style="width: 120px;">编号：</td>
							<td><input type="hidden" name="number" id="number" /><span
								id="numberStr"></span></td>
						</tr>
						<tr>
							<td class="title">上传申请文件：</td>
							<td ><input type="file" name="file" id="file" onchange="uploadFile();" /></td>
							<td class="title">文件模板：</td>
							<td align="left"><a href="${ctx}/excel/${url}">点击下载</a></td>
							<td class="title" style="text-align: right; width: 10%;">
								询价对比照片：<span style="color: red;"></span>
							</td>
							<td>
								<input type="file" name="file1" id="file1">
							</td>
						</tr>
						<tr>
							<td class="title" style="text-align: right; width: 10%;">
								采购事由：<span style="color: red;"></span>
							</td>
							<td colspan="7">
								<input id="reason" name="reason"  style="width: 800px" >
						  	</td>
						</tr>
					</tbody>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						物料详情
					</span>
				</div>
				<div id="table">
					<table class="table1">
						<tbody id="detail" class="mytbody">
							<tr class="" align="center">
								<td class="" style="width: 50px">序号</td>
								<td class="" style="width: 100px">物料编码</td>
								<td class="" style="width: 80px">品名分类</td>
								<td class="" style="width: 80px">品牌</td>
								<td class="" style="width: 80px">规格型号</td>
								<td class="" style="width: 80px">封装</td>
								<td class="" style="width: 40px">单位</td>
								<td class="" style="width: 80px">需求数</td>
								<td class="" style="width: 80px">需求日期</td>
								<td class="" style="width: 80px">计划日期</td>
								<td class="" style="width: 80px">采购数量</td>
								<td class="" style="width: 50px">单价</td>
								<td class="" style="width: 80px">其他费用</td>
								<td class="" style="width: 80px">供应商</td>
								<td class="" style="width: 40px">链接</td>
							</tr>
						</tbody>
					</table>
				</div>

				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="text-align: left;">预算费用总计： 
									<span id="countMoney1" style="color:red"> 0 </span> 元 <input
										type="hidden" name="countMoney" id="countMoney" value="0" />
							</td>
						</tr>
					</tbody>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						备注信息<a style="color: red"></a>
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td style="border-right: none;"><textarea name="bz" id="bz"
									style="width: 50%; height: 80px; padding: 5px 10px;"></textarea>
								<span style="color: red;">不超过150个字符</span></td>
						</tr>
					</tbody>
				</table>

			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
			</div>

		</form>
	</div>

</div>

</body>
</html>
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	var mode = '${mode}';
	var index = 0;
	var countMoney = 0;
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#物料采购申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getCode();

		var index1 = '${index}';
		var totalMoney = '${totalMoney}';
		if (index1) {
			index = index1;
		}
		if (totalMoney) {
			countMoney = totalMoney;
		}
		suggest();
	});
	
	function uploadFile() {
		$.ajax({
			url : 'uploadPurchaseFileAjax',
			type : 'POST',
			data : new FormData($('#form1')[0]),
			processData : false,
			contentType : false,
			success : function(result) {
				$('#file').val('');
				if(mode == 1) {
					result = JSON.parse(result);
				}
				if(result.success == -1) {
					layer.alert(result.msg, {icon : 2});
				} else {
					var tr = '';
					var totalMoney = 0;
					$.each(result.data,function(i, obj){
						tr += '<tr>';
						var y = i + 1;
						tr += '<td id="index" align="center">'+ y +'</td>';
						tr += '<td class="limitText" title="'+ obj.maCode +'"><input name="maCode" value="'+ obj.maCode +'" type="hidden">'+ obj.maCode +'</td>';
						tr += '<td class="limitText" title="'+ obj.classify +'"><input name="classify" value="'+ obj.classify +'" type="hidden">'+ obj.classify +'</td>';
						tr += '<td class="limitText" title="'+ obj.brand +'"><input name="brand" value="'+ obj.brand +'" type="hidden">'+ obj.brand +'</td>';
						tr += '<td class="limitText" title="'+ obj.spec +'"><input name="spec" value="'+ obj.spec +'" type="hidden">'+ obj.spec +'</td>';
						tr += '<td class="limitText" title="'+ obj.package1 +'"><input name="package1" value="'+ obj.package1 +'" type="hidden">'+ obj.package1 +'</td>';
						tr += '<td class="limitText" title="'+ obj.unit +'"><input name="unit" value="'+ obj.unit +'" type="hidden">'+ obj.unit +'</td>';
						tr += '<td class="limitText" title="'+ obj.needNum +'"><input name="needNum" value="'+ obj.needNum +'" type="hidden">'+ obj.needNum +'</td>';
						tr += '<td class="limitText" title="'+ obj.needDate +'"><input name="needDate" value="'+ obj.needDate +'" type="hidden">'+ obj.needDate +'</td>';
						tr += '<td class="limitText" title="'+ obj.useDate +'"><input name="useDate" value="'+ obj.useDate +'" type="hidden">'+ obj.useDate +'</td>';
						tr += '<td class="limitText" title="'+ obj.buynum +'"><input name="buynum" value="'+ obj.buynum +'" type="hidden">'+ obj.buynum +'</td>';
						tr += '<td class="limitText" title="'+ obj.price +'"><input name="price" value="'+ obj.price +'" type="hidden">'+ obj.price +'</td>';
						tr += '<td class="limitText" title="'+ obj.cost +'"><input name="cost" value="'+ obj.cost +'" type="hidden">'+ obj.cost +'</td>';
						tr += '<td class="limitText" title="'+ obj.supplier +'"><input name="supplier" value="'+ obj.supplier +'" type="hidden">'+ obj.supplier +'</td>';
						tr += '<td class="limitText" title="'+ obj.link +'"><input name="link" value="'+ obj.link +'" type="hidden">'+ obj.link +'</td>';
						tr += '</tr>';
						totalMoney += Number(obj.buynum) * Number(obj.price) + Number(obj.cost);
					});
					$('#detail').append(tr);
					$('#countMoney1').html(totalMoney.toFixed(2));
					$('#countMoney').val(totalMoney.toFixed(2));
				}
			}
		});
	}
	
	function del(obj) {
		$(obj).parent().parent().remove();
	} 	
	
	
	
	

	function suggest() {
		// 输入建议框
		var bsSuggest = $("#maCodeStr").bsSuggest({
			allowNoKeyword : false, //是否允许无关键字时请求数据
			getDataMethod : "url", //获取数据的方式，总是从 URL 获取
			url : "getMaCodeList?maCode=", /*优先从url ajax 请求 json 帮助数据，注意最后一个参数为关键字请求参数*/
			fnPreprocessKeyword : function(keyword) { // 中文乱码
				return encodeURI(keyword, "UTF-8");
			},
			processData : function(json) {// url 获取数据时，对数据的处理，作为 getData 的回调函数
				var i, len, data = {
					value : []
				};
				if (!json || json.length == 0) {
					return false;
				}
				len = json.length;
				for (i = 0; i < len; i++) {
					data.value.push({
						"" : json[i]
					});
				}
				return data;
			}
		}).on('onSetSelectValue', function(e) { // 下拉选中时触发
			var code = $('#maCodeStr').val();
			$.get("getMateriaInfo", {
				maCode : code
			}, function(result) {
				if (mode == 1) {
					result = eval("(" + result + ")");
				}
				$('#classfiyStr').val(result.classfiy);
				$('#brandStr').val(result.brand);
				$('#specStr').val(result.spec);
				$('#package1Str').val(result.package1);
				$('#unitStr').val(result.unit);
				$('#stockNum').html(result.stock);
			})
		});
	}

	function getCode() {
		$.get("getPurchaseCode", {
			str : 'CG',
			type : 1
		}, function(result) {
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$('#number').val(result);
			$('#numberStr').html(result);
		});
	}

	function countNum() {
		var needNum = $('#needNumStr').val();
		var stockNum = $('#stockNum').html();
		var buyNum = Number(needNum) - Number(stockNum);
		if (buyNum < 0) {
			$('#buyNumStr').html(0);
		} else {
			$('#buyNumStr').html(buyNum);
		}
	}

	function add() {
		// 整数位最多8位，小数6位
		var reg = /^[0-9]\d{0,7}(\.\d{1,6})?$/;
		var code = $('#maCodeStr').val();
		if (!code) {
			code = '暂无编码';
		}
		var classify = $('#classfiyStr').val();
		var brand = $('#brandStr').val();
		var spec = $('#specStr').val();
		if (!spec) {
			layer.msg('请填写规格型号');
			return;
		}
		var package1 = $('#package1Str').val();
		var unit = $('#unitStr').val();
		var needNum = $('#needNumStr').val();
		if (!reg.test(needNum)) {
			layer.msg('需求数量必须为数字');
			return;
		}
		needNum = parseFloat(needNum);
		var stock = $('#stockNum').html();
		var needDate = $('#needDateStr').val();
		if (!needDate) {
			layer.msg('需求日期不能为空');
			return;
		}
		var useDate = $('#useDateStr').val();
		if (!useDate) {
			layer.msg('计划日期不能为空');
			return;
		}
		var buyNum = $('#buyNumStr').html();
		buyNum = parseFloat(buyNum);
		var price = $('#priceStr').val();
		if (!reg.test(price)) {
			layer.msg('单价必须为数字');
			return;
		}
		price = parseFloat(price);
		var cost = $('#costStr').val();
		if (!reg.test(cost)) {
			layer.msg('其他费用必须为数字');
			return;
		}
		cost = parseFloat(cost);
		var supplier = $('#supplier').val();
		var link = $('#linkStr').val();
		var td = '<tr class="myInput>';
		td += '<td align="center" style="width:40px" id="index"></td>';
		td += '<td class="limitText" title="'+ code + '" style="width:80px"><input type="hidden"  name="maCode" value="'+code+'"/><span>'
				+ code + '</span></td>';
		td += '<td class="limitText" title="'+ classify + '" style="width:80px"><input type="hidden" name="classify" value="'+classify+'"/><span>'
				+ classify + '</span></td>';
		td += '<td class="limitText" title="'+ brand + '" style="width:80px"><input type="hidden" name="brand" value="'+brand+'"/><span>'
				+ brand + '</span></td>';
		td += '<td class="limitText" title="'+ spec + '" style="width:80px"><input type="hidden" name="spec" value="'+spec+'"/><span>'
				+ spec + '</span></td>';
		td += '<td class="limitText" title="'+ package1 + '" style="width:80px"><input type="hidden" name="package1" value="'+package1+'"/><span>'
				+ package1 + '</span></td>';
		td += '<td class="limitText" title="'+ unit + '" style="width:40px"><input type="hidden" name="unit" value="'+unit+'"/><span>'
				+ unit + '</span></td>';
		td += '<td class="limitText" title="'+ needNum + '" style="width:50px"><input type="hidden" name="needNum" value="'+needNum+'"/><span>'
				+ needNum + '</span></td>';
		td += '<td class="limitText" title="'+ stock + '" style="width:50px"><input type="hidden" name="stockNum" value="'+stock+'"/><span>'
				+ stock + '</span></td>';
		td += '<td class="limitText" title="'+ needDate + '" style="width:80px"><input type="hidden" name="needDate" value="'+needDate+'"/><span>'
				+ needDate + '</span></td>';
		// 判断日期时间
		var xq = needDate.replace(/\./g, "");
		var jh = useDate.replace(/\./g, "");
		if (Number(xq) < Number(jh)) {
			td += '<td class="limitText" title="'+ useDate + '" style="width:80px"><input type="hidden" name="useDate" value="'+useDate+'"/><span style="color:red">'
					+ useDate + '</span></td>';
		} else {
			td += '<td class="limitText" title="'+ useDate + '" style="width:80px"><input type="hidden" name="useDate" value="'+useDate+'"/><span>'
					+ useDate + '</span></td>';
		}
		td += '<td class="limitText" title="'+ buyNum + '" style="width:80px"><input type="hidden" name="buynum" value="'+buyNum+'"/><span>'
				+ buyNum + '</span></td>';
		td += '<td class="limitText" title="'+ price + '" style="width:80px"><input type="hidden" name="price" value="'+price+'"/><span>'
				+ price + '</span></td>';
		td += '<td class="limitText" title="'+ cost + '" style="width:50px"><input type="hidden" name="cost" value="'+cost+'"/><span>'
				+ cost + '</span></td>';
		td += '<td class="limitText" title="'+ supplier + '" style="width:40px"><input type="hidden" name="supplier" value="'+supplier+'"/><span>'
				+ supplier + '</span></td>';
		td += '<td class="limitText" title="'+ link + '" style="width:40px"><input type="hidden" name="link" value="'+link+'"/><span>'
				+ link + '</span></td>';
		td += '<td class="limitText" ><a href="javascript:void(0);" onclick="del(this)">删除</a></td>';
		td += '</tr>';
		$('#detail').append(td);
		countMoney = Number(countMoney)
				+ (Number(buyNum) * Number(price) + Number(cost));
		$('#countMoney1').html(countMoney.toFixed(2));
		$('#countMoney').val(countMoney.toFixed(2));
		$('#maCodeStr').val('');
		$('#needDateStr').val('');
		$('#useDateStr').val('');
		$('#buyNumStr').html('');
		$('#priceStr').val('');
		$('#classfiyStr').val('');
		$('#stockNum').html('');
		$('#brandStr').val('');
		$('#specStr').val('');
		$('#package1Str').val('');
		$('#unitStr').val('');
		$('#needNumStr').val('');
		$('#costStr').val('');
		$('#supplier').val('');
		$('#linkStr').val('');
	}

	

	function submitForm() {
		if(!$('#requisitionCode').val()) {
			layer.msg('请填写采购依据，没有写无');
			return;
		}
		if($('input[name="maCode"]').length == 0) {
			layer.msg('请添加物料');
			return;
		}
		var flag = false;
		$('input[name="cost"]').each(function(index, obj) {
			if (!obj.value) {
				flag = true;
			}
		});
		if (flag) {
			layer.msg('其他费用不能为空');
			return;
		}
		
		$('#form1').submit();
// 		var str = '<table class="table1">';
// 		str += '<tr class="">';
// 		str += '<td class="l-td-header " style="width: 50px; text-align: center;">序号</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">物料编码</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">品名分类</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">品牌</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">规格型号</td>';
// 		str += '<td class="l-td-header " style="text-align: center;width:50px">封装</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">单位</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">需求数</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">库存数</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">需求日期</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">计划日期</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">采购数量</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">单价</td>';
// 		str += '<td class="l-td-header " style="text-align: center;width:80px;">其他费用</td>';
// 		str += '<td class="l-td-header " style="text-align: center;width:60px;">供应商</td>';
// 		str += '<td class="l-td-header " style="text-align: center;">链接</td></tr>';
		
// 		var maCodes = $('input[name="maCode"]');
// 		var classifys = $('input[name="classify"]');
// 		var brands = $('input[name="brand"]');
// 		var specs = $('input[name="spec"]');
// 		var packages = $('input[name="package1"]');
// 		var units = $('input[name="unit"]');
// 		var stocks = $('input[name="stockNum"]');
// 		var needNums = $('input[name="needNum"]');
// 		var needDates = $('input[name="needDate"]');
// 		var useDates = $('input[name="useDate"]');
// 		var buyNums = $('input[name="buynum"]');
// 		var prices = $('input[name="price"]');
// 		var costs = $('input[name="cost"]');
// 		var suppliers = $('input[name="supplier"]');
// 		var links = $('input[name="link"]');
		
// 		for(var i = 0; i < maCodes.length; i++) {
// 			var y = i + 1;
// 			str += '<tr>';
// 			str += '<td align="center" style="width:40px" class="limitText" title="'+ y + '">' + y + '</td>';
// 			str += '<td class="limitText" title="'+ maCodes[i].value + '" style="width:80px">' + maCodes[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ classifys[i].value + '" style="width:80px">' + classifys[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ brands[i].value + '" style="width:80px">' + brands[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ specs[i].value + '" style="width:80px">' + specs[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ packages[i].value + '" style="width:80px">' + packages[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ units[i].value + '" style="width:40px">' + units[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ needNums[i].value + '" style="width:50px">' + needNums[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ stocks[i].value + '" style="width:50px">' + stocks[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ needDates[i].value + '" style="width:80px">' + needDates[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ useDates[i].value + '" style="width:80px">' + useDates[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ buyNums[i].value + '" style="width:80px">' + buyNums[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ prices[i].value + '" style="width:80px">' + prices[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ costs[i].value + '" style="width:50px">' + costs[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ suppliers[i].value + '" style="width:40px">' + suppliers[i].value + '</td>';
// 			str += '<td class="limitText" title="'+ links[i].value + '" style="width:40px">' + links[i].value + '</td>';
// 			str += '</tr>';
// 		}
// 		layer.open({
// 			title : '请确认申请信息',
// 			content : str + "</table>",
// 			btn : [ '确认', '取消' ],
// 			area : [ "1200px", "" ],
// 			yes : function() {
// 				document.form1.submit();
// 			}
// 		});

	}
</script>