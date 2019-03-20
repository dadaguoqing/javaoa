<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<link rel="stylesheet" href="${ctx}/resources/bootstrap/css/bootstrap.css" />
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;申请详情
		</div>
			<div class="navTitle2" url="${ctx }/web/oa/external/dealApproveList">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>
		<form name="form1" action="${ctx }/web/oa/external/handDealApprove" id="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<input type="hidden" id="id" name="id" value="${ea.id}"/>
			<div style="border: 1px solid #2191C0;">
				<div id="content"></div>
					<div class="tableTitle" style="padding: 10px 20px;">
							<span> <img
								src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								审批意见
							</span>
					</div>
						<table class="table1">
							<tbody>
								<tr>
									<td style="width: 20%;">审批意见： <select id="tg" name="sp"
										onchange="yjChanged()">
											<option value="通过" style="color: green;" selected="selected">通过</option>
											<option value="不通过" style="color: red;">不通过</option>
									</select>
									</td>

									<td style="border-right: none;">补充意见：<input name="opinion"
										id="opinion" style="width: 50%;" value="ok" /> <span
										style="color: red;">不超过50个字符</span>
									</td>
								</tr>
							</tbody>
						</table>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交审批结果</a>
			</div>
		</form>
	</div>
</div>

</body>
</html>
<script type="text/javascript">
	var mode = '${mode}';
	$(function() {
		$('#OA审批').addClass('ui-tabs-current');
		$('#外协加工处理审批').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getContent();
	});
	
	function getContent() {
		$.get("${ctx}/web/oa/external/getContent", {id : $('#id').val()}, function(result) {
			$('#content').html(result);
		})
	}
	
	function submitForm() {
		if(testStr('opinion', '补充意见')) {
			return;
		}
		$('#form1').submit();
	}
</script>