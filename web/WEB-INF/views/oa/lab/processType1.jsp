<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
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
	</tbody>
	</table>
	
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
	