<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/bootstrap/js/bootstrap-suggest.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/pagination/jquery.pagination.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
#data td {
	padding: 1px 1px;
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
			<a style="color: #C0392B;"
				href="${ctx}/web/oa/materia_v2/myReventRecord">查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/materia_v2/reventApply"
			id="form1" method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }返料入库申请单</span>
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
						</tr>
						<tr>
							<td class="title" style="width: 120px;">上传申请文件：</td>
							<td title="上传申请文件,excel表格"><input type="file" name="file"
								id="file" onchange="uploadFile();"></td>
							<td class="title" style="width: 120px;">模板下载：</td>
							<td align="center"><a href="${ctx }/upload/${url}">下载模板</a></td>
							<td class="title">编号：</td>
							<td><span id="numberStr"></span><input type="hidden"
								name="number" id="number" /></td>
						</tr>
					</tbody>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						物料详情
					</span>
				</div>
				<table class="table1">
					<tbody id="data">
						<tr align="center">
							<td class="l-td-header" style="width: 50px">序号</td>
							<td class="l-td-header" style="width: 120px">物料编码</td>
							<td class="l-td-header" style="width: 300px">规格型号</td>
							<td class="l-td-header">品名分类</td>
							<td class="l-td-header">封装</td>
							<td class="l-td-header">品牌</td>
							<td class="l-td-header">返料数量</td>
							<td class="l-td-header">操作</td>
						</tr>
						<tr>
							<td></td>
							<td><div class="form-group">
									<div class="col-xs-9">
										<div class="input-group">
											<input id="maCode0" style="width: 150px" onclick="suggest(0)" />
											<div class="input-group-btn" style="width: 1px;">
												<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
											</div>
										</div>
									</div>
								</div></td>
							<td id="spec0"></td>
							<td style="width: 180px" id="classify0"></td>
							<td style="width: 180px" id="package10"></td>
							<td style="width: 180px" id="brand0"></td>
							<td style="width: 80px"><input style="width: 80px" id="num0"
								onblur="testNum(this)" /></td>
							<td align="center" style="width: 50px"><a onclick="add();"
								class="lzui-btn lzui-corner-all">+</a></td>
						</tr>
					</tbody>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						备注信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td style="border-right: none;"><textarea name="content"
									id="bz" style="width: 50%; height: 80px; padding: 5px 10px;"></textarea>
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
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/submitForm.js"></script>
<script>
	var mode = '${mode}';
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#返料入库申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getCode();
	});

	function getCode() {
		$.get("getPurchaseCode", {
			str : 'FL',
			type : 4
		}, function(result) {
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$('#number').val(result);
			$('#numberStr').html(result);
		});
	}

	function suggest(i) {
		// 输入建议框
		var bsSuggest = $("#maCode" + i).bsSuggest({
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
			var code = $('#maCode' + i).val();
			$.get("getMateriaInfo", {
				maCode : code,
			}, function(result) {
				if (mode == 1) {
					result = eval("(" + result + ")");
				}
				$('#classify' + i).html(result.classfiy);
				$('#spec' + i).html(result.spec);
				$('#package1' + i).html(result.package1);
				$('#brand' + i).html(result.brand);
				$('#stock' + i).html(result.stock);
			})
		});
	}

	function uploadFile() {
		$
				.ajax({
					url : 'uploadFileAjax',
					type : 'POST',
					data : new FormData($('#form1')[0]),
					processData : false,
					contentType : false,
					success : function(result) {
						if (mode == 1) {
							result = eval("(" + result + ")");
						}
						if (!result.success) {
							layer.alert(result.msg);
							$('#file').val('');
						} else {
							var tr = '<tr>';
							$
									.each(
											result.data,
											function(i, obj) {
												var j = i + 1;
												var sId = 'imp' + j;
												tr += '<td align="center" name="index"></td>';
												tr += '<td><div class="form-group">'
														+ '	<div class="col-xs-9">'
														+ '	<div class="input-group">'
														+ '		<input  id="maCode'
														+ j
														+ '" name="maCode" style="width:150px" onclick="suggest('
														+ j
														+ ')" value="'
														+ obj.materiaCode
														+ '"/>'
														+ '		<div class="input-group-btn" style="width: 1px;">'
														+ '			<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>'
														+ '		</div>'
														+ '	</div>'
														+ '	</div>'
														+ '</div></td>';
												tr += '<td id="spec'+j+'" name="spec">'
														+ obj.spec + '</td>';
												tr += '<td id="classify'+j+'">'
														+ obj.classfiy
														+ '<input type="hidden" name="classify" value="'+ obj.classify+ '"/></td>';
												tr += '<td id="package1'+j+'">'
														+ obj.package1
														+ '</td>';
												tr += '<td id="brand'+j+'">'
														+ obj.brand + '</td>';
												tr += '<td ><input style="width:80px"  name="num" value="'
														+ obj.num
														+ '" onblur="testNum(this)" /></td>';
												tr += '<td align="center"><a onclick="del(this);" class="lzui-btn lzui-corner-all">-</a></td>';
												tr += '</tr>';
											})
							$('#data').append(tr);
							sortIndex();
						}
					}
				});
	}

	function del(obj) {
		$(obj).parent().parent().remove();
		sortIndex();
	}

	function sortIndex() {
		$('td[name="index"]').each(function(i) {
			$(this).text(Number(i) + 1);
		});
	}

	function add() {
		if (!$('#spec0').html()) {
			layer.msg('请选择物料');
			return;
		}
		if (!$('#num0').val()) {
			layer.msg('请填写数量');
			return;
		}
		var code = $('#maCode0').val();
		var tr = '<tr><td style="width:50px" name="index" align="center"></td>';
		tr += '<td>' + code
				+ '<input type="hidden" name="maCode" value="'+code+'"/></td>';
		tr += '<td name="spec">' + $('#spec0').html() + '</td>';
		tr += '<td>' + $('#classify0').html() + '</td>';
		tr += '<td>' + $('#package10').html() + '</td>';
		tr += '<td>' + $('#brand0').html() + '</td>';
		tr += '<td>' + $('#num0').val()
				+ '<input type="hidden" name="num" value="' + $('#num0').val()
				+ '"/></td>';
		tr += '<td align="center"><a onclick="del(this);" class="lzui-btn lzui-corner-all">-</a></td>';
		$('#data').append(tr);
		sortIndex();
		$('#maCode0').val('');
		$('#spec0').html('');
		$('#classify0').html('');
		$('#brand0').html('');
		$('#package10').html('');
		$('#num0').val('');
	}

	function testNum(obj) {
		var num = $(obj).val();
		if (num) {
			var reg = /^[0-9]\d{0,7}(\.\d{1,6})?$/;
			if (!reg.test(num)) {
				layer.msg('请输入正确的数量');
				$(obj).val('');
			}
		}
	}

	function submitForm() {
		getCode();
		if ($('input[name="maCode"]').length == 0) {
			layer.msg('请添加要申领的物料');
			return;
		}

		var specs = $('td[name="spec"]');
		var flag = false;
		$.each(specs, function(i, obj) {
			y = i + 1;
			if ($(this).text() == '无匹配编码') {
				layer.msg('序号为' + y + '的物料不存在');
				flag = true;
			}
		});
		if (flag) {
			return;
		}
		var bz = $('#bz').val();
		if (!bz) {
			alert('请填写备注');
			return;
		}
		document.form1.submit();
	}
</script>