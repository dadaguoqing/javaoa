<%@ page language="java" import="java.util.*,java.text.*"
	pageEncoding="utf-8"%>
<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<c:if test="${cur.id < 35}">
		<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
		</div>
	</c:if>
</c:forEach>
<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<c:if test="${cur.id > 35 and cur.id < 66}">
		<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
		</div>
	</c:if>
</c:forEach>
<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<c:if test="${cur.id > 66 and cur.id < 87}">
		<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
		</div>
	</c:if>
</c:forEach>
<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<c:if test="${cur.id > 87 and cur.id < 99}">
		<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
		</div>
	</c:if>
</c:forEach>
<c:forEach items="${loginUserMenuMap['5']}" var="cur">
	<c:if test="${cur.id > 99}">
		<div id="${cur.name }" class="navTitle2" url="${ctx }/${cur.url}">
			<img src="${ctx }/resources/images/item.png"
				style="vertical-align: middle; width: 20px; height: 20px;" />&nbsp;&nbsp;${cur.name }
		</div>
	</c:if>
</c:forEach>
