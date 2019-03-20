<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>

<script type="text/javascript"
	src="${ctx }/resources/bootstrap/js/bootstrap-suggest.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
.shortInput {
	width: 40px;
}

#data td {
	padding: 1px 1px;
}

</style>
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">

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
				href="${ctx}/web/oa/materia_v2/applyRecord">查看申领记录</a>
		</div>
		<div style="border: 1px solid #2191C0;">
			<form name="form1" action="${ctx }/web/oa/materia_v2/saveMateria"
				id="form1" method="post" enctype="multipart/form-data">
				<input type="hidden" value="${submitCode}" name="submitCode" />
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }物料申领单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 10%;">申领人：</td>
							<td>${loginUser.name }</td>
							<td class="title" style="width: 120px;">所属部门：</td>
							<td style="width: 90px;">${loginUserDept.name }</td>
							<td class="title" style="width: 120px;">申领时间：</td>
							<%
								Date n = new Date();
								pageContext.setAttribute("now", n);
							%>
							<td style="border-right: none;">
								<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日 HH点mm分ss秒" />
							</td>
						</tr>
						<tr>
							<td class="title" style="width: 120px;">申领仓库：</td>
							<td style="width: 120px;"><select name="warehouseId"
								id="warehouse">
							</select></td>
							<td class="title" style="width: 120px;">编号：</td>
							<td><input type="hidden" name="code" id="code"
								value="${str }" /><span id="codeStr"></span></td>
							<td class="title" style="width: 120px;">模板下载：</td>
							<td align="left"><a href="${ctx }/excel/${url}">下载模板</a></td>
						</tr>
						<tr>
							<td class="title" style="width: 120px;">上传申请文件：</td>
							<td colspan="3" title="上传申请文件,excel表格"><input type="file"
								name="file" id="file" onchange="uploadFile();"></td>
							<td class="title" style="width: 120px;">上传附件：</td>
							<td colspan="3" title="请上传小于10M的压缩文件"><input type="file"
								name="file1" id="file1"></td>
						</tr>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						申领物料信息
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
							<td class="l-td-header">库存</td>
							<td class="l-td-header">数量</td>
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
							<td style="width: 60px" id="stock0"></td>
							<td style="width: 80px"><input style="width: 80px" id="num0"
								onblur="testNum(this)" /></td>
							<td align="center" style="width: 50px"><a onclick="add();"
								class="lzui-btn lzui-corner-all">+</a></td>
						</tr>
					</tbody>
				</table>


				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						备注信息<a style="color: red">（必填）</a>
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
			</form>
		</div>
		<span style="color: red;">*</span>号标注的为必填项。
		<div class="" style="margin-top: 10px; margin-bottom: 20px;">
			<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申领</a>
		</div>
	</div>
</div>


</body>
</html>
<script type="text/javascript" src="${ctx}/resources/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/submitForm.js"></script>
<script>
	//开发模式 
	var mode = '${mode}';

	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#物料申领').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getCode();

		// 获取仓库
		$.get("getWarehouses", function(result) {
			var str = "<option value='0'>==请选择仓库==</option>";
			if (mode == 1) {
				result = eval("(" + result + ")");
			}
			$.each(result, function(i, obj) {
				str += '<option value="'+obj.id+'">' + obj.warehouse
						+ '</option>';
			});
			$('#warehouse').append(str);
		});

		// 结束
	});

	function getCode() {
		$.get("getPurchaseCode", {
			str : 'SL',
			type : 6
		}, function(result) {
			if (mode == 1) {
				result = eval("(" + result + ")");
			}
			$('#code').val(result);
			$('#codeStr').html(result);
		});
	}

	function suggest(i) {
		var whId = $('#warehouse').val();
		if (whId != 0) {
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
					whId : whId
				}, function(result) {
					if (mode == 1) {
						result = eval("(" + result + ")");
					}
					$('#classify' + i).html(result.classfiy);
					$('#spec' + i).html(result.spec);
					$('#package1' + i).html(result.package1);
					$('#stock' + i).html(result.stock);
				})
			});
		} else {
			layer.msg('请先选择仓库');
			$('#file').val('');
			return;
		}
	}

	function uploadFile() {
		var whId = $('#warehouse').val();
		if (whId != 0) {
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
													tr += '<td id="spec'+j+'"><input type="hidden" name="spec" value="'+ obj.spec+ '">'
															+ obj.spec
															+ '</td>';
													tr += '<td id="classify'+j+'">'
															+ obj.classfiy
															+ '<input type="hidden" name="classify" value="'+ obj.classify+ '"/></td>';
													tr += '<td id="package1'+j+'">'
															+ obj.package1
															+ '<input type="hidden" name="package1" value="'+ obj.package1+ '"/></td>';
													tr += '<td style="width:60px" id="stock'+j+'" name="stock">'
															+ obj.stock
															+ '</td>';
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
		} else {
			layer.msg('请先选择仓库');
			$('#file').val('');
			return;
		}
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
		var whId = $('#warehouse').val();
		if (whId == 0) {
			layer.msg('请先选择仓库');
			return;
		}
		if (!$('#stock0').html()) {
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
		tr += '<td>' + $('#spec0').html() + '<input type="hidden" name="spec" value="'+$('#spec0').html()+'"/></td>';
		tr += '<td>' + $('#classify0').html() + '<input type="hidden" name="classify" value="'+$('#classify0').html()+'"/></td>';
		tr += '<td>' + $('#package10').html() + '<input type="hidden" name="package1" value="'+$('#package10').html()+'"/></td>';
		tr += '<td name="stock">' + $('#stock0').html() + '</td>';
		tr += '<td>' + $('#num0').val()
				+ '<input type="hidden" name="num" value="' + $('#num0').val()
				+ '"/></td>';
		tr += '<td align="center"><a onclick="del(this);" class="lzui-btn lzui-corner-all">-</a></td>';
		$('#data').append(tr);
		sortIndex();
		$('#maCode0').val('');
		$('#spec0').html('');
		$('#classify0').html('');
		$('#package10').html('');
		$('#stock0').html('');
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
		var whId = $('#warehouse').val();
		if (whId == 0) {
			layer.msg('请先选择仓库');
			return;
		}

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

		var nums = $('input[name="num"]');
		var stocks = $('td[name="stock"]');
		for (var i = 0; i < nums.length; i++) {
			var y = i + 1;
			if (Number(nums[i].value) > Number(stocks[i].firstChild.nodeValue)) {
				layer.msg('序号为' + y + '的物料库存不足');
				return;
			}
		}
		var bz = $('#bz').val();
		if (!bz) {
			alert('请填写备注');
			return;
		}
		document.form1.submit();
	}
</script>