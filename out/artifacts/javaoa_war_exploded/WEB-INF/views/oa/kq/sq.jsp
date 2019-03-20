<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>
<link rel="stylesheet" href="${ctx }/resources/css/addProject.css" />
<style>
td {
	
}
</style>
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

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>


		<div class="lzui-tooltips"
			style="margin: 10px; color: #C0392B; font-weight: bold; font-size: 14px;">
			<a style="color: #C0392B;" href="${ctx}/web/oa/kq/mysq?curpage=1">点击查看申请记录</a>
		</div>
		<div style="padding: 0px 10px;">

			<form name="form1" action="" method="post">
				<input type="hidden" value="${submitCode}" name="submitCode" />
				<div style="border: 1px solid #2191C0; margin-top: 0px;">
					<div class="main-header" id="id1">
						<span style="color: #eee;">${compName }打卡补签申请单</span>
					</div>
					<div class="" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							基本信息
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="width: 60px;">申请人：</td>
								<td style="width: 90px;">
									<%-- 
				${loginUser.name }
				--%> <select name="proposer" id="proposer">
										<c:if test="${ empty selectUser}">
											<option value="0">-请选择-</option>
										</c:if>
										<c:if test="${not empty selectUser}">
											<option value="${selectUser.id }">${selectUser.name}</option>
										</c:if>
										<c:forEach items="${users}" var="cur">
											<option value="${cur.id }">${cur.name }</option>
										</c:forEach>
								</select>
								</td>
								<td class="title" style="border-left: none; width: 120px;">所属部门：</td>
								<td style="width: 90px;"><span id="deptSpan"> <c:if
											test="${not empty dept }">
					"${dept.name }"
				</c:if>
								</span></td>
								<td class="title" style="width: 90px;">申请时间：</td>
								<%
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
									Date n = new Date();
									String now = sdf.format(n);
									pageContext.setAttribute("now", now);
								%>
								<td style="border-right: none;"><input type="hidden"
									name="createTime" value="${now }" /> ${now }</td>
								<td class="title">补签类型</td>
								<td style="border-right: none;"><select name="style"
									id="style">
										<c:if test="${empty style }">
											<option value="0">请选择补签类型</option>
										</c:if>
										<c:if test="${not empty style and style==0}">
											<option value="0">请选择补签类型</option>
										</c:if>
										<c:if test="${not empty style and style==1}">
											<option value="1">迟到打卡补签</option>
										</c:if>
										<c:if test="${not empty style and style==2}">
											<option value="2">忘记打卡补签</option>
										</c:if>
										<c:if test="${not empty style and style==3}">
											<option value="3">公事出差打卡补签</option>
										</c:if>
										<c:if test="${not empty style and style==4}">
											<option value="4">工牌丢失打卡补签</option>
										</c:if>
										<c:if test="${not empty style and style==5}">
					<<option value="5">先研院授课打卡补签</option>
										</c:if>
										<c:if test="${not empty style and style==6}">
											<option value="6">公事外出打卡补签</option>
										</c:if>
										<c:if test="${not empty style and style==7}">
											<option value="7">其他原因</option>
										</c:if>
										<option value="1">迟到打卡补签</option>
										<option value="2">忘记打卡补签</option>
										<option value="3">公事出差打卡补签</option>
										<option value="4">工牌丢失打卡补签</option>
										<option value="5">先研院授课打卡补签</option>
										<option value="6">公事外出打卡补签</option>
										<option value="7">其他原因</option>
								</select></td>
							</tr>
						</tbody>
					</table>

					<div class="" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							考勤日期
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="border-left: none; width: 120px;">考勤起止日期：</td>
								<td style="width: 160px;"><c:if test="${empty begin }">
										<input name="dayStr1" id="dayStr" class="Wdate"
											readonly="readonly" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" />
									</c:if> <c:if test="${not empty begin }">
										<input value="${begin }" name="dayStr1" id="dayStr"
											class="Wdate" readonly="readonly" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" />
									</c:if></td>
								<td style="width: 16px;">至</td>
								<td style="width: 160px;"><c:if test="${empty end }">
										<input name="dayStr2" id="dayStr1" class="Wdate"
											readonly="readonly" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" />
									</c:if> <c:if test="${not empty end }">
										<input value="${end }" name="dayStr2" id="dayStr1"
											class="Wdate" readonly="readonly" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" />
									</c:if></td>
								<td>

									<div style="">
										<a href="javascript:submitSearchForm();"
											class="lzui-btn lzui-corner-all">搜索</a>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							考勤详情
						</span>
					</div>

					<table class="table1">
						<tbody id="kqxqTime">
							<c:if test="${empty list }">
								<tr>
									<td class="l-td-last-right" colspan="6" style="color: red;">
										对不起，目前还没有相关数据。</td>
								</tr>
							</c:if>
							<c:forEach items="${list }" var="cur">
								<tr id="${cur.dayStr }">
									<td style="width: 150px;"><input type="hidden"
										name="dayStrs" id="dayStrs" value="${cur.dayStr }" /> <input
										disabled="disabled" name="dayStr" value="${cur.dayStr }" /></td>
									<td class="title" style="border-left: none; width: 120px;">上班打卡时间：</td>
									<td><input name="checkin" id="checkin" class="Wdate"
										value="${cur.checkin }" readonly="readonly" autocomplete="off"
										onFocus="WdatePicker({startDate:'%y-%M-01 17时30分', dateFmt:'HH:mm'})"></td>
									<td class="title" style="border-left: none; width: 120px;">下班打卡时间：</td>
									<td><input name="checkout" id="checkout" class="Wdate"
										value="${cur.checkout }" readonly="readonly"
										autocomplete="off"
										onFocus="WdatePicker({startDate:'%y-%M-01 17时30分', dateFmt:'HH:mm'})"></td>
									<td><a href="javascript:deleteTr('${cur.dayStr}');">删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							补打卡事由
						</span>
					</div>

					<table class="table1">
						<tbody>
							<tr>
								<td style="border-right: none;"><textarea name="content"
										id="cnt" style="width: 50%; height: 120px; padding: 5px 10px;"></textarea>
									<span style="color: red;">不超过150个字符，ctrl+Enter提交</span></td>
							</tr>
						</tbody>
					</table>

				</div>
				<div class="" style="margin-top: 10px; margin-bottom: 20px;">
					<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
				</div>
			</form>
		</div>

	</div>
