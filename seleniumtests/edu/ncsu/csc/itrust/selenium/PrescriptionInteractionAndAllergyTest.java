package edu.ncsu.csc.itrust.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PrescriptionInteractionAndAllergyTest extends iTrustSeleniumTest {
	
	protected void setUp() throws Exception{
		//super.setUp();
		gen.clearAllTables();
		gen.hcp0();
	}

	public static final String ADDRESS = "http://localhost:8080/iTrust/";

	/*
	 * No Allergy Prescribe
	 */
	public void testNoAllergyPrescribe() throws Exception {
		gen.standardData();
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		// Navigate to iTrust
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Select Document Office Visit from the menu
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Find patient 25
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25']")).submit();

		// Check if on the right page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());

		// Proceed
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Add office visit for 02/01/2012
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("02/01/2012");
		driver.findElement(By.id("update")).click();
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 25L, "");

		// Add a prescription for Nexium
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("01864020 - Nexium");
        driver.findElement(By.cssSelector("option[value=\"01864020\"]")).submit();
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("20");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("02/01/2012");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("08/01/2012");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys("Take once daily.");
		driver.findElement(By.id("addprescription")).submit();
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 25L, "");

		// Check if updated
		assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());

		// Close the driver
		driver.quit();
	}

	/*
	 * Allergic Prescribe
	 */
	public void testAllergicPrescribe() throws Exception {
		gen.standardData();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		// Navigate to iTrust
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());

		// Select PHR Information from the menu
		driver.findElement(By.linkText("PHR Information")).click();

		// Find patient 25
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("25");
		driver.findElement(By.xpath("//input[@value='25']")).submit();

		// Add an allergy to Penicillin
		driver.findElement(By.id("description")).clear();
		driver.findElement(By.id("description")).sendKeys("Penicillin");
		driver.findElement(By.name("addA")).click();

		// Select Document Office Visit from the menu
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Proceed
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Create a new office visit for 01/01/2012
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("01/01/2012");
		driver.findElement(By.id("update")).click();
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 25L, "Office visit");

		// Add a prescription for Penicillin
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("664662530 - Penicillin");
        driver.findElement(By.cssSelector("option[value=\"664662530\"]")).submit();
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("60");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("01/01/2012");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("01/31/2012");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys("Take three times daily with food.");
		driver.findElement(By.id("addprescription")).submit();

		// Check for allergy statement
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Penicillin."));

		// Close the driver
		driver.quit();
	}

	/*
	 * Prescribe Override Cancel
	 */
	public void testPrescribeOverrideCancel() throws Exception {
		gen.standardData();
		gen.ndCodes100();
		gen.patient100();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();

		// Navigate to iTrust
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Select Document Office Visit from menu
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Find patient 100
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("100");
		driver.findElement(By.xpath("//input[@value='100']")).submit();

		// Proceed
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Create a new office visit
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("02/01/2012");
		driver.findElement(By.id("update")).click();
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 100L, "Office visit");

		// Add a prescription for Lantus
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("00882219 - Lantus");
        driver.findElement(By.cssSelector("option[value=\"00882219\"]")).submit();
		driver.findElement(By.id("dosage")).clear();
		driver.findElement(By.id("dosage")).sendKeys("60");
		driver.findElement(By.id("startDate")).clear();
		driver.findElement(By.id("startDate")).sendKeys("01/01/2012");
		driver.findElement(By.id("endDate")).clear();
		driver.findElement(By.id("endDate")).sendKeys("01/31/2012");
		driver.findElement(By.id("instructions")).clear();
		driver.findElement(By.id("instructions")).sendKeys("Take three times daily with food.");
		driver.findElement(By.id("addprescription")).submit();
		
		// Check for allergy message
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Lantus. First Found: 02/03/2012"));

		// Cancel prescription add
		driver.findElement(By.id("cancel")).submit();
		assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 100L, "");

		// Close the driver
		driver.quit();
	}

	/*
	 * Allergic Prescribe Twice
	 */
	public void testAllergicPrescribeTwice() throws Exception {
		gen.standardData();
		gen.ndCodes100();
		gen.patient100();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		// Navigate to iTrust
		driver.get(ADDRESS);
		
		// Login
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
	    
	    // Select Document Office Visit from the menu
	    driver.findElement(By.linkText("Document Office Visit")).click();
	    
	    // Search for patient 100
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("100");
		driver.findElement(By.xpath("//input[@value='100']")).submit();
	    
	    // Proceed
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    // Add a new office visit for 02/01/2012
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("02/01/2012");
	    driver.findElement(By.id("update")).click();
	    
	    // Add a prescription for Lantus
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("00882219 - Lantus");
        driver.findElement(By.cssSelector("option[value=\"00882219\"]")).submit();
	    driver.findElement(By.id("dosage")).clear();
	    driver.findElement(By.id("dosage")).sendKeys("60");
	    driver.findElement(By.id("startDate")).clear();
	    driver.findElement(By.id("startDate")).sendKeys("01/01/2012");
	    driver.findElement(By.id("endDate")).clear();
	    driver.findElement(By.id("endDate")).sendKeys("01/31/2012");
	    driver.findElement(By.id("instructions")).clear();
	    driver.findElement(By.id("instructions")).sendKeys("Take three times daily with food.");
	    driver.findElement(By.id("addprescription")).submit();
	    
	    // Check for allergy message
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Lantus. First Found: 02/03/2012"));
	    
	    // Override
	    new Select(driver.findElement(By.id("overrideCode"))).selectByVisibleText("00001 - Alerted interaction not clincally significant");
	    driver.findElement(By.name("continue")).click();
		assertLogged(TransactionType.OVERRIDE_INTERACTION_WARNING, 9000000000L, 100L, "");
	    
	    // Add a prescription for Lantus
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("00882219 - Lantus");
        driver.findElement(By.cssSelector("option[value=\"00882219\"]")).submit();
	    driver.findElement(By.id("dosage")).clear();
	    driver.findElement(By.id("dosage")).sendKeys("60");
	    driver.findElement(By.id("startDate")).clear();
	    driver.findElement(By.id("startDate")).sendKeys("01/01/2012");
	    driver.findElement(By.id("endDate")).clear();
	    driver.findElement(By.id("endDate")).sendKeys("01/31/2012");
	    driver.findElement(By.id("instructions")).clear();
	    driver.findElement(By.id("instructions")).sendKeys("Take three times daily with food.");
	    driver.findElement(By.id("addprescription")).submit();
	    
	    // Check for allergy message
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Lantus. First Found: 02/03/2012"));

		// Close the driver
		driver.quit();
	}

	/*
	 * Interaction And Allergy Prescribe
	 */
	public void testInteractionAndAllergyPrescribe() throws Exception {
		gen.standardData();
		gen.patient2();
		gen.officeVisit4();
		gen.ndCodes1();
		gen.drugInteractions3();
		gen.ORCodes();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		// Navigate to iTrust
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Select patient 100
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("100");
		driver.findElement(By.xpath("//input[@value='100']")).submit();

		// Select an office visit
		driver.findElement(By.linkText("01/01/2012")).click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 100L, "Office visit");
		
		// Add a prescription
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("619580501 - Adefovir");
        driver.findElement(By.cssSelector("option[value=\"619580501\"]")).submit();
		driver.findElement(By.name("dosage")).clear();
		driver.findElement(By.name("dosage")).sendKeys("10");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("09/22/2012");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("10/22/2012");
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys("Take once a day");
		driver.findElement(By.id("addprescription")).submit();

		// Check if added
		assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 100L, "");

		 // Add a prescription
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("081096 - Aspirin");
        driver.findElement(By.cssSelector("option[value=\"081096\"]")).submit();
		driver.findElement(By.name("dosage")).clear();
		driver.findElement(By.name("dosage")).sendKeys("15");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("10/15/2012");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("10/31/2012");
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys("Take twice daily with water");
		driver.findElement(By.id("addprescription")).submit();
		
