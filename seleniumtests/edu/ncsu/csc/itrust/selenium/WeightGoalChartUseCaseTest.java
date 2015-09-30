package edu.ncsu.csc.itrust.selenium;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class WeightGoalChartUseCaseTest extends iTrustSeleniumTest {

	/**
	 * setUp
	 */
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	/**
	 * HCP Spencer Reid logs into iTrust
	 * view body measurement for Random Person
	 * View Chart under weight
	 * message "No Past Body Measurement Found"
	 * @throws Exception
	 */
	public void testViewEmptyWeightGoalChart () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9900000025", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000025L, 0L, "");
										
		//Click View Body Measurements
		driver.findElement(By.xpath("//a[text()='View Body Measurements']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();

		assertEquals("Random Person's Body Measurements", driver.findElement(By.xpath("//div/table/tbody/tr/th")).getText());
		assertEquals("Weight (lbs.)\n(View Chart)", driver.findElement(By.xpath("//div/table/tbody/tr[2]/td[2]")).getText());

		//Click view chart under weight
		driver.findElement(By.xpath("//div/table/tbody/tr[2]/td[2]/a")).click();
		assertTrue(driver.findElement(By.cssSelector("BODY")).getText().contains("No Past Body Measurements Found"));
	}

	/**
	 * HCP Spencer Reid logs into iTrust
	 * view body measurement for Spencer Reid
	 * View Chart under weight
	 * graph displayed
	 * @throws Exception
	 */
	public void testViewWeightGoalChart () throws Exception {
		// login HCP
		HtmlUnitDriver driver = (HtmlUnitDriver) login("9900000025", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000025L, 0L, "");
												
		//Click View Body Measurements
		driver.findElement(By.xpath("//a[text()='View Body Measurements']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("685");
		driver.findElement(By.xpath("//input[@value='685']")).submit();

		assertEquals("Jennifer Jareau's Body Measurements", driver.findElement(By.xpath("//div/table/tbody/tr/th")).getText());
		assertEquals("Weight (lbs.)\n(View Chart)", driver.findElement(By.xpath("//div/table/tbody/tr[2]/td[2]")).getText());

		//Click view chart under weight
		driver.findElement(By.xpath("//div/table/tbody/tr[2]/td[2]/a")).click();
		assertFalse(driver.findElement(By.cssSelector("BODY")).getText().contains("No Past Body Measurements Found"));
		assertEquals("800", driver.findElement(By.xpath("//div/img")).getAttribute("width"));
	}

	/**
	 * HCP Kelly Doctor logs into iTrust
	 * view body measurement
	 * non-nutritionist redirected to homepage
	 * @throws Exception
	 */
	public void testNonNutritionistViewWeightGoalChart () throws Exception {
		// login HCP
		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		//Click View Body Measurements
		driver.findElement(By.xpath("//a[text()='View Body Measurements']")).click();
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("685");
		driver.findElement(By.xpath("//input[@value='685']")).submit();
		
		//redirect to homepage
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

	}
}
