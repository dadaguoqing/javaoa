<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<c:forEach items="${loginUserMenuMap['2']}" var="cur">
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
		<%@ include file="../../public/hello.jsp"%>
		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>
		<c:if test="${param.msg == 1}">
			<div class="lzui-error">操作成功</div>
		</c:if>
		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;"
				href="${ctx}/web/oa/leave/findAllXj?curpage=1">点击查看销假记录</a>
		</div>
		<form name="sForm" method="post"
			action="${ctx}/web/oa/leave/goCancelLeave">
			<input type="hidden" id="userid" name="empId" value="${empId }" /> <input
				type="hidden" id="empName" name="empName" value="${empName }" />
			<table id="baseInfoTable">
				<!-- 				<tr> -->
				<!-- 					<td class="right" style="width: 80px;">销假日期：</td> -->
				<%-- 					<td><input name="dayStr" id="dayStr" value="${dayStr}" --%>
				<!-- 						class="Wdate" style="width: 112px;" autocomplete="off" -->
				<!-- 						onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /></td> -->

				<!-- 								TODO -->
				<!-- 					<td><input type="button" value="点击选择人员" style="width: 100px;" -->
				<!-- 						id="crbtn" /></td> -->

				<%-- 					<td class="" style="width: 85px; color: red;" id="usernames">${empty empName ? '还未选择员工' : empName }</td> --%>
				<!-- 					<td> -->
				<!-- 						<div style="padding: 0px 10px;"> -->
				<!-- 							<div class="buttonDiv saveBtn" id="sBtn"> -->
				<!-- 								<span>搜索</span> <img -->
				<%-- 									src="${ctx}/resources/images/server_add.png" --%>
				<!-- 									style="width: 16px; height: 16px;" /> -->
				<!-- 							</div> -->
				<!-- 						</div> -->
				<!-- 					</td> -->
				<!-- 				</tr> -->
			</table>
		</form>

		<div class="gridContainer"
			style="width: 95%; margin-left: 0px; margin-top: 15px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="6">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								请假列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header l-td-left" style="width: 175px;">申请人</td>
						<td class="l-td-header" style="width: 175px;">开始时间</td>
						<td class="l-td-header" style="width: 175px;">结束时间</td>
						<td class="l-td-header" style="width: 40px;">类型</td>
						<td class="l-td-header l-td-left" style="width: 170px;">审批状态</td>
						<td class="l-td-header l-td-last-right" style="">操作</td>
					</tr>

					<c:forEach items="${list}" var="cur">
						<c:if test="${cur.status == 4}">
							<tr>
								<td class="l-td l-td-left">${cur.proposerName }</td>
								<td class="l-td">${cur.beginTime }</td>
								<td class="l-td l-td-left">${cur.endTime }</td>
								<td class="l-td l-td-left">${cur.type }</td>
								<td class="l-td l-td-left"><c:choose>
										<c:when test="${cur.status == -1}">
	    					审批不通过
	    					</c:when>
										<c:when test="${cur.status == 1}">
	    					主管审批中
	    					</c:when>
										<c:when test="${cur.status == 2}">
	    					总监审批中
	    					</c:when>
										<c:when test="${cur.status == 3}">
	    					总经理审批中
	    					</c:when>
										<c:when test="${cur.status == 4}">
	    					审批通过
	    					</c:when>
									</c:choose></td>
								<td class="l-td l-td-last-right" style="">
								<a class="lzui-btn lzui-corner-all"
									onclick="xj(${cur.id},'${cur.endTime}','${cur.beginTime}')">提前回来</a>
								<a class="lzui-btn lzui-corner-all"
									onclick="cancel(${cur.id})">取消请假</a>
								</td>
							</tr>
						</c:if>
					</c:forEach>

					<c:if test="${empty list}">
						<tr>
							<td class="l-td l-td-last-right" colspan="6" style="color: red;">
								对不起，目前还没有相关数据。</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
</div>

<div id="xj" style="display: none">
	<div class="gridContainer"
		style="width: 95%; margin-left: 0px; margin-top: 15px;">
		<div class="tableTitle" style="padding: 0px 10px;">
			<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
				原请假起始时间
			</span>
		</div>
		<table class="table1 lzzUIGrid">
			<tbody>
				<tr>
					<td class="title" style="width: 90px;">开始时间：</td>
					<td><span id="kaishi"></span></td>
					<td class="title" style="width: 90px;">结束时间：</td>
					<td><span id="jieshu"></span></td>
				</tr>
			</tbody>
		</table>
		<div class="tableTitle" style="padding: 0px 10px;">
			<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
				实际请假时间
			</span>
		</div>
		<table class="table1">
			<tbody>
				<tr>
					<td class="title" style="width: 90px;">开始时间：</td>
					<td style="width: 260px;">
					<input name="id" id="id" type="hidden" />
					<input name="type" id="type" type="hidden"  value="0" />
					 <%@ include file="../public/xjDate.jsp"%></td>
					<td class="title" style="border-left: none; width: 90px;">结束时间：</td>
					<td style="width: 260px;"><%@ include
							file="../public/xjDate2.jsp"%></td>
				</tr>
			</tbody>
		</table>
	</div>
</div>

<div id="qx" style="display: none">
	<form id="form2" name="form2" method="post" action="${ctx}/web/oa/leave/cancel1">
			<input id="id" name="id" type="hidden" />
			<input id="type" name="type" type="hidden" value="1"/>
			确认取消此请假？
	</form>
</div>

</body>
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script>
	function xj(id,et,bt) {
		var form = '<form id="form1" name="form1" method="post"'
			+ 'action="${ctx}/web/oa/leave/cancel1">';
		layer.open({
			title : false,
			btn : [ '确定', '取消' ],
			area : [ '800px', '' ],
			// success里面的参数顺序为layero、index
			success : function(layero,index) {
				$(layero).find('#kaishi').html(bt);
				$(layero).find('#jieshu').html(et);
				$(layero).find('#id').val(id);
				$(layero).find('#bt').val(bt.substring(0,11));
				$(layero).find('#et').val(et.substring(0,11));
			},
			// yes里面参数顺序必须为index、layero
			yes : function(index,layero) {
				if(!$(layero).find('#bt').val()) {
					layer.msg('开始时间日期不能为空',{type : 1});
					return;
				}
				if(!$(layero).find('#et').val()) {
					layer.msg('结束时间日期不能为空',{type : 1});
					return;
				}
				var beginDate = $(layero).find('#bt').val();
				var beginHour = $(layero).find('#beginHour').val();
				var beginMin = $(layero).find('#beginMin').val();
				var bt = beginDate + beginHour + beginMin;
				var endDate = $(layero).find('#et').val();
				var endHour = $(layero).find('#endHour').val();
				var endMin = $(layero).find('#endMin').val();
				var et = endDate + endHour + endMin;
				if(bt > et) {
					layer.msg('起始时间不合法',{type : 1});
					return;
				}
				$(layero).find('#form1').submit();
			},
			content : form + $('#xj').html() + '</form>'
		});
	}
	
	function cancel(id) {
		layer.open({
			title : '取消请假',
			icon : 6,
			content : $('#qx').html(),
			btn : ['确定','取消'],
			success : function(layero,index) {
				$(layero).find('#id').val(id);
			},
			yes : function(index,layero) {
				$(layero).find('#form2').submit();
			}
		});
	}

	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#请假注销').addClass('cur');
		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});
	});

	function setUNames(name) {
		$('#empName').val(name);
		$('#usernames').html(name);
	}

	function setUids(id) {
		$('#userid').val(id);
	}
</script>
</html>