package edu.ncsu.csc.itrust.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


/**
 * Test class for the viewVisitedHCPs.jsp
 */
public class ViewMyProvidersTest extends iTrustSeleniumTest {

	@Override
	protected void setUp() throws Exception {
		super.setUp(); // clear tables is called in super
		gen.clearAllTables();
		gen.standardData();
	}

	/**
	 * testViewMyProviders1
	 * @throws Exception
	 */
	public void testViewMyProviders1() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("1","pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		TableElement table = new TableElement(driver.findElement(By.id("hcp_table")));
		assertEquals(4, table.getRowSize());
		assertEquals("Gandalf Stormcrow", table.getCellAsText(1, 0));
		assertEquals("Kelly Doctor", table.getCellAsText(2, 0));
	    driver.findElement(By.name("filter_name")).clear();
	    driver.findElement(By.name("filter_name")).sendKeys("Doctor");
	    driver.findElement(By.name("update_filter")).click();
	    assertEquals("iTrust - My Providers", driver.getTitle());
	    table = new TableElement(driver.findElement(By.id("hcp_table")));
	    // Only Kelly Doctor should be listed now.
	 	assertEquals(3, table.getRowSize());
	 	assertEquals("Kelly Doctor", table.getCellAsText(1, 0));
		// Gandalf Stormcrow is no longer listed.
	 	assertFalse("Gandalf Stormcrow".equals(table.getCellAsText(2, 0)));
	}
	
	/**
	 * testViewMyProviders2
	 * @throws Exception
	 */
	public void testViewMyProviders2() throws Exception {
		HtmlUnitDriver driver = (HtmlUnitDriver)login("1","pw");
	    driver.findElement(By.xpath("//div[@id='iTrustMenu']/div/div[2]/div/h2")).click();
	    driver.findElement(By.linkText("My Providers")).click();
		assertEquals("iTrust - My Providers", driver.getTitle());
		TableElement table = new TableElement(driver.findElement(By.id("hcp_table")));
		assertEquals(4, table.getRowSize());
		assertEquals("Gandalf Stormcrow", table.getCellAsText(1, 0));
		assertEquals("Kelly Doctor", table.getCellAsText(2, 0));
		
	    driver.findElement(By.name("filter_specialty")).clear();
	    driver.findElement(By.name("filter_specialty")).sendKeys("surgeon");
	    driver.findElement(By.name("update_filter")).click();
	    assertEquals("iTrust - My Providers", driver.getTitle());
	    table = new TableElement(driver.findElement(By.id("hcp_table")));
	    // Only Kelly Doctor should be listed now.
	 	assertEquals(3, table.getRowSize());
	 	assertEquals("Kelly Doctor", table.getCellAsText(1, 0));
		// Gandalf Stormcrow is no longer listed.
	 	assertFalse("Gandalf Stormcrow".equals(table.getCellAsText(2, 0)));
	}

}
