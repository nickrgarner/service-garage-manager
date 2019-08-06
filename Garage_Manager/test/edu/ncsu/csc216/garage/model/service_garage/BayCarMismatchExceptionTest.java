package edu.ncsu.csc216.garage.model.service_garage;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests BayCarMismatchException null constructor for correct default message
 * 
 * @author Nick Garner
 *
 */
public class BayCarMismatchExceptionTest {

	/**
	 * Tests BayCarMismatchException null constructor for correct default message
	 */
	@Test
	public void testBayCarMismatchException() {
		BayCarMismatchException e = new BayCarMismatchException();
		assertEquals("Vehicle type does not match Bay type.", e.getMessage());
	}

}
