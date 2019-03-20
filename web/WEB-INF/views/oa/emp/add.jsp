<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['2']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:10px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">员工管理</div>
	</div>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<c:if test="${not empty empId}">
	<div class="lzui-error">
		用户添加成功，请立即为该用户设定权限，否则该用户将没有权限。<a href="${ctx}/web/oa/role/emp?empId=${empId}">点击这里设置权限</a>
	</div>
	</c:if>
	
	<form name="form1" action="${ctx }/web/oa/user/add" method="post">
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">员工信息</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">姓名 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="name" id="nameIpt"/>
			</td>	
			<td class="title" style="width:90px;">登陆账号 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="code" id="codeIpt"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<select name="deptId" id="deptId">
					<option value="">-请选择-</option>
					<c:forEach items="${depts}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
				</select>
			</td>	
			<td class="title" style="width:90px;">入职时间：</td>
			<td  style="border-right:none; width:90px;">
				<input name="entryDate" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" />
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="border-left:none; width:90px;">身份证号：</td>	
			<td style="width:90px;">
				<input name="idCardNum"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">性别：</td>	
			<td style="width:90px;">
				<select name="gender">
					<option value="男">-男-</option>
					<option value="女">-女-</option>
				</select>
			</td>	
			<td class="title" style="width:90px;">姓名全拼<span style="color:red;">*</span>：</td>
			<td  style=" width:90px;">
				<input name="pinyin" id="pinyin"/>
			</td>	
			<td class="title" style="border-right:none; width:90px;">&nbsp;</td>	
			<td>
				&nbsp;
			</td>
			
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		教育信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">毕业学校<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="collage" id="collage"/>
			</td>	
			<td class="title" style="width:90px;">所学专业<span style="color:red;">*</span>：</td>
			<td style="width:90px;">
				<input name="major" id="major"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">学历：</td>	
			<td style="width:90px;">
				<select name="edu">
					<option value="本科">本科</option>
					<option value="大专">大专</option>
					<option value="其它">其它</option>
					<option value="硕士">硕士</option>
					<option value="博士">博士</option>
					<option value="博士后">博士后</option>
				</select>
			</td>	
			<td class="title" style="width:90px;">毕业时间：</td>
			<td  style="border-right:none; width:90px;">
				<input name="grdMonth" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy年MM月'})"/>
			</td>	
		</tr>
	</tbody></table>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		联系方式</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:90px;">email <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="email" id="email"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">手机号码<span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="phone1" id="phone1"/>
			</td>
			
			<td class="title" style="width:90px;">&nbsp;</td>	
			<td style="width:90px;">
				&nbsp;
			</td>	
			
			<td class="title" style="border-right:none; width:90px;">&nbsp;</td>	
			<td style="width:90px;">
				&nbsp;
			</td>	
			
		</tr>
		
	</tbody></table>
	
	</div>
	<span style="color:red;">*</span>号标注的为必填项。
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">点击添加</a>
	</div>
	
	</form>
	</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#OA管理').addClass('ui-tabs-current');	
	$('#添加员工').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});
});
function submitForm(){
	var email = $('#email').val();
	var name = $('#nameIpt').val();
	var code = $('#codeIpt').val();

	var pinyin = $('#pinyin').val();
	var phone1 = $('#phone1').val();
	var major = $('#major').val();
	var collage = $('#collage').val();
	var deptId = $('#deptId').val();

	name = $.trim(name);
	$('#nameIpt').val(name);
	if(!name){
		alert('请输入姓名。');
		return;
	}

	code = $.trim(code);
	$('#codeIpt').val(code);
	if(!code){
		alert('请输入登陆账号。');
		return;
	}

	pinyin = $.trim(pinyin);
	$('#pinyin').val(pinyin);
	if(!pinyin){
		alert('请输入姓名全拼。');
		return;
	}

	if(!deptId){
		alert('请选择部门。');
		return;
	}

	if(!collage){
		alert('请输入毕业学校。');
		return;
	}

	if(!major){
		alert('请输入所学专业。');
		return;
	}


	email = $.trim(email);
	$('#email').val(email);
	if(!email){
		alert('请输入email。');
		return;
	}

	if(!phone1){
		alert('请输入手机号码。');
		return;
	}
	
	document.form1.submit();
}
</script>