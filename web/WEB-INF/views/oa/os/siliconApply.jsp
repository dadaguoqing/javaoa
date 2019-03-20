<%@ page language="java"
	import="java.util.*,java.text.*,com.hj.oa.bean.Role,com.hj.util.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>

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
		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc; width: 99%;">
			<div
				style="width: 93px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">芯片申请</div>
		</div>

		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/os/siliconApplyList">点击查看申请记录</a>
		</div>

		<form name="form1" action="${ctx }/web/oa/os/siliconApply"
			method="post">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<div
				style="border: 1px solid #2191C0; margin-top: 10px; width:99%;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }芯片申请单</span>
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
							<td style="width: 10%;">${loginUser.name }</td>
							<td class="title" style="width: 10%;">所属部门：</td>
							<td style="width: 10%;">${loginUserDept.name }</td>
							<td class="title" style="width: 10%;">申领时间：</td>
							<%
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH日mm分");
								Date n = new Date();
								String now = sdf.format(n);
								pageContext.setAttribute("now", now);
								String tdy = now.substring(0, 11);
							%>
							<td style="border-right: none; width: 20%;">${now }</td>
							<td class="title" style="width: 10%;">需求时间：</td>
							<td style="width: 20%;"><input name="useDayStr" id="bt"
								style="width: 115px" class="Wdate" autocomplete="off"
								onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /></td>
						</tr>
					</tbody>
				</table>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						申领芯片
					</span>
				</div>

				<table class="table1" id='cdTable'>
					<tbody>
						<tr>
							<td class="title" style="text-align: left; width: 10%;">
								芯片型号</td>
							<td class="title" style="text-align: left; width: 10%;">
								申请数量</td>
							<td class="title" style="text-align: left; width: 10%;">单位</td>
							<td class="title" style="text-align: left; width: 40%;">
								申请原因</td>
							<td class="title"
								style="text-align: left; border-right: none; width: 10%;">
								操作</td>
						</tr>

						<tr id="addCdTr">
							<td style=""><input type="hidden" id="id" /> <input
								type="text" id="brand" /></td>
							<td><input type="text" id="num" style="width: 80%" /></td>
							<td>颗(PCS)</td>
							<td><select id="reason" style="width: 50%">
									<option>请选择申请原因</option>
									<option>Demo板使用</option>
									<option>SLT板使用</option>
									<option>FPGA子卡使用</option>
									<option>芯片功能验证</option>
									<option>仓库备货</option>
									<option>测试平台调试</option>
									<option>芯片失效分析</option>
									<option>客户样片申请</option>
									<option>展示使用</option>
									<option>认证测试使用</option>
							</select></td>
							<td style="width: 60px; border-right: none;"><a
								href="javascript:addcd();">确定</a></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申领</a>
			</div>
		</form>
	</div>

</div>

</body>
</html>
	<script type="text/javascript"
	src="${ctx}/resources/submitInfo/submitForm.js"></script>
<script>
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#芯片申请').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	var count = 3;
	if('${alert}' == 1){
		alert('您不需要申请');
		location.href="${ctx }/web/oa/leave/afl";
	}

	function addcd() {
		var id = $('#id').val();
		var brand = $('#brand').val();
		var num = $('#num').val();
		var reason = $('#reason').val();

		if (!brand) {
			alert('请填写芯片型号');
			return;
		}

		if (!num) {
			alert('请填写数量');
			return;
		}

		if (reason == '请选择申请原因') {
			alert('请选择申请原因');
			return;
		}

		var reg = new RegExp("^[1-9][0-9]*$");
		if (!reg.test(num)) {
			alert('请正确填写数量（数量必须是正整数）');
			return;
		}

		count++;
		var html = '<tr id="cd'+count+'">' + '<td style="">' + brand
				+ '<input type="hidden" value="'+id+'" name="ids"/>'
				+ '<input type="hidden" value="'+brand+'" name="brands"/>'
				+ '<input type="hidden" value="'+num+'" name="nums"/>'
				+ '<input type="hidden" value="'+reason+'" name="reasons"/>'
				+ '</td>' + '<td style="">' + num + '</td>'
				+ '<td style="">颗(PCS)</td>' + '<td style="">' + reason
				+ '</td>' + '<td style=" border-right:none; ">'
				+ '<a href="javascript:delcd(' + count + ');">删除</a>' + '</td>'
				+ '</tr>';
		$('#addCdTr').after(html);
		$('#brand').val('');
		$('#num').val('');
		$('#reason').val('请选择申请原因');
		$('#id').val('');
	}

	function delcd(tid) {
		$('#cd' + tid).remove();
	}

	function submitForm() {
		var iptPrices = $('#cdTable input[name="ids"]');
		if (!iptPrices || iptPrices.length == 0) {
			alert('请至少添加一种芯片');
			return;
		}
		document.form1.submit();
	}
</script>