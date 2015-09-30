package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ViewMyRecordsTest extends iTrustSeleniumTest {
	
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient4();
		gen.hcp0();
		gen.clearLoginFailures();
		gen.hcp3();
	}
	
	/*
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View My Records
	 */
	/**
	 * testViewRecords3
	 * @throws Exception
	 */
	public void testViewRecords3() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
	    //assertTrue(pageContains("210.0lbs"));
	    //assertTrue(pageContains("500 mg/dL"));
	    driver.findElement(By.linkText("Jun 10, 2007")).click();
	    //assertTrue(pageContains("Diabetes with ketoacidosis"));
		//assertTrue(pageContains("Prioglitazone"));
		//assertTrue(pageContains("Tetracycline"));
		//assertTrue(pageContains("Notes:"));
	    assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
	}
	
	/*
	 * Authenticate Patient
	 * MID: 4
	 * Password: pw
	 * Choose option View My Records
	 */
	/**
	 * testViewRecords4
	 * @throws Exception
	 */
	public void testViewRecords4() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("4", "pw");
		assertLogged(TransactionType.HOME_VIEW, 4L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		//assertFalse(pageContains("Exception"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 4L, 4L, "");
	}
	
	/*
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View My Records
	 * Choose to view records for mid 1, the person he represents.
	 */
	/**
	 * testViewRecords5
	 * @throws Exception
	 */
	public void testViewRecords5() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("View My Records")).click();
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 2L, "");
		
		//assertTrue(pageContains("Random Person"));
		driver.findElement(By.linkText("Random Person")).click();
		
		// check to make sure you are viewing patient 1's records
		//assertTrue(pageContains("You are currently viewing your representee's records"));
		assertLogged(TransactionType.MEDICAL_RECORD_VIEW, 2L, 1L, "");
	}
}
