<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="../public/header.jsp" %>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<%@ include file="../public/indexMenu.jsp" %>
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<div class="hshi">您好！<span style="font-weight:bold;">${loginUser.name }</span>。欢迎使用${compName }${sysName }。
	<span style="color:red; font-weight:bold;" id="dkSpan"></span>
	</div>
	
	
	<div style=" line-height:30px; margin-bottom:15px; margin-top:15px; font-weight:bold; padding-left:15px;" id="dlDiv">
	
	<c:if test="${not empty mydlusers}">
		您目前代理
		<c:forEach items="${ mydlusers}" var="cur">
		<a href="${ctx }/web/dlogin?oid=${cur.id}">${cur.name }</a>&nbsp;
		</c:forEach> 
		的OA系统，点击姓名进入。
		
	</c:if>
	
	<c:if test="${not empty mydluser and not daili}">
		您的的下列权限[
		<c:forEach items="${mybeidlMenus}" var="m" varStatus="st"><span style="color:red;">${m.name }<c:if test="${!st.last}">、</c:if></span></c:forEach>
		]目前由${ mydluser.name}代理，
		<a href="${ctx }/web/dl/undl?eid=${mydluser.id}">点击解除代理</a>&nbsp;&nbsp;
	</c:if>
	
	<c:if test="${daili}">
		您目前为代理登陆，
		<a href="${ctx }/web/loginFromDl">点击返回自己的主页。</a>&nbsp;&nbsp;
	</c:if>
	
	</div>
	
	<div style=" margin-bottom:15px; height:88px; width:730px; padding-left:11px; position:relative;">
	
		<iframe allowtransparency="true" frameborder="0" width="720" height="78" scrolling="no" src="http://tianqi.2345.com/plugin/widget/index.htm?s=2&z=1&t=1&v=0&d=4&bd=0&k=000000&f=000000&q=1&e=0&a=0&c=58321&w=720&h=78&align=left"></iframe>	
		<div style="position:absolute; width:740px; left:0; top:0; height:78px; filter:alpha(opacity=0); background-color:#fff; -moz-opacity:0; opacity: 0; -khtml-opacity: 0; " id="coverDiv">
		
		</div>
		<div style="position:absolute; width:30px; right:0; top:0; height:78px; background-color:rgb(238, 255, 238);"></div>
	</div>
	
	<div style="border:1px solid #2191C0; width:730px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">我的待办事件</span>
	</div>
	
	<c:if test="${ empty myDaiban && isEmptyDls && (empty myLeaves) && empty empDc}">
		<div style="padding:10px;">您暂时没有待办事件。</div>
	</c:if>
	
	<c:if test="${not empty empDc}">
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		今日订餐</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
    		
    		<td class="l-td" style="padding: 2px 3px; width:140px; font-weight:bold;">
    			开始时间
    		</td>
    		<td class="l-td" style="padding: 2px 3px; width:140px; font-weight:bold;">
    			预计结束时间
    		</td>
    		<td class="l-td" style="padding: 2px 3px; width:40px;font-weight:bold;">
    			操作
    		</td>
    	</tr>
    	
		<tr>
    		<td class="l-td" style="padding: 2px 3px;">
    			${empDc.createTime }
    		</td>
    		<td class="l-td" style="padding:2px 3px;">
    			${empDc.endTime }
    		</td>
    		<td class="l-td" style="padding:2px 3px;">
    			<a href="${ctx }/web/oa/dc/dingcan">点击订餐</a>
    		</td>
    	</tr>
    	</tbody></table>
	</c:if>
	
	<c:if test="${not empty myLeaves}">
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		我的请假申请（正在处理）</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
    		<td class="l-td" style="padding: 2px 3px; width:140px; font-weight:bold;">
    			申请时间
    		</td>
    		<td class="l-td" style="padding: 2px 3px; width:140px; font-weight:bold;">
    			开始时间
    		</td>
    		<td class="l-td" style="padding: 2px 3px; width:140px; font-weight:bold;">
    			结束时间
    		</td>
    		<td class="l-td" style="padding: 2px 3px; width:130px; font-weight:bold;">
    			当前状态
    		</td>
    		<td class="l-td" style="padding: 2px 3px; width:40px;font-weight:bold;">
    			详情
    		</td>
    	</tr>
    	<c:forEach items="${myLeaves}" var="cur" >
		<tr>
    		<td class="l-td" style="padding: 2px 3px;">
    			${cur.createTime }
    		</td>
    		<td class="l-td" style="padding:2px 3px;">
    			${cur.beginTime }
    		</td>
    		<td class="l-td" style="padding:2px 3px;">
    			${cur.endTime }
    		</td>
    		<td class="l-td" style="padding:2px 3px;">
    			<c:choose>
    					<c:when test="${cur.status == -1}">
    					审批不通过
    					</c:when>
    					<c:when test="${cur.status == 1}">
    					主管审批中（${AllUsers[cur.mgrId].name }）
    					</c:when>
    					<c:when test="${cur.status == 2}">
    					总监审批中（${AllUsers[cur.directId].name }）
    					</c:when>
    					<c:when test="${cur.status == 3}">
    					总经理审批中（${AllUsers[cur.bossId].name }）
    					</c:when>
    					<c:when test="${cur.status == 4}">
    					审批通过
    					</c:when>
    				</c:choose>
    		</td>
    		<td class="l-td" style="padding:2px 3px;">
    			<a href="${ctx }/web/oa/leave/info/${cur.id}">详情</a>
    		</td>
    	</tr>
    	</c:forEach>
    	</tbody></table>
	</c:if>
	
	<c:if test="${not empty myDaiban}">
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		与我相关</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
    		
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			序号
    		</td>
    		<td class="l-td" style="padding:7px; width:200px; font-weight:bold;">
    			待办事件名称
    		</td>
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			数目
    		</td>
    		<td class="l-td" style="padding:7px; font-weight:bold;">
    			操作
    		</td>
    	</tr>
		<c:forEach items="${myDaiban}" var="cur" varStatus="st">
			<tr>
    		
    		<td class="l-td" style="padding:7px;">
    			${st.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.name}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			<a href="${ctx }/${cur.url}">前往处理</a>
    		</td>
    		</tr>
    	</c:forEach>
	</tbody></table>
	</c:if>
	
	
	<c:if test="${not empty dlnames}">
	<c:forEach items="${dlnames}" var="cname">
	<c:if test="${not empty dls[cname]}">
	<div class="tableTitle" style="padding:10px 20px;">
		<span >
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		代理${cname }的事件</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
    		
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			序号
    		</td>
    		<td class="l-td" style="padding:7px; width:200px; font-weight:bold;">
    			待办事件名称
    		</td>
    		<td class="l-td" style="padding:7px; width:40px; font-weight:bold;">
    			数目
    		</td>
    		<td class="l-td" style="padding:7px; font-weight:bold;">
    			操作
    		</td>
    	</tr>
		<c:forEach items="${dls[cname]}" var="cur" varStatus="st">
			<tr>
    		
    		<td class="l-td" style="padding:7px;">
    			${st.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.name}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			${cur.count}
    		</td>
    		<td class="l-td" style="padding:7px;">
    			<a href="javascript: pGo('${ctx }/web/dlogin?oid=${cur.dialiId}');">前往处理</a>
    		</td>
    		</tr>
    	</c:forEach>
	</tbody></table>
	</c:if>
	</c:forEach>
	</c:if>
		
	</div>
	
