package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import java.util.List;
import edu.ncsu.csc.itrust.beans.BodyMeasurementBean;


/**
 * 
 * @author Team 19
 * This test works with the action file for body measurement, it generate some objects
 * and test through various branches of the action file. We will check for both invalid and valid passings
 * in this unit test case.
 *
 */
public class BodyMeasurementActionTest extends TestCase  {
	BodyMeasurementAction action;
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient0DerekMorgan();
		gen.patient0JenniferJareau();
		gen.patient0AaronHotchner();
		gen.hcp11();
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 2L);
	}
	//Jen Jar = 685
	//686 aaron hotch
	
	/**
	 * Log in as Jennifer Jareau who has 3 existing body measurement entries
	 * We are going to check if the correct list size is returned.
	 */
	public void testExistingEntries() throws Exception {
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(3, results.size());
	}
	
	/**
	 * Patient Aaron Hotcher has no entries in the body measurement table
	 * running the return tables should return a 0.
	 */
	public void testNoEntries() throws Exception {
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 686L);
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(0, results.size());
	}
	
	/**
	 * Log in as Jennifer Jareau who has 3 existing body measurement entries
	 * We are going to check if the correct list size is returned.
	 * This is checking the ASC organized version, it should return 3 as well but in a different order
	 */
	public void testExistingEntriesASC() throws Exception {
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		List<BodyMeasurementBean> results = action.getBodyMeasurementWithMIDASC();
		assertEquals(3, results.size());
	}
	
	/**
	 * As Aaron Hotcher a user with 0 entries we are going to add
	 * a correctly formatted entry see if it is successfully added into the data
	 * base and can be returned by other functions
	 */
	public void testAddBodyMeasurement() throws Exception{
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 686L);
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a valid measurement entry to add
		bodyBean.setPatientID(686);
		bodyBean.setLogDate("11/11/2014");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(60.0);
		
		boolean addTest = action.addBodyMeasurement(bodyBean);
		assertTrue(addTest);
		
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(1, results.size());
	}
	
	/**
	 * As Jennifer Jareau we are going to add a body measurement entry
	 * but we are going to incorrectly format the date
	 * an error should be thrown as we are going to catch to make sure 
	 * initially three entries
	 */
	public void testBadDateFormat() throws Exception{
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a invalid measurement entry to add - bad date
		bodyBean.setPatientID(686);
		bodyBean.setLogDate("1/11/2014");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(60.0);
		
		try {
			action.addBodyMeasurement(bodyBean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Enter Log Date in MM/DD/YYYY]", e.getMessage());
		}
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(3, results.size());
	}
	
	/**
	 * As Jennifer Jareau we are going to add a body measurement entry
	 * but we are going to set the date to the future
	 * an error should be thrown as we are going to catch to make sure 
	 * Initially three entries
	 */
	public void testFutureDate() throws Exception{
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a invalid measurement entry to add - future date
		bodyBean.setPatientID(686);
		bodyBean.setLogDate("11/11/2016");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(60.0);
		
		try {
			action.addBodyMeasurement(bodyBean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Log Date: Restricted to Current or Past Dates]", e.getMessage());
		}
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(3, results.size());
	}
	
	/**
	 * As Jennifer Jareau we are going to add a body measurement entry
	 * but we are going to set weight to be a negative double
	 * an error should be thrown as we are going to catch to make sure 
	 * Initially three entries
	 */
	public void testNegWeight() throws Exception{
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a invalid measurement entry to add - negative weight
		bodyBean.setPatientID(686);
		bodyBean.setLogDate("11/11/2014");
		bodyBean.setWeight(-150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(60.0);
		
		try {
			action.addBodyMeasurement(bodyBean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Weight: Must be Greater than 0]", e.getMessage());
		}
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(3, results.size());
	}
	
	/**
	 * As Jennifer Jareau we are going to add a body measurement entry
	 * but we are going to set height to be a negative double
	 * an error should be thrown as we are going to catch to make sure 
	 * Initially three entries
	 */
	public void testNegHeight() throws Exception{
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a invalid measurement entry to add - negative height
		bodyBean.setPatientID(686);
		bodyBean.setLogDate("11/11/2014");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(-65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(60.0);
		
		try {
			action.addBodyMeasurement(bodyBean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Height: Must be Greater than 0]", e.getMessage());
		}
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(3, results.size());
	}
	
	/**
	 * As Jennifer Jareau we are going to add a body measurement entry
	 * but we are going to set waist to be a negative double
	 * an error should be thrown as we are going to catch to make sure 
	 * Initially three entries
	 */
	public void testNegWaist() throws Exception{
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a invalid measurement entry to add - negative waist
		bodyBean.setPatientID(686);
		bodyBean.setLogDate("11/11/2014");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(-30.7);
		bodyBean.setArms(60.0);
		
		try {
			action.addBodyMeasurement(bodyBean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Waist: Must be Greater than 0]", e.getMessage());
		}
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(3, results.size());
	}
	
	/**
	 * As Jennifer Jareau we are going to add a body measurement entry
	 * but we are going to set arms to be a negative double
	 * an error should be thrown as we are going to catch to make sure 
	 * Initially three entries
	 */
	public void testNegArms() throws Exception{
		action = new BodyMeasurementAction(TestDAOFactory.getTestInstance(), 685L);
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a invalid measurement entry to add - negative arms
		bodyBean.setPatientID(686);
		bodyBean.setLogDate("11/11/2014");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(-60.0);
		
		try {
			action.addBodyMeasurement(bodyBean);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Arms/Wing Span: Must be Greater than 0]", e.getMessage());
		}
		List<BodyMeasurementBean> results = action.getBodyMeasurement();
		assertEquals(3, results.size());
	}
}
