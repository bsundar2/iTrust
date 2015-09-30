<%@page errorPage="/auth/exceptionHandler.jsp"%>
<%@page import="edu.ncsu.csc.itrust.action.BodyMeasurementAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.BodyMeasurementBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>


<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - Add Body Measurements";
%>

<%@include file="/header.jsp" %>

<div align=center>
	<h2>Add Body Measurements</h2>
	
	<% 
	BodyMeasurementAction action = new BodyMeasurementAction(prodDAO, loggedInMID);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	String logDate = request.getParameter("logDate");
	String weight = request.getParameter("weight");
	String height = request.getParameter("height");
	String waist = request.getParameter("waist");
	String arms = request.getParameter("arms");
	
	if(logDate != null && weight != null && height != null && waist != null && arms != null){
		try{
			//create object
			BodyMeasurementBean bodybean = new BodyMeasurementBean();
			bodybean.setPatientID(loggedInMID.longValue());
			bodybean.setLogDate(logDate);
			bodybean.setWeight(Double.parseDouble(weight));
			bodybean.setHeight(Double.parseDouble(height));
			bodybean.setWaist(Double.parseDouble(waist));
			bodybean.setArms(Double.parseDouble(arms));
			
			//add the object
			action.addBodyMeasurement(bodybean);
			
			//Log if action was succesful
			loggingAction.logEvent(TransactionType.VIEW_BODY_MEASUREMENTS, loggedInMID.longValue(), loggedInMID.longValue() , "Patient adds to their body measurements");
			
			%>
			<span class="font_success">"Body Measurements Successfully Added." </span><br>
			<%
			
		} catch (FormValidationException e) { //Need to create a new error to be handled
			out.println("<span style=\"color:red\">" + e.getMessage() + "<br>" + "</span>");
	
		} catch (NumberFormatException e) { // handles blank fields of incorrect input
			out.println("<span style=\"color:red\">" + "[Blank Fields: Data Not Submitted] or [Measurements Must be Numerical]" + "<br>" + "</span>");
	
		}
	}
	%>
	
<form method="post" action="addBodyMeasurement.jsp">
<table>
<tr>
<td><input type="text" name="logDate"></td><td><span class="font1">Log Date (MM/DD/YYYY)</span></td>
</tr>
<tr>
<td><input type="text" name="weight"></td><td><span class="font1">Weight (lbs.)</span></td>
</tr>
<tr>
<td><input type="text" name="height"></td><td><span class="font1">Height (in.)</span></td>
</tr>
<tr>
<td><input type="text" name="waist"></td><td><span class="font1">Waist (in.)</span></td>
</tr>
<tr>
<td><input type="text" name="arms"></td><td><span class="font1">Arms/Wing Span (in.)</span></td>
</tr>
</table>
<input type="submit" value="Add Body Measurements Entry">
</form>




<%@include file="/footer.jsp"%>