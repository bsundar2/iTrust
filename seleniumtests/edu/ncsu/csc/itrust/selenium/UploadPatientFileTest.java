package edu.ncsu.csc.itrust.selenium;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;


import edu.ncsu.csc.itrust.enums.TransactionType;

public class UploadPatientFileTest extends iTrustSeleniumTest {
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testHCPPatientUploadValidData() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());

		WebElement form = driver.findElement(By.name("patientFile"));
		form.sendKeys("testing-files/sample_patientupload/HCPPatientUploadValidData.csv");
		WebElement sendsubmit = driver.findElement(By.name("sendFile"));
		sendsubmit.submit();
		
		WebElement baseTable = driver.findElement(By.className("fTable"));
		List<WebElement> tableRows = baseTable.findElements(By.tagName("tr"));
		
		assertTrue(tableRows.get(1).getText().contains("Michael"));
		assertTrue(tableRows.get(1).getText().contains("Marley"));
		assertTrue(tableRows.get(2).getText().contains("Michael"));
		assertTrue(tableRows.get(2).getText().contains("Bazik"));
		assertTrue(tableRows.get(3).getText().contains("Barry"));
		assertTrue(tableRows.get(3).getText().contains("Peddycord"));
	}
	
	public void testHCPPatientUploadRequiredFieldMissing() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());

		WebElement form = driver.findElement(By.name("patientFile"));
		form.sendKeys("testing-files/sample_patientupload/HCPPatientUploadRequiredFieldMissing.csv");
		WebElement sendsubmit = driver.findElement(By.name("sendFile"));
		sendsubmit.submit();
		
		// Current page should contain message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		assertTrue(currentPage.getText().contains("File upload was unsuccessful"));
		assertTrue(currentPage.getText().contains("Required field \"email\" is missing"));
	}
	
	public void testHCPPatientUploadInvalidField() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());

		WebElement form = driver.findElement(By.name("patientFile"));
		form.sendKeys("testing-files/sample_patientupload/HCPPatientUploadInvalidField.csv");
		WebElement sendsubmit = driver.findElement(By.name("sendFile"));
		sendsubmit.submit();
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		assertTrue(currentPage.getText().contains("File upload was unsuccessful"));
		assertTrue(currentPage.getText().contains("Field \"invalidfield\" is invalid!"));
	}
	
	public void testHCPPatientUploadInvalidData() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());

		WebElement form = driver.findElement(By.name("patientFile"));
		form.sendKeys("testing-files/sample_patientupload/HCPPatientUploadInvalidData.csv");
		WebElement sendsubmit = driver.findElement(By.name("sendFile"));
		sendsubmit.submit();

		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertTrue(currentPage.getText().contains("File upload was successful, but some patients could not be added"));
		assertTrue(currentPage.getText().contains("Field number mismatch on line 3"));
		assertTrue(currentPage.getText().contains("Field number mismatch on line 4"));
		assertTrue(currentPage.getText().contains("Input validation failed for patient \"not,valid first\"!"));
		assertTrue(currentPage.getText().contains("Input validation failed for patient \"Not valid\"!"));
		assertTrue(currentPage.getText().contains("Correct"));
		assertTrue(currentPage.getText().contains("Number")); 
	}
	
	public void testHCPPatientUploadEmptyFile() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());

		WebElement form = driver.findElement(By.name("patientFile"));
		form.sendKeys("testing-files/sample_patientupload/HCPPatientUploadEmptyFile.csv");
		WebElement sendsubmit = driver.findElement(By.name("sendFile"));
		sendsubmit.submit();
		
		// No need to build a table. Just check if text exists on the current page
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertTrue(currentPage.getText().contains("File upload was unsuccessful"));
		assertTrue(currentPage.getText().contains("File is not valid CSV file"));
	}
	
	public void testHCPPatientUploadDuplicateField() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());

		WebElement form = driver.findElement(By.name("patientFile"));
		form.sendKeys("testing-files/sample_patientupload/HCPPatientUploadDuplicateField.csv");
		WebElement sendsubmit = driver.findElement(By.name("sendFile"));
		sendsubmit.submit();
		
		// No need to build a table. Just check if text exists on the current page
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertTrue(currentPage.getText().contains("File upload was unsuccessful"));
		assertTrue(currentPage.getText().contains("Duplicate field \"firstName\""));
	}
	
	public void testHCPPatientUploadBinaryData() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		driver.findElement(By.linkText("Upload Patient File")).click();
		assertEquals("iTrust - Upload Patient File", driver.getTitle());

		WebElement form = driver.findElement(By.name("patientFile"));
		form.sendKeys("testing-files/sample_patientupload/HCPPatientUploadBinaryData.doc");
		WebElement sendsubmit = driver.findElement(By.name("sendFile"));
		sendsubmit.submit();
		
		// No need to build a table. Just check if text exists on the current page
		
		// Current page should contain a message
		WebElement currentPage = driver.findElement(By.id("iTrustContent"));
		// System.out.println(currentPage.getText());
		
		assertTrue(currentPage.getText().contains("File upload was unsuccessful"));
	}
}
