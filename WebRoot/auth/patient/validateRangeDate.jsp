<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.DateCheck"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>
<%@page import="java.util.Date"%>


<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>

<%@include file="/global.jsp"%>

<% 
	String rangeDateStart = request.getParameter("rangeDateStart");
	String rangeDateEnd = request.getParameter("rangeDateEnd");
	
	DateCheck checker = new DateCheck();
	boolean status = false; 
	
	status = checker.checkRange(rangeDateStart, rangeDateEnd);
	
	if(status == true){
	}else if(status == false){
		//set session
		session.setAttribute("singleDateError", "Date: [Incorrect Fomat must be (MM/DD/YYYY) and Non-Future Time]");
		//redirect to cat page
		response.sendRedirect("/iTrust/auth/patient/categorizeFoodDiary.jsp");
		return;
	}
	
	//Make sure that start is before end
	//First parse them into a comparable date format
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	Date s = dateFormat.parse(rangeDateStart);
	Date e = dateFormat.parse(rangeDateEnd);
	if(s.after(e)){
		session.setAttribute("singleDateError", "Date: [Start Date Must Be Before End Date]");
		response.sendRedirect("/iTrust/auth/patient/categorizeFoodDiary.jsp");
		return;
	}
	
	loggingAction.logEvent(TransactionType.CATEGORIZE_FOOD_DIARY, loggedInMID.longValue(), loggedInMID.longValue() , "Patient views their food diary by date");
	session.setAttribute("start", rangeDateStart);
	session.setAttribute("end", rangeDateEnd);
	response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");

%>

<%@include file="/footer.jsp"%>