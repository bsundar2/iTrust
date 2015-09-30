package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class SuggestionUseCaseTest extends iTrustSeleniumTest {


	/**
	 * setUp
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * testViewEmptySuggestion
	 * HCP Spencer Reid logs into iTrust
	 * view food diary of Jennifer Jareau
	 * @throws Exception
	 */
	public void testViewEmptySuggestion () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9900000025", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000025L, 0L, "");
				

		//Click View Food Diary
		driver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("685");
		driver.findElement(By.xpath("//input[@value='685']")).submit();
		driver.findElement(By.cssSelector("#viewall > table > tbody > tr > td > input[type=\"submit\"]")).click();
		    
		    
		//empty text box present next to daily total
		assertEquals("Patient Daily Totals", driver.findElement(By.xpath("//table[2]/tbody/tr/th")).getText());
		assertEquals("", driver.findElement(By.name("suggestion")).getValue());		

	}
	
	/**
	 * HCP Spencer Reid logs into iTrust
	 * view food diary and add suggestion
	 * @throws Exception
	 */
	public void testWriteSuggestion () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9900000025", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000025L, 0L, "");
						
		//Click View Food Diary
		driver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("685");
		driver.findElement(By.xpath("//input[@value='685']")).submit();
		
		driver.findElement(By.cssSelector("#viewall > table > tbody > tr > td > input[type=\"submit\"]")).click();
		
		//empty text box present next to daily total
		assertEquals("Patient Daily Totals", driver.findElement(By.xpath("//table[2]/tbody/tr/th")).getText());
		assertEquals("", driver.findElement(By.name("suggestion")).getValue());		
		
		//write suggestion "Good Job"
		driver.findElement(By.name("suggestion")).sendKeys("Good Job");
		driver.findElement(By.name("suggestion")).submit();
		
		//suggestion saved
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Suggestion Successfully Added"));
		assertEquals("Good Job", driver.findElement(By.name("suggestion")).getValue());		
		
	}
	
	/**
	 * HCP Spencer Reid added suggestion for Jennifer Jareau
	 * Jennifer Jareau logs in and check suggestion
	 * @throws Exception
	 */
	public void testViewSuggestion () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9900000025", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000025L, 0L, "");
						
		//Click View Food Diary
		driver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("685");
		driver.findElement(By.xpath("//input[@value='685']")).submit();
		
		driver.findElement(By.cssSelector("#viewall > table > tbody > tr > td > input[type=\"submit\"]")).click();
		
		//empty text box present next to daily total
		assertEquals("Patient Daily Totals", driver.findElement(By.xpath("//table[2]/tbody/tr/th")).getText());
		assertEquals("", driver.findElement(By.name("suggestion")).getValue());		
		
		//write suggestion "Good Job"
		driver.findElement(By.name("suggestion")).sendKeys("Good Job");
		driver.findElement(By.name("suggestion")).submit();
		
		//suggestion saved
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("Suggestion Successfully Added"));
		assertEquals("Good Job", driver.findElement(By.name("suggestion")).getValue());		

		//logout
		driver.findElement(By.xpath("//a[text()='Logout']")).click();	
		assertLogged(TransactionType.LOGOUT, 9900000025L, 9900000025L, "");
		
		//patient login
		HtmlUnitDriver pdriver = (HtmlUnitDriver) login("685", "pw");
		assertEquals("iTrust - Patient Home", pdriver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 685L, 0L, "");
		
		//Click View Food Diary
		pdriver.findElement(By.xpath("//a[text()='View Food Diary']")).click();
		pdriver.findElement(By.cssSelector("#viewall > table > tbody > tr > td > input[type=\"submit\"]")).click();
		
		//suggestion next to total for 9/30/2012
		assertTrue(pdriver.findElement(By.cssSelector("BODY")).getText().contains("Good Job"));	

	}
}
