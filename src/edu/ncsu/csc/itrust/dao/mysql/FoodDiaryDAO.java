package edu.ncsu.csc.itrust.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.DBUtil;
import edu.ncsu.csc.itrust.beans.FoodDiaryBean; //My Food diary beans
import edu.ncsu.csc.itrust.beans.loaders.FoodDiaryLoader; // My food loader
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class FoodDiaryDAO {
	private DAOFactory factory;
	private FoodDiaryLoader foodLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public FoodDiaryDAO(DAOFactory factory) {
		this.factory = factory;
		this.foodLoader = new FoodDiaryLoader();
	}
	
	/**
	 * Returns of all list of food diary records correlating to the given MID
	 * 
	 * @param mid the patient's id
	 * @return a list of FoodDiaryBean with the information from all the food diary records
	 * correlating to the given MID
	 * @throws DBException
	 */
	public List<FoodDiaryBean> getFoodDiaryByMID(long mid)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM patientfooddiary WHERE MID=? ORDER BY ldate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<FoodDiaryBean> list = foodLoader.loadList(rs);
			rs.close();
			ps.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * 
	 * Inserts a Food diary entry to a given a food diary bean object
	 * @return true if added, exception will be thrown if something is wrong
	 * @throws ParseException 
	 * @
	 * 
	 * UPDATED 2/23/15 To work with food diary objects rather than just the MID also added date parses to SQL style rather than UTIL
	 * 
	 */
	public boolean addFoodDiary(FoodDiaryBean fd) throws DBException, ParseException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO patientfooddiary(mid, ldate, meal, food, servings, cals, fat, sodium, carbs, fiber, sugar, protein) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
			
			ps.setLong(1, fd.getPatientID());
			
			//needed to parse to get the date in the correct format
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
			sdf.setLenient(false); //make a no exception rule for the format
			Date parsedDate = sdf.parse(fd.getEntryDate()); //parse with format in mind
			ps.setDate(2, new java.sql.Date(parsedDate.getTime()));
			
			ps.setString(3, fd.getMeal());
			ps.setString(4, fd.getFood());
			ps.setDouble(5, fd.getServings());
			ps.setDouble(6, fd.getCals());
			ps.setDouble(7, fd.getFat());
			ps.setDouble(8, fd.getSodium());
			ps.setDouble(9, fd.getCarbs());
			ps.setDouble(10, fd.getFiber());
			ps.setDouble(11, fd.getSugar());
			ps.setDouble(12, fd.getProtein());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		DBUtil.closeConnection(conn, ps);
		return true;
	}
	
	/**
	 * 
	 * deletes a Food diary entry to a given a food diary bean object
	 * @return true if added, exception will be thrown if something is wrong
	 * @throws ParseException 
	 * 
	 * 
	 * UPDATED 2/23/15 To work with food diary objects rather than just the MID also added date parses to SQL style rather than UTIL
	 * 
	 */
	//ps = conn.prepareStatement("DELETE FROM patientfooddiary WHERE MID = ? and ldate = ? and meal = ? and food = ? and servings = ? and cals = ? and fat = ? and sodium = ? and carbs = ? and fiber = ? and sugar = ? and protein = ?");
	public boolean deleteFoodDiaryEntry(FoodDiaryBean fd) throws DBException, ParseException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("DELETE FROM patientfooddiary WHERE MID = ? and ldate = ? and meal = ? and food = ? and servings = ? and cals = ? and fat = ? and sodium = ? and carbs = ? and fiber = ? and sugar = ? and protein = ?");
			ps.setLong(1, fd.getPatientID());
			
			//needed to parse to get the date in the correct format
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
			sdf.setLenient(false); //make a no exception rule for the format
			Date parsedDate = sdf.parse(fd.getEntryDate()); //parse with format in mind
			ps.setDate(2, new java.sql.Date(parsedDate.getTime()));
			
			ps.setString(3, fd.getMeal());
			ps.setString(4, fd.getFood());
			ps.setDouble(5, fd.getServings());
			ps.setDouble(6, fd.getCals());
			ps.setDouble(7, fd.getFat());
			ps.setDouble(8, fd.getSodium());
			ps.setDouble(9, fd.getCarbs());
			ps.setDouble(10, fd.getFiber());
			ps.setDouble(11, fd.getSugar());
			ps.setDouble(12, fd.getProtein());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		DBUtil.closeConnection(conn, ps);
		return true;
	}
}
