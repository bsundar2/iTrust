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
	BodyMeasurementAction action = new BodyMeasurementAction(prodDAO, loggedInMID);
	List<BodyMeasurementBean> bmlist = action.getBodyMeasurement();
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	loggingAction.logEvent(TransactionType.VIEW_BODY_MEASUREMENTS, loggedInMID.longValue(), loggedInMID.longValue() , "Patient views their body measurements");
%>

<div align=center>
	<h2>My Body Measurements Log</h2>

	<table class="fTable" align="center" id="foodDiaryTable">
	    <tr>
	        <th colspan="13">Patient Body Measurements</th>
	    </tr>
	
	    <tr class="subHeader">
	        <td style="text-align: center">Log Date</td>
	        <td style="text-align: center">Weight (lbs.)<br>(<a href="/iTrust/auth/patient/bodyMeasurementChart.jsp?dataType=Weight">View Chart</a>)</td>
	        <td style="text-align: center">Height (in.)<br>(<a href="/iTrust/auth/patient/bodyMeasurementChart.jsp?dataType=Height">View Chart</a>)</td>
	        <td style="text-align: center">Waist (in.)<br>(<a href="/iTrust/auth/patient/bodyMeasurementChart.jsp?dataType=Waist">View Chart</a>)</td>
	        <td style="text-align: center">Arms<br>Wing Span (in.)<br>(<a href="/iTrust/auth/patient/bodyMeasurementChart.jsp?dataType=Arms">View Chart</a>)</td>
	    </tr>
	    
	<%
	for (BodyMeasurementBean bmbean: bmlist) {
	%>
	        <tr>
	            <td style="text-align: center; min-width: 8em">
	                    <%= StringEscapeUtils.escapeHtml(bmbean.getLogDate()) %>
	            </td>            <td style="text-align: center; min-width: 8em">
	                    <%= StringEscapeUtils.escapeHtml(Double.toString(bmbean.getWeight())) %>
	            </td>            <td style="text-align: center; min-width: 8em">
	                    <%= StringEscapeUtils.escapeHtml(Double.toString(bmbean.getHeight())) %>
	            </td>            <td style="text-align: center; min-width: 8em">
	                    <%= StringEscapeUtils.escapeHtml(Double.toString(bmbean.getWaist())) %>
	            </td>            <td style="text-align: center; min-width: 8em">
	                    <%= StringEscapeUtils.escapeHtml(Double.toString(bmbean.getArms())) %>
	            </td>            
	        </tr>
	<%
	}
	%>
  
	</table>

<%@include file="/footer.jsp"%>