</div>
</body>
</html>
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(function() {
		$('a#OA申请').addClass('ui-tabs-current');
		$('#打卡补签申请').addClass('cur');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

		$('#proposer').change(function() {
			var uid = $(this).val();
			if (uid && uid != 0) {
				$.get('${ctx}/web/oa/user/ajaxDept?uid=' + uid, function(data) {
					//var d = eval(data);
					$('#deptSpan').html(data);
				});
			} else {
				$('#deptSpan').html('');
			}

			var dt = $('#dayStr').val();
			if (dt && '0' != uid) {
				ajaxGetCheckout(dt, uid);
			}
		});

		$('#cnt').keypress(function(e) {
			if (e.ctrlKey && e.which == 13 || e.which == 10) {
				submitForm()
			} else if (e.shiftKey && e.which == 13 || e.which == 10) {
				submitForm()
			}
		})
	});

	function dchange() {
		var str = $(this).val();
		$('#dayStrSpan').html(str);
		$('#dayStr1').val(str);
		var empId = $('#proposer').val();
		if (str && '0' != empId) {
			ajaxGetCheckout(str, empId);
		}
		appendDate(str, 1);
	}
	//计算两个日期之间的天数
	function Computation(date1, date2) {
		var iDays, Date1, Date2;
		//aDate = date1.split("-");      
		//oDate1 = new Date(aDate[1] + '-' + aDate[0] + '-' + aDate[2]); 
		Date1 = new Date(date1.substring(0, 4) + '-' + date1.substring(5, 7)
				+ '-' + date1.substring(8, 10));
		Date2 = new Date(date2.substring(0, 4) + '-' + date2.substring(5, 7)
				+ '-' + date2.substring(8, 10));
		//   aDate = date1.split("-");      
		//   oDate2 = new Date(aDate[1] + '-' + aDate[0] + '-' + aDate[2]);      
		iDays = parseInt(Math.abs(Date1 - Date2) / 1000 / 60 / 60 / 24) + 1;
		//   var weekday=0;
		//   for(var i=1;i<iDays;i++){
		// 	var time=new Date(oDate1.getYear(),oDate1.getMonth(),oDate1.getDay()+i);
		// 	if(time.getDay()==0||time.getDay()==6){
		// 		weekday++;
		// 	}
		//   }
		return iDays;
		// alert(iDays);
		//   var lDays=iDays-weekday;
		//   document.getElementById("dateresult").innerText=lDays;  
	}
	//添加考勤详情
	function appendDate(begin, Days) {
		$('#kqxqTime').empty();
		for (var i = 0; i < Days; i++) {
			var new_tr = $('<tr>' + '<td style="width:100px;">'
					+ '<input name="dayStr" id="dayS'
					+ i
					+ '" value="'
					+ begin.split("月")[0]
					+ '月'
					+ ((eval("(" + begin.substring(8, 10) + ")") + i) > 9 ? (eval("("
							+ begin.substring(8, 10) + ")") + i)
							: ("0" + (eval("(" + begin.substring(8, 10) + ")") + i)))
					+ '日'
					+ '" class="Wdate" readonly="readonly" autocomplete="off"/>'
					+ '</td>'
					+ '<td class="title" style="border-left:none; width:120px;">上班打卡时间：</td>'
					+ '<td style="width:160px;">'
					+ '<input name="checkin" id="checkin'
					+ i
					+ '" class="Wdate" value="08:30" readonly="readonly" autocomplete="off" onFocus="WdatePicker({startDate:&quot;%y-%M-01 08时30分&quot;, dateFmt:&quot;HH:mm&quot;})"/>'
					+ '</td>'
					+ '<td class="title" style="border-left:none; width:120px;">下班打卡时间：</td>'
					+ '<td style="width:160px;">'
					+ '<input name="checkout" id="checkout'
					+ i
					+ '" class="Wdate" value="17:30" readonly="readonly" autocomplete="off" onFocus="WdatePicker({startDate:\'%y-%M-01 17时30分\', dateFmt:\'HH:mm\'})"/>'
					+ '</td>'
					+ '<td  style="border-right:none; color:red;" id="info'+i+'">'
					+ '</td>' + '</tr>');
			$('#kqxqTime').append(new_tr);
		}
	}
	function dchange1() {
		var str = $(this).val();
		var empId = $('#proposer').val();
		if (str && '0' != empId) {
			ajaxGetCheckout(str, empId);
		}
		var beginStr = $('#dayStr').val();
		var Days = Computation(beginStr, str);
		appendDate(beginStr, Days);
	}
	function deleteTr(id) {
		$("#" + id + "").remove();
	}
	function ajaxGetCheckout(dayStr, empId) {
		var url = "${ctx}/web/oa/kq/day?t=" + Math.random();
		$.post(url, {
			dayStr : dayStr,
			empId : empId
		}, function(data) {

			data = eval(data);

			if (data.ret == '1') {
				var cout = data.checkout;
				var cin = data.checkin;

				if (cin) {
					var ss = cin.split(":");
					$('#beginHour').val(ss[0]);
					$('#beginMin').val(ss[1]);
				}
				if (cout) {
					var ss = cout.split(":");
					$('#endHour').val(ss[0]);
					$('#endMin').val(ss[1]);
				}
				$('#info').html("");
			} else {
				$('#beginHour').val('-1');
				$('#beginMin').val('-1');
				$('#endHour').val('-1');
				$('#endMin').val('-1');
				$('#info').html("系统中没有该用户当天的打卡记录。");
			}
		});
	}

	function submitForm() {
		var dayStr = $('#dayStr').val();

		var cnt = $('#cnt').val();
		var uid = $('#proposer').val();

		var beginHour = $('#beginHour').val();
		var beginMin = $('#beginMin').val();
		var endHour = $('#endHour').val();
		var endMin = $('#endMin').val();
		var checkins = $('#checkin').val();
		var checkouts = $('#checkout').val();
		$('#checkins').val(checkins);
		$('#checkouts').val(checkouts);

		cnt = $.trim(cnt);
		$('#cnt').val(cnt);

		if ('0' == uid) {
			alert('请选择申请人');
			return;
		}

		if (!dayStr) {
			alert('请选择考勤日期');
			return;
		}

		if (beginHour == '-1' || beginMin == '-1' || endHour == '-1'
				|| endMin == '-1') {
			alert('请填写上班打卡时间');
			return;
		}

		if (!cnt) {
			alert('请填写补打卡事由');
			return;
		}

		document.form1.action = "${ctx }/web/oa/kq/sqInsert";
		form1.submit();
	}

	function submitSearchForm() {
		var dayStr = $('#dayStr').val();

		var cnt = $('#cnt').val();
		var uid = $('#proposer').val();

		var beginHour = $('#beginHour').val();
		var beginMin = $('#beginMin').val();
		var endHour = $('#endHour').val();
		var endMin = $('#endMin').val();

		cnt = $.trim(cnt);
		$('#cnt').val(cnt);

		if ('0' == uid) {
			alert('请选择申请人！');
			return;
		}

		if (!dayStr) {
			alert('请选择考勤日期！');
			return;
		}
		var style = $('#style').val();
		if (style == 0) {
			alert('请选择补签类型！');
			return;
		}
		document.form1.action = "${ctx }/web/oa/kq/sqList";
		form1.submit();
	}
</script>