package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class CreateUAPTest extends iTrustSeleniumTest{

	private WebDriver driver;

	private StringBuffer verificationErrors = new StringBuffer();
	
	
	@Before
	public void setUp() {
		// Create a new instance of the html unit driver
		driver = new HtmlUnitDriver();

		// Navigate to desired web page
		driver.get("http://localhost:8080/iTrust/");
	}
	
	@Test
	public void testCreateUAP1() throws Exception {
		String expectedTitle = "iTrust - HCP Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000000");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[6]/div/h2")).click();
	    driver.findElement(By.linkText("UAP")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("Drake");
	    driver.findElement(By.name("lastName")).clear();
	    driver.findElement(By.name("lastName")).sendKeys("Ramoray");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("drake@drake.com");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.linkText("Continue to personnel information.")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("Doctor");
	    driver.findElement(By.name("lastName")).clear();
	    driver.findElement(By.name("lastName")).sendKeys("Watson");
	    driver.findElement(By.name("streetAddress1")).clear();
	    driver.findElement(By.name("streetAddress1")).sendKeys("1234 Varsity Ln");
	    driver.findElement(By.name("streetAddress2")).clear();
	    driver.findElement(By.name("streetAddress2")).sendKeys("2nd Lane");
	    driver.findElement(By.name("city")).clear();
	    driver.findElement(By.name("city")).sendKeys("Cary");
	    new Select(driver.findElement(By.name("state"))).selectByVisibleText("North Carolina");
	    driver.findElement(By.name("zip")).clear();
	    driver.findElement(By.name("zip")).sendKeys("12345-1234");
	    driver.findElement(By.name("phone")).clear();
	    driver.findElement(By.name("phone")).sendKeys("704-100-1000");
	    driver.findElement(By.name("action")).click();
	    Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
	    //assertLogged(TransactionType.UAP_CREATE, 9000000000L, Long.parseLong(newMID), "");
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
