package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import edu.ncsu.csc.itrust.enums.TransactionType;

/**
 * Use Case 28
 */
public class ViewPatientsUseCaseTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		HttpUnitOptions.setScriptingEnabled(false);
		gen.clearAllTables();
		gen.standardData();		
	}

	/*
	 * Precondition:
	 * LHCP 9000000000 and Patients 1-4 are in the database 
	 * Office Visits 11, 902-911, 111, and 1 are in the database. 
	 * LHCP 9000000000 has authenticated successfully.
	 * Description:
	 * 1. LHCP clicks on "View All Patients" link.
	 * Expected Results:
	 * A list of the following should be displayed:
	 * Andy Programmer, 344 Bob Street Raleigh NC 27607, 2007-06-10.
	 * Care needs, 1247 Noname Dr Suite 106 Raleigh NC 27606, 2005-10-10.
	 * Random Person, 1247 Noname Dr Suite 106 Raleigh NC 27606, 2005-10-10.
	 */
	/**
	 * testViewLHCPPatients
	 * @throws Exception
	 */
	public void testViewLHCPPatients() throws Exception{
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000000L, 0L, "");
		driver.findElement(By.cssSelector("div.panel-heading")).click();
	    driver.findElement(By.linkText("All Patients")).click();
		assertEquals("iTrust - View All Patients", driver.getTitle());
		
		//System.out.println(driver.getPageSource());
		WebElement tableElement = driver.findElement(By.id("patientList"));
		TableElement table = new TableElement(tableElement);
		assertEquals("09/14/2009", table.getTableCell(1, 2).getText());
		assertEquals("10/10/2005", table.getTableCell(2, 2).getText());
		assertEquals("",           table.getTableCell(3, 2).getText());
		assertEquals("06/07/2007", table.getTableCell(4, 2).getText());
		assertEquals("07/10/2004", table.getTableCell(5, 2).getText());
		assertEquals("05/10/2006", table.getTableCell(6, 2).getText());
		assertEquals("05/10/1999", table.getTableCell(7, 2).getText());
		assertEquals("06/10/2007", table.getTableCell(8, 2).getText());
		assertEquals("06/09/2007", table.getTableCell(9, 2).getText());
		assertEquals("", table.getTableCell(10, 2).getText());
		assertEquals("344 Bob Street Raleigh NC 27607", table.getTableCell(1, 1).getText());
		driver.findElement(By.linkText("Andy Programmer")).click();
		assertLogged(TransactionType.PATIENT_LIST_VIEW, 9000000000L, 0L, "");
		assertEquals("iTrust - Edit Personal Health Record", driver.getTitle());
		
		
		
	}
}
