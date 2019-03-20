<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp" %>

<style>
<!--
.shortInput {width:40px;}
.redInput {border:1px solid red;}
-->
</style>

<div style="position:absolute; margin:0px; padding:0px; border:none; top:60px; left:0px; bottom:0px;*height:expression(eval(document.documentElement.clientHeight-60)); width:100%;">
<!-- fixed navigater-->
<div id="theNavigater">
	<div style="position:absolute;left:2px; top:2px;"><img src="${ctx }/resources/images/previous.png" id="hiddenNavImg"/></div>
	<div class="navTitle">系统导航</div>
	
	<c:forEach items="${loginUserMenuMap['4']}" var="cur">
	<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}"><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;${cur.name }</div>
	</c:forEach>	
</div>
<div id="theNavigater2">
	<img src="${ctx }/resources/images/next.png"/>点击展开导航栏
</div>

<div style="margin: 10px 5px 0 195px;">
	<%@ include file="../../public/hello.jsp" %>
	
	<c:if test="${not empty msg}">
	<div class="lzui-error">${msg }</div>
	</c:if>
	
	<div class="lzui-tooltips" style="margin:10px; color:#C0392B; font-weight:bold; font-size:14px;">
  		<a style="color:#C0392B;" href="${ctx}/web/oa/lab/myPcbSqRecord">点击查看申请记录</a>
  	</div>
	
	<form name="form1" action="${ctx }/web/oa/lab/pcbSq" method="post">
	<input type="hidden" value="${submitCode}" name="submitCode"/>
	<div style="border:1px solid #2191C0; margin-top:10px;">
	<div class="main-header" id="id1">
		<span style="color:#eee;">${compName }PCB加工工艺要求说明书</span>
	</div>
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		基本信息</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:120px;">文件名称：</td>	
			<td style="width:90px;">
				<input name="fileName" id="fileName" onblur="checkFileName();"/>
				<span id="fileNameId" style="color:red; font-weight:bold;"></span>
			</td>	
			<td class="title" style="width:90px;">投板时间：</td>
			<%
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
				Date n = new Date();
				String now = sdf.format(n);
				pageContext.setAttribute("now",now);
			%>
			<td  style=" width:200px;">
				<input type="hidden" name="createTime" value="${now }"/>
				${now }
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">文档目录：</td>	
			<td style="border-right:none;" colspan="3">
				<input name="filePath" id="filePath" style="width:200px;"/>
			</td>	
			
			<%-- 
			<td class="title" style="border-left:none; width:90px;">订单编号：</td>	
			<td style="width:90px;">
				<input name="ddbh" id="ddbh"/>
			</td>	
			--%>
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:120px;">PCB加工工艺要求：</td>	
			<td style="border-right:none;" colspan="7">
				<select name="gyyq" id="gyyq">
					<option value="新投（新文件）">新投（新文件）</option>
					<option value="加做 （文件与上一版完全相同，以下内容只须写数量和交货日期。）">加做 （文件与上一版完全相同，以下内容只须写数量和交货日期。）</option>
					<option value="改版 （局部修改请说明修改部位；新文件覆盖旧文件请写明新文件名。）">改版 （局部修改请说明修改部位；新文件覆盖旧文件请写明新文件名。）</option>
				</select>
			</td>	
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		详细参数</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:105px;">数量：</td>	
			<td style="">
				<input name="numSet" id="numSet" class="shortInput"/>set &nbsp;&nbsp;
				<input name="numUnit" id="numUnit" class="shortInput"/>unit
			</td>	
			<td class="title" style="width:105px;">层数：</td>	
			<td style="">
				<select name="cs" id="cs">
					<option value="双层">双层</option> <option value="单层">单层</option>
					<option value="4层">4层</option> <option value="6层">6层</option>
					<option value="8层">8层</option> 
					<option value="10层">10层</option> <option value="12层">12层</option>
					<option value="14层">14层</option> <option value="16层">16层</option>
					<option value="18层">18层</option> <option value="20层">20层</option>
				</select>
				
				<a href="javascript:showCcpl();" id="showCcpl">查看层次排列</a>
			</td>	
			<td class="title" style="border-left:none; width:120px;">尺寸：</td>	
			<td style="border-right:none;">
				<input name="ccChang" id="ccChang" class="shortInput"/>mm &nbsp;*&nbsp;
				<input name="ccKuang" id="ccKuang" class="shortInput"/>mm
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">成品板厚：</td>	
			<td style="">
				<select name="cpbh" id="cpbh">
					<option value="1.6mm">1.6mm</option>
					<option value="0.4mm">0.4mm</option> <option value="0.6mm">0.6mm</option>
					<option value="0.8mm">0.8mm</option> <option value="1.0mm">1.0mm</option>
					<option value="1.2mm">1.2mm</option> <option value="1.4mm">1.4mm</option>
					<option value="2.0mm">2.0mm</option>
					<option value="2.5mm">2.5mm</option> <option value="3.0mm">3.0mm</option>
					<option value="4.0mm">4.0mm</option> <option value="5.0mm">5.0mm</option>
					<option value="6.0mm">6.0mm</option>
				</select>
			</td>	
			<td class="title" style="width:90px;">材料：</td>	
			<td style="">
				<select name="cl" id="cl">
					<option value="FR-4">FR-4</option> <option value="Rogers 4003">Rogers 4003</option>
					<option value="Rogers 4350">Rogers 4350</option> <option value="Ro 系列">Ro 系列</option>
					<option value="Taconic 系列">Taconic 系列</option> <option value="铝基材料">铝基材料</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">成品铜铂厚度：</td>	
			<td style="border-right:none;">
				内层 <select name="tbNei" id="tbNei">
					<option value="1 OZ">1 OZ</option> <option value="0.5 OZ">0.5 OZ</option>
					<option value="2 OZ">2 OZ</option> <option value="3 OZ">3 OZ</option>
					<option value="4 OZ">4 OZ</option> <option value="5 OZ">5 OZ</option>
					<option value="6 OZ">6 OZ</option> 
				</select>
				&nbsp;&nbsp;
				外层 <select name="tbWai" id="tbWai">
					<option value="1 OZ">1 OZ</option> <option value="0.5 OZ">0.5 OZ</option>
					<option value="2 OZ">2 OZ</option> <option value="3 OZ">3 OZ</option>
					<option value="4 OZ">4 OZ</option> <option value="5 OZ">5 OZ</option>
					<option value="6 OZ">6 OZ</option> 
				</select>
			</td>	
		</tr>
		
		<tr>
			
			<td class="title" style="border-left:none; width:90px;">阻 焊：</td>	
			<td style="">
				<select name="zh" id="zh">
					<option value="双面">双面</option> <option value="单面">单面</option>
				</select>
				&nbsp;&nbsp;
				颜色： <select name="zhColor" id="zhColor">
					<option value="浅绿色">浅绿色</option> <option value="蓝色">蓝色</option>
					<option value="红色">红色</option> <option value="黄色">黄色</option>
					<option value="黑色">黑色</option> <option value="白色">白色</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">字符：</td>	
			<td style="">
				<select name="zf" id="zf">
					<option value="双面">双面</option> <option value="单面">单面</option>
				</select>
				&nbsp;&nbsp;
				颜色： <select name="zfColor" id="zfColor">
					<option value="白色">白色</option> <option value="黑色">黑色</option> 
					<option value="红色">红色</option> <option value="黄色">黄色</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">测试通断：</td>	
			<td style="border-right:none;">
			 	<select name="cstd" id="cstd">
					<option value="是">是</option> <option value="否">否</option>
				</select>
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">阻外形加工方式：</td>	
			<td style="">
				<select name="wxjgfs" id="wxjgfs">
					<option value="分开交货">分开交货</option> <option value="数控铣">数控铣</option>
					<option value="数控铣加V-CUT">数控铣加V-CUT</option> <option value="邮票孔联接">邮票孔联接</option>
					<option value="桥连">桥连</option> <option value="冲模">冲模</option>
				</select>
				
				
			</td>	
			<td class="title" style="border-left:none; width:90px;">金手指要求：</td>	
			<td style="">
				<select name="jszyq" id="jszyq">
					<option value="无">无</option> <option value="倒角30度">倒角30度</option>
					<option value="倒角45度">倒角45度</option> <option value="倒角60度">倒角60度</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">电测报告：</td>	
			<td style="border-right:none;">
			 	<select name="dcbg" id="dcbg">
					<option value="是">是</option> <option value="否">否</option>
				</select>
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">阻抗测试报告：</td>	
			<td style="">
				<select name="zkcsbg" id="zkcsbg">
					<option value="是">是</option> <option value="否">否</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">成品检验报告：</td>	
			<td style="">
				<select name="cpjcbg" id="cpjcbg">
					<option value="是">是</option> <option value="否">否</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">过孔是否覆盖阻焊：</td>	
			<td style="border-right:none;">
			 	<select name="fgzh" id="fgzh">
					<option value="是">是</option> <option value="否">否</option>
					<option value="全板过孔塞油">全板过孔塞油</option>
				</select>
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">表面涂层：</td>	
			<td style="">
				<select name="bmtc" id="bmtc">
					<option value="喷锡">喷锡</option> <option value="全板镀金">全板镀金</option> 
					<option value="全板沉金">全板沉金</option> <option value="防氧化处理">防氧化处理</option> 
					<option value="喷锡+金手指">喷锡+金手指</option> <option value="裸铜板">裸铜板</option> 
					<option value="无铅喷锡">无铅喷锡</option> <option value="镀镍">镀镍</option> 
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">表面涂层厚度：</td>	
			<td style="">
				<select name="bmtchd" id="bmtchd">
					 <option value="1~3微英寸">1~3微英寸</option> <option value="3~5微英寸">3~5微英寸</option>
					 <option value="5~10微英寸">5~10微英寸</option> <option value="10~20微英寸">10~20微英寸</option>
					 <option value="20~240微英寸">20~240微英寸</option>
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">阻抗描述：</td>	
			<td style="border-right:none;">
			 	<select name="kzms" id="kzms">
					<option value="无">无</option> <option value="见阻抗说明文档">见阻抗说明文档</option>
				</select><br/>
				<span id="kzmsId" style="color:red;"></span>
			</td>	
		</tr>
	</tbody></table>
	<%-- 
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		备注信息</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea name="bz" id="bz" style="width:50%; height:120px; padding:5px 10px;">${leave.content }</textarea>
				<span style="color:red;">可不填，不超过150个字符，ctrl+Enter提交</span>
			</td>	
		</tr>
	</tbody></table>
	--%>
	</div>
	<div class="" style="margin-top:10px; margin-bottom:20px;">
		<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交申请</a>
	</div>
	
	</form>
	</div>
	
	<div id="2Layers" style="display:none;">
		<div style="border:1px solid #2191C0; margin-top:0px; width:675px;">
		<div class="tableTitle" style="padding:10px 20px;">
			<span>
			<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
			2层板Gerber文件层详细列表：</span>
		</div>
		<table class="table1" ><tbody>
			<tr>
				<td style="border-left:none;">No.</td>	
				<td style="">Document Name</td>	
				<td style="border-right:none;">Fabrication Layer</td>	
			</tr>
			<tr><td  style='border-left:none;'>1</td><td  style=''>art001.pho</td><td  style='border-right:none;'>Top Trace</td></tr>
			<tr><td  style='border-left:none;'>2</td><td  style=''>art004.pho</td><td  style='border-right:none;'>Bottom Trace</td></tr>
			<tr><td  style='border-left:none;'>3</td><td  style=''>dd001024.pho</td><td  style='border-right:none;'>Drill Drawing</td></tr>
			<tr><td  style='border-left:none;'>4</td><td  style=''>sm001021.pho</td><td  style='border-right:none;'>Solder Mask Top</td></tr>
			<tr><td  style='border-left:none;'>5</td><td  style=''>sm004028.pho</td><td  style='border-right:none;'>Solder Mask Bottom</td></tr>
			<tr><td  style='border-left:none;'>6</td><td  style=''>smd001023.pho</td><td  style='border-right:none;'>Paste Mask Top</td></tr>
			<tr><td  style='border-left:none;'>7</td><td  style=''>smd004022.pho</td><td  style='border-right:none;'>Paste Mask Bottom</td></tr>
			<tr><td  style='border-left:none;'>8</td><td  style=''>ssb004029.pho</td><td  style='border-right:none;'>Silkscreen Bottom</td></tr>
			<tr><td  style='border-left:none;'>9</td><td  style=''>sst001026.pho</td><td  style='border-right:none;'>Silkscreen Top</td></tr>
			<tr><td  style='border-left:none;'>10</td><td  style=''>drl001.drl</td><td  style='border-right:none;'>Drill</td></tr>
			
		</tbody></table>
		</div>
	</div>
	
	<div id="4Layers" style="display:none;">
		<div style="border:1px solid #2191C0; margin-top:0px; width:675px;">
		<div class="tableTitle" style="padding:10px 20px;">
			<span>
			<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
			4层板Gerber文件层详细列表：</span>
		</div>
		<table class="table1" ><tbody>
			<tr>
				<td style="border-left:none;">No.</td>	
				<td style="">Document Name</td>	
				<td style="border-right:none;">Fabrication Layer</td>	
			</tr>
			<tr><td  style='border-left:none;'>1</td><td  style=''>art001.pho</td><td  style='border-right:none;'>Top Trace</td></tr>
			<tr><td  style='border-left:none;'>2</td><td  style=''>art002.pho</td><td  style='border-right:none;'>Ground</td></tr>
			<tr><td  style='border-left:none;'>3</td><td  style=''>art003.pho</td><td  style='border-right:none;'>Power</td></tr>
			<tr><td  style='border-left:none;'>4</td><td  style=''>art004.pho</td><td  style='border-right:none;'>Bottom Trace</td></tr>
			<tr><td  style='border-left:none;'>5</td><td  style=''>dd001024.pho</td><td  style='border-right:none;'>Drill Drawing</td></tr>
			<tr><td  style='border-left:none;'>6</td><td  style=''>sm001021.pho</td><td  style='border-right:none;'>Solder Mask Top</td></tr>
			<tr><td  style='border-left:none;'>7</td><td  style=''>sm004028.pho</td><td  style='border-right:none;'>Solder Mask Bottom</td></tr>
			<tr><td  style='border-left:none;'>8</td><td  style=''>smd001023.pho</td><td  style='border-right:none;'>Paste Mask Top</td></tr>
			<tr><td  style='border-left:none;'>9</td><td  style=''>smd004022.pho</td><td  style='border-right:none;'>Paste Mask Bottom</td></tr>
			<tr><td  style='border-left:none;'>10</td><td  style=''>ssb004029.pho</td><td  style='border-right:none;'>Silkscreen Bottom</td></tr>
			<tr><td  style='border-left:none;'>11</td><td  style=''>sst001026.pho</td><td  style='border-right:none;'>Silkscreen Top</td></tr>
			<tr><td  style='border-left:none;'>12</td><td  style=''>drl001.drl</td><td  style='border-right:none;'>Drill</td></tr>
				
		</tbody></table>
		</div>
	</div>
	
	<div id="6Layers" style="display:none;">
		<div style="border:1px solid #2191C0; margin-top:0px; width:675px;">
		<div class="tableTitle" style="padding:10px 20px;">
			<span>
			<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
			6层板Gerber文件层详细列表：</span>
		</div>
		<table class="table1" ><tbody>
			<tr>
				<td style="border-left:none;">No.</td>	
				<td style="">Document Name</td>	
				<td style="border-right:none;">Fabrication Layer</td>	
			</tr>
			<tr><td  style='border-left:none;'>1</td><td  style=''>art001.pho</td><td  style='border-right:none;'>Top Trace</td></tr>
			<tr><td  style='border-left:none;'>2</td><td  style=''>art002.pho</td><td  style='border-right:none;'>Ground</td></tr>
			<tr><td  style='border-left:none;'>3</td><td  style=''>art003.pho</td><td  style='border-right:none;'>S1</td></tr>
			<tr><td  style='border-left:none;'>4</td><td  style=''>art004.pho</td><td  style='border-right:none;'>PWR</td></tr>
			<tr><td  style='border-left:none;'>5</td><td  style=''>art005.pho</td><td  style='border-right:none;'>S2</td></tr>
			<tr><td  style='border-left:none;'>6</td><td  style=''>art006.pho</td><td  style='border-right:none;'>Bottom</td></tr>
			<tr><td  style='border-left:none;'>7</td><td  style=''>sst001026.pho</td><td  style='border-right:none;'>Silkscreen Top</td></tr>
			<tr><td  style='border-left:none;'>8</td><td  style=''>ssb006029.pho</td><td  style='border-right:none;'>Silkscreen Bottom</td></tr>
			<tr><td  style='border-left:none;'>9</td><td  style=''>sm001021.pho</td><td  style='border-right:none;'>Solder Mask Top</td></tr>
			<tr><td  style='border-left:none;'>10</td><td  style=''>sm006028.pho</td><td  style='border-right:none;'>Solder Mask Bottom</td></tr>
			<tr><td  style='border-left:none;'>11</td><td  style=''>dd001024.pho</td><td  style='border-right:none;'> </td></tr>
			<tr><td  style='border-left:none;'>12</td><td  style=''>dh001.drl</td><td  style='border-right:none;'> </td></tr>
			<tr><td  style='border-left:none;'>13</td><td  style=''>smd001023.pho</td><td  style='border-right:none;'>Paste Mask Top</td></tr>
			<tr><td  style='border-left:none;'>14</td><td  style=''>smd006022.pho</td><td  style='border-right:none;'>Paste Mask Bottom</td></tr>

							
		</tbody></table>
		</div>
	</div>
	
	<div id="cfmlay" style="display:none;">
		<div style="border:1px solid #2191C0; margin-top:0px; width:675px;">
		<div class="tableTitle" style="padding:10px 20px;">
			<span>
			<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
			申请信息详情：</span>
		</div>
		<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:120px;">文件名称：</td>	
			<td style="width:90px;" >
				<span id="sbFileName"></span>
			</td>	
			<td class="title" style="width:90px;">投板时间：</td>
			
			<td  style="border-left:none; width:200px;">	
				<span id="sbCreateTime">${now }</span>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">文档目录：</td>	
			<td style="width:90px;">
				<span id="sbFilePath"></span>
			</td>	
			
		</tr>
		<tr>
			<td class="title" style="border-left:none; width:120px;">PCB加工工艺要求：</td>	
			<td style="" colspan="5">
				<span id="sbGyyq"></span>
			</td>	
			
		</tr>
	</tbody></table>
	
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		详细参数</span>
	</div>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:105px;">数量：</td>	
			<td style="">
				<span id="sbNumSet"></span>
				set &nbsp;&nbsp;
				<span id="sbNumUnit"></span>
				unit
			</td>	
			<td class="title" style="width:105px;">层数：</td>	
			<td style="">
				<span id="sbCs"></span>
			</td>	
			<td class="title" style="border-left:none; width:120px;">尺寸：</td>	
			<td style="border-right:none;">
				<span id="sbCcChang"></span>
				mm &nbsp;*&nbsp;
				<span id="sbCcKuang"></span>mm
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">成品板厚：</td>	
			<td style="">
				<span id="sbCpbh"></span>
			</td>	
			<td class="title" style="width:90px;">材料：</td>	
			<td style="">
				<span id="sbCl"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">成品铜铂厚度：</td>	
			<td style="border-right:none;">
				内层：<span id="sbTbNei"></span>
				<br/>
				外层：<span id="sbTbWai"></span>
			</td>	
		</tr>
		
		<tr>
			
			<td class="title" style="border-left:none; width:90px;">阻 焊：</td>	
			<td style="">
				<span id="sbZh"></span>
				<br/>
				颜色： <span id="sbZhColor"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">字符：</td>	
			<td style="">
				<span id="sbZf"></span>
				<br/>
				颜色： <span id="sbZfColor"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">测试通断：</td>	
			<td style="border-right:none;">
				<span id="sbCstd"></span>
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">阻外形加工方式：</td>	
			<td style="">
				<span id="sbWxjgfs"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">金手指要求：</td>	
			<td style="">
				<span id="sbJszyq"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">电测报告：</td>	
			<td style="border-right:none;">
				<span id="sbDcbg"></span>
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">阻抗测试报告：</td>	
			<td style="">
				<span id="sbZkcsbg"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">成品检验报告：</td>	
			<td style="">
				<span id="sbCpjcbg"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">过孔是否覆盖阻焊：</td>	
			<td style="border-right:none;">
				<span id="sbFgzh"></span>
			</td>	
		</tr>
		
		<tr>
			<td class="title" style="border-left:none; width:90px;">表面涂层：</td>	
			<td style="">
				<span id="sbBmtc"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">表面涂层厚度：</td>	
			<td style="">
				<span id="sbBmtchd"></span>
			</td>	
			<td class="title" style="border-left:none; width:90px;">阻抗描述：</td>	
			<td style="border-right:none;">
				<span id="sbKzms"></span>
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
	$('a#OA申请').addClass('ui-tabs-current');	
	$('#加工申请').addClass('cur');	

	$('.navTitle2').click(function(){
		var url = $(this).attr('url');
		if(url)
			document.location.href=url;
	});

	$('#kzms').change(function(){
		var v = $(this).val();
		//alert(v);
		if(v == '见阻抗说明文档' ){
			$('#kzmsId').html("请确保文档目录下有阻抗说明文档");
			
			//$('#kzms').removeClass('redInput');
		}else{
			$('#kzmsId').html("");
			//$('#kzms').addClass('redInput');
		}
	});

	$('#cs').change(function(){
		showCcpl();
	});
});

