package edu.ncsu.csc.itrust.selenium;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class ChangePasswordTest extends iTrustSeleniumTest{
	private WebDriver driver;

	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception{
		
		gen.clearAllTables();
		gen.standardData();
		// Create a new instance of the html unit driver
		driver = new HtmlUnitDriver();

		// Navigate to desired web page
		driver.get("http://localhost:8080/iTrust/");
	}
	
	@Test
	public void testChangePassword_Acceptance_Short() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

	    driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/changePassword.jsp']")).click();	
	    driver.findElement(By.name("oldPass")).clear();
	    driver.findElement(By.name("oldPass")).sendKeys("pw");
	    driver.findElement(By.name("newPass")).clear();
	    driver.findElement(By.name("newPass")).sendKeys("pass1");
	    driver.findElement(By.name("confirmPass")).clear();
	    driver.findElement(By.name("confirmPass")).sendKeys("pass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    Assert.assertTrue(driver.getPageSource().contains(
				"Password Changed"));
	    driver.findElement(By.cssSelector("a[href='/iTrust/logout.jsp']")).click();	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pass1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    gen.clearAllTables();
		gen.standardData();
	}
//	
	@Test
	public void testChangePassword_Acceptance_Long() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

	    driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/changePassword.jsp']")).click();	
	    driver.findElement(By.name("oldPass")).clear();
	    driver.findElement(By.name("oldPass")).sendKeys("pw");
	    driver.findElement(By.name("newPass")).clear();
	    driver.findElement(By.name("newPass")).sendKeys("pass12345abcde");
	    driver.findElement(By.name("confirmPass")).clear();
	    driver.findElement(By.name("confirmPass")).sendKeys("pass12345abcde");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    Assert.assertTrue(driver.getPageSource().contains(
				"Password Changed"));
	    driver.findElement(By.cssSelector("a[href='/iTrust/logout.jsp']")).click();	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pass12345abcde");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    gen.clearAllTables();
		gen.standardData();
    }
//	
	@Test
	public void testChangePassword_Invalid_Length() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

	    driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/changePassword.jsp']")).click();	
	    driver.findElement(By.name("oldPass")).clear();
	    driver.findElement(By.name("oldPass")).sendKeys("pw");
	    driver.findElement(By.name("newPass")).clear();
	    driver.findElement(By.name("newPass")).sendKeys("pas1");
	    driver.findElement(By.name("confirmPass")).clear();
	    driver.findElement(By.name("confirmPass")).sendKeys("pas1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    Assert.assertTrue(driver.getPageSource().contains(
				"Invalid password"));
	    driver.findElement(By.cssSelector("a[href='/iTrust/logout.jsp']")).click();	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pas1");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    gen.clearAllTables();
		gen.standardData();
	}
//	
	@Test
	public void testChangePassword_Invalid_Characters() throws Exception {
		String expectedTitle = "iTrust - Patient Home";

	    driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		driver.findElement(By.cssSelector("a[href='/iTrust/auth/changePassword.jsp']")).click();	
	    driver.findElement(By.name("oldPass")).clear();
	    driver.findElement(By.name("oldPass")).sendKeys("pw");
	    driver.findElement(By.name("newPass")).clear();
	    driver.findElement(By.name("newPass")).sendKeys("password");
	    driver.findElement(By.name("confirmPass")).clear();
	    driver.findElement(By.name("confirmPass")).sendKeys("password");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    Assert.assertTrue(driver.getPageSource().contains(
				"Invalid password"));
	    driver.findElement(By.cssSelector("a[href='/iTrust/logout.jsp']")).click();	
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("password");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    driver.findElement(By.id("j_username")).click();
	    driver.findElement(By.id("j_username")).clear();
	    driver.findElement(By.id("j_username")).sendKeys("1");
	    driver.findElement(By.id("j_password")).clear();
	    driver.findElement(By.id("j_password")).sendKeys("pw");
	    driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
	    gen.clearAllTables();
		gen.standardData();
	}
//	

//	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
}
