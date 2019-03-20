<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<style>
<!--
.shortInput {
	width: 40px;
}

.shortInput2 {
	width: 100px;
}
-->
</style>

<div
	style="position: absolute; margin: 0px; padding: 0px; border: none; top: 60px; left: 0px; bottom: 0px; *height: expression(eval(document.documentElement.clientHeight-60)); width: 100%;">
	<!-- fixed navigater-->
	<div id="theNavigater">
		<div style="position: absolute; left: 2px; top: 2px;">
			<img src="${ctx }/resources/images/previous.png" id="hiddenNavImg" />
		</div>
		<div class="navTitle">系统导航</div>

		<div class="navTitle2 cur" url="javascript:;">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;申请详情
		</div>
		<div class="navTitle2" url="${ctx }/web/oa/lab/myPcbSp2">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

		<c:if test="${not empty msg}">
			<div class="lzui-error">${msg }</div>
		</c:if>

		<form name="form1" action="${ctx }/web/oa/lab/pcbSp" method="post">
			<input type="hidden" value="${submitCode}" name="submitCode" /> <input
				type="hidden" value="${sq.id}" name="id" />
			<div style="border: 1px solid #2191C0; margin-top: 10px;">
				<div class="main-header" id="id1">
					<span style="color: #eee;">${compName }外协加工工艺要求说明书</span>
				</div>
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						基本信息
					</span>
				</div>
				<table class="table1">
					<tbody>
						<tr>
							<td class="title" style="border-left: none; width: 10%;">申请人：</td>
							<td style="width: 90px;">${sq.proposerName }</td>
							<td class="title" style="border-left: none; width: 10%;">文件名称：</td>
							<td style="width: 30%;">${pcb.fileName}</td>
							<td class="title" style="width: 10%;">投板时间：</td>

							<td style="border-right: none; width: 20%;">
								${pcb.createTime}</td>
						</tr>

						<tr>
							<td class="title" style="border-left: none; width: 90px;">文档目录：</td>
							<td style="width: 90px;" colspan="6">${pcb.filePath }</td>
						</tr>
					</tbody>
				</table>

				<!-- 	PCB加工 -->
				<c:if test="${not empty pcb.numSet}">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							PCB加工详细参数
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="border-left: none; width: 105px;">数量：</td>
								<td style="">${pcb.numSet}set &nbsp;&nbsp; ${pcb.numUnit }unit
								</td>
								<td class="title" style="width: 105px;">层数：</td>
								<td style="" id="cs">${pcb.cs }</td>
								<td class="title" style="border-left: none; width: 120px;">尺寸：</td>
								<td style="border-right: none;">${pcb.ccChang }mm
									&nbsp;*&nbsp; ${pcb.ccKuang }mm</td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">成品板厚：</td>
								<td style="" id="cpbh">${pcb.cpbh}</td>
								<td class="title" style="width: 90px;">材料：</td>
								<td style="" id="cl">${pcb.cl }</td>
								<td class="title" style="border-left: none; width: 90px;">成品铜铂厚度：</td>
								<td style="border-right: none;">内层：<label id="tbNei">${pcb.tbNei }</label>
									&nbsp;&nbsp; 外层：<label id="tbWai">${pcb.tbWai } </label>
								</td>
							</tr>

							<tr>

								<td class="title" style="border-left: none; width: 90px;">阻
									焊：</td>
								<td style=""><label id="zh">${pcb.zh }</label> &nbsp;&nbsp;
									颜色：<label id="zhColor">${pcb.zhColor } </label></td>
								<td class="title" style="border-left: none; width: 90px;">字符：</td>
								<td style=""><label id="zf">${pcb.zf }</label> &nbsp;&nbsp;
									颜色：<label id="zfColor"> ${pcb.zfColor } </label></td>
								<td class="title" style="border-left: none; width: 90px;">测试通断：</td>
								<td style="border-right: none;" id="cstd">${pcb.cstd }</td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">阻外形加工方式：</td>
								<td style="" id="wxjgfs">${pcb.wxjgfs }</td>
								<td class="title" style="border-left: none; width: 90px;">金手指要求：</td>
								<td style="" id="jszyq">${pcb.jszyq }</td>
								<td class="title" style="border-left: none; width: 90px;">电测报告：</td>
								<td style="border-right: none;" id="dcbg">${pcb.dcbg }</td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">阻抗测试报告：</td>
								<td style="" id="zkcsbg">${pcb.zkcsbg }</td>
								<td class="title" style="border-left: none; width: 90px;">成品检验报告：</td>
								<td style="" id="cpjcbg">${pcb.cpjcbg}</td>
								<td class="title" style="border-left: none; width: 90px;">过孔是否覆盖阻焊：</td>
								<td style="border-right: none;" id="fgzh">${pcb.fgzh}</td>
							</tr>

							<tr>
								<td class="title" style="border-left: none; width: 90px;">表面涂层：</td>
								<td style="" id="bmtc">${pcb.bmtc}</td>
								<td class="title" style="border-left: none; width: 90px;">表面涂层厚度：</td>
								<td style="" id="bmtchd">${pcb.bmtchd}</td>
								<td class="title" style="border-left: none; width: 90px;">阻抗描述：</td>
								<td style="border-right: none;" id="kzms">${pcb.kzms}</td>
							</tr>
						</tbody>
					</table>
				</c:if>

				<!-- 	焊接加工 -->
				<c:if test="${ not empty pcb.weldType }">
					<div class="tableTitle"
						style="padding: 10px 20px; background-color: #ccfff5;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							焊接加工详细参数
						</span>
					</div>
					<table class="table1" style="background-color: #ccfff5;">
						<tr>
							<td class="title" style="border-left: none; width: 10%">焊接类型：</td>
							<td colspan="6" id="weldType">
								${pcb.weldType==1?'只焊接阻容':'全部焊接' }</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${pcb.weldType==2 }">
					<table class="table1" style="background-color: #ccfff5;">
						<tr>
							<td class="title" style="border-left: none; width: 10%">数量：</td>
							<td width="242px">${pcb.num }</td>
							<td class="title" style="border-left: none; width: 105px">层数：</td>
							<td id="piles" width="215px">${pcb.piles==1?'单面':'双面' }</td>
							<td class="title" style="border-left: none; width: 120px">焊接点数:</td>
							<td>贴片点数:${pcb.paster },插件点数:${pcb.paster2 }</td>
						</tr>
						<tr>
							<td class="title" style="border-left: none; width: 10%">焊接工艺要求：</td>
							<td id="gyType">${pcb.gyType==1?'有铅':'无铅' }</td>
							<td class="title" style="border-left: none;">是否有浮高、卧倒等特殊要求：</td>
							<td id="hjgy">${pcb.hjgy==1?'无':'见焊接加工工艺要求说明书'}</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${pcb.BGAType==2 }">
					<table class="table1" style="background-color: #ccfff5;">
						<tr>
							<td class="title" style="border-left: none; width: 10%">是否有BGA焊接：</td>
							<td id="BGAType" width="242px">${pcb.BGAType==1?'无':'有' }</td>
							<td>最多点数:${pcb.maxSize }, 间距:${pcb.ju }mm, 锡珠直径:${pcb.xzzj }mm
							</td>
						</tr>
					</table>
				</c:if>
				<c:if test="${pcb.BGAType==1 }">
					<table class="table1" style="background-color: #ccfff5;">
						<tr>
							<td class="title" style="border-left: none; width: 10%">是否有BGA焊接：</td>
							<td id="BGAType" width="242px">${pcb.BGAType==1?'无':'有' }</td>
						</tr>
					</table>
				</c:if>
				<!-- 	钢网加工 -->
				<c:if test="${not empty pcb.numSet2 }">
					<div class="tableTitle"
						style="padding: 10px 20px; background-color: #99ffeb">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							钢网加工详细参数
						</span>
					</div>
					<table class="table1" style="background-color: #99ffeb">
						<tbody>
							<tr>
								<td class="title" style="border-left: none; width: 105px;">数量：</td>
								<td style="width: 242px">${pcb.numSet2 }set</td>

								<td class="title" style="border-left: none; width: 105px;">尺寸：</td>
								<td style="border-left: none; width: 215px">${pcb.ccChang2 }mm
									&nbsp;*&nbsp; ${pcb.size }mm</td>
								<td class="title" style="border-left: none; width: 12spx;">钢网厚度：</td>
								<td style="border-right: none;" id="cpbh2">${pcb.cpbh2 }</td>
							</tr>

							<tr>

								<td class="title" style="width: 90px;">钢网材料：</td>
								<td style="" id="cl2">${pcb.cl2 }</td>

								<td class="title" style="border-left: none; width: 90px;">用途：</td>
								<td style="" id="bmtc2">${pcb.bmtc2 }</td>
								<td class="title" style="border-left: none; width: 90px;">抛光工艺：</td>
								<td style="border-right: none;" id="bmtchd2">${pcb.bmtchd2 }
								</td>
							</tr>
						</tbody>
					</table>
				</c:if>
				<!-- 	打白胶加工 -->
				<c:if test="${not empty type4 }">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							打白胶加工详细参数
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="border-left: none; width: 150px;">打白胶工艺要求文档:</td>
								<td style="color: red;" colspan="6">见文档目录下的打白胶工艺要求文档</td>
							</tr>
						</tbody>
					</table>
				</c:if>

				<!-- 	打白胶加工 -->
				<c:if test="${not empty type5 }">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							三防漆加工详细参数
						</span>
					</div>
					<table class="table1">
						<tbody>
							<tr>
								<td class="title" style="border-left: none; width: 150px;">三防漆工艺要求文档:</td>
								<td style="color: red;" colspan="6">见文档目录下的三防漆工艺要求文档</td>
							</tr>
						</tbody>
					</table>
				</c:if>



				<c:if test="${sq.status==1}">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							报价相关信息 <span style="color: red;">（标*号内容为制板负责人必填）</span></span>
					</div>

					<table class="table1">
						<tbody>
							<c:forEach items="${types }" var="type">
								<c:if test="${type=='1'}">
									<tr>
										<td class="title" style="border-left: none; width: 10%;">PCB加工厂商<span
											style="color: red;">*</span>：
										</td>
										<td style="width: 242px"><input name="jgcs1" id="jgcs1" />
										</td>
										<td class="title" style="border-left: none; width: 105px;">报价<span
											style="color: red;">*</span>：
										</td>
										<td style="width: 210px"><input name="bj1" id="bj1"
											class="shortInput2" onblur="countMoney()" />元</td>
										<td class="title" style="border-left: none; width: 120px;">预计加工周期<span
											style="color: red;">*</span>：
										</td>
										<td style="border-right: none;"><input name="jhsj1"
											id="jhsj1" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /> <input
											name="jhsj12" id="jhsj12" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /></td>
									</tr>
								</c:if>
								<c:if test="${type=='2'}">
									<tr>
										<td class="title" style="border-left: none; width: 90px;">焊接加工厂商<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="jgcs2" id="jgcs2" /></td>
										<td class="title" style="border-left: none; width: 90px;">报价<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="bj2" id="bj2"
											class="shortInput2" onblur="countMoney()" />元</td>
										<td class="title" style="border-left: none; width: 120px;">预计加工周期<span
											style="color: red;">*</span>：
										</td>
										<td style="border-right: none;"><input name="jhsj2"
											id="jhsj2" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /> <input
											name="jhsj22" id="jhsj22" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /></td>
									</tr>
								</c:if>
								<c:if test="${type=='3'}">
									<tr>
										<td class="title" style="border-left: none; width: 90px;">钢网加工厂商<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="jgcs3" id="jgcs3" /></td>
										<td class="title" style="border-left: none; width: 90px;">报价<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="bj3" id="bj3"
											class="shortInput2" onblur="countMoney()" />元</td>
										<td class="title" style="border-left: none; width: 120px;">预计加工周期<span
											style="color: red;">*</span>：
										</td>
										<td style="border-right: none;"><input name="jhsj3"
											id="jhsj3" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /> <input
											name="jhsj32" id="jhsj32" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /></td>
									</tr>
								</c:if>
								<c:if test="${type=='4'}">
									<tr>
										<td class="title" style="border-left: none; width: 90px;">打白胶加工厂商<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="jgcs4" id="jgcs4" /></td>
										<td class="title" style="border-left: none; width: 90px;">报价<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="bj4" id="bj4"
											class="shortInput2" onblur="countMoney()" />元</td>
										<td class="title" style="border-left: none; width: 120px;">预计加工周期<span
											style="color: red;">*</span>：
										</td>
										<td style="border-right: none;"><input name="jhsj4"
											id="jhsj4" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /> <input
											name="jhsj42" id="jhsj42" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /></td>
									</tr>
								</c:if>
								<c:if test="${type=='5'}">
									<tr>
										<td class="title" style="border-left: none; width: 90px;">三防漆加工厂商<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="jgcs5" id="jgcs5" /></td>
										<td class="title" style="border-left: none; width: 90px;">报价<span
											style="color: red;">*</span>：
										</td>
										<td style=""><input name="bj5" id="bj5"
											class="shortInput2" onblur="countMoney()" />元</td>
										<td class="title" style="border-left: none; width: 120px;">预计加工周期<span
											style="color: red;">*</span>：
										</td>
										<td style="border-right: none;"><input name="jhsj5"
											id="jhsj5" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /> <input
											name="jhsj52" id="jhsj52" class="Wdate" autocomplete="off"
											onFocus="WdatePicker({dateFmt:'yyyy年MM月dd日'})" /></td>
									</tr>
								</c:if>
							</c:forEach>
							<tr>
								<td class="title">总报价：</td>
								<td colspan="6"><input style="color: red" id="zbj"
									name="zbj" />&nbsp;元</td>
							</tr>
						</tbody>
					</table>
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							备注信息
						</span>
					</div>

					<table class="table1">
						<tbody>
							<tr>
								<td style="border-right: none;"><textarea name="bz" id="bz"
										style="width: 50%; height: 80px; padding: 5px 10px;">${sq.content }</textarea>
									<span style="color: red;">可不填，不超过150个字符，ctrl+Enter提交</span></td>
							</tr>
						</tbody>
					</table>
				</c:if>

				<c:if test="${sq.status!=1}">
					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							报价相关信息 <span style="color: red;">（标*号内容为制板负责人必填）</span></span>
					</div>


					<table class="table1">
						<tbody>
							<c:if test='${not empty sq.jgcs }'>
								<tr>
									<td class="title" style="border-left: none; width: 10%;">PCB加工厂商：</td>
									<td style="width: 242px">${sq.jgcs }</td>
									<td class="title" style="border-left: none; width: 105px;">报价：</td>
									<td style="width: 215px">${sq.bj }元</td>
									<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
									<td style="border-right: none;">${sq.jhsj }-${sq.jhsj12 }
									</td>
								</tr>
							</c:if>
							<c:if test='${not empty sq.jgcs2 }'>
								<tr>
									<td class="title" style="border-left: none; width: 150px;">焊接加工厂商：</td>
									<td style="">${sq.jgcs2 }</td>
									<td class="title" style="border-left: none; width: 90px;">报价：</td>
									<td style="">${sq.bj2 }元</td>
									<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
									<td style="border-right: none;">${sq.jhsj2 }-${sq.jhsj22 }
									</td>
								</tr>
							</c:if>
							<c:if test='${not empty sq.jgcs3 }'>
								<tr>
									<td class="title" style="border-left: none; width: 150px;">钢网加工厂商：</td>
									<td style="">${sq.jgcs3 }</td>
									<td class="title" style="border-left: none; width: 90px;">报价：</td>
									<td style="">${sq.bj3 }元</td>
									<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
									<td style="border-right: none;">${sq.jhsj3 }-${sq.jhsj32 }
									</td>
								</tr>
							</c:if>
							<c:if test='${not empty sq.jgcs4 }'>
								<tr>
									<td class="title" style="border-left: none; width: 150px;">打白胶加工厂商：</td>
									<td style="">${sq.jgcs4 }</td>
									<td class="title" style="border-left: none; width: 90px;">报价：</td>
									<td style="">${sq.bj4 }元</td>
									<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
									<td style="border-right: none;">${sq.jhsj4 }-${sq.jhsj42 }
									</td>
								</tr>
							</c:if>
							<c:if test='${not empty sq.jgcs5 }'>
								<tr>
									<td class="title" style="border-left: none; width: 150px;">三防漆加工厂商：</td>
									<td style="">${sq.jgcs5 }</td>
									<td class="title" style="border-left: none; width: 90px;">报价：</td>
									<td style="">${sq.bj5 }元</td>
									<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
									<td style="border-right: none;">${sq.jhsj5 }-${sq.jhsj52 }
									</td>
								</tr>
							</c:if>
							<tr>
								<td class="title" style="border-left: none; width: 90px;">总报价：</td>
								<td style="color: red;" colspan="6">${sq.zbj }元</td>
							</tr>
						</tbody>
					</table>






					<div class="tableTitle" style="padding: 10px 20px;">
						<span> <img
							src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
							备注信息
						</span>
					</div>

					<table class="table1">
						<tbody>
							<tr>
								<td style="border-right: none;"><textarea disabled
										style="width: 50%; height: 80px; padding: 5px 10px;">${sq.content }</textarea>
									<span style="color: red;">可不填，不超过150个字符，ctrl+Enter提交</span></td>
							</tr>
						</tbody>
					</table>
				</c:if>

				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						审批记录
					</span>
				</div>

				<table class="table1">
					<tbody>
						<tr>

							<td style="width: 12%;">制板负责人意见 <c:if
									test="${ not empty sq.zbId }">
									<span style="color: red;">（${AllUsers[sq.zbId].name}）</span>
								</c:if>
							</td>
							<td style="width: 12%; color: red;"><c:choose>
									<c:when test="${ empty sq.zbId }">
										<span style="color: #666;">本次审核不需要经过制板负责人</span>
									</c:when>
									<c:when test="${ sq.currentId == sq.zbId && sq.status == 1}">
										<span style="color: green;">正在审批</span>
									</c:when>
									<c:otherwise>
					${ empty sq.zbCmt ? '暂未到达该流程' : sq.zbCmt  }
				</c:otherwise>
								</c:choose></td>

							<td style="width: 12%;">主管意见 <c:if
									test="${ not empty sq.mgrId }">
									<span style="color: red;">（${AllUsers[sq.mgrId].name}）</span>
								</c:if>
							</td>
							<td style="width: 12%; color: red;"><c:choose>
									<c:when test="${ empty sq.mgrId }">
										<span style="color: #666;">本次审核不需要经过主管</span>
									</c:when>
									<c:when test="${ sq.currentId == sq.mgrId && sq.status == 2}">
										<span style="color: green;">正在审批</span>
									</c:when>
									<c:otherwise>
					${ empty sq.mgrCmt ? '暂未到达该流程' : sq.mgrCmt  }
				</c:otherwise>
								</c:choose></td>

							<td style="width: 12%;">财务主管意见 <c:if
									test="${ not empty sq.caiwuId }">
									<span style="color: red;">（${AllUsers[sq.caiwuId].name}）</span>
								</c:if>
							</td>
							<td style="width: 12%; color: red;"><c:choose>
									<c:when test="${ empty sq.caiwuId }">
										<span style="color: #666;">本次审批不需要经过财务主管</span>
									</c:when>
									<c:when test="${ sq.currentId == sq.caiwuId && sq.status == 3}">
										<span style="color: #green;">正在审批</span>
									</c:when>
									<c:otherwise>
					${ empty sq.caiwuCmt ? '暂未到达该流程' : sq.caiwuCmt  }
				</c:otherwise>
								</c:choose></td>

							<td style="width: 12%;">总经理意见 <c:if
									test="${ not empty sq.bossId }">
									<span style="color: red;">（${AllUsers[sq.bossId].name}）</span>
								</c:if>
							</td>
							<td style="width: 12%; color: red; border-right: none;"><c:choose>
									<c:when test="${ empty sq.bossId }">
										<span style="color: #666;">本次审核不需要经过总经理</span>
									</c:when>
									<c:when test="${ sq.currentId == sq.bossId && sq.status == 4}">
										<span style="color: green;">正在审批</span>
									</c:when>
									<c:otherwise>
					${ empty sq.bossCmt ? '暂未到达该流程' : sq.bossCmt  }
				</c:otherwise>
								</c:choose></td>
						</tr>
					</tbody>
				</table>


				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
						审批意见
					</span>
				</div>

				<table class="table1">
					<tbody>
						<tr>

							<td style="width: 320px;">审批意见： <select id="tg" name="sp"
								onchange="yjChanged()">
									<option value="审批通过" style="color: green;" selected="selected">通过</option>
									<option value="不通过" style="color: red;">不通过</option>
							</select>
							</td>

							<td style="border-right: none;">补充意见：<input name="yj"
								id="yj" style="width: 180px;" value="ok" /> <span
								style="color: red;">不超过50个字符</span>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="" style="margin-top: 10px; margin-bottom: 20px;">
				<a href="javascript:submitForm();" class="lzui-btn lzui-corner-all">提交审批结果</a>
			</div>

		</form>
	</div>

