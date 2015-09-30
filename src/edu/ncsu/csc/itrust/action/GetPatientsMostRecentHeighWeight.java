package edu.ncsu.csc.itrust.action;

import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.exception.ITrustException;

public class GetPatientsMostRecentHeighWeight {
	private DAOFactory factory;

	/**
	 * Set up defaults
	 * 
	 * @param factory The DAOFactory used for creating the DAOs for this action.
	 */
	public GetPatientsMostRecentHeighWeight(DAOFactory factory) {
		this.factory = factory;
	}
	
	/**
	 * @author Yuang 
	 * Returns the person's most recent height that matches the inputMID param
	 * 
	 * @param inputMID The MID to look up.
	 * @return the person's name
	 * @throws DBException
	 * @throws ITrustException
	 */
	public double getPatientHeight(long inputMID) throws ITrustException {
		try {
			long mid = Long.valueOf(inputMID);
			return factory.getHealthRecordsDAO().getPatientsLatestHeight(mid);
		} catch (NumberFormatException e) {
			throw new ITrustException("MID not in correct form");
		}
	}
	
	/**
	 * @author Yuang 
	 * Returns the person's most recent weight that matches the inputMID param
	 * 
	 * @param inputMID The MID to look up.
	 * @return the person's name
	 * @throws DBException
	 * @throws ITrustException
	 */
	public double getPatientWeight(long inputMID) throws ITrustException {
		try {
			long mid = Long.valueOf(inputMID);
			return factory.getHealthRecordsDAO().getPatientsLatestWeight(mid);
		} catch (NumberFormatException e) {
			throw new ITrustException("MID not in correct form");
		}
	}
}
