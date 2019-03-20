<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
	<table class="table1" ><tbody>
		<tr>
			<td class="title" style="border-left:none; width:105px;">数量：</td>	
			<td width="242px">
				<input name="numSet2" id="numSet2" class="shortInput"/>set
			</td>	
			
			<td class="title" style="border-left:none; width:105px;">尺寸：</td>	
			<td style="border-left:none;width:225px">
				<input name="ccChang2" id="ccChang2" class="shortInput"/>mm &nbsp;*&nbsp;
				<input name="size" id="size" class="shortInput"/>mm
			</td>	
			<td class="title" style="border-left:none; width:120px;">钢网厚度：</td>	
			<td style="border-right:none;">
				<select name="cpbh2" id="cpbh2">
					<option value="0.1mm">0.1mm</option> <option value="0.12mm">0.12mm</option> 
					<option value="0.13mm">0.13mm</option>
					<option value="0.15mm">0.15mm</option> <option value="0.18mm">0.18mm</option>
					<option value="0.2mm">0.2mm</option>
				</select>
			</td>
		</tr>
		
		<tr>
				
			<td class="title" style="width:90px;">钢网材料：</td>	
			<td style="">
				<select name="cl2" id="cl2">
					<option value="金属">金属</option> <option value="塑料">塑料</option>
				</select>
			</td>	
			
			<td class="title" style="border-left:none; width:90px;">用途：</td>	
			<td style="">
				<select name="bmtc2" id="bmtc2">
					<option value="印刷锡膏">印刷锡膏</option> <option value="印刷红胶">印刷红胶</option> 
					<option value="印刷胶水">印刷胶水</option>  
				</select>
			</td>	
			<td class="title" style="border-left:none; width:90px;">抛光工艺：</td>	
			<td style="border-right:none;">
				<select name="bmtchd2" id="bmtchd2">
					 <option value="打磨抛光">打磨抛光</option> <option value="电解抛光">电解抛光</option>
				</select>
			</td>	
		</tr>
	</tbody></table>