<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>
<%@page import="java.text.DecimalFormat"%>  

<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page errorPage="/auth/exceptionHandler.jsp" %>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>

<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>

<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>

<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>

<%@include file="/global.jsp"%>

<%
pageTitle = "iTrust - Categorize Food Diary";
%>


<%@include file="/header.jsp"%>

<div align=center>
	<h2>Categorize Food Diary Entries</h2>
</div>

<%

//Require a Patient ID first
String pidString = (String)session.getAttribute("pid");
if (pidString == null || 1 > pidString.length()) {
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=hcp-uap/categorizeFoodDiary.jsp");
	return;
}

//REDIRECT CODE FOR UNSPECIALIZED HCP's
PersonnelDAO alpha = new PersonnelDAO(prodDAO);
PersonnelBean beta= alpha.getPersonnel(loggedInMID.longValue());
String charlie = beta.getSpecialty();


	if(!charlie.equalsIgnoreCase("nutritionist")){
		response.sendRedirect("/iTrust/auth/hcp/home.jsp");
	}
    
	//Clears the session vars that hold the date

	if(session.getAttribute("single") != null){
	session.setAttribute("single", null);
	}
	if(session.getAttribute("start") != null){
		session.setAttribute("start", null);
	}
	if(session.getAttribute("end") != null){
		session.setAttribute("end", null);
	}
	//Error Handling Session Variables
	if(session.getAttribute("singleDateError") != null){
		out.println("<div align=center> <span style=\"color:red\"  class=\"font_success\">" + session.getAttribute("singleDateError")+ "<br>" + "</span></div>");
	session.setAttribute("singleDateError", null);
	}

%>



<br>
<br>

<form method="post" action="validateSingleDate.jsp" name=single id="sing">
<h4>&nbsp;&nbsp;Single Date</h4>
<table>
<tr>
<td><span class="font1">Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><input type="text" name="singleDate"></td>
<td><input type="submit" value="Filter"></td>
</tr>
</table>
</form>

<br>
<br>

<form method="post" action="validateRangeDate.jsp" name=range>
<h4>&nbsp;&nbsp;Range of Dates</h4>
<table>
<tr>
<td><span class="font1">Start&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><input type="text" name="rangeDateStart"></td>
<td><span class="font1">End&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><input type="text" name="rangeDateEnd"></td>
<td><input type="submit" value="Filter"></td>
</tr>
</table>
</form>

<br>
<br>

<form method="post" action="viewFoodDiaryNut.jsp" name=all id="viewall" >
<h4>&nbsp;&nbsp;View All</h4>
<table>
<tr>
<td><input type="submit" value="Filter"></td>
</tr>
</table>
</form>





<%@include file="/footer.jsp"%>
