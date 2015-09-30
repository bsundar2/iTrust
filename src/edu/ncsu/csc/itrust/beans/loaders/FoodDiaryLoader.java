package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.FoodDiaryBean;

/**
 * A loader for FoodDiaryBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class FoodDiaryLoader implements BeanLoader<FoodDiaryBean> {
	
	public List<FoodDiaryBean> loadList(ResultSet rs) throws SQLException {
		List<FoodDiaryBean> list = new ArrayList<FoodDiaryBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
	

	/**
	 * @param rs ResultSet the result set of FoodDiary database information
	 * Used iteratively by other classes to load the result information into a list
	 * once the information from the result set is loaded it is sent to be displayed
	 */
	public FoodDiaryBean loadSingle(ResultSet rs) throws SQLException {
		FoodDiaryBean p = new FoodDiaryBean();
		p.setPatientID(rs.getLong("MID"));
		p.setEntryDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("ldate").getTime())));;
		p.setMeal(rs.getString("meal"));
		p.setFood(rs.getString("food"));
		p.setServings(rs.getDouble("servings"));
		p.setCals(rs.getDouble("cals"));
		p.setFat(rs.getDouble("fat"));
		p.setSodium(rs.getDouble("sodium"));
		p.setCarbs(rs.getDouble("carbs"));
		p.setFiber(rs.getDouble("fiber"));
		p.setSugar(rs.getDouble("sugar"));
		p.setProtein(rs.getDouble("protein"));
		return p;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, FoodDiaryBean p) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}
}
