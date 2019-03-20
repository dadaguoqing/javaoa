<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script type="text/javascript"
	src="${ctx }/resources/bootstrap/js/bootstrap-suggest.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
.shortInput {
	width: 40px;
}

.limitText {
	overflow: hidden; /*自动隐藏文字*/
	text-overflow: ellipsis; /*文字隐藏后添加省略号*/
	white-space: nowrap; /*强制不换行*/
	max-width: 200px;
}
/* 去除input边框样式 */
.inputNone {
	border: 0px;
	background-color: rgb(238, 255, 238);
}

.td2 td {
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
			<a style="color: #C0392B;" href="${ctx}/web/oa/materia_v2/syList">查看申请记录</a>
		</div>
		<div style="border: 1px solid #2191C0;">
			<form name="form1" action="${ctx }/web/oa/materia_v2/saveSy"
				id="form1" method="post">
				<input type="hidden" value="${submitCode}" name="submitCode" />
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }物料损益申请单</span>
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
							<td class="title">所属部门：</td>
							<td>${loginUserDept.name }</td>
							<td class="title" style="width: 120px;">编号：</td>
							<td><input type="hidden" name="number" id="number"
								value="${str }" /><span id="numberStr"></span></td>
						</tr>
						<tr>
							<td class="title" style="width: 120px;">仓库：</td>
							<td style="width: 120px;"><select name="warehouseId"
								id="warehouse">
							</select></td>
							<td class="title" style="width: 120px;">申领时间：</td>
							<%
								Date n = new Date();
								pageContext.setAttribute("now", n);
							%>
							<td style="border-right: none;" colspan="3"><fmt:formatDate
									value="${now}" pattern="yyyy年MM月dd日 HH点mm分ss秒" /></td>
						</tr>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						申领物料信息
					</span>
				</div>
				<table class="table1 td2">
					<tbody id="data">
						<tr align="center">
							<td class="l-td-header" style="width: 50px">序号</td>
							<td class="l-td-header" style="width: 120px">物料编码</td>
							<td class="l-td-header" style="width: 240px">品名分类</td>
							<td class="l-td-header" style="width: 240px">规格型号</td>
							<td class="l-td-header" style="width: 240px">封装</td>
							<td class="l-td-header" style="width: 80px">数量</td>
							<td class="l-td-header" style="width: 50px">操作</td>
						</tr>
						<tr>
							<td></td>
							<td><div class="form-group">
									<div class="col-xs-12">
										<div class="input-group">
											<input id="maCode" style="width: 120px" />
											<div class="input-group-btn" style="width: 1px;">
												<ul class="dropdown-menu dropdown-menu-right" role="menu"></ul>
											</div>
										</div>
									</div>
								</div></td>
							<td class="limitText"><span id="classify"></span></td>
							<td class="limitText"><span id="spec"></span></td>
							<td class="limitText"><span id="package1"></span></td>
							<td><input id="num" style="width: 80px" /></td>
							<td align="center"><a href="javascript:add();"
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
			<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
		</div>
	</div>
</div>


