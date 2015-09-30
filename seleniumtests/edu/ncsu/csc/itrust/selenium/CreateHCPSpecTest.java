package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateHCPSpecTest extends iTrustSeleniumTest{

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
	public void testSpecialtyOnForm() throws Exception {
		String expectedTitle = "iTrust - Admin Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);

		driver.findElement(By.cssSelector("div.panel-heading")).click();
		driver.findElement(By.linkText("Add HCP")).click();
		driver.findElement(By.name("firstName")).sendKeys("Firstname");
		driver.findElement(By.name("lastName")).sendKeys("Lastname");
		driver.findElement(By.name("email")).sendKeys("abcdef@ncsu.edu");
		new Select(driver.findElement(By.name("specialty")))
				.selectByVisibleText("Pediatrician");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		// Assert.assertTrue(driver.getPageSource().contains("Information Successfully Updated"));

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
