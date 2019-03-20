<%@ page language="java" import="java.util.*,com.hj.oa.bean.*,com.hj.util.*" pageEncoding="utf-8"%>
	<div class="navTitle2" id="首页2" url="${ctx}/web/oa/index" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;首页</div>
	<div class="navTitle2" id="通知公告" url="${ctx }/web/oa/notice/list" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;通知公告</div>
	<!--  
	<div class="navTitle2" id="规章制度" url="${ctx }/web/oa/rules/list" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;规章制度</div>
	-->
	<%
		User llUser = (User)session.getAttribute("loginUser");
		if(!OtherUtil.isSpecialEmp(llUser.getId())){
			
	%>
	<div class="navTitle2" id="员工出勤" url="${ctx }/web/oa/user/attendance" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;员工出勤</div>
	<%
		}
	%>
	<div class="navTitle2" id="员工通讯录" url="${ctx }/web/oa/user/emp/infos" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;员工通讯录</div>
	<div class="navTitle2" id="工作日历" url="${ctx }/web/oa/date/cal" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;工作日历</div>
		<%
			List<Role> roless = (List<Role>)session.getAttribute("loginUserRoles");
			if( RoleUtil.hasRole(roless,"技术支持") ){
		%>
	<div class="navTitle2" id="日常维护" url="${ctx }/web/oa/user/wh" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;日常维护</div>
		<%
			}
		%>
		
		<%
			if(!OtherUtil.isSpecialEmp(llUser.getId())){
		%>
	<div class="navTitle2" id="今日订餐" url="${ctx }/web/oa/dc/dingcan" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;今日订餐 </div>
	
<%-- <%
			List<Role> roles = (List<Role>)session.getAttribute("loginUserRoles");
			if( RoleUtil.hasRole(roles,"总经理") || RoleUtil.hasRole(roles,"技术总监")  || RoleUtil.hasRole(roles,"部门主管")  ){
		%>
	<div class="navTitle2" id="年度评价" url="${ctx }/web/oa/ev/mine" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;年度评价<span style="color:red;">（new）</span> </div>
	 
		<%
			}
		%> --%>
	 
		<%
			}
		%>
		
		<%--
			if(llUser.getId() == 13){//linda
		
	<div class="navTitle2" id="端午节福利" url="${ctx }/web/oa/gift/mine" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;端午节福利<span style="color:red;">(new)</span> </div>	
		
			}
		--%>
		
		<%
			if( RoleUtil.hasRole(roless,"订餐管理员") ){
		%>
	<div class="navTitle2" id="订餐管理" url="${ctx }/web/oa/dc/mgr" ><img src="${ctx }/resources/images/item.png" style="vertical-align:middle;width:20px;height:20px;"/>&nbsp;&nbsp;订餐管理 </div>
		<%
			}
		%>
		