//Functionality missing
//		 // Check for allergy message
//		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Aspirin"));
//		assertEquals("Currently Prescribed: Adefovir", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//
//		 // Override
//		 new Select(driver.findElement(By.id("overrideCode"))).selectByVisibleText("00001 - Alerted interaction not clincally significant");
//		 driver.findElement(By.id("continue")).click();
//		 assertLogged(TransactionType.OVERRIDE_INTERACTION_WARNING, 9000000000L, 100L, "");
//		 
//		 // Check if added
//		 assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//		 assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 100L, "");

		// Close the driver
		driver.quit();
	}

	/*
	 * Allergic Prescribe Override
	 */
	public void testAllergicPrescribeOverride() throws Exception {
		gen.patient2();
		gen.officeVisit4();
		gen.ndCodes1();
		gen.drugInteractions3();
		gen.ORCodes();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();

		// Navigate to iTrust
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Select patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
		driver.findElement(By.xpath("//input[@value='2']")).submit();

		// Select the office visit for 09/15/2009
		driver.findElement(By.linkText("09/15/2009")).click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 2L, "Office visit");

		// Add a prescription
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("081096 - Aspirin");
        driver.findElement(By.cssSelector("option[value=\"081096\"]")).submit();
		driver.findElement(By.name("dosage")).sendKeys("15");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("09/22/2009");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("10/22/2009");
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys("Take twice daily with water");
		driver.findElement(By.id("addprescription")).submit();

		// Check for allergy message
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Aspirin"));

		// Override
		new Select(driver.findElement(By.id("overrideCode"))).selectByVisibleText("00006 - Limited course of treatment");
		driver.findElement(By.id("continue")).click();

		// Check if added
		assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");

		// Close the driver
		driver.quit();
	}

	/*
	 * Interaction Cancel
	 */
	public void testInteractionCancel() throws Exception {
		gen.patient1();
		gen.officeVisit3();
		gen.ndCodes1();
		gen.drugInteractions3();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();

		// Navigate to iTrust
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");

		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Select patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();

		// Select the office visit for 9/17/2009
		driver.findElement(By.linkText("09/17/2009")).click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 1L, "Office visit");

		// Add a prescription
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("619580501 - Adefovir");
        driver.findElement(By.cssSelector("option[value=\"619580501\"]")).submit();
		driver.findElement(By.name("dosage")).clear();
		driver.findElement(By.name("dosage")).sendKeys("10");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("09/22/2009");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("11/22/2009");
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys("Take once daily with meal");
		driver.findElement(By.id("addprescription")).submit();

		// Check for message
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Currently Prescribed: Aspirin."));

		// Cancel
		driver.findElement(By.id("cancel")).click();

		// Check current page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());

		// Check for Random Person
		//assertEquals("Random Person", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 1L, "");

		// Close the driver
		driver.quit();
	}

	/*
	 * Interaction Override
	 */
	public void testInteractionOverride() throws Exception {
		gen.patient1();
		gen.officeVisit3();
		gen.ndCodes1();
		gen.ndCodes4();
		gen.drugInteractions4();
		gen.ORCodes();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();

		// Navigate to iTrust
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Select patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
		driver.findElement(By.xpath("//input[@value='1']")).submit();

		// Select an office visit
		driver.findElement(By.linkText("09/17/2009")).click();
		assertLogged(TransactionType.OFFICE_VISIT_VIEW, 9000000000L, 1L, "Office visit");

		// Add a prescription
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("01864020 - Nexium");
        driver.findElement(By.cssSelector("option[value=\"01864020\"]")).submit();
		driver.findElement(By.name("dosage")).clear();
		driver.findElement(By.name("dosage")).sendKeys("10");
		driver.findElement(By.name("startDate")).clear();
		driver.findElement(By.name("startDate")).sendKeys("08/22/2009");
		driver.findElement(By.name("endDate")).clear();
		driver.findElement(By.name("endDate")).sendKeys("11/22/2009");
		driver.findElement(By.name("instructions")).clear();
		driver.findElement(By.name("instructions")).sendKeys("Take once daily with meal");
		driver.findElement(By.id("addprescription")).submit();

		// Check for message
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Currently Prescribed: Aspirin."));

		// Override
		new Select(driver.findElement(By.id("overrideCode"))).selectByVisibleText("00001 - Alerted interaction not clincally significant");
		driver.findElement(By.id("continue")).click();

		// Check for prescription
		assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		assertLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 1L, "");

		// Close the driver
		driver.quit();
	}

}
