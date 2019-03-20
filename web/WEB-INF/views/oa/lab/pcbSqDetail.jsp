<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<%@ include file="../../public/header.jsp"%>

<style>
<!--
.shortInput {
	width: 40px;
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
		<div class="navTitle2" url="${ctx }/web/oa/lab/myPcbSqRecord2">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;返回上级
		</div>

	</div>
	<div id="theNavigater2">
		<img src="${ctx }/resources/images/next.png" />点击展开导航栏
	</div>

	<div style="margin: 10px 5px 0 195px;">
		<%@ include file="../../public/hello.jsp"%>

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
						<td style="width: 90px;" colspan="2">${pcb.filePath }</td>
						<td class="title" style="border-left: none; width: 90px;">订单编号：</td>
						<td style="width: 90px;" colspan="2">${pcb.ddbh }</td>
					</tr>
				</tbody>
			</table>

			<!-- 	PCB加工 -->
			<c:if test="${not empty pcb.numSet}">
				<div class="tableTitle" style="padding: 10px 20px;">
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
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
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
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
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
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
								&nbsp;*&nbsp; ${pcb.size }</td>
							<td class="title" style="border-left: none; width: 120px;">钢网厚度：</td>
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
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
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
					<span> <img src="${ctx }/resources/images/communication.gif" />&nbsp;&nbsp;&nbsp;&nbsp;
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

			<table class="table1">
				<tbody>
					<c:if test='${not empty sq.jgcs }'>
						<tr>
							<td class="title" style="border-left: none; width: 150px;">PCB加工厂商：</td>
							<td style="">${sq.jgcs }</td>
							<td class="title" style="border-left: none; width: 90px;">报价：</td>
							<td style="">${sq.bj }元</td>
							<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
							<td style="border-right: none;">${sq.jhsj }-${sq.jhsj12 }</td>
						</tr>
					</c:if>
					<c:if test='${not empty sq.jgcs2 }'>
						<tr>
							<td class="title" style="border-left: none; width: 150px;">焊接加工厂商：</td>
							<td style="">${sq.jgcs2 }</td>
							<td class="title" style="border-left: none; width: 90px;">报价：</td>
							<td style="">${sq.bj2 }元</td>
							<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
							<td style="border-right: none;">${sq.jhsj2 }-${sq.jhsj22 }</td>
						</tr>
					</c:if>
					<c:if test='${not empty sq.jgcs3 }'>
						<tr>
							<td class="title" style="border-left: none; width: 150px;">钢网加工厂商：</td>
							<td style="">${sq.jgcs3 }</td>
							<td class="title" style="border-left: none; width: 90px;">报价：</td>
							<td style="">${sq.bj3 }元</td>
							<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
							<td style="border-right: none;">${sq.jhsj3 }-${sq.jhsj32 }</td>
						</tr>
					</c:if>
					<c:if test='${not empty sq.jgcs4 }'>
						<tr>
							<td class="title" style="border-left: none; width: 150px;">打白胶加工厂商：</td>
							<td style="">${sq.jgcs4 }</td>
							<td class="title" style="border-left: none; width: 90px;">报价：</td>
							<td style="">${sq.bj4 }元</td>
							<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
							<td style="border-right: none;">${sq.jhsj4 }-${sq.jhsj42 }</td>
						</tr>
					</c:if>
					<c:if test='${not empty sq.jgcs5 }'>
						<tr>
							<td class="title" style="border-left: none; width: 150px;">三防漆加工厂商：</td>
							<td style="">${sq.jgcs5 }</td>
							<td class="title" style="border-left: none; width: 90px;">报价：</td>
							<td style="">${sq.bj5 }元</td>
							<td class="title" style="border-left: none; width: 120px;">预计加工周期：</td>
							<td style="border-right: none;">${sq.jhsj5 }-${sq.jhsj52 }</td>
						</tr>
					</c:if>
					<c:if test="${not empty sq.zbj} ">
						<tr>
							<td class="title" style="border-left: none; width: 90px;">总报价：</td>
							<td style="color: red;" colspan="6">${sq.zbj }元</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<%-- 
	<div class="tableTitle" style="padding:10px 20px;">
		<span>
		<img src="${ctx }/resources/images/communication.gif"/>&nbsp;&nbsp;&nbsp;&nbsp;
		备注信息</span>
	</div>
	
	<table class="table1" ><tbody>
		<tr>
			<td  style="border-right:none; ">
				<textarea disabled style="width:50%; height:80px; padding:5px 10px;">${sq.content }</textarea>
			</td>	
		</tr>
	</tbody></table>
	--%>
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

		</div>

	</div>

</div>

</body>
</html>
<script language="javascript" type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script>
	$(function() {
		$('#OA申请').addClass('ui-tabs-current');

		$('.navTitle2').click(function() {
			var url = $(this).attr('url');
			if (url)
				document.location.href = url;
		});

	});
</script>