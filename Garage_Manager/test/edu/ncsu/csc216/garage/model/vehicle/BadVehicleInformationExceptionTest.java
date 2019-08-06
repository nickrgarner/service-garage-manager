package edu.ncsu.csc216.garage.model.vehicle;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests BadVehicleInformationException null constructor for correct default message.
 * 
 * @author Nick Garner
 *
 */
public class BadVehicleInformationExceptionTest {

	/**
	 * Tests BadVehicleInformationException null constructor for default message.
	 */
	@Test
	public void testBadVehicleInformationException() {
		BadVehicleInformationException e = new BadVehicleInformationException();
		assertEquals("Vehicle information is not valid.", e.getMessage());
	}

}
