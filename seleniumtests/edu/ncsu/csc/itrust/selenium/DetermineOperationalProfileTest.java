package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import edu.ncsu.csc.itrust.enums.TransactionType;

public class DetermineOperationalProfileTest extends iTrustSeleniumTest {
	

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.uap1();
		gen.tester();
	}


	/**
	 * Precondition: Sample data is in the database. CreatePatient2 has passed.
	 * Login with user 9999999999 and password pw.
	 */
	public void testDetermineOperationalProfile() throws Exception {
		// login as uap and add a patient
		WebDriver wd = login("8000000009", "uappass1");
		wd.get(ADDRESS + "auth/uap/home.jsp");
		assertEquals("iTrust - UAP Home", wd.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 8000000009L, 0L, "");
		
		wd.findElement(By.linkText("Add Patient")).click();
		WebElement firstName = wd.findElement(By.name("firstName"));
		WebElement lastName = wd.findElement(By.name("lastName"));
		WebElement email = wd.findElement(By.name("email"));
		
		firstName.sendKeys("bob");
		lastName.sendKeys("bob");
		email.sendKeys("bob@bob.com");
		
		firstName.submit();
		
		WebElement table1 = wd.findElement(By.tagName("table"));
		String newMID = table1.findElements(By.xpath("tbody/tr/td")).get(1).getText();

		assertLogged(TransactionType.PATIENT_CREATE, 8000000009L, Long.parseLong(newMID), "");
		
		List<WebElement> anchors = wd.findElements(By.tagName("a"));		
		anchors.get(2).click();
		assertLogged(TransactionType.LOGOUT, 8000000009L, 8000000009L, "");
		// login as tester to check the operational profile
		wd = login("9999999999", "pw");
		WebElement table = wd.findElement(By.tagName("table"));
		
		List<WebElement> row = table.findElements(By.tagName("tr")).get(8).findElements(By.xpath("td"));
		
		assertEquals("Create a patient", row.get(0).getText());
		assertEquals("1", row.get(1).getText()); // was 1
		assertEquals("17%", row.get(2).getText()); // was 17%
		assertEquals("1", row.get(3).getText()); // was 1
		assertEquals("20%", row.get(4).getText()); // was 20%
		assertEquals("0", row.get(5).getText()); // was 0
		assertEquals("0%", row.get(6).getText()); // was 0%
		
		List<WebElement> row1 = table.findElements(By.tagName("tr")).get(70).findElements(By.xpath("td"));
		
		assertEquals("Add Medical procedure code", row1.get(0).getText());
		assertEquals("0", row1.get(1).getText());
		assertEquals("0", row1.get(3).getText());
		assertEquals("0", row1.get(5).getText());
		assertLogged(TransactionType.OPERATIONAL_PROFILE_VIEW, 9999999999L, 0L, "");
	}
	
	public int getRowNumber(String description)
	{
		TransactionType[] values = TransactionType.values();
		int rownumber = 0;
		for (int i=0; i<values.length; i++)
		{
			if (description.equals(values[i].getDescription()))
				rownumber = i+1;
		}
		
		return rownumber;
	}
}
