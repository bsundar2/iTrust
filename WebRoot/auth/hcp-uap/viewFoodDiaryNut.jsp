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

<%@page import="edu.ncsu.csc.itrust.action.FoodDiaryAction"%> <% //  Fooddiaryaction %> 
<%@page import="edu.ncsu.csc.itrust.beans.FoodDiaryBean"%> <% //  Changed to EntryBean %>

<%@page import="edu.ncsu.csc.itrust.action.DietSuggestionAction"%>  
<%@page import="edu.ncsu.csc.itrust.beans.DietSuggestionBean"%> 

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@ page import="java.io.BufferedReader" %>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>  

<%@page import="edu.ncsu.csc.itrust.action.ViewHealthRecordsHistoryAction"%>
<%@page import="edu.ncsu.csc.itrust.beans.HealthRecord"%>

<%@page import="edu.ncsu.csc.itrust.exception.ITrustException"%>
<%@page import="edu.ncsu.csc.itrust.exception.DBException"%>


<%@include file="/global.jsp" %>

<%
pageTitle = "iTrust - View Patient Food Diary";
%>

<%@include file="/header.jsp" %>

<form action="categorizeFoodDiary.jsp">
 <input type="submit" value="Reselect Dates"/>  
</form>

<br>
<itrust:patientNav thisTitle="Patient Food Diary" />

<%


String pidString = (String)session.getAttribute("pid");
long pidLong = Long.parseLong(pidString);

FoodDiaryAction action = new FoodDiaryAction(prodDAO, pidLong);
List<FoodDiaryBean> eatlist = action.getFoodDiary();

DietSuggestionAction suggestionAction = new DietSuggestionAction(prodDAO, pidLong);
List<DietSuggestionBean> suggestionList = suggestionAction.getSuggestion();
List<FoodDiaryBean> newlist = new ArrayList<FoodDiaryBean>();

SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

//Used for Filtering
String single = (String)session.getAttribute("single");
String start = (String)session.getAttribute("start");
String end = (String)session.getAttribute("end");

//Already validated so safe assumption
if(single !=null){
	for (int p = 0; p < eatlist.size(); p++) {
		if(eatlist.get(p).getEntryDate().equals(single)){
			newlist.add(eatlist.get(p));
		}
	}
	eatlist = newlist; //set single date formatted list
}else if( start !=null && end != null ){
	Date s = dateFormat.parse(start);
	Date e = dateFormat.parse(end);
	for (int n = 0; n < eatlist.size(); n++) {
	String  c = eatlist.get(n).getEntryDate(); //the current interative date
	Date current = dateFormat.parse(c); //Change the string date to a date formatted thing
		if(current.before(s)){
			continue;
		}else if(current.after(e)){
			continue;
		}else{
			newlist.add(eatlist.get(n));
		}
	}
	eatlist = newlist; //set range date formatted list
}


String successString = (String)session.getAttribute("success");
if (successString != null) {
	out.println("<div align=center> <span style=\"color:green\"  class=\"font_success\">" + (String)session.getAttribute("success") + "<br></span></div>");
	session.setAttribute("success", null);
}


String suggestion = request.getParameter("suggestion");
// System.out.println("Suggestion: " + suggestion);

String sub = request.getParameter("sub");	
// System.out.println("SUB: " + sub);
if(sub == null) {
	sub = "no";
}


String entryDate = request.getParameter("entry");
if(entryDate == null) {
	entryDate = "";
}

if(sub.equals("yes")){
	try {
		DietSuggestionBean dsBean = new DietSuggestionBean();	
		
		
		dsBean.setPatientID(pidLong);

		dsBean.setEntryDate(entryDate);
		dsBean.setSuggestion(suggestion);
		
		suggestionAction.addSuggestion(dsBean);
		
		session.setAttribute("err1", "[Food Diary Entry Successfully Changed!]");
		
		
		session.setAttribute("success", "Suggestion Successfully Added");
		response.sendRedirect("/iTrust/auth/hcp-uap/viewFoodDiaryNut.jsp");
		
		%>
		Suggestion Successfully Added.
		<%
		
		return;

	} catch (FormValidationException e) { //Need to create a new error to be handled
		session.setAttribute("err1", e.getMessage());
		response.sendRedirect("/iTrust/auth/hcp-uap/viewFoodDiaryNut.jsp");
		//out.println("<span class=\"font_failure\">" + e.getMessage() + "<br>");	
	}
	
	
}


loggingAction.logEvent(TransactionType.VIEW_FOOD_DIARY, loggedInMID.longValue(), pidLong , "Nutritionist views patient food diary");
loggingAction.logEvent(TransactionType.ADD_SUGGESTION, loggedInMID.longValue(), pidLong , "Nutritionist adds a suggestion to patient's food diary entry");




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
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getCals())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getFat())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getSodium())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getCarbs())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getSugar())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getFiber())) %>
            </td>            <td style="text-align: center; min-width: 8em">
                    <%= StringEscapeUtils.escapeHtml(Double.toString(fdbean.getProtein())) %>
            </td>
        </tr>
<%
}
%>
    
</table>

	<table class="fTable" align="center" id="foodDiaryTotals">
    <tr>
        <th colspan="11">Patient Daily Totals <input type="hidden" name="formIsFilled" value="true">
        </th>
    </tr>

    <tr class="subHeader">
        <td style="text-align: center">Date</td>
        <td style="text-align: center">Calories</td>
        <td style="text-align: center">Grams Fat</td>
        <td style="text-align: center">mg Sodium</td>
        <td style="text-align: center">Grams Carbs</td>
        <td style="text-align: center">Grams Sugars</td>
        <td style="text-align: center">Grams Fiber</td>
        <td style="text-align: center">Grams Protein</td>
        <td style="text-align: center">Nutritionist's Suggestions</td>
        <td style="text-align: center"></td>
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

	DietSuggestionBean dsbn = null;
	
	try{
		dsbn = suggestionAction.getSuggestionBean(entDate);
	} catch(DBException e) {
		dsbn = new DietSuggestionBean();
	}
	 
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
    <form action="viewFoodDiaryNut.jsp" method="post">
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
    	

    <%
  //  for(DietSuggestionBean dsb: suggestionList) {
    	if(suggestionList.size() == 0) {
    		%> 
    	    <td> <input name="suggestion"
    				value="<%=StringEscapeUtils.escapeHtml("")%>"
    				type="text">
    		</td>
    		<input type="hidden" value=<%=superDate.getEntryDate() %> name="entry">
    		<input type="hidden" value=<%="yes"%> name="sub">
    		
    		<%} 
    	else if(dsbn == null){
    			%> 
    		    <td> <input name="suggestion"
    					value="<%=StringEscapeUtils.escapeHtml("")%>"
    					type="text">
    			</td>
    			<input type="hidden" value=<%=superDate.getEntryDate() %> name="entry">
    			<input type="hidden" value=<%="yes"%> name="sub">
    			
    			<% 
    	}
    	else {
    		%> 
		    <td> <input name="suggestion"
					value="<%=StringEscapeUtils.escapeHtml(dsbn.getSuggestion())%>"
					type="text">
			</td>
			<input type="hidden" value=<%=superDate.getEntryDate() %> name="entry">
			<input type="hidden" value=<%="yes"%> name="sub">
			
			<%
    		
    	}
   // }
	
	%>
	
	<td> <input type="submit" 
				value="Save">
	</td>
	</form>
	
				
	</tr>
	</form>
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
