<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/bootstrap/js/bootstrap-suggest.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
.shortInput1 {
	width: 40px;
}

.shortInput2 {
	width: 350px;
}

.shortInput3 {
	width: 200px;
}

.shortInput4 {
	width: 80px;
}

.mytbody td {
	padding: 2px 2px;
	margin: 0px 0px;
}

.shortText {
	overflow: hidden; /*自动隐藏文字*/
	text-overflow: ellipsis; /*文字隐藏后添加省略号*/
	white-space: nowrap; /*强制不换行*/
	max-width: 100px;
}
</style>


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
			<a style="color: #C0392B;" href="${ctx}/web/oa/materia_v2/myPurList2">查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/materia_v2/purchase2"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }物料请购申请单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title">申请人：</td>
							<td>${loginUser.name }</td>
							<td class="title">所属部门：</td>
							<td>${loginUserDept.name }</td>
							<td class="title">申请时间：</td>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
								Date n = new Date();
								String now = sdf.format(n);
								pageContext.setAttribute("now", now);
								String tdy = now.substring(0, 11);
							%>
							<td style="border-right: none;">${now }</td>
							<td class="title">编号：</td>
							<td><span id="numberStr"></span><input type="hidden"
								name="number" id="number" /></td>
						</tr>
						<tr>
							<td class="title" style="text-align: right;">请购文件：<span
								style="color: red;"></span>
							</td>
							<td style="color: red;" colspan="3"><input type="file"
								id="file" name="file" onchange="xlsxUpload(this,2)" /></td>
							<td class="title" style="text-align: right;">附件：<span
								style="color: red;"></span>
							</td>
							<td style="color: red;"><input type="file" id="file1"
								name="file1" /></td>
							<td class="title" style="width: 120px;">模板下载：</td>
							<td style="color: red;" align="center"><a
								href="${ctx }/excel/${url}">点击下载</a></td>
						</tr>
						<tr>
							<td class="title">请购事由：</td>
							<td colspan="5"><input id="projectName" name="projectName"
								style="width: 100%" /></td>
							<td class="title">项目编号：</td>
							<td colspan="5"><input id="projectCode" name="projectCode"
								placeholder="请填写项目编号，没有填无" style="width: 100%" /></td>
						</tr>

					</tbody>
				</table>


				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						请购详情
					</span>
				</div>
				<table class="table1">
					<tbody id="mylist">
						<tr class="">
							<td class="l-td-header " style="width: 50px; text-align: center;">序号</td>
							<td class="l-td-header " style="text-align: center;">物料编码</td>
							<td class="l-td-header " style="text-align: center;">规格型号</td>
							<td class="l-td-header " style="text-align: center;">品名分类</td>
							<td class="l-td-header " style="text-align: center;">品牌</td>
							<td class="l-td-header " style="text-align: center;">封装</td>
							<td class="l-td-header " style="text-align: center;">单位</td>
							<td class="l-td-header " style="text-align: center;">其他参数要求</td>
							<td class="l-td-header " style="text-align: center;">需求数量</td>
							<td class="l-td-header " style="text-align: center;">需求日期</td>
							<td class="l-td-header " style="text-align: center;">预计单价</td>
							<td class="l-td-header " style="text-align: center;">操作</td>
						</tr>
						<tr class="mytbody">
							<td></td>
							<td><input id="codeStr" style="width: 100px" /></td>
							<td><input id="specStr" style="width: 100px" /></td>
							<td><input id="classfiyStr" style="width: 100px" /></td>
							<td><input id="brandStr" /></td>
							<td><input id="package1Str" style="width: 100px" /></td>
							<td><input id="unitStr" style="width: 40px" /></td>
							<td><input id="otherStr" style="width: 100px" /></td>
							<td><input id="needNumStr" style="width: 60px" /></td>
							<td><input id="useDateStr" style="width: 80px" class="Wdate"
								autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy.MM.dd'})" /></td>
							<td><input id="priceStr" style="width: 60px" /></td>
							<td><a onclick="addMa2()" class="lzui-btn lzui-corner-all"
								style="padding: 1px 5px;">+</a></td>
						</tr>
					<c:if test="${not empty list}">
						<c:forEach items="${list}" var="mpd" varStatus="i">
							<tr>
								<td></td>
								<td><input id="maCode" name="maCode" style="width: 100px"
									value="${mpd.maCode}" /></td>
								<td><input id="spec" name="spec" style="width: 100px"
									value="${mpd.spec}" /></td>
								<td><input id="classfiy" name="classify"
									style="width: 100px" value="${mpd.classify}" /></td>
								<td><input id="brand" name="brand" value="${mpd.brand}" /></td>
								<td><input id="package1" name="package1"
									style="width: 100px" value="${mpd.package1}" /></td>
								<td><input id="unit" name="unit" style="width: 40px"
									value="${mpd.unit}" /></td>
								<td><input id="other" name="other" style="width: 100px"
									value="${mpd.link}" /></td>
								<td><input id="needNum" name="needNum" style="width: 60px"
									value="${mpd.needNum}" /></td>
								<td><input id="needDate" name="needDate" style="width: 80px"
									class="Wdate" value="${mpd.useDate}" autocomplete="off"
									onFocus="WdatePicker({dateFmt:'yyyy.MM.dd'})" /></td>
								<td><input id="price" name="price" style="width: 60px"
									value="${mpd.price}" /></td>
								<td class="shortText"><a onclick="del(this)"
									class="lzui-btn lzui-corner-all" style="padding: 1px 5px;">-</a></td>
							</tr>
						</c:forEach>
					</c:if>
					</tbody>
					<tr>
						<td colspan="2" class="title">预算费用总计：</td>
						<c:if test="${not empty totalMoney}">
							<td colspan="10"><span style="color: red;" id="totalMoney">${totalMoney}</span>元</td>
						</c:if>
						<c:if test="${empty totalMoney}">
							<td colspan="10"><span style="color: red;" id="totalMoney">0</span>元</td>
						</c:if>
					</tr>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						备注信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td style="border-right: none;"><textarea name="bz" id="bz"
									style="width: 50%; height: 80px; padding: 5px 10px;"></textarea>
								<span style="color: red;">不超过150个字符（可不填）</span></td>
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
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#物料请购申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		// 获取请购编号
		getPurchaseCode();
	});

	function getPurchaseCode() {
		$.get("getPurchaseCode", {
			str : 'QG',
			type : 2
		}, function(result) {
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$('#number').val(result);
			$('#numberStr').html(result);
		});
	}

	function addMa2() {
		var code = $('#codeStr').val();
		if (!code) {
			code = '暂无编码';
		}
		var spec = $('#specStr').val();
		if (!spec) {
			layer.msg('规格型号不能为空');
			return;
		}
		var classfiy = $('#classfiyStr').val();
		if (!classfiy) {
			classfiy = '';
		}
		var brand = $('#brandStr').val();
		if (!brand) {
			brand = '';
		}
		var package1 = $('#package1Str').val();
		if (!package1) {
			package1 = '';
		}
		var unit = $('#unitStr').val();
		var other = $('#otherStr').val();
		var needNum = $('#needNumStr').val();
		if (!needNum) {
			layer.msg('需求数量不能为空');
			return;
		}
		var needDate = $('#useDateStr').val();
		if (!needDate) {
			layer.msg('需求日期不能为空');
			return;
		}
		var price = $('#priceStr').val();
		if (!price) {
			layer.msg('预计单价不能为空');
			return;
		}
		var reg = /^[0-9]\d{0,7}(\.\d{1,6})?$/;

		if (!reg.test(needNum)) {
			layer.msg('需求数量必须为数字');
			return;
		}

		needNum = parseFloat(needNum);
		if (needNum <= 0) {
			layer.msg('需求数量必须大于0');
			return;
		}

		if (!needDate) {
			layer.msg('需求日期不能为空');
			return;
		}
		if (!reg.test(price)) {
			layer.msg('单价必须为数字');
			return;
		}
		var td = '<tr class="myInput"><td align="center"  name="seq"></td>';
		td += '<td  class="shortText"  title="'+code+'" ><input type="hidden" name="maCode" value="'+code+'"/><span>'
				+ code + '</span></td>';
		td += '<td  class="shortText"   title="'+spec+'" ><input type="hidden" name="spec" value="'+spec+'"/><span>'
				+ spec + '</span></td>';
		td += '<td  class="shortText"   title="'+classfiy+'" ><input type="hidden" name="classify" value="'+classfiy+'"/><span>'
				+ classfiy + '</span></td>';
		td += '<td  class="shortText"  title="'+brand+'" ><input type="hidden" name="brand" value="'+brand+'"/><span>'
				+ brand + '</span></td>';
		td += '<td  class="shortText"  title="'+package1+'" ><input type="hidden" name="package1" value="'+package1+'"/><span>'
				+ package1 + '</span></td>';
		td += '<td  class="shortText"  title="'+unit+'" ><input type="hidden" name="unit" value="'+unit+'"/><span>'
				+ unit + '</span></td>';
		td += '<td  class="shortText"  title="'+other+'" ><input type="hidden" name="other" value="'+other+'"/><span>'
				+ other + '</span></td>';
		td += '<td  class="shortText"  title="'+needNum+'" ><input type="hidden" name="needNum" value="'+needNum+'" /><span>'
				+ needNum + '</span></td>';
		td += '<td  class="shortText"  title="'+needDate+'" ><input type="hidden" name="needDate" value="'+needDate+'"/><span>'
				+ needDate + '</span></td>';
		td += '<td  class="shortText" title="'+price+'" ><input type="hidden" name="price" value="'+price+'"  /><span>'
				+ price + '</span></td>';
		td += '<td  class="shortText"><a onclick="del(this)" class="lzui-btn lzui-corner-all" style="padding: 1px 5px;">-</a></td>';
		td += '</tr>';
		$('#mylist').append(td);
		$('#codeStr').val('');
		$('#specStr').val('');
		$('#classfiyStr').val('');
		$('#brandStr').val('');
		$('#package1Str').val('');
		$('#unitStr').val('');
		$('#otherStr').val('');
		$('#needNumStr').val('');
		$('#useDateStr').val('');
		$('#priceStr').val('');
		countTotalMoney();
		getSeq();
	}

	function del(obj) {
		$(obj).parent().parent().remove();
	}

	function getSeq() {
		var i = 0;
		$('td[name="seq"]').each(function(i) {
			$(this).text(++i);
		});
		countTotalMoney();
	}

	function countTotalMoney() {
		var totalMoney = 0;
		var nums = $('input[name="needNum"]');
		var prices = $('input[name="price"]');
		for (var i = 0; i < nums.length; i++) {
			num = nums[i].value;
			price = prices[i].value;
			console.log(num);
			console.log(price);
			if (num && price) {
				totalMoney += Number(num) * Number(price);
			} else {
				continue;
			}
		}
		$('#totalMoney').html(totalMoney);
	}

	function submitForm() {
		var projectCode = $('#projectCode').val();
		if (!projectCode) {
			layer.msg('项目编号不能为空');
			return;
		}

		var projectName = $('#projectName').val();
		if (!projectName) {
			layer.msg('请填写请购事由');
			return;
		}
		var file = $('#file').val();
		var codes = $('input[name="maCode"]');
		if (codes.length == 0 && !file) {
			layer.msg('请上传文件或添加物料');
			return;
		}
		var flag = false;
		$.each($("input[name='needNum']"), function(i, obj) {
			if (!obj.value) {
				layer.msg('需求数量不能为空');
				flag = true;
				return false;
			}
		});
		$.each($("input[name='needDate']"), function(i, obj) {
			if (!obj.value) {
				layer.msg('需求日期不能为空');
				flag = true;
				return false;
			}
		});
		$.each($("input[name='price']"), function(i, obj) {
			if (!obj.value) {
				layer.msg('预计单价不能为空');
				flag = true;
				return false;
			}
		});
		if (flag) {
			return;
		}
		var contentHtml;
		if ($('input[name="maCode"]').length != 0) {
			var str = '<table class="table1">';
			str += '<tr class="">';
			str += '<td class="l-td-header " style="width: 50px; text-align: center;">序号</td>';
			str += '<td class="l-td-header " style="text-align: center;">物料编码</td>';
			str += '<td class="l-td-header " style="text-align: center;">规格型号</td>';
			str += '<td class="l-td-header " style="text-align: center;">品名分类</td>';
			str += '<td class="l-td-header " style="text-align: center;">品牌</td>';
			str += '<td class="l-td-header " style="text-align: center;">封装</td>';
			str += '<td class="l-td-header " style="text-align: center;">单位</td>';
			str += '<td class="l-td-header " style="text-align: center;">其他参数要求</td>';
			str += '<td class="l-td-header " style="text-align: center;">需求数量</td>';
			str += '<td class="l-td-header " style="text-align: center;">需求日期</td>';
			str += '<td class="l-td-header " style="text-align: center;">预计单价</td></tr>';
			var data = $('#mylist').clone();
			var dataChild = data.children();
			// 删除最后一行
			$.each(dataChild, function(i, obj) {
				if (i < 2) {
					// IE不支持remove方法
					if(isIE()){
						obj.removeNode(true);
					}else{
						obj.remove();
					}
				}
				if(isIE()){
					obj.lastChild.removeNode(true);
				}else{
					obj.lastChild.remove();
				}
			});
			contentHtml = str + data.html() + "</table>";
			layer.open({
				content : contentHtml,
				area : [ '1200px', '' ],
				btn : [ '确定', '取消' ],
				yes : function() {
					document.form1.submit();
				}
			});
		} else {
			if ($('#file')) {
				document.form1.submit();
			}
		}

	}
</script>