<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<c:forEach items="${loginUserMenuMap['7']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-bottom:0px;line-height:20px; height:22px; border-bottom:1px solid #ccc;">
	<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">个人信息</div>
	</div>
	
	<!--  
	<div class="lzui-tooltips" style="padding:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  		个人信息修改之后，系统将自动发送邮件给直属领导确认。请不要随意修改。
  	</div>
	-->
	
	<div style="border:1px solid #2191C0; ">
	<div class="main-header" id="id1">
		<span style="color:#eee;">个人信息</span>
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
				${loginUser.name }
			</td>	
			<td class="title" style="width:90px;">登陆账号 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				${loginUser.code }
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
			<td style="width:90px;">
				<select disabled="disabled" name="deptId" id="deptId">
					<option value="">-请选择-</option>
					<c:forEach items="${depts}" var="cur">
					<option value="${cur.id }">${cur.name }</option>
					</c:forEach>
				</select>
			</td>	
			<td class="title" style="width:90px;">&nbsp;</td>
			<td  style=" width:90px;">
				&nbsp;
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="border-left:none; width:90px;">身份证号：</td>	
			<td style="width:90px;">
				<input name="idCardNum" disabled="disabled" value="${loginUser.idCardNum }"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">性别：</td>	
			<td style="width:90px;">
				<select name="gender" id="gender" disabled="disabled" >
					<option value="男">-男-</option>
					<option value="女">-女-</option>
				</select>
			</td>
			<td class="title" style="width:90px;">入职时间：</td>
			<td  style="border-right:none; width:90px;">
				<input name="entryDate" disabled="disabled" class="Wdate" value="${loginUser.entryDate }"  />
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
			<td class="title" style="border-left:none; width:90px;">毕业学校：</td>	
			<td style="width:90px;">
				<input name="collage" disabled="disabled" value="${loginUser.collage }"/>
			</td>	
			<td class="title" style="width:90px;">所学专业：</td>
			<td style="width:90px;">
				<input name="major" disabled="disabled" value="${loginUser.major }"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">学历：</td>	
			<td style="width:90px;">
				<select name="edu" id="edu" disabled="disabled">
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
				<input name="grdMonth" class="Wdate"  disabled="disabled" value="${loginUser.grdMonth }"/>
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
				${loginUser.email }
			</td>	
			<td class="title" style="border-left:none; width:90px;">手机号码：</td>	
			<td style="width:90px;">
				<input name="phone1" disabled="disabled" value="${loginUser.phone1 }"/>
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
	
	
</div>
	
</div>

</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#个人中心').addClass('ui-tabs-current');	
	$('#个人信息').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#gender').val('${loginUser.gender}');
	$('#deptId').val('${loginUser.deptId}');
	$('#edu').val('${loginUser.edu}');
});
function submitForm(){
	document.form1.submit();
}
</script>