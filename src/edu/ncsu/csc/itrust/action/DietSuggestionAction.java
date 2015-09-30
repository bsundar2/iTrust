package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.DietSuggestionBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.DietSuggestionDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;

public class DietSuggestionAction {
	private DietSuggestionDAO suggestionDAO;
	long patient;
	
	public DietSuggestionAction(DAOFactory factory, long loggedInMID) {
		suggestionDAO = factory.getDietSuggestionDAO();
		patient = loggedInMID;
	}
	
	/**
	 * Returns a list of all suggestions for a particular patient.
	 * @return list of suggestions
	 * @throws DBException
	 */
	public List<DietSuggestionBean> getSuggestion() throws DBException {
		return suggestionDAO.getSuggestionByMID(patient);
	}
	
	/** 
	 * Returns a specific bean corresponding to a patient's food diary entry.
	 * @param date date of food diary entry
	 * @return diet suggestion bean
	 * @throws DBException 
	 * @throws ParseException
	 */
	public DietSuggestionBean getSuggestionBean(String date) throws DBException, ParseException {
		return suggestionDAO.getSuggestionByDate(patient, date);
	}
	
	/**
	 * Adds a suggestion to a specific patient's food diary entry.
	 * @param suggestionBean the bean containing the suggestion.
	 * @return true if it was able to add it.
	 * @throws DBException
	 * @throws FormValidationException
	 */
	public boolean addSuggestion(DietSuggestionBean suggestionBean) throws DBException, FormValidationException {
		/**
		 * Handles Date incorrect format
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(suggestionBean.getEntryDate());
        }
        catch (ParseException e) {
        	throw new FormValidationException("Enter Consumption Date in MM/dd/yyyy");
        }
        if (!sdf.format(testDate).equals(suggestionBean.getEntryDate())) {
        	throw new FormValidationException("Enter Consumption Date in MM/dd/yyyy");
        }
        
        /**
         * Handles Date Wrong Time
         */
        Date now = new Date();
        int compare = testDate.compareTo(now);
        if(compare > 0){
        	throw new FormValidationException("Consumption Date: Restricted to Current or Past Dates");
        } 
        
		try {
			return suggestionDAO.addSuggestion(suggestionBean);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}	
