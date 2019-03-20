<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<style type="text/css">
/* pager */
.pager a, .pager .cur, .pager a span, .pager .cur span, .pager a.prv,
	.pager a.next, .pager a.prv:hover, .pager a.next:hover span {
	position: relative;
	z-index: 1;
	font-size: 12px;
}

.pager {
	font-family: Tahoma, Arial, "宋体";
	margin: 10px auto;
	text-align: left;
	font-weight: bold;
	color: #666;
}

.allcount {
	font-weight: normal;
	color: #888;
	clear: both;
	padding: 3px 0 0 0;
}

.pager a, .pager .cur {
	color: #666;
	display: inline-block;
	background: url() 0 -336px;
	height: 32px;
	padding: 0 0 0 13px;
	line-height: 31px;
	margin-right: -1px;
	background: #9AC6FF;
}

.pager a span, .pager .cur span {
	display: inline-block;
	background: url() right -336px;
	height: 32px;
 	padding: 0 13px 0 0; 
	background: #9AC6FF;
}

.pager .cur, .pager a:hover {
	background: url() 0 -378px;
	color: #fff;
	z-index: 12;
	text-decoration: none;
	background-color: #FFBE76;
}

.pager .cur span, .pager a:hover span {
	background: url() right -378px;
}

.pager a.prv span {
	background: url() 0 -252px;
}

.pager a.prv:hover span{
	background: url() 0 -294px;
}

.pager a.next span {
	background: url() right -252px;
}

.pager a.next:hover span {
	background: url() right -294px;
}

.pager a.last, .pager a.prv, .pager a.next {
	line-height: 31px;
}

.pager a.last, .pager a.prv, .pager a.next {
	top: -1px;
	top: -1px\0; +
	top: -1px;
	_top: -2px;
	font-family: '宋体'
}

#main .pager {
	margin-top: 30px;
}

