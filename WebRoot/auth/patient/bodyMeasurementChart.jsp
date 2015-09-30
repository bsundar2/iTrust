<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="edu.ncsu.csc.itrust.action.BodyMeasurementAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.BodyMeasurementBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>


<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Body Measurements";
%>

<%@include file="/header.jsp" %>

<%
String pmid = Long.toString(loggedInMID);

String recType = (String)request.getParameter("dataType");

BodyMeasurementAction action = new BodyMeasurementAction(prodDAO, loggedInMID);
List<BodyMeasurementBean> bmlist = action.getBodyMeasurement();

if (!recType.equals("Weight") && !recType.equals("Height") && !recType.equals("Waist") && !recType.equals("Arms")) {
	response.sendRedirect("/iTrust/auth/patient/home.jsp");
	return;
}

String graphSource = "BodyChartServlet?mid=" + pmid + "&type=" + recType;
//System.out.println(graphSource);
%>

<%if(bmlist.size() > 0){
%>
<div align=center>
<h2>Patient Body Measurement Chart: <%=recType%></h2>
<img src="<%=graphSource%>" width="800" height="600" border="0"/>
</div>
<%}else{%>
<div align=center>
<h2>Patient Body Measurement Chart: <%=recType%></h2>
No Past Body Measurements Found
</div>
<%}%>

<form action="viewBodyMeasurement.jsp">
 <input type="submit" value="Back"/>  
</form>

<%@include file="/footer.jsp"%>