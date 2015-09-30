<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>
<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.action.FoodDiaryAction"%> <% //  Fooddiaryaction %> 
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%> <% //  Changed to EntryBean %> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.net.URLEncoder" %>


<%@page errorPage="/auth/exceptionHandler.jsp" %>

<%@taglib prefix="itrust" uri="/WEB-INF/tags.tld" %>

<%@include file="/global.jsp"%>

<%
    pageTitle = "iTrust - Edit Patient Food Diary";
%>

<%@include file="/header.jsp"%>

<div align=center>
	<h2>Edit Food Diary Entry</h2>

	<%
	FoodDiaryAction action = new FoodDiaryAction(prodDAO, loggedInMID);
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	loggingAction.logEvent(TransactionType.ADD_FOOD_DIARY, loggedInMID.longValue(), loggedInMID.longValue() , "Patient adds to their food diary");
	
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
	String sub = request.getParameter("sub");
	
	//After the submit button is pressed
	//Create the variables
	if(sub.equals("yes")){
		//try to add the object - now we know that its unique - checks for valid fields
		try{
			//Old Variables before Edit
			String OentryDate = request.getParameter("OentryDate");
			String Omeal = request.getParameter("Omeal");
			String Ofood = request.getParameter("Ofood");
			String Oservings = request.getParameter("Oservings");
			String Ocals = request.getParameter("Ocals");
			String Ofat = request.getParameter("Ofat");
			String Osodium = request.getParameter("Osodium");
			String Ocarbs = request.getParameter("Ocarbs");
			String Ofiber = request.getParameter("Ofiber");
			String Osugar = request.getParameter("Osugar");
			String Oprotein = request.getParameter("Oprotein");
			
			
			// This block handles the user not inputting anything for the number values
			// if the user enters nothing for a number field this will catch before the parse
			// and redirect to the view page and show the error
			try{
				if(servings.isEmpty() || cals.isEmpty() || fat.isEmpty() || sodium.isEmpty() || carbs.isEmpty() || fiber.isEmpty() || sugar.isEmpty() || protein.isEmpty()){
					throw new FormValidationException("Servings: Must be Greater than 0");
				}
			} catch(FormValidationException e){
				session.setAttribute("err1", "[Servings, Calories, Fat, Sodium, Carbs, Fiber, Sugar, and Protein - Must not be Blank!]");
				response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");	
				return;
			}
			
			//Try to Create New Object
			FoodDiaryBean fb = new FoodDiaryBean();
			fb.setPatientID(loggedInMID.longValue());
			fb.setEntryDate(entryDate);
			fb.setMeal(meal);
			fb.setFood(food);
			fb.setServings(Double.parseDouble(servings));
			fb.setCals(Double.parseDouble(cals));
			fb.setFat(Double.parseDouble(fat));
			fb.setSodium(Double.parseDouble(sodium));
			fb.setCarbs(Double.parseDouble(carbs));
			fb.setSugar(Double.parseDouble(fiber));
			fb.setFiber(Double.parseDouble(sugar));
			fb.setProtein(Double.parseDouble(protein));
			
			//Create Old Object
			FoodDiaryBean Ofb = new FoodDiaryBean();
			Ofb.setPatientID(loggedInMID.longValue());
			Ofb.setEntryDate(OentryDate);
			Ofb.setMeal(Omeal);
			Ofb.setFood(Ofood);
			Ofb.setServings(Double.parseDouble(Oservings));
			Ofb.setCals(Double.parseDouble(Ocals));
			Ofb.setFat(Double.parseDouble(Ofat));
			Ofb.setSodium(Double.parseDouble(Osodium));
			Ofb.setCarbs(Double.parseDouble(Ocarbs));
			Ofb.setSugar(Double.parseDouble(Ofiber));
			Ofb.setFiber(Double.parseDouble(Osugar));
			Ofb.setProtein(Double.parseDouble(Oprotein));
			
			// If two objects are the same error message
			if(Ofb.equals(fb)){
				session.setAttribute("err1", "[No Fields Changed in Edit Mode]");
				response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");
				return;
			}	
			
			// We also know that this is unique so a deletion can also occur
			// Seems fields were ok now try adding to see if w-i valid range
			action.addFoodDiary(fb);
					
			// If it made is this far this means a valid change was made in the form and the new entry was added
			// Now we should be able to delete the old one
			action.deleteFoodDiaryEntry(Ofb);
					
			// Done
			// Log that action successfion
			loggingAction.logEvent(TransactionType.EDIT_FOOD_DIARY, loggedInMID.longValue(), loggedInMID.longValue() , "Patient edits a food diary entry");
			
			//Return to View menu
			session.setAttribute("err1", "[Food Diary Entry Successfully Changed!]");
			response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");
			return;
					
			

		} catch (FormValidationException e) { //Need to create a new error to be handled
			session.setAttribute("err1", e.getMessage());
			response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");
			//out.println("<span class=\"font_failure\">" + e.getMessage() + "<br>");			

		} catch (NumberFormatException e) { // handles blank fields of incorrect input
			session.setAttribute("err1", "[Blank Fields: Data Not Submitted] or [Calories and Below Must be Whole Numbers]");
			response.sendRedirect("/iTrust/auth/patient/viewFoodDiaryPat.jsp");
			//out.println("<span class=\"font_failure\">" + "[Blank Fields: Data Not Submitted] or [Calories and Below Must be Whole Numbers]" + "<br>");
		} 
					
		%>
		<span class="font_success">"Food Diary Successfully Added." </span><br>
		<%
	}
	

	//Intial creation of Edit page
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
	

	
	

	
	%>
	
	
