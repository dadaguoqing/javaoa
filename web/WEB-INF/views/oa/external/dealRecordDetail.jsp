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
			<div class="navTitle2" url="${ctx }/web/oa/external/dealRecordList">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

	<a href="javascript:printHtml();" class="lzui-btn lzui-corner-all">打印页面</a>
	<div id="print">
		<form name="form1" action="${ctx }/web/oa/external/handDealApprove" id="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<input type="hidden" id="id" name="id" value="${ea.id}"/>
			<div style="border: 1px solid #2191C0;" id="content">
			</div>
		</form>
		</div>
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