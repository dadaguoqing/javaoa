<%@ page language="java" import="java.util.*,com.hj.oa.bean.Role,com.hj.util.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<<style>
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
	
	<%@ include file="../../public/indexMenu.jsp" %>	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">客户订餐</div>
		
		<c:if test="${not empty msg}">
		<div class="lzui-error">${msg }</div>
		</c:if>
		
		<c:if test="${cancelDc == 0}">
		<div class="lzui-error">订餐已经结束，您的订餐不能再修改</div>
		</c:if>
		
		<c:if test="${dc==null}">
			<div class="lzui-error">今日订餐还没有开始。</div>
		</c:if>
		
		<c:if test="${dc!=null && dc.status >= 1}">
			<div class="lzui-error">今日订餐已经结束。</div>
		</c:if>
		
		<c:if test="${empDc!=null && empDc.status == 1}">
		<c:if test="${empty empCaidans}">
		<div class="lzui-tooltips" style="padding-top:10px; color:#C0392B; font-weight:bold; font-size:15px;">
  			您今天没有为客户订餐。
  		</div>
  		</c:if>
		<c:if test="${not empty empCaidans}">
		<div class="lzui-tooltips" style="padding-top:10px; color:#C0392B; font-weight:bold; ">
  			您今天为客户定了：
  		</div>
		<div class="lzui-table-wapper" style="margin-bottom:15px; margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:160px;">商家名称</th>
	  				<th style=""> | 描述</th>
	  				<th style=" width:80px;"> | 价格</th>
	  				<th style=" width:80px;"> | 数量</th>
	  				<th style=" width:100px;"> | 金额</th>
	  				<c:if test="${dc.status == 0}">	
	  				<th style=" width:100px;"> | 操作</th>
	  				</c:if>
	  			</tr>
	  			<c:forEach items="${empCaidans}" var="cd">
		  		<tr class="lzui-td" >
			    	<td>${cd.providerName }</td>
			    	<td>
			    		${cd.description }
			    	</td>
			    	<td>${cd.price }元</td>
			    	<td>${cd.num}份</td>
			    	<td>
			    		${cd.price }元
					</td>
					<c:if test="${dc.status == 0}">	
					<td>
			    		<a href="javascript:del(${cd.cdId})">删除</a>
					</td>
					</c:if>
			    </tr>
			    </c:forEach>
			    <tr class="lzui-td"  style="color:red;">
			    	<td>${total.providerName }</td>
			    	<td>
			    		&nbsp;
			    	</td>
			    	<td>&nbsp;</td>
			    	<td>${total.num}份</td>
			    	<td>
			    		${total.price }元
					</td>
					<c:if test="${dc.status == 0}">	
					<td>
			    		&nbsp;
			    	</td>
			    	</c:if>
			    </tr>
			    </table>
			</div>
		</c:if>
		
		</c:if>
		
		
		
		<c:if test="${empDc!=null && dc.status == 0}">
		<form name="form1" action="${ctx }/web/oa/dc/empDc" method="post">
		<input type="hidden" name="empDcId" value="${empDc.id}"/>
		<input type="hidden" name="cdId" id="cdId"/>
		<input type="hidden" name="num" id="num"/>
		<div class="lzui-tooltips" style="padding-top:10px; color:#27AE60; font-weight:bold; ">
  			今日订餐菜单：
  		</div>
		<div class="lzui-table-wapper" style="margin-top:0px;">
	  			<table class="lzui-table" style="width:100%;">
	  			<tr>
	  				<th style="padding-left:15px; width:160px;">商家名称</th>
	  				<th style=""> | 描述</th>
	  				<th style=" width:80px;"> | 价格</th>
	  				<th style=" width:80px;"> | 数量</th>
	  				<th style=" width:120px;"> | 选择</th>
	  			</tr>
	  			
	  			<c:forEach items="${cds }" var="cur" varStatus="st">
		  		<tr class="lzui-td ttd" id="t${st.count}">
			    	<td>${cur.providerName }</td>
			    	<td>
			    		${cur.description }
			    	</td>
			    	<td>${cur.price }元</td>
			    	<td><input type="text" value="1" id="num${st.count}" style="width:14px;"/>份</td>
			    	<td>
			    		<input type="hidden" id="cd${st.count}" value="${cur.id }"/>
			    		<input type="hidden" id="price${st.count}" value="${cur.price }"/>
						<input class="cb" name="cbt" id="${st.count}" type="checkbox"/>
					</td>
			    </tr>
			    </c:forEach>
			    
			    </table>
			</div>
			
			<div class="lzui-table-wapper" style="margin-top:12px;">
			 	<a href="javascript:submitDingCan();" class="lzui-btn2" 
			 	style="height:30px; line-height:30px; float:left; width:100px;font-weight:bold; font-size:15px;">
			 	提交订单
			 	</a>
			 	<c:if test="${empDc.status==0}">
			 	<a href="javascript:CancelDingCan(${empDc.id});" class="lzui-btn" 
			 	style="height:30px; line-height:30px; margin-left:15px; float:left; width:100px;font-weight:bold; font-size:15px;">
			 	今天不定
			 	</a>
			 	</c:if>
			</div>
			</form>
		</c:if>
	</div>
