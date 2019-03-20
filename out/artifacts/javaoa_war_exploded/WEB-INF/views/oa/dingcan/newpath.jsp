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
	
	<%@ include file="../../public/indexMenu.jsp" %>	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	
	<div style="margin-top:10px;position:relative; line-height:20px; height:22px; border-bottom:1px solid #ccc;">
		<div style="width:72px; color:#34495E;border-bottom:2px solid #27AE60; padding-left:10px; font-size:14px; font-weight:bold;">今日订餐</div>
		
		
		
		
	
		
		
			<div class="lzui-error">
				<c:out value="${ zeroth_cat_message}" />
				<c:out value="${ total_cost}" />	
			</div>
		
		
		
		
		
		
</div>
</div>
<script>
$(function(){
	$('#首页').addClass('ui-tabs-current');	
	$('#今日订餐').addClass('cur');	
	
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
	//zeroth cat
	var desList = "";
	
	var proList = "";
	
	var priceList = "";
	//z_c)
	
	for( var i=0; i<cds.length; i++){
		
		
		var cd = cds[i];
		//alert(cd);
		//alert("id:"+cd.attr('id'));
		var id  = $(cd).attr('id');
		//alert(id);
		var cdId = $('#cd'+id).val();
		cdIds = cdIds+cdId+",";
		
		var pdId = $('#pd'+id).val();
		
		var price = $('#price'+id).val();
		
		//zeroth cat
		desList = desList+cdId+",";
		
		proList = proList+pdId+",";
		
		priceList = priceList+price+",";
		//z_c
		
		var num = $('#num'+id).val();
		nums = nums + num+",";


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
	//zeroth_cat
	
	$('#des').val(desList);
	
	$('#prov').val(proList);
	
	$('#pri').val(priceList);
	//z_c
	var st = "您本次订餐一共"+numAll+"份，消费" + priceAll + "元。确定提交？";
	var flag = confirm(st);
	if(flag){
		document.form1.submit();
	}
}


</script>
</body>
</html>