function checkFileName(){
	var fileName = $('#fileName').val();

	$.post('${ctx}/web/oa/lab/ajaxPcbName?hb?t='+Math.random(),{fileName:fileName},function(data){
		
		//alert(data);
		var d = eval(data);
		if(d.ret == 'ok'){
			//window.location.href
			$('#fileNameId').html("");
			$('#fileName').removeClass('redInput');
		}else{
			$('#fileNameId').html("该文件名已被使用");
			$('#fileName').addClass('redInput');
		}
	});
	
	
}

function showCcpl(){
	var v = $('#cs').val();
	if(v == '双层' ){
		showCcpl2(2);
	}else if(v == '4层' ){
		showCcpl2(4);
	}if(v == '6层' ){
		showCcpl2(6);
	}
}

function showCcpl2(tid){
	clearInterval(itv);
	
	$.layer({
		shade : [0.7, '#000'],
	    title: '层次排列',
	    type: 1,
	    closeBtn: [1, true],
	    border: [3],
	    shadeClose: true,
	    page: {dom : '#' + tid + 'Layers'},
	    area: ['auto', 'auto'],
	    close: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    }
	});
}

function smtCofirm(){

	$('#sbFileName').html($('#fileName').val());
	$('#sbFilePath').html($('#filePath').val());
	$('#sbGyyq').html($('#gyyq').val());
	$('#sbNumSet').html($('#numSet').val());
	$('#sbNumUnit').html($('#numUnit').val());
	$('#sbCs').html($('#cs').val());
	$('#sbCcChang').html($('#ccChang').val());
	$('#sbCcKuang').html($('#ccKuang').val());
	$('#sbCpbh').html($('#cpbh').val());
	$('#sbCl').html($('#cl').val());
	$('#sbTbNei').html($('#tbNei').val());
	$('#sbTbWai').html($('#tbWai').val());
	$('#sbZh').html($('#zh').val());
	$('#sbZhColor').html($('#zhColor').val());
	$('#sbZf').html($('#zf').val());
	$('#sbZfColor').html($('#zfColor').val());
	$('#sbCstd').html($('#cstd').val());
	$('#sbCstdType').html($('#cstdType').val());
	$('#sbWxjgfs').html($('#wxjgfs').val());
	$('#sbJszyq').html($('#jszyq').val());
	$('#sbDcbg').html($('#dcbg').val());
	$('#sbZkcsbg').html($('#zkcsbg').val());
	$('#sbCpjcbg').html($('#cpjcbg').val());
	$('#sbFgzh').html($('#fgzh').val());
	$('#sbBmtc').html($('#bmtc').val());
	$('#sbBmtchd').html($('#bmtchd').val());
	$('#sbKzms').html($('#kzms').val());
	
	clearInterval(itv);
	
	$.layer({
		shade : [0.7, '#000'],
	    title: '请确认您要提交的信息',
	    btns:2,
	    btn: ['确定', '取消'],
	    type: 1,
	    closeBtn: [1, true],
	    border: [3],
	    shadeClose: true,
	    page: {dom : '#cfmlay'},
	    area: ['auto', '490px'],
	    no: function(index){
	    	itv = setInterval(ajaxGetDb,len);
	    },yes: function(index){
	    	document.form1.submit();
		}
	});
}

