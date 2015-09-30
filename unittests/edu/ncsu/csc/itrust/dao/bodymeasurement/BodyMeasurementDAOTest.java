package edu.ncsu.csc.itrust.dao.bodymeasurement;

import java.util.List;

import edu.ncsu.csc.itrust.beans.BodyMeasurementBean;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.BodyMeasurementDAO;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Tests the body measurement DAO class to check and see if inputs are being handled
 * correctly and output is correct as well.
 * 
 * This tests the DAO commands without the indirect calls from the BodyMeasurementAction file.
 * 
 * @author Jay Patel
 * @author Weijia Li
 * @author Yuang Ni
 * @author Balaji Sundaram
 *
 */
public class BodyMeasurementDAOTest extends TestCase{
	DAOFactory factory = TestDAOFactory.getTestInstance();
	private BodyMeasurementDAO fdDAO = factory.getBodyMeasurementDAO();

	/**
	 * Sets up some users for testing
	 */
	@Override
	protected void setUp() throws Exception {
		TestDataGenerator gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient0DerekMorgan();
		gen.patient0JenniferJareau();
		gen.patient0AaronHotchner();
		gen.hcp11();
	}
	
	/**
	 * Logged in as Derek Morgan who has no initial body measurement
	 * entries we will see what he results return
	 * MID 684
	 */
	public void testNoEntries() throws Exception {
		long loggedInPatientMID = 684;
		List<BodyMeasurementBean> results = fdDAO.getBodyMeasurementByMID(loggedInPatientMID);
		assertTrue(results.isEmpty());
	}
	
	/**
	 * Logged in as Jennifer J. who has no initial body measurement
	 * entries we will see what he results return
	 * MID 685
	 */
	public void testExistingEntries() throws Exception {
		long loggedInPatientMID = 685;
		List<BodyMeasurementBean> results = fdDAO.getBodyMeasurementByMID(loggedInPatientMID);
		assertEquals(3,results.size());
	}
	
	/**
	 * Logged in as Jennifer J. who has no initial body measurement
	 * entries we will see what he results return
	 * MID 685
	 */
	public void testExistingEntriesASC() throws Exception {
		long loggedInPatientMID = 685;
		List<BodyMeasurementBean> results = fdDAO.getBodyMeasurementByMIDAscending(loggedInPatientMID);
		assertEquals(3,results.size());
	}
	
	/**
	 * Logged in as Jennifer J. who has no initial body measurement
	 * entries we will see what he results return
	 * MID 685
	 */
	public void testAddEntry() throws Exception {
		long loggedInPatientMID = 685;
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//Creating a valid measurement entry to add
		bodyBean.setPatientID(loggedInPatientMID);
		bodyBean.setLogDate("11/11/2014");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(60.0);
		
		boolean addTest = fdDAO.addBodyMeasurement(bodyBean);
		assertTrue(addTest);
		
		List<BodyMeasurementBean> results = fdDAO.getBodyMeasurementByMID(loggedInPatientMID);
		assertEquals(4,results.size());
	}

}
