package edu.ncsu.csc.itrust.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class ExperiecedLHCPsUseCaseTest extends iTrustSeleniumTest {
	private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Override
	  public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		//WebDriver driver = new HtmlUnitDriver();
	    //baseUrl = "http://localhost:8080/";
	    //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  
	  
	  //something wrong with 79.10
	  @Test
	  public void testViewDiagnoses() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Diagnoses")).click();
	    assertEquals("iTrust - My Diagnoses", driver.getTitle());
	    assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 1L, 1L, "");
	    assertEquals("Acute Lycanthropy(250.00)", driver.findElement(By.linkText("Acute Lycanthropy(250.00)")).getText());
	  }

	  @Test
	  public void testViewDiagnosisEchoVirus() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Diagnoses")).click();
	    assertEquals("iTrust - My Diagnoses", driver.getTitle());
	    assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 1L, 1L, "");
	    driver.findElement(By.linkText("Acute Lycanthropy(250.00)")).click();
	    assertEquals("HCPs having experience with diagnosis 250.00", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/h2")).getText());
	    //assertLogged(TransactionType.EXPERIENCED_LHCP_FIND, 1L, 0L, "");
	  }
	  
	  @Test
	  public void testViewHCPDetails() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    assertEquals("iTrust - Patient Home", driver.getTitle());
	    assertLogged(TransactionType.HOME_VIEW, 1L, 0L, "");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Diagnoses")).click();
	    assertEquals("iTrust - My Diagnoses", driver.getTitle());
	    assertLogged(TransactionType.DIAGNOSES_LIST_VIEW, 1L, 1L, "");
	    driver.findElement(By.linkText("Acute Lycanthropy(250.00)")).click();
	    assertEquals("HCPs having experience with diagnosis 250.00", driver.findElement(By.xpath("//div[@id='iTrustContent']/div[2]/h2")).getText());
	    //driver.findElement(By.linkText("Jason Frankenstein")).click();
	    //assertEquals("iTrust - View Personnel Details", driver.getTitle());
	    //assertEquals("Jason Frankenstein", driver.findElement(By.cssSelector("div > span")).getText());
	    //assertLogged(TransactionType.PERSONNEL_VIEW, 1L, 9000000004L, "");
	  }
	  
	  
	 /* @After
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }*/

}
