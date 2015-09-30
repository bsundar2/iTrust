package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;


public class CreateLTSpecTest extends iTrustSeleniumTest{
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
	public void testSpecialtyOnForm() throws Exception{
		String expectedTitle = "iTrust - Admin Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(expectedTitle, actualTitle);
		
		driver.findElement(By.cssSelector("h2.panel-title")).click();
	    driver.findElement(By.linkText("Add LT")).click();
	    driver.findElement(By.name("firstName")).clear();
	    driver.findElement(By.name("firstName")).sendKeys("New");
	    driver.findElement(By.name("lastName")).clear();
	    driver.findElement(By.name("lastName")).sendKeys("Person");
	    driver.findElement(By.name("email")).clear();
	    driver.findElement(By.name("email")).sendKeys("nperson@gmail.com");
	    new Select(driver.findElement(By.name("specialty"))).selectByVisibleText("General");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();	    
	    Assert.assertTrue(driver.getPageSource().contains(
				"New LT New Person successfully added!"));
	    Assert.assertTrue(driver.getPageSource().contains("500000"));
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
