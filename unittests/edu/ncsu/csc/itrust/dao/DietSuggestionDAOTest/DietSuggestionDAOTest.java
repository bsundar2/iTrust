package edu.ncsu.csc.itrust.dao.DietSuggestionDAOTest;

import java.util.List;

import edu.ncsu.csc.itrust.beans.DietSuggestionBean;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DietSuggestionDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Tests the diet suggestion DAO class to check and see if inputs are being handled
 * correctly and output is correct as well.
 * 
 * This tests the DAO commands without the indirect calls from the DietSuggestionAction file.
 * 
 * @author Jay Patel
 * @author Weijia Li
 * @author Yuang Ni
 * @author Balaji Sundaram
 *
 */
public class DietSuggestionDAOTest extends TestCase {
	DAOFactory factory = TestDAOFactory.getTestInstance();
	private DietSuggestionDAO dsDAO = factory.getDietSuggestionDAO();

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
	 * Logged in as Derek Morgan who has no initial diet suggestion.
	 * entries we will see what the results return
	 * MID 684
	 */
	public void testNoEntries() throws Exception {
		long loggedInPatientMID = 684;
		List<DietSuggestionBean> results = dsDAO.getSuggestionByMID(loggedInPatientMID);
		assertTrue(results.isEmpty());
	}
	
	/**
	 * Logged in as Jennifer Jareau who has a suggestion.
	 * entries we will see what he results return
	 * Tests the addSuggestion method as well.
	 * MID 685
	 */
	public void testExistingEntries() throws Exception {
		long loggedInPatientMID = 685;
		DietSuggestionBean dsBean = new DietSuggestionBean();
		
		dsBean.setPatientID(loggedInPatientMID);
		dsBean.setEntryDate("04/13/2014");
		dsBean.setSuggestion("good");
		
		dsDAO.addSuggestion(dsBean);
		
		List<DietSuggestionBean> results = dsDAO.getSuggestionByMID(loggedInPatientMID);
		assertEquals(1,results.size());
	}
	
	/**
	 * Logged in as Jennifer Jareau who has a suggestion.
	 * entries we will see what he results return
	 * MID 685
	 */
	public void testExistingEntryBean() throws Exception {
		long loggedInPatientMID = 685;
		
DietSuggestionBean dsBean = new DietSuggestionBean();
		
		dsBean.setPatientID(loggedInPatientMID);
		dsBean.setEntryDate("04/13/2014");
		dsBean.setSuggestion("good");
		
		dsDAO.addSuggestion(dsBean);
		
		DietSuggestionBean dsb = dsDAO.getSuggestionByDate(loggedInPatientMID, "04/13/2014");
		
		assertEquals("good", dsb.getSuggestion());
	}
	
	
}
