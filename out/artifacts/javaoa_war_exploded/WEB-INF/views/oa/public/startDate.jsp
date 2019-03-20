<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<link href="${ctx}/resources/css/bootstrap.min.css" rel="stylesheet" />
<link href="${ctx}/resources/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" />
<script type="text/javascript" src="${ctx}/resources/js/jquery.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery-1.10.1.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/bootstrap-datetimepicker.zh-CN.js"></script>

<input size="16" type="text"  readonly
	class="form_datetime" placeholder="开始时间" id="startDate" name="startDate">

<script type="text/javascript">
	$(".form_datetime").datetimepicker({
		format : 'yyyy-mm-dd hh:ii',
		language : 'zh-CN'
	});
</script>
