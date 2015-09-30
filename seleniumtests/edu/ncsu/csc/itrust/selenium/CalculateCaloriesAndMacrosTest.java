package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class CalculateCaloriesAndMacrosTest extends iTrustSeleniumTest {


	/**
	 * setUp
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * testCalculateCaloriesAndMacrosWithDefualtWeightAndHeight () 
	 * Patient Baby Programmer(MID: 5)logs into iTrust.
	 * Patient Baby Programmer(MID: 5) clicks View Food Diary
	 * In "Categorize Food Diary Entries", Patient Baby Programmer(MID: 5) enters 12/02/1993 as start date and 12/02/2014 as end date.
	 * Patient Jennifer Jareau clicks the "Calculate My Recommanded Calories" button
	 */
	public void testCalculateCaloriesAndMacrosWithDefualtWeightAndHeight () throws Exception {
		// log in as patient Baby Programmer 
		HtmlUnitDriver driver = (HtmlUnitDriver) login("5", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 5L, 0L, "");
		
		driver.findElement(By.linkText("View Food Diary")).click();
		
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/categorizeFoodDiary.jsp", driver.getCurrentUrl());
		
		WebElement startDate_box = driver.findElement(By.xpath("//*[@id=\"rangedate\"]/table/tbody/tr/td[1]/input"));
		startDate_box.sendKeys("12/02/1993");
		
		WebElement dueDate_box = driver.findElement(By.xpath("//*[@id=\"rangedate\"]/table/tbody/tr/td[2]/input"));
		dueDate_box.sendKeys("12/02/2013");
		
		// No need to test "exception", try "No data" this time instead
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"viewall\"]/table/tbody/tr/td/input"));
		submit_button.submit();
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/viewFoodDiaryPat.jsp", driver.getCurrentUrl());
		
		// click on "Calculate My Recommanded Calories"
		driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/input")).click();
		
		// No data found on my record should show up 
		WebElement DailyGraph = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[2]/tbody/tr/td[3]"));
		assertEquals("No data found on my record", DailyGraph.getText());
		
		// Since Id has been assigned for each value in table, directly call By.id
		assertEquals("1944.8 grams", driver.findElement(By.id("BMRValue")).getText());
		assertEquals("1166.9 grams", driver.findElement(By.id("CarbValue")).getText());
		assertEquals("194.5 grams", driver.findElement(By.id("SuagrValue")).getText());
		assertEquals("437.6 grams", driver.findElement(By.id("ProteinValue")).getText());
		assertEquals("145.9 grams", driver.findElement(By.id("FatValue")).getText());
		assertEquals("2,300 milligrams", driver.findElement(By.id("SodiumValue")).getText());
		assertEquals("28 to 34 grams", driver.findElement(By.id("FiberValue")).getText());
		
	}
	
	/**
	 * testCalculateCaloriesAndMacrosWithNoDefualtWeightAndHeight()
	 * Patient Fozzie Bear(MID: 22)logs into iTrust.
	 * Patient Fozzie Bear(MID: 22) clicks View Food Diary
	 * In "Categorize Food Diary Entries", Patient Fozzie Bear(MID: 22) enters 12/02/1993 as start date and 12/02/2014 as end date.
	 * Patient Fozzie Bear(MID: 22)clicks the "Calculate My Recommanded Calories" button
	 * Patient Fozzie Bear(MID: 22) types in 70.0 as most recent height
	 * Patient Fozzie Bear(MID: 22) types in 190.0 as most recent weight
	 * Patient Fozzie Bear(MID: 22)clicks the "Calculate My Recommanded Calories" button
	 * Patient Fozzie Bear(MID: 22) types in 71.0 as most recent height
	 * Patient Fozzie Bear(MID: 22) types in 191.0 as most recent weight
	 * Patient Fozzie Bear(MID: 22)clicks the "Calculate My Recommanded Calories" button
	 * 
	 *  @throws Exception
	 */	
	public void testCalculateCaloriesAndMacrosWithNoDefualtWeightAndHeight() throws Exception {
		// log in as patient Baby Programmer 
		HtmlUnitDriver driver = (HtmlUnitDriver) login("22", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 22L, 0L, "");
		
		driver.findElement(By.linkText("View Food Diary")).click();
		
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/categorizeFoodDiary.jsp", driver.getCurrentUrl());
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"viewall\"]/table/tbody/tr/td/input"));
		submit_button.submit();
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/viewFoodDiaryPat.jsp", driver.getCurrentUrl());
			
		WebElement height_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[6]/input"));
		
		driver.setJavascriptEnabled(true);
		driver.executeScript("arguments[0].setAttribute('value', '" + "70.0" +"')", height_box);
		
		WebElement weight_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[8]/input"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "190.0" +"')", weight_box);
		
		// click on "Calculate My Recommanded Calories"
		submit_button = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/input"));
		submit_button.click();
		
		WebElement DailyGraph = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[2]/tbody/tr/td[3]"));
		assertEquals("No data found on my record", DailyGraph.getText());
		
		
		// Since Id has been assigned for each value in table, directly call By.id
		assertEquals("1873.5 grams", driver.findElement(By.id("BMRValue")).getText());
		assertEquals("1124.1 grams", driver.findElement(By.id("CarbValue")).getText());
		assertEquals("187.4 grams", driver.findElement(By.id("SuagrValue")).getText());
		assertEquals("421.5 grams", driver.findElement(By.id("ProteinValue")).getText());
		assertEquals("140.5 grams", driver.findElement(By.id("FatValue")).getText());
		assertEquals("2,300 milligrams", driver.findElement(By.id("SodiumValue")).getText());
		assertEquals("28 to 34 grams", driver.findElement(By.id("FiberValue")).getText());
		
		// refresh elements 
		height_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[6]/input"));
		weight_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[8]/input"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "71.0" +"')", height_box);
		driver.executeScript("arguments[0].setAttribute('value', '" + "191.0" +"')", weight_box);
		
		// click on "Calculate My Recommanded Calories"
		submit_button = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/input"));
		submit_button.click();
		
		DailyGraph = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[2]/tbody/tr/td[3]"));
		assertEquals("No data found on my record", DailyGraph.getText());
		
		// Since Id has been assigned for each value in table, directly call By.id
		assertEquals("1892.4 grams", driver.findElement(By.id("BMRValue")).getText());
		assertEquals("1135.4 grams", driver.findElement(By.id("CarbValue")).getText());
		assertEquals("189.2 grams", driver.findElement(By.id("SuagrValue")).getText());
		assertEquals("425.8 grams", driver.findElement(By.id("ProteinValue")).getText());
		assertEquals("141.9 grams", driver.findElement(By.id("FatValue")).getText());
		assertEquals("2,300 milligrams", driver.findElement(By.id("SodiumValue")).getText());
		assertEquals("28 to 34 grams", driver.findElement(By.id("FiberValue")).getText());	
	}
	
	/**
	 * Patient Fozzie Bear(MID: 22)logs into iTrust.
	 * Patient Fozzie Bear(MID: 22) clicks View Food Diary
	 * In "Categorize Food Diary Entries", Patient Fozzie Bear(MID: 22) enters 12/02/1993 as start date and 12/02/2014 as end date.
	 * Patient Fozzie Bear(MID: 22) types in -70.0 as most recent height
	 * Patient Fozzie Bear(MID: 22) types in 190.0 as most recent weight
	 * Patient Fozzie Bear(MID: 22)clicks the "Calculate My Recommanded Calories" button
	 * Patient Fozzie Bear(MID: 22) types in 70.0123 as most recent height
	 * Patient Fozzie Bear(MID: 22) types in 190.0 as most recent weight
	 * Patient Fozzie Bear(MID: 22)clicks the "Calculate My Recommanded Calories" button
	 *	
	 * @throws Exception
	 */
	public void testCalculateCaloriesAndMacrosWithInvalidInput() throws Exception {
		// log in as patient Baby Programmer 
		HtmlUnitDriver driver = (HtmlUnitDriver) login("22", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 22L, 0L, "");
		
		driver.findElement(By.linkText("View Food Diary")).click();
		
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/categorizeFoodDiary.jsp", driver.getCurrentUrl());
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"viewall\"]/table/tbody/tr/td/input"));
		submit_button.submit();
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/viewFoodDiaryPat.jsp", driver.getCurrentUrl());
			
		WebElement height_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[6]/input"));
		
		driver.setJavascriptEnabled(true);
		driver.executeScript("arguments[0].setAttribute('value', '" + "-70.0" +"')", height_box);
		
		WebElement weight_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[8]/input"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "190.0" +"')", weight_box);
		
		// click on "Calculate My Recommanded Calories"
		submit_button = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/input"));
		submit_button.click();
		
		WebElement message = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/div/span"));
		assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Height: Up to 3-digit number + up to 1 decimal place]", message.getText());
		
		
		height_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[6]/input"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "70.0123" +"')", height_box);
		
		weight_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[8]/input"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "190.0" +"')", weight_box);
		
		// click on "Calculate My Recommanded Calories"
		submit_button = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/input"));
		submit_button.click();
		
		message = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/div/span"));
		assertEquals("This form has not been validated correctly. The following field are not properly filled in: [Height: Up to 3-digit number + up to 1 decimal place]", message.getText());	
	}
	
	/**
	 * testCompareCaloriesAndMacrosWithSeveralFoodEntry()
	 * Patient Aaron Hotchner(MID: 686) logs into iTrust.
	 * Patient Aaron Hotchner(MID: 686) clicks View Food Diary
	 * In "Categorize Food Diary Entries", Patient Aaron Hotchner(MID: 686) enters 12/02/1993 as start date and 12/02/2014 as end date.
	 * Patient Aaron Hotchner(MID: 686) types in 70.0 as most recent height
	 * Patient Aaron Hotchner(MID: 686) types in 190.0 as most recent weight
	 * Patient Aaron Hotchner(MID: 686) Select 04/13/2014 from the dropdown box 
	 * Patient Aaron Hotchner(MID: 686) clicks the "Calculate My Recommanded Calories" button
	 * Patient Aaron Hotchner(MID: 686) Select 05/21/2014 from the dropdown box 
	 * Patient Aaron Hotchner(MID: 686) clicks the "Calculate My Recommanded Calories" button
	 * @throws Exception
	 */
	public void testCompareCaloriesAndMacrosWithSeveralFoodEntry() throws Exception {
		// log in as patient Baby Programmer 
		HtmlUnitDriver driver = (HtmlUnitDriver) login("686", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 686L, 0L, "");
		
		driver.findElement(By.linkText("View Food Diary")).click();
		
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/categorizeFoodDiary.jsp", driver.getCurrentUrl());
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"viewall\"]/table/tbody/tr/td/input"));
		submit_button.submit();
		// assert current URL
		assertEquals(ADDRESS + "auth/patient/viewFoodDiaryPat.jsp", driver.getCurrentUrl());
			
		WebElement height_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[6]/input"));
		
		driver.setJavascriptEnabled(true);
		driver.executeScript("arguments[0].setAttribute('value', '" + "70.0" +"')", height_box);
		
		WebElement weight_box = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[8]/input"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "190.0" +"')", weight_box);
		
		// dropdown box session
		Select compareTo = new Select
				(driver.findElementByXPath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[11]/select"));
		compareTo.selectByVisibleText("04/13/2014");
		
		// click on "Calculate My Recommanded Calories"
		submit_button = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/input"));
		submit_button.click();
		
		compareTo = new Select
				(driver.findElementByXPath("//*[@id=\"weightAndHeightForm\"]/div/table[1]/tbody/tr/td[11]/select"));
		compareTo.selectByVisibleText("05/21/2013");
		submit_button = driver.findElement(By.xpath("//*[@id=\"weightAndHeightForm\"]/div/input"));
		submit_button.click();

	}
}
