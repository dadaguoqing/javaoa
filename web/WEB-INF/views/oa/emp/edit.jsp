<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">

<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	<div id="编辑员工" class="navTitle2 cur" url="${ctx }/web/oa/user/edit?id=${emp.id}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;编辑员工</div>
	<div class="navTitle2" url="${ctx }/web/oa/user/list"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
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
	
	<form name="form1" action="${ctx }/web/oa/user/edit" method="post">
	<input type="hidden" name="id" value="${emp.id }"/>
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
				<input name="name" id="nameIpt" value="${emp.name }"/>
			</td>	
			<td class="title" style="width:90px;">登陆账号 <span style="color:red;">*</span>：</td>	
			<td style="width:90px;">
				<input name="code" id="codeIpt" value="${emp.code }"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">所属部门：</td>	
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
				<input name="entryDate" class="Wdate" value="${emp.entryDate }" onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" />
			</td>	
		</tr>
		<tr>
				
			<td class="title" style="border-left:none; width:90px;">身份证号：</td>	
			<td style="width:90px;">
				<input name="idCardNum" value="${emp.idCardNum }"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">性别：</td>	
			<td style="width:90px;">
				<select name="gender" id="gender">
					<option value="男">-男-</option>
					<option value="女">-女-</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">职位：</td>	
			<td style="width:90px;">
				<input type="button" value="点击查看" id="crbtn" onclick="javascript:btnOnClick(${emp.id})"/>
			</td>
			
			<td class="title" style="border-left:none; width:90px;">任职资格：</td>	
			<td style="width:90px;">
				<c:if test="${not empty empComp}">
					<a target="_blank" href="${ctx}/${empComp.pdf}">预览</a> &nbsp;|&nbsp; <a href="${ctx}/web/oa/user/changeEmpCompShow/${emp.id }">重新上传</a>&nbsp;|&nbsp; <a href="${ctx}/web/oa/user/deleteEmpCompShow/${emp.id }">删除</a>
				</c:if>
				<c:if test="${empty empComp}">
					<a href="${ctx}/web/oa/user/changeEmpCompShow/${emp.id }">点击添加</a>
				</c:if>
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
				<input name="collage" value="${emp.collage }"/>
			</td>	
			<td class="title" style="width:90px;">所学专业：</td>
			<td style="width:90px;">
				<input name="major" value="${emp.major }"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">学历：</td>	
			<td style="width:90px;">
				<select name="edu" id="edu">
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
				<input name="grdMonth" class="Wdate" value="${emp.grdMonth }" onFocus="WdatePicker({dateFmt:'yyyy年MM月'})"/>
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
				<input name="email" id="email" value="${emp.email }"/>
			</td>	
			<td class="title" style="border-left:none; width:90px;">手机号码：</td>	
			<td style="width:90px;">
				<input name="phone1" value="${emp.phone1 }"/>
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
	
	<table class="table1" style="display: none;" ><tbody>
		<c:forEach items="${empRoles}" var="role" varStatus="st">
			<c:if test="${st.count==0}"><tr></c:if>
    		<c:if test="${role.check == 'unchecked' }">
	    		<td class="l-td" style="padding-top:10px; width:100px; padding-bottom:10px;">
	    			<label for="cb${role.id}">${role.name }</label> <input id="${role.id}" type="checkbox" value="${role.id}" name="roles" nameValue="${role.name }" class="uids"/>
	    		</td>
    		</c:if>
    		<c:if test="${role.check == 'checked' }">
	    		<td class="l-td" style="padding-top:10px; width:100px; padding-bottom:10px;">
	    			<label for="cb${role.id}">${role.name }</label> <input id="${role.id}" type="checkbox" checked="checked" value="${role.id}" name="roles" nameValue="${role.name }" class="uids"/>
	    		</td>
    		</c:if>
    		<c:if test="${st.count%5==0 && st.count!=0}"></tr><tr></c:if>
    		<c:if test="${st.last}"></tr></c:if>
    	</c:forEach>
	</tbody></table>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交修改</a>
		<a href="javascript:window.history.back();" class="lzui-btn lzui-corner-all">返回</a>
	</div>
	
	</form>
	</div>
	
</div>


</body>
</html>
<script language="javascript" type="text/javascript" src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
$(function(){
	$('#员工管理').addClass('ui-tabs-current');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#gender').val('${emp.gender}');
	$('#deptId').val('${emp.deptId}');
	$('#edu').val('${emp.edu}');
	
});
function submitForm(){
	var email = $('#email').val();
	var name = $('#nameIpt').val();
	var code = $('#codeIpt').val();

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

	email = $.trim(email);
	$('#email').val(email);
	if(!email){
		alert('请输入email。');
		return;
	}
	
	document.form1.submit();
}
function btnOnClick(empId){
	var r = window.showModalDialog('${ctx}/web/oa/user/chooseUserRoles?empId='+empId,'','status:0;help:0;dialogWidth:650px;dialogHeight:500px;toolbar=yes');
	r = $.trim(r);
 	if (r) {
		var ss = r.split(",");
		for(var i = 0; i < ss.length; i++) {
			$("#"+ss[i]).attr("checked", true);
		}
	} else {
		alert('请选择员工职位！');
	}
}
</script>