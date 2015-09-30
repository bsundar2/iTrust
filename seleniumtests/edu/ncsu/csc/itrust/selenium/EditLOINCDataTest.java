package edu.ncsu.csc.itrust.selenium;
import edu.ncsu.csc.itrust.selenium.OSValidator; //Used for validating which OS the user is running on

import static org.junit.Assert.*;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class EditLOINCDataTest extends iTrustSeleniumTest {
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Override
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}

	@Test
	public void testAddLOINCFileNoIgnore() throws Exception {
		//set up for the start location and driver 
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		//make sure start in log-in page
		driver.get(baseUrl + "auth/forwardUser.jsp");
		try {
			assertEquals("iTrust - Login", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
		//log in processes
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//check that log in correctly
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Edit LOINC Codes")).click();
		//make sure it makes to Maintain LOINC code page
		assertEquals("iTrust - Maintain LOINC Codes", driver.getTitle());
		assertLogged(TransactionType.LOINC_CODE_VIEW, 9000000001L, 0L, "");
		//driver.findElement(By.name("import")).submit(); 
		driver.get(baseUrl + "/auth/admin/uploadLOINC.jsp");
		assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());

		driver.findElement(By.name("loincFile")).clear();
		
		//OS validator needed for the different OS classpaths
		OSValidator validator = new OSValidator();
		String myOS = validator.getOSType();
		//so the correct path is used based off of OS		
		switch(myOS) {
			case "Mac":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/sampleLoinc.txt");
			break;
			case "Windows":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files\\sample_loinc\\sampleLoinc.txt");
				break;
			case "Unix/Linux":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/sampleLoinc.txt");
				break;
			default:
				fail("OS not recognized.");
		}
		
		new Select(driver.findElement(By.name("ignoreDupData"))).selectByVisibleText("Replace Duplicates");
		driver.findElement(By.id("sendFile")).click();
		assertLogged(TransactionType.LOINC_CODE_FILE_ADD, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Return to LOINC Codes List")).click();
		try {
			assertEquals("iTrust - Maintain LOINC Codes", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
	}

	@Test
	public void testUploadBadLOINCFile() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get(baseUrl + "auth/forwardUser.jsp");
		try {
			assertEquals("iTrust - Login", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
		//log in processes
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//check that log in correctly
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Edit LOINC Codes")).click();

		assertEquals("iTrust - Maintain LOINC Codes", driver.getTitle());
		assertLogged(TransactionType.LOINC_CODE_VIEW, 9000000001L, 0L, "");
		//driver.findElement(By.id("import")).click();
		driver.get(baseUrl + "/auth/admin/uploadLOINC.jsp");
		assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());
		driver.findElement(By.name("loincFile")).clear();
		driver.findElement(By.name("loincFile")).sendKeys("testing-files\\sample_loinc\\badLoincFile.txt");
		driver.findElement(By.id("sendFile")).click();
		// Warning: verifyTextPresent may require manual changes
		try {
			assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*File invalid\\. No LOINC data added\\.[\\s\\S]*$"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
		driver.findElement(By.linkText("Return to LOINC Codes List")).click();
		try {
			assertEquals("iTrust - Maintain LOINC Codes", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
	}

	@Test
	public void testAddLOINCFile() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get(baseUrl + "auth/forwardUser.jsp");
		try {
			assertEquals("iTrust - Login", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
		//log in processes
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//check that log in correctly
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Edit LOINC Codes")).click();
		
		assertEquals("iTrust - Maintain LOINC Codes", driver.getTitle());
		assertLogged(TransactionType.LOINC_CODE_VIEW, 9000000001L, 0L, "");
		//driver.findElement(By.id("import")).click();
		driver.get(baseUrl + "/auth/admin/uploadLOINC.jsp");
		assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());
		driver.findElement(By.name("loincFile")).clear();
		
		//OS validator needed for the different OS classpaths
		OSValidator validator = new OSValidator();
		String myOS = validator.getOSType();
		//so the correct path is used based off of OS
		switch(myOS) {
			case "Mac":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/sampleLoinc.txt");
				break;
			case "Windows":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files\\sample_loinc\\sampleLoinc.txt");
				break;
			case "Unix/Linux":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/sampleLoinc.txt");
				break;
			default:
				fail("OS not recognized.");
		}
		
		driver.findElement(By.id("sendFile")).click();
		assertLogged(TransactionType.LOINC_CODE_FILE_ADD, 9000000001L, 0L, "");
		driver.findElement(By.linkText("Return to LOINC Codes List")).click();
		try {
			assertEquals("iTrust - Maintain LOINC Codes", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
	}

	@Test
	public void testUploadLOINCFileInvalidLines() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		driver.get(baseUrl + "auth/forwardUser.jsp");
		try {
			assertEquals("iTrust - Login", driver.getTitle());
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
		//log in processes
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		//check that log in correctly
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Edit LOINC Codes")).click();

		assertEquals("iTrust - Maintain LOINC Codes", driver.getTitle());
		assertLogged(TransactionType.LOINC_CODE_VIEW, 9000000001L, 0L, "");
		//driver.findElement(By.id("import")).click();
		driver.get(baseUrl + "/auth/admin/uploadLOINC.jsp");
		assertEquals("iTrust - Upload LOINC Codes", driver.getTitle());
		driver.findElement(By.name("loincFile")).clear();
		
		OSValidator validator = new OSValidator();
		String myOS = validator.getOSType();
		//so the correct path is used based off of OS
		switch(myOS) {
			case "Mac":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/invalidLine.txt");
				break;
			case "Windows":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files\\sample_loinc\\invalidLine.txt");
				break;
			case "Unix/Linux":
				driver.findElement(By.name("loincFile")).sendKeys("testing-files/sample_loinc/invalidLine.txt");
				break;
			default:
				fail("OS not recognized.");
		}
		
		driver.findElement(By.id("sendFile")).click();
		assertLogged(TransactionType.LOINC_CODE_FILE_ADD, 9000000001L, 0L, "");
		try {
			assertTrue(driver.findElement(By.cssSelector("BODY")).getText().matches("^[\\s\\S]*ERROR, LINE 2: \"10054-5\" \"I skip rest of fields\" This form has not been validated correctly\\. The following field are not properly filled in: \\[You must have a Lab Procedure Code, Component and Kind Of Property\\][\\s\\S]*$"));
		} catch (Error e) {
			verificationErrors.append(e.toString());
			fail();
		}
	}

}
