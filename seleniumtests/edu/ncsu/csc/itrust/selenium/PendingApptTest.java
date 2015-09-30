package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

public class PendingApptTest extends iTrustSeleniumTest {
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentAlert();
	}
	
	@Test
	public void testPendingAppointmentAlert () throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		assertTrue(driver.getPageSource().contains("Appointment requests."));
	}
	
	@Test
	public void testAcceptAnAppointment() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentAlert();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		driver.findElement(By.linkText("Appointment Requests")).click();
		driver.findElement(By.linkText("Approve")).click();
		driver.findElement(By.xpath("//a[text() = 'Home']")).click();
		
		assertFalse(driver.getPageSource().contains("Appointment requests."));
	}
	
	@Test
	public void testConflictingAppt() throws Exception {
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentConflict();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		driver.findElement(By.linkText("Appointment Requests")).click();
		driver.findElement(By.linkText("Approve")).click();
		driver.findElement(By.xpath("//a[text() = 'Home']")).click();
		driver.findElement(By.linkText("1"));
		try {
			driver.findElement(By.linkText("2"));
			fail("Expected NoSuchElementException.");
		} catch (NoSuchElementException nsee) {
			assertTrue(true); // Expected exception
		}
	}
	
	@Test
	public void testDeclineAnAppointment() throws Exception{
		gen.clearAllTables();
		gen.standardData();
		gen.pendingAppointmentAlert();
		
		HtmlUnitDriver driver = (HtmlUnitDriver)login("9000000000", "pw");
		driver.findElement(By.linkText("Appointment Requests")).click();
		driver.findElement(By.linkText("Reject")).click();
		driver.findElement(By.xpath("//a[text() = 'Home']")).click();
		
		assertTrue(driver.getPageSource().contains("No appointment requests."));
	}
}

