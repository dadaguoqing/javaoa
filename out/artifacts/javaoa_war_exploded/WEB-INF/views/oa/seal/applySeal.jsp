<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/json2.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
body{
	font-size : 13px;
}
.shortInput {
	width: 40px;
}

.data td {
	padding: 2px;
}

.addData input {
	outline: none;
	border: 0px;
	background-color: rgb(238, 255, 238);
}

.limitText {
	overflow: hidden; /*自动隐藏文字*/
	text-overflow: ellipsis; /*文字隐藏后添加省略号*/
	white-space: nowrap; /*强制不换行*/
	/* 	width: 20em; /*不允许出现半汉字截断*/
	max-width: 50px;
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
		<c:if test="${param.msg1 == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg}</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/seal/sealList">查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/seal/saveSealApply"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" /> <input
				type="hidden" id="number1" name="number" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }用章申请单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 100px">申请人：</td>
							<td>${loginUser.name }</td>
							<td class="title" style="width: 100px">所属部门：</td>
							<td>${loginUserDept.name }</td>
							<td class="title">编号：</td>
							<td id="numberStr"></td>
						</tr>
						<tr>
							<td class="title">用章类型：</td>
							<td><select id="type" name="sealType" onchange="showDate()">
							</select></td>
							<td class="title">申请时间：</td>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
								Date n = new Date();
								String now = sdf.format(n);
								pageContext.setAttribute("now", now);
								String tdy = now.substring(0, 11);
							%>
							<td style="border-right: none;" colspan="3">${now}</td>
						</tr>
						<tr>
							<td class="title">用章事由：</td>
							<td colspan="10"><select id="yzsy" name="content"></select>
							</td>
						</tr>
						<tr>
							<td class="title" id="prtTitle" style="display: none">外带人：</td>
							<td style="display: none" id="ptrTd"><select id="wdr"
								name="wdrStr">
									<option value="-1">==请选择外带人==</option>
							</select></td>
						</tr>

						<tr id="nbyz" style="display: none">
							<td class="title">用章日期：</td>
							<td><select name="startDate">
									<c:forEach items="${dates}" var="date">
										<option value="${date}">${date}</option>
									</c:forEach>
							</select></td>
							<td class="title">用章时间：</td>
							<td colspan="10">${sd.weekday}<input type="hidden"
								value="${sd.weekday}" name="endDate" />
							</td>
						</tr>
						<tr id="wdyz" style="display: none">
							<td class="title">用章日期：</td>
							<td><select name="startDate2" id="startDate2">
									<option value="-1">==请选择用章日期==</option>
									<c:forEach items="${dates2}" var="date">
										<option value="${date}">${date}</option>
									</c:forEach>
							</select></td>
							<td class="title">用章时间：</td>
							<td colspan="10"><jsp:include
									page="../public/startForSeal.jsp" /> -- <jsp:include
									page="../public/endForSeal.jsp" /></td>
						</tr>


					</tbody>
				</table>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						用章详情
					</span>
				</div>
				<table class="table1" id='cdTable'>
					<tbody id="data">
						<tr>
							<td class="title" style="text-align: center; width: 50px;">序号</td>
							<td class="title" style="text-align: center; width: 150px;">
								用章文件名</td>
							<td class="title" style="text-align: center; width: 100px;">文件详情</td>
							<td class="title" style="text-align: center; width: 150px;">用章单位</td>
							<td class="title" style="text-align: center; width: 100px;">印章类型</td>
							<td class="title" style="text-align: center; width: 130px;">用印处</td>
							<td class="title" style="text-align: center; width: 80px;">文件份数</td>
							<td class="title" style="text-align: center; width: 80px;">用章数量</td>
							<td class="title" style="text-align: center; width: 50px;">操作</td>
						</tr>
						<tr class="data">
							<td align="center" style="width: 50px"></td>
							<td align="center" style="width: 150px"><input name=""
								id="fileName"></td>
							<td align="center"><input name="" id="fileDetail"></td>
							<td align="center"><select name="" id="sealCompany"
								onchange="showSealType(this)"><option value="0">=请选择用章单位=</option></select></td>
							<td align="center"><select name="" id="sealName"><option
										value="0">=请选择印章类型=</option></select></td>
							<td align="center"><select id="location"
								style="width: 130px;">
							</select></td>
							<td align="center"><input name="" id="fileNum"
								style="width: 80px"></td>
							<td align="center"><input name="" id="userNum"
								style="width: 80px"></td>
							<td align="center"><a onclick="add(this)"
								class="lzui-btn lzui-corner-all">+</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:void(0);" onclick="submitForm()"   class="lzui-btn lzui-corner-all">提交申请</a>
			</div>
		</form>
	</div>
