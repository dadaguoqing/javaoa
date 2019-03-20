<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet"
	href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<style>
.shortInput {
	width: 40px;
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
			<div class="lzui-error">${msg}</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;"
				href="${ctx}/web/oa/materia_v2/myInwareList">查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/materia_v2/inware4" id="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<div style="border: 1px solid #2191C0;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }物料入库申请单</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="width: 150px">申请人：</td>
							<td>${loginUser.name }</td>
							<td class="title" style="width: 150px">所属部门：</td>
							<td>${loginUserDept.name }</td>
							<td class="title" style="width: 150px">申请时间：</td>
							<td style="border-right: none;">
								<fmt:formatDate value="${now}" pattern="yyyy年MM月dd日 HH时mm分"  />
							</td>
							<td class="title">编号：</td>
							<td><input type="hidden" name="code" id="code" /><span
								id="codeStr"></span></td>
						</tr>
						<tr>
							<td class="title">物料类型：</td>
							<td><select id="wl" name="wl">
									<option value="研发物料">研发物料</option>
									<option value="生产物料">生产物料</option>
							</select></td>
							<td class="title">质检员：</td>
							<td>
								<input id="jyr" name="jyr" type="hidden" value="${user.id}" />${user.name}
								<input type="hidden" value="1" name="type">
							</td>
							<td class="title">上传申请文件：</td>
							<td ><input type="file" name="file" id="file" onchange="uploadFile();" /></td>
							<td class="title">文件模板：</td>
							<td align="center"><a href="${ctx}/excel/${url}">点击下载</a></td>
						</tr>
					</tbody>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						物料详情
					</span>
				</div>
				<table class="table1" id='cdTable'>
					<tbody>
						<tr>
							<td class="title" style="text-align: center;width: 40px;">序号</td>
							<td class="title" style="text-align: center;width: 120px;">
								物料编码</td>
							<td class="title" style="text-align: center;width: 120px;">品名分类</td>
							<td class="title" style="text-align: center;width: 120px;">品牌</td>
							<td class="title" style="text-align: center;width: 120px;">规格型号</td>
							<td class="title" style="text-align: center;width: 120px;">封装</td>
							<td class="title" style="text-align: center;width: 50px;">单位</td>
							<td class="title" style="text-align: center;width: 120px;">供应商</td>
							<td class="title" style="text-align: center;width: 100px;">单价（元）</td>
							<td class="title" style="text-align: center;width: 140px;">其他费用（元）</td>
							<td class="title" style="text-align: center;width: 80px;">采购数量</td>
						</tr>
					</tbody>
					<tbody id="data">
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
							<td style="border-right: none;"><textarea name="bz" id="bz"
									style="width: 50%; height: 80px; padding: 5px 10px;"></textarea>
								<span style="color: red;">不超过150个字符</span></td>
						</tr>
					</tbody>
				</table>
			</div>
			<span style="color: red;">*</span>号标注的为必填项。
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
		$('#物料入库申请').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getCode();
	});

	function getCode() {
		$.get("getPurchaseCode", {
			str : 'RK',
			type : 3
		}, function(result) {
			if (mode == 1) {
				result = JSON.parse(result);
			}
			$('#code').val(result);
			$('#codeStr').html(result);
		});
	}
	
	function uploadFile() {
			$('#data').empty();
			$.ajax({
				url : 'uploadInboundFileAjax',
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
						layer.alert(result.msg, {
							icon : 2
						});
					} else {
						var tr = '';
						$.each(result.data,function(i, obj){
							tr += '<tr>';
							var y = i + 1;
							tr += '<td style="text-align:center;">'+ y +'</td>';
							tr += '<td class="limitText" title="'+ obj.materiaCode +'"><input name="materiaCode" value="'+ obj.materiaCode +'" type="hidden">'+ obj.materiaCode +'</td>';
							tr += '<td class="limitText" title="'+ obj.classfiy +'"><input name="classfiy" value="'+ obj.classfiy +'" type="hidden">'+ obj.classfiy +'</td>';
							tr += '<td class="limitText" title="'+ obj.brand +'"><input name="brand" value="'+ obj.brand +'" type="hidden">'+ obj.brand +'</td>';
							tr += '<td class="limitText" title="'+ obj.spec +'"><input name="spec" value="'+ obj.spec +'" type="hidden">'+ obj.spec +'</td>';
							tr += '<td class="limitText" title="'+ obj.package1 +'"><input name="package1" value="'+ obj.package1 +'" type="hidden">'+ obj.package1 +'</td>';
							tr += '<td class="limitText" title="'+ obj.unit +'"><input name="unit" value="'+ obj.unit +'" type="hidden">'+ obj.unit +'</td>';
							tr += '<td class="limitText" title="'+ obj.supplier +'"><input name="supplier" value="'+ obj.supplier +'" type="hidden">'+ obj.supplier +'</td>';
							tr += '<td class="limitText" title="'+ obj.price +'"><input name="price" value="'+ obj.price +'" type="hidden">'+ obj.price +'</td>';
							tr += '<td class="limitText" title="'+ obj.cost +'"><input name="cost" value="'+ obj.cost +'" type="hidden">'+ obj.cost +'</td>';
							tr += '<td class="limitText" title="'+ obj.num +'"><input name="num" value="'+ obj.num +'" type="hidden">'+ obj.num +'</td>';
							tr += '</tr>';
						});
						$('#data').append(tr);
					}
				}
			});
		}
	function submitForm() {
		if($('input[name="materiaCode"]').length == 0) {
			layer.alert('请先上传文件！');
			return;
		}
		document.form1.submit();
	}
</script>