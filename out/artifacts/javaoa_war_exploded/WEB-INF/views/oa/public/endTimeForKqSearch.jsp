<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

				<select name="endHour" id="endHour">
					<option value="">时</option>
					<%
					for(int i=0; i<24; i++){
						String hstr = i<10 ? "0"+i : i+"";
					%>
					<option value="<%=hstr%>"><%=i%>时</option>
					<%
					}
					%>
				</select>
				<select name="endMin" id="endMin">
					<option value="">分</option>
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
					var endHour = '${endHour}';
					var endMin = '${endMin}';
					$('#endHour').val(endHour);
					$('#endMin').val(endMin);
				</script>