<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../../myheader.jsp"%>

<script type="text/javascript" src="${ctx }/resources/js/layer/layer.js"></script>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<div class="navTitle2 cur" url="${ctx }/web/oa/prop/addPlace">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;放置地点
		</div>
		<div class="navTitle2" url="${ctx }/web/oa/prop/add">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>
	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">

		<div
			style="margin-top: 10px; position: relative; line-height: 20px; height: 22px; border-bottom: 1px solid #ccc;">
			<div class="lzui-error" style="display: none" id="msg"></div>
			<div
				style="width: 92px; color: #34495E; border-bottom: 2px solid #27AE60; padding-left: 10px; font-size: 14px; font-weight: bold;">放置地点</div>
			<div style="padding: 10px">
				<select id="places" style="width: 155px; height: 25px"></select> <input
					id="newPlace" placeholder="请输入修改后的地点" style="width: 150px; height: 20px;" />
			</div>
			<div style="padding-top: 5px;margin-left: 12px">
				<a onclick="update()" class="lzui-btn lzui-corner-all">修改</a>
			</div>
		</div>
	</div>

</div>

</body>
</html>
<script>
	var mode = '${mode}';

	$(function() {
		$('#资产管理').addClass('ui-tabs-current');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

		$.get("getPlaces", function(result) {
			if (mode == 1) {
				result = JSON.parse(result);
			}
			if (!result.success) {
				layer.msg(result.msg);
			} else {
				tr = '';
				$.each(result.data, function(i, obj) {
					tr += '<option value="'+obj.id+'">' + obj.name
							+ '</option>';
				});
				$('#places').append(tr);
			}
		});
		
		
	});
	
	
	function update() {
		if(!$('#newPlace').val() || $('#newPlace').val() == null){
			layer.msg('新地点不能为空');
			return ;
		}
		$.post("updatePlaceAjax",
				{id:$('#places').val(),name:$('#newPlace').val()},
				function(result){
					if(mode == 1) {
						result = JSON.parse(result);
					}
					$('#msg').html(result.msg);
					$('#msg').show();
				});
	}
	</script>