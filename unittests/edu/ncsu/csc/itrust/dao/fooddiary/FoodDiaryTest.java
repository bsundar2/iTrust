package edu.ncsu.csc.itrust.dao.fooddiary;

import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;
import junit.framework.TestCase;

/**
 * Tests the food diary DAO class to check and see if inputs are being handled
 * correctly and output is correct as well.
 * 
 * This tests the DAO commands without the indirect calls from the FoodDiarAction file.
 * 
 * @author Evin Clapper
 * @author Jay Patel
 *
 */
public class FoodDiaryTest extends TestCase {
	DAOFactory factory = TestDAOFactory.getTestInstance();
	private FoodDiaryDAO fdDAO = factory.getFoodDiaryDAO();
	
	/**
	 * Sets up some quick patient food diary data for testing
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
	 * Logged in as Derek Morgan who has no initial food diary
	 * entries we will see what he results return
	 * MID 684
	 */
	public void testNoEntries() throws Exception {
		long loggedInPatientMID = 684;
		List<FoodDiaryBean> results = fdDAO.getFoodDiaryByMID(loggedInPatientMID);
		assertTrue(results.isEmpty());
	}
	
	/**
	 * Logged in as Jennifer Jareau who already has some
	 * entries in her food diary it should return 2 of them
	 * MID 685
	 */
	public void testExistingEntries() throws Exception {
		long loggedInPatientMID = 685;
		List<FoodDiaryBean> results = fdDAO.getFoodDiaryByMID(loggedInPatientMID);
		assertEquals(2, results.size());
	}
	
	/**
	 * Arron Hotchner already has two existing food diaries
	 * we are going to add one more and see if it gets added to the database 
	 * and if the result set updates
	 * MID 686
	 */
	public void testAddFoodDiaries() throws Exception {
		long loggedInPatientMID = 686;
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(loggedInPatientMID);
		mealToEat.setEntryDate("11/12/2015");
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
		
		boolean addTest = fdDAO.addFoodDiary(mealToEat);
		assertTrue(addTest); //If correctly added
		//Now check for a total of three
		List<FoodDiaryBean> results = fdDAO.getFoodDiaryByMID(loggedInPatientMID);
		assertEquals(3, results.size()); //He initially has two
	}
	
	public void testDelFoodDiaries() throws Exception {
		long loggedInPatientMID = 686;
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		/* Creating a test meal to add */
		mealToEat.setPatientID(loggedInPatientMID);
		mealToEat.setEntryDate("11/12/2015");
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
		
		boolean addTest = fdDAO.addFoodDiary(mealToEat);
		assertTrue(addTest); //If correctly added
		//Now check for a total of three
		List<FoodDiaryBean> results = fdDAO.getFoodDiaryByMID(loggedInPatientMID);
		assertEquals(3, results.size()); //He initially has two
		
		boolean delTest = fdDAO.deleteFoodDiaryEntry(mealToEat);
		assertTrue(delTest); //If correctly deleted
		results = fdDAO.getFoodDiaryByMID(loggedInPatientMID);
		assertEquals(2, results.size()); //He initially has three
	}
	



}
