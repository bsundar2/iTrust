package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.BodyMeasurementBean;

/**
 * @author Jay Patel
 * @author Weijia Li
 * @author Yuang Ni
 * @author Balaji Sundaram
 * 
 * A loader for BodyMeasurementBean.
 * 
 * Loads in information to/from beans using ResultSets and PreparedStatements. Use the superclass to enforce consistency. 
 * For details on the paradigm for a loader (and what its methods do), see {@link BeanLoader}
 */
public class BodyMeasurementLoader {
	
	public List<BodyMeasurementBean> loadList(ResultSet rs) throws SQLException {
		List<BodyMeasurementBean> list = new ArrayList<BodyMeasurementBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}
	
	/**
	 * @param rs ResultSet the result set of body measurement database information
	 * Used iteratively by other classes to load the result information into a list
	 * once the information from the result set is loaded it is sent to be displayed
	 */
	public BodyMeasurementBean loadSingle(ResultSet rs) throws SQLException {
		BodyMeasurementBean p = new BodyMeasurementBean();
		p.setPatientID(rs.getLong("MID"));
		p.setLogDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("ldate").getTime())));
		p.setWeight(rs.getDouble("weight"));
		p.setHeight(rs.getDouble("height"));
		p.setWaist(rs.getDouble("waist"));
		p.setArms(rs.getDouble("arms"));
		return p;
	}
	
	public PreparedStatement loadParameters(PreparedStatement ps, BodyMeasurementBean p) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}

}
