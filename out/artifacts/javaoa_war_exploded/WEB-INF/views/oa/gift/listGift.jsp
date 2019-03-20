<%@ page language="java" import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<style>
<!--
.selectBak {background: #E74C3C; color:#eee; font-weight:bold;}
.selectBak:hover{background: #E74C3C; color:#eee; font-weight:normal;}
-->
</style>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;设置礼品</div>
	<div class="navTitle2" url="${ctx }/web/oa/gift/evt/list"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>
	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px; margin-bottom:10px; position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
		
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">设置礼品</div>
		
		<form id="form1" name="form1" action="${ctx }/web/oa/gift/gift/add" method="post" enctype="multipart/form-data">
		<input type="hidden" name="eventId" value="${evt.id }"/>
		<input type="hidden" name="idEdit" id="idEdit"/>
		<div class="gridContainer" style="width:99%; margin-left:0px;margin-top:15px;">
		<table class="lzzUIGrid"><tbody>
			<tr>
				<td class="l-td-toolbar" colspan="6">
					<div class="l-float-left" style="font-weight:bold; font-size:15px; margin-right:20px;" >
					《${evt.name }》礼品
					</div>
				</td>
			</tr>
			
			<tr>
				<td class="l-td-header l-td-left" style="width:40px;">序号</td>
				<td class="l-td-header l-td-left" style="">描述</td>
				<td class="l-td-header" style="">礼品详情</td>
				<td class="l-td-header l-td-last-right" style="width:120px;">操作</td>
			</tr>
	    	
	    	<c:forEach items="${list}" var="cur" varStatus="st">
	    		<tr>
	    			<td class="l-td l-td-left">${st.count}</td>
	    			<td class="l-td l-td-left">
	    				<span class="norm${cur.id }" >${cur.name }</span>
	    				<span style="display:none;" class="edit${cur.id }">
	    					<input name="nameEdit" id="nameEdit" value="${cur.name }" class="edit${cur.id } edt" style="width:250px;"/>
	    				</span>
	    			</td>
	    			<td class="l-td">
						<span class="norm${cur.id }" >
							<a target="_blank" href="${ctx }/web/oa/gift/${cur.id}.pdf">点击查看详情</a>
						</span>
						<span style="display:none;" class="edit${cur.id }">
							<input type="file" id="fileEdit" name="fileEdit" class="edit${cur.id } edt"/><span style="color:red;"></span>
						</span>
					</td>
	    			<td class="l-td">
	    				<span class="norm${cur.id }" >
						<a href="javascript:editTr(${cur.id});">编辑</a>
						| &nbsp;
						<a href="javascript:del(${cur.id}, '${cur.name }' );">删除</a>
						</span>
						
						<span style="display:none;" class="edit${cur.id }">
						<a href="javascript:updateTr(${cur.id});">确定</a>
						| &nbsp;
						<a href="javascript:cancelTr(${cur.id});">取消</a>
						</span>
						
					</td>
	    		</tr>
	    	</c:forEach>
	    	
	    		<tr class="addtr">	
	    			<td class="l-td l-td-left">${size}</td>
	    			<td class="l-td l-td-left">
						<input name="name" id="name" style="width:250px;"/>
					</td>
	    			<td class="l-td">
						<input type="file" id="file" name="file" class="adds"/><span style="color:red;">(请上传pdf文件)</span>
					</td>
	    			<td class="l-td">
						<a href="javascript:addGift();">添加</a>
					</td>
					
	    		</tr>
	    		
	    	<c:if test="${empty list}">
	    	<tr>
				<td class="l-td l-td-last-right" colspan="4" style="color:red;">
					该活动目前还没添加礼品，请马上添加。
				</td>
			</tr>
	    	</c:if>
		</tbody></table>
		</div>
		</form>
		
	</div>
</div>
</div>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	
});

var editing = false;

function editTr(id){
	if(editing){
		alert('只能同时编辑一行。');
		return;
	}
	editing = true;
	$('.norm'+id).hide();
	$('.edit'+id).show();
	$('.addtr').hide();
	
}

function cancelTr(id){
	$('.edit'+id).hide();
	$('.norm'+id).show();
	$('.addtr').show();
	editing = false;
}

function del(id, name){

	var flag = confirm("确定要删除礼物《"+name+"》？");
	if(flag){
		location.href = "${ctx }/web/oa/gift/gift/del?id="+id;
	}
}

function addGift(){
	
	var name = $('#name').val();
	var file = $('#file').val();
	

	if(!name){
		alert("请填写礼品描述");
		return;
	}

	if(!file){
		alert("请上传礼品详情pdf文档");
		return;
	}

	document.form1.submit();
}

function updateTr(id){

	$('#idEdit').val(id);
	var name = $('#nameEdit').val();
	

	if(!name){
		alert("请填写礼品描述");
		return;
	}

	$('#form1').attr('action','${ctx }/web/oa/gift/gift/edit');

	$('.adds').attr("disabled","disabled");
	$('.edt').attr("disabled","disabled");
	$('.edit'+id).removeAttr("disabled");
	
	$('#form1').submit();

}

</script>
</body>
</html>