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
import edu.ncsu.csc.itrust.beans.DietSuggestionBean; 
import edu.ncsu.csc.itrust.beans.loaders.DietSuggestionBeanLoader; 
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

public class DietSuggestionDAO {
	private DAOFactory factory;
	private DietSuggestionBeanLoader suggestionLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public DietSuggestionDAO(DAOFactory factory) {
		this.factory = factory;
		this.suggestionLoader = new DietSuggestionBeanLoader();
	}
	
	/**
	 * Returns a list of all suggestions for the given MID
	 * 
	 * @param mid the patient's id
	 * @return a list of SuggestionBean with the information from all the suggestion records
	 * correlating to the given MID
	 * @throws DBException
	 */
	public List<DietSuggestionBean> getSuggestionByMID(long mid)
			throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM dietSuggestion WHERE MID=? ORDER BY ldate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<DietSuggestionBean> list = suggestionLoader.loadList(rs);
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
	 * Returns a specific suggestion bean for the given MID
	 * 
	 * @param mid the patient's id
	 * @return a SuggestionBean with the information from all the suggestion records
	 * correlating to the given MID
	 * @throws DBException
	 * @throws ParseException 
	 */
	public DietSuggestionBean getSuggestionByDate(long pid, String date)
			throws DBException, ParseException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM dietSuggestion WHERE MID=? AND ldate=?");
			
			ps.setLong(1, pid);
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
			sdf.setLenient(false); //make a no exception rule for the format
			Date parsedDate = sdf.parse(date); //parse with format in mind
			ps.setDate(2, new java.sql.Date(parsedDate.getTime()));
			
			ResultSet rs = ps.executeQuery();	
			
			if(! rs.first()){
//				System.out.println("Empty");
			}
			else {
//				System.out.println("Not empty");
			}
			
			DietSuggestionBean bean = suggestionLoader.loadSingle(rs);
//			System.out.println("Never gets here");
			
			rs.close();
			ps.close();
			return bean;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	/**
	 * Inserts a suggestion to a given a diet suggestion bean object
	 * @return true if added, exception will be thrown if something is wrong
	 * @throws ParseException 
	 * 
	 */
	public boolean addSuggestion(DietSuggestionBean ds) throws DBException, ParseException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO dietSuggestion (MID, ldate, suggestion) VALUES (?,?,?) ON DUPLICATE KEY UPDATE suggestion = VALUES(suggestion)");
			
			ps.setLong(1, ds.getPatientID());
			
			//needed to parse to get the date in the correct format
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
			sdf.setLenient(false); //make a no exception rule for the format
			Date parsedDate = sdf.parse(ds.getEntryDate()); //parse with format in mind
			ps.setDate(2, new java.sql.Date(parsedDate.getTime()));
			
			ps.setString(3, ds.getSuggestion());

			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		DBUtil.closeConnection(conn, ps);
		return true;
	}
}
