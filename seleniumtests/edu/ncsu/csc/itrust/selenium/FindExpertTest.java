package edu.ncsu.csc.itrust.selenium;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import edu.ncsu.csc.itrust.DBBuilder;
import edu.ncsu.csc.itrust.enums.TransactionType;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;


/**
 * Selenium test conversion for HttpUnit FindExpertTest 
 * @author Team 08
 */
@SuppressWarnings("unused")
public class FindExpertTest extends iTrustSeleniumTest {

	private HtmlUnitDriver driver; 
	
	@Before
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();	
		gen.uc47SetUp();
		driver = new HtmlUnitDriver();
		driver.get("http://localhost:8080/iTrust/");
	}

	/*
	 * Test ability to edit and locate personnel based on specialty
	 */
	public void testEditAndFindExpert() throws Exception {

		WebElement element;
		
		driver.findElement(By.id("j_username")).sendKeys("9000000001");
		driver.findElement(By.id("j_password")).sendKeys("pw");
	
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - Admin Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000001L, 0L, "");
		
		driver.findElement(By.id("edit-menu")).click();
		driver.navigate().to("http://localhost:8080/iTrust/auth/getPersonnelID.jsp?forward=staff/editPersonnel.jsp");
		
		assertEquals("iTrust - Please Select a Personnel", driver.getTitle());
		
		element = driver.findElement(By.name("FIRST_NAME"));
		element.sendKeys("Kelly");
		element = driver.findElement(By.name("LAST_NAME"));
		element.sendKeys("Doctor");
	
		driver.findElement(By.id("userSearchForm")).submit();
		driver.findElement(By.cssSelector("input[value='9000000000']")).submit();
		
		assertEquals("iTrust - Edit Personnel", driver.getTitle());
		element = driver.findElement(By.name("phone"));
		element.clear(); //NOTE: Be sure to call clear() when editing fields
		element.sendKeys("919-100-1000");
		
		driver.findElement(By.cssSelector("input[value='Edit Personnel Record']")).submit();
		String output = driver.findElement(By.cssSelector("span[class='iTrustMessage']")).getText();
		assertEquals("Information Successfully Updated", output);
		assertLogged(TransactionType.LHCP_EDIT, 9000000001L, 9000000000L, "");
		
		driver.findElement(By.cssSelector("a[href = '/iTrust/logout.jsp']")).click();
		assertEquals("iTrust - Login", driver.getTitle());
		
		driver.findElement(By.id("j_username")).sendKeys("1");
		driver.findElement(By.id("j_password")).sendKeys("pw");
		
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		assertEquals("iTrust - Patient Home", driver.getTitle());
		
		driver.findElement(By.cssSelector("a[href= '/iTrust/auth/patient/findExpert.jsp']")).click();
		assertEquals("iTrust - Find an Expert", driver.getTitle());
		
		Select dropDownSpec = new Select(driver.findElement(By.name("specialty")));
		dropDownSpec.selectByVisibleText("Surgeon");
		
		element = driver.findElement(By.name("findExpert"));
		element.click();
		assertEquals("iTrust - Find an Expert", driver.getTitle());
		
		element = driver.findElement(By.id("viewPhysician"));		
		assertTrue(element.getText().contains("Kelly Doctor"));
	}
	
	@After
	public void tearDown() throws Exception {
		gen.uc47TearDown();
		driver.quit();
	}

}
