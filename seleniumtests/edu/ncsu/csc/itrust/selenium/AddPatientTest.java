package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.enums.TransactionType;


public class AddPatientTest extends iTrustSeleniumTest{
	
	protected WebDriver driver;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testValidPatient() throws Exception{
		//Login
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click the add patients link
		driver.findElement(By.linkText("Patient")).click();
		assertEquals("iTrust - Add Patient", driver.getTitle());

		//Enter in information
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("John");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("Doe");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("john.doe@example.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		assertTrue(driver.findElement(By.xpath("//body")).getText().contains("successfully added"));
		
		//Retrieve mid and pass
		String mid = driver.findElement(By.xpath("//table[1]//tr[2]/td[2]")).getText();
		String pass =  driver.findElement(By.xpath("//table[1]//tr[3]/td[2]")).getText();
		
		//Logout
		driver.close();
		//Login with new user and check if it worked
		driver = login(mid, pass);
		assertEquals("iTrust - Patient Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, Long.parseLong(mid), 0L, "");
	}
	
	public void testBlankPatientName() throws Exception{
		//Login
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click the add patients link
		driver.findElement(By.linkText("Patient")).click();
		assertEquals("iTrust - Add Patient", driver.getTitle());

		//Enter in information but blank first name
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("Doe");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("john.doe@example.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		assertTrue(driver.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		//Enter in information but blank last name
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("John");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("john.doe@example.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		assertTrue(driver.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
	}
	
	public void testInvalidPatientName() throws Exception{
		//Login
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click the add patients link
		driver.findElement(By.linkText("Patient")).click();
		assertEquals("iTrust - Add Patient", driver.getTitle());

		//Enter in information but invalid first name
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("----");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("Doe");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("john.doe@example.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		assertTrue(driver.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
		
		//Enter in information but invalid last name
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("John");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("----");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("john.doe@example.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		assertTrue(driver.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
	}
	
	public void testInvalidPatientEmail() throws Exception{
		//Login
		driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		
		//Click the add patients link
		driver.findElement(By.linkText("Patient")).click();
		assertEquals("iTrust - Add Patient", driver.getTitle());
	
		//Enter in information but invalid email
		driver.findElement(By.xpath("//input[@name='firstName']")).sendKeys("John");
		driver.findElement(By.xpath("//input[@name='lastName']")).sendKeys("Doe");
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("---@---.com");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		assertTrue(driver.findElement(By.xpath("//body")).getText().contains("This form has not been validated correctly."));
	}
}
