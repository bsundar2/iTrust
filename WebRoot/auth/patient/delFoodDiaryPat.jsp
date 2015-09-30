<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.FoodDiaryAction"%> <% //  Fooddiaryaction %> 
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%> <% //  Changed to EntryBean %> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>


<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Delete Patient Food Diary";
%>

<%@include file="/header.jsp"%>

<div align=center>
	<h2>Delete Food Diary Entry</h2>

	<%
	FoodDiaryAction action = new FoodDiaryAction(prodDAO, loggedInMID);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	loggingAction.logEvent(TransactionType.ADD_FOOD_DIARY, loggedInMID.longValue(), loggedInMID.longValue() , "Patient deletes a food diary entry");
	
	String entryDate = request.getParameter("entryDate");
	String meal = request.getParameter("meal");
	String food = request.getParameter("food");
	String servings = request.getParameter("servings");
	String cals = request.getParameter("cals");
	String fat = request.getParameter("fat");
	String sodium = request.getParameter("sodium");
	String carbs = request.getParameter("carbs");
	String fiber = request.getParameter("fiber");
	String sugar = request.getParameter("sugar");
	String protein = request.getParameter("protein");
	
	//Tests Sample code
	
	
	if(entryDate != null && meal != null && food != null && servings != null  && cals != null && fat != null  && sodium != null && carbs != null && fiber != null && sugar != null && protein != null){
			
			//create object
			FoodDiaryBean foodbean = new FoodDiaryBean();
			foodbean.setPatientID(loggedInMID.longValue());
			foodbean.setEntryDate(entryDate);
			foodbean.setMeal(meal);
			foodbean.setFood(food);
			foodbean.setServings(Double.parseDouble(servings));
			foodbean.setCals(Double.parseDouble(cals));
			foodbean.setFat(Double.parseDouble(fat));
			foodbean.setSodium(Double.parseDouble(sodium));
			foodbean.setCarbs(Double.parseDouble(carbs));
			foodbean.setSugar(Double.parseDouble(sugar));
			foodbean.setFiber(Double.parseDouble(fiber));
			foodbean.setProtein(Double.parseDouble(protein));
			
			//delete the object
			action.deleteFoodDiaryEntry(foodbean);
	}
	//Log the successful action before redirect
	loggingAction.logEvent(TransactionType.DELETE_FOOD_DIARY, loggedInMID.longValue(), loggedInMID.longValue() , "Patient deletes a food diary entry");
	
	//Done redirect to page to see if changes taken place
	response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");

	%>

<%@include file="/footer.jsp"%>
