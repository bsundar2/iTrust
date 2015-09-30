<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.DateCheck"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>


<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>

<%@include file="/global.jsp"%>

<% 
	String singleDate = request.getParameter("singleDate");

	DateCheck checker = new DateCheck();
	boolean status = false; 
	
	status = checker.checkSingle(singleDate);
	
	if(status == true){
	}else if(status == false){
		//set session
		session.setAttribute("singleDateError", "Date: [Incorrect Fomat must be (MM/DD/YYYY) and Non-Future Time]");
		//redirect to cat page
		response.sendRedirect("/iTrust/auth/patient/categorizeFoodDiary.jsp");
		return;
	}
	
	loggingAction.logEvent(TransactionType.CATEGORIZE_FOOD_DIARY, loggedInMID.longValue(), loggedInMID.longValue() , "Patient views their food diary by date");
	session.setAttribute("single", singleDate);
	response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");

%>

<%@include file="/footer.jsp"%>