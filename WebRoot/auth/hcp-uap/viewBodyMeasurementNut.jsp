<%@page import="edu.ncsu.csc.itrust.action.base.PersonnelBaseAction"%>
<%@taglib uri="/WEB-INF/tags.tld" prefix="itrust"%>
<%@page errorPage="/auth/exceptionHandler.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="java.io.BufferedReader" %>
<%@page import="java.util.ArrayList"%>
<%@page import="edu.ncsu.csc.itrust.action.ViewHealthRecordsHistoryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.HealthRecord"%>
<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.action.BodyMeasurementAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.BodyMeasurementBean"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Patient Body Measurements";
%>

<%@include file="/header.jsp" %>
<itrust:patientNav thisTitle="Patient Body Measurements" />

<%
	// Require a Patient ID first
	String pidString = (String)session.getAttribute("pid");
	if (pidString == null || 1 > pidString.length()) {
		response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/viewBodyMeasurementNut.jsp");
	   	return;
	}

	//REDIRECT CODE FOR UNSPECIALIZED HCP's
	PersonnelDAO alpha = new PersonnelDAO(prodDAO);
	PersonnelBean beta= alpha.getPersonnel(loggedInMID.longValue());
	String charlie = beta.getSpecialty();

	if(!charlie.equalsIgnoreCase("nutritionist")){
		response.sendRedirect("/iTrust/auth/hcp/home.jsp");
	}
	
	long pidLong = Long.parseLong(pidString);
	
	BodyMeasurementAction action = new BodyMeasurementAction(prodDAO, pidLong);
	List<BodyMeasurementBean> bmlist = action.getBodyMeasurement();
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	loggingAction.logEvent(TransactionType.VIEW_BODY_MEASUREMENTS, loggedInMID.longValue(), pidLong , "Nutritionist views body measurements");
	
	//Create ViewHealthRecordsHistoryAction object to interact with patient's historical health metric history
	ViewHealthRecordsHistoryAction historyAction = new ViewHealthRecordsHistoryAction(prodDAO,pidString,loggedInMID.longValue());
	//Get the patient's name
	String patientName = historyAction.getPatientName();

	String weightLink = "\"/iTrust/auth/hcp-uap/bodyMeasurementChartNut.jsp?dataType=Weight&pmid=" + pidString + "\"";
	String heightLink = "\"/iTrust/auth/hcp-uap/bodyMeasurementChartNut.jsp?dataType=Height&pmid=" + pidString + "\"";
	String waistLink = "\"/iTrust/auth/hcp-uap/bodyMeasurementChartNut.jsp?dataType=Waist&pmid=" + pidString + "\"";
	String armsLink = "\"/iTrust/auth/hcp-uap/bodyMeasurementChartNut.jsp?dataType=Arms&pmid=" + pidString + "\"";
	
	%>
	
	<div align=center>
	<h2>Body Measurements Log</h2>

	<table class="fTable" align="center" id="foodDiaryTable">
	    <tr>
	        <th colspan="13"><%=StringEscapeUtils.escapeHtml(patientName)%>'s Body Measurements</th>
	    </tr>
	
	    <tr class="subHeader">
	        <td style="text-align: center">Log Date</td>
	        <td style="text-align: center">Weight (lbs.)<br>(<a href=<%=weightLink%>>View Chart</a>)</td>
	        <td style="text-align: center">Height (in.)<br>(<a href=<%=heightLink%>>View Chart</a>)</td>
	        <td style="text-align: center">Waist (in.)<br>(<a href=<%=waistLink%>>View Chart</a>)</td>
	        <td style="text-align: center">Arms<br>Wing Span (in.)<br>(<a href=<%=armsLink%>>View Chart</a>)</td>
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
	
	
	