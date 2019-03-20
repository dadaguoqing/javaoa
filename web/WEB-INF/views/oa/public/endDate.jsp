<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>

<input size="16" type="text"  readonly
	class="form_datetime" placeholder="结束时间" id="endDate" name="endDate">

<script type="text/javascript">
	$(".form_datetime").datetimepicker({
		format : 'yyyy-mm-dd hh:ii',
		language : 'zh-CN'
	});
</script>

