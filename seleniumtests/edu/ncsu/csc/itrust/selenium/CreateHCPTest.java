package edu.ncsu.csc.itrust.selenium;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class CreateHCPTest extends iTrustSeleniumTest{
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
	public void testCreateValidHCP() throws Exception{
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
		driver.findElement(By.name("firstName")).sendKeys("Laurie");
		driver.findElement(By.name("lastName")).sendKeys("Williams");
		driver.findElement(By.name("email")).sendKeys("laurie@ncsu.edu");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		driver.findElement(By.linkText("Continue to personnel information."))
				.click();
		driver.findElement(By.name("streetAddress1")).sendKeys(
				"900 Main Campus Dr");
		driver.findElement(By.name("streetAddress2")).sendKeys("Box 2509");
		driver.findElement(By.name("city")).sendKeys("Raleigh");
		new Select(driver.findElement(By.name("state")))
				.selectByVisibleText("North Carolina");
		driver.findElement(By.name("zip")).sendKeys("27606-1234");
		driver.findElement(By.name("phone")).sendKeys("919-100-1000");
		driver.findElement(By.name("action")).click();

		Assert.assertTrue(driver.getPageSource().contains(
				"Information Successfully Updated"));
	}

	@Test
	public void testEditValidPersonnel() throws Exception{
		String expectedTitle = "iTrust - Admin Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);

		driver.findElement(
				By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
		driver.findElement(By.linkText("Edit Personnel")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Personnel");
		driver.findElement(By.name("FIRST_NAME")).clear();
		driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
		driver.findElement(By.name("LAST_NAME")).clear();
		driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
		driver.findElement(By.xpath("//input[@value='User Search']")).click();
		driver.findElement(By.xpath("(//input[@value='9000000000'])[2]"))
				.click();
		driver.findElement(By.name("city")).clear();
		driver.findElement(By.name("city")).sendKeys("Brooklyn");
		driver.findElement(By.name("action")).click();
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div"))
				.click();
		driver.findElement(By.linkText("Edit Personnel")).click();
		assertEquals(driver.getTitle(), "iTrust - Please Select a Personnel");
	}

	@Test
	public void testEditHospitalAssignments() throws Exception {
		String expectedTitle = "iTrust - Admin Home";

		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		// get the title of the page
		String actualTitle = driver.getTitle();
		// verify title
		assertEquals(actualTitle, expectedTitle);
		
		driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("Edit HCP Assignment to Hospital")).click();	    
	    assertEquals("iTrust - Please Select a Personnel", driver.getTitle());	    
	    driver.findElement(By.name("FIRST_NAME")).clear();
	    driver.findElement(By.name("FIRST_NAME")).sendKeys("Kelly");
	    driver.findElement(By.name("LAST_NAME")).clear();
	    driver.findElement(By.name("LAST_NAME")).sendKeys("Doctor");
	    driver.findElement(By.xpath("//input[@value='User Search']")).click();
	    driver.findElement(By.xpath("(//input[@value='9000000000'])[2]")).click();
	    assertEquals("iTrust - Hospital Staffing Assignments", driver.getTitle());
	    
	    driver.findElement(By.linkText("Assign")).click();
	    Assert.assertTrue(driver.getPageSource().contains(
				"HCP has been assigned"));

	    driver.findElement(By.linkText("Unassign")).click();
	    Assert.assertTrue(driver.getPageSource().contains(
				"HCP has been unassigned"));

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
