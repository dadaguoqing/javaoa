<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>



<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="${ctx }/web/oa/notice/list" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>

	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	<form name="form1" action="${ctx }/web/oa/notice/add" method="post">
	<div style="border:1px solid #2191C0; margin-top:10px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">发布通知公告</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="width:90px;">发布者：</td>	
			<td style="width:90px;">
				<input type="hidden" name="publisher" value="${loginUser.name }"/>
				${loginUser.name }
			</td>	
			<td class="title" style="border-left:none; width:90px;">发布部门：</td>	
			<td style="width:90px;">
				<input name="pubName" style="width:120px;"/>
			</td>
			<td class="title" style="border-left:none; width:90px;">标题：</td>	
			<td style="width:90px;">
				<input name="title" id="tit" style="width:120px;"/>
			</td>
			
			<td class="title" style="border-left:none; width:90px;">显示方式：</td>	
			<td style="width:90px;">
				<select name="showType">
					<option value="0">当前页显示</option>
					<option value="1">新页面打开</option>
				</select>
			</td>
			
			<td class="title" style="width:90px;">所属分类：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
			%>
			<td  style="border-right:none; width:200px;">
				<!--  -->
				<input type="hidden" name="createTime" value="${now }"/>
				<select name="type">
					<option value="公司通知">公司通知</option>
					<option value="规章制度">规章制度</option>
					<option value="新增功能">新增功能</option>
					<option value="公文模板">公文模板</option>
					<option value="组织结构">组织结构</option>
				</select>
			</td>	
		</tr>
		<tr>
			<td class="title" style="width:90px;">接收员工：</td>	
			<td style="width:90px;">
				<select name="isAll" id="isAll">
					<option value="1">所有人员</option>
					<option value="0">特定人员</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">
				<input type="button" value="点击选择人员" id="crbtn" style="display:none;"/>
			</td>	
			<td class="title" style="border-left:none; text-align:left; overflow:hidden;" colspan="7">
				<input type="hidden" name="uids" id="userids"/>
				<span id="usernames"></span>
			</td>
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		公告内容</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="content" id="cnt" style="width:80%; height:250px; padding:5px 10px;"></textarea>
				<!-- 加载编辑器的容器 
			    <script id="container" name="content" type="text/plain">
        		这里写你的初始化内容
    			</script>-->
			    <!-- 配置文件 -->
			    <script type="text/javascript" src="${ctx}/resources/ueditor/ueditor.config.js"></script>
			    <!-- 编辑器源码文件 -->
			    <script type="text/javascript" src="${ctx}/resources/ueditor/ueditor.all.js"></script>
			    <!-- 实例化编辑器 -->
			    <script type="text/javascript">
			        var ue = UE.getEditor('cnt',{
			       	
				    });
			    </script>
			</td>	
		</tr>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">发布通知</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#通知公告').addClass('cur');

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#isAll').change(function(){
		var isAll = $(this).val();
		
		if(isAll == 0){
			$('#crbtn').show();
			$('#userids').val('');
			$('#usernames').html('还未选择员工');
		}else{
			$('#crbtn').hide();
			$('#userids').val('');
			$('#usernames').html('');
		}
	});

	$('#crbtn').click(function(){
		var r = window.open('${ctx}/web/oa/user/chooseUser','','status:0;help:0;dialogWidth:650px;dialogHeight:500px;toolbar=yes');
		r = $.trim(r);
		var ss = r.split("byL");
		r = ss[0];
		var names = ss[1];
		if(r){
			$('#usernames').html(names);
		}else{
			$('#userids').val('');
			$('#usernames').html('还未选择员工');
		}
	});
});


function setUNames(unms){
	$('#usernames').html("您选择了：" + unms);
}

function setUids(str){
	$('#userids').val(str);
}

function submitForm(){
	var cnt = $('#cnt').val();
	cnt = $.trim(cnt);

	var tit = $('#tit').val();
	tit = $.trim(tit);

	var isAll = $('#isAll').val();

	if(!tit){
		alert('请填写标题');
		return;
	}
	
	if(!cnt){
		alert('请填写公告内容');
		return;
	}

	if(isAll == 0){
		var uids = $('#userids').val();
		if(!uids){
			alert('请选择接收员工。');
			return;
		}
	}
	//alert('ok');
	document.form1.submit();
}
</script>