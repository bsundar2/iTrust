package edu.ncsu.csc.itrust.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author Jay Patel
 * @author Evin Clapper
 * 
 * A Bean for storing data about an specific food diary entry
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters.
 *
 */


@SuppressWarnings("unused")
public class FoodDiaryBean {
	private long patientID = 0;
	private String entryDate;// = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
	private String meal = "";
	private String food = "";
	private double servings = 0;
	private double cals = 0;
	private double fat = 0;
	private double sodium = 0;
	private double carbs = 0;
	private double fiber = 0;
	private double sugar = 0;
	private double protein = 0;
	/**
	 * @return the patientID
	 */
	public long getPatientID() {
		return patientID;
	}
	/**
	 * @param patientID the patientID to set
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	/**
	 * @return the entryDate
	 */
	public String getEntryDate() {
		return entryDate;
	}
	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	/**
	 * @return the meal
	 */
	public String getMeal() {
		return meal;
	}
	/**
	 * @param meal the meal to set
	 */
	public void setMeal(String meal) {
		this.meal = meal;
	}
	/**
	 * @return the food
	 */
	public String getFood() {
		return food;
	}
	/**
	 * @param food the food to set
	 */
	public void setFood(String food) {
		this.food = food;
	}
	/**
	 * @return the servings
	 */
	public double getServings() {
		return servings;
	}
	/**
	 * @param servings the servings to set
	 */
	public void setServings(double servings) {
		this.servings = servings;
	}
	/**
	 * @return the cals
	 */
	public double getCals() {
		return cals;
	}
	/**
	 * @param cals the cals to set
	 */
	public void setCals(double cals) {
		this.cals = cals;
	}
	/**
	 * @return the fat
	 */
	public double getFat() {
		return fat;
	}
	/**
	 * @param fat the fat to set
	 */
	public void setFat(double fat) {
		this.fat = fat;
	}
	/**
	 * @return the sodium
	 */
	public double getSodium() {
		return sodium;
	}
	/**
	 * @param sodium the sodium to set
	 */
	public void setSodium(double sodium) {
		this.sodium = sodium;
	}
	/**
	 * @return the carbs
	 */
	public double getCarbs() {
		return carbs;
	}
	/**
	 * @param carbs the carbs to set
	 */
	public void setCarbs(double carbs) {
		this.carbs = carbs;
	}
	/**
	 * @return the fiber
	 */
	public double getFiber() {
		return fiber;
	}
	/**
	 * @param fiber the fiber to set
	 */
	public void setFiber(double fiber) {
		this.fiber = fiber;
	}
	/**
	 * @return the sugar
	 */
	public double getSugar() {
		return sugar;
	}
	/**
	 * @param sugar the sugar to set
	 */
	public void setSugar(double sugar) {
		this.sugar = sugar;
	}
	/**
	 * @return the protein
	 */
	public double getProtein() {
		return protein;
	}
	/**
	 * @param protein the protein to set
	 */
	public void setProtein(double protein) {
		this.protein = protein;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cals);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(carbs);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((entryDate == null) ? 0 : entryDate.hashCode());
		temp = Double.doubleToLongBits(fat);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(fiber);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((food == null) ? 0 : food.hashCode());
		result = prime * result + ((meal == null) ? 0 : meal.hashCode());
		result = prime * result + (int) (patientID ^ (patientID >>> 32));
		temp = Double.doubleToLongBits(protein);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(servings);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sodium);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(sugar);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FoodDiaryBean other = (FoodDiaryBean) obj;
		if (Double.doubleToLongBits(cals) != Double
				.doubleToLongBits(other.cals))
			return false;
		if (Double.doubleToLongBits(carbs) != Double
				.doubleToLongBits(other.carbs))
			return false;
		if (entryDate == null) {
			if (other.entryDate != null)
				return false;
		} else if (!entryDate.equals(other.entryDate))
			return false;
		if (Double.doubleToLongBits(fat) != Double.doubleToLongBits(other.fat))
			return false;
		if (Double.doubleToLongBits(fiber) != Double
				.doubleToLongBits(other.fiber))
			return false;
		if (food == null) {
			if (other.food != null)
				return false;
		} else if (!food.equals(other.food))
			return false;
		if (meal == null) {
			if (other.meal != null)
				return false;
		} else if (!meal.equals(other.meal))
			return false;
		if (patientID != other.patientID)
			return false;
		if (Double.doubleToLongBits(protein) != Double
				.doubleToLongBits(other.protein))
			return false;
		if (Double.doubleToLongBits(servings) != Double
				.doubleToLongBits(other.servings))
			return false;
		if (Double.doubleToLongBits(sodium) != Double
				.doubleToLongBits(other.sodium))
			return false;
		if (Double.doubleToLongBits(sugar) != Double
				.doubleToLongBits(other.sugar))
			return false;
		return true;
	}
	
	

}
