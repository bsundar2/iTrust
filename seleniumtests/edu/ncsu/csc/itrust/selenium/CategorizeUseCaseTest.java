package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * 
 * @author Team 19
 * We test the filter ooption of the food diary and make sure that 
 * the correct filters get applied so only the correct
 * results are shown
 *
 */
public class CategorizeUseCaseTest extends iTrustSeleniumTest {
	
	/**
	 * setUp
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * testViewEmptyCategorization
	 * There are two existing entries both exist on the same day
	 * we will text what happens if the date is outside the range so no results show
	 * up on the food diary page
	 */
	public void testViewEmptyCategorization () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("685", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 685L, 0L, "");
				

		//Click View Food Diary
		driver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
	    driver.findElement(By.name("singleDate")).clear();
	    driver.findElement(By.name("singleDate")).sendKeys("02/02/2000");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();    
		    
		//make sure we effectively filtered out the date
	    assertTrue(!driver.findElement(By.cssSelector("BODY")).getText().contains("09/30/2012"));		

	}
	
	/**
	 * testSingularDate
	 * There are two existing entries both exist on the same day
	 * we will text what happens if the date is in the range so no results show
	 * up on the food diary page
	 */
	public void testSingularDate () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("685", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 685L, 0L, "");
				

		//Click View Food Diary
		driver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
	    driver.findElement(By.name("singleDate")).clear();
	    driver.findElement(By.name("singleDate")).sendKeys("09/30/2012");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();    
		    
		//make sure we effectively filtered out the date
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("09/30/2012"));		
	}
	
	/**
	 * testRangeDate
	 * There are two existing entries both exist on the same day
	 * we will text what happens if we set a range that include the date
	 * we want to make sure it is included in the results
	 */
	public void testRangeDate () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("685", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 685L, 0L, "");
				

		//Click View Food Diary
		driver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
	    driver.findElement(By.name("rangeDateStart")).clear();
	    driver.findElement(By.name("rangeDateStart")).sendKeys("01/01/2005");
	    driver.findElement(By.name("rangeDateEnd")).clear();
	    driver.findElement(By.name("rangeDateEnd")).sendKeys("02/02/2014");
	    driver.findElement(By.xpath("(//input[@value='Filter'])[2]")).click();
		    
		//make sure we effectively filtered out the date
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("09/30/2012"));		
	}
	
	/**
	 * testRangeDate
	 * There are two existing entries both exist on the same day
	 * we will text what happens if we the date to be an invalid value
	 */
	public void testDateValidity () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("685", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 685L, 0L, "");
				

		//Click View Food Diary
		//Click View Food Diary
		driver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
	    driver.findElement(By.name("singleDate")).clear();
	    driver.findElement(By.name("singleDate")).sendKeys("this is not a date");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click(); 
		    
		//make sure we effectively filtered out the date
	    assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Date: [Incorrect Fomat must be (MM/DD/YYYY) and Non-Future Time]"));		
	}
}


