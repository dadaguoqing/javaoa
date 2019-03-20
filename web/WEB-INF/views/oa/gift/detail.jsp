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
	
	<div class="navTitle2 cur" url="javascript:;"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;员工详情</div>
	<div class="navTitle2" url="${ctx }/web/oa/gift/emps?evtId=${evt.id}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;返回上级</div>	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		
		
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">员工详情</div>
		
		
		
		
  		<div class="lzui-tooltips" style="padding-top:10px; color:#27AE60; font-weight:bold; ">
  			选择《${gift.name }》的员工：
  		</div>
		<div class="lzui-table-wapper" style="margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:160px;">序号</th>
	  				<th style=""> | 描述</th>
	  				<th style=" width:300px;"> | 员工姓名</th>
	  			</tr>
	  			
	  			
	  			<c:forEach items="${gifts }" var="cur" varStatus="st">
		  		<tr class="lzui-td ttd" id="t${st.count}">
			    	<td>${st.count}</td>
			    	<td>
			    		${cur.name }
			    	</td>
			    	<td>
			    		${cur.empName }
					</td>
			    </tr>
			    </c:forEach>
			    
			    </table>
			</div>
			
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

	
});

</script>
</body>
</html>