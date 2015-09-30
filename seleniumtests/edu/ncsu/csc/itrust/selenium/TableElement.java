package edu.ncsu.csc.itrust.selenium;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

/**
 * TableElement a helper class for Selenium test htmlunitdriver retrieving
 * data from tables.
 * @author Chi-Han
 *
 */
public class TableElement {
	WebElement tableElement;
	List<List<WebElement>> table;
	
	/**
	 * Constructor.
	 * This object will help user to get data from each cell of the table.
	 * @param tableElement The table WebElement.
	 */
	public TableElement(WebElement tableElement) {
		// TODO Auto-generated constructor stub
		this.tableElement = tableElement;
		table = new ArrayList<List<WebElement>>();
		List<WebElement> trCollection = tableElement.findElements(By.xpath("tbody/tr"));
		for(WebElement trElement : trCollection){
			List<WebElement> tdCollection = trElement.findElements(By.xpath("td"));
			table.add(tdCollection);
		}
		
	}
	/**
	 * Get data from given row and column cell.
	 * @param row (start from 0)
	 * @param column(start from 0)
	 * @return The WebElement in that given cell.
	 */
	public WebElement getTableCell(int row, int column){
		return table.get(row).get(column);
	}
	
	/**
	 * Get data from given row and column cell.
	 * @param row (start from 0)
	 * @param column(start from 0)
	 * @return The WebElement in that given cell.
	 */
	public String getCellAsText(int row, int column){
		return table.get(row).get(column).getText();
	}
	
	public int getRowSize(){
		 return tableElement.findElements(By.xpath("tbody/tr")).size();
	}
	
	public int getColumnSize(){
		return tableElement.findElements(By.xpath("tbody/tr")).get(0).findElements(By.xpath("td")).size();
	}

}
