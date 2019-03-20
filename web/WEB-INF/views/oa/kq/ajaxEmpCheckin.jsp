<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div style="margin: 10px 0px;">
	<c:if test="${empty ci}">
		<span style="color: red;">无该用户当日打卡记录。</span>
	</c:if>
	<c:if test="${not empty ci}">
		<span style="color: red;">员工当日打卡情况，上班打卡：
			${empty ci.checkin? '未打卡' : ci.checkin}，下班打卡：${empty ci.checkout ? '未打卡' : ci.checkout}</span>
		<script>
			$('#checkin').val('${ci.checkin }');
			$('#checkout').val('${ci.checkout }');
		</script>
	</c:if>
</div>
