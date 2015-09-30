package edu.ncsu.csc.itrust.beans;

/**
 * @version 1.0 3/20/15
 * initial creation
 * 
 * @author Jay Patel
 * @author Weijia Li
 * @author Yuang Ni
 * @author Balaji Sundaram
 * 
 * A bean for creating the body measurement object for a specific entry
 * 
 * A bean's purpose is to store data. Period. Little or no functionality is to be added to a bean 
 * (with the exception of minor formatting such as concatenating phone numbers together). 
 * A bean must only have Getters and Setters.
 *
 */

public class BodyMeasurementBean {
	private long patientID = 0;
	private String logDate;
	private double weight = 0;
	private double height = 0;
	private double waist = 0;
	private double arms = 0;
	
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
	 * @return the logDate
	 */
	public String getLogDate() {
		return logDate;
	}
	
	/**
	 * @param logDate the logDate to set
	 */
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}
	
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}
	
	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * @return the waist
	 */
	public double getWaist() {
		return waist;
	}
	/**
	 * @param waist the waist to set
	 */
	public void setWaist(double waist) {
		this.waist = waist;
	}
	/**
	 * @return the arms
	 */
	public double getArms() {
		return arms;
	}
	
	/**
	 * @param arms the arms to set
	 */
	public void setArms(double arms) {
		this.arms = arms;
	}
	

}
