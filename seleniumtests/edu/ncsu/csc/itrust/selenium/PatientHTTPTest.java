package edu.ncsu.csc.itrust.selenium;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PatientHTTPTest extends iTrustSeleniumTest {
	private WebDriver driver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
	}
	
	public void testChangePassword() throws Exception {
		driver.get(ADDRESS + "/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.get(ADDRESS + "/logout.jsp");
		assertLogged(TransactionType.LOGOUT, 2L, 2L, "");
		assertEquals("iTrust - Login", driver.getTitle());
		
		driver.findElement(By.linkText("Reset Password")).click();
		new Select(driver.findElement(By.name("role"))).selectByVisibleText("Patient");
		driver.findElement(By.name("mid")).sendKeys("2");
		driver.findElement(By.xpath("//*[@id='iTrustContent']/form/table/tbody/tr[4]/td/input")).submit();

		driver.findElement(By.name("answer")).sendKeys("good");
		driver.findElement(By.name("password")).sendKeys("password2");
		driver.findElement(By.name("confirmPassword")).sendKeys("password2");
		driver.findElement(By.xpath("//*[@id='iTrustContent']/form/table/tbody/tr[5]/td/input")).submit();

		assertTrue(driver.getPageSource().contains("Password changed"));
		assertLogged(TransactionType.PASSWORD_RESET, 2L, 2L, "");
		
		driver = login("2", "pw");
		
		assertTrue(driver.getPageSource().contains("Failed login"));
		
		driver = login("2", "password2");
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		assertTrue(driver.getTitle().equals("iTrust - Patient Home"));
	}
	
	public void testViewPrescriptionRecords1() throws Exception {
		driver.get(ADDRESS + "/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
		
		
		driver.findElement(By.linkText("Prescription Records")).click();
		assertEquals("iTrust - Get My Prescription Report", driver.getTitle());
		driver.findElement(By.xpath("//*[@id='iTrustContent']/div/form/table/tbody/tr[1]/td[2]/input")).click();
		assertTrue(driver.getPageSource().contains("No prescriptions found"));
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 1L, 1L, "");
	}
	
	public void testViewPrescriptionRecords2() throws Exception {
		driver.get(ADDRESS + "/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		
		
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.findElement(By.linkText("Prescription Records")).click();
		assertEquals("iTrust - Get My Prescription Report", driver.getTitle());
		driver.findElement(By.xpath("//*[@id='iTrustContent']/div/form/table/tbody/tr[1]/td[2]/input")).click();
		assertLogged(TransactionType.PRESCRIPTION_REPORT_VIEW, 2L, 2L, "");
		
		assertEquals("64764-1512", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[3]/td[1]/a")).getText());
		assertEquals("Prioglitazone", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
		assertEquals("10/10/2006 to 10/11/2020", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[3]/td[3]")).getText());
		assertEquals("Kelly Doctor", driver.findElement(By.xpath("//*[@id='iTrustContent']/div/table/tbody/tr[3]/td[4]")).getText());
	}
	
	/*
	public void testCodeInjection() throws Exception {
		driver.get(ADDRESS + "/auth/forwardUser.jsp");
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("2");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertLogged(TransactionType.HOME_VIEW, 2L, 0L, "");
		
		driver.get(ADDRESS + "auth/patient/myDiagnoses.jsp?icd=%3Cscript%3Ewindow.location=%22http://bit.ly/4kb77v%22%3C/script%3E");
		//WebResponse wr = wc.getResponse(ADDRESS + "auth/patient/myDiagnoses.jsp?icd=%3Cscript%3Ewindow.location=%22http://bit.ly/4kb77v%22%3C/script%3E");
		assertFalse(driver.getPageSource().contains("RickRoll'D"));
		assertTrue(driver.getTitle().contains("iTrust - My Diagnoses"));
		assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 2L, 2L, "");
	}
	*/
}
