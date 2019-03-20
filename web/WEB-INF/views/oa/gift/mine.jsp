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
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;选择礼品</div>
	<div class="navTitle2" url="${ctx }/web/oa/gift/list"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>	

</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px; margin-bottom:10px; position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
		
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">选择福利</div>
		
		<c:if test="${loginUser.id == evt.managerId}">
		<div class="" style="margin:10px 0 0 0;">
			<a href="${ctx }/web/oa/gift/emps?evtId=${evt.id}" class="lzui-btn lzui-corner-all" style="padding:8px 30px;">查看员工选择详情</a>
		</div>
		</c:if>
		
		<c:if test="${not empty gift}">
		<div class="lzui-tooltips" style="padding-top:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  			您当前选择的礼品是：《${gift.name }》，本次活动截止时间：${evt.endTime}。
  		</div>
  		</c:if>
  		
		<form name="form1" action="${ctx }/web/oa/gift/mine" method="post">
		<input type="hidden" name="evtId" value="${evt.id}"/>
		<div class="lzui-tooltips" style="padding-top:10px; color:#27AE60; font-weight:bold; ">
  			福利品选择如下：
  		</div>
		<div class="lzui-table-wapper" style="margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:0px; width:60px; ">序号</th>
	  				<th style=""> | 描述</th>
	  				<th style=" width:140px;"> | 查看详情</th>
	  				<th style=" width:120px;"> | 选择</th>
	  			</tr>
	  			
	  			
	  			<c:forEach items="${gifts }" var="cur" varStatus="st">
		  		<tr class="lzui-td ttd" id="t${st.count}">
			    	<td style="padding-left:0px;">${st.count}</td>
			    	<td>
			    		${cur.name }
			    	</td>
			    	<td>
			    		<a target="_blank" href="${ctx }/web/oa/gift/${cur.id}.pdf">点击查看详情</a>
			    	</td>
			    	<td>
						<input class="cb" name="giftId" value="${cur.id }" id="${st.count}" navaVal="${cur.name}" type="checkbox"/>
					</td>
			    </tr>
			    </c:forEach>
			    
			    </table>
			</div>
			
			<c:if test="${!over}">
			
			<c:if test="${empty gift}">
			<div class="lzui-table-wapper" style="margin-top:12px; margin-bottom:15px;">
			 	<a href="javascript:submitGift();" class="lzui-btn2" 
			 	style="height:30px; line-height:30px; float:left; width:100px;font-weight:bold; font-size:15px;">
			 	提交我的选择
			 	</a>
			</div>
			</c:if>
			
			<c:if test="${not empty gift}">
			<div class="lzui-table-wapper" style="margin-top:12px; margin-bottom:15px;">
			 	<a href="javascript:submitGift();" class="lzui-btn2" 
			 	style="height:30px; line-height:30px; float:left; width:100px;font-weight:bold; font-size:15px;">
			 	修改我的选择
			 	</a>
			</div>
			</c:if>
			
			</c:if>
			</form>
		
	</div>
</div>
</div>
<script>
$(function(){
	$('#个人中心').addClass('ui-tabs-current');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	//ie兼容
	$('.lzui-td input').click(function(evt){
		evt.stopPropagation();
	});

	$('.lzui-td a').click(function(evt){
		evt.stopPropagation();
	});

	$('.cb').click(function(evt){
		var item = $(this);
		var id = item.attr('id');
		var ic =  item.prop('checked');
		if(ic){
			$('.cb').prop('checked',false);
			$('.ttd').removeClass('selectBak');
			item.prop('checked',true);
			$('#t'+id).addClass('selectBak');
		}else{
			item.prop('checked',false);
			$('#t'+id).removeClass('selectBak');
		}
	});

	$('.ttd').click(function(evt){
		var item = $(this).find("input[name='giftId']");
		var id = item.attr('id');
		var ic =  item.prop('checked');
		if(ic){
			item.prop('checked',false);
			$('#t'+id).removeClass('selectBak');
		}else{
			$('.lzui-td input[name="giftId"]').prop('checked',false);
			$('.ttd').removeClass('selectBak');
			item.prop('checked',true);
			$('#t'+id).addClass('selectBak');
		}
	});
});

function submitGift(){
	var cds = $('.lzui-td input:checked');
	if(!cds || cds.length<1){
		alert("请选择一个福利品");
		return;
	}

	if(cds.length>1){
		alert("您只能选择一个福利品");
		return;
	}

	var cd = cds[0];

	var st = "您选择了《" + $(cd).attr('navaVal') + "》，确定提交？";
	var flag = confirm(st);
	if(flag){
		document.form1.submit();
	}
}

</script>
</body>
</html>