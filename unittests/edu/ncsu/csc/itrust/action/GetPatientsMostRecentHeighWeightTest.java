package edu.ncsu.csc.itrust.action;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.dao.DAOFactory;
import edu.ncsu.csc.itrust.datagenerators.TestDataGenerator;
import edu.ncsu.csc.itrust.exception.ITrustException;
import edu.ncsu.csc.itrust.testutils.TestDAOFactory;

public class GetPatientsMostRecentHeighWeightTest extends TestCase {
	private DAOFactory factory = TestDAOFactory.getTestInstance();
	private TestDataGenerator gen = new TestDataGenerator();

	@Override
	protected void setUp() throws Exception {
		gen.clearAllTables();
		gen.standardData();
	}
	
	public void testHeight1() throws Exception {
		assertEquals(72.0, new GetPatientsMostRecentHeighWeight(factory).getPatientHeight(1));
	}
	
	public void testHeight2() throws Exception {
		assertEquals(62.0, new GetPatientsMostRecentHeighWeight(factory).getPatientHeight(2));
	}
	
	public void testHeight3() throws Exception {
		assertEquals(72.0, new GetPatientsMostRecentHeighWeight(factory).getPatientHeight(3));
	}
	
	public void testWeight1() throws Exception {
		assertEquals(185.0, new GetPatientsMostRecentHeighWeight(factory).getPatientWeight(1));
	}
	
	public void testWeight2() throws Exception {
		assertEquals(210.0, new GetPatientsMostRecentHeighWeight(factory).getPatientWeight(2));
	}
	
	public void testWeight3() throws Exception {
		assertEquals(180.0, new GetPatientsMostRecentHeighWeight(factory).getPatientWeight(3));
	}

}
