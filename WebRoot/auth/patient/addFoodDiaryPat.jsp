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
    pageTitle = "iTrust - Add Patient Food Diary";
%>

<%@include file="/header.jsp"%>

<div align=center>
	<h2>Add Food Diary Entry</h2>

	<%
	FoodDiaryAction action = new FoodDiaryAction(prodDAO, loggedInMID);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	
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
	
	if(entryDate != null && meal != null && food != null && servings != null  && cals != null && fat != null  && sodium != null && carbs != null && fiber != null && sugar != null && protein != null){
		try {
			
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
			foodbean.setSugar(Double.parseDouble(fiber));
			foodbean.setFiber(Double.parseDouble(sugar));
			foodbean.setProtein(Double.parseDouble(protein));
			
			//add the object
			action.addFoodDiary(foodbean);
			
			//Log if action was succesful
			loggingAction.logEvent(TransactionType.ADD_FOOD_DIARY, loggedInMID.longValue(), loggedInMID.longValue() , "Patient adds to their food diary");
			
				%>
				<span class="font_success">"Food Diary Successfully Added." </span><br>
				<%
	
		} catch (FormValidationException e) { //Need to create a new error to be handled
			out.println("<span class=\"font_failure\">" + e.getMessage() + "<br>");
	
		} catch (NumberFormatException e) { // handles blank fields of incorrect input
			out.println("<span class=\"font_failure\">" + "[Blank Fields: Data Not Submitted] or [Calories and Below Must be Whole Numbers]" + "<br>");
	
		} 
	}
	%>
	
<form method="post" action="addFoodDiaryPat.jsp">
<table>
<tr>
<td><input type="text" name="entryDate"></td><td><span class="font1">Consumption Date</span></td>
</tr>
<tr>
<td><input type="text" name="meal"></td><td><span class="font1">Meal</span></td>
</tr>
<tr>
<td><input type="text" name="food"></td><td><span class="font1">Food Name</span></td>
</tr>
<tr>
<td><input type="text" name="servings"></td><td><span class="font1">Servings</span></td>
</tr>
<tr>
<td><input type="text" name="cals"></td><td><span class="font1">Calories Per Servings</span></td>
</tr>
<tr>
<td><input type="text" name="fat"></td><td><span class="font1">Grams Fat Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="sodium"></td><td><span class="font1">mg Sodium Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="carbs"></td><td><span class="font1">Grams Carbs Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="fiber"></td><td><span class="font1">Grams Fiber Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="sugar"></td><td><span class="font1">Grams Sugar Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="protein"></td><td><span class="font1">Grams Protein Per Serving</span></td>
</tr>
</table>
<input type="submit" value="Add Food Diary Entry">
</form>






<%@include file="/footer.jsp"%>
