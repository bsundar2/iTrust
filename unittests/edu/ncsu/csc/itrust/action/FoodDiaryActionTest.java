package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.FormValidationException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;

/**
 * Tests the FoodDiaryAction class
 * Does the various tasks that the class is assigned to do and checks results
 * to see if the out and inputs are correctly done
 * @author Jay Patel
 * @author Evin Clapper
 */
public class FoodDiaryActionTest extends TestCase {
	FoodDiaryAction action;
	private TestDataGenerator gen;

	@Override
	protected void setUp() throws Exception {
		gen = new TestDataGenerator();
		gen.clearAllTables();
		gen.patient0DerekMorgan();
		gen.patient0JenniferJareau();
		gen.patient0AaronHotchner();
		gen.hcp11();
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 2L);
	}

	/**
	 * Logged in as Derek Morgan who has no initial food diary
	 * entries we will see what he results return
	 * MID 684
	 */
	public void testNoEntries() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 684L);
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertTrue(results.isEmpty());
	}
	
	/**
	 * Logged in as Jennifer Jareau who already has some
	 * entries in her food diary it should return 2 of them
	 * MID 685
	 */
	public void testExistingEntries() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 685L);
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size());
	}
	
	/**
	 * tests that the size of someones entry goes down after it gets deleted
	 * Logged in as Jennifer Jareau who already has some
	 * entries in her food diary it should return 2 of them
	 * MID 685
	 */
	public void testDeleteEntry() throws Exception {
		//Currently has two
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 685L);
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size());
		
		//Erase an entry
		FoodDiaryBean eraseMe = results.get(0);
		action.deleteFoodDiaryEntry(eraseMe);
		
		//get a new count
		results = action.getFoodDiary();
		assertEquals(1, results.size());
	}
	
	/**
	 * Arron Hotchner already has two existing food diaries
	 * we are going to add one more and see if it gets added to the database 
	 * and if the result set updates
	 * MID 686
	 */
	public void testAddFoodDiaries() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2011");
		mealToEat.setMeal("snack");
		mealToEat.setFood("Apple");
		mealToEat.setServings(20);
		mealToEat.setCals(5);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		boolean addTest = action.addFoodDiary(mealToEat);
		assertTrue(addTest); //If correctly added
		//Now check for a total of three
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(3, results.size()); //He initially has two
	}
	
	/**
	 * We will be using MID 686 for this test
	 * and we are going to add a diary entry with an incorrectly formatted
	 * date it should catch the exception
	 */
	public void testBadFormatDate() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("2011/12/02");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(20);
		mealToEat.setCals(5);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Enter Consumption Date in MM/dd/yyyy]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * and we are going to add a diary entry with future date
	 * it should catch the exception and nothing added
	 */
	public void testLaterDate() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2025");
		mealToEat.setMeal("lunch");
		mealToEat.setFood("Apple");
		mealToEat.setServings(20);
		mealToEat.setCals(5);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Consumption Date: Restricted to Current or Past Dates]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a bad meal type and see that an
	 * exception is thrown, also entry is not added
	 */
	public void testBadMeal() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("fourthmeal");
		mealToEat.setFood("Apple");
		mealToEat.setServings(20);
		mealToEat.setCals(5);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Meal: Restricted to Breakfast, Lunch, Dinner, or Snack]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will pass the function incorrect food string types
	 * empty, null, and white space all should throw errors and
	 * not be added to database
	 */
	public void testFoodErrors() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("breakfast");
		mealToEat.setFood("");
		mealToEat.setServings(20);
		mealToEat.setCals(5);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		//blank
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Food Name: Cannot Leave Blank]", e.getMessage());
		}
		//null
		mealToEat.setFood(null);
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Food Name: Cannot Leave Blank]", e.getMessage());
		}
		//whitespace
		mealToEat.setFood("     ");
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Food Name: Cannot Leave Blank]", e.getMessage());
		}
		//No entries were added in the process		
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
		
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a bad number of servings to test exception
	 * throwing, the entry is also not added
	 */
	public void testServings() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(0);
		mealToEat.setCals(5);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Servings: Must be Greater than 0]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a negative number of calories
	 * and see if exception is thrown and entry is not added
	 */
	public void testCals() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(2.3);
		mealToEat.setCals(-53);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Calories Per Servings: Must be 0 or Greater]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a negative number of fat
	 * and see if exception is thrown and entry is not added
	 */
	public void testFat() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(2.3);
		mealToEat.setCals(27);
		mealToEat.setFat(-50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Grams Fat Per Servings: Must be 0 or Greater]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a negative number of sodium
	 * and see if exception is thrown and entry is not added
	 */
	public void testSodium() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(2.3);
		mealToEat.setCals(27);
		mealToEat.setFat(50);
		mealToEat.setSodium(-20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [mg Sodium Per Servings: Must be 0 or Greater]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a negative number of carbs
	 * and see if exception is thrown and entry is not added
	 */
	public void testCarbs() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(2.3);
		mealToEat.setCals(27);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(-10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Grams Carbs Per Servings: Must be 0 or Greater]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a negative number of fiber
	 * and see if exception is thrown and entry is not added
	 */
	public void testFiber() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(2.3);
		mealToEat.setCals(27);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(-10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Grams Fiber Per Servings: Must be 0 or Greater]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a negative number of sugar
	 * and see if exception is thrown and entry is not added
	 */
	public void testSugar() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(2.3);
		mealToEat.setCals(27);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(-2);
		mealToEat.setProtein(2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Grams Sugar Per Servings: Must be 0 or Greater]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	/**
	 * We will be using MID 686 for this test
	 * We are going to pass a negative number of protein
	 * and see if exception is thrown and entry is not added
	 */
	public void testProtein() throws Exception {
		action = new FoodDiaryAction(TestDAOFactory.getTestInstance(), 686L);
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(686);
		mealToEat.setEntryDate("11/12/2012");
		mealToEat.setMeal("dinner");
		mealToEat.setFood("Apple");
		mealToEat.setServings(2.3);
		mealToEat.setCals(27);
		mealToEat.setFat(50);
		mealToEat.setSodium(20);
		mealToEat.setCarbs(10);
		mealToEat.setFiber(10);
		mealToEat.setSugar(2);
		mealToEat.setProtein(-2);
		
		try {
			action.addFoodDiary(mealToEat);
			fail("exception should have been thrown");
		} catch (FormValidationException e) {
			assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Grams Protein Per Servings: Must be 0 or Greater]", e.getMessage());
		}
		List<FoodDiaryBean> results = action.getFoodDiary();
		assertEquals(2, results.size()); // Entry number stays the same
	}
	
	
	
}
