<%@page import="java.util.List"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="edu.ncsu.csc.itrust.dao.DAOFactory"%>
<%@page import="edu.ncsu.csc.itrust.BeanBuilder"%>

<%@page import="edu.ncsu.csc.itrust.exception.FormValidationException"%>
<%@page import="edu.ncsu.csc.itrust.beans.PersonnelBean"%>
<%@page import="edu.ncsu.csc.itrust.dao.mysql.PersonnelDAO"%>

<%@page import="edu.ncsu.csc.itrust.action.FoodDiaryAction"%> <% //  Fooddiaryaction %> 
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%> <% //  Changed to EntryBean %> 
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>
<%@page import="java.util.ArrayList"%>

<%@page import="edu.ncsu.csc.itrust.action.ViewHealthRecordsHistoryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.HealthRecord"%>

<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Patient Food Diary";
%>

<%@include file="/header.jsp" %>
<itrust:patientNav thisTitle="Patient Food Diary" />

<%
// Require a Patient ID first
String pidString = (String)session.getAttribute("pid");
if (pidString == null || 1 > pidString.length()) {
	response.sendRedirect("/iTrust/auth/getPatientID.jsp?forward=nutritionist/viewFoodDiaryNut.jsp");
   	return;
}

//TEMP REDIRECT CODE REMOVE WHEN PROBLEM SOLVED
//PersonnelDAO pd = new PersonnelDAO(prodDAO);
//String s = pd.getRole(loggedInMID.longValue());
//if(!s.equals("nutritionist")){
//	response.sendRedirect("/iTrust/auth/hcp/home.jsp");
//}



long pidLong = Long.parseLong(pidString);

FoodDiaryAction action = new FoodDiaryAction(prodDAO, pidLong);
List<FoodDiaryBean> eatlist = action.getFoodDiary();
SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

loggingAction.logEvent(TransactionType.VIEW_FOOD_DIARY, loggedInMID.longValue(), pidLong , "Nutritionist views patient food diary");



//Create ViewHealthRecordsHistoryAction object to interact with patient's historical health metric history
ViewHealthRecordsHistoryAction historyAction = new ViewHealthRecordsHistoryAction(prodDAO,pidString,loggedInMID.longValue());
//Get the patient's name
String patientName = historyAction.getPatientName();


%>

<table class="fTable" align="center" id="foodDiaryTable">
    <tr>
        <th colspan="11"> <%=StringEscapeUtils.escapeHtml(patientName)%>'s Food Diary</th>
    </tr>

    <tr class="subHeader">
        <td style="text-align: center">Consumption<br>Date</td>
        <td style="text-align: center">Type of<br>Meal</td>
        <td style="text-align: center">Name of<br>food</td>
        <td style="text-align: center">Servings</td>
        <td style="text-align: center">Calories per<br>Serving</td>
        <td style="text-align: center">Grams Fat per<br>Serving</td>
        <td style="text-align: center">mg Sodium per<br>Serving</td>
        <td style="text-align: center">Grams Carbs per<br>Serving</td>
        <td style="text-align: center">Grams Sugars per<br>Serving</td>
        <td style="text-align: center">Grams Fiber per<br>Serving</td>
        <td style="text-align: center">Grams Protein per<br>Serving</td>
    </tr>
    
<%
for (FoodDiaryBean fdbean: eatlist) {
%>
        <tr>
            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(fdbean.getEntryDate()) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(fdbean.getMeal()) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(fdbean.getFood()) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getServings())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Integer.toString(fdbean.getCals())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Integer.toString(fdbean.getFat())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Integer.toString(fdbean.getSodium())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Integer.toString(fdbean.getCarbs())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Integer.toString(fdbean.getSugar())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Integer.toString(fdbean.getFiber())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Integer.toString(fdbean.getProtein())) %>
            </td>
        </tr>
<%
}
%>
<a href="/iTrust/auth/getPatientID.jsp?forward=nutritionist/viewFoodDiaryNut.jsp">Switch Patient</a>
    
</table>

	<table class="fTable" align="center" id="foodDiaryTotals">
    <tr>
        <th colspan="11">Patient Daily Totals</th>
    </tr>

    <tr class="subHeader">
        <td style="text-align: center"><br>Date</td>
        <td style="text-align: center">Calories</td>
        <td style="text-align: center">Grams Fat</td>
        <td style="text-align: center">mg Sodium</td>
        <td style="text-align: center">Grams Carbs</td>
        <td style="text-align: center">Grams Sugars</td>
        <td style="text-align: center">Grams Fiber</td>
        <td style="text-align: center">Grams Protein</td>
    </tr>

<%

double dCal = 0;
double dFat = 0;
double dSod = 0;
double dCarbs = 0;
double dSugar = 0;
double dFiber = 0;
double dProt = 0;
List<String> dateCheck = new ArrayList<String>();
for(FoodDiaryBean superDate: eatlist) {
	
	String entDate = superDate.getEntryDate();
	//Check for duplicates
	if( dateCheck.size() == 0 || !(dateCheck.contains(entDate)) ){
		dateCheck.add(entDate);
	} else {
		dCal = 0;
		dFat = 0;
		dSod = 0;
		dCarbs = 0;
		dSugar = 0;
		dFiber = 0;
		dProt = 0;
		continue; //skips if already exists
	}
	
	for (FoodDiaryBean foodbean: eatlist) {
		if(foodbean.getEntryDate().equals(entDate)){
			double serve = foodbean.getServings();
			dCal += (double)foodbean.getCals() * serve;
			dFat += (double)foodbean.getFat() * serve;
			dSod += (double)foodbean.getSodium() * serve;
			dCarbs += (double)foodbean.getCarbs() * serve;
			dSugar += (double)foodbean.getSugar() * serve;
			dFiber += (double)foodbean.getFiber() * serve;
			dProt += (double)foodbean.getProtein() * serve;
		}
	}
%>	

	<tr>
					<td style="text-align: center; min-width: 8em">
	        <%= StringEscapeUtils.escapeHtml(entDate) %>				
	</td>			<td style="text-align: center; min-width: 8em">
            <%= StringEscapeUtils.escapeHtml(Double.toString(dCal)) %>
    </td>            <td style="text-align: center; min-width: 8em">
            <%= StringEscapeUtils.escapeHtml(Double.toString(dFat)) %>
    </td>            <td style="text-align: center; min-width: 8em">
            <%= StringEscapeUtils.escapeHtml(Double.toString(dSod)) %>
    </td>            <td style="text-align: center; min-width: 8em">
            <%= StringEscapeUtils.escapeHtml(Double.toString(dCarbs)) %>
    </td>            <td style="text-align: center; min-width: 8em">
            <%= StringEscapeUtils.escapeHtml(Double.toString(dSugar)) %>
    </td>            <td style="text-align: center; min-width: 8em">
            <%= StringEscapeUtils.escapeHtml(Double.toString(dFiber)) %>
    </td>            <td style="text-align: center; min-width: 8em">
            <%= StringEscapeUtils.escapeHtml(Double.toString(dProt)) %>
    </td>               
	</tr>
	
<%
	
	//reset counter
	dCal = 0;
	dFat = 0;
	dSod = 0;
	dCarbs = 0;
	dSugar = 0;
	dFiber = 0;
	dProt = 0;
}

%>

	</table>

<%@include file="/footer.jsp" %>
