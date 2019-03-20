<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>

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

		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<c:if test="${not empty msg}">
				<div class="lzui-error">${msg }</div>
			</c:if>

			<div
				style="width: 80px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">年假损益</div>
			<form name="form1" method="post" action="${ctx}/web/oa/nianjia/sy">
				<div class="lzui-tooltips"
					style="padding: 10px; color: #C0392B; font-weight: bold; font-size: 15px;">
					请按照下面的流程操作。</div>

				<div class="">

					<div class="lzui-headers">
						<div class="t1">
							<span>1</span>第一步、请选择员工
						</div>

						<div class="lzui-table-wapper"
							style="margin-top: 7px; height: 365px; overflow: scroll;">
							<table class="lzui-table" style="width: 100%;">
								<tr >
									<th
										style="width: 60px; text-align: center; padding-left: 15px;">
										<input type="checkbox" id="qx" onclick="quanxuan()" />
									</th>
									<th style="width: 80px;">| 姓名</th>
									<th style="width: 120px;">| 登陆账号</th>
								</tr>

								<c:if test="${empty users}">
									<tr>
										<td style="text-align: center; color: #27AE60;" colspan="3">
											系统中目前还没有相关用户!</td>
									</tr>
								</c:if>

								<c:forEach items="${users}" var="cur">
									<tr class="lzui-td utd" uid="${cur.id }">
										<td style="text-align: center;"><input type="checkbox"
											class="ids uids" name="empIds" value="${cur.id }"
											id="u${cur.id}" /></td>
										<td>${cur.name }</td>
										<td>${cur.code }</td>
									</tr>
								</c:forEach>
							</table>
						</div>

					</div>

					<div class="lzui-headers">
						<div class="t2">
							<span>2</span>第二步、选择对应的操作（类型、损益）
						</div>

						<div class="lzui-table-wapper" style="margin-top: 7px;">

							<div style="margin-bottom: 10px;">
								<span
									style="font-weight: bold; line-height: 25px; display: block; margin-bottom: 10px; margin-top: 10px;">请选择假期类型<span
									style="color: red">*</span></span> <select style="margin-left: 15px;"
									name="type" id="type">
									<option value="0">请选择</option>
									<option value="年假">年假</option>
									<option value="病假">病假</option>
								</select> <span
									style="font-weight: bold; line-height: 25px; display: block; margin-bottom: 10px; margin-top: 10px;">请选择增加或减少<span
									style="color: red">*</span></span> <select style="margin-left: 15px;"
									name="zj" id="zj">
									<option value="0">请选择</option>
									<option value="1">增加</option>
									<option value="-1">减少</option>
								</select> <span
									style="font-weight: bold; line-height: 25px; display: block; margin-bottom: 10px; margin-top: 10px;">请填写时间长度<span
									style="color: red">*</span></span> <input name="day" id="day"
									style="width: 50px" value="0" type="number" min="0" max="30"
									onblur="testnum(this,30)" /> 天 <input name="hour" id="hour"
									style="width: 50px" value="0" min="0" max="7" type="number"
									onblur="testnum(this,7)" /> 时 <input name="min" id="min"
									style="width: 50px" value="0" min="0" max="59" type="number"
									onblur="testnum(this,59)" /> 分 <span
									style="font-weight: bold; line-height: 25px; display: block; margin-bottom: 10px; margin-top: 10px;">请填写损益原因（不超过300个字符）<span
									style="color: red">*</span></span>
								<textarea name="bz" id="bz"
									style="width: 100%; height: 60px; padding: 7px;"></textarea>
							</div>
						</div>
					</div>

					<div class="lzui-headers">
						<div class="t3">
							<span>3</span>第三步、确认无误，点击提交。
						</div>

						<div class="lzui-table-wapper" style="margin-top: 7px;">
							<a href="javascript:submitRoles();" class="lzui-btn2"
								style="height: 30px; line-height: 30px; width: 100px; font-weight: bold; font-size: 15px;">提
								交</a>
						</div>

					</div>

					<div style="clear: both;"></div>

				</div>
			</form>
		</div>
	</div>

</div>

</body>
</html>

<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>
<script>
	function quanxuan() {
		$('input[name="empIds"]').each(function(j, item) {
			if ($('#qx').is(':checked')) {
				item.checked = true;
			} else {
				item.checked = false;
			}
		});
	}

	function testnum(obj, max) {
		var reg = /^\d+$/;
		var num = $(obj).val();
		if (!num) {
			layer.msg('不能为空！');
			$(obj).val(0);
			return;
		}
		if (!reg.test(num)) {
			layer.msg('格式错误，请填写非负整数！');
			$(obj).val(0);
			return;
		}
		if (num > max) {
			layer.msg('最大限制为' + max + '！');
			$(obj).val(max);
			return;
		}
	}

	function submitRoles() {
		var ult = $('.uids:checked');
		var l1 = ult.length;
		if (l1 == 0) {
			layer.msg("请选择员工！");
			return;
		}

		var timeLen = $('#timeLen').val();
		timeLen = $.trim(timeLen);
		var bz = $('#bz').val();
		bz = $.trim(bz);
		var type = $('#type').val();
		var zj = $('#zj').val();

		if (type == 0) {
			layer.msg("请选择假期类型！");
			return;
		}

		if (zj == 0) {
			layer.msg("请选择增加或者减少！");
			return;
		}

		if (bz == '') {
			layer.msg("请填写损益原因！");
			return;
		}

		if ($('#day').val() == 0 && $('#hour').val() == 0
				&& $('#min').val() == 0) {
			layer.msg('请填写时间长度！');
			return;
		}
		if (!$('#day').val() || !$('#hour').val() || !$('#min').val()) {
			layer.msg('时间长度不能为空！');
			return;
		}

		document.form1.submit();
	}

	$(function() {
		$('#OA管理').addClass('ui-tabs-current');
		$('#年假损益').addClass('cur');

		//ie兼容
		$('.lzui-td input').click(function(evt) {
			evt.stopPropagation();
		});

		/*
		$('.rtd').click(function(evt){
			var item = $(this).find("input");
			item.prop('checked',true);
			ajaxEmps();
		});*/

		$('.utd').click(function(evt) {
			var item = $(this).find("input");
			var ic = item.prop('checked');
			if (ic) {
				item.prop('checked', false);
			} else {
				item.prop('checked', true);
			}
		});

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

		//全选
		/*
		$('#checkAllUser').click(function(evt){
			var item = $(this);
			var ic =  item.prop('checked');
			if(ic){
				$('.utd input:checkbox').prop('checked',true);
			}else{
				$('.utd input:checkbox').prop('checked',false);
			}
		});*/

	});

	/*
	 function ajaxEmps(){
	 var rid = $('.rtd input:checked').val();
	
	 $.get('${ctx}/web/oa/role/ajaxEmpsByRole/'+rid, function(data){
	 $('.uids').prop('checked',false);
	 var emps = eval(data);
	 for(var i=0; i<emps.length; i++){
	 $('#u'+emps[i]).prop('checked',true);
	 }
	 });
	 }*/
</script>