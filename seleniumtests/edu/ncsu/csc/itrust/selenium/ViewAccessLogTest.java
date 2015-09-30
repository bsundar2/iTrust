package edu.ncsu.csc.itrust.selenium;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ViewAccessLogTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.uap1();
		gen.patient2();
		gen.patient1();
		gen.patient4();
		gen.hcp0();
		gen.hcp3();
		gen.er4();
	}
	
	/*
	 * HCP 9000000000 has viewed PHR of patient 2.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View Access Log
	 */
	public void testViewAccessLog1() throws Exception {

		gen.transactionLog();
		
		// hcp views phr of patient 2
		// login hcp
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		// click Edit PHR
		WebElement link = driver.findElement(By.linkText("PHR Information"));
		
        // URL assertion
        String linkLocatin = link.getAttribute("href");
        assertEquals("/iTrust/auth/hcp-uap/editPHR.jsp", linkLocatin);
        
        link.click();
        
        driver.findElement(By.name("UID_PATIENTID")).sendKeys("2");
        driver.findElement(By.xpath("//input[@value='2']")).submit();
        
		assertEquals(ADDRESS + "auth/hcp-uap/editPHR.jsp", driver.getCurrentUrl());
		assertLogged(TransactionType.PATIENT_HEALTH_INFORMATION_VIEW, 9000000000L, 2L, "");
        
		// login patient 2
		driver = (HtmlUnitDriver)login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();

		// Capture the table
		// Source from: http://stackoverflow.com/questions/14191935/how-to-find-specific-lines-in-a-table-using-selenium
	
		WebElement baseTable = driver.findElement(By.className("fTable"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		
		// Test if the first row contains the date format 
		// If there is, then we successfully capture the table
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
		
		assertTrue(tableRows.get(1).getText().contains(dateFormat.format(date)));
		assertTrue(tableRows.get(1).getText().contains("Kelly Doctor"));
		assertTrue(tableRows.get(1).getText().contains("View personal health information"));
	
	}
	
	/*
	 * HCP 9000000000 has viewed PHR of patient 2 on 11/11/2007.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View Access Log
	 * Choose date range 6/22/2000 through 6/23/2000
	 */
	public void testViewAccessLog2() throws Exception {

		gen.transactionLog();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");

		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();
		
		WebElement startDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[2]/input[1]"));
		startDate_box.sendKeys("06/22/2000");
		
		WebElement dueDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[4]/input[1]"));
		dueDate_box.sendKeys("06/23/2000");
		
		// No need to test "exception", try "No data" this time instead
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.submit();
		
		WebElement chart_generated = driver.findElementById("iTrustContent");
	
		assertTrue( chart_generated.getText().contains("No Data"));
		assertLogged(TransactionType.ACCESS_LOG_VIEW, 2L, 0L, "");
	}
	
	/*
	 * Authenticate Patient
	 * MID: 1
	 * Password: pw
	 * Choose option View Access Log
	 */
	public void testViewAccessLog3() throws Exception {
		gen.transactionLog();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");

		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();
		
		WebElement chart_generated = driver.findElementById("iTrustContent");
	
		assertTrue( chart_generated.getText().contains("No Data"));
		assertLogged(TransactionType.ACCESS_LOG_VIEW, 1L, 0L, "");
	}
	
	/*
	 * HCP 9000000000 has viewed PHR of patient 2 on 11/11/2007.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View Access Log
	 * Choose date range 2000/5/5 through 2020/5/5
	 * Invalid format in use yyyy/mm/dd
	 */
	public void testViewAccessLogYearFirstFormat() throws Exception {

		gen.transactionLog();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");

		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();
		
		
		// enable JAVASCRIPT
		// I am trying to avoid to use sendKeys since this command will
		// automatically validate the date form. 
		
		// Source: http://stackoverflow.com/questions/25583641/set-value-of-input-instead-of-sendkeys-selenium-webdriver-nodejs
		
		// After researching online, input value can be made through Javascript
		driver.setJavascriptEnabled(true);
		
		WebElement startDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[2]/input[1]"));
		// startDate_box.sendKeys("2000/5/5");
		driver.executeScript("arguments[0].setAttribute('value', '" + "2000/5/5" +"')", startDate_box);
		
		WebElement dueDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[4]/input[1]"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "2020/5/5" +"')", dueDate_box);
		

		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.click();
		
		WebElement message = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/h2"));
		
		
		// iTrust should prompt a exception because an invalid input has been made.
		assertTrue( message.getText().contains("Information not valid"));
	}
	
	/*
	 * HCP 9000000000 has viewed PHR of patient 2 on 11/11/2007.
	 * Authenticate Patient
	 * MID: 2
	 * Password: pw
	 * Choose option View Access Log
	 * Choose date range 11/12/2015 through 11/11/2015
	 * Invalid format in use yyyy/mm/dd
	 */
	public void testViewAccessLogDateOrder() throws Exception {

		gen.transactionLog();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");

		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();
		
		WebElement startDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[2]/input[1]"));
		driver.setJavascriptEnabled(true);
		driver.executeScript("arguments[0].setAttribute('value', '" + "11/12/2015" +"')", startDate_box);
		
		WebElement dueDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[4]/input[1]"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "11/11/2015" +"')", dueDate_box);
		

		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.click();
		
		WebElement message = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/h2"));
		
		
		// iTrust should prompt a exception because an invalid input has been made.
		assertTrue( message.getText().contains("Information not valid"));
	}
	
	public void testViewAccessLogByDate() throws Exception {

		gen.transactionLog();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");

		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();
		
		WebElement startDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[2]/input[1]"));
		
		driver.setJavascriptEnabled(true);
		driver.executeScript("arguments[0].setAttribute('value', '" + "03/01/2008" +"')", startDate_box);
		
		WebElement dueDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[4]/input[1]"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "12/01/2008" +"')", dueDate_box);
		

		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.click();
		
		WebElement baseTable = driver.findElement(By.className("fTable"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		
		System.out.println(tableRows.get(1).getText());
		
		assertTrue(tableRows.get(1).getText().contains("No Data"));
		
		// Strange error: TypeError: Cannot find function addEventListener in object [object]
		// http://grokbase.com/t/gg/selenium-users/126ng1snzx/typeerror-cannot-find-function-addeventlistener-in-object-exception-using-htmlunitdriver
		// 7:31 PM Mar, 26 
	}
	
	public void testViewAccessLogByRole() throws Exception {
		gen.transactionLog3();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		
		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();
		
		WebElement startDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[2]/input[1]"));
		
		driver.setJavascriptEnabled(true);
		driver.executeScript("arguments[0].setAttribute('value', '" + "02/01/2008" +"')", startDate_box);
		
		WebElement dueDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[4]/input[1]"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "09/22/2009" +"')", dueDate_box);
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.click();
		
		// Tricky part. This box is hidden and written in scipt.
		// Note: No need to get script out from the page. Directly run the function works.
		//driver.executeScript("document.getElementsByName('sortBy')[0].setAttribute(\"dateOrRole\", 'role');");
		
		driver.findElement(By.linkText("Role")).click();
		// Dang it took me one hour siting here stupidly trying to figure out how form and javascript should work
		// Just click "Role" will sort roles.
		
		WebElement baseTable = driver.findElement(By.className("fTable"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		
		assertTrue(tableRows.get(1).getText().contains("Emergency Responder"));
		assertTrue(tableRows.get(2).getText().contains("LHCP"));
		assertTrue(tableRows.get(3).getText().contains("LHCP"));
		assertTrue(tableRows.get(4).getText().contains("LHCP"));
		assertTrue(tableRows.get(5).getText().contains("Personal Health Representative"));
		assertTrue(tableRows.get(6).getText().contains("UAP"));
        
	}
	
	/**
	 * Verifies that a representative is able to view the access log of a representee.
	 * @throws Exception
	 */
	public void testViewAccessLogRepresentativeView() throws Exception {

		gen.clearAllTables();
		gen.standardData();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("24", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		// click on View Access Log
		driver.findElement(By.linkText("Access Log")).click();
		
		// Create  a select class
		Select viewLogFor = new Select
				(driver.findElementByXPath("//*[@id=\"logMIDSelectMenu\"]"));
		viewLogFor.selectByVisibleText("Dare Devil");
		
		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.click();
		

		WebElement baseTable = driver.findElement(By.className("fTable"));
		assertFalse(baseTable.getText().contains("Beaker Beaker"));
		assertTrue(baseTable.getText().contains("2007-06-23 06:55:59.0"));
		System.out.print(baseTable.getText());
		
		// Refresh table
		baseTable = driver.findElement(By.className("fTable"));
		// Click on Role button
		driver.findElement(By.linkText("Role")).click();
		assertEquals("iTrust - View My Access Log", driver.getTitle());
		assertFalse(baseTable.getText().contains("Beaker Beaker"));
		assertTrue(baseTable.getText().contains("2007-06-23 06:55:59.0"));

		driver.findElement(By.linkText("Access Log")).click();
		assertEquals("iTrust - View My Access Log", driver.getTitle());
		

		driver.findElement(By.linkText("Access Log")).click();
		baseTable = driver.findElement(By.className("fTable"));
		
		assertFalse(baseTable.getText().contains("Kelly Doctor"));
		assertTrue(baseTable.getText().contains("2007-06-25 06:54:59.0"));
	}
	
	/**
	 * Verifies that a non-representative is not shown a representee to select.
	 * @throws Exception
	 */
	public void testViewAccessLogNonRepresentativeView1() throws Exception {
		gen.clearAllTables();
		gen.standardData();

		HtmlUnitDriver driver = (HtmlUnitDriver)login("24", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());

		driver.findElement(By.linkText("Access Log")).click();
		assertEquals("iTrust - View My Access Log", driver.getTitle());

		WebElement baseTable = driver.findElement(By.className("fTable"));
		assertFalse(baseTable.getText().contains("Devils Advocate"));
	}
	
	/**
	 * Verifies that DLHCP information is correctly hidden in the Access Log for a non-representative.
	 * @throws Exception
	 */
	public void testViewAccessLogNonRepresentativeView2() throws Exception {
		gen.clearAllTables();
		gen.standardData();

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000007", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());

		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
		// choose a patient
        driver.findElement(By.name("UID_PATIENTID")).sendKeys("5");
        driver.findElement(By.xpath("//input[@value='5']")).submit();
		assertEquals("iTrust - Edit Patient", driver.getTitle());
		
		// click on log out
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
		assertEquals("iTrust - Login", driver.getTitle());
		
		// refreshed driver
		driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
        driver.findElement(By.name("UID_PATIENTID")).sendKeys("5");
        driver.findElement(By.xpath("//input[@value='5']")).submit();
		assertEquals("iTrust - Edit Patient", driver.getTitle());
		
		// click PHR information
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
		assertEquals("iTrust - Login", driver.getTitle());
		
		// what's the point of test like this?
		driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Please Select a Patient", driver.getTitle());
		
        driver.findElement(By.name("UID_PATIENTID")).sendKeys("5");
        driver.findElement(By.xpath("//input[@value='5']")).submit();
		assertEquals("iTrust - Edit Patient", driver.getTitle());
		
		driver.findElement(By.linkText("Basic Health Information")).click();
		assertEquals("iTrust - Edit Basic Health Record", driver.getTitle());
		
		driver.findElement(By.linkText("Patient Information")).click();
		assertEquals("iTrust - Edit Patient", driver.getTitle());
		
		driver.findElement(By.linkText("PHR Information")).click();
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/ul/li[2]/a")).click();
		assertEquals("iTrust - Login", driver.getTitle());
		
		
		driver = (HtmlUnitDriver)login("5", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		driver.findElement(By.linkText("Access Log")).click();
		assertEquals("iTrust - View My Access Log", driver.getTitle());
		
		WebElement baseTable = driver.findElement(By.className("fTable"));
		assertFalse(baseTable.getText().contains("Kelly Doctor"));
		assertTrue(baseTable.getText().contains("Beaker Beaker"));
	}
	
	/**
	 * Verifies that the access log correctly handle bad date inputs
	 * @throws Exception
	 */
	public void testViewAccessLogBadDateHandling() throws Exception
	{
		gen.clearAllTables();
		gen.standardData();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("23", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		driver.findElement(By.linkText("Access Log")).click();
		assertEquals("iTrust - View My Access Log", driver.getTitle());
		
		driver.setJavascriptEnabled(true);
		
		WebElement startDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[2]/input[1]"));
		// startDate_box.sendKeys("2000/5/5");
		driver.executeScript("arguments[0].setAttribute('value', '" + "6/22/2007" +"')", startDate_box);
		
		WebElement dueDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[4]/input[1]"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "6/21/2007" +"')", dueDate_box);

		WebElement submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.click();
		

		WebElement message = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]/h2"));		
		// iTrust should prompt a exception because an invalid input has been made.
		assertTrue( message.getText().contains("Information not valid"));
		
		// refresh two boxes and one button. Use findelementByName this time because "June 22nd" will ne 
		// be validated after all
		startDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[2]/input[1]"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "June 22nd, 2007" +"')", startDate_box);
		
		dueDate_box = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/table/tbody/tr[2]/td[4]/input[1]"));
		driver.executeScript("arguments[0].setAttribute('value', '" + "6/23/2007" +"')", dueDate_box);
		
		submit_button = driver.findElement(By.xpath("//*[@id=\"logMIDSelectionForm\"]/div/input"));
		submit_button.click();
		
		message = driver.findElement(By.xpath("//*[@id=\"iTrustContent\"]"));
		
		// iTrust should prompt a exception because an invalid input has been made.
		assertTrue( message.getText().contains("Information not valid"));
		
	}
}
