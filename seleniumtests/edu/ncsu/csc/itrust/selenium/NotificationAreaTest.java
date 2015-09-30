package edu.ncsu.csc.itrust.selenium;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.*;


/**
 * Use Case 34
 * Use Case 60
 */
@SuppressWarnings("unused")
public class NotificationAreaTest extends iTrustSeleniumTest {
	
	@Override
	protected void setUp() throws Exception{
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.uc60();
	}
	
	public void testPatientViewDeclaredProviderFromNotificationCenter () throws Exception {
		WebDriver driver = login("2", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("Gandalf Stormcrow"));
		assertTrue(pageText.contains("999-888-7777"));
		assertTrue(pageText.contains("gstormcrow@iTrust.org"));
		driver.quit();
	}

	public void testHCPTelemedicineDetailsFromNotificationCenter () throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("MM/dd/yyyy");
		//String tomorrow = formatter.format(new Date((new Date()).getTime() + 86400000));
		gen.appointmentCase3();
		gen.remoteMonitoring3();
		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		String pageText = driver.findElement(By.tagName("body")).getText();
		assertTrue(pageText.contains("3 physiological status reports"));
		assertTrue(pageText.contains("0 weight/pedometer status reports"));
		driver.quit();
	}
			
	public void testRepresenteeAppointmentDetailsFromNotificationCenter() throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat();
		formatter.applyPattern("MM/dd/yyyy");
		String tomorrow = formatter.format(new Date((new Date()).getTime() + 86400000));
		gen.appointmentCase3();
		WebDriver driver = login("1", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		driver.findElement(By.linkText(tomorrow)).click();
				
		assertTrue(driver.getPageSource().contains("Random Person"));
		assertTrue(driver.getPageSource().contains("10:30"));
		assertTrue(driver.getPageSource().contains("Kelly Doctor"));
		assertTrue(driver.getPageSource().contains("45 minutes"));		
		assertTrue(driver.getPageSource().contains("General Checkup after your knee surgery."));
		driver.quit();
	}
	
	public void testUnreadMessagesCount() throws Exception {

		WebDriver driver = login("9000000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		String pageText = driver.getPageSource();
		assertTrue(pageText.contains("12"));

		

	}
	
	public void testUnpaidBillsCount() throws Exception {
		WebDriver driver = login("311", "pw");
		assertEquals("iTrust - Patient Home", driver.getTitle());
		String pageText = driver.getPageSource();
		driver.findElement(By.linkText("1"));
		assertTrue(pageText.contains("1"));
		assertTrue(pageText.contains("new bill."));
	}
}
