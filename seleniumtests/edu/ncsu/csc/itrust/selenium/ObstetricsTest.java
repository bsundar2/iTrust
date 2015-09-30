package edu.ncsu.csc.itrust.selenium;

//import java.awt.Robot;

import org.openqa.selenium.By;
/*import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;*/
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
/*import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebResponse;
import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;
*/
import edu.ncsu.csc.itrust.enums.FlagValue;
import edu.ncsu.csc.itrust.enums.TransactionType;

public class ObstetricsTest extends iTrustSeleniumTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		//gen.uc63(); //no longer needed because the uc64() method calls it
		gen.uc64();
		gen.hcp0(); //Kelly Doctor
		gen.patient21(); //Princess Peach
		gen.patient22();
	}

	/* Initializing Obstetrics Records UC63 */
	public void testAddPatientNoPriors() throws Exception {
		
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");

		driver.getCurrentUrl();
		
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();		
		
		driver.getCurrentUrl();
		// select specified patient
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("400");
		
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		// button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
	
		// fill out the form and submit
		driver.getCurrentUrl();
		
		
//		element = driver.findElement(By.name("lmp"));
//		element.sendKeys("7/1/2014");
//		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
//		jsExecutor.executeScript("$(arguments[0]).change();", element);
		
		
		
		
		
		
		driver.findElement(By.name("lmp")).sendKeys("7/1/2014");	
		driver.findElement(By.name("date")).sendKeys("9/23/2014");
		driver.findElement(By.name("edd")).sendKeys("4/7/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("12-0");
		driver.findElement(By.id("submit")).submit();
		
	    
		driver.getCurrentUrl();
		// submit button click should trigger page redirect
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		// success message should be displayed
		driver.getCurrentUrl();
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 400L, "");
		//*/
	}
	
	public void testAddPatient2Priors() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		// make sure previous pregnancies are listed
		assertTrue(driver.getPageSource().contains("2010"));
		assertTrue(driver.getPageSource().contains("2012"));
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("7/26/2014");
		driver.findElement(By.name("date")).sendKeys("10/14/2014");
		driver.findElement(By.name("edd")).sendKeys("05/02/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("11-3");
		driver.findElement(By.id("submit")).submit();
		
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 401L, "");
	}
	
	//TODO while this functionality exists, it is untestable because of HttpUnit not running complicated JavaScript
	public void testAddPatientEnterPrior() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("402");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("08/24/2014");
		driver.findElement(By.name("date")).sendKeys("10/4/2014");
		driver.findElement(By.name("edd")).sendKeys("05/31/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("5-6");
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 402L, "");
	}
	
	public void testAddPatientMale() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("403");
		// button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
	}
			
	public void testViewPatientNonObstetricsHCP() throws Exception {
		testAddPatient2Priors();
		// login HCP Kelly Doctor (not obstetrics)		
		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		// view a previous record
		driver.findElement(By.linkText("10/14/2014-Initial")).click();
		assertEquals("iTrust - View Obstetrics Record", driver.getTitle());	
	}
	
	public void testAddNonExistentPatient() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("Somebody Else");
		driver.getCurrentUrl();
		assertTrue(driver.getPageSource().contains("0"));
	}
	
	public void testAddPatientByID() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("404");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("03/14/2013");
		driver.findElement(By.name("date")).sendKeys("03/22/2013");
		driver.findElement(By.name("edd")).sendKeys("12/19/2013");
		driver.findElement(By.name("weeksPregnant")).sendKeys("1-1");
		driver.findElement(By.id("submit")).submit();
		
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		assertLogged(TransactionType.CREATE_INITIAL_OBSTETRICS_RECORD, 9000000012L, 404L, "");
	}
	
	public void testViewPatientObstetricsHCP() throws Exception {
		testAddPatientByID();
		// login HCP Harry Potter
		WebDriver driver = login("9000000013", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000013L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("404");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// view a previous record
		driver.findElement(By.linkText("03/22/2013-Initial")).click();
		assertEquals("iTrust - View Obstetrics Record", driver.getTitle());	
		assertLogged(TransactionType.VIEW_INITIAL_OBSTETRICS_RECORD, 9000000013L, 404L, "");
	}
	
	public void testAddPatientFutureLMP() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("405");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("05/14/2045");
		driver.findElement(By.name("date")).sendKeys("10/04/2014");
		driver.findElement(By.name("edd")).sendKeys("2/18/2046");
		driver.findElement(By.name("weeksPregnant")).sendKeys("-1597--1");
		driver.findElement(By.id("submit")).submit();
		// submit button click should trigger error message
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Last menstrual period cannot be after Date of visit"));
	}
	
	public void testAddPatientChangeMind() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("406");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		
		driver.findElement(By.linkText("Back to Home")).click();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
	}
	
	/* Adding Obstetrics Office Visits UC64 */
	public void testAddObstetricsOV() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Add Obstetrics Office Visit
		driver.findElement(By.linkText("Add Obstetrics Office Visit")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("400");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("10/07/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("14-0");	
		driver.findElement(By.name("weight")).sendKeys("137");
		driver.findElement(By.name("bloodPressureS")).sendKeys("103");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("62");	
		driver.findElement(By.name("fhr")).sendKeys("152");		
		driver.findElement(By.name("fhu")).sendKeys("14");	
		driver.findElement(By.id("submit")).submit();
		
		
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));
		
	}
	
	public void testAddEditObstetricsOV() throws Exception {
		//-----Add-----//
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Add Obstetrics Office Visit
		driver.findElement(By.linkText("Add Obstetrics Office Visit")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("11/25/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("17-3");	
		driver.findElement(By.name("weight")).sendKeys("147.2");
		driver.findElement(By.name("bloodPressureS")).sendKeys("104");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("58");	
		driver.findElement(By.name("fhr")).sendKeys("143");		
		driver.findElement(By.name("fhu")).sendKeys("17");	
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));
		
		//-----Edit-----//
		// click on the desired Office Visit link
		driver.findElement(By.partialLinkText("11/25/2014-Office Visit")).click();
		driver.getCurrentUrl();
		driver.findElement(By.id("officeVisit"));
		driver.findElement(By.name("fhu")).sendKeys("18");	
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully edited"));
	}
	
	public void testAddObstetricsNonObHCP() throws Exception {
		// login HCP Kelly Doctor
		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// click on Add Obstetrics Office Visit
		driver.findElement(By.linkText("Add Obstetrics Office Visit")).click();	
		// select specified patient
		driver.getCurrentUrl();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("21");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Document Office Visit", driver.getTitle());
	}
	
	public void testAddObstetricsOVPregnancyOver() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Add Obstetrics Office Visit
		driver.findElement(By.linkText("Add Obstetrics Office Visit")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("404");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("03/01/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("50-2");	
		driver.findElement(By.name("weight")).sendKeys("145.6");
		driver.findElement(By.name("bloodPressureS")).sendKeys("101");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("60");	
		driver.findElement(By.name("fhr")).sendKeys("158");		
		driver.findElement(By.name("fhu")).sendKeys("24");	
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The patient chosen is not a current obstetrics patient"));		
	}
	
	public void testAddObstetricsOVNotInitialized() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		
		// click on Add Obstetrics Office Visit
		driver.findElement(By.linkText("Add Obstetrics Office Visit")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("21");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("The patient chosen is not a current obstetrics patient"));
	}
	
	/**
	 * Tests the full control flow path for the twins flag.
	 * <ol>
	 * <li>Init patient</li>
	 * <li>Verify twins not set in initial pregnancy record</li>
	 * <li>Add a new office visit (twins box not checked)</li>
	 * <li>Verify twins not set in initial pregnancy record</li>
	 * <li>Edit office visit, set twins flag</li>
	 * <li>Verify twins set in initial pregnancy record</li>
	 * <li>Edit office visit, verify twins flag set</li>
	 * <li>Edit office visit, uncheck twins flag</li>
	 * <li>Verify twins not set in initial pregnancy record</li>
	 * </ol>
	 * @throws Exception
	 */
	public void testAllTwinsFlag() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");

		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("21");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("10/14/2014");
		driver.findElement(By.name("date")).sendKeys("10/29/2014");
		driver.findElement(By.name("edd")).sendKeys("7/21/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("2-1");
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		
		// click open the new record
		driver.findElement(By.partialLinkText("10/29/2014-Initial")).click();
		//Verify not twins yet
		driver.getCurrentUrl();
		assertTrue( driver.findElement(By.name("twins")).toString().contains("disabled"));
		
		// goto add page
		driver.findElement(By.partialLinkText("Add Obstetrics Office Visit")).click();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("11/05/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-1");	
		driver.findElement(By.name("weight")).sendKeys("140.2");
		driver.findElement(By.name("bloodPressureS")).sendKeys("104");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("58");	
		driver.findElement(By.name("fhr")).sendKeys("143");		
		driver.findElement(By.name("fhu")).sendKeys("17");	
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));
		
		// go back and verify still not twins
		driver.findElement(By.partialLinkText("10/29/2014-Initial")).click();
		//Verify not twins yet
		assertTrue( !driver.findElement(By.name("twins")).toString().contains("checked"));
		//go back
		driver.findElement(By.partialLinkText("Back to Home")).click();
		
		// click on the desired Office Visit link
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		driver.getCurrentUrl();
		driver.findElement(By.id("twins")).click();
		driver.findElement(By.id("submit")).submit();
		//go back into the visit and make sure the field is checked
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		System.out.println(driver.findElement(By.id("twins")));
		assertTrue( driver.findElement(By.name("twins")).toString().contains("checked"));
		//go back
		driver.findElement(By.partialLinkText("Back to Home")).click();
		
		// go back and verify now twins is set
		driver.findElement(By.partialLinkText("10/29/2014-Initial")).click();
		//Verify not twins yet
		assertTrue( driver.findElement(By.name("twins")).toString().contains("checked"));
		//go back
		driver.findElement(By.partialLinkText("Back to Home")).click();
		
		// click on the desired Office Visit link
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		driver.findElement(By.id("twins")).click();
		
		driver.findElement(By.id("submit")).submit();
		// go back and verify uncheck worked
		driver.findElement(By.partialLinkText("10/29/2014-Initial")).click();
		//Verify not twins yet
		assertTrue( !driver.findElement(By.name("twins")).toString().contains("checked"));
	}
	
	/**
	 * Tests the full control flow path for the low-lying placenta flag.
	 * <ol>
	 * <li>Init patient</li>
	 * <li>Add a new office visit (placenta box not checked)</li>
	 * <li>Edit office visit, verify placenta flag not set</li>
	 * <li>Edit office visit, set placenta flag</li>
	 * <li>Edit office visit, verify placenta flag set</li>
	 * <li>Edit office visit, uncheck placenta flag</li>
	 * <li>Edit office visit, verify placenta flag not set</li>
	 * </ol>
	 * @throws Exception
	 */
	public void testAllPlacentaFlag() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("21");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("10/14/2014");
		driver.findElement(By.name("date")).sendKeys("10/29/2014");
		driver.findElement(By.name("edd")).sendKeys("7/21/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("2-1");
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));

		
		// goto add page
		driver.findElement(By.partialLinkText("Add Obstetrics Office Visit")).click();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("11/05/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-1");	
		driver.findElement(By.name("weight")).sendKeys("140.2");
		driver.findElement(By.name("bloodPressureS")).sendKeys("104");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("58");	
		driver.findElement(By.name("fhr")).sendKeys("143");		
		driver.findElement(By.name("fhu")).sendKeys("17");	
		driver.findElement(By.id("submit")).submit();
		
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));

		// go back and verify still not twins
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		//Verify not placenta yet
		assertTrue( !driver.findElement(By.name("placenta")).toString().contains("checked"));
		//go back
		driver.findElement(By.partialLinkText("Back to Home")).click();
		
		// click on the desired Office Visit link
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		driver.getCurrentUrl();
		driver.findElement(By.id("placenta")).click();
		driver.findElement(By.id("submit")).submit();
		//go back into the visit and make sure the field is checked
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		assertTrue( driver.findElement(By.name("placenta")).toString().contains("checked"));
		//go back
		driver.findElement(By.partialLinkText("Back to Home")).click();
		
		// go back and verify now twins is set
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		//Verify placenta
		assertTrue( driver.findElement(By.name("placenta")).toString().contains("checked"));
		//go back
		driver.findElement(By.partialLinkText("Back to Home")).click();
		
		// click on the desired Office Visit link
		driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		driver.findElement(By.id("placenta")).click();
		
		driver.findElement(By.id("submit")).submit();
		// go back and verify uncheck worked
	//	driver.findElement(By.partialLinkText("11/05/2014-Office Visit")).click();
		//Verify not twins yet
	//	assertTrue( !driver.findElement(By.name("placenta")).toString().contains("checked"));
	}
	
	//Begin UC66
	
	public void testDocumentObOVAgeHBPWeight() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
	
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("10/05/2014");
		driver.findElement(By.name("date")).sendKeys("10/29/2014");
		driver.findElement(By.name("edd")).sendKeys("7/12/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-3");
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));

		//Now begins test unique stuff
		
		// goto add page
		driver.findElement(By.partialLinkText("Add Obstetrics Office Visit")).click();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("11/05/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("4-3");	
		driver.findElement(By.name("weight")).sendKeys("125");
		driver.findElement(By.name("bloodPressureS")).sendKeys("100");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("91");	
		driver.findElement(By.name("fhr")).sendKeys("160");		
		driver.findElement(By.name("fhu")).sendKeys("14");	
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));

		assertTrue(driver.getPageSource().contains(FlagValue.HighBloodPressure.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.AdvancedMaternalAge.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.WeightChange.toString()));
	}
	
	public void testDocumentObOVHighFHR() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
	
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("10/05/2014");
		driver.findElement(By.name("date")).sendKeys("10/29/2014");
		driver.findElement(By.name("edd")).sendKeys("7/12/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-3");
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		
		//Now begins test unique stuff
		
		// goto add page
		driver.findElement(By.partialLinkText("Add Obstetrics Office Visit")).click();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("11/05/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("4-3");	
		driver.findElement(By.name("weight")).sendKeys("140");
		driver.findElement(By.name("bloodPressureS")).sendKeys("130");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("80");	
		driver.findElement(By.name("fhr")).sendKeys("171");		
		driver.findElement(By.name("fhu")).sendKeys("14");	
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));

		assertTrue(driver.getPageSource().contains(FlagValue.AbnormalFHR.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.AdvancedMaternalAge.toString()));
	}
	
	public void testDocumentAbnormalFHRBoundaryValue() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
	
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("10/05/2014");
		driver.findElement(By.name("date")).sendKeys("10/29/2014");
		driver.findElement(By.name("edd")).sendKeys("7/12/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-3");
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		
		//Now begins test unique stuff
		
		// goto add page
		driver.findElement(By.partialLinkText("Add Obstetrics Office Visit")).click();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("11/05/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("4-3");	
		driver.findElement(By.name("weight")).sendKeys("140");
		driver.findElement(By.name("bloodPressureS")).sendKeys("130");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("80");	
		driver.findElement(By.name("fhr")).sendKeys("105");		
		driver.findElement(By.name("fhu")).sendKeys("14");	
		driver.findElement(By.id("submit")).submit();
		
		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));

		assertTrue(!driver.getPageSource().contains(FlagValue.AbnormalFHR.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.AdvancedMaternalAge.toString()));
	}
	
	
	//Begin UC67
	
	public void testAddAllergy() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on PHR Info
		driver.findElement(By.linkText("PHR Information")).click();
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		
		
		// fill in the office visit part of the form
		driver.findElement(By.name("description")).sendKeys("Penicillin");
		//submit the page
		driver.findElement(By.name("addA")).click();
		
		//validate current page updated
		assertTrue(driver.getPageSource().contains("Penicillin"));
				
		//go to the obstetrics homepage
		driver.findElement(By.linkText("View Obstetrics Records")).click();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		//validate obstetrics page updated
		assertTrue(driver.getPageSource().contains("Penicillin"));
	}
	
	public void testAddSecondAllergy() throws Exception {
		testAddAllergy();
		
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on PHR Info
		driver.findElement(By.linkText("PHR Information")).click();
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		
		// fill in the office visit part of the form
		driver.findElement(By.name("description")).sendKeys("Humans");
		//submit the page
		driver.findElement(By.name("addA")).click();
		
		//validate current page updated
		assertTrue(driver.getPageSource().contains("Penicillin"));
		assertTrue(driver.getPageSource().contains("Humans"));
		
		//go to the obstetrics homepage
		driver.findElement(By.linkText("View Obstetrics Records")).click();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		//validate current page updated
		assertTrue(driver.getPageSource().contains("Penicillin"));
		assertTrue(driver.getPageSource().contains("Humans"));
	}
	
	public void testAddInvalidAllergy() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on PHR Info
		driver.findElement(By.linkText("PHR Information")).click();
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		
		// fill in the office visit part of the form
		driver.findElement(By.name("description")).sendKeys("!@#$");
		//submit the page
		driver.findElement(By.name("addA")).click();
		
		//validate current page updated
		assertTrue(!driver.getPageSource().contains("!@#$"));
		assertTrue(driver.getPageSource().contains("This form has not been validated correctly. The following field are not properly filled in: [Allergy Description: Up to 30 characters, letters, numbers, and a space]"));
		
		//go to the obstetrics homepage
		driver.findElement(By.linkText("View Obstetrics Records")).click();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		//validate obstetrics home updated
		assertTrue(!driver.getPageSource().contains("Humans"));
	}
	
	public void testBasicLaborDeliveryReport() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("401");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
	
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("10/05/2014");
		driver.findElement(By.name("date")).sendKeys("10/29/2014");
		driver.findElement(By.name("edd")).sendKeys("7/12/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-3");
		driver.findElement(By.id("submit")).submit();

		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		
		
		//Now begins test unique stuff
		
		// click Labor and Delivery Report
		driver.findElement(By.linkText("Labor and Delivery Report")).click();
		assertEquals("iTrust - Labor and Delivery Report", driver.getTitle());
		
		//Spot check pregnancy data
		assertTrue(driver.getPageSource().contains("Miscarriage"));
		assertTrue(driver.getPageSource().contains("Vaginal Delivery"));
		
		//Spot check EDD data
		assertTrue(driver.getPageSource().contains("07/12/2015"));
		
		
	}
	
	public void testLaborDeliveryReportFlags() throws Exception {
		// login HCP Kathryn Evans
		WebDriver driver = login("9000000012", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000012L, 0L, "");
		
		// click on Obstetrics Home
		driver.findElement(By.linkText("Obstetrics Home")).click();	
		// select specified patient
		driver.getCurrentUrl();
		WebElement element = driver.findElement(By.name("UID_PATIENTID"));
		element.sendKeys("21");
		// button click should trigger page redirect
		element.submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		
		// click Add Obstetrics Record
		driver.findElement(By.id("addInitialButtonForm")).submit();
		driver.getCurrentUrl();
		assertEquals("iTrust - Initialize Obstetrics Record", driver.getTitle());
	
		
		// fill out the form and submit
		driver.findElement(By.name("lmp")).sendKeys("10/05/2014");
		driver.findElement(By.name("date")).sendKeys("10/29/2014");
		driver.findElement(By.name("edd")).sendKeys("7/12/2015");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-3");
		driver.findElement(By.id("submit")).submit();

		// submit button click should trigger page redirect
		driver.getCurrentUrl();
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertEquals(ADDRESS + "auth/hcp/obstetricsHome.jsp?initial", driver.getCurrentUrl().toString());
		assertTrue(driver.getPageSource().contains("Obstetrics Record successfully added"));
		
		// click on PHR Info
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		
		
		// fill in the office visit part of the form
		driver.findElement(By.name("description")).sendKeys("Penicillin");
		//submit the page
		driver.findElement(By.name("addA")).click();
		
		//validate current page updated
		assertTrue(driver.getPageSource().contains("Penicillin"));
			
		// goto add page
		driver.findElement(By.partialLinkText("Add Obstetrics Office Visit")).click();
		driver.getCurrentUrl();
		assertEquals("iTrust - Add Obstetrics Office Visit", driver.getTitle());
		
		// fill out the form and submit
		driver.findElement(By.name("date")).sendKeys("11/05/2014");
		driver.findElement(By.name("weeksPregnant")).sendKeys("3-1");	
		driver.findElement(By.name("weight")).sendKeys("140");
		driver.findElement(By.name("bloodPressureS")).sendKeys("190");		
		driver.findElement(By.name("bloodPressureD")).sendKeys("80");	
		driver.findElement(By.name("fhr")).sendKeys("200");		
		driver.findElement(By.name("fhu")).sendKeys("14");
		driver.findElement(By.name("twins")).click();
		driver.findElement(By.id("submit")).submit();
		
		// should redirect to the obstetrics homepage
		assertEquals("iTrust - Obstetrics", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Obstetrics Office Visit successfully added"));
		
		// validate that appropriate conditions were flagged
		assertTrue(driver.getPageSource().contains(FlagValue.AbnormalFHR.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.Twins.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.HighBloodPressure.toString()));

		// add a pre-existing condition
		driver.findElement(By.partialLinkText("Patient Pre-existing Conditions")).click();
		assertEquals("iTrust - Obstetrics Record Pre-Existing Conditions", driver.getTitle());
		
		driver.findElement(By.name("condition")).sendKeys("Diabetes");
		driver.findElement(By.id("submit")).submit();
		
		// validate that it was added successfully
		assertEquals("iTrust - Obstetrics Record Pre-Existing Conditions", driver.getTitle());
		assertTrue(driver.getPageSource().contains("Diabetes"));
	
		// click Labor and Delivery Report
		driver.findElement(By.partialLinkText("Labor and Delivery Report")).click();
		assertEquals("iTrust - Labor and Delivery Report", driver.getTitle());
		
		// check pregnancy data: flags, pre-existing conditions, allergies, etc.
		assertTrue(driver.getPageSource().contains("Penicillin"));
		assertTrue(driver.getPageSource().contains("Diabetes"));
		assertTrue(driver.getPageSource().contains(FlagValue.AbnormalFHR.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.Twins.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.HighBloodPressure.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.MaternalAllergies.toString()));
		assertTrue(driver.getPageSource().contains(FlagValue.PreExistingConditions.toString()));		
	}

}
