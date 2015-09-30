package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PatientFoodDiaryEditDeleteTest extends iTrustHTTPTest{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp11();
		gen.patient0DerekMorgan(); //Newly Added
		gen.patient0JenniferJareau(); //Newly added
		gen.patient0AaronHotchner(); //Newly added;
		gen.patient0EmilyPrentiss();
		gen.icd9cmCodes();
	}
	
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/*
	 * Authenticate Patient: Emily Prentiss
	 * MID: 687
	 * Password: pw
	 * Choose View Food Diary.
	 * For the row 03/16/1014 Chocolate Shake press "edit"
	 * Enter the following valid values on the page
	 * Servings 3
	 * Calories 1327
	 * Fat 62.5
	 * Sodium 687
	 * Carbs 176.4
	 * Sugars 112.4
	 * Protein 15.6
	 * and press submit
	 */
	public void testScenario1EditValidValues() throws Exception {
		// login
		WebConversation wc = login("687", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Food Diary").click();
		
		//Check if correct page
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		wr = form.submit();
		
		//Check view page
		wr = wc.getCurrentPage();
		
		//Check if correct page
		assertEquals("iTrust - Edit Patient Food Diary", wr.getTitle());
		
		//lets fill out the edit page
		WebForm editForm = wr.getForms()[0];
		editForm.setParameter("servings", "3");
		editForm.setParameter("cals", "1327");
		editForm.setParameter("fat", "62.5");
		editForm.setParameter("sodium", "687");
		editForm.setParameter("carbs", "176.4");
		editForm.setParameter("sugar", "112.4");
		editForm.setParameter("protein", "15.6");
		wr = editForm.submit();
		
		//check if some values are update and check if totals are updated
		//value check
		assertTrue(wr.getText().contains("1327"));
		assertTrue(wr.getText().contains("62.5"));
		assertTrue(wr.getText().contains("687"));
		assertTrue(wr.getText().contains("176.4"));
		assertTrue(wr.getText().contains("112.4"));
		assertTrue(wr.getText().contains("15.6"));
		
		//total check
		assertTrue(wr.getText().contains("3981")); //totals cals
		assertTrue(wr.getText().contains("187.5")); //totals fat
		
		//check for completion message
		assertTrue(wr.getText().contains("[Food Diary Entry Successfully Changed!]")); //totals fat
		
		//Check log
		assertLogged(TransactionType.EDIT_FOOD_DIARY, 687L, 687L, "Patient edits a food diary entry");
	}
	
	/*
	 * Authenticate Patient: Aaron Hotchner
	 * MID: 686
	 * Password: pw
	 * Choose View Food Diary.
	 * For the row 04/13/2014 Oreos press "edit"
	 * Enter the following valid values on the page
	 * Servings -17
	 * and press submit
	 */
	public void testScenario2EditInvalidValues() throws Exception {
		// login
		WebConversation wc = login("686", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Food Diary").click();
		
		//Check if correct page
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		wr = form.submit();
		
		//Check view page
		wr = wc.getCurrentPage();
		
		//Check if correct page
		assertEquals("iTrust - Edit Patient Food Diary", wr.getTitle());
		
		//lets fill out the edit page
		WebForm editForm = wr.getForms()[0];
		editForm.setParameter("servings", "-17");
		wr = editForm.submit();
		
		//Check if redirection successful
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//Assert that the error message was displayed
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Servings: Must be Greater than 0]"));
		
		//Check that he oreos servings are not changed
		assertFalse(wr.getText().contains("-17"));
		assertTrue(wr.getText().contains("53"));
		
		//Check some totals to make sure they are the same
		assertTrue(wr.getText().contains("7420.0")); // Calories total oreos
		assertTrue(wr.getText().contains("371.0")); // Fat total oreos
		assertTrue(wr.getText().contains("4770.0")); // Sodium total oreos
		
		//Failure so need to check logs
	}
	
	/*
	 * Authenticate Patient: Jennifer Jareau
	 * MID: 685
	 * Password: pw
	 * Choose View Food Diary.
	 * Confirms to delete entry 09/30/2012 HotDog
	 * and press ok
	 */
	public void testScenario3DeleteEntry() throws Exception {
		// login
		WebConversation wc = login("685", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Food Diary").click();
		
		//Check if correct page
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//Check that hotdog exists on the page before deletion
		assertTrue(wr.getText().contains("Hotdog"));
		
		//Get the form and delete the entry
		WebForm form = wr.getForms()[1];
		wr = form.submit();
		
		//Check view page
		wr = wc.getCurrentPage();
		
		//Check if correct page
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//Check that hotdogs was deleted
		assertFalse(wr.getText().contains("Hotdog"));
		
		//Check for updated sums
		assertTrue(wr.getText().contains("156.0")); // cals
		assertTrue(wr.getText().contains("0.0")); // fat
		assertTrue(wr.getText().contains("30.0")); // sod
		assertTrue(wr.getText().contains("38.4")); // carbs
		assertTrue(wr.getText().contains("34.8")); // sugar
		assertTrue(wr.getText().contains("0.0")); // fiber
		assertTrue(wr.getText().contains("1.2")); // protein
		
		//Check log
		assertLogged(TransactionType.DELETE_FOOD_DIARY, 685L, 685L, "Patient deletes a food diary entry");
	}
	
	/*
	 * Authenticate Patient: Emily Prentiss
	 * MID: 687
	 * Password: pw
	 * Choose View Food Diary.
	 * For the row 03/16/1014 Chocolate Shake press "edit"
	 * Enter the following valid values on the page
	 * Meal fourthmeal
	 * and press submit
	 */
	public void testScenario4EditInvalidMeal() throws Exception {
		// login
		WebConversation wc = login("687", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Food Diary").click();
		
		//Check if correct page
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		wr = form.submit();
		
		//Check view page
		wr = wc.getCurrentPage();
		
		//Check if correct page
		assertEquals("iTrust - Edit Patient Food Diary", wr.getTitle());
		
		//lets fill out the edit page
		WebForm editForm = wr.getForms()[0];
		editForm.setParameter("meal", "fourthmeal");
		wr = editForm.submit();
		
		//Check if correct page redirection
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//check if some values are update and check if totals are updated
		//value check
		assertTrue(wr.getText().contains("Lunch")); // Meal
		assertTrue(wr.getText().contains("500")); // Cals
		assertTrue(wr.getText().contains("23.5")); // Fat
		
		//check for error message
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Meal: Restricted to Breakfast, Lunch, Dinner, or Snack]")); //totals fat
	}
	
	/*
	 * Authenticate Patient: Emily Prentiss
	 * MID: 687
	 * Password: pw
	 * Choose View Food Diary.
	 * For the row 03/16/1014 Chocolate Shake press "edit"
	 * Enter the following valid values on the page
	 * Servings 'blank'
	 * and press submit
	 */
	public void testScenario5EditBlankField() throws Exception {
		// login
		WebConversation wc = login("687", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Food Diary").click();
		
		//Check if correct page
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		wr = form.submit();
		
		//Check view page
		wr = wc.getCurrentPage();
		
		//Check if correct page
		assertEquals("iTrust - Edit Patient Food Diary", wr.getTitle());
		
		//lets fill out the edit page
		WebForm editForm = wr.getForms()[0];
		editForm.setParameter("servings", "");
		wr = editForm.submit();
		
		//Check if correct page redirection
		assertEquals("iTrust - View Patient Food Diary", wr.getTitle());
		
		//check if some values are update and check if totals are updated
		//value check
		assertTrue(wr.getText().contains("Lunch")); // Meal
		assertTrue(wr.getText().contains("500")); // Cals
		assertTrue(wr.getText().contains("23.5")); // Fat
		
		//check for error message
		assertTrue(wr.getText().contains("[Servings, Calories, Fat, Sodium, Carbs, Fiber, Sugar, and Protein - Must not be Blank!]")); //totals fat
	}
}