.allcount span.red {
	color: #888;
}
</style>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<c:forEach items="${loginUserMenuMap['4']}" var="cur">
			<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
				<img src="${ctx }/resources/images/item.png"
					style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
			</div>
		</c:forEach>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>
	<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
	</c:if>
	<div style="margin: 10px 5px 0 195px; padding-bottom: 15px;">
		<div class="tableTitle" style="border-bottom: 1px dotted #aaaaaa; padding-bottom:5px;">
		<img src="${ctx}/resources/images/communication.gif">&nbsp;&nbsp;&nbsp;&nbsp;
		查询条件</div>
	<form name="sForm" action="${ctx}/web/oa/kq/mysq?curpage=1" method="post" >
	<table id="baseInfoTable">
		<tr>
			<td class="right" style="width:80px;">补签类型：</td>
			<td>
			<select name="style" id="style">
					<option value="0"  <c:if test="${style=='0' }">selected="selected"</c:if>>请选择补签类型</option>
					<option value="1" <c:if test="${style=='1' }">selected="selected"</c:if>>迟到打卡补签</option>
					<option value="2" <c:if test="${style=='2' }">selected="selected"</c:if>>忘记打卡补签</option>
					<option value="3" <c:if test="${style=='3' }">selected="selected"</c:if>>公事出差打卡补签</option>
					<option value="4" <c:if test="${style=='4' }">selected="selected"</c:if>>工牌丢失打卡补签</option>
					<option value="5" <c:if test="${style=='5' }">selected="selected"</c:if>>先研院授课打卡补签</option>
					<option value="6" <c:if test="${style=='6' }">selected="selected"</c:if>>公事外出打卡补签</option>
				</select>
			</td>
			<td>
				<div style="padding:0px 10px;">
					<div class="buttonDiv saveBtn" id="sBtn">
						<span>搜索</span>
						<img src="${ctx}/resources/images/server_add.png" style="width:16px;height:16px;"/>
					</div>
			</div>
			</td>	
			<td>
			开始时间：<input name="beginDate" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			结束时间：<input name="endDate" id="bt"  style="width:115px" class="Wdate" autocomplete="off" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})"/>
			
			</td>
			<td>
			<div class="" style="margin:10px 0 10px 0;">
			<a  id="smbtn" class="lzui-btn lzui-corner-all" style="padding:0px 20px;">导出补签记录</a>
				</div>
				</td>
		</tr>
	</table>
	</form>
		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			
			<a style="color: #C0392B;" href="${ctx}/web/oa/kq/sq">点击申请打卡补签</a>
			
		</div>

		<div class="gridContainer"
			style="width: 95%; margin-left: 0px; margin-top: 0px;">
			<table class="lzzUIGrid">
				<tbody>
					<tr>
						<td class="l-td-toolbar" colspan="6">
							<div class="l-float-left"
								style="font-weight: bold; font-size: 15px; margin-right: 20px;">
								我的打卡补签申请列表</div>
						</td>
					</tr>

					<tr>
						<td class="l-td-header l-td-left" style="width: 60px;">申请人</td>
						<td class="l-td-header" style="width: 170px;">申请时间</td>
						<td class="l-td-header" style="width: 170px;">考勤日期</td>
						<td class="l-td-header" style="width: 120px;">上班打卡</td>
						<td class="l-td-header" style="width: 120px;">下班打卡</td>
						<td class="l-td-header l-td-last-right" style="">审批状态</td>
					</tr>

					<c:forEach items="${list}" var="cur">
						<tr>
							<td class="l-td l-td-left">${cur.proposerName }</td>
							<td class="l-td">${cur.createTime }</td>
							<td class="l-td">${cur.dayStr }</td>
							<td class="l-td">${cur.checkin }</td>
							<td class="l-td">${cur.checkout}</td>
							<td class="l-td"><c:choose>
									<c:when test="${cur.status == -1}">
    					审批不通过
    					</c:when>
									<c:when test="${cur.status == 0}">
    					正在审批
    					</c:when>
									<c:when test="${cur.status == 1}">
    					审批通过
    					</c:when>

								</c:choose></td>
						</tr>
					</c:forEach>

					<c:if test="${empty list}">
						<tr>
							<td class="l-td-last-right" colspan="6" style="color: red;">
								对不起，目前还没有相关数据。</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<div class="pager">
				<a class="last " href="javascript:;" onclick="goPageAction(1);"><span>首页</span></a>
				<c:if test="${curpage<=1 }">
					<a class="prv " href="javascript:;"><span>上一页</span></a>
				</c:if>
				<c:if test="${curpage>1 }">
					<a class="prv " href="javascript:;"
						onclick="goPageAction(${curpage-1});"><span>上一页</span></a>
				</c:if>
				<c:forEach items="${arr }" varStatus="stat">
					<c:if test="${stat.count>=curpage-5 && stat.count<= curpage+5 }">
						<c:if test="${stat.count==curpage }">
							<a class="cur" href="javascript:;"><span>${curpage }</span></a>
						</c:if>
						<c:if test="${stat.count!=curpage }">
							<a href="javascript:;" onclick="goPageAction(${stat.count });"><span>${stat.count }</span></a>
						</c:if>
					</c:if>
				</c:forEach>
				<c:if test="${curpage>=totalPage }">
					<a class="next " href="#"><span>下一页</span></a>
				</c:if>
				<c:if test="${curpage<totalPage }">
					<a class="next " href="javascript:;"
						onclick="goPageAction(${curpage+1});"><span>下一页</span></a>
				</c:if>
				<a class="last " href="javascript:;"
					onclick="goPageAction(${totalPage});"><span>末页</span></a>
				<div class="allcount" style="font-style: italic; font-size: 16">
					共<span class="red">${totalPage }</span>页/每页<span class="red">20</span>条
				</div>
			</div>
		</div>
	</div>
</div>
</body>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
function goPageAction(curpage) {
	location.href="${ctx}/web/oa/kq/mysq?style=${style}&curpage="+curpage;
}
$(function(){
	$('a#OA申请').addClass('ui-tabs-current');	
	$('#打卡补签申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
	$('#sBtn').click(function(){
		document.sForm.submit();
	});
	$('#smbtn').click(function(){
		document.sForm.action="${ctx }/web/oa/kq/exportRetroactiveRecord";
		document.sForm.submit();
	});
	
});
</script>
</html>