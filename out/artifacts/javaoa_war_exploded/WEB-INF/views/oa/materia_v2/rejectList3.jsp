<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>
		<%@ include file="../../public/lead.jsp"%>
	</div>
	
	<style>
		hr {
		padding : 0 0;
		margin: 0 0;
		}
	</style>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>

		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
					<a style="color: #C0392B;"
						href="${ctx}/web/oa/materia_v2/rejectApprove">返回</a>
		</div>

		<div class="gridContainer"
			style="width: 99%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="8">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								申请记录列表</div>
						</td>
					</tr>
					<tr>
						<td class="l-td-header" style="width: 10%;">申请人</td>
						<td class="l-td-header" style="width: 20%;">编号</td>
						<td class="l-td-header" style="width: 20%;">状态</td>
						<td class="l-td-header" style="width: 20%;">申请时间</td>
						<td class="l-td-header" style="">查看详情</td>
					</tr>
					<c:if test="${empty ljchen }">
						<c:forEach items="${list}" var="cur">
							<tr>
								<td class="l-td">${AllUsers[cur.empId].name}</td>
								<td class="l-td">${cur.requisitionCode}</td>
								<td class="l-td">
									<c:if test="${cur.status == 1}">
										一级主管审批中
									</c:if> 
									<c:if test="${cur.status == 2}">
										二级主管审批中
									</c:if>
									<c:if test="${cur.status == 3}">
										财务主管审批中
									</c:if>
									<c:if test="${cur.status == 4}">
										总经理审批中
									</c:if>
									<c:if test="${cur.status == 5}">
										审批通过
									</c:if> 
									<c:if test="${cur.status == -1}">
										审批取消
									</c:if>
								</td>
								<td class="l-td">${cur.daystr}</td>
								<td class="l-td"><a
									href="${ctx }/web/oa/materia_v2/rejectDetail2?id=${cur.id}&type=2">查看详情</a></td>
							</tr>
						</c:forEach>
					</c:if>

				</tbody>
				<tbody id="data">
				</tbody>
				<c:if test="${empty list}">
					<tr>
						<td class="l-td-last-right" colspan="7" style="color: red;">
							对不起，目前还没有相关数据。</td>
					</tr>
				</c:if>
			</table>
		</div>
		<div style="text-align:right">
	    	<div class="pager"></div>
		</div>
	</div>
	</div>
	</body>
</html>
	<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/mypage/page.css">
<script type="text/javascript" src="${ctx}/resources/mypage/page.js"></script>
<script>
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');
		$('#物料报废审批').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});
	
	onload = function() {
	    //用用回调
	    function go(p) {
	    	location.href = '${ctx}/web/oa/materia_v2/rejectList3?curPage=' + p;
// 	        $('.pager').pager(p, pageCount, go);
	    }
	    $('.pager').pager(Number('${page.currentPage}'), Number('${page.pageCount}'), go);
	}

	
</script>