</div>
</div>

<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');
	$('#首页2').addClass('cur');
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$.get('${ctx}/web/oa/kq/today?t='+Math.random(),function(data){
		var d = eval(data);
		
		if(d.ret == -1){
			$('#dkSpan').html('考勤数据服务传输异常，请联系系统管理员。');
		}else{
			var checkin = d.checkin;
			var checkout = d.checkout;
			if(null == checkin){
				$('#dkSpan').html('您今天还没有打卡记录。');
				return;
			}
			
			if(null != checkout){
				$('#dkSpan').html('今日打卡信息，上班打卡：' + checkin + '，最近一次打卡：' + checkout );
				return;
			}

			if("null" != checkin){
				$('#dkSpan').html('今日打卡信息，上班打卡：' + checkin);
			}
		}
		
	});
	
	var dlDiv = $('#dlDiv').html();
	dlDiv = $.trim(dlDiv);
	if(!dlDiv){
		$('#dlDiv').remove();
	}

	$('#coverDiv').click(function(evt){
		//alert(evt.stopPropagation);
		
		evt.preventDefault(); 
		if(evt.stopPropagation){
			evt.stopPropagation();    //标准   
		}else{
       		evt.cancelBubble = true;  //IE   
		}
		return false;
	});

	$('#coverDiv').mouseover(function(evt){
		//alert(evt.stopPropagation);
		evt.preventDefault(); 
		//alert(1);
		if(evt.stopPropagation){
			evt.stopPropagation();    //标准   
		}else{
       		evt.cancelBubble = true;  //IE   
		}
	});
	
	/**/
});

function pGo(url){
	window.location.href=url;
}
</script>

<%@ include file="../public/footer.jsp" %>
