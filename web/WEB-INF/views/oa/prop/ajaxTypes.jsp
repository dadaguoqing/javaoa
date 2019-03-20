<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
						
						<tr>
			  				<th style=" width:60px; text-align:center; padding-left:15px;">
			  					序号
			  				</th>
			  				<th style=" width:80px;"> | 类别名称 </th>
			  			</tr>
			  			
			  			<c:forEach items="${types}" var="cur" varStatus="st">
			  			<tr class="lzui-td">
			  				<td style="text-align:center; padding-left:15px;">
			  					${st.count }
			  				</td>
			  				<td>${cur.name }</td>
			  			</tr>
			  			</c:forEach>