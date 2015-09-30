package edu.ncsu.csc.itrust.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class PersonnelUseCaseTest extends iTrustSeleniumTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		driver = new HtmlUnitDriver();
		baseUrl = "http://localhost:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * testAddER
	 * 
	 * @throws Exception
	 */
	public void testAddER() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// click on Add ER
		driver.findElement(By.linkText("Add ER")).click();
		assertEquals("iTrust - Add ER", driver.getTitle());
	}

	/**
	 * testCreateER
	 * 
	 * @throws Exception
	 */
	public void testCreateER() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// click on Add ER
		driver.findElement(By.linkText("Add ER")).click();
		assertEquals("iTrust - Add ER", driver.getTitle());
		driver.findElement(By.name("firstName")).sendKeys("Nick");
		driver.findElement(By.name("lastName")).sendKeys("Oftime");
		driver.findElement(By.name("email")).sendKeys("nick@itrust.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		@SuppressWarnings("static-access")
		String newMID = driver.findElement(
				By.cssSelector("table").xpath("//tr[2]/td[2]")).getText();
		// Verify new emergency responder data is present
		assertLogged(TransactionType.ER_CREATE, 9000000001L,
				Long.parseLong(newMID), "");
	}

	/**
	 * testEditERDetails
	 * 
	 * @throws Exception
	 */
	public void testEditERDetails() throws Exception {
		driver.get(baseUrl + "/iTrust/auth/forwardUser.jsp");
		driver.findElement(By.id("j_username")).clear();
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).clear();
		driver.findElement(By.id("j_password")).sendKeys("pw");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");

		// click on Add ER
		driver.findElement(By.linkText("Add ER")).click();
		assertEquals("iTrust - Add ER", driver.getTitle());
		driver.findElement(By.name("firstName")).sendKeys("Nick");
		driver.findElement(By.name("lastName")).sendKeys("Oftime");
		driver.findElement(By.name("email")).sendKeys("nick@itrust.com");
		driver.findElement(By.cssSelector("input[type=\"submit\"]")).click();

		@SuppressWarnings("static-access")
		String newMID = driver.findElement(
				By.cssSelector("table").xpath("//tr[2]/td[2]")).getText();
		// Verify new emergency responder data is present
		assertLogged(TransactionType.ER_CREATE, 9000000001L,
				Long.parseLong(newMID), "");

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

		assertLogged(TransactionType.ER_EDIT, 9000000001L,
				Long.parseLong(newMID), "");
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
