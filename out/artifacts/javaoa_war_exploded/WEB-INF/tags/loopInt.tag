<%@ tag body-content="empty" pageEncoding="utf-8" isELIgnored="false"%>
<%@ attribute name="times" required="true" rtexprvalue="true" type="java.lang.Integer"%>

<%
Integer times = (Integer)this.getJspContext().getAttribute("times");
for(int i=0; i<=times; i++){
%>
<option value="<%=i%>"><%=i%></option>
<%
}
%>