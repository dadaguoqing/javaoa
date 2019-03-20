	<%@ page language="java" import="java.util.*,java.text.*" pageEncoding="utf-8"%>
<!-- 		 数量: ___、 层数（单面/双面）、焊接点数（贴片点数:___，插件点数：___）, -->
<!-- 		 焊接工艺要求（有铅、无铅），是否有BGA焊接（无/有（最多点数__,间距___,锡珠直径___））， -->
<!-- 		 是否有浮高、卧倒等特殊要求（无/有_提交焊接加工工艺要求说明书_） -->
	<table class="table1" >
		<tr>
			<td class="title" style="border-left:none;width:10%">焊接类型：</td>
			<td colspan="6">
				<select name="weldType" id="weldType">
					<option value="1">只焊接阻容</option>
					<option value="2">全部焊接</option>
				</select>
			</td>
		</tr>
		</table>
		<div style="display:none" id="weld">
		<table class="table1" >
		<tr>
			<td class="title" style="border-left:none;width:10%">数量：</td>
			<td width="242px"><input name="num" id="num"></td>
			<td class="title" style="border-left:none;width:105px;">层数：</td>
			<td width="225px"><select name="piles" id="piles">
					<option value="1">单面</option>
					<option value="2">双面</option>
				</select>
			</td>
			<td class="title" style="border-left:none;width:120px;">焊接点数:</td>
			<td>贴片点数:<input name="paster" id="paster" class="shortInput"/>
			,插件点数:<input name="paster2" id="paster2" class="shortInput"/></td>
		</tr>
		<tr>
			<td class="title" style="border-left:none;width:10%">焊接工艺要求：</td>
			<td >
				<select name="gyType" id="gyType">
					<option value="1">有铅</option>
					<option value="2">无铅</option>
				</select>
			</td>
			
			<td class="title" style="border-left:none;" >是否有浮高、卧倒等特殊要求：</td>
			<td >
				<select name="hjgy" id="hjgy">
					<option value="1">无</option>
					<option value="2">见焊接加工工艺要求说明书</option>
				</select>
				<a id="hjsm" style="color:red"></a>
			</td>
		</tr>
	</table>
	
	<table  class="table1" >
		<tr>
			<td class="title" style="border-left:none;width:10%;">是否有BGA焊接：</td>
			<td width="242px">
				<select name="BGAType" id="BGAType">
					<option value="1">无</option>
					<option value="2">有</option>
				</select>
			</td>
			<td colspan="2" id="bga">
				最多点数:<input name="maxSize" id="maxSize" class="shortInput" />,
				间距:<input name="ju" id="ju" class="shortInput"/>mm,
				锡珠直径:<input name="xzzj" id="xzzj" class="shortInput"/>mm
			</td>
		</tr>
	</table>
	</div>
		