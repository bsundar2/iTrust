package edu.ncsu.csc.itrust.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

//import com.sun.org.apache.bcel.internal.generic.Select;


public class GetVisitRemindersTest {
	 private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @Before
	  public void setUp() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
	    baseUrl = "http://localhost:8080/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testGetVisitReminders_DiagnosedCareNeeders() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
		
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    assertEquals("iTrust - Visit Reminders", driver.getTitle());
	    new Select(driver.findElement(By.id("ReminderType"))).selectByVisibleText("Diagnosed Care Needers");
	    driver.findElement(By.id("getReminders")).click();
	    assertEquals("iTrust - Visit Reminders", driver.getTitle());
	    assertEquals("Zappic Clith", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td[2]")).getText());
	    assertEquals("919-555-9213", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Random Person", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[2]/tbody/tr[2]/td[2]")).getText());
	    assertEquals("919-971-0000", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[2]/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Anakin Skywalker", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[3]/tbody/tr[2]/td[2]")).getText());
	    assertEquals("919-419-5555", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[3]/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Darryl Thompson", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[4]/tbody/tr[2]/td[2]")).getText());
	    assertEquals("919-555-6709", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[4]/tbody/tr[3]/td[2]")).getText());
	  }
	  
	  @Test
	  public void testGetVisitReminders_TestInitialPage() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    assertEquals("iTrust - Visit Reminders", driver.getTitle());
	    new Select(driver.findElement(By.id("ReminderType"))).selectByVisibleText("Diagnosed Care Needers");
	    // ERROR: Caught exception [ERROR: Unsupported command [getSelectedLabel | id=ReminderType | ]]
	    new Select(driver.findElement(By.id("ReminderType"))).selectByVisibleText("Flu Shot Needers");
	    // ERROR: Caught exception [ERROR: Unsupported command [getSelectedLabel | id=ReminderType | ]]
	    new Select(driver.findElement(By.id("ReminderType"))).selectByVisibleText("Immunization Needers");
	    // ERROR: Caught exception [ERROR: Unsupported command [getSelectedLabel | id=ReminderType | ]]
	  }
	  
	  @Test
	  public void testGetVisitReminders_FluShotNeeders() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    new Select(driver.findElement(By.id("ReminderType"))).selectByVisibleText("Flu Shot Needers");
	    driver.findElement(By.id("getReminders")).click();
	    assertEquals("iTrust - Visit Reminders", driver.getTitle());
	    assertEquals("NoRecords Has", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[2]/td[2]")).getText());
	    assertEquals("919-971-0000", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Bad Horse", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[2]/tbody/tr[2]/td[2]")).getText());
	    assertEquals("919-123-4567", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[2]/tbody/tr[3]/td[2]")).getText());
	    assertEquals("Aaron Hotchner", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[3]/tbody/tr[2]/td[2]")).getText());
	    assertEquals("233-657-4532", driver.findElement(By.xpath("//div[@id='iTrustContent']/div/table[3]/tbody/tr[3]/td[2]")).getText());
	  }

	  @Test
	  public void testGetVisitReminders_ImmunizationNeeders() throws Exception {
		WebDriver driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080/iTrust/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(baseUrl + "auth/forwardUser.jsp");
			
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("9000000000");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[3]/div/h2")).click();
	    assertEquals("iTrust - HCP Home", driver.getTitle());
	    driver.findElement(By.linkText("Office Visit Reminders")).click();
	    assertEquals("iTrust - Visit Reminders", driver.getTitle());
	    new Select(driver.findElement(By.id("ReminderType"))).selectByVisibleText("Immunization Needers");
	    driver.findElement(By.id("getReminders")).click();
	    // ERROR: Caught exception [ERROR: Unsupported command [getTable | css=body > div.container-fluid | ]]
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
