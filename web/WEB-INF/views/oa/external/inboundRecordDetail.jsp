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
			<div class="navTitle2" url="${ctx }/web/oa/external/inboundRecordList">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
			</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<div style="margin: 10px 5px 0 195px;" id="print">
		<%@ include file="../../public/hello.jsp"%>
		<a href="javascript:printHtml();" class="lzui-btn lzui-corner-all">打印页面</a>
		<div id="print">
		<form name="form1" action="${ctx }/web/oa/external/handInbound" id="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" value="${submitCode}" name="submitCode" />
			<input type="hidden" id="id" name="id" value="${ea.id}"/>
			<div style="border: 1px solid #2191C0;">
				<div id="content"></div>
					<div class="tableTitle" style="padding: 10px 20px;">
							<span> <img
								src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
								入库详情
							</span>
					</div>
					<table class="table1">
						<c:forEach items="${types}" var="type">
							<c:if test="${type == '1' }">
								<c:if test="${not empty ei1.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">PCB入库数量:</td>
										<td >${ei1.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei1.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '2' }">
								<c:if test="${not empty ei2.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">焊接入库数量:</td>
										<td >${ei2.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei2.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '3' }">
								<c:if test="${not empty ei3.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">钢网入库数量:</td>
										<td >${ei3.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei3.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '4' }">
								<c:if test="${not empty ei4.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">打白胶入库数量:</td>
										<td >${ei4.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei4.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '5' }">
								<c:if test="${not empty ei5.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">三防漆入库数量:</td>
										<td >${ei5.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei5.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '6' }">
								<c:if test="${not empty ei6.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">亚克力入库数量:</td>
										<td >${ei6.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei6.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							<c:if test="${type == '7' }">
								<c:if test="${not empty ei7.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">机箱入库数量:</td>
										<td >${ei7.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei7.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
							
							<c:if test="${type == '8' }">
								<c:if test="${not empty ei8.inboundNum}">
									<tr>
										<td class="title" style="width: 180px">线束入库数量:</td>
										<td >${ei8.inboundNum} PCS</td>
										<td class="title" style="width: 180px">入库时间:</td>
										<td >${ei8.inboundDate}</td>
									</tr>
								</c:if>
							</c:if>
						</c:forEach>
					</table>
			</div>
			<div class="" style="margin-top: 10px; padding-bottom: 10px;">
			</div>
		</form>
	</div>
</div>
</div>
</body>
</html>

<div id="data" style="display: none;">
	<table class="table1">
		<tr>
			<td class="title" style="width: 120px">入库数量:</td>
			<td><input name="inboundNum" id="inboundNum" style="width: 100px" type="number"> PCS</td>
		</tr>
		<tr>
			<td class="title" style="width: 120px">入库时间:</td>
			<td>
				<input name="inboundDate" id="inboundDate"  style="width:115px" 
					class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			</td>
		</tr>
	</table>
</div>

<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	var mode = '${mode}'
	$(function() {
		$('#OA处理').addClass('ui-tabs-current');
		$('#外协加工入库').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
		getContent();
	});
	
	
	function inbound(id) {
		layer.open({
			content : $('#data').html(),
			btn : ['确定', '取消'],
			area : ['400px', ''],
			yes : function(index, layero) {
				var inboundNum = $(layero).find('#inboundNum').val();
				var inboundDate = $(layero).find('#inboundDate').val();
				if(!inboundNum) {
					layer.msg('请填写入库数量！', {type : 1});
					return ;
				}
				var reg = /^\d+$/;
				if (!reg.test(inboundNum) || inboundNum < 1) {
					layer.msg('入库数量格式错误，请填写正整数！', {type : 1});
					return true;
				}
				if(!inboundDate) {
					layer.msg('请填写入库时间！', {type : 1});
					return ;
				}
				
				$.post('${ctx}/web/oa/external/handInbound',{
					inboundNum : inboundNum,
					inboundDate : inboundDate,
					type : id,
					applyCode : '${ea.applyCode}',
					id : '${ea.id}'
				}, function(result) {
					if(mode == 1) {
						result = JSON.parse(result);
					}
					if(result.success == 1) {
						location.reload();
					} else {
						layer.alert(result.msg, {
							icon : 2
						});
					}
				});
			}
		});
	}
	
	function getContent() {
		$.get("${ctx}/web/oa/external/getContent", {id : $('#id').val()}, function(result) {
			$('#content').html(result);
		})
	}
</script>