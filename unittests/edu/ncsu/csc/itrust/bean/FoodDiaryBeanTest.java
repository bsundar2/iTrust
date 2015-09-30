package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean;

public class FoodDiaryBeanTest extends TestCase {
	
	public void testFoodDiaryBean() {
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		
		mealToEat.setPatientID(666);
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
		
		assertEquals(666, mealToEat.getPatientID());
		assertEquals("11/12/2015", mealToEat.getEntryDate());
		assertEquals("dinner", mealToEat.getMeal());
		assertEquals("Apple", mealToEat.getFood());
		assertEquals((double) 20, mealToEat.getServings());
		assertEquals(5.0, mealToEat.getCals());
		assertEquals(50.0, mealToEat.getFat());
		assertEquals(20.0, mealToEat.getSodium());
		assertEquals(10.0, mealToEat.getCarbs());
		assertEquals(10.0, mealToEat.getFiber());
		assertEquals(2.0, mealToEat.getSugar());
		assertEquals(2.0, mealToEat.getProtein());
	}
	
	public void testEquals(){
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		mealToEat.setPatientID(666);
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
		
		FoodDiaryBean mealToEatTwo = new FoodDiaryBean();
		mealToEatTwo.setPatientID(666);
		mealToEatTwo.setEntryDate("11/12/2015");
		mealToEatTwo.setMeal("dinner");
		mealToEatTwo.setFood("Apple");
		mealToEatTwo.setServings(20);
		mealToEatTwo.setCals(5);
		mealToEatTwo.setFat(50);
		mealToEatTwo.setSodium(20);
		mealToEatTwo.setCarbs(10);
		mealToEatTwo.setFiber(10);
		mealToEatTwo.setSugar(2);
		mealToEatTwo.setProtein(2);
		
		FoodDiaryBean mealToEatThree = new FoodDiaryBean();
		mealToEatThree.setPatientID(666);
		mealToEatThree.setEntryDate("11/12/2015");
		mealToEatThree.setMeal("dinner");
		mealToEatThree.setFood("Apple");
		mealToEatThree.setServings(20);
		mealToEatThree.setCals(5);
		mealToEatThree.setFat(50);
		mealToEatThree.setSodium(20);
		mealToEatThree.setCarbs(10);
		mealToEatThree.setFiber(10);
		mealToEatThree.setSugar(25);
		mealToEatThree.setProtein(2);
		
		assertTrue(mealToEat.equals(mealToEat));
		assertTrue(mealToEat.equals(mealToEatTwo));
		assertFalse(mealToEat.equals(mealToEatThree));
	}
	
	public void testHash(){
		FoodDiaryBean mealToEat = new FoodDiaryBean();
		mealToEat.setPatientID(666);
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
		
		int hc = mealToEat.hashCode();
		assertEquals(666, mealToEat.getPatientID());
		assertEquals("11/12/2015", mealToEat.getEntryDate());
		assertEquals("dinner", mealToEat.getMeal());
		assertEquals("Apple", mealToEat.getFood());
		assertEquals((double) 20, mealToEat.getServings());
		assertEquals(5.0, mealToEat.getCals());
		assertEquals(50.0, mealToEat.getFat());
		assertEquals(20.0, mealToEat.getSodium());
		assertEquals(10.0, mealToEat.getCarbs());
		assertEquals(10.0, mealToEat.getFiber());
		assertEquals(2.0, mealToEat.getSugar());
		assertEquals(2.0, mealToEat.getProtein());
	}

}
