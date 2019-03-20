<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;修正打卡记录
		</div>
		<div class="navTitle2" url="${ctx }/web/oa/user/wh">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">

		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<c:if test="${not empty msg}">
				<div class="lzui-error">${msg}</div>
			</c:if>

			<div id="dkSpan"></div>


			<div
				style="width: 97px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">修正打卡记录</div>

			<div id="	"
				style="color: red; font-weight: bold; padding: 15px 5px; display: none;">

			</div>
			<form name="form1" method="post" action="${ctx}/web/oa/kq/reset">
				<div class="lzui-tooltips"
					style="padding: 10px; color: #C0392B; font-weight: bold; font-size: 15px;">
					请按照下面的流程操作。</div>

				<div class="">


					<div class="lzui-headers">
						<div class="t1">
							<span>1</span>第一步、选择员工
						</div>

						<div class="lzui-table-wapper"
							style="margin-top: 7px; height: 365px; overflow: scroll;">
							<table class="lzui-table" style="width: 100%;">
								<tr>
									<th
										style="width: 60px; text-align: center; padding-left: 15px;"><input
										type="checkbox" id="qx" onclick="quanxuan()" /></th>
									<th style="width: 80px;">| 姓名</th>
									<th style="width: 120px;">| 登陆账号</th>
								</tr>

								<c:if test="${empty users}">
									<tr>
										<td style="text-align: center; color: #27AE60;" colspan="3">
											系统中目前还没有相关用户!</td>
									</tr>
								</c:if>

								<c:forEach items="${users}" var="cur">
									<tr class="lzui-td utd" uid="${cur.id }">
										<td style="text-align: center;"><input type="checkbox"
											class="ids uids" name="empIds" value="${cur.id }"
											id="u${cur.id}" /></td>
										<td>${cur.name }</td>
										<td>${cur.code }</td>
									</tr>
								</c:forEach>
							</table>
						</div>

					</div>

					<div class="lzui-headers">
						<div class="t2">
							<span>2</span>第二步、选择日期，以及打卡时间
						</div>

						<div class="lzui-table-wapper" style="margin-top: 7px;">

							<div style="margin-bottom: 10px;">
								<span
									style="font-weight: bold; line-height: 25px; display: block; margin-bottom: 10px; margin-top: 10px;">打卡日期<span
									style="color: red">*</span></span> <input name="dayStr" id="dayStr"
									class="Wdate" readonly="readonly" autocomplete="off"
									onFocus="WdatePicker({onpicked:dchange,dateFmt:'yyyy年MM月dd日'})" />
								<span
									style="font-weight: bold; line-height: 25px; display: block; margin-bottom: 10px; margin-top: 10px;">上班打卡<span
									style="color: red">*（格式：08:01）</span></span> <input name="checkin"
									id="checkin" /> <span
									style="font-weight: bold; line-height: 25px; display: block; margin-bottom: 10px; margin-top: 10px;">下班打卡<span
									style="color: red">*（格式：18:05）</span></span> <input name="checkout"
									id="checkout" />
							</div>

						</div>

					</div>

					<div class="lzui-headers">
						<div class="t3">
							<span>3</span>第三步、确认无误，点击提交。
						</div>

						<div class="lzui-table-wapper" style="margin-top: 7px;">
							<a href="javascript:submitRoles();" class="lzui-btn2"
								style="height: 30px; line-height: 30px; width: 100px; font-weight: bold; font-size: 15px;">修改记录</a>
						</div>

					</div>

					<div style="clear: both;"></div>

				</div>
			</form>
		</div>
	</div>

</div>

</body>
</html>
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	function quanxuan() {
		$('input[name="empIds"]').each(function(i, obj) {
			if ($('#qx').is(':checked')) {
				obj.checked = true;
			} else {
				obj.checked = false;
			}
		});
	}

	function submitRoles() {
		var ult = $('.uids:checked');
		var l1 = ult.length;
		if (l1 == 0) {
			alert("请选员工。");
			return;
		}

		document.form1.submit();
	}

	function dchange() {
		var ult = $('.uids:checked');
		var l1 = ult.length;
		if (l1 == 0) {
			//alert("请先选择一名员工。");
			return;
		}
		var empId = ult[0].value;
		var daystr = $('#dayStr').val();
				dayStr = encodeURIComponent(daystr);
				$.get('${ctx}/web/oa/kq/empCheckin?empId=' + empId + '&dayStr='
						+ dayStr + '&t=' + Math.random(), function(data) {
					$('#dkSpan').show();
					$('#dkSpan').html(data);
				});
// 		$.post('${ctx}/web/oa/kq/empCheckin', {
// 			empId : empId,
// 			dayStr : dayStr
// 		}, function(result) {
// 			$('#dkSpan').show();
// 			$('#dkSpan').html(data);
// 			// 			$('#checkin').val(result.checkin);
// 			// 			$('#checkout').val(result.checkout);
// 		});
	}

	$(function() {
		$('#首页').addClass('ui-tabs-current');

		//ie兼容
		$('.lzui-td input').click(function(evt) {
			evt.stopPropagation();
		});

		$('.utd').click(function(evt) {
			var item = $(this).find("input");
			var ic = item.prop('checked');
			if (ic) {
				item.prop('checked', false);
			} else {
				item.prop('checked', true);
			}
		});

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

	});
</script>