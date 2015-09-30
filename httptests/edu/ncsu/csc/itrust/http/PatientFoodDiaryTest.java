package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

public class PatientFoodDiaryTest extends iTrustHTTPTest{

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp11();
		gen.patient0DerekMorgan(); //Newly Added
		gen.patient0JenniferJareau(); //Newly added
		gen.patient0AaronHotchner(); //Newly added;
		gen.icd9cmCodes();
	}
	
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	/*
	 * Authenticate Patient: Derek Morgan
	 * MID: 684
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 2/4/2015 for the date, selects Dinner, enters Fruity Pebbles for food, 
	 * enters 7 servings, 110 cal, 1g of fat, 170mg of sodium, 24g of carbs, 
	 * 0g of fiber, 11g of sugars, and 1g of protein
	 * Confirm and approve the selection
	 */
	public void testScenario1Derek1() throws Exception {
		// login
		WebConversation wc = login("684", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Food Diary").click();
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("entryDate", "02/04/2015");
		form.setParameter("meal", "Dinner");
		form.setParameter("food", "Fruity Pebbles");
		form.setParameter("servings", "7");
		form.setParameter("cals", "110");
		form.setParameter("fat", "1");
		form.setParameter("sodium", "170");
		form.setParameter("carbs", "24");
		form.setParameter("fiber", "0");
		form.setParameter("sugar", "11");
		form.setParameter("protein", "1");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		assertTrue(wr.getText().contains("Food Diary Successfully Added."));
		
		//Check view page
		wr = wc.getCurrentPage();
		wr = wr.getLinkWith("View Food Diary").click();
		assertTrue(wr.getText().contains("02/04/2015"));
		assertTrue(wr.getText().contains("Dinner"));
		assertTrue(wr.getText().contains("Fruity Pebbles"));
	}

	
	/*
	 * Authenticate Patient: Jennifer Jareau 
	 * MID: 685
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 11/12/2014 for the date, selects Snack, enters Cookie Dough Ice Cream for food, 
	 * enters .5 servings, 160 calories, 8g of fat, 45mg of sodium, 21g of carbs, 
	 * 0g of fiber, 16g of sugars, and 2g of protein
	 * Confirm and approve the selection
	 */
	public void testScenarioJennifer2() throws Exception {
		// login
		WebConversation wc = login("685", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
	
		wr = wr.getLinkWith("Add Food Diary").click();
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("entryDate", "11/12/2014");
		form.setParameter("meal", "Snack");
		form.setParameter("food", "Cookie Dough Ice Cream");
		form.setParameter("servings", ".5");
		form.setParameter("cals", "160");
		form.setParameter("fat", "8");
		form.setParameter("sodium", "45");
		form.setParameter("carbs", "21");
		form.setParameter("fiber", "0");
		form.setParameter("sugar", "16");
		form.setParameter("protein", "2");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		assertTrue(wr.getText().contains("Food Diary Successfully Added."));//Check if added correctly
		
		//Check view page to see if added as well as existing ones		
		wr = wc.getCurrentPage();
		wr = wr.getLinkWith("View Food Diary").click();
		assertTrue(wr.getText().contains("Hotdog")); //Old Stuff Scenario 2
		assertTrue(wr.getText().contains("09/30/2012")); //See if old date is there
		assertTrue(wr.getText().contains("Mango Passion Fruit Juice")); //Old stuff Scenario 2
		assertTrue(wr.getText().contains("Cookie Dough Ice Cream")); //Just added
		assertTrue(wr.getText().contains("11/12/2014")); //See if new added date is there
	}
	
	/*
	 * Authenticate HCP: Spencer Reid 9900000025
	 * Password: pw
	 * Choose Food Diary.
	 * Check food diary of MID 2
	 * Check and see if all fields exist
	 */
	public void testScenarioSReid() throws Exception {
		// login
		WebConversation wc = login("9900000025", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Food Diary").click();
		// choose patient 686
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "686");
		patientForm.getButtons()[1].click();
		//Check if information is correct
		WebForm form = wr.getForms()[0];
		//Submit it and update the WebResponse
		wr = form.submit();
		//Check to see if all items are on page now
		assertTrue(wr.getText().contains("Oreos"));
		assertTrue(wr.getText().contains("Cheese and Bean Dip"));
		assertTrue(wr.getText().contains("04/13/2014"));
		assertTrue(wr.getText().contains("05/21/2013"));
		assertTrue(wr.getText().contains("53")); //check unique servings 
		assertTrue(wr.getText().contains(".75")); //check unique servings 
	}
	
	////Test Below are the Five Additional Test
	/*
	 * Authenticate Patient: Jennifer Jareau 
	 * MID: 685
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 11/12/2016 for the date, selects Snack, enters Cookie Dough Ice Cream for food, 
	 * enters .5 servings, 160 calories, 8g of fat, 45mg of sodium, 21g of carbs, 
	 * 0g of fiber, 16g of sugars, and 2g of protein
	 * Confirm and approve the selection
	 * 
	 * Date is in future and invalid
	 * 
	 */
	public void testAddFDBadDate() throws Exception {
		// login
		WebConversation wc = login("685", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Food Diary").click();
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("entryDate", "11/12/2020");
		form.setParameter("meal", "Snack");
		form.setParameter("food", "Cookie Dough Ice Cream");
		form.setParameter("servings", ".5");
		form.setParameter("cals", "160");
		form.setParameter("fat", "8");
		form.setParameter("sodium", "45");
		form.setParameter("carbs", "21");
		form.setParameter("fiber", "0");
		form.setParameter("sugar", "16");
		form.setParameter("protein", "2");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		//Check to see if all items are on page now
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Consumption Date: Restricted to Current or Past Dates]"));
	}
	
	/*
	 * Authenticate Patient: Jennifer Jareau 
	 * MID: 685
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 30/1/2015 for the date, selects Snack, enters Cookie Dough Ice Cream for food, 
	 * enters .5 servings, 160 calories, 8g of fat, 45mg of sodium, 21g of carbs, 
	 * 0g of fiber, 16g of sugars, and 2g of protein
	 * Confirm and approve the selection
	 * 
	 * Date in in DD MM YYYY and is invalid
	 *
	 */
	public void testAddFDInvalidDateFormat() throws Exception {
		// login
		WebConversation wc = login("685", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Food Diary").click();
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("entryDate", "30/1/2015");
		form.setParameter("meal", "Snack");
		form.setParameter("food", "Cookie Dough Ice Cream");
		form.setParameter("servings", ".5");
		form.setParameter("cals", "160");
		form.setParameter("fat", "8");
		form.setParameter("sodium", "45");
		form.setParameter("carbs", "21");
		form.setParameter("fiber", "0");
		form.setParameter("sugar", "16");
		form.setParameter("protein", "2");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		//Check to see if all items are on page now
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Enter Consumption Date in MM/dd/yyyy]"));
	}
	
	/*
	 * Authenticate Patient: Derek Morgan
	 * MID: 684
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 2/4/2015 for the date, selects Dinner, enters Fruity Pebbles for food, 
	 * enters -7 servings, 110 cal, 1g of fat, 170mg of sodium, 24g of carbs, 
	 * 0g of fiber, 11g of sugars, and 1g of protein
	 * Confirm and approve the selection
	 * 
	 * Invalid amount of servings check if error message appears
	 * 
	 */
	public void testInvalidServings() throws Exception {
		// login
		WebConversation wc = login("684", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Food Diary").click();
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("entryDate", "02/04/2015");
		form.setParameter("meal", "Dinner");
		form.setParameter("food", "Fruity Pebbles");
		form.setParameter("servings", "-7");
		form.setParameter("cals", "110");
		form.setParameter("fat", "1");
		form.setParameter("sodium", "170");
		form.setParameter("carbs", "24");
		form.setParameter("fiber", "0");
		form.setParameter("sugar", "11");
		form.setParameter("protein", "1");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Servings: Must be Greater than 0]"));
	}
	
	/*
	 * Authenticate Patient: Derek Morgan
	 * MID: 684
	 * Password: pw
	 * Choose Food Diary.
	 * In the entry boxes below fill the following items
	 * Change Field:
	 * 2/4/2015 for the date, selects Dinner, enters Fruity Pebbles for food, 
	 * enters 7 servings, 110 cal, 1g of fat, 170mg of sodium, 24g of carbs, 
	 * 0g of fiber, 11g of sugars, and 1g of protein
	 * Confirm and approve the selection
	 * 
	 * Invalid amount of servings check if error message appears
	 * 
	 */
	public void testInvalidProteins() throws Exception {
		// login
		WebConversation wc = login("684", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		
		wr = wr.getLinkWith("Add Food Diary").click();
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("entryDate", "02/04/2015");
		form.setParameter("meal", "Dinner");
		form.setParameter("food", "Fruity Pebbles");
		form.setParameter("servings", "7");
		form.setParameter("cals", "110");
		form.setParameter("fat", "1");
		form.setParameter("sodium", "170");
		form.setParameter("carbs", "24");
		form.setParameter("fiber", "0");
		form.setParameter("sugar", "11");
		form.setParameter("protein", "-9000");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Grams Protein Per Servings: Must be 0 or Greater]"));
	}
	
	/*
	 * Authenticate HCP: Spencer Reid 
	 * Password: pw
	 * Choose Food Diary.
	 * Find the entries to MID 684
	 * Nothing has been entered yet so nothing should appear
	 * A No Entries message should be on the page
	 */
	public void testEmptyFoodDiary() throws Exception {
		// login
		WebConversation wc = login("9900000025", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		wr = wr.getLinkWith("View Food Diary").click();
		// choose patient 686
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "684");
		patientForm.getButtons()[1].click();
		//Check if information is correct
		WebForm form = wr.getForms()[0];
		//Submit it and update the WebResponse
		wr = form.submit();
		//Check to that nothing was ever added to the table
		assertFalse(wr.getText().contains("02/04/2015"));
		assertFalse(wr.getText().contains("Dinner"));
		assertFalse(wr.getText().contains("Fruity Pebbles"));
	}
}