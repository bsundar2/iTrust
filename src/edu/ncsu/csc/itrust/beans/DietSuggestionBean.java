package edu.ncsu.csc.itrust.beans;

public class DietSuggestionBean {
	private long patientID = 0;
	private String entryDate;
	private String suggestion = "";
	
	/**
	 * Returns the patient's ID.
	 * @return patient ID
	 */
	public long getPatientID() {
		return patientID;
	}

	
	/**
	 * Sets the patient's ID.
	 * @param patientID patient's ID
	 */
	public void setPatientID(long patientID) {
		this.patientID = patientID;
	}
	
	/**
	 * Returns the entry date of a food diary entry.
	 * @return entry date
	 */
	public String getEntryDate() {
		return entryDate;
	}

	/**
	 * Sets the entry date of a food diary entry.
	 * @param entryDate entry date of food diary entry
	 */
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
	
	/**
	 * Returns the suggestion for a patient's food diary entry.
	 * @return suggestion
	 */
	public String getSuggestion() {
		return suggestion;
	}

	/**
	 * Sets the suggestion for a patient's food diary entry.
	 * @param suggestion 
	 */
	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
}