</div>

</body>
</html>
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function() {
		$('#OA审批').addClass('ui-tabs-current');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

		var cs = '${pcb.cs}';
		if (cs != '双层') {
			$('#cs').css('color', 'red');
		}
		var cpbh = '${pcb.cpbh}';
		if (cpbh != '1.6mm') {
			$('#cpbh').css('color', 'red');
		}
		var cl = '${pcb.cl}';
		if (cl != 'FR-4') {
			$('#cl').css('color', 'red');
		}
		var tbNei = '${pcb.tbNei}';
		var tbWai = '${pcb.tbWai}';
		if (tbNei != '1 OZ') {
			$('#tbNei').css('color', 'red');
		}
		if (tbWai != '1 OZ') {
			$('#tbWai').css('color', 'red');
		}
		var zh = '${pcb.zh}';
		var zhColor = '${pcb.zhColor}';
		if (zh != '双面') {
			$('#zh').css('color', 'red');
		}
		if (zhColor != '浅绿色') {
			$('#zhColor').css('color', 'red');
		}
		var zf = '${pcb.zf}';
		if (zf != '双面') {
			$('#zf').css('color', 'red');
		}
		var zfColor = '${pcb.zfColor}';
		if (zfColor != '白色') {
			$('#zfColor').css('color', 'red');
		}
		var cstd = '${pcb.cstd}';
		if (cstd != '是') {
			$('#cstd').css('color', 'red');
		}
		var wxjgfs = '${pcb.wxjgfs}';
		if (wxjgfs != '分开交货') {
			$('#wxjgfs').css('color', 'red');
		}
		var jszyq = '${pcb.jszyq}';
		if (jszyq != '无') {
			$('#jszyq').css('color', 'red');
		}
		var dcbg = '${pcb.dcbg}';
		if (dcbg != '是') {
			$('#dcbg').css('color', 'red');
		}
		var zkcsbg = '${pcb.zkcsbg}';
		if (zkcsbg != '是') {
			$('#zkcsbg').css('color', 'red');
		}
		var cpjcbg = '${pcb.cpjcbg}';
		if (cpjcbg != '是') {
			$('#cpjcbg').css('color', 'red');
		}
		var fgzh = '${pcb.fgzh}';
		if (fgzh != '是') {
			$('#fgzh').css('color', 'red');
		}
		var bmtc = '${pcb.bmtc}';
		if (bmtc != '喷锡') {
			$('#bmtc').css('color', 'red');
		}
		var bmtchd = '${pcb.bmtchd}';
		if (bmtchd != '1~3微英寸') {
			$('#bmtchd').css('color', 'red');
		}
		var kzms = '${pcb.kzms}';
		if (kzms != '无') {
			$('#kzms').css('color', 'red');
		}
		var weldType = '${pcb.weldType}';
		if (weldType != '1') {
			$('#weldType').css('color', 'red');
		}
		var piles = '${pcb.piles}';
		if (piles != '1') {
			$('#piles').css('color', 'red');
		}
		var gyType = '${pcb.gyType}';
		if (gyType != '1') {
			$('#gyType').css('color', 'red');
		}
		var BGAType = '${pcb.BGAType}';
		if (BGAType != '1') {
			$('#BGAType').css('color', 'red');
		}
		var hjgy = '${pcb.hjgy}';
		if (hjgy != '1') {
			$('#hjgy').css('color', 'red');
		}
		var cpbh2 = '${pcb.cpbh2}';
		if (cpbh2 != '0.1mm') {
			$('#hcpbh2').css('color', 'red');
		}
		var cl2 = '${pcb.cl2}';
		if (cl2 != '金属') {
			$('#cl2').css('color', 'red');
		}
		var bmtc2 = '${pcb.bmtc2}';
		if (bmtc2 != '印刷锡膏') {
			$('#bmtc2').css('color', 'red');
		}
		var bmtchd2 = '${pcb.bmtchd2}';
		if (bmtchd2 != '打磨抛光') {
			$('#bmtchd2').css('color', 'red');
		}

	});

	function yjChanged() {
		$('#yj').val("");
	}

	function countMoney() {
		var bj1 = Number($('#bj1').val());
		if (!bj1) {
			bj1 = 0;
		}
		var bj2 = Number($('#bj2').val());
		if (!bj2) {
			bj2 = 0;
		}
		var bj3 = Number($('#bj3').val());
		if (!bj3) {
			bj3 = 0;
		}
		var bj4 = Number($('#bj4').val());
		if (!bj4) {
			bj4 = 0;
		}
		var bj5 = Number($('#bj5').val());
		if (!bj5) {
			bj5 = 0;
		}
		var zbj = (bj1 + bj2 + bj3 + bj4 + bj5).toFixed(2);
		$('#zbj').val(zbj);
	}

	function beforeSubmit() {
		<c:if test="${sq.status==1}">
		var types = '${pcb.type}';
		var type = types.split(",");
		for (var i = 0; i < type.length; i++) {
			var jgcs = $('#jgcs' + type[i] + '').val();
			if (!jgcs) {
				alert('请填写加工厂商');
				return false;
			}

			var bj = $('#bj' + type[i] + '').val();
			if (!bj) {
				alert('请填写报价');
				return false;
			}

			var jhsj = $('#jhsj' + type[i] + '').val();
			if (!jhsj) {
				alert('请选择预计加工周期');
				return false;
			}
			var jhsj2 = $('#jhsj' + type[i] + '2').val();
			if (!jhsj2) {
				alert('请选择预计加工周期');
				return false;
			}
		}
		</c:if>
		var tg = $('#tg').val();

		if (tg == '0') {
			alert('请选择审批意见');
			return false;
		}

		var yj = $('#yj').val();
		yj = $.trim(yj);
		if (!yj) {
			alert('请输入补充意见');
			return false;
		}
		return true;
	}

	function submitForm() {
		if ($('#tg').val() == '不通过') {
			document.form1.submit();
		} else {
			var flag = beforeSubmit();
			if (flag) {
				document.form1.submit();
			}
		}
	}
</script>