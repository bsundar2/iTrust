package edu.ncsu.csc.itrust.http;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;

/**
 * 
 * @author Group 19
 *
 * This class does all the HTTPUnit tests of the body measurement classes
 * it takes the black box test scenarios that were designed and but them into an
 * automated form. We will check for form validation in this test as well as permissions and
 * correct graphic rendering.
 */
public class BodyMeasurementTest extends iTrustHTTPTest{
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.hcp11();
		gen.hcp0();
		gen.patient1(); //Newly Added
		gen.patient0JenniferJareau(); //Newly added
		gen.icd9cmCodes();
	}
	
	@Override
	protected void tearDown() throws Exception {
		gen.clearAllTables();
	}
	
	public void testViewEmptyWeightCharts() throws Exception{
		// login
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		wr = wr.getLinkWith("View Body Measurements").click();
		wr = wr.getLinkWith("View Chart").click();
		assertTrue(wr.getText().contains("No Past Body Measurements Found"));
	}
	
	/**
	 * We log in as Nutritionist Spencer Ried and he is going to view
	 * a patients (Random Person's) weight charts, Random person does not have
	 * any entries so instead of a chart rendering a message should appear that no entries
	 * were found.
	 * @throws Exception
	 */
	public void testViewWeightGoalsCharts() throws Exception{
		// login
		WebConversation wc = login("9900000025", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		//View the body measurements
		wr = wr.getLinkWith("View Body Measurements").click();
		
		// choose patient 685
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "685");
		patientForm.getButtons()[1].click();
		//Check if information is correct
		WebForm form = wr.getForms()[0];
		//Submit it and update the WebResponse
		wr = form.submit();
		
		//View her chart
		wr = wr.getLinkWith("View Chart").click();
		//Check that an image was returned therefor the words were never printed.
		
		assertTrue(!wr.getText().contains("No Past Body Measurements Found"));
	}
	
	/**
	 * HCP0 Kelly is going to check out Jennifer Jareau's weight records
	 * the issue is that Kelly is not assigned as a nutritionist as a role
	 * so when Kelly tries to access body measurement data she will be
	 * redirected to her homepage.
	 * @throws Exception
	 */
	public void testWeightGoalsAsNonNutritionist() throws Exception{
		// login
		WebConversation wc = login("9000000000", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - HCP Home", wr.getTitle());
		
		//View the body measurements
		wr = wr.getLinkWith("View Body Measurements").click();
		
		// choose patient 685
		WebForm patientForm = wr.getForms()[0];
		patientForm.getScriptableObject().setParameterValue("UID_PATIENTID", "685");
		patientForm.getButtons()[1].click();
		//Check if information is correct
		WebForm form = wr.getForms()[0];
		//Submit it and update the WebResponse
		wr = form.submit();
		
		//Kelly was redirected back to home
		assertEquals("iTrust - HCP Home", wr.getTitle());
	}
	
	/**
	 * Patient 1 random person is going to add a new entry to 
	 * his body measurements. He will enter 02/04/2017 for the log date
	 * 2 for the weight, 2 for the height, 2 for the waist, and 2 for the arms
	 * Since his date in set in the future the test will fail since it is not allowed.
	 * @throws Exception
	 */
	public void testInvalidDate() throws Exception{
		// login
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		wr = wr.getLinkWith("Add Body Measurements").click();
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("logDate", "02/04/2017");
		form.setParameter("weight", "2");
		form.setParameter("height", "2");
		form.setParameter("waist", "2");
		form.setParameter("arms", "2");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		
		//See that it notices the date was in the future
		//System.out.println(wr.getText());
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Log Date: Restricted to Current or Past Dates]"));
	}
	
	/**
	 * Patient 1 random person is going to add a new entry to 
	 * his body measurements. He will enter 02/04/2014 for the log date
	 * -2 for the weight, 2 for the height, 2 for the waist, and 2 for the arms
	 * Since his weight is a negative number the form will not submit and an error message will be
	 * printed on the page.
	 * @throws Exception
	 */
	public void testInvalidWeight() throws Exception{
		// login
		WebConversation wc = login("1", "pw");
		WebResponse wr = wc.getCurrentPage();
		assertEquals("iTrust - Patient Home", wr.getTitle());
		wr = wr.getLinkWith("Add Body Measurements").click();
		
		//Get the form and change the name to test 
		WebForm form = wr.getForms()[0];
		form.setParameter("logDate", "02/04/2014");
		form.setParameter("weight", "-2");
		form.setParameter("height", "2");
		form.setParameter("waist", "2");
		form.setParameter("arms", "2");
		//Submit it and update the WebResponse
		wr = form.submit();
		form = wr.getForms()[0]; //Get the current form
		
		//See that it notices the weight was negative
		assertTrue(wr.getText().contains("This form has not been validated correctly. The following field are not properly filled in: [Weight: Must be Greater than 0]"));
	}

}