function submitForm(){
	var fileName = $('#fileName').val();
	var filePath = $('#filePath').val();

	var numSet = $('#numSet').val();
	var numUnit = $('#numUnit').val();
	var ccChang = $('#ccChang').val();
	var ccKuang = $('#ccKuang').val();

	var fnm = $('#fileNameId').html();

	if(fnm){
		alert('文件名称不能重复');
		return;
	}

	if(!fileName){
		alert('请填写文件名称');
		return;
	}
	if(!filePath){
		alert('请填写文档目录');
		return;
	}
	

	if(!numSet){
		alert('请填写数量（set）');
		return;
	}
	
	if(!numUnit){
		alert('请填写数量（unit）');
		return;
	}

	if(! /^\d+$/.test(numSet) || ! /^\d+$/.test(numUnit) ){
		alert('数量必须是整数');
		return;
	}

	if(!ccChang){
		alert('请填写尺寸（长）');
		return;
	}
	if(!ccKuang){
		alert('请填写尺寸（宽）');
		return;
	}

	if(! /^[0-9]*(\.[0-9]{1,2})?$/.test(ccChang) || ! /^[0-9]*(\.[0-9]{1,2})?$/.test(ccKuang)) {
		alert('尺寸必须是有效数字');
		return;
	}
	smtCofirm();
	//document.form1.submit();
}
</script>