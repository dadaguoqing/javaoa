<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../../myheader.jsp"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<c:choose>
			<c:when test="${type == 1}">
				<c:forEach items="${loginUserMenuMap['4']}" var="cur">
					<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
						<img src="${ctx }/resources/images/item.png"
							style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
					</div>
				</c:forEach>
			</c:when>
			<c:when test="${type != 1}">
				<%@ include file="../../../public/lead2.jsp"%>
			</c:when>
		</c:choose>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../../public/hello.jsp"%>

		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>

		<c:if test="${type == 1}">
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;" href="${ctx}/web/oa/merchandise/apply/rejectInbound">返回</a>
			</div>
		</c:if>
		<c:if test="${type == 2}">
			<div class="lzui-tooltips"
				style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
				<a style="color: #C0392B;"
					href="${ctx}/web/oa/merchandise/apply/recordList/${applyType}/3">查看审批记录</a>
			</div>
		</c:if>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="7">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header " style="width: 10%;">申请人</td>
						<td class="l-td-header" style="width: 15%;">编号</td>
						<td class="l-td-header" style="width: 15%;">审批状态</td>
						<td class="l-td-header" style="width: 20%;">申请时间</td>
						<td class="l-td-header " style="">查看详情</td>
					</tr>
					<c:forEach items="${list}" var="cur">
						<tr>
							<td class="l-td">${AllUsers[cur.empId].name }</td>
							<td class="l-td">${cur.applyCode }</td>
							<td class="l-td"><c:if test="${cur.status == 1}">
									产品运营经理审批中
								</c:if>
								<c:if test="${cur.status == 2}">
									审批通过
								</c:if>
								<c:if test="${cur.status == 3}">
									入库成功
								</c:if>
								<c:if test="${cur.status > 3 || cur.status < -1}">
									状态异常
								</c:if>
								<c:if test="${cur.status == -1}">
									审批取消
								</c:if></td>
								<td class="l-td">${cur.applyDate }</td>
							<td class="l-td"><a
								href="${ctx }/web/oa/merchandise/apply/recordDetail/5/${type}?id=${cur.id}">查看详情</a></td>
						</tr>
					</c:forEach>
					<c:if test="${empty list}">
						<tr>
							<td class="l-td-last-right" colspan="7" style="color: red;">
								对不起，目前还没有相关数据。</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<div style="text-align:right">
	    	<div class="pager"></div>
		</div>
	</div>
</div>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/mypage/page.css">
<script type="text/javascript" src="${ctx}/resources/mypage/page.js"></script>
<script>
	var type = '${type}';
	
	onload = function() {
	    //用用回调
	    function go(p) {
	    	location.href = type +'?curPage=' + p;
// 	        $('.pager').pager(p, pageCount, go);
	    }
	    $('.pager').pager(Number('${curPage}'), Number('${pageCount}'), go);
	}
	
	$(function() {
		if (type != 1) {
			$('#OA审批').addClass('ui-tabs-current');
			$('#不良品入库审批').addClass('cur');
		} else {
			$('#OA申请').addClass('ui-tabs-current');
			$('#不良品入库申请').addClass('cur');
		}
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
</script>
</body>
</html>