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
import edu.ncsu.csc.itrust.beans.BodyMeasurementBean;
import edu.ncsu.csc.itrust.beans.loaders.BodyMeasurementLoader;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.DBException;

/**
 * @version 1.0 3/20/15
 * initial creation
 * 
 * @author Jay Patel
 * @author Weijia Li
 * @author Yuang Ni
 * @author Balaji Sundaram
 * 
 * This is the DAO file for the body measurement objects, currently its only tasks are to
 * call the sql tables to perform two functions adding a weight measurement entry by MID and then
 * retrieving all the entries based on MID.
 *
 */

public class BodyMeasurementDAO {
	private DAOFactory factory;
	private BodyMeasurementLoader bodyLoader;
	
	/**
	 * The typical constructor.
	 * @param factory The {@link DAOFactory} associated with this DAO, which is used for obtaining SQL connections, etc.
	 */
	public BodyMeasurementDAO(DAOFactory factory) {
		this.factory = factory;
		this.bodyLoader = new BodyMeasurementLoader();
	}
	
	/**
	 * Inserts a body measurement entry to a given a body measurement bean object
	 * @return true if added, exception will be thrown if something is wrong
	 * @throws ParseException 
	 */
	public boolean addBodyMeasurement(BodyMeasurementBean bm) throws DBException, ParseException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("INSERT INTO patientBodyMeasurements(mid, ldate, weight, height, waist, arms) VALUES(?,?,?,?,?,?)");
			
			ps.setLong(1, bm.getPatientID());
			
			//needed to parse to get the date in the correct format
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); //Set the format for the date
			sdf.setLenient(false); //make a no exception rule for the format
			Date parsedDate = sdf.parse(bm.getLogDate()); //parse with format in mind
			ps.setDate(2, new java.sql.Date(parsedDate.getTime()));
			
			ps.setDouble(3, bm.getWeight());
			ps.setDouble(4, bm.getHeight());
			ps.setDouble(5, bm.getWaist());
			ps.setDouble(6, bm.getArms());
			
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new DBException(e);
		}
		DBUtil.closeConnection(conn, ps);
		return true;
	}
	
	/**
	 * Returns of all list of body measurement records correlating to the given MID
	 * 
	 * @param mid the patient's id
	 * @return a list of FoodDiaryBean with the information from all the body measurement records
	 * correlating to the given MID
	 * @throws DBException
	 */
	public List<BodyMeasurementBean> getBodyMeasurementByMID(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM patientBodyMeasurements WHERE MID=? ORDER BY ldate DESC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<BodyMeasurementBean> list = bodyLoader.loadList(rs);
			rs.close();
			ps.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	public List<BodyMeasurementBean> getBodyMeasurementByMIDAscending(long mid) throws DBException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = factory.getConnection();
			ps = conn.prepareStatement("SELECT * FROM patientBodyMeasurements WHERE MID=? ORDER BY ldate ASC");
			ps.setLong(1, mid);
			ResultSet rs = ps.executeQuery();
			List<BodyMeasurementBean> list = bodyLoader.loadList(rs);
			rs.close();
			ps.close();
			return list;
		} catch (SQLException e) {
			throw new DBException(e);
		} finally {
			DBUtil.closeConnection(conn, ps);
		}
	}
	
	

}
