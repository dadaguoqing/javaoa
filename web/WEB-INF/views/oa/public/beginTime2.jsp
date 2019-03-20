<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

				<select name="beginHour" id="beginHour">
					<option value="-1">请选择</option>
					<%
					for(int i=0; i<24; i++){
						String hstr = i<10 ? "0"+i : i+"";
					%>
					<option value="<%=hstr%>"><%=i%>时</option>
					<%
					}
					%>
				</select>
				<select name="beginMin" id="beginMin">
					<option value="-1">请选择</option>
					<%
					for(int i=0; i<60; i++){
						String hstr = i<10 ? "0"+i : i+"";
					%>
					<option value="<%=hstr%>"><%=i%>分</option>
					<%
					}
					%>
				</select>
				<script>
					var beginHour = '${beginHour}' == '' ? '08' : '${beginHour}';
					var beginMin = '${beginMin}' == '' ? '30' : '${beginMin}';
					$('#beginHour').val(beginHour);
					$('#beginMin').val(beginMin);
				</script>