</body>
</html>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/submitForm.js"></script>
<script>
	$(function() {
		var mode = '${mode}';
		$('#OA申请').addClass('ui-tabs-current');
		$('#物料损益申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		$.get("getPurchaseCode", {
			str : 'SY',
			type : 5
		}, function(result) {
			if (mode == 1) {
				result = eval("(" + result + ")");
			}
			$('#number').val(result);
			$('#numberStr').html(result);
		});

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

		// 输入建议框
		var bsSuggest = $("#maCode").bsSuggest({
			indexId : 4, //每组数据的第几个数据，作为input输入框的 data-id，设为 -1 且 idField 为空则不设置此值
			indexKey : 0, //data.value 的第几个数据，作为input输入框的内容
			allowNoKeyword : false, //是否允许无关键字时请求数据
			getDataMethod : "url", //获取数据的方式，总是从 URL 获取
			url : "getMaCodeList?maCode=", /*优先从url ajax 请求 json 帮助数据，注意最后一个参数为关键字请求参数*/
			fnPreprocessKeyword : function(keyword) {
				return encodeURI(keyword, "UTF-8");
			},
			listStyle : {
				'padding-top' : 0,
				'max-height' : '375px',
				'max-width' : '600px',
				'overflow' : 'auto',
				'width' : 'auto',
				'transition' : '0.3s',
				'-webkit-transition' : '0.3s',
				'-moz-transition' : '0.3s',
				'-o-transition' : '0.3s'
			},
			processData : function(json) {
				// url 获取数据时，对数据的处理，作为 getData 的回调函数              
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
		}).on('onSetSelectValue', function(e) { // 选择下拉选中的值触发
			var code = $('#maCode').val();
			$.get("getMateriaInfo", {
				maCode : code
			}, function(result) {
				if (mode == 1) {
					result = eval("(" + result + ")");
				}
				$('#classify').html(result.classfiy);
				$('#spec').html(result.spec);
				$('#package1').html(result.package1);
			})
		});
	});

	function add() {
		var code = $('#maCode').val();
		if (!code) {
			layer.msg("请输入物料编码");
			return;
		}
		var reg = /^(-)?[0-9]\d{0,7}(\.\d{1,6})?$/;
		var num = $('#num').val();
		if (!num) {
			layer.msg('请输入数量');
			return;
		}
		if (num == 0) {
			layer.msg('数量不能为0');
			return;
		}
		if (!reg.test(num)) {
			layer.msg("数量格式有误");
			return;
		}
		var tr = '<tr>';
		tr += '<td style="width: 50px" align="center"><span name="index"></span></td>';
		tr += '<td style="width: 120px"><input class="inputNone" value="'
				+ $('#maCode').val()
				+ '" name="code" style="width:120px;" readonly ></td>';
		tr += '<td style="width: 240px" class="limitText"><span>'
				+ $('#classify').html() + '</span></td>';
		tr += '<td style="width: 240px"class="limitText"><span>'
				+ $('#spec').html() + '</span></td>';
		tr += '<td style="width: 240px"class="limitText"><span>'
				+ $('#package1').html() + '</span></td>';
		tr += '<td style="width: 80px" class="limitText"><input class="inputNone" value="'
				+ $('#num').val()
				+ '" name="num" style="width: 80px" readonly></td>';
		tr += '<td style="width: 50px" align="center"><a onclick="del(this)" class="lzui-btn lzui-corner-all">-</a></td>';
		$('#data').append(tr);
		sortIndex();
	}

	function sortIndex() {
		$('span[name="index"]').each(function(i, obj) {
			$(this).text(i + 1);
		});
	}

	function del(obj) {
		$(obj).parent().parent().remove();
		sortIndex();
	}

	function submitForm() {
		var warehouse = $('#warehouse').val();
		if (warehouse == 0) {
			layer.msg('请选择仓库');
			return;
		}

		if ($('input[name="code"]').length == 0) {
			layer.msg('请添加物料');
			return;
		}
		if (!$('#bz').val()) {
			layer.msg('请填写备注');
			return;
		}

		var str = '<table class="table1" style="background-color:rgb(238, 255, 238)"><tr align="center">';
		str += '<td class="l-td-header" style="width: 50px">序号</td>';
		str += '<td class="l-td-header" style="width: 120px">物料编码</td>';
		str += '<td class="l-td-header" style="width: 240px">品名分类</td>';
		str += '<td class="l-td-header" style="width: 240px">规格型号</td>';
		str += '<td class="l-td-header" style="width: 240px">封装</td>';
		str += '<td class="l-td-header" style="width: 80px">数量</td></tr>';
		var data = $('#data').clone();
		var dataChild = data.children();
		// 删除最后一行
		$.each(dataChild,function(i, obj) {
			if (i < 2) {
				obj.remove();
			}
			obj.lastChild.remove();
		});
		layer.open({
			content : str + data.html() + '</table>',
			area : [ '900px', '' ],
			btn : [ '确定', '取消' ],
			yes : function() {
				document.form1.submit();
			}
		});

	}
</script>