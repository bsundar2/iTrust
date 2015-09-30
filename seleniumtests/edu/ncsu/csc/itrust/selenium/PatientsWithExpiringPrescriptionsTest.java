package edu.ncsu.csc.itrust.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.junit.Test;

import edu.ncsu.csc.itrust.enums.TransactionType;
import static org.junit.Assert.*;

public class PatientsWithExpiringPrescriptionsTest extends iTrustSeleniumTest {
	
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.icd9cmCodes();
		gen.ndCodes();
		gen.hospitals();
		gen.hcp1();
		gen.hcp2();
		gen.hcp3();
		gen.patient9();
		gen.patient10();
		gen.patient11();
		gen.patient12();
		gen.patient13();
		gen.patient14();
	}
	
	@Test
	public void testPatient9() throws Exception {
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals(driver.getTitle(), "iTrust - HCP Home");
		//assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		
		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertTrue(driver.getPageSource().contains("Darryl"));
		assertTrue(driver.getPageSource().contains("Thompson"));
		assertTrue(driver.getPageSource().contains("a@b.com"));
		assertTrue(driver.getPageSource().contains("919-555-6709"));
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 99L, "");
	}
	
	/*
	 * An equivalence class test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires in 10 days)
	 */
	@Test
	public void testPatientTen() throws Exception {

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertFalse(driver.getPageSource().contains("Zappic Clith"));
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 10L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires in 7 days)
	 * Diagnosed with 493.99
	 */
	@Test
	public void testPatientEleven() throws Exception {

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertTrue(driver.getPageSource().contains("Marie"));
		assertTrue(driver.getPageSource().contains("Thompson"));
		assertTrue(driver.getPageSource().contains("e@f.com"));
		assertTrue(driver.getPageSource().contains("919-555-9213"));
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 11L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires in 8 days)
	 */
	@Test
	public void testPatientTwelve() throws Exception {

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertFalse(driver.getPageSource().contains("Blammo"));
		assertFalse(driver.getPageSource().contains("Volcano"));
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 12L, "");
	}
	
	/*
	 * An equivalence class test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, NOT special-diagnosis-history, prescription expires in 5 days)
	 */
	@Test
	public void testPatientThirteen() throws Exception {

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertFalse(driver.getPageSource().contains("Blim Cildron"));
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 13L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires today)
	 * Diagnosed with 459.99 (This is the closest possible to 460 because the table uses
	 *  decimal(5,2) )
	 */
	@Test
	public void testPatientFourteen() throws Exception {

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertTrue(driver.getPageSource().contains("Zack"));
		assertTrue(driver.getPageSource().contains("Arthur"));
		assertTrue(driver.getPageSource().contains("k@l.com"));
		assertTrue(driver.getPageSource().contains("919-555-1234"));	
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 14L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should NOT appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expired yesterday)
	 */
	@Test
	public void testPatientFifteen() throws Exception {

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertFalse(driver.getPageSource().contains("Malk"));
		assertFalse(driver.getPageSource().contains("Flober"));
		assertNotLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 15L, "");
	}
	
	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires today)
	 */
	@Test
	public void testPatientOrdering() throws Exception {

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9900000000", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9900000000L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		List<WebElement> tables = driver.findElementsByTagName("table");
		assertEquals(1, tables.size());
		WebElement table = tables.get(0);
		List<WebElement> ths = table.findElements(By.tagName("th"));
		assertEquals(1, ths.size());
		assertEquals("Tester Arehart", ths.get(0).getText());
		List<WebElement> trs = table.findElements(By.tagName("tr"));
		assertEquals(5, trs.size());
		assertEquals("Zack Arthur", trs.get(2).findElements(By.tagName("td")).get(0).getText());
		assertEquals("Darryl Thompson", trs.get(3).findElements(By.tagName("td")).get(0).getText());
		assertEquals("Marie Thompson", trs.get(4).findElements(By.tagName("td")).get(0).getText());

		assertTrue(driver.getPageSource().contains("Tester Arehart"));
		assertFalse(driver.getPageSource().contains("9900000000"));
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 99L, "");
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 11L, "");
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9900000000L, 14L, "");

	}
	
	/*
	 * A boundary-value test for a patient who should appear on the list.
	 * (Designated Tester Arehart, special-diagnosis-history, prescription expires today)
	 */
	@Test
	public void testAcceptance() throws Exception {
		gen.UC32Acceptance();

		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000003", "pw");
		assertEquals("iTrust - HCP Home", driver.getTitle());
		assertLogged(TransactionType.HOME_VIEW, 9000000003L, 0L, "");
		
		driver.findElement(By.linkText("Potential Prescription-Renewals")).click();
		
		List<WebElement> tables = driver.findElementsByTagName("table");
		assertEquals(1, tables.size());
		WebElement table = tables.get(0);
		List<WebElement> ths = table.findElements(By.tagName("th"));
		assertEquals(1, ths.size());
		assertEquals("Gandalf Stormcrow", ths.get(0).getText());
		List<WebElement> trs = table.findElements(By.tagName("tr"));
		assertEquals(4, trs.size());
		assertEquals("Andy Koopa", trs.get(2).findElements(By.tagName("td")).get(0).getText());
		assertEquals("David Prince", trs.get(3).findElements(By.tagName("td")).get(0).getText());

		
		assertTrue(driver.getPageSource().contains("Gandalf Stormcrow"));
		assertFalse(driver.getPageSource().contains("9000000003"));
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9000000003L, 16L, "");
		assertLogged(TransactionType.PRECONFIRM_PRESCRIPTION_RENEWAL, 9000000003L, 17L, "");
	}
	
}