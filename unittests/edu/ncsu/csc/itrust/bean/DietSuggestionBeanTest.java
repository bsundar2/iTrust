package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.DietSuggestionBean;

/**
 * 
 * @author Team 19
 * This test class simply creates a diet suggestion bean object and sets some values
 * if the values are set correctly then the test will pass by using the getters.
 *
 */
public class DietSuggestionBeanTest extends TestCase {
	public void testDietSuggestionBean() {
		//Create object
		DietSuggestionBean suggestionBean = new DietSuggestionBean();
		
		//fill with some data
		suggestionBean.setPatientID(685);
		suggestionBean.setEntryDate("11/11/2014");
		suggestionBean.setSuggestion("Good job");
		
		assertEquals(685, suggestionBean.getPatientID());
		assertEquals("11/11/2014", suggestionBean.getEntryDate());
		assertEquals("Good job", suggestionBean.getSuggestion());
	}
}
