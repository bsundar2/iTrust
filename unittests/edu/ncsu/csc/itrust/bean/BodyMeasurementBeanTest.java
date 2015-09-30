package edu.ncsu.csc.itrust.bean;

import junit.framework.TestCase;
import edu.ncsu.csc.itrust.beans.BodyMeasurementBean;

/**
 * 
 * @author Team 19
 * This test class simply creates a body measurement bean object and sets some values
 * if the values are set correctly then the test will pass by using the getters.
 *
 */
public class BodyMeasurementBeanTest extends TestCase {

	public void testBodyMeasurementBean(){
		//Create object
		BodyMeasurementBean bodyBean = new BodyMeasurementBean();
		
		//fill with some data
		bodyBean.setPatientID(999);
		bodyBean.setLogDate("11/11/1911");
		bodyBean.setWeight(150.2);
		bodyBean.setHeight(65.3);
		bodyBean.setWaist(30.7);
		bodyBean.setArms(60.0);
		
		assertEquals(999, bodyBean.getPatientID());
		assertEquals("11/11/1911", bodyBean.getLogDate());
		assertEquals(150.2, bodyBean.getWeight());
		assertEquals(65.3, bodyBean.getHeight());
		assertEquals(30.7, bodyBean.getWaist());
		assertEquals(60.0, bodyBean.getArms());

	}
}
