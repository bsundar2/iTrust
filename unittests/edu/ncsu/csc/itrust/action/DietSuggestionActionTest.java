package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import edu.ncsu.csc.itrust.beans.DietSuggestionBean;

import java.util.List;


/**
 * @author Team 19
 * This test works with the action file for diet suggestions, it generates some objects
 * and tests through various branches of the action file. We will check for both invalid and valid passing
 * in this unit test case.
 */
public class DietSuggestionActionTest extends TestCase {
	DietSuggestionAction action;
	private TestDataGenerator gen;
	
	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient0DerekMorgan();
		gen.patient0JenniferJareau();
		gen.patient0AaronHotchner();
		gen.hcp11();
		action = new DietSuggestionAction(TestDAOFactory.getTestInstance(), 2L);
	}
	
	/**
	 * Log in as Jennifer Jareau who has an existing suggestoin entry
	 * We are going to check if the correct list size is returned.
	 * Also tests the addSuggestion function of the action class.
	 */
	public void testExistingEntries() throws Exception {
		action = new DietSuggestionAction(TestDAOFactory.getTestInstance(), 685L);
		
		DietSuggestionBean dsBean = new DietSuggestionBean();
		
		dsBean.setPatientID(685L);
		dsBean.setEntryDate("04/13/2014");
		dsBean.setSuggestion("good");
		
		action.addSuggestion(dsBean);
		
		List<DietSuggestionBean> results = action.getSuggestion();
		assertEquals(1, results.size());
	}
	
	public void testGetSuggestionBean() throws Exception {
		action = new DietSuggestionAction(TestDAOFactory.getTestInstance(), 685L);
		
DietSuggestionBean dsBean = new DietSuggestionBean();
		
		dsBean.setPatientID(685L);
		dsBean.setEntryDate("04/13/2014");
		dsBean.setSuggestion("good");
		
		action.addSuggestion(dsBean);
		
		DietSuggestionBean dsb = action.getSuggestionBean("04/13/2014");
		assertEquals("good", dsb.getSuggestion());
		
	}
	
}
