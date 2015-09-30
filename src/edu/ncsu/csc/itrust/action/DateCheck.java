package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * From the jsp this file gets passed the date in either
 * singular form or a range form. Depending on the input the checker will
 * see if the dates follow format and are correct
 * @author Team 19
 *
 */

public class DateCheck {
	
	public boolean checkSingle(String start){
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(start);
        }catch (ParseException e) {
        	return false;
        }
        if (!sdf.format(testDate).equals(start)) {
        	return false;
        }
        
        /**
         * Handles Date Wrong Time
         */
        Date now = new Date();
        int compare = testDate.compareTo(now);
        if(compare > 0){
        	return false;
        }
		
		return true;
	}
	
	public boolean checkRange(String start, String end){
		
		//Checks the start date
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(start);
        }
        catch (ParseException e) {
        	return false;
        }
        if (!sdf.format(testDate).equals(start)) {
        	return false;
        }
        
        /**
         * Handles Date Wrong Time
         */
        Date now = new Date();
        int compare = testDate.compareTo(now);
        if(compare > 0){
        	return false;
        }
        
        //Checks the end date
        testDate = null;
        try {
            testDate = sdf.parse(end);
        }
        catch (ParseException e) {
        	return false;
        }
        if (!sdf.format(testDate).equals(end)) {
        	return false;
        }
        
        /**
         * Handles Date Wrong Time
         */
        Date nowAlpha = new Date();
        int compareBeta = testDate.compareTo(nowAlpha);
        if(compareBeta > 0){
        	return false;
        }
		
		return true;
	}

}