</div>

</body>
</html>
<script type="text/javascript"
	src="${ctx}/resources/submitInfo/submitForm.js"></script>
<script>
	var mode = '${mode}';
	var data;
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#用章申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getCode();
		var access = '${access}';
		$.get("getSealType", function(result) {
			// 部署删除
			result = eval("(" + result + ")");
			tr = '<option value="0">=请选择用章类型=</option>';
			$.each(result, function(i, obj) {
				if (obj.id <= access) {
					tr += '<option value="'+obj.id+'">' + obj.sealType
							+ '</option>';
				}
			});
			$('#type').append(tr);
		});

		$.get("getSealCompany", function(result) {
			// 部署删除
			if (mode == 1) {
				data = JSON.parse(result);
			} else {
				data = result;
			}
			var arr = new Array();
			$.each(data, function(i, obj) {
				arr[i] = obj.sealCompany;
			});
			arr = unique(arr);
			tr = '';
			for ( var i in arr) {
				tr += '<option value="'+ arr[i] +'">' + arr[i] + '</option>';
			}
			$('#sealCompany').append(tr);
		});

		$.get("getYzsy", function(result) {
			var yzsy = '';
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$.each(result, function(i, obj) {
				yzsy += '<option value="'+obj.content+'">' + obj.content
						+ '</option>';
			});
			$('#yzsy').append(yzsy);
		});
		$.get("getYyc", function(result) {
			var yyc = '';
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$.each(result, function(i, obj) {
				yyc += '<option value="'+obj.content+'">' + obj.content
						+ '</option>';
			});
			$('#location').append(yyc);
		});

	});

	function getCode() {
		$.get("getPurchaseCode", {
			str : 'YZ',
			type : 7
		}, function(result) {
			// 部署删除
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$('#number1').val(result);
			$('#numberStr').html(result);
		});
	}

	// js数组去重
	function unique(arr) {
		var len = arr.length;
		var result = []
		for (var i = 0; i < len; i++) {
			var flag = true;
			for (var j = i; j < arr.length - 1; j++) {
				if (arr[i] == arr[j + 1]) {
					flag = false;
					break;
				}
			}
			if (flag) {
				result.push(arr[i])
			}
		}
		return result;
	}

	function showSealType(obj) {
		$('#sealName').empty();
		var sealCompany = $(obj).val();
		tr = '<option value="0">=请选择印章类型=</option>';
		$.each(data, function(i, obj) {
			if (obj.sealCompany == sealCompany) {
				tr += '<option value="'+ obj.sealName +'">' + obj.sealName
						+ '</option>';
			}
		});
		$('#sealName').append(tr);
	}

	function showDate() {
		var type = $('#type').val();
		if (type == 1) {
			$('#wdyz').hide();
			$('#nbyz').show();
			$('#ptrTd').hide();
			$('#prtTitle').hide();
		} else if (type == 3) {
			$('#ptrTd').show();
			$('#prtTitle').show();
			$('#wdyz').show();
			$('#nbyz').hide();
			$.get("getSealPtr", function(result) {
				// 部署删除
				if (mode == 1) {
				result = JSON.parse(result);
			}
				var tr = '';
				$.each(result, function(i, obj) {
					tr += '<option value="'+obj.name+'">' + obj.name
							+ '</option>';
				});
				$('#wdr').append(tr);
			});
		}
	}

	function add(obj) {
		var reg = /^[1-9]\d*$/;
		var fileName = $('#fileName').val();
		if (!fileName) {
			layer.msg('请填写用章文件名');
			return;
		}
		var fileDetail = $('#fileDetail').val();
		if (!fileDetail) {
			layer.msg('请填写文件详情');
			return;
		}
		var sealCompany = $('#sealCompany').val();
		if (sealCompany == 0) {
			layer.msg('请选择用章单位');
			return;
		}
		var sealName = $('#sealName').val();
		if (sealName == 0) {
			layer.msg('请选择印章类型');
			return;
		}
		var fileNum = $('#fileNum').val();
		if (!reg.test(fileNum)) {
			layer.msg('文件份数格式错误');
			return;
		}
		var userNum = $('#userNum').val();
		if (!reg.test(userNum)) {
			layer.msg('用章数量格式错误');
			return;
		}
		var location = $('#location').val();
		if (location == '-1') {
			layer.msg('请选择用印处');
			return;
		}
		tr = '<tr>';
		tr += '<td name="index" align="center"></td>';
		tr += '<td class="limitText" title="'+fileName+'">'
				+ fileName
				+ '<input name="fileName" value="'+fileName+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+fileDetail+'">'
				+ fileDetail
				+ '<input name="fileDetail" value="'+fileDetail+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+sealCompany+'">'
				+ sealCompany
				+ '<input name="sealCompany" value="'+sealCompany+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+sealName+'">'
				+ sealName
				+ '<input name="sealName" value="'+sealName+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+location+'">'
				+ location
				+ '<input name="location" value="'+location+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+sealName+'">'
				+ fileNum
				+ '<input name="fileNum" value="'+fileNum+'" type="hidden" /></td>';
		tr += '<td class="limitText" title="'+userNum+'">'
				+ userNum
				+ '<input name="userNum" value="'+userNum+'" type="hidden" /></td>';
		tr += '<td align="center"><a onclick="del(this)" class="lzui-btn lzui-corner-all">-</a></td>';
		tr += '</tr>';
		$('#data').append(tr);
		sortIndex();
	}

	function del(obj) {
		$(obj).parent().parent().remove();
		sortIndex();
	}

	function sortIndex() {
		$.each($('td[name="index"]'), function(i, obj) {
			$(obj).html(i + 1);
		});
	}
	
	function isIE(){
			if(!!window.ActiveXObject || "ActiveXObject" in window){
				return true;
			}else{
				return false;
			}
	}

	function submitForm() {
		var type = $('#type').val();
		if (type == 0) {
			layer.msg('请选择用章类型');
			return;
		}

		if ($('#type').val() == 3) {
			var date = $('#startDate2').val();
			if (!date) {
				layer.msg('请选择用章日期');
				return;
			}
			var time = $('#sHour').val() + $('#sMin').val();
			var time2 = $('#eHour').val() + $('#eMin').val();
			if (time >= time2) {
				layer.msg('用章时间不合法');
				return;
			}
			$('#endDate').val(time + '--' + time2);

			var ptr = $('#ptr').val();
			if (ptr == '-1') {
				layer.msg('请选择陪同人');
				return;
			}
		}

		if ($('input[name="fileName"]').length == 0) {
			layer.msg('请添加申请信息');
			return;
		}
		var str = '<table class="table1" style="background-color:rgb(238, 255, 238)"><tr align="center">';
		str += '<td class="l-td-header" style="width: 50px">序号</td>';
		str += '<td class="l-td-header" style="width: 150px">用章文件名</td>';
		str += '<td class="l-td-header" style="width: 150px">文件详情</td>';
		str += '<td class="l-td-header" style="width: 150px">用章单位</td>';
		str += '<td class="l-td-header" style="width: 100px">印章类型</td>';
		str += '<td class="l-td-header" style="text-align: center;">用印处</td>';
		str += '<td class="l-td-header" style="width: 80px">文件份数</td>';
		str += '<td class="l-td-header" style="width: 80px">用章数量</td></tr>';
		var data = $('#data').clone();
		var dataChild = data.children();
		
		// 删除最后一行
		$.each(dataChild,function(i, obj) {
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
		layer.open({
			title : '请确认用章信息',
			content : str + data.html() + '</table>',
			area : [ '900px', '' ],
			btn : [ '确定', '取消' ],
			yes : function() {
				document.form1.submit();
			}
		});
	}
</script>