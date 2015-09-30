package edu.ncsu.csc.itrust.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.dao.mysql.FoodDiaryDAO;
import edu.ncsu.csc.itrust.exception.DBException;
import edu.ncsu.csc.itrust.exception.FormValidationException;

/**
 * 
 * @author Jay Patel
 * @author Evin Clapper
 * 
 * This is the action file for the Food Diary what it is designed to do is work with the
 * two functions it is designed for, either viewing the entire food log by either the 
 * patient or the HCP or this is designed to add an single entry to the food log
 *
 */
public class FoodDiaryAction {
	private FoodDiaryDAO foodDiaryDAO;
	long patient;
	
	public FoodDiaryAction(DAOFactory factory, long loggedInMID) {
		foodDiaryDAO = factory.getFoodDiaryDAO();
		patient = loggedInMID;
	}
	
	public List<FoodDiaryBean> getFoodDiary() throws DBException {
		return foodDiaryDAO.getFoodDiaryByMID(patient);
	}
	
	public boolean deleteFoodDiaryEntry(FoodDiaryBean fooddiary) throws DBException {
		
		//NEED TO ADD SAME CHECKERS AS ADD FOOD DIARY
		
		try {
			return foodDiaryDAO.deleteFoodDiaryEntry(fooddiary);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean addFoodDiary(FoodDiaryBean fooddiary) throws DBException, FormValidationException {
		
		/**
		 * Handles Date incorrect format
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date testDate = null;
        try {
            testDate = sdf.parse(fooddiary.getEntryDate());
        }
        catch (ParseException e) {
        	throw new FormValidationException("Enter Consumption Date in MM/dd/yyyy");
        }
        if (!sdf.format(testDate).equals(fooddiary.getEntryDate())) {
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
        
        /**
         * Handles that meal is one of four
         */
        String meal = fooddiary.getMeal();
        if(!meal.equalsIgnoreCase("breakfast") && !meal.equalsIgnoreCase("lunch") && !meal.equalsIgnoreCase("dinner") && !meal.equalsIgnoreCase("snack")){
        	throw new FormValidationException("Meal: Restricted to Breakfast, Lunch, Dinner, or Snack");
        }
        
        /**
         * Handles food input
         */
        if(fooddiary.getFood() == null|| fooddiary.getFood().equals("") || fooddiary.getFood().trim().isEmpty()){
        	throw new FormValidationException("Food Name: Cannot Leave Blank");
        }
        
        /**
         * Handles servings needing to be positive
         */
        if(fooddiary.getServings() <= 0){
        	throw new FormValidationException("Servings: Must be Greater than 0");
        }
        
        /**
         * Handles calories
         */
        if(fooddiary.getCals()<0){
        	throw new FormValidationException("Calories Per Servings: Must be 0 or Greater");
        }
        
        /**
         * Handles Fat
         */
        if(fooddiary.getFat()<0){
        	throw new FormValidationException("Grams Fat Per Servings: Must be 0 or Greater");
        }
        
        /**
         * Handles Sodium
         */
        if(fooddiary.getSodium()<0){
        	throw new FormValidationException("mg Sodium Per Servings: Must be 0 or Greater");
        }
        
        /**
         * Handles Carbs
         */
        if(fooddiary.getCarbs()<0){
        	throw new FormValidationException("Grams Carbs Per Servings: Must be 0 or Greater");
        }
        
        /**
         * Handles Fiber
         */
        if(fooddiary.getFiber()<0){
        	throw new FormValidationException("Grams Fiber Per Servings: Must be 0 or Greater");
        }
        
        /**
         * Handles Sugar
         */
        if(fooddiary.getSugar()<0){
        	throw new FormValidationException("Grams Sugar Per Servings: Must be 0 or Greater");
        }
        
        /**
         * Handles Protein
         */
        if(fooddiary.getProtein()<0){
        	throw new FormValidationException("Grams Protein Per Servings: Must be 0 or Greater");
        }
        
		try {
			return foodDiaryDAO.addFoodDiary(fooddiary);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}