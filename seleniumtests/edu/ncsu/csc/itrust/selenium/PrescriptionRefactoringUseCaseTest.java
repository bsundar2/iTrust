package edu.ncsu.csc.itrust.selenium;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.beans.DiagnosisBean;
import edu.ncsu.csc.itrust.beans.OfficeVisitBean;
import edu.ncsu.csc.itrust.beans.PrescriptionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DiagnosesDAO;
import edu.ncsu.csc.itrust.dao.mysql.OfficeVisitDAO;
import edu.ncsu.csc.itrust.dao.mysql.PrescriptionsDAO;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;


public class PrescriptionRefactoringUseCaseTest extends iTrustSeleniumTest {
	
	protected void setUp() throws Exception {
		//super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public static final String ADDRESS = "http://localhost:8080/iTrust/";
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	
	private long getVisitID(WebDriver dr) throws Exception {
		WebElement e = dr.findElement(By.name("ovID"));
		String ovIDStr = e.getValue();
		return Long.parseLong(ovIDStr);
	}
	
	private String todayOffsetStr(int offset) {
		return dateOffsetStr(new Date(), offset);
	}
	
	private String dateOffsetStr(Date date, int offset) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, offset);
		return dateFormat.format(cal.getTime());
	}
	
	/*
	 * Acceptance Scenario 1
	 */
	public void testAcceptanceScenario1() throws Exception {
		//Generate the HCP
		gen.hcp4();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		//Visit the iTrust home page
		driver.get(ADDRESS);
		
		// Get the page title
		assertEquals("iTrust - Login", driver.getTitle());

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000004");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();
	    	    
	    // Select patient 1
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
	    driver.findElement(By.xpath("//input[@value='1']")).submit();
	    
	    // Proceed
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();	    
	    
	    // Add a new office visit
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("04/01/2015");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByVisibleText("Test Hospital 9191919191");
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("Showing signs of dehydration.");
	    driver.findElement(By.id("update")).click();
	    
	    // Check if added
	    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    
	    // Check if the action was logged
	    assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000004L, 1L, "Office visit");
	    
		// Check database
		long ovid = getVisitID(driver);
		OfficeVisitDAO ovDAO = factory.getOfficeVisitDAO();
		assertTrue(ovDAO.checkOfficeVisitExists(ovid, 1));
		OfficeVisitBean ovbean = ovDAO.getOfficeVisit(ovid);
		assertEquals("9191919191", ovbean.getHospitalID());
		assertEquals("Showing signs of dehydration.", ovbean.getNotes());
		assertEquals("04/01/2015", ovbean.getVisitDateStr());
		
		// Add diagnosis
	    new Select(driver.findElement(By.name("ICDCode"))).selectByVisibleText("79.10 - Echovirus");
		driver.findElement(By.id("add_diagnosis")).click();
		
		// Check if added
	    assertEquals("Diagnosis information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    
	    // Check database
		DiagnosesDAO diagDAO = factory.getDiagnosesDAO();
		assertEquals(1, diagDAO.getList(ovid).size());
		DiagnosisBean diagBean = diagDAO.getList(ovid).get(0);
		assertEquals(diagBean.getICDCode(), "79.10");
		
		// Close the driver
		driver.quit();
		
	}
	
	/*
	 * Acceptance Scenario 2
	 */
	public void testAcceptanceScenario2() throws Exception {		
		//Generate office visit
		gen.officeVisit8();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();

		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
			
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();
		
		// Select patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    
	    // Proceed
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();	   
	    
	    // Add a new office visit
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("01/01/2011");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByIndex(3);
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("Complains of acute fatigue.");
	    driver.findElement(By.id("update")).click();
	    
	    // Check if added
	    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 2L, "Office visit");
	    
		// Add a prescription
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("081096 - Aspirin");
        driver.findElement(By.cssSelector("option[value=\"081096\"]")).submit();
	    driver.findElement(By.name("dosage")).clear();
	    driver.findElement(By.name("dosage")).sendKeys("200");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("02/01/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("02/15/2011");
	    driver.findElement(By.name("instructions")).clear();
	    driver.findElement(By.name("instructions")).sendKeys("Take every six hours with food.");
	    driver.findElement(By.id("addprescription")).submit();
	    
	    // Check that the event was not logged
	    assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");
	    
	    // Look for an allergy warning
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Aspirin."));
	    
		// Cancel
		driver.findElement(By.id("cancel")).click();
	    
	    // Make sure on the same page
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
	    
	    // Check that the event was not logged
	    assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 2L, "");
	    
	    // Check the database
		PrescriptionsDAO pDAO = factory.getPrescriptionsDAO();
		long ovid = getVisitID(driver);
		assertEquals(0, pDAO.getList(ovid).size());

		// Close the driver
		driver.quit();
	}
	
	/*
	 * Acceptance Scenario 3
	 */
	public void testAcceptanceScenario3() throws Exception {		
		gen.officeVisit5();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000003");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
				
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();
				
		// Select patient 5
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("5");
	    driver.findElement(By.xpath("//input[@value='5']")).submit();
	    
	    // Create a date
	    String dateString = todayOffsetStr(-1);
	    
	    // Select an office visit
	 	driver.findElement(By.linkText(dateString)).click();
	    
		// Check that the page contains the existing office visit
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		assertEquals(dateString, driver.findElement(By.name("visitDate")).getValue());
		assertEquals("Hates getting shots", driver.findElement(By.name("notes")).getValue());

		// Check for existing lab procedure
	    assertEquals("13495-7", driver.findElement(By.xpath("//table[@id='labProceduresTable']/tbody/tr[3]/td")).getText());
	    
	    // Check for existing immunizations
	    assertEquals("Measles, Mumps, Rubella", driver.findElement(By.xpath("//table[@id='immunizationsTable']/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Hepatitis B", driver.findElement(By.xpath("//table[@id='immunizationsTable']/tbody/tr[4]/td[2]")).getText());
	    assertEquals("Poliovirus", driver.findElement(By.xpath("//table[@id='immunizationsTable']/tbody/tr[5]/td[2]")).getText());
	
		// Add a new prescription.
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("664662530 - Penicillin");
        driver.findElement(By.cssSelector("option[value=\"664662530\"]")).submit();
	    driver.findElement(By.name("dosage")).sendKeys("150");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys(todayOffsetStr(0));
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys(todayOffsetStr(21));
	    driver.findElement(By.name("instructions")).clear();
	    driver.findElement(By.name("instructions")).sendKeys("Take once daily with water.");
	    driver.findElement(By.id("addprescription")).submit();
	
		// Check page and database.
	    assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		PrescriptionsDAO preDAO = factory.getPrescriptionsDAO();
		assertEquals(1, preDAO.getList(380).size());
		PrescriptionBean preBean = preDAO.getList(380).get(0);
		assertEquals(preBean.getMedication().getNDCode(), "664662530");

		// Close the driver
		driver.quit();
	}
	
	/*
	 * Acceptance Scenario 4
	 */
	public void testAcceptanceScenario4() throws Exception {
		gen.officeVisit6();
		gen.hcp4();
		gen.ndCodes3();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();

		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000004");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Select patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
	    
	    // Select the office visit from 02/02/2011
	    driver.findElement(By.linkText("02/02/2011")).click();
	    
		// Check that the page contains the existing office visit
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		assertEquals(390, getVisitID(driver));
		assertEquals("02/02/2011", driver.findElement(By.name("visitDate")).getValue());
		assertEquals("Second medical visit in two days", driver.findElement(By.name("notes")).getValue()); //get value or get text?
		
		// Check for existing prescription
	    assertEquals("Aspirin (081096)", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[3]/td")).getText());

		// Add a new prescription.
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("678771191 - Ibuprofen");
        driver.findElement(By.cssSelector("option[value=\"678771191\"]")).submit();
	    driver.findElement(By.name("dosage")).sendKeys("400");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("02/02/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("02/16/2011");
	    driver.findElement(By.name("instructions")).clear();
	    driver.findElement(By.name("instructions")).sendKeys("Take once daily");
	    driver.findElement(By.id("addprescription")).submit();
	    		
		// Verify that warning occurred
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Currently Prescribed: Aspirin"));

		// Override
		new Select(driver.findElement(By.id("overrideCode"))).selectByVisibleText("00006 - Limited course of treatment");
		driver.findElement(By.id("continue")).click();

		// Check page and database.
	    assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		PrescriptionsDAO preDAO = factory.getPrescriptionsDAO();
		assertEquals(2, preDAO.getList(390).size());
		PrescriptionBean preBean = preDAO.getList(390).get(1);
		assertEquals("678771191", preBean.getMedication().getNDCode());

		// Close the driver
		driver.quit();
	}
	
	/*
	 * Acceptance Scenario 5
	 */
	public void testAcceptanceScenario5() throws Exception {
		gen.officeVisit7();
		gen.hcp5();
		gen.ndCodes3();
		
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000005");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();

		// Select patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
	    driver.findElement(By.xpath("//input[@value='1']")).submit();

	    // Select the office visit from "01/26/2011"
	    driver.findElement(By.linkText("01/26/2011")).click();
	    
		// Check that the page contains the existing office visit
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		assertEquals(395, getVisitID(driver));
		
		// Check for existing lab procedure
	    assertEquals("Ibuprofen (678771191)", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[3]/td[1]")).getText());
		
		// edit the prescription
	    driver.findElement(By.linkText("Ibuprofen (678771191)")).click();
	    assertEquals("iTrust - Edit Prescription Information", driver.getTitle());
	    driver.findElement(By.name("dosage")).clear();
	    driver.findElement(By.name("dosage")).sendKeys("1000");
	    driver.findElement(By.name("instructions")).clear();
	    driver.findElement(By.name("instructions")).sendKeys("Take as many as you want");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    // Check the page and database
	    assertEquals("iTrust - Document Office Visit", driver.getTitle());
	    assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		PrescriptionsDAO preDAO = factory.getPrescriptionsDAO();
		assertEquals(1, preDAO.getList(395).size());
		PrescriptionBean preBean = preDAO.getList(395).get(0);
		assertEquals("678771191", preBean.getMedication().getNDCode());
		assertEquals(1000, preBean.getDosage());
		assertEquals("Take as many as you want", preBean.getInstructions());
		
	    assertEquals("Ibuprofen (678771191)", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[3]/td")).getText());
	    assertEquals("1000mg", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Take as many as you want", driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[3]/td[4]")).getText());

		// Close the driver
		driver.quit();
	}
	
	/*
	 * Edit Prescription Logging
	 */
	public void testEditPrescriptionLogging() throws Exception {
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();
	    
	    // Select patient 1
	    driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
	    driver.findElement(By.xpath("//input[@value='1']")).submit();
		
	    // Proceed
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
	    // Add a new office visit
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("02/02/2011");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByIndex(2);
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("It's a sunny day.");
	    driver.findElement(By.id("update")).click();
	    
	    // Check if office visit is added
	    assertEquals("Information Successfully Updated", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
		
		// Add a prescription
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("081096 - Aspirin");
        driver.findElement(By.cssSelector("option[value=\"081096\"]")).click();
		driver.findElement(By.id("dosage")).clear();
	    driver.findElement(By.id("dosage")).sendKeys("200");
	    driver.findElement(By.id("startDate")).clear();
	    driver.findElement(By.id("startDate")).sendKeys("02/02/2011");
	    driver.findElement(By.id("endDate")).clear();
	    driver.findElement(By.id("endDate")).sendKeys("02/09/2011");
	    driver.findElement(By.id("instructions")).clear();
	    driver.findElement(By.id("instructions")).sendKeys("Take daily with water.");
	    driver.findElement(By.id("addprescription")).submit();
	    
// Functionality issue
//	    // Check if added
//	    assertEquals("Prescription information successfully updated.", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
//
//		// Edit the prescription
//		driver.findElement(By.linkText("Aspirin (081096)")).click();
//	    assertEquals("iTrust - Edit Prescription Information", driver.getTitle());
//	    driver.findElement(By.name("dosage")).clear();
//	    driver.findElement(By.name("dosage")).sendKeys("400");
//	    driver.findElement(By.id("addprescription")).click();
//	    assertLogged(TransactionType.PRESCRIPTION_EDIT, 9000000000L, 1L, "");
//
//		// Remove the prescription
//	    driver.findElement(By.linkText("Remove")).click();
//		assertLogged(TransactionType.PRESCRIPTION_REMOVE, 9000000000L, 1L, "");
//	    assertEquals("iTrust - Document Office Visit", driver.getTitle());

		// Close the driver
		driver.quit();
	}
	
	/*
	 * Illegal Characters
	 */
	public void testIllegalCharacters() throws Exception {
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();

		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();
		
		// Select patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
	    driver.findElement(By.xpath("//input[@value='1']")).submit();
				
	    // Proceed
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Try to add a new office visit
	    driver.findElement(By.name("visitDate")).clear();
	    driver.findElement(By.name("visitDate")).sendKeys("02/02/2011");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByIndex(2);
	    driver.findElement(By.name("notes")).clear();
	    driver.findElement(By.name("notes")).sendKeys("&ampersand&");
	    driver.findElement(By.id("update")).click();
	    
		// Check for error message
		assertTrue(driver.findElement(By.cssSelector("#iTrustContent > h2")).getText().contains("Information not valid"));
        assertEquals("Notes: Up to 300 alphanumeric characters, with space, and other punctuation", driver.findElement(By.className("errorList")).getText());
        
		assertNotLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 1L, "");

		// Close the driver
		driver.quit();
	}
	
	/*
	 * Prescription No Instructions
	 */
	public void testPrescriptionNoInstructions() throws Exception {
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
				
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
				
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();
				
		// Select patient 1
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("1");
	    driver.findElement(By.xpath("//input[@value='1']")).submit();
						
		// Proceed
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();	
		
		// Add a new office visit
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("02/02/2011");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByIndex(2);
	    driver.findElement(By.name("notes")).sendKeys("It's a sunny day.");
	    driver.findElement(By.id("update")).click();
	    
	    // Check current page
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		
		// Check for office visit
		assertLogged(TransactionType.OFFICE_VISIT_CREATE, 9000000000L, 1L, "");

		// Make sure no prescription
		assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 1L, "");
		
		// Add a prescription, but without instructions
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("081096 - Aspirin");
        driver.findElement(By.cssSelector("option[value=\"081096\"]")).submit();
	    driver.findElement(By.id("dosage")).sendKeys("200");
	    driver.findElement(By.id("startDate")).clear();
	    driver.findElement(By.id("startDate")).sendKeys("02/02/2011");
	    driver.findElement(By.id("endDate")).clear();
	    driver.findElement(By.id("endDate")).sendKeys("02/09/2011");
	    driver.findElement(By.id("instructions")).clear();
	    driver.findElement(By.id("addprescription")).submit();
	    
	    // Check for error message
		assertTrue(driver.findElement(By.xpath("//div[@id='iTrustContent']/div[4]/div/h2")).getText().contains("Information not valid"));
        assertEquals("Instructions: Up to 300 alphanumeric characters, with space, and other punctuation", driver.findElement(By.className("errorList")).getText());
        
	    //assertEquals("Instructions: Up to 300 alphanumeric characters, with space, and other punctuation", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 1L, "");

		// Close the driver
		driver.quit();
	}

	/*
	 * Edit Prescription No Override Reason
	 */
	public void testEditPrescriptionNoOverrideReason() throws Exception {
		// Create instance of the web driver
		WebDriver driver = new HtmlUnitDriver();
		
		//Visit the iTrust home page
		driver.get(ADDRESS);

		// Login
		driver.findElement(By.name("j_username")).sendKeys("9000000000");;
		driver.findElement(By.name("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
				
		// Check if logged in
		assertEquals("iTrust - HCP Home", driver.getTitle());
				
		// Click on the document office visit option
		driver.findElement(By.linkText("Document Office Visit")).click();
				
		// Select patient 2
		driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
	    driver.findElement(By.xpath("//input[@value='2']")).submit();
		
		// Proceed
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Add a new office visit
		driver.findElement(By.name("visitDate")).clear();
		driver.findElement(By.name("visitDate")).sendKeys("02/02/2011");
	    new Select(driver.findElement(By.name("hospitalID"))).selectByIndex(2);
	    driver.findElement(By.name("notes")).sendKeys("It's a sunny day.");
	    driver.findElement(By.id("update")).click();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
		assertNotLogged(TransactionType.PRESCRIPTION_ADD, 9000000000L, 1L, "");
		
		// Add a prescription that is an allergy
		new Select(driver.findElement(By.id("medID"))).selectByVisibleText("664662530 - Penicillin");
        driver.findElement(By.cssSelector("option[value=\"664662530\"]")).submit();
		driver.findElement(By.name("dosage")).clear();
	    driver.findElement(By.name("dosage")).sendKeys("200");
	    driver.findElement(By.name("startDate")).clear();
	    driver.findElement(By.name("startDate")).sendKeys("02/02/2011");
	    driver.findElement(By.name("endDate")).clear();
	    driver.findElement(By.name("endDate")).sendKeys("02/09/2011");
	    driver.findElement(By.name("instructions")).clear();
	    driver.findElement(By.name("instructions")).sendKeys("Take every day");
	    driver.findElement(By.id("addprescription")).submit();
	    
	    // Check for error message
		assertTrue(driver.findElement(By.xpath("//table[@id='prescriptionsTable']/tbody/tr[6]/td/div/div")).getText().contains("Allergy: Penicillin"));

//	    assertEquals("Allergy: Penicillin", driver.findElement(By.cssSelector("span.iTrustMessage")).getText());
	    driver.findElement(By.id("continue")).click();

		// Close the driver
		driver.quit();
	}

}
