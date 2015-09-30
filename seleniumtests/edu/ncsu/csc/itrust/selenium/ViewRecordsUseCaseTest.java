package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 9
 */
public class ViewRecordsUseCaseTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	public void testViewMyRecords() throws Exception {
		// Login
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		// Records page contains patient information
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div"))
				.click();
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[7]/div"))
				.click();
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[7]/div/h2")).click();
		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
		driver.findElement(By.linkText("View My Records")).click();
	}

	public void testRepresent() throws Exception {
		// Login
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		// Clicking on a representee's name takes you to their records
	    driver.findElement(By.linkText("Baby Programmer")).click();
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 5L, "");
	}
	
	public void testDoctor() throws Exception {
		// Login
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");	
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	    driver.findElement(By.linkText("Kelly Doctor")).click();
	}
	
	/*
	 * Precondition:
	 * Patient 2 and all his data have been loaded into iTrust
	 * Patient 2 has successfully authenticated
	 * Description:
	 * 1. Patient 2 chooses to view his records
	 * 2. Chooses link to office visit "6/10/2007"
	 * Expected Result:
	 * The following data should be displayed: Office Visit Details Date: 06/10/2007
	 *   HCP: Kelly Doctor (9000000000)
	 *   Diagnoses
	 *   ICD Code	Description
	 *   No Diagnoses for this visit
	 *   Medications
	 *   No Medications on record
	 *   Procedures
	 *   No Procedures on record
	 */
	public void testViewPatientOfficeVisit() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	    driver.findElement(By.linkText("Jun 10, 2007")).click();
	}
}
