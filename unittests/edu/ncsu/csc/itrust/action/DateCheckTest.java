package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

public class DateCheckTest extends TestCase {
	DateCheck checker;
	
	@Override
	protected void setUp() throws Exception {
		checker = new DateCheck();
	}
	
	public void testSingleDate(){
		
		String valid = "09/30/2012";
		String badformat = "2012/30/09";
		String text = "this is not a date";
		String future = "09/30/2060";
		
		assertTrue(checker.checkSingle(valid));
		assertFalse(checker.checkSingle(badformat));
		assertFalse(checker.checkSingle(text));
		assertFalse(checker.checkSingle(future));
	}
	
	public void testRangeDates(){
		//good
		//bad
		//future
		
		String valid = "09/30/2012";
		String badformat = "2012/30/09";
		String text = "this is not a date";
		String future = "09/30/2060";
		
		assertTrue(checker.checkRange(valid, valid));
		assertFalse(checker.checkRange(badformat, valid));
		assertFalse(checker.checkRange(text, valid));
		assertFalse(checker.checkRange(future, valid));
		
		assertTrue(checker.checkRange(valid, valid));
		assertFalse(checker.checkRange(valid, badformat));
		assertFalse(checker.checkRange(valid, text));
		assertFalse(checker.checkRange(valid, future));
		
	}

}