</div>
</div>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#订餐管理').addClass('cur');	
	
	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	//ie兼容
	$('.lzui-td input').click(function(evt){
		evt.stopPropagation();
	});

	$('.cb').click(function(evt){
		var item = $(this);
		var id = item.attr('id');
		var ic =  item.prop('checked');
		if(ic){
			//$('.cb').prop('checked',false);
			//$('.ttd').removeClass('selectBak');
			item.prop('checked',true);
			$('#t'+id).addClass('selectBak');
		}else{
			item.prop('checked',false);
			$('#t'+id).removeClass('selectBak');
		}
	});

	$('.ttd').click(function(evt){
		var item = $(this).find("input[name='cbt']");
		var id = item.attr('id');
		var ic =  item.prop('checked');
		if(ic){
			item.prop('checked',false);
			$('#t'+id).removeClass('selectBak');
		}else{
			//$('.lzui-td input[name="cbt"]').prop('checked',false);
			//$('.ttd').removeClass('selectBak');
			item.prop('checked',true);
			$('#t'+id).addClass('selectBak');
		}
	});

});

function submitDingCan(){
	var cds = $('.lzui-td input:checked');
	if(!cds || cds.length<1){
		alert("请选择菜单");
		return;
	}

	var numAll = 0;
	var priceAll = 0;

	var cdIds = "";
	var nums = "";
	
	for( var i=0; i<cds.length; i++){
		var cd = cds[i];
		//alert(cd);
		//alert("id:"+cd.attr('id'));
		var id  = $(cd).attr('id');
		//alert(id);
		var cdId = $('#cd'+id).val();
		cdIds = cdIds+cdId+",";
		

		var num = $('#num'+id).val();
		nums = nums + num+",";
		
		var price = $('#price'+id).val();

		//alert("菜单:"+cdId + ",数量:"+num);

		var reg = new RegExp("^[1-9][0-9]*$");
		if(!reg.test(num)){
			alert('请正确填写数量（数量必须是正整数）');
			return;
		}

		var p = num * price;
		numAll += parseInt(num);
		priceAll += p;
	}
	
	$('#cdId').val(cdIds);
	$('#num').val(nums);
	
	var st = "您本次订餐一共"+numAll+"份，消费" + priceAll + "元。确定提交？";
	var flag = confirm(st);
	if(flag){
		document.form1.submit();
	}
}

function CancelDingCan(id){
	var flag = confirm("您确定今天不订饭？");
	if(flag){
		document.location.href = "${ctx}/web/oa/dc/empCancelDc?empDcId="+id;
	}
}

function del(cdId){
	document.location.href = "${ctx}/web/oa/dc/cancelCd?empId=0&cdId="+cdId;
}

</script>
</body>
</html>