<form method="post" action="editFoodDiaryPat.jsp">
<table>
<tr>
<td><input type="text" name="entryDate" value=<%=foodbean.getEntryDate()%>></td><td><span class="font1">Consumption Date</span></td>
</tr>
<tr>
<td><input type="text" name="meal" value=<%=foodbean.getMeal()%>></td><td><span class="font1">Meal</span></td>
</tr>
<tr>
<td><input type="text" name="food" value='<%=foodbean.getFood()%>'></td><td><span class="font1">Food Name</span></td>
</tr>
<tr>
<td><input type="text" name="servings" value=<%=Double.toString(foodbean.getServings())%>></td><td><span class="font1">Servings</span></td>
</tr>
<tr>
<td><input type="text" name="cals" value=<%=Double.toString(foodbean.getCals())%>></td><td><span class="font1">Calories Per Servings</span></td>
</tr>
<tr>
<td><input type="text" name="fat" value=<%=Double.toString(foodbean.getFat())%>></td><td><span class="font1">Grams Fat Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="sodium" value=<%=Double.toString(foodbean.getSodium())%>></td><td><span class="font1">mg Sodium Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="carbs" value=<%=Double.toString(foodbean.getCarbs())%>></td><td><span class="font1">Grams Carbs Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="fiber" value=<%=Double.toString(foodbean.getFiber())%>></td><td><span class="font1">Grams Fiber Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="sugar" value=<%=Double.toString(foodbean.getSugar())%>></td><td><span class="font1">Grams Sugar Per Serving</span></td>
</tr>
<tr>
<td><input type="text" name="protein" value=<%=Double.toString(foodbean.getProtein())%>></td><td><span class="font1">Grams Protein Per Serving</span></td>
</tr>
<tr>
<td>
<input type="hidden" value=<%="yes"%> name="sub">
<input type="hidden" value=<%=foodbean.getEntryDate()%> name="OentryDate">
<input type="hidden" value=<%=foodbean.getMeal()%> name="Omeal">
<input type="hidden" value='<%=foodbean.getFood()%>' name="Ofood">
<input type="hidden" value=<%=Double.toString(foodbean.getServings())%> name="Oservings">
<input type="hidden" value=<%=Double.toString(foodbean.getCals())%> name="Ocals">
<input type="hidden" value=<%=Double.toString(foodbean.getFat())%> name="Ofat">
<input type="hidden" value=<%=Double.toString(foodbean.getSodium())%> name="Osodium">
<input type="hidden" value=<%=Double.toString(foodbean.getCarbs())%> name="Ocarbs">
<input type="hidden" value=<%=Double.toString(foodbean.getSugar())%> name="Osugar">
<input type="hidden" value=<%=Double.toString(foodbean.getFiber())%> name="Ofiber">
<input type="hidden" value=<%=Double.toString(foodbean.getProtein())%> name="Oprotein">
</tr>
</table>
<input type="submit" value="Edit Food Diary Entry">
</form>






<%@include file="/footer.jsp"%>
