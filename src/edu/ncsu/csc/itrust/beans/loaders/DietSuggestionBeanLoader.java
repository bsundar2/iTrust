package edu.ncsu.csc.itrust.beans.loaders;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.ncsu.csc.itrust.beans.DietSuggestionBean;

	
public class DietSuggestionBeanLoader implements BeanLoader<DietSuggestionBean> {
		
	public List<DietSuggestionBean> loadList(ResultSet rs) throws SQLException {
		List<DietSuggestionBean> list = new ArrayList<DietSuggestionBean>();
		while (rs.next()) {
			list.add(loadSingle(rs));
		}
		return list;
	}

	/**
	 * @param rs ResultSet the result set of DietSuggestion database information
	 * Used iteratively by other classes to load the result information into a list
	 * once the information from the result set is loaded it is sent to be displayed
	 */
	public DietSuggestionBean loadSingle(ResultSet rs) throws SQLException {
		DietSuggestionBean p = new DietSuggestionBean();
		p.setPatientID(rs.getLong("MID"));
		p.setEntryDate(new SimpleDateFormat("MM/dd/yyyy").format(new Date(rs.getDate("ldate").getTime())));
		p.setSuggestion(rs.getString("suggestion"));
		
		return p;
	}

	public PreparedStatement loadParameters(PreparedStatement ps, DietSuggestionBean p) throws SQLException {
		throw new IllegalStateException("unimplemented!");